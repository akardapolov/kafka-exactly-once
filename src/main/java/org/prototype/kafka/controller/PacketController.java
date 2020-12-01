package org.prototype.kafka.controller;

import org.prototype.kafka.model.Packet;
import org.prototype.kafka.service.PacketProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PacketController {
  private PacketProducerService packetProducerService;

  public PacketController(PacketProducerService packetProducerService) {
    this.packetProducerService = packetProducerService;
  }

  @PostMapping(value="/post")
  public ResponseEntity<String> handleFileUpload(@RequestBody Packet packet) {
    return ResponseEntity.ok(packetProducerService.handlePacket(packet));
  }

}
