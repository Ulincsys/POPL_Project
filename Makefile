build: clean
	mvn package

clean:
	mvn clean

run: build
	java -jar target/parser*.jar

test: build
	java -jar target/parser*.jar src/main/tests/python_test_code.py
