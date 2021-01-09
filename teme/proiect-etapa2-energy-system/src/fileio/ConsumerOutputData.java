package fileio;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Entity that meets the output requirements for consumers
 */
public final class ConsumerOutputData {
    private int id;
    private boolean isBankrupt;
    private int budget;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public boolean isBankrupt() {
        return isBankrupt;
    }
    @JsonProperty("isBankrupt")
    public void setBankrupt(final boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(final int budget) {
        this.budget = budget;
    }
}
