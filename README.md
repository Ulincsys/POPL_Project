# Mizzou CS4450: PoPL Fall 2021 Python Parser Project

Sean Brennan, John McGinness, Zachary Sample, James Tompkins

## Getting Started

Our project is a parser designed to parse Python3 syntax. It does not cover all parts of Python3's lexical analysis, just the parts required for the project that are in the `python_test_code.py` test code provided (so no floats, etc). 

### Java

The very first thing you will want to do is ensure you have [Java](https://www.java.com/en/download/) installed on your machine.

### ANTLR Installation & How to Use

Following the [official ANTLR documentation](https://github.com/antlr/antlr4/blob/master/doc/getting-started.md), start by installing ANTLR on your machine. The latest version as of writing this documentation is `4.9.3`.
After installation, we can now run the ANTLR parsing tool! Grammar files have the extension `*.g4` and to give a general idea of the syntax/form of a grammar file, we included the `Hello.g4` grammar file provided in their documentation for testing. Just like it's stated in the "A First Example" portion of the documentation linked prior, you can run the ANTLR tool on a grammar file in the command line by typing `antlr4 Grammer.g4`. This will generate the lexer, parser, etc files. We now need to compile these Java files. You can call the Java compiler from the command line like so: `javac *.java`. Note that this will compile every `.java` file present in the directory.

### Building w/ Maven

First, ensure you have [Maven installed](https://maven.apache.org/install.html). Then to manually compile everything, run `maven clean`, `mvn package` to install the dependencies and build the project, then `java -jar target/parser*.jar`. Or if you want to do it the easy way, John wrote a `makefile` and you can just run `make run` in the root directory to do this all automatically instead. Note: on Windows you need to make sure it's properly installed first.

## Project Requirements (Grammar File `Python3.g4`)

- [x] if/else blocks
  - Line 98
- [x] Variable definitions
  - Line 134
- [x] while & for loops
  - Line 94 & 95
- [x] Arithmetic operators (+, -, *, /, %, ^)
  - Line 113 & 114
- [x] Assignment operators (=, +=, -=, *=, /=, ^=, %=)
  - Line 99
- [x] Conditional statements (<, <=, >, >=, ==, !=, and, or, not)
  - Line 115
- [x] Support for comments
  - Line 154

## Bonus (Java File `Main.java`)

- [ ] Syntax error message
- [x] Visualization of parse tree (Graphviz)
  - Originally Sean was looking into using the TestRig/grun feature that's built into ANTLR, but we ended up opting for Graphviz instead with Zach leading its development.

## How to use/run the parser

After completing the "Getting Started" section, run `make run` in the terminal in the root directory of the project. You will then be prompted to enter the input which can be terminated with `ctrl-D` on Unix or `ctrl-Z + Enter Key` on Windows.

### Demo Video

[![Demo Video](https://avatars0.githubusercontent.com/u/80584?s=280&v=4)](https://www.youtube.com/watch?v=dQw4w9WgXcQ)

## Resources

[Java](https://www.java.com/en/)
[ANTLR](https://www.antlr.org/)
[ANTLR GitHub](https://github.com/antlr/antlr4)
[Python3 Lexical Analysis](https://docs.python.org/3/reference/lexical_analysis.html)
[Python3 Expressions](https://docs.python.org/3/reference/expressions.html)
[Microsoft Regular Expressions](https://docs.microsoft.com/en-us/dotnet/standard/base-types/regular-expression-language-quick-reference)
