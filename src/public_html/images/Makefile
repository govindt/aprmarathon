include $(JAVA_DEV_ROOT)/makefiles/Makefile.include

# This is relative to public_html dir.
JSP = images

JSP_SOURCE = $(patsubst %.png,$(DISTDIR)/public_html/images/%.png,$(wildcard *.png)) $(patsubst %.gif,$(DISTDIR)/public_html/images/%.gif,$(wildcard *.gif)) $(patsubst %.jpg,$(DISTDIR)/public_html/images/%.jpg,$(wildcard *.jpg))

RMI_SOURCE =

MAIN =

$(DISTDIR)/public_html/images/%.gif :: %.gif
	$(COPY) $< $@

$(DISTDIR)/public_html/images/%.jpg :: %.jpg
	$(COPY) $< $@

$(DISTDIR)/public_html/images/%.png :: %.png
	$(COPY) $< $@


all: $(JSP_SOURCE)
