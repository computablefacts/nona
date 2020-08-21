package com.computablefacts.nona.functions.stringoperators;

import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.helpers.WildcardMatcher;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class MatchWildcard extends Function {

  public MatchWildcard() {
    super("MATCHWILDCARD", true);
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 2,
        "MATCHWILDCARD takes exactly two parameters.");
    Preconditions.checkArgument(parameters.get(0).isString(), "%s should be a string",
        parameters.get(0));
    Preconditions.checkArgument(parameters.get(1).isString(), "%s should be a string",
        parameters.get(1));

    String string = parameters.get(0).asString();
    String pattern = parameters.get(1).asString();

    return BoxedType.create(WildcardMatcher.match(string, WildcardMatcher.compact(pattern)));
  }
}
