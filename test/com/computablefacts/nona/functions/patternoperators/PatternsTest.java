package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsForward.date;
import static com.computablefacts.nona.functions.patternoperators.PatternsForward.dateTime;
import static com.computablefacts.nona.functions.patternoperators.PatternsForward.email;
import static com.computablefacts.nona.functions.patternoperators.PatternsForward.emoticon;
import static com.computablefacts.nona.functions.patternoperators.PatternsForward.iplocal;
import static com.computablefacts.nona.functions.patternoperators.PatternsForward.ipv4;
import static com.computablefacts.nona.functions.patternoperators.PatternsForward.ipv6;
import static com.computablefacts.nona.functions.patternoperators.PatternsForward.macAddress;
import static com.computablefacts.nona.functions.patternoperators.PatternsForward.onion;
import static com.computablefacts.nona.functions.patternoperators.PatternsForward.time;
import static com.computablefacts.nona.functions.patternoperators.PatternsForward.unixPath;
import static com.computablefacts.nona.functions.patternoperators.PatternsForward.url;
import static com.computablefacts.nona.functions.patternoperators.PatternsForward.winPath;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.errorprone.annotations.Var;
import com.google.re2j.Matcher;
import com.google.re2j.Pattern;

public class PatternsTest {

  @Test
  public void testSmileyJapanese() {

    Pattern pattern = Pattern.compile("^" + emoticon() + "$");

    assertTrue(pattern.matches("^^"));
    assertTrue(pattern.matches("^_^"));
  }

  @Test
  public void testSmileyHappyFace() {

    Pattern pattern = Pattern.compile("^" + emoticon() + "$");

    assertTrue(pattern.matches(":-)"));
    assertTrue(pattern.matches(":-]"));
    assertTrue(pattern.matches("8-)"));
    assertTrue(pattern.matches(":-}"));
    assertTrue(pattern.matches(":o)"));
    assertTrue(pattern.matches("=]"));
    assertTrue(pattern.matches("=)"));
    assertTrue(pattern.matches(":)"));
    assertTrue(pattern.matches(":]"));
    assertTrue(pattern.matches("8)"));
    assertTrue(pattern.matches(":}"));
  }

  @Test
  public void testSmileyLaughing() {

    Pattern pattern = Pattern.compile("^" + emoticon() + "$");

    assertTrue(pattern.matches(":-D"));
    assertTrue(pattern.matches("8-D"));
    assertTrue(pattern.matches("x-D"));
    assertTrue(pattern.matches("X-D"));
    assertTrue(pattern.matches("=D"));
    assertTrue(pattern.matches(":D"));
    assertTrue(pattern.matches("8D"));
  }

  @Test
  public void testSmileyFrown() {

    Pattern pattern = Pattern.compile("^" + emoticon() + "$");

    assertTrue(pattern.matches(":-("));
    assertTrue(pattern.matches(":-["));
    assertTrue(pattern.matches(":{"));
    assertTrue(pattern.matches(":("));
    assertTrue(pattern.matches(":["));
    assertTrue(pattern.matches(":@"));
  }

  @Test
  public void testSmileyCrying() {

    Pattern pattern = Pattern.compile("^" + emoticon() + "$");

    assertTrue(pattern.matches(":'-("));
    assertTrue(pattern.matches(":'("));
  }

  @Test
  public void testSmileyTearsOfHappiness() {

    Pattern pattern = Pattern.compile("^" + emoticon() + "$");

    assertTrue(pattern.matches(":'-)"));
    assertTrue(pattern.matches(":')"));
  }

  @Test
  public void testSmileySurprise() {

    Pattern pattern = Pattern.compile("^" + emoticon() + "$");

    assertTrue(pattern.matches(":-O"));
    assertTrue(pattern.matches(":-o"));
    assertTrue(pattern.matches(":-0"));
    assertTrue(pattern.matches("8-0"));
    assertTrue(pattern.matches(":O"));
  }

