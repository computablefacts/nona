package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsForward.bic;
import static com.computablefacts.nona.functions.patternoperators.PatternsForward.leftBoundary;
import static com.computablefacts.nona.functions.patternoperators.PatternsForward.rightBoundary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.computablefacts.nona.dictionaries.BicFr;
import com.computablefacts.nona.dictionaries.Country;
import com.computablefacts.nona.dictionaries.Lei;
import com.computablefacts.nona.functions.stringoperators.RegexExtract;
import com.computablefacts.nona.types.BoxedType;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;
import com.google.common.base.Preconditions;

public class ExtractBic extends RegexExtract {

  private static final Map<String, Set<Lei>> BIC8_DICTIONARY = Lei.load().stream().collect(
      Collectors.groupingBy(Lei::bic8, Collectors.mapping(lei -> lei, Collectors.toSet())));
  private static final Map<String, BicFr> FRENCH_BIC_DICTIONARY = BicFr.load();
  private static final Map<String, Country> COUNTRY_CODE_DICTIONARY = Country.load();

  public ExtractBic() {
    super("EXTRACT_BIC");
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 1,
        "EXTRACT_BIC takes exactly one parameter : %s", parameters);
    Preconditions.checkArgument(parameters.get(0).isString(), "%s should be a string",
        parameters.get(0));

    List<BoxedType> newParameters = new ArrayList<>();
    newParameters.add(parameters.get(0));
    newParameters.add(BoxedType.create(leftBoundary() + bic() + rightBoundary()));

    BoxedType boxedType = super.evaluate(newParameters);
    SpanSequence sequence = (SpanSequence) boxedType.value();
    SpanSequence newSequence = new SpanSequence();

    for (Span span : sequence.sequence()) {

      String institutionCode = span.getFeature("GROUP_1");
      String countryCode = span.getFeature("GROUP_2");
      String locationCode = span.getFeature("GROUP_3");
      String branchCode = span.getFeature("GROUP_4");

      // TODO : backport code related to institution validation

      if (COUNTRY_CODE_DICTIONARY.containsKey(countryCode)) {

        span.removeFeature("GROUP_COUNT");
        span.removeFeature("GROUP_1");
        span.removeFeature("GROUP_2");
        span.removeFeature("GROUP_3");
        span.removeFeature("GROUP_4");

        span.setFeature("INSTITUTION_CODE", institutionCode);
        span.setFeature("COUNTRY_CODE", countryCode);
        span.setFeature("COUNTRY_NAME", COUNTRY_CODE_DICTIONARY.get(countryCode).name());
        span.setFeature("LOCATION_CODE", locationCode);
        span.setFeature("BRANCH_CODE", branchCode);

        // If the last character of the location code is 0 it is a Test BIC
        span.setFeature("IS_TEST_BIC",
            Boolean.toString(locationCode.charAt(locationCode.length() - 1) == '0'));

        // Bic to legal name with additional precision if it is a French bank
        String bic = span.text().replaceAll("[^A-Za-z0-9]", "");

        if (FRENCH_BIC_DICTIONARY.containsKey(bic)) {
          span.setFeature("BANK_NAME", FRENCH_BIC_DICTIONARY.get(bic).name());
          span.setFeature("CITY", FRENCH_BIC_DICTIONARY.get(bic).city());
        } else {

          String bic8 = bic.substring(0, Math.min(bic.length(), 8));

          if (BIC8_DICTIONARY.containsKey(bic8)) {
            for (Lei lei : BIC8_DICTIONARY.get(bic8)) {
              if (lei.countryCode().equals(countryCode)) {
                span.setFeature("BANK_NAME", lei.legalName());
              }
            }
          }
        }

        newSequence.add(span);
      }
    }
    return BoxedType.create(newSequence);
  }
}
