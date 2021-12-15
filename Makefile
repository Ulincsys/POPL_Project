
clean:
	mvn clean

build: clean
	mvn package

run: build
	java -jar target/parser*.jar
