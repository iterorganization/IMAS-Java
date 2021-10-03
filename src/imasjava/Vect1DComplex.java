package imasjava;

public class Vect1DComplex extends VectComplex {
  public Vect1DComplex(int dim) {
    array = new Complex[dim];
  }

  public Vect1DComplex(Complex array[]) {
    this.array = array;
  }

  public int getDim() {
    return array.length;
  }

  public int[] getDims() {
    int[] d = {array.length};
    return d;
  }

  public Complex getElementAt(int idx) {
    return array[idx];
  }

  public void setElementAt(int idx, Complex element) {
    array[idx] = element;
  }

  // negative number means that we don't limit the size
  // of the string
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
    if (getSize() > maxPrintSize) {
      return toSummary();
    } else {
      Complex complexNumber = null;
      String retStr = "[";
      complexNumber = this.array[0];
      retStr += complexNumber.toString();
      for (int i = 1; i < array.length; i++) {
        complexNumber = this.array[i];
        retStr += "," + complexNumber.toString();
      }
      retStr += "]";
      return retStr;
    }
  }
}
