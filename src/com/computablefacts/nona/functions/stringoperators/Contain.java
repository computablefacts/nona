package com.computablefacts.nona.functions.stringoperators;

import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class Contain extends Function {

  public Contain() {
    super("CONTAIN", true);
  }

  @Override
  protected BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 2, "CONTAIN takes exactly two parameters.");

    return BoxedType.create(parameters.get(0).asString().contains(parameters.get(1).asString()));
  }
}
