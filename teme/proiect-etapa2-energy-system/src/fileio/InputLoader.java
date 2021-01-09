package fileio;

import common.Constants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class InputLoader {
    /**
     * The path to the input file
     */
    private final String inputPath;

    public InputLoader(final String inputPath) {
        this.inputPath = inputPath;
    }

    public String getInputPath() {
        return inputPath;
    }

    /**
     * The method reads the database
     * @return an Input object
     */
    public Input readData() {

        JSONParser jsonParser = new JSONParser();
        List<ConsumerInputData> consumers = new ArrayList<>();
        List<DistributorInputData> distributors = new ArrayList<>();
        List<ProducerInputData> producers = new ArrayList<>();
        int nrTurns = 0;
        List<List<ConsumerInputData>> newConsumers = new ArrayList<>();
        List<List<DistributorInputData>> distributorChanges = new ArrayList<>();
        List<List<ProducerInputData>> producerChanges = new ArrayList<>();

        try {
            // Parsing the contents of the JSON file
            JSONObject jsonObject = (JSONObject) jsonParser
                    .parse(new FileReader(inputPath));
            nrTurns = Integer.parseInt((jsonObject)
                    .get(Constants.NUMBER_OF_TURNS).toString());
            JSONObject initialData = (JSONObject) jsonObject.get(Constants.INITIAL_DATA);
            JSONArray jsonConsumers = (JSONArray)
                    initialData.get(Constants.CONSUMERS);
            JSONArray jsonDistributors = (JSONArray)
                    initialData.get(Constants.DISTRIBUTORS);
            JSONArray jsonProducers = (JSONArray)
                    initialData.get(Constants.PRODUCERS);
            JSONArray jsonMonthlyUpdates = (JSONArray)
                    jsonObject.get(Constants.MONTHLY_UPDATES);


            if (jsonConsumers != null) {
                for (Object jsonConsumer : jsonConsumers) {
                    JSONObject js = ((JSONObject) (jsonConsumer));
                    consumers.add(new ConsumerInputData(
                            Integer.parseInt(js.get(Constants.ID).toString()),
                            Integer.parseInt(js.get(Constants.INITIAL_BUDGET).toString()),
                            Integer.parseInt(js.get(Constants.MONTHLY_INCOME).toString())
                    ));
                }
            } else {
                System.out.println("NU EXISTA CONSUMERS");
            }

            if (jsonDistributors != null) {
                for (Object jsonDistributor : jsonDistributors) {
                    JSONObject js = ((JSONObject) (jsonDistributor));
                    distributors.add(new DistributorInputData(
                            Integer.parseInt(js.get(Constants.ID).toString()),
                            Integer.parseInt(js.get(Constants.CONTRACT_LENGTH).toString()),
                            Integer.parseInt(js.get(Constants.INITIAL_BUDGET).toString()),
                            Integer.parseInt(js.get(Constants.INITIAL_INFRASTRUCTURE_COST)
                                    .toString()),
                            Integer.parseInt(js.get(Constants.ENERGY_NEEDED_KW).toString()),
                            js.get(Constants.PRODUCER_STRATEGY).toString()
                    ));
                }
            } else {
                System.out.println("NU EXISTA DISTRIBUTORS");
            }

            if (jsonProducers != null) {
                for (Object jsonProducer : jsonProducers) {
                    JSONObject js = ((JSONObject) (jsonProducer));
                    producers.add(new ProducerInputData(
                            Integer.parseInt(js.get(Constants.ID).toString()),
                            js.get(Constants.ENERGY_TYPE).toString(),
                            Integer.parseInt(js.get(Constants.MAX_DISTRIBUTORS).toString()),
                            Double.parseDouble(js.get(Constants.PRICE_KW).toString()),
                            Integer.parseInt(js.get(Constants.ENERGY_PER_DISTRIBUTOR)
                                    .toString())
                    ));
                }
            } else {
                System.out.println("NU EXISTA PRODUCERS");
            }

            int i = 0;
            for (Object jsonMonthlyUpdate : jsonMonthlyUpdates) {

                List<ConsumerInputData> newCons = new ArrayList<>();
                List<DistributorInputData> distrChg = new ArrayList<>();
                List<ProducerInputData> prodChg = new ArrayList<>();

                if (((JSONObject) jsonMonthlyUpdate).get(Constants.NEW_CONSUMERS) != null) {
                    for (Object iterator : (JSONArray) ((JSONObject) jsonMonthlyUpdate)
                            .get(Constants.NEW_CONSUMERS)) {
                        JSONObject js = ((JSONObject) (iterator));
                        newCons.add(new ConsumerInputData(
                                Integer.parseInt(js.get(Constants.ID).toString()),
                                Integer.parseInt(js.get(Constants.INITIAL_BUDGET).toString()),
                                Integer.parseInt(js.get(Constants.MONTHLY_INCOME).toString())
                        ));
                    }
                }

                if (((JSONObject) jsonMonthlyUpdate).get(Constants.DISTRIBUTOR_CHANGES) != null) {
                    for (Object iterator2 : (JSONArray) ((JSONObject) jsonMonthlyUpdate)
                            .get(Constants.DISTRIBUTOR_CHANGES)) {
                        JSONObject js = ((JSONObject) (iterator2));
                        distrChg.add(new DistributorInputData(
                                Integer.parseInt(js.get(Constants.ID).toString()),
                                0,
                                0,
                                Integer.parseInt(js.get(Constants.INFRASTRUCTURE_COST).toString()),
                                0,
                                null
                        ));
                    }
                }

                if (((JSONObject) jsonMonthlyUpdate).get(Constants.PRODUCER_CHANGES) != null) {
                    for (Object iterator3 : (JSONArray) ((JSONObject) jsonMonthlyUpdate)
                            .get(Constants.PRODUCER_CHANGES)) {
                        JSONObject js = ((JSONObject) (iterator3));
                        prodChg.add(new ProducerInputData(
                                Integer.parseInt(js.get(Constants.ID).toString()),
                                null,
                                0,
                                0,
                                Integer.parseInt(js.get(Constants.ENERGY_PER_DISTRIBUTOR).
                                        toString())
                        ));
                    }
                }

                if (newCons.size() > 0) {
                    newConsumers.add(i, newCons);
                } else {
                    newConsumers.add(i, null);
                }

                if (distrChg.size() > 0) {
                    distributorChanges.add(i, distrChg);
                } else {
                    distributorChanges.add(i, null);
                }

                if (prodChg.size() > 0) {
                    producerChanges.add(i, prodChg);
                } else {
                    producerChanges.add(i, null);
                }
                i++;
            }

            if (jsonConsumers == null) {
                consumers = null;
            }

            if (jsonDistributors == null) {
                distributors = null;
            }

            if (jsonProducers == null) {
                producers = null;
            }


        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        return new Input(consumers, distributors, producers,
                nrTurns, newConsumers, distributorChanges, producerChanges);
    }
}
