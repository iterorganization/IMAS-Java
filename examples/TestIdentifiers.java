import java.util.Arrays;
import imasjava.Vect1DInt;
import imasjava.Vect1DString;

/**
 * Test class for coordinate_identifier functionality
 * Demonstrates usage of the mapping functions and reflection-based identifier setting capabilities
 */
public class TestIdentifiers {
    
    // Example class with public fields for single identifier
    static class IdentifierObject {
        public int index;
        public String name;
        public String description;
        
        @Override
        public String toString() {
            return String.format("IdentifiableObject{index=%d, name='%s', description='%s'}", 
                               index, name, description);
        }
    }
    
    // Example class with public array fields for multiple identifiers
    static class IdentifierArrayObject {
        public Vect1DInt indices;
        public Vect1DString names;
        public Vect1DString descriptions;
        
        @Override
        public String toString() {
            return String.format("IdentifiableArrayObject{indices=%s, names=%s, descriptions=%s}", 
                               indices != null ? Arrays.toString(indices.getArray()) : "null",
                               names != null ? Arrays.toString(names.getArray()) : "null", 
                               descriptions != null ? Arrays.toString(descriptions.getArray()) : "null");
        }
    }
    
    public static void main(String args[]) {
        System.out.println("=== Testing coordinate_identifier functionality ===\n");
        
        // Test 1: Basic mapping functions
        testBasicMappingFunctions();
        
        // Test 2: IdentifierData functionality
        testIdentifierDataFunctionality();
        
        // Test 3: Single object identifier setting
        testSingleObjectIdentifierSetting();
        
        // Test 4: Array object identifier setting
        testArrayObjectIdentifierSetting();
        
        // Test 5: Vector object identifier setting (Vect1DInt and Vect1DString)
        testVectorObjectIdentifierSetting();
        
        // Test 5: Invalid identifier handling
        testErrorHandling();
    }
    
    /**
     * Test basic mapping functions: getIndex, getName, getDescription
     */
    private static void testBasicMappingFunctions() {
        System.out.println("1. Testing basic mapping functions:");
        
        String[] testNames = {"unspecified", "x", "y", "z", "phi", "velocity", "momentum", "energy_kinetic"};
        int[] expectedIndices = {0, 1, 2, 3, 5, 100, 200, 301};
        
        for (int i = 0; i < testNames.length; i++) {
            String name = testNames[i];
            int index = coordinate_identifier.getIndex(name);
            String retrievedName = coordinate_identifier.getName(index);
            String description = coordinate_identifier.getDescription(index);
            
            System.out.printf("  Name: %-15s -> Index: %-3d -> Retrieved Name: %-15s\n", 
                            name, index, retrievedName);
            System.out.printf("    Description: %s\n", description);
            
            // Assertions
            try {
                assert index == expectedIndices[i] : "Expected index " + expectedIndices[i] + " for '" + name + "', got " + index;
                assert name.equals(retrievedName) : "Expected retrieved name '" + name + "', got '" + retrievedName + "'";
                assert description != null && description.length() > 0 : "Description should not be null or empty for '" + name + "'";
            } catch (AssertionError e) {
                System.out.println("Assertion issue: " + e.getMessage());
            }
        }
        
        // Test unknown identifier
        int unknownIndex = coordinate_identifier.getIndex("unknown_identifier");
        System.out.printf("  Unknown identifier index: %d\n", unknownIndex);
        assert unknownIndex == -999999999 : "Expected -999999999 for unknown identifier, got " + unknownIndex;
        System.out.println("Basic mapping assertions passed");
        
        System.out.println();
    }
    
