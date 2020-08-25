package com.computablefacts.nona.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Var;

/**
 * Based on @{link
 * https://itsallbinary.com/java-printing-to-console-in-table-format-simple-code-with-flexible-width-left-align-header-separator-line/}
 */
@CheckReturnValue
final public class AsciiTable {

  private AsciiTable() {}

  public static String format(String[][] table, boolean hasHeader) {
    return format(table, hasHeader, false, 30);
  }

  public static String format(String[][] table, boolean hasHeader, boolean leftJustify,
      int maxColWidth) {

    Preconditions.checkNotNull(table, "table should not be null");
    Preconditions.checkArgument(table.length > 0, "table should have at least one row");
    Preconditions.checkArgument(table[0].length > 0, "table should have at least one column");
    Preconditions.checkArgument(maxColWidth > 0, "maxColWidth must be > 0");

    // Wrap all rows
    List<String[]> tableTmp = new ArrayList<>(table.length);

    for (int i = 0; i < table.length; i++) {

      String[] row = table[i];
      @Var
      boolean needExtraRow = false;
      @Var
      int splitRow = 0;

      do {

        needExtraRow = false;
        String[] newRow = new String[row.length];

        for (int j = 0; j < row.length; j++) {

          if (Strings.nullToEmpty(row[j]).length() < maxColWidth) {
            newRow[j] = splitRow == 0 ? Strings.nullToEmpty(row[j]) : "";
          } else if (row[j].length() > (splitRow * maxColWidth)) {
            int end = Math.min(row[j].length(), (splitRow * maxColWidth) + maxColWidth);
            newRow[j] = row[j].substring(splitRow * maxColWidth, end);
            needExtraRow = true;
          } else {
            newRow[j] = "";
          }
        }

        tableTmp.add(newRow);

        if (needExtraRow) {
          splitRow++;
        }
      } while (needExtraRow);
    }

    String[][] tableNew = new String[tableTmp.size()][tableTmp.get(0).length];

    for (int i = 0; i < tableTmp.size(); i++) {
      tableNew[i] = tableTmp.get(i);
    }

    // Calculate the appropriate length of each column by looking at the width of data in each cell
    Map<Integer, Integer> columnLengths = new HashMap<>();

    for (int i = 0; i < tableNew.length; i++) {
      for (int j = 0; j < tableNew[i].length; j++) {
        if (!columnLengths.containsKey(j)) {
          columnLengths.put(j, 0);
        }
        if (columnLengths.get(j) < Strings.nullToEmpty(tableNew[i][j]).length()) {
          columnLengths.put(j, tableNew[i][j].length());
        }
      }
    }

    // Prepare the table row format
    StringBuilder builder = new StringBuilder();
    String emptyCell = leftJustify ? "-" : "";
    String columnSeparator = "|";

    for (int i = 0; i < columnLengths.size(); i++) {
      builder.append(columnSeparator).append(" %").append(emptyCell).append(columnLengths.get(i))
          .append("s ");
    }

    builder.append(columnSeparator).append("\n");
    String format = builder.toString();

    // Prepare row separator
    builder.setLength(0);

    for (int i = 0; i < columnLengths.size(); i++) {
      builder.append("+--");
      for (int j = 0; j < columnLengths.get(i); j++) {
        builder.append("-");
      }
    }

    builder.append("+\n");
    String rowSeparator = builder.toString();

    // Format the table
    builder.setLength(0);
    builder.append(rowSeparator);

    for (int i = 0; i < tableNew.length; i++) {

      builder.append(String.format(format, tableNew[i]));

      if (hasHeader && i == 0) {
        builder.append(rowSeparator);
      }
    }
    return builder.append(rowSeparator).toString();
  }
}
