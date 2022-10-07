package com.computablefacts.nona.functions.listoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.asterix.codecs.JsonCodec;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@CheckReturnValue
public class Get extends Function {

  public Get() {
    super(eCategory.LIST_OPERATORS, "GET",
        "GET(x, y) returns the element of list x at position y or returns the value of MAP x at key y.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 2, "GET takes exactly two parameters.");

    if (parameters.get(0).isCollection()) {

      Preconditions.checkArgument(parameters.get(1).isNumber(), "%s should be a number", parameters.get(1));
      Preconditions.checkArgument(parameters.get(1).asInt() >= 0, "%s should be >= 0", parameters.get(1));

      Collection<?> list = parameters.get(0).asCollection();
      int index = parameters.get(1).asInt();
      return index > list.size() ? BoxedType.empty() : box(Iterables.get(list, index));
    }
    if (parameters.get(0).isMap()) {

      Preconditions.checkArgument(parameters.get(1).isString(), "%s should be a MAP key", parameters.get(1));
      Preconditions.checkArgument(!Strings.isNullOrEmpty(parameters.get(1).asString()),
          "a MAP key should neither be null nor empty", parameters.get(1));

      Map<?, ?> map = parameters.get(0).asMap();
      String key = parameters.get(1).asString();
      return map == null ? BoxedType.empty() : box(map.get(key) == null ? "" : map.get(key));
    }
    if (parameters.get(0).isString()) {

      String str = parameters.get(0).asString();

      if (str.startsWith("[") && str.endsWith("]")) {

        Preconditions.checkArgument(parameters.get(1).isNumber(), "%s should be a number", parameters.get(1));
        Preconditions.checkArgument(parameters.get(1).asInt() >= 0, "%s should be >= 0", parameters.get(1));

        Collection<?> list = JsonCodec.asCollection(parameters.get(0).asString());
        int index = parameters.get(1).asInt();
        return index > list.size() ? BoxedType.empty() : box(Iterables.get(list, index));
      }
      if (str.startsWith("{") && str.endsWith("}")) {

        Preconditions.checkArgument(parameters.get(1).isString(), "%s should be a MAP key", parameters.get(1));
        Preconditions.checkArgument(!Strings.isNullOrEmpty(parameters.get(1).asString()),
            "a MAP key should neither be null nor empty", parameters.get(1));

        Map<?, ?> map = JsonCodec.asObject(parameters.get(0).asString());
        String key = parameters.get(1).asString();
        return map == null ? BoxedType.empty() : box(map.get(key) == null ? "" : map.get(key));
      }
    }
    return BoxedType.empty();
  }
}
