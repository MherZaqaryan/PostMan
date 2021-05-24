package me.MrIronMan.postman.configuration;

import me.MrIronMan.postman.PostMan;
import me.MrIronMan.postman.api.data.DataManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class MessagesData extends DataManager {

    public static String
            PREFIX,
            NO_PERMISSION,
            VERIFICATION_PENDING,
            VERIFICATION_OCCUPIED,
            VERIFICATION_ALREADY_SET,
            VERIFICATION_INVALID_EMAIL,
            VERIFICATION_SENDING,
            VERIFICATION_SENT,
            VERIFICATION_FAILED,
            VERIFICATION_ALREADY_VERIFIED,
            VERIFICATION_NOT_PENDING,
            VERIFICATION_DENIED,
            VERIFICATION_SUCCESS,
            VERIFICATION_WRONG_CODE,
            USAGES_EMAIL,
            USAGES_VERIFY;

    public MessagesData(Plugin plugin, String dir, String name) {
        super(plugin, dir, name);
        YamlConfiguration yml = getConfig();
        yml.options().header("PostMan plugin by MrIronMan Version: v" + PostMan.getInstance().getDescription().getVersion());

        final String
                PATH_PREFIX = "prefix",
                PATH_NO_PERMISSION = "no-permission",
                PATH_VERIFICATION_PENDING = "verification.pending",
                PATH_VERIFICATION_OCCUPIED = "verification.occupied",
                PATH_VERIFICATION_ALREADY_SET = "verification.already-set",
                PATH_VERIFICATION_INVALID_EMAIL = "verification.invalid-email",
                PATH_VERIFICATION_SENDING = "verification.sending",
                PATH_VERIFICATION_SENT = "verification.sent",
                PATH_VERIFICATION_FAILED = "verification.failed",
                PATH_VERIFICATION_ALREADY_VERIFIED = "verification.already-verified",
                PATH_VERIFICATION_NOT_PENDING = "verification.not-pending",
                PATH_VERIFICATION_DENIED = "verification.denied",
                PATH_VERIFICATION_SUCCESS = "verification.success",
                PATH_VERIFICATION_WRONG_CODE = "verification.wrong-code",
                PATH_USAGES_EMAIL = "command-usages.set-email",
                PATH_USAGES_VERIFY = "command-usages.verify";

        yml.addDefault(PATH_PREFIX, "&6&lPostMan &8→&r");
        yml.addDefault(PATH_NO_PERMISSION, "{prefix} &cSorry, but you don't have permission to execute this command.");
        yml.addDefault(PATH_VERIFICATION_PENDING, "{prefix} &cYour authentication is in pending yet.");
        yml.addDefault(PATH_VERIFICATION_OCCUPIED, "{prefix} &cThis email address already exists.");
        yml.addDefault(PATH_VERIFICATION_ALREADY_SET, "{prefix} &cYour email address already set.");
        yml.addDefault(PATH_VERIFICATION_INVALID_EMAIL, "{prefix} &cThis email address is not valid.");
        yml.addDefault(PATH_VERIFICATION_SENDING, "{prefix} &aSending verification code...");
        yml.addDefault(PATH_VERIFICATION_SENT, "{prefix} &3Authentication code successfully sent to &b%email% &3, you have 5 minutes to verify it!");
        yml.addDefault(PATH_VERIFICATION_FAILED, "{prefix} &cFailed to send verification code, please report this to staff team");
        yml.addDefault(PATH_VERIFICATION_ALREADY_VERIFIED, "{prefix} &cYour account already verified!");
        yml.addDefault(PATH_VERIFICATION_NOT_PENDING, "{prefix} &cYour authentication is not pending.");
        yml.addDefault(PATH_VERIFICATION_DENIED, "{prefix} &cYour authentication was denied.");
        yml.addDefault(PATH_VERIFICATION_SUCCESS, "{prefix} &aYour account was successfully verified!");
        yml.addDefault(PATH_VERIFICATION_WRONG_CODE, "{prefix} &cThis verification code is wrong!");
        yml.addDefault(PATH_USAGES_EMAIL, "{prefix} &cUsage:&7 /SetEmail <YourEmail>");
        yml.addDefault(PATH_USAGES_VERIFY, "{prefix} &cUsage:&7 /Verify <Code>");
        yml.options().copyDefaults(true);
        save();

        PREFIX = getString(PATH_PREFIX);
        NO_PERMISSION = getString(PATH_NO_PERMISSION);
        VERIFICATION_PENDING = getString(PATH_VERIFICATION_PENDING);
        VERIFICATION_OCCUPIED = getString(PATH_VERIFICATION_OCCUPIED);
        VERIFICATION_ALREADY_SET = getString(PATH_VERIFICATION_ALREADY_SET);
        VERIFICATION_INVALID_EMAIL = getString(PATH_VERIFICATION_INVALID_EMAIL);
        VERIFICATION_SENDING = getString(PATH_VERIFICATION_SENDING);
        VERIFICATION_SENT = getString(PATH_VERIFICATION_SENT);
        VERIFICATION_FAILED = getString(PATH_VERIFICATION_FAILED);
        VERIFICATION_ALREADY_VERIFIED = getString(PATH_VERIFICATION_ALREADY_VERIFIED);
        VERIFICATION_NOT_PENDING = getString(PATH_VERIFICATION_NOT_PENDING);
        VERIFICATION_DENIED = getString(PATH_VERIFICATION_DENIED);
        VERIFICATION_SUCCESS = getString(PATH_VERIFICATION_SUCCESS);
        VERIFICATION_WRONG_CODE = getString(PATH_VERIFICATION_WRONG_CODE);
        USAGES_EMAIL = getString(PATH_USAGES_EMAIL);
        USAGES_VERIFY = getString(PATH_USAGES_VERIFY);
    }

}
