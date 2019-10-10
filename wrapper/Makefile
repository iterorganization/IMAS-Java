# -*- makefile -*- #
include ../../Makefile.common

# Library interface number (used as soname suffix)
# If any interfaces have been added, removed, or changed since the last update,
# increment this number. Do not increment if it is certain the changes retain
# ABI compatibility. This may be possible if the changes are only in the
# implementation and do not change any function signatures or data structures.
# N.B. this number is not tied to the AL major version number whatsoever.
SO_NUM=4

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
ifneq ($(SYSTEM),MacOS)
CXX=g++
else
CXX=clang++
endif
CFLAGS=-std=c11 -pedantic -Wall -fPIC -O0 ${DBGFLAGS}
CXXFLAGS=-std=c++11 -pedantic -Wall -fPIC -O0  -fno-inline-functions ${DBGFLAGS}
LDF=gfortran -lc -lstdc++
endif

#-------------- Options for JNI ----------------

COMMON_OBJECTS= build/imasjava_wrapper_LowLevel.o
CPPSRC= imasjava_wrapper_LowLevel.cpp

# Include OS-specific Makefile, if exists.
ifneq (,$(wildcard Makefile.$(SYSTEM)))
include Makefile.$(SYSTEM)
else
sources_install install uninstall:
	$(error No Makefile.$(SYSTEM) found for this system: $(UNAME_S))
endif

all: $(TARGETS)
sources:
sources_install: $(wildcard *.c *.h) | $(datadir)/src/javainterface/wrapper
	$(INSTALL_DATA) $^ $(datadir)/src/javainterface/wrapper

$(libdir) $(includedir) $(datadir)/src/javainterface/wrapper ./build ./lib:
	$(mkdir_p) $@

install: all sources_install $(addsuffix _install,$(TARGETS)) | $(includedir)
#	$(INSTALL_DATA) imasjava_wrapper_LowLevel.h $(includedir)

clean:
	$(RM) -r ./build ./lib  *.d

# Creates dependency files
%.d: %.cpp
	@set -e; rm -f $@; \
	$(CXX) -MM $(CXXFLAGS) $(INCLUDES) $< > $@.$$$$; \
	sed 's,\($*\)\.o[ :]*,\1.o $@ : ,g' < $@.$$$$ > $@; \
	$(RM) $@.$$$$

# Includes all dependency files
-include $(CPPSRC:.cpp=.d)

build/%.o: %.cpp | ./build
	$(CXX) $(CXXFLAGS) $(INCLUDES) -o $@ -c $<


