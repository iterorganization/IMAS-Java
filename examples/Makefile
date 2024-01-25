ifeq ("no","$(strip $(IMAS_JAVA))")
all sources sources_install sources_uninstall install uninstall clean clean-src check test:
	$(warning "Ignoring examples/java (IMAS_JAVA=no).")
else

ifeq (,$(AL_BUILD))
AL_BUILD=../..
$(warning AL_BUILD variable unset, probably not running 'make al_examples' from the Installer. Assuming AL_BUILD=$(AL_BUILD).)
endif

JAVA_FILES := $(wildcard Test*.java)
CLASS_FILES := $(patsubst %.java, %.class, $(JAVA_FILES))
IMAS_CLASS_FILES := $(AL_BUILD)/javainterface/build

# The java interface requires access to these environment variables:
# These versions need to exist, but don't seem to be used for anything
IMAS_VERSION ?= 0.0.0
AL_VERSION ?= 0.0.0
# IMAS_PREFIX/lib/ is where the libal-java-binding.so is expected by imasjava
IMAS_PREFIX ?= $(realpath $(AL_BUILD)/javainterface/wrapper)
# MDS+ models and lowlevel library path:
ids_path ?= $(realpath $(AL_BUILD)/models/mdsplus)
LD_LIBRARY_PATH := $(AL_BUILD)/lowlevel:$(LD_LIBRARY_PATH)

export IMAS_VERSION AL_VERSION IMAS_PREFIX LD_LIBRARY_PATH ids_path

all: compile

sources sources_install sources_uninstall install uninstall clean-src:

compile: $(CLASS_FILES)

$(CLASS_FILES): CommonValidateUtilities.class

%.class: %.java
	javac -cp $(IMAS_CLASS_FILES):$(CLASSPATH):$(AL_BUILD)/examples/java $<

test: $(CLASS_FILES)
	( \
	for prog in $(patsubst %.class, %, $^);do \
	 ( java -cp .:$(IMAS_CLASS_FILES):$(CLASSPATH):$(AL_BUILD)/examples/java $$prog 2>&1 \
	   || echo "Error: command exited with status $$?" ) \
	 | tee ./$$prog.log && \
	 test 0 -eq $$(grep -c -i -E "fault|error|exception|severe" ./$$prog.log ) && \
	 echo ok $$N - $$prog passed. || echo not ok $$N - $$prog failed. ;\
	 N=$$(($$N+1)) ;\
	done \
	) | tee $@.log
	@(echo Log summary of $@:; grep -E "^ok |^not ok " $@.log) >&2
	@test 0 -eq $$(grep -c -i error $@.log) || exit 1

clean:
	rm *.class

endif # IMAS_JAVA
