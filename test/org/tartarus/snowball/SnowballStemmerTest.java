package org.tartarus.snowball;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.junit.Test;
import org.tartarus.snowball.ext.englishStemmer;
import org.tartarus.snowball.ext.frenchStemmer;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.common.base.Preconditions;

public class SnowballStemmerTest {

  @Test
  public void testEnglishStemmer() throws IOException {
    test(new englishStemmer(), "/data/porter_stem_diffs_en.txt");
  }

  @Test
  public void testFrenchStemmer() throws IOException {
    test(new frenchStemmer(), "/data/porter_stem_diffs_fr.txt");
  }

  private void test(SnowballStemmer stemmer, String file) throws IOException {

    Preconditions.checkNotNull(stemmer, "stemmer should not be null");
    Preconditions.checkNotNull(file, "file should not be null");

    try (InputStream inputStream = SnowballStemmerTest.class.getResourceAsStream(file)) {

      CsvSchema schema =
          CsvSchema.emptySchema().withHeader().withColumnSeparator(',').withQuoteChar('"');
      CsvMapper mapper = new CsvMapper();

      try (MappingIterator<Map<String, String>> iterator =
          mapper.readerFor(Map.class).with(schema).readValues(inputStream)) {

        while (iterator.hasNext()) {
          Map<String, String> row = iterator.next();
          test(stemmer, row.getOrDefault("word", ""), row.getOrDefault("stem", ""));
        }
      }
    }
  }

  private void test(SnowballStemmer stemmer, String word, String stem) {

    Preconditions.checkNotNull(stemmer, "stemmer should not be null");
    Preconditions.checkNotNull(word, "word should not be null");
    Preconditions.checkNotNull(stem, "stem should not be null");

    stemmer.setCurrent(word);

    if (stemmer.stem()) {
      assertEquals(stem, stemmer.getCurrent());
    } else {
      fail();
    }
  }
}
