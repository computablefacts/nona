package com.computablefacts.nona.functions.dateoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.List;

@CheckReturnValue
public class ElapsedDays extends Function {

  public ElapsedDays() {
    super(eCategory.DATE_OPERATORS, "ELAPSED_DAYS",
        "ELAPSED_DAYS(x, y) returns the number of elapsed days between one date x and one date y. If x is before y, the result will be a positive integer. If x is after y, the result will be a negative integer.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 2, "ELAPSED_DAYS takes exactly two parameters.");
    Preconditions.checkArgument(parameters.get(0).isDate(), "%s should be a date", parameters.get(0));
    Preconditions.checkArgument(parameters.get(1).isDate(), "%s should be a date", parameters.get(1));

    Calendar calendar1 = Calendar.getInstance();
    calendar1.setTime(parameters.get(0).asDate());

    Calendar calendar2 = Calendar.getInstance();
    calendar2.setTime(parameters.get(1).asDate());

    return box(ChronoUnit.DAYS.between(calendar1.toInstant(), calendar2.toInstant()));
  }
}
