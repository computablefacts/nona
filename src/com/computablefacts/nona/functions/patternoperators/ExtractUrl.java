package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsForward.leftBoundary;
import static com.computablefacts.nona.functions.patternoperators.PatternsForward.rightBoundary;
import static com.computablefacts.nona.functions.patternoperators.PatternsForward.url;

import java.util.ArrayList;
import java.util.List;

import com.computablefacts.nona.functions.stringoperators.RegexExtract;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;

public class ExtractUrl extends RegexExtract {

  public ExtractUrl() {
    super("EXTRACT_URL");
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 1,
        "EXTRACT_URL takes exactly one parameter : %s", parameters);
    Preconditions.checkArgument(parameters.get(0).isString(), "%s should be a string",
        parameters.get(0));

    List<BoxedType> newParameters = new ArrayList<>();
    newParameters.add(parameters.get(0));
    newParameters
        .add(BoxedType.create(leftBoundary() + "(?i)" + url() + "(?-i)" + rightBoundary()));

    return super.evaluate(newParameters);
  }
}
