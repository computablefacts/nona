package com.computablefacts.nona;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Var;

@CheckReturnValue
public class Function {

  private static final Cache<String, BoxedType<?>> cache_ = CacheBuilder.newBuilder().recordStats()
      .maximumSize(1000).expireAfterWrite(1, TimeUnit.HOURS).build();
  private final List<Function> parameters_ = new ArrayList<>();
  private String name_;

  public Function(String expression) {
    this(expression, false);
  }

  protected Function(String expression, boolean isDefinition) {
    if (isDefinition) {
      name_ = Preconditions.checkNotNull(expression).trim().toUpperCase();
    } else {
      parseFunction(Preconditions.checkNotNull(expression));
    }
  }

  /**
   * Wrap a text inside the special function _(&lt;text&gt;). This function ensures that whatever
   * characters the text contain, it will be interpreted as a {@link String}. Useful to escape
   * {@link String} with parentheses and quotation marks.
   * 
   * @param text Text to wrap.
   * @return Wrapped text.
   */
  public static String wrap(String text) {
    return "_(" + encode(text) + ")";
  }

  /**
   * Replace left and right parentheses by their unicode equivalent \u0028 and \u0029. Replace
   * quotation marks with their unicode equivalent \u0022. Replace comma by its unicode equivalent
   * \u002c.
   *
   * @param text Text to encode.
   * @return Encoded text.
   */
  public static String encode(String text) {
    return Preconditions.checkNotNull(text, "text should not be null").replace("(", "\\u0028")
        .replace(")", "\\u0029").replace("\"", "\\u0022").replace(",", "\\u002c");
  }

  /**
   * Replace unicode values \u0028 and \u0029 by the left and right parentheses characters. Replace
   * unicode values \u0022 by quotation marks. Replace unicode value \u002c by the comma character.
   *
   * @param text Text to decode.
   * @return Decoded text.
   */
  public static String decode(String text) {
    return Preconditions.checkNotNull(text, "text should not be null").replace("\\u0028", "(")
        .replace("\\u0029", ")").replace("\\u0022", "\"").replace("\\u002c", ",");
  }

  public String name() {
    return name_;
  }

  public int arity() {
    return parameters_.size();
  }

  public boolean isValid() {
    if (!Strings.isNullOrEmpty(name_)) {
      for (Function fn : parameters_) {
        if (!fn.isValid()) {
          return false;
        }
      }
      return true;
    }
    return parameters_.isEmpty();
  }

  public boolean hasReferenceTo(String function) {

    Preconditions.checkArgument(!Strings.isNullOrEmpty(function));

    if (!Strings.isNullOrEmpty(name_)) {
      for (Function fn : parameters_) {
        if (fn.hasReferenceTo(function)) {
          return true;
        }
      }
      return name_.equals(function);
    }
    return false;
  }

  public BoxedType evaluate() {
    return evaluate((Map<String, Function>) null);
  }

  public BoxedType evaluate(Map<String, Function> definitions) {

    if (!isValid()) {
      return null;
    }

    if (definitions == null || !definitions.containsKey(name_)) {
      return BoxedType.create(name_);
    }

    List<BoxedType> parameters = new ArrayList<>(parameters_.size());

    for (Function fn : parameters_) {
      parameters.add(fn.evaluate(definitions));
    }

    Function function = definitions.get(name_);

    if (!function.isCacheable()) {
      return function.evaluate(parameters);
    }

    try {
      String key = name_ + "(" + Joiner.on(',')
          .join(parameters.stream().map(BoxedType::asString).collect(Collectors.toList())) + ")";
      return cache_.get(key, () -> function.evaluate(parameters));
    } catch (ExecutionException e) {
      // TODO
    }
    return null;
  }

  /**
   * Execute the current function.
   *
   * @param parameters the function parameters.
   * @return computed value.
   */
  public BoxedType evaluate(List<BoxedType> parameters) {
    throw new RuntimeException(
        "Function " + name_ + "/" + parameters.size() + " is not implemented.");
  }

  /**
   * Override this method if the value computed by {@link Function#evaluate(List)} will always be
   * the same for a fixed set of parameters.
   *
   * @return true iif the value returned by {@link Function#evaluate(List)} is cacheable, false
   *         otherwise.
   */
  protected boolean isCacheable() {
    return true;
  }

  private void parseFunction(String expression) {

    int indexArgsBegin = expression.indexOf('(');
    int indexArgsEnd = expression.lastIndexOf(')');

    if (indexArgsBegin < 0 && indexArgsEnd < 0) {
      name_ = expression;
      return;
    }

    Preconditions.checkState(indexArgsBegin > 0,
        "\"" + expression + "\" is an invalid expression. Missing \"(\".");
    Preconditions.checkState(indexArgsEnd > 0,
        "\"" + expression + "\" is an invalid expression. Missing \")\".");

    name_ = expression.substring(0, indexArgsBegin).trim().toUpperCase();

    if ("_".equals(name_)) {
      name_ = decode(expression.substring(indexArgsBegin + 1, indexArgsEnd));
      return;
    }

    for (String parameter : parseParameters(
        expression.substring(indexArgsBegin + 1, indexArgsEnd))) {
      parameters_.add(new Function(parameter));
    }
  }

  private List<String> parseParameters(String parameters) {

    List<String> functions = new ArrayList<>();
    @Var
    int nbParenthesis = 0;
    @Var
    int myIndex = 0;
    @Var
    int myIndexPrev = myIndex;
    @Var
    boolean hasOneMoreParameter = false;

    while (myIndex < parameters.length()) {
      if (parameters.charAt(myIndex) == '(') {
        nbParenthesis++;
      } else if (parameters.charAt(myIndex) == ')') {
        nbParenthesis--;
      } else if (parameters.charAt(myIndex) == '"') {
        myIndex++;
        while (myIndex < parameters.length() && parameters.charAt(myIndex) != '"') {
          myIndex++;
        }
      } else if (parameters.charAt(myIndex) == ',' && nbParenthesis == 0) {
        String parameter = parameters.substring(myIndexPrev, myIndex).trim();
        functions.add(parameter);
        myIndexPrev = myIndex + 1;
        hasOneMoreParameter = true;
      }
      myIndex++;
    }

    if (nbParenthesis == 0) { // Do not forget the last parameter!
      String parameter = parameters.substring(myIndexPrev, myIndex).trim();
      if (hasOneMoreParameter || !parameter.isEmpty()) {
        functions.add(parameter);
      }
    }
    return functions;
  }
}
