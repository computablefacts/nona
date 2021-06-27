package com.computablefacts.nona.functions.stringoperators;

import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class Trim extends Function {

  public Trim() {
    super(eCategory.STRING_OPERATORS, "TRIM",
        "TRIM(x) returns the string x with the whitespaces at the beginning and the end of the string removed.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "TRIM takes exactly one parameter.");
    Preconditions.checkArgument(parameters.get(0).isString(), "%s should be a string",
        parameters.get(0));

    String str = parameters.get(0).asString();
    if (str.startsWith("\"") && str.endsWith("\"")) {
      return BoxedType.create(str.substring(1, str.length() - 1).trim());
    }
    return BoxedType.create(str.trim());
  }
}
