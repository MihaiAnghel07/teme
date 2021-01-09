package fileio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.util.ArrayList;

public final class OutputLoader {
    /**
     * The path to the output file
     */
    private final File outputPath;
    private ArrayList<ConsumerOutputData> consumers;
    private ArrayList<DistributorOutputData> distributors;
    private ArrayList<ProducerOutputData> producers;

    public OutputLoader(final File outputPath,
                        final ArrayList<ConsumerOutputData> consumers,
                        final ArrayList<DistributorOutputData> distributors,
                        final ArrayList<ProducerOutputData> producers) {

        this.outputPath = outputPath;
        this.consumers = consumers;
        this.distributors = distributors;
        this.producers = producers;
    }
    @JsonIgnore
    public File getOutputPath() {
        return outputPath;
    }

    public ArrayList<ConsumerOutputData> getConsumers() {
        return consumers;
    }

    public void setConsumers(final ArrayList<ConsumerOutputData> consumers) {
        this.consumers = consumers;
    }

    public ArrayList<DistributorOutputData> getDistributors() {
        return distributors;
    }

    public void setDistributors(final ArrayList<DistributorOutputData> distributors) {
        this.distributors = distributors;
    }

    public void setProducers(final ArrayList<ProducerOutputData> producers) {
        this.producers = producers;
    }
    /**
     * The method returns the entity for producers output
     */
    public ArrayList<ProducerOutputData> getenergyProducers() {
        return producers;
    }
    /**
     * The method writes the database
     */
    public void writeData() {

        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try {
            objectMapper.writeValue(outputPath, this);
        } catch (Exception e) {
            e.fillInStackTrace();
        }

    }
}
