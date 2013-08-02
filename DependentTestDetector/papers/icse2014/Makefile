MAIN_FILE=paper

all: $(MAIN_FILE).pdf

$(MAIN_FILE).pdf: $(wildcard *.tex) $(MAIN_FILE).bbl
	@echo "Making $(MAIN_FILE).pdf"
	@if test -f $(MAIN_FILE).aux; then \
	  cp $(MAIN_FILE).aux $(MAIN_FILE).sav; \
	else \
	  : > $(MAIN_FILE).sav; \
	fi; \
	trap 'rm -f $(MAIN_FILE).sav' EXIT INT TERM; \
	while :; do \
	  pdflatex "$(MAIN_FILE)" | grep pages, || exit 1; \
	  cmp --silent $(MAIN_FILE).sav $(MAIN_FILE).aux && break; \
	  echo Rerunning TeX to get cross references right; \
	  cp $(MAIN_FILE).aux $(MAIN_FILE).sav; \
	  bibtex $(MAIN_FILE); \
	done; \
	rm -f $(MAIN_FILE).sav


$(MAIN_FILE).bbl: references.bib $(MAIN_FILE).aux
	bibtex $(MAIN_FILE)

$(MAIN_FILE).aux: $(MAIN_FILE).tex
	pdflatex $(MAIN_FILE)

#This is for submissions that require a PS file.
ps-letter: $(MAIN_FILE).pdf
	latex $(MAIN_FILE)
	dvips -P cmz -t letter -o $(MAIN_FILE).ps $(MAIN_FILE).dvi

ps-a4: $(MAIN_FILE).pdf
	latex $(MAIN_FILE)
	dvips -P cmz -t a4 -o $(MAIN_FILE).ps $(MAIN_FILE).dvi

TAGS: tags
tags:
	etags `latex-process-inputs -list paper.tex`

clean:
	@rm -f *.log
	@rm -f *.aux
	@rm -f *.toc
	@rm -f *.bbl
	@rm -f *.blg
	@rm -f *.maf

realclean: clean
	@rm -f *.pdf
	@rm -f *.ps
	@rm -f *.dvi
	@rm -f *.bib.bak

