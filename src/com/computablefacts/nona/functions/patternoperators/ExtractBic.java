package com.computablefacts.nona.functions.patternoperators;

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
import com.google.errorprone.annotations.Var;

public class ExtractBic extends RegexExtract {

  /*
   * Extracted from https://www.iban.com/country-codes
   */
  static String[] DICTIONARY = {"Afghanistan,AF", "Albania,AL", "Algeria,DZ", "American Samoa,AS",
      "Andorra,AD", "Angola,AO", "Anguilla,AI", "Antarctica,AQ", "Antigua and Barbuda,AG",
      "Argentina,AR", "Armenia,AM", "Aruba,AW", "Australia,AU", "Austria,AT", "Azerbaijan,AZ",
      "Bahamas (the),BS", "Bahrain,BH", "Bangladesh,BD", "Barbados,BB", "Belarus,BY", "Belgium,BE",
      "Belize,BZ", "Benin,BJ", "Bermuda,BM", "Bhutan,BT", "Bolivia (Plurinational State of),BO",
      "Bonaire, Sint Eustatius and Saba,BQ", "Bosnia and Herzegovina,BA", "Botswana,BW",
      "Bouvet Island,BV", "Brazil,BR", "British Indian Ocean Territory (the),IO",
      "Brunei Darussalam,BN", "Bulgaria,BG", "Burkina Faso,BF", "Burundi,BI", "Cabo Verde,CV",
      "Cambodia,KH", "Cameroon,CM", "Canada,CA", "Cayman Islands (the),KY",
      "Central African Republic (the),CF", "Chad,TD", "Chile,CL", "China,CN", "Christmas Island,CX",
      "Cocos (Keeling) Islands (the),CC", "Colombia,CO", "Comoros (the),KM",
      "Congo (the Democratic Republic of the),CD", "Congo (the),CG", "Cook Islands (the),CK",
      "Costa Rica,CR", "Croatia,HR", "Cuba,CU", "Curaçao,CW", "Cyprus,CY", "Czechia,CZ",
      "Côte d'Ivoire,CI", "Denmark,DK", "Djibouti,DJ", "Dominica,DM", "Dominican Republic (the),DO",
      "Ecuador,EC", "Egypt,EG", "El Salvador,SV", "Equatorial Guinea,GQ", "Eritrea,ER",
      "Estonia,EE", "Eswatini,SZ", "Ethiopia,ET", "Falkland Islands (the) [Malvinas],FK",
      "Faroe Islands (the),FO", "Fiji,FJ", "Finland,FI", "France,FR", "French Guiana,GF",
      "French Polynesia,PF", "French Southern Territories (the),TF", "Gabon,GA", "Gambia (the),GM",
      "Georgia,GE", "Germany,DE", "Ghana,GH", "Gibraltar,GI", "Greece,GR", "Greenland,GL",
      "Grenada,GD", "Guadeloupe,GP", "Guam,GU", "Guatemala,GT", "Guernsey,GG", "Guinea,GN",
      "Guinea-Bissau,GW", "Guyana,GY", "Haiti,HT", "Heard Island and McDonald Islands,HM",
      "Holy See (the),VA", "Honduras,HN", "Hong Kong,HK", "Hungary,HU", "Iceland,IS", "India,IN",
      "Indonesia,ID", "Iran (Islamic Republic of),IR", "Iraq,IQ", "Ireland,IE", "Isle of Man,IM",
      "Israel,IL", "Italy,IT", "Jamaica,JM", "Japan,JP", "Jersey,JE", "Jordan,JO", "Kazakhstan,KZ",
      "Kenya,KE", "Kiribati,KI", "Korea (the Democratic People's Republic of),KP",
      "Korea (the Republic of),KR", "Kuwait,KW", "Kyrgyzstan,KG",
      "Lao People's Democratic Republic (the),LA", "Latvia,LV", "Lebanon,LB", "Lesotho,LS",
      "Liberia,LR", "Libya,LY", "Liechtenstein,LI", "Lithuania,LT", "Luxembourg,LU", "Macao,MO",
      "Madagascar,MG", "Malawi,MW", "Malaysia,MY", "Maldives,MV", "Mali,ML", "Malta,MT",
      "Marshall Islands (the),MH", "Martinique,MQ", "Mauritania,MR", "Mauritius,MU", "Mayotte,YT",
      "Mexico,MX", "Micronesia (Federated States of),FM", "Moldova (the Republic of),MD",
      "Monaco,MC", "Mongolia,MN", "Montenegro,ME", "Montserrat,MS", "Morocco,MA", "Mozambique,MZ",
      "Myanmar,MM", "Namibia,NA", "Nauru,NR", "Nepal,NP", "Netherlands (the),NL",
      "New Caledonia,NC", "New Zealand,NZ", "Nicaragua,NI", "Niger (the),NE", "Nigeria,NG",
      "Niue,NU", "Norfolk Island,NF", "Northern Mariana Islands (the),MP", "Norway,NO", "Oman,OM",
      "Pakistan,PK", "Palau,PW", "Palestine, State of,PS", "Panama,PA", "Papua New Guinea,PG",
      "Paraguay,PY", "Peru,PE", "Philippines (the),PH", "Pitcairn,PN", "Poland,PL", "Portugal,PT",
      "Puerto Rico,PR", "Qatar,QA", "Republic of North Macedonia,MK", "Romania,RO",
      "Russian Federation (the),RU", "Rwanda,RW", "Réunion,RE", "Saint Barthélemy,BL",
      "Saint Helena, Ascension and Tristan da Cunha,SH", "Saint Kitts and Nevis,KN",
      "Saint Lucia,LC", "Saint Martin (French part),MF", "Saint Pierre and Miquelon,PM",
      "Saint Vincent and the Grenadines,VC", "Samoa,WS", "San Marino,SM",
      "Sao Tome and Principe,ST", "Saudi Arabia,SA", "Senegal,SN", "Serbia,RS", "Seychelles,SC",
      "Sierra Leone,SL", "Singapore,SG", "Sint Maarten (Dutch part),SX", "Slovakia,SK",
      "Slovenia,SI", "Solomon Islands,SB", "Somalia,SO", "South Africa,ZA",
      "South Georgia and the South Sandwich Islands,GS", "South Sudan,SS", "Spain,ES",
      "Sri Lanka,LK", "Sudan (the),SD", "Suriname,SR", "Svalbard and Jan Mayen,SJ", "Sweden,SE",
      "Switzerland,CH", "Syrian Arab Republic,SY", "Taiwan (Province of China),TW", "Tajikistan,TJ",
      "Tanzania, United Republic of,TZ", "Thailand,TH", "Timor-Leste,TL", "Togo,TG", "Tokelau,TK",
      "Tonga,TO", "Trinidad and Tobago,TT", "Tunisia,TN", "Turkey,TR", "Turkmenistan,TM",
      "Turks and Caicos Islands (the),TC", "Tuvalu,TV", "Uganda,UG", "Ukraine,UA",
      "United Arab Emirates (the),AE",
      "United Kingdom of Great Britain and Northern Ireland (the),GB",
      "United States Minor Outlying Islands (the),UM", "United States of America (the),US",
      "Uruguay,UY", "Uzbekistan,UZ", "Vanuatu,VU", "Venezuela (Bolivarian Republic of),VE",
      "Viet Nam,VN", "Virgin Islands (British),VG", "Virgin Islands (U.S.),VI",
      "Wallis and Futuna,WF", "Western Sahara,EH", "Yemen,YE", "Zambia,ZM", "Zimbabwe,ZW",
      "Åland Islands,AX"};
  private static Map<String, String> COUNTRY_CODES_ISO_3166_ALPHA2 = new HashMap<>();

  static {
    for (String row : DICTIONARY) {

      List<String> cols = Splitter.on(',').splitToList(row);

      COUNTRY_CODES_ISO_3166_ALPHA2.put(cols.get(1), cols.get(0));
    }
  }

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

      if (COUNTRY_CODES_ISO_3166_ALPHA2.containsKey(countryCode)) {

        span.removeFeature("GROUP_COUNT");
        span.removeFeature("GROUP_1");
        span.removeFeature("GROUP_2");
        span.removeFeature("GROUP_3");
        span.removeFeature("GROUP_4");

        span.setFeature("INSTITUTION_CODE", institutionCode);
        span.setFeature("COUNTRY_CODE", countryCode);
        span.setFeature("COUNTRY_NAME", COUNTRY_CODES_ISO_3166_ALPHA2.get(countryCode));
        span.setFeature("LOCATION_CODE", locationCode);
        span.setFeature("BRANCH_CODE", branchCode);

        // If the last character of the location code is 0 it is a Test BIC
        span.setFeature("IS_TEST_BIC",
            Boolean.toString(locationCode.charAt(locationCode.length() - 1) == '0'));

        newSequence.add(span);
      }
    }
    return BoxedType.create(newSequence);
  }
}
