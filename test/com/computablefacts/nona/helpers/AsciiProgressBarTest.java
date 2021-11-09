package com.computablefacts.nona.helpers;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@Deprecated
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
    Assert.assertEquals("\r100% ################################################## |",
        outContent_.toString());
  }

  @Test
  public void testIndeterminateTask() {

    AsciiProgressBar.IndeterminateProgressBar progressBar = AsciiProgressBar.createIndeterminate();

    for (int i = 0; i < 10000; i++) {

      progressBar.update();

      if (i == 0) {
        Assert.assertTrue(outContent_.toString()
            .endsWith("\r100% ################################################## 2/2 (slice 1)"));
      } else if (i == 2) {
        Assert.assertTrue(outContent_.toString()
            .endsWith("\r100% ################################################## 3/3 (slice 2)"));
      } else if (i == 5) {
        Assert.assertTrue(outContent_.toString()
            .endsWith("\r100% ################################################## 4/4 (slice 3)"));
      } else if (i == 10) {
        Assert.assertTrue(outContent_.toString()
            .endsWith("\r100% ################################################## 6/6 (slice 4)"));
      }
    }

    progressBar.complete();

    Assert.assertTrue(outContent_.toString().endsWith(
        "\r100% ################################################## 2821/3597 (slice 20)"));
  }
}
