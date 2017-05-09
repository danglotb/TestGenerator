package fr.inria.testgenerator;

import spoon.reflect.code.CtFieldRead;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.ModifierKind;

/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 07/05/17
 */
public class FitnessInsertion {

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
}
