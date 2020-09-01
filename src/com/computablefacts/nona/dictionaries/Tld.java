package com.computablefacts.nona.dictionaries;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.errorprone.annotations.CheckReturnValue;

/**
 * TLD - Top-Level Domain
 */
@CheckReturnValue
final public class Tld {

  private Tld() {}

  public static Set<String> load() {

    Set<String> set = new HashSet<>();

    try (InputStream inputStream = Tld.class.getResourceAsStream("/data/tlds.csv")) {

      CsvSchema schema = CsvSchema.emptySchema().withHeader().withColumnSeparator(',');
      CsvMapper mapper = new CsvMapper();

      try (MappingIterator<Map<String, String>> iterator =
          mapper.readerFor(Map.class).with(schema).readValues(inputStream)) {

        while (iterator.hasNext()) {
          Map<String, String> tld = iterator.next();
          set.add(tld.get("tld"));
        }
      } catch (IOException e) {
        // TODO
      }
    } catch (IOException e) {
      // TODO
    }
    return set;
  }
}
