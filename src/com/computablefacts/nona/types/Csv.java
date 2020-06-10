package com.computablefacts.nona.types;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
final public class Csv implements Comparable<Csv> {

  private final List<Map<String, String>> rows_;

  public Csv(List<Map<String, String>> list) {

    Preconditions.checkNotNull(list, "list should not be null");

    rows_ = new ArrayList<>(list);
  }

  public static Csv create(String csv) {
    try {

      CsvMapper csvMapper = new CsvMapper();
      CsvSchema csvSchema = CsvSchema.emptySchema().withHeader();
      MappingIterator<Map<String, String>> mappingIterator =
          csvMapper.reader().forType(Map.class).with(csvSchema).readValues(csv);

      return new Csv(mappingIterator.readAll());
    } catch (IOException e) {
      // TODO
    }
    return null;
  }

  public int nbRows() {
    return rows_.size();
  }

  public Map<String, String> row(int row) {

    Preconditions.checkArgument(row >= 0 && row < nbRows(), "row must be >= 0 and <%s", nbRows());

    return rows_.get(row);
  }

  public String value(int row, String colName) {

    Preconditions.checkArgument(!Strings.isNullOrEmpty(colName),
        "colName should neither be null nor empty");

    return row(row).get(colName);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Csv other = (Csv) obj;
    return Objects.equals(rows_, other.rows_);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(rows_);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("rows", rows_).omitNullValues().toString();
  }

  /**
   * WARNING : DO NOT USE.
   *
   * This method exists to ensure the {@link Csv} datatype can be boxed using {@link BoxedType}.
   */
  @Override
  public int compareTo(@NotNull Csv csv) {
    return 0;
  }
}
