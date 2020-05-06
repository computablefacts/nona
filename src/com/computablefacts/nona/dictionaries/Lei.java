package com.computablefacts.nona.dictionaries;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import com.computablefacts.nona.dictionaries.deserializers.WhiteSpaceRemovalDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.errorprone.annotations.Var;

final public class Lei {

  @JsonProperty("lei")
  @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
  private String lei_;

  @JsonProperty("legalName")
  @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
  private String legalName_;

  @JsonProperty("countryCode")
  @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
  private String countryCode_;

  @JsonProperty("bic")
  @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
  private String bic_;

  private Lei() {}

  public static List<Lei> load() {

    List<Lei> list = new ArrayList<>();

    try (InputStream inputStream =
        Lei.class.getClassLoader().getResourceAsStream("./data/mfis.csv")) {

      CsvSchema schema = CsvSchema.emptySchema().withHeader().withColumnSeparator(',');
      CsvMapper mapper = new CsvMapper();

      try (MappingIterator<Lei> iterator =
          mapper.readerFor(Lei.class).with(schema).readValues(inputStream)) {

        while (iterator.hasNext()) {
          Lei lei = iterator.next();
          list.add(lei);
        }
      } catch (IOException e) {
        // TODO
      }
    } catch (IOException e) {
      // TODO
    }
    return list;
  }

  /**
   * Load LEI records.
   *
   * @param fileLeiCdf XML downloaded from
   *        https://www.gleif.org/en/lei-data/gleif-concatenated-file/download-the-concatenated-file
   * @param fileLeiToBic CSV downloaded from
   *        https://www.gleif.org/en/lei-data/lei-mapping/download-bic-to-lei-relationship-files
   * @return LEI records with BIC information (if any).
   */
  public static List<Lei> legalIdentityIdentifiers(String fileLeiCdf, String fileLeiToBic) {

    Map<String, String> leiToBic = new HashMap<>();

    if (fileLeiToBic != null) {
      try (InputStream inputStream = new FileInputStream(new File(fileLeiToBic))) {

        CsvSchema schema = CsvSchema.emptySchema().withHeader().withColumnSeparator(',');
        CsvMapper mapper = new CsvMapper();

        try (MappingIterator<Map<String, String>> iterator =
            mapper.readerFor(Map.class).with(schema).readValues(inputStream)) {

          while (iterator.hasNext()) {
            Map<String, String> m = iterator.next();
            leiToBic.put(m.get("LEI").trim(), m.get("BIC").trim());
          }
        } catch (IOException e) {
          // TODO
        }
      } catch (IOException e) {
        // TODO
      }
    }

    List<Lei> records = new ArrayList<>();

    try {

      XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
      XMLEventReader reader =
          xmlInputFactory.createXMLEventReader(new FileInputStream(new File(fileLeiCdf)));

      @Var
      Lei record = null;

      while (reader.hasNext()) {

        @Var
        XMLEvent nextEvent = reader.nextEvent();

        if (nextEvent.isStartElement()) {

          StartElement startElement = nextEvent.asStartElement();

          switch (startElement.getName().getLocalPart()) {
            case "LEIRecord":
              record = new Lei();
              break;
            case "LEI":
              if (record != null) {
                nextEvent = reader.nextEvent();
                record.lei_ = nextEvent.asCharacters().getData();
              }
              break;
            case "LegalName":
              if (record != null) {
                nextEvent = reader.nextEvent();
                record.legalName_ = nextEvent.asCharacters().getData();
              }
              break;
            case "Country":
              if (record != null) {
                nextEvent = reader.nextEvent();
                record.countryCode_ = nextEvent.asCharacters().getData();
              }
              break;
            default:
              break;
          }
        }
        if (nextEvent.isEndElement()) {

          EndElement endElement = nextEvent.asEndElement();

          if ("LEIRecord".equals(endElement.getName().getLocalPart()) && record != null) {

            if (leiToBic.containsKey(record.lei())) {
              record.bic_ = leiToBic.get(record.lei());
            }

            records.add(record);
            record = null;
          }
        }
      }
    } catch (FileNotFoundException | XMLStreamException e) {
      // TODO
    }
    return records;
  }

  public static List<Lei> monetaryFinancialInstitutions(List<Lei> records) {
    return records.stream().filter(r -> !Strings.isNullOrEmpty(r.bic()))
        .collect(Collectors.toList());
  }

  public static void monetaryFinancialInstitutionsToCsv(List<Lei> records, String file) {
    try {
      CsvMapper mapper = new CsvMapper();
      mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);

      CsvSchema schema = CsvSchema.builder().addColumn("lei").addColumn("bic")
          .addColumn("legalName").addColumn("countryCode").build();

      ObjectWriter writer = mapper.writerFor(Lei.class).with(schema);
      writer.writeValues(new File(file)).writeAll(monetaryFinancialInstitutions(records));
    } catch (IOException e) {
      // TODO
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Lei other = (Lei) obj;
    return lei_.equals(other.lei_) && legalName_.equals(other.legalName_)
        && countryCode_.equals(other.countryCode_) && bic_.equals(other.bic_);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(lei_, legalName_, countryCode_, bic_);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("lei", lei_).add("legalName", legalName_)
        .add("countryCode", countryCode_).add("bic", bic_).omitNullValues().toString();
  }

  public String lei() {
    return lei_;
  }

  public String legalName() {
    return legalName_;
  }

  public String countryCode() {
    return countryCode_;
  }

  public String bic() {
    return Strings.nullToEmpty(bic_);
  }

  public String bic8() {
    return Strings.isNullOrEmpty(bic_) ? "" : bic_.substring(0, 8);
  }
}
