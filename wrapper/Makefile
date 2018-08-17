# -*- makefile -*- #
include ../../Makefile.common

SHELL=/bin/sh

## Adding DEBUG=yes to make command to print additional debug info
DBGFLAGS= -g
ifeq (${DEBUG},yes)
DBGFLAGS+= -DDEBUG
endif
ifeq (${STOPONEXCEPT},yes)
DBGFLAGS+= -DSOE
endif


ifeq "$(strip $(CC))" "icc"
 ## intel compiler should be >= 13 to meet C++11 requirement
 CXX=icpc
 CFLAGS=-std=c99 -Wall -fPIC -O0 -shared-intel ${DBGFLAGS}
 CXXFLAGS=-std=c++11 -pedantic -Wall -fPIC -O0 -fno-inline-functions -shared-intel ${DBGFLAGS}
 LDF=ifort -lc -lstdc++ 
else
 CXX=g++
 CFLAGS=--std=c99 --pedantic -Wall -fPIC -O0 ${DBGFLAGS}
 CXXFLAGS=--std=c++11 --pedantic -Wall -fPIC -O0  -fno-inline-functions ${DBGFLAGS}
 LDF=gfortran -lc -lstdc++ 
endif


#-------------- Options for JNI ----------------


 INCLUDES = -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/linux -I../../lowlevel
 LIBS= -L$(JAVA_HOME)/jre/lib/amd64 -L../../lowlevel  -limas
 COMMON_OBJECTS= build/imasjava_wrapper_LowLevel.o
 CPPSRC= imasjava_wrapper_LowLevel.cpp

TARGETS = libimas-java-binding.so libimas-java-binding.a


all: $(TARGETS) 
sources:
sources_install: $(wildcard *.c *.h)
	$(mkdir_p) $(datadir)/src/javainterface/wrapper
	$(INSTALL_DATA) $^ $(datadir)/src/javainterface/wrapper

install: all sources_install
	$(mkdir_p) $(libdir) $(includedir) 
	cd lib && for OBJECT in *.so; do \
		$(INSTALL_DATA) -T $$OBJECT $(libdir)/$$OBJECT.$(IMAS_MAJOR).$(IMAS_MINOR).$(IMAS_MICRO); \
	   	ln -svfT $$OBJECT.$(IMAS_MAJOR).$(IMAS_MINOR).$(IMAS_MICRO) $(libdir)/$$OBJECT.$(IMAS_MAJOR).$(IMAS_MINOR); \
	   	ln -svfT $$OBJECT.$(IMAS_MAJOR).$(IMAS_MINOR).$(IMAS_MICRO) $(libdir)/$$OBJECT.$(IMAS_MAJOR); \
	   	ln -svfT $$OBJECT.$(IMAS_MAJOR).$(IMAS_MINOR).$(IMAS_MICRO) $(libdir)/$$OBJECT; \
	done
#	$(INSTALL_DATA) imasjava_wrapper_LowLevel.h $(includedir)



clean: 
	$(RM) -r ./build ./lib  *.d



# Creates dependency files
%.d: %.cpp
	@set -e; rm -f $@; \
	$(CXX) -MM $(CXXFLAGS) $(INCLUDES) $< > $@.$$$$; \
	sed 's,\($*\)\.o[ :]*,\1.o $@ : ,g' < $@.$$$$ > $@; \
	rm -f $@.$$$$

# Includes all dependency files
-include $(CPPSRC:.cpp=.d)


build/%.o: %.cpp 
	@mkdir -p ./build
	$(CXX) $(CXXFLAGS) $(INCLUDES) -o $@ -c $< 
 



# dynamic library
libimas-java-binding.so: $(COMMON_OBJECTS) 	
	$(mkdir_p) ./lib 
	$(CXX) -g -o lib/$@ -Wl,-z,defs -shared -Wl,-soname,$@.$(IMAS_MAJOR).$(IMAS_MINOR) $^ $(LIBS)

# static library
libimas-java-binding.a: $(COMMON_OBJECTS) 
	$(mkdir_p) ./lib 
	$(AR) rvs lib/$@ $^

