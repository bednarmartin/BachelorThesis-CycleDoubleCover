package algorithm.strategies;

import algorithm.graph.CycleDoubleCover;

import java.util.List;


/**
 * Interface representing what should happen when a cycle double cover is found.
 */
public interface CDCStrategy {
    /**
     * This method processes the Cycle Double Cover according to the strategy.
     *
     * @param cycleDoubleCover cycle double cover to be processed
     * @return true if the Cycle Double Cover should be included
     */
    boolean processCDC(CycleDoubleCover cycleDoubleCover);

    /**
     * This method grants access to the Cycle Double Covers that were processed.
     *
     * @return List of cycle double covers
     */
    List<CycleDoubleCover> getCDCs();
}
