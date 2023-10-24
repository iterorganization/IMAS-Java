package imasjava;

public class Vect1DString extends SummaryString {
  String array[];

  public Vect1DString(int dim) {
    array = new String[dim];
  }

  public Vect1DString(String array[]) {
    this.array = array;
  }

  public int getDim() {
    return array.length;
  }

  public int getDim(int i) {
    if (i==0) return array.length;
    else return 0;
  }

  public String getElementAt(int idx) {
    return array[idx];
  }

  public void setElementAt(int idx, String element) {
    array[idx] = element;
  }

  public String[] getArray() {
    return array;
  }

  public void setArray(String[] array, int dim1) {
    // this.array = new String[dim1];
    this.array = array;
  }

  public String toSummaryString(int length) {

    if (length < 0) {
      return toString();
    }

    LimitedSizeStringBuilder sb = new LimitedSizeStringBuilder(length);

    try {
      sb.append("[");
      for (int i = 0; i < array.length; i++) {
        if (i < array.length - 1) sb.append("" + array[i] + ",");
        else sb.append(String.valueOf(array[i]));
      }
      sb.append("]");
    } catch (StringLimitException ex) {
      // This might happen, eventually
      // but it's not an error

    }
    return sb.toString();
  }

  public String toSummaryStringElements(int elements) {
    String retStr = "[";
    int i = 0;
    for (i = 0; i < array.length && i < elements; i++) {
      if (i < array.length - 1) retStr += "" + array[i] + ",";
      else retStr += "" + array[i];
    }
    if (i < array.length) {
      retStr += "...";
    }
    retStr += "]";
    return retStr;
  }

  public String toString() {
    String retStr = "[";
    for (int i = 0; i < array.length; i++) {
      if (i < array.length - 1) retStr += "\"" + array[i] + "\",";
      else retStr += "\"" + array[i] + "\"";
    }
    retStr += "]";
    return retStr;
  }
}
