package com.computablefacts.nona.functions.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;

public class SnippetExtractorTest {

  @Test
  public void testLocationEqualsNull() {

    SnippetExtractor.Location location = new SnippetExtractor.Location("word", 0);

    Assert.assertNotEquals(location, null);
  }

  @Test
  public void testLocationEquals() {

    SnippetExtractor.Location location1 = new SnippetExtractor.Location("word", 0);
    SnippetExtractor.Location location2 = new SnippetExtractor.Location("word", 0);

    Assert.assertEquals(location1, location1);
    Assert.assertEquals(location2, location2);

    Assert.assertEquals(location1, location2);
    Assert.assertEquals(location2, location1);
  }

  @Test
  public void testLocationHashcode() {

    SnippetExtractor.Location location1 = new SnippetExtractor.Location("word", 0);
    SnippetExtractor.Location location2 = new SnippetExtractor.Location("word", 0);

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
  public void testDoNotTruncateHead() {
    String snippet =
        SnippetExtractor.extract(Lists.newArrayList("gmail"), "zor@gmail.com", 5, 0, "...");
    assertEquals("...gmail...", snippet);
  }

  @Test
  public void testDoNotTruncateTail() {
    String snippet =
        SnippetExtractor.extract(Lists.newArrayList("zor"), "zor@gmail.com", 3, 50, "...");
    assertEquals("zor@gmail.com", snippet);
  }

  private String text() {
    return "Welcome to Yahoo!, the world’s most visited home page. Quickly find what you’re searching for, get in touch with friends and stay in-the-know with the latest news and information. CloudSponge provides an interface to easily enable your users to import contacts from a variety of the most popular webmail services including Yahoo, Gmail and Hotmail/MSN as well as popular desktop address books such as Mac Address Book and Outlook.";
  }
}
