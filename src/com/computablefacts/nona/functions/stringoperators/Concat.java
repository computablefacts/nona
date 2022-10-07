package com.computablefacts.nona.functions.stringoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.List;

@CheckReturnValue
public class Concat extends Function {

  public Concat() {
    super(eCategory.STRING_OPERATORS, "CONCAT",
        "CONCAT(x, ..., z) returns \"x...z\" the concatenation of x and ... and z.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() >= 2, "CONCAT takes at least two parameters.");

    StringBuilder builder = new StringBuilder();

    for (BoxedType<?> parameter : parameters) {
      String str = parameter.asString();
      Preconditions.checkNotNull(str, "null parameters are forbidden");
      builder.append(str);
    }
    return box(builder.toString().trim());
  }
}
