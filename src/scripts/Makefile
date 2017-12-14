TOP = ../..

DEV_SCRIPTS =./generate.sh \
	./genui.sh \
	./move_generated.sh \
	./diff_generated.sh \
	./setup_ws.sh \
	./env.sh \
	./env.csh \
	./update_passwd.sh \

SCRIPTS = $(TOP)/bin/table_export \
	$(TOP)/bin/table_import \
	$(TOP)/bin/create_sequence \
	$(TOP)/bin/create_tables.sql \
	$(TOP)/bin/drop_tables.sql \
	$(TOP)/bin/mysql_create_tables.sql \
	$(TOP)/bin/mysql_drop_tables.sql \
	$(TOP)/bin/setup \
	$(TOP)/bin/run_java \
	$(TOP)/bin/upload_to_db \
	$(TOP)/bin/post_to_url \


$(TOP)/bin/% :: %.sh
	mkdir -p $(TOP)/bin
	cp $< $@
	chmod u+w $@
	chmod +x $@

$(TOP)/bin/%.sql :: %.sql
	mkdir -p $(TOP)/bin
	cp $< $@

all: $(SCRIPTS)

clean: 
	rm -f $(SCRIPTS)
