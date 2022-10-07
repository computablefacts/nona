package com.computablefacts.nona.functions.additiveoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Var;
import java.math.BigDecimal;
import java.util.List;

@CheckReturnValue
public class Add extends Function {

  public Add() {
    super(eCategory.ADDITIVE_OPERATORS, "ADD", "ADD(x, ..., y) returns x + ... + y.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() >= 2, "ADD takes at least two parameters.");

    @Var BigDecimal res = BigDecimal.ZERO;

    for (BoxedType<?> param : parameters) {

      Preconditions.checkArgument(param.isNumber(), "%s should be a number", param);

      res = res.add(param.asBigDecimal());
    }
    return box(res);
  }
}
