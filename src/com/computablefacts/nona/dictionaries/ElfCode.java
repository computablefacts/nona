package com.computablefacts.nona.dictionaries;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.computablefacts.nona.dictionaries.deserializers.WhiteSpaceRemovalDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

final public class ElfCode {

  @JsonProperty("ELF Code")
  @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
  private String elfCode_;

  @JsonProperty("Country of formation")
  @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
  private String countryName_;

  @JsonProperty("Country Code (ISO 3166-1)")
  @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
  private String countryCode_;

  @JsonProperty("Jurisdiction of formation")
  @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
  private String jurisdictionOfFormation_;

  @JsonProperty("Country sub-division code (ISO 3166-2)")
  @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
  private String countrySubdivisionCode_;

  @JsonProperty("Entity Legal Form name Local name")
  @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
  private String legalFormNameLocal_;

  @JsonProperty("Language")
  @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
  private String language_;

  @JsonProperty("Language Code (ISO 639-1)")
  @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
  private String languageCode_;

  @JsonProperty("Entity Legal Form name Transliterated name (per ISO 01-140-10)")
  @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
  private String legalFormNameTransliterated_;

  @JsonProperty("Abbreviations Local language")
  @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
  private String abbreviationLocal_;

  @JsonProperty("Abbreviations transliterated")
  @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
  private String abbreviationTransliterated_;

  @JsonProperty("Date created YYYY-MM-DD (ISO 8601)")
  private Date dateCreated_;

  @JsonProperty("ELF Status ACTV/INAC")
  @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
  private String elfStatus_;

  @JsonProperty("Modification")
  @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
  private String modification_;

  @JsonProperty("Modification date YYYY-MM-DD (ISO 8601)")
  private Date modificationDate_;

  @JsonProperty("Reason")
  @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
  private String reason_;

  private ElfCode() {}

  public static List<ElfCode> load() {

    List<ElfCode> list = new ArrayList<>();

    try (InputStream inputStream =
        ElfCode.class.getClassLoader().getResourceAsStream("./data/elf-codes.csv")) {

      CsvSchema schema = CsvSchema.emptySchema().withHeader().withColumnSeparator(',');
      CsvMapper mapper = new CsvMapper();

      try (MappingIterator<ElfCode> iterator =
          mapper.readerFor(ElfCode.class).with(schema).readValues(inputStream)) {

        while (iterator.hasNext()) {
          ElfCode cc = iterator.next();
          list.add(cc);
        }
      } catch (IOException e) {
        // TODO
      }
    } catch (IOException e) {
      // TODO
    }
    return list;
  }

  public String elfCode() {
    return elfCode_;
  }

  public String countryName() {
    return countryName_;
  }

  public String countryCode() {
    return countryCode_;
  }

  public String jurisdictionOfFormation() {
    return jurisdictionOfFormation_;
  }

  public String countrySubdivisionCode() {
    return countrySubdivisionCode_;
  }

  public String legalFormNameLocal() {
    return legalFormNameLocal_;
  }

  public String language() {
    return language_;
  }

  public String languageCode() {
    return languageCode_;
  }

  public String legalFormNameTransliterated() {
    return legalFormNameTransliterated_;
  }

  public String abbreviationLocal() {
    return abbreviationLocal_;
  }

  public String abbreviationTransliterated() {
    return abbreviationTransliterated_;
  }

  public Date dateCreated() {
    return dateCreated_;
  }

  public String elfStatus() {
    return elfStatus_;
  }

  public String modification() {
    return modification_;
  }

  public Date modificationDate() {
    return modificationDate_;
  }

  public String reason() {
    return reason_;
  }
}
