package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsCompact.compact;
import static com.computablefacts.nona.functions.patternoperators.PatternsCompact.iban;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.computablefacts.nona.functions.stringoperators.RegexExtract;
import com.computablefacts.nona.types.BoxedType;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Var;

@CheckReturnValue
public class Iban extends RegexExtract {

  private static final Map<String, com.computablefacts.nona.dictionaries.Iban> IBAN_DICTIONARY =
      com.computablefacts.nona.dictionaries.Iban.load();

  public Iban() {
    super("IBAN");
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "IBAN takes exactly one parameter : %s",
        parameters);

    List<BoxedType> newParameters = new ArrayList<>();
    newParameters.add(BoxedType.create(compact(parameters.get(0).asString())));
    newParameters.add(BoxedType.create(iban()));

    BoxedType boxedType = super.evaluate(newParameters);
    SpanSequence sequence = (SpanSequence) boxedType.value();
    SpanSequence newSequence = new SpanSequence();

    for (@Var
    Span span : sequence) {

      @Var
      String iban = span.text();
      String countryCode = span.getGroup(2);

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

        span.addTag("IBAN");
        span.setFeature("BBAN", iban.substring(4));
        span.setFeature("COUNTRY_CODE", countryCode);
        span.setFeature("COUNTRY_NAME", IBAN_DICTIONARY.get(countryCode).countryName());
        span.setFeature("IS_SEPA_MEMBER",
            Boolean.toString(IBAN_DICTIONARY.get(countryCode).isSepaMember()));

        span.removeGroups();
        newSequence.add(span);
      }
    }
    return BoxedType.create(newSequence);
  }
}
