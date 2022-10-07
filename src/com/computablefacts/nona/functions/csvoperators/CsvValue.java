package com.computablefacts.nona.functions.csvoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.computablefacts.nona.types.Csv;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.List;

@CheckReturnValue
public class CsvValue extends Function {

  public CsvValue() {
    super(eCategory.CSV_OPERATORS, "CSV_VALUE",
        "CSV_VALUE(csv, a, b) returns the cell value at the intersection of row a (integer) and column b (string).");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 3, "CSV_VALUE takes exactly three parameters.");
    Preconditions.checkArgument(parameters.get(0).value() instanceof Csv, "%s should be a csv", parameters.get(0));
    Preconditions.checkArgument(parameters.get(1).isNumber(), "%s should be a number (row number)", parameters.get(1));
    Preconditions.checkArgument(parameters.get(2).isString(), "%s should be a string (col name)", parameters.get(2));

    return box(((Csv) parameters.get(0).value()).value(parameters.get(1).asInt(), parameters.get(2).asString()));
  }
}
