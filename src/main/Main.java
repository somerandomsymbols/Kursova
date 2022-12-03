package main;

import expression.BooleanExpression;
import sequention.Sequention;
import sequention.SequentionTree;

import java.util.HashSet;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        //A&B>C|!(A&B&C)
        //(A>C)>(B|C>(A|B>C))
        //!B|C>!(!A|B)|(!A|C)

        String string = scanner.nextLine();
        BooleanExpression booleanExpression = BooleanExpression.valueOf(string);
        Sequention sequention = new Sequention(booleanExpression);
        SequentionTree st = new SequentionTree(sequention);
        System.out.println(st);

        while(!st.isTerminal())
        {
            st.clearTerminals();
            scanner.nextLine();
            st.makeStep(System.out);
        }

        System.out.println("Секвенційне дерево термінальне і " + (st.isClosed() ? "замкнене. Секвенція виконується." : "незамкнене. Секвенція не виконується."));
    }
}