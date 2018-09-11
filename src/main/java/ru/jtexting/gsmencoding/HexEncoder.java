package ru.jtexting.gsmencoding;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;


public class HexEncoder extends CharsetEncoder {

  private static final byte[] REPLACEMENT = new byte[] { (byte)0x00 };

  private char a, b;
  private boolean cashed = false;

  public HexEncoder (Charset cs) {
    super(cs, 0.5F, 1F, REPLACEMENT);
  }

  private static byte unhexChar(char c) {
    if (c > '/' && c < ':') {
      return (byte)(c - '0');
    } else if (c > '`' && c < 'g') {
      return (byte)(c - 'a' + 10);
    } else  if (c > '@' && c < 'G') {
      return (byte)(c - 'A' + 10);
    }
    throw new IllegalArgumentException();
  }

  @Override
  protected CoderResult encodeLoop(CharBuffer in, ByteBuffer out) {
    while(in.hasRemaining()) {
      a = in.get();
      if (in.hasRemaining()) {
        b = in.get();
        cashed = true;
        CoderResult cr = implFlush(out);
        if (cr != CoderResult.UNDERFLOW) {
          if (cr.isMalformed()) {
            in.position(in.position() - cr.length());
          }
          return cr;
        }
      } else {
        in.position(in.position() - 1);
        implReset();
        return CoderResult.malformedForLength(1);
      }
    }
    return CoderResult.UNDERFLOW;
  }

  @Override
  protected CoderResult implFlush(ByteBuffer out) {
    if (cashed) {
      byte ba, bb;
      try {
        ba = unhexChar(a);
        bb = unhexChar(b);
      } catch(IllegalArgumentException e) {
        return CoderResult.malformedForLength(2); // Skip both characters as they represents single byte
      }
      out.put((byte)(ba << 4 | bb));
      implReset();
    }
    return CoderResult.UNDERFLOW;
  }

  @Override
  protected void implReset() {
    a = 0;
    b = 0;
    cashed = false;
  }

}
