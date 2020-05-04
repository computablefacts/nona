package com.computablefacts.nona.functions.patternoperators;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.computablefacts.nona.functions.stringoperators.RegexExtract;
import com.computablefacts.nona.types.BoxedType;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;

public class ExtractIban extends RegexExtract {

  /*
   * Extracted from https://bank-code.net/iban/country-list
   *
   * Includes a fix for Sao Tome and Principe whose IBAN was wrong.
   */
  static String[] DICTIONARY = {"Albania,AL,FALSE,28,AL47 2121 1009 0000 0002 3569 8741",
      "Andorra,AD,FALSE,24,AD12 0001 2030 2003 5910 0100",
      "Austria,AT,TRUE,20,AT61 1904 3002 3457 3201",
      "Azerbaijan,AZ,FALSE,28,AZ21 NABZ 0000 0000 1370 1000 1944",
      "Bahrain,BH,FALSE,22,BH67 BMAG 0000 1299 1234 56",
      "Belarus,BY,FALSE,28,BY13 NBRB 3600 9000 0000 2Z00 AB00",
      "Belgium,BE,TRUE,16,BE68 5390 0754 7034",
      "Bosnia and Herzegovina,BA,FALSE,20,BA39 1290 0794 0102 8494",
      "Brazil,BR,FALSE,29,BR18 0036 0305 0000 1000 9795 493C 1",
      "Bulgaria,BG,TRUE,22,BG80 BNBG 9661 1020 3456 78",
      "Costa Rica,CR,FALSE,22,CR05 0152 0200 1026 2840 66",
      "Croatia,HR,TRUE,21,HR12 1001 0051 8630 0016 0",
      "Cyprus,CY,TRUE,28,CY17 0020 0128 0000 0012 0052 7600",
      "Czechia,CZ,TRUE,24,CZ65 0800 0000 1920 0014 5399",
      "Denmark,DK,TRUE,18,DK50 0040 0440 1162 43",
      "Dominican Republic,DO,FALSE,28,DO28 BAGR 0000 0001 2124 5361 1324",
      "El Salvador,SV,FALSE,28,SV 62 CENR 00000000000000700025",
      "Estonia,EE,TRUE,20,EE38 2200 2210 2014 5685",
      "Faroe Islands,FO,FALSE,18,FO62 6460 0001 6316 34",
      "Finland,FI,TRUE,18,FI21 1234 5600 0007 85",
      "France,FR,TRUE,27,FR14 2004 1010 0505 0001 3M02 606",
      "Georgia,GE,FALSE,22,GE29 NB00 0000 0101 9049 17",
      "Germany,DE,TRUE,22,DE89 3704 0044 0532 0130 00",
      "Gibraltar,GI,TRUE,23,GI75 NWBK 0000 0000 7099 453",
      "Greece,GR,TRUE,27,GR16 0110 1250 0000 0001 2300 695",
      "Greenland,GL,FALSE,18,GL89 6471 0001 0002 06",
      "Guatemala,GT,FALSE,28,GT82 TRAJ 0102 0000 0012 1002 9690",
      "Hungary,HU,TRUE,28,HU42 1177 3016 1111 1018 0000 0000",
      "Iceland,IS,TRUE,26,IS14 0159 2600 7654 5510 7303 39",
      "Iraq,IQ,FALSE,23,IQ98 NBIQ 8501 2345 6789 012",
      "Ireland,IE,TRUE,22,IE29 AIBK 9311 5212 3456 78",
      "Israel,IL,FALSE,23,IL62 0108 0000 0009 9999 999",
      "Italy,IT,TRUE,27,IT60 X054 2811 1010 0000 0123 456",
      "Jordan,JO,FALSE,30,JO94 CBJO 0010 0000 0000 0131 000302",
      "Kazakhstan,KZ,FALSE,20,KZ86 125K ZT50 0410 0100",
      "Kosovo,XK,FALSE,20,XK05 1212 0123 4567 8906",
      "Kuwait,KW,FALSE,30,KW81 CBKU 0000 0000 0000 1234 560101",
      "Latvia,LV,TRUE,21,LV80 BANK 0000 4351 9500 1",
      "Lebanon,LB,FALSE,28,LB62 0999 0000 0001 0019 0122 9114",
      "Liechtenstein,LI,TRUE,21,LI21 0881 0000 2324 013A A",
      "Lithuania,LT,TRUE,20,LT12 1000 0111 0100 1000",
      "Luxembourg,LU,TRUE,20,LU28 0019 4006 4475 0000",
      "Macedonia,MK,FALSE,19,MK07 2501 2000 0058 984",
      "Malta,MT,TRUE,31,MT84 MALT 0110 0001 2345 MTLC AST0 01S",
      "Mauritania,MR,FALSE,27,MR13 0002 0001 0100 0012 3456 753",
      "Mauritius,MU,FALSE,30,MU17 BOMM 0101 1010 3030 0200 000M UR",
      "Moldova,MD,FALSE,24,MD24 AG00 0225 1000 1310 4168",
      "Monaco,MC,TRUE,27,MC58 1122 2000 0101 2345 6789 030",
      "Montenegro,ME,FALSE,22,ME25 5050 0001 2345 6789 51",
      "Netherlands,NL,TRUE,18,NL91 ABNA 0417 1643 00", "Norway,NO,TRUE,15,NO93 8601 1117 947",
      "Pakistan,PK,FALSE,24,PK36 SCBL 0000 0011 2345 6702",
      "Palestine,PS,FALSE,29,PS92 PALS 0000 0000 0400 1234 5670 2",
      "Poland,PL,TRUE,28,PL61 1090 1014 0000 0712 1981 2874",
      "Portugal,PT,TRUE,25,PT50 0002 0123 1234 5678 9015 4",
      "Qatar,QA,FALSE,29,QA58 DOHB 0000 1234 5678 90AB CDEF G",
      "Romania,RO,TRUE,24,RO49 AAAA 1B31 0075 9384 0000",
      "Saint Lucia,LC,FALSE,32,LC55 HEMM 0001 0001 0012 0012 00023015",
      "San Marino,SM,TRUE,27,SM86 U032 2509 8000 0000 0270 100",
      "Sao Tome and Principe,ST,FALSE,25,ST68 0001 0001 0051 8453 1011 2",
      "Saudi Arabia,SA,FALSE,24,SA03 8000 0000 6080 1016 7519",
      "Serbia,RS,FALSE,22,RS35 2600 0560 1001 6113 79",
      "Seychelles,SC,FALSE,31,SC18 SSCB 1101 0000 0000 0000 1497 USD",
      "Slovakia,SK,TRUE,24,SK31 1200 0000 1987 4263 7541",
      "Slovenia,SI,TRUE,19,SI56 2633 0001 2039 086",
      "Spain,ES,TRUE,24,ES91 2100 0418 4502 0005 1332",
      "Sweden,SE,TRUE,24,SE45 5000 0000 0583 9825 7466",
      "Switzerland,CH,TRUE,21,CH93 0076 2011 6238 5295 7",
      "Timor-Leste,TL,FALSE,23,TL38 0080 0123 4567 8910 157",
      "Tunisia,TN,FALSE,24,TN59 1000 6035 1835 9847 8831",
      "Turkey,TR,FALSE,26,TR33 0006 1005 1978 6457 8413 26",
      "Ukraine,UA,FALSE,29,UA21 3223 1300 0002 6007 2335 6600 1",
      "United Arab Emirates,AE,FALSE,23,AE07 0331 2345 6789 0123 456",
      "United Kingdom,GB,TRUE,22,GB29 NWBK 6016 1331 9268 19",
      "Vatican City State,VA,FALSE,22,VA59 001 1230 0001 2345 678",
      "Virgin Islands,VG,FALSE,24,VG96 VPVG 0000 0123 4567 8901"};

