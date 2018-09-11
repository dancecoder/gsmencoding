package ru.jtexting.gsmencoding;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;


public class Gsm7bitDefaultCharset extends Charset {

  private static final String CANONICAL_NAME = "X-GSM7-Default";
  private static final String[] ALIASES = new String[] { "GSM7", "GSM7bit", "GSM-7", "GSM7" };

  // GSM 03.38
  private static final char[] CHARSET = new char[] {
    '@', '£', '$', '¥', 'è', 'é', 'ù', 'ì', 'ò', 'Ç', '\n', 'Ø', 'ø', '\r', 'Å', 'å',
    'Δ', '_', 'Φ', 'Γ', 'Λ', 'Ω', 'Π', 'Ψ', 'Σ', 'Θ', 'Ξ', '\u001B', 'Æ', 'æ', 'ß', 'É',
    ' ', '!', '"', '#', '¤', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/',
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ':', ';', '<', '=', '>', '?',
    '¡', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
    'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'Ä', 'Ö', 'Ñ', 'Ü', '§',
    '¿', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
    'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'ä', 'ö', 'ñ', 'ü', 'à'
  };

  private static final char[] CHARSET_EXT = new char[] {
    '@', '£', '$', '¥', 'è', 'é', 'ù', 'ì', 'ò', 'Ç', '\f', 'Ø', 'ø', '\r', 'Å', 'å',
    'Δ', '_', 'Φ', 'Γ', '^', 'Ω', 'Π', 'Ψ', 'Σ', 'Θ', 'Ξ', ' ', 'Æ', 'æ', 'ß', 'É',
    ' ', '!', '"', '#', '¤', '%', '&', '\'', '{', '}', '*', '+', ',', '-', '.', '\\',
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ':', ';', '[', '~', ']', '?',
    '|', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
    'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'Ä', 'Ö', 'Ñ', 'Ü', '§',
    '¿', 'a', 'b', 'c', 'd', '€', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
    'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'ä', 'ö', 'ñ', 'ü', 'à'
  };

  public Gsm7bitDefaultCharset() {
    super(CANONICAL_NAME, ALIASES);
  }

  @Override
  public boolean contains(Charset cs) {
    return (cs instanceof Gsm7bitDefaultCharset);
  }

  @Override
  public CharsetDecoder newDecoder() {
    return new Gsm7bitDecoder(this, CHARSET, CHARSET_EXT);
  }

  @Override
  public CharsetEncoder newEncoder() {
    CharsetEncoder encoder = new Gsm7bitEncoder(this, CHARSET, CHARSET_EXT);
    encoder.onUnmappableCharacter(CodingErrorAction.REPLACE);
    return encoder;
  }

}
