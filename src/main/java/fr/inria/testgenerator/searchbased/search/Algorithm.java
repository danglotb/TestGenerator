package fr.inria.testgenerator.searchbased.search;

import fr.inria.testgenerator.searchbased.support.Results;

import java.util.function.Function;

/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 10/05/17
 */
public abstract class Algorithm {

    protected Function<int[], Integer> run;

    public Algorithm(Function<int[], Integer> run) {
        this.run = run;
    }

    public abstract Results run();

    public abstract Results run(int[] initialSolution);

}
