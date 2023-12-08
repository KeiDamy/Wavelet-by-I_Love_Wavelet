ANT	= env LC_ALL=ja_JP.UTF-8 ant
ARCHIVE	= $(shell basename `pwd`)

all:
	$(ANT) all

clean:
	$(ANT) clean

test:
	$(ANT) test

install:
	$(ANT) install

doc:
	$(ANT) doc

wipe: clean
	@find . -name ".DS_Store" -exec rm {} ";" -exec echo rm -f {} ";"
	(cd ../ ; rm -f ./$(ARCHIVE).zip)

#zip:
#	$(ANT) zip

zip: wipe
	@find . -exec touch -t `date "+%Y%m%d%H%M"` {} \; ; xattr -cr .
	(cd ../ ; zip -r ./$(ARCHIVE).zip ./$(ARCHIVE)/ --exclude='*/.svn/*')
