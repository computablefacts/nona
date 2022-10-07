package com.computablefacts.nona.functions.stringoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.List;

@CheckReturnValue
public class Substring extends Function {

  public Substring() {
    super(eCategory.STRING_OPERATORS, "SUBSTRING",
        "SUBSTRING(x, a, b) returns the substring of x starting at position a (included) and ending at position b (excluded).");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() > 1 && parameters.size() < 4,
        "SUBSTRING takes at least two parameters and at most three.");

    String string = parameters.get(0).asString();

    Preconditions.checkNotNull(string, "string should not be null");
    Preconditions.checkArgument(parameters.get(1).isNumber(), "%s should be an integer >= 0 and <= %s",
        parameters.get(1), string.length());

    if (parameters.size() == 2) {
      return box(string.substring(parameters.get(1).asInt()));
    }

    int begin = parameters.get(1).asInt();

    Preconditions.checkArgument(parameters.get(2).isNumber(), "%s should be an integer >= %s and <= %s",
        parameters.get(2), begin, string.length());

    return box(string.substring(begin, parameters.get(2).asInt()));
  }
}
