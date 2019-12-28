package com.computablefacts.nona.functions.multiplicativeoperators;

import java.math.BigDecimal;
import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Var;

@CheckReturnValue
public class Multiply extends Function {

  public Multiply() {
    super("MULTIPLY", true);
  }

  @Override
  protected BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() >= 2, "MULTIPLY takes at least two parameters.");

    @Var
    BigDecimal res = BigDecimal.ONE;

    for (BoxedType param : parameters) {

      Preconditions.checkArgument(param.isNumber(), "%s should be a number", param);

      res = res.multiply(param.asBigDecimal());

      if (BigDecimal.ZERO.compareTo(res) == 0) {
        return BoxedType.create(BigDecimal.ZERO);
      }
    }
    return BoxedType.create(res);
  }
}
