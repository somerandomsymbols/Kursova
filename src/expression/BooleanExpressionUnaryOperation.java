package expression;

public class BooleanExpressionUnaryOperation extends BooleanExpression
{
    public enum UnaryOperation
    {
        not
    }

    private UnaryOperation operation;
    private BooleanExpression argument;

    public BooleanExpressionUnaryOperation(UnaryOperation o, BooleanExpression a)
    {
        this.operation = o;
        this.argument = a;
    }

    public static UnaryOperation getOperation(char c)
    {
        switch (c)
        {
            case '¬': case '!':
                return UnaryOperation.not;
            default:
                return null;
        }
    }

    public static char getOperationChar(UnaryOperation o)
    {
        if (o == null)
            return '?';

        switch (o)
        {
            case not:
                return '¬';
            default:
                return '?';
        }
    }

    @Override
    public String toString()
    {
        String s = "";

        s += getOperationChar(this.operation);

        if (this.argument instanceof BooleanExpressionBinaryOperation)
            s += "(" + this.argument.toString() + ")";
        else
            s += this.argument.toString();

        return s;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof BooleanExpressionUnaryOperation))
            return false;

        return this.operation.equals(((BooleanExpressionUnaryOperation) obj).operation) && this.argument.equals(((BooleanExpressionUnaryOperation) obj).argument);
    }

    @Override
    public int hashCode()
    {
        return this.operation.hashCode() * this.argument.hashCode();
    }

    public UnaryOperation getOperation()
    {
        return this.operation;
    }

    public BooleanExpression getArgument()
    {
        return this.argument;
    }

    @Override
    public BooleanExpressionUnaryOperation copy()
    {
        return new BooleanExpressionUnaryOperation(this.operation, this.argument.copy());
    }
}
