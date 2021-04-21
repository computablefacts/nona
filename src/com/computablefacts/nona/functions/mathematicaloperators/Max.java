package com.computablefacts.nona.functions.mathematicaloperators;

import java.math.BigDecimal;
import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Var;

@CheckReturnValue
public class Max extends Function {

  public Max() {
    super(eCategory.MATHEMATICAL_OPERATORS, "MAX",
        "MAX(x, ..., z) returns the largest number between {x, ..., z}.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() >= 2, "MAX takes at least two parameters.");

    @Var
    BigDecimal max = BigDecimal.ZERO;

    for (BoxedType<?> param : parameters) {

      Preconditions.checkArgument(param.isNumber(), "%s should be a number", param);

      max = max.max(param.asBigDecimal());
    }
    return BoxedType.create(max);
  }
}
