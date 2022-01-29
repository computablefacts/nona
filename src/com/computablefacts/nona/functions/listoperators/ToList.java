package com.computablefacts.nona.functions.listoperators;

import java.util.List;
import java.util.stream.Collectors;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class ToList extends Function {

  public ToList() {
    super(eCategory.LIST_OPERATORS, "TO_LIST",
        "TO_LIST(x) returns the LIST associated with string x.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "TO_LIST takes exactly one parameter.");
    Preconditions.checkArgument(parameters.get(0).isString(), "%s should be a string",
        parameters.get(0));

    String list = parameters.get(0).asString().trim();

    Preconditions.checkState(list.startsWith("["), "list must start with '[' : %s", list);
    Preconditions.checkState(list.endsWith("]"), "list must end with ']' : %s", list);

    return box(Splitter.on(',').trimResults().omitEmptyStrings()
        .splitToStream(list.substring(1, list.length() - 1))
        .map(e -> e.startsWith("\"") ? e.substring(1) : e)
        .map(e -> e.endsWith("\"") ? e.substring(0, e.length() - 1) : e)
        .collect(Collectors.toList()));
  }
}