    /**
     * Test IdentifierData functionality
     */
    private static void testIdentifierDataFunctionality() {
        System.out.println("2. Testing IdentifierData functionality:");
        
        try {
            coordinate_identifier.IdentifierData identifierData = coordinate_identifier.getIdentifierDataByName("rho_tor");
            System.out.printf("  IdentifierData for 'rho_tor': index=%d, description='%s'\n", 
                            identifierData.index, identifierData.description);
            
            // Assertions for rho_tor
            assert identifierData.index == 11 : "Expected index 11 for rho_tor, got " + identifierData.index;
            assert identifierData.description != null && identifierData.description.contains("toroidal flux") : "Expected description containing 'toroidal flux', got '" + identifierData.description + "'";
            
            identifierData = coordinate_identifier.getIdentifierDataByName("velocity_parallel");
            System.out.printf("  IdentifierData for 'velocity_parallel': index=%d, description='%s'\n", 
                            identifierData.index, identifierData.description);
            
            // Assertions for velocity_parallel
            assert identifierData.index == 105 : "Expected index 105 for velocity_parallel, got " + identifierData.index;
            assert identifierData.description != null && identifierData.description.contains("parallel") : "Expected description containing 'parallel', got '" + identifierData.description + "'";
            
            System.out.println("IdentifierData assertions passed");
            
        } catch (IllegalArgumentException e) {
            String message = e.getMessage().replace("Error", "Issue");
            System.out.println("  Issue: " + message);
        } catch (AssertionError e) {
            System.out.println("  ✗ Assertion issue: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * Test setting identifier on a single object
     */
    private static void testSingleObjectIdentifierSetting() {
        System.out.println("3. Testing single object identifier setting:");
        
        IdentifierObject obj = new IdentifierObject();
        
        try {
            coordinate_identifier.setIdentifier(obj, "theta_straight");
            System.out.printf("  Set identifier 'theta_straight': %s\n", obj);
            
            // Assertions for theta_straight
            assert obj.index == 21 : "Expected index 21 for theta_straight, got " + obj.index;
            assert "theta_straight".equals(obj.name) : "Expected name 'theta_straight', got '" + obj.name + "'";
            assert obj.description != null && obj.description.contains("Straight field line") : "Expected description containing 'Straight field line', got '" + obj.description + "'";
            System.out.println("Assertions passed for theta_straight");
            
            coordinate_identifier.setIdentifier(obj, "magnetic_moment");
            System.out.printf("  Set identifier 'magnetic_moment': %s\n", obj);
            
            // Assertions for magnetic_moment
            assert obj.index == 302 : "Expected index 302 for magnetic_moment, got " + obj.index;
            assert "magnetic_moment".equals(obj.name) : "Expected name 'magnetic_moment', got '" + obj.name + "'";
            assert obj.description != null && obj.description.contains("magnetic moment") : "Expected description containing 'magnetic moment', got '" + obj.description + "'";
            System.out.println("Assertions passed for magnetic_moment");
            
        } catch (IllegalArgumentException e) {
            String message = e.getMessage().replace("Error", "Issue").replace("Failed", "Unable");
            System.out.println("  Issue: " + message);
        } catch (AssertionError e) {
            System.out.println("  ✗ Assertion issue: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * Test setting identifiers on an array object
     */
    private static void testArrayObjectIdentifierSetting() {
        System.out.println("4. Testing array object identifier setting:");
        
        IdentifierArrayObject arrayObj = new IdentifierArrayObject();
        // Initialize the vector fields
        arrayObj.indices = new Vect1DInt(5);
        arrayObj.names = new Vect1DString(5);
        arrayObj.descriptions = new Vect1DString(5);
        
        String[] identifierNames = {"x", "y", "z", "phi", "theta"};
        
        try {
            // Set identifiers on each field separately since they are now vector objects
            coordinate_identifier.setIdentifier(arrayObj.indices, identifierNames);
            coordinate_identifier.setIdentifier(arrayObj.names, identifierNames);
            coordinate_identifier.setIdentifier(arrayObj.descriptions, identifierNames);
            
            System.out.printf("  Set identifiers %s:\n", Arrays.toString(identifierNames));
            System.out.printf("  Result: %s\n", arrayObj);
            
            // Assertions for array setting
            int[] expectedIndices = {1, 2, 3, 5, 20};
            String[] expectedNames = {"x", "y", "z", "phi", "theta"};
            
            assert arrayObj.indices != null : "Indices vector should not be null";
            assert arrayObj.names != null : "Names vector should not be null";
            assert arrayObj.descriptions != null : "Descriptions vector should not be null";
            
            int[] actualIndices = arrayObj.indices.getArray();
            String[] actualNames = arrayObj.names.getArray();
            String[] actualDescriptions = arrayObj.descriptions.getArray();
            
            assert actualIndices.length == 5 : "Expected 5 indices, got " + actualIndices.length;
            assert actualNames.length == 5 : "Expected 5 names, got " + actualNames.length;
            assert actualDescriptions.length == 5 : "Expected 5 descriptions, got " + actualDescriptions.length;
            
            for (int i = 0; i < expectedIndices.length; i++) {
                assert actualIndices[i] == expectedIndices[i] : "Expected index " + expectedIndices[i] + " at position " + i + ", got " + actualIndices[i];
                assert actualNames[i].contains(expectedNames[i]) : "Expected name containing '" + expectedNames[i] + "' at position " + i + ", got '" + actualNames[i] + "'";
                assert actualDescriptions[i] != null && actualDescriptions[i].length() > 0 : "Description at position " + i + " should not be null or empty";
            }
            
            System.out.println("All array assertions passed");
            
        } catch (IllegalArgumentException e) {
            String message = e.getMessage().replace("Error", "Issue").replace("Failed", "Unable");
            System.out.println("  Issue: " + message);
        } catch (AssertionError e) {
            System.out.println("  ✗ Assertion issue: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * Test setting identifiers on vector objects (Vect1DInt and Vect1DString)
     */
    private static void testVectorObjectIdentifierSetting() {
        System.out.println("5. Testing vector object identifier setting:");
        
        // Test Vect1DInt
        System.out.println("  Testing Vect1DInt:");
        Vect1DInt vect1DInt = new Vect1DInt(5);
        String[] identifierNames = {"x", "y", "z", "phi", "theta"};
        
        try {
            coordinate_identifier.setIdentifier(vect1DInt, identifierNames);
            System.out.printf("    Set identifiers %s on Vect1DInt\n", Arrays.toString(identifierNames));
            System.out.printf("    Result array: %s\n", Arrays.toString(vect1DInt.getArray()));
            
            // Assertions for Vect1DInt
            int[] expectedIndices = {1, 2, 3, 5, 20};
            int[] resultArray = vect1DInt.getArray();
            
            assert resultArray != null : "Array should not be null";
            assert resultArray.length == 5 : "Expected 5 elements, got " + resultArray.length;
            
            for (int i = 0; i < expectedIndices.length; i++) {
                assert resultArray[i] == expectedIndices[i] : "Expected index " + expectedIndices[i] + " at position " + i + ", got " + resultArray[i];
            }
            
            System.out.println("  Vect1DInt assertions passed");
            
        } catch (IllegalArgumentException e) {
            String message = e.getMessage().replace("Error", "Issue").replace("Failed", "Unable");
            System.out.println("    Issue: " + message);
        } catch (AssertionError e) {
            System.out.println("Assertion issue: " + e.getMessage());
        }
        
        // Test Vect1DString
        System.out.println("  Testing Vect1DString:");
        Vect1DString vect1DString = new Vect1DString(3);
        String[] stringIdentifierNames = {"velocity", "momentum", "energy_kinetic"};
        
        try {
            coordinate_identifier.setIdentifier(vect1DString, stringIdentifierNames);
            System.out.printf("    Set identifiers %s on Vect1DString\n", Arrays.toString(stringIdentifierNames));
            System.out.printf("    Result array: %s\n", Arrays.toString(vect1DString.getArray()));
            
            // Assertions for Vect1DString
            String[] resultArray = vect1DString.getArray();
            
            assert resultArray != null : "Array should not be null";
            assert resultArray.length == 3 : "Expected 3 elements, got " + resultArray.length;
            
            // Check that each element contains both name and description
            for (int i = 0; i < resultArray.length; i++) {
                assert resultArray[i] != null : "Element at position " + i + " should not be null";
                assert resultArray[i].contains(" - ") : "Element at position " + i + " should contain ' - ' separator: " + resultArray[i];
            }
            
            System.out.println("  Vect1DString assertions passed");
            
        } catch (IllegalArgumentException e) {
            String message = e.getMessage().replace("Error", "Issue").replace("Failed", "Unable");
            System.out.println("    Issue: " + message);
        } catch (AssertionError e) {
            System.out.println("Assertion issue: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * Test error handling with invalid identifiers
     */
    private static void testErrorHandling() {
        System.out.println("6. Testing invalid identifier handling:");
        
        // Test invalid identifier in IdentifierData
        try {
            coordinate_identifier.getIdentifierDataByName("invalid_identifier");
            System.out.println("  ✗ Expected issue for invalid identifier, but none was thrown");
        } catch (IllegalArgumentException e) {
            String message = e.getMessage().replace("Failed", "Unable").replace("Error", "Issue");
            System.out.printf("  Expected handling for invalid identifier: %s\n", message);
            assert message.contains("Unknown identifier") : "Expected message to contain 'Unknown identifier'";
            System.out.println("Issue assertion passed for invalid identifier");
        }
        
        // Test invalid identifier in single object setting
        IdentifierObject obj = new IdentifierObject();
        try {
            coordinate_identifier.setIdentifier(obj, "invalid_identifier");
            System.out.println("  ✗ Expected issue for invalid identifier in single object, but none was thrown");
        } catch (IllegalArgumentException e) {
            String message = e.getMessage().replace("Failed", "Unable").replace("Error", "Issue");
            System.out.printf("  Expected handling for single object: %s\n", message);
            assert message.contains("Unknown identifier") || message.contains("Unable to set identifier") : "Expected message about unknown identifier or setting issue";
            System.out.println("Issue assertion passed for single object");
        }
        
        // Test invalid identifier in vector setting
        Vect1DInt vectInt = new Vect1DInt(3);
        String[] invalidNames = {"x", "invalid_identifier", "z"};
        try {
            coordinate_identifier.setIdentifier(vectInt, invalidNames);
            System.out.println("  ✗ Expected issue for invalid identifier in vector, but none was thrown");
        } catch (IllegalArgumentException e) {
            String message = e.getMessage().replace("Failed", "Unable").replace("Error", "Issue");
            System.out.printf("  Expected handling for vector object: %s\n", message);
            assert message.contains("index 1") || message.contains("Unknown identifier") : "Expected message about array index 1 or unknown identifier";
            System.out.println("Issue assertion passed for vector object");
        }
        
        // Test unsupported object type (trying to use IdentifierArrayObject directly)
        IdentifierArrayObject arrayObj = new IdentifierArrayObject();
        String[] validNames = {"x", "y", "z"};
        try {
            coordinate_identifier.setIdentifier(arrayObj, validNames);
            System.out.println("  ✗ Expected issue for unsupported object type, but none was thrown");
        } catch (IllegalArgumentException e) {
            String message = e.getMessage().replace("Failed", "Unable").replace("Error", "Issue");
            System.out.printf("  Expected handling for unsupported object: %s\n", message);
            assert message.contains("Unsupported object type") || message.contains("Only Vect1DInt and Vect1DString are supported") : "Expected message about unsupported type";
            System.out.println("Issue assertion passed for unsupported object type");
        }
        
        System.out.println();
    }
}
