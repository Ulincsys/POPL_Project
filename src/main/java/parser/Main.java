package parser;
import java.nio.CharBuffer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.Scanner;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.gui.TestRig;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\\A"); // https://community.oracle.com/tech/developers/discussion/2608149/how-to-convert-input-stream-to-string
        // char[] buf = new char[1024];
        // int chars_read = 0;
        // InputStreamReader reader = new InputStreamReader(System.in);
        // int temp;
        // while ((temp = reader.read(buf, chars_read, 1024-chars_read)) > 0) chars_read += temp;
        String input = scanner.next();
        System.out.format("Chars read: %d\n", input.length());
//        CalculatorLexer lexer = new CalculatorLexer(CharStreams.fromString("3 * 4 + 5"));
        Python3Lexer lexer = new Python3Lexer(CharStreams.fromString(input));
        Python3Parser parser = new Python3Parser(new CommonTokenStream(lexer));
        ParseTree tree = parser.parse();
        GraphVisitor visitor = new GraphVisitor();
        GraphNode result = visitor.visit(tree);
        System.out.println(tree.toString());
        System.err.println(result.toGraphvizDot());

        //Visualizations: grun/TestRig included w/ antlr
        //Source: https://www.antlr.org/api/JavaTool/org/antlr/v4/gui/TestRig.html#TestRig(java.lang.String[])
/*
        try{
            System.out.println("Loading visualizations...");
            String grammarFileName = "Python3";
            String startRuleName = " r ";
            String flags = "-gui";
            String[] params = {grammarFileName, startRuleName, flags};
            TestRig viz = new TestRig(params); //Look into this
            viz.process();
        }
        catch(Exception e) {
            System.out.println("Exception thrown for visualizations: " + e);
        }
*/
    }
}
