package fr.inria.testgenerator.searchbased;

/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 10/05/17
 */
public class Results {

    public final int[] inputTestData;

    public final int consumedFitnessEvaluation;

    public final boolean success;

    public Results(int[] inputTestData, int consumedFitnessEvaluation, boolean success) {
        this.inputTestData = inputTestData;
        this.consumedFitnessEvaluation = consumedFitnessEvaluation;
        this.success = success;
    }
}
