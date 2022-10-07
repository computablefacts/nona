package com.computablefacts.nona.functions.listoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;

public class ConcatListsTest {

  @Test
  public void testConcatEmptyLists() {
    Function fn = new Function("CONCAT_LISTS(TO_LIST([]), TO_LIST([]))");
    Assert.assertEquals(BoxedType.of(new ArrayList<>()), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testConcatFirstIsAnEmptyList() {

    String function = String.format("CONCAT_LISTS(TO_LIST(%s), TO_LIST(%s))", Function.wrap("[]"),
        Function.wrap("[1, 2, 3]"));
    Function fn = new Function(function);

    Assert.assertEquals(BoxedType.of(Lists.newArrayList(1, 2, 3)), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testConcatSecondIsAnEmptyList() {

    String function = String.format("CONCAT_LISTS(TO_LIST(%s), TO_LIST(%s))", Function.wrap("[1, 2, 3]"),
        Function.wrap("[]"));
    Function fn = new Function(function);

    Assert.assertEquals(BoxedType.of(Lists.newArrayList(1, 2, 3)), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testConcatLists() {

    String function = String.format("CONCAT_LISTS(TO_LIST(%s), TO_LIST(%s))", Function.wrap("[1, 2, 3]"),
        Function.wrap("[\"a\", \"b\", \"c\"]"));
    Function fn = new Function(function);

    Assert.assertEquals(BoxedType.of(Lists.newArrayList(1, 2, 3, "a", "b", "c")), fn.evaluate(Function.definitions()));
  }
}
