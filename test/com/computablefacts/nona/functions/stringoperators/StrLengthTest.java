package com.computablefacts.nona.functions.stringoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class StrLengthTest {

  @Test
  public void testStrLength() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("STRLENGTH", new StrLength());

    Function fn = new Function("STRLENGTH(cyrille)");
    Assert.assertEquals(BoxedType.create(7), fn.evaluate(functions));
  }
}
