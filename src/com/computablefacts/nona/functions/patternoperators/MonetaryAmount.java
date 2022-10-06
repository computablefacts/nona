package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsForward.monetaryAmount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.computablefacts.asterix.Span;
import com.computablefacts.asterix.SpanSequence;
import com.computablefacts.asterix.BoxedType;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class MonetaryAmount extends MatchPattern {

  private static final Map<Integer, String> GROUPS = new HashMap<>();

  static {
    GROUPS.put(2, "SYMBOL1");
    GROUPS.put(3, "FINANCIAL_NUMBER");
    GROUPS.put(4, "NUMBER");
    GROUPS.put(5, "SYMBOL2");
  }

  public MonetaryAmount() {
    super("MONETARY_AMOUNT", monetaryAmount(), GROUPS);
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1,
        "MONETARY_AMOUNT takes exactly one parameter : %s", parameters);

    BoxedType<?> boxedType = super.evaluate(parameters);
    SpanSequence sequence = (SpanSequence) boxedType.value();
    SpanSequence newSequence = new SpanSequence();

    for (Span span : sequence) {

      String symbol1 = span.getFeature("SYMBOL1");
      String financialNumber = span.getFeature("FINANCIAL_NUMBER");
      String number = span.getFeature("NUMBER");
      String symbol2 = span.getFeature("SYMBOL2");

      if (!Strings.isNullOrEmpty(symbol1) || !Strings.isNullOrEmpty(symbol2)) {

        span.addTag("MONETARY_AMOUNT");
        span.setFeature("CURRENCY", Strings.isNullOrEmpty(symbol1) ? symbol2 : symbol1);
        span.setFeature("AMOUNT",
            Strings.isNullOrEmpty(financialNumber) ? number : financialNumber);

        span.removeFeature("SYMBOL1");
        span.removeFeature("FINANCIAL_NUMBER");
        span.removeFeature("NUMBER");
        span.removeFeature("SYMBOL2");
        span.removeGroups();

        newSequence.add(span);
      }
    }
    return box(newSequence);
  }
}
