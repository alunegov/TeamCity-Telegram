package alunegov.teamcity;

import java.io.IOException;

import alunegov.teamcity.message.BuildStatusMessage;
import alunegov.teamcity.message.Message;
import alunegov.telegram.TelegramWrapper;
import jetbrains.buildServer.serverSide.BuildServerAdapter;
import jetbrains.buildServer.serverSide.SBuildServer;
import jetbrains.buildServer.serverSide.SRunningBuild;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Alexander on 19.08.2016.
 */

public class TelegramServerAdapter extends BuildServerAdapter {
    private final SBuildServer buildServer;
    private final TelegramWrapper telegramWrapper;

    public TelegramServerAdapter(SBuildServer buildServer, TelegramWrapper telegramWrapper) {
        super();

        this.buildServer = buildServer;
        this.telegramWrapper = telegramWrapper;
    }

    public void init() {
        buildServer.addListener(this);
    }

    @Override
    public void buildFinished(@NotNull SRunningBuild build) {
        super.buildFinished(build);

        // TODO: Get watched events from project/admin settings
        /*if (!build.isPersonal() && build.getBuildStatus().isSuccessful()) {
            postToTelegram(BuildStatusMessage.formatOnSuccess(build));
        }
        else */if (!build.isPersonal() && build.getBuildStatus().isFailed()) {
            postToTelegram(BuildStatusMessage.formatOnFailure(build));
        }
    }

    @Override
    public void serverShutdown() {
        super.serverShutdown();

        // TODO: Get watched events from project/admin settings
        //postToTelegram(new Message("Server is shutting down"));
    }

    @Override
    public void serverStartup() {
        super.serverStartup();

        // TODO: Get watched events from project/admin settings
        //postToTelegram(new Message("Server is started"));
    }

    private void postToTelegram(@NotNull Message message) {
        // TODO: Get bot token and chat id from project/admin settings
        telegramWrapper.setBotToken("264613101:AAGVeKgork1d06EuE_Vy4jdF8kN_nbfaoS4");
        telegramWrapper.setChatId("-121885192");

        try {
            telegramWrapper.send(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
