class UserProfile(
    var userID: Int,
    var lastTweet: Tweet,
    var numOfTweets: Int
    ) {
  var listOfFollowing: Map[Int, String] = Map()
  def getFollowingList() = {
    //returns a list users followed 
    listOfFollowing.keySet
  }
  
  def getLastTenTweets() = {
    //return a list of tweet to the caller
  }
  
  def addTweet(tweet: Tweet) ={
    // code to add tweet to the end of linked list
  }
  
  def foundMention(userID: String, tweet: Tweet) {
    // code to add tweet for the user found in the mention
    //lets do this later
  }
  
  def print() {
    println("user ID is " + userID)
    println("last tweet is " + lastTweet)
    println("number of tweets " + numOfTweets)
    println("list of following " + listOfFollowing.mkString(" >> "))
  }
}