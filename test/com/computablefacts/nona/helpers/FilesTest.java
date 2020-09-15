package com.computablefacts.nona.helpers;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;

public class FilesTest {

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

    List<String> outputGz = com.computablefacts.nona.helpers.Files
        .compressedLineStream(new File(fileOriginal.getAbsoluteFile() + ".gz"),
            StandardCharsets.UTF_8)
        .filter(row -> row.getKey().equals(Integer.parseInt(row.getValue(), 10)))
        .map(row -> row.getValue()).collect(Collectors.toList());

    Assert.assertEquals(input, outputGz);

    // Test reading from file
    com.computablefacts.nona.helpers.Files.gunzip(fileGzip, fileOriginal);
    com.computablefacts.nona.helpers.Files.delete(fileGzip);

    List<String> outputOriginal =
        com.computablefacts.nona.helpers.Files.lineStream(fileOriginal, StandardCharsets.UTF_8)
            .filter(row -> row.getKey().equals(Integer.parseInt(row.getValue(), 10)))
            .map(row -> row.getValue()).collect(Collectors.toList());

    Assert.assertEquals(input, outputOriginal);

    // Cleanup
    com.computablefacts.nona.helpers.Files.delete(fileOriginal);
  }
}
