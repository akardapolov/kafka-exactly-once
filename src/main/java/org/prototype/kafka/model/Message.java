package org.prototype.kafka.model;

public class Message
{
  private String messageType;

  private String messageUUID;

  private Source source;

  private String priority;

  private String contentType;

  private String content;

  public String getMessageType ()
  {
    return messageType;
  }

  public void setMessageType (String messageType)
  {
    this.messageType = messageType;
  }

  public String getMessageUUID ()
  {
    return messageUUID;
  }

  public void setMessageUUID (String messageUUID)
  {
    this.messageUUID = messageUUID;
  }

  public Source getSource ()
  {
    return source;
  }

  public void setSource (Source source)
  {
    this.source = source;
  }

  public String getPriority ()
  {
    return priority;
  }

  public void setPriority (String priority)
  {
    this.priority = priority;
  }

  public String getContentType ()
  {
    return contentType;
  }

  public void setContentType (String contentType)
  {
    this.contentType = contentType;
  }

  public String getContent ()
  {
    return content;
  }

  public void setContent (String content)
  {
    this.content = content;
  }

  @Override
  public String toString()
  {
    return "ClassPojo [messageType = "+messageType+", messageUUID = "+messageUUID+", source = "+source+", priority = "+priority+", contentType = "+contentType+", content = "+content+"]";
  }
}
