package com.computablefacts.nona.helpers;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.*;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.optimaize.langdetect.DetectedLanguage;
import com.optimaize.langdetect.LanguageDetector;
import com.optimaize.langdetect.LanguageDetectorBuilder;
import com.optimaize.langdetect.i18n.LdLocale;
import com.optimaize.langdetect.ngram.NgramExtractors;
import com.optimaize.langdetect.profiles.LanguageProfile;
import com.optimaize.langdetect.profiles.LanguageProfileReader;
import com.optimaize.langdetect.text.CommonTextObjectFactories;
import com.optimaize.langdetect.text.TextObject;
import com.optimaize.langdetect.text.TextObjectFactory;

final public class Languages {

  private static final double LANGUAGE_CONFIDENCE_SCORE = 0.85;
  private static final Logger logger_ = LoggerFactory.getLogger(Languages.class);
  private static final TextObjectFactory textObjectFactory_ =
      CommonTextObjectFactories.forDetectingOnLargeText();
  private static final LanguageDetector languageDetector_;

  static {

    List<LanguageProfile> profiles = new ArrayList<>(10);

    try {
      LanguageProfileReader reader = new LanguageProfileReader();
      profiles.add(reader.readBuiltIn(LdLocale.fromString("ar"))); // Arabic
      profiles.add(reader.readBuiltIn(LdLocale.fromString("eu"))); // Basque
      profiles.add(reader.readBuiltIn(LdLocale.fromString("ca"))); // Catalan
      profiles.add(reader.readBuiltIn(LdLocale.fromString("da"))); // Danish
      profiles.add(reader.readBuiltIn(LdLocale.fromString("nl"))); // Dutch
      profiles.add(reader.readBuiltIn(LdLocale.fromString("en"))); // English
      profiles.add(reader.readBuiltIn(LdLocale.fromString("fi"))); // Finnish
      profiles.add(reader.readBuiltIn(LdLocale.fromString("fr"))); // French
      profiles.add(reader.readBuiltIn(LdLocale.fromString("de"))); // German
      profiles.add(reader.readBuiltIn(LdLocale.fromString("el"))); // Greek
      profiles.add(reader.readBuiltIn(LdLocale.fromString("hi"))); // Hindi
      profiles.add(reader.readBuiltIn(LdLocale.fromString("hu"))); // Hungarian
      profiles.add(reader.readBuiltIn(LdLocale.fromString("id"))); // Indonesian
      profiles.add(reader.readBuiltIn(LdLocale.fromString("ga"))); // Irish
      profiles.add(reader.readBuiltIn(LdLocale.fromString("it"))); // Italian
      profiles.add(reader.readBuiltIn(LdLocale.fromString("lt"))); // Lithuanian
      profiles.add(reader.readBuiltIn(LdLocale.fromString("ne"))); // Nepali
      profiles.add(reader.readBuiltIn(LdLocale.fromString("no"))); // Norwegian
      profiles.add(reader.readBuiltIn(LdLocale.fromString("pt"))); // Portuguese
      profiles.add(reader.readBuiltIn(LdLocale.fromString("ro"))); // Romanian
      profiles.add(reader.readBuiltIn(LdLocale.fromString("ru"))); // Russian
      profiles.add(reader.readBuiltIn(LdLocale.fromString("es"))); // Spanish
      profiles.add(reader.readBuiltIn(LdLocale.fromString("sv"))); // Swedish
      profiles.add(reader.readBuiltIn(LdLocale.fromString("ta"))); // Tamil
      profiles.add(reader.readBuiltIn(LdLocale.fromString("tr"))); // Turkish
    } catch (IOException e) {
      logger_.error(Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
    }

    languageDetector_ = LanguageDetectorBuilder.create(NgramExtractors.standard())
        .withProfiles(profiles).probabilityThreshold(LANGUAGE_CONFIDENCE_SCORE).build();
  }

  /**
   * Detect the language of a text.
   *
   * Note that this method does not work well when the input text to analyze is short, or unclean.
   * For example tweets.
   *
   * Furthermore, when a text is written in multiple languages, this method is not appropriate. You
   * can try to split the text (by sentence or paragraph) and detect the individual parts. Running
   * the language guesser on the whole text will just tell you the language that is most dominant,
   * in the best case.
   *
   * At last, this method does not work well when the input text is in none of the expected (and
   * supported) languages. For example, if you only load the language profiles for English and
   * German but the text is written in French, the program may pick the more likely one or say it
   * doesn't know.
   *
   * See https://github.com/optimaize/language-detector for details.
   * 
   * @param text text.
   * @return the language (and a confidence score) if a language has been detected, null otherwise.
   */
  public static Map.Entry<eLanguage, Double> language(String text) {

    Preconditions.checkNotNull(text, "text should not be null");

    TextObject txt = textObjectFactory_.forText(text);
    List<DetectedLanguage> probabilities = languageDetector_.getProbabilities(txt);

    if (probabilities.isEmpty()) {
      return null;
    }

    DetectedLanguage best = probabilities.get(0);

    if (best.getProbability() < LANGUAGE_CONFIDENCE_SCORE) {
      return null;
    }

    switch (best.getLocale().getLanguage()) {
      case "ar":
        return new AbstractMap.SimpleEntry<>(eLanguage.ARABIC, best.getProbability());
      case "eu":
        return new AbstractMap.SimpleEntry<>(eLanguage.BASQUE, best.getProbability());
      case "ca":
        return new AbstractMap.SimpleEntry<>(eLanguage.CATALAN, best.getProbability());
      case "da":
        return new AbstractMap.SimpleEntry<>(eLanguage.DANISH, best.getProbability());
      case "nl":
        return new AbstractMap.SimpleEntry<>(eLanguage.DUTCH, best.getProbability());
      case "en":
        return new AbstractMap.SimpleEntry<>(eLanguage.ENGLISH, best.getProbability());
      case "fi":
        return new AbstractMap.SimpleEntry<>(eLanguage.FINNISH, best.getProbability());
      case "fr":
        return new AbstractMap.SimpleEntry<>(eLanguage.FRENCH, best.getProbability());
      case "de":
        return new AbstractMap.SimpleEntry<>(eLanguage.GERMAN, best.getProbability());
      case "el":
        return new AbstractMap.SimpleEntry<>(eLanguage.GREEK, best.getProbability());
      case "hi":
        return new AbstractMap.SimpleEntry<>(eLanguage.HINDI, best.getProbability());
      case "hu":
        return new AbstractMap.SimpleEntry<>(eLanguage.HUNGARIAN, best.getProbability());
      case "id":
        return new AbstractMap.SimpleEntry<>(eLanguage.INDONESIAN, best.getProbability());
      case "ga":
        return new AbstractMap.SimpleEntry<>(eLanguage.IRISH, best.getProbability());
      case "it":
        return new AbstractMap.SimpleEntry<>(eLanguage.ITALIAN, best.getProbability());
      case "lt":
        return new AbstractMap.SimpleEntry<>(eLanguage.LITHUANIAN, best.getProbability());
      case "ne":
        return new AbstractMap.SimpleEntry<>(eLanguage.NEPALI, best.getProbability());
      case "no":
        return new AbstractMap.SimpleEntry<>(eLanguage.NORWEGIAN, best.getProbability());
      case "pt":
        return new AbstractMap.SimpleEntry<>(eLanguage.PORTUGUESE, best.getProbability());
      case "ro":
        return new AbstractMap.SimpleEntry<>(eLanguage.ROMANIAN, best.getProbability());
      case "ru":
        return new AbstractMap.SimpleEntry<>(eLanguage.RUSSIAN, best.getProbability());
      case "es":
        return new AbstractMap.SimpleEntry<>(eLanguage.SPANISH, best.getProbability());
      case "sv":
        return new AbstractMap.SimpleEntry<>(eLanguage.SWEDISH, best.getProbability());
      case "ta":
        return new AbstractMap.SimpleEntry<>(eLanguage.TAMIL, best.getProbability());
      case "tr":
        return new AbstractMap.SimpleEntry<>(eLanguage.TURKISH, best.getProbability());
      default:
        return null;
    }
  }

  /**
   * Get a Snowball stemmer from a language code.
   *
   * See https://snowballstem.org/ for details.
   *
   * @return a Snowball stemmer if the language has been found, null otherwise.
   */
  public static SnowballStemmer stemmer(eLanguage language) {

    Preconditions.checkNotNull(language, "language should not be null");

    switch (language) {
      case ARABIC:
        return new arabicStemmer();
      case BASQUE:
        return new basqueStemmer();
      case CATALAN:
        return new catalanStemmer();
      case DANISH:
        return new danishStemmer();
      case DUTCH:
        return new dutchStemmer();
      case ENGLISH:
        return new englishStemmer();
      case FINNISH:
        return new finnishStemmer();
      case FRENCH:
        return new frenchStemmer();
      case GERMAN:
        return new germanStemmer();
      case GREEK:
        return new greekStemmer();
      case HINDI:
        return new hindiStemmer();
      case HUNGARIAN:
        return new hungarianStemmer();
      case INDONESIAN:
        return new indonesianStemmer();
      case IRISH:
        return new irishStemmer();
      case ITALIAN:
        return new italianStemmer();
      case LITHUANIAN:
        return new lithuanianStemmer();
      case NEPALI:
        return new nepaliStemmer();
      case NORWEGIAN:
        return new norwegianStemmer();
      case PORTUGUESE:
        return new portugueseStemmer();
      case ROMANIAN:
        return new romanianStemmer();
      case RUSSIAN:
        return new russianStemmer();
      case SPANISH:
        return new spanishStemmer();
      case SWEDISH:
        return new swedishStemmer();
      case TAMIL:
        return new tamilStemmer();
      case TURKISH:
        return new turkishStemmer();
      default:
        return null;
    }
  }

  public enum eLanguage {
    ARABIC, BASQUE, CATALAN, DANISH, DUTCH, ENGLISH, FINNISH, FRENCH, GERMAN, GREEK, HINDI, HUNGARIAN, INDONESIAN, IRISH, ITALIAN, LITHUANIAN, NEPALI, NORWEGIAN, PORTUGUESE, ROMANIAN, RUSSIAN, SPANISH, SWEDISH, TAMIL, TURKISH
  }
}
