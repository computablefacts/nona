package com.computablefacts.nona.functions.csvoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.Csv;
import org.junit.Assert;
import org.junit.Test;

public class ToCsvTest {

  @Test
  public void testToCsv() {

    String csv = "col_1,col_2,col_3\n11,12,13\n21,22,23";

    Function fn = new Function("TO_CSV(" + Function.wrap(csv) + ")");
    Assert.assertEquals(BoxedType.of(Csv.create(csv)), fn.evaluate(Function.definitions()));
  }
}
