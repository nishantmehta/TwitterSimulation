
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
  val server = system.actorOf(Props[ServerEndpoint], name = "serverendpoint")

  //initiate endpoint
  server ! startServer(twitterDB)

  //testing all services
  val test = system.actorOf(Props[TestingClient])
  //test ! startTestingClient(server, twitterDB, config)

}

class ServerEndpoint extends Actor {
  var twitterDB: TweetDataBase = null
  var time = System.currentTimeMillis()
  var count = 0
  def receive = {
    case msg: String => {
      println("got a msg from client")
    }
    case startServer(twitterDb) => {
      twitterDB = twitterDb
      println("server is running now")
    }
    case AddTweet(userId, tweet) => {
      //println("got a request")
      val worker = context.actorOf(Props[AddTweetWorker])
      worker ! AddTweetWork(userId, tweet, twitterDB, sender)
      worker ! PoisonPill
      checkCount()
    }
    case fetchUpdate(userId) => {
      val worker = context.actorOf(Props[FetchFollowingWorker])
      worker ! FetchUpdateWork(userId, twitterDB, sender)
      worker ! PoisonPill
      checkCount()
    }
  }
  
  def checkCount() = {
    count += 1
      //println("got one " + count)
      if(count % 200 == 0){
        if(((System.currentTimeMillis() - time)/1000)!=0)
        println("got 200 " +  " msg/sec " + count/((System.currentTimeMillis() - time)/1000))
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
        //server ! AddTweet(temp, tweet)
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