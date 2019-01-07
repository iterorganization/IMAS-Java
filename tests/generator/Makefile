include ../../../Makefile.common

IDSDEF= ../../../xml/IDSDef.xml
TESTDIR = ../testsuite

ifneq ("no","$(strip $(SYS_WIN))")
	JAVA = $(JAVA_HOME)/bin/java
	JAVAC = $(JAVA_HOME)/bin/javac
	JAR = $(JAVA_HOME)/bin/jar
else
	JAVA = java
	JAVAC = javac
	JAR = jar
endif

all: test


clean:
	$(RM) -r $(TESTDIR)

$(TESTDIR)/TestSuite.java: TestSuiteJava.xsl $(IDSDEF)
	$(mkdir_p) $(TESTDIR)
	xsltproc TestSuiteJava.xsl $(IDSDEF) > $(TESTDIR)/TestSuite.java

$(TESTDIR)/TestSuite.class: $(TESTDIR)/TestSuite.java
	$(JAVAC) $(TESTDIR)/TestSuite.java

test: $(TESTDIR)/TestSuite.class
	$(JAVA) -cp $(TESTDIR):${CLASSPATH} TestSuite
