package com.computablefacts.nona.functions.stringoperators;

import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class Concat extends Function {

  public Concat() {
    super(eCategory.STRING_OPERATORS, "CONCAT",
        "CONCAT(x, ..., z) returns \"x...z\" the concatenation of x and ... and z.");
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() >= 2, "CONCAT takes at least two parameters.");

    StringBuilder builder = new StringBuilder();
    for (BoxedType parameter : parameters) {
      if (parameter.asString().startsWith("\"") && parameter.asString().endsWith("\"")) {
        builder.append(parameter.asString(), 1, parameter.asString().length() - 1);
      } else {
        builder.append(parameter);
      }
    }
    return BoxedType.create(builder.toString().trim());
  }
}
