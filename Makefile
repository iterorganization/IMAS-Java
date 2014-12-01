include ../Makefile.common

SOURCES = ualmemory/javainterface/*.java 

#-------- Options for cache monitoring ---------
ifeq "$(strip $(MONITOR))" "yes"
 SOURCES += CacheMonitor.java
endif
#-----------------------------------------------

CLASSES = $(SOURCES:.java=.class) 

all : ualjava.jar

install: all
	mkdir -p $(INSTALL)/jar
	cp ualjava.jar $(INSTALL)/jar

clean:
	@ $(RM) $(CLASSES) 
	@ $(RM) ualjava.jar

clean-src: clean
	@ $(RM) ualmemory/javainterface/imas.java



ualjava.jar : $(SOURCES) ualmemory/javainterface/imas.java
	javac $(SOURCES)
	jar cf ualjava.jar $(CLASSES)

ualmemory/javainterface/imas.java : IDSDef2Java.xsl
	xsltproc IDSDef2Java.xsl ../xml/IDSDef.xml > ualmemory/javainterface/imas.java

