package com.computablefacts.nona.functions.controlflowoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class WhichTest {

  @Test
  public void testWhich() {

    Map<String, Function> definitions = Function.definitions();
    definitions.put("WHICH_X",
        new Function("WHICH_X(x) := WHICH(LT(x, 0), negative, GT(x, 0), positive, EQUAL(x, 0), zero)"));

    Function fn = new Function("WHICH_X(5)");

    Assert.assertEquals(BoxedType.of("positive"), fn.evaluate(definitions));
  }

  @Test
  public void testWhichWithDefaultStatement() {

    Map<String, Function> definitions = Function.definitions();
    definitions.put("WHICH_X", new Function("WHICH_X(x) := WHICH(LT(x, 0), negative, GT(x, 0), positive, _, zero)"));

    Function fn = new Function("WHICH_X(0)");

    Assert.assertEquals(BoxedType.of("zero"), fn.evaluate(definitions));
  }

  @Test(expected = IllegalStateException.class)
  public void testWhichWithMissingStatement() {

    Map<String, Function> definitions = Function.definitions();
    definitions.put("WHICH_X", new Function("WHICH_X(x) := WHICH(LT(x, 0), negative, GT(x, 0), positive)"));

    Function fn = new Function("WHICH_X(0)");
    BoxedType result = fn.evaluate(definitions);
  }
}
