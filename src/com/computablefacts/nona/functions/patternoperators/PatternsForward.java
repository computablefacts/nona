package com.computablefacts.nona.functions.patternoperators;

import java.util.stream.Collectors;

import com.computablefacts.nona.dictionaries.Tld;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.re2j.Pattern;

/**
 * Patterns that should be applied on the raw text.
 *
 * The first group must always be the full matched pattern.
 */
final public class PatternsForward {

  private PatternsForward() {}

  public static String leftBoundary() {
    return "(?:^|\\p{Zs}|\\b|[^\\p{N}\\p{L}])";
  }

  public static String rightBoundary() {
    return "(?:$|\\p{Zs}|\\b|[^\\p{N}\\p{L}])";
  }

  /**
   * Regex for emoticons extraction. Match and capture a single group :
   *
   * <ol>
   * <li>Group 1 : whole match</li>
   * </ol>
   *
   * @return regular expression.
   */
  public static String emoticon() {
    return "(:-0)|(8-0)|([Xx]-[Dd])|([Xx]-[Pp])|(:'-\\()|(:'-\\))|(O_O)|(o-o)|(O_o)|(o_O)|(o_o)|(O-O)|(>_<)|(>_>)|(<_<)|(\\\\o/)|(:S)|(\\^\\^)|(\\^_\\^)|([<>]?[:;=8][\\-o\\*\\']?[\\)\\]\\(\\[dDpPOo/\\:\\}\\{@\\|\\\\]|[\\)\\]\\(\\[dDpPOo/\\:\\}\\{@\\|\\\\][\\-o\\*\\']?[:;=8xX][<>]?)";
  }

  /**
   * Regex for Bank Identifier Code extraction. Match and capture 5 groups :
   *
   * <ol>
   * <li>Group 1 : whole match</li>
   * <li>Group 2 : the institution code</li>
   * <li>Group 3 : the country code</li>
   * <li>Group 4 : the location code</li>
   * <li>Group 5 : the branch code (optional)</li>
   * </ol>
   * 
   * @return regular expression.
   */
  public static String bic() {

    String institution = "[A-Za-z]{4}";
    String country = "[A-Za-z]{2}";
    String location = "[A-Za-z0-9]{2}";
    String branch = "[A-Za-z0-9]{3}";

    return "((" + institution + ")[-#\\p{Zs}]*(" + country + ")[-#\\p{Zs}]*(" + location
        + ")(?:[-#\\p{Zs}]*(" + branch + "))?)";
  }

  /**
   * Regex for email extraction. Match and capture 3 groups :
   *
   * <ol>
   * <li>Group 1 : whole match</li>
   * <li>Group 2 : the username</li>
   * <li>Group 3 : the ip/domain</li>
   * </ol>
   *
   * @return regular expression.
   */
  public static String email() {

    String username = "\"?\\w(?:[-.+]?\\w)*\"?";
    String ip = "\\[?\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\]?";
    String domain = "[-\\w]+(?:\\.[-\\w]+)*\\.[A-Za-z]{2,4}";

    return "(?i)((" + username + ")[@＠]((?:" + ip + ")|(?:" + domain + ")))(?-i)";
  }

  /**
   * Regex for URL extraction. Match and capture 8 groups :
   *
   * <ol>
   * <li>Group 1 : whole match</li>
   * <li>Group 2 : the protocol (optional)</li>
   * <li>Group 3 : the username (optional)</li>
   * <li>Group 4 : the password (optional)</li>
   * <li>Group 5 : the hostname</li>
   * <li>Group 6 : the port (optional)</li>
   * <li>Group 7 : the path (optional)</li>
   * <li>Group 8 : the query string (optional)</li>
   * </ol>
   * 
   * @return regular expression.
   */
  public static String url() {

    String protocol = "[a-zA-Z]+";
    String username = "[\\p{L}\\p{N}]+";
    String password = "[\\p{L}\\p{N}]+";
    String hostname = "\\w+(?:[-\\.]?\\w)*(?:\\.(?:" + tld() + "))";
    String port = "\\d{2,5}";
    String path =
        "/[^.!,?;\"'<>\\[\\]{}\\s\\x7F-\\xFF]*(?:[.!,]+[^.!,?;\"'<>\\[\\]{}\\s\\x7F-\\xFF]+)*";
    String queryString = "[^.!,?;\"'<>\\[\\]{}\\s\\x7F-\\xFF]+";

    return "(?i)((?:(" + protocol + ")://)(?:(" + username + ")(?::(" + password + "))?[@＠])?("
        + hostname + ")(?::(" + port + "))?(" + path + ")?(?:\\?(" + queryString + "))?)(?-i)";
  }

