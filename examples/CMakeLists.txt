project( javainterface-ex )

find_package(Java)
include(UseJava)
include(ALExampleUtilities)

# Sources
################################################################################

set( TEST_ENVIRONMENT
    "LD_LIBRARY_PATH=$<TARGET_FILE_DIR:al-java-binding>:$ENV{LD_LIBRARY_PATH}"
)

if( AL_BACKEND_MDSPLUS )
  # Use MDSPLUS backend for testing
  get_target_property( MDSPLUS_MODEL_DIR al-mdsplus-model BINARY_DIR )
  list( APPEND TEST_ENVIRONMENT "IMAS_AL_DEFAULT_BACKEND=12;ids_path=${MDSPLUS_MODEL_DIR}" )
  message( "Using MDSPLUS backend for Java examples" )
elseif( AL_BACKEND_HDF5 )
  # If MDSPLUS is not available and HDF5 is, use that for testing
  list( APPEND TEST_ENVIRONMENT "IMAS_AL_DEFAULT_BACKEND=13" )
  message( "Using HDF5 backend for Java examples" )
else()
  message( "Skipping tests that require persistent storage" )
endif()

set( TESTS
    TestCoreProfilesEmpty
    TestCoreProfilesGet
    TestCoreProfilesGetSlice
    TestCoreProfilesOpen
    TestCoreProfilesOpen_Force_Create
    TestCoreProfilesPut
    TestCoreProfilesPutSlice
    TestCoreProfilesSerialize
    TestCoreProfilesValidate
    TestCoreProfilesSampleGet
    TestDistributionsValidate
    TestMagneticsValidate
    TestPfActiveGet
    TestPfActiveGetSlice
    TestPfActivePut
    TestPfActivePutSlice
    TestResizeArray
    TestVersion
    TestWavesValidate
)

# Report an error when a .java source file exists without corresponding test:
error_on_missing_tests( "java" "${TESTS}" )

# Targets
################################################################################

get_target_property( AL_JAR_FILE al-java JAR_FILE )
set( TEST_CLASSPATH "${CMAKE_CURRENT_BINARY_DIR}:${AL_JAR_FILE}" )


add_custom_target( example-java-all )

foreach( TEST IN LISTS TESTS )
  set( TEST_NAME "example-java-${TEST}" )
  add_custom_command(
    OUTPUT ${CMAKE_CURRENT_BINARY_DIR}/${TEST}.class
    COMMAND ${Java_JAVAC_EXECUTABLE} --class-path ${TEST_CLASSPATH} ${CMAKE_CURRENT_SOURCE_DIR}/${TEST}.java -d ${CMAKE_CURRENT_BINARY_DIR}
    DEPENDS ${TEST}.java al-java
  )
  add_custom_target( ${TEST_NAME} ALL DEPENDS ${CMAKE_CURRENT_BINARY_DIR}/${TEST}.class )
  add_dependencies( example-java-all ${TEST_NAME} )

  # Disable some tests if no persistent backends available
  set( DISABLED OFF )
  if( NOT AL_BACKEND_MDSPLUS AND NOT AL_BACKEND_HDF5 AND TEST_NAME MATCHES "Get|Put|Empty|Serialize" )
    set( DISABLED ON )
  endif()

  # Java command to run the test
  set( TEST_COMMAND ${Java_JAVA_EXECUTABLE} --class-path ${TEST_CLASSPATH} ${TEST} )

  # Execute the test without plugins
  add_test( NAME ${TEST_NAME} COMMAND ${TEST_COMMAND} )
  set_al_example_properties( ${TEST_NAME} ${DISABLED} OFF "${TEST_ENVIRONMENT}" )

  # Execute the test with plugins
  if( AL_PLUGINS )
    add_test( NAME ${TEST_NAME}-with-plugins COMMAND ${TEST_COMMAND} )
    set_al_example_properties( ${TEST_NAME}-with-plugins ${DISABLED} ON "${TEST_ENVIRONMENT}" )
  endif()
endforeach()

# Manually set some dependencies
set_tests_properties(
  example-java-TestCoreProfilesOpen_Force_Create
  PROPERTIES FIXTURES_SETUP examples-java-CoreProfilesOpen
)
set_tests_properties(
  example-java-TestCoreProfilesOpen
  PROPERTIES FIXTURES_REQUIRED examples-java-CoreProfilesOpen
)
