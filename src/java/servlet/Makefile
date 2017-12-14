#
# @(#)Makefile	1.5 04/03/02
#
# Project Name Project
#
# Author: Govind Thirumalai
#
#
include $(JAVA_DEV_ROOT)/makefiles/Makefile.include

SOURCE = AppServlet.java

CLASSES = $(DISTDIR)/classes/AppServlet.class

$(DISTDIR)/classes/%.class :: %.java
	$(JAVAC) -classpath "$(CLASSPATH)" -d "$(LOCAL_CLASS_DIR)" $(SOURCE)

all: $(CLASSES)

clean:
	rm -rf $(CLASSES)

