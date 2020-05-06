package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsForward.leftBoundary;
import static com.computablefacts.nona.functions.patternoperators.PatternsForward.rightBoundary;
import static com.computablefacts.nona.functions.patternoperators.PatternsForward.winPath;

import java.util.ArrayList;
import java.util.List;

import com.computablefacts.nona.functions.stringoperators.RegexExtract;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;

public class ExtractUnixPath extends RegexExtract {

  public ExtractUnixPath() {
    super("EXTRACT_UNIX_PATH");
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 1,
        "EXTRACT_UNIX_PATH takes exactly one parameter : %s", parameters);
    Preconditions.checkArgument(parameters.get(0).isString(), "%s should be a string",
        parameters.get(0));

    List<BoxedType> newParameters = new ArrayList<>();
    newParameters.add(parameters.get(0));
    newParameters
        .add(BoxedType.create(leftBoundary() + "(?i)" + winPath() + "(?-i)" + rightBoundary()));

    return super.evaluate(newParameters);
  }
}
