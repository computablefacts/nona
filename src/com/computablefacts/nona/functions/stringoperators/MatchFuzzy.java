package com.computablefacts.nona.functions.stringoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.List;
import me.xdrop.fuzzywuzzy.FuzzySearch;

@CheckReturnValue
public class MatchFuzzy extends Function {

  public MatchFuzzy() {
    super(eCategory.STRING_OPERATORS, "MATCH_FUZZY",
        "MATCH_FUZZY(r, m, x, y) returns true if x and y matches with a score greater than or equal to m using the r algorithm, false otherwise. "
            + "Possible values for r are \"simple\", \"partial\", \"weighted\", \"token_set_simple\", \"token_sort_simple\", \"token_set_partial\" and \"token_sort_partial\". "
            + "m must be an integer between 0 and 100.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 4, "MATCH_FUZZY takes exactly four parameters.");
    Preconditions.checkArgument(parameters.get(0).isString(), "%s should be a string", parameters.get(0));
    Preconditions.checkArgument(parameters.get(1).isNumber(), "%s should be a number", parameters.get(1));
    Preconditions.checkArgument(parameters.get(2).isString(), "%s should be a string", parameters.get(2));
    Preconditions.checkArgument(parameters.get(3).isString(), "%s should be a string", parameters.get(3));

    BoxedType<?> ratioType = parameters.get(0);
    BoxedType<?> minScore = parameters.get(1);
    BoxedType<?> string1 = parameters.get(2);
    BoxedType<?> string2 = parameters.get(3);

    int score;

    if (ratioType.asString().equals("partial")) {
      score = FuzzySearch.partialRatio(string1.asString(), string2.asString());
    } else if (ratioType.asString().equals("weighted")) {
      score = FuzzySearch.weightedRatio(string1.asString(), string2.asString());
    } else if (ratioType.asString().equals("token_set_simple")) {
      score = FuzzySearch.tokenSetRatio(string1.asString(), string2.asString());
    } else if (ratioType.asString().equals("token_sort_simple")) {
      score = FuzzySearch.tokenSortRatio(string1.asString(), string2.asString());
    } else if (ratioType.asString().equals("token_set_partial")) {
      score = FuzzySearch.tokenSetPartialRatio(string1.asString(), string2.asString());
    } else if (ratioType.asString().equals("token_sort_partial")) {
      score = FuzzySearch.tokenSortPartialRatio(string1.asString(), string2.asString());
    } else { // simple
      score = FuzzySearch.ratio(string1.asString(), string2.asString());
    }
    return box(score >= minScore.asInt());
  }
}
