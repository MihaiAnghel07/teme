package dataprocessing;

import fileio.ConsumerInputData;
import fileio.DistributorInputData;
import fileio.ProducerInputData;
import strategies.GreenStrategy;
import strategies.PriceStrategy;
import strategies.QuantityStrategy;

import java.util.ArrayList;

/**
 * The class contains three methods:
 * First method find the minimum price contract
 * The second one assigns consumer contracts (contract of minimum value)
 * The last one assigns distributors contracts (contracts between distributors and producers)
 */
public final class Contracts {
    private final ApplyStrategy applyStrategy;

    public Contracts() {
        applyStrategy = new ApplyStrategy();
    }
    /**
     * I chose a large enough number to be able to find the minimum value
     * @return id of contract minimum value
     */
    private int findIdOfContractsMinimumValue(final ArrayList<DistributorInputData> distributors) {

        int min = 10000000;
        int id = -1;
        for (DistributorInputData distributor : distributors) {
            if (!distributor.getIsBankrupt()) {
                if (distributor.getContractPrice() < min) {
                    min = distributor.getContractPrice();
                    id = distributor.getId();
                }
            }
        }

        return id;
    }
    /**
     * Iterates through consumers, if the consumer is not bankrupt and has no other
     * contract, he is assigned a new contract
     */
    public void chooseDistributor(final ArrayList<ConsumerInputData> consumers,
                                  final ArrayList<DistributorInputData> distributors) {

        int id = findIdOfContractsMinimumValue(distributors);

        for (ConsumerInputData consumer : consumers) {
            // consumer is not bankrupt and has no other contract
            if (consumer.getRemainedContractMonths() == 0 && !consumer.getIsBankrupt()) {
                // update the clients list of the past distributor
                int oldId = consumer.getDistributorId();
                if (distributors.get(oldId).getContractsToConsumers().size() > 0) {
                    if (distributors.get(oldId).getContractsToConsumers()
                            .contains(consumer.getId())) {
                        distributors.get(oldId).getContractsToConsumers()
                                .remove(Integer.valueOf(consumer.getId()));
                    }
                }
                // set the new information of contract
                if (consumer.isIndebt()) {
                    consumer.setOldContractPrice(consumer.getContractPrice());
                    consumer.setOldDistributorId(consumer.getDistributorId());
                } else {
                    consumer.setOldContractPrice(distributors.get(id).getContractPrice());
                    consumer.setOldDistributorId(id);
                }

                consumer.setDistributorId(id);
                consumer.setContractPrice(distributors.get(id).getContractPrice());
                consumer.setRemainedContractMonths(distributors.get(id).getContractLength());
                distributors.get(id).addContractToConsumers(consumer.getId());
            }
        }
    }
    /**
     * Iterates through distributors, if distributor is looking for a production contract,
     * firstly he is removed from old producers clients list, then apply the distributor strategy
     */
    public void chooseProducer(final ArrayList<DistributorInputData> distributors,
                               final ArrayList<ProducerInputData> producers) {

        for (DistributorInputData distributor : distributors) {
            if (distributor.isChooseProductionContract() && !distributor.getIsBankrupt()) {
                // distributor is removed from old producers clients list
                for (ProducerInputData producer : producers) {
                    if (producer.getContracts().contains(distributor.getId())) {
                        producer.getContracts().remove(Integer.valueOf(distributor.getId()));
                        producer.setCurrentNrDistributors(producer.getCurrentNrDistributors() - 1);
                        distributor.getContractsToProducers()
                                .remove(Integer.valueOf(producer.getId()));
                    }
                }

                if (distributor.getProducerStrategy().equals("GREEN")) {
                    applyStrategy.chooseContract(new GreenStrategy(), distributor, producers);
                }

                if (distributor.getProducerStrategy().equals("PRICE")) {
                    applyStrategy.chooseContract(new PriceStrategy(), distributor, producers);
                }

                if (distributor.getProducerStrategy().equals("QUANTITY")) {
                    applyStrategy.chooseContract(new QuantityStrategy(), distributor, producers);
                }
                // Until the next modification of the producers with which it has a contract,
                // the distributor does not renew the contracts
                distributor.setChooseProductionContract(false);
            }
        }
    }
}
