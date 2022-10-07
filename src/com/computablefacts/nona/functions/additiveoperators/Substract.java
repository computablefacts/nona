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
public class Substract extends Function {

  public Substract() {
    super(eCategory.ADDITIVE_OPERATORS, "SUB", "SUB(x, ..., z) returns x - ... - z.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() >= 2, "SUB takes at least two parameters.");
    Preconditions.checkArgument(parameters.get(0).isNumber(), "%s should be a number", parameters.get(0));

    @Var BigDecimal res = parameters.get(0).asBigDecimal();

    for (int i = 1; i < parameters.size(); i++) {

      BoxedType<?> bt = parameters.get(i);

      Preconditions.checkArgument(bt.isNumber(), "%s should be a number", bt);

      res = res.subtract(bt.asBigDecimal());
    }
    return box(res);
  }
}
