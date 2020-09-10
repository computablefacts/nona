/*
 * Copyright (c) 2011-2020 MNCC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * @author http://www.mncc.fr
 */
package com.computablefacts.nona.helpers;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Multiset;
import com.google.errorprone.annotations.Var;

public class TextTest {

  @Test(expected = NullPointerException.class)
  public void testNullSentenceSplitter() {
    Text text = new Text("Hello world!", null, wordSplitter());
  }

  @Test(expected = NullPointerException.class)
  public void testNullTextSplitter() {
    Text text = new Text("Hello world!", sentenceSplitter(), null);
  }

  @Test(expected = NullPointerException.class)
  public void testNullText() {
    Text text = new Text(null, sentenceSplitter(), wordSplitter());
  }

  @Test
  public void testSingleWordSentence() {

    Text text = new Text("Hello!", sentenceSplitter(), wordSplitter());

    Assert.assertEquals("Hello!", text.text());
    Assert.assertEquals(32, text.hash().length());
    Assert.assertEquals(sentenceSplitter(), text.sentenceSplitter());
    Assert.assertEquals(wordSplitter(), text.wordSplitter());
    Assert.assertEquals("", text.mostProbableNextWord("hello"));

    Assert.assertEquals(1, text.bagOfWords().size());
    Assert.assertEquals(1, text.bagOfWords().count("hello"));

    Assert.assertEquals(0, text.bagOfBigrams().size());
  }

  @Test
  public void testMultiWordsSentence() {

    Text text = new Text("Hello world!", sentenceSplitter(), wordSplitter());

    Assert.assertEquals("Hello world!", text.text());
    Assert.assertEquals(32, text.hash().length());
    Assert.assertEquals(sentenceSplitter(), text.sentenceSplitter());
    Assert.assertEquals(wordSplitter(), text.wordSplitter());
    Assert.assertEquals("world", text.mostProbableNextWord("hello"));
    Assert.assertEquals("", text.mostProbableNextWord("world"));

    Assert.assertEquals(2, text.bagOfWords().size());
    Assert.assertEquals(1, text.bagOfWords().count("hello"));
    Assert.assertEquals(1, text.bagOfWords().count("world"));

    Assert.assertEquals(1, text.bagOfBigrams().size());
    Assert.assertEquals(1,
        text.bagOfBigrams().count(new AbstractMap.SimpleEntry<>("hello", "world")));
  }

  @Test
  public void testUnknownWords() {

    Set<String> wordsSeen = new HashSet<>();
    Text text = new Text("Hello Kevin!\nHello Joe!", sentenceSplitter(), wordSplitter(), wordsSeen);

    Assert.assertTrue(wordsSeen.contains("hello"));
    Assert.assertTrue(wordsSeen.contains("kevin"));
    Assert.assertTrue(wordsSeen.contains("joe"));

    Assert.assertEquals("Hello Kevin!\nHello Joe!", text.text());
    Assert.assertEquals(32, text.hash().length());
    Assert.assertEquals(sentenceSplitter(), text.sentenceSplitter());
    Assert.assertEquals(wordSplitter(), text.wordSplitter());
    Assert.assertEquals("<UNK>", text.mostProbableNextWord("<UNK>"));
    Assert.assertEquals("<UNK>", text.mostProbableNextWord("hello"));

    Assert.assertEquals(3, text.bagOfWords().size());
    Assert.assertEquals(1, text.bagOfWords().count("hello"));
    Assert.assertEquals(2, text.bagOfWords().count("<UNK>"));

    Assert.assertEquals(2, text.bagOfBigrams().size());
    Assert.assertEquals(1,
        text.bagOfBigrams().count(new AbstractMap.SimpleEntry<>("<UNK>", "<UNK>")));
    Assert.assertEquals(1,
        text.bagOfBigrams().count(new AbstractMap.SimpleEntry<>("hello", "<UNK>")));
  }

  @Test
  public void testEqualsWithNull() {

    Text text = new Text("Hello world!", sentenceSplitter(), wordSplitter());

    Assert.assertFalse(text.equals(null));
  }

  @Test
  public void testEqualsWithWrongObjectType() {

    Text text = new Text("Hello world!", sentenceSplitter(), wordSplitter());

    Assert.assertFalse(text.equals("string"));
  }

  @Test
  public void testEquals() {

    Text text1 = new Text("Hello world!", sentenceSplitter(), wordSplitter());
    Text text2 = new Text("Hello world!", sentenceSplitter(), wordSplitter());

    Assert.assertTrue(text1.equals(text2));
    Assert.assertTrue(text2.equals(text1));
  }

