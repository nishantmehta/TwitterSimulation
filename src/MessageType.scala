
/*
 * All messages for communication to actors
 */

sealed trait TwitterServerMessages
case class work extends TwitterServerMessages
case class TwitterRequestMessage(msgtype: String, msgcontent: String) extends TwitterServerMessages
