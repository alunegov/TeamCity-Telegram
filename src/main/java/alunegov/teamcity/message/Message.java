package alunegov.teamcity.message;

/**
 * Created by Alexander on 19.08.2016.
 */

public class Message {
    private String text;

    private MessageFormat format;

    public Message() {
        this("", MessageFormat.PlainText);
    }

    public Message(String text) {
        this(text, MessageFormat.PlainText);
    }

    public Message(String text, MessageFormat format) {
        this.text = text;
        this.format = format;
    }

    public String getText() {
        return text;
    }

    public Message setText(String text) {
        this.text = text;
        return this;
    }

    public MessageFormat getFormat() {
        return format;
    }

    public Message setFormat(MessageFormat format) {
        this.format = format;
        return this;
    }
}