  @Test
  public void testHashcode() {

    Text text1 = new Text("Hello world!", sentenceSplitter(), wordSplitter());
    Text text2 = new Text("Hello world!", sentenceSplitter(), wordSplitter());

    Assert.assertEquals(text1.hashCode(), text2.hashCode());
  }

  @Test
  public void testExtractBigrams() {

    Text text = new Text(
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
        sentenceSplitter(), wordSplitter());

    @Var
    Multiset<String> bigrams = text.bigrams(null, null, '¤');

    Assert.assertEquals(91, bigrams.size());
    Assert.assertEquals(91 - 3 - 1, bigrams.elementSet().size());
    Assert.assertEquals(4, bigrams.count("lorem¤ipsum"));
    Assert.assertEquals(2, bigrams.count("dummy¤text"));

    bigrams = text.bigrams("dummy", null, '¤');

    Assert.assertEquals(2, bigrams.size());
    Assert.assertEquals(1, bigrams.elementSet().size());
    Assert.assertEquals(2, bigrams.count("dummy¤text"));

    bigrams = text.bigrams("typesetting", "industry", '¤');

    Assert.assertEquals(1, bigrams.size());
    Assert.assertEquals(1, bigrams.elementSet().size());
    Assert.assertEquals(1, bigrams.count("typesetting¤industry"));

    bigrams = text.bigrams(null, "ipsum", '¤');

    Assert.assertEquals(4, bigrams.size());
    Assert.assertEquals(1, bigrams.elementSet().size());
    Assert.assertEquals(4, bigrams.count("lorem¤ipsum"));
  }

  @Test
  public void testExtractTrigrams() {

    Text text = new Text(
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
        sentenceSplitter(), wordSplitter());

    @Var
    Multiset<String> trigrams = text.trigrams("lorem", "ipsum", null, '¤');

    Assert.assertEquals(3, trigrams.size());
    Assert.assertEquals(1, trigrams.count("lorem¤ipsum¤is"));
    Assert.assertEquals(1, trigrams.count("lorem¤ipsum¤has"));
    Assert.assertEquals(1, trigrams.count("lorem¤ipsum¤passages"));

    trigrams = text.trigrams(null, "lorem", "ipsum", '¤');

    Assert.assertEquals(3, trigrams.size());
    Assert.assertEquals(1, trigrams.count("of¤lorem¤ipsum"));
    Assert.assertEquals(1, trigrams.count("industry¤lorem¤ipsum"));
    Assert.assertEquals(1, trigrams.count("containing¤lorem¤ipsum"));

    trigrams = text.trigrams("printing", null, "typesetting", '¤');

    Assert.assertEquals(1, trigrams.size());
    Assert.assertEquals(1, trigrams.count("printing¤and¤typesetting"));

    trigrams = text.trigrams("the", null, null, '¤');

    Assert.assertEquals(6, trigrams.size());
    Assert.assertEquals(1, trigrams.count("the¤release¤of"));
    Assert.assertEquals(1, trigrams.count("the¤leap¤into"));
    Assert.assertEquals(1, trigrams.count("the¤1960s¤with"));
    Assert.assertEquals(1, trigrams.count("the¤1500s¤when"));
    Assert.assertEquals(1, trigrams.count("the¤printing¤and"));
    Assert.assertEquals(1, trigrams.count("the¤industry¤s"));

    trigrams = text.trigrams(null, "typesetting", null, '¤');

    Assert.assertEquals(2, trigrams.size());
    Assert.assertEquals(1, trigrams.count("electronic¤typesetting¤remaining"));
    Assert.assertEquals(1, trigrams.count("and¤typesetting¤industry"));

    trigrams = text.trigrams(null, null, "printer", '¤');

    Assert.assertEquals(1, trigrams.size());
    Assert.assertEquals(1, trigrams.count("an¤unknown¤printer"));

    trigrams = text.trigrams(null, null, null, '¤');

    Assert.assertEquals(90, trigrams.size());

    trigrams = text.trigrams("galley", "of", "type", '¤');

    Assert.assertEquals(1, trigrams.size());
    Assert.assertEquals(1, trigrams.count("galley¤of¤type"));
  }

  private Function<String, List<String>> sentenceSplitter() {
    return text -> Splitter.on(CharMatcher.anyOf("\n\r")).trimResults().omitEmptyStrings()
        .splitToList(text);
  }

  private Function<String, List<String>> wordSplitter() {
    return text -> Splitter.on(CharMatcher.whitespace().or(CharMatcher.breakingWhitespace()))
        .trimResults().omitEmptyStrings().splitToList(
            StringIterator.removeDiacriticalMarks(text).replaceAll("\\p{P}", " ").toLowerCase());
  }
}
