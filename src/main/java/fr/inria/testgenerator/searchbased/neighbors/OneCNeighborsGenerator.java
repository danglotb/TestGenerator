package fr.inria.testgenerator.searchbased.neighbors;

/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 09/05/17
 */
public abstract class OneCNeighborsGenerator implements NeighborsGenerator<int[]> {

    protected int maxCursor;
    protected int currentCursor;

    public OneCNeighborsGenerator(int maxCursor) {
        this.maxCursor = maxCursor;
        this.currentCursor = 0;
    }

    @Override
    public int[][] getAll(int[] from) {
        int [][] result = new int[maxCursor][from.length];
        for (int i = 0; i < maxCursor; i++) {
            System.arraycopy(from, 0, result[i], 0, from.length);
        }
        while (hasNext()) {
            result[currentCursor] = next(result[currentCursor]);
            updateCursor();
        }
        reset();
        return result;
    }

    @Override
    public void reset() {
        currentCursor = 0;
    }

    @Override
    public void updateCursor() {
        currentCursor++;
    }

    @Override
    public boolean hasNext() {
        return currentCursor < maxCursor;
    }
}
