package parser;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;

public class GraphVisitor implements Python3Visitor<GraphNode> {
	@Override
    public GraphNode visit(ParseTree root) {
        return root.accept(this);
    }
    @Override
    public GraphNode visitParse(Python3Parser.ParseContext ctx) {
        List<GraphNode> children = new ArrayList<>();
        for (Python3Parser.StatementContext child : ctx.statement()) {
            if (child instanceof Python3Parser.EmptyStatementContext) {
                // ignore empty statements
            } else {
                GraphNode node = child.accept(this);
                System.out.println(child + " " + child.getClass() + " " + node);
                children.add(node);
            }
        }
        return new GraphNode("root", children);
    }
	@Override
    public GraphNode visitEmptyStatement(Python3Parser.EmptyStatementContext ctx) {
        return new GraphNode("emptyStatement");
    }
	@Override
    public GraphNode visitWhileStatement(Python3Parser.WhileStatementContext ctx) {
        RuleContext body = ctx.statement();
        if (body == null) body = ctx.block();
        
        return new GraphNode(
            "while loop",
            Arrays.asList(
                new GraphNode("condition", ctx.expression().accept(this)),
                new GraphNode("body", body.accept(this))
            )
        );
    }
	@Override
    public GraphNode visitForStatement(Python3Parser.ForStatementContext ctx) {
        RuleContext body = ctx.statement();
        if (body == null) body = ctx.block();
        
        return new GraphNode(
            "while loop",
            Arrays.asList(
                new GraphNode("target", ctx.IDENTIFIER().accept(this)),
                new GraphNode("iterable", ctx.expression().accept(this)),
                new GraphNode("body", body.accept(this))
            )
        );
    }
	@Override
    public GraphNode visitBreakStatement(Python3Parser.BreakStatementContext ctx) {
        return new GraphNode("breakStatement");
    }
	@Override
    public GraphNode visitExpressionStatement(Python3Parser.ExpressionStatementContext ctx) {
        return new GraphNode(
            "expressionStatement",
            ctx.expression().accept(this)
        );
    }
	@Override
    public GraphNode visitIfStatement(Python3Parser.IfStatementContext ctx) {
        List<GraphNode> children = new ArrayList<>();
        
        RuleContext ifbody = ctx.ifbodystat;
        if (ifbody == null) ifbody = ctx.ifbodyblock;
            
        RuleContext elsebody = ctx.elsebodystat;
        if (elsebody == null) elsebody = ctx.elsebodyblock;
        
        children.add(new GraphNode(
            "'if' block",
            Arrays.asList(
                new GraphNode("condition", ctx.ifex.accept(this)),
                new GraphNode("body", ifbody.accept(this))
            )
        ));
        ctx.elifStatement().forEach(elifctx -> children.add(elifctx.accept(this)));
        if (elsebody != null) {
            children.add(new GraphNode(
                "'else' block",
                new GraphNode("body", elsebody.accept(this))
            ));
        }
        
        return new GraphNode(
            "if statement",
            children
        );
    }
	@Override
    public GraphNode visitElifStatement(Python3Parser.ElifStatementContext ctx) {
        RuleContext elifbody = ctx.bodystat;
        if (elifbody == null) elifbody = ctx.bodyblock;
        
        return new GraphNode(
            "'elif' block",
            Arrays.asList(
                new GraphNode("condition", ctx.elifex.accept(this)),
                new GraphNode("body", elifbody.accept(this))
            )
        );
    }
	@Override
    public GraphNode visitAssignmentStatement(Python3Parser.AssignmentStatementContext ctx) {
        return new GraphNode(
            "assignmentStatement",
            Arrays.asList(
                ctx.IDENTIFIER().accept(this),
                tokenHelper(ctx.op),
                ctx.expression().accept(this)
            )
        );
    }
	@Override
    public GraphNode visitBlock(Python3Parser.BlockContext ctx) {
        return new GraphNode(
            "block",
            ctx.statement().stream().map(statement -> statement.accept(this)).collect(Collectors.toList())
        );
    }
	@Override
    public GraphNode visitOrExpression(Python3Parser.OrExpressionContext ctx) {
        return new GraphNode(
            "orExpression",
            Arrays.asList(
                ctx.lhs.accept(this),
                tokenHelper(ctx.op),
                ctx.rhs.accept(this)
            )
        );
    }
	@Override
    public GraphNode visitAndExpression(Python3Parser.AndExpressionContext ctx) {
        return new GraphNode(
            "andExpression",
            Arrays.asList(
                ctx.lhs.accept(this),
                tokenHelper(ctx.op),
                ctx.rhs.accept(this)
            )
        );
    }
	@Override
    public GraphNode visitAddExpression(Python3Parser.AddExpressionContext ctx) {
        return new GraphNode(
            "addExpression",
            Arrays.asList(
                ctx.lhs.accept(this),
                tokenHelper(ctx.op),
                ctx.rhs.accept(this)
            )
        );
    }
	@Override
    public GraphNode visitAtomExpression(Python3Parser.AtomExpressionContext ctx) {
        return new GraphNode(
            "atomExpression",
            ctx.a.accept(this)
        );
    }
	@Override
    public GraphNode visitFunctionCallExpression(Python3Parser.FunctionCallExpressionContext ctx) {
        List<GraphNode> arguments = new ArrayList<>();
        GraphNode functionName = new GraphNode("function name", ctx.IDENTIFIER().accept(this));
        for (Python3Parser.ExpressionContext expression : ctx.expression()) {
            arguments.add(expression.accept(this));
        }
        return new GraphNode(
            "functionCallExpression",
            Arrays.asList(
                functionName,
                new GraphNode(
                    "arguments",
                    arguments
                )
            )
        );
    }
	@Override
    public GraphNode visitNotExpression(Python3Parser.NotExpressionContext ctx) {
        return new GraphNode(
            "notExpression",
            Arrays.asList(
                tokenHelper(ctx.op),
                ctx.rhs.accept(this)
            )
        );
    }
	@Override
    public GraphNode visitComparisonExpression(Python3Parser.ComparisonExpressionContext ctx) {
        return new GraphNode(
            "comparisonExpression",
            Arrays.asList(
                ctx.lhs.accept(this),
                tokenHelper(ctx.op),
                ctx.rhs.accept(this)
            )
        );
    }
	@Override
    public GraphNode visitNegateExpression(Python3Parser.NegateExpressionContext ctx) {
        return new GraphNode(
            "negateExpression",
            Arrays.asList(
                tokenHelper(ctx.op),
                ctx.rhs.accept(this)
            )
        );
    }
	@Override
    public GraphNode visitMulExpression(Python3Parser.MulExpressionContext ctx) {
        return new GraphNode(
            "mulExpression",
            Arrays.asList(
                ctx.lhs.accept(this),
                tokenHelper(ctx.op),
                ctx.rhs.accept(this)
            )
        );
    }
	@Override
    public GraphNode visitIntAtom(Python3Parser.IntAtomContext ctx) {
        return new GraphNode(
            "intAtom",
            ctx.INT().accept(this)
        );
    }
	@Override
    public GraphNode visitStringAtom(Python3Parser.StringAtomContext ctx) {
        return new GraphNode(
            "stringAtom",
            ctx.STRING().accept(this)
        );
    }
	@Override
    public GraphNode visitIdentifierAtom(Python3Parser.IdentifierAtomContext ctx) {
        return new GraphNode(
            "identifierAtom",
            ctx.IDENTIFIER().accept(this)
        );
    }
	@Override
    public GraphNode visitParenAtom(Python3Parser.ParenAtomContext ctx) {
        return new GraphNode(
            "parenAtom",
            Arrays.asList(
                ctx.LPAREN().accept(this),
                ctx.e.accept(this),
                ctx.RPAREN().accept(this)
            )
        );
    }
    
	@Override
    public GraphNode visitChildren(RuleNode node) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
	@Override
    public GraphNode visitErrorNode(ErrorNode node) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
	@Override
    public GraphNode visitTerminal(TerminalNode node) {
        return new GraphNode(
            Python3Lexer.VOCABULARY.getDisplayName(node.getSymbol().getType()),
            new GraphNode(node.getText())
        );
    }
    
    protected GraphNode tokenHelper(Token token) {
        return new GraphNode(
            token.getText()
        );
    }
}
