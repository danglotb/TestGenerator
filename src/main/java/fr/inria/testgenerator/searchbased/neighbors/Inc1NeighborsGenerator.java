package fr.inria.testgenerator.searchbased.neighbors;

import fr.inria.testgenerator.searchbased.Helper;

/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 09/05/17
 */
public class Inc1NeighborsGenerator extends OneCNeighborsGenerator {

    public Inc1NeighborsGenerator(int maxCursor) {
        super(maxCursor);
    }

    @Override
    public int[] next(int[] from) {
        from[currentCursor] = (from[currentCursor] + 1) % Helper.MAX_VALUE;
        return from;
    }
}
