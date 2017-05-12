package fr.inria.testgenerator.searchbased.support;

import eu.fbk.se.tcgen2.BinarySearch;
import fr.inria.testgenerator.searchbased.neighbors.NeighborsGenerator;
import fr.inria.testgenerator.searchbased.neighbors.RandomNeighorsGenerator;
import fr.inria.testgenerator.searchbased.search.Algorithm;
import fr.inria.testgenerator.searchbased.search.HillClimbingBest;
import org.junit.Test;
import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.ModifierKind;

import static fr.inria.testgenerator.searchbased.support.Helper.BUDGET;
import static fr.inria.testgenerator.searchbased.support.Helper.current_budget;

/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 10/05/17
 */
public class JUnitTestGenerator {

    public static void generateTest(Results results) {
        Launcher launcher = new Launcher();
        launcher.getEnvironment().setAutoImports(false);
        launcher.getEnvironment().setCommentEnabled(true);

        launcher.addInputResource("docs/BinarySearch.java");
        launcher.buildModel();

        final CtClass<?> classUnderTest = launcher.getFactory().Class().get("eu.fbk.se.tcgen2.BinarySearch");

        final CtClass<?> classTest = launcher.getFactory().createClass();
        classTest.setSimpleName("TestBinarySearch");
        classUnderTest.getPackage().addType(classTest);
        classTest.addModifier(ModifierKind.PUBLIC);

        final CtMethod<Void> testMethod_43 = launcher.getFactory().createMethod();
        testMethod_43.setSimpleName("testBinarySearchOnMiddle");
        testMethod_43.addModifier(ModifierKind.PUBLIC);
        testMethod_43.setType(launcher.getFactory().Type().VOID_PRIMITIVE);
        testMethod_43.addAnnotation(launcher.getFactory().createAnnotation(
                launcher.getFactory().Type().createReference(Test.class)
        ));
        final int search = BinarySearch.search(Helper.int6toint5(results.inputTestData), results.inputTestData[5]);
        testMethod_43.setBody(
                launcher.getFactory().createCodeSnippetStatement(
                        "org.junit.Assert.assertEquals(" + search + "," +
                                "BinarySearch.search(" + results.inputTestDataToString() + "))")
        );

        classTest.addMethod(testMethod_43);
        launcher.prettyprint();
    }

    public static void main(String[] args) {
        NeighborsGenerator<int[]> neighborsGenerator = new RandomNeighorsGenerator(6);
        boolean success = false;
        Algorithm algorithm = new HillClimbingBest(Helper.run, neighborsGenerator);
        Results results = null;
        while (!success) {
            results = algorithm.run(Helper.initRandomSolution());
            success = results.success;
            current_budget = BUDGET;
        }
        generateTest(results);
    }

}
