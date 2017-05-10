package fr.inria.testgenerator;

import fr.inria.testgenerator.searchbased.neighbors.NeighborsGenerator;
import fr.inria.testgenerator.searchbased.neighbors.RandomNeighorsGenerator;
import fr.inria.testgenerator.searchbased.search.Algorithm;
import fr.inria.testgenerator.searchbased.search.HillClimbingBest;
import fr.inria.testgenerator.searchbased.support.Helper;
import fr.inria.testgenerator.searchbased.support.Results;
import spoon.Launcher;
import spoon.SpoonModelBuilder;
import spoon.reflect.declaration.CtClass;

import java.net.MalformedURLException;

import static fr.inria.testgenerator.instrumentor.Instrumentor.insertFitnessAssignement;
import static fr.inria.testgenerator.instrumentor.Instrumentor.insertFitnessField;
import static fr.inria.testgenerator.searchbased.support.Helper.BUDGET;
import static fr.inria.testgenerator.searchbased.support.Helper.current_budget;

/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 10/05/17
 */
public class Main {

    public static void main(String[] args) throws MalformedURLException {
        Launcher launcher = new Launcher();
        launcher.getEnvironment().setAutoImports(false);
        launcher.getEnvironment().setCommentEnabled(true);

        launcher.addInputResource("docs/BinarySearch.java");
        launcher.buildModel();

        final CtClass<?> clazz = launcher.getFactory().Class().get("eu.fbk.se.tcgen2.BinarySearch");
        insertFitnessField(clazz);
        insertFitnessAssignement(43, clazz.getMethodsByName("search").get(0));//TODO we could parametized this, but w/e
        launcher.prettyprint();
        launcher.createCompiler().compile(SpoonModelBuilder.InputType.CTTYPES);

        NeighborsGenerator<int[]> neighborsGenerator = new RandomNeighorsGenerator(6);
        boolean success = false;
        Algorithm algorithm = new HillClimbingBest(Helper.runReflection, neighborsGenerator);
        Results results = null;
        while (!success) {
            results = algorithm.run();
            success = results.success;
            current_budget = BUDGET;
        }
        final int output = Helper.runReflection.apply(results.inputTestData);
        System.out.println(results.inputTestDataToString());
        System.out.println(output);
    }
}
