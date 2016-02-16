IDSDEF= ../../../xml/IDSDef.xml
TESTDIR = ../testsuite

all: test


clean:
		rm -rf $(TESTDIR)

$(TESTDIR)/TestSuite.java:  TestSuiteJava.xsl $(IDSDEF)
	mkdir -p $(TESTDIR)
	xsltproc TestSuiteJava.xsl $(IDSDEF) > $(TESTDIR)/TestSuite.java

$(TESTDIR)/TestSuite.class:  $(TESTDIR)/TestSuite.java
	javac $(TESTDIR)/TestSuite.java
	
test: $(TESTDIR)/TestSuite.class	
	java -cp $(TESTDIR):${CLASSPATH} TestSuite 
