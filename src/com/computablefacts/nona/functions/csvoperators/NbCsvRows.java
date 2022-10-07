package com.computablefacts.nona.functions.csvoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.computablefacts.nona.types.Csv;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.List;

@CheckReturnValue
public class NbCsvRows extends Function {

  public NbCsvRows() {
    super(eCategory.CSV_OPERATORS, "NB_CSV_ROWS", "NB_CSV_ROWS(csv) returns the total number of rows.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "NB_CSV_ROWS takes exactly one parameter.");
    Preconditions.checkArgument(parameters.get(0).value() instanceof Csv, "%s should be a csv", parameters.get(0));

    return box(((Csv) parameters.get(0).value()).nbRows());
  }
}
