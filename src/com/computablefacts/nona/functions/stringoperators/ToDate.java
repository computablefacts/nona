package com.computablefacts.nona.functions.stringoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.logfmt.LogFormatter;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Var;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CheckReturnValue
public class ToDate extends Function {

  private static final Logger logger_ = LoggerFactory.getLogger(ToDate.class);

  public ToDate() {
    super(eCategory.STRING_OPERATORS, "TO_DATE", "TO_DATE(x, y) parse x to a date object using format y.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 2, "TO_DATE takes exactly one parameter.");

    String format = parameters.get(1).asString();
    String date = parameters.get(0).asString();

    Preconditions.checkNotNull(format, "format should not be null");
    Preconditions.checkNotNull(date, "date should not be null");

    @Var SimpleDateFormat sdf = null;

    try {
      sdf = new SimpleDateFormat(format);
    } catch (Exception e) {
      logger_.error(LogFormatter.create(true).message("parsing format failed").add("date", date).add("format", format)
          .formatError());
    }

    if (sdf == null) {
      return BoxedType.empty();
    }

    try {
      return box(sdf.parse(date));
    } catch (ParseException e) {
      logger_.error(LogFormatter.create(true).message("parsing date failed").add("date", date).add("format", format)
          .formatError());
    }
    return BoxedType.empty();
  }
}
