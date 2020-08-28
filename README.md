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

Nona contains a few helpers to perform text mining/NLP related tasks.

### Span

A [span](src/com/computablefacts/nona/types/Span.java) is a fragment of string 
with properties/features.

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