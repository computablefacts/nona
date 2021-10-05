package com.computablefacts.nona.functions.stringoperators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.computablefacts.logfmt.LogFormatter;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class ToDate extends Function {

  private static final Logger logger_ = LoggerFactory.getLogger(ToDate.class);

  public ToDate() {
    super(eCategory.STRING_OPERATORS, "TO_DATE",
        "TO_DATE(x, y) parse x to a date object using format y.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 2, "TO_DATE takes exactly one parameter.");

    String format = parameters.get(1).asString();
    String date = parameters.get(0).asString();

    Preconditions.checkNotNull(format, "format should not be null");
    Preconditions.checkNotNull(date, "date should not be null");

    SimpleDateFormat sdf = new SimpleDateFormat(format);

    Preconditions.checkNotNull(sdf, "sdf should not be null");

    try {
      return box(sdf.parse(date));
    } catch (ParseException e) {
      logger_.error(LogFormatter.create(true).message("parsing failed").add("date", date)
          .add("format", format).formatError());
    }
    return BoxedType.empty();
  }
}
