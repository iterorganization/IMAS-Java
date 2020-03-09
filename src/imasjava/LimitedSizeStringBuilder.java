package imasjava;

/**
 * This class helps to create size limitted Strings.
 *
 * Note that limit is not strict. It determines when it is no longer possible
 * to append to the String.
 *
 * Example: if we specify String limit to 2 and we add String "hello" the resulting
 * String will be: "hello" and exception will be thrown.
 *
 * The reason I choose this approach is to make sure we store whole values coming from
 * the data vectors. If we have vector of values = [1.123456, 2.234567, 3.345678 ] and
 * I append these values as Strings I want to avoid situation where values are truncated.
 *
 * I want to have (in resulting String): "1.123456" instead of "1." (in case of max size equal 2)
 * 
 * If I declare variable of LimitedSizeStringBuilder(0), it means that first String appendend to this
 * StringBuilder will trigger exception. This way you can limit String to just one element regardles
 * of it's size.
 *
 */

public class LimitedSizeStringBuilder {

  int           limit = -1;
  StringBuilder sb    = null;


  /**
   * Creates LimitedSizeStringBuilder without size limit 
   */
  public LimitedSizeStringBuilder() {
    this.sb    = new StringBuilder();
  }

  /**
   * Creates LimitedSizeStringBuilder with size limit 
   *
   * @param size    maximum size of the LimitedSizeStringBuilder. If value is negative, there is no limit.
   * 
   */
  public LimitedSizeStringBuilder(int size) {
    this.limit = size; 
    this.sb    = new StringBuilder();
  }

  /**
   * Appends String s to internal StringBuilder.
   *
   * @param s  String to be appendend. If the size of StringBuilder is greater then limit (before or after adding the string) this method throws exception.
   * @throws StringLimitException If StringBuilder length is bigger than maximum length
   *
   */
  public LimitedSizeStringBuilder append(String s) throws StringLimitException {

    if(limit >= 0) {
      // TODO: can we make it somehow smarter?
      if(sb.length() >= limit) {
        throw new StringLimitException();
      }
      sb.append(s);
      if(sb.length() >= limit) {
        throw new StringLimitException();
      }
    } else {
      sb.append(s);
    }
    return this;
  }

  public String toString() {

    return sb.toString();

  } 

}
