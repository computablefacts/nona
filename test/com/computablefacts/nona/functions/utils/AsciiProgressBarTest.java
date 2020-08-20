package com.computablefacts.nona.functions.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@net.jcip.annotations.NotThreadSafe
public class AsciiProgressBarTest {

  private final ByteArrayOutputStream outContent_ = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent_ = new ByteArrayOutputStream();
  private final PrintStream originalOut_ = System.out;
  private final PrintStream originalErr_ = System.err;

  @Before
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent_));
    System.setErr(new PrintStream(errContent_));
  }

  @After
  public void restoreStreams() {
    System.setOut(originalOut_);
    System.setErr(originalErr_);
  }

  @Test
  public void testUpdate0() {
    AsciiProgressBar.ProgressBar progressBar = AsciiProgressBar.create();
    progressBar.update(0, 100);
    Assert.assertEquals("\r  0%  |", outContent_.toString());
  }

  @Test
  public void testUpdate25() {
    AsciiProgressBar.ProgressBar progressBar = AsciiProgressBar.create();
    progressBar.update(25, 100);
    Assert.assertEquals("\r 25% ############ /", outContent_.toString());
  }

  @Test
  public void testUpdate50() {
    AsciiProgressBar.ProgressBar progressBar = AsciiProgressBar.create();
    progressBar.update(50, 100);
    Assert.assertEquals("\r 50% ######################### -", outContent_.toString());
  }

  @Test
  public void testUpdate75() {
    AsciiProgressBar.ProgressBar progressBar = AsciiProgressBar.create();
    progressBar.update(75, 100);
    Assert.assertEquals("\r 75% ##################################### \\", outContent_.toString());
  }

  @Test
  public void testUpdate100() {
    AsciiProgressBar.ProgressBar progressBar = AsciiProgressBar.create();
    progressBar.update(100, 100);
    Assert.assertEquals("\r100% ################################################## |\r\n",
        outContent_.toString());
  }
}
