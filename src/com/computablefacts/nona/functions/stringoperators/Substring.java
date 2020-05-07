package com.computablefacts.nona.functions.stringoperators;

import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class Substring extends Function {

  public Substring() {
    super("SUBSTRING", true);
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() > 1 && parameters.size() < 4,
        "SUBSTRING takes at least two parameters and at most three.");
    Preconditions.checkArgument(parameters.get(0).isString(), "%s should be a string",
        parameters.get(0));

    String string = parameters.get(0).asString();

    Preconditions.checkArgument(parameters.get(1).isNumber(),
        "%s should be an integer >= 0 and <= %s", parameters.get(1), string.length());

    if (parameters.size() == 2) {
      return BoxedType.create(string.substring(parameters.get(1).asInt()));
    }

    int begin = parameters.get(1).asInt();

    Preconditions.checkArgument(parameters.get(2).isNumber(),
        "%s should be an integer >= %s and <= %s", parameters.get(2), begin, string.length());

    return BoxedType.create(string.substring(begin, parameters.get(2).asInt()));
  }
}
