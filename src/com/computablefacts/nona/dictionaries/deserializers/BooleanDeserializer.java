package com.computablefacts.nona.dictionaries.deserializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class BooleanDeserializer extends JsonDeserializer<Boolean> {

  public BooleanDeserializer() {}

  @Override
  public Boolean deserialize(JsonParser parser, DeserializationContext context) throws IOException {
    return Boolean.parseBoolean(parser.getText());
  }
}
