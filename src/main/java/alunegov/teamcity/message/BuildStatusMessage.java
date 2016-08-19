package alunegov.teamcity.message;

import java.util.ArrayList;
import java.util.List;

import jetbrains.buildServer.BuildProblemData;
import jetbrains.buildServer.serverSide.SRunningBuild;
import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.users.UserSet;
import jetbrains.buildServer.vcs.SelectPrevBuildPolicy;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Alexander on 19.08.2016.
 */

public class BuildStatusMessage {
    public static Message formatOnSuccess(@NotNull SRunningBuild build) {
        assert build.getBuildStatus().isSuccessful();

        String text = String.format("Project '%s' built successfully.", build.getFullName());

        return new Message(text, MessageFormat.Markdown);
    }

    public static Message formatOnFailure(@NotNull SRunningBuild build) {
        assert build.getBuildStatus().isFailed();

        List<String> lines = new ArrayList<>();

        lines.add(String.format("*Project '%s' build failed!*", build.getFullName()));

        List<BuildProblemData> failureReasons =  build.getFailureReasons();
        if (!failureReasons.isEmpty()) {
            lines.add("Build problems");
            lines.add("```");
            for (BuildProblemData buildProblem : failureReasons) {
                lines.add(String.format("  %s|%s", buildProblem.getType(), buildProblem.getIdentity()));
                lines.add("  " + buildProblem.getDescription());
                lines.add("  --------------");
            }
            lines.add("```");
        }

        UserSet<SUser> committers = build.getCommitters(SelectPrevBuildPolicy.SINCE_LAST_BUILD);
        String committersStr = "";
        if (committers != null) {
            for (SUser committer : committers.getUsers()) {
                if (committer != null) {
                    committersStr += committer.getUsername() + ", ";
                }
            }
        }
        if (!committersStr.isEmpty()) {
            lines.add("Committers: `" + committersStr + "`");
        }

        String text = lines.stream().reduce((s1, s2) -> s1 + "\n" + s2).orElse("_Oops, i'm nothing to say_");

        return new Message(text, MessageFormat.Markdown);
    }
}
