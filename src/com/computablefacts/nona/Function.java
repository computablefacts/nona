package com.computablefacts.nona;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.computablefacts.nona.functions.additiveoperators.Add;
import com.computablefacts.nona.functions.additiveoperators.Substract;
import com.computablefacts.nona.functions.assignmentoperators.Is;
import com.computablefacts.nona.functions.booleanlogicoperators.And;
import com.computablefacts.nona.functions.booleanlogicoperators.IsBlank;
import com.computablefacts.nona.functions.booleanlogicoperators.IsEmpty;
import com.computablefacts.nona.functions.booleanlogicoperators.IsFalse;
import com.computablefacts.nona.functions.booleanlogicoperators.IsNull;
import com.computablefacts.nona.functions.booleanlogicoperators.IsNullOrBlank;
import com.computablefacts.nona.functions.booleanlogicoperators.IsNullOrEmpty;
import com.computablefacts.nona.functions.booleanlogicoperators.IsTrue;
import com.computablefacts.nona.functions.booleanlogicoperators.Not;
import com.computablefacts.nona.functions.booleanlogicoperators.Or;
import com.computablefacts.nona.functions.comparisonoperators.Equal;
import com.computablefacts.nona.functions.comparisonoperators.GreaterThan;
import com.computablefacts.nona.functions.comparisonoperators.GreaterThanOrEqual;
import com.computablefacts.nona.functions.comparisonoperators.LessThan;
import com.computablefacts.nona.functions.comparisonoperators.LessThanOrEqual;
import com.computablefacts.nona.functions.controlflowoperators.If;
import com.computablefacts.nona.functions.controlflowoperators.Switch;
import com.computablefacts.nona.functions.controlflowoperators.Which;
import com.computablefacts.nona.functions.csvoperators.CsvValue;
import com.computablefacts.nona.functions.csvoperators.NbCsvRows;
import com.computablefacts.nona.functions.csvoperators.ToCsv;
import com.computablefacts.nona.functions.dateoperators.AddDays;
import com.computablefacts.nona.functions.dateoperators.AddHours;
import com.computablefacts.nona.functions.dateoperators.AddMinutes;
import com.computablefacts.nona.functions.dateoperators.AddMonths;
import com.computablefacts.nona.functions.dateoperators.AddSeconds;
import com.computablefacts.nona.functions.dateoperators.AddYears;
import com.computablefacts.nona.functions.dateoperators.ElapsedDays;
import com.computablefacts.nona.functions.jsonoperators.NbJsonObjects;
import com.computablefacts.nona.functions.jsonoperators.ToJson;
import com.computablefacts.nona.functions.mathematicaloperators.Ceil;
import com.computablefacts.nona.functions.mathematicaloperators.Floor;
import com.computablefacts.nona.functions.mathematicaloperators.Max;
import com.computablefacts.nona.functions.mathematicaloperators.Min;
import com.computablefacts.nona.functions.multiplicativeoperators.Divide;
import com.computablefacts.nona.functions.multiplicativeoperators.Mod;
import com.computablefacts.nona.functions.multiplicativeoperators.Multiply;
import com.computablefacts.nona.functions.patternoperators.*;
import com.computablefacts.nona.functions.patternoperators.Number;
import com.computablefacts.nona.functions.stringoperators.*;
import com.computablefacts.nona.helpers.StringIterator;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Var;

@CheckReturnValue
public class Function {

  private static final Cache<String, BoxedType<?>> cache_ = CacheBuilder.newBuilder().recordStats()
      .maximumSize(1000).expireAfterWrite(1, TimeUnit.HOURS).build();
  private static final HashFunction MURMUR3_128 = Hashing.murmur3_128();
  private Atom head_;
  private Atom body_;

  public Function(String expression) {
    parseFunction(Preconditions.checkNotNull(expression, "expression should not be null"));
  }

