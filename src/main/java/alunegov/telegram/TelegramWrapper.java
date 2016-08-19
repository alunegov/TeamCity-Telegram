package alunegov.telegram;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Modifier;
import java.net.Proxy;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;

import alunegov.teamcity.message.Message;
import alunegov.teamcity.message.MessageFormat;
import alunegov.telegram.botapi.methods.SendMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Alex on 17.08.2016.
 */

public class TelegramWrapper {
    private static final Logger log = Logger.getLogger("TelegramWrapper");

    private static final String BASE_URL = "https://api.telegram.org/bot";

    private String botToken;

    private String chatId;

    TelegramWrapper() {
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

    public void send(@NotNull Message message) throws IOException {
        URL url = new URL(BASE_URL + botToken + "/" + SendMessage.PATH);

        // TODO: Don't use proxy here, it should be configured somehow in TeamCity
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.0.9", 8080));

        HttpsURLConnection conn = (HttpsURLConnection)url.openConnection(proxy);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        conn.setDoOutput(true);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId)
                .setText(message.getText())
                .setDisableWebPagePreview(true);
        if (message.getFormat() == MessageFormat.Markdown) {
            sendMessage.enableMarkdown(true);
        }
        else if (message.getFormat() == MessageFormat.Html) {
            sendMessage.enableHtml(true);
        }

        Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.STATIC, Modifier.FINAL)
                .create();
        String msg = gson.toJson(sendMessage);

        OutputStream out = conn.getOutputStream();
        out.write(msg.getBytes("UTF-8"));
        out.flush();
        out.close();

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new IOException(String.format("Bad response (%d)", responseCode));
        }

        conn.disconnect();
    }
}
