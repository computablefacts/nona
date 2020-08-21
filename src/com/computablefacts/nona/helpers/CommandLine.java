package com.computablefacts.nona.helpers;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Var;

@CheckReturnValue
public abstract class CommandLine {

  protected CommandLine() {}

  public static File getFileCommand(String[] args, String name, String defaultValue) {
    String filename = getStringCommand(args, name, defaultValue);
    File file = new File(filename);
    if (!file.isFile()) {
      throw new RuntimeException("Invalid file \"" + filename + "\" (not a file)");
    }
    // if (file.length() <= 0) {
    // throw new RuntimeException("Invalid file \"" + filename + "\" (size <= 0)");
    // }
    return file;
  }

  public static String getStringCommand(String[] args, String name, String defaultValue) {
    String retVal = getArg(args, name, defaultValue);
    if (retVal == null) {
      if (defaultValue == null) {
        return null;
      }
      throw new RuntimeException("Missing command \"" + name + "\"");
    }
    return retVal;
  }

  public static Integer getIntCommand(String[] args, String name, Integer defaultValue) {
    String retVal =
        getArg(args, name, defaultValue == null ? null : Integer.toString(defaultValue));
    if (retVal == null) {
      if (defaultValue == null) {
        return null;
      }
      throw new RuntimeException("Missing command \"" + name + "\"");
    }
    return Integer.parseInt(retVal, 10);
  }

  public static Double getDoubleCommand(String[] args, String name, Double defaultValue) {
    String retVal = getArg(args, name, defaultValue == null ? null : Double.toString(defaultValue));
    if (retVal == null) {
      if (defaultValue == null) {
        return null;
      }
      throw new RuntimeException("Missing command \"" + name + "\"");
    }
    return Double.parseDouble(retVal);
  }

  public static Boolean getBooleanCommand(String[] args, String name, Boolean defaultValue) {
    String retVal =
        getArg(args, name, defaultValue == null ? null : Boolean.toString(defaultValue));
    if (retVal == null) {
      if (defaultValue == null) {
        return null;
      }
      throw new RuntimeException("Missing command \"" + name + "\"");
    }
    return Boolean.parseBoolean(retVal);
  }

  public static Map<String, String> getAttributesCommand(String[] args, String name,
      String defaultValue) {
    String retVal = getArg(args, name, defaultValue);
    if (retVal == null) {
      if (defaultValue == null) {
        return null;
      }
      throw new RuntimeException("Missing command \"" + name + "\"");
    }
    Map<String, String> attributes = new HashMap<>();
    if (!Strings.isNullOrEmpty(retVal)) {
      List<String> kvs = Splitter.on('&').trimResults().omitEmptyStrings().splitToList(retVal);
      for (String kv : kvs) {
        List<String> keyValue = Splitter.on('=').trimResults().omitEmptyStrings().splitToList(kv);
        if (keyValue.size() != 2) {
          throw new RuntimeException(kv + " is an invalid key=value pair");
        }
        if (attributes.containsKey(keyValue.get(0))) {
          throw new RuntimeException("The key " + keyValue.get(0) + " is present twice");
        }
        attributes.put(keyValue.get(0), keyValue.get(1));
      }
    }
    return attributes;
  }

  public static String getArg(String[] args, String name) {
    return getArg(args, name, null);
  }

  public static String getArg(String[] args, String name, String defaultValue) {
    @Var
    int argIdx = -1;
    for (int idx = 0; idx < args.length; idx++) {
      if (("-" + name).equals(args[idx])) {
        argIdx = idx;
        break;
      }
    }
    if (argIdx == -1) {
      return defaultValue;
    }
    if (argIdx < args.length - 1) {
      return args[argIdx + 1].trim();
    }
    throw new RuntimeException("Missing argument value. Argument name: " + name);
  }
}
