package com.computablefacts.nona.dictionaries;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.computablefacts.nona.dictionaries.deserializers.WhiteSpaceRemovalDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

final public class CountryCode {

  @JsonProperty("country")
  @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
  private String name_;

  @JsonProperty("alpha2")
  @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
  private String alpha2_;

  @JsonProperty("alpha3")
  @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
  private String alpha3_;

  @JsonProperty("numeric")
  private int numeric_;

  private CountryCode() {}

  public static Map<String, CountryCode> load() {

    Map<String, CountryCode> map = new HashMap<>();

    try (InputStream inputStream =
        CountryCode.class.getClassLoader().getResourceAsStream("./data/country-codes.csv")) {

      CsvSchema schema = CsvSchema.emptySchema().withHeader().withColumnSeparator(',');
      CsvMapper mapper = new CsvMapper();

      try (MappingIterator<CountryCode> iterator =
          mapper.readerFor(CountryCode.class).with(schema).readValues(inputStream)) {

        while (iterator.hasNext()) {
          CountryCode cc = iterator.next();
          map.put(cc.alpha2(), cc);
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
    CountryCode other = (CountryCode) obj;
    return name_.equals(other.name_) && alpha2_.equals(other.alpha2_)
        && alpha3_.equals(other.alpha3_) && numeric_ == other.numeric_;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(name_, alpha2_, alpha3_, numeric_);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("name", name_).add("alpha2", alpha2_)
        .add("alpha3", alpha3_).add("numeric", numeric_).omitNullValues().toString();
  }

  public String name() {
    return name_;
  }

  public String alpha2() {
    return alpha2_;
  }

  public String alpha3() {
    return alpha3_;
  }

  public int numeric() {
    return numeric_;
  }
}
