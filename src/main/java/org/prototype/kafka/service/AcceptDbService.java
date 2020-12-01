package org.prototype.kafka.service;

import java.util.HashMap;
import org.springframework.stereotype.Component;

@Component
public class AcceptDbService {

  // Local in memory DB
  private HashMap<String, String> database;

  public AcceptDbService() {
    this.database = new HashMap<>();
  }

  public boolean isProcessedMessage(String key) {
    return database.get(key) != null;
  }

  public void proceedMessage(String key, String body) {
    this.database.put(key, body);
  }

}
