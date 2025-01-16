package com.kush.dev.projectz.runnerz.run;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.asm.TypeReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class RunJsonDataLoader implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(RunJsonDataLoader.class);
    private final JdbcClientRunRepository runRepository;
    private final ObjectMapper objectMapper;

    public RunJsonDataLoader(JdbcClientRunRepository runRepository, ObjectMapper objectMapper) {
        this.runRepository = runRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) {
        if(runRepository.count() == 0){
            logger.info("Loading data from JSON file");
            try( InputStream inputStream = TypeReference.class.getResourceAsStream("/data/runs.json")){
                Runs allRuns = objectMapper.readValue(inputStream, Runs.class);
                logger.info("Reading {} runs from JSON file {} and saving into database", allRuns.runs().size(), allRuns);
                runRepository.saveAll(allRuns.runs());
            } catch (IOException e) {
                logger.error("Failed to load data from JSON file", e);
            }
        } else {
            logger.info("Data already loaded");
        }
    }
}
