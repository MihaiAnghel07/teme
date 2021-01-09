package dataprocessing;

import fileio.DistributorInputData;
import fileio.ProducerInputData;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * The class contains four methods:
 * First method compute production cost according to guide
 * Second method computes the contract price according to guide
 * Third method computes the profit according to guide
 * The last one sets the results of the first three methods in the distributor fields
 */
public final class FormulasCompute {
    /**
     * the production cost formula from guide is applied
     */
    private int getProductionCost(final DistributorInputData distributor,
                                  final ArrayList<ProducerInputData> producers) {

        producers.sort(Comparator.comparing(ProducerInputData::getId));
        double cost = 0;
         for (int i = 0; i < distributor.getContractsToProducers().size(); i++) {
             int id = distributor.getContractsToProducers().get(i);
             cost += producers.get(id).getEnergyPerDistributor()
                     * producers.get(id).getPriceKW();
         }

         return (int) Math.round(Math.floor(cost / 10));
    }
    /**
     * the contract price formula from guide is applied
     */
    private int getContractPrice(final DistributorInputData distributor) {

        double contractPrice;
        if (distributor.getNumberOfClients() > 0) {
            contractPrice = (int) Math.round(Math.floor(((double) distributor
                    .getInitialInfrastructureCost() / distributor.getNumberOfClients()))
                    + distributor.getProductionCost() + getProfit(distributor));
        } else {
            contractPrice = distributor.getInitialInfrastructureCost() + distributor
                    .getProductionCost() + getProfit(distributor);
        }

        return (int) contractPrice;
    }
    /**
     * the profit formula from guide is applied
     */
    private int getProfit(final DistributorInputData distributor) {

        return (int) Math.round(Math.floor(0.2 * distributor.getProductionCost()));
    }
    /**
     * If distributor is not bankrupt, its data is set
     */
    public void setFormulasResults(final ArrayList<DistributorInputData> distributors,
                                          final ArrayList<ProducerInputData> producers) {

        for (DistributorInputData distributor : distributors) {
            if (!distributor.getIsBankrupt()) {
                distributor.setProductionCost(getProductionCost(distributor, producers));
                distributor.setContractPrice(getContractPrice(distributor));
                distributor.setProfit(getProfit(distributor));
            }
        }
    }
}
