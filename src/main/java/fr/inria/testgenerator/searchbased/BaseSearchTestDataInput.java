package fr.inria.testgenerator.searchbased;

import fr.inria.testgenerator.searchbased.neighbors.NeighborsGenerator;
import fr.inria.testgenerator.searchbased.neighbors.RandomNeighorsGenerator;
import fr.inria.testgenerator.searchbased.search.Algorithm;
import fr.inria.testgenerator.searchbased.search.HillClimbingBest;
import fr.inria.testgenerator.searchbased.search.RandomAlgorithm;
import fr.inria.testgenerator.searchbased.support.Helper;
import fr.inria.testgenerator.searchbased.support.Results;

import static fr.inria.testgenerator.searchbased.support.Helper.BUDGET;
import static fr.inria.testgenerator.searchbased.support.Helper.current_budget;

/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 10/05/17
 */
public class BaseSearchTestDataInput {

    public static void main(String[] args) {

        final int nbTimeToRun = 1000;

        NeighborsGenerator<int[]> neighborsGenerator = new RandomNeighorsGenerator(6);
        int nbSuccess = 0;
        int totalBudgetConsumed = 0;

        Algorithm algorithm = new HillClimbingBest(Helper.run, neighborsGenerator);
        for (int i = 0; i < nbTimeToRun; i++) {
            final Results results = algorithm.run();
            nbSuccess += results.success ? 1 : 0;
            totalBudgetConsumed += results.consumedFitnessEvaluation;
            current_budget = BUDGET;
        }
        System.out.println(nbSuccess + " success over " + nbTimeToRun + " tries");
        System.out.println("Avg budget consumed " + String.format("%.2f", ((double) totalBudgetConsumed / (double) nbTimeToRun)));

        nbSuccess = 0;
        totalBudgetConsumed = 0;
        for (int i = 0; i < nbTimeToRun; i++) {
            algorithm = new RandomAlgorithm(Helper.run, (int) (23 * Math.pow(i, 2) + (42 * 32)));
            final Results results = algorithm.run();
            nbSuccess += results.success ? 1 : 0;
            totalBudgetConsumed += results.consumedFitnessEvaluation;
            current_budget = BUDGET;
        }
        System.out.println(nbSuccess + " success over " + nbTimeToRun + " tries");
        System.out.println("Avg budget consumed " + String.format("%.2f", ((double) totalBudgetConsumed / (double) nbTimeToRun)));
    }

}
