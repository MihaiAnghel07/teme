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
     * Before going through the 'n' months, an initial round is made
     */
    public void initialMonth(final ArrayList<ConsumerInputData> consumers,
                             final ArrayList<DistributorInputData> distributors,
                             final ArrayList<ProducerInputData> producers) {


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
    public void regularMonth(final ArrayList<ConsumerInputData> consumers,
                             final ArrayList<DistributorInputData> distributors,
                             final ArrayList<ProducerInputData> producers,
                             final List<List<ConsumerInputData>> newConsumers,
                             final List<List<DistributorInputData>> distributorsChanges,
                             final List<List<ProducerInputData>> producersChanges,
                             final int i) {

        updates.updateContractsLength(consumers);
        updates.updateDistributors(distributors, distributorsChanges.get(i));
        updates.updateConsumers(consumers, newConsumers.get(i));
        income.addMonthlyIncome(consumers);
        contracts.chooseDistributor(consumers, distributors);
        payments.consumerPayments(consumers, distributors);
        payments.distributorPayments(consumers, distributors, producers);
        updates.updateProducers(producers, producersChanges.get(i), distributors);
        formulasCompute.setFormulasResults(distributors, producers);
        contracts.chooseProducer(distributors, producers);
        updates.updateDistributorsList(distributors, consumers);
        updates.updateMonthlyStats(producers, i + 1);
    }

}
