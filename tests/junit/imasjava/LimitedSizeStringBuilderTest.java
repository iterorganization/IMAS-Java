package imasjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class LimitedSizeStringBuilderTest {

  @Test
  @DisplayName("Unlimitted String with a, b, c values appended")
  void addABCToUnlimittedSizeStringBuilder() {
    LimitedSizeStringBuilder sb = new LimitedSizeStringBuilder(); 
    try {
      sb.append("a").append("b").append("c");
    } catch(StringLimitException ex) {
      fail("Should not throw exception");
    }

    assertEquals("abc", sb.toString(), "Error while appending");
  }

  @Test
  @DisplayName("Adding String to StringBuilder with size 2")
  void addABCToLimitedSizeStringBuilder() {
    LimitedSizeStringBuilder sb = new LimitedSizeStringBuilder(2);
    assertThrows(StringLimitException.class, () -> {
      sb.append("a").append("b").append("c");
    });
  }

  @Test
  @DisplayName("Adding String to StringBuilder that reached maximum - should throw exception twice")
  void addABCToMaximumLengthStringBuilder() {
    LimitedSizeStringBuilder sb = new LimitedSizeStringBuilder(2);
    
    assertThrows(StringLimitException.class, () -> {
      sb.append("abc");
    });
    
    assertThrows(StringLimitException.class, () -> {
      sb.append("abc");
    });
    
    assertEquals("abc", sb.toString(), "It looks like we have appended over the limit");
  }

  @Test
  @DisplayName("Adding three one letter String to StringBuilder with size 2 - should throw Exception")
  void addABCToLimitedSizeStringBuilderCatchException() {
    LimitedSizeStringBuilder sb = new LimitedSizeStringBuilder(2);

    assertThrows(StringLimitException.class, () -> {
      sb.append("a").append("b").append("c");
    });
    
    assertEquals("ab", sb.toString(), "Error while appending");
  }

  @Test
  @DisplayName("Unlimitted String with unlimitted number of strings appended - should throw exception")
  void addUnlimittedNumberOfStringsToUnlimittedSizeStringBuilder() {
    LimitedSizeStringBuilder sb = new LimitedSizeStringBuilder(); 
    assertThrows(java.lang.OutOfMemoryError.class, () -> {
      while(true) {
        try {
          sb.append("very long String - ");
        } catch(StringLimitException ex) {
          fail("Should not throw StringLimitException exception");
        }
      }
    });
  }

  

}
