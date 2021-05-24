package me.MrIronMan.postman.configuration;

import me.MrIronMan.postman.PostMan;
import me.MrIronMan.postman.api.data.DataManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class ConfigData extends DataManager {

    public static boolean
            MYSQL_ENABLED,
            RUN_ON_VERIFY,
            CHECK_UPDATES;

    public static String
            MYSQL_HOST,
            MYSQL_PORT,
            MYSQL_USER,
            MYSQL_DATABASE,
            MYSQL_PASS,
            MAIL_SERVER_HOST,
            MAIL_SERVER_PORT,
            ACCOUNT_EMAIL,
            ACCOUNT_PASS,
            COMMAND_SETEMAIL,
            COMMAND_VERIFY;

    public static int
            VERIFY_CODE_LENGTH,
            VERIFY_CONFIRM_TIME;

    public static List<String>
            VERIFY_COMMANDS;

    public ConfigData(Plugin plugin, String dir, String name) {
        super(plugin, dir, name);
        YamlConfiguration yml = getConfig();
        yml.options().header("PostMan plugin by MrIronMan Version: v" + PostMan.getInstance().getDescription().getVersion());

        final String
                PATH_MYSQL_ENABLED = "mysql.enabled",
                PATH_MYSQL_HOST = "mysql.host",
                PATH_MYSQL_PORT = "mysql.port",
                PATH_MYSQL_USER = "mysql.username",
                PATH_MYSQL_DATABASE = "mysql.database",
                PATH_MYSQL_PASS = "mysql.password",
                PATH_MAIL_SERVER_HOST = "mail-server.host",
                PATH_MAIL_SERVER_PORT = "mail-server.port",
                PATH_ACCOUNT_EMAIL = "email-account.email",
                PATH_ACCOUNT_PASS = "email-account.password",
                PATH_RUN_ON_VERIFY = "run-command-on-verify",
                PATH_VERIFY_COMMANDS = "verify-commands",
                PATH_COMMAND_SETEMAIL = "set-email-command",
                PATH_COMMAND_VERIFY = "verify-command",
                PATH_VERIFY_CODE_LENGTH = "verify-code-length",
                PATH_VERIFY_CONFIRM_TIME = "verify-confirm-time",
                PATH_CHECK_UPDATES = "check-updates";

        yml.addDefault(PATH_MYSQL_ENABLED, false);
        yml.addDefault(PATH_MYSQL_HOST, "localhost");
        yml.addDefault(PATH_MYSQL_PORT, "3306");
        yml.addDefault(PATH_MYSQL_USER, "root");
        yml.addDefault(PATH_MYSQL_DATABASE, "PostMan");
        yml.addDefault(PATH_MYSQL_PASS, "cheese");
        yml.addDefault(PATH_MAIL_SERVER_HOST, "smtp.gmail.com");
        yml.addDefault(PATH_MAIL_SERVER_PORT, "587");
        yml.addDefault(PATH_ACCOUNT_EMAIL, "example@example.com");
        yml.addDefault(PATH_ACCOUNT_PASS, "cheese");
        yml.addDefault(PATH_RUN_ON_VERIFY, false);
        yml.addDefault(PATH_VERIFY_COMMANDS, new String[]{"give %player% minecraft:diamond 2"});
        yml.addDefault(PATH_COMMAND_SETEMAIL, "setemail");
        yml.addDefault(PATH_COMMAND_VERIFY, "verify");
        yml.addDefault(PATH_VERIFY_CODE_LENGTH, 6);
        yml.addDefault(PATH_VERIFY_CONFIRM_TIME, 300);
        yml.addDefault(PATH_CHECK_UPDATES, true);
        yml.options().copyDefaults(true);
        save();

        MYSQL_ENABLED = getBoolean(PATH_MYSQL_ENABLED);

        if (MYSQL_ENABLED) {
            MYSQL_HOST = getString(PATH_MYSQL_HOST);
            MYSQL_PORT = getString(PATH_MYSQL_PORT);
            MYSQL_DATABASE = getString(PATH_MYSQL_DATABASE);
            MYSQL_USER = getString(PATH_MYSQL_USER);
            MYSQL_PASS = getString(PATH_MYSQL_PASS);
        }

        MAIL_SERVER_HOST = getString(PATH_MAIL_SERVER_HOST);
        MAIL_SERVER_PORT = getString(PATH_MAIL_SERVER_PORT);
        ACCOUNT_EMAIL = getString(PATH_ACCOUNT_EMAIL);
        ACCOUNT_PASS = getString(PATH_ACCOUNT_PASS);
        RUN_ON_VERIFY = getBoolean(PATH_RUN_ON_VERIFY);
        VERIFY_COMMANDS = getStringList(PATH_VERIFY_COMMANDS);
        COMMAND_SETEMAIL = getString(PATH_COMMAND_SETEMAIL);
        COMMAND_VERIFY = getString(PATH_COMMAND_VERIFY);
        VERIFY_CODE_LENGTH = getInt(PATH_VERIFY_CODE_LENGTH);
        VERIFY_CONFIRM_TIME = getInt(PATH_VERIFY_CONFIRM_TIME);
        CHECK_UPDATES = getBoolean(PATH_CHECK_UPDATES);
    }

}
