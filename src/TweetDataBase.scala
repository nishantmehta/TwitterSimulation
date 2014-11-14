class TweetDataBase {
  /*
   * In memory database to store all tweets of the user. 
   * 
   */
  var index: Map[String, UserProfile] = Map()

  def initializeDB(scaleOfDb: Int) = {
    //create a DB with userID starting with 1 and going up till scaleOfDb
    //fill the follower table also based on stats
  }

  def getLatestTweetFromUser(userID: String) {
    //get latest tweets for a particular user
  }

  def addTweet(userID: String, tweet: Tweet) {

  }

  def getLatestTweetFromAllFollowing(userID: String) {

  }
}