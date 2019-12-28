package com.computablefacts.nona.functions.multiplicativeoperators;

import java.math.BigDecimal;
import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;

public class Divide extends Function {

  public Divide() {
    super("DIVIDE", true);
  }

  @Override
  protected BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 2, "DIVIDE takes exactly two parameters.");
    Preconditions.checkArgument(parameters.get(0).isNumber(), "%s should be a number",
        parameters.get(0));
    Preconditions.checkArgument(parameters.get(1).isNumber(), "%s should be a number",
        parameters.get(1));

    BigDecimal dividend = parameters.get(0).asBigDecimal();
    BigDecimal divisor = parameters.get(1).asBigDecimal();

    return BoxedType.create(dividend.divide(divisor));
  }
}
