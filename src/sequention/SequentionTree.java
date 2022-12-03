package sequention;

import expression.BooleanExpression;
import expression.BooleanExpressionBinaryOperation;
import expression.BooleanExpressionUnaryOperation;
import expression.BooleanExpressionVariable;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

public class SequentionTree
{
    private Set<Sequention> sequentions;
    private Set<Sequention> terminalSequentions;

    public SequentionTree(Sequention s)
    {
        this.sequentions = new HashSet<>();
        this.terminalSequentions = new HashSet<>();
        this.sequentions.add(s);
    }

    public SequentionTree(Set<Sequention> s)
    {
        this.sequentions = s;
        this.terminalSequentions = new HashSet<>();
    }

    public boolean isTerminal()
    {
        return this.sequentions.isEmpty();
    }

    public boolean isClosed()
    {
        for (var s : this.terminalSequentions)
            if (!s.isClosed())
                return false;

        return true;
    }

    public void clearTerminals()
    {
        this.terminalSequentions.clear();
    }

    public void makeStep(PrintStream out)
    {
        if (!sequentions.isEmpty())
        {
            Sequention s = this.sequentions.stream().findFirst().get();
            this.sequentions.remove(s);

            if (out != null)
                out.print(s + ": ");

            if (s.isTerminal())
            {
                this.terminalSequentions.add(s);

                if (out != null)
                    out.print("не містить пропозиційних зв'язок. Секвенція " + (s.isClosed() ? "замкнена." : "незамкнена."));
            }
            else
            {
                if (!s.getLeftExpressions().isEmpty())
                {
                    BooleanExpression expression = s.getLeftExpressions().stream().findFirst().get();

                    if (expression instanceof BooleanExpressionVariable)
                    {
                        s.getLeftExpressions().remove(expression);
                        s.getLeftVariables().add((BooleanExpressionVariable) expression);

                        if (out != null)
                            out.print(expression + " є пропозиційною змінною.");
                    }
                    else if (expression instanceof BooleanExpressionUnaryOperation)
                    {
                        BooleanExpressionUnaryOperation unaryOperation = (BooleanExpressionUnaryOperation) expression;

                        switch (unaryOperation.getOperation())
                        {
                            case not:
                                s.getLeftExpressions().remove(unaryOperation);
                                s.getRightExpressions().add(unaryOperation.getArgument());

                                if (out != null)
                                    out.print("За ПВ1 маємо: ");

                                break;
                            default:
                                break;
                        }

                        if (out != null)
                            out.print(s);
                    }
                    else if (expression instanceof BooleanExpressionBinaryOperation)
                    {
                        BooleanExpressionBinaryOperation binaryOperation = (BooleanExpressionBinaryOperation) expression;
                        Sequention s1;

                        switch (binaryOperation.getOperation())
                        {
                            case and:
                                s.getLeftExpressions().remove(binaryOperation);
                                s.getLeftExpressions().add(binaryOperation.getLeftArgument());
                                s.getLeftExpressions().add(binaryOperation.getRightArgument());

                                if (out != null)
                                    out.print("За ПВ5 маємо: ");

                                break;
                            case or:
                                s.getLeftExpressions().remove(binaryOperation);
                                s1 = s.copy();
                                s.getLeftExpressions().add(binaryOperation.getLeftArgument());
                                s1.getLeftExpressions().add(binaryOperation.getRightArgument());
                                this.sequentions.add(s1);

                                if (out != null)
                                    out.print("За ПВ3 маємо: " + s1 + ", ");

                                break;
                            case imply:
                                s.getLeftExpressions().remove(binaryOperation);
                                s1 = s.copy();
                                s.getRightExpressions().add(binaryOperation.getLeftArgument());
                                s1.getLeftExpressions().add(binaryOperation.getRightArgument());
                                this.sequentions.add(s1);

                                if (out != null)
                                    out.print("За ПВ7 маємо: " + s1 + ", ");

                                break;
                            case equals:
                                s.getLeftExpressions().remove(binaryOperation);
                                s1 = s.copy();
                                s.getLeftExpressions().add(binaryOperation.getLeftArgument());
                                s.getLeftExpressions().add(binaryOperation.getRightArgument());
                                s1.getRightExpressions().add(binaryOperation.getLeftArgument());
                                s1.getRightExpressions().add(binaryOperation.getRightArgument());
                                this.sequentions.add(s1);

                                if (out != null)
                                    out.print("За ПВ9 маємо: " + s1 + ", ");

                                break;
                            default:
                                break;
                        }

                        if (out != null)
                            out.print(s);
                    }
                }
                else if (!s.getRightExpressions().isEmpty())
                {
                    BooleanExpression expression = s.getRightExpressions().stream().findFirst().get();

                    if (expression instanceof BooleanExpressionVariable)
                    {
                        s.getRightExpressions().remove(expression);
                        s.getRightVariables().add((BooleanExpressionVariable) expression);

                        if (out != null)
                            out.print(expression + " є пропозиційною змінною.");
                    }
                    else if (expression instanceof BooleanExpressionUnaryOperation)
                    {
                        BooleanExpressionUnaryOperation unaryOperation = (BooleanExpressionUnaryOperation) expression;

                        switch (unaryOperation.getOperation())
                        {
                            case not:
                                s.getRightExpressions().remove(unaryOperation);
                                s.getLeftExpressions().add(unaryOperation.getArgument());

                                if (out != null)
                                    out.print("За ПВ2 маємо: ");

                                break;
                            default:
                                break;
                        }

                        if (out != null)
                            out.print(s);
                    }
                    else if (expression instanceof BooleanExpressionBinaryOperation)
                    {
                        BooleanExpressionBinaryOperation binaryOperation = (BooleanExpressionBinaryOperation) expression;
                        Sequention s1;

                        switch (binaryOperation.getOperation())
                        {
                            case and:
                                s.getRightExpressions().remove(binaryOperation);
                                s1 = s.copy();
                                s.getRightExpressions().add(binaryOperation.getLeftArgument());
                                s1.getRightExpressions().add(binaryOperation.getRightArgument());
                                this.sequentions.add(s1);

                                if (out != null)
                                    out.print("За ПВ6 маємо: " + s1 + ", ");

                                break;
                            case or:
                                s.getRightExpressions().remove(binaryOperation);
                                s.getRightExpressions().add(binaryOperation.getLeftArgument());
                                s.getRightExpressions().add(binaryOperation.getRightArgument());

                                if (out != null)
                                    out.print("За ПВ4 маємо: ");

                                break;
                            case imply:
                                s.getRightExpressions().remove(binaryOperation);
                                s.getLeftExpressions().add(binaryOperation.getLeftArgument());
                                s.getRightExpressions().add(binaryOperation.getRightArgument());

                                if (out != null)
                                    out.print("За ПВ8 маємо: ");

                                break;
                            case equals:
                                s.getRightExpressions().remove(binaryOperation);
                                s1 = s.copy();
                                s.getLeftExpressions().add(binaryOperation.getLeftArgument());
                                s.getRightExpressions().add(binaryOperation.getRightArgument());
                                s1.getLeftExpressions().add(binaryOperation.getRightArgument());
                                s1.getRightExpressions().add(binaryOperation.getLeftArgument());
                                this.sequentions.add(s1);

                                if (out != null)
                                    out.print("За ПВ10 маємо: " + s1 + ", ");

                                break;
                            default:
                                break;
                        }

                        if (out != null)
                            out.print(s);
                    }
                }

                this.sequentions.add(s);
            }

            if (out != null)
                out.println();
        }
    }

    @Override
    public String toString()
    {
        if (this.sequentions.isEmpty() && this.terminalSequentions.isEmpty())
            return "{ { ⊢⊣ } }";

        String s = "";

        for (var e : this.sequentions)
            s += ", " + e.toString();

        for (var e : this.terminalSequentions)
        {
            s += ", " + e.toString();

            if (e.isClosed())
                s += "×";
            else
                s += "*";
        }

        return "{ " + s.substring(2) + " }";
    }
}