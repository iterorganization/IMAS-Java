package imasjava;

public abstract class SummaryString {

  // we have to have this "ugly" method here :(
  // Ideally, we would have our own StringBuilder with size limit, but we can't make
  // it an easy way - StringBuilder is final and we can't extend it.
  //
  protected void addString(StringBuilder sb, String s, int limit) throws StringLimitException {
    sb.append(s);
    if(limit > 0 && sb.length() > limit) {
      throw new StringLimitException();
    }
  }

  // Returns String that represents the object
  // The length of the String is truncated to the
  // size equal length
  public abstract String toSummaryString(int length);

}


