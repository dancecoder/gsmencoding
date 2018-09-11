package com.dutikov.jgsmencoding;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import org.junit.Assert;
import org.junit.Test;

public class HexCharsetTest {

  HexCharset charset = new HexCharset();

  @Test
  public void decode() {
    byte[] bytes = new byte[] {
      (byte)0xFF, (byte)0x10, (byte)0x15, (byte)0xFA, (byte)0xb7
    };
    CharBuffer chb = charset.decode(ByteBuffer.wrap(bytes));
    String s = chb.toString();
    Assert.assertTrue("FF1015FAB7".equals(s));
  }

  @Test
  public void encode() {
    Assert.assertTrue(charset.canEncode());
    encodeInternal(
      "FA00B7EE180A",
      new byte[] { (byte)0xFA, (byte)0x00, (byte)0xB7, (byte)0xEE, (byte)0x18, (byte)0x0A }
    );
  }

  @Test
  public void encodeInvalidString() {
    encodeInternal(
      "FA00B7EE180",
      new byte[] { (byte)0xFA, (byte)0x00, (byte)0xB7, (byte)0xEE, (byte)0x18, (byte)0x00 }
    );
    encodeInternal(
      "FA00B7?E18",
      new byte[] { (byte)0xFA, (byte)0x00, (byte)0xB7, (byte)0x00, (byte)0x18 }
    );
    encodeInternal(
      "FA00B7?E181",
      new byte[] { (byte)0xFA, (byte)0x00, (byte)0xB7, (byte)0x00, (byte)0x18, (byte)0x00 }
    );
  }

  private void encodeInternal(String in, byte[] expecteds) {
    ByteBuffer bb = charset.encode(in);
    Assert.assertTrue(bb.limit() == expecteds.length);
    for (int i = 0; i < bb.limit(); i++) {
      Assert.assertTrue(bb.get(i) ==  expecteds[i]);
    }
  }

}