  @Test
  public void testSmileyWink() {

    Pattern pattern = Pattern.compile("^" + emoticon() + "$");

    assertTrue(pattern.matches(";-)"));
    assertTrue(pattern.matches(";-]"));
    assertTrue(pattern.matches(";D"));
    assertTrue(pattern.matches(";)"));
    assertTrue(pattern.matches(";]"));
  }

  @Test
  public void testSmileyCheeky() {

    Pattern pattern = Pattern.compile("^" + emoticon() + "$");

    assertTrue(pattern.matches(":-P"));
    assertTrue(pattern.matches("X-P"));
    assertTrue(pattern.matches("x-p"));
    assertTrue(pattern.matches(":-p"));
    assertTrue(pattern.matches(":P"));
    assertTrue(pattern.matches(":p"));
    assertTrue(pattern.matches("=p"));
  }

  @Test
  public void testSmileySkeptical() {

    Pattern pattern = Pattern.compile("^" + emoticon() + "$");

    assertTrue(pattern.matches(":\\"));
    assertTrue(pattern.matches("=\\"));
    assertTrue(pattern.matches(":S"));
    assertTrue(pattern.matches(":-/"));
    assertTrue(pattern.matches(":/"));
    assertTrue(pattern.matches("=/"));
  }

  @Test
  public void testSmileyStraightFace() {

    Pattern pattern = Pattern.compile("^" + emoticon() + "$");

    assertTrue(pattern.matches(":-|"));
    assertTrue(pattern.matches(":|"));
  }

  @Test
  public void testSmileySealedLips() {

    Pattern pattern = Pattern.compile("^" + emoticon() + "$");

    assertTrue(pattern.matches(":-X"));
    assertTrue(pattern.matches(":X"));
  }

  @Test
  public void testSmileyDevious() {

    Pattern pattern = Pattern.compile("^" + emoticon() + "$");

    assertTrue(pattern.matches("<_<"));
    assertTrue(pattern.matches(">_>"));
    assertTrue(pattern.matches(">_<"));
  }

  @Test
  public void testSmileyLol() {

    Pattern pattern = Pattern.compile("^" + emoticon() + "$");

    assertTrue(pattern.matches("\\o/"));
  }

  @Test
  public void testSmileyShock() {

    Pattern pattern = Pattern.compile("^" + emoticon() + "$");

    assertTrue(pattern.matches("O_O"));
    assertTrue(pattern.matches("o-o"));
    assertTrue(pattern.matches("O_o"));
    assertTrue(pattern.matches("o_O"));
    assertTrue(pattern.matches("o_o"));
    assertTrue(pattern.matches("O-O"));
  }

  @Test
  public void testEmail() {

    Pattern pattern = Pattern.compile("^" + email() + "$", Pattern.CASE_INSENSITIVE);

    @Var
    Matcher matcher = pattern.matcher("john.doe@example.com");

    assertTrue(matcher.find());
    assertEquals(2, matcher.groupCount());
    assertEquals("john.doe@example.com", matcher.group(0));
    assertEquals("john.doe", matcher.group(1));
    assertEquals("example.com", matcher.group(2));

    matcher = pattern.matcher("john+doe@example.com");

    assertTrue(matcher.find());
    assertEquals(2, matcher.groupCount());
    assertEquals("john+doe@example.com", matcher.group(0));
    assertEquals("john+doe", matcher.group(1));
    assertEquals("example.com", matcher.group(2));

    matcher = pattern.matcher("john.doe@example-international.com");

    assertTrue(matcher.find());
    assertEquals(2, matcher.groupCount());
    assertEquals("john.doe@example-international.com", matcher.group(0));
    assertEquals("john.doe", matcher.group(1));
    assertEquals("example-international.com", matcher.group(2));

    matcher = pattern.matcher("john+doe@example-international.com");

    assertTrue(matcher.find());
    assertEquals(2, matcher.groupCount());
    assertEquals("john+doe@example-international.com", matcher.group(0));
    assertEquals("john+doe", matcher.group(1));
    assertEquals("example-international.com", matcher.group(2));
  }

