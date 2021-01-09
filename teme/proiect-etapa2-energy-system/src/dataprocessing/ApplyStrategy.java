package dataprocessing;

import fileio.DistributorInputData;
import fileio.ProducerInputData;
import strategies.Strategy;

import java.util.ArrayList;
/**
 * This class performs strategy calling
 */
public final class ApplyStrategy {
    /**
     * Method calls strategy for choosing an energy contract
     */
    public void chooseContract(final Strategy strategy,
                               final DistributorInputData distributor,
                               final ArrayList<ProducerInputData> producers) {

        strategy.chooseProducers(distributor, producers);
    }
}
