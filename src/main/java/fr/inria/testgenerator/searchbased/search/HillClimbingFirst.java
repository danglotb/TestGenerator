package fr.inria.testgenerator.searchbased.search;

import fr.inria.testgenerator.searchbased.Helper;
import fr.inria.testgenerator.searchbased.Results;
import fr.inria.testgenerator.searchbased.neighbors.NeighborsGenerator;

import static fr.inria.testgenerator.searchbased.Helper.BUDGET;
import static fr.inria.testgenerator.searchbased.Helper.current_budget;

/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 10/05/17
 */
public class HillClimbingFirst implements Algorithm {

    private NeighborsGenerator<int[]> neighborsGenerator;

    public HillClimbingFirst(NeighborsGenerator<int[]> neighborsGenerator) {
        this.neighborsGenerator = neighborsGenerator;
    }

    @Override
    public Results run() {
        int[] bestSolution = new int[6];
        int bestFitness = Integer.MAX_VALUE;
        int[] currentSolution = Helper.initRandomSolution();
        int currentFitness = Helper.run(currentSolution);
        while (Helper.current_budget > 0 && currentFitness != 0) {
            int selectedFitness = -1;
            while (neighborsGenerator.hasNext()) {
                final int[] tmpSolution = neighborsGenerator.next(currentSolution);
                int tmpFitness = Helper.run(tmpSolution);
                if (tmpFitness < currentFitness) {
                    System.arraycopy(tmpSolution, 0, currentSolution, 0, currentSolution.length);
                    currentFitness = tmpFitness;
                    selectedFitness = currentFitness;
                    if (bestFitness > currentFitness) {
                        System.arraycopy(currentSolution, 0, bestSolution, 0, currentSolution.length);
                        bestFitness = currentFitness;
                    }
                    break;
                }
                neighborsGenerator.updateCursor();
            }
            neighborsGenerator.reset();
            if (selectedFitness == -1) {
                currentSolution = Helper.initRandomSolution();
                currentFitness = Helper.run(currentSolution);
            }
        }
        int consumedBudget = current_budget == -1 ? BUDGET : BUDGET - current_budget;
        return new Results(bestSolution, consumedBudget, bestFitness == 0);
    }
}
