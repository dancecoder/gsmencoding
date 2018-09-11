package com.dutikov.jgsmencoding;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;


public class HexDecoder extends CharsetDecoder {

  public HexDecoder(Charset cs) {
    super(cs, 2.0F, 2.0F);
  }

  private static final char[] HEX_CHAR_TABLE = {
    '0','1','2','3','4','5','6','7',
    '8','9','A','B','C','D','E','F'
  };

  @Override
  protected CoderResult decodeLoop(ByteBuffer in, CharBuffer out) {
    int b;
    while(in.hasRemaining()) {
      b = in.get() & 0xFF;
      out.put(HEX_CHAR_TABLE[b >> 4 & 15]);
      out.put(HEX_CHAR_TABLE[b & 15]);
    }
    return CoderResult.UNDERFLOW;
  }

}
