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
public class Base64Decode extends Function {

  private static final Base64.Decoder b64Decoder_ = Base64.getDecoder();

  public Base64Decode() {
    super(eCategory.STRING_OPERATORS, "BASE64_DECODE",
        "BASE64_DECODE(x) returns decoded version of the base64 string x.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "BASE64_DECODE takes exactly one parameter.");

    String x = parameters.get(0).asString();

    Preconditions.checkNotNull(x, "x should not be null");

    return box(Base64Codec.decodeB64(b64Decoder_, x));
  }
}
