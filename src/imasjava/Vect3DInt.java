package imasjava;

public class Vect3DInt extends SummaryString {
  int dim1;
  int dim2;
  int dim3;
  int array[];

  public Vect3DInt(int dim1, int dim2, int dim3) {
    this.dim1 = dim1;
    this.dim2 = dim2;
    this.dim3 = dim3;
    array = new int[dim1 * dim2 * dim3];
  }

  public Vect3DInt(int dim1, int dim2, int dim3, int array[]) {
    this(dim1, dim2, dim3);
    this.array = array;
  }

  public int getDim(int idx) {
    switch (idx) {
      case 0:
        return dim1;
      case 1:
        return dim2;
      default:
        return dim3;
    }
  }

  public Vect2DInt getElementAt(int i) {
    int retArr[] = new int[dim1 * dim2];
    System.arraycopy(array, i * dim1 * dim2, retArr, 0, dim1 * dim2);
    return new Vect2DInt(dim1, dim2, retArr);
  }

  public void setElementAt(int i, Vect2DInt element) {
    System.arraycopy(element.array, 0, array, i * dim1 * dim2, dim1 * dim2);
  }

  public int getElementAt(int i, int j, int k) {
    return array[i + j * dim1 + k * dim1 * dim2];
  }

  public void setElementAt(int i, int j, int k, int element) {
    array[i + j * dim1 + k * dim1 * dim2] = element;
  }

  public int[] getArray() {
    return array;
  }

  public void setArray(int[] array, int dim1, int dim2, int dim3) {
    // this.array = new int[dim1 * dim2 * dim3];
    this.array = array;
    this.dim1 = dim1;
    this.dim2 = dim2;
    this.dim3 = dim3;
  }

  /**
   * Returns internal array as row-major ordered array - copy of an array is returned.
   *
   * <p>If you want to update values of oryginal column-major ordered array you have to use one of
   * the methods: set, setElementAt, or setArray (requires 1D data with column-major order).
   *
   * @see set, setElementAt, setArray
   */
  public int[][][] get() {

    int[][][] array = new int[dim1][dim2][dim3];
    for (int i = 0; i < dim1; i++) {
      for (int p = 0; p < dim2; p++) {
        for (int q = 0; q < dim3; q++) {
          array[i][p][q] = this.array[i + p * dim1 + q * dim1 * dim2];
        }
      }
    }

    return array;
  }

  /**
   * Sets internal array in column-major ordered way.
   *
   * @param array row-major ordered array
   */
  public void set(int[][][] array) {

    for (int i = 0; i < dim1; i++) {
      for (int p = 0; p < dim2; p++) {
        for (int q = 0; q < dim3; q++) {
          this.array[i + p * dim1 + q * dim1 * dim2] = array[i][p][q];
        }
      }
    }
  }

  public String toSummaryString(int length) {
    if (length < 0) {
      return toString();
    }

    LimitedSizeStringBuilder sb = new LimitedSizeStringBuilder(length);

    try {

      sb.append("[");
      for (int i = 0; i < dim1; i++) {
        sb.append("[");
        for (int j = 0; j < dim2; j++) {
          sb.append("[");
          for (int k = 0; k < dim3; k++) {
            if (k < dim3 - 1) sb.append("" + array[i + j * dim1 + k * dim1 * dim2] + ",");
            else sb.append("" + array[i + j * dim1 + k * dim1 * dim2]);
          }
          sb.append("]");
        }
        sb.append("]");
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
    for (i = 0; i < dim1 && i < elements; i++) {
      retStr += "[";
      int j = 0;
      for (j = 0; j < dim2 && j < elements; j++) {
        retStr += "[";
        int k = 0;
        for (k = 0; k < dim3 && k < elements; k++) {
          if (k < dim3 - 1) retStr += "" + array[i + j * dim1 + k * dim1 * dim2] + ",";
          else retStr += "" + array[i + j * dim1 + k * dim1 * dim2];
        }
        if (k < dim3) {
          retStr += "...";
        }
        retStr += "]";
      }
      if (j < dim2) {
        retStr += "...";
      }
      retStr += "]";
    }
    if (i < dim1) {
      retStr += "...";
    }
    retStr += "]";
    return retStr;
  }

  public String toString() {
    String retStr = "[";
    for (int i = 0; i < dim1; i++) {
      retStr += "[";
      for (int j = 0; j < dim2; j++) {
        retStr += "[";
        for (int k = 0; k < dim3; k++) {
          if (k < dim3 - 1) retStr += "" + array[i + j * dim1 + k * dim1 * dim2] + ",";
          else retStr += "" + array[i + j * dim1 + k * dim1 * dim2];
        }
        retStr += "]";
      }
      retStr += "]";
    }
    retStr += "]";
    return retStr;
  }
}
