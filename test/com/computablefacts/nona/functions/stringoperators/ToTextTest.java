package com.computablefacts.nona.functions.stringoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class ToTextTest {

  @Test
  public void testStringToText() {

    Function fn = new Function("TO_TEXT(\"text\")");
    Assert.assertEquals(BoxedType.create("text"), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIntegerToText() {

    Function fn = new Function("TO_TEXT(2018)");
    Assert.assertEquals(BoxedType.create("2018"), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testDecimalToText() {

    Function fn = new Function("TO_TEXT(2018.123)");
    Assert.assertEquals(BoxedType.create("2018.123"), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testCsvToText() {

    Function fn = new Function("TO_TEXT(\"col_1,col_2,col_3\\n11,12,13\\n21,22\")");
    Assert.assertEquals(BoxedType.create("\"col_1,col_2,col_3\\n11,12,13\\n21,22\""),
        fn.evaluate(Function.definitions()));
  }

  @Test
  public void testJsonToText() {

    Function fn = new Function(
        "TO_TEXT({\"birthdate\":\"2004-04-01T00:00:00Z\",\"last_name\":\"doe\",\"id\":1,\"first_name\":\"john\",\"age\":17})");
    Assert.assertEquals(BoxedType.create(
        "{\"birthdate\":\"2004-04-01T00:00:00Z\",\"last_name\":\"doe\",\"id\":1,\"first_name\":\"john\",\"age\":17}"),
        fn.evaluate(Function.definitions()));
  }
}
