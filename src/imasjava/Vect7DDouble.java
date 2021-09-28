package imasjava;

public class Vect7DDouble extends SummaryString {
  int dim1;
  int dim2;
  int dim3;
  int dim4;
  int dim5;
  int dim6;
  int dim7;
  double array[];

  public Vect7DDouble(int dim1, int dim2, int dim3, int dim4, int dim5, int dim6, int dim7) {
    this.dim1 = dim1;
    this.dim2 = dim2;
    this.dim3 = dim3;
    this.dim4 = dim4;
    this.dim5 = dim5;
    this.dim6 = dim6;
    this.dim7 = dim7;
    array = new double[dim1 * dim2 * dim3 * dim4 * dim5 * dim6 * dim7];
  }

  public Vect7DDouble(
      int dim1, int dim2, int dim3, int dim4, int dim5, int dim6, int dim7, double array[]) {
    this(dim1, dim2, dim3, dim4, dim5, dim6, dim7);
    this.array = array;
  }

  public int getDim(int idx) {
    switch (idx) {
      case 0:
        return dim1;
      case 1:
        return dim2;
      case 2:
        return dim3;
      case 3:
        return dim4;
      case 4:
        return dim5;
      case 5:
        return dim6;
      case 6:
        return dim7;
      default:
        return dim7;
    }
  }

  public Vect6DDouble getElementAt(int i) {
    double retArr[] = new double[dim1 * dim2 * dim3 * dim4 * dim5 * dim6];
    System.arraycopy(
        array,
        i * dim1 * dim2 * dim3 * dim4 * dim5 * dim6,
        retArr,
        0,
        dim1 * dim2 * dim3 * dim4 * dim5 * dim6);
    return new Vect6DDouble(dim1, dim2, dim3, dim4, dim5, dim6, retArr);
  }

  public void setElementAt(int i, Vect6DDouble element) {
    System.arraycopy(
        element.array,
        0,
        array,
        i * dim1 * dim2 * dim3 * dim4 * dim5 * dim7,
        dim1 * dim2 * dim3 * dim4 * dim5 * dim6);
  }

  public double getElementAt(int i, int j, int k, int h, int l, int m, int n) {
    return array[
        i
            + j * dim1
            + k * dim1 * dim2
            + h * dim1 * dim2 * dim3
            + l * dim1 * dim2 * dim3 * dim4
            + m * dim1 * dim2 * dim3 * dim4 * dim5
            + n * dim1 * dim2 * dim3 * dim4 * dim5 * dim6];
  }

  public void setElementAt(int i, int j, int k, int h, int l, int m, int n, double element) {
    array[
            i
                + j * dim1
                + k * dim1 * dim2
                + h * dim1 * dim2 * dim3
                + l * dim1 * dim2 * dim3 * dim4
                + m * dim1 * dim2 * dim3 * dim4 * dim5
                + n * dim1 * dim2 * dim3 * dim4 * dim5 * dim6] =
        element;
  }

  public double[] getArray() {
    return array;
  }

  public void setArray(
      double[] array, int dim1, int dim2, int dim3, int dim4, int dim5, int dim6, int dim7) {
    // this.array = new double[dim1 * dim2 * dim3 * dim4 * dim5 * dim6 * dim7];
    this.array = array;
    this.dim1 = dim1;
    this.dim2 = dim2;
    this.dim3 = dim3;
    this.dim4 = dim4;
    this.dim5 = dim5;
    this.dim6 = dim6;
    this.dim7 = dim7;
  }

