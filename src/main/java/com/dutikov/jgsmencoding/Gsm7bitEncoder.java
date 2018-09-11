package com.dutikov.jgsmencoding;

import java.io.ByteArrayOutputStream;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;

public class Gsm7bitEncoder extends CharsetEncoder {

  private static final byte ESC = 27;
  private static final byte[] REPLACEMENT = new byte[] { 63 }; // ?

  private final char[] table;
  private final char[] extension;


  public Gsm7bitEncoder (Charset cs, char[] tbl, char[] ext) {
    super(cs, 1, 1, REPLACEMENT);
    this.table = tbl;
    this.extension = ext;
  }

  private ByteArrayOutputStream buffer = null;

  @Override
  protected void implReset() {
    buffer = new ByteArrayOutputStream();
  }

  @Override
  protected CoderResult encodeLoop(CharBuffer in, ByteBuffer out) {
    char c = 0;
    byte b = 0;
    while(in.hasRemaining()) {
      c = in.get();
      b = findByte(c, table);
      if (b == -1) {
        b = findByte(c, extension);
        if (b == -1) {
          return CoderResult.unmappableForLength(1);
        } else {
          buffer.write(ESC);
          buffer.write(b);
        }
      } else {
        buffer.write(b);
      }
    }
    return CoderResult.UNDERFLOW;
  }

  @Override
  protected CoderResult implFlush(ByteBuffer out) {
    byte[] octets = this.buffer.toByteArray();
    byte[] septets = new byte[octets.length - octets.length / 8];
    if(octets.length == 1) {
      septets[0] = octets[0];
    } else {
      int oi, si;
      for (oi = 0, si = 0; oi < octets.length - 1; oi++, si = oi-oi/8) {
        septets[si] = (byte)(octets[oi] >> oi % 8 | octets[oi+1] << 7 - oi % 8);
      }
      if (octets.length % 8 > 0) {
        septets[oi-oi/7] = (byte)(octets[oi] >> oi % 8);
      }
    }
    try {
      out.put(septets);
    } catch (BufferOverflowException e) {
      return CoderResult.OVERFLOW;
    }
    return CoderResult.UNDERFLOW;
  }

  private byte findByte(char c, char[] cset) {
    for(int i = 0; i < cset.length; i++) {
      if (cset[i] == c) {
        return (byte)i;
      }
    }
    return -1;
  }

}
