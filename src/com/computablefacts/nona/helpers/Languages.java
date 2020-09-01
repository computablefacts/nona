package com.computablefacts.nona.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.*;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.errorprone.annotations.CheckReturnValue;
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

import RDRPOSTagger.jSCRDRtagger.FWObject;
import RDRPOSTagger.jSCRDRtagger.InitialTagger;
import RDRPOSTagger.jSCRDRtagger.Node;
import RDRPOSTagger.jSCRDRtagger.RDRPOSTagger;
import RDRPOSTagger.jSCRDRtagger.Utils;
import RDRPOSTagger.jSCRDRtagger.WordTag;

@CheckReturnValue
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
   * @param language language.
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

  /**
   * Get a list of stopwords from a language code.
   * 
   * @param language language.
   * @return a list of stopwords if the language has been found, null otherwise.
   */
  public static Set<String> stopwords(eLanguage language) {

    Preconditions.checkNotNull(language, "language should not be null");

    String path;

    switch (language) {
      case ARABIC:
        path = "/stopwords_ar.txt";
        break;
      case BASQUE:
        path = "/stopwords_eu.txt";
        break;
      case CATALAN:
        path = "/stopwords_ca.txt";
        break;
      case DANISH:
        path = "/stopwords_da.txt";
        break;
      case DUTCH:
        path = "/stopwords_nl.txt";
        break;
      case ENGLISH:
        path = "/stopwords_en.txt";
        break;
      case FINNISH:
        path = "/stopwords_fi.txt";
        break;
      case FRENCH:
        path = "/stopwords_fr.txt";
        break;
      case GERMAN:
        path = "/stopwords_de.txt";
        break;
      case GREEK:
        path = "/stopwords_el.txt";
        break;
      case HINDI:
        path = "/stopwords_hi.txt";
        break;
      case HUNGARIAN:
        path = "/stopwords_hu.txt";
        break;
      case INDONESIAN:
        path = "/stopwords_id.txt";
        break;
      case IRISH:
        path = "/stopwords_ga.txt";
        break;
      case ITALIAN:
        path = "/stopwords_it.txt";
        break;
      case LITHUANIAN:
        path = "/stopwords_lt.txt"; // TODO
        break;
      case NEPALI:
        path = "/stopwords_ne.txt"; // TODO
        break;
      case NORWEGIAN:
        path = "/stopwords_no.txt";
        break;
      case PORTUGUESE:
        path = "/stopwords_pt.txt";
        break;
      case ROMANIAN:
        path = "/stopwords_ro.txt";
        break;
      case RUSSIAN:
        path = "/stopwords_ru.txt";
        break;
      case SPANISH:
        path = "/stopwords_es.txt";
        break;
      case SWEDISH:
        path = "/stopwords_sv.txt";
        break;
      case TAMIL:
        path = "/stopwords_ta.txt"; // TODO
        break;
      case TURKISH:
        path = "/stopwords_tr.txt";
        break;
      default:
        return null;
    }

    String prefix = "/data/stopwords";
    Set<String> stopwords = new HashSet<>();

    try (InputStream stream = Languages.class.getResourceAsStream(prefix + path)) {
      try (BufferedReader buffer =
          new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
        for (String line; (line = buffer.readLine()) != null;) {

          String stopword = line.trim();

          if (!stopword.startsWith("#")) {
            stopwords.add(stopword);
          }
        }
      } catch (IOException | NullPointerException e) {
        logger_.error(Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
        return null;
      }
    } catch (IOException | NullPointerException e) {
      logger_.error(Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
      return null;
    }
    return stopwords;
  }

  /**
   * Get POS tags for a given sentence.
   *
   * Note that :
   * 
   * <ul>
   * <li>English tags come from the Penn Treebank Tagset :
   * https://www.sketchengine.eu/modified-penn-treebank-tagset/</li>
   * <li>French tags come from the SEM Tagset : http://apps.lattice.cnrs.fr/sem/about</li>
   * <li>German tags come from the Stuttgart–Tubingen Tagset :
   * https://www.sketchengine.eu/german-stts-part-of-speech-tagset/</li>
   * <li>Italian tags come from the Tanl POS Tagset :
   * http://medialab.di.unipi.it/wiki/Tanl_POS_Tagset</li>
   * </ul>
   *
   * Tags for other languages come from the Universal POS Tagset :
   * https://universaldependencies.org/u/pos/index.html
   *
   * @param language language.
   * @param sentence sentence.
   * @return list of POS tags.
   */
  public static List<Map.Entry<String, String>> tag(eLanguage language, String sentence) {

    Preconditions.checkNotNull(language, "language should not be null");
    Preconditions.checkNotNull(sentence, "sentence should not be null");

    if (Strings.isNullOrEmpty(sentence)) {
      return new ArrayList<>();
    }

    String dict;
    String rdr;

    switch (language) {
      case ARABIC:
        dict = "/ud-treebanks-v2.4/UD_Arabic-PADT/ar_padt-ud-train.conllu.UPOS.DICT";
        rdr = "/ud-treebanks-v2.4/UD_Arabic-PADT/ar_padt-ud-train.conllu.UPOS.RDR";
        break;
      case BASQUE:
        dict = "/ud-treebanks-v2.4/UD_Basque-BDT/eu_bdt-ud-train.conllu.UPOS.DICT";
        rdr = "/ud-treebanks-v2.4/UD_Basque-BDT/eu_bdt-ud-train.conllu.UPOS.RDR";
        break;
      case CATALAN:
        dict = "/ud-treebanks-v2.4/UD_Catalan-AnCora/ca_ancora-ud-train.conllu.UPOS.DICT";
        rdr = "/ud-treebanks-v2.4/UD_Catalan-AnCora/ca_ancora-ud-train.conllu.UPOS.RDR";
        break;
      case DANISH:
        dict = "/ud-treebanks-v2.4/UD_Danish-DDT/da_ddt-ud-train.conllu.UPOS.DICT";
        rdr = "/ud-treebanks-v2.4/UD_Danish-DDT/da_ddt-ud-train.conllu.UPOS.RDR";
        break;
      case DUTCH:
        dict = "/ud-treebanks-v2.4/UD_Dutch-Alpino/nl_alpino-ud-train.conllu.UPOS.DICT";
        rdr = "/ud-treebanks-v2.4/UD_Dutch-Alpino/nl_alpino-ud-train.conllu.UPOS.RDR";
        break;
      case ENGLISH:
        dict = "/POS/English.DICT";
        rdr = "/POS/English.RDR";
        break;
      case FINNISH:
        dict = "/ud-treebanks-v2.4/UD_Finnish-TDT/fi_tdt-ud-train.conllu.UPOS.DICT";
        rdr = "/ud-treebanks-v2.4/UD_Finnish-TDT/fi_tdt-ud-train.conllu.UPOS.RDR";
        break;
      case FRENCH:
        dict = "/POS/French.DICT";
        rdr = "/POS/French.RDR";
        break;
      case GERMAN:
        dict = "/POS/German.DICT";
        rdr = "/POS/German.RDR";
        break;
      case GREEK:
        dict = "/ud-treebanks-v2.4/UD_Greek-GDT/el_gdt-ud-train.conllu.UPOS.DICT";
        rdr = "/ud-treebanks-v2.4/UD_Greek-GDT/el_gdt-ud-train.conllu.UPOS.RDR";
        break;
      case HINDI:
        dict = "/POS/Hindi.DICT";
        rdr = "/POS/Hindi.RDR";
        break;
      case HUNGARIAN:
        dict = "/ud-treebanks-v2.4/UD_Hungarian-Szeged/hu_szeged-ud-train.conllu.UPOS.DICT";
        rdr = "/ud-treebanks-v2.4/UD_Hungarian-Szeged/hu_szeged-ud-train.conllu.UPOS.RDR";
        break;
      case INDONESIAN:
        dict = "/ud-treebanks-v2.4/UD_Indonesian-GSD/id_gsd-ud-train.conllu.UPOS.DICT";
        rdr = "/ud-treebanks-v2.4/UD_Indonesian-GSD/id_gsd-ud-train.conllu.UPOS.RDR";
        break;
      case IRISH:
        dict = "/ud-treebanks-v2.4/UD_Irish-IDT/ga_idt-ud-train.conllu.UPOS.DICT";
        rdr = "/ud-treebanks-v2.4/UD_Irish-IDT/ga_idt-ud-train.conllu.UPOS.RDR";
        break;
      case ITALIAN:
        dict = "/POS/Italian.DICT";
        rdr = "/POS/Italian.RDR";
        break;
      case LITHUANIAN:
        dict = "/ud-treebanks-v2.4/UD_Lithuanian-ALKSNIS/lt_alksnis-ud-train.conllu.UPOS.DICT";
        rdr = "/ud-treebanks-v2.4/UD_Lithuanian-ALKSNIS/lt_alksnis-ud-train.conllu.UPOS.RDR";
        break;
      case NEPALI:
        dict = "/"; // TODO
        rdr = "/"; // TODO
        break;
      case NORWEGIAN:
        dict = "/ud-treebanks-v2.4/UD_Norwegian-Bokmaal/no_bokmaal-ud-train.conllu.UPOS.DICT";
        rdr = "/ud-treebanks-v2.4/UD_Norwegian-Bokmaal/no_bokmaal-ud-train.conllu.UPOS.RDR";
        break;
      case PORTUGUESE:
        dict = "/ud-treebanks-v2.4/UD_Portuguese-GSD/pt_gsd-ud-train.conllu.UPOS.DICT";
        rdr = "/ud-treebanks-v2.4/UD_Portuguese-GSD/pt_gsd-ud-train.conllu.UPOS.RDR";
        break;
      case ROMANIAN:
        dict = "/ud-treebanks-v2.4/UD_Romanian-RRT/ro_rrt-ud-train.conllu.UPOS.DICT";
        rdr = "/ud-treebanks-v2.4/UD_Romanian-RRT/ro_rrt-ud-train.conllu.UPOS.RDR";
        break;
      case RUSSIAN:
        dict = "/ud-treebanks-v2.4/UD_Russian-GSD/ru_gsd-ud-train.conllu.UPOS.DICT";
        rdr = "/ud-treebanks-v2.4/UD_Russian-GSD/ru_gsd-ud-train.conllu.UPOS.RDR";
        break;
      case SPANISH:
        dict = "/ud-treebanks-v2.4/UD_Spanish-GSD/es_gsd-ud-train.conllu.UPOS.DICT";
        rdr = "/ud-treebanks-v2.4/UD_Spanish-GSD/es_gsd-ud-train.conllu.UPOS.RDR";
        break;
      case SWEDISH:
        dict = "/ud-treebanks-v2.4/UD_Swedish-Talbanken/sv_talbanken-ud-train.conllu.UPOS.DICT";
        rdr = "/ud-treebanks-v2.4/UD_Swedish-Talbanken/sv_talbanken-ud-train.conllu.UPOS.RDR";
        break;
      case TAMIL:
        dict = "/ud-treebanks-v2.4/UD_Tamil-TTB/ta_ttb-ud-train.conllu.UPOS.DICT";
        rdr = "/ud-treebanks-v2.4/UD_Tamil-TTB/ta_ttb-ud-train.conllu.UPOS.RDR";
        break;
      case TURKISH:
        dict = "/ud-treebanks-v2.4/UD_Turkish-GB/tr_gb-ud-test.conllu.UPOS.DICT";
        rdr = "/ud-treebanks-v2.4/UD_Turkish-GB/tr_gb-ud-test.conllu.UPOS.RDR";
        break;
      default:
        return null;
    }

    String prefix = "/data/rdrpostagger";
    List<Map.Entry<String, String>> tags = new ArrayList<>();

    try (InputStream dictStream = Languages.class.getResourceAsStream(prefix + dict)) {
      try (InputStream modelStream = Languages.class.getResourceAsStream(prefix + rdr)) {

        HashMap<String, String> FREQDICT = Utils.getDictionary(dictStream);
        RDRPOSTagger tree = new RDRPOSTagger();
        tree.constructTreeFromRulesFile(modelStream);
        List<WordTag> wordtags = InitialTagger.InitTagger4Sentence(FREQDICT, sentence.trim());

        if (wordtags == null) {
          return null;
        }

        for (int i = 0; i < wordtags.size(); i++) {

          FWObject object = Utils.getObject(wordtags, wordtags.size(), i);
          Node firedNode = tree.findFiredNode(object);

          if (firedNode.depth > 0) {
            tags.add(new AbstractMap.SimpleEntry<>(wordtags.get(i).word, firedNode.conclusion));
          } else { // Fired at root, return initialized tag.
            tags.add(new AbstractMap.SimpleEntry<>(wordtags.get(i).word, wordtags.get(i).tag));
          }
        }
      } catch (IOException | NullPointerException e) {
        logger_.error(Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
        return null;
      }
    } catch (IOException | NullPointerException e) {
      logger_.error(Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
      return null;
    }
    return tags;
  }

  public enum eLanguage {
    ARABIC, BASQUE, CATALAN, DANISH, DUTCH, ENGLISH, FINNISH, FRENCH, GERMAN, GREEK, HINDI, HUNGARIAN, INDONESIAN, IRISH, ITALIAN, LITHUANIAN, NEPALI, NORWEGIAN, PORTUGUESE, ROMANIAN, RUSSIAN, SPANISH, SWEDISH, TAMIL, TURKISH
  }
}
