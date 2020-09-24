package com.computablefacts.nona;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.computablefacts.nona.functions.additiveoperators.Add;
import com.computablefacts.nona.functions.additiveoperators.Substract;
import com.computablefacts.nona.functions.assignmentoperators.Is;
import com.computablefacts.nona.functions.booleanlogicoperators.And;
import com.computablefacts.nona.functions.booleanlogicoperators.IsFalse;
import com.computablefacts.nona.functions.booleanlogicoperators.IsTrue;
import com.computablefacts.nona.functions.booleanlogicoperators.Not;
import com.computablefacts.nona.functions.booleanlogicoperators.Or;
import com.computablefacts.nona.functions.comparisonoperators.Equal;
import com.computablefacts.nona.functions.comparisonoperators.GreaterThan;
import com.computablefacts.nona.functions.comparisonoperators.GreaterThanOrEqual;
import com.computablefacts.nona.functions.comparisonoperators.LessThan;
import com.computablefacts.nona.functions.comparisonoperators.LessThanOrEqual;
import com.computablefacts.nona.functions.controlflowoperators.If;
import com.computablefacts.nona.functions.csvoperators.CsvValue;
import com.computablefacts.nona.functions.csvoperators.NbCsvRows;
import com.computablefacts.nona.functions.csvoperators.ToCsv;
import com.computablefacts.nona.functions.jsonoperators.NbJsonObjects;
import com.computablefacts.nona.functions.jsonoperators.ToJson;
import com.computablefacts.nona.functions.mathematicaloperators.Max;
import com.computablefacts.nona.functions.mathematicaloperators.Min;
import com.computablefacts.nona.functions.multiplicativeoperators.Divide;
import com.computablefacts.nona.functions.multiplicativeoperators.Multiply;
import com.computablefacts.nona.functions.patternoperators.*;
import com.computablefacts.nona.functions.patternoperators.Number;
import com.computablefacts.nona.functions.stringoperators.Concat;
import com.computablefacts.nona.functions.stringoperators.Contain;
import com.computablefacts.nona.functions.stringoperators.EndWith;
import com.computablefacts.nona.functions.stringoperators.IndexOf;
import com.computablefacts.nona.functions.stringoperators.MatchDictionary;
import com.computablefacts.nona.functions.stringoperators.MatchFuzzy;
import com.computablefacts.nona.functions.stringoperators.MatchRegex;
import com.computablefacts.nona.functions.stringoperators.MatchWildcard;
import com.computablefacts.nona.functions.stringoperators.Snippet;
import com.computablefacts.nona.functions.stringoperators.StartWith;
import com.computablefacts.nona.functions.stringoperators.StrLength;
import com.computablefacts.nona.functions.stringoperators.Substring;
import com.computablefacts.nona.functions.stringoperators.ToInteger;
import com.computablefacts.nona.functions.stringoperators.ToLowerCase;
import com.computablefacts.nona.functions.stringoperators.ToUpperCase;
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
  private eCategory category_;
  private String description_;

  public Function(String expression) {
    parseFunction(Preconditions.checkNotNull(expression, "expression should not be null"));
  }

  protected Function(eCategory category, String expression, String description) {
    category_ = Preconditions.checkNotNull(category, "category should not be null");
    name_ = Preconditions.checkNotNull(expression, "expression should not be null").trim()
        .toUpperCase();
    description_ = Preconditions.checkNotNull(description, "description should not be null").trim();
  }

  public static Map<String, Function> definitions() {

    Map<String, Function> definitions = new HashMap<>();

    // Additive operators
    definitions.put("ADD", new Add());
    definitions.put("SUB", new Substract());

    // Assignment operator
    definitions.put("IS", new Is());

    // Boolean logic operators
    definitions.put("AND", new And());
    definitions.put("IS_FALSE", new IsFalse());
    definitions.put("IS_TRUE", new IsTrue());
    definitions.put("NOT", new Not());
    definitions.put("OR", new Or());

    // Comparison operators
    definitions.put("EQUAL", new Equal());
    definitions.put("GT", new GreaterThan());
    definitions.put("GTE", new GreaterThanOrEqual());
    definitions.put("LT", new LessThan());
    definitions.put("LTE", new LessThanOrEqual());

    // Control flow operators
    definitions.put("IF", new If());

    // Csv operators
    definitions.put("CSV_VALUE", new CsvValue());
    definitions.put("NB_CSV_ROWS", new NbCsvRows());
    definitions.put("TO_CSV", new ToCsv());

    // Json operators
    definitions.put("NB_JSON_OBJECTS", new NbJsonObjects());
    definitions.put("TO_JSON", new ToJson());

    // Mathematical operators
    definitions.put("MAX", new Max());
    definitions.put("MIN", new Min());

    // Multiplicative operators
    definitions.put("DIV", new Divide());
    definitions.put("MUL", new Multiply());

    // Pattern operators
    definitions.put("MATCH_BASE64", new Base64());
    definitions.put("MATCH_BIC", new Bic());
    definitions.put("MATCH_COMPANY_NAME", new CompanyWithElf());
    definitions.put("MATCH_CSS_PROPERTIES", new CssProperties());
    definitions.put("MATCH_DATE", new Date());
    definitions.put("MATCH_DATE_TIME", new DateTime());
    definitions.put("MATCH_ECMASCRIPT6_KEYWORDS", new EcmaScript6Keywords());
    definitions.put("MATCH_EMAIL", new Email());
    definitions.put("MATCH_FINANCIAL_NUMBER", new FinancialNumber());
    definitions.put("MATCH_HTML_WINDOW_OBJECT_PROPERTIES", new HtmlWindowObjectProperties());
    definitions.put("MATCH_IBAN", new Iban());
    definitions.put("MATCH_IPV4", new IpV4());
    definitions.put("MATCH_IPV6", new IpV6());
    definitions.put("MATCH_JS_OBJECTS_PROPERTIES_AND_METHODS", new JsObjectsPropertiesAndMethods());
    definitions.put("MATCH_MONETARY_AMOUNT", new MonetaryAmount());
    definitions.put("MATCH_NUMBER", new Number());
    definitions.put("MATCH_ONION", new Onion());
    definitions.put("MATCH_PERCENT", new Percent());
    definitions.put("MATCH_TIME", new Time());
    definitions.put("MATCH_UNIX_PATH", new UnixPath());
    definitions.put("MATCH_URL", new Url());
    definitions.put("MATCH_WIN_PATH", new WinPath());
    definitions.put("MATCH_WORD_WITH_APOSTROPHES_OR_DASHES", new WordWithApostrophesOrDashes());
    definitions.put("MATCH_WORD_WITHOUT_APOSTROPHES_OR_DASHES",
        new WordWithoutApostrophesOrDashes());

    // String operators
    definitions.put("CONCAT", new Concat());
    definitions.put("CONTAIN", new Contain());
    definitions.put("END_WITH", new EndWith());
    definitions.put("INDEX_OF", new IndexOf());
    definitions.put("MATCH_DICTIONARY", new MatchDictionary());
    definitions.put("MATCH_FUZZY", new MatchFuzzy());
    definitions.put("MATCH_REGEX", new MatchRegex());
    definitions.put("MATCH_WILDCARD", new MatchWildcard());
    definitions.put("SNIPPET", new Snippet());
    definitions.put("START_WITH", new StartWith());
    definitions.put("STR_LENGTH", new StrLength());
    definitions.put("SUBSTRING", new Substring());
    definitions.put("TO_INTEGER", new ToInteger());
    definitions.put("TO_LOWERCASE", new ToLowerCase());
    definitions.put("TO_UPPERCASE", new ToUpperCase());

    return definitions;
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

  public eCategory category() {
    return category_;
  }

  public String description() {
    return description_;
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
