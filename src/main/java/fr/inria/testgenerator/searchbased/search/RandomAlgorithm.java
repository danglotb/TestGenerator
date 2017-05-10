package fr.inria.testgenerator.searchbased.search;

import fr.inria.testgenerator.searchbased.Helper;
import fr.inria.testgenerator.searchbased.Results;

import java.util.Random;

import static fr.inria.testgenerator.searchbased.Helper.BUDGET;
import static fr.inria.testgenerator.searchbased.Helper.current_budget;

/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 09/05/17
 */
public class RandomAlgorithm implements Algorithm {

    private Random random;

    public RandomAlgorithm(int seed)  {
        this.random = new Random(seed);
    }

    public Results run() {
        int[] currentSolution = Helper.initRandomSolution();
        int currentFitness = Helper.run(currentSolution);
        while (Helper.current_budget > 0 && currentFitness != 0) {
            int[] tmpSolution = next();
            int tmpFitness = Helper.run(currentSolution);
            if (tmpFitness < currentFitness) {
                System.arraycopy(currentSolution, 0, tmpSolution, 0, 6);
                currentFitness = tmpFitness;
            }
        }
        int consumedBudget = current_budget == -1 ? BUDGET : BUDGET - current_budget;
        return new Results(currentSolution, consumedBudget, currentFitness == 0);
    }

    private int[] next() {
        return new int[]{
                random.nextInt(Helper.MAX_VALUE),
                random.nextInt(Helper.MAX_VALUE),
                random.nextInt(Helper.MAX_VALUE),
                random.nextInt(Helper.MAX_VALUE),
                random.nextInt(Helper.MAX_VALUE),
                random.nextInt(Helper.MAX_VALUE)
        };
    }

}
