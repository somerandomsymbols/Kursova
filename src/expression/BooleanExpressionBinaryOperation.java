package expression;

public class BooleanExpressionBinaryOperation extends BooleanExpression
{
    public enum BinaryOperation
    {
        equals,
        imply,
        or,
        and
    }

    private BooleanExpression leftArgument;
    private BooleanExpression rightArgument;
    private BinaryOperation operation;

    public BooleanExpressionBinaryOperation(BooleanExpression a, BinaryOperation o, BooleanExpression b)
    {
        this.leftArgument = a;
        this.operation = o;
        this.rightArgument = b;
    }

    public static BinaryOperation getOperation(char c)
    {
        switch (c)
        {
            case '∧': case '&':
                return BinaryOperation.and;
            case '∨': case '|':
                return BinaryOperation.or;
            case '→': case '>':
                return BinaryOperation.imply;
            case '↔': case '=':
                return BinaryOperation.equals;
            default:
                return null;
        }
    }

    public static char getOperationChar(BinaryOperation o)
    {
        if (o == null)
            return '?';

        switch (o)
        {
            case and:
                return '∧';
            case or:
                return '∨';
            case imply:
                return '→';
            case equals:
                return '↔';
            default:
                return '?';
        }
    }

    @Override
    public String toString()
    {
        String s = "";

        if (leftArgument instanceof BooleanExpressionBinaryOperation &&
                ((BooleanExpressionBinaryOperation) leftArgument).operation.compareTo(this.operation) <= 0)
            s += "(" + this.leftArgument + ")";
        else
            s += this.leftArgument;

        s += getOperationChar(this.operation);

        if (rightArgument instanceof BooleanExpressionBinaryOperation &&
                ((BooleanExpressionBinaryOperation) rightArgument).operation.compareTo(this.operation) < 0)
            s += "(" + this.rightArgument + ")";
        else
            s += this.rightArgument;

        return s;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof BooleanExpressionBinaryOperation))
            return false;

        return this.leftArgument.equals(((BooleanExpressionBinaryOperation) obj).leftArgument) && this.operation.equals(((BooleanExpressionBinaryOperation) obj).operation) && this.rightArgument.equals(((BooleanExpressionBinaryOperation) obj).rightArgument);
    }

    @Override
    public int hashCode()
    {
        return this.operation.hashCode() * this.leftArgument.hashCode() * this.rightArgument.hashCode();
    }

    public BinaryOperation getOperation()
    {
        return this.operation;
    }

    public BooleanExpression getLeftArgument()
    {
        return this.leftArgument;
    }

    public BooleanExpression getRightArgument()
    {
        return this.rightArgument;
    }

    @Override
    public BooleanExpressionBinaryOperation copy()
    {
        return new BooleanExpressionBinaryOperation(this.leftArgument.copy(), this.operation, this.rightArgument.copy());
    }
}
