
import java.util.Formatter.DateTime

class Tweet(
  var nextTweet: Tweet,
  var previousTweet: Tweet,
  var mentions: List[String],
  var tweet: String,
  var tweetID: String,
  var userID: Int,
  var tweetType: String) {
  
  def printTweet() = {
    println("tweet ID is " + tweetID)
    println("this was tweeted by " + userID)
    println("tweet text is " + tweet)
    println("tweet type is " + tweetType)
    println("people mentioned in this tweet are " + mentions.mkString(" - "))
  }
  
  

}