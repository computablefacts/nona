package com.computablefacts.nona.functions.csvoperators;

import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.computablefacts.nona.types.Csv;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class NbCsvRows extends Function {

  public NbCsvRows() {
    super("NBCSVROWS", true);
  }

  @Override
  protected BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "NBCSVROWS takes exactly one parameter.");
    Preconditions.checkArgument(parameters.get(0).value() instanceof Csv, "%s should be a csv",
        parameters.get(0));

    return BoxedType.create(((Csv) parameters.get(0).value()).nbRows());
  }
}