  private static Map<String, Integer> IBAN_LENGTHS = new HashMap<>();
  private static Map<String, String> COUNTRY_NAMES = new HashMap<>();
  private static Map<String, Boolean> SEPA_MEMBERS = new HashMap<>();

  static {
    for (String row : DICTIONARY) {

      List<String> cols = Splitter.on(',').splitToList(row);

      IBAN_LENGTHS.put(cols.get(1), Integer.parseInt(cols.get(3), 10));
      COUNTRY_NAMES.put(cols.get(1), cols.get(0));
      SEPA_MEMBERS.put(cols.get(1), Boolean.parseBoolean(cols.get(2)));
    }
  }

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
    newParameters
        .add(BoxedType.create("(?:^|\\p{Zs}|\\b)([A-Z]{2})(\\d{2})([A-Z\\d]+)(?:$|\\p{Zs}|\\b)"));

    BoxedType boxedType = super.evaluate(newParameters);
    SpanSequence sequence = (SpanSequence) boxedType.value();
    SpanSequence newSequence = new SpanSequence();

    for (Span span : sequence.sequence()) {

      String iban = span.text();
      String countryCode = span.getFeature("GROUP_1");

      if (iban.length() == IBAN_LENGTHS.getOrDefault(countryCode, -1)) {

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
          span.setFeature("COUNTRY_NAME", COUNTRY_NAMES.get(countryCode));
          span.setFeature("IS_SEPA_MEMBER", Boolean.toString(SEPA_MEMBERS.get(countryCode)));

          newSequence.add(span);
        }
      }
    }
    return BoxedType.create(newSequence);
  }
}
