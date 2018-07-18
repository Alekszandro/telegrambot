package com.alexstepural.photobot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Main {

	public static void main(String[] args) {

		ApiContextInitializer.init();
		
		TelegramBotsApi bot=new TelegramBotsApi();
		
		try{
			bot.registerBot(new PhotoBot());
		} catch(TelegramApiException e) {e.printStackTrace();};
	}

}
