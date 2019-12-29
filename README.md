# Nona

![Maven Central](https://img.shields.io/maven-central/v/com.computablefacts/nona)
[![Build Status](https://travis-ci.com/computablefacts/nona.svg?branch=master)](https://travis-ci.com/computablefacts/nona)
[![codecov](https://codecov.io/gh/computablefacts/nona/branch/master/graph/badge.svg)](https://codecov.io/gh/computablefacts/nona)

Nona is an extensible Excel-like programming language.

## Adding Nona to your build

Nona's Maven group ID is `com.computablefacts` and its artifact ID is `nona`.

To add a dependency on Nona (STABLE) using Maven, use the following:

```xml
<dependency>
  <groupId>com.computablefacts</groupId>
  <artifactId>nona</artifactId>
  <version>1.0</version>
</dependency>
```

To add a dependency on Nona (SNAPSHOT) using Maven, use the following:

```xml
<dependency>
  <groupId>com.computablefacts</groupId>
  <artifactId>nona</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```

SNAPSHOT artifacts are pushed to Sonatype on each master commit.

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