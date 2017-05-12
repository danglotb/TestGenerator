package fr.inria.testgenerator.searchbased;

import fr.inria.testgenerator.searchbased.neighbors.NeighborsGenerator;
import fr.inria.testgenerator.searchbased.neighbors.RandomNeighorsGenerator;
import fr.inria.testgenerator.searchbased.search.Algorithm;
import fr.inria.testgenerator.searchbased.search.HillClimbingBest;
import fr.inria.testgenerator.searchbased.search.HillClimbingFirst;
import fr.inria.testgenerator.searchbased.search.RandomAlgorithm;
import fr.inria.testgenerator.searchbased.support.Helper;
import fr.inria.testgenerator.searchbased.support.Results;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static fr.inria.testgenerator.searchbased.support.Helper.BUDGET;
import static fr.inria.testgenerator.searchbased.support.Helper.current_budget;

/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 10/05/17
 */
public class BaseSearchTestDataInput {

    public static class ResultRun {
        public final int nbSuccessHC;
        public final int nbSuccessHCF;
        public final int nbSuccessRD;
        public final int[] consumedBudgetHC;
        public final int[] consumedBudgetHCF;
        public final int[] consumedBudgetRD;
        public final int[] historyHC;
        public final int[] historyHCF;
        public final int[] historyRD;
        public final List<Integer> fitnessOverTimeHC;
        public final List<Integer> fitnessOverTimeHCF;
        public final List<Integer> fitnessOverTimeRD;

        public ResultRun(int nbSuccessHC, int nbSuccessHCF, int nbSuccessRD,
                         int[] consumedBudgeHC, int[] consumedBudgeHCF, int[] consumedBudgeRD,
                         int[] historytHC, int[] historyHCF, int[] historyRD,
                         List<Integer> fitnessOverTimeHC, List<Integer> fitnessOverTimeHCF, List<Integer> fitnessOverTimeRD) {
            this.nbSuccessHC = nbSuccessHC;
            this.nbSuccessHCF = nbSuccessHCF;
            this.nbSuccessRD = nbSuccessRD;
            this.consumedBudgetHC = consumedBudgeHC;
            this.consumedBudgetHCF = consumedBudgeHCF;
            this.consumedBudgetRD = consumedBudgeRD;
            this.historyHC = historytHC;
            this.historyHCF = historyHCF;
            this.historyRD = historyRD;
            this.fitnessOverTimeHC = fitnessOverTimeHC;
            this.fitnessOverTimeHCF = fitnessOverTimeHCF;
            this.fitnessOverTimeRD = fitnessOverTimeRD;
        }
    }

    public static void main(String[] args) {
        final ResultRun resultRun = run();
        writeBoxplotFileBudget(resultRun);
        writeFitnessOverTime(resultRun);
        writeBoxplotFileFitness(resultRun);
        writeHistory(resultRun);
        writeDataForFisher(resultRun);
    }

