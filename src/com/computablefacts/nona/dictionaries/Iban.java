package com.computablefacts.nona.dictionaries;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.computablefacts.nona.dictionaries.deserializers.BooleanDeserializer;
import com.computablefacts.nona.dictionaries.deserializers.WhiteSpaceRemovalDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

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

    try (InputStream inputStream =
        Iban.class.getClassLoader().getResourceAsStream("./data/ibans.csv")) {

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

  public String countryName() {
    return countryName_;
  }

  public String countryCode() {
    return countryCode_;
  }

  public boolean isSepaMember() {
    return isSepaMember_;
  }

  public int ibanLength() {
    return ibanLength_;
  }

  public String ibanExample() {
    return ibanExample_;
  }
}
