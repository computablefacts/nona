package com.computablefacts.nona.helpers;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.tartarus.snowball.SnowballStemmerTest;

import com.google.common.collect.Lists;

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

  /**
   * Penn Treebank Tagset.
   *
   * See https://www.sketchengine.eu/modified-penn-treebank-tagset/ for details.
   */
  @Test
  public void testPosTaggingEnglish() {
    Assert
        .assertEquals(
            Lists.newArrayList(new AbstractMap.SimpleEntry<>("This", "DT") /* determiner */,
                new AbstractMap.SimpleEntry<>("kind", "NN") /* noun, singular or mass */,
                new AbstractMap.SimpleEntry<>("of", "IN") /* preposition */,
                new AbstractMap.SimpleEntry<>("work", "NN") /* noun, singular or mass */,
                new AbstractMap.SimpleEntry<>("wears",
                    "VBZ") /* verb, 3rd person singular present */,
                new AbstractMap.SimpleEntry<>("me", "PRP")/* personal pronoun */,
                new AbstractMap.SimpleEntry<>("out", "RP")/* particle */,
                new AbstractMap.SimpleEntry<>(".", ".") /* punctuation */),
            Languages.tag(Languages.eLanguage.ENGLISH, "This kind of work wears me out ."));
  }

  /**
   * SEM Tagset.
   *
   * See http://apps.lattice.cnrs.fr/sem/about fo details.
   */
  @Test
  public void testPosTaggingFrench() {
    Assert.assertEquals(
        Lists.newArrayList(new AbstractMap.SimpleEntry<>("Elle", "CL") /* pronom clitique */,
            new AbstractMap.SimpleEntry<>("est", "V") /* verbe */,
            new AbstractMap.SimpleEntry<>("en", "P") /* préposition */,
            new AbstractMap.SimpleEntry<>("train", "N") /* nom */,
            new AbstractMap.SimpleEntry<>("de", "P") /* préposition */,
            new AbstractMap.SimpleEntry<>("s'exprimer", "V") /* verbe */,
            new AbstractMap.SimpleEntry<>(".", "PONCT") /* ponctuation */
        ), Languages.tag(Languages.eLanguage.FRENCH, "Elle est en train de s'exprimer ."));
  }

  /**
   * Stuttgart–Tubingen Tagset.
   *
   * See https://www.sketchengine.eu/german-stts-part-of-speech-tagset/ for details.
   */
  @Test
  public void testPosTaggingGerman() {
    Assert.assertEquals(
        Lists.newArrayList(new AbstractMap.SimpleEntry<>("Ich", "PPER") /* personal pronoun */,
            new AbstractMap.SimpleEntry<>("kann", "VMFIN") /* finite modal verb */,
            new AbstractMap.SimpleEntry<>("mit",
                "APPR") /* preposition left hand part of double preposition */,
            new AbstractMap.SimpleEntry<>("diesen", "PDAT") /* demonstrative determiner */,
            new AbstractMap.SimpleEntry<>("Leuten",
                "NN") /* noun (but not adjectives used as nouns) */,
            new AbstractMap.SimpleEntry<>("nicht", "PTKNEG") /* negative particle */,
            new AbstractMap.SimpleEntry<>("arbeiten", "VVINF") /* infinitive of full verb */,
            new AbstractMap.SimpleEntry<>("!", "$.") /* punctuation */
        ),
        Languages.tag(Languages.eLanguage.GERMAN, "Ich kann mit diesen Leuten nicht arbeiten !"));
  }

  /**
   * Tanl POS Tagset.
   *
   * See http://medialab.di.unipi.it/wiki/Tanl_POS_Tagset for details.
   */
  @Test
  public void testPosTaggingItalian() {
    Assert.assertEquals(Lists.newArrayList(new AbstractMap.SimpleEntry<>("Ha", "V") /* verb */,
        new AbstractMap.SimpleEntry<>("bisogno", "S") /* noun */,
        new AbstractMap.SimpleEntry<>("di", "E") /* preposition */,
        new AbstractMap.SimpleEntry<>("una", "RI") /* indeterminative article */,
        new AbstractMap.SimpleEntry<>("tazza", "S") /* noun */,
        new AbstractMap.SimpleEntry<>("di", "E") /* preposition */,
        new AbstractMap.SimpleEntry<>("zucchero", "S") /*  */,
        new AbstractMap.SimpleEntry<>(".", "FS") /* sentence boundary punctuation */
    ), Languages.tag(Languages.eLanguage.ITALIAN, "Ha bisogno di una tazza di zucchero ."));
  }

  @Test
  public void testPosTagging() {
    // Assert.assertEquals(Lists.newArrayList(),
    // Languages.tag(Languages.eLanguage.ARABIC, "لماذا لم تخبرني عن ماري؟"));
    // Assert.assertEquals(Lists.newArrayList(),
    // Languages.tag(Languages.eLanguage.BASQUE, "Zenbat gizonezko ingelesa ikasi nahi dute?"));
    // Assert.assertEquals(Lists.newArrayList(), Languages.tag(Languages.eLanguage.CATALAN,
    // "Què penses sobre la independència de Catalunya?"));
    // Assert.assertEquals(Lists.newArrayList(),
    // Languages.tag(Languages.eLanguage.DANISH, "Hold op med at skændes om penge."));
    // Assert.assertEquals(Lists.newArrayList(),
    // Languages.tag(Languages.eLanguage.DUTCH, "Dit is maar een hypothese."));
    // Assert.assertEquals(Lists.newArrayList(), Languages.tag(Languages.eLanguage.FINNISH,
    // "Luulen, että lukitsin avaimeni sisälle autoon."));
    // Assert.assertEquals(Lists.newArrayList(),
    // Languages.tag(Languages.eLanguage.GREEK, "Είσαι πολύ γενναία."));
    // Assert.assertEquals(Lists.newArrayList(),
    // Languages.tag(Languages.eLanguage.HINDI, "मैं अपनी ग़लती मानता हूँ।"));
    // Assert.assertEquals(Lists.newArrayList(),
    // Languages.tag(Languages.eLanguage.HUNGARIAN, "Elhiszem, hogy Tomi analfabéta."));
    // Assert.assertEquals(Lists.newArrayList(),
    // Languages.tag(Languages.eLanguage.INDONESIAN, "Mereka semua bersalah."));
    // Assert.assertEquals(Lists.newArrayList(), Languages.tag(Languages.eLanguage.IRISH,
    // "Dúirt an bean gur mhaith léi dul ag obair í féin."));
    // Assert.assertEquals(Lists.newArrayList(),
    // Languages.tag(Languages.eLanguage.LITHUANIAN, "Galvoju, kad Tomas yra neraštingas."));
    // Assert.assertEquals(Lists.newArrayList(),
    // Languages.tag(Languages.eLanguage.NEPALI, "घर रातो छ।"));
    // Assert.assertEquals(Lists.newArrayList(), Languages.tag(Languages.eLanguage.NORWEGIAN,
    // "Eg kjem til å hugse denne hendinga for alltid."));
    // Assert.assertEquals(Lists.newArrayList(),
    // Languages.tag(Languages.eLanguage.PORTUGUESE, "Eu acho que eu peguei sol demais hoje."));
    // Assert.assertEquals(Lists.newArrayList(),
    // Languages.tag(Languages.eLanguage.ROMANIAN, "Ai planuri pentru astăzi?"));
    // Assert.assertEquals(Lists.newArrayList(),
    // Languages.tag(Languages.eLanguage.RUSSIAN, "Такая работа меня изматывает."));
    // Assert.assertEquals(Lists.newArrayList(),
    // Languages.tag(Languages.eLanguage.SPANISH, "La chica es hábil con sus dedos."));
    // Assert.assertEquals(Lists.newArrayList(),
    // Languages.tag(Languages.eLanguage.SWEDISH, "Det är ingen bra bil, men ändå en bil."));
    // Assert.assertEquals(Lists.newArrayList(),
    // Languages.tag(Languages.eLanguage.TAMIL, "அமைதியாக இருங்கள்"));
    // Assert.assertEquals(Lists.newArrayList(),
    // Languages.tag(Languages.eLanguage.TURKISH, "Bu tür iş beni yıpratıyor."));
  }

  @Test
  public void testRemoveCommentsFromStopwordsLists() {

    Set<String> stopwordsFr = Languages.stopwords(Languages.eLanguage.FRENCH);

    Assert.assertFalse(stopwordsFr.contains("dans           |  with"));
    Assert.assertTrue(stopwordsFr.contains("dans"));
    Assert.assertFalse(stopwordsFr.contains("with"));

    Set<String> stopwordsFi = Languages.stopwords(Languages.eLanguage.FINNISH);

    Assert.assertFalse(stopwordsFi.contains(
        "joka   jonka         jota   jossa   josta   johon  jolla   jolta   jolle   jona   joksi  | who which"));
    Assert.assertTrue(stopwordsFi.contains("joka"));
    Assert.assertTrue(stopwordsFi.contains("jonka"));
    Assert.assertTrue(stopwordsFi.contains("jota"));
    Assert.assertTrue(stopwordsFi.contains("jossa"));
    Assert.assertTrue(stopwordsFi.contains("josta"));
    Assert.assertTrue(stopwordsFi.contains("johon"));
    Assert.assertTrue(stopwordsFi.contains("jolla"));
    Assert.assertTrue(stopwordsFi.contains("jolta"));
    Assert.assertTrue(stopwordsFi.contains("jolle"));
    Assert.assertTrue(stopwordsFi.contains("jona"));
    Assert.assertTrue(stopwordsFi.contains("joksi"));
    Assert.assertFalse(stopwordsFi.contains("who which"));
    Assert.assertFalse(stopwordsFi.contains("who"));
    Assert.assertFalse(stopwordsFi.contains("which"));
  }
}

