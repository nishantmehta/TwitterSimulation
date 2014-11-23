import akka.actor.Actor
import akka.actor.actorRef2Scala

class AddTweetWorker extends Actor {
  def receive = {
    case AddTweetWork(userId, tweet, twitterDB, client) => {
      var user: UserProfile = twitterDB.index(userId)
      user.addTweet(tweet)
      //client ! "hi"
      //println("hi")
    }
  }
}