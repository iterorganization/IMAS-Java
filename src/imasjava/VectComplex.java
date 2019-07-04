package imasjava;
import java.util.Arrays;


public abstract class VectComplex implements VectGeneric {

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

    public String toSummary()
    {
	String retStr = "[dim="+Arrays.toString(getDims())+", elt=";
	for (int i=0; i<10; i++)
	    retStr += array[i]+", ";
	return retStr+", ...]";
    }
}
