package dataprocessing;

import fileio.ConsumerInputData;
import fileio.DistributorInputData;
import fileio.ProducerInputData;

import java.util.ArrayList;

/**
 * The class computes consumers and distributors monthly payments
 * */
public final class Payments {
    /**
     * According to the guide, the consumer pays or postpones
     * a rate and in the worst case he is declared bankrupt
     * */
    public void consumerPayments(final ArrayList<ConsumerInputData> consumers,
                                 final ArrayList<DistributorInputData> distributors) {

        double procent = 1.2; // from guide
        for (ConsumerInputData consumer : consumers) {
            if (!consumer.getIsBankrupt()) {
                if (consumer.getRemainedContractMonths() > 0) {
                    // if the consumer is not indebt
                    if (!consumer.isIndebt()) {
                        if (consumer.getInitialBudget() >= consumer.getContractPrice()) {
                            consumer.setInitialBudget(consumer.getInitialBudget()
                                    - consumer.getContractPrice());
                            int id = consumer.getDistributorId();
                            distributors.get(id).setInitialBudget(distributors.get(id)
                                    .getInitialBudget() + consumer.getContractPrice());
                        } else {
                            consumer.setIndebt(true);
                        }
                    } else {
                        // he must also pay the remaining month plus the penalty

                        int newContractPrice;
                        int flag = 0;
                        // first case: old distributor == current distributor
                        if (consumer.getDistributorId() == consumer.getOldDistributorId()) {
                            newContractPrice = (int) (Math.round(Math.floor(procent * consumer
                                    .getOldContractPrice())) + consumer.getContractPrice());
                        } else {
                            // second case
                            newContractPrice = (int) (Math.round(Math.floor(procent * consumer
                                    .getOldContractPrice())));
                            consumer.setIndebt(true);
                            flag = 1;
                        }

                        if (consumer.getInitialBudget() >= newContractPrice) {
                            consumer.setInitialBudget(consumer.getInitialBudget()
                                    - newContractPrice);
                            int id = consumer.getDistributorId();
                            distributors.get(id).setInitialBudget(distributors.get(id)
                                    .getInitialBudget() + newContractPrice);

                            if (flag == 0) {
                                consumer.setIndebt(false);
                            }
                            consumer.setOldContractPrice(consumer.getContractPrice());
                            consumer.setOldDistributorId(consumer.getDistributorId());
                        } else {
                            // he is declared bankrupt
                            consumer.setIsBankrupt(true);
                            consumer.setRemainedContractMonths(0);
                        }
                    }
                }
            }
        }
    }
    /**
     * According to the guide, the distributor pays infrastructure and production
     * monthly cost depending on the number of customers
     * */
    public void distributorPayments(final ArrayList<ConsumerInputData> consumers,
                                    final ArrayList<DistributorInputData> distributors,
                                    final ArrayList<ProducerInputData> producers) {

        for (DistributorInputData distributor : distributors) {
            if (!distributor.getIsBankrupt()) {
                distributor.setInitialBudget(distributor.getInitialBudget()
                            - distributor.getTotalCosts());

                if (distributor.getInitialBudget() < 0) {
                    distributor.setIsBankrupt(true);

                    // I update the contracts lists of energy producers and eliminate
                    // the bankrupt distributor
                    for (int i = 0; i < distributor.getContractsToProducers().size(); i++) {
                        int id = distributor.getContractsToProducers().get(i);
                        producers.get(id).getContracts().remove(Integer.valueOf(distributor
                                .getId()));
                        producers.get(id).setCurrentNrDistributors(producers.get(id)
                                .getCurrentNrDistributors() - 1);
                    }

                    //consumers on the list of the bankrupt distributor will have to choose
                    // another contract at the beginning of next month
                    for (int i = 0; i < distributor.getContractsToConsumers().size(); i++) {
                        consumers.get(distributor.getContractsToConsumers().get(i))
                                .setRemainedContractMonths(0);
                        consumers.get(distributor.getContractsToConsumers().get(i))
                                .setIsBankrupt(false);
                    }
                    distributor.getContractsToConsumers().clear();
                }
            }
        }
    }

}
