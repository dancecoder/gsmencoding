package ru.jtexting.gsmencoding;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import org.junit.Assert;
import org.junit.Test;

public class Gsm7bitDefaultCharsetTest {

  Gsm7bitDefaultCharset charset = new Gsm7bitDefaultCharset();

  private class Codepair {
    byte[] octets;
    String string;
    Codepair(String s, byte[] o) {
      this.string = s;
      this.octets = o;
    }
  }

  Codepair[] CODEPAIRS = new Codepair[] {
    new Codepair("1", new byte[] {
      (byte)0b0110001
    }),
    new Codepair("12", new byte[] {
      (byte)0b00110001, (byte)0b00011001
    }),
    new Codepair("123", new byte[] {
      (byte)0b00110001, (byte)0b11011001, (byte)0b00001100
    }),
    new Codepair("1234", new byte[] {
      (byte)0b00110001, (byte)0b11011001, (byte)0b10001100, (byte)0b00000110
    }),
    new Codepair("12345", new byte[] {
      (byte)0b00110001, (byte)0b11011001, (byte)0b10001100, (byte)0b01010110,
      (byte)0b00000011,
    }),
    new Codepair("123456", new byte[] {
      (byte)0b00110001, (byte)0b11011001, (byte)0b10001100, (byte)0b01010110,
      (byte)0b10110011, (byte)0b00000001,
    }),
    // NOTE: it is impossible to distinquish last '@' staracter of 8n character
    //       sequence and spare bits of 8n-1 character sequence
    //       see 6.1.2.3.1 of 3GPP TS 23.038 V15.0.0 (2018-06)
    new Codepair("1234567@", new byte[] {
      (byte)0b00110001, (byte)0b11011001, (byte)0b10001100, (byte)0b01010110,
      (byte)0b10110011, (byte)0b11011101, (byte)0b00000000
    }),
    new Codepair("1234567\r", new byte[] {
      (byte)0b00110001, (byte)0b11011001, (byte)0b10001100, (byte)0b01010110,
      (byte)0b10110011, (byte)0b11011101, (byte)0b00011010,
    }),
    new Codepair("12345678", new byte[] {
      (byte)0b00110001, (byte)0b11011001, (byte)0b10001100, (byte)0b01010110,
      (byte)0b10110011, (byte)0b11011101, (byte)0b01110000,
    }),
    new Codepair("123456789", new byte[] {
      (byte)0b00110001, (byte)0b11011001, (byte)0b10001100, (byte)0b01010110,
      (byte)0b10110011, (byte)0b11011101, (byte)0b01110000,
      (byte)0b00111001,
    }),
    new Codepair("123456789:", new byte[] {
      (byte)0b00110001, (byte)0b11011001, (byte)0b10001100, (byte)0b01010110,
      (byte)0b10110011, (byte)0b11011101, (byte)0b01110000,
      (byte)0b00111001, (byte)0b00011101
    }),
    new Codepair("123456789:;", new byte[] {
      (byte)0b00110001, (byte)0b11011001, (byte)0b10001100, (byte)0b01010110,
      (byte)0b10110011, (byte)0b11011101, (byte)0b01110000,
      (byte)0b00111001, (byte)0b11011101, (byte)0b00001110
    }),
    new Codepair("123456789:;<", new byte[] {
      (byte)0b00110001, (byte)0b11011001, (byte)0b10001100, (byte)0b01010110,
      (byte)0b10110011, (byte)0b11011101, (byte)0b01110000,
      (byte)0b00111001, (byte)0b11011101, (byte)0b10001110, (byte)0b00000111
    }),
    new Codepair("123456789:;<=", new byte[] {
      (byte)0b00110001, (byte)0b11011001, (byte)0b10001100, (byte)0b01010110,
      (byte)0b10110011, (byte)0b11011101, (byte)0b01110000,
      (byte)0b00111001, (byte)0b11011101, (byte)0b10001110, (byte)0b11010111,
      (byte)0b00000011,
    }),
    new Codepair("123456789:;<=>", new byte[] {
      (byte)0b00110001, (byte)0b11011001, (byte)0b10001100, (byte)0b01010110,
      (byte)0b10110011, (byte)0b11011101, (byte)0b01110000,
      (byte)0b00111001, (byte)0b11011101, (byte)0b10001110, (byte)0b11010111,
      (byte)0b11110011, (byte)0b00000001
    }),
    // see comment above
    new Codepair("123456789:;<=>?@", new byte[] {
      (byte)0b00110001, (byte)0b11011001, (byte)0b10001100, (byte)0b01010110,
      (byte)0b10110011, (byte)0b11011101, (byte)0b01110000,
      (byte)0b00111001, (byte)0b11011101, (byte)0b10001110, (byte)0b11010111,
      (byte)0b11110011, (byte)0b11111101, (byte)0b00000000
    }),
    // '~' is in shift table
    new Codepair("123456789:;<=>?~", new byte[] {
      (byte)0b00110001, (byte)0b11011001, (byte)0b10001100, (byte)0b01010110,
      (byte)0b10110011, (byte)0b11011101, (byte)0b01110000,
      (byte)0b00111001, (byte)0b11011101, (byte)0b10001110, (byte)0b11010111,
      (byte)0b11110011, (byte)0b11111101, (byte)0b00110110,
      (byte)0b00111101
    }),
    new Codepair("123456789:;<=>?@123456789:;<=>?@", new byte[] {
      (byte)0b00110001, (byte)0b11011001, (byte)0b10001100, (byte)0b01010110,
      (byte)0b10110011, (byte)0b11011101, (byte)0b01110000,
      (byte)0b00111001, (byte)0b11011101, (byte)0b10001110, (byte)0b11010111,
      (byte)0b11110011, (byte)0b11111101, (byte)0b00000000,
      (byte)0b00110001, (byte)0b11011001, (byte)0b10001100, (byte)0b01010110,
      (byte)0b10110011, (byte)0b11011101, (byte)0b01110000,
      (byte)0b00111001, (byte)0b11011101, (byte)0b10001110, (byte)0b11010111,
      (byte)0b11110011, (byte)0b11111101, (byte)0b00000000
    }),
  };

  @Test
  public void decode() {
    for (Codepair cp : CODEPAIRS) {
      CharBuffer sb = charset.decode(ByteBuffer.wrap(cp.octets));
      if (!sb.toString().equals(cp.string)) {
        Assert.fail("Decode for: " + cp.string);
      }
    }
  }

  @Test
  public void encode() {
    for (Codepair cp : CODEPAIRS) {
      try {
        ByteBuffer bb = charset.encode(cp.string);
        if (bb.limit() == cp.octets.length) {
          for (int i = 0; i < bb.limit(); i++) {
            if (bb.get(i) != cp.octets[i]) {
              Assert.fail("Decode for: " + cp.string);
            }
          }
        } else {
          Assert.fail("Decode for: " + cp.string);
        }
      } catch(Exception e) {
        Assert.fail("Decode for: " + cp.string + " exception: " + e.toString());
      }
    }
  }

}
