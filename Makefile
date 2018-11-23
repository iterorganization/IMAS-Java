# -*- makefile -*- #
include ../Makefile.common

ifeq ("no","$(strip $(IMAS_JAVA))")
all sources sources_install install clean clean-src:
	$(warning "Ignoring javainterface (IMAS_JAVA=no).")
else

JAVAC = javac
JAR = jar
JFLAGS = -g -Xmaxerrs 10 -sourcepath ./src -d ./build

# Get a list of IDS from IDSDEF file
IDSDEF = ../xml/IDSDef.xml
IDSNAMES := $(shell sed '/<IDS name=/!d;s/.*name="\([^"]*\)".*/\1/' $(IDSDEF))
IDSSOURCES = $(addprefix src/imasjava/ids/,$(addsuffix _IDSBase.java,$(IDSNAMES)))
# Generated sources (not all sources)
GENSOURCES = src/imasjava/imas.java $(IDSSOURCES)
# Static sources
STA2SOURCES = $(addprefix src/imasjava/,$(addsuffix .java,UALLowLevel))
STA1SOURCES = $(addprefix src/imasjava/,$(addsuffix .java,UALException utilities/ImasReflection Vect1DBoolean Vect1DDouble Vect1DFloat Vect1DInt Vect1DString Vect2DDouble Vect2DFloat Vect2DInt Vect3DDouble Vect3DFloat Vect3DInt Vect4DDouble Vect5DDouble Vect6DDouble Vect7DDouble))
SOURCES = $(GENSOURCES) $(STA2SOURCES) $(STA1SOURCES)

# List of class files (not including derived class files)
CLASSES = $(subst src/,build/,$(SOURCES:.java=.class))

# We can do with just one class file target
CLASSFILE = build/imasjava/imas.class
JARFILE = lib/imas.jar

all: $(JARFILE)

sources: $(GENSOURCES)
sources_install: $(GENSOURCES)
	$(mkdir_p) $(datadir)/src/javainterface/imasjava/{ids,utilities}
	$(INSTALL_DATA) src/imasjava/*.java $(datadir)/src/javainterface/imasjava
	$(INSTALL_DATA) src/imasjava/ids/*.java $(datadir)/src/javainterface/imasjava/ids
	$(INSTALL_DATA) src/imasjava/utilities/*.java $(datadir)/src/javainterface/imasjava/utilities

install: all
	$(mkdir_p) $(prefix)/jar
	$(INSTALL_DATA) ./lib/imas.jar $(prefix)/jar/

clean:
	$(RM) -r ./build ./lib
	$(RM) $(JARFILE)

clean-src: clean
	$(RM) $(GENSOURCES)

$(JARFILE): $(CLASSFILE)
	@$(mkdir_p) $(@D)
	$(JAR) cf $@ -C ./build .

$(CLASSES): $(GENSOURCES)
	@$(mkdir_p) $(@D)
	$(JAVAC) $(JFLAGS) $(subst build/,src/,$(@:.class=.java))

# Use an intermediate target to enforce nonparallel generation.
gensources: IDSDef2Java.xsl | saxonicajar
	java net.sf.saxon.Transform -t -s:$(IDSDEF) -xsl:$<

# Test if all generated sources are found to exist as files to
# gracefully skip generation if not needed.
ifeq ($(words $(GENSOURCES)), $(words $(wildcard $(GENSOURCES))))
$(GENSOURCES):
else
$(GENSOURCES): gensources
endif

#----------------------- classpath deps ---------------------
include ../Makefile.classpath
endif # IMAS_JAVA=no?
