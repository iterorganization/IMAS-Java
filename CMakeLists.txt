# CMake build configuration for the Access Layer Java HLI
cmake_minimum_required( VERSION 3.16 )

# Configuration options for common assets
################################################################################
option( AL_DOWNLOAD_DEPENDENCIES "Automatically download common assets from the AL git repository" ON )
set( AL_COMMON_GIT_REPOSITORY "ssh://git@git.iter.org/imas/al-common.git" CACHE STRING "Git repository of AL-common" )
set( AL_COMMON_VERSION "main" CACHE STRING "Git commit/tag/branch of AL-common" )

# BEGIN >> al-lowlevel -> al-core rename
# overrule default versions of repos
# FIXME: remove before merging!
set( AL_COMMON_VERSION "refactor/rename-al-core" CACHE STRING "" FORCE )
set( AL_CORE_VERSION "refactor/rename-al-core" CACHE STRING "" FORCE )
# << END

include(FetchContent)

# Load common assets
################################################################################
if( ${AL_DOWNLOAD_DEPENDENCIES} )
  # Download common assets from the ITER git:
  FetchContent_Declare(
    al-common
    GIT_REPOSITORY "${AL_COMMON_GIT_REPOSITORY}"
    GIT_TAG "${AL_COMMON_VERSION}"
  )
else()
  # Look in $AL_COMMON_PATH or ../al-common for common files
  if( DEFINED ENV{AL_COMMON_PATH} )
    set( AL_COMMON_PATH $ENV{AL_COMMON_PATH} )
  else()
    set( AL_COMMON_PATH "${CMAKE_CURRENT_SOURCE_DIR}/../al-common" )
  endif()
  FetchContent_Declare(
    al-common
    SOURCE_DIR      "${AL_COMMON_PATH}"
  )
endif()
FetchContent_MakeAvailable( al-common )


# Define project
################################################################################

# Set VERSION and FULL_VERSION from `git describe`:
set( GIT_ARCHIVE_DESCRIBE [[$Format:%(describe)$]] )
include( ALDetermineVersion )
project( al-java
  VERSION ${VERSION}
  DESCRIPTION "Java High Level Interface of the IMAS Access Layer"
  HOMEPAGE_URL "https://imas.iter.org/"
  LANGUAGES C CXX
)
if( NOT PROJECT_VERSION_TWEAK EQUAL 0 )
  message( "Building a development version of the Access Layer Java HLI" )
endif()

# Compiler settings
include( ALSetCompilerFlags )
# Common HLI options
include( ALCommonConfig )
# Data dictionary
include( ALBuildDataDictionary )
# AL core, plugins and documentation
include( ALCore )

# Stop processing when only building documentation
if( AL_DOCS_ONLY )
  return()
endif()


# Dependencies
################################################################################

find_package( JNI REQUIRED )
find_package( Java REQUIRED )
include( UseJava )
find_package( SaxonHE )
# Find LibXslt for the xsltproc program
find_package( LibXslt QUIET )
if( NOT LIBXSLT_XSLTPROC_EXECUTABLE )
  message( FATAL_ERROR "Could not find xsltproc" )
endif()


# Generate sources
################################################################################

set( GENERATED_SRC_DIR "${CMAKE_CURRENT_BINARY_DIR}/src/imasjava" )

set( GENERATED_SOURCES "${GENERATED_SRC_DIR}/imas.java" )
foreach( IDS_NAME IN LISTS IDS_NAMES )
  list( APPEND GENERATED_SOURCES "${GENERATED_SRC_DIR}/ids/${IDS_NAME}_IDSBase.java" )
endforeach()

add_custom_command(
  # Use a dummy output file: when listing all actual output files (which are
  # source files of target `al-java`), they would be re-created with every build.
  OUTPUT ${GENERATED_SRC_DIR}/dummy.txt
  # Generate source files with saxon
  COMMAND
    java -cp ${SaxonHE_CLASSPATH}
      net.sf.saxon.Transform -t -warnings:fatal
      "-xsl:${CMAKE_CURRENT_SOURCE_DIR}/IDSDef2Java.xsl"
      "-s:${IDSDEF}"
      DD_VERSION=${DD_VERSION}
      AL_VERSION=${FULL_VERSION}
      SYSTEM=${CMAKE_SYSTEM_NAME}
  # Create/update our dummy file
  COMMAND ${CMAKE_COMMAND} -E touch ${GENERATED_SRC_DIR}/dummy.txt
  DEPENDS IDSDef2Java.xsl ${IDSDEF}
  WORKING_DIRECTORY ${CMAKE_CURRENT_BINARY_DIR}
)
add_custom_target( al-java-sources
  COMMAND ${CMAKE_COMMAND} -E echo ""
  DEPENDS ${GENERATED_SRC_DIR}/dummy.txt
  # Actually these are results of the custom_command above, but with this trick
  # we don't rebuild it always (only when IDSDef.xml or the xslts have changed)
  BYPRODUCTS ${GENERATED_SOURCES}
)

set( SOURCES ${GENERATED_SOURCES} )
list( APPEND SOURCES
    src/imasjava/ALException.java
    src/imasjava/ALLowLevel.java
    src/imasjava/Complex.java
    src/imasjava/CoordinateValidation.java
    src/imasjava/LimitedSizeStringBuilder.java
    src/imasjava/StringLimitException.java
    src/imasjava/SummaryString.java
    src/imasjava/ValidationException.java
    src/imasjava/Vect1DBoolean.java
    src/imasjava/Vect1DComplex.java
    src/imasjava/Vect1DDouble.java
    src/imasjava/Vect1DFloat.java
    src/imasjava/Vect1DInt.java
    src/imasjava/Vect1DString.java
    src/imasjava/Vect2DComplex.java
    src/imasjava/Vect2DDouble.java
    src/imasjava/Vect2DFloat.java
    src/imasjava/Vect2DInt.java
    src/imasjava/Vect3DComplex.java
    src/imasjava/Vect3DDouble.java
    src/imasjava/Vect3DFloat.java
    src/imasjava/Vect3DInt.java
    src/imasjava/Vect4DComplex.java
    src/imasjava/Vect4DDouble.java
    src/imasjava/Vect5DComplex.java
    src/imasjava/Vect5DDouble.java
    src/imasjava/Vect6DComplex.java
    src/imasjava/Vect6DDouble.java
    src/imasjava/Vect7DComplex.java
    src/imasjava/Vect7DDouble.java
    src/imasjava/VectComplex.java
    src/imasjava/VectGeneric.java
    src/imasjava/utilities/ImasReflection.java
    src/imasjava/wrapper/LowLevel.java
    src/imasjava/wrapper/Wrapper.java
)


# Generate sources (identifiers)
################################################################################

# Note: identifiers.xsl tries to import identifiers.common.xsl
# Unfortunately, xsl:include doesn't support parameters, so we need to put them together
# in the build folder:
file( CREATE_LINK
  "${al-common_SOURCE_DIR}/identifiers.common.xsl" 
  "${CMAKE_CURRENT_BINARY_DIR}/identifiers.common.xsl"
  SYMBOLIC COPY_ON_ERROR
)
file( CREATE_LINK
  "${CMAKE_CURRENT_SOURCE_DIR}/identifiers.xsl" 
  "${CMAKE_CURRENT_BINARY_DIR}/identifiers.xsl"
  SYMBOLIC COPY_ON_ERROR
)
set( IDENTIFIER_SRC_DIR ${CMAKE_CURRENT_BINARY_DIR}/identifiers/src/ )

set( IDENTIFIER_SOURCES )
foreach( ID_FILE IN LISTS DD_IDENTIFIER_FILES )
  # cmake_path( GET ID_FILE STEM ID_NAME)
  string( REGEX REPLACE "(^.*[/\\]|[.]xml$)" "" ID_NAME ${ID_FILE} )
  set( SOURCE_NAME "${IDENTIFIER_SRC_DIR}/${ID_NAME}.java" )
  add_custom_command(
    OUTPUT ${SOURCE_NAME} ${HEADER_NAME}
    COMMAND ${CMAKE_COMMAND} -E make_directory ${IDENTIFIER_SRC_DIR}
    COMMAND ${LIBXSLT_XSLTPROC_EXECUTABLE}
      --stringparam name ${ID_NAME}
      --stringparam prefix ${IDENTIFIER_SRC_DIR}
      ${CMAKE_CURRENT_BINARY_DIR}/identifiers.xsl
      ${ID_FILE}
    DEPENDS ${CMAKE_CURRENT_BINARY_DIR}/identifiers.xsl
    WORKING_DIRECTORY ${CMAKE_CURRENT_BINARY_DIR}
  )
  list( APPEND IDENTIFIER_SOURCES ${SOURCE_NAME} )
endforeach()


# Targets
################################################################################

add_library( al-java-binding SHARED wrapper/imasjava_wrapper_LowLevel.cpp )
target_include_directories( al-java-binding PRIVATE ${JNI_INCLUDE_DIRS} "wrapper" )
target_link_libraries( al-java-binding al )
# Store lib-al-java-binding.so in the lib/ folder (required to run examples)
set_target_properties( al-java-binding PROPERTIES LIBRARY_OUTPUT_DIRECTORY lib )

# https://cmake.org/cmake/help/latest/module/UseJava.html#add-jar
add_jar( al-java
    SOURCES ${SOURCES}
    OUTPUT_NAME imas
)
add_dependencies( al-java al-java-binding )

add_jar( al-identifiers-java
    SOURCES ${IDENTIFIER_SOURCES}
    OUTPUT_NAME al-identifiers
)


# Install
################################################################################

install( TARGETS al-java-binding )
install_jar( al-java DESTINATION jar )
install_jar( al-identifiers-java DESTINATION jar )

# Tests
################################################################################

# No "test" rule existed in Makefile

if( AL_EXAMPLES )
  add_subdirectory( examples )
endif()
