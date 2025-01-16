package com.kush.dev.projectz.runnerz.run;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RunRepositoryOld {

    private List<Run> runs = new ArrayList<>();

    List<Run> findAll(){
        return runs;
    }

    @PostConstruct
    private void init(){
        // Commented out to avoid conflicts with the new RunRepository
//        runs.add(new Run(1, "First Run", LocalDateTime.now(),  LocalDateTime.now().plusHours(1), 5, Location.OUTDOOR));
//        runs.add(new Run(2, "Second Run", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), 5, Location.INDOOR));
//        runs.add(new Run(3, "Third Run",  LocalDateTime.now(), LocalDateTime.now().plusMinutes(45), 5, Location.OUTDOOR));
    }

    public Optional<Run> findById(Integer id) {
        return runs.stream()
                .filter(run -> run.id().equals(id))
                .findFirst();
    }

    public void create(Run run) {
        runs.add(run);
    }

    public void delete(Integer id) {
        runs.removeIf(run -> run.id().equals(id));
    }

    public void update(Run run, Integer id) {
        Optional<Run> existingRun = findById(id);
        if(existingRun.isEmpty()){
            throw new RunNotFoundException("Run not found");
        }
        runs.remove(existingRun.get());
        runs.add(run);
    }
}
