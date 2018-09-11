package ru.jtexting.gsmencoding;

import org.junit.Assert;
import org.junit.Test;

public class CharsetProviderTest {

  CharsetProvider csp = new CharsetProvider();

  @Test
  public void CheckParentClass() {
    Assert.assertTrue(csp instanceof java.nio.charset.spi.CharsetProvider);
  }

  @Test
  public void isGsmEncodingAvailable() {
    Assert.assertNotNull(csp.charsetForName("GSM7"));
    Assert.assertNotNull(csp.charsetForName("GSM7bit"));
    Assert.assertNotNull(csp.charsetForName("GSM-7"));
    Assert.assertNotNull(csp.charsetForName("GSM7"));
  }

  @Test
  public void isHexEncodingAvailable() {
    Assert.assertNotNull(csp.charsetForName("HEX"));
    Assert.assertNotNull(csp.charsetForName("Hex"));
    Assert.assertNotNull(csp.charsetForName("hex"));
  }

}
