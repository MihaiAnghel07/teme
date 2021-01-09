package dataprocessing;

import fileio.DistributorInputData;
import fileio.ProducerInputData;

/**
 * The class helps to sign the production contracts and returns the energy
 * value of the signed contract
 */
public final class ProductionContracts {
    /**
     * The distributor signs the contract with the producer if the conditions are met and
     * the energy value is returned to know if another contract needs to be signed
     * @param totalEnergy amount of contract's energy
     */
    public int getEnergyOfSignedContract(final DistributorInputData distributor,
                                         final ProducerInputData producer,
                                         int totalEnergy) {

        if (producer.getCurrentNrDistributors() < producer.getMaxDistributors()) {
            producer.addContract(distributor.getId());
            producer.setCurrentNrDistributors(producer.getCurrentNrDistributors() + 1);
            distributor.addContractsToProducers(producer.getId());
            totalEnergy += producer.getEnergyPerDistributor();
        }
        return totalEnergy;
    }
}
