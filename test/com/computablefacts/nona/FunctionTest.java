package com.computablefacts.nona;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.functions.additiveoperators.Add;
import com.computablefacts.nona.functions.multiplicativeoperators.Divide;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.collect.Lists;

import nl.jqno.equalsverifier.EqualsVerifier;

public class FunctionTest {

  @Test
  public void testWrap() {
    Assert.assertEquals("_(\\u0028\\u002c\\u0029)", Function.wrap("(,)"));
    Assert.assertEquals("_(\\u0028\\u002c\\u0029)", Function.wrap("\\u0028\\u002c\\u0029"));
    Assert.assertEquals("_(\\u0022\\u002c\\u0022)", Function.wrap("\",\""));
  }

  @Test
  public void testEncode() {
    Assert.assertEquals("\\u0028\\u002c\\u0029", Function.encode("(,)"));
    Assert.assertEquals("\\u0022\\u002c\\u0022", Function.encode("\",\""));
  }

  @Test
  public void testDecode() {
    Assert.assertEquals("(,)", Function.decode("\\u0028\\u002c\\u0029"));
    Assert.assertEquals("\",\"", Function.decode("\\u0022\\u002c\\u0022"));
  }

  @Test
  public void testDoNotDecode() {

    Assert.assertEquals("\\u1000", Function.decode("\\u1000"));
    Assert.assertEquals("\\u2000", Function.decode("\\u2000"));
    Assert.assertEquals("\\u3000", Function.decode("\\u3000"));
    Assert.assertEquals("\\u4000", Function.decode("\\u4000"));
    Assert.assertEquals("\\u5000", Function.decode("\\u5000"));
    Assert.assertEquals("\\u6000", Function.decode("\\u6000"));
    Assert.assertEquals("\\u7000", Function.decode("\\u7000"));
    Assert.assertEquals("\\u8000", Function.decode("\\u8000"));
    Assert.assertEquals("\\u9000", Function.decode("\\u9000"));

    Assert.assertEquals("\\u0100", Function.decode("\\u0100"));
    Assert.assertEquals("\\u0200", Function.decode("\\u0200"));
    Assert.assertEquals("\\u0300", Function.decode("\\u0300"));
    Assert.assertEquals("\\u0400", Function.decode("\\u0400"));
    Assert.assertEquals("\\u0500", Function.decode("\\u0500"));
    Assert.assertEquals("\\u0600", Function.decode("\\u0600"));
    Assert.assertEquals("\\u0700", Function.decode("\\u0700"));
    Assert.assertEquals("\\u0800", Function.decode("\\u0800"));
    Assert.assertEquals("\\u0900", Function.decode("\\u0900"));

    Assert.assertEquals("\\u0010", Function.decode("\\u0010"));
    Assert.assertEquals("\\u0040", Function.decode("\\u0040"));
    Assert.assertEquals("\\u0050", Function.decode("\\u0050"));
    Assert.assertEquals("\\u0060", Function.decode("\\u0060"));
    Assert.assertEquals("\\u0070", Function.decode("\\u0070"));
    Assert.assertEquals("\\u0080", Function.decode("\\u0080"));
    Assert.assertEquals("\\u0090", Function.decode("\\u0090"));

    Assert.assertEquals("\\u0000", Function.decode("\\u0000"));
    Assert.assertEquals("\\u0020", Function.decode("\\u0020"));
    Assert.assertEquals("\\u0030", Function.decode("\\u0030"));
  }

  @Test
  public void testEncodeLineFeedAndCarriageReturn() {
    Assert.assertEquals("\\u000d", Function.encode("\n"));
    Assert.assertEquals("\\u000a", Function.encode("\r"));
    Assert.assertEquals("\\u000d\\u000a", Function.encode("\n\r"));
    Assert.assertEquals("\\u000a\\u000d", Function.encode("\r\n"));
  }

  @Test
  public void testDecodeLineFeedAndCarriageReturn() {
    Assert.assertEquals("\n", Function.decode("\\u000d"));
    Assert.assertEquals("\r", Function.decode("\\u000a"));
    Assert.assertEquals("\n\r", Function.decode("\\u000d\\u000a"));
    Assert.assertEquals("\r\n", Function.decode("\\u000a\\u000d"));
  }

  @Test
  public void testEvaluateFunctionWithNoParameter1() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("ZERO", new Function(eCategory.ASSIGNMENT_OPERATORS, "ZERO", "For tests only.") {

      @Override
      public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {
        return BoxedType.create(parameters.isEmpty() ? 0 : 1);
      }
    });

    Function fn = new Function("ZERO()");

