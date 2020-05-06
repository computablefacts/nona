package com.computablefacts.nona.functions.patternoperators;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.computablefacts.nona.dictionaries.Elf;
import com.computablefacts.nona.functions.stringoperators.RegexExtract;
import com.computablefacts.nona.types.BoxedType;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.re2j.Pattern;

public class ExtractCompanyName extends RegexExtract {

  private static final List<Elf> ELF_DICTIONARY = Elf.load();
  private static final String ELF_ABBREVIATION_PATTERN =
      Joiner.on("|").join(ELF_DICTIONARY.stream().flatMap(elf -> {

        Set<String> abbr1 =
            elf.abbreviationLocal().stream().map(String::toUpperCase).collect(Collectors.toSet());
        Set<String> abbr2 = elf.abbreviationTransliterated().stream().map(String::toUpperCase)
            .collect(Collectors.toSet());

        Set<String> set = new HashSet<>();
        set.addAll(abbr1);
        set.addAll(abbr2);
        set.addAll(abbr1.stream().map(abbr -> abbr.replace(".", "")).collect(Collectors.toSet()));
        set.addAll(abbr2.stream().map(abbr -> abbr.replace(".", "")).collect(Collectors.toSet()));

        return set.stream();
      }).filter(abbr -> !Strings.isNullOrEmpty(abbr)).map(abbr -> Pattern
          .quote(new StringBuilder(abbr).reverse().toString()).replaceAll("\\p{Zs}+", "\\\\p{Zs}+"))
          .collect(Collectors.toSet()));

  public ExtractCompanyName() {
    super("EXTRACT_COMPANY_NAME");
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 1,
        "EXTRACT_COMPANY_NAME takes exactly one parameter : %s", parameters);
    Preconditions.checkArgument(parameters.get(0).isString(), "%s should be a string",
        parameters.get(0));

    List<BoxedType> newParameters = new ArrayList<>();
    newParameters.add(
        BoxedType.create(new StringBuilder(parameters.get(0).asString()).reverse().toString()));
    newParameters.add(BoxedType.create(

        // Left boundary
        "(?:^|\\p{Zs}|\\b)" +

        // Entity legal form
            "(?i)(" + ELF_ABBREVIATION_PATTERN + ")(?-i)" +

            // Whitespace
            "(?:\\p{Zs}+\\p{Ll}{1,3}\\p{Zs}+|\\p{Zs}+|[&,])" +

            // Company name must start with an upper-case letter
            // TODO : can a company name starts with a number?
            "((?:(?:\\p{Zs}+\\p{Ll}{1,3}\\p{Zs}+|\\p{Zs}+|[&,-])*(?:(?:\\p{L}|\\p{N})+\\p{Lu}))+)" +

            // Right boundary
            "(?:$|\\p{Zs}|\\b)"));

    BoxedType boxedType = super.evaluate(newParameters);
    SpanSequence sequence = (SpanSequence) boxedType.value();
    SpanSequence newSequence = new SpanSequence();

    for (Span span : sequence.sequence()) {

      String text = parameters.get(0).asString();
      Span newSpan = new Span(text, text.length() - span.end(), text.length() - span.begin());

      newSpan.setFeature("LEGAL_FORM",
          new StringBuilder(span.getFeature("GROUP_1")).reverse().toString().trim());
      newSpan.setFeature("COMPANY_NAME",
          new StringBuilder(span.getFeature("GROUP_2")).reverse().toString().trim());

      newSequence.add(newSpan);
    }
    return BoxedType.create(newSequence);
  }
}
