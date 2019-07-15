include ../../../Makefile.common

IDSDEF= ../../../xml/IDSDef.xml
TESTDIR = ../testsuite

JFLAGS = -g -Xmaxerrs 10 -sourcepath ./src -d ./build

# Include OS-specific Makefile, if exists.
ifneq (,$(wildcard Makefile.$(SYSTEM)))
include Makefile.$(SYSTEM)
endif


CLASSFILES = build/Generator.class build/Helper.class build/Checker.class 

all: test


build:
	$(mkdir_p) $@

clean:
	$(RM) -r $(TESTDIR)

$(TESTDIR)/TestSuite.java: TestSuiteJava.xsl $(IDSDEF)
	$(mkdir_p) $(TESTDIR)
	xsltproc TestSuiteJava.xsl $(IDSDEF) > $(TESTDIR)/TestSuite.java

$(TESTDIR)/TestSuite.class: $(TESTDIR)/TestSuite.java $(CLASSFILES)
	$(JAVAC) -g -Xmaxerrs 10 -sourcepath ./src $(TESTDIR)/TestSuite.java


# Static sources 
build/%.class:src/%.java | build
	$(JAVAC) -classpath $(CLASSPATH) $(JFLAGS) $^

test: $(TESTDIR)/TestSuite.class
	$(JAVA) -cp ./build:$(TESTDIR):${CLASSPATH} TestSuite
