/*
 * Copyright (c) 2011-2020 MNCC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * @author http://www.mncc.fr
 */
package com.computablefacts.nona.functions.utils;

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

  public static String format(String[][] table) {
    return format(table, false, 30);
  }

  public static String format(String[][] table, boolean leftJustify, int maxColWidth) {

    Preconditions.checkNotNull(table, "table should not be null");
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

    // Format the table
    builder.setLength(0);

    for (String[] row : tableNew) {
      builder.append(String.format(format, row));
    }
    return builder.toString();
  }
}