  /**
   * Returns internal array as row-major ordered array - copy of an array is returned.
   *
   * <p>If you want to update values of oryginal column-major ordered array you have to use one of
   * the methods: set, setElementAt, or setArray (requires 1D data with column-major order).
   *
   * @see set, setElementAt, setArray
   */
  public double[][][][][][][] get() {

    double[][][][][][][] array = new double[dim1][dim2][dim3][dim4][dim5][dim6][dim7];
    for (int i = 0; i < dim1; i++) {
      for (int p = 0; p < dim2; p++) {
        for (int q = 0; q < dim3; q++) {
          for (int r = 0; r < dim4; r++) {
            for (int s = 0; s < dim5; s++) {
              for (int t = 0; t < dim6; t++) {
                for (int u = 0; u < dim7; u++) {
                  array[i][p][q][r][s][t][u] =
                      this.array[
                          i
                              + p * dim1
                              + q * dim1 * dim2
                              + r * dim1 * dim2 * dim3
                              + s * dim1 * dim2 * dim3 * dim4
                              + t * dim1 * dim2 * dim3 * dim4 * dim5
                              + u * dim1 * dim2 * dim3 * dim4 * dim5 * dim6];
                }
              }
            }
          }
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
  public void set(double[][][][][][] array) {
    for (int i = 0; i < dim1; i++) {
      for (int p = 0; p < dim2; p++) {
        for (int q = 0; q < dim3; q++) {
          for (int r = 0; r < dim4; r++) {
            for (int s = 0; s < dim5; s++) {
              for (int t = 0; t < dim6; t++) {
                for (int u = 0; u < dim7; u++) {
                  this.array[
                          i
                              + p * dim1
                              + q * dim1 * dim2
                              + r * dim1 * dim2 * dim3
                              + s * dim1 * dim2 * dim3 * dim4
                              + t * dim1 * dim2 * dim3 * dim4 * dim5
                              + u * dim1 * dim2 * dim3 * dim4 * dim5 * dim6] =
                      array[i][p][q][r][s][t][u];
                }
              }
            }
          }
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
            sb.append("[");
            for (int h = 0; h < dim4; h++) {
              sb.append("[");
              for (int l = 0; l < dim5; l++) {
                sb.append("[");
                for (int m = 0; m < dim6; m++) {
                  for (int n = 0; n < dim7; m++) {
                    if (n < dim7 - 1)
                      sb.append(
                          ""
                              + array[
                                  i
                                      + j * dim1
                                      + k * dim1 * dim2
                                      + h * dim1 * dim2 * dim3
                                      + k * dim1 * dim2 * dim3 * dim4
                                      + m * dim1 * dim2 * dim3 * dim4 * dim5
                                      + n * dim1 * dim2 * dim3 * dim4 * dim5 * dim6]
                              + ",");
                    else
                      sb.append(
                          ""
                              + array[
                                  i
                                      + j * dim1
                                      + k * dim1 * dim2
                                      + h * dim1 * dim2 * dim3
                                      + k * dim1 * dim2 * dim3 * dim4
                                      + m * dim1 * dim2 * dim3 * dim4 * dim5
                                      + n * dim1 * dim2 * dim3 * dim4 * dim5 * dim6]);
                  }
                  sb.append("]");
                }
                sb.append("]");
              }
              sb.append("]");
            }
            sb.append("]");
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
          retStr += "[";
          int h = 0;
          for (h = 0; h < dim4 && h < elements; h++) {
            retStr += "[";
            int l = 0;
            for (l = 0; l < dim5 && l < elements; l++) {
              retStr += "[";
              int m = 0;
              for (m = 0; m < dim6 && m < elements; m++) {
                int n = 0;
                for (n = 0; n < dim7 && n < elements; m++) {
                  if (n < dim7 - 1)
                    retStr +=
                        ""
                            + array[
                                i
                                    + j * dim1
                                    + k * dim1 * dim2
                                    + h * dim1 * dim2 * dim3
                                    + k * dim1 * dim2 * dim3 * dim4
                                    + m * dim1 * dim2 * dim3 * dim4 * dim5
                                    + n * dim1 * dim2 * dim3 * dim4 * dim5 * dim6]
                            + ",";
                  else
                    retStr +=
                        ""
                            + array[
                                i
                                    + j * dim1
                                    + k * dim1 * dim2
                                    + h * dim1 * dim2 * dim3
                                    + k * dim1 * dim2 * dim3 * dim4
                                    + m * dim1 * dim2 * dim3 * dim4 * dim5
                                    + n * dim1 * dim2 * dim3 * dim4 * dim5 * dim6];
                }
                if (n < dim7) {
                  retStr += "...";
                }
                retStr += "]";
              }
              if (m < dim6) {
                retStr += "...";
              }
              retStr += "]";
            }
            if (l < dim5) {
              retStr += "...";
            }
            retStr += "]";
          }
          if (h < dim4) {
            retStr += "...";
          }
          retStr += "]";
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
          retStr += "[";
          for (int h = 0; h < dim4; h++) {
            retStr += "[";
            for (int l = 0; l < dim5; l++) {
              retStr += "[";
              for (int m = 0; m < dim6; m++) {
                for (int n = 0; n < dim7; m++) {
                  if (n < dim7 - 1)
                    retStr +=
                        ""
                            + array[
                                i
                                    + j * dim1
                                    + k * dim1 * dim2
                                    + h * dim1 * dim2 * dim3
                                    + k * dim1 * dim2 * dim3 * dim4
                                    + m * dim1 * dim2 * dim3 * dim4 * dim5
                                    + n * dim1 * dim2 * dim3 * dim4 * dim5 * dim6]
                            + ",";
                  else
                    retStr +=
                        ""
                            + array[
                                i
                                    + j * dim1
                                    + k * dim1 * dim2
                                    + h * dim1 * dim2 * dim3
                                    + k * dim1 * dim2 * dim3 * dim4
                                    + m * dim1 * dim2 * dim3 * dim4 * dim5
                                    + n * dim1 * dim2 * dim3 * dim4 * dim5 * dim6];
                }
                retStr += "]";
              }
              retStr += "]";
            }
            retStr += "]";
          }
          retStr += "]";
        }
        retStr += "]";
      }
      retStr += "]";
    }
    retStr += "]";
    return retStr;
  }
}
