package com.computablefacts.nona.helpers;

import org.junit.Assert;
import org.junit.Test;

public class AsciiTableTest {

  @Test(expected = NullPointerException.class)
  public void testNullTable() {
    String table = AsciiTable.format(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTableWithoutRows() {
    String table = AsciiTable.format(new String[0][0]);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTableWithoutColumns() {
    String table = AsciiTable.format(new String[1][0]);
  }

  @Test
  public void testTableWithNullValues() {

    String[][] testTable =
        new String[][] {{"id", "First Name", "Last Name", "Age"}, {"1", null, null, null}};
    String table = AsciiTable.format(testTable);

    Assert.assertEquals(
        "| id | First Name | Last Name | Age |\n" + "|  1 |            |           |     |\n",
        table);
  }

  @Test
  public void testRightJustifiedTable() {

    String[][] testTable =
        new String[][] {{"id", "First Name", "Last Name", "Age"}, {"1", "John", "Johnson", "45"},
            {"2", "Tom", "", "35"}, {"3", "Rose", "Johnson", "22"}, {"4", "Jimmy", "Kimmel", ""}};
    String table = AsciiTable.format(testTable);

    Assert.assertEquals("| id | First Name | Last Name | Age |\n"
        + "|  1 |       John |   Johnson |  45 |\n" + "|  2 |        Tom |           |  35 |\n"
        + "|  3 |       Rose |   Johnson |  22 |\n" + "|  4 |      Jimmy |    Kimmel |     |\n",
        table);
  }

  @Test
  public void testLeftJustifiedTable() {

    String[][] testTable =
        new String[][] {{"id", "First Name", "Last Name", "Age"}, {"1", "John", "Johnson", "45"},
            {"2", "Tom", "", "35"}, {"3", "Rose", "Johnson", "22"}, {"4", "Jimmy", "Kimmel", ""}};
    String table = AsciiTable.format(testTable, true, 80);

    Assert.assertEquals("| id | First Name | Last Name | Age |\n"
        + "| 1  | John       | Johnson   | 45  |\n" + "| 2  | Tom        |           | 35  |\n"
        + "| 3  | Rose       | Johnson   | 22  |\n" + "| 4  | Jimmy      | Kimmel    |     |\n",
        table);
  }

  @Test
  public void testDataWrapping() {

    String[][] testTable = new String[][] {{"id", "First Name", "Last Name", "Age", "Profile"},
        {"1", "John", "Johnson", "45", "My name is John Johnson. My id is 1. My age is 45."},
        {"2", "Tom", "", "35", "My name is Tom. My id is 2. My age is 35."},
        {"3", "Rose",
            "Johnson Johnson Johnson Johnson Johnson Johnson Johnson Johnson Johnson Johnson", "22",
            "My name is Rose Johnson. My id is 3. My age is 22."},
        {"4", "Jimmy", "Kimmel", "",
            "My name is Jimmy Kimmel. My id is 4. My age is not specified. "
                + "I am the host of the late night show. I am not fan of Matt Damon. "}};
    String table = AsciiTable.format(testTable);

    Assert.assertEquals(
        "| id | First Name |                      Last Name | Age |                        Profile |\n"
            + "|  1 |       John |                        Johnson |  45 | My name is John Johnson. My id |\n"
            + "|    |            |                                |     |            is 1. My age is 45. |\n"
            + "|    |            |                                |     |                                |\n"
            + "|  2 |        Tom |                                |  35 | My name is Tom. My id is 2. My |\n"
            + "|    |            |                                |     |                     age is 35. |\n"
            + "|    |            |                                |     |                                |\n"
            + "|  3 |       Rose | Johnson Johnson Johnson Johnso |  22 | My name is Rose Johnson. My id |\n"
            + "|    |            | n Johnson Johnson Johnson John |     |            is 3. My age is 22. |\n"
            + "|    |            |            son Johnson Johnson |     |                                |\n"
            + "|    |            |                                |     |                                |\n"
            + "|  4 |      Jimmy |                         Kimmel |     | My name is Jimmy Kimmel. My id |\n"
            + "|    |            |                                |     |  is 4. My age is not specified |\n"
            + "|    |            |                                |     | . I am the host of the late ni |\n"
            + "|    |            |                                |     | ght show. I am not fan of Matt |\n"
            + "|    |            |                                |     |                        Damon.  |\n"
            + "|    |            |                                |     |                                |\n",
        table);
  }
}
