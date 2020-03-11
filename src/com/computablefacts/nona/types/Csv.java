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
import com.google.errorprone.annotations.Var;

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

  public String value(int rowId, String colName) {

    Preconditions.checkArgument(rowId >= 0 && rowId < nbRows(), "rowId must be >= 0 and <%s",
        nbRows());
    Preconditions.checkArgument(!Strings.isNullOrEmpty(colName),
        "colName should neither be null nor empty");

    return rows_.get(rowId).get(colName);
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

  @Override
  public int compareTo(@NotNull Csv csv) {

    @Var
    int cmp = Integer.compare(nbRows(), csv.nbRows());

    if (cmp != 0) {
      return cmp;
    }

    for (int i = 0; i < nbRows(); i++) {
      for (String col : rows_.get(i).keySet()) {

        cmp = value(i, col).compareTo(csv.value(i, col));

        if (cmp != 0) {
          return cmp;
        }
      }
    }
    return 0;
  }
}
