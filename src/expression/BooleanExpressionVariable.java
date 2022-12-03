package expression;

public class BooleanExpressionVariable extends BooleanExpression
{
    private char name;

    public BooleanExpressionVariable(char n)
    {
        this.name = n;
    }

    @Override
    public String toString()
    {
        return Character.toString(name);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof BooleanExpressionVariable))
            return false;

        return this.name == ((BooleanExpressionVariable) obj).name;
    }

    @Override
    public int hashCode()
    {
        return this.name;
    }

    @Override
    public BooleanExpressionVariable copy()
    {
        return new BooleanExpressionVariable(this.name);
    }
}
