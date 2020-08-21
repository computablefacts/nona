/*
 * Copyright (c) 2011-2020 MNCC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * @author http://www.mncc.fr
 */
package com.computablefacts.nona.helpers;

import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Var;

/**
 * Based on @{link https://masterex.github.io/archive/2011/10/23/java-cli-progress-bar.html}
 */
@CheckReturnValue
final public class AsciiProgressBar {

  private AsciiProgressBar() {}

  public static ProgressBar create() {
    return new ProgressBar();
  }

  /**
   * Ascii progress bar. On completion this component will reset itself so it can be reused <br />
   * <br />
   * 100% ################################################## | <br />
   * <br />
   */
  public static class ProgressBar {

    private final StringBuilder progress_;

    public ProgressBar() {
      progress_ = new StringBuilder(60);
    }

    /**
     * Called whenever the progress bar needs to be updated. That is whenever progress was made.
     *
     * @param done an int representing the work done so far.
     * @param total an int representing the total work.
     */
    public void update(int done, int total) {

      char[] workchars = {'|', '/', '-', '\\'};
      String format = "\r%3d%% %s %c";

      int percent = (done * 100) / total;
      @Var
      int extrachars = (percent / 2) - progress_.length();

      while (extrachars-- > 0) {
        progress_.append('#');
      }

      System.out.printf(format, percent, progress_, workchars[done % workchars.length]);

      if (done == total) {
        System.out.flush();
        progress_.setLength(0);
      }
    }
  }
}
