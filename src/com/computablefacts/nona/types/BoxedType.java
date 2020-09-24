package com.computablefacts.nona.types;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.validation.constraints.NotNull;

import com.computablefacts.nona.Generated;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
final public class BoxedType<T extends Comparable> implements Comparable<BoxedType<T>> {

  private static final LoadingCache<String, BoxedType<?>> cache_ =
      CacheBuilder.newBuilder().recordStats().maximumSize(100).expireAfterWrite(1, TimeUnit.HOURS)
          .build(new CacheLoader<String, BoxedType<?>>() {

            @Override
            public BoxedType<?> load(String text) {

              if ("true".equals(text.toLowerCase())) {
                return new BoxedType<>(true);
              }
              if ("false".equals(text.toLowerCase())) {
                return new BoxedType<>(false);
              }

              try {
                return new BoxedType<>(new BigInteger(text));
              } catch (NumberFormatException ex) {
                // FALL THROUGH
              }

              try {

                BoxedType bt = new BoxedType<>(new BigDecimal(text));

                // text matching \d+[.] should be interpreted as string
                // text matching [.]\d+ should be interpreted as string
                if (!text.trim().endsWith(".") && !text.trim().startsWith(".")) {
                  return bt;
                }
              } catch (NumberFormatException ex) {
                // FALL THROUGH
              }

              return new BoxedType<>(text);
            }
          });
  private final T value_; // T in {Boolean, BigInteger, BigDecimal, String}

  private BoxedType(T value) {
    value_ = value;
  }

  public static BoxedType<?> create(Comparable<?> value) {

    if (value instanceof Boolean || value instanceof BigInteger || value instanceof BigDecimal) {
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
      return cache_.getUnchecked((String) value); // type coercion
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
    return Objects.hash(value_);
  }

  @Override
  public boolean equals(Object o) {

    if (o == null) {
      return false;
    }
    if (!(o instanceof BoxedType)) {
      return false;
    }

    BoxedType bt = (BoxedType) o;

    if (isBoolean() && bt.isBoolean()) {
      return asBool().equals(bt.asBool());
    }
    if (isNumber() && bt.isNumber()) {
      return asBigDecimal().compareTo(bt.asBigDecimal()) == 0;
    }
    if (isString() && bt.isString()) {
      return value_.equals(bt.value_);
    }
    return com.google.common.base.Objects.equal(value_, bt.value_);
  }

  @Override
  public int compareTo(@NotNull BoxedType<T> object) {
    if (isBoolean() && object.isBoolean()) {
      return asBool().compareTo(object.asBool());
    }
    if (isNumber() && object.isNumber()) {
      return asBigDecimal().compareTo(object.asBigDecimal());
    }
    if (isString() && object.isString()) {
      return asString().compareTo(object.asString());
    }
    return value_ == null && object.value_ == null ? 0
        : value_ == null ? -1 : object.value_ == null ? 1 : value_.compareTo(object.value_);
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
    return isEmpty() ? null
        : isString() ? (String) value_
            : isBoolean() ? Boolean.toString(asBool())
                : isBigInteger() ? asBigInteger().toString(10)
                    : isBigDecimal() ? asBigDecimal().stripTrailingZeros().toString()
                        : value_.toString();
  }

  private boolean isInteger(BigDecimal bd) {
    return bd.stripTrailingZeros().scale() <= 0;
  }
}
