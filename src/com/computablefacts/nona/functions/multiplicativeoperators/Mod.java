package com.computablefacts.nona.functions.multiplicativeoperators;

import java.math.BigInteger;
import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class Mod extends Function {

  public Mod() {
    super(eCategory.MATHEMATICAL_OPERATORS, "MOD", "MOD(x, y) returns the remainder of x / y.");
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 2, "MOD takes exactly two parameters.");
    Preconditions.checkArgument(parameters.get(0).isBigInteger(), "%s should be an integer",
        parameters.get(0));
    Preconditions.checkArgument(parameters.get(1).isBigInteger(), "%s should be an integer",
        parameters.get(1));

    BigInteger x = parameters.get(0).asBigInteger();
    BigInteger y = parameters.get(1).asBigInteger();

    return BoxedType.create(x.mod(y));
  }
}
