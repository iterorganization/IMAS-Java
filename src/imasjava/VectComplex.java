package imasjava;
import java.util.Arrays;


public abstract class VectComplex extends SummaryString implements VectGeneric {

    Complex array[];

    public int getSize() {
	    return array.length;
    }
    public Complex[] getArray() {
	    return array;
    }
    public String arrayToString() {
	    return Arrays.toString(array);
    }

    public abstract int[] getDims();
    public abstract String toString();

    public String toSummary() {
      return toSummaryString(100);
    }
}
