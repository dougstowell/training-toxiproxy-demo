package com.dougstowell.training.toxiproxy.demo.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.dougstowell.training.toxiproxy.demo.model.Event;

@Repository
public interface DbService extends CrudRepository<Event, String> {
}
