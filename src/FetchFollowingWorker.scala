import akka.actor.Actor
import akka.actor.actorRef2Scala

class FetchFollowingWorker extends Actor {
  def receive = {
    case FetchUpdateWork(userId, twitterDB, client) => {
      var tweets: List[Tweet] = twitterDB.getLatestTweetFromAllFollowing(userId)
      client ! FetUpdatesResponse(tweets)
    }
  }
}