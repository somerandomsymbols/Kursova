package expression;

public abstract class BooleanExpression
{
    @Override
    public abstract String toString();

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();

    private static int skipBrackets(String s, int i)
    {
        for (++i; i < s.length(); ++i)
            if (s.charAt(i) == '(')
                i = skipBrackets(s, i);
            else if (s.charAt(i) == ')')
                return i;

        return s.length();
    }

    private static int lookForChar(String s, int i)
    {
        for (; i < s.length(); ++i)
            if (s.charAt(i) == '(')
                i = skipBrackets(s, i);
            else if (s.charAt(i) != ')')
                return i;

        return s.length();
    }

    public static BooleanExpression valueOf(String string)
    {
        if (string == null || string.isEmpty())
            return null;

        for (var b : BooleanExpressionBinaryOperation.BinaryOperation.values())
            for (int i = lookForChar(string, 0); i < string.length(); i = lookForChar(string, i+1))
                if (b.equals(BooleanExpressionBinaryOperation.getOperation(string.charAt(i))))
                    return new BooleanExpressionBinaryOperation(
                            BooleanExpression.valueOf(string.substring(0, i)),
                            BooleanExpressionBinaryOperation.getOperation(string.charAt(i)),
                            BooleanExpression.valueOf(string.substring(i + 1)));

        if (string.charAt(0) == '(')
            return BooleanExpression.valueOf(string.substring(1,string.length()-1));

        BooleanExpressionUnaryOperation.UnaryOperation u = BooleanExpressionUnaryOperation.getOperation(string.charAt(0));

        if (u != null)
            return new BooleanExpressionUnaryOperation(u,
                    BooleanExpression.valueOf(string.substring(1)));

        if (string.length() == 1)
            return new BooleanExpressionVariable(string.charAt(0));

        return null;
    }

    public abstract BooleanExpression copy();
}
