package fr.inria.testgenerator.searchbased.search;

import fr.inria.testgenerator.searchbased.support.Helper;
import fr.inria.testgenerator.searchbased.support.Results;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import static fr.inria.testgenerator.searchbased.support.Helper.BUDGET;
import static fr.inria.testgenerator.searchbased.support.Helper.current_budget;

/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 09/05/17
 */
public class RandomAlgorithm extends Algorithm {

    private Random random;

    public RandomAlgorithm(Function<int[], Integer> run, int seed)  {
        super(run);
        this.random = new Random(seed);
    }

    @Override
    public Results run() {
        return run(Helper.initRandomSolution());
    }

    public Results run(int[] initialSolution) {
        List<Integer> fitnessOverTheTime = new ArrayList<>();
        int[] currentSolution = new int[initialSolution.length];
        System.arraycopy(initialSolution, 0, currentSolution, 0, initialSolution.length);
        int currentFitness = run.apply(currentSolution);
        fitnessOverTheTime.add(currentFitness);
        while (Helper.current_budget > 0 && currentFitness != 0) {
            int[] tmpSolution = next();
            int tmpFitness = run.apply(tmpSolution);
            if (tmpFitness < currentFitness) {
                System.arraycopy(currentSolution, 0, tmpSolution, 0, 6);
                currentFitness = tmpFitness;
            }
            fitnessOverTheTime.add(currentFitness);
        }
        int consumedBudget = current_budget == -1 ? BUDGET : BUDGET - current_budget;
        return new Results(currentSolution, consumedBudget, currentFitness == 0, fitnessOverTheTime);
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
