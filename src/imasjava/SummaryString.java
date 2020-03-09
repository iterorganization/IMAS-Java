package imasjava;

public abstract class SummaryString {

  /**
   * Returns String that represents the object
   * 
   * @param length length of the string at which no more appends will be done
   */
  public abstract String toSummaryString(int length);
  
  /**
   * Returns String that represents the object
   * 
   * @param elements for array based object, maximum number of elements that will be
   * passed back inside String
   */
  public abstract String toSummaryStringElements(int elements);

}


