package com.computablefacts.nona.functions.listoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.asterix.codecs.JsonCodec;
import com.computablefacts.nona.Function;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;

public class ToListTest {

  @Test
  public void testToListOnEmptyList() {
    Function fn = new Function("TO_LIST([])");
    Assert.assertEquals(BoxedType.of(new ArrayList<>()), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testArrayToList() {

    String array = Function.wrap("[1, 2, 3]");
    Function fn = new Function(String.format("TO_LIST(%s)", array));

    Assert.assertEquals(BoxedType.of(Lists.newArrayList(1, 2, 3)), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testJsonToList() {

    String json = "[{\"id\": 1} , {\"id\": 2}, {\"id\": 3}]";
    Function fn = new Function(String.format("TO_LIST(TO_JSON(%s))", Function.wrap(json)));

    Collection<?> actual = fn.evaluate(Function.definitions()).asCollection();
    Collection<?> expected = JsonCodec.asCollection(json);

    Assert.assertEquals(expected, actual);
  }
}
