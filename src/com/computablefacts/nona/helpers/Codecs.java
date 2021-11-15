package com.computablefacts.nona.helpers;

import static com.computablefacts.nona.functions.patternoperators.PatternsForward.base64;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.computablefacts.asterix.Span;
import com.computablefacts.asterix.SpanSequence;
import com.computablefacts.logfmt.LogFormatter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Var;
import com.google.re2j.Matcher;
import com.google.re2j.Pattern;

@CheckReturnValue
final public class Codecs {

  public static final Span SPAN_EMPTY = new Span("", 0, 0);
  public static final SpanSequence SPAN_SEQUENCE_EMPTY = new SpanSequence();

  /**
   * A function that encodes a primitive to a lexicographically sortable string.
   */
  public static final Function<Object, Span> defaultLexicoder = object -> {
    if (object == null) {
      return SPAN_EMPTY;
    }
    if (object instanceof String) {
      return new Span((String) object, 0, ((String) object).length());
    }
    if (object instanceof Boolean) {
      String str = Boolean.toString((Boolean) object);
      return new Span(str, 0, str.length());
    }
    if (object instanceof BigInteger) {
      String str = BigDecimalCodec.encode(new BigDecimal((BigInteger) object));
      return new Span(str, 0, str.length());
    }
    if (object instanceof BigDecimal) {
      String str = BigDecimalCodec.encode((BigDecimal) object);
      return new Span(str, 0, str.length());
    }
    if (object instanceof Integer) {
      String str = BigDecimalCodec.encode(BigDecimal.valueOf((Integer) object));
      return new Span(str, 0, str.length());
    }
    if (object instanceof Long) {
      String str = BigDecimalCodec.encode(BigDecimal.valueOf((Long) object));
      return new Span(str, 0, str.length());
    }
    if (object instanceof Double) {
      Double d = (Double) object;
      if (Double.isNaN(d)) {
        return new Span("NaN", 0, 3);
      }
      if (Double.isInfinite(d)) {
        return new Span("inf", 0, 3);
      }
      String str = BigDecimalCodec.encode(BigDecimal.valueOf(d));
      return new Span(str, 0, str.length());
    }
    if (object instanceof Float) {
      Float f = (Float) object;
      if (Float.isNaN(f)) {
        return new Span("NaN", 0, 3);
      }
      if (Float.isInfinite(f)) {
        return new Span("inf", 0, 3);
      }
      String str = BigDecimalCodec.encode(BigDecimal.valueOf(f));
      return new Span(str, 0, str.length());
    }
    if (object instanceof Date) {
      String str = DateTimeFormatter.ISO_INSTANT.format(((Date) object).toInstant());
      return new Span(str, 0, str.length());
    }
    return SPAN_EMPTY;
  };

