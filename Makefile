include ../Makefile.common

ifeq ("no","$(JAVA)")
$(warning Ignoring javainterface (JAVA=no).)
all:
sources:
clean:
clean-src:
install:
else

JAVAC = javac
JAR = jar
JFLAGS = -g -Xmaxerrs 10 -sourcepath ./src -d ./build

# Get a list of IDS from IDSDEF file
IDSDEF = ../xml/IDSDef.xml
IDSNAMES := $(shell sed '/<IDS name=/!d;s/.*name="\(.*\)"/\1/' $(IDSDEF))
IDSSOURCES = $(addprefix src/imasjava/ids/,$(addsuffix _IDSBase.java,$(IDSNAMES)))
# Generated sources (not all sources)
GENSOURCES = src/imasjava/imas.java $(IDSSOURCES)

# List of class files (not including derived class files)
CLASSES = $(subst src/,build/,$(GENSOURCES:.java=.class))

# We can do with just one class file target
CLASSFILE = build/imasjava/imas.class
JARFILE = lib/imas.jar

all: $(JARFILE)

sources: $(GENSOURCES)

install: all
	install -d $(INSTALL)/jar
	install -m644 ./lib/imas.jar $(INSTALL)/jar/

clean:
	$(RM) -r ./build ./lib
	$(RM) $(JARFILE)

clean-src: clean
	$(RM) $(GENSOURCES)

$(JARFILE): $(dir $(JARFILE)) $(CLASSFILE)
	$(JAR) cvf $@ -C ./build .

$(CLASSFILE): $(dir $(CLASSFILE)) $(GENSOURCES)
	$(JAVAC) $(JFLAGS) $(subst build/,src/,$(@:.class=.java))

# Use an intermediate target to enforce nonparallel generation.
gensources: IDSDef2Java.xsl saxonicajar
	java net.sf.saxon.Transform -t -s:$(IDSDEF) -xsl:$<

# Test if all generated sources are found to exist as files to
# gracefully skip generation if not needed.
ifeq ($(words $(GENSOURCES)), $(words $(wildcard $(GENSOURCES))))
$(GENSOURCES):
else
$(GENSOURCES): gensources
endif

# Directory prerequisites
$(dir $(CLASSFILE) $(JARFILE)):
	install -d $@

# Test for saxon
# Check that "saxon9he.jar" utility is set in CLASSPATH and existst
SAXONICAJAR=$(wildcard $(filter %saxon9he.jar,$(subst :, ,$(CLASSPATH))))
saxonicajar:
ifeq (,$(SAXONICAJAR))
	$(error Invalid /path/to/saxon9he.jar in CLASSPATH. Forgot to load module?)
endif

endif
