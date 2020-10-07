package com.computablefacts.nona.helpers;

import java.math.BigDecimal;
import java.text.ParseException;

import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Var;

/**
 * Encodes numbers as strings in such a way that the lexicographic order of the encoded strings is
 * the same as the natural order of the original numbers. The length of an encoded number is only
 * slightly larger than the length of its original number. Unlike other schemes, there is no limit
 * to the size of numbers which may be encoded.
 *
 * @author ted stockwell
 * @author David Fuelling
 */
@CheckReturnValue
final public class BigDecimalCodec {

  /**
   * @param input
   *
   * @return
   */
  public static String decode(String input) {
    try {
      if (input == null) {
        return null;
      }
      if (input.length() <= 0) {
        return "";
      }
      return new Decoder(input)._output;
    } catch (ParseException e) {
      throw new RuntimeException("Failed to decode number:" + input, e);
    }
  }

  /**
   * @param input
   *
   * @return
   */
  public static BigDecimal decodeAsBigDecimal(String input) {
    try {
      if (input == null) {
        return null;
      }
      if (input.length() <= 0) {
        throw new RuntimeException("Internal Error: Cannot decode an empty String");
      }
      return new BigDecimal(new Decoder(input)._output);
    } catch (ParseException e) {
      throw new RuntimeException("Failed to decode number:" + input, e);
    }
  }

  /**
   * @param input
   *
   * @return
   */
  public static String encode(String input) {
    try {
      if (input == null) {
        return null;
      }
      if (input.length() <= 0) {
        return "";
      }
      return new Encoder(input)._output;
    } catch (ParseException e) {
      throw new RuntimeException("Failed to parse number:" + input, e);
    }
  }

  /**
   * @param decimal
   *
   * @return
   */
  public static String encode(BigDecimal decimal) {
    if (decimal == null) {
      return null;
    }
    return BigDecimalCodec.encode(decimal.toPlainString());
  }

  /**
   *
   *
   */
  static public class Encoder {

    private String _input;
    private int _position = 0;
    private int _end;
    private String _output = "";
    private boolean _isNegative = false;

    private Encoder(String input) throws ParseException {

      this._input = input;
      this._end = this._input.length();

      char c = this._input.charAt(this._position);
      if (c == '-') {
        this._input.charAt(this._position++);
        this._isNegative = true;
      }

      this.readNumberBeforeDecimal();
      if (this.readDecimalPoint()) {
        this.readNumber(this._end - this._position);
      }
      this._output += this._isNegative ? '?' : '*';
    }

    /**
     * @return
     */
    private boolean readDecimalPoint() throws ParseException {
      if (this._end <= this._position) {
        return false;
      }
      char c = this._input.charAt(this._position++);
      if (c != '.') {
        this.throwParseException("Expected decimal point");
      }
      if (this._end <= this._position) {
        return false;
      }
      this._output += this._isNegative ? ':' : '.';
      return true;
    }

    /**
     *
     * @throws ParseException
     */
    private void readNumberBeforeDecimal() throws ParseException {

      char[] buffer = new char[this._input.length()];

      // readAll number until decimal point reached or end
      @Var
      int i = 0;
      while (this._end > this._position) {
        char c = this._input.charAt(this._position++);
        if (('0' <= c) && (c <= '9')) {
          buffer[i++] = (char) (this._isNegative ? '0' + ('9' - c) : c);
        } else if (c == '.') {
          this._position--;
          break;
        }
      }

      // now figure out needed prefixes
      @Var
      String prefix = "";
      @Var
      int l = i;
      @Var
      String unaryPrefix = this._isNegative ? "*" : "?";
      while (1 < l) {
        unaryPrefix += this._isNegative ? '*' : '?';
        @Var
        String s = Integer.toString(l);
        if (this._isNegative) {
          char[] cs = s.toCharArray();
          for (int j = 0; j < cs.length; j++) {
            cs[j] = (char) (('0' + '9') - cs[j]);
          }
          s = new String(cs);
        }
        prefix = s + prefix;
        l = s.length();
      }

      this._output += unaryPrefix; // output unary prefix count
      this._output += prefix; // output prefixes
      this._output += new String(buffer, 0, i); // now output actual
      // number
    }

    /**
     * @param length
     */
    private void readNumber(@Var int length) {
      if (this._isNegative) {
        while (0 < length--) {
          this._output += (char) ('0' + ('9' - this._input.charAt(this._position++)));
        }
      } else {
        this._output += this._input.substring(this._position, this._position + length);
        this._position += length;
      }
    }

    /**
     * @param message
     */
    private void throwParseException(String message) throws ParseException {
      throw new ParseException(message, this._position);
    }
  }

  /**
   *
   */
  static public class Decoder {

    private String _input;
    private int _position = 0;
    private int _end;
    private String _output = "";
    private boolean _isNegative = false;

    /**
     * @param input
     */
    private Decoder(String input) throws ParseException {

      this._input = input;
      this._end = this._input.length();
      @Var
      int lastChar = this._input.charAt(this._end - 1);
      while ((lastChar == '*') || (lastChar == '?') || (lastChar == ':') || (lastChar == '.')) {
        lastChar = this._input.charAt((--this._end) - 1);
      }

      char c = this._input.charAt(this._position);
      if (c == '*') {
        this._output += '-';
        this._isNegative = true;
      } else if (c != '?') {
        throw new ParseException("All encoded numbers must begin with either '?' or '*'",
            this._position);
      }

      this.readSequence();
      if (this.readDecimalPoint()) {
        this.readNumber(this._end - this._position);
      }
    }

    /**
     * @return
     */
    private boolean readDecimalPoint() throws ParseException {
      if (this._end <= this._position) {
        return false;
      }
      char c = this._input.charAt(this._position++);
      if (c != (this._isNegative ? ':' : '.')) {
        throw new ParseException("Expected decimal point", this._position);
      }
      if (this._end <= this._position) {
        return false;
      }
      this._output += '.';
      return true;
    }

    /**
     *
     * @throws ParseException
     */
    private void readSequence() throws ParseException {
      @Var
      int sequenceCount = 0;
      while (true) {
        int c = this._input.charAt(this._position++);
        if ((c == '*') || (c == '?')) {
          sequenceCount++;
        } else {
          this._position--;
          break;
        }
      }
      this.readNumberSequence(sequenceCount);
    }

    /**
     * @param sequenceCount
     */
    private void readNumberSequence(@Var int sequenceCount) {
      @Var
      int prefixLength = 1;
      while (1 < sequenceCount--) {
        prefixLength = this.readPrefix(prefixLength);
      }
      this.readNumber(prefixLength);
    }

    /**
     * @param length
     *
     * @return
     */
    private int readPrefix(@Var int length) {
      String s;
      if (this._isNegative) {
        char[] cs = new char[length];
        @Var
        int i = 0;
        while (0 < length--) {
          cs[i++] = (char) ('0' + ('9' - this._input.charAt(this._position++)));
        }
        s = new String(cs);
      } else {
        s = this._input.substring(this._position, this._position + length);
        this._position += length;
      }
      return Integer.parseInt(s);
    }

    /**
     * @param length
     */
    private void readNumber(@Var int length) {
      if (this._isNegative) {
        while (0 < length--) {
          this._output += (char) ('0' + ('9' - this._input.charAt(this._position++)));
        }
      } else {
        this._output += this._input.substring(this._position, this._position + length);
        this._position += length;
      }
    }
  }
}
