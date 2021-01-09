package fileio;

import java.util.ArrayList;
import java.util.List;

/**
 * Information about a distributor, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class DistributorInputData {
    /**
     * distributor id
     */
    private int id;
    /**
     * distributor contract length
     */
    private int contractLength;
    /**
     * distributor initial budget
     */
    private int initialBudget;
    /**
     * distributor initial infrastructure cost
     */
    private int initialInfrastructureCost;
    /**
     * distributor production cost
     */
    private int productionCost;
    /**
     * distributor energy needed
     */
    private int energyNeededKW;
    /**
     * strategy
     */
    private String producerStrategy;
    /**
     * The profit
     */
    private int profit;
    /**
     * The profit
     */
    private int contractPrice;
    /**
     * List of consumers ids
     */
    private final List<Integer> contractsToConsumers;
    /**
     * List of producers ids
     */
    private final List<Integer> contractsToProducers;
    /**
     * the variable helps us know if distributors are bankrupt or not
     */
    private boolean isBankrupt;
    /**
     * the variable helps us know if distributors must choose another production contract
     */
    private boolean chooseProductionContract;

    public DistributorInputData(final int id, final int contractLength,
                                final int initialBudget, final int initialInfrastructureCost,
                                final int energyNeededKW, final String producerStrategy) {
        this.id = id;
        this.contractLength = contractLength;
        this.initialBudget = initialBudget;
        this.initialInfrastructureCost = initialInfrastructureCost;
        this.energyNeededKW = energyNeededKW;
        this.producerStrategy = producerStrategy;
        this.contractsToConsumers = new ArrayList<>();
        this.contractsToProducers = new ArrayList<>();
    }

    public boolean isChooseProductionContract() {
        return chooseProductionContract;
    }

    public void setChooseProductionContract(boolean chooseProductionContract) {
        this.chooseProductionContract = chooseProductionContract;
    }

    public void setIsBankrupt(final boolean isBankrupt) {
        this.isBankrupt = isBankrupt;
    }

    public boolean getIsBankrupt() {
        return isBankrupt;
    }
    /**
     * add a contract to contractToConsumers list
     */
    public void addContractToConsumers(final int contract) {
        this.contractsToConsumers.add(contract);
    }

    public List<Integer> getContractsToConsumers() {
        return contractsToConsumers;
    }
    /**
     * add a contract to contractToProducers list
     */
    public void addContractsToProducers(final int contract) {
        this.contractsToProducers.add(contract);
    }

    public List<Integer> getContractsToProducers() {
        return contractsToProducers;
    }

    public int getNumberOfClients() {
        return contractsToConsumers.size();
    }

    public void setProfit(final int profit) {
        this.profit = profit;
    }

    public int getProfit() {
        return profit;
    }

    public int getTotalCosts() {
        return initialInfrastructureCost + productionCost * getNumberOfClients();
    }

    public void setContractPrice(final int contractPrice) {
        this.contractPrice = contractPrice;
    }

    public int getContractPrice() {
        return contractPrice;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public void setContractLength(final int contractLength) {
        this.contractLength = contractLength;
    }

    public void setInitialBudget(final int initialBudget) {
        this.initialBudget = initialBudget;
    }

    public void setInitialInfrastructureCost(final int initialInfrastructureCost) {
        this.initialInfrastructureCost = initialInfrastructureCost;
    }

    public void setEnergyNeededKW(final int energyNeededKW) {
        this.energyNeededKW = energyNeededKW;
    }

    public void setProducerStrategy(final String producerStrategy) {
        this.producerStrategy = producerStrategy;
    }

    public int getId() {
        return id;
    }

    public int getContractLength() {
        return contractLength;
    }

    public int getInitialBudget() {
        return initialBudget;
    }

    public int getInitialInfrastructureCost() {
        return initialInfrastructureCost;
    }

    public int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public String getProducerStrategy() {
        return producerStrategy;
    }

    public int getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(int productionCost) {
        this.productionCost = productionCost;
    }

    /**
     * It helped me debugging
     */
    public String toString() {
        return "DistributorInputData{"
                + "id = " + id + '\''
                + ", initialBudget = " + initialBudget + '\''
                + ", isBankrupt = " + isBankrupt + '\''
                + ", producerStrategy = " + producerStrategy + '\''
                + ", contracts = " + contractsToConsumers + '}' + '\n';
    }
}
