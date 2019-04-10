include ../../../Makefile.common

IDSDEF= ../../../xml/IDSDef.xml
TESTDIR = ../testsuite

# Include OS-specific Makefile, if exists.
ifneq (,$(wildcard Makefile.$(SYSTEM)))
include Makefile.$(SYSTEM)
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
