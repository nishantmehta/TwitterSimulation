
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
import akka.actor.PoisonPill

object TwitterMain extends App {

  //initiate the database
  val config = new TwitterConfig
  val twitterDB = new TweetDataBase()
  twitterDB.initializeDB(config)

  val system = ActorSystem("twitterserver")
  val server = system.actorOf(Props[ServerEndpoint])

  //initiate endpoint
  server ! startServer(twitterDB)

  //testing all services
  val test = system.actorOf(Props[TestingClient])
  test ! startTestingClient(server, twitterDB, config)

}

class ServerEndpoint extends Actor {
  var twitterDB: TweetDataBase = null
  def receive = {
    case startServer(twitterDb) => {
      twitterDB = twitterDb
      println("server is running now")
    }
    case AddTweet(userId, tweet) => {
      val worker = context.actorOf(Props[AddTweetWorker])
      worker ! AddTweetWork(userId, tweet, twitterDB, sender)
      worker ! PoisonPill
    }
    case fetchUpdate(userId) => {
      val worker = context.actorOf(Props[FetchFollowingWorker])
      worker ! FetchUpdateWork(userId, twitterDB, sender)
      worker ! PoisonPill
    }
  }
}

class TestingClient() extends Actor {
  def receive = {
    case startTestingClient(server, twitterDB, config) => {
      var userID = nextInt(config.numberOfUsers)
      var user: UserProfile = twitterDB.index(userID)
      user.print()
      var tempUser: UserProfile = null
      var listOfFollowing = user.getFollowingList
      var tweet: Tweet = null
      for (temp <- listOfFollowing) {
        tweet = new Tweet("abc1234" + temp, "this is tweet from user " + temp, temp, null, null, null, "tweet")
        server ! AddTweet(temp, tweet)
      }
      server ! fetchUpdate(user.userID)

      println("checked for the first time >>")
      for (x <- 1 to 1000000) {

      }
      var count = 0
      for (temp <- listOfFollowing if count < 3) {
        tweet = new Tweet("123abc1234" + temp, "this is tweet from user " + temp, temp, null, null, null, "tweet")
        server ! AddTweet(temp, tweet)
        count += 1
      }
      server ! fetchUpdate(user.userID)

      println("checked for the second time >>")
    }
    case FetUpdatesResponse(tweets) => {
      for (x <- tweets) {
        x.printTweet()
      }
      println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
    }
  }

}

/*
  def validateServerFunctions(twitterDB: TweetDataBase, config: TwitterConfig)  {
    var userID = nextInt(config.numberOfUsers)
    var user: UserProfile = twitterDB.index(userID)
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
      tempUser = twitterDB.index(temp)
      tempUser.addTweet(new Tweet("123abc1234" + tempUser.userID, "this is tweet from user " + tempUser.userID, tempUser.userID, null, null, null, "tweet"))
      count += 1
    }
    tweets1 = twitterDB.getLatestTweetFromAllFollowing(user.userID)
    
    for(x <- tweets1) {
      x.printTweet()
    }
    println("checked for the second time >>")
  }

*/