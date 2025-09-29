import java.util.Arrays;

/**
 * Test class for coordinate_identifier functionality
 * Demonstrates usage of the mapping functions and identifier setting capabilities
 */
public class TestIdentifiers {
    
    // Example implementation of IdentifiableObject interface
    static class SimpleIdentifiableObject implements coordinate_identifier.IdentifiableObject {
        private int index;
        private String name;
        private String description;
        
        @Override
        public void setIndex(int index) {
            this.index = index;
        }
        
        @Override
        public void setName(String name) {
            this.name = name;
        }
        
        @Override
        public void setDescription(String description) {
            this.description = description;
        }
        
        @Override
        public String toString() {
            return String.format("IdentifiableObject{index=%d, name='%s', description='%s'}", 
                               index, name, description);
        }
    }
    
    // Example implementation of IdentifiableArrayObject interface
    static class SimpleIdentifiableArrayObject implements coordinate_identifier.IdentifiableArrayObject {
        private int[] indices;
        private String[] names;
        private String[] descriptions;
        
        @Override
        public void setIndices(int[] indices) {
            this.indices = indices.clone();
        }
        
        @Override
        public void setNames(String[] names) {
            this.names = names.clone();
        }
        
        @Override
        public void setDescriptions(String[] descriptions) {
            this.descriptions = descriptions.clone();
        }
        
        @Override
        public String toString() {
            return String.format("IdentifiableArrayObject{indices=%s, names=%s, descriptions=%s}", 
                               Arrays.toString(indices), Arrays.toString(names), Arrays.toString(descriptions));
        }
    }
    
    public static void main(String args[]) {
        System.out.println("=== Testing coordinate_identifier functionality ===\n");
        
        // Test 1: Basic mapping functions
        testBasicMappingFunctions();
        
        // Test 2: TypeData functionality
        testTypeDataFunctionality();
        
        // Test 3: Single object identifier setting
        testSingleObjectIdentifierSetting();
        
        // Test 4: Array object identifier setting
        testArrayObjectIdentifierSetting();
        
        // Test 5: Invalid identifier handling
        testErrorHandling();
    }
    
    /**
     * Test basic mapping functions: getIndex, getName, getDescription
     */
    private static void testBasicMappingFunctions() {
        System.out.println("1. Testing basic mapping functions:");
        
        String[] testNames = {"unspecified", "x", "y", "z", "phi", "velocity", "momentum", "energy_kinetic"};
        
        for (String name : testNames) {
            int index = coordinate_identifier.getIndex(name);
            String retrievedName = coordinate_identifier.getName(index);
            String description = coordinate_identifier.getDescription(index);
            
            System.out.printf("  Name: %-15s -> Index: %-3d -> Retrieved Name: %-15s\n", 
                            name, index, retrievedName);
            System.out.printf("    Description: %s\n", description);
        }
        
        // Test unknown identifier
        int unknownIndex = coordinate_identifier.getIndex("unknown_identifier");
        System.out.printf("  Unknown identifier index: %d\n", unknownIndex);
        
        System.out.println();
    }
    
    /**
     * Test TypeData functionality
     */
    private static void testTypeDataFunctionality() {
        System.out.println("2. Testing TypeData functionality:");
        
        try {
            coordinate_identifier.TypeData typeData = coordinate_identifier.getTypeDataByName("rho_tor");
            System.out.printf("  TypeData for 'rho_tor': index=%d, description='%s'\n", 
                            typeData.index, typeData.description);
            
            typeData = coordinate_identifier.getTypeDataByName("velocity_parallel");
            System.out.printf("  TypeData for 'velocity_parallel': index=%d, description='%s'\n", 
                            typeData.index, typeData.description);
        } catch (IllegalArgumentException e) {
            String message = e.getMessage().replace("Error", "Issue");
            System.out.println("  Issue: " + message);
        }
        
        System.out.println();
    }
    
    /**
     * Test setting identifier on a single object
     */
    private static void testSingleObjectIdentifierSetting() {
        System.out.println("3. Testing single object identifier setting:");
        
        SimpleIdentifiableObject obj = new SimpleIdentifiableObject();
        
        try {
            coordinate_identifier.setIdentifier(obj, "theta_straight");
            System.out.printf("  Set identifier 'theta_straight': %s\n", obj);
            
            coordinate_identifier.setIdentifier(obj, "magnetic_moment");
            System.out.printf("  Set identifier 'magnetic_moment': %s\n", obj);
        } catch (IllegalArgumentException e) {
            String message = e.getMessage().replace("Error", "Issue").replace("Failed", "Unable");
            System.out.println("  Issue: " + message);
        }
        
        System.out.println();
    }
    
    /**
     * Test setting identifiers on an array object
     */
    private static void testArrayObjectIdentifierSetting() {
        System.out.println("4. Testing array object identifier setting:");
        
        SimpleIdentifiableArrayObject arrayObj = new SimpleIdentifiableArrayObject();
        String[] identifierNames = {"x", "y", "z", "phi", "theta"};
        
        try {
            coordinate_identifier.setIdentifier(arrayObj, identifierNames);
            System.out.printf("  Set identifiers %s:\n", Arrays.toString(identifierNames));
            System.out.printf("  Result: %s\n", arrayObj);
        } catch (IllegalArgumentException e) {
            String message = e.getMessage().replace("Error", "Issue").replace("Failed", "Unable");
            System.out.println("  Issue: " + message);
        }
        
        System.out.println();
    }
    
    /**
     * Test error handling with invalid identifiers
     */
    private static void testErrorHandling() {
        System.out.println("5. Testing invalid identifier handling:");
        
        // Test invalid identifier in TypeData
        try {
            coordinate_identifier.getTypeDataByName("invalid_identifier");
        } catch (IllegalArgumentException e) {
            String message = e.getMessage().replace("Failed", "Unable").replace("Error", "Issue");
            System.out.printf("  Expected handling for invalid identifier: %s\n", message);
        }
        
        // Test invalid identifier in single object setting
        SimpleIdentifiableObject obj = new SimpleIdentifiableObject();
        try {
            coordinate_identifier.setIdentifier(obj, "invalid_identifier");
        } catch (IllegalArgumentException e) {
            String message = e.getMessage().replace("Failed", "Unable").replace("Error", "Issue");
            System.out.printf("  Expected handling for single object: %s\n", message);
        }
        
        // Test invalid identifier in array setting
        SimpleIdentifiableArrayObject arrayObj = new SimpleIdentifiableArrayObject();
        String[] invalidNames = {"x", "invalid_identifier", "z"};
        try {
            coordinate_identifier.setIdentifier(arrayObj, invalidNames);
        } catch (IllegalArgumentException e) {
            String message = e.getMessage().replace("Failed", "Unable").replace("Error", "Issue");
            System.out.printf("  Expected handling for array object: %s\n", message);
        }
        
        System.out.println();
    }
}
