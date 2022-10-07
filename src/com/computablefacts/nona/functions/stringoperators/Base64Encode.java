package com.computablefacts.nona.functions.stringoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.asterix.codecs.Base64Codec;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.Base64;
import java.util.List;

@CheckReturnValue
public class Base64Encode extends Function {

  private static final Base64.Encoder b64Encoder_ = Base64.getEncoder();

  public Base64Encode() {
    super(eCategory.STRING_OPERATORS, "BASE64_ENCODE",
        "BASE64_ENCODE(x) returns the base64 encoded version of string x.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "BASE64_ENCODE takes exactly one parameter.");

    String x = parameters.get(0).asString();
    return box(Base64Codec.encodeB64(b64Encoder_, x));
  }
}
