package br.com.fiap.tweets;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.fiap.constants.Constants;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TweetAnalytics {

	public static Status writeTweet(String tweet, Twitter twitter) throws TwitterException{
		
		return twitter.updateStatus(tweet);
	}
	
	
	public List<Status> findTweets(String hashTag, String date, Twitter twitter) throws TwitterException{
		
		List<Status> tweets = new ArrayList<>();
		QueryResult result;
		Query query = new Query(hashTag);
		query.setSince(date);
		
		do{
		
			result = twitter.search(query);
		
			result.getTweets().forEach(r -> tweets.add(r));
			
		}while((query = result.nextQuery()) != null);
		
		return tweets;
	}
	
	public List<Status> findRetweets(String hashTag, String date, Twitter twitter) throws TwitterException{
		
		List<Status> tweets = new ArrayList<>();
		List<Status> retweets = new ArrayList<>();
		QueryResult result;
		Query query = new Query(hashTag);
		query.setSince(date);
		
		do{
		
			result = twitter.search(query);
		
			tweets = result.getTweets().stream()
						.filter(r -> r.getRetweetCount() >= 1)
						.collect(Collectors.toList());
			
			tweets.forEach(t -> retweets.add(t));
			
		}while((query = result.nextQuery()) != null);
		
		return retweets;
	}
	
	public List<Status> findFavorites(String hashTag, String date, Twitter twitter) throws TwitterException{
		
		List<Status> tweets = new ArrayList<>();
		List<Status> favorites = new ArrayList<>();
		QueryResult result;
		Query query = new Query(hashTag);
		query.setSince(date);
		
		do{
		
			result = twitter.search(query);
		
			tweets = result.getTweets().stream()
						.filter(r -> r.getFavoriteCount() >= 1)
						.collect(Collectors.toList());
			
			tweets.forEach(t -> favorites.add(t));
			
		}while((query = result.nextQuery()) != null);
		
		return favorites;
	}
	
	public void tweetAnalysisPerWeek(String hashtag, Twitter twitter) throws TwitterException{
		LocalDate now = LocalDate.now();
		LocalDate week = now.minusWeeks(Constants.NUMBER_WEEKS);
		System.out.print("Tweets analisados - De: " + week);
		System.out.println(" até " + now);
		
		List<Status> tweets = findTweets(hashtag, week.toString(), twitter);
		
		
		Map<String, Integer> tweetWeek = new HashMap<>();
		
		for (Status tweet : tweets) {
			
			LocalDate created = tweet.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			
			if((created.isEqual(now) || created.isBefore(now)) && created.isAfter(week)){
				
				DateTimeFormatter format = DateTimeFormatter.ofPattern("EEEE");
				String weekDay = created.format(format);
				int count = 0;
				if(!tweetWeek.containsKey(weekDay)){
					count += 1;
					tweetWeek.put(weekDay, count);
				}else{
					count = tweetWeek.get(weekDay);
					tweetWeek.remove(weekDay);
					count += 1;
					tweetWeek.put(weekDay, count);
					
				}
			}
		}
		
		for (Map.Entry<String,Integer> countWeek : tweetWeek.entrySet()) {
		    System.out.println(countWeek.getKey());
		    System.out.println(countWeek.getValue());
		}
		
	}
	
	public void retweetAnalysisPerWeek(String hashtag, Twitter twitter) throws TwitterException{
		LocalDate now = LocalDate.now();
		LocalDate week = now.minusWeeks(Constants.NUMBER_WEEKS);
		System.out.print("Retweets analisados - De: " + week);
		System.out.println(" até " + now);
		
		List<Status> retweets = findRetweets(hashtag, week.toString(), twitter);
		
		
		Map<String, Integer> retweetWeek = new HashMap<>();
		
		for (Status retweet : retweets) {
			
			LocalDate created = retweet.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			
			if((created.isEqual(now) || created.isBefore(now)) && created.isAfter(week)){
				
				DateTimeFormatter format = DateTimeFormatter.ofPattern("EEEE");
				String weekDay = created.format(format);
				int count = 0;
				if(!retweetWeek.containsKey(weekDay)){
					count += 1;
					retweetWeek.put(weekDay, count);
				}else{
					count = retweetWeek.get(weekDay);
					retweetWeek.remove(weekDay);
					count += 1;
					retweetWeek.put(weekDay, count);
					
				}
			}
		}
		
		for (Map.Entry<String,Integer> countWeek : retweetWeek.entrySet()) {
		    System.out.println(countWeek.getKey());
		    System.out.println(countWeek.getValue());
		}
		
	}
	
	public void favoritesPerWeek(String hashtag, Twitter twitter) throws TwitterException{
		LocalDate now = LocalDate.now();
		LocalDate week = now.minusWeeks(Constants.NUMBER_WEEKS);
		System.out.print("Favoritados analisados - De: " + week);
		System.out.println(" até " + now);
		
		List<Status> favorites = findFavorites(hashtag, week.toString(), twitter);
		
		
		Map<String, Integer> favoriteWeek = new HashMap<>();
		
		for (Status favorite : favorites) {
			
			LocalDate created = favorite.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			
			if((created.isEqual(now) || created.isBefore(now)) && created.isAfter(week)){
				
				DateTimeFormatter format = DateTimeFormatter.ofPattern("EEEE");
				String weekDay = created.format(format);
				int count = 0;
				if(!favoriteWeek.containsKey(weekDay)){
					count += 1;
					favoriteWeek.put(weekDay, count);
				}else{
					count = favoriteWeek.get(weekDay);
					favoriteWeek.remove(weekDay);
					count += 1;
					favoriteWeek.put(weekDay, count);
					
				}
			}
		}
		
		for (Map.Entry<String,Integer> countWeek : favoriteWeek.entrySet()) {
		    System.out.println(countWeek.getKey());
		    System.out.println(countWeek.getValue());
		}
		
	}
	
}
