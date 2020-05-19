package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsForward.base64;

import java.util.List;

import com.computablefacts.nona.types.BoxedType;
import com.computablefacts.nona.types.SpanSequence;
import com.google.common.base.Preconditions;

public class Base64 extends MatchPattern {

  public Base64() {
    super("BASE64", base64());
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "BASE64 takes exactly one parameter : %s",
        parameters);

    if (parameters.get(0).asString().length() % 4 == 0) {
      return super.evaluate(parameters);
    }
    return BoxedType.create(new SpanSequence());
  }
}
