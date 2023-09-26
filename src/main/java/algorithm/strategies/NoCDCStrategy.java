package algorithm.strategies;

import algorithm.graph.CycleDoubleCover;

import java.util.ArrayList;
import java.util.List;

/**
 * This CDCStrategy does nothing.
 */
public class NoCDCStrategy implements CDCStrategy {
    /**
     * @inheritDoc
     */
    @Override
    public boolean processCDC(CycleDoubleCover cycleDoubleCover) {
        return true;
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<CycleDoubleCover> getCDCs() {
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return "";
    }
}
