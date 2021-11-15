package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsForward.email;
import static com.computablefacts.nona.functions.patternoperators.PatternsForward.ipv4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.computablefacts.asterix.Span;
import com.computablefacts.asterix.SpanSequence;
import com.computablefacts.nona.dictionaries.Tld;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class Email extends MatchPattern {

  private static final Set<String> TLD_DICTIONARY = Tld.load();
  private static final Map<Integer, String> GROUPS = new HashMap<>();

  static {
    GROUPS.put(2, "USERNAME");
    GROUPS.put(3, "DOMAIN");
  }

  public Email() {
    super("MATCH_EMAIL", email(), GROUPS);
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "EMAIL takes exactly one parameter : %s",
        parameters);

    BoxedType<?> boxedType = super.evaluate(parameters);
    SpanSequence sequence = (SpanSequence) boxedType.value();
    SpanSequence newSequence = new SpanSequence();

    for (Span span : sequence) {

      String username = span.getFeature("USERNAME");
      String domain = span.getFeature("DOMAIN");

      span.addTag("EMAIL");
      span.removeGroups();

      if (domain.matches("^\\[?" + ipv4() + "\\]?$")) {
        span.setFeature("USERNAME", username.toLowerCase());
        span.setFeature("DOMAIN", domain.toLowerCase());

        newSequence.add(span);
      } else {

        String tld = domain.substring(domain.lastIndexOf('.') + 1);

        if (TLD_DICTIONARY.contains(tld.toUpperCase())) {
          span.setFeature("USERNAME", username.toLowerCase());
          span.setFeature("DOMAIN", domain.toLowerCase());
          span.setFeature("TLD", tld.toUpperCase());

          newSequence.add(span);
        }
      }
    }
    return box(newSequence);
  }
}
