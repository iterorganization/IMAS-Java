package imasjava;

public abstract class SummaryString {


  protected boolean addString(StringBuilder sb, String s, int limit) {
    sb.append(s);
    if(limit > 0 && sb.length() > limit) {
      return true;
    }
    return false;
  }

  // Returns String that represents the object
  // The length of the String is truncated to the
  // size equal length
  public abstract String toSummaryString(int length);
  public abstract String toSummaryString();

}


