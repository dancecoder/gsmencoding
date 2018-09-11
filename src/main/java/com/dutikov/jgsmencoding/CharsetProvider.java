package com.dutikov.jgsmencoding;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;

public class CharsetProvider extends java.nio.charset.spi.CharsetProvider {

  private static final ArrayList<Charset> CHARSETS = new ArrayList<>(2);

  static {
    CHARSETS.add(new Gsm7bitDefaultCharset());
    CHARSETS.add(new HexCharset());
  }

  @Override
  public Iterator<Charset> charsets() {
    return CHARSETS.iterator();
  }

  @Override
  public Charset charsetForName(String charsetName) {
    for (Charset cs : CHARSETS) {
      for (String alias : cs.aliases()) {
        if (alias.equals(charsetName)) {
          return cs;
        }
      }
    }
    return null;
  }

}
