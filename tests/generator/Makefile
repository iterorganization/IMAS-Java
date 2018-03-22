IDSDEF= ../../../xml/IDSDef.xml
TESTDIR = ../testsuite

all: test


clean:
		$(RM) -r $(TESTDIR)

$(TESTDIR)/TestSuite.java:  TestSuiteJava.xsl $(IDSDEF)
	$(mkdir_p) $(TESTDIR)
	xsltproc TestSuiteJava.xsl $(IDSDEF) > $(TESTDIR)/TestSuite.java

$(TESTDIR)/TestSuite.class:  $(TESTDIR)/TestSuite.java
	javac $(TESTDIR)/TestSuite.java
	
test: $(TESTDIR)/TestSuite.class	
	java -cp $(TESTDIR):${CLASSPATH} TestSuite 
