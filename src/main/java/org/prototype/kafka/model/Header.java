package org.prototype.kafka.model;

public class Header
{
  private Destination destination;

  private String transportUUID;

  public Destination getDestination ()
  {
    return destination;
  }

  public void setDestination (Destination destination)
  {
    this.destination = destination;
  }

  public String getTransportUUID ()
  {
    return transportUUID;
  }

  public void setTransportUUID (String transportUUID)
  {
    this.transportUUID = transportUUID;
  }

  @Override
  public String toString()
  {
    return "ClassPojo [destination = "+destination+", transportUUID = "+transportUUID+"]";
  }
}
