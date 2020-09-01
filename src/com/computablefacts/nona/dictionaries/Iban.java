package com.computablefacts.nona.dictionaries;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.computablefacts.nona.Generated;
import com.computablefacts.nona.dictionaries.deserializers.BooleanDeserializer;
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
 * IBAN - International Bank Account Number
 */
@CheckReturnValue
final public class Iban {

  @JsonProperty("country_name")
  @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
  private String countryName_;

  @JsonProperty("country_code")
  @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
  private String countryCode_;

  @JsonProperty("sepa_member")
  @JsonDeserialize(using = BooleanDeserializer.class)
  private boolean isSepaMember_;

  @JsonProperty("iban_length")
  private int ibanLength_;

  @JsonProperty("iban_example")
  @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
  private String ibanExample_;

  private Iban() {}

  public static Map<String, Iban> load() {

    Map<String, Iban> map = new HashMap<>();

    try (InputStream inputStream = Iban.class.getResourceAsStream("/data/ibans.csv")) {

      CsvSchema schema = CsvSchema.emptySchema().withHeader().withColumnSeparator(',');
      CsvMapper mapper = new CsvMapper();

      try (MappingIterator<Iban> iterator =
          mapper.readerFor(Iban.class).with(schema).readValues(inputStream)) {

        while (iterator.hasNext()) {
          Iban iban = iterator.next();
          map.put(iban.countryCode(), iban);
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
    Iban other = (Iban) obj;
    return Objects.equal(countryName_, other.countryName_)
        && Objects.equal(countryCode_, other.countryCode_)
        && Objects.equal(isSepaMember_, other.isSepaMember_)
        && Objects.equal(ibanLength_, other.ibanLength_)
        && Objects.equal(ibanExample_, other.ibanExample_);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(countryName_, countryCode_, isSepaMember_, ibanLength_, ibanExample_);
  }

  @Generated
  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("countryName", countryName_)
        .add("countryCode", countryCode_).add("isSepaMember", isSepaMember_)
        .add("ibanLength", ibanLength_).add("ibanExample", ibanExample_).omitNullValues()
        .toString();
  }

  @Generated
  public String countryName() {
    return countryName_;
  }

  @Generated
  public String countryCode() {
    return countryCode_;
  }

  @Generated
  public boolean isSepaMember() {
    return isSepaMember_;
  }

  @Generated
  public int ibanLength() {
    return ibanLength_;
  }

  @Generated
  public String ibanExample() {
    return ibanExample_;
  }
}
