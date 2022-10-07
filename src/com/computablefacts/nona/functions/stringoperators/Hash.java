package com.computablefacts.nona.functions.stringoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.common.hash.Hashing;
import com.google.errorprone.annotations.CheckReturnValue;
import java.nio.charset.StandardCharsets;
import java.util.List;

@CheckReturnValue
public class Hash extends Function {

  public Hash() {
    super(eCategory.STRING_OPERATORS, "HASH",
        "HASH(x, a, ..., m) returns the hash x in {MD5, SHA256, SHA512, CRC32} of the concatenated string \"a...m\".");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() >= 2, "HASH takes at least two parameters.");
    Preconditions.checkArgument(parameters.get(0).isString(), "%s should be a string", parameters.get(0));

    String hash = parameters.get(0).asString().toUpperCase();
    StringBuilder builder = new StringBuilder();

    for (int i = 1; i < parameters.size(); i++) {
      builder.append(parameters.get(i).asString());
    }

    if ("SHA256".equals(hash)) {
      return BoxedType.create(Hashing.sha256().hashString(builder, StandardCharsets.UTF_8).toString());
    }
    if ("SHA512".equals(hash)) {
      return BoxedType.create(Hashing.sha512().hashString(builder, StandardCharsets.UTF_8).toString());
    }
    if ("CRC32".equals(hash)) {
      return BoxedType.create(Hashing.crc32().hashString(builder, StandardCharsets.UTF_8).toString());
    }
    return box(Hashing.md5().hashString(builder, StandardCharsets.UTF_8).toString());
  }
}
