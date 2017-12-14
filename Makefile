#***************************************************************************
#
#  Project Name Project - Main Makefile
#  Author : Govind Thirumalai 
#
#***************************************************************************
include $(JAVA_DEV_ROOT)/makefiles/Makefile.include

ifndef JAVAC
  ifdef JAVA_HOME
    JDKBIN=$(JDK)/bin/
  endif
  ifeq ($(DEBUG), 1)
    JAVAC=$(JDKBIN)javac -g -classpath "$(CLASSPATH)"
  else
    JAVAC=$(JDKBIN)javac -O -classpath "$(CLASSPATH)"
  endif
endif

twtools_all : all deploy

all: $(DEST_DIRS)
	@for d in $(SOURCE_DIRS); do                                      \
		if test -d $(BASEDIR)/$$d; then                           \
			set -e;                                           \
			cd $(BASEDIR)/$$d; $(MAKE) $@; cd $(BASEDIR)/..;  \
			set +e;                                           \
		else                                                      \
			echo "Skipping non-directory $(BASEDIR)/$$d...";  \
		fi;                                                       \
	done


include $(JAVA_DEV_ROOT)/makefiles/Makefile.rules

clean:
	echo $(DISTDIR)
	$(RMDIR) $(DISTDIR) $(SCRIPTS_DIST_DIR)

deploy: 
	cd $(CLASS_DIR); $(JAR) cf app.jar app
	cd $(CLASS_DIR); $(JAR) cf core.jar core
	$(MKDIR) -p $(DEPLOY_DIR)/aprmarathon
	$(RMDIR) $(DEPLOY_DIR)/aprmarathon/jsp
	$(COPY) -r $(PUBLIC_HTML_DIST_DIR) $(DEPLOY_DIR)/aprmarathon/jsp
	$(RMDIR) $(DEPLOY_DIR)/aprmarathon/WEB-INF/classes
	$(RMDIR) $(DEPLOY_DIR)/aprmarathon/WEB-INF/lib/app.jar
	$(RMDIR) $(DEPLOY_DIR)/aprmarathon/WEB-INF/lib/app
	$(RMDIR) $(DEPLOY_DIR)/aprmarathon/WEB-INF/lib/core.jar
	$(RMDIR) $(DEPLOY_DIR)/aprmarathon/WEB-INF/lib/core
	$(MKDIR) $(DEPLOY_DIR)/aprmarathon/WEB-INF/classes
	$(COPY) $(CLASS_DIR)/*.class $(DEPLOY_DIR)/aprmarathon/WEB-INF/classes
	$(COPY) $(CLASS_DIR)/*.properties $(DEPLOY_DIR)/aprmarathon/WEB-INF/classes
	$(COPY) $(CLASS_DIR)/*.tld $(DEPLOY_DIR)/aprmarathon/WEB-INF/classes
	$(COPY) $(CLASS_DIR)/*.ccf $(DEPLOY_DIR)/aprmarathon/WEB-INF/classes
	$(COPY) $(CLASS_DIR)/*.jar $(DEPLOY_DIR)/aprmarathon/WEB-INF/lib
	$(MOVE) -f $(DEPLOY_DIR)/aprmarathon/WEB-INF/classes/menu.tld $(DEPLOY_DIR)/aprmarathon/WEB-INF

release: all
	$(MKDIR) $(DEPLOY_DIR)/aprmarathon/WEB-INF/lib
	@for d in $(JARS_3RDPARTY); do                                    \
		if test ! -f $(DEPLOY_DIR)/aprmarathon/WEB-INF/lib/$$d; then       \
			$(COPY) $(JAR_3RDPARTY_DIR)/$$d $(DEPLOY_DIR)/aprmarathon/WEB-INF/lib;  \
		fi;                                                       \
	done
	$(RM) $(CATALINA_HOME)/logs/*;
	$(RM) $(CATALINA_HOME)/temp/*;
	$(RMDIR) $(CATALINA_HOME)/work/*;
	$(MKDIR) $(JAVA_DEV_ROOT)/cd;
	(cd $(JAVA_DEV_ROOT); $(JAVA_HOME)/bin/jar cvf $(JAVA_DEV_ROOT)/cd/aprmarathon.jar ./data ./bin jakarta-tomcat)
