package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsBackward.companyName;
import static com.computablefacts.nona.functions.patternoperators.PatternsBackward.reverse;
import static com.computablefacts.nona.functions.patternoperators.PatternsForward.leftBoundary;
import static com.computablefacts.nona.functions.patternoperators.PatternsForward.rightBoundary;

import java.util.ArrayList;
import java.util.List;

import com.computablefacts.nona.functions.stringoperators.RegexExtract;
import com.computablefacts.nona.types.BoxedType;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;
import com.google.common.base.Preconditions;

public class ExtractCompanyName extends RegexExtract {

  public ExtractCompanyName() {
    super("EXTRACT_COMPANY_NAME");
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 1,
        "EXTRACT_COMPANY_NAME takes exactly one parameter : %s", parameters);
    Preconditions.checkArgument(parameters.get(0).isString(), "%s should be a string",
        parameters.get(0));

    List<BoxedType> newParameters = new ArrayList<>();
    newParameters.add(BoxedType.create(reverse(parameters.get(0).asString())));
    newParameters.add(BoxedType.create(leftBoundary() + companyName() + rightBoundary()));

    BoxedType boxedType = super.evaluate(newParameters);
    SpanSequence sequence = (SpanSequence) boxedType.value();
    SpanSequence newSequence = new SpanSequence();

    for (Span span : sequence.sequence()) {

      String text = parameters.get(0).asString();
      Span newSpan = new Span(text, text.length() - span.end(), text.length() - span.begin());

      newSpan.setFeature("LEGAL_FORM",
          new StringBuilder(span.getGroup(1)).reverse().toString().trim());
      newSpan.setFeature("COMPANY_NAME",
          new StringBuilder(span.getGroup(2)).reverse().toString().trim());

      newSequence.add(newSpan);
    }
    return BoxedType.create(newSequence);
  }
}
