package com.computablefacts.nona.functions.stringoperators;

import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class ToLowerCase extends Function {

  public ToLowerCase() {
    super("TOLOWERCASE", true);
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "TOLOWERCASE takes exactly one parameter.");
    Preconditions.checkArgument(parameters.get(0).isString(), "%s should be a string",
            parameters.get(0));

    return BoxedType.create(parameters.get(0).asString().toLowerCase());
  }
}
