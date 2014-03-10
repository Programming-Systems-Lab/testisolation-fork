TEX_FILES=$(shell latex-process-inputs -list main.tex)

all: main.pdf embedfont1 cleanmain checkfont

main.pdf: ${TEX_FILES} references.bib
	pdflatex main.tex
	bibtex main
	pdflatex main.tex
	pdflatex main.tex

TAGS: tags
tags:
	etags `latex-process-inputs -list main.tex`

clean:
	rm -f main.aux main.bbl main.log main.pdf TAGS

cleanmain:
	rm main.pdf

checkfont:
	pdffonts main-final.pdf

#embedfont:
#	ps2pdf -dSubsetFonts=true -dEmbedAllFonts=true -dCompatibilityLevel=1.4 -dPDFSETTINGS=/prepress -sPAPERSIZE=letter main.ps 

embedfont1:
	gs -q -dSAFER -dNOPAUSE -dBATCH -sDEVICE=pdfwrite -sOutputFile=main-final.pdf -dCompatibilityLevel=1.5 -dPDFSETTINGS=/prepress -c .setpdfwrite -f main.pdf
