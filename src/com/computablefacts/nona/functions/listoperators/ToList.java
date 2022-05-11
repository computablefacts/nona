package com.computablefacts.nona.functions.listoperators;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.computablefacts.nona.types.BoxedType;
import com.computablefacts.nona.types.Json;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class ToList extends Function {

  public ToList() {
    super(eCategory.LIST_OPERATORS, "TO_LIST",
        "TO_LIST(x) returns the LIST associated with string or JSON x.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "TO_LIST takes exactly one parameter.");
    Preconditions.checkArgument(
        parameters.get(0).isString() || parameters.get(0).value() instanceof Json,
        "%s should be a string or a JSON array/object", parameters.get(0));

    if (parameters.get(0).isString()) {

      String list = parameters.get(0).asString().trim();

      Preconditions.checkState(list.startsWith("["), "list must start with '[' : %s", list);
      Preconditions.checkState(list.endsWith("]"), "list must end with ']' : %s", list);

      return box(Splitter.on(',').trimResults().omitEmptyStrings()
          .splitToStream(list.substring(1, list.length() - 1))
          .map(e -> e.startsWith("\"") ? e.substring(1) : e)
          .map(e -> e.endsWith("\"") ? e.substring(0, e.length() - 1) : e)
          .collect(Collectors.toList()));
    }

    List<Json> list = new ArrayList<>();
    Json json = (Json) parameters.get(0).value();

    for (int i = 0; i < json.nbObjects(); i++) {
      list.add(new Json(json.object(i)));
    }
    return list.isEmpty() ? BoxedType.empty() : box(list);
  }
}
