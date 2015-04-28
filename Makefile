include ../Makefile.common

SOURCES = ualmemory/javainterface/*.java

#-------- Options for cache monitoring ---------
ifeq (yes,$(strip $(MONITOR)))
 SOURCES += CacheMonitor.java
endif
#-----------------------------------------------

CLASSES = $(SOURCES:.java=.class)

all : imas.jar

install: all
	mkdir -p $(INSTALL)/jar
	cp imas.jar $(INSTALL)/jar

clean:
	@ $(RM) $(CLASSES)
	@ $(RM) imas.jar

clean-src: clean
	@ $(RM) ualmemory/javainterface/imas.java



imas.jar : $(SOURCES) ualmemory/javainterface/imas.java
	javac $(SOURCES)
	jar cf imas.jar $(CLASSES)

ualmemory/javainterface/imas.java : IDSDef2Java.xsl
	xsltproc IDSDef2Java.xsl ../xml/IDSDef.xml > ualmemory/javainterface/imas.java

