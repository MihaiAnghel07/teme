package strategies;

import fileio.DistributorInputData;
import fileio.ProducerInputData;

import java.util.ArrayList;
/**
 * Strategy interface
 */
public interface Strategy {
    /**
     * This method will be implemented by strategies
     */
    void chooseProducers(DistributorInputData distributor,
                         ArrayList<ProducerInputData> producers);
}
