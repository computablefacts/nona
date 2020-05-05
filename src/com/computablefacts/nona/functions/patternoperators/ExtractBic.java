package com.computablefacts.nona.functions.patternoperators;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.computablefacts.nona.dictionaries.CountryCode;
import com.computablefacts.nona.dictionaries.FrenchBic;
import com.computablefacts.nona.functions.stringoperators.RegexExtract;
import com.computablefacts.nona.types.BoxedType;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.Var;

public class ExtractBic extends RegexExtract {

  private static final Map<String, FrenchBic> FRENCH_BIC_DICTIONARY = FrenchBic.load();
  private static final Map<String, CountryCode> COUNTRY_CODE_DICTIONARY = CountryCode.load();

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

    // Keep only alphanumeric characters
    newParameters.add(
        BoxedType.create(parameters.get(0).asString().toUpperCase().replaceAll("[^A-Z0-9]", "")));

    // Match and capture (1) the institution code, (2) the country code, (3) the location code, and
    // (4) the branch code (optional)
    newParameters.add(BoxedType.create("([A-Z]{4})([A-Z]{2})([A-Z0-9]{2})([A-Z0-9]{3})?"));

    BoxedType boxedType = super.evaluate(newParameters);
    SpanSequence sequence = (SpanSequence) boxedType.value();
    SpanSequence newSequence = new SpanSequence();

    for (@Var
    Span span : sequence.sequence()) {

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

        // Additional feature if it is a French BIC
        if (!FRENCH_BIC_DICTIONARY.containsKey(span.text())) {
          span.setFeature("IS_FRENCH_BANK", "false");
        } else {

          FrenchBic bic = FRENCH_BIC_DICTIONARY.get(span.text());

          span.setFeature("IS_FRENCH_BANK", "true");
          span.setFeature("BANK_NAME", bic.name());
        }

        newSequence.add(span);
      }
    }
    return BoxedType.create(newSequence);
  }
}
