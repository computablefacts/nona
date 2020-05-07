package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsForward.financialNumber;

public class FinancialNumber extends PatternExtract {

  public FinancialNumber() {
    super("FINANCIAL_NUMBER", financialNumber());
  }
}