  protected Function(eCategory category, String expression, String description) {
    head_ = new Atom(expression, new ArrayList<>(), category, description);
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
    definitions.put("IS_BLANK", new IsBlank());
    definitions.put("IS_EMPTY", new IsEmpty());
    definitions.put("IS_FALSE", new IsFalse());
    definitions.put("IS_NULL", new IsNull());
    definitions.put("IS_NULL_OR_BLANK", new IsNullOrBlank());
    definitions.put("IS_NULL_OR_EMPTY", new IsNullOrEmpty());
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
    definitions.put("SWITCH", new Switch());
    definitions.put("WHICH", new Which());

    // Csv operators
    definitions.put("CSV_VALUE", new CsvValue());
    definitions.put("NB_CSV_ROWS", new NbCsvRows());
    definitions.put("TO_CSV", new ToCsv());

    // Date operators
    definitions.put("ADD_DAYS", new AddDays());
    definitions.put("ADD_HOURS", new AddHours());
    definitions.put("ADD_MINUTES", new AddMinutes());
    definitions.put("ADD_MONTHS", new AddMonths());
    definitions.put("ADD_SECONDS", new AddSeconds());
    definitions.put("ADD_YEARS", new AddYears());
    definitions.put("ELAPSED_DAYS", new ElapsedDays());

    // Json operators
    definitions.put("NB_JSON_OBJECTS", new NbJsonObjects());
    definitions.put("TO_JSON", new ToJson());

    // Mathematical operators
    definitions.put("CEIL", new Ceil());
    definitions.put("FLOOR", new Floor());
    definitions.put("MAX", new Max());
    definitions.put("MIN", new Min());

    // Multiplicative operators
    definitions.put("DIV", new Divide());
    definitions.put("MOD", new Mod());
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
    definitions.put("FILL_TEMPLATE", new FillTemplate());
    definitions.put("HASH", new Hash());
    definitions.put("INDEX_OF", new IndexOf());
    definitions.put("MATCH_DICTIONARY", new MatchDictionary());
    definitions.put("MATCH_FUZZY", new MatchFuzzy());
    definitions.put("MATCH_REGEX", new MatchRegex());
    definitions.put("MATCH_WILDCARD", new MatchWildcard());
    definitions.put("SNIPPET", new Snippet());
    definitions.put("START_WITH", new StartWith());
    definitions.put("STR_LENGTH", new StrLength());
    definitions.put("SUBSTRING", new Substring());
    definitions.put("TO_DATE", new ToDate());
    definitions.put("TO_DECIMAL", new ToDecimal());
    definitions.put("TO_INTEGER", new ToInteger());
    definitions.put("TO_LOWERCASE", new ToLowerCase());
    definitions.put("TO_TEXT", new ToText());
    definitions.put("TO_UPPERCASE", new ToUpperCase());
    definitions.put("TRIM", new Trim());

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
   * \u002c. Replace carriage return by its unicode equivalent \u000d. Replace line feed by its
   * unicode equivalent \u000a. Replace equal return by its unicode equivalent \u003d. Replace colon
   * by its unicode equivalent \u003a.
   *
   * @param text Text to encode.
   * @return Encoded text.
   */
  public static String encode(String text) {

    Preconditions.checkNotNull(text, "text should not be null");

    StringBuilder builder = new StringBuilder(text.length());

    for (int i = 0; i < text.length(); i++) {
      char c = text.charAt(i);
      switch (c) {
        case '(':
          builder.append("\\u0028");
          break;
        case ')':
          builder.append("\\u0029");
          break;
        case '"':
          builder.append("\\u0022");
          break;
        case ',':
          builder.append("\\u002c");
          break;
        case ':':
          builder.append("\\u003a");
          break;
        case '=':
          builder.append("\\u003d");
          break;
        case '\n':
          builder.append("\\u000d");
          break;
        case '\r':
          builder.append("\\u000a");
          break;
        default:
          builder.append(c);
          break;
      }
    }
    return builder.toString();
  }

  /**
   * Replace unicode values \u0028 and \u0029 by the left and right parentheses characters. Replace
   * unicode values \u0022 by quotation marks. Replace unicode value \u002c by the comma character.
   * Replace unicode value \u000d by the carriage return character. Replace unicode value \u000a by
   * the line feed character. Replace unicode value \u003d by the equal character. Replace unicode
   * value \u003a by the colon character.
   *
   * @param text Text to decode.
   * @return Decoded text.
   */
  public static String decode(String text) {

    Preconditions.checkNotNull(text, "text should not be null");

    StringBuilder builder = new StringBuilder(text.length());
    StringIterator iterator = new StringIterator(text);

    while (iterator.hasNext()) {
      char c0 = iterator.next();
      if (c0 != '\\' || !iterator.hasNext()) {
        builder.append(c0);
      } else {
        char c1 = iterator.next();
        if (c1 != 'u' || !iterator.hasNext()) {
          builder.append(c0).append(c1);
        } else {
          char c2 = iterator.next();
          if (c2 != '0' || !iterator.hasNext()) {
            builder.append(c0).append(c1).append(c2);
          } else {
            char c3 = iterator.next();
            if (c3 != '0' || !iterator.hasNext()) {
              builder.append(c0).append(c1).append(c2).append(c3);
            } else {
              char c4 = iterator.next();
              if ((c4 != '0' && c4 != '2' && c4 != '3') || !iterator.hasNext()) {
                builder.append(c0).append(c1).append(c2).append(c3).append(c4);
              } else {
                char c5 = iterator.next();
                if (c4 == '0' && c5 == 'a') {
                  builder.append('\r');
                } else if (c4 == '0' && c5 == 'd') {
                  builder.append('\n');
                } else if (c4 == '2' && c5 == '2') {
                  builder.append('"');
                } else if (c4 == '2' && c5 == '8') {
                  builder.append('(');
                } else if (c4 == '2' && c5 == '9') {
                  builder.append(')');
                } else if (c4 == '2' && c5 == 'c') {
                  builder.append(',');
                } else if (c4 == '3' && c5 == 'a') {
                  builder.append(':');
                } else if (c4 == '3' && c5 == 'd') {
                  builder.append('=');
                } else {
                  builder.append(c0).append(c1).append(c2).append(c3).append(c4).append(c5);
                }
              }
            }
          }
        }
      }
    }
    return builder.toString();
  }

  @Generated
  public String name() {
    return head_.name();
  }

  @Generated
  public List<Function> parameters() {
    return head_.parameters();
  }

  @Generated
  public eCategory category() {
    return head_.category();
  }

  @Generated
  public String description() {
    return head_.description();
  }

  @Generated
  public int arity() {
    return head_.arity();
  }

  public boolean isValid() {
    if (!Strings.isNullOrEmpty(head_.name())) {
      for (Function fn : head_.parameters()) {
        if (!fn.isValid()) {
          return false;
        }
      }
      return true;
    }
    return head_.parameters().isEmpty();
  }

  public boolean hasReferenceTo(String function) {

    Preconditions.checkArgument(!Strings.isNullOrEmpty(function),
        "function should neither be null nor empty");

    if (!Strings.isNullOrEmpty(head_.name())) {
      for (Function fn : head_.parameters()) {
        if (fn.hasReferenceTo(function)) {
          return true;
        }
      }
      return head_.name().equals(function);
    }
    return false;
  }

  public BoxedType evaluate() {
    return evaluate(null, null);
  }

  public BoxedType<?> evaluate(Map<String, Function> definitions) {
    return evaluate(definitions, null);
  }

  public BoxedType<?> evaluate(Map<String, Function> definitions,
      Map<String, BoxedType<?>> substitutions) {

    if (!isValid()) {
      return null;
    }

    if (definitions == null || !definitions.containsKey(head_.name())) {
      if (substitutions != null && substitutions.containsKey(head_.name())) {
        return substitutions.get(head_.name());
      }
      return box(head_.name());
    }

    Function function = definitions.get(head_.name());

    if (function == null || function.body_ == null) {
      return evaluate(definitions, substitutions, head_);
    }

    Preconditions.checkState(head_.arity() == function.arity(),
        "Mismatch between the head arity and the function definition: %s found vs %s expected",
        head_.arity(), function.arity());

    List<BoxedType<?>> parameters = new ArrayList<>(head_.arity());

    for (Function fn : head_.parameters()) {
      parameters.add(fn.evaluate(definitions, substitutions));
    }

    Map<String, BoxedType<?>> substs = new HashMap<>();

    for (int i = 0; i < head_.arity(); i++) {
      substs.put(function.parameters().get(i).evaluate(definitions).asString(), parameters.get(i));
    }
    return evaluate(definitions, substs, function.body_);
  }

  /**
   * Execute the current function.
   *
   * @param parameters the function parameters.
   * @return computed value.
   */
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {
    throw new RuntimeException(
        "Function " + head_.name() + "/" + parameters.size() + " is not implemented.");
  }

  /**
   * Override this method if the value computed by {@link Function#evaluate(List)} is not always the
   * same for a fixed set of parameters.
   *
   * @return true iif the value returned by {@link Function#evaluate(List)} is cacheable, false
   *         otherwise.
   */
  protected boolean isCacheable() {
    return true;
  }

  /**
   * Coerce object but ensure strings in scientific notation are not coerced to
   * BigInteger/BigDecimal i.e. "79E2863560" should not be interpreted as 7.9E+2863561
   *
   * @param obj object to coerce.
   * @return boxed type.
   */
  protected BoxedType<?> box(Object obj) {
    return BoxedType.create(obj, false);
  }

  private void parseFunction(String expression) {

    int indexBody = expression.indexOf(":=");

    if (indexBody < 0) {
      head_ = parseAtom(expression.trim());
    } else {
      head_ = parseAtom(expression.substring(0, indexBody).trim());
      body_ = parseAtom(expression.substring(indexBody + 2).trim());
    }
  }

  private Atom parseAtom(String expression) {

    int indexArgsBegin = expression.indexOf('(');
    int indexArgsEnd = expression.lastIndexOf(')');

    if (indexArgsBegin < 0 && indexArgsEnd < 0) {
      return new Atom(expression);
    }

    Preconditions.checkState(indexArgsBegin > 0,
        "\"" + expression + "\" is an invalid expression. Missing \"(\".");
    Preconditions.checkState(indexArgsEnd > 0,
        "\"" + expression + "\" is an invalid expression. Missing \")\".");

    String name = expression.substring(0, indexArgsBegin).trim().toUpperCase();

    if ("_".equals(name)) {
      return new Atom(decode(expression.substring(indexArgsBegin + 1, indexArgsEnd)));
    }

    List<Function> parameters = new ArrayList<>();

    for (String parameter : parseParameters(
        expression.substring(indexArgsBegin + 1, indexArgsEnd))) {
      parameters.add(new Function(parameter));
    }
    return new Atom(name, parameters);
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

  private BoxedType<?> evaluate(Map<String, Function> definitions,
      Map<String, BoxedType<?>> substitutions, Atom atom) {

    Preconditions.checkNotNull(atom, "atom should not be null");

    List<BoxedType<?>> parameters = new ArrayList<>(atom.arity());

    for (Function fn : atom.parameters()) {
      parameters.add(fn.evaluate(definitions, substitutions));
    }

    Function function = definitions.get(atom.name());

    if (!function.isCacheable()) {
      return function.evaluate(parameters);
    }

    try {
      Hasher hasher = MURMUR3_128.newHasher();
      hasher.putString(atom.name(), StandardCharsets.UTF_8);
      hasher.putInt(atom.arity());
      hasher.putInt(parameters.size());
      parameters.forEach(
          bt -> hasher.putString(Strings.nullToEmpty(bt.asString()), StandardCharsets.UTF_8));
      String key = hasher.hash().toString();
      return cache_.get(key, () -> function.evaluate(parameters));
    } catch (ExecutionException e) {
      // TODO
    }
    return null;
  }

  final static class Atom {

    private final String name_;
    private final List<Function> parameters_;

    private final eCategory category_;
    private final String description_;

    public Atom(String name) {
      this(name, new ArrayList<>());
    }

    public Atom(String name, List<Function> parameters) {
      this(name, parameters, eCategory.UNKNOWN, eCategory.UNKNOWN.name());
    }

    public Atom(String name, List<Function> parameters, eCategory category, String description) {

      Preconditions.checkNotNull(name, "name should not be null");
      Preconditions.checkNotNull(parameters, "parameters should not be null");
      Preconditions.checkNotNull(category, "category should not be null");
      Preconditions.checkNotNull(description, "description should not be null");

      name_ = name;
      parameters_ = parameters;
      category_ = category;
      description_ = description;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == this) {
        return true;
      }
      if (obj == null || getClass() != obj.getClass()) {
        return false;
      }
      Atom other = (Atom) obj;
      return Objects.equal(name_, other.name_) && Objects.equal(parameters_, other.parameters_)
          && Objects.equal(category_, other.category_)
          && Objects.equal(description_, other.description_);
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(name_, parameters_, category_, description_);
    }

    @Generated
    @Override
    public String toString() {
      return MoreObjects.toStringHelper(this).add("name", name_).add("parameters", parameters_)
          .add("category", category_).add("description", description_).add("arity", arity())
          .omitNullValues().toString();
    }

    @Generated
    public String name() {
      return name_;
    }

    @Generated
    public List<Function> parameters() {
      return parameters_;
    }

    @Generated
    public eCategory category() {
      return category_;
    }

    @Generated
    public String description() {
      return description_;
    }

    @Generated
    public int arity() {
      return parameters_.size();
    }
  }
}
