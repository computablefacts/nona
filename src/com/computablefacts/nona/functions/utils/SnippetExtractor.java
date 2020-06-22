package com.computablefacts.nona.functions.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.computablefacts.nona.types.Span;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.google.errorprone.annotations.Var;

/**
 * Based on @{link https://boyter.org/2013/04/building-a-search-result-extract-generator-in-php/}
 */
final public class SnippetExtractor {

  private SnippetExtractor() {}

  /**
   * 1/6 ratio on prevCount tends to work pretty well and puts the terms in the middle of the
   * extract.
   *
   * @param words the words around which to center the snippet.
   * @param text the text to snippet.
   * @return the snippet.
   */
  public static String extract(List<String> words, String text) {
    return extract(words, text, 300, 50, "...");
  }

  /**
   * Center a snippet around a given list of words.
   *
   * @param words the words around which to center the snippet.
   * @param text the text to snippet.
   * @param relLength the maximum length of the snippet in characters.
   * @param prevCount the number of characters to display before the leftmost match.
   * @param indicator ellipsis to indicate where the text has been cut out.
   * @return the snippet.
   */
  public static String extract(List<String> words, String text, int relLength, int prevCount,
      String indicator) {

    Preconditions.checkNotNull(words, "words must not be null");
    Preconditions.checkNotNull(text, "text must not be null");
    Preconditions.checkNotNull(indicator, "indicator must not be null");
    Preconditions.checkArgument(!words.isEmpty(), "words is empty");
    Preconditions.checkArgument(relLength >= 0, "relLength must be >= 0");
    Preconditions.checkArgument(prevCount >= 0, "prevCount must be >= 0");

    int textLength = text.length();

    if (textLength <= relLength) {
      return text;
    }

    List<Span> locations = wordsLocations(Sets.newHashSet(words), text);

    @Var
    int startPos = locations == null || locations.isEmpty() ? 0
        : snippetLocation(locations, relLength, prevCount);

    @Var
    int begin = startPos;
    @Var
    int end = Math.min(textLength, startPos + relLength);

    // If we are going to snip too much...
    if ((begin + relLength) > textLength) {
      begin = Math.max(0, textLength - relLength);
    }

    // Check to ensure we dont snip the first word
    if (begin > 0) {
      int index = text.lastIndexOf(" ", begin);
      if (index < 0) {
        begin = Math.max(0, begin - prevCount);
      } else {
        begin = index + 1;
      }
    }

    // Check to ensure we don't snip the last word
    if ((begin + relLength) < textLength) {
      int index = text.indexOf(" ", end);
      if (index < 0) {
        end = Math.min(textLength, end + prevCount);
      } else {
        end = index;
      }
    }

    // If we trimmed from the begin/end add ellipsis
    return (begin > 0 ? "..." : "") + text.substring(begin, end) + (end < textLength ? "..." : "");
  }

  /**
   * Find the locations of each of the words.
   *
   * @param words words to match.
   * @param text text.
   * @return index of each word in text.
   */
  public static List<Span> wordsLocations(Set<String> words, String text) {

    Preconditions.checkNotNull(words, "words is null");
    Preconditions.checkNotNull(text, "text is null");

    List<Span> locations = new ArrayList<>();

    for (String word : words) {

      int length = word.length();
      @Var
      int loc = text.indexOf(word);

      while (loc >= 0) {
        locations.add(new Span(text, loc, loc + length));
        loc = text.indexOf(word, loc + length);
      }
    }

    locations.sort(Comparator.comparingInt(Span::begin));
    return locations;
  }

  /**
   * Work out which is the most relevant portion to display.
   *
   * @param locations locations of all matching words.
   * @param relLength the maximum length of the snippet in characters.
   * @param prevCount the number of characters to display before the leftmost match.
   * @return where to start the snippet.
   */
  public static int snippetLocation(List<Span> locations, int relLength, int prevCount) {

    Preconditions.checkNotNull(locations, "locations is null");
    Preconditions.checkArgument(!locations.isEmpty(), "locations is empty");

    @Var
    int bestLocation = 0;
    @Var
    int bestDiff = 0;
    @Var
    int nbDistinctWords = 0;

    for (int i = 0; i < locations.size(); i++) {

      @Var
      int endPos = locations.get(i).begin() + locations.get(i).text().length();
      int beginPos = locations.get(i).begin();
      Set<String> words = new HashSet<>();
      words.add(locations.get(i).text());

      for (int j = i + 1; j < locations.size()
          && (locations.get(j).begin() - beginPos) < relLength; j++) {
        endPos = locations.get(j).begin() + locations.get(j).text().length();
        words.add(locations.get(j).text());
      }

      if (words.size() > nbDistinctWords /* maximize the number of distinct words */
          || (words.size() == nbDistinctWords
              && endPos - beginPos < bestDiff) /* minimize the window size */) {
        bestLocation = beginPos;
        bestDiff = endPos - beginPos;
        nbDistinctWords = words.size();
      }
    }
    return Math.max(0, bestLocation - prevCount);
  }
}
