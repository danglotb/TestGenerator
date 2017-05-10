package fr.inria.testgenerator.searchbased.support;

import eu.fbk.se.tcgen2.BinarySearch;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Function;
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

    public final static Function<int[], Integer> run = (solution) -> {
        BinarySearch.fitness = Integer.MAX_VALUE;
        BinarySearch.search(int6toint5(solution), solution[5]);
        Helper.current_budget--;
        return BinarySearch.fitness;
    };

    public final static Function<int[], Integer> runReflection = (solution) -> {
        try {
            URLClassLoader classLoader = new URLClassLoader(
                    new URL[]{
                            new File("spooned-classes/").toURI().toURL()
                    }, ClassLoader.getSystemClassLoader()
            );
            int[] array = new int[solution.length - 1];
            System.arraycopy(solution, 0, array, 0, array.length);
            final Class<?> aClass = classLoader.loadClass("eu.fbk.se.tcgen2.BinarySearch");
            final Object instance = aClass.newInstance();
            final Method search = aClass.getMethod("search", int[].class, int.class);
            search.invoke(instance, array, solution[solution.length - 1]);
            final int fitness = (int) aClass.getField("fitness").get(instance);
            aClass.getMethod("reset").invoke(aClass);
            return fitness;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    };

    public static int[] initRandomSolution() {
        return new int[]{
                random.nextInt(MAX_VALUE),
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
