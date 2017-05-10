package fr.inria.testgenerator.searchbased.neighbors;

import java.util.Random;

/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 09/05/17
 */
public class RandomNeighorsGenerator extends OneCNeighborsGenerator {

    private Random random;

    public RandomNeighorsGenerator(int maxCursor) {
        this(maxCursor, 23);
    }

    public RandomNeighorsGenerator(int maxCursor, int seed) {
        super(maxCursor);
        this.random = new Random(seed);
    }

    @Override
    public int[] next(int[] from) {
        from[currentCursor] = random.nextInt(50);
        return from;
    }

}
