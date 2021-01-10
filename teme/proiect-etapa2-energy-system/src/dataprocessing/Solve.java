package dataprocessing;

import fileio.ConsumerInputData;
import fileio.DistributorInputData;
import fileio.ProducerInputData;

import java.util.ArrayList;
import java.util.List;

/**
 * The class contains three methods that solves monthly rounds
 */
public final class Solve {
    private final FormulasCompute formulasCompute;
    private final Income income;
    private final Contracts contracts;
    private final Payments payments;
    private final Updates updates;
    private static Solve solveInstance = null;

    /**
     * Use of singleton
     */
    public static Solve getInstance() {

        if (solveInstance == null) {
            solveInstance = new Solve();
        }

        return solveInstance;
    }
    /**
     * This is the constructor
     */
    private Solve() {

        this.formulasCompute = new FormulasCompute();
        this.income = new Income();
        this.contracts = new Contracts();
        this.payments = new Payments();
        this.updates = Updates.getInstance(); //singleton
    }
    /**
     * Here is the simulation of system
     */
    public void startSimulation(final int nrTurns,
                                final ArrayList<ConsumerInputData> consumers,
                                final ArrayList<DistributorInputData> distributors,
                                final ArrayList<ProducerInputData> producers,
                                final List<List<ConsumerInputData>> newConsumers,
                                final List<List<DistributorInputData>> distributorsChanges,
                                final List<List<ProducerInputData>> producersChanges) {


        CheckBankrupt checkBankrupt = new CheckBankrupt();
        Updates updates = Updates.getInstance();
        Solve solve = Solve.getInstance();

        // Initial month
        solve.initialMonth(consumers, distributors, producers);

        // Regular months
        for (int i = 0; i < nrTurns; i++) {
            solve.regularMonth(consumers, distributors, producers, newConsumers,
                    distributorsChanges, producersChanges, i);
            if (checkBankrupt.checkDistributorsBankrupt(distributors)) {
                break;
            }
        }
        // I must update contracts length
        updates.updateContractsLength(consumers);
    }

    /**
     * Before going through the 'n' months, an initial round is made
     */
    private void initialMonth(final ArrayList<ConsumerInputData> consumers,
                             final ArrayList<DistributorInputData> distributors,
                             final ArrayList<ProducerInputData> producers) {

        // The distributors choose their production contracts
        // The distributors calculate the monthly profit, contract price, and production cost
        // The consumers get the salary
        // The consumers choose favorable contract
        // The consumers pays contract rate
        // The distributors pay their bill

        // I set the flag so I can assign the initial contracts
        for (DistributorInputData distributor : distributors) {
            distributor.setChooseProductionContract(true);
        }

        contracts.chooseProducer(distributors, producers);
        formulasCompute.setFormulasResults(distributors, producers);
        income.addMonthlyIncome(consumers);
        contracts.chooseDistributor(consumers, distributors);
        payments.consumerPayments(consumers, distributors);
        payments.distributorPayments(consumers, distributors, producers);

    }
    /**
     * Compute regular month commands
     */
    private void regularMonth(final ArrayList<ConsumerInputData> consumers,
                             final ArrayList<DistributorInputData> distributors,
                             final ArrayList<ProducerInputData> producers,
                             final List<List<ConsumerInputData>> newConsumers,
                             final List<List<DistributorInputData>> distributorsChanges,
                             final List<List<ProducerInputData>> producersChanges,
                             final int i) {

        // Updates are made (contract length decreases, new consumers are
        // added to the game and distributors set their new infrastructure cost)
        // The distributors calculate the monthly profit, contract price, and production cost
        // The consumers get the salary
        // The consumers choose favorable contract
        // The consumers pays contract rate
        // The distributors pay their bill
        // The producers are updated and Observer design pattern is used
        // The distributors choose their production contract
        // If at the end of the month consumers are bankrupt and distributors
        // are not bankrupt, the bankrupt consumers are removed from distributors lists

        // start of month
        updates.updateContractsLength(consumers);
        updates.updateDistributors(distributors, distributorsChanges.get(i));
        updates.updateConsumers(consumers, newConsumers.get(i));
        formulasCompute.setFormulasResults(distributors, producers);
        income.addMonthlyIncome(consumers);
        contracts.chooseDistributor(consumers, distributors);
        payments.consumerPayments(consumers, distributors);
        payments.distributorPayments(consumers, distributors, producers);

        // mid-month
        updates.updateProducers(producers, producersChanges.get(i), distributors);

        // end of month
        contracts.chooseProducer(distributors, producers);
        updates.updateDistributorsList(distributors, consumers);
        updates.updateMonthlyStats(producers, i + 1);
    }

}
