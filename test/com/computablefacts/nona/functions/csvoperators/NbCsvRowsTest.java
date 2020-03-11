package com.computablefacts.nona.functions.csvoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class NbCsvRowsTest {

  @Test
  public void testNumberOfRows() {

    String csv = "col_1,col_2,col_3\n11,12,13\n21,22,23";

    Map<String, Function> functions = new HashMap<>();
    functions.put("TOCSV", new ToCsv());
    functions.put("NBCSVROWS", new NbCsvRows());

    Function fn = new Function("NBCSVROWS(TOCSV(" + Function.wrap(csv) + "))");
    Assert.assertEquals(BoxedType.create(2), fn.evaluate(functions));
  }
}
