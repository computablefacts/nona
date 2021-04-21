package com.computablefacts.nona.functions.dateoperators;

import java.util.Calendar;
import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class AddDays extends Function {

  public AddDays() {
    super(eCategory.DATE_OPERATORS, "ADD_DAYS", "ADD_DAYS(x, y) add y days to date x.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 2, "ADD_DAYS takes exactly two parameters.");
    Preconditions.checkArgument(parameters.get(0).isDate(), "%s should be a date",
        parameters.get(0));
    Preconditions.checkArgument(parameters.get(1).isBigInteger(), "%s should be an integer",
        parameters.get(1));

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(parameters.get(0).asDate());
    calendar.add(Calendar.DATE, parameters.get(1).asInt());

    return BoxedType.create(calendar.getTime());
  }
}
