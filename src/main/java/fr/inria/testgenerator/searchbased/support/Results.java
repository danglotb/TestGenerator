package fr.inria.testgenerator.searchbased.support;

import java.util.Arrays;
import java.util.stream.Collectors;

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

    public String inputTestDataToString() {
        int[] array = new int[inputTestData.length-1];
        System.arraycopy(inputTestData, 0, array, 0, array.length);
        return new StringBuilder().append("new int[] {")
                .append(Arrays.stream(array)
                        .mapToObj(Integer::toString)
                        .collect(Collectors.joining(","))
                ).append("}, ").append(inputTestData[inputTestData.length - 1]).toString();
    }
}
