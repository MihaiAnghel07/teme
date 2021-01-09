package fileio;

import java.util.ArrayList;
import java.util.List;

/**
 * Information about a producer, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class ProducerInputData {
    private int id;
    private final String energyType;
    private final int maxDistributors;
    private final double priceKW;
    private int energyPerDistributor;
    private int currentNrDistributors;
    /**
     * List of distributors ids
     */
    final List<Integer> contracts;
    final List<MonthlyStats> monthlyStats;

    public ProducerInputData(final int id, final String energyType, final int maxDistributors,
                             final double priceKW, final int energyPerDistributor) {
        this.id = id;
        this.energyType = energyType;
        this.maxDistributors = maxDistributors;
        this.priceKW = priceKW;
        this.energyPerDistributor = energyPerDistributor;
        this.contracts = new ArrayList<>();
        this.monthlyStats = new ArrayList<>();
    }

    public List<MonthlyStats> getMonthlyStats() {
        return monthlyStats;
    }
    /**
     * add monthlyStats entity to list
     */
    public void addMonthlyStats(final MonthlyStats monthlyStats) {
        this.monthlyStats.add(monthlyStats);
    }

    public int getCurrentNrDistributors() {
        return currentNrDistributors;
    }

    public void setCurrentNrDistributors(final int currentNrDistributors) {
        this.currentNrDistributors = currentNrDistributors;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public void setEnergyPerDistributor(final int energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }

    public int getId() {
        return id;
    }

    public String getEnergyType() {
        return energyType;
    }

    public int getMaxDistributors() {
        return maxDistributors;
    }

    public double getPriceKW() {
        return priceKW;
    }

    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public List<Integer> getContracts() {
        return contracts;
    }
    /**
     * add contract in list of contracts
     */
    public void addContract(final int contract) {
        this.contracts.add(contract);
    }

    /**
     * It helped me debugging
     */
    public String toString() {
        return "Producer{"
                + "id = " + id + '\''
                + ", energyType = " + energyType + '\''
                + ", maxDistributors = " + maxDistributors + '\''
                + ", priceKW = " + priceKW + '\''
                + ", energyPerDistributor = " + energyPerDistributor + '\''
                + ", MonthlyStats = " + monthlyStats + '}' + '\n';
    }
}
