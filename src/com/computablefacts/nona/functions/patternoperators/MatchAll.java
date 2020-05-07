package com.computablefacts.nona.functions.patternoperators;

import java.util.ArrayList;
import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.functions.stringoperators.MatchDictionary;
import com.computablefacts.nona.functions.stringoperators.RegexExtract;
import com.computablefacts.nona.types.BoxedType;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.Var;

public class MatchAll extends Function {

  private static final List<RegexExtract> FORWARD_AND_BACKWARD_EXTRACTORS =
      Lists.newArrayList(new Bic(), new CompanyWithElf(), new Date(), new DateTime(), new Email(),
          new FinancialNumber(), new IpV4(), new IpV6(), new MonetaryAmount(), new Number(),
          new Onion(), new Percent(), new Time(), new UnixPath(), new Url(), new WinPath());
  private static final List<RegexExtract> COMPACT_EXTRACTORS = Lists.newArrayList(new Iban());

  public MatchAll() {
    super("EXTRACT_ALL", true);
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() >= 1,
        "EXTRACT_ALL takes at least one parameter : %s", parameters);

    // WARNING : do not check the parameters types. Always assume it is a string.
    // Preconditions.checkArgument(parameters.get(0).isString(), "%s should be a string",
    // parameters.get(0));

    SpanSequence newSequence = new SpanSequence();

    // Extract from dictionary
    MatchDictionary dico = new MatchDictionary();

    for (int i = 1; i < parameters.size(); i++) {

      List<BoxedType> newParameters = new ArrayList<>();
      newParameters.add(parameters.get(i));
      newParameters.add(parameters.get(0));

      newSequence.add((SpanSequence) dico.evaluate(newParameters).value());
    }

    // Extract forward and backward patterns
    for (RegexExtract extractor : FORWARD_AND_BACKWARD_EXTRACTORS) {
      newSequence
          .add((SpanSequence) extractor.evaluate(Lists.newArrayList(parameters.get(0))).value());
    }

    // Remove overlapping matches
    List<Span> spans = newSequence.sequence();
    spans.sort((s1, s2) -> {

      @Var
      int cmp = Ints.compare(s1.begin(), s2.begin());

      if (cmp != 0) {
        return cmp;
      }
      return Ints.compare(s1.length(), s2.length());
    });

    for (int i = 1; i < spans.size(); i++) {

      Span cur = spans.get(i);
      Span prev = spans.get(i - 1);

      cur.removeGroups();
      prev.removeGroups();

      if (prev.overlapsAll(cur) || prev.overlapsLeftOf(cur) || cur.overlapsAll(prev)) {
        if (prev.length() < cur.length()) {

          // Longer matches prevail over shorter matches
          spans.remove(i - 1);
        } else {

          if (prev.overlapsAll(cur) && cur.overlapsAll(prev)) {

            // If the prev and cur features are identical, copy all the groups/features from the
            // deleted one to the new one
            cur.features().forEach(prev::setFeature);
            cur.tags().forEach(prev::addTag);
            cur.groups().forEach(prev::setGroup);
          }

          // Left-most matches prevail over right-most
          spans.remove(i);
        }
        i--;
      }
    }

    // Extract compact patterns
    for (RegexExtract extractor : COMPACT_EXTRACTORS) {
      newSequence
          .add((SpanSequence) extractor.evaluate(Lists.newArrayList(parameters.get(0))).value());
    }
    return BoxedType.create(newSequence);
  }
}
