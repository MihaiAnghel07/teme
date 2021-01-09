package strategies;

import dataprocessing.ProductionContracts;
import fileio.DistributorInputData;
import fileio.ProducerInputData;

import java.util.ArrayList;
import java.util.Comparator;
/**
 * The class implements Green Strategy
 */
public final class GreenStrategy implements Strategy {
    /**
     * Overrided method sorted the producers according to guide rules
     * and implements the contracts between distributors and producers
     * by calling ProductionContracts class
     */
    @Override
    public void chooseProducers(final DistributorInputData distributor,
                                final ArrayList<ProducerInputData> producers) {

        producers.sort(Comparator.comparing(ProducerInputData::getPriceKW).reversed()
                .thenComparing(ProducerInputData::getEnergyPerDistributor).reversed()
                .thenComparing(ProducerInputData::getId));

        ProductionContracts productionContracts = new ProductionContracts();
        int totalEnergy = 0;
        for (ProducerInputData producer : producers) {
           // First we are only looking for producers that offer green energy
            if (!producer.getEnergyType().equals("COAL")
                    && !producer.getEnergyType().equals("NUCLEAR")) {

                if (producer.getCurrentNrDistributors() < producer.getMaxDistributors()) {
                    totalEnergy = productionContracts.getEnergyOfSignedContract(distributor,
                            producer, totalEnergy);

                    if (totalEnergy >= distributor.getEnergyNeededKW()) {
                        break;
                    }
                }
            }
        }
        // If not all the desired energy is obtained, contracts are also sought
        // in rest of producers
        if (totalEnergy < distributor.getEnergyNeededKW()) {
            for (ProducerInputData producer : producers) {

                if (!producer.getContracts().contains(distributor.getId())) {
                    totalEnergy = productionContracts.getEnergyOfSignedContract(distributor,
                            producer, totalEnergy);

                    if (totalEnergy >= distributor.getEnergyNeededKW()) {
                        break;
                    }
                }
            }
        }
    }
}
