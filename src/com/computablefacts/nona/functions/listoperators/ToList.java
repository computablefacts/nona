package com.computablefacts.nona.functions.listoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.asterix.codecs.JsonCodec;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@CheckReturnValue
public class ToList extends Function {

  public ToList() {
    super(eCategory.LIST_OPERATORS, "TO_LIST", "TO_LIST(x) returns the LIST associated with string or JSON x.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "TO_LIST takes exactly one parameter.");

    if (parameters.get(0).isString()) {

      Preconditions.checkArgument(parameters.get(0).isString(), "%s should be a string", parameters.get(0));

      String list = parameters.get(0).asString().trim();

      Preconditions.checkState(list.startsWith("["), "list must start with '[' : %s", list);
      Preconditions.checkState(list.endsWith("]"), "list must end with ']' : %s", list);

      Map<String, Object> json = JsonCodec.asObject(String.format("{\"root\":%s}", list));
      return json == null || json.get("root") == null ? BoxedType.empty() : box(json.get("root"));
    }

    Preconditions.checkArgument(parameters.get(0).isCollection(), "%s should be a Collection of objects",
        parameters.get(0));

    Collection<?> collection = parameters.get(0).asCollection();
    return collection.isEmpty() ? BoxedType.empty() : box(collection);
  }
}
