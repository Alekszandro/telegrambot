package com.alexstepural.photobot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class PhotoBot extends TelegramLongPollingBot {

	public void onUpdateReceived(Update update) {
		if(update.hasMessage()&&update.getMessage().hasText()) {
			String message_text=update.getMessage().getText();
			long chat_id=update.getMessage().getChatId();
			
			if(message_text.equals("/start")) {
			SendMessage message=new SendMessage()
					.setChatId(chat_id)
					.setText(message_text);
			try {
				execute(message);
			} catch(TelegramApiException te) {te.printStackTrace();		}
			}
			
			else if(message_text.equals("/pic")){
				SendPhoto msg=new SendPhoto()
						.setChatId(chat_id)
						.setPhoto("")
						.setCaption("Photo");
				try {
					sendPhoto(msg);
				} catch (TelegramApiException te) {	te.printStackTrace();		}
			}
			else if(message_text.equals("/markup")) {
				SendMessage message=new SendMessage()
						.setChatId(chat_id)
						.setText("Here is your keyboard");
				
				ReplyKeyboardMarkup keyboardMarkup=new ReplyKeyboardMarkup();
				
				List <KeyboardRow> keyboard=new ArrayList<>();
				
				KeyboardRow row=new KeyboardRow();
				row.add("Row 1 Button 1");
				row.add("Row 1 Button 2");
				row.add("Row 1 Button 3");
				keyboard.add(row);
				
				row=new KeyboardRow();
				row.add("Row 2 Button 1");
				row.add("Row 2 Button 2");
				row.add("Row 2 Button 3");
				keyboard.add(row);
				
				keyboardMarkup.setKeyboard(keyboard);
				
				message.setReplyMarkup(keyboardMarkup);
				
				try {
					execute(message);
				} catch(TelegramApiException te) {te.printStackTrace();		}
				
			}
			else {
				SendMessage message=new SendMessage()
						.setChatId(chat_id)
						.setText("Unknown command");
				try {
					execute(message);
				} catch(TelegramApiException te) {te.printStackTrace();		}
			}
		}
		else if(update.hasMessage()&&update.getMessage().hasPhoto()) {
			long chat_id=update.getMessage().getChatId();
			List <PhotoSize> photos=update.getMessage().getPhoto();
			
			String f_id=photos.stream()
					.sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
					.findFirst()
					.orElse(null).getFileId();
	
			int f_width=photos.stream()
					.sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
					.findFirst()
					.orElse(null).getWidth();
			
			int f_height=photos.stream()
					.sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
					.findFirst()
					.orElse(null).getHeight();
	
			String caption="file_id: "+f_id+"\nwidth"+Integer.toString(f_width)+"\nheight"+Integer.toString(f_height);
			SendPhoto msg=new SendPhoto()
					.setChatId(chat_id)
					.setPhoto(f_id)
					.setCaption(caption);
			try {
			sendPhoto(msg);
		} catch (TelegramApiException te) {	te.printStackTrace();		}
		}
	}

	public String getBotUsername() {
		// TODO Auto-generated method stub
		return Constants.BOT_NAME;
	}

	@Override
	public String getBotToken() {
		// TODO Auto-generated method stub
		return Constants.BOT_TOKEN;
	}

}
