package fr.inria.testgenerator.searchbased;

import fr.inria.testgenerator.searchbased.support.Helper;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 10/05/17
 */
public class StatisticalSearchDataInput {

    public static class ResultStat {
        public final int budget;
        public final int bound;
        public final BaseSearchTestDataInput.ResultRun result;

        public ResultStat(int budget, int bound, BaseSearchTestDataInput.ResultRun result) {
            this.budget = budget;
            this.bound = bound;
            this.result = result;
        }

        @Override
        public String toString() {
            return budget + " " + bound + " " + this.result.nbSuccessHC + " " + this.result.nbSuccessHCF + " " + this.result.nbSuccessRD;
        }
    }

    public static void main(String[] args) {
        int[] budgets = new int[]{0, 50, 100, 300, 500, 1000, 1500, 2000};
        int[] bounds = new int[]{50};
        List<ResultStat> results = new ArrayList<>();
        for (int i = 0; i < budgets.length; i++) {
            Helper.BUDGET = budgets[i];
            for (int i1 = 0; i1 < bounds.length; i1++) {
                Helper.MAX_VALUE = bounds[i1];
                results.add(new ResultStat(budgets[i], bounds[i1], BaseSearchTestDataInput.run()));
            }
        }
        try (FileWriter writer = new FileWriter("output/stat_1.data")) {
            final StringBuilder output = new StringBuilder()
                    .append("budget ")
                    .append("bound ")
                    .append("algo ")
                    .append("success ")
                    .append("avg")
                    .append("\n");

            Function<int[], String> avg = (array) ->
                    String.format("%.2f", Arrays.stream(array).average().orElse(Double.NaN));

            results.forEach(resultStat ->
                    output.append(resultStat.budget).append(" ")
                            .append(resultStat.bound).append(" ")
                            .append("HC").append(" ")
                            .append(resultStat.result.nbSuccessHC).append(" ")
                            .append(avg.apply(resultStat.result.consumedBudgetHC)).append("\n")
                            .append(resultStat.budget).append(" ")
                            .append(resultStat.bound).append(" ")
                            .append("HCF").append(" ")
                            .append(resultStat.result.nbSuccessHCF).append(" ")
                            .append(avg.apply(resultStat.result.consumedBudgetHCF)).append("\n")
                            .append(resultStat.budget).append(" ")
                            .append(resultStat.bound).append(" ")
                            .append("RD").append(" ")
                            .append(resultStat.result.nbSuccessRD).append(" ")
                            .append(avg.apply(resultStat.result.consumedBudgetRD)).append("\n")
            );
            writer.write(output.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
