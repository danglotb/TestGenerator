package fr.inria.testgenerator;

import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.ModifierKind;

/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 07/05/17
 */
public class FitnessInsertion {

    public static void main(String[] args) {
        System.out.println(run("eu.fbk.se.tcgen2.BinarySearch",
                "src/main/java/eu/fbk/se/tcgen2/BinarySearch.java"));
    }

    public static void insertFitnessField(CtClass<?> clazz) {
        final CtField<Integer> fitnessField = clazz.getFactory().createField();
        fitnessField.setSimpleName("fitness");
        fitnessField.addModifier(ModifierKind.PUBLIC);
        fitnessField.addModifier(ModifierKind.STATIC);
        fitnessField.setType(clazz.getFactory().Type().INTEGER_PRIMITIVE);
        clazz.addField(fitnessField);
    }

    public static CtClass<?> run(String fullQualifiedName, String path) {
        Launcher launcher = new Launcher();
        launcher.getEnvironment().setAutoImports(true);
        launcher.getEnvironment().setCommentEnabled(true);

        launcher.addInputResource(path);
        launcher.buildModel();

        final CtClass<Object> clazz = launcher.getFactory().Class().get(fullQualifiedName);
        insertFitnessField(clazz);

        return clazz;
    }

}
