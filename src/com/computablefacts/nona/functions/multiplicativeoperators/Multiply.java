package com.computablefacts.nona.functions.multiplicativeoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Var;
import java.math.BigDecimal;
import java.util.List;

@CheckReturnValue
public class Multiply extends Function {

  public Multiply() {
    super(eCategory.MULTIPLICATIVE_OPERATORS, "MUL", "MUL(x, ..., z) returns x * ... * z.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() >= 2, "MUL takes at least two parameters.");

    @Var BigDecimal res = BigDecimal.ONE;

    for (BoxedType<?> param : parameters) {

      Preconditions.checkArgument(param.isNumber(), "%s should be a number", param);

      res = res.multiply(param.asBigDecimal());

      if (BigDecimal.ZERO.compareTo(res) == 0) {
        return box(BigDecimal.ZERO);
      }
    }
    return box(res);
  }
}
