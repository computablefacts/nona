package com.computablefacts.nona;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Classes and methods annotated by annotation whose retention policy is runtime or class and whose
 * simple name contains "Generated" (previously equality was required) are filtered out during
 * generation of report.
 *
 * See https://github.com/jacoco/jacoco/releases/tag/v0.8.2 for details.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Generated {
}
