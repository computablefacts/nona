package com.computablefacts.nona.types;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.computablefacts.nona.Generated;
import com.computablefacts.nona.helpers.StringIterator;
import com.google.common.collect.Lists;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
final public class BoxedType<T> {

  private static final BoxedType<?> NULL = new BoxedType<>(null);
  private static final BoxedType<?> TRUE = new BoxedType<>(true);
  private static final BoxedType<?> FALSE = new BoxedType<>(false);
  private final T value_; // T in {Boolean, BigInteger, BigDecimal, String}

  private BoxedType(T value) {
    value_ = value;
  }

  public static BoxedType<?> empty() {
    return NULL;
  }

  public static BoxedType<?> create(int[] values) {
    return BoxedType.create(Arrays.stream(values).boxed().collect(Collectors.toList()));
  }

  public static BoxedType<?> create(long[] values) {
    return BoxedType.create(Arrays.stream(values).boxed().collect(Collectors.toList()));
  }

  public static BoxedType<?> create(double[] values) {
    return BoxedType.create(Arrays.stream(values).boxed().collect(Collectors.toList()));
  }

  public static BoxedType<?> create(float[] values) {
    List<Float> floats = new ArrayList<>(values.length);
    for (float f : values) {
      floats.add(f);
    }
    return BoxedType.create(floats);
  }

  public static BoxedType<?> create(boolean[] values) {
    List<Boolean> booleans = new ArrayList<>(values.length);
    for (boolean b : values) {
      booleans.add(b);
    }
    return BoxedType.create(booleans);
  }

  public static BoxedType<?> create(Object[] values) {
    return BoxedType.create(Lists.newArrayList(values));
  }

  public static BoxedType<?> create(Object value) {
    return create(value, true);
  }

  /**
   * Coerce a given value.
   *
   * @param value the value to box/coerce.
   * @param interpretStringInScientificNotation true iif "79E2863560" should be interpreted as
   *        7.9E+2863561 and false otherwise.
   * @return a boxed/coerced value.
   */
  public static BoxedType<?> create(Object value, boolean interpretStringInScientificNotation) {
    if (value == null) {
      return NULL;
    }
    if (value instanceof Boolean) {
      return ((Boolean) value) ? TRUE : FALSE;
    }
    if (value instanceof BigInteger || value instanceof BigDecimal) {
      return new BoxedType<>(value);
    }
    if (value instanceof Integer) {
      return new BoxedType<>(BigInteger.valueOf((Integer) value));
    }
    if (value instanceof Long) {
      return new BoxedType<>(BigInteger.valueOf((Long) value));
    }
    if (value instanceof Float) {
      return new BoxedType<>(BigDecimal.valueOf((Float) value));
    }
    if (value instanceof Double) {
      return new BoxedType<>(BigDecimal.valueOf((Double) value));
    }
    if (value instanceof String) {

      // Attempt type coercion
      String text = (String) value;

      if ("true".equalsIgnoreCase(text)) {
        return TRUE;
      }
      if ("false".equalsIgnoreCase(text)) {
        return FALSE;
      }

      if (interpretStringInScientificNotation || (!text.contains("E") && !text.contains("e"))) {
        try {

          BoxedType<?> bt = new BoxedType<>(new BigInteger(text));

          // Here, text is an integer (otherwise a NumberFormatException has been thrown)
          StringIterator iterator = new StringIterator(text);
          iterator.movePast(new char[] {'0'});

          // The condition below ensures "0" is interpreted as a number but "00" as a string
          if (iterator.position() > 1 || (iterator.position() > 0 && iterator.remaining() > 0)) {

            // text matching [0]+[0-9]+ should be interpreted as string
            return new BoxedType<>(text);
          }
          return bt;
        } catch (NumberFormatException ex) {
          // FALL THROUGH
        }

        try {

          BoxedType<?> bt = new BoxedType<>(new BigDecimal(text));
          String textTrimmed = text.trim();

          // text matching \d+[.] should be interpreted as string
          // text matching [.]\d+ should be interpreted as string
          if (!textTrimmed.endsWith(".") && !textTrimmed.startsWith(".")) {
            return bt;
          }
        } catch (NumberFormatException ex) {
          // FALL THROUGH
        }
      }
      return new BoxedType<>(text);
    }
    return new BoxedType<>(value);
  }

  @Override
  public int hashCode() {
    if (isBoolean()) {
      return asBool().hashCode();
    }
    if (isNumber()) {
      return asBigDecimal().stripTrailingZeros().hashCode();
    }
    if (isString()) {
      return asString().hashCode();
    }
    return Objects.hash(value());
  }

