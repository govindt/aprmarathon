#
# Makefile
#
# Project Name Project
#
# Author: Govind Thirumalai
#
#

include $(JAVA_DEV_ROOT)/makefiles/Makefile.include

#CLASSES  = $(patsubst %.java,$(DISTDIR)/classes/%.class,$(wildcard *.java))
CLASSES = $(DISTDIR)/classes/AddUser.class \
	$(DISTDIR)/classes/HttpClientPost.class \
	$(DISTDIR)/classes/UploadSpreadSheet.class \
	$(DISTDIR)/classes/CssMenu.class \

PROPERTIES  = $(patsubst %.properties,$(DISTDIR)/classes/%.properties,$(wildcard *.properties))
CACHE_CONFIG  = $(patsubst %.ccf,$(DISTDIR)/classes/%.ccf,$(wildcard *.ccf))
LOG_CONFIG  = $(patsubst %.logging,$(DISTDIR)/classes/%.logging,$(wildcard *.logging))
TLD_FILES  = $(patsubst %.tld,$(DISTDIR)/classes/%.tld,$(wildcard *.tld))

$(DISTDIR)/classes/%.class :: %.java
	$(JAVAC) -classpath "$(CLASSPATH)" -d "$(LOCAL_CLASS_DIR)" $<

$(DISTDIR)/classes/%.properties :: %.properties
	$(COPY) $< $@

$(DISTDIR)/classes/%.ccf :: %.ccf
	$(COPY) $< $@

$(DISTDIR)/classes/%.logging :: %.logging
	$(COPY) $< $@

$(DISTDIR)/classes/%.tld :: %.tld
	$(COPY) $< $@

all: $(CLASSES) $(PROPERTIES) $(CACHE_CONFIG) $(LOG_CONFIG) $(TLD_FILES)

clean:
	rm -rf $(CLASSES) $(PROPERTIES) $(CACHE_CONFIG) $(LOG_CONFIG) $(TLD_FILES)
