package fr.inria.testgenerator.searchbased;

import eu.fbk.se.tcgen2.BinarySearch;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 09/05/17
 */
public class Helper {

    public static Random random = new Random(42);

    public static int MAX_VALUE = 50;

    public static int BUDGET = 300;

    public static int current_budget = BUDGET;

    public static int[] int6toint5(int[] from) {
        int[] values = new int[5];
        System.arraycopy(from, 0, values, 0, 5);
        return values;
    }

    public static int run(int[] solution) {
        BinarySearch.fitness = Integer.MAX_VALUE;
        BinarySearch.search(int6toint5(solution), solution[5]);
        Helper.current_budget--;
        return BinarySearch.fitness;
    }

    public static int[] initRandomSolution() {
        return new int[]{random.nextInt(MAX_VALUE),
                random.nextInt(MAX_VALUE),
                random.nextInt(MAX_VALUE),
                random.nextInt(MAX_VALUE),
                random.nextInt(MAX_VALUE),
                random.nextInt(MAX_VALUE)
        };
    }

    public static String solutionAsString(int[] from) {
        return Arrays.stream(from).mapToObj(Integer::toString).collect(Collectors.joining(","));
    }

}
