package strategies;

import dataprocessing.ProductionContracts;
import fileio.DistributorInputData;
import fileio.ProducerInputData;

import java.util.ArrayList;
import java.util.Comparator;
/**
 * The class implements Quantity Strategy
 */
public final class QuantityStrategy implements Strategy {
    /**
     * Overrided method sorted the producers according to guide rules
     * and implements the contracts between distributors and producers
     * by calling ProductionContracts class
     */
    @Override
    public void chooseProducers(final DistributorInputData distributor,
                                final ArrayList<ProducerInputData> producers) {

        producers.sort(Comparator.comparing(ProducerInputData::getEnergyPerDistributor)
                .reversed().thenComparing(ProducerInputData::getId));

        ProductionContracts productionContracts = new ProductionContracts();
        int totalEnergy = 0;
        for (ProducerInputData producer : producers) {
            totalEnergy = productionContracts.getEnergyOfSignedContract(distributor,
                    producer, totalEnergy);
            if (totalEnergy >= distributor.getEnergyNeededKW()) {
                break;
            }
        }
    }
}
