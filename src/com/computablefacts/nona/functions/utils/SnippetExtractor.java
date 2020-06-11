package com.computablefacts.nona.functions.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
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

    List<Location> locations = wordsLocations(Sets.newHashSet(words), text);

    @Var
    int startPos = locations == null || locations.isEmpty() ? 0
        : snipLocation(locations, relLength, prevCount);

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
  public static List<Location> wordsLocations(Set<String> words, String text) {

    Preconditions.checkNotNull(words, "words is null");
    Preconditions.checkNotNull(text, "text is null");

    List<Location> locations = new ArrayList<>();

    for (String word : words) {

      int length = word.length();
      @Var
      int loc = text.indexOf(word);

      while (loc >= 0) {
        locations.add(new Location(word, loc));
        loc = text.indexOf(word, loc + length);
      }
    }

    locations.sort(Comparator.comparingInt(Location::position));
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
  private static int snipLocation(List<Location> locations, int relLength, int prevCount) {

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
      int endPos = locations.get(i).position() + locations.get(i).word().length();
      int beginPos = locations.get(i).position();
      Set<String> words = new HashSet<>();
      words.add(locations.get(i).word());

      for (int j = i + 1; j < locations.size()
          && (locations.get(j).position() - beginPos) < relLength; j++) {
        endPos = locations.get(j).position() + locations.get(j).word().length();
        words.add(locations.get(j).word());
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

  final public static class Location {

    private final String word_;
    private final int position_;

    public Location(String word, int position) {
      word_ = word;
      position_ = position;
    }

    public String word() {
      return word_;
    }

    public int position() {
      return position_;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == this) {
        return true;
      }
      if (obj == null || getClass() != obj.getClass()) {
        return false;
      }
      Location other = (Location) obj;
      return Objects.equal(word_, other.word_) && Objects.equal(position_, other.position_);
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(word_, position_);
    }

    @Override
    public String toString() {
      return MoreObjects.toStringHelper(this).add("word", word_).add("position", position_)
          .omitNullValues().toString();
    }
  }
}
