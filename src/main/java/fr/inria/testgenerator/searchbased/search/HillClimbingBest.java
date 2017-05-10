package fr.inria.testgenerator.searchbased.search;

import fr.inria.testgenerator.searchbased.Helper;
import fr.inria.testgenerator.searchbased.Results;
import fr.inria.testgenerator.searchbased.neighbors.NeighborsGenerator;
import fr.inria.testgenerator.searchbased.neighbors.RandomNeighorsGenerator;

import static fr.inria.testgenerator.searchbased.Helper.BUDGET;
import static fr.inria.testgenerator.searchbased.Helper.current_budget;


/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 09/05/17
 */
public class HillClimbingBest implements Algorithm {

    private NeighborsGenerator<int[]> neighborsGenerator;

    public HillClimbingBest(NeighborsGenerator<int[]> neighborsGenerator) {
        this.neighborsGenerator = neighborsGenerator;
    }

    @Override
    public Results run() {
        int[] bestSolution = new int[6];
        int bestFitness = Integer.MAX_VALUE;
        int[] currentSolution = Helper.initRandomSolution();
        int currentFitness = Helper.run(currentSolution);
        while (Helper.current_budget > 0 && currentFitness != 0) {
            final int[][] all = neighborsGenerator.getAll(currentSolution);
            int selectedSolution = -1;
            int selectedFitness = -1;
            int[] fitnesses = new int[all.length];
            for (int i = 0; i < all.length && Helper.current_budget > 0; i++) {
                fitnesses[i] = Helper.run(all[i]);
                if (fitnesses[i] < currentFitness) {
                    selectedSolution = i;
                    selectedFitness = fitnesses[i];
                }
            }
            if (selectedSolution != -1) {
                System.arraycopy(all[selectedSolution], 0, currentSolution, 0, currentSolution.length);
                currentFitness = selectedFitness;
                if (bestFitness > currentFitness) {
                    System.arraycopy(currentSolution, 0, bestSolution, 0, currentSolution.length);
                    bestFitness = currentFitness;
                }
            } else {
                if (bestFitness > currentFitness) {
                    System.arraycopy(currentSolution, 0, bestSolution, 0, currentSolution.length);
                    bestFitness = currentFitness;
                }
                currentSolution = Helper.initRandomSolution();
                currentFitness = Helper.run(currentSolution);
            }
        }
        int consumedBudget = current_budget == -1 ? BUDGET : BUDGET - current_budget;
        return new Results(bestSolution, consumedBudget, bestFitness == 0);
    }
}
