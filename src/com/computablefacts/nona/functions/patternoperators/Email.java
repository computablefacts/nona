package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsForward.email;
import static com.computablefacts.nona.functions.patternoperators.PatternsForward.leftBoundary;
import static com.computablefacts.nona.functions.patternoperators.PatternsForward.rightBoundary;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.computablefacts.nona.dictionaries.Tld;
import com.computablefacts.nona.functions.stringoperators.RegexExtract;
import com.computablefacts.nona.types.BoxedType;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;
import com.google.common.base.Preconditions;

public class Email extends RegexExtract {

  private static final Set<String> TLD_DICTIONARY = Tld.load();

  public Email() {
    super("EMAIL");
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "EMAIL takes exactly one parameter : %s",
        parameters);
    Preconditions.checkArgument(parameters.get(0).isString(), "%s should be a string",
        parameters.get(0));

    List<BoxedType> newParameters = new ArrayList<>();
    newParameters.add(parameters.get(0));
    newParameters.add(BoxedType.create(leftBoundary() + email() + rightBoundary()));

    BoxedType boxedType = super.evaluate(newParameters);
    SpanSequence sequence = (SpanSequence) boxedType.value();
    SpanSequence newSequence = new SpanSequence();

    for (Span span : sequence.sequence()) {

      String username = span.getGroup(1);
      String domain = span.getGroup(2);

      span.removeAllGroups();

      // TODO : use IPV4 regex
      if (domain.matches("^\\[?\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\]?$")) {

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
    return BoxedType.create(newSequence);
  }
}
