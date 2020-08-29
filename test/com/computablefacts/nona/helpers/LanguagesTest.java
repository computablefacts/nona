package com.computablefacts.nona.helpers;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.tartarus.snowball.SnowballStemmerTest;

public class LanguagesTest {

  @Test
  public void testNotALanguage() {
    Assert.assertNull(Languages.language(""));
  }

  /**
   * Sentences extracted from https://tatoeba.org
   */
  @Test
  public void testDetectLanguage() {
    Assert.assertEquals(Languages.eLanguage.ARABIC,
        Languages.language("لماذا لم تخبرني عن ماري؟").getKey());
    Assert.assertEquals(Languages.eLanguage.BASQUE,
        Languages.language("Zenbat gizonezko ingelesa ikasi nahi dute?").getKey());
    Assert.assertEquals(Languages.eLanguage.CATALAN,
        Languages.language("Què penses sobre la independència de Catalunya?").getKey());
    Assert.assertEquals(Languages.eLanguage.DANISH,
        Languages.language("Hold op med at skændes om penge.").getKey());
    Assert.assertEquals(Languages.eLanguage.DUTCH,
        Languages.language("Dit is maar een hypothese.").getKey());
    Assert.assertEquals(Languages.eLanguage.ENGLISH,
        Languages.language("This kind of work wears me out.").getKey());
    Assert.assertEquals(Languages.eLanguage.FINNISH,
        Languages.language("Luulen, että lukitsin avaimeni sisälle autoon.").getKey());
    Assert.assertEquals(Languages.eLanguage.FRENCH,
        Languages.language("Elle est en train de s'exprimer.").getKey());
    Assert.assertEquals(Languages.eLanguage.GERMAN,
        Languages.language("Ich kann mit diesen Leuten nicht arbeiten!").getKey());
    Assert.assertEquals(Languages.eLanguage.GREEK,
        Languages.language("Είσαι πολύ γενναία.").getKey());
    Assert.assertEquals(Languages.eLanguage.HINDI,
        Languages.language("मैं अपनी ग़लती मानता हूँ।").getKey());
    Assert.assertEquals(Languages.eLanguage.HUNGARIAN,
        Languages.language("Elhiszem, hogy Tomi analfabéta.").getKey());
    Assert.assertEquals(Languages.eLanguage.INDONESIAN,
        Languages.language("Mereka semua bersalah.").getKey());
    Assert.assertEquals(Languages.eLanguage.IRISH,
        Languages.language("Dúirt an bean gur mhaith léi dul ag obair í féin.").getKey());
    Assert.assertEquals(Languages.eLanguage.ITALIAN,
        Languages.language("Ha bisogno di una tazza di zucchero.").getKey());
    Assert.assertEquals(Languages.eLanguage.LITHUANIAN,
        Languages.language("Galvoju, kad Tomas yra neraštingas.").getKey());
    Assert.assertEquals(Languages.eLanguage.NEPALI, Languages.language("घर रातो छ।").getKey());
    Assert.assertEquals(Languages.eLanguage.NORWEGIAN,
        Languages.language("Eg kjem til å hugse denne hendinga for alltid.").getKey());
    Assert.assertEquals(Languages.eLanguage.PORTUGUESE,
        Languages.language("Eu acho que eu peguei sol demais hoje.").getKey());
    Assert.assertEquals(Languages.eLanguage.ROMANIAN,
        Languages.language("Ai planuri pentru astăzi?").getKey());
    Assert.assertEquals(Languages.eLanguage.RUSSIAN,
        Languages.language("Такая работа меня изматывает.").getKey());
    Assert.assertEquals(Languages.eLanguage.SPANISH,
        Languages.language("La chica es hábil con sus dedos.").getKey());
    Assert.assertEquals(Languages.eLanguage.SWEDISH,
        Languages.language("Det är ingen bra bil, men ändå en bil.").getKey());
    Assert.assertEquals(Languages.eLanguage.TAMIL,
        Languages.language("அமைதியாக இருங்கள்").getKey());
    Assert.assertEquals(Languages.eLanguage.TURKISH,
        Languages.language("Bu tür iş beni yıpratıyor.").getKey());
  }

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

  @Test
  public void testLoadStopwords() {
    Assert.assertFalse(Languages.stopwords(Languages.eLanguage.ARABIC).isEmpty());
    Assert.assertFalse(Languages.stopwords(Languages.eLanguage.BASQUE).isEmpty());
    Assert.assertFalse(Languages.stopwords(Languages.eLanguage.CATALAN).isEmpty());
    Assert.assertFalse(Languages.stopwords(Languages.eLanguage.DANISH).isEmpty());
    Assert.assertFalse(Languages.stopwords(Languages.eLanguage.DUTCH).isEmpty());
    Assert.assertFalse(Languages.stopwords(Languages.eLanguage.ENGLISH).isEmpty());
    Assert.assertFalse(Languages.stopwords(Languages.eLanguage.FINNISH).isEmpty());
    Assert.assertFalse(Languages.stopwords(Languages.eLanguage.FRENCH).isEmpty());
    Assert.assertFalse(Languages.stopwords(Languages.eLanguage.GERMAN).isEmpty());
    Assert.assertFalse(Languages.stopwords(Languages.eLanguage.GREEK).isEmpty());
    Assert.assertFalse(Languages.stopwords(Languages.eLanguage.HINDI).isEmpty());
    Assert.assertFalse(Languages.stopwords(Languages.eLanguage.HUNGARIAN).isEmpty());
    Assert.assertFalse(Languages.stopwords(Languages.eLanguage.INDONESIAN).isEmpty());
    Assert.assertFalse(Languages.stopwords(Languages.eLanguage.IRISH).isEmpty());
    Assert.assertFalse(Languages.stopwords(Languages.eLanguage.ITALIAN).isEmpty());
    Assert.assertNull(Languages.stopwords(Languages.eLanguage.LITHUANIAN));
    Assert.assertNull(Languages.stopwords(Languages.eLanguage.NEPALI));
    Assert.assertFalse(Languages.stopwords(Languages.eLanguage.NORWEGIAN).isEmpty());
    Assert.assertFalse(Languages.stopwords(Languages.eLanguage.PORTUGUESE).isEmpty());
    Assert.assertFalse(Languages.stopwords(Languages.eLanguage.ROMANIAN).isEmpty());
    Assert.assertFalse(Languages.stopwords(Languages.eLanguage.RUSSIAN).isEmpty());
    Assert.assertFalse(Languages.stopwords(Languages.eLanguage.SPANISH).isEmpty());
    Assert.assertFalse(Languages.stopwords(Languages.eLanguage.SWEDISH).isEmpty());
    Assert.assertNull(Languages.stopwords(Languages.eLanguage.TAMIL));
    Assert.assertFalse(Languages.stopwords(Languages.eLanguage.TURKISH).isEmpty());
  }
}

