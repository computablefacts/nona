package com.computablefacts.nona.functions.stringoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.List;

@CheckReturnValue
public class IndexOf extends Function {

  public IndexOf() {
    super(eCategory.STRING_OPERATORS, "INDEX_OF",
        "INDEX_OF(x, y) returns the position of the substring y in x, -1 otherwise.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 2, "INDEX_OF takes exactly two parameters.");

    String x = parameters.get(0).asString();
    String y = parameters.get(1).asString();

    Preconditions.checkNotNull(x, "x should not be null");
    Preconditions.checkNotNull(y, "y should not be null");

    return box(x.indexOf(y));
  }
}
