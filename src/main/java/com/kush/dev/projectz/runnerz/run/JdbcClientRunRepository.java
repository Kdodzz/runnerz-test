package com.kush.dev.projectz.runnerz.run;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcClientRunRepository {

    private static final Logger log = LoggerFactory.getLogger(JdbcClientRunRepository.class);
    private final JdbcClient jdbcClient;

    public JdbcClientRunRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Run> findAll(){
        return jdbcClient.sql("SELECT * FROM Run")
                .query(Run.class)
                .list();
    }

    public Optional<Run> findById(Integer id) {
        return jdbcClient.sql("SELECT * FROM Run WHERE id = :id")
                .param("id", id)
                .query(Run.class)
                .optional();
    }

    public void create(Run run) {
        int updated = jdbcClient.sql("INSERT INTO Run (id, title, started_on, completed_on, miles, location) " +
                        "VALUES (?, ?, ?, ?, ?, ?)")
                .params(List.of(run.id(), run.title(), run.startedOn(), run.completedOn(), run.miles(), run.location().toString()))
                .update();

        Assert.state(updated == 1, "Failed to create: " + run.title());
    }

    public void delete(Integer id) {
        int updated = jdbcClient.sql("DELETE FROM Run WHERE id = :id")
                .param("id", id)
                .update();
        Assert.state(updated == 1, "Failed to delete run: " + id);
    }

    public void update(Run run, Integer id) {
        findById(id).orElseThrow(() -> new RunNotFoundException("Run not found"));
        jdbcClient.sql("UPDATE Run SET id = ?, title = ?, started_on = ?, completed_on = ?, miles = ?, location = ? WHERE id = ?")
                .params(run.id(), run.title(), run.startedOn(), run.completedOn(), run.miles(), run.location().toString(), id)
                .update();
    }

    public int count() {
        return jdbcClient.sql("SELECT id FROM Run")
                .query()
                .listOfRows()
                .size();
    }

    public void saveAll(List<Run> runs) {
        runs.forEach(this::create);
    }

    public List<Run> findByLocation(Location location) {
       return jdbcClient.sql("SELECT * FROM Run WHERE location = :location")
                .param("location", location)
                .query(Run.class)
                .list();
    }


}
