package algorithm.strategies;


/**
 * Abstract class for CDCStrategy
 */
public abstract class PrintCDCStrategy implements CDCStrategy {
    /**
     * printCDCStrategy
     */
    protected final CDCStrategy printCDCStrategy;

    /**
     * Constructor
     *
     * @param printCDCStrategy print CDC strategy
     */
    protected PrintCDCStrategy(CDCStrategy printCDCStrategy) {
        this.printCDCStrategy = printCDCStrategy;
    }

    /**
     * @InheritDoc
     */
    protected PrintCDCStrategy() {
        this.printCDCStrategy = new NoCDCStrategy();
    }

    @Override
    public String toString() {
        return "";
    }

}
