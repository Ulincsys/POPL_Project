package parser;
import java.nio.CharBuffer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import java.util.Scanner;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.gui.TestRig;
import parser.Python3Parser;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner;
        if (args.length > 0) {
            File file = new File(args[0]);
            try {
                scanner = new Scanner(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return;
            }
        } else {
            scanner = new Scanner(System.in);
        }
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

        //Outputs:
        //Output tree string to file (src: https://www.geeksforgeeks.org/redirecting-system-out-println-output-to-a-file-in-java/)
        PrintStream output = new PrintStream(new File("tree.txt"));
        PrintStream console = System.out;
        System.setOut(output);
        System.out.println(result.toGraphvizDot());
        System.setOut(console);
        System.out.println(result.toGraphvizDot());
        //Export Graphviz to SVG
        //System.err.println(result.toGraphvizDot());


        //Visualizations: grun/TestRig included w/ antlr
        //Source: https://www.antlr.org/api/JavaTool/org/antlr/v4/gui/TestRig.html#TestRig(java.lang.String[])
/*
        try{
            System.out.println("Loading visualizations...");
//            System.out.println("currently in directory " + System.getProperty("user.dir"));
            //We need to run TestRig/grun in the same directory as the compiled Lexer & Parser classes
            String grammarFileName = "Python3";
            String startRuleName = " r ";
            String flags = "-gui";
            String[] params = {grammarFileName, startRuleName, flags};
            TestRig viz = new TestRig(params); //Look into this
            //viz.process(lexer, Python3Parser.class, parser, CharStreams.fromString(new String(buf, 0, chars_read)));
            viz.process();
        }
        catch(Exception e) {
            System.out.println("Exception thrown for visualizations: " + e);
        }
*/
    }
}
