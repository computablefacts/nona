package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsForward.leftBoundary;
import static com.computablefacts.nona.functions.patternoperators.PatternsForward.rightBoundary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.computablefacts.nona.functions.stringoperators.RegexExtract;
import com.computablefacts.nona.types.BoxedType;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/**
 * WARNING : the first group of the pattern must be the extracted entity without the left/right
 * boundaries.
 */
public class MatchPattern extends RegexExtract {

  private final String name_;
  private final String pattern_;
  private final Map<Integer, String> groupsToFeature_;

  public MatchPattern(String name, String pattern) {
    this(name, pattern, new HashMap<>());
  }

  public MatchPattern(String name, String pattern, Map<Integer, String> patternGroupToFeature) {
    super(name);
    name_ = name;
    pattern_ = Preconditions.checkNotNull(pattern, "pattern should not be null");
    groupsToFeature_ = Preconditions.checkNotNull(patternGroupToFeature,
        "patternGroupToFeature should not be null");
  }

  /**
   * Take the extracted span and resize it to match group one of the pattern.
   *
   * @param span span to resize.
   * @return resized span.
   */
  public static Span resize(Span span) {

    String match = Strings.nullToEmpty(span.getGroup(1)).trim();

    if (!match.isEmpty()) {

      int index = span.text().indexOf(match);

      if (index >= 0) {

        // Resize span : remove left & right boundaries
        Span newSpan =
            new Span(span.rawText(), span.begin() + index, span.begin() + index + match.length());
        span.features().forEach(newSpan::setFeature);
        span.tags().forEach(newSpan::addTag);

        return newSpan;
      }
    }
    return span;
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "%s takes exactly one parameter : %s",
        name_, parameters);

    List<BoxedType> newParameters = new ArrayList<>();
    newParameters.add(parameters.get(0));
    newParameters.add(BoxedType.create(leftBoundary() + pattern_ + rightBoundary()));

    BoxedType boxedType = super.evaluate(newParameters);
    SpanSequence sequence = (SpanSequence) boxedType.value();
    SpanSequence newSequence = new SpanSequence();

    for (Span span : sequence) {

      Span newSpan = resize(span);

      for (Integer group : groupsToFeature_.keySet()) {
        newSpan.setFeature(groupsToFeature_.get(group),
            Strings.nullToEmpty(span.getGroup(group)).trim());
      }

      newSpan.addTag(name_);
      newSequence.add(newSpan);
    }
    return BoxedType.create(newSequence);
  }
}
