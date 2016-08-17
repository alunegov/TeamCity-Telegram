package alunegov.telegram.botapi.methods;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alex on 17.08.2016.
 */
public class SendMessage {
    public static final String PATH = "sendMessage";

    @SerializedName("chat_id")
    private String chatId;

    @SerializedName("text")
    private String text;

    @SerializedName("parse_mode")
    private String parseMode;

    @SerializedName("disable_web_page_preview")
    private Boolean disableWebPagePreview;

    public SendMessage() {
        chatId = "";
        text = "";
        parseMode = null;
        disableWebPagePreview = null;
    }

    public String getChatId() {
        return chatId;
    }

    public SendMessage setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public String getText() {
        return text;
    }

    public SendMessage setText(String text) {
        this.text = text;
        return this;
    }

    public String getParseMode() {
        return parseMode;
    }

    public void setParseMode(String parseMode) {
        this.parseMode = parseMode;
    }

    public Boolean getDisableWebPagePreview() {
        return disableWebPagePreview;
    }

    public SendMessage setDisableWebPagePreview(Boolean disableWebPagePreview) {
        this.disableWebPagePreview = disableWebPagePreview;
        return this;
    }

    public SendMessage enableMarkdown(Boolean enable) {
        this.parseMode = enable ? "Markdown" : null;
        return this;
    }

    public SendMessage enableHtml(Boolean enable) {
        this.parseMode = enable ? "HTML" : null;
        return this;
    }
}
