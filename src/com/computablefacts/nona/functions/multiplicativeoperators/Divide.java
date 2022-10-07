package com.computablefacts.nona.functions.multiplicativeoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import java.math.BigDecimal;
import java.util.List;

public class Divide extends Function {

  public Divide() {
    super(eCategory.MULTIPLICATIVE_OPERATORS, "DIV", "DIV(x, y) returns x / y.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 2, "DIV takes exactly two parameters.");
    Preconditions.checkArgument(parameters.get(0).isNumber(), "%s should be a number", parameters.get(0));
    Preconditions.checkArgument(parameters.get(1).isNumber(), "%s should be a number", parameters.get(1));

    BigDecimal dividend = parameters.get(0).asBigDecimal();
    BigDecimal divisor = parameters.get(1).asBigDecimal();

    return box(dividend.divide(divisor));
  }
}
