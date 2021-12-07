package hello;

class MyVisitor extends CalculatorBaseVisitor<Integer> {
    @Override
    public Integer visitParse(CalculatorParser.ParseContext ctx) {
        return this.visit(ctx.expression());
    }

    @Override
    public Integer visitAddExpression(CalculatorParser.AddExpressionContext ctx) {
        if (ctx.op.getText().equals("+"))
            return visit(ctx.lhs) + visit(ctx.rhs);
        if (ctx.op.getText().equals("-"))
            return visit(ctx.lhs) - visit(ctx.rhs);
        throw new IllegalArgumentException("unrecognized op: " + ctx.op.getText());
    }

    @Override
    public Integer visitMulExpression(CalculatorParser.MulExpressionContext ctx) {
        if (ctx.op.getText().equals("*"))
            return visit(ctx.lhs) * visit(ctx.rhs);
        if (ctx.op.getText().equals("/"))
            return visit(ctx.lhs) / visit(ctx.rhs);
        throw new IllegalArgumentException("unrecognized op: " + ctx.op.getText());
    }

    @Override
    public Integer visitParenExpression(CalculatorParser.ParenExpressionContext ctx) {
        return visit(ctx.e);
    }

    @Override
    public Integer visitAtomExpression(CalculatorParser.AtomExpressionContext ctx) {
        if (ctx.a instanceof CalculatorParser.IntAtomContext) {
            return Integer.parseInt(((CalculatorParser.IntAtomContext)ctx.a).INT().getText());
        }
        throw new IllegalArgumentException("unrecognized atom.");
    }
/*
    public Integer visitSum(CalculatorParser.SumContext ctx) {
        switch (ctx.getRuleIndex()) {
            case 1:
                return this.visit(ctx.product());
            case 2:
                return this.visit(ctx.product()) + this.visit(ctx.sum());
        }
        throw new NullPointerException(); // todo
    }

    public Integer visitMulExpression(CalculatorParser.ProductContext ctx) {
        switch (ctx.getRuleIndex()) {
            case 1:
                return this.visit(ctx.term());
            case 2:
                return this.visit(ctx.term()) + this.visit(ctx.product());
        }
        System.out.println("" + ctx.getRuleIndex());
        throw new NullPointerException(); // todo
    }

    public Integer visitTerm(CalculatorParser.TermContext ctx) {
        return Integer.parseInt(ctx.INT().getText());
    }
*/
}
