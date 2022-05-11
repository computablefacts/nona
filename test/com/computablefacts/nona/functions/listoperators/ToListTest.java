package com.computablefacts.nona.functions.listoperators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.asterix.codecs.JsonCodec;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.computablefacts.nona.types.Json;
import com.google.common.collect.Lists;

public class ToListTest {

  @Test
  public void testToListOnEmptyList() {
    Function fn = new Function("TO_LIST([])");
    Assert.assertEquals(BoxedType.create(new ArrayList<>()), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testArrayToList() {
    Function fn = new Function("TO_LIST(_([1, 2, 3]))");
    Assert.assertEquals(BoxedType.create(Lists.newArrayList("1", "2", "3")),
        fn.evaluate(Function.definitions()));
  }

  @Test
  public void testJsonToList() {

    String json = "[{\"id\": 1} , {\"id\": 2}, {\"id\": 3}]";

    Function fn = new Function("TO_LIST(TO_JSON(" + Function.wrap(json) + "))");

    Collection<?> actual = fn.evaluate(Function.definitions()).asCollection();
    Collection<?> expected =
        JsonCodec.asCollection(json).stream().map(Json::new).collect(Collectors.toList());

    Assert.assertEquals(expected, actual);
  }
}
