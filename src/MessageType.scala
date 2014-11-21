import akka.actor.ActorRef

/*
 * All messages for communication to actors
 */

sealed trait TwitterServerMessages
case class startServer(twitterDB: TweetDataBase) extends TwitterServerMessages
case class startTestingClient(server: ActorRef, twitterDB: TweetDataBase, congif: TwitterConfig) extends TwitterServerMessages

//client communications
case class AddTweet(userId: Int, tweet: Tweet) extends TwitterServerMessages
case class fetchUpdate(userId: Int) extends TwitterServerMessages

case class AddTweetWork(userId: Int, tweet: Tweet, tweetDB: TweetDataBase, client: ActorRef) extends TwitterServerMessages
case class FetchUpdateWork(userId: Int, twittertDB: TweetDataBase, client: ActorRef) extends TwitterServerMessages

case class FetUpdatesResponse(tweets: List[Tweet]) extends TwitterServerMessages