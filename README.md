# Mizzou CS4450: PoPL Fall 2021 Python Parser Project

## Getting Started

### Java

The very first thing you will want to do is ensure you have [Java](https://www.java.com/en/download/) installed on your machine.

### ANTLR Installation & How to Use

Following the [official ANTLR documentation](https://github.com/antlr/antlr4/blob/master/doc/getting-started.md), start by installing ANTLR on your machine. The latest version as of writing this documentation is `4.9.3`.
After installation, we can now run the ANTLR parsing tool! Grammar files have the extension `*.g4` and to give a general idea of the syntax/form of a grammar file, we included the `Hello.g4` grammar file provided in their documentation for testing. Just like it's stated in the "A First Example" portion of the documentation linked prior, you can run the ANTLR tool on a grammar file in the command line by typing `antlr4 Grammer.g4`. This will generate the lexer, parser, etc files. We now need to compile these Java files. You can call the Java compiler from the command line like so: `javac *.java`. Note that this will compile every `.java` file present in the directory.

### Building w/ Maven

First, ensure you have [Maven installed](https://maven.apache.org/install.html). Then run `mvn package` to install the dependencies and build the project. Then run `java -jar target/original-parser-0.1.0.jar`

## Project Requirements

- [ ] if/else blocks
- [x] Variable definitions
  - `Python.g4` line 125
- [ ] while & for loops
- [ ] Arithmetic operators (+, -, *, /, %, ^)
- [ ] Assignment operators (=, +=, -=, *=, /=, ^=, %=)
- [ ] Conditional statements (<, <=, >, >=, ==, !=, and, or, not)
- [x] Support for comments
  - `Python3.g4` line 146

## Bonus

- [ ] Syntax error message
- [ ] Visualization of parse tree (Graphviz or Grun?)

## Resources

[Java](https://www.java.com/en/)
[ANTLR](https://www.antlr.org/)
[ANTLR GitHub](https://github.com/antlr/antlr4)
[Python3 Lexical Analysis](https://docs.python.org/3/reference/lexical_analysis.html)
[Python3 Expressions](https://docs.python.org/3/reference/expressions.html)
[Microsoft Regular Expressions](https://docs.microsoft.com/en-us/dotnet/standard/base-types/regular-expression-language-quick-reference)
