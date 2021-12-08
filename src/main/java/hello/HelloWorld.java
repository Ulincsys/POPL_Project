package hello;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class HelloWorld {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        CalculatorLexer lexer = new CalculatorLexer(CharStreams.fromString("3 * 4 + 5"));
        CalculatorLexer lexer = new CalculatorLexer(CharStreams.fromString(br.readLine()));
        CalculatorParser parser = new CalculatorParser(new CommonTokenStream(lexer));
        ParseTree tree = parser.parse();
        MyVisitor visitor = new MyVisitor();
//        parser.addParseVisitor(visitor);
//        visitor.visit(tree)
        System.out.println("Hello World: " + visitor.visit(tree));
    }
}
