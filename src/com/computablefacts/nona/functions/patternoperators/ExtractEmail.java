package com.computablefacts.nona.functions.patternoperators;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.computablefacts.nona.dictionaries.Tld;
import com.computablefacts.nona.functions.stringoperators.RegexExtract;
import com.computablefacts.nona.types.BoxedType;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;
import com.google.common.base.Preconditions;

public class ExtractEmail extends RegexExtract {

  private static final Set<String> TLD_DICTIONARY = Tld.load();

  public ExtractEmail() {
    super("EXTRACT_EMAIL");
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 1,
        "EXTRACT_EMAIL takes exactly one parameter : %s", parameters);
    Preconditions.checkArgument(parameters.get(0).isString(), "%s should be a string",
        parameters.get(0));

    List<BoxedType> newParameters = new ArrayList<>();
    newParameters.add(parameters.get(0));

    // Match and capture (1) the username, and (2) the ip/domain
    newParameters.add(BoxedType.create(
        "(?:^|\\p{Zs}|\\b)(\"?\\w(?:[-.+]?\\w)*\"?)[@＠]((?:\\[?\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\]?)|(?:[-\\w]+(?:\\.[-\\w]+)*\\.[A-Za-z]{2,4}))(?:$|\\p{Zs}|\\b)"));

    BoxedType boxedType = super.evaluate(newParameters);
    SpanSequence sequence = (SpanSequence) boxedType.value();
    SpanSequence newSequence = new SpanSequence();

    for (Span span : sequence.sequence()) {

      String username = span.getFeature("GROUP_1");
      String domain = span.getFeature("GROUP_2");

      span.removeFeature("GROUP_COUNT");
      span.removeFeature("GROUP_1");
      span.removeFeature("GROUP_2");

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
