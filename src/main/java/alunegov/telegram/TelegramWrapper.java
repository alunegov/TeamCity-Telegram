package alunegov.telegram;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;

import alunegov.telegram.botapi.methods.SendMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Alex on 17.08.2016.
 */
public class TelegramWrapper {
    private static final String BASE_URL = "https://api.telegram.org/bot";

    private String botToken;

    private String chatId;

    public TelegramWrapper() {
        this.botToken = null;
        this.chatId = null;
    }

    public String getBotToken() {
        return botToken;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public void send() throws IOException {
        URL url = new URL(BASE_URL + botToken + "/" + SendMessage.PATH);

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId)
                .setText("")
                .enableMarkdown(true);

        Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.STATIC, Modifier.FINAL)
                .create();
        String msg = gson.toJson(sendMessage);

        OutputStream out = conn.getOutputStream();
        out.write(msg.getBytes());
        out.close();
    }
}
