package com.computablefacts.nona.dictionaries;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.computablefacts.nona.Generated;
import com.computablefacts.nona.dictionaries.deserializers.WhiteSpaceRemovalDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.errorprone.annotations.CheckReturnValue;

/**
 * BIC - Bank Identifier Code
 */
@CheckReturnValue
final public class BicFr {

  @JsonProperty("name")
  @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
  private String name_;

  @JsonProperty("city")
  @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
  private String city_;

  @JsonProperty("swift_code")
  @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
  private String swiftCode_;

  private BicFr() {}

  public static Map<String, BicFr> load() {

    Map<String, BicFr> map = new HashMap<>();

    try (InputStream inputStream = BicFr.class.getResourceAsStream("/data/french-bics.csv")) {

      CsvSchema schema = CsvSchema.emptySchema().withHeader().withColumnSeparator(',');
      CsvMapper mapper = new CsvMapper();

      try (MappingIterator<BicFr> iterator =
          mapper.readerFor(BicFr.class).with(schema).readValues(inputStream)) {

        while (iterator.hasNext()) {
          BicFr cbf = iterator.next();
          map.put(cbf.swiftCode(), cbf);
        }
      } catch (IOException e) {
        // TODO
      }
    } catch (IOException e) {
      // TODO
    }
    return map;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    BicFr other = (BicFr) obj;
    return Objects.equal(name_, other.name_) && Objects.equal(city_, other.city_)
        && Objects.equal(swiftCode_, other.swiftCode_);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(name_, city_, swiftCode_);
  }

  @Generated
  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("name", name_).add("city", city_)
        .add("swiftCode", swiftCode_).omitNullValues().toString();
  }

  public String name() {
    return name_;
  }

  public String city() {
    return city_;
  }

  public String swiftCode() {
    return swiftCode_;
  }
}
