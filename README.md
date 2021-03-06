# Nona

![Maven Central](https://img.shields.io/maven-central/v/com.computablefacts/nona)
[![Build Status](https://travis-ci.com/computablefacts/nona.svg?branch=master)](https://travis-ci.com/computablefacts/nona)
[![codecov](https://codecov.io/gh/computablefacts/nona/branch/master/graph/badge.svg)](https://codecov.io/gh/computablefacts/nona)

Nona is an extensible Excel-like programming language.

## Adding Nona to your build

Nona's Maven group ID is `com.computablefacts` and its artifact ID is `nona`.

To add a dependency on Nona using Maven, use the following:

```xml
<dependency>
  <groupId>com.computablefacts</groupId>
  <artifactId>nona</artifactId>
  <version>1.x</version>
</dependency>
```

## Snapshots 

Snapshots of Nona built from the `master` branch are available through Sonatype 
using the following dependency:

```xml
<dependency>
  <groupId>com.computablefacts</groupId>
  <artifactId>nona</artifactId>
  <version>1.x-SNAPSHOT</version>
</dependency>
```

In order to be able to download snapshots from Sonatype add the following profile 
to your project `pom.xml`:

```xml
 <profiles>
    <profile>
        <id>allow-snapshots</id>
        <activation><activeByDefault>true</activeByDefault></activation>
        <repositories>
            <repository>
                <id>snapshots-repo</id>
                <url>https://oss.sonatype.org/content/repositories/snapshots</url>
                <releases><enabled>false</enabled></releases>
                <snapshots><enabled>true</enabled></snapshots>
            </repository>
        </repositories>
    </profile>
</profiles>
```

## Publishing a new version

Deploy a release to Maven Central with these commands:

```bash
$ git tag <version_number>
$ git push origin <version_number>
```

To update and publish the next SNAPSHOT version, just change and push the version:

```bash
$ mvn versions:set -DnewVersion=<version_number>-SNAPSHOT
$ git commit -am "Update to version <version_number>-SNAPSHOT"
$ git push origin master
```

## Points of interest

Nona contains a few helpers to perform text mining/NLP related tasks :

- Data Structures
    - [Span](#span)
    - [SpanSequence](#spansequence)
    - [Text](#text)
    - [IBagOfWords](#ibagofwords)
    - [IBagOfNGrams](#ibagofngrams) 
    - [IBagOfTexts](#ibagoftexts)  
    - [ConfusionMatrix](#confusionmatrix)      
- Algorithms
    - [NGramIterator](#ngramiterator)
    - [StringIterator](#stringiterator)
    - [SnippetExtractor](#snippetextractor)
    - [DocSetLabeler](#docsetlabeler)
    - [Languages](#languages)
    - [Codecs](#codecs)
- Text-based user interface
    - [AsciiProgressBar](#asciiprogressbar)
    - [AsciiTable](#asciitable)

### Span

A [Span](src/com/computablefacts/nona/types/Span.java) is a fragment of string 
with properties/features. Furthermore, this class contains many methods to detect
and deal with overlaps between spans.

```java
// Create a new Span object
Span span = new Span("123456789", 2, 5);
span.setFeature("has_digits", "true");
span.setFeature("has_letters", "false");

// Usage
String text = span.text(); // "345"
String rawText = span.rawText(); // "123456789"
Map<String, String> features = span.features(); // {"has_digits":"true", "has_letters":"false"}
```

### SpanSequence

A [SpanSequence](src/com/computablefacts/nona/types/SpanSequence.java) is a list 
of spans.

```java
// Create a new SpanSequence object
String text = "123456789";

Span span123 = new Span(text, 0, 3);
Span span456 = new Span(text, 3, 6);
Span span789 = new Span(text, 6, 9);

SpanSequence sequence = new SpanSequence(Lists.newArrayList(span123, span456, span789));

// Usage
int size = sequence.size() // 3
Span span = sequence.span(1) // span456
```

### Text

A [Text](src/com/computablefacts/nona/helpers/Text.java) object represents the 
textual content of a document as a graph of sentences and words. The 
[Text](src/com/computablefacts/nona/helpers/Text.java) object extends the 
[IBagOfWords](#ibagofwords) and [IBagOfNGrams](ibagofngrams) interfaces.

```java
// Split text into sentences
Function<String, List<String>> sentenceSplitter = string -> 
    Splitter.on(CharMatcher.anyOf(";.?!"))
        .trimResults()
        .omitEmptyStrings()
        .splitToList(string);

// Split sentence into words
Function<String, List<String>> wordSplitter = string -> 
    Splitter.on(CharMatcher.whitespace().or(CharMatcher.breakingWhitespace()))
        .trimResults()
        .omitEmptyStrings()
        .splitToList(string);

// Create a Text object
Text text = new Text("Hello Kevin! Hello Joe!", sentenceSplitter, wordSplitter);

// Usage
String txt = text.text(); // "Hello Kevin! Hello Joe!"
Multiset<String> bow = text.bagOfWords(); // {"Hello":2, "Joe":1, "Kevin":1, "!":2}
Multiset<List<String>> bob = text.bagOfBigrams(); // {"Hello Kevin":1, "Hello Joe":1, "Kevin!":1, "Joe!":1}
```

### IBagOfWords

A [IBagOfWords](src/com/computablefacts/nona/helpers/IBagOfWords.java) is a 
representation of text that describes the occurrence of words within a document.

```java
// Create a bag-of-words
Multiset<String> bag = HashMultiset.create();
bag.add("Hello");
bag.add("Kevin");
bag.add("!");
bag.add("Hello");
bag.add("Joe");
bag.add("!");

IBagOfWords bow = IBagOfWords.wrap(bag);

// Here, bow = {"Hello":2, "Joe":1, "Kevin":1, "!":2}

// Usage
int frequency = bow.frequency("Hello"); // 2
double normalizedFrequency = bow.normalizedFrequency("Hello"); // 2/6 = 0.333333
```

### IBagOfNGrams

A [IBagOfNGrams](src/com/computablefacts/nona/helpers/IBagOfNGrams.java) is a
representation of text that describes the co-occurrences of two ore more words 
within a document.

```java
// Create a bag-of-bigrams
Multiset<List<String>> bag = HashMultiset.create();
bag.add(Lists.newArrayList("Hello", "Kevin"));
bag.add(Lists.newArrayList("Kevin", "!"));
bag.add(Lists.newArrayList("Hello", "Joe"));
bag.add(Lists.newArrayList("Joe", "!"));

IBagOfNGrams bob = IBagOfNGrams.wrap(bag);

// Here, bob = {"Hello Kevin":1, "Hello Joe":1, "Kevin!":1, "Joe!":1}

// Usage
int frequency = bob.frequency("Hello", "Joe"); // 1
double normalizedFrequency = bob.normalizedFrequency("Hello", "Joe"); // 1/4 = 0.25
String mostProbableWordAfterHello = bob.mostProbableNextWord("Hello"); // "Kevin" (probability = 0.5) or "Joe" (probability = 0.5)
```

### IBagOfTexts

A [IBagOfTexts](src/com/computablefacts/nona/helpers/IBagOfTexts.java) is a collection
of [Text](#text) objects. The [IBagOfTexts](src/com/computablefacts/nona/helpers/IBagOfTexts.java) 
object extends the [IBagOfWords](#ibagofwords) and [IBagOfNGrams](#ibagofngrams) 
interfaces. The [BagOfTexts](src/com/computablefacts/nona/helpers/BagOfTexts.java) 
class is an implementation of the [IBagOfTexts](src/com/computablefacts/nona/helpers/IBagOfTexts.java)
interface.

```java
// Split text into sentences
Function<String, List<String>> sentenceSplitter = string -> 
    Splitter.on(CharMatcher.anyOf(";.?!"))
        .trimResults()
        .omitEmptyStrings()
        .splitToList(string);

// Split sentence into words
Function<String, List<String>> wordSplitter = string -> 
    Splitter.on(CharMatcher.whitespace().or(CharMatcher.breakingWhitespace()))
        .trimResults()
        .omitEmptyStrings()
        .splitToList(string);

// Create a bag-of-texts
BagOfTexts bot = new BagOfTexts(sentenceSplitter, wordSplitter);
bot.add("Hello Kevin! Hello Joe!");
bot.add("Goodbye Bill. Goodbye Joe.");

// Usage
Multiset<String> bow = bot.bagOfWords(); // {"Hello":2, "Goodbye":2, "Kevin":1, "Joe":2, "Bill":1, ".":2, "!":2}
Multiset<List<String>> bob = bot.bagOfBigrams(); // {"Hello Kevin":1, "Hello Joe":1, "Kevin!":1, "Joe!":1, "Goodbye Bill":1, "Goodbye Joe":1, "Bill.":1, "Joe.":1}

int nbDistinctTexts = bot.numberOfDistinctTexts(); // 2
int avgTextLength = bot.averageTextLength(); // (6 + 6) / 2 = 6

int nbDistinctDocsHello = bot.numberOfDistinctTextsOccurrences("Hello"); // 1
int nbDistinctDocsJoe = bot.numberOfDistinctTextsOccurrences("Joe"); // 2

double dfHello = bot.documentFrequency("Hello"); // 1 / 2 = 0.5
double dfJoe = bot.documentFrequency("Joe"); // 2 / 2 = 1

double idfHello = bot.inverseDocumentFrequency("Hello"); // 1 + log(2 / (1 + 1))
double idfJoe = bot.inverseDocumentFrequency("Joe"); // 1 + log(2 / (1 + 2))

...

List<Text> textsJoe = bot.find(Sets.newHashSet("Joe"), 10, IBagOfTexts::bm25); // textsJoe = ["Hello Kevin! Hello Joe!", "Goodbye Bill. Goodbye Joe."]
List<Text> textsKevinAndJoe = bot.find(Sets.newHashSet("Kevin", "Joe"), 10, IBagOfTexts::bm25); // textsKevinAndJoe = ["Hello Kevin! Hello Joe!"]
```

### ConfusionMatrix

A [ConfusionMatrix](src/com/computablefacts/nona/helpers/ConfusionMatrix.java) is 
a tool to evaluate the accuracy of a classification. The following metrics are 
available :

- Matthews Correlation Coefficient
- Accuracy
- Positive Predictive Value (aka. Precision)
- Negative Predictive Value
- Sensitivity (aka. Recall)
- Specificity
- F1 Score
- False Positive Rate
- False Discovery Rate
- False Negative Rate

Furthermore, two functions have been added to compute the following metrics :  

- Micro-Average
- Macro-Average

```java
ConfusionMatrix matrix = new ConfusionMatrix("");
matrix.addTruePositives(620);
matrix.addTrueNegatives(8820);
matrix.addFalsePositives(180);
matrix.addFalseNegatives(380);

double mcc = matrix.matthewsCorrelationCoefficient(); // 0.0001
double accuracy = matrix.accuracy(); // 0.000001
double sensitivity = matrix.sensitivity(); // 0.000001
...
```

### NGramIterator

A [NGramIterator](src/com/computablefacts/nona/helpers/NGramIterator.java) allows
the creation of a list of [n-grams](https://en.wikipedia.org/wiki/N-gram) from a
string.

```java
//  Create a list of overlapping 3-grams
Iterator<Span> iterator = new NGramIterator(3, "overlaps", true);
List<Span> ngrams = Lists.newArrayList(iterator);

// Here, ngrams = ["ove", "ver", "erl", "rla", "lap", "aps"]

//  Create a list of non-overlapping 3-grams
Iterator<Span> iterator = new NGramIterator(3, "overlaps", false);
List<Span> ngrams = Lists.newArrayList(iterator);

// Here, ngrams = ["ove", "rla", "ps"]
```

### StringIterator

A [StringIterator](src/com/computablefacts/nona/helpers/StringIterator.java) 
facilitates iterating over the characters in a string. Furthermore, this class
contains many functions to find a character category : punctuation marks, arrows, 
hyphens, apostrophes, bullets, quotation marks, etc. 

```java
// Split a string on white-space characters
String text = "123 456 789";
SpanSequence sequence = new SpanSequence();
StringIterator iterator = new StringIterator(text);

while (iterator.hasNext()) {
    
    iterator.movePastWhitespace();
    int begin = iterator.position();
    iterator.moveToWhitespace();
    int end = iterator.position();

    sequence.add(new Span(text, begin, end));
}

// Here, sequence = ["123", "456", "789"]
```

### SnippetExtractor

A [SnippetExtractor](src/com/computablefacts/nona/helpers/SnippetExtractor.java)
allows the extraction (from a text) of the snippet that contains the most dense 
selection of words (from a given list).

```java
String text = 
    "Welcome to Yahoo!, the world’s most visited home page. Quickly find what you’re " +
    "searching for, get in touch with friends and stay in-the-know with the latest news " +
    "and information. CloudSponge provides an interface to easily enable your users to " +
    "import contacts from a variety of the most popular webmail services including Yahoo, " +
    "Gmail and Hotmail/MSN as well as popular desktop address books such as Mac Address Book " +
    "and Outlook.";

String words = Lists.newArrayList("latest", "news", "CloudSponge");

String snippet = SnippetExtractor.extract(words, text);

// Here, snippet = "...touch with friends and stay in-the-know with the latest news and 
//                  information. CloudSponge provides an interface to easily enable your 
//                  users to import contacts from a variety of the most popular webmail 
//                  services including Yahoo, Gmail and Hotmail/MSN as well as popular 
//                  desktop address books such as Mac..."
```

### DocSetLabeler

A highly customizable implementation of the [DocSetLabeler](src/com/computablefacts/nona/helpers/DocSetLabeler.java) 
[algorithm](https://arxiv.org/abs/1409.7591) :

> An algorithm capable of generating expressive thematic labels for any subset of
> documents in a corpus can greatly facilitate both characterization and navigation
> of document collections.

### Languages

The [Languages](src/com/computablefacts/nona/helpers/Languages.java) class 
contains helpers to :

- Perform [language identification](https://en.wikipedia.org/wiki/Language_identification),
- Perform [POS-tagging](https://en.wikipedia.org/wiki/Part-of-speech_tagging),
- Perform [stemming](https://en.wikipedia.org/wiki/Stemming),
- Load lists of [stopwords](https://en.wikipedia.org/wiki/Stop_word),

for the following languages :

- ARABIC
- BASQUE
- CATALAN
- DANISH
- DUTCH
- ENGLISH
- FINNISH
- FRENCH
- GERMAN
- GREEK
- HINDI
- HUNGARIAN
- INDONESIAN
- IRISH
- ITALIAN
- LITHUANIAN
- NEPALI
- NORWEGIAN
- PORTUGUESE
- ROMANIAN
- RUSSIAN
- SPANISH
- SWEDISH
- TAMIL
- TURKISH

Note that all libraries used are business-friendly :

- The [Optimaize](https://github.com/optimaize/language-detector) language 
identification algorithm is licenced under the Apache 2 Licence.
- The [Snowball](https://snowballstem.org/license.html) stemmers are licenced
under the 3-clause BSD Licence.
- The [RDRPOSTagger](http://rdrpostagger.sourceforge.net/) POS-tagger is licenced
under GPL-3 Licence.
- The [Solr](https://lucene.apache.org) lists of stopwords are licenced under 
the Apache 2 Licence.

```java
String sentence = "Ha bisogno di una tazza di zucchero .";
List<String> words = Splitter.on(' ')
    .trimResults()
    .omitEmptyStrings()
    .splitToList(sentence);

// Language identification
Map.Entry<eLanguage, Double> language = Languages.language(sentence); 

// Here, language = {"language":"ITALIAN", "confidence_score":0.9999995292613422}

// Stopwords
Set<String> stopwords = Languages.stopwords(language.getKey());

// Stemming
SnowballStemmer stemmer = Languages.stemmer(language.getKey());

stemmer.setCurrent(words.get(0));
String stemHa = stemmer.getCurrent(); // "ha"

stemmer.setCurrent(words.get(1));
String stemBisogno = stemmer.getCurrent(); // "bisogn"

...

// POS Tagging
List<Map.Entry<String, String>> tags = Languages.tag(language.getKey(), sentence);

// Here, tags = [{Ha,V}, {bisogno,S}, {di,E}, {una,RI}, {tazza,S}, {di,E}, {zucchero,S}, {.,FS}]
// See http://medialab.di.unipi.it/wiki/Tanl_POS_Tagset for tags meanings
```

### Codecs

The [Codecs](src/com/computablefacts/nona/helpers/Codecs.java) and 
[BigDecimalCodec](src/com/computablefacts/nona/helpers/BigDecimalCodec.java) classes 
contain helpers to :

- Encode/decode objects to/from JSON strings,
- Encode/decode string to/from Base64,
- Encode a number as a string such that the lexicographic order of the generated 
string is in the same order as the numeric order.

```java
Pair<String, String> pair = new Pair<>("key1", "value1");
String json = Codecs.asString(pair); // {"key":"key1","value":"value1"}

List<Pair<?, ?>> pairs = Lists.newArrayList(new Pair<>("key1", "value1"), new Pair<>("key2", 2), new Pair<>("key3", false));
String json = Codecs.asString(pairs); // "[{"key":"key1","value":"value1"},{"key":"key2","value":2},{"key":"key3","value":false}]"

Map<String, Object> json = Codecs.asObject("{\"key\":\"key1\",\"value\":\"value1\"}");
Collection<Map<String, Object>> json = Codecs.asCollection("[{\"key\":\"key1\",\"value\":\"value1\"},{\"key\":\"key2\",\"value\":2},{\"key\":\"key3\",\"value\":false}]");

Codecs.decodeB64(Base64.getDecoder(), "dGVzdA=="); // test
Codecs.encodeB64(Base64.getEncoder(), "test"); // dGVzdA==

BigDecimalCodec.encode(BigDecimal.valueOf(123456789L)); // ??9123456789*
BigDecimalCodec.encode(BigDecimal.valueOf(-123456789L)); // **0876543210?
```

### AsciiProgressBar

The [AsciiProgressBar](src/com/computablefacts/nona/helpers/AsciiProgressBar.java) class 
contains helpers to display a textual progress bar while enumerating a bounded or 
an unbounded dataset.

```java
// Progress bar on a bounded dataset
AtomicInteger count = new AtomicInteger(0);
AsciiProgressBar.ProgressBar bar = AsciiProgressBar.create();
List<String> list = ...
list.peek(e ->  bar.update(count.incrementAndGet(), list.size())).forEach(System.out::println);

// Progress bar on an unbounded dataset
AsciiProgressBar.IndeterminateProgressBar bar = AsciiProgressBar.createIndeterminate();
Stream<String> stream = ...
stream.peek(e -> bar.update()).forEach(System.out::println);
bar.complete(); // reset
```

### AsciiTable

The [AsciiTable](src/com/computablefacts/nona/helpers/AsciiTable.java) class
contains helpers to display a textual table.

```java
String[][] table = ...
        
// Display a table with header
System.out.println(AsciiTable.format(table, true));

// Display a table without header
System.out.println(AsciiTable.format(table, false));
```