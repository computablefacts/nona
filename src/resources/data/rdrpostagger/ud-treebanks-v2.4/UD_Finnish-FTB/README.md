# Summary

FinnTreeBank 1 consists of manually annotated grammatical examples
from VISK. The UD version of FinnTreeBank 1 was converted from a
native annotation model with a script and later manually revised.

# Introduction

The UD version of FinnTreeBank 1 was derived from FinnTreeBank 1 2014
by a scripted mapping of labels and some restructuring in an attempt
to conform approximately to the UD Finnish model.

Unannotated linguistic material in FinnTreeBank 1 2014 was adapted
from the examples in Ison suomen kieliopin verkkoversio (The Web
Version of the Large Grammar of Finnish, VISK), available on-line as
<http://scripta.kotus.fi/visk>, and annotations originally produced
and further revised in FIN-CLARIN projects in the Department of Modern
Languages, University of Helsinki.


## Differences from the UD Finnish model

Some differences from the UD Finnish model remain unaddressed.

Surface words that have an adverb or a conjunction fused with a
following negative verb are separated into two tokens in the
FinnTreeBank model. The UD version keeps them as such but adds the
unannotated joined token before the two.

Most punctuation tokens are linked to a nearby token instead of a
clause head.

As a catchall, the dep relation is used as intended when a more proper
mapping could not be determined.

Some FinnTreeBank annotations and other notes are retained in the MISC
field.


## Splitting

The previous version of the treebank was split into training,
development, and test sets by repeatedly taking 8 sentences into
training set, 1 into development set, and 1 into test set.


# Statistics

Tree count:  18723
Word count:  159612
Token count: 159314
Dep. relations: 38 of which 8? language specific
POS tags: 17
Category=value feature pairs: 80


# Sources

VISK = Auli Hakulinen, Maria Vilkuna, Riitta Korhonen, Vesa Koivisto,
Tarja Riitta Heinonen and Irja Alho 2004: Iso suomen
kielioppi. Helsinki: Suomalaisen Kirjallisuuden Seura. Online version.
Available: http://scripta.kotus.fi/visk URN:ISBN:978-952-5446-35-7

<http://www.ling.helsinki.fi/kieliteknologia/tutkimus/treebank/>

fin-clarin@helsinki.fi

# Changelog

* UD v1.1 -> UD v1.2

Negative=Yes was changed to Negative=Neg
The data was re-split to resolve train/dev/test overlaps within the treebank as well as with UD_Finnish

* UD v1.2 -> UD v1.3

A major revision of the underlying FinnTreebank 1 with some manually
annotated UD analyses (notably xcomp; manual annotations are visible
in the UD version as Alt in the MISC field).

Adjusted the conversion script to better match the universal
guidelines (e.g. finite verbs now have VerbForm, again using PART) and
the Finnish guidelines (e.g. now using nsubj:cop, csubj:cop,
nmod:own).

Components of multiword names are now separate tokens.

Duplicates in the underlying FinnTreebank 1 have been removed and
sentences are now labeled with unique identifiers like "ekvje-124"
(the part before the hyphen identifies the sentence within
FinnTreebank 1).

* UD v1.3 -> UD v2

nmod:own -> nmod, rotate conj groups to have head on left

* UD v2 -> UD v2.4

Revised annotations that were rejected by the validator
(except those that the validator only rejects since 2019-05-01)
- VERB/aux - change pos to AUX
- non-ADV/advmod, non-NUM/nummod - change pos or rel
- children of 'case' and 'mark' - hoist to parent
- cop other than 'olla' - re-annotate manually
- non-projective punctuation - re-attached by script
- 'koska' and 'kun' as SCONJ/advmod - changed most to PART
- etc.

<pre>
=== Machine-readable metadata (DO NOT REMOVE!) ================================
Data available since: UD v1.1
License: CC BY 4.0
Includes text: yes
Genre: grammar-examples
Lemmas: converted from manual
UPOS: converted from manual
XPOS: manual native
Features: converted from manual
Relations: converted from manual
Contributors: Piitulainen, Jussi; Nurmi, Hanna
Contributing: elsewhere
Contact: fin-clarin@helsinki.fi
===============================================================================
</pre>
