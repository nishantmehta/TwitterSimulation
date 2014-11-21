import akka.actor._
import akka.pattern.{ after, ask, pipe }

class FetchFollowingWorker extends Actor {
  def receive = {
    case FetchUpdateWork(userId, twitterDB, client) => {
      var tweets :List[Tweet]= twitterDB.getLatestTweetFromAllFollowing(userId)
      client ! FetUpdatesResponse(tweets)
    }
  }
}