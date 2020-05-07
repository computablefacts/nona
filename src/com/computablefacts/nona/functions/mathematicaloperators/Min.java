package com.computablefacts.nona.functions.mathematicaloperators;

import java.math.BigDecimal;
import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Var;

@CheckReturnValue
public class Min extends Function {

  public Min() {
    super("MIN", true);
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() >= 2, "MIN takes at least two parameters.");

    @Var
    BigDecimal min = null;

    for (BoxedType param : parameters) {

      Preconditions.checkArgument(param.isNumber(), "%s should be a number", param);

      min = min == null ? param.asBigDecimal() : min.min(param.asBigDecimal());
    }
    return BoxedType.create(min);
  }
}
