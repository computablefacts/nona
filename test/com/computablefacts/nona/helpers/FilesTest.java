package com.computablefacts.nona.helpers;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

@Deprecated
public class FilesTest {

  @Test
  public void testCreate1() throws IOException {

    File file = java.nio.file.Files.createTempFile("utf8-", ".txt").toFile();

    Files.delete(file);
    Files.create(file, "1\n2\n3");

    Assert.assertEquals("1\n2\n3", Files.load(file, StandardCharsets.UTF_8));
  }

  @Test
  public void testCreate2() throws IOException {

    File file = java.nio.file.Files.createTempFile("utf8-", ".txt").toFile();

    Files.delete(file);
    Files.create(file, Lists.newArrayList("1", "2", "3"));

    Assert.assertEquals("1\n2\n3", Files.load(file, StandardCharsets.UTF_8));

  }

  @Test
  public void testAppend1() throws IOException {

    File file = java.nio.file.Files.createTempFile("utf8-", ".txt").toFile();

    Files.delete(file);
    Files.create(file, "1\n2");
    Files.append(file, "\n3");

    Assert.assertEquals("1\n2\n3", Files.load(file, StandardCharsets.UTF_8));
  }

  @Test
  public void testAppend2() throws IOException {

    File file = java.nio.file.Files.createTempFile("utf8-", ".txt").toFile();

    Files.delete(file);
    Files.create(file, Lists.newArrayList("1", "2"));
    Files.append(file, Lists.newArrayList("3"));

    Assert.assertEquals("1\n2\n3", Files.load(file, StandardCharsets.UTF_8));
  }

  @Test
  public void testLineStream() throws IOException {

    // Create test file
    List<String> input = IntStream.range(1, 100).boxed().map(i -> Integer.toString(i, 10))
        .collect(Collectors.toList());
    File fileOriginal = java.nio.file.Files.createTempFile("utf8-", ".txt").toFile();
    File fileGzip = new File(fileOriginal.getAbsoluteFile() + ".gz");
    com.computablefacts.nona.helpers.Files.append(fileOriginal, input);

    // Test reading from GZ file
    com.computablefacts.nona.helpers.Files.gzip(fileOriginal, fileGzip);
    com.computablefacts.nona.helpers.Files.delete(fileOriginal);

    List<String> outputGz1 = com.computablefacts.nona.helpers.Files
        .compressedLineStream(fileGzip, StandardCharsets.UTF_8)
        .filter(row -> row.getKey().equals(Integer.parseInt(row.getValue(), 10)))
        .map(row -> row.getValue()).collect(Collectors.toList());

    Assert.assertEquals(input, outputGz1);

    List<String> outputGz2 = Splitter.on('\n').trimResults().omitEmptyStrings()
        .splitToList(Files.loadCompressed(fileGzip, StandardCharsets.UTF_8));

    Assert.assertEquals(input, outputGz2);

    // Test reading from file
    com.computablefacts.nona.helpers.Files.gunzip(fileGzip, fileOriginal);
    com.computablefacts.nona.helpers.Files.delete(fileGzip);

    List<String> outputOriginal1 =
        com.computablefacts.nona.helpers.Files.lineStream(fileOriginal, StandardCharsets.UTF_8)
            .filter(row -> row.getKey().equals(Integer.parseInt(row.getValue(), 10)))
            .map(row -> row.getValue()).collect(Collectors.toList());

    Assert.assertEquals(input, outputOriginal1);

    List<String> outputOriginal2 = Splitter.on('\n').trimResults().omitEmptyStrings()
        .splitToList(Files.load(fileOriginal, StandardCharsets.UTF_8));

    Assert.assertEquals(input, outputOriginal2);

    // Cleanup
    com.computablefacts.nona.helpers.Files.delete(fileOriginal);
  }
}
