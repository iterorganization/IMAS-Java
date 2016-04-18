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

gen : IDSDef2Java.xsl
	 java net.sf.saxon.Transform -t -s:../xml/IDSDef.xml -xsl:IDSDef2Java.xsl 

comp: 
	javac -g -Xmaxerrs 10 ./src/imasjava/*.java           -sourcepath ./src  -d ./build 
	javac -g -Xmaxerrs 10 ./src/imasjava/utilities/*.java -sourcepath ./src  -d ./build

install: all
	mkdir -p $(INSTALL)/jar
	cp ./lib/imas.jar $(INSTALL)/jar

clean:
	$(RM) -rf ./build/ 
	$(RM) -rf ./lib/

clean-src: clean
	$(RM) -rf ./src/imasjava/imas.java
	$(RM) -rf ./src/imasjava/ids

imas.jar : $(SOURCES) 
#	javac $(SOURCES)
#	jar cf imas.jar $(CLASSES)
	mkdir -p ./build/
	javac -g -Xmaxerrs 10 ./src/imasjava/*.java           -sourcepath ./src  -d ./build
	javac -g -Xmaxerrs 10 ./src/imasjava/utilities/*.java -sourcepath ./src  -d ./build
	mkdir -p ./lib/
	jar cf ./lib/imas.jar -C ./build . 

$(SOURCES) : IDSDef2Java.xsl
	java net.sf.saxon.Transform -t -s:../xml/IDSDef.xml -xsl:IDSDef2Java.xsl 
#	java net.sf.saxon.Transform -t -s:../xml/IDSDef.xml -xsl:IDSDef2Java.xsl -o:imasjava/imas.java
#	xsltproc IDSDef2Java.xsl ../xml/IDSDef.xml > imasjava/imas.java
endif
