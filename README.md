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
  <version>3.x</version>
</dependency>
```

## Snapshots 

Snapshots of Nona built from the `master` branch are available through Sonatype 
using the following dependency:

```xml
<dependency>
  <groupId>com.computablefacts</groupId>
  <artifactId>nona</artifactId>
  <version>3.x-SNAPSHOT</version>
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
                <url>https://s01.oss.sonatype.org/content/repositories/snapshots</url>
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

- Algorithms
    - [Languages](#languages)

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
