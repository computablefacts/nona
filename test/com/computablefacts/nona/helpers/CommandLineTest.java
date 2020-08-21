package com.computablefacts.nona.helpers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class CommandLineTest {

  @Test(expected = RuntimeException.class)
  public void testMissingArgumentValue() {

    String[] args = new String[] {"-cmd"};

    String value = CommandLine.getArg(args, "cmd");
  }

  @Test(expected = RuntimeException.class)
  public void testGetFileCommandWrongArgWithDefault() {

    String[] args = new String[] {"-cmd", "c:\\test.txt"};

    File f = CommandLine.getFileCommand(args, "nop", "");
  }

  @Test(expected = NullPointerException.class)
  public void testGetFileCommandWrongArgWithoutDefault() {

    String[] args = new String[] {"-cmd", "c:\\test.txt"};

    File f = CommandLine.getFileCommand(args, "nop", null);
  }

  @Test
  public void testGetFileCommand() throws IOException {

    File file = Files.createTempFile("tmp", "-1").toFile();

    String[] args = new String[] {"-cmd", file.getAbsolutePath()};

    Assert.assertEquals(file.getAbsolutePath(),
        CommandLine.getFileCommand(args, "cmd", "").getAbsolutePath());
    Assert.assertEquals(file.getAbsolutePath(),
        CommandLine.getFileCommand(args, "cmd", null).getAbsolutePath());
  }

  @Test
  public void testGetStringCommand() {

    String[] args = new String[] {"-cmd", "test"};

    Assert.assertEquals("test", CommandLine.getStringCommand(args, "cmd", ""));
    Assert.assertEquals("test", CommandLine.getStringCommand(args, "cmd", null));
    Assert.assertEquals("", CommandLine.getStringCommand(args, "nop", ""));
    Assert.assertNull(CommandLine.getStringCommand(args, "nop", null));
  }

  @Test
  public void testGetIntCommand() {

    String[] args = new String[] {"-cmd", "123"};

    Assert.assertEquals(Integer.valueOf(123), CommandLine.getIntCommand(args, "cmd", 0));
    Assert.assertEquals(Integer.valueOf(123), CommandLine.getIntCommand(args, "cmd", null));
    Assert.assertEquals(Integer.valueOf(0), CommandLine.getIntCommand(args, "nop", 0));
    Assert.assertNull(CommandLine.getIntCommand(args, "nop", null));
  }

  @Test
  public void testGetDoubleCommand() {

    String[] args = new String[] {"-cmd", "123.456"};

    Assert.assertEquals(Double.valueOf(123.456), CommandLine.getDoubleCommand(args, "cmd", 0.0));
    Assert.assertEquals(Double.valueOf(123.456), CommandLine.getDoubleCommand(args, "cmd", null));
    Assert.assertEquals(Double.valueOf(0.0), CommandLine.getDoubleCommand(args, "nop", 0.0));
    Assert.assertNull(CommandLine.getDoubleCommand(args, "nop", null));
  }

  @Test
  public void testGetBooleanCommand() {

    String[] args = new String[] {"-cmd1", "TRUE", "-cmd2", "false"};

    Assert.assertEquals(true, CommandLine.getBooleanCommand(args, "cmd1", false));
    Assert.assertEquals(true, CommandLine.getBooleanCommand(args, "cmd1", null));

    Assert.assertEquals(false, CommandLine.getBooleanCommand(args, "cmd2", false));
    Assert.assertEquals(false, CommandLine.getBooleanCommand(args, "cmd2", null));

    Assert.assertEquals(false, CommandLine.getBooleanCommand(args, "nop", false));
    Assert.assertNull(CommandLine.getBooleanCommand(args, "nop", null));
  }

  @Test(expected = RuntimeException.class)
  public void testGetAttributesCommandWithMissingValue() {

    String[] args = new String[] {"-cmd", "k1=v1&k2&k3=v3"};

    Map<String, String> attributes = CommandLine.getAttributesCommand(args, "cmd", "");
  }

  @Test(expected = RuntimeException.class)
  public void testGetAttributesCommandWithDuplicateKey() {

    String[] args = new String[] {"-cmd", "k1=v1&k2=v2&k1=v3"};

    Map<String, String> attributes = CommandLine.getAttributesCommand(args, "cmd", "");
  }

  @Test
  public void testGetAttributesCommand() {

    Map<String, String> attributes = new HashMap<>();
    attributes.put("k1", "v1");
    attributes.put("k2", "v2.1 v2.2");
    attributes.put("k3", "v3");

    String[] args = new String[] {"-cmd", "k1=v1&k2=v2.1 v2.2&k3=v3"};

    Assert.assertEquals(attributes, CommandLine.getAttributesCommand(args, "cmd", ""));
    Assert.assertEquals(attributes, CommandLine.getAttributesCommand(args, "cmd", null));
    Assert.assertEquals(new HashMap<>(), CommandLine.getAttributesCommand(args, "nop", ""));
    Assert.assertNull(CommandLine.getAttributesCommand(args, "nop", null));
  }

  @Test
  public void testGetArg() {

    String[] args = new String[] {"-cmd", "test"};

    Assert.assertEquals("test", CommandLine.getArg(args, "cmd"));
    Assert.assertNull(CommandLine.getArg(args, "nop"));
  }
}
