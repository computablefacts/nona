package com.computablefacts.nona.functions.dateoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.Calendar;
import java.util.List;

@CheckReturnValue
public class AddHours extends Function {

  public AddHours() {
    super(eCategory.DATE_OPERATORS, "ADD_HOURS", "ADD_HOURS(x, y) add y hours to date x.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 2, "ADD_HOURS takes exactly two parameters.");
    Preconditions.checkArgument(parameters.get(0).isDate(), "%s should be a date", parameters.get(0));
    Preconditions.checkArgument(parameters.get(1).isBigInteger(), "%s should be an integer", parameters.get(1));

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(parameters.get(0).asDate());
    calendar.add(Calendar.HOUR, parameters.get(1).asInt());

    return box(calendar.getTime());
  }
}
