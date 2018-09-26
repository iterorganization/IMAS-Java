# -*- makefile -*- #
include ../../Makefile.common

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

ifneq ("no","$(strip $(IMAS_MDSPLUS))")
	ifneq ("no","$(strip $(SYS_WIN))")
		LIBS+= $(MDSPLUS_DIR)/lib/MdsMisc.a
		LIBS+= $(MDSPLUS_DIR)/lib/XTreeShr.a
		LIBS+= $(MDSPLUS_DIR)/lib/MdsObjectsCppShr.a
		LIBS+= $(MDSPLUS_DIR)/lib/MdsIpShr.a
		LIBS+= $(MDSPLUS_DIR)/lib/MdsLib.a
		LIBS+= $(MDSPLUS_DIR)/lib/TdiShr.a
		LIBS+= $(MDSPLUS_DIR)/lib/TreeShr.a
		LIBS+= $(MDSPLUS_DIR)/lib/MdsShr.a
		LIBS+= -lxml2 -lws2_32 -ldl -liphlpapi
	else
		LIBS+= -L$(MDSPLUS_DIR)/lib
		LIBS+= -lTreeShr -lTdiShr -lMdsShr -lMdsIpShr -lXTreeShr
	endif
endif

ifneq ("no","$(strip $(IMAS_UDA))")
	ifneq ("no","$(strip $(SYS_WIN))")
		LIBS+= -L$(UDA_HOME)/lib
		LIBS+= $(UDA_HOME)/lib/libuda_cpp.a
		LIBS+= $(UDA_HOME)/lib/libportablexdr.a
		LIBS+= -lws2_32 -lssl -lcrypto
	else
		LIBS+= `pkg-config --libs uda-cpp`
	endif
endif

ifneq ("no","$(strip $(IMAS_HDF5))")
	ifneq ("no","$(strip $(SYS_WIN))")
		LIBS+= -L$(HDF5_HOME)/lib
		LIBS+= $(HDF5_HOME)/lib/libhdf5.a -ldl -lz
	else
		LIBS+= -L$(HDF5_HOME)/lib
		LIBS+= -hdf5 -ldl -lz
	endif
endif


COMMON_OBJECTS= build/imasjava_wrapper_LowLevel.o
CPPSRC= imasjava_wrapper_LowLevel.cpp

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
	$(mkdir_p)./build
	$(CXX) $(CXXFLAGS) $(INCLUDES) -o $@ -c $<



# dynamic library
lib/libimas-java-binding.so: $(COMMON_OBJECTS) 	
	@$(mkdir_p) ./lib 
	$(CXX) -g -o $@ -Wl,-z,defs -shared -Wl,-soname,$@.$(IMAS_MAJOR).$(IMAS_MINOR) $^ $(LIBS)

# static library
lib/libimas-java-binding.a: $(COMMON_OBJECTS) 
	@$(mkdir_p) ./lib 
	$(AR) rvs $@ $^

# Windows dynamic library
lib/libimas-java-binding.dll: $(COMMON_OBJECTS) 
	@$(mkdir_p) ./lib 
	$(CXX) -g -o $@ -shared -Wl,-soname,$@.$(IMAS_MAJOR).$(IMAS_MINOR) -Wl,--out-implib,$@.lib $^ $(LIBS)

# Windows static library
lib/libimas-java-binding.lib: $(COMMON_OBJECTS) 
	@$(mkdir_p) ./lib 
	$(AR) rcvsu $@ $^
	ranlib $@
