package com.computablefacts.nona.functions.stringoperators;

import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class IndexOf extends Function {

  public IndexOf() {
    super("INDEXOF", true);
  }

  @Override
  protected BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 2, "INDEXOF takes exactly two parameters.");
    Preconditions.checkArgument(parameters.get(0).isString(), "%s should be a string",
        parameters.get(0));

    return BoxedType.create(parameters.get(0).asString().indexOf(parameters.get(1).asString()));
  }
}
