import akka.actor._
import akka.pattern.{ after, ask, pipe }

class AddTweetWorker extends Actor {
 val name: String ="hi"
  def receive = {
    case AddTweetWork(userId, tweet, twitterDB, client) => {
      var user: UserProfile = twitterDB.index(userId)
      user.addTweet(tweet)
      client ! tweet
    }
  }
}