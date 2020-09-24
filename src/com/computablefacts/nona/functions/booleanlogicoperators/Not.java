package com.computablefacts.nona.functions.booleanlogicoperators;

import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class Not extends Function {

  public Not() {
    super(eCategory.BOOLEAN_LOGIC_OPERATORS, "NOT",
        "NOT(x) returns true if x is false. Returns false if x is true.");
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "NOT takes exactly one parameter.");
    Preconditions.checkArgument(parameters.get(0).isBoolean(), "%s should be a boolean",
        parameters.get(0));

    return BoxedType.create(!parameters.get(0).asBool());
  }
}
