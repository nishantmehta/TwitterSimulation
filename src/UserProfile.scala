class UserProfile(
    var userID: String,
    var LastTweet: Tweet,
    var listOfFollowing: Map[String, String] = Map(),
    var numOfTweets: Int,
    var dbRef: TweetDataBase
    ) {
  
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
}