package fileio;

/**
 * Information about a consumer, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class ConsumerInputData {
    /**
     * consumer id
     */
    private int id;
    /**
     * consumer initial budget
     */
    private int initialBudget;
    /**
     * consumer monthly income
     */
    private int monthlyIncome;
    /**
     * it will help us to know if consumer has already postponed the payment
     */
    private boolean isIndebt;
    /**
     * Id of current contract
     */
    private int distributorId;
    /**
     * the remaining length of contract (if it is 0 -> the consumer has no contract)
     */
    private int remainedContractMonths;
    /**
     * Value of current contract
     */
    private int contractPrice;
    /**
     * Value of current old contract
     */
    private int oldContractPrice;
    /**
     * The variable helps us know if consumers are bankrupt or not
     */
    private boolean isBankrupt;
    /**
     * Old distributor id
     */
    private int oldDistributorId;

    public ConsumerInputData(final int id, final int initialBudget, final int monthlyIncome) {
        this.id = id;
        this.initialBudget = initialBudget;
        this.monthlyIncome = monthlyIncome;
    }

    public int getOldDistributorId() {
        return oldDistributorId;
    }

    public void setOldDistributorId(int oldDistributorId) {
        this.oldDistributorId = oldDistributorId;
    }

    public int getOldContractPrice() {
        return oldContractPrice;
    }

    public void setOldContractPrice(final int oldContractPrice) {
        this.oldContractPrice = oldContractPrice;
    }

    public void setIsBankrupt(final boolean isBankrupt) {
        this.isBankrupt = isBankrupt;
    }

    public boolean getIsBankrupt() {
        return isBankrupt;
    }

    public void setContractPrice(final int contractPrice) {
        this.contractPrice = contractPrice;
    }

    public int getContractPrice() {
        return contractPrice;
    }

    public void setRemainedContractMonths(final int remainedContractMonths) {
        this.remainedContractMonths = remainedContractMonths;
    }

    public int getRemainedContractMonths() {
        return remainedContractMonths;
    }
    /**
     * method sets distributor id
     */
    public void setDistributorId(final int distributorId) {
        this.distributorId = distributorId;
    }

    public int getDistributorId() {
        return distributorId;
    }

    public void setIndebt(final boolean isIndept) {
        this.isIndebt = isIndept;
    }

    public boolean isIndebt() {
        return isIndebt;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public void setInitialBudget(final int initialBudget) {
        this.initialBudget = initialBudget;
    }

    public void setMonthlyIncome(final int monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public int getId() {
        return id;
    }

    public int getInitialBudget() {
        return initialBudget;
    }

    public int getMonthlyIncome() {
        return monthlyIncome;
    }
    /**
     * It Helped me debugging
     */
    public String toString() {
        return "ConsumerInputData{"
                + "id = " + id + '\''
                + ", isBankrupt = " + isBankrupt + '\''
                + ", remainedContract = " + remainedContractMonths + '\''
                + ", initialBudget = " + initialBudget + '}' + '\n';
    }
}
