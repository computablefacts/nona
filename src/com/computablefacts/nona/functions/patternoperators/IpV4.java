package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsForward.iplocal;
import static com.computablefacts.nona.functions.patternoperators.PatternsForward.ipv4;
import static com.computablefacts.nona.functions.patternoperators.PatternsForward.leftBoundary;
import static com.computablefacts.nona.functions.patternoperators.PatternsForward.rightBoundary;

import java.util.ArrayList;
import java.util.List;

import com.computablefacts.nona.functions.stringoperators.RegexExtract;
import com.computablefacts.nona.types.BoxedType;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;
import com.google.common.base.Preconditions;
import com.google.re2j.Pattern;

public class IpV4 extends RegexExtract {

  private static Pattern IP_LOCAL = Pattern.compile(iplocal(), Pattern.CASE_INSENSITIVE);

  public IpV4() {
    super("IPV4");
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "IPV4 takes exactly one parameter : %s",
        parameters);
    Preconditions.checkArgument(parameters.get(0).isString(), "%s should be a string",
        parameters.get(0));

    List<BoxedType> newParameters = new ArrayList<>();
    newParameters.add(parameters.get(0));
    newParameters.add(BoxedType.create(leftBoundary() + ipv4() + rightBoundary()));

    BoxedType boxedType = super.evaluate(newParameters);
    SpanSequence sequence = (SpanSequence) boxedType.value();
    SpanSequence newSequence = new SpanSequence();

    for (Span span : sequence.sequence()) {

      span.setFeature("IS_LOCAL", Boolean.toString(IP_LOCAL.matches(span.text())));
      span.removeAllGroups();

      newSequence.add(span);
    }
    return BoxedType.create(newSequence);
  }
}
