package fr.inria.testgenerator.instrumentor;

import spoon.Launcher;
import spoon.reflect.code.*;
import spoon.reflect.declaration.*;
import spoon.reflect.visitor.filter.TypeFilter;

/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 08/05/17
 */
public class Instrumentor {

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
            throw new RuntimeException(e);
        }

        CtElement currentParent = targetStatement;
        int depth = 0;
        while (! (currentParent instanceof CtMethod) ) {
            if (currentParent instanceof CtBlock) {
                ((CtBlock) currentParent).insertBegin(method.getParent().getFactory().createCodeSnippetStatement(
                        "fitness = Math.min(fitness, " + depth++ + ")"
                ));
            }
            currentParent = currentParent.getParent();
        }
    }

    public static void insertFitnessField(CtClass<?> clazz) {
        final CtField<Integer> fitnessField = clazz.getFactory().createField();
        fitnessField.setSimpleName("fitness");
        fitnessField.addModifier(ModifierKind.PUBLIC);
        fitnessField.addModifier(ModifierKind.STATIC);
        fitnessField.setType(clazz.getFactory().Type().INTEGER_PRIMITIVE);

        final CtFieldRead<Integer> fieldRead = clazz.getFactory().createFieldRead();
        fieldRead.setType(clazz.getFactory().Type().createReference(Integer.class));

        clazz.addField(fitnessField);

        final CtMethod<Void> reset = clazz.getFactory().createMethod();
        reset.setSimpleName("reset");
        reset.addModifier(ModifierKind.PUBLIC);
        reset.addModifier(ModifierKind.STATIC);
        reset.setType(clazz.getFactory().Type().VOID_PRIMITIVE);
        reset.setBody(
                clazz.getFactory().createCodeSnippetStatement("fitness = Integer.MAX_VALUE")
        );
        clazz.addMethod(reset);
    }

    public static void main(String[] args) {
        Launcher launcher = new Launcher();
        launcher.getEnvironment().setAutoImports(false);
        launcher.getEnvironment().setCommentEnabled(true);

        launcher.addInputResource("docs/BinarySearch.java");
        launcher.buildModel();

        final CtClass<?> clazz = launcher.getFactory().Class().get("eu.fbk.se.tcgen2.BinarySearch");
        insertFitnessField(clazz);
        insertFitnessAssignement(43, clazz.getMethodsByName("search").get(0));//TODO we could parametized this, but w/e

        launcher.prettyprint();
    }

}
