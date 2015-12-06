include ../Makefile.common

SOURCES = imasjava/*.java

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
	$(RM) $(CLASSES)
	$(RM) imas.jar

clean-src: clean
	$(RM) imasjava/imas.java

imas.jar : $(SOURCES) imasjava/imas.java
	javac $(SOURCES)
	jar cf imas.jar $(CLASSES)

imasjava/imas.java : IDSDef2Java.xsl
	java net.sf.saxon.Transform -t -s:../xml/IDSDef.xml -xsl:IDSDef2Java.xsl -o:imasjava/imas.java
#	xsltproc IDSDef2Java.xsl ../xml/IDSDef.xml > imasjava/imas.java