  @Test
  public void testSimpleUrl() {

    Pattern pattern = Pattern.compile("^" + url() + "$", Pattern.CASE_INSENSITIVE);

    @Var
    Matcher matcher = pattern.matcher("http://www.computablefacts.com/");

    assertTrue(matcher.find());

    assertEquals(7, matcher.groupCount());
    assertEquals("http://www.computablefacts.com/", matcher.group(0));
    assertEquals("http", matcher.group(1));
    assertEquals(null, matcher.group(2));
    assertEquals(null, matcher.group(3));
    assertEquals("www.computablefacts.com", matcher.group(4));
    assertEquals(null, matcher.group(5));
    assertEquals("/", matcher.group(6));
    assertEquals(null, matcher.group(7));

    matcher = pattern.matcher("https://1337.net");

    assertTrue(matcher.find());

    assertEquals(7, matcher.groupCount());
    assertEquals("https://1337.net", matcher.group(0));
    assertEquals("https", matcher.group(1));
    assertEquals(null, matcher.group(2));
    assertEquals(null, matcher.group(3));
    assertEquals("1337.net", matcher.group(4));
    assertEquals(null, matcher.group(5));
    assertEquals(null, matcher.group(6));
    assertEquals(null, matcher.group(7));

    matcher = pattern.matcher("https://a.bc.de");

    matcher.find();

    assertEquals(7, matcher.groupCount());
    assertEquals("https://a.bc.de", matcher.group(0));
    assertEquals("https", matcher.group(1));
    assertEquals(null, matcher.group(2));
    assertEquals(null, matcher.group(3));
    assertEquals("a.bc.de", matcher.group(4));
    assertEquals(null, matcher.group(5));
    assertEquals(null, matcher.group(6));
    assertEquals(null, matcher.group(7));
  }

  @Test
  public void testComplexUrl() {

    Pattern pattern = Pattern.compile("^" + url() + "$", Pattern.CASE_INSENSITIVE);

    @Var
    Matcher matcher = pattern.matcher("http://userid:password@example.com:8080");

    assertTrue(matcher.find());

    assertEquals(7, matcher.groupCount());
    assertEquals("http://userid:password@example.com:8080", matcher.group(0));
    assertEquals("http", matcher.group(1));
    assertEquals("userid", matcher.group(2));
    assertEquals("password", matcher.group(3));
    assertEquals("example.com", matcher.group(4));
    assertEquals("8080", matcher.group(5));
    assertEquals(null, matcher.group(6));
    assertEquals(null, matcher.group(7));

    matcher = pattern.matcher("https://userid@example.com/blah_blah_(wikipedia)/?q=test1&r=test2");

    assertTrue(matcher.find());

    assertEquals(7, matcher.groupCount());
    assertEquals("https://userid@example.com/blah_blah_(wikipedia)/?q=test1&r=test2",
        matcher.group(0));
    assertEquals("https", matcher.group(1));
    assertEquals("userid", matcher.group(2));
    assertEquals(null, matcher.group(3));
    assertEquals("example.com", matcher.group(4));
    assertEquals(null, matcher.group(5));
    assertEquals("/blah_blah_(wikipedia)/", matcher.group(6));
    assertEquals("q=test1&r=test2", matcher.group(7));
  }

  @Test
  public void testSimpleOnion() {

    Pattern pattern = Pattern.compile("^" + onion() + "$", Pattern.CASE_INSENSITIVE);

    @Var
    Matcher matcher = pattern.matcher("http://3g2upl4pq6kufc4m.onion/");

    assertTrue(matcher.find());

    assertEquals(4, matcher.groupCount());
    assertEquals("http://3g2upl4pq6kufc4m.onion/", matcher.group(0));
    assertEquals("http", matcher.group(1));
    assertEquals("3g2upl4pq6kufc4m.onion", matcher.group(2));
    assertEquals(null, matcher.group(3));
    assertEquals("/", matcher.group(4));

    matcher = pattern.matcher("http://lw4ipk5choakk5ze.onion/raw/evbLewgkDSVkifzv8zAo/");

    assertTrue(matcher.find());

    assertEquals(4, matcher.groupCount());
    assertEquals("http://lw4ipk5choakk5ze.onion/raw/evbLewgkDSVkifzv8zAo/", matcher.group(0));
    assertEquals("http", matcher.group(1));
    assertEquals("lw4ipk5choakk5ze.onion", matcher.group(2));
    assertEquals(null, matcher.group(3));
    assertEquals("/raw/evbLewgkDSVkifzv8zAo/", matcher.group(4));
  }

