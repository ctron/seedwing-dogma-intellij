.PHONY: all
all: clean bundle

BASE = ../seedwing-dogma-syntax
TARGET = build/bundle

.PHONY: clean
clean:
	rm -rf $(TARGET)

.PHONY: bundle
bundle:
	mkdir -p $(TARGET)
	cp -v $(BASE)/package.json $(TARGET)/
	cp -av $(BASE)/syntaxes $(TARGET)/
	cp -v $(BASE)/language-configuration.json $(TARGET)/
	rm -f src/main/resources/bundle.zip
	rm -f $(TARGET)/../bundle.zip
	cd $(TARGET) && zip -vr ../bundle.zip .
	cp $(TARGET)/../bundle.zip src/main/resources/bundle.zip
	unzip -l src/main/resources/bundle.zip
