package fr.inria.testgenerator.searchbased.search;

import fr.inria.testgenerator.searchbased.neighbors.NeighborsGenerator;
import fr.inria.testgenerator.searchbased.support.Helper;
import fr.inria.testgenerator.searchbased.support.Results;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static fr.inria.testgenerator.searchbased.support.Helper.BUDGET;
import static fr.inria.testgenerator.searchbased.support.Helper.current_budget;


/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 09/05/17
 */
public class HillClimbingFirst extends Algorithm {

    private NeighborsGenerator<int[]> neighborsGenerator;

    public HillClimbingFirst(Function<int[], Integer> run, NeighborsGenerator<int[]> neighborsGenerator) {
        super(run);
        this.neighborsGenerator = neighborsGenerator;
    }

    @Override
    public Results run() {
        return run(Helper.initRandomSolution());
    }

    @Override
    public Results run(int[] initialSolution) {
        int[] bestSolution = new int[6];
        int bestFitness = Integer.MAX_VALUE;
        int[] currentSolution = new int[initialSolution.length];
        System.arraycopy(initialSolution, 0, currentSolution, 0, initialSolution.length);
        int currentFitness = run.apply(currentSolution);
        List<Integer> fitnessOverTheTime = new ArrayList<>();
        while (Helper.current_budget > 0 && currentFitness != 0) {
            fitnessOverTheTime.add(currentFitness);
            final int[][] all = neighborsGenerator.getAll(currentSolution);
            int selectedSolution = -1;
            int selectedFitness = -1;
            int[] fitnesses = new int[all.length];
            for (int i = 0; i < all.length && Helper.current_budget > 0; i++) {
                fitnesses[i] = run.apply(all[i]);
                if (fitnesses[i] < currentFitness) {
                    selectedSolution = i;
                    selectedFitness = fitnesses[i];
                    break;
                }
            }
            if (selectedSolution != -1) {
                System.arraycopy(all[selectedSolution], 0, currentSolution, 0, currentSolution.length);
                currentFitness = selectedFitness;
                if (bestFitness > currentFitness) {
                    System.arraycopy(currentSolution, 0, bestSolution, 0, currentSolution.length);
                    bestFitness = currentFitness;
                }
            }
        }
        fitnessOverTheTime.add(currentFitness);
        int consumedBudget = current_budget == -1 ? BUDGET : BUDGET - current_budget;
        return new Results(bestSolution, consumedBudget, bestFitness == 0, fitnessOverTheTime);
    }
}
