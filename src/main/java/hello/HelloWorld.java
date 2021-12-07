package hello;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class HelloWorld {
    public static void main(String[] args) {
        CalculatorLexer lexer = new CalculatorLexer(CharStreams.fromString("3 * 4 + 5"));
        CalculatorParser parser = new CalculatorParser(new CommonTokenStream(lexer));
        ParseTree tree = parser.parse();
        MyVisitor visitor = new MyVisitor();
//        parser.addParseVisitor(visitor);
//        visitor.visit(tree)
        System.out.println("Hello World: " + visitor.visit(tree));
    }
}
