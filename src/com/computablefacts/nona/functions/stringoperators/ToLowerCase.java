package com.computablefacts.nona.functions.stringoperators;

import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class ToLowerCase extends Function {

  public ToLowerCase() {
    super(eCategory.STRING_OPERATORS, "TO_LOWERCASE",
        "TO_LOWERCASE(x, y) converts x to lowercase.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1,
        "TO_LOWERCASE takes exactly one parameter.");

    String x = parameters.get(0).asString();
    Preconditions.checkNotNull(x, "x should not be null");
    return BoxedType.create(x.toLowerCase());
  }
}
