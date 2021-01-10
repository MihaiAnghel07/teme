
import dataprocessing.Solve;
import fileio.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
/**
 * Entry point to the simulation
 */
public final class Main {

    private Main() { }

    /**
     * Main function which reads the input file and starts simulation
     *
     * @param args input and output files
     * @throws Exception might error when reading/writing/opening files, parsing JSON
     */
    public static void main(final String[] args) throws Exception {

        InputLoader inputLoader = new InputLoader(args[0]);
        Input input = inputLoader.readData();

        // Parse input data
        int nrTurns = input.getNumberOfTurns();
        ArrayList<ConsumerInputData> consumers = new ArrayList<>(input.getConsumers());
        ArrayList<DistributorInputData> distributors = new ArrayList<>(input.getDistributors());
        ArrayList<ProducerInputData> producers = new ArrayList<>(input.getProducers());
        List<List<ConsumerInputData>> newConsumers = new ArrayList<>(input.getNewConsumers());
        List<List<DistributorInputData>> distributorsChanges = new ArrayList<>(input
                .getDistributorChanges());
        List<List<ProducerInputData>> producersChanges = new ArrayList<>(input
                .getProducerChanges());

        //start the simulation
        Solve solve = Solve.getInstance();
        solve.startSimulation(nrTurns, consumers, distributors, producers,
                newConsumers, distributorsChanges, producersChanges);

        // I build the output entities
        Output output = new Output();
        output.buildConsumersOutput(consumers);
        output.buildDistributorsOutput(distributors, consumers);
        output.buildProducersOutput(producers);

        // Parse data to output
        File outputFile = new File(args[1]);
        OutputLoader outputLoader = new OutputLoader(outputFile,
                                                     output.getConsumerOutputData(),
                                                     output.getDistributorOutputData(),
                                                     output.getProducerOutputData());
        outputLoader.writeData();
    }
}
