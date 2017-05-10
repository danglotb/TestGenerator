package fr.inria.testgenerator.searchbased.support;

import fr.inria.testgenerator.searchbased.neighbors.NeighborsGenerator;
import fr.inria.testgenerator.searchbased.neighbors.RandomNeighorsGenerator;
import fr.inria.testgenerator.searchbased.search.Algorithm;
import fr.inria.testgenerator.searchbased.search.HillClimbingBest;
import org.junit.Test;
import spoon.Launcher;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;

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
        final CtMethod<?> search = classUnderTest
                .getMethodsByName("search")
                .get(0);

        final CtClass<?> classTest = launcher.getFactory().createClass();
        classTest.setSimpleName("TestBinarySearch");
        classUnderTest.getPackage().addType(classTest);

        final CtMethod<?> testMethod_43 = launcher.getFactory().createMethod();
        testMethod_43.setSimpleName("testBinarySearchOnMiddle");
        testMethod_43.addAnnotation(launcher.getFactory().createAnnotation(
                launcher.getFactory().Type().createReference(Test.class)
        ));
        testMethod_43.setBody(
                launcher.getFactory().createCodeSnippetStatement(
                        "BinarySearch.search(" + results.inputTestDataToString() + ")")
        );

        classTest.addMethod(testMethod_43);
        System.out.println(classTest);
    }

    public static void main(String[] args) {
        NeighborsGenerator<int[]> neighborsGenerator = new RandomNeighorsGenerator(6);
        boolean success = false;
        Algorithm algorithm = new HillClimbingBest(Helper.run, neighborsGenerator);
        Results results = null;
        while (!success) {
            results = algorithm.run();
            success = results.success;
            current_budget = BUDGET;
        }
        generateTest(results);
    }

}
