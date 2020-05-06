package com.computablefacts.nona.functions.patternoperators;

import java.util.stream.Collectors;

import com.computablefacts.nona.dictionaries.Tld;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.re2j.Pattern;

/**
 * Patterns that should be applied on the raw text.
 */
final public class PatternsForward {

  private PatternsForward() {}

  public static String leftBoundary() {
    return "(?:^|\\p{Zs}|\\b)";
  }

  public static String rightBoundary() {
    return "(?:$|\\p{Zs}|\\b)";
  }

  public static String tld() {
    return Joiner.on('|').join(Tld.load().stream().filter(tld -> !Strings.isNullOrEmpty(tld))
        .map(tld -> Pattern.quote(tld.toUpperCase())).collect(Collectors.toSet()));
  }

  /**
   * Match and capture a single group :
   *
   * <ol>
   * <li>Group 1 : the emoticon</li>
   * </ol>
   *
   * @return regular expression.
   */
  public static String emoticon() {
    return "(:-0)|(8-0)|([Xx]-[Dd])|([Xx]-[Pp])|(:'-\\()|(:'-\\))|(O_O)|(o-o)|(O_o)|(o_O)|(o_o)|(O-O)|(>_<)|(>_>)|(<_<)|(\\\\o/)|(:S)|(\\^\\^)|(\\^_\\^)|([<>]?[:;=8][\\-o\\*\\']?[\\)\\]\\(\\[dDpPOo/\\:\\}\\{@\\|\\\\]|[\\)\\]\\(\\[dDpPOo/\\:\\}\\{@\\|\\\\][\\-o\\*\\']?[:;=8xX][<>]?)";
  }

  /**
   * Match and capture 4 groups :
   *
   * <ol>
   * <li>Group 1 : the institution code</li>
   * <li>Group 2 : the country code</li>
   * <li>Group 3 : the location code</li>
   * <li>Group 4 : the branch code (optional)</li>
   * </ol>
   * 
   * @return regular expression.
   */
  public static String bic() {

    String institution = "[A-Za-z]{4}";
    String country = "[A-Za-z]{2}";
    String location = "[A-Za-z0-9]{2}";
    String branch = "[A-Za-z0-9]{3}";

    return "(" + institution + ")[-#\\p{Zs}]*(" + country + ")[-#\\p{Zs}]*(" + location
        + ")(?:[-#\\p{Zs}]*(" + branch + "))?";
  }

  /**
   * Match and capture 2 groups :
   *
   * <ol>
   * <li>Group 1 : the username</li>
   * <li>Group 2 : the ip/domain</li>
   * </ol>
   *
   * @return regular expression.
   */
  public static String email() {

    String username = "\"?\\w(?:[-.+]?\\w)*\"?";
    String ip = "\\[?\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\]?";
    String domain = "[-\\w]+(?:\\.[-\\w]+)*\\.[A-Za-z]{2,4}";

    return "(" + username + ")[@＠]((?:" + ip + ")|(?:" + domain + "))";
  }

  /**
   * Match and capture 7 groups :
   *
   * <ol>
   * <li>Group 1 : the protocol (optional)</li>
   * <li>Group 2 : the username (optional)</li>
   * <li>Group 3 : the password (optional)</li>
   * <li>Group 4 : the hostname</li>
   * <li>Group 5 : the port (optional)</li>
   * <li>Group 6 : the path (optional)</li>
   * <li>Group 7 : the query string (optional)</li>
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

    return "(?:(" + protocol + ")://)(?:(" + username + ")(?::(" + password + "))?[@＠])?("
        + hostname + ")(?::(" + port + "))?(" + path + ")?(?:\\?(" + queryString + "))?";
  }

  /**
   * Match and capture 4 groups :
   *
   * <ol>
   * <li>Group 1 : the protocol (optional)</li>
   * <li>Group 2 : the hostname</li>
   * <li>Group 3 : the the port (optional)</li>
   * <li>Group 4 : the the path (optional)</li>
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

    return "(" + protocol + ")://(" + hostname + ")(" + port + ")?(" + path + ")?";
  }

  /**
   * Match and capture 1 group :
   *
   * <ol>
   * <li>Group 1 : full path</li>
   * </ol>
   * 
   * @return regular expression.
   */
  public static String winPath() {

    String drive = "[A-Z]:\\\\";
    String name = "[A-Z\\d][A-Z\\d\\- '_\\(\\)]{0,61}";
    String extension = "[A-Z\\d]{1,6}";

    return "(" + drive + "(?:" + name + "\\\\?)*" + name + "(?:\\." + extension + ")?)";
  }

  /**
   * Match and capture 1 group :
   *
   * <ol>
   * <li>Group 1 : full path</li>
   * </ol>
   *
   * @return regular expression.
   */
  public static String unixPath() {
    return "((?:/[A-Z\\d.][A-Z\\d\\-._]{0,61})+)";
  }
}
