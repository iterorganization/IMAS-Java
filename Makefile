include ../Makefile.common

ifeq ("no","$(JAVA)")
$(warning Ignoring javainterface (JAVA=no).)
all:
clean:
clean-src:
install:
else

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
	xsltproc IDSDef2Java.xsl ../xml/IDSDef.xml > imasjava/imas.java
endif # JAVA=no?
