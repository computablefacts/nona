package com.computablefacts.nona.functions.stringoperators;

import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class ToInteger extends Function {

  public ToInteger() {
    super("TOINTEGER", true);
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "TOINTEGER takes exactly one parameter.");
    Preconditions.checkArgument(parameters.get(0).isNumber(), "%s should be a number",
        parameters.get(0));

    return BoxedType.create(parameters.get(0).asBigInteger());
  }
}
