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

final public class FrenchBic {

  @JsonProperty("name")
  @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
  private String name_;

  @JsonProperty("city")
  @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
  private String city_;

  @JsonProperty("swift_code")
  @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
  private String swiftCode_;

  private FrenchBic() {}

  public static Map<String, FrenchBic> load() {

    Map<String, FrenchBic> map = new HashMap<>();

    try (InputStream inputStream =
        FrenchBic.class.getClassLoader().getResourceAsStream("./data/french-bics.csv")) {

      CsvSchema schema = CsvSchema.emptySchema().withHeader().withColumnSeparator(',');
      CsvMapper mapper = new CsvMapper();

      try (MappingIterator<FrenchBic> iterator =
          mapper.readerFor(FrenchBic.class).with(schema).readValues(inputStream)) {

        while (iterator.hasNext()) {
          FrenchBic cbf = iterator.next();
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
