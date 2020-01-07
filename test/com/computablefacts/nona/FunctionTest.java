package com.computablefacts.nona;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.types.BoxedType;
import com.google.common.util.concurrent.UncheckedExecutionException;

public class FunctionTest {

  @Test
  public void testWrap() {
    Assert.assertEquals("_(\\u0028,\\u0029)", Function.wrap("(,)"));
    Assert.assertEquals("_(\\u0028,\\u0029)", Function.wrap("\\u0028,\\u0029"));
  }

  @Test
  public void testEncode() {
    Assert.assertEquals("\\u0028,\\u0029", Function.encode("(,)"));
  }

  @Test
  public void testDecode() {
    Assert.assertEquals("(,)", Function.decode("\\u0028,\\u0029"));
  }

  @Test
  public void testEvaluateFunctionWithNoParameter1() {

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
  public void testEvaluateFunctionWithNoParameter2() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("ZERO", new Function("ZERO", true) {

      @Override
      protected BoxedType evaluate(List<BoxedType> parameters) {
        return BoxedType.create(parameters.isEmpty() ? 0 : 1);
      }
    });

    Function fn = new Function("ZERO");
    Assert.assertEquals(BoxedType.create(0), fn.evaluate(functions));
  }

  @Test
  public void testEvaluateFunctionWithoutDefinition() {
    Function fn = new Function("NO_DEFINITION");
    Assert.assertEquals(BoxedType.create("NO_DEFINITION"), fn.evaluate());
  }

  @Test
  public void testIsValid() {
    Function fn = new Function("test");
    Assert.assertTrue(fn.isValid());
  }

  @Test
  public void testParseFunctionWithIntegerParameter() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("FN", new Function("FN", true) {

      @Override
      protected BoxedType evaluate(List<BoxedType> parameters) {
        return parameters.get(0);
      }
    });

    Function fn = new Function("FN(666)");
    Assert.assertEquals(BoxedType.create(666), fn.evaluate(functions));
  }

  @Test
  public void testParseFunctionWithDoubleParameter() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("FN", new Function("FN", true) {

      @Override
      protected BoxedType evaluate(List<BoxedType> parameters) {
        return parameters.get(0);
      }
    });

    Function fn = new Function("FN(6.66)");
    Assert.assertEquals(BoxedType.create(6.66), fn.evaluate(functions));
  }

  @Test
  public void testParseFunctionWithStringParameter() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("FN", new Function("FN", true) {

      @Override
      protected BoxedType evaluate(List<BoxedType> parameters) {
        return parameters.get(0);
      }
    });

    Function fn = new Function("FN(string)");
    Assert.assertEquals(BoxedType.create("string"), fn.evaluate(functions));
  }

  @Test
  public void testParseFunctionWithDoubleQuotedStringParameter() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("FN", new Function("FN", true) {

      @Override
      protected BoxedType evaluate(List<BoxedType> parameters) {
        return parameters.get(0);
      }
    });

    Function fn = new Function("FN(\"string\")");
    Assert.assertEquals(BoxedType.create("\"string\""), fn.evaluate(functions));
  }

  @Test
  public void testParseFunctionWithParenthesisedStringParameter() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("FN", new Function("FN", true) {

      @Override
      protected BoxedType evaluate(List<BoxedType> parameters) {
        return parameters.get(0);
      }
    });

    Function fn = new Function("FN(" + Function.wrap("str()ing") + ")");
    Assert.assertEquals(BoxedType.create("str()ing"), fn.evaluate(functions));
  }

  @Test
  public void testParseFunctionWithACommaInStringParameter() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("FN", new Function("FN", true) {

      @Override
      protected BoxedType evaluate(List<BoxedType> parameters) {
        return parameters.get(0);
      }
    });

    Function fn = new Function("FN(" + Function.wrap("str,ing") + ")");
    Assert.assertEquals(BoxedType.create("str,ing"), fn.evaluate(functions));
  }

  @Test(expected = UncheckedExecutionException.class) // TODO : bugfix
  public void testParseFunctionWithASingleDoubleQuoteInStringParameter() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("FN", new Function("FN", true) {

      @Override
      protected BoxedType evaluate(List<BoxedType> parameters) {
        return parameters.get(0);
      }
    });

    Function fn = new Function("FN(_(str\"ing))");
    Assert.assertEquals(BoxedType.create("str\"ing"), fn.evaluate(functions));
  }

  @Test
  public void testParseFunctionWithASingleBeginningParenthesisInStringParameter() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("FN", new Function("FN", true) {

      @Override
      protected BoxedType evaluate(List<BoxedType> parameters) {
        return parameters.get(0);
      }
    });

    Function fn = new Function("FN(" + Function.wrap("str(ing") + ")");
    Assert.assertEquals(BoxedType.create("str(ing"), fn.evaluate(functions));
  }

  @Test
  public void testParseFunctionWithASingleEndingParenthesisInStringParameter() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("FN", new Function("FN", true) {

      @Override
      protected BoxedType evaluate(List<BoxedType> parameters) {
        return parameters.get(0);
      }
    });

    Function fn = new Function("FN(" + Function.wrap("str)ing") + ")");
    Assert.assertEquals(BoxedType.create("str)ing"), fn.evaluate(functions));
  }
}
