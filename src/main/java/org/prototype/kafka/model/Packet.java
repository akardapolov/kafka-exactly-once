package org.prototype.kafka.model;

public class Packet
{
  private Header header;

  private Message message;

  public Header getHeader ()
  {
    return header;
  }

  public void setHeader (Header header)
  {
    this.header = header;
  }

  public Message getMessage ()
  {
    return message;
  }

  public void setMessage (Message message)
  {
    this.message = message;
  }

  @Override
  public String toString()
  {
    return "ClassPojo [header = "+header+", message = "+message+"]";
  }
}
