package com.computablefacts.nona.functions.csvoperators;

import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.computablefacts.nona.types.BoxedType;
import com.computablefacts.nona.types.Csv;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class ToCsv extends Function {

  public ToCsv() {
    super(eCategory.CSV_OPERATORS, "TO_CSV",
        "TO_CSV(x) returns the CSV object associated to string x.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "TO_CSV takes exactly one parameter.");
    Preconditions.checkArgument(parameters.get(0).isString(), "%s should be a string",
        parameters.get(0));

    return box(Csv.create(parameters.get(0).asString()));
  }
}