  /**
   * Regex for TOR onions extraction. Match and capture 5 groups :
   *
   * <ol>
   * <li>Group 1 : whole match</li>
   * <li>Group 2 : the protocol (optional)</li>
   * <li>Group 3 : the hostname</li>
   * <li>Group 4 : the the port (optional)</li>
   * <li>Group 5 : the the path (optional)</li>
   * </ol>
   *
   * @return regular expression.
   */
  public static String onion() {

    String protocol = "[a-zA-Z]+";
    String hostname = "[a-zA-Z2-7]{16}\\.[oO][nN][iI][oO][nN]";
    String port = ":\\d+";
    String path =
        "/[^.!,?;\"'<>()\\[\\]{}\\s\\x7F-\\xFF]*(?:[.!,?]+[^.!,?;\"'<>()\\[\\]{}\\s\\x7F-\\xFF]+)*";

    return "(?i)((" + protocol + ")://(" + hostname + ")(" + port + ")?(" + path + ")?)(?-i)";
  }

  /**
   * Regex for Windows file path extraction. Match and capture 1 group :
   *
   * <ol>
   * <li>Group 1 : whole match</li>
   * </ol>
   * 
   * @return regular expression.
   */
  public static String winPath() {

    String drive = "[A-Z]:\\\\";
    String name = "[A-Z\\d][A-Z\\d\\- '_\\(\\)]{0,61}";
    String extension = "[A-Z\\d]{1,6}";

    return "(?i)(" + drive + "(?:" + name + "\\\\?)*" + name + "(?:\\." + extension + ")?)(?-i)";
  }

  /**
   * Regex for Unix file path extraction. Match and capture 1 group :
   *
   * <ol>
   * <li>Group 1 : whole match</li>
   * </ol>
   *
   * @return regular expression.
   */
  public static String unixPath() {
    return "(?i)((?:/[A-Z\\d.][A-Z\\d\\-._]{0,61})+)(?-i)";
  }

  /**
   * Regex for IPV4 addresses extraction. Match and capture 1 group :
   *
   * <ol>
   * <li>Group 1 : whole match</li>
   * </ol>
   *
   * @return regular expression.
   */
  public static String ipv4() {
    return "(?i)((?:(?:\\d|[01]?\\d\\d|2[0-4]\\d|25[0-5])\\.){3}(?:25[0-5]|2[0-4]\\d|[01]?\\d\\d|\\d)(?:\\/\\d{1,2})?)(?-i)";
  }

  /**
   * Regex for local IP addresses extraction. Match and capture 1 group :
   *
   * <ol>
   * <li>Group 1 : whole match</li>
   * </ol>
   *
   * @return regular expression.
   */
  public static String iplocal() {

    String ten = "10\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
    String oneNineTwo = "192\\.168\\.\\d{1,3}\\.\\d{1,3}";
    String oneSevenTwo = "172\\.(?:1[6-9]|2\\d|3[01])\\.\\d{1,3}\\.\\d{1,3}";
    String oneTwoSeven = "127\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";

    return "(?i)(" + ten + "|" + oneNineTwo + "|" + oneSevenTwo + "|" + oneTwoSeven + ")(?-i)";
  }

  /**
   * Regex for IPV6 addresses extraction. Only normal addresses are matched. Match and capture 1
   * group :
   *
   * <ol>
   * <li>Group 1 : whole match</li>
   * </ol>
   *
   * @return regular expression.
   */
  public static String ipv6() {

    String ipv6hex4deccompressed =
        "(?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?::(?:[0-9A-Fa-f]{1,4}:)*25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d(?:\\.(?:25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}";
    String ipv66hex4dec =
        "(?:[0-9A-Fa-f]{1,4}:){6,6}25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d(?:\\.(?:25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}";
    String ipv6hexcompressed =
        "(?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?::(?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?";
    String ipv6regex = "(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}";

    return "(?i)(" + ipv6hex4deccompressed + "|" + ipv66hex4dec + "|" + ipv6hexcompressed + "|"
        + ipv6regex + ")(?-i)";
  }

  /**
   * Regex for MAC addresses extraction. Match and capture 1 group :
   *
   * <ol>
   * <li>Group 1 : whole match</li>
   * </ol>
   *
   * @return regular expression.
   */
  public static String macAddress() {
    return "(?i)([A-F\\d]{2}(?:[:-][A-F\\d]{2}){5})(?-i)";
  }

  /**
   * Regex for DATE extraction. Match and capture 1 group :
   *
   * <ol>
   * <li>Group 1 : whole match</li>
   * </ol>
   *
   * @return regular expression.
   */
  public static String date() {

    String yyyymmdd = "(?:19|20)\\d\\d[- /.](?:1[012]|0*[1-9])[- /.](?:3[01]|[12][0-9]|0*[1-9])";
    String ddmmyyyy = "(?:3[01]|[12][0-9]|0*[1-9])[- /.](?:1[012]|0*[1-9])[- /.](?:19|20)\\d\\d";
    String mmddyyyy = "(?:1[012]|0*[1-9])[- /.](?:3[01]|[12][0-9]|0*[1-9])[- /.](?:19|20)\\d\\d";

    return "(?i)(" + yyyymmdd + "|" + ddmmyyyy + "|" + mmddyyyy + ")(?-i)";
  }

