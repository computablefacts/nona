package com.computablefacts.nona.functions.stringoperators;

import java.util.List;
import java.util.stream.Collectors;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.functions.utils.SnippetExtractor;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class ExtractSnippet extends Function {

  public ExtractSnippet() {
    super("EXTRACTSNIPPET", true);
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() >= 2,
        "EXTRACTSNIPPET takes at least two parameters.");
    Preconditions.checkArgument(parameters.get(0).isString(), "%s should be a string",
        parameters.get(0));

    String text = parameters.get(0).asString();
    List<String> words = parameters.subList(1, parameters.size()).stream().map(BoxedType::asString)
        .collect(Collectors.toList());

    return BoxedType.create(SnippetExtractor.extract(words, text));
  }
}
