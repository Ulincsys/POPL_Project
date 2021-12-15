build: clean
	mvn package

clean:
	mvn clean

run: build
	java -jar target/parser*.jar
