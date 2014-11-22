
import scala.util.Random.nextInt
import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.PoisonPill
import akka.actor.Props
import akka.actor.actorRef2Scala

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