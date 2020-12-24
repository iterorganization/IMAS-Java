# -*- makefile -*- #
include ../Makefile.common

ifneq ("yes","$(strip $(IMAS_JAVA))")
all sources sources_install install uninstall clean clean-src:
	$(warning "Ignoring javainterface (IMAS_JAVA=no).")
else

# Ensure _JAVA_OPTIONS does not override/conflict with JFLAGS
_JAVA_OPTIONS =
JFLAGS = -g -Xmaxerrs 10 -J-Xmx1g -sourcepath ./src -d ./build

JUnit = 1.5.2
JUnit.JAR = junit-platform-console-standalone-$(JUnit).jar

# Get a list of IDS from IDSDEF file
IDSDEF = ../xml/IDSDef.xml
IDSNAMES := $(shell sed '/<IDS name=/!d;s/.*name="\([^"]*\)".*/\1/' $(IDSDEF))
IDSSOURCES = $(addprefix src/imasjava/ids/,$(addsuffix _IDSBase.java,$(IDSNAMES)))
# Generated sources (not all sources)
GENSOURCES = src/imasjava/imas.java $(IDSSOURCES)
# Static sources
STA2SOURCES = $(addprefix src/imasjava/,$(addsuffix .java,UALLowLevel))
STA1SOURCES = $(addprefix src/imasjava/,$(addsuffix .java,UALException utilities/ImasReflection Vect1DBoolean Vect1DDouble Vect1DFloat Vect1DInt Vect1DString Vect2DDouble Vect2DFloat Vect2DInt Vect3DDouble Vect3DFloat Vect3DInt Vect4DDouble Vect5DDouble Vect6DDouble Vect7DDouble Complex Vect1DComplex Vect2DComplex Vect3DComplex Vect4DComplex Vect5DComplex Vect6DComplex wrapper/Wrapper wrapper/LowLevel ))
SOURCES = $(GENSOURCES) $(STA2SOURCES) $(STA1SOURCES)

# We can do with just one class file target
CLASSFILE = build/imasjava/imas.class

JARFILE = lib/imas.jar

all: bindings $(JARFILE) id_java_all

bindings:
	$(MAKE) -C wrapper

sources: $(GENSOURCES) id_java_sources

# Include OS-specific Makefile, if exists.
ifneq (,$(wildcard Makefile.$(SYSTEM)))
include Makefile.$(SYSTEM)
else
sources_install install:
	$(error No Makefile.$(SYSTEM) found for this system: $(UNAME_S))
endif

clean: id_java_clean
	$(MAKE) -C wrapper/ $@
	$(RM) -r ./build ./lib
	$(RM) $(JARFILE)

clean-src: clean id_java_clean-src
	$(RM) $(GENSOURCES)

lib build $(prefix)/jar $(addprefix $(datadir)/src/javainterface/imasjava/,ids utilities):
	$(mkdir_p) $@

$(JARFILE): $(CLASSFILE) | lib
	$(JAR) cf $@ -C ./build .

# Static sources for imas class
$(CLASSFILE): build/%.class:src/%.java | build
	$(JAVAC) $(JFLAGS) $^

# Use an intermediate target to enforce nonparallel generation.
# Gracefully skip generation of sources if not needed.
$(GENSOURCES): gensources
	$(if $(wildcard $@~),@echo Correcting indentation of $@ ; $(BEAUTIFY) $@ && $(RM) $@~)
gensources: IDSDef2Java.xsl | saxonicajar
	$(if $(call allnewerthan,$(GENSOURCES),$^),, $(SAXON) -t -s:$(IDSDEF) -xsl:$< SYSTEM=$(SYSTEM) DD_VERSION=$(DD_GIT_DESCRIBE) AL_VERSION=$(UAL_GIT_DESCRIBE) && \
	  touch $(addsuffix ~,$(GENSOURCES)) )
beautify: $(GENSOURCES) | javaformatjar
	$(JAVA) com.google.googlejavaformat.java.Main -i $^

junit-dirs:
	-mkdir -p junit_jar
	-mkdir -p junit_target

junit-test-compile: $(JARFILE) junit-download
	$(JAVA_HOME)/bin/javac -cp lib/imas.jar:junit_jar/$(JUnit.JAR) -d junit_target tests/junit/imasjava/*.java

junit-test-run: junit-test-compile
	$(JAVA_HOME)/bin/java -jar junit_jar/$(JUnit.JAR) --class-path lib/imas.jar:junit_target --scan-class-path

junit-download: junit-dirs
	curl -s -z junit_jar/$(JUnit.JAR) \
	-o junit_jar/$(JUnit.JAR) \
	http://central.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/$(JUnit)/$(JUnit.JAR)

#----------------------- identifiers ---------------------
include ../Makefile.identifiers

#----------------------- classpath deps ---------------------
include ../Makefile.classpath
# Check existence of the utility to get a clean Java format
ifeq ("","$(wildcard $(JAVAFORMATJAR))")
BEAUTIFY = echo Skipped
else
BEAUTIFY = $(JAVA) com.google.googlejavaformat.java.Main -i
endif

endif # IMAS_JAVA=no?
