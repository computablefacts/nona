package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsForward.leftBoundary;
import static com.computablefacts.nona.functions.patternoperators.PatternsForward.rightBoundary;

import java.util.ArrayList;
import java.util.List;

import com.computablefacts.nona.functions.stringoperators.RegexExtract;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;

public class PatternExtract extends RegexExtract {

  private final String name_;
  private final String pattern_;

  public PatternExtract(String name, String pattern) {
    super(name);
    name_ = name;
    pattern_ = Preconditions.checkNotNull(pattern, "pattern should not be null");
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "%s takes exactly one parameter : %s",
        name_, parameters);
    Preconditions.checkArgument(parameters.get(0).isString(), "%s should be a string",
        parameters.get(0));

    List<BoxedType> newParameters = new ArrayList<>();
    newParameters.add(parameters.get(0));
    newParameters.add(BoxedType.create(leftBoundary() + pattern_ + rightBoundary()));

    return super.evaluate(newParameters);
  }
}
