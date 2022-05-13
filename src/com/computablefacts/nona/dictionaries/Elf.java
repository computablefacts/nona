package com.computablefacts.nona.dictionaries;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import com.computablefacts.asterix.Generated;
import com.computablefacts.nona.dictionaries.deserializers.WhiteSpaceRemovalDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Splitter;
import com.google.errorprone.annotations.CheckReturnValue;

/**
 * ELF - Entity Legal Form
 */
@CheckReturnValue
final public class Elf {

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

  private Elf() {}

  public static List<Elf> load() {

    List<Elf> list = new ArrayList<>();

    try (InputStream inputStream = Elf.class.getResourceAsStream("/data/elfs.csv")) {

      CsvSchema schema = CsvSchema.emptySchema().withHeader().withColumnSeparator(',');
      CsvMapper mapper = new CsvMapper();

      try (MappingIterator<Elf> iterator =
          mapper.readerFor(Elf.class).with(schema).readValues(inputStream)) {

        while (iterator.hasNext()) {
          Elf cc = iterator.next();
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

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Elf other = (Elf) obj;
    return Objects.equal(elfCode_, other.elfCode_)
        && Objects.equal(countryName_, other.countryName_)
        && Objects.equal(countryCode_, other.countryCode_)
        && Objects.equal(jurisdictionOfFormation_, other.jurisdictionOfFormation_)
        && Objects.equal(countrySubdivisionCode_, other.countrySubdivisionCode_)
        && Objects.equal(legalFormNameLocal_, other.legalFormNameLocal_)
        && Objects.equal(language_, other.language_)
        && Objects.equal(languageCode_, other.languageCode_)
        && Objects.equal(legalFormNameTransliterated_, other.legalFormNameTransliterated_)
        && Objects.equal(abbreviationLocal_, other.abbreviationLocal_)
        && Objects.equal(abbreviationTransliterated_, other.abbreviationTransliterated_)
        && Objects.equal(dateCreated_, other.dateCreated_)
        && Objects.equal(elfStatus_, other.elfStatus_)
        && Objects.equal(modification_, other.modification_)
        && Objects.equal(modificationDate_, other.modificationDate_)
        && Objects.equal(reason_, other.reason_);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(elfCode_, countryName_, countryCode_, jurisdictionOfFormation_,
        countrySubdivisionCode_, legalFormNameLocal_, language_, languageCode_,
        legalFormNameTransliterated_, abbreviationLocal_, abbreviationTransliterated_, dateCreated_,
        elfStatus_, modification_, modificationDate_, reason_);
  }

  @Generated
  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("elfCode", elfCode_)
        .add("countryName", countryName_).add("countryCode", countryCode_)
        .add("jurisdictionOfFormation", jurisdictionOfFormation_)
        .add("countrySubdivisionCode", countrySubdivisionCode_)
        .add("legalFormNameLocal", legalFormNameLocal_).add("language", language_)
        .add("languageCode", languageCode_)
        .add("legalFormNameTransliterated", legalFormNameTransliterated_)
        .add("abbreviationLocal", abbreviationLocal_)
        .add("abbreviationTransliterated", abbreviationTransliterated_)
        .add("dateCreated", dateCreated_).add("elfStatus", elfStatus_)
        .add("modification", modification_).add("modificationDate", modificationDate_)
        .add("reason", reason_).omitNullValues().toString();
  }

  @Generated
  public String elfCode() {
    return elfCode_;
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
  public String jurisdictionOfFormation() {
    return jurisdictionOfFormation_;
  }

  @Generated
  public String countrySubdivisionCode() {
    return countrySubdivisionCode_;
  }

  @Generated
  public String legalFormNameLocal() {
    return legalFormNameLocal_;
  }

  @Generated
  public String language() {
    return language_;
  }

  @Generated
  public String languageCode() {
    return languageCode_;
  }

  @Generated
  public String legalFormNameTransliterated() {
    return legalFormNameTransliterated_;
  }

  @Generated
  public Set<String> abbreviationLocal() {
    return new HashSet<>(Splitter.on(';').splitToList(abbreviationLocal_));
  }

  @Generated
  public Set<String> abbreviationTransliterated() {
    return new HashSet<>(Splitter.on(';').splitToList(abbreviationTransliterated_));
  }

  @Generated
  public Date dateCreated() {
    return dateCreated_;
  }

  @Generated
  public String elfStatus() {
    return elfStatus_;
  }

  @Generated
  public String modification() {
    return modification_;
  }

  @Generated
  public Date modificationDate() {
    return modificationDate_;
  }

  @Generated
  public String reason() {
    return reason_;
  }
}
