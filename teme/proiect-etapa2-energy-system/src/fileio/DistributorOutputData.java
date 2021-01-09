package fileio;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
/**
 * Entity that meets the output requirements for distributors
 */
public final class DistributorOutputData {
    private int id;
    private int energyNeededKW;
    private int contractCost;
    private int budget;
    private String producerStrategy;
    private boolean isBankrupt;
    private List<DistributorContractsOutputData> contracts;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public void setEnergyNeededKW(int energyNeededKW) {
        this.energyNeededKW = energyNeededKW;
    }

    public int getContractCost() {
        return contractCost;
    }

    public void setContractCost(int contractCost) {
        this.contractCost = contractCost;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(final int budget) {
        this.budget = budget;
    }

    public String getProducerStrategy() {
        return producerStrategy;
    }

    public void setProducerStrategy(String producerStrategy) {
        this.producerStrategy = producerStrategy;
    }

    public boolean isBankrupt() {
        return isBankrupt;
    }
    @JsonProperty("isBankrupt")
    public void setBankrupt(final boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public List<DistributorContractsOutputData> getContracts() {
        return contracts;
    }

    public void setContracts(final List<DistributorContractsOutputData> contracts) {
        this.contracts = contracts;
    }
}
