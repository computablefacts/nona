package com.computablefacts.nona.functions.csvoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import org.junit.Assert;
import org.junit.Test;

public class NbCsvRowsTest {

  @Test
  public void testNumberOfRows() {

    String csv = "col_1,col_2,col_3\n11,12,13\n21,22,23";

    Function fn = new Function("NB_CSV_ROWS(TO_CSV(" + Function.wrap(csv) + "))");
    Assert.assertEquals(BoxedType.of(2), fn.evaluate(Function.definitions()));
  }
}
