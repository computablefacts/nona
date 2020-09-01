package com.computablefacts.nona.dictionaries.deserializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class WhiteSpaceRemovalDeserializer extends JsonDeserializer<String> {

  public WhiteSpaceRemovalDeserializer() {}

  @Override
  public String deserialize(JsonParser parser, DeserializationContext context) throws IOException {
    return parser.getText().trim();
  }
}
