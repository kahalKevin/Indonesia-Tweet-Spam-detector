from tweepy import Stream
from tweepy import OAuthHandler
from tweepy.streaming import StreamListener
import time
import json
#import _mysql
import MySQLdb



#        replace mysql.server with "localhost" if you are running via your own server!
#                        server       MySQL username	MySQL pass  Database name.
conn = MySQLdb.connect("localhost","root","","test")
conn.set_character_set('utf8')
c = conn.cursor()
c.execute('SET NAMES utf8;')
c.execute('SET CHARACTER SET utf8;')
c.execute('SET character_set_connection=utf8;')


#consumer key, consumer secret, access token, access secret.
ckey="CK0gGSHhzeBf9wlNNypAdEcRf"
csecret="yxC2dbArLBymTA83ZoZlqiSH8wLJKE1jtyASerxYCjLcBLwBPk"
atoken="3679939332-4PZExn7iAch3PQsFjKZTSJZsxJbGk9Zb4F4qwZq"
asecret="miqur5tI6XGVppJAAP1K54oQGBGG5wWtOTb6PufvWOLQN"

class listener(StreamListener):

    def on_data(self, data):
        all_data = json.loads(data)

        #getting data
        createdAt = all_data["created_at"]
        source = all_data["source"]
        tweet = all_data["text"]        
        username = all_data["user"]["screen_name"]
        userLocation = all_data["user"]["location"]
        followersCount = all_data["user"]["followers_count"]
        friendsCount = all_data["user"]["friends_count"]
        listedCount = all_data["user"]["listed_count"]
        favoritesCount = all_data["user"]["favourites_count"]
        statusesCount = all_data["user"]["statuses_count"]
        userCreatedAt = all_data["user"]["created_at"]
        timeZone = all_data["user"]["time_zone"]
        language = all_data["user"]["lang"]
        #userFollowing = all_data["user"]["following"]
        #userFollReq = all_data["user"]["follow_request_sent"]
        #geoCoordinates = all_data["geo"]["coordinates"]
        #coordinate = all_data["coordinates"]["coordinates"]
        placeType = all_data["place"]["place_type"]
        placeName = all_data["place"]["name"]
        placeFullName= all_data["place"]["full_name"]
        countryCode = all_data["place"]["country_code"]
        country = all_data["place"]["country"]
        #contributor = all_data["contributors"]
        #tweetRetweetCount = all_data["retweet_count"]
        #tweetFavoriteCount = all_data["favorite_count"]
        #favorited = all_data["favorited"]
        #retweeted = all_data["retweeted"]

        if countryCode=="ID":
            c.execute("INSERT INTO tweetstreamdata (id_timestamp, createdAt, source, tweet,username,user_location,followers_count,friends_count,listed_count,favorites_count,status_count,user_Created_at,time_zone,user_language,place_type,place_name,place_full_name,place_country_code,place_country) VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)",
                (time.time(), createdAt, source,tweet,username,userLocation,followersCount,friendsCount,listedCount,favoritesCount,statusesCount,userCreatedAt,timeZone,language,placeType,placeName,placeFullName,countryCode,country))
            conn.commit()

        #print((username,tweet))
        
        return True

    def on_error(self, status):
        print status

auth = OAuthHandler(ckey, csecret)
auth.set_access_token(atoken, asecret)

twitterStream = Stream(auth, listener())
twitterStream.filter(locations=[95,-11,141,6])
    
