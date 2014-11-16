
import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import scala.util.Random.nextInt
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
  
  //testing all services
  validateServerFunctions(twitterDB, config)
  
 
  def validateServerFunctions(twitterDB: TweetDataBase, config: TwitterConfig)  {
    var userID = nextInt(config.numberOfUsers)
    var user: UserProfile = twitterDB.index.getOrElse(83, null)
    user.print()
    var tempUser: UserProfile = null
    var listOfFollowing = user.getFollowingList
    for(temp <- listOfFollowing) {
      
      tempUser = twitterDB.index.getOrElse(temp, null)
      
      tempUser.addTweet(new Tweet("abc1234" + tempUser.userID, "this is tweet from user " + tempUser.userID, tempUser.userID, null, null, null, "tweet"))
    }
    
    var tweets1 :List[Tweet]= twitterDB.getLatestTweetFromAllFollowing(user.userID)
    
    for(x <- tweets1) {
      x.printTweet()
    }
    
    println("checked for the first time >>")
    var count = 0
    for(temp <- listOfFollowing if count <3) {
      tempUser = twitterDB.index.getOrElse(temp, null)
      tempUser.addTweet(new Tweet("123abc1234" + tempUser.userID, "this is tweet from user " + tempUser.userID, tempUser.userID, null, null, null, "tweet"))
      count += 1
    }
    tweets1 = twitterDB.getLatestTweetFromAllFollowing(user.userID)
    
    for(x <- tweets1) {
      x.printTweet()
    }
    println("checked for the second time >>")
  }
}



class ServerEndpoint extends Actor {

  def receive = {
    case TwitterRequestMessage(msgtype, msgcontent) => {
      //depending upon the request call a new function

    }

  }

}

 /*
  var user: UserProfile = twitterDB.index.getOrElse(83, null)
  user.print()
  var tweet :Tweet= null
  for(x <- 1 to 20){
    tweet = new Tweet("abc1234" + x, "this is tweet " + x, user.userID, null, null, null, "tweet")
    user.addTweet(tweet)
  }
  
  var tweets :List[Tweet] = user.getLastTenTweets()
  
  for(t <- tweets){
    t.printTweet()
  }
  
  println("user following " + user.getFollowingList.mkString(" , "))
  println("got " + tweets.size + " tweets from the user " + user.numOfTweets)
  */
  //create actor to handle request from client