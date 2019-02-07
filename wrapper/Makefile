# -*- makefile -*- #
include ../../Makefile.common

# Library interface number (used as soname suffix)
# If any interfaces have been added, removed, or changed since the last update,
# increment this number. Do not increment if it is certain the changes retain
# ABI compatibility. This may be possible if the changes are only in the
# implementation and do not change any function signatures or data structures.
# N.B. this number is not tied to the AL major version number whatsoever.
SO_NUM=4

export IMAS_PREFIX=$(UAL)

ifeq (,$(wildcard $(IMAS_PREFIX)))
$(error $$IMAS_PREFIX is unset)
endif

SHELL=/bin/sh

## Adding DEBUG=yes to make command to print additional debug info
DBGFLAGS= -g
ifeq (${DEBUG},yes)
DBGFLAGS+= -DDEBUG
endif
ifeq (${STOPONEXCEPT},yes)
DBGFLAGS+= -DSOE
endif

# Trick from Python: add links lib, include to lowlevel
LN_LIB:=$(shell cd $(UAL) && test ! -d lib && $(LN_S) lowlevel lib)
LN_INC:=$(shell cd $(UAL) && test ! -d include && $(LN_S) lowlevel include)


ifeq "$(strip $(CC))" "icc"
## intel compiler should be >= 13 to meet C++11 requirement
CXX=icpc
CFLAGS=-std=c99 -Wall -fPIC -O0 -shared-intel ${DBGFLAGS}
CXXFLAGS=-std=c++11 -pedantic -Wall -fPIC -O0 -fno-inline-functions -shared-intel ${DBGFLAGS}
LDF=ifort -lc -lstdc++
else
CXX=g++
CFLAGS=--std=c11 --pedantic -Wall -fPIC -O0 ${DBGFLAGS}
CXXFLAGS=--std=c++11 --pedantic -Wall -fPIC -O0  -fno-inline-functions ${DBGFLAGS}
LDF=gfortran -lc -lstdc++
endif


#-------------- Options for JNI ----------------

ifneq ("no","$(strip $(SYS_WIN))")
INCLUDES= -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/win32 -I$(IMAS_PREFIX)/include
LIBS= -L$(JAVA_HOME)/jre/lib/amd64 -L$(IMAS_PREFIX)/lib
LIBS+= $(IMAS_PREFIX)/lib/libimas.lib
TARGETS= lib/libimas-java-binding.dll lib/libimas-java-binding.lib
CFLAGS+= -DWIN32
CXXFLAGS+= -DWIN32
else
INCLUDES= -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/linux -I$(IMAS_PREFIX)/include
LIBS= -L$(JAVA_HOME)/jre/lib/amd64 -L$(IMAS_PREFIX)/lib -limas
TARGETS= lib/libimas-java-binding.so lib/libimas-java-binding.a
endif
COMMON_OBJECTS= build/imasjava_wrapper_LowLevel.o
CPPSRC= imasjava_wrapper_LowLevel.cpp


ifneq ("no","$(strip $(IMAS_MDSPLUS))")
ifneq ("no","$(strip $(SYS_WIN))")
LIBS+= $(MDSPLUS_DIR)/lib/XTreeShr.a
LIBS+= $(MDSPLUS_DIR)/lib/MdsObjectsCppShr.a
LIBS+= $(MDSPLUS_DIR)/lib/TdiShr.a
LIBS+= $(MDSPLUS_DIR)/lib/TreeShr.a
LIBS+= $(MDSPLUS_DIR)/lib/MdsIpShr.a
LIBS+= $(MDSPLUS_DIR)/lib/MdsShr.a
LIBS+= -lxml2 -lws2_32 -ldl -liphlpapi
else
#LIBS+= -L$(MDSPLUS_DIR)/lib
#LIBS+= -lTreeShr -lTdiShr -lMdsShr -lMdsIpShr -lXTreeShr
endif
endif

ifneq ("no","$(strip $(IMAS_UDA))")
ifneq ("no","$(strip $(SYS_WIN))")
LIBS+= -L$(UDA_HOME)/lib
LIBS+= $(UDA_HOME)/lib/libuda_cpp.a
LIBS+= $(UDA_HOME)/lib/libportablexdr.a
LIBS+= -lws2_32 -lssl -lcrypto
else
#LIBS+= `pkg-config --libs uda-cpp`
#LIBS+= -lssl -lcrypto
endif
endif
ifneq ("no","$(strip $(IMAS_HDF5))")
ifneq ("no","$(strip $(SYS_WIN))")
LIBS+= -L$(HDF5_HOME)/lib
LIBS+= $(HDF5_HOME)/lib/libhdf5.a -ldl -lz
else
#LIBS+= -L$(HDF5_HOME)/lib
#LIBS+= -lhdf5 -ldl -lz
endif
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

# Dynamic library
lib/libimas-java-binding-$(DD_GIT_DESCRIBE).so.$(SO_NUM): $(COMMON_OBJECTS) | ./lib
	$(CXX) -g -o $@ -Wl,-z,defs -shared -Wl,-soname,$@ $^ $(LIBS)
lib/libimas-java-binding-$(DD_GIT_DESCRIBE).so: %:%.$(SO_NUM)
	$(LN_S) $(<F) $@
lib/libimas-java-binding.so:lib/libimas-java-binding-$(DD_GIT_DESCRIBE).so
	$(LN_S) $(<F) $@
lib/libimas-java-binding.so_install: %.so_install:%-$(DD_GIT_DESCRIBE).so.$(SO_NUM) | $(libdir)
	$(INSTALL_DATA) $< $(libdir)
	$(LN_S) $(<F) $(libdir)/$(*F)-$(DD_GIT_DESCRIBE).so
	$(LN_S) $(<F) $(libdir)/$(*F).so

# Static library
lib/libimas-java-binding-$(DD_GIT_DESCRIBE).a: $(COMMON_OBJECTS) | ./lib
	$(AR) rvs $@ $^
lib/libimas-java-binding.a:lib/libimas-java-binding-$(DD_GIT_DESCRIBE).a
	$(LN_S) $(<F) $@
lib/libimas-java-binding.a_install: %.a_install:%-$(DD_GIT_DESCRIBE).a | $(libdir)
	$(INSTALL_DATA) $< $(libdir)
	$(LN_S) $(<F) $(libdir)/$(*F).a

# Windows dynamic library
lib/libimas-java-binding.dll: $(COMMON_OBJECTS) | ./lib
	$(CXX) -g -o $@ -shared -Wl,-soname,$@.$(SO_NUM) -Wl,--out-implib,$@.lib $^ $(LIBS)
lib/libimas-java-binding.dll_install: %_install:%
	# FIXME: No installation defined for $@

# Windows static library
lib/libimas-java-binding.lib: $(COMMON_OBJECTS) | ./lib
	$(AR) rcvsu $@ $^
	ranlib $@
lib/libimas-java-binding.lib_install: %_install:%
	# FIXME: No installation defined for $@
