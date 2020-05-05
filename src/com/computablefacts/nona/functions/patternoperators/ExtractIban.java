package com.computablefacts.nona.functions.patternoperators;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.computablefacts.nona.dictionaries.Iban;
import com.computablefacts.nona.functions.stringoperators.RegexExtract;
import com.computablefacts.nona.types.BoxedType;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.Var;

public class ExtractIban extends RegexExtract {

  private static final Map<String, Iban> IBAN_DICTIONARY = Iban.load();

  public ExtractIban() {
    super("EXTRACT_IBAN");
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 1,
        "EXTRACT_IBAN takes exactly one parameter : %s", parameters);
    Preconditions.checkArgument(parameters.get(0).isString(), "%s should be a string",
        parameters.get(0));

    List<BoxedType> newParameters = new ArrayList<>();

    // Keep only alphanumeric characters
    newParameters.add(
        BoxedType.create(parameters.get(0).asString().toUpperCase().replaceAll("[^A-Z0-9]", "")));

    // Match and capture (1) the country code, (2) the check digits, and (3) the rest
    newParameters.add(BoxedType.create("([A-Z]{2})(\\d{2})([A-Z\\d]+)"));

    BoxedType boxedType = super.evaluate(newParameters);
    SpanSequence sequence = (SpanSequence) boxedType.value();
    SpanSequence newSequence = new SpanSequence();

    for (@Var
    Span span : sequence.sequence()) {

      @Var
      String iban = span.text();
      String countryCode = span.getFeature("GROUP_1");

      if (!IBAN_DICTIONARY.containsKey(countryCode)) {
        continue;
      }

      int length = IBAN_DICTIONARY.get(countryCode).ibanLength();

      // Match is too short? ignore!
      if (iban.length() < length) {
        continue;
      }

      // Match is too long? resize!
      if (iban.length() > length) {
        span = new Span(newParameters.get(0).asString(), span.begin(), span.begin() + length);
        iban = span.text();
      }

      // Rearrange country code and check digits
      String digits = iban.substring(4) + iban.substring(0, 4);

      // Convert chars to ints
      StringBuilder builder = new StringBuilder(digits.length());

      for (int i = 0; i < digits.length(); i++) {
        builder.append(Character.digit(digits.charAt(i), 36));
      }

      // Compute checksum
      int checksum = new BigInteger(builder.toString()).mod(BigInteger.valueOf(97)).intValue();

      if (checksum == 1) {

        span.removeFeature("GROUP_COUNT");
        span.removeFeature("GROUP_1");
        span.removeFeature("GROUP_2");
        span.removeFeature("GROUP_3");

        span.setFeature("BBAN", iban.substring(4));
        span.setFeature("COUNTRY_CODE", countryCode);
        span.setFeature("COUNTRY_NAME", IBAN_DICTIONARY.get(countryCode).countryName());
        span.setFeature("IS_SEPA_MEMBER",
            Boolean.toString(IBAN_DICTIONARY.get(countryCode).isSepaMember()));

        newSequence.add(span);
      }
    }
    return BoxedType.create(newSequence);
  }
}