    private static void writeFitnessOverTime(ResultRun resultRun) {
        final int[] index = new int[]{0};
        try (FileWriter writer = new FileWriter("output/fitnessHC.data")) {
            writer.write(resultRun.fitnessOverTimeHC.stream()
                    .map(fitness -> index[0]++ + " " + fitness.toString())
                    .collect(Collectors.joining("\n"))
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        index[0] = 0;
        try (FileWriter writer = new FileWriter("output/fitnessHCF.data")) {
            writer.write(resultRun.fitnessOverTimeHC.stream()
                    .map(fitness -> index[0]++ + " " + fitness.toString())
                    .collect(Collectors.joining("\n"))
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (resultRun.fitnessOverTimeRD != null) {
            index[0] = 0;
            try (FileWriter writer = new FileWriter("output/fitnessRD.data")) {
                writer.write(resultRun.fitnessOverTimeRD.stream()
                        .map(fitness -> index[0]++ + " " + fitness.toString())
                        .collect(Collectors.joining("\n"))
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void writeBoxplotFileFitness(ResultRun run) {
        final String separator = " ";

        try (FileWriter writer = new FileWriter("output/success.data")) {
            writer.write(new StringBuilder().append("algo").append(separator).append("nbSuccess").append("\n")
                    .append("0").append(separator).append(run.nbSuccessHC).append("\n")
                    .append("1").append(separator).append(run.nbSuccessHCF).append("\n")
                    .append("2").append(separator).append(run.nbSuccessRD).append("\n").toString()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeBoxplotFileBudget(ResultRun run) {
        final String separator = " ";
        StringBuilder s = new StringBuilder();
        s.append("consumedbudget algo\n");
        for (int i = 0; i < run.consumedBudgetHC.length; i++) {
            s.append(run.consumedBudgetHC[i]).append(separator).append("HC").append("\n");
        }
        for (int i = 0; i < run.consumedBudgetHC.length; i++) {
            s.append(run.consumedBudgetHCF[i]).append(separator).append("HCF").append("\n");
        }
        for (int i = 0; i < run.consumedBudgetHC.length; i++) {
            s.append(run.consumedBudgetRD[i]).append(separator).append("RD").append("\n");
        }
        try (FileWriter writer = new FileWriter("output/boxplot.data")) {
            writer.write(s.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String arrayIntToStr(int[] array, String algo) {
        return Arrays.stream(array)
                .mapToObj(Integer::toString)
                .map(element -> algo + " " + element)
                .collect(Collectors.joining("\n")) + "\n";
    }

    private static void writeDataForFisher(ResultRun run) {
        try {
            FileWriter writer = new FileWriter("output/fisher_HC.data");
            writer.write(run.nbSuccessHC + " " + (nbTimeToRun - run.nbSuccessHC) + "\n");
            writer.close();
            writer = new FileWriter("output/fisher_HCF.data");
            writer.write(run.nbSuccessHCF + " " + (nbTimeToRun - run.nbSuccessHCF) + "\n");
            writer.close();
            writer = new FileWriter("output/fisher_RD.data");
            writer.write(run.nbSuccessRD + " " + (nbTimeToRun - run.nbSuccessRD) + "\n");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeHistory(ResultRun run) {
        try {
            FileWriter writer = new FileWriter("output/budget_h.data");
            writer.write(arrayIntToStr(run.consumedBudgetHC, "HC"));
            writer.write(arrayIntToStr(run.consumedBudgetHCF, "HCF"));
            writer.write(arrayIntToStr(run.consumedBudgetRD, "RD"));
            writer.close();
            writer = new FileWriter("output/fitness_h.data");
            writer.write(arrayIntToStr(run.historyHC, "HC"));
            writer.write(arrayIntToStr(run.historyHCF, "HCF"));
            writer.write(arrayIntToStr(run.historyRD, "RD"));
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final int nbTimeToRun = 1000;

    private static int[][] initialsSolutions = new int[nbTimeToRun][6];

    static {
        for (int i = 0; i < nbTimeToRun; ) {
            final int[] solution = Helper.initRandomSolution();
            if (Helper.run.apply(solution) != 0) {
                initialsSolutions[i] = solution;
                i++;
            }
        }
        current_budget = BUDGET;
    }

    public static ResultRun run() {
        NeighborsGenerator<int[]> neighborsGenerator = new RandomNeighorsGenerator(6);
        int nbSuccess = 0;

        int[] historyHC = new int[nbTimeToRun];
        int[] historyHCF = new int[nbTimeToRun];
        int[] historyRD = new int[nbTimeToRun];

        int[] consumedBudgedHC = new int[nbTimeToRun];
        int[] consumedBudgedHCF = new int[nbTimeToRun];
        int[] consumedBudgedRD = new int[nbTimeToRun];

        Algorithm algorithm = new HillClimbingBest(Helper.run, neighborsGenerator);
        List<Integer> fitnessOverTimeHC = null;
        for (int i = 0; i < nbTimeToRun; i++) {
            final Results results = algorithm.run(initialsSolutions[i]);
            nbSuccess += results.success ? 1 : 0;
            historyHC[i] = results.success ? 1 : 0;
            if (results.success && fitnessOverTimeHC == null) {
                fitnessOverTimeHC = results.fitnessOverTheTime;
            }
            consumedBudgedHC[i] = results.consumedFitnessEvaluation;
            current_budget = BUDGET;
        }

        algorithm = new HillClimbingFirst(Helper.run, neighborsGenerator);
        List<Integer> fitnessOverTimeHCF = null;
        int nbSuccessHCF = 0;
        for (int i = 0; i < nbTimeToRun; i++) {
            final Results results = algorithm.run(initialsSolutions[i]);
            nbSuccessHCF += results.success ? 1 : 0;
            historyHCF[i] = results.success ? 1 : 0;
            if (results.success && fitnessOverTimeHCF == null) {
                fitnessOverTimeHCF = results.fitnessOverTheTime;
            }
            consumedBudgedHCF[i] = results.consumedFitnessEvaluation;
            current_budget = BUDGET;
        }

        List<Integer> fitnessOverTimeRD = null;
        int nbSuccessRD = 0;
        algorithm = new RandomAlgorithm(Helper.run, (int) (23 * Math.pow(23, 2) + (42 * 32)));
        for (int i = 0; i < nbTimeToRun; i++) {
            final Results results = algorithm.run(initialsSolutions[i]);
            nbSuccessRD += results.success ? 1 : 0;
            historyRD[i] = results.success ? 1 : 0;
            if (results.success && fitnessOverTimeRD == null) {
                fitnessOverTimeRD = results.fitnessOverTheTime;
            }
            consumedBudgedRD[i] = results.consumedFitnessEvaluation;
            current_budget = BUDGET;
        }

        return new ResultRun(nbSuccess, nbSuccessHCF, nbSuccessRD,
                consumedBudgedHC, consumedBudgedHCF, consumedBudgedRD,
                historyHC, historyHCF, historyRD,
                fitnessOverTimeHC, fitnessOverTimeHCF, fitnessOverTimeRD);
    }

}
