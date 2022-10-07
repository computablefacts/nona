package com.computablefacts.nona.functions.stringoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.asterix.SnippetExtractor;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@CheckReturnValue
public class Snippet extends Function {

  public Snippet() {
    super(eCategory.STRING_OPERATORS, "SNIPPET",
        "SNIPPET(x, a, ..., c) extract a snippet from x centered around the words a, ..., c.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() >= 2, "SNIPPET takes at least two parameters.");

    String text = parameters.get(0).asString();

    Preconditions.checkNotNull(text, "text should not be null");

    List<String> words = parameters.subList(1, parameters.size()).stream().map(BoxedType::asString)
        .filter(Objects::nonNull).collect(Collectors.toList());

    return box(SnippetExtractor.extract(words, text));
  }
}
