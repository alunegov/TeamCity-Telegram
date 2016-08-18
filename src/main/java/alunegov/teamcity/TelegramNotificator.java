package alunegov.teamcity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import alunegov.telegram.TelegramWrapper;
import jetbrains.buildServer.notification.NotificatorAdapter;
import jetbrains.buildServer.notification.NotificatorRegistry;
import jetbrains.buildServer.serverSide.SRunningBuild;
import jetbrains.buildServer.serverSide.UserPropertyInfo;
import jetbrains.buildServer.users.NotificatorPropertyKey;
import jetbrains.buildServer.users.PropertyKey;
import jetbrains.buildServer.users.SUser;

/**
 * Created by Alexander on 17.08.2016.
 */
public class TelegramNotificator extends NotificatorAdapter {
    private static final Logger log = Logger.getLogger("TelegramNotificator");

    private static final String notificatorType = "TelegramNotifier";

    private static final String telegramBotTokenKey = "telegram.bot.token";
    private static final String telegramChatKey = "telegram.chat";

    private static final PropertyKey telegramBotToken = new NotificatorPropertyKey(notificatorType, telegramBotTokenKey);
    private static final PropertyKey telegramChat = new NotificatorPropertyKey(notificatorType, telegramChatKey);

    private final NotificatorRegistry notificatorRegistry;

    public TelegramNotificator(NotificatorRegistry notificatorRegistry) {
        this.notificatorRegistry = notificatorRegistry;
    }

    public void init() {
        List<UserPropertyInfo> userPropertyInfos = new ArrayList<>();

        userPropertyInfos.add(new UserPropertyInfo(telegramBotTokenKey, "Telegram bot token"));
        userPropertyInfos.add(new UserPropertyInfo(telegramChatKey, "Telegram chat"));

        notificatorRegistry.register(this, userPropertyInfos);
    }

    public void notifyBuildSuccessful(SRunningBuild build, Set<SUser> users) {
        log.info(build.toString());

        for (SUser user : users) {
            log.info("user: " + user.toString());
            log.info("telegramBotToken: " + user.getPropertyValue(telegramBotToken));
            log.info("telegramChat: " + user.getPropertyValue(telegramChat));
        }

        TelegramWrapper tw = new TelegramWrapper();
        tw.setBotToken("");
        tw.setChatId("");

        try {
            tw.send("тест");
        }
        catch (IOException e) {
            log.severe("TelegramNotificator FAILED");
            log.severe(Arrays.toString(e.getStackTrace()));
            e.printStackTrace();
        }
    }

    public String getNotificatorType() {
        return notificatorType;
    }

    public String getDisplayName() {
        return "Telegram Notifier";
    }
}
