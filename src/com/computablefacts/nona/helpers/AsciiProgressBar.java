package com.computablefacts.nona.helpers;

import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Var;

/**
 * Based on @{link https://masterex.github.io/archive/2011/10/23/java-cli-progress-bar.html}
 */
@Deprecated
@CheckReturnValue
final public class AsciiProgressBar {

  private AsciiProgressBar() {}

  public static ProgressBar create() {
    return new ProgressBar();
  }

  public static IndeterminateProgressBar createIndeterminate() {
    return new IndeterminateProgressBar();
  }

  /**
   * Ascii progress bar. On completion this component will reset itself so it can be reused. <br />
   * <br />
   * 100% ################################################## | <br />
   * <br />
   */
  public static class ProgressBar {

    private final char[] workChars_ = {'|', '/', '-', '\\'};
    private final String formatWheel_ = "\r%3d%% %s %c";
    private final String formatMsg_ = "\r%3d%% %s %s";
    private final StringBuilder progress_;
    private int percentPrev_ = 0;

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
      update(done, total, null);
    }

    /**
     * Called whenever the progress bar needs to be updated. That is whenever progress was made.
     *
     * @param done an int representing the work done so far.
     * @param total an int representing the total work.
     * @param msg an additional message to display at the end of the row.
     */
    public void update(int done, int total, String msg) {

      int percent = (done * 100) / total;

      if (done == 0 || done == total || percent > percentPrev_) {

        @Var
        int extraChars = (percent / 2) - progress_.length();

        while (extraChars-- > 0) {
          progress_.append('#');
        }

        if (msg == null) {
          System.out.printf(formatWheel_, percent, progress_, workChars_[done % workChars_.length]);
        } else {
          System.out.printf(formatMsg_, percent, progress_, msg);
        }

        percentPrev_ = percent;
      }

      if (done == total) {
        System.out.flush();
        progress_.setLength(0);
        percentPrev_ = 0;
      }
    }
  }

  /**
   * Ascii progress bar for tasks that take an indeterminate amount of time. On completion this
   * component will reset itself so it can be reused. <br />
   * <br />
   * 100% ################################################## | <br />
   * <br />
   */
  public static class IndeterminateProgressBar {

    private final ProgressBar bar_ = new ProgressBar();
    private int done_ = 1;
    private int total_ = 2;
    private int slice_ = 1;

    public IndeterminateProgressBar() {}

    public void complete() {

      bar_.update(total_, total_, done_ + "/" + total_ + " (slice " + slice_ + ")");

      done_ = 1;
      total_ = 2;
      slice_ = 1;
    }

    public void update() {

      bar_.update(++done_, total_, done_ + "/" + total_ + " (slice " + slice_ + ")");

      if (done_ >= total_) {
        total_ += (done_ / 2);
        done_ = 1;
        slice_++;
      }
    }
  }
}
