package com.computablefacts.nona.functions.stringoperators;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.constraints.NotNull;

import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;
import com.google.common.base.Preconditions;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Var;

@CheckReturnValue
public class MatchDictionary extends Function {

  private static final LoadingCache<String, Trie> cache_ =
      CacheBuilder.newBuilder().recordStats().maximumSize(10).expireAfterWrite(30, TimeUnit.MINUTES)
          .build(new CacheLoader<String, Trie>() {

            @Override
            public Trie load(@NotNull String file) {

              // See https://github.com/robert-bor/aho-corasick for details
              Trie.TrieBuilder builder =
                  Trie.builder().ignoreCase().ignoreOverlaps().onlyWholeWords();

              try (BufferedReader br = new BufferedReader(new FileReader(file))) {

                @Var
                String keyword;

                while ((keyword = br.readLine()) != null) {
                  builder.addKeyword(keyword.trim());
                }
              } catch (IOException e) {
                // TODO
              }
              return builder.build();
            }
          });

  public MatchDictionary() {
    super("MATCHDICTIONARY", true);
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 2,
        "MATCHDICTIONARY takes exactly two parameters.");

    // WARNING : do not check the parameters types. Always assume it is a string.
    // Preconditions.checkArgument(parameters.get(0).isString(), "%s should be a string",
    // parameters.get(0));
    // Preconditions.checkArgument(parameters.get(1).isString(), "%s should be a string",
    // parameters.get(1));

    String file = parameters.get(0).asString();
    String text = parameters.get(1).asString();

    Preconditions.checkArgument(new File(file).exists(), "%s does not exist", file);

    Trie trie = cache_.getUnchecked(file);
    Collection<Emit> emits = trie.parseText(text);
    SpanSequence sequence = new SpanSequence();

    for (Emit emit : emits) {
      Span span = new Span(text, emit.getStart(), emit.getEnd() + 1);
      sequence.add(span);
    }
    return BoxedType.create(sequence);
  }
}