package com.computablefacts.nona.helpers;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.types.Span;
import com.google.common.collect.Lists;

@Deprecated
public class SnippetExtractorTest {

  @Test
  public void testLocationEqualsNull() {

    Span location = new Span("word", 0, "word".length());

    Assert.assertNotEquals(location, null);
  }

  @Test
  public void testLocationEquals() {

    Span location1 = new Span("word", 0, "word".length());
    Span location2 = new Span("word", 0, "word".length());

    Assert.assertEquals(location1, location1);
    Assert.assertEquals(location2, location2);

    Assert.assertEquals(location1, location2);
    Assert.assertEquals(location2, location1);
  }

  @Test
  public void testLocationHashcode() {

    Span location1 = new Span("word", 0, "word".length());
    Span location2 = new Span("word", 0, "word".length());

    Assert.assertEquals(location1.hashCode(), location2.hashCode());
  }

  @Test
  public void testSearchedWordsNotInText() {
    String snippet = SnippetExtractor.extract(Lists.newArrayList("not_here"), "Hello world!");
    assertEquals("Hello world!", snippet);
  }

  @Test
  public void testTextShorterThanSnippetMaxLength() {
    String snippet = SnippetExtractor.extract(Lists.newArrayList("world"), "Hello world!");
    assertEquals("Hello world!", snippet);
  }

  @Test
  public void testSnippetWithLeftEllipsis() {
    String snippet = SnippetExtractor.extract(Lists.newArrayList("Yahoo", "Outlook"), text());
    assertEquals(
        "...in-the-know with the latest news and information. CloudSponge provides an interface to easily enable your users to import contacts from a variety of the most popular webmail services including Yahoo, Gmail and Hotmail/MSN as well as popular desktop address books such as Mac Address Book and Outlook.",
        snippet);
  }

  @Test
  public void testSnippetWithoutLeftEllipsis() {
    String snippet =
        SnippetExtractor.extract(Lists.newArrayList("Yahoo", "Outlook"), text(), 300, 50, "");
    assertEquals(
        "in-the-know with the latest news and information. CloudSponge provides an interface to easily enable your users to import contacts from a variety of the most popular webmail services including Yahoo, Gmail and Hotmail/MSN as well as popular desktop address books such as Mac Address Book and Outlook.",
        snippet);
  }

  @Test
  public void testSnippetWithRightEllipsis() {
    String snippet =
        SnippetExtractor.extract(Lists.newArrayList("most", "visited", "home", "page"), text());
    assertEquals(
        "Welcome to Yahoo!, the world’s most visited home page. Quickly find what you’re searching for, get in touch with friends and stay in-the-know with the latest news and information. CloudSponge provides an interface to easily enable your users to import contacts from a variety of the most popular webmail...",
        snippet);
  }

  @Test
  public void testSnippetWithLeftAndRightEllipsis() {
    String snippet =
        SnippetExtractor.extract(Lists.newArrayList("latest", "news", "CloudSponge"), text());
    assertEquals(
        "...touch with friends and stay in-the-know with the latest news and information. CloudSponge provides an interface to easily enable your users to import contacts from a variety of the most popular webmail services including Yahoo, Gmail and Hotmail/MSN as well as popular desktop address books such as Mac...",
        snippet);
  }

  @Test
  public void testSnippetWithEmptyIndicator() {
    String snippet = SnippetExtractor.extract(Lists.newArrayList("latest", "news", "CloudSponge"),
        text(), 300, 50, "");
    assertEquals(
        "touch with friends and stay in-the-know with the latest news and information. CloudSponge provides an interface to easily enable your users to import contacts from a variety of the most popular webmail services including Yahoo, Gmail and Hotmail/MSN as well as popular desktop address books such as Mac",
        snippet);
  }

  @Test
  public void testSnippetWithNullIndicator() {
    String snippet = SnippetExtractor.extract(Lists.newArrayList("latest", "news", "CloudSponge"),
        text(), 300, 50, null);
    assertEquals(
        "...touch with friends and stay in-the-know with the latest news and information. CloudSponge provides an interface to easily enable your users to import contacts from a variety of the most popular webmail services including Yahoo, Gmail and Hotmail/MSN as well as popular desktop address books such as Mac...",
        snippet);
  }

  @Test
  public void testDoNotTruncateHead() {
    String snippet =
        SnippetExtractor.extract(Lists.newArrayList("gmail"), "zor@gmail.com", 5, 0, "...");
    assertEquals("...gmail...", snippet);
  }

  @Test
  public void testDoNotTruncateHeadWithNullIndicator() {
    String snippet =
        SnippetExtractor.extract(Lists.newArrayList("gmail"), "zor@gmail.com", 5, 0, null);
    assertEquals("...gmail...", snippet);
  }

  @Test
  public void testDoNotTruncateHeadWithEmptyIndicator() {
    String snippet =
        SnippetExtractor.extract(Lists.newArrayList("gmail"), "zor@gmail.com", 5, 0, "");
    assertEquals("gmail", snippet);
  }

  @Test
  public void testDoNotTruncateTail() {
    String snippet =
        SnippetExtractor.extract(Lists.newArrayList("zor"), "zor@gmail.com", 3, 50, "...");
    assertEquals("zor@gmail.com", snippet);
  }

  @Test
  public void testDoNotTruncateTailWithNullIndicator() {
    String snippet =
        SnippetExtractor.extract(Lists.newArrayList("zor"), "zor@gmail.com", 3, 50, null);
    assertEquals("zor@gmail.com", snippet);
  }

  @Test
  public void testDoNotTruncateTailWithEmptyIndicator() {
    String snippet =
        SnippetExtractor.extract(Lists.newArrayList("zor"), "zor@gmail.com", 3, 50, "");
    assertEquals("zor@gmail.com", snippet);
  }

  private String text() {
    return "Welcome to Yahoo!, the world’s most visited home page. Quickly find what you’re searching for, get in touch with friends and stay in-the-know with the latest news and information. CloudSponge provides an interface to easily enable your users to import contacts from a variety of the most popular webmail services including Yahoo, Gmail and Hotmail/MSN as well as popular desktop address books such as Mac Address Book and Outlook.";
  }
}
