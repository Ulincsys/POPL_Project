package hello;
import java.nio.CharBuffer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class HelloWorld {
    public static void main(String[] args) throws IOException {
{
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
{
        char[] buf = new char[1024];
        int chars_read = 0;
        InputStreamReader reader = new InputStreamReader(System.in);
        int temp;
        while ((temp = reader.read(buf, chars_read, 1024-chars_read)) > 0) chars_read += temp;
        System.out.println("Chars read: " + chars_read);
//        CalculatorLexer lexer = new CalculatorLexer(CharStreams.fromString("3 * 4 + 5"));
        IndentLexer lexer = new IndentLexer(CharStreams.fromString(new String(buf, 0, chars_read)));
        IndentParser parser = new IndentParser(new CommonTokenStream(lexer));
        ParseTree tree = parser.parse();
        System.out.println(tree.toString());
}
    }
}
