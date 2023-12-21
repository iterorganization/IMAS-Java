import java.lang.*;

import imasjava.*;
import imasjava.wrapper.LowLevel;

class CommonValidateUtilities {

// This function invoke the validate() method and check is the ValidationException is raised and it's correspond to the expectation.
public static void check_validation(Object ids, String expected){
    try {
        // We try to get the 'validate' method from the given object.
        java.lang.reflect.Method method = ids.getClass().getMethod("validate");
        // If no method 'validate' is returned, we print an error and abort.
        if(method == null)  {
            System.err.println("Error: no validate method found.");
            return;
        }
        // We invoke the method
        method.invoke(ids);
        // If no exceptions is raised but expected, we raise an error.
        if(expected!="") System.err.println("Error: no exception occured");
        // If no exceptions is raised and nothing expected, the test is passed.
        if(expected=="") System.out.println("Test passed.");
    } 
    // We the invokation of the method raised an error we check the error message to the expected one.
    catch (java.lang.reflect.InvocationTargetException e) {
        if(expected!="") {
            if (!expected.equals(e.getTargetException().getMessage())) {
                System.err.println("Error:");
                System.err.println("Observed: "+ e.getTargetException().getMessage());
                System.err.println("Expected: "+ expected);
            }
            else {
                // The test is passed if the error message to the expected one. 
                System.out.println("Test passed.");
            }
        } else {
            System.err.println("Error: an unexpected exception occured");
            System.err.println(e.getTargetException());
        }
    }
    // For all error from the invokation of the validate() method, we print an error message.
    catch (Exception e) {
        System.err.println("Error: an unexpected exception occured");
        System.err.println(e.getMessage());
    }
}
}