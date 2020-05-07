package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsForward.iplocal;
import static com.computablefacts.nona.functions.patternoperators.PatternsForward.ipv4;

import java.util.List;

import com.computablefacts.nona.types.BoxedType;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;
import com.google.common.base.Preconditions;
import com.google.re2j.Pattern;

public class IpV4 extends MatchPattern {

  private static Pattern IP_LOCAL = Pattern.compile(iplocal(), Pattern.CASE_INSENSITIVE);

  public IpV4() {
    super("IPV4", ipv4());
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "IPV4 takes exactly one parameter : %s",
        parameters);
    Preconditions.checkArgument(parameters.get(0).isString(), "%s should be a string",
        parameters.get(0));

    BoxedType boxedType = super.evaluate(parameters);
    SpanSequence sequence = (SpanSequence) boxedType.value();

    for (Span span : sequence.sequence()) {
      span.setFeature("IS_LOCAL", Boolean.toString(IP_LOCAL.matches(span.text())));
      span.removeAllGroups();
    }
    return BoxedType.create(sequence);
  }
}
