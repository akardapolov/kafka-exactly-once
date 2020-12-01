package org.prototype.kafka.service;

import lombok.extern.slf4j.Slf4j;
import org.prototype.kafka.model.Packet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Slf4j
public class PacketProducerService {

  @Value("${spring.kafka.producer.topic}")
  private String kafkaTopic;

  private final KafkaTemplate<String, Packet> kafkaTemplate;

  public PacketProducerService(KafkaTemplate<String, Packet> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @Async
  public String handlePacket(Packet packet){
    var future = sendResultListenableFuture(packet);

    future.addCallback(new ListenableFutureCallback<>() {

      @Override
      public void onSuccess(SendResult<String, Packet> result) {
        log.info("Документ:" +
            result.getProducerRecord().value().getMessage().getMessageUUID() + " отправлен..");
      }

      @Override
      public void onFailure(Throwable ex) {
        log.error("Ошибка: " + ex.getMessage());
      }
    });

    return "Пакет " + packet.getMessage().getMessageUUID() + " обработан..";
  }

  public ListenableFuture<SendResult<String, Packet>> sendResultListenableFuture(
      Packet data) {
   /* return kafkaTemplate.executeInTransaction(tr ->
        tr.send(String.valueOf(data.getMessage().getMessageUUID().hashCode()), data));*/
        //todo Timeout expired after 60000milliseconds while awaiting InitProducerId

    return kafkaTemplate.send(kafkaTopic,
        String.valueOf(data.getMessage().getMessageUUID().hashCode()),
        data);
  }

}
