package fileio;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class Output {
    private final ArrayList<ConsumerOutputData> consumerOutputData;
    private final ArrayList<DistributorOutputData> distributorOutputData;
    private final ArrayList<ProducerOutputData> producerOutputData;

    public Output() {
        this.consumerOutputData = new ArrayList<>();
        this.distributorOutputData = new ArrayList<>();
        this.producerOutputData = new ArrayList<>();
    }

    public ArrayList<ConsumerOutputData> getConsumerOutputData() {
        return consumerOutputData;
    }

    public ArrayList<DistributorOutputData> getDistributorOutputData() {
        return distributorOutputData;
    }

    public ArrayList<ProducerOutputData> getProducerOutputData() {
        return producerOutputData;
    }
    /**
     * Method builds consumers entity for output
     */
    public void buildConsumersOutput(final ArrayList<ConsumerInputData> consumers) {

        for (ConsumerInputData consumer : consumers) {
            ConsumerOutputData cons = new ConsumerOutputData();
            cons.setId(consumer.getId());
            cons.setBankrupt(consumer.getIsBankrupt());
            cons.setBudget(consumer.getInitialBudget());
            consumerOutputData.add(cons);
        }
    }
    /**
     * Method builds distributors entity for output
     */
    public void buildDistributorsOutput(final ArrayList<DistributorInputData> distributors,
                                        final ArrayList<ConsumerInputData> consumers) {

        for (DistributorInputData distributor : distributors) {
            DistributorOutputData dist = new DistributorOutputData();
            dist.setId(distributor.getId());
            dist.setEnergyNeededKW(distributor.getEnergyNeededKW());
            dist.setContractCost(distributor.getContractPrice());
            dist.setBudget(distributor.getInitialBudget());
            dist.setProducerStrategy(distributor.getProducerStrategy());
            dist.setBankrupt(distributor.getIsBankrupt());

            List<DistributorContractsOutputData> distributorContracts = new ArrayList<>();
            for (int i = 0; i < distributor.getContractsToConsumers().size(); i++) {
                DistributorContractsOutputData contract = new DistributorContractsOutputData();
                contract.setConsumerId(consumers.get(distributor.getContractsToConsumers().get(i))
                        .getId());
                contract.setPrice(consumers.get(distributor.getContractsToConsumers().get(i))
                        .getContractPrice());
                contract.setRemainedContractMonths(consumers.get(distributor
                        .getContractsToConsumers().get(i)).getRemainedContractMonths());
                distributorContracts.add(contract);
            }
            dist.setContracts(distributorContracts);
            distributorOutputData.add(dist);
        }
    }
    /**
     * Method builds producers entity for output
     */
    public void buildProducersOutput(final ArrayList<ProducerInputData> producers) {

        producers.sort(Comparator.comparing(ProducerInputData::getId));
        for (ProducerInputData producer : producers) {
            ProducerOutputData prod = new ProducerOutputData();
            prod.setId(producer.getId());
            prod.setMaxDistributors(producer.getMaxDistributors());
            prod.setPriceKW(producer.getPriceKW());
            prod.setEnergyType(producer.getEnergyType());
            prod.setEnergyPerDistributor(producer.getEnergyPerDistributor());
            prod.setMonthlyStats(producer.getMonthlyStats());
            producerOutputData.add(prod);
        }
    }
}