  private static final Logger logger_ = LoggerFactory.getLogger(Codecs.class);
  private static final ObjectMapper mapper_ = new ObjectMapper();
  private static final Pattern base64_ = Pattern.compile("^" + base64() + "$",
      Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
  private static final Pattern splitOnPunct_ = Pattern.compile("[^\r\n\t\\p{P}\\p{Zs}\\|\\^<>+=~]+",
      Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

  /**
   * A naive tokenizer that removes diacritics and split on punctuation marks.
   */
  public static final Function<String, SpanSequence> defaultTokenizer = text -> {
    if (text == null || text.isEmpty()) {
      return SPAN_SEQUENCE_EMPTY;
    }

    SpanSequence spanSequence = new SpanSequence();
    String newText =
        StringIterator.removeDiacriticalMarks(StringIterator.normalize(text)).toLowerCase();
    Matcher matcher = splitOnPunct_.matcher(newText);

    while (matcher.find()) {

      int begin = matcher.start();
      int end = matcher.end();

      spanSequence.add(new Span(newText, begin, end));
    }
    return spanSequence;
  };

  static {
    SimpleModule dateSerializerModule = new SimpleModule();
    dateSerializerModule.addSerializer(Date.class, new CustomDateSerializer());
    mapper_.registerModule(dateSerializerModule);
  }

  /**
   * Check if a string is probably encoded in Base64.
   *
   * @param str string to test.
   * @return true if the string is probably a Base64-encoded string, false otherwise.
   */
  @Deprecated
  public static boolean isProbablyBase64(@Var String str) {
    if (Strings.isNullOrEmpty(str)) {
      return false;
    }
    str = str.trim();
    return str.length() % 4 == 0 && base64_.matcher(str).matches();
  }

  /**
   * Decode a string from Base64.
   *
   * @param encoder a {@link Base64.Encoder}.
   * @param value q Base64-encoded string.
   * @return a string.
   */
  @Deprecated
  public static String encodeB64(Base64.Encoder encoder, String value) {
    return Preconditions.checkNotNull(encoder, "encoder should not be null")
        .encodeToString(Strings.nullToEmpty(value).getBytes(StandardCharsets.UTF_8));
  }

  /**
   * Encode a string to Base64.
   *
   * @param decoder a {@link Base64.Decoder}.
   * @param value a string to encode.
   * @return a Base64-encoded string.
   */
  @Deprecated
  public static String decodeB64(Base64.Decoder decoder, String value) {
    return new String(Preconditions.checkNotNull(decoder, "decoder should not be null").decode(
        Preconditions.checkNotNull(value, "value should not be null")), StandardCharsets.UTF_8);
  }

  /**
   * Convert an object to a JSON object.
   *
   * @param obj an object.
   * @param <T> the object type.
   * @return a JSON object.
   */
  @Deprecated
  public static <T> @NotNull Map<String, Object> asMap(T obj) {
    try {
      return obj == null ? Collections.emptyMap()
          : mapper_.convertValue(obj, new TypeReference<Map<String, Object>>() {});
    } catch (IllegalArgumentException e) {
      logger_.error(LogFormatter.create(true).message(e).formatError());
    }
    return Collections.emptyMap();
  }

  /**
   * Convert a {@link Collection} of objects to a {@link Collection} of JSON objects.
   *
   * @param obj a {@link Collection} of objects.
   * @param <T> the object type.
   * @return a {@link Collection} of JSON objects.
   */
  @Deprecated
  public static <T> @NotNull Collection<Map<String, Object>> asCollectionOfMaps(Collection<T> obj) {
    if (obj == null) {
      return Collections.emptyList();
    }
    return obj.stream().filter(Objects::nonNull).map(Codecs::asMap).collect(Collectors.toList());
  }

  /**
   * Convert an array of objects to a {@link Collection} of JSON objects.
   * 
   * @param obj an array of objects.
   * @param <T> the object type.
   * @return a {@link Collection} of JSON objects.
   */
  @Deprecated
  @SafeVarargs
  public static <T> @NotNull Collection<Map<String, Object>> asCollectionOfMaps(T... obj) {
    if (obj == null) {
      return Collections.emptyList();
    }
    return Arrays.stream(obj).filter(Objects::nonNull).map(Codecs::asMap)
        .collect(Collectors.toList());
  }

  /**
   * Convert an object to a JSON string.
   *
   * @param obj an object.
   * @param <T> the object type.
   * @return a JSON string.
   */
  @Deprecated
  public static <T> @NotNull String asString(T obj) {
    try {
      return obj == null ? "{}" : mapper_.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      logger_.error(LogFormatter.create(true).message(e).formatError());
    }
    return "{}";
  }

  /**
   * Convert a {@link Collection} of objects to a JSON string.
   *
   * @param obj a {@link Collection} of objects.
   * @param <T> the object type.
   * @return a JSON string.
   */
  @Deprecated
  public static <T> @NotNull String asString(Collection<T> obj) {
    try {
      return obj == null ? "[]" : mapper_.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      logger_.error(LogFormatter.create(true).message(e).formatError());
    }
    return "[]";
  }

  /**
   * Convert an array of objects to a JSON string.
   *
   * @param obj an array of objects.
   * @param <T> the object type.
   * @return a JSON string.
   */
  @Deprecated
  @SafeVarargs
  public static <T> @NotNull String asString(T... obj) {
    try {
      return obj == null ? "[]" : mapper_.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      logger_.error(LogFormatter.create(true).message(e).formatError());
    }
    return "[]";
  }

  /**
   * Convert a string to a JSON object.
   *
   * @param json string.
   * @return a JSON object.
   */
  @Deprecated
  public static @NotNull Map<String, Object> asObject(String json) {
    try {
      return json == null ? Collections.emptyMap()
          : mapper_.readValue(json, TypeFactory.defaultInstance().constructType(Map.class));
    } catch (IOException e) {
      logger_.error(LogFormatter.create(true).message(e).formatError());
    }
    return Collections.emptyMap();
  }

  /**
   * Convert a string to a {@link Collection} of JSON objects.
   *
   * @param json string.
   * @return a {@link Collection} of JSON objects.
   */
  @Deprecated
  public static @NotNull Collection<Map<String, Object>> asCollection(String json) {
    try {
      return json == null ? Collections.emptyList()
          : mapper_.readValue(json,
              TypeFactory.defaultInstance().constructCollectionType(List.class, Map.class));
    } catch (IOException e) {
      logger_.error(LogFormatter.create(true).message(e).formatError());
    }
    return Collections.emptyList();
  }

  /**
   * Convert a string to an array of JSON objects.
   *
   * @param json string.
   * @return an array of JSON objects.
   */
  @Deprecated
  public static @NotNull Map<String, Object>[] asArray(String json) {
    try {
      return json == null ? new Map[0]
          : mapper_.readValue(json, TypeFactory.defaultInstance().constructArrayType(Map.class));
    } catch (IOException e) {
      logger_.error(LogFormatter.create(true).message(e).formatError());
    }
    return new Map[0];
  }

  @Deprecated
  private static class CustomDateSerializer extends StdSerializer<Date> {

    public CustomDateSerializer() {
      this(null);
    }

    public CustomDateSerializer(Class<Date> t) {
      super(t);
    }

    @Override
    public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider)
        throws IOException {
      jgen.writeString(DateTimeFormatter.ISO_INSTANT.format(value.toInstant()));
    }
  }
}

