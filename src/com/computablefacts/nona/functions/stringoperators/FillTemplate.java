package com.computablefacts.nona.functions.stringoperators;

import au.com.codeka.carrot.CarrotEngine;
import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.bindings.MapBindings;
import au.com.codeka.carrot.resource.FileResourceLocator;
import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.errorprone.annotations.CheckReturnValue;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CheckReturnValue
public class FillTemplate extends Function {

  public FillTemplate() {
    super(eCategory.STRING_OPERATORS, "FILL_TEMPLATE",
        "FILL_TEMPLATE(t, u, v, x, y, ...) load template t from disk and fill it by replacing keyword u with value v, keyword x with value y, etc.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() >= 3, "FILL_TEMPLATE takes at least three parameters.");

    File file = new File(parameters.get(0).asString());

    Preconditions.checkState(file.exists(), "template not found : %s", parameters.get(0));
    Preconditions.checkState((parameters.size() - 1) % 2 == 0,
        "mismatch between the number of keywords and the number of values");

    Map<String, Object> bindings = new HashMap<>();

    for (int i = 1; i < parameters.size(); i += 2) {
      bindings.put(parameters.get(i).asString(), parameters.get(i + 1).value());
    }
    return box(fill(file.getAbsolutePath(), bindings));
  }

  /**
   * Fill a template.
   *
   * @param template the template to fill.
   * @param bindings the bindings to use for filling the template.
   * @return a filled template.
   */
  protected String fill(String template, Map<String, Object> bindings) {
    try {
      CarrotEngine engine = new CarrotEngine(
          new Configuration.Builder().setEncoding("utf-8").setResourceLocator(new FileResourceLocator.Builder(template))
              .build());
      return engine.process(template, new MapBindings(bindings));
    } catch (CarrotException e) {
      System.out.println(Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
    }
    return "";
  }
}

