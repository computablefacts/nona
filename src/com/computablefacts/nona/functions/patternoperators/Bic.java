package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsForward.bic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.computablefacts.nona.dictionaries.BicFr;
import com.computablefacts.nona.dictionaries.Country;
import com.computablefacts.nona.dictionaries.Lei;
import com.computablefacts.nona.types.BoxedType;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;
import com.google.common.base.Preconditions;

public class Bic extends MatchPattern {

  private static final Map<String, Set<Lei>> BIC8_DICTIONARY = Lei.load().stream().collect(
      Collectors.groupingBy(Lei::bic8, Collectors.mapping(lei -> lei, Collectors.toSet())));
  private static final Map<String, BicFr> FRENCH_BIC_DICTIONARY = BicFr.load();
  private static final Map<String, Country> COUNTRY_CODE_DICTIONARY = Country.load();
  private static final Map<Integer, String> GROUPS = new HashMap<>();

  static {
    GROUPS.put(2, "INSTITUTION_CODE");
    GROUPS.put(3, "COUNTRY_CODE");
    GROUPS.put(4, "LOCATION_CODE");
    GROUPS.put(5, "BRANCH_CODE");
  }

  public Bic() {
    super("BIC", bic(), GROUPS);
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "BIC takes exactly one parameter : %s",
        parameters);
    Preconditions.checkArgument(parameters.get(0).isString(), "%s should be a string",
        parameters.get(0));

    BoxedType boxedType = super.evaluate(parameters);
    SpanSequence sequence = (SpanSequence) boxedType.value();
    SpanSequence newSequence = new SpanSequence();

    for (Span span : sequence.sequence()) {

      Span newSpan = resize(span);
      String countryCode = newSpan.getFeature("COUNTRY_CODE");
      String locationCode = newSpan.getFeature("LOCATION_CODE");

      // TODO : backport code related to institution validation

      if (COUNTRY_CODE_DICTIONARY.containsKey(countryCode)) {

        newSpan.addTag("BIC");
        newSpan.setFeature("COUNTRY_NAME", COUNTRY_CODE_DICTIONARY.get(countryCode).name());

        // If the last character of the location code is 0 it is a Test BIC
        newSpan.setFeature("IS_TEST_BIC",
            Boolean.toString(locationCode.charAt(locationCode.length() - 1) == '0'));

        // Bic to legal name with additional precision if it is a French bank
        String bic = newSpan.text().replaceAll("[^A-Za-z0-9]", "");

        if (FRENCH_BIC_DICTIONARY.containsKey(bic)) {

          newSpan.setFeature("BANK_NAME", FRENCH_BIC_DICTIONARY.get(bic).name());
          newSpan.setFeature("CITY", FRENCH_BIC_DICTIONARY.get(bic).city());

          newSpan.removeGroups();
          newSequence.add(newSpan);
        } else {

          String bic8 = bic.substring(0, Math.min(bic.length(), 8));

          if (BIC8_DICTIONARY.containsKey(bic8)) {
            for (Lei lei : BIC8_DICTIONARY.get(bic8)) {
              if (lei.countryCode().equals(countryCode)) {

                newSpan.setFeature("BANK_NAME", lei.legalName());

                newSpan.removeGroups();
                newSequence.add(newSpan);
              }
            }
          }
        }
      }
    }
    return BoxedType.create(newSequence);
  }
}
