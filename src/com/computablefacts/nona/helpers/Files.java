package com.computablefacts.nona.helpers;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.computablefacts.logfmt.LogFormatter;
import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Var;

@Deprecated
@CheckReturnValue
final public class Files {

  private static final Logger logger_ = LoggerFactory.getLogger(Files.class);

  private Files() {}

  public static void delete(File file) {

    Preconditions.checkNotNull(file, "file must not be null");
    Preconditions.checkArgument(file.exists(), "file does not exist : %s", file);

    try {
      java.nio.file.Files.delete(file.toPath());
    } catch (IOException e) {
      logger_.error(LogFormatter.create(true).message(e).formatError());
    }
  }

  public static void create(File file, String text) {

    Preconditions.checkNotNull(file, "file must not be null");
    Preconditions.checkArgument(!file.exists(), "file already exists : %s", file);
    Preconditions.checkNotNull(text, "text must not be null");

    try {
      java.nio.file.Files.write(file.toPath(), text.getBytes(Charsets.UTF_8),
          StandardOpenOption.CREATE);
    } catch (IOException e) {
      logger_.error(LogFormatter.create(true).message(e).formatError());
    }
  }

  public static void create(File file, List<String> rows) {

    Preconditions.checkNotNull(file, "file must not be null");
    Preconditions.checkArgument(!file.exists(), "file already exists : %s", file);
    Preconditions.checkNotNull(rows, "rows must not be null");

    try {
      java.nio.file.Files.write(file.toPath(), rows, Charsets.UTF_8, StandardOpenOption.CREATE);
    } catch (IOException e) {
      logger_.error(LogFormatter.create(true).message(e).formatError());
    }
  }

  public static void append(File file, String text) {

    Preconditions.checkNotNull(file, "file must not be null");
    Preconditions.checkArgument(file.exists(), "file does not exist : %s", file);
    Preconditions.checkNotNull(text, "text must not be null");

    try {
      java.nio.file.Files.write(file.toPath(), text.getBytes(Charsets.UTF_8),
          StandardOpenOption.APPEND);
    } catch (IOException e) {
      logger_.error(LogFormatter.create(true).message(e).formatError());
    }
  }

  public static void append(File file, List<String> rows) {

    Preconditions.checkNotNull(file, "file must not be null");
    Preconditions.checkArgument(file.exists(), "file does not exist : %s", file);
    Preconditions.checkNotNull(rows, "rows must not be null");

    try {
      java.nio.file.Files.write(file.toPath(), rows, Charsets.UTF_8, StandardOpenOption.APPEND);
    } catch (IOException e) {
      logger_.error(LogFormatter.create(true).message(e).formatError());
    }
  }

  public static void gzip(File input, File output) {

    Preconditions.checkNotNull(input, "input must not be null");
    Preconditions.checkArgument(input.exists(), "input does not exist : %s", input);
    Preconditions.checkNotNull(output, "output must not be null");
    Preconditions.checkArgument(!output.exists(), "output already exists : %s", output);

    try (FileInputStream fis = new FileInputStream(input)) {
      try (FileOutputStream fos = new FileOutputStream(output)) {
        try (GZIPOutputStream gzip = new GZIPOutputStream(fos)) {

          byte[] buffer = new byte[1024];
          @Var
          int len;

          while ((len = fis.read(buffer)) != -1) {
            gzip.write(buffer, 0, len);
          }
        } catch (IOException e) {
          logger_.error(LogFormatter.create(true).message(e).formatError());
        }
      } catch (IOException e) {
        logger_.error(LogFormatter.create(true).message(e).formatError());
      }
    } catch (IOException e) {
      logger_.error(LogFormatter.create(true).message(e).formatError());
    }
  }

  public static void gunzip(File input, File output) {

    Preconditions.checkNotNull(input, "input must not be null");
    Preconditions.checkArgument(input.exists(), "input does not exist : %s", input);
    Preconditions.checkNotNull(output, "output must not be null");
    Preconditions.checkArgument(!output.exists(), "output already exists : %s", output);

    try (FileInputStream fis = new FileInputStream(input)) {
      try (GZIPInputStream gzip = new GZIPInputStream(fis)) {
        try (FileOutputStream fos = new FileOutputStream(output)) {

          byte[] buffer = new byte[1024];
          @Var
          int len;

          while ((len = gzip.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
          }
        } catch (IOException e) {
          logger_.error(LogFormatter.create(true).message(e).formatError());
        }
      } catch (IOException e) {
        logger_.error(LogFormatter.create(true).message(e).formatError());
      }
    } catch (IOException e) {
      logger_.error(LogFormatter.create(true).message(e).formatError());
    }
  }

  public static String load(File file, Charset charset) {
    return lineStream(file, charset).map(Map.Entry::getValue).collect(Collectors.joining("\n"));
  }

  public static String loadCompressed(File file, Charset charset) {
    return compressedLineStream(file, charset).map(Map.Entry::getValue)
        .collect(Collectors.joining("\n"));
  }

  public static Stream<Map.Entry<Integer, String>> lineStream(File file, Charset charset) {
    return lineReaderToStream(lineNumberReader(file, charset));
  }

  public static Stream<Map.Entry<Integer, String>> compressedLineStream(File file,
      Charset charset) {
    return lineReaderToStream(compressedLineNumberReader(file, charset));
  }

  private static LineNumberReader lineNumberReader(File file, Charset charset) {

    Preconditions.checkNotNull(file, "file must not be null");
    Preconditions.checkArgument(file.exists(), "file does not exist : %s", file);
    Preconditions.checkNotNull(charset, "charset must not be null");

    try {
      return new LineNumberReader(java.nio.file.Files.newBufferedReader(file.toPath(), charset));
    } catch (IOException e) {
      logger_.error(LogFormatter.create(true).message(e).formatError());
    }
    return null;
  }

  private static LineNumberReader compressedLineNumberReader(File file, Charset charset) {

    Preconditions.checkNotNull(file, "file must not be null");
    Preconditions.checkArgument(file.exists(), "file does not exist : %s", file);
    Preconditions.checkNotNull(charset, "charset must not be null");

    try {
      return new LineNumberReader(
          new InputStreamReader(new GZIPInputStream(new FileInputStream(file)), charset));
    } catch (IOException e) {
      logger_.error(LogFormatter.create(true).message(e).formatError());
    }
    return null;
  }

  private static Stream<Map.Entry<Integer, String>> lineReaderToStream(LineNumberReader reader) {
    if (reader != null) {
      Iterator<Map.Entry<Integer, String>> iterator =
          new AbstractIterator<Map.Entry<Integer, String>>() {

            @Override
            protected Map.Entry<Integer, String> computeNext() {
              try {
                String line = reader.readLine();
                if (line != null) {
                  return new AbstractMap.SimpleImmutableEntry<>(reader.getLineNumber(), line);
                }
              } catch (IOException e) {
                logger_.error(LogFormatter.create(true).message(e).formatError());
              }
              try {
                reader.close();
              } catch (IOException e) {
                logger_.error(LogFormatter.create(true).message(e).formatError());
              }
              return endOfData();
            }
          };
      return StreamSupport
          .stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false)
          .onClose(() -> {
            try {
              reader.close();
            } catch (IOException e) {
              logger_.error(LogFormatter.create(true).message(e).formatError());
            }
          });
    }
    return Stream.empty();
  }
}
