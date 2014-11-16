
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
import scala.collection.mutable.ListBuffer

object TwitterMain extends App {

  //initiate the database
  val config = new TwitterConfig
  val twitterDB = new TweetDataBase()
  twitterDB.initializeDB(config)
  
  var user: UserProfile = twitterDB.index.getOrElse(83, null)
  user.print()
  var tweet :Tweet= null
  for(x <- 1 to 20){
    tweet = new Tweet("abc1234" + x, "this is tweet " + x, user.userID, null, null, null, "tweet")
    user.addTweet(tweet)
  }
  
  var tweets :List[Tweet] = user.getLastTenTweets()
  
  //for(t <- tweets){
  //  t.printTweet()
  //}
  
  println("user following " + user.getFollowingList.mkString(" , "))
  println("got " + tweets.size + " tweets from the user " + user.numOfTweets)
  
  //create actor to handle request from client

}



class ServerEndpoint extends Actor {

  def receive = {
    case TwitterRequestMessage(msgtype, msgcontent) => {
      //depending upon the request call a new function

    }

  }

}