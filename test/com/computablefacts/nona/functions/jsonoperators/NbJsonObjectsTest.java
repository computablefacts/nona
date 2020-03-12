package com.computablefacts.nona.functions.jsonoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class NbJsonObjectsTest {

  @Test
  public void testEmptyArrayNumberOfObjects() {

    String json = "[]";

    Map<String, Function> functions = new HashMap<>();
    functions.put("TOJSON", new ToJson());
    functions.put("NBJSONOBJECTS", new NbJsonObjects());

    Function fn = new Function("NBJSONOBJECTS(TOJSON(" + Function.wrap(json) + "))");
    Assert.assertEquals(BoxedType.create(0), fn.evaluate(functions));
  }

  @Test
  public void testArrayNumberOfObjects() {

    String json =
        "[{\"col_1\": 11, \"col_2\": 12, \"col_3\": 13} , {\"col_1\": 21, \"col_2\": 22, \"col_3\": 23}]";

    Map<String, Function> functions = new HashMap<>();
    functions.put("TOJSON", new ToJson());
    functions.put("NBJSONOBJECTS", new NbJsonObjects());

    Function fn = new Function("NBJSONOBJECTS(TOJSON(" + Function.wrap(json) + "))");
    Assert.assertEquals(BoxedType.create(2), fn.evaluate(functions));
  }

  @Test
  public void testEmptyObjectNumberOfObjects() {

    String json = "{}";

    Map<String, Function> functions = new HashMap<>();
    functions.put("TOJSON", new ToJson());
    functions.put("NBJSONOBJECTS", new NbJsonObjects());

    Function fn = new Function("NBJSONOBJECTS(TOJSON(" + Function.wrap(json) + "))");
    Assert.assertEquals(BoxedType.create(0), fn.evaluate(functions));
  }

  @Test
  public void testObjectNumberOfObjects() {

    String json = "{\"col_1\": 11, \"col_2\": 12, \"col_3\": 13}";

    Map<String, Function> functions = new HashMap<>();
    functions.put("TOJSON", new ToJson());
    functions.put("NBJSONOBJECTS", new NbJsonObjects());

    Function fn = new Function("NBJSONOBJECTS(TOJSON(" + Function.wrap(json) + "))");
    Assert.assertEquals(BoxedType.create(1), fn.evaluate(functions));
  }
}
