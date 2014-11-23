import scala.collection.mutable.ListBuffer
import scala.util.Random.nextInt

class TweetDataBase {
  /*
   * In memory database to store all tweets of the user. 
   * 
   */
  var index: Map[Int, UserProfile] = Map()

  def initializeDB(config: TwitterConfig) = {
    /*
     * create a DB with userID starting with 1 and going up till scaleOfDb
     * fill the follower table also based on stats
     * 20% of the user makes up for most part of the following list
     * thus first 20% of this range(scaleOfDb) will be seleced 98% of the time to fill the follower list
     * I am referring to this http://www.annouckwelhuis.nl/twitter-and-the-pareto-principle-2/
     */

    //make two set of users
    //users with high frequency of tweets
    var producers: List[Int] = List.range(0, (config.numberOfUsers * .2).toInt)
    //users with low frequency of tweets
    var consumers: List[Int] = List.range((config.numberOfUsers * .2).toInt, config.numberOfUsers)
    var user: UserProfile = null
    for (userID <- producers) {
      user = new UserProfile(userID, null, 0)
      index += (userID -> user)
      fillFollowing(user, config)
    }
    for (userID <- consumers) {
      user = new UserProfile(userID, null, 0)
      index += (userID -> user)
      fillFollowing(user, config)
    }
    println("DB is initialized with size of " + index.size)
  }

  //fills the DB with data for user following
  //this data is also filled using statistics
  def fillFollowing(user: UserProfile, config: TwitterConfig) {
    for (i <- 1 to config.avgNumOfFollower + 1) {
      if (i < config.numOfHFFollowing + 1) {
        user.listOfFollowing += (nextInt((config.numberOfUsers * .2).toInt) -> null)
      } else {
        user.listOfFollowing += (nextInt((config.numberOfUsers).toInt) -> null)
      }
    }
  }
  def getLatestTweetFromUser(userID: Int) {
    //get latest tweets for a particular user
    var user: UserProfile = index.getOrElse(userID, null)
    user.getLastTenTweets()
  }

  def addTweet(userID: Int, tweet: Tweet) {
    var user: UserProfile = index.getOrElse(userID, null)
    user.addTweet(tweet)
  }

  def getLatestTweetFromAllFollowing(userID: Int): List[Tweet] = {
    var user: UserProfile = index.getOrElse(userID, null)
    var listOfFollowing: Map[Int, String] = user.listOfFollowing
    var result = new ListBuffer[Tweet]
    var temp: UserProfile = null
    var flag: Boolean = true
    for ((id, lastTweet) <- listOfFollowing) {
      temp = index.getOrElse(id, null)
      for (tweet <- temp.getLastTenTweets if flag) {
        if (lastTweet != tweet.tweetID) {
          result += tweet
        } else {
          flag = false
        }
      }
      if(result.size != 0 && result.last != null)
    	  user.listOfFollowing += (id -> result.last.tweetID)
      flag = true
    }
    result.toList
  }
}