package fr.inria.testgenerator.searchbased.neighbors;

/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 09/05/17
 */
public interface NeighborsGenerator<T> {

    T[] getAll(T from);

    T next(T from);

    boolean hasNext();

    void updateCursor();

    void reset();

}