  /**
   * Regex for TIME extraction. Match and capture 1 group :
   *
   * <ol>
   * <li>Group 1 : whole match</li>
   * </ol>
   *
   * @return regular expression.
   */
  public static String time() {

    String hours = "(?:[0-1]?[0-9]|[2][0-3])";
    String minutes = "(?:h|:)(?:[0-5]?[0-9])";
    String seconds = "(?:(?:mn|:)[0-5]?[0-9]s*)?";

    return "(?i)(" + hours + minutes + seconds + ")(?-i)";
  }

  /**
   * Regex for DATETIME extraction. Match and capture 4 groups :
   *
   * <ol>
   * <li>Group 1 : whole match</li>
   * <li>Group 2 : date</li>
   * <li>Group 3 : time</li>
   * <li>Group 4 : timezone</li>
   * </ol>
   *
   * @return regular expression.
   */
  public static String dateTime() {
    return "(" + date() + "[T\\p{Zs}]" + time()
        + "(?i)(Z|[+-](?:2[0-3]|[01][0-9]):[0-5][0-9])?(?-i))";
  }

  /**
   * Regex for NUMBERS extraction. Match and capture 1 group :
   *
   * <ol>
   * <li>Group 1 : whole match</li>
   * </ol>
   *
   * @return regular expression.
   */
  public static String number() {
    return "(?i)([-+]?\\p{Zs}*(?:[0-9]{1,3}\\p{Zs}?)*(?:[0-9]+[,]|[0-9]*[.])?[0-9]+(?:[eE][-+]?[0-9]+)?)(?-i)";
  }

  /**
   * Regex for FINANCIAL NUMBERS extraction. Match and capture 1 group :
   *
   * <ol>
   * <li>Group 1 : whole match</li>
   * </ol>
   *
   * See https://en.wikipedia.org/wiki/Decimal_separator for details.
   *
   * @return regular expression.
   */
  public static String financialNumber() {

    // 1,234,567.89 (us)
    String form1 = "(?:(?:[0-9]{1,3}[,])+[0-9]{3}(?:[.][0-9]+)?|[0-9]{1,3}[.][0-9]+)";

    // 1 234 567,89 (europe) or 1 234 567.89 (europe)
    String form2 = "(?:(?:[0-9]{1,3}\\p{Zs})+[0-9]{3}(?:[,.][0-9]+)?|[0-9]{1,3}[,.][0-9]+)";

    // 123,4567.89 (china)
    String form3 = "(?:(?:[0-9]{1,4}[\\p{Zs},])+[0-9]{4}(?:[.][0-9]+)?|[0-9]{1,4}[.][0-9]+)";

    // 1.234.567,89 (europe)
    String form4 = "(?:(?:[0-9]{1,3}[.])+[0-9]{3}(?:[,][0-9]+)?|[0-9]{1,3}[,][0-9]+)";

    // 1'234'567.89 (switzerland) or 1'234'567,89 (switzerland)
    String form5 = "(?:(?:[0-9]{1,3}['])+[0-9]{3}(?:[.,][0-9]+)?|[0-9]{1,3}[.,][0-9]+)";

    // 1'234,567.89 (mexico)
    String form6 = "(?:(?:[0-9]{1,3}['])+[0-9]{1,3}[,])?[0-9]{1,3}(?:[.][0-9]+)?";

    return "(?i)([-+]?\\p{Zs}*(?:(?:" + form1 + ")|(?:" + form2 + ")|(?:" + form3 + ")|(?:" + form4
        + ")|(?:" + form5 + ")|(?:" + form6 + ")))(?-i)";
  }

  /**
   * Regex for MONETARY AMOUNTS extraction. Match and capture 5 groups :
   *
   * <ol>
   * <li>Group 1 : whole match</li>
   * <li>Group 2 : monetary symbol. If not matched, group 5 is matched.</li>
   * <li>Group 3 : financial number. If not matched, group 4 is matched.</li>
   * <li>Group 4 : regular number. If not matched, group 3 is matched.</li>
   * <li>Group 5 : monetary symbol. If not matched, group 2 is matched.</li>
   * </ol>
   *
   * @return regular expression.
   */
  public static String monetaryAmount() {
    return "(?i)((\\p{Sc}?)\\p{Zs}*(?:" + financialNumber() + "|" + number()
        + ")\\p{Zs}*(\\p{Sc}?))(?-i)";
  }

  /**
   * Regex for PERCENTS extraction. Match and capture 2 groups :
   *
   * <ol>
   * <li>Group 1 : whole match</li>
   * <li>Group 2 : number match</li>
   * </ol>
   *
   * @return regular expression.
   */
  public static String percent() {
    return "(?i)(" + number() + "\\p{Zs}*%)(?-i)";
  }

  private static String tld() {
    return Joiner.on('|').join(Tld.load().stream().filter(tld -> !Strings.isNullOrEmpty(tld))
        .map(tld -> Pattern.quote(tld.toUpperCase())).collect(Collectors.toSet()));
  }
}
