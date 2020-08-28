package com.computablefacts.nona.helpers;

import java.io.IOException;

import org.junit.Test;
import org.tartarus.snowball.SnowballStemmerTest;

public class LanguagesTest {

  @Test
  public void testEnglishStemmer() throws IOException {
    SnowballStemmerTest.test(Languages.stemmer(Languages.eLanguage.ENGLISH),
        "/data/porter_stem_diffs_en.txt");
  }

  @Test
  public void testFrenchStemmer() throws IOException {
    SnowballStemmerTest.test(Languages.stemmer(Languages.eLanguage.FRENCH),
        "/data/porter_stem_diffs_fr.txt");
  }
}