  @Test
  public void testWinPath() {

    Pattern patternWin = Pattern.compile("^" + winPath() + "$", Pattern.CASE_INSENSITIVE);
    Pattern patternUnix = Pattern.compile("^" + unixPath() + "$", Pattern.CASE_INSENSITIVE);

    assertTrue(patternWin.matches("C:\\a_data\\logs"));
    assertFalse(patternUnix.matches("C:\\a_data\\logs"));
  }

  @Test
  public void testUnixPath() {

    Pattern patternUnix = Pattern.compile("^" + unixPath() + "$", Pattern.CASE_INSENSITIVE);
    Pattern patternWin = Pattern.compile("^" + winPath() + "$", Pattern.CASE_INSENSITIVE);

    assertTrue(patternUnix.matches("/var/logs/tomcat8/catalina.out"));
    assertFalse(patternWin.matches("/var/logs/tomcat8/catalina.out"));
  }

  @Test
  public void testIpV4() {

    Pattern pattern = Pattern.compile("^" + ipv4() + "$", Pattern.CASE_INSENSITIVE);

    assertTrue(pattern.matches("1.0.1.0")); // China
    assertTrue(pattern.matches("8.8.8.8")); // Google DNS in USA
    assertTrue(pattern.matches("100.1.2.3")); // USA
    assertTrue(pattern.matches("172.15.1.2")); // USA
    assertTrue(pattern.matches("172.32.1.2")); // USA
    assertTrue(pattern.matches("192.167.1.2")); // Italy

    assertTrue(pattern.matches("10.1.2.3")); // 10.0.0.0/8 is considered private
    assertTrue(pattern.matches("127.0.0.1")); // localhost
    assertTrue(pattern.matches("172.16.1.2")); // 172.16.0.0/12 is considered private
    assertTrue(pattern.matches("172.31.1.2")); // same as previous, but near the end of that range
    assertTrue(pattern.matches("192.168.1.2")); // 192.168.0.0/16 is considered private

    // TODO : fix regex to disable these patterns?
    assertTrue(pattern.matches("0.1.2.3")); // 0.0.0.0/8 is reserved for some broadcasts
    assertTrue(pattern.matches("255.255.255.255")); // reserved broadcast is not an IP

    assertFalse(pattern.matches(".2.3.4"));
    assertFalse(pattern.matches("1.2.3."));
    assertFalse(pattern.matches("1.2.3.256"));
    assertFalse(pattern.matches("1.2.256.4"));
    assertFalse(pattern.matches("1.256.3.4"));
    assertFalse(pattern.matches("256.2.3.4"));
    assertFalse(pattern.matches("1.2.3.4.5"));
    assertFalse(pattern.matches("1..3.4"));
  }

  @Test
  public void testIpLocal() {

    Pattern pattern = Pattern.compile("^" + iplocal() + "$", Pattern.CASE_INSENSITIVE);

    assertFalse(pattern.matches("1.0.1.0")); // China
    assertFalse(pattern.matches("8.8.8.8")); // Google DNS in USA
    assertFalse(pattern.matches("100.1.2.3")); // USA
    assertFalse(pattern.matches("172.15.1.2")); // USA
    assertFalse(pattern.matches("172.32.1.2")); // USA
    assertFalse(pattern.matches("192.167.1.2")); // Italy

    assertTrue(pattern.matches("10.1.2.3")); // 10.0.0.0/8 is considered private
    assertTrue(pattern.matches("127.0.0.1")); // localhost
    assertTrue(pattern.matches("172.16.1.2")); // 172.16.0.0/12 is considered private
    assertTrue(pattern.matches("172.31.1.2")); // same as previous, but near the end of that range
    assertTrue(pattern.matches("192.168.1.2")); // 192.168.0.0/16 is considered private

    assertFalse(pattern.matches("0.1.2.3")); // 0.0.0.0/8 is reserved for some broadcasts
    assertFalse(pattern.matches("255.255.255.255")); // reserved broadcast is not an IP

    assertFalse(pattern.matches(".2.3.4"));
    assertFalse(pattern.matches("1.2.3."));
    assertFalse(pattern.matches("1.2.3.256"));
    assertFalse(pattern.matches("1.2.256.4"));
    assertFalse(pattern.matches("1.256.3.4"));
    assertFalse(pattern.matches("256.2.3.4"));
    assertFalse(pattern.matches("1.2.3.4.5"));
    assertFalse(pattern.matches("1..3.4"));
  }

