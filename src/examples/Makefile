#
# @(#)Makefile	1.8 04/03/02
#
# Project Name Project
#
# Author: Govind Thirumalai
#
#

include $(JAVA_DEV_ROOT)/makefiles/Makefile.include

CLASSES  = $(patsubst %.java,$(DISTDIR)/classes/%.class,$(wildcard *.java))
PROPERTIES  = $(patsubst %.properties,$(DISTDIR)/classes/%.properties,$(wildcard *.properties))

$(DISTDIR)/classes/%.class :: %.java
	$(JAVAC) -Xlint -classpath "$(CLASSPATH)" -d "$(LOCAL_CLASS_DIR)" $<

$(DISTDIR)/classes/%.properties :: %.properties
	$(COPY) $< $@

all: $(CLASSES) $(PROPERTIES)

clean:
	rm -rf $(CLASSES) $(PROPERTIES)
