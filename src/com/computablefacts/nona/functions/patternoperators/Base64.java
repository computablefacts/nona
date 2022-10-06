package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsForward.base64;

import java.util.List;

import com.computablefacts.asterix.SpanSequence;
import com.computablefacts.asterix.BoxedType;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class Base64 extends MatchPattern {

  public Base64() {
    super("BASE64", base64());
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "BASE64 takes exactly one parameter : %s",
        parameters);

    if (parameters.get(0).asString().length() % 4 == 0) {
      return super.evaluate(parameters);
    }
    return box(new SpanSequence());
  }
}
