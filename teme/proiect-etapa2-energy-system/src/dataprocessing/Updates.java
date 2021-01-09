package dataprocessing;

import fileio.ConsumerInputData;
import fileio.DistributorInputData;
import fileio.MonthlyStats;
import fileio.ProducerInputData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * This class makes specific updates
 */
public final class Updates {
    private static Updates updateInstance = null;

    private Updates() { }
    /**
     * Use of singleton
     */
    public static Updates getInstance() {

        if (updateInstance == null) {
            updateInstance = new Updates();
        }

        return updateInstance;
    }
    /**
     * There are added new monthly consumers
     */
    public void updateConsumers(final ArrayList<ConsumerInputData> consumers,
                                final List<ConsumerInputData> newConsumers) {

        if (newConsumers != null) {
            consumers.addAll(newConsumers);
        }
    }
    /**
     * The distributors monthly prices are updated(if distributor is not bankrupt)
     */
    public void updateDistributors(final ArrayList<DistributorInputData> distributors,
                                   final List<DistributorInputData> distributorsChanges) {

        if (distributorsChanges != null) {
            for (DistributorInputData distributorChanges : distributorsChanges) {
                distributors.get(distributorChanges.getId()).setInitialInfrastructureCost(
                        distributorChanges.getInitialInfrastructureCost());
            }
        }
    }
    /**
     * The producers monthly energy is updated(if producer is not bankrupt)
     */
    public void updateProducers(final ArrayList<ProducerInputData> producers,
                                final List<ProducerInputData> producersChanges,
                                final ArrayList<DistributorInputData> distributors) {

        if (producersChanges != null) {
            for (ProducerInputData producerChanges : producersChanges) {
                producers.get(producerChanges.getId()).setEnergyPerDistributor(
                        producerChanges.getEnergyPerDistributor());

                List<Integer> observersIds = producers.get(producerChanges.getId())
                        .getContracts();
                // The observers of the producer that has undergone changes will have
                // to re-apply the strategy for choosing the contracts with the producers
                // I have to alert the observers:
                for (Integer observerId : observersIds) {
                    distributors.get(observerId).setChooseProductionContract(true);
                }
            }
        }
    }
    /**
     * If at the end of the month consumers are bankrupt and distributors are not
     * bankrupt, they are removed from their lists
     */
    public void updateDistributorsList(final ArrayList<DistributorInputData> distributors,
                                       final ArrayList<ConsumerInputData> consumers) {

        for (DistributorInputData distributor : distributors) {
            if (!distributor.getIsBankrupt()) {
                for (ConsumerInputData consumer : consumers) {
                    if (distributor.getContractsToConsumers().contains(consumer.getId())) {
                        if (consumer.getIsBankrupt()) {
                            distributor.getContractsToConsumers().remove(Integer
                                    .valueOf(consumer.getId()));
                        }
                    }
                }
            }
        }

    }
    /**
     * Consumers contract length is decreased(if consumer is not bankrupt)
     */
    public void updateContractsLength(final  ArrayList<ConsumerInputData> consumers) {

        for (ConsumerInputData consumer : consumers) {
            if (!consumer.getIsBankrupt()) {
                consumer.setRemainedContractMonths(consumer.getRemainedContractMonths() - 1);
            }
        }
    }
    /**
     * The method performs producers monthly stats update
     */
    public void updateMonthlyStats(final ArrayList<ProducerInputData> producers,
                                   final int month) {

        for (ProducerInputData producer : producers) {
            List<Integer> list = new ArrayList<>(producer.getContracts());
            list.sort(Comparator.comparing(Integer::byteValue));
            producer.addMonthlyStats(new MonthlyStats(month, list));
        }
    }
}
