package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsForward.leftBoundary;
import static com.computablefacts.nona.functions.patternoperators.PatternsForward.onion;
import static com.computablefacts.nona.functions.patternoperators.PatternsForward.rightBoundary;

import java.util.ArrayList;
import java.util.List;

import com.computablefacts.nona.functions.stringoperators.RegexExtract;
import com.computablefacts.nona.types.BoxedType;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;
import com.google.common.base.Preconditions;

public class Onion extends RegexExtract {

  public Onion() {
    super("ONION");
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "ONION takes exactly one parameter : %s",
        parameters);
    Preconditions.checkArgument(parameters.get(0).isString(), "%s should be a string",
        parameters.get(0));

    List<BoxedType> newParameters = new ArrayList<>();
    newParameters.add(parameters.get(0));
    newParameters.add(BoxedType.create(leftBoundary() + onion() + rightBoundary()));

    BoxedType boxedType = super.evaluate(newParameters);
    SpanSequence sequence = (SpanSequence) boxedType.value();
    SpanSequence newSequence = new SpanSequence();

    for (Span span : sequence.sequence()) {

      span.setFeature("PROTOCOL", span.getGroup(1));
      span.setFeature("HOSTNAME", span.getGroup(2));
      span.setFeature("PORT", span.getGroup(3));
      span.setFeature("PATH", span.getGroup(4));
      span.removeAllGroups();

      newSequence.add(span);
    }
    return BoxedType.create(newSequence);
  }
}
