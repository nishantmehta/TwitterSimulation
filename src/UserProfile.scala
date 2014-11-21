import scala.collection.mutable.ListBuffer

class UserProfile(
  var userID: Int,
  var lastTweet: Tweet,
  var numOfTweets: Int) {

  var listOfFollowing: Map[Int, String] = Map()

  def getFollowingList() = {
    //returns a list users followed 
    listOfFollowing.keySet.toList
  }

  def getLastTenTweets() = {
    //return a list of tweet to the caller
    var count = 1
    var tweet: Tweet = lastTweet
    var tweets = new ListBuffer[Tweet]
    while (count <= 10 && count <= numOfTweets) {
      tweets += tweet
      tweet = tweet.previousTweet
      count += 1
    }
    tweets.toList
  }

  def addTweet(tweet: Tweet) = {
    // code to add tweet to the end of linked list
    numOfTweets += 1
    if (lastTweet != null) {
      lastTweet.nextTweet = tweet
      tweet.previousTweet = lastTweet
    }
    lastTweet = tweet
  }

  def foundMention(userID: String, tweet: Tweet) {
    //code to add tweet for the user found in the mention
    //lets do this later
  }

  def print() {
    println("user ID is " + userID)
    println("last tweet is " + lastTweet)
    println("number of tweets " + numOfTweets)
    println("list of following " + listOfFollowing.mkString(" >> "))
    println("number of user following " + listOfFollowing.size)
  }
}