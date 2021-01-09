package dataprocessing;

import fileio.ConsumerInputData;

import java.util.ArrayList;

/**
 * The class contains a method that add consumers monthly income
 */
public final class Income {
    /**
     * If consumer is not bankrupt, he takes the income
     */
    public void addMonthlyIncome(final ArrayList<ConsumerInputData> consumers) {

        for (ConsumerInputData consumer : consumers) {
            if (!consumer.getIsBankrupt()) {
                consumer.setInitialBudget(consumer.getInitialBudget()
                        + consumer.getMonthlyIncome());
            }
        }

    }
}
