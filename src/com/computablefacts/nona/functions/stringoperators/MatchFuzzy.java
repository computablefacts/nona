package com.computablefacts.nona.functions.stringoperators;

import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import me.xdrop.fuzzywuzzy.FuzzySearch;

@CheckReturnValue
public class MatchFuzzy extends Function {

  public MatchFuzzy() {
    super("MATCHFUZZY", true);
  }

  @Override
  protected BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 4,
        "MATCHFUZZY takes exactly four parameters.");

    BoxedType ratioType = parameters.get(0);
    BoxedType minScore = parameters.get(1);
    BoxedType string1 = parameters.get(2);
    BoxedType string2 = parameters.get(3);

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
    return BoxedType.create(score >= minScore.asInt());
  }
}
