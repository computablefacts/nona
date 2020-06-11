package com.computablefacts.nona.functions.stringoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class ExtractSnippetTest {

  @Test
  public void testExtractSnippetWithOneMatch() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("ESNIPPET", new ExtractSnippet());

    Function fn = new Function("ESNIPPET(" + Function.wrap(text()) + ", Welcome)");
    Assert.assertEquals(BoxedType.create(
        "Welcome to Yahoo!, the world’s most visited home page. Quickly find what you’re searching for, get in touch with friends and stay in-the-know with the latest news and information. CloudSponge provides an interface to easily enable your users to import contacts from a variety of the most popular webmail..."),
        fn.evaluate(functions));
  }

  @Test
  public void testExtractSnippetWithMoreThanOneMatch() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("ESNIPPET", new ExtractSnippet());

    Function fn = new Function("ESNIPPET(" + Function.wrap(text()) + ", Yahoo, Outlook)");
    Assert.assertEquals(BoxedType.create(
        "...in-the-know with the latest news and information. CloudSponge provides an interface to easily enable your users to import contacts from a variety of the most popular webmail services including Yahoo, Gmail and Hotmail/MSN as well as popular desktop address books such as Mac Address Book and Outlook."),
        fn.evaluate(functions));
  }

  @Test
  public void testExtractSnippetWithNoMatch() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("ESNIPPET", new ExtractSnippet());

    Function fn = new Function("ESNIPPET(" + Function.wrap(text()) + ", john doe)");
    Assert.assertEquals(BoxedType.create(
        "Welcome to Yahoo!, the world’s most visited home page. Quickly find what you’re searching for, get in touch with friends and stay in-the-know with the latest news and information. CloudSponge provides an interface to easily enable your users to import contacts from a variety of the most popular webmail..."),
        fn.evaluate(functions));
  }

  private String text() {
    return "Welcome to Yahoo!, the world’s most visited home page. Quickly find what you’re searching for, get in touch with friends and stay in-the-know with the latest news and information. CloudSponge provides an interface to easily enable your users to import contacts from a variety of the most popular webmail services including Yahoo, Gmail and Hotmail/MSN as well as popular desktop address books such as Mac Address Book and Outlook.";
  }
}