  @Test
  public void testIpV6() {

    Pattern pattern = Pattern.compile("^" + ipv6() + "$", Pattern.CASE_INSENSITIVE);

    assertTrue(pattern.matches("2002:4559:1FE2::4559:1FE2"));
    assertTrue(pattern.matches("1080:0:0:0:8:800:200C:417A"));
    assertTrue(pattern.matches("1080::8:800:200C:417A"));
    assertTrue(pattern.matches("2001:db8:3333:4444:5555:6666:7777:8888"));
    assertTrue(pattern.matches("2001:db8:3333:4444:CCCC:DDDD:EEEE:FFFF"));
    assertTrue(pattern.matches("::")); // implies all 8 segments are zero
    assertTrue(pattern.matches("2001:db8::")); // implies that the last six segments are zero
    assertTrue(pattern.matches("::1234:5678")); // implies that the first six segments are zero
    assertTrue(pattern.matches("2001:db8::1234:5678")); // implies that the middle four segments are
                                                        // zero
    assertTrue(pattern.matches("2001:0db8:0001:0000:0000:0ab9:C0A8:0102"));
    assertTrue(pattern.matches("2001:db8:1::ab9:C0A8:102")); // same as above but compressed to
                                                             // eliminate leading zeros
    assertTrue(pattern.matches("::1")); // localhost
    assertTrue(pattern.matches("fe80::")); // link-local prefix
    assertTrue(pattern.matches("2001::")); // global unicast prefix

    assertFalse(pattern.matches("|0000:|5678::"));
    assertFalse(pattern.matches("1200::AB00:1234::2552:7777:1313")); // can only use :: once in an
                                                                     // address
    assertFalse(pattern.matches("1200:0000:AB00:1234:O000:2552:7777:1313")); // invalid character in
                                                                             // field of what looks
                                                                             // to be all 0's
  }

  @Test
  public void testMacAddress() {

    Pattern pattern = Pattern.compile("^" + macAddress() + "$", Pattern.CASE_INSENSITIVE);

    assertTrue(pattern.matches("00:0a:95:9d:68:16"));
  }

  @Test
  public void testDate() {

    Pattern pattern = Pattern.compile("^" + date() + "$", Pattern.CASE_INSENSITIVE);

    assertTrue(pattern.matches("15/09/1984"));
    assertTrue(pattern.matches("15.09.1984"));
    assertTrue(pattern.matches("1984-09-15"));
    assertTrue(pattern.matches("15/9/1984"));
    assertTrue(pattern.matches("15.9.1984"));
    assertTrue(pattern.matches("1984-9-15"));
  }

  @Test
  public void testTime() {

    Pattern pattern = Pattern.compile("^" + time() + "$", Pattern.CASE_INSENSITIVE);

    assertTrue(pattern.matches("15:30:59"));
    assertTrue(pattern.matches("15h30"));
  }

  @Test
  public void testDateTime() {

    Pattern pattern = Pattern.compile("^" + dateTime() + "$", Pattern.CASE_INSENSITIVE);

    assertTrue(pattern.matches("2017-07-11 16:28:55"));
  }

  @Test
  public void testTimestamp() {

    Pattern pattern = Pattern.compile("^" + dateTime() + "$", Pattern.CASE_INSENSITIVE);

    assertTrue(pattern.matches("2017-10-30T16:18:00"));
  }
}
