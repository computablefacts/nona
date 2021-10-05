package com.computablefacts.nona.functions.comparisonoperators;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.computablefacts.logfmt.LogFormatter;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class GreaterThan extends Function {

  private static final Logger logger_ = LoggerFactory.getLogger(GreaterThan.class);

  public GreaterThan() {
    super(eCategory.COMPARISON_OPERATORS, "GT",
        "GT(x, y) returns true if x and y are both of the same type and x is strictly greater than y.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 2, "GT takes exactly two parameters.");

    BoxedType<?> param1 = parameters.get(0);
    BoxedType<?> param2 = parameters.get(1);
    Optional<Integer> cmp = param1.compareTo(param2);

    if (cmp.isPresent()) {
      return box(cmp.get() > 0);
    }

    logger_.error(LogFormatter.create(true).message("objects are not comparable")
        .add("first_object", param1.toString()).add("second_object", param2.toString())
        .formatError());

    return box(false);
  }
}
