package com.computablefacts.nona.helpers;

import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.*;

final public class Languages {

  /**
   * Get a Snowball stemmer from a language code.
   *
   * @return a Snowball stemmer if the language has been found, null otherwise.
   */
  public static SnowballStemmer stemmer(eLanguage language) {
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
    ALBANIAN, ARABIC, BASQUE, CATALAN, CHINESE, DANISH, DUTCH, ENGLISH, FINNISH, FRENCH, GERMAN, GREEK, HINDI, HUNGARIAN, INDONESIAN, IRISH, ITALIAN, JAPANESE, LATVIAN, LITHUANIAN, NEPALI, NORWEGIAN, POLISH, PORTUGUESE, ROMANIAN, RUSSIAN, SLOVAK, SLOVENIAN, SPANISH, SWAHILI, SWEDISH, TAMIL, TURKISH, UKRAINIAN
  }
}
