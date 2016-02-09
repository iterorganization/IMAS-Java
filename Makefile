include ../Makefile.common

SOURCES = src/imasjava/*.java

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
	javac -Xmaxerrs 10 ./src/imasjava/*.java -sourcepath ./src  -d ./build 

install: all
	mkdir -p $(INSTALL)/jar
	cp ./lib/imas.jar $(INSTALL)/jar

clean:
	$(RM) -rf ./build/ 
	$(RM) -rf ./lib/

clean-src: clean
	$(RM) -rf ./src/imasjava/imas.java
	$(RM) -rf ./src/imasjava/ids

imas.jar : $(SOURCES) imasjava/imas.java
#	javac $(SOURCES)
#	jar cf imas.jar $(CLASSES)
	mkdir -p ./build/
	javac -Xmaxerrs 10 ./src/imasjava/*.java -sourcepath ./src  -d ./build
	mkdir -p ./lib/
	jar cvf ./lib/imas.jar -C ./build . 

imasjava/imas.java : IDSDef2Java.xsl
	java net.sf.saxon.Transform -t -s:../xml/IDSDef.xml -xsl:IDSDef2Java.xsl 
#	java net.sf.saxon.Transform -t -s:../xml/IDSDef.xml -xsl:IDSDef2Java.xsl -o:imasjava/imas.java
#	xsltproc IDSDef2Java.xsl ../xml/IDSDef.xml > imasjava/imas.java

