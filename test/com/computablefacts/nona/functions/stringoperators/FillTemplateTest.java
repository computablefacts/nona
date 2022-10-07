package com.computablefacts.nona.functions.stringoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.Test;

public class FillTemplateTest {

  @Test
  public void testFillTemplate() throws IOException {

    Path input = Files.createTempFile("template-", ".txt");
    Files.write(input, Lists.newArrayList("key1={{value1}}\nkey3={{value3}}\nkey3={{value3}}"));

    Function fn = new Function(
        "FILL_TEMPLATE(" + input.toString() + ",value1,myValue1,value2,myValue2,value3,myValue3)");

    Assert.assertEquals(BoxedType.of("key1=myValue1\nkey3=myValue3\nkey3=myValue3"),
        fn.evaluate(Function.definitions()));
  }
}
