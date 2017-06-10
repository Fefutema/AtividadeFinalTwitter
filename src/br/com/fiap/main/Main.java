package br.com.fiap.main;

import java.time.LocalDate;
import java.util.List;

import br.com.fiap.config.Config;
import br.com.fiap.tweets.TweetAnalytics;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class Main {

	public static void main(String[] args) {

		Twitter twitter = Config.getInstance();
		
		//String tweet = "Hello World !!!";
		
		TweetAnalytics analytics = new TweetAnalytics();
		LocalDate now = LocalDate.now();
		String hashtag = "#Java8";
		
		try {
			
			System.out.println("Tweet por semana");
			analytics.tweetAnalysisPerWeek(hashtag, twitter);
			System.out.println("-----------------------------------------------------------");
			
			System.out.println("Retweet por semana");
			analytics.retweetAnalysisPerWeek(hashtag, twitter);
			System.out.println("-----------------------------------------------------------");
			
			System.out.println("Favoritados por semana");
			analytics.favoritesPerWeek(hashtag, twitter);
			System.out.println("-----------------------------------------------------------");
			
			
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		
		
	}

}
