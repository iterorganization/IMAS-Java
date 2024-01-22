
package imasjava;

public class CoordinateValidation {


    static public String coordinate_not_filled_message(String path, String[] coordNames, int[] coordvalues, int[] Shape, int dim, String[] coordList){
        String output;
        output = "Element '";
        String newPath = path;
        for (int i=0;i<coordNames.length;i++) {
            newPath = newPath.replace(coordNames[i],Integer.toString(coordvalues[i]));
        }
        output = output + newPath;
        output = output + "' must have its coordinate in dimension " + Integer.toString(dim);
        output = output + " (any of ";
        for (String str: coordList) {
            newPath = str;
            for (int i=0;i<coordNames.length;i++) {
                newPath = newPath.replace(coordNames[i],Integer.toString(coordvalues[i]));
            }
            output = output + newPath + " ";
        }
        output = output + ") filled.";
        return output;

    }

    static public String coordinate_incorrect_size_message(String path, String[] coordNames, int[] coordvalues, int[] Shape, int dim, int size, String coord){
        String output;
        output = "Element '";
        String newPath = path;
        for (int i=0;i<coordNames.length;i++) {
            newPath = newPath.replace(coordNames[i],Integer.toString(coordvalues[i]));
        }
        output = output + newPath;
        output = output + "' has incorrect shape ";
        output = output + "(" + Integer.toString(Shape[0]);
        if (Shape.length>1) {
            for (int i=1;i<Shape.length;i++) {
                output = output + ", " + Integer.toString(Shape[i]);
            }
        }
        output = output + "): its coordinate in dimension " + Integer.toString(dim);
        output = output + " (";
        newPath = coord;
        for (int i=0;i<coordNames.length;i++) {
            newPath = newPath.replace(coordNames[i],Integer.toString(coordvalues[i]));
        }
        output = output + newPath;
        output = output + ") has size " + Integer.toString(size) +".";
        return output;
    }

    static public String coordinate_incorrect_fixedsize_message(String path, String[] coordNames, int[] coordvalues, int[] Shape, int dim, int fixedSize){
        String output;
        output = "Element '";
        String newPath = path;
        for (int i=0;i<coordNames.length;i++) {
            newPath = newPath.replace(coordNames[i],Integer.toString(coordvalues[i]));
        }
        output = output + newPath;
        output = output + "' has incorrect shape ";
        output = output + "(" + Integer.toString(Shape[0]);
        if (Shape.length>1) {
            for (int i=1;i<Shape.length;i++) {
                output = output + ", " + Integer.toString(Shape[i]);
            }
        }
        output = output + "): dimension " + Integer.toString(dim);
        output = output + " must have size " +  Integer.toString(fixedSize);
        return output;
    }
}