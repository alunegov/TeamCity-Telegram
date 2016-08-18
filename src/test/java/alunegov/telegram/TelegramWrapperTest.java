package alunegov.telegram;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Alexander on 18.08.2016.
 */
public class TelegramWrapperTest {
    @Test
    public void sendTest() {
        TelegramWrapper tw = new TelegramWrapper();
        tw.setBotToken("");
        tw.setChatId("");

        try {
            tw.send("тест");
            Assert.assertTrue(true);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }
    }
}
