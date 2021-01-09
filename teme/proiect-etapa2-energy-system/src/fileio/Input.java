package fileio;

import java.util.List;

/**
 * The class contains information about input
 * <p>
 * DO NOT MODIFY
 */
public final class Input {
    /**
     * List of consumers
     */
    private final List<ConsumerInputData> consumersData;
    /**
     * List of distributors
     */
    private final List<DistributorInputData> distributorsData;
    /**
     * List of producers
     */
    private final List<ProducerInputData> producersData;
    /**
     * Number of turns
     */
    private final int numberOfTurns;
    /**
     * The new consumers per month
     */
    private final List<List<ConsumerInputData>> newConsumers;
    /**
     * The new costs per month
     */
    private final List<List<DistributorInputData>> distributorChanges;
    /**
     * The producers changes per month
     */
    private final List<List<ProducerInputData>> producerChanges;

    public Input() {
        this.consumersData = null;
        this.distributorsData = null;
        this.producersData = null;
        this.numberOfTurns = 0;
        this.newConsumers = null;
        this.distributorChanges = null;
        this.producerChanges = null;
    }

    public Input(final List<ConsumerInputData> consumers,
                 final List<DistributorInputData> distributors,
                 final List<ProducerInputData> producers,
                 final int numberOfTurns,
                 final List<List<ConsumerInputData>> newConsumers,
                 final List<List<DistributorInputData>> distributorChanges,
                 final List<List<ProducerInputData>> producerChanges) {
        this.consumersData = consumers;
        this.distributorsData = distributors;
        this.producersData = producers;
        this.numberOfTurns = numberOfTurns;
        this.newConsumers = newConsumers;
        this.distributorChanges = distributorChanges;
        this.producerChanges = producerChanges;
    }

    public List<ConsumerInputData> getConsumers() {
        return consumersData;
    }

    public List<DistributorInputData> getDistributors() {
        return distributorsData;
    }

    public List<ProducerInputData> getProducers() {
        return producersData;
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public List<List<ConsumerInputData>> getNewConsumers() {
        return newConsumers;
    }

    public List<List<DistributorInputData>> getDistributorChanges() {
        return distributorChanges;
    }

    public List<List<ProducerInputData>> getProducerChanges() {
        return producerChanges;
    }
}