  @Override
  public boolean equals(Object o) {

    if (o == this) {
      return true;
    }
    if (!(o instanceof BoxedType)) {
      return false;
    }

    BoxedType<?> bt = (BoxedType<?>) o;

    if (isBoolean() && bt.isBoolean()) {
      return asBool().equals(bt.asBool());
    }
    if (isNumber() && bt.isNumber()) {
      return asBigDecimal().compareTo(bt.asBigDecimal()) == 0;
    }
    if (isString() && bt.isString()) {
      return asString().equals(bt.asString());
    }
    return Objects.equals(value(), bt.value());
  }

  @Generated
  @Override
  public String toString() {
    return asString();
  }

  public T value() {
    return value_;
  }

  public boolean isEmpty() {
    return value_ == null;
  }

  public boolean isBoolean() {
    return value_ instanceof Boolean;
  }

  public boolean isNumber() {
    return value_ instanceof Number;
  }

  public boolean isBigInteger() {
    return value_ instanceof BigInteger;
  }

  public boolean isBigDecimal() {
    return value_ instanceof BigDecimal;
  }

  public boolean isString() {
    return value_ instanceof String;
  }

  public boolean isCollection() {
    return value_ instanceof Collection;
  }

  public boolean isDate() {
    return value_ instanceof Date;
  }

  public Boolean asBool() {
    return isBoolean() ? (Boolean) value_ : null;
  }

  public Integer asInt() {
    return isNumber() ? ((Number) value_).intValue() : null;
  }

  public Float asFloat() {
    return isNumber() ? ((Number) value_).floatValue() : null;
  }

  public Double asDouble() {
    return isNumber() ? ((Number) value_).doubleValue() : null;
  }

  public Long asLong() {
    return isNumber() ? ((Number) value_).longValue() : null;
  }

  public BigInteger asBigInteger() {
    return isBigInteger() ? (BigInteger) value_
        : isBigDecimal() && isInteger((BigDecimal) value_) ? ((BigDecimal) value_).toBigInteger()
            : null;
  }

  public BigDecimal asBigDecimal() {
    return isBigDecimal() ? (BigDecimal) value_
        : isBigInteger() ? new BigDecimal((BigInteger) value_) : null;
  }

  public String asString() {
    String str = isEmpty() ? null
        : isString() ? (String) value_
            : isBoolean() ? Boolean.toString(asBool())
                : isBigInteger() ? asBigInteger().toString(10)
                    : isBigDecimal() ? asBigDecimal().stripTrailingZeros().toString()
                        : isDate() ? DateTimeFormatter.ISO_INSTANT.format(asDate().toInstant())
                            : value_.toString();
    if (str == null) {
      return null;
    }
    if (str.startsWith("\"") && str.endsWith("\"")) {
      return str.length() == 1 ? "" /* deal with '"' */ : str.substring(1, str.length() - 1);
    }
    return str;
  }

  public Collection<?> asCollection() {
    return isCollection() ? (Collection<?>) value_ : Lists.newArrayList(value_);
  }

  public Date asDate() {
    return isDate() ? (Date) value_ : null;
  }

  /**
   * Compare two boxed types. This method returns an empty {@link Optional} if the types of the
   * underlying values are not comparable to each others.
   *
   * @param bt {@link BoxedType}
   * @return returns an empty {@link Optional} if the underlying values cannot be compared. The
   *         result of {@link Comparable#compareTo} otherwise.
   */
  public Optional<Integer> compareTo(@NotNull BoxedType<?> bt) {
    if (isEmpty() && bt.isEmpty()) {
      return Optional.of(0);
    }
    if (isEmpty()) {
      return Optional.of(-1);
    }
    if (bt.isEmpty()) {
      return Optional.of(1);
    }
    if (isBoolean() && bt.isBoolean()) {
      return Optional.of(asBool().compareTo(bt.asBool()));
    }
    if (isNumber() && bt.isNumber()) {
      return Optional.of(asBigDecimal().compareTo(bt.asBigDecimal()));
    }
    if (isString() && bt.isString()) {
      return Optional.of(asString().compareTo(bt.asString()));
    }
    if (isComparableTo(bt)) {
      Comparable obj1 = asComparable();
      Comparable obj2 = bt.asComparable();
      return Optional.of(obj1.compareTo(obj2));
    }
    return Optional.empty();
  }

  private boolean isInteger(BigDecimal bd) {
    return bd.stripTrailingZeros().scale() <= 0;
  }

  private Comparable<T> asComparable() {
    return isComparable() ? (Comparable<T>) value_ : null;
  }

  private boolean isComparable() {
    return value_ instanceof Comparable;
  }

  private boolean isComparableTo(BoxedType<?> bt) {
    if (isEmpty() || bt.isEmpty()) {
      return false;
    }
    if (!isComparable() || !bt.isComparable()) {
      return false;
    }
    return value().getClass().equals(bt.value().getClass());
  }
}
