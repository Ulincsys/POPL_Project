package parser;
import java.nio.CharBuffer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class Main {
    public static void main(String[] args) throws IOException {
        char[] buf = new char[1024];
        int chars_read = 0;
        InputStreamReader reader = new InputStreamReader(System.in);
        int temp;
        while ((temp = reader.read(buf, chars_read, 1024-chars_read)) > 0) chars_read += temp;
        System.out.println("Chars read: " + chars_read);
//        CalculatorLexer lexer = new CalculatorLexer(CharStreams.fromString("3 * 4 + 5"));
        Python3Lexer lexer = new Python3Lexer(CharStreams.fromString(new String(buf, 0, chars_read)));
        Python3Parser parser = new Python3Parser(new CommonTokenStream(lexer));
        ParseTree tree = parser.parse();
        System.out.println(tree.toString());
    }
}
