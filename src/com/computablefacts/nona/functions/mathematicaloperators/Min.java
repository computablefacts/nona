package com.computablefacts.nona.functions.mathematicaloperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Var;
import java.math.BigDecimal;
import java.util.List;

@CheckReturnValue
public class Min extends Function {

  public Min() {
    super(eCategory.MATHEMATICAL_OPERATORS, "MIN", "MIN(x, ..., z) returns the lowest number between {x, ..., z}.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() >= 2, "MIN takes at least two parameters.");

    @Var BigDecimal min = null;

    for (BoxedType<?> param : parameters) {

      Preconditions.checkArgument(param.isNumber(), "%s should be a number", param);

      min = min == null ? param.asBigDecimal() : min.min(param.asBigDecimal());
    }
    return box(min);
  }
}
