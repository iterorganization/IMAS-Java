# -*- makefile -*- #
include ../Makefile.common

ifeq ("no","$(strip $(IMAS_JAVA))")
all sources sources_install install clean clean-src:
	$(warning "Ignoring javainterface (IMAS_JAVA=no).")
else

ifneq ("no","$(strip $(SYS_WIN))")
JAVA = $(JAVA_HOME)/bin/java
JAVAC = $(JAVA_HOME)/bin/javac
JAR = $(JAVA_HOME)/bin/jar
else
JAVA = java
JAVAC = javac
JAR = jar
endif

JFLAGS = -g -Xmaxerrs 10 -sourcepath ./src -d ./build

# Get a list of IDS from IDSDEF file
IDSDEF = ../xml/IDSDef.xml
IDSNAMES := $(shell sed '/<IDS name=/!d;s/.*name="\([^"]*\)".*/\1/' $(IDSDEF))
IDSSOURCES = $(addprefix src/imasjava/ids/,$(addsuffix _IDSBase.java,$(IDSNAMES)))
# Generated sources (not all sources)
GENSOURCES = src/imasjava/imas.java $(IDSSOURCES)
# Static sources
STA2SOURCES = $(addprefix src/imasjava/,$(addsuffix .java,UALLowLevel))
STA1SOURCES = $(addprefix src/imasjava/,$(addsuffix .java,UALException utilities/ImasReflection Vect1DBoolean Vect1DDouble Vect1DFloat Vect1DInt Vect1DString Vect2DDouble Vect2DFloat Vect2DInt Vect3DDouble Vect3DFloat Vect3DInt Vect4DDouble Vect5DDouble Vect6DDouble Vect7DDouble wrapper/Wrapper wrapper/LowLevel ))
SOURCES = $(GENSOURCES) $(STA2SOURCES) $(STA1SOURCES)

# We can do with just one class file target
CLASSFILE = build/imasjava/imas.class

JARFILE = lib/imas.jar

all: bindings $(JARFILE) id_java_all

bindings:
	$(MAKE) -C wrapper

sources: $(GENSOURCES) id_java_sources
ifeq ("no","$(strip $(SYS_WIN))")
sources_install: $(GENSOURCES) id_java_sources_install | $(addprefix $(datadir)/src/javainterface/imasjava/,ids utilities)
	$(MAKE) -C wrapper/ $@
	$(INSTALL_DATA) src/imasjava/*.java $(datadir)/src/javainterface/imasjava
	$(INSTALL_DATA) src/imasjava/ids/*.java $(datadir)/src/javainterface/imasjava/ids
	$(INSTALL_DATA) src/imasjava/utilities/*.java $(datadir)/src/javainterface/imasjava/utilities
else
sources_install: $(GENSOURCES) id_java_sources_install
endif

ifeq ("no","$(strip $(SYS_WIN))")
install: all id_java_install | $(prefix)/jar
	$(MAKE) -C wrapper/ $@
	$(INSTALL_DATA) ./lib/imas.jar $(prefix)/jar/
else
install: all id_java_install
	$(mkdir_p) $(packagedir)/javainterface/lib
	$(mkdir_p) $(packagedir)/javainterface/jar
	$(mkdir_p) $(packagedir)/fortraninterface/lib
	for OBJECT in `find ./wrapper/lib -type f \( -name "*.lib" -or -name "*.dll" \)`; do \
		cp $$OBJECT $(packagedir)/javainterface/lib; \
	done
	cp ./lib/imas.jar $(packagedir)/javainterface/jar
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
gensources: IDSDef2Java.xsl | saxonicajar
	$(if $(call allnewerthan,$(GENSOURCES),$^),, $(JAVA) net.sf.saxon.Transform -t -s:$(IDSDEF) -xsl:$< SYS_WIN=$(SYS_WIN))

#----------------------- identifiers ---------------------
include ../Makefile.identifiers

#----------------------- classpath deps ---------------------
include ../Makefile.classpath
endif # IMAS_JAVA=no?
