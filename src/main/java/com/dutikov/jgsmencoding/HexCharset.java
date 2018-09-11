package com.dutikov.jgsmencoding;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;


public class HexCharset extends Charset {

  private static final String CANONICAL_NAME = "X-HEXADECIMAL";
  private static final String[] ALIASES = new String[] { "HEX", "hex", "Hex" };

  public HexCharset() {
    super(CANONICAL_NAME, ALIASES);
  }

  @Override
  public boolean contains(Charset cs) {
    return (cs instanceof HexCharset);
  }

  @Override
  public CharsetDecoder newDecoder() {
    return new HexDecoder(this);
  }

  @Override
  public CharsetEncoder newEncoder() {
    CharsetEncoder encoder = new HexEncoder(this);
    return encoder;
  }
}
