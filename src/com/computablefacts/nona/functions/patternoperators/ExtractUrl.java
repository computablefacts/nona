package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsForward.leftBoundary;
import static com.computablefacts.nona.functions.patternoperators.PatternsForward.rightBoundary;
import static com.computablefacts.nona.functions.patternoperators.PatternsForward.url;

import java.util.ArrayList;
import java.util.List;

import com.computablefacts.nona.functions.stringoperators.RegexExtract;
import com.computablefacts.nona.types.BoxedType;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;
import com.google.common.base.Preconditions;

public class ExtractUrl extends RegexExtract {

  public ExtractUrl() {
    super("EXTRACT_URL");
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 1,
        "EXTRACT_URL takes exactly one parameter : %s", parameters);
    Preconditions.checkArgument(parameters.get(0).isString(), "%s should be a string",
        parameters.get(0));

    List<BoxedType> newParameters = new ArrayList<>();
    newParameters.add(parameters.get(0));
    newParameters
        .add(BoxedType.create(leftBoundary() + "(?i)" + url() + "(?-i)" + rightBoundary()));

    BoxedType boxedType = super.evaluate(newParameters);
    SpanSequence sequence = (SpanSequence) boxedType.value();
    SpanSequence newSequence = new SpanSequence();

    for (Span span : sequence.sequence()) {

      span.setFeature("PROTOCOL", span.getFeature("GROUP_1"));
      span.setFeature("USERNAME", span.getFeature("GROUP_2"));
      span.setFeature("PASSWORD", span.getFeature("GROUP_3"));
      span.setFeature("HOSTNAME", span.getFeature("GROUP_4"));
      span.setFeature("PORT", span.getFeature("GROUP_5"));
      span.setFeature("PATH", span.getFeature("GROUP_6"));
      span.setFeature("QUERY_STRING", span.getFeature("GROUP_7"));

      span.removeFeature("GROUP_COUNT");
      span.removeFeature("GROUP_1");
      span.removeFeature("GROUP_2");
      span.removeFeature("GROUP_3");
      span.removeFeature("GROUP_4");
      span.removeFeature("GROUP_5");
      span.removeFeature("GROUP_6");
      span.removeFeature("GROUP_7");

      newSequence.add(span);
    }
    return BoxedType.create(newSequence);
  }
}
