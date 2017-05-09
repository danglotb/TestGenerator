package fr.inria.testgenerator.instrumentor;

import spoon.Launcher;
import spoon.reflect.code.*;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.List;

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
            System.out.println("Could not find the target statement");
            throw new RuntimeException(e);
        }

        final CtBlock targetBlock = targetStatement.getParent(CtBlock.class);
        final List<CtBlock> blocks = method.getElements(new TypeFilter<>(CtBlock.class));
        final int indexTargetBlock = blocks.indexOf(targetBlock);

        blocks.forEach(ctBlock ->
                ctBlock.insertBegin(
                        method.getParent().getFactory().createCodeSnippetStatement(
                                "fitness = Math.min(fitness, " + Math.abs(indexTargetBlock - blocks.indexOf(ctBlock)) + ")"
                        )
                )
        );
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
    }

    public static void main(String[] args) {
        Launcher launcher = new Launcher();
        launcher.getEnvironment().setAutoImports(false);
        launcher.getEnvironment().setCommentEnabled(true);

        launcher.addInputResource("docs/BinarySearch.java");
        launcher.buildModel();

        final CtClass<Object> originalClass = launcher.getFactory().Class().get("eu.fbk.se.tcgen2.BinarySearch");
        final CtClass<?> clazz = originalClass.clone();
        clazz.setSimpleName(clazz.getSimpleName() + "_INSTR_43");
        originalClass.getPackage().addType(clazz);
        insertFitnessField(clazz);
        insertFitnessAssignement(43, clazz.getMethodsByName("search").get(0));//TODO we could parametized this, but w/e

        launcher.prettyprint();
    }

}
