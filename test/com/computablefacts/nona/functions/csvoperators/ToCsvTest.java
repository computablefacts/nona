package com.computablefacts.nona.functions.csvoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.computablefacts.nona.types.Csv;

public class ToCsvTest {

  @Test
  public void testToCsv() {

    String csv = "col_1,col_2,col_3\n11,12,13\n21,22,23";

    Map<String, Function> functions = new HashMap<>();
    functions.put("TOCSV", new ToCsv());

    Function fn = new Function("TOCSV(" + Function.wrap(csv) + ")");
    Assert.assertEquals(BoxedType.create(Csv.create(csv)), fn.evaluate(functions));
  }
}
