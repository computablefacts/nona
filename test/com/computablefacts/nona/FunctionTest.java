package com.computablefacts.nona;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.types.BoxedType;

public class FunctionTest {

  @Test
  public void testFunctionWithNoParameter() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("ZERO", new Function("ZERO", true) {

      @Override
      protected BoxedType evaluate(List<BoxedType> parameters) {
        return BoxedType.create(parameters.isEmpty() ? 0 : 1);
      }
    });

    Function fn = new Function("ZERO()");
    Assert.assertEquals(BoxedType.create(0), fn.evaluate(functions));
  }

  @Test
  public void testIsValidForLiterals() {
    Function fn = new Function("test");
    Assert.assertTrue(fn.isValid());
  }

  @Test
  public void testEvaluateForLiterals() {
    Function fn = new Function("test");
    Assert.assertEquals(BoxedType.create("test"), fn.evaluate());
  }
}
