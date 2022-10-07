package com.computablefacts.nona.functions.stringoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.asterix.WildcardMatcher;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.List;

@CheckReturnValue
public class MatchWildcard extends Function {

  public MatchWildcard() {
    super(eCategory.STRING_OPERATORS, "MATCH_WILDCARD",
        "MATCH_WILDCARD(x, y) returns true if x matches the y pattern, false otherwise. "
            + "Available wildcards are \"*\" (0 or more) and \"?\" (exactly one).");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 2, "MATCH_WILDCARD takes exactly two parameters.");

    String string = parameters.get(0).asString();
    String pattern = parameters.get(1).asString();

    Preconditions.checkNotNull(string, "string should not be null");
    Preconditions.checkNotNull(pattern, "pattern should not be null");

    return box(WildcardMatcher.match(string, WildcardMatcher.compact(pattern)));
  }
}
