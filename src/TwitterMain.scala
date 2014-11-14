
import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import scala.util.control.Breaks._
import java.security.MessageDigest
import scala.collection.SortedMap
import akka.actor.actorRef2Scala
import akka.actor.ActorRef
import akka.dispatch._
import scala.concurrent.Future
import scala.concurrent.Promise
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await


object TwitterMain extends App {

  //initiate the database
 
  //create actor to handle request from client
  
}



 class ServerEndpoint extends Actor {
   
	 def receive = {
	   case TwitterRequestMessage(msgtype, msgcontent) =>{
		   //depending upon the request call a new function
	     
	   }
	   
	 }
	 
  }