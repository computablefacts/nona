package com.computablefacts.nona.functions.patternoperators;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;
import com.google.common.collect.Sets;

public class EmailTest {

  private static final Set<String> VALID_EMAILS =
      Sets.newHashSet("email@example.com", "firstname.lastname@example.com",
          "email@subdomain.example.com", "firstname+lastname@example.com", "email@123.123.123.123",
          "email@[123.123.123.123]", "\"email\"@example.com", "1234567890@example.com",
          "email@example-one.com", "_______@example.com", "email@example.name",
          "email@example.co.jp", "firstname-lastname@example.com");

  private static final Set<String> VALID_EMAILS_IN_TEXTS =
      Sets.newHashSet("Joe Smith <email@example.com>", "email@example.com (Joe Smith)");

  private static final Set<String> INVALID_EMAILS =
      Sets.newHashSet("plainaddress", "#@%^%#$@#$@#.com", "@example.com", "email.example.com",
          "email.@example.com", "あいうえお@example.com", "email@example", "email@example.web",
          "email@111.222.333.44444", "email@example..com");

  private static final Set<String> INVALID_EMAILS_IN_TEXTS =
      Sets.newHashSet("Joe Smith <.email@example.com>", "email..email@example.com (Joe Smith)",
          "Joe Smith <Abc..123@example.com>", "email@example@example.com (Joe Smith)");

  @Test
  public void testValidEmails() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("EMAIL", new Email());

    for (String email : VALID_EMAILS) {

      Function fn = new Function("EMAIL(" + email + ")");
      SpanSequence spans = (SpanSequence) fn.evaluate(functions).value();
      Span span = spans.span(0);

      Assert.assertEquals(1, spans.size());
      Assert.assertEquals(email.toLowerCase(), span.text().toLowerCase());
      Assert.assertTrue(span.hasTag("EMAIL"));
      Assert.assertNotNull(span.getFeature("USERNAME"));
      Assert.assertNotNull(span.getFeature("DOMAIN"));
    }
  }

  @Test
  public void testValidEmailsInTexts() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("EEMAIL", new Email());

    for (String email : VALID_EMAILS_IN_TEXTS) {

      Function fn = new Function("EEMAIL(" + email + ")");
      SpanSequence spans = (SpanSequence) fn.evaluate(functions).value();
      Span span = spans.span(0);

      Assert.assertEquals(1, spans.size());
      Assert.assertTrue(span.hasTag("EMAIL"));
      Assert.assertEquals("email@example.com", span.text().toLowerCase());
      Assert.assertEquals("email", span.getFeature("USERNAME"));
      Assert.assertEquals("example.com", span.getFeature("DOMAIN"));
      Assert.assertEquals("COM", span.getFeature("TLD"));
    }
  }

  @Test
  public void testInvalidEmails() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("EMAIL", new Email());

    for (String email : INVALID_EMAILS) {

      Function fn = new Function("EMAIL(" + email + ")");
      SpanSequence spans = (SpanSequence) fn.evaluate(functions).value();

      Assert.assertEquals(0, spans.size());
    }
  }

  @Test
  public void testInvalidEmailsInTexts() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("EMAIL", new Email());

    for (String email : INVALID_EMAILS_IN_TEXTS) {

      Function fn = new Function("EMAIL(" + email + ")");
      SpanSequence spans = (SpanSequence) fn.evaluate(functions).value();

      Assert.assertEquals(1, spans.size());
    }
  }
}
