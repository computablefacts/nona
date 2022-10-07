package com.computablefacts.nona.functions.stringoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import org.junit.Assert;
import org.junit.Test;

public class SnippetTest {

  @Test
  public void testExtractSnippetWithOneMatch() {

    Function fn = new Function("SNIPPET(" + Function.wrap(text()) + ", Welcome)");
    Assert.assertEquals(BoxedType.of(
            "Welcome to Yahoo!, the world’s most visited home page. Quickly find what you’re searching for, get in touch with friends and stay in-the-know with the latest news and information. CloudSponge provides an interface to easily enable your users to import contacts from a variety of the most popular webmail..."),
        fn.evaluate(Function.definitions()));
  }

  @Test
  public void testExtractSnippetWithMoreThanOneMatch() {

    Function fn = new Function("SNIPPET(" + Function.wrap(text()) + ", Yahoo, Outlook)");
    Assert.assertEquals(BoxedType.of(
            "...in-the-know with the latest news and information. CloudSponge provides an interface to easily enable your users to import contacts from a variety of the most popular webmail services including Yahoo, Gmail and Hotmail/MSN as well as popular desktop address books such as Mac Address Book and Outlook."),
        fn.evaluate(Function.definitions()));
  }

  @Test
  public void testExtractSnippetWithNoMatch() {

    Function fn = new Function("SNIPPET(" + Function.wrap(text()) + ", john doe)");
    Assert.assertEquals(BoxedType.of(
            "Welcome to Yahoo!, the world’s most visited home page. Quickly find what you’re searching for, get in touch with friends and stay in-the-know with the latest news and information. CloudSponge provides an interface to easily enable your users to import contacts from a variety of the most popular webmail..."),
        fn.evaluate(Function.definitions()));
  }

  private String text() {
    return "Welcome to Yahoo!, the world’s most visited home page. Quickly find what you’re searching for, get in touch with friends and stay in-the-know with the latest news and information. CloudSponge provides an interface to easily enable your users to import contacts from a variety of the most popular webmail services including Yahoo, Gmail and Hotmail/MSN as well as popular desktop address books such as Mac Address Book and Outlook.";
  }
}
