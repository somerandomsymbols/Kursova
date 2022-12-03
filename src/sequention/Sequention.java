package sequention;

import expression.BooleanExpression;
import expression.BooleanExpressionBinaryOperation;
import expression.BooleanExpressionUnaryOperation;
import expression.BooleanExpressionVariable;

import java.util.HashSet;
import java.util.Set;

public class Sequention
{
    private Set<BooleanExpression> leftExpressions, rightExpressions;
    private Set<BooleanExpressionVariable> leftVariables, rightVariables;

    public Sequention(BooleanExpression right)
    {
        this.leftVariables = new HashSet<>();
        this.rightVariables = new HashSet<>();
        this.leftExpressions = new HashSet<>();
        this.rightExpressions = new HashSet<>();
        this.rightExpressions.add(right);
    }

    public Sequention(BooleanExpression left, BooleanExpression right)
    {
        this.leftVariables = new HashSet<>();
        this.rightVariables = new HashSet<>();
        this.leftExpressions = new HashSet<>();
        this.rightExpressions = new HashSet<>();
        this.leftExpressions.add(left);
        this.rightExpressions.add(right);
    }

    public Sequention(Set<BooleanExpression> left, Set<BooleanExpression> right)
    {
        this.leftVariables = new HashSet<>();
        this.rightVariables = new HashSet<>();
        this.leftExpressions = left;
        this.rightExpressions = right;
    }

    public boolean isTerminal()
    {
        return this.leftExpressions.isEmpty() && this.rightExpressions.isEmpty();
    }

    public boolean isClosed()
    {
        for (var v : this.leftVariables)
            if (this.rightVariables.contains(v))
                return true;

        return false;
    }

    public Set<BooleanExpression> getLeftExpressions()
    {
        return this.leftExpressions;
    }

    public Set<BooleanExpression> getRightExpressions()
    {
        return this.rightExpressions;
    }

    public Set<BooleanExpressionVariable> getLeftVariables()
    {
        return this.leftVariables;
    }

    public Set<BooleanExpressionVariable> getRightVariables()
    {
        return this.rightVariables;
    }

    public Sequention copy()
    {
        Set<BooleanExpression> le = new HashSet<>(), re = new HashSet<>();

        for (var e : this.leftExpressions)
            le.add(e.copy());

        for (var e : this.rightExpressions)
            re.add(e.copy());

        Sequention s = new Sequention(le, re);

        for (var e : this.leftVariables)
            s.leftVariables.add(e.copy());

        for (var e : this.rightVariables)
            s.rightVariables.add(e.copy());

        return s;
    }

    @Override
    public String toString()
    {
        if (this.leftVariables.isEmpty() && this.rightVariables.isEmpty() && this.leftExpressions.isEmpty() && this.rightExpressions.isEmpty())
            return "{ ⊢⊣ }";

        String s = "";

        for (var e : this.leftExpressions)
            s += ", ⊢" + e.toString();

        for (var e : this.rightExpressions)
            s += ", ⊣" + e.toString();

        for (var v : this.leftVariables)
            s += ", ⊢" + v.toString();

        for (var v : this.rightVariables)
            s += ", ⊣" + v.toString();

        return "{ " + s.substring(2) + " }";
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Sequention))
            return false;

        return this.leftExpressions.equals(((Sequention) obj).leftExpressions) &&
                this.rightExpressions.equals(((Sequention) obj).rightExpressions) &&
                this.leftVariables.equals(((Sequention) obj).leftVariables) &&
                this.rightVariables.equals(((Sequention) obj).rightVariables);
    }

    @Override
    public int hashCode()
    {
        return this.leftExpressions.hashCode() * this.rightExpressions.hashCode() * this.leftVariables.hashCode() * this.rightVariables.hashCode();
    }
}
