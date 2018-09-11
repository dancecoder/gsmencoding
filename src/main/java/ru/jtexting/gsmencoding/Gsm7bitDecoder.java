package ru.jtexting.gsmencoding;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;


public class Gsm7bitDecoder extends CharsetDecoder {

  private static final byte ESCAPE = 27;

  private final char[] table;
  private final char[] extension;

  public Gsm7bitDecoder(Charset cs, char[] tbl, char[] ext) {
    super(cs, 1 + 1F/7F, 1 + 1F/7F);
    this.table = tbl;
    this.extension = ext;
  }

  private int i = 0;
  private int b = 0;
  private int p = 0;
  private int d = 0;
  private boolean shift = false;

  @Override
  protected void implReset() {
    i = 0;
    b = 0;
    p = 0;
    d = 0;
    shift = false;
  }

  @Override
  protected CoderResult decodeLoop(ByteBuffer in, CharBuffer out) {
    while(in.hasRemaining()) {
      if (out.hasRemaining()) {
        b = in.get() & 0xFF;
        d = (b << i % 7 | (p >> 8 - i % 7) & ~(-1 << i%7)) & 127;
        handleByte(d, out);
        p = b;
        i++;
        if (i > 0 && (i) % 7 == 0) {
          d = (p >> 1) & 0xFF;
          p = 0;
          handleByte(d, out);
        }
      } else {
        return CoderResult.OVERFLOW;
      }
    }
    return CoderResult.UNDERFLOW;
  }

  private void handleByte(int d, CharBuffer out) {
    if (shift) {
      out.put(extension[d]);
      shift = false;
    } else {
      if (d == ESCAPE) {
        shift = true;
      } else {
        out.put(table[d]);
        shift = false;
      }
    }
  }

}
