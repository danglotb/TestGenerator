package fr.inria.testgenerator;

import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.List;

/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 08/05/17
 */
public class FitnessAssignmentInjector {

    /*
        The target class should have been processed by FitnessInsertion
     */

    public static void insertFitnessAssignement(int targetLine, CtMethod<?> method) {
        CtStatement targetStatement = null;
        try {
            targetStatement = method.getElements(new TypeFilter<CtStatement>(CtStatement.class) {
                @Override
                public boolean matches(CtStatement element) {
                    return element.getPosition().getLine() == targetLine && super.matches(element);
                }
            }).get(0);
        } catch (Exception e) {
            System.out.println("Could not find the target statement");
            throw new RuntimeException(e);
        }

        final CtBlock targetBlock = targetStatement.getParent(CtBlock.class);
        final List<CtBlock> blocks = method.getElements(new TypeFilter<>(CtBlock.class));
        final int indexTargetBlock = blocks.indexOf(targetBlock);

        blocks.forEach(ctBlock -> {
                    int approachLevel = Math.abs(indexTargetBlock - blocks.indexOf(ctBlock));
                    final CtCodeSnippetStatement snippet = ctBlock.getFactory().createCodeSnippetStatement(
                            "fitness = Math.min(" + "fitness" + "," + approachLevel + ")"
                    );
                    ctBlock.insertBegin(snippet);
                }
        );
    }

}
