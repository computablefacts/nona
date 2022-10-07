package com.computablefacts.nona.functions.csvoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import org.junit.Assert;
import org.junit.Test;

public class CsvValueTest {

  @Test
  public void testGetCsvValue() {

    String csv = "col_1,col_2,col_3\n11,12,13\n21,22,23";

    Function fn1 = new Function("CSV_VALUE(TO_CSV(" + Function.wrap(csv) + "), 0, col_1)");
    Assert.assertEquals(BoxedType.of(11), fn1.evaluate(Function.definitions()));

    Function fn2 = new Function("CSV_VALUE(TO_CSV(" + Function.wrap(csv) + "), 1, col_2)");
    Assert.assertEquals(BoxedType.of(22), fn2.evaluate(Function.definitions()));
  }
}
