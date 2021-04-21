package com.computablefacts.nona.functions.patternoperators;

import java.util.ArrayList;
import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.computablefacts.nona.functions.stringoperators.MatchDictionary;
import com.computablefacts.nona.functions.stringoperators.MatchRegex;
import com.computablefacts.nona.types.BoxedType;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Var;

@Deprecated
@CheckReturnValue
public class MatchAll extends Function {

  private static final List<MatchRegex> FORWARD_AND_BACKWARD_EXTRACTORS = Lists.newArrayList(
      new Base64(), new Bic(), new CompanyWithElf(), new Date(), new DateTime(), new Email(),
      new FinancialNumber(), new IpV4(), new IpV6(), new MonetaryAmount(), new Number(),
      new Onion(), new Percent(), new Time(), new UnixPath(), new Url(), new WinPath());
  private static final List<MatchRegex> COMPACT_EXTRACTORS = Lists.newArrayList(new Iban());

  private final List<MatchRegex> forwardAndBackwardExtractors_;
  private final List<MatchRegex> compactExtractors_;

  public MatchAll() {
    this(FORWARD_AND_BACKWARD_EXTRACTORS, COMPACT_EXTRACTORS);
  }

  public MatchAll(List<MatchRegex> forwardAndBackwardExtractors,
      List<MatchRegex> compactExtractors) {

    super(eCategory.PATTERN_OPERATORS, "MATCH_ALL", "Deprecated.");

    forwardAndBackwardExtractors_ = Preconditions.checkNotNull(forwardAndBackwardExtractors,
        "forwardAndBackwardExtractors should not be null");
    compactExtractors_ =
        Preconditions.checkNotNull(compactExtractors, "compactExtractors should not be null");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() >= 1,
        "EXTRACT_ALL takes at least one parameter : %s", parameters);

    SpanSequence newSequence = new SpanSequence();

    // Extract from dictionary
    MatchDictionary dico = new MatchDictionary();

    for (int i = 1; i < parameters.size(); i++) {

      List<BoxedType<?>> newParameters = new ArrayList<>();
      newParameters.add(parameters.get(i));
      newParameters.add(parameters.get(0));

      newSequence.add((SpanSequence) dico.evaluate(newParameters).value());
    }

    // Extract forward and backward patterns
    for (MatchRegex extractor : forwardAndBackwardExtractors_) {
      newSequence
          .add((SpanSequence) extractor.evaluate(Lists.newArrayList(parameters.get(0))).value());
    }

    // Remove overlapping matches
    newSequence.sort((s1, s2) -> {

      @Var
      int cmp = Ints.compare(s1.begin(), s2.begin());

      if (cmp != 0) {
        return cmp;
      }
      return Ints.compare(s1.length(), s2.length());
    });

    for (int i = 1; i < newSequence.size(); i++) {

      Span cur = newSequence.span(i);
      Span prev = newSequence.span(i - 1);

      cur.removeGroups();
      prev.removeGroups();

      if (prev.overlapsAll(cur) || prev.overlapsLeftOf(cur) || cur.overlapsAll(prev)) {
        if (prev.length() < cur.length()) {

          // Longer matches prevail over shorter matches
          newSequence.remove(i - 1);
        } else {

          if (prev.overlapsAll(cur) && cur.overlapsAll(prev)) {

            // If the prev and cur features are identical, copy all the groups/features from the
            // deleted one to the new one
            cur.features().forEach(prev::setFeature);
            cur.tags().forEach(prev::addTag);
            cur.groups().forEach(prev::setGroup);
          }

          // Left-most matches prevail over right-most
          newSequence.remove(i);
        }
        i--;
      }
    }

    // Extract compact patterns
    for (MatchRegex extractor : compactExtractors_) {
      newSequence
          .add((SpanSequence) extractor.evaluate(Lists.newArrayList(parameters.get(0))).value());
    }
    return BoxedType.create(newSequence);
  }
}
