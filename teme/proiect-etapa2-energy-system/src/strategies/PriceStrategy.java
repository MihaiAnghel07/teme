package strategies;

import fileio.DistributorInputData;
import fileio.ProducerInputData;

import java.util.ArrayList;
import java.util.Comparator;
/**
 * The class implements Price Strategy
 */
public final class PriceStrategy implements Strategy {
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

        int totalEnergy = 0;
        for (ProducerInputData producer : producers) {
            if (producer.getCurrentNrDistributors() < producer.getMaxDistributors()) {
                producer.addContract(distributor.getId());
                producer.setCurrentNrDistributors(producer.getCurrentNrDistributors() + 1);
                distributor.addContractsToProducers(producer.getId());
                totalEnergy += producer.getEnergyPerDistributor();
            }
            if (totalEnergy >= distributor.getEnergyNeededKW()) {
                break;
            }
        }
    }
}
