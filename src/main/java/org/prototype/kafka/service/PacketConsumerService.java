package org.prototype.kafka.service;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.prototype.kafka.model.Packet;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PacketConsumerService {

  private AcceptDbService acceptDbService;

  public PacketConsumerService(AcceptDbService acceptDbService) {
    this.acceptDbService = acceptDbService;
  }

  @KafkaListener(topics = "#{'${spring.kafka.producer.topic}'}",
      groupId = "#{'${spring.kafka.producer.group}'}",
      containerFactory = "kafkaListenerConsumerFactory")
  public void getMessage(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
    log.info("Сообщение получено..");

    try {
      Packet packet = new Gson().fromJson(record.value(), Packet.class);

      if (!acceptDbService.isProcessedMessage(packet.getMessage().getMessageUUID())) {
        log.info("Сообщение " + packet.getMessage().getMessageUUID() + " обработано..");
        acceptDbService.proceedMessage(packet.getMessage().getMessageUUID(), record.value());
      } else {
        log.info("Сообщение " + packet.getMessage().getMessageUUID() + " уже было обработано..");
      }

    } catch (Exception e) {
      log.error("Ошибка при обработке сообщения.." + e.getMessage() + "..." + record.value(), e);
    } finally {
      acknowledgment.acknowledge();
    }
  }
}