    Assert.assertEquals("ZERO", fn.name());
    Assert.assertEquals(0, fn.arity());
    Assert.assertEquals(BoxedType.create(0), fn.evaluate(functions));
  }

  @Test
  public void testEvaluateFunctionWithNoParameter2() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("ZERO", new Function(eCategory.ASSIGNMENT_OPERATORS, "ZERO", "For tests only.") {

      @Override
      public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {
        return BoxedType.create(parameters.isEmpty() ? 0 : 1);
      }
    });

    Function fn = new Function("ZERO");

    Assert.assertEquals("ZERO", fn.name());
    Assert.assertEquals(0, fn.arity());
    Assert.assertEquals(BoxedType.create(0), fn.evaluate(functions));
  }

  @Test
  public void testEvaluateFunctionWithoutDefinition() {
    Function fn = new Function("FN");
    Assert.assertEquals(BoxedType.create("FN"), fn.evaluate());
  }

  @Test
  public void testIsValid() {
    Function fn = new Function("test");
    Assert.assertEquals(0, fn.arity());
    Assert.assertTrue(fn.isValid());
  }

  @Test(expected = IllegalStateException.class)
  public void testFunctionWithoutParametersAndName() {
    Function fn = new Function("()");
    Assert.assertFalse(fn.isValid());
  }

  @Test(expected = IllegalStateException.class)
  public void testFunctionWithOneParameterButNoName() {
    Function fn = new Function("(anonymous)");
    Assert.assertFalse(fn.isValid());
  }

  @Test
  public void testFunctionWithoutParameters() {

    Function fn = new Function("FN()");

    Assert.assertEquals("FN", fn.name());
    Assert.assertEquals(0, fn.arity());
    Assert.assertTrue(fn.isValid());
  }

  @Test
  public void testFunctionWithTwoEmptyParameters() {

    Function fn = new Function("FN(_(), _())");

    Assert.assertEquals("FN", fn.name());
    Assert.assertEquals(2, fn.arity());
    Assert.assertTrue(fn.isValid());
  }

  @Test
  public void testFunctionWithTwoEmptyParameters2() {

    Function fn = new Function("FN(_(), )");

    Assert.assertEquals("FN", fn.name());
    Assert.assertEquals(2, fn.arity());
    Assert.assertTrue(fn.isValid());
  }

  @Test
  public void testFunctionWithTwoEmptyParameters3() {

    Function fn = new Function("FN(, _())");

    Assert.assertEquals("FN", fn.name());
    Assert.assertEquals(2, fn.arity());
    Assert.assertTrue(fn.isValid());
  }

  @Test
  public void testFunctionWithTwoEmptyParameters4() {

    Function fn = new Function("FN(, )");

    Assert.assertEquals("FN", fn.name());
    Assert.assertEquals(2, fn.arity());
    Assert.assertTrue(fn.isValid());
  }

  @Test
  public void testEmptyStringAsFunction() {

    Function fn = new Function("");

    Assert.assertEquals("", fn.name());
    Assert.assertEquals(0, fn.arity());
    Assert.assertTrue(fn.isValid());
  }

  @Test
  public void testEmptyStringAsFunction2() {

    Function fn = new Function("_()");

    Assert.assertEquals("", fn.name());
    Assert.assertEquals(0, fn.arity());
    Assert.assertTrue(fn.isValid());
  }

  @Test
  public void testParseFunctionWithIntegerParameter() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("FN", new Function(eCategory.ASSIGNMENT_OPERATORS, "FN", "For tests only.") {

      @Override
      public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {
        return parameters.get(0);
      }
    });

    Function fn = new Function("FN(666)");

    Assert.assertEquals("FN", fn.name());
    Assert.assertEquals(1, fn.arity());
    Assert.assertEquals(BoxedType.create(666), fn.evaluate(functions));
  }

  @Test
  public void testParseFunctionWithDoubleParameter() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("FN", new Function(eCategory.ASSIGNMENT_OPERATORS, "FN", "For tests only.") {

      @Override
      public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {
        return parameters.get(0);
      }
    });

    Function fn = new Function("FN(6.66)");

    Assert.assertEquals("FN", fn.name());
    Assert.assertEquals(1, fn.arity());
    Assert.assertEquals(BoxedType.create(6.66), fn.evaluate(functions));
  }

  @Test
  public void testParseFunctionWithStringParameter() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("FN", new Function(eCategory.ASSIGNMENT_OPERATORS, "FN", "For tests only.") {

      @Override
      public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {
        return parameters.get(0);
      }
    });

    Function fn = new Function("FN(string)");

    Assert.assertEquals("FN", fn.name());
    Assert.assertEquals(1, fn.arity());
    Assert.assertEquals(BoxedType.create("string"), fn.evaluate(functions));
  }

  @Test
  public void testParseFunctionWithDoubleQuotedStringParameter() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("FN", new Function(eCategory.ASSIGNMENT_OPERATORS, "FN", "For tests only.") {

      @Override
      public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {
        return parameters.get(0);
      }
    });

    Function fn = new Function("FN(\"string\")");

    Assert.assertEquals("FN", fn.name());
    Assert.assertEquals(1, fn.arity());
    Assert.assertEquals(BoxedType.create("\"string\""), fn.evaluate(functions));
  }

  @Test
  public void testParseFunctionWithParenthesisedStringParameter() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("FN", new Function(eCategory.ASSIGNMENT_OPERATORS, "FN", "For tests only.") {

      @Override
      public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {
        return parameters.get(0);
      }
    });

    Function fn = new Function("FN(" + Function.wrap("str()ing") + ")");

    Assert.assertEquals("FN", fn.name());
    Assert.assertEquals(1, fn.arity());
    Assert.assertEquals(BoxedType.create("str()ing"), fn.evaluate(functions));
  }

  @Test
  public void testParseFunctionWithACommaInStringParameter() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("FN", new Function(eCategory.ASSIGNMENT_OPERATORS, "FN", "For tests only.") {

      @Override
      public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {
        return parameters.get(0);
      }
    });

    Function fn = new Function("FN(" + Function.wrap("str,ing") + ")");

    Assert.assertEquals("FN", fn.name());
    Assert.assertEquals(1, fn.arity());
    Assert.assertEquals(BoxedType.create("str,ing"), fn.evaluate(functions));
  }

  @Test
  public void testParseFunctionWithASingleDoubleQuoteInStringParameter() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("FN", new Function(eCategory.ASSIGNMENT_OPERATORS, "FN", "For tests only.") {

      @Override
      public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {
        return parameters.get(0);
      }
    });

    Function fn = new Function("FN(" + Function.wrap("str\"ing") + ")");

    Assert.assertEquals("FN", fn.name());
    Assert.assertEquals(1, fn.arity());
    Assert.assertEquals(BoxedType.create("str\"ing"), fn.evaluate(functions));
  }

  @Test
  public void testParseFunctionWithASingleBeginningParenthesisInStringParameter() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("FN", new Function(eCategory.ASSIGNMENT_OPERATORS, "FN", "For tests only.") {

      @Override
      public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {
        return parameters.get(0);
      }
    });

    Function fn = new Function("FN(" + Function.wrap("str(ing") + ")");

    Assert.assertEquals("FN", fn.name());
    Assert.assertEquals(1, fn.arity());
    Assert.assertEquals(BoxedType.create("str(ing"), fn.evaluate(functions));
  }

  @Test
  public void testParseFunctionWithASingleEndingParenthesisInStringParameter() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("FN", new Function(eCategory.ASSIGNMENT_OPERATORS, "FN", "For tests only.") {

      @Override
      public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {
        return parameters.get(0);
      }
    });

    Function fn = new Function("FN(" + Function.wrap("str)ing") + ")");

    Assert.assertEquals("FN", fn.name());
    Assert.assertEquals(1, fn.arity());
    Assert.assertEquals(BoxedType.create("str)ing"), fn.evaluate(functions));
  }

  @Test(expected = RuntimeException.class)
  public void testDirectCallToEvaluate() {
    BoxedType<?> bt = new Function(eCategory.ASSIGNMENT_OPERATORS, "FN", "For tests only.")
        .evaluate(new ArrayList<>());
  }

  @Test
  public void testHasReferenceTo() {

    Function fn = new Function("DIV(SUM(1, 2, 3, 4), 4)");

    Assert.assertTrue(fn.hasReferenceTo("DIV"));
    Assert.assertTrue(fn.hasReferenceTo("SUM"));
    Assert.assertFalse(fn.hasReferenceTo("SUB"));
    Assert.assertFalse(fn.hasReferenceTo("MUL"));
  }

  @Test
  public void testWithCache() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("DIV", new Divide() {

      @Override
      public boolean isCacheable() {
        return true;
      }
    });
    functions.put("SUM", new Add() {

      @Override
      public boolean isCacheable() {
        return true;
      }
    });

    Function fn = new Function("DIV(SUM(1, 2, 3, 4), 4)");

    Assert.assertEquals(BoxedType.create(2.5), fn.evaluate(functions));
  }

  @Test
  public void testWithoutCache() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("DIV", new Divide() {

      @Override
      public boolean isCacheable() {
        return false;
      }
    });
    functions.put("SUM", new Add() {

      @Override
      public boolean isCacheable() {
        return false;
      }
    });

    Function fn = new Function("DIV(SUM(1, 2, 3, 4), 4)");

    Assert.assertEquals(BoxedType.create(2.5), fn.evaluate(functions));
  }

  @Test
  public void testFunctionAliasingWithSingleParameter() {

    Map<String, Function> definitions = Function.definitions();
    definitions.put("IS_ODD", new Function("IS_ODD(x) := IF(EQUAL(MOD(x, 2), 0), false, true)"));
    definitions.put("IS_EVEN", new Function("IS_EVEN(x) := NOT(IS_ODD(x))"));

    Assert.assertFalse(new Function("IS_ODD(2)").evaluate(definitions).asBool());
    Assert.assertTrue(new Function("IS_EVEN(2)").evaluate(definitions).asBool());

    Assert.assertTrue(new Function("IS_ODD(1)").evaluate(definitions).asBool());
    Assert.assertFalse(new Function("IS_EVEN(1)").evaluate(definitions).asBool());
  }

  @Test
  public void testParseNotAFunctionAliasing() {
    Function function =
        new Function("CONCAT(\"LHS\", \"" + Function.encode(" := ") + "\", \"RHS\")");
    Assert.assertNotNull(function);
  }

  @Test
  public void testAtomEqualsAndHashcode() {
    EqualsVerifier.forClass(Function.Atom.class)
        .withPrefabValues(List.class, Lists.newArrayList("x"), Lists.newArrayList("x", "y"))
        .verify();
  }
}
