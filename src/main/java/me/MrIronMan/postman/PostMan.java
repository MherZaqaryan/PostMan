package me.MrIronMan.postman;

import me.MrIronMan.postman.api.commands.SubCommand;
import me.MrIronMan.postman.command.*;
import me.MrIronMan.postman.command.subcommands.ComposeSubCommand;
import me.MrIronMan.postman.command.subcommands.ControlSubCommand;
import me.MrIronMan.postman.command.subcommands.SetSubjectSubCommand;
import me.MrIronMan.postman.command.subcommands.UnregisterSubCommand;
import me.MrIronMan.postman.configuration.*;
import me.MrIronMan.postman.database.*;
import me.MrIronMan.postman.listeners.*;
import me.MrIronMan.postman.util.*;
import me.MrIronMan.postman.menu.MenuListener;
import me.MrIronMan.postman.menu.PlayerMenuUtility;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

public class PostMan extends JavaPlugin {

    private static PostMan instance;

    public static int spigotId = 88316;
    public static int bStatsId = 11181;

    private static ConfigData configData;
    private static MessagesData messagesData;

    private SQLite SQLite;
    private MySQL mySQL;
    private DbManager dbManager;
    private ReflectUtils reflectUtils;

    private UpdateChecker updateChecker;

    public static LinkedList<SubCommand> subCommands = new LinkedList<>();;

    private static final HashMap<UUID, String> playerEmailMap = new HashMap<>();
    private static final HashMap<UUID, String> playerVerificationMap = new HashMap<>();
    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        new Metrics(this, bStatsId);

        reflectUtils = new ReflectUtils();
        configData = new ConfigData(this, getDataFolder().getPath(), "Config");
        messagesData = new MessagesData(this, getDataFolder().getPath(), "Messages");

        reflectUtils.registerCommand("postman", new PostManCommand());
        reflectUtils.registerCommand(ConfigData.COMMAND_SETEMAIL, new SetEmailCommand());
        reflectUtils.registerCommand(ConfigData.COMMAND_VERIFY, new VerifyCommand());

        subCommands.addAll(Arrays.asList(
                new ComposeSubCommand(),
                new ControlSubCommand(),
                new SetSubjectSubCommand(),
                new UnregisterSubCommand()
        ));

        if (ConfigData.MYSQL_ENABLED) {
            this.mySQL = new MySQL(
                    configData.getString(ConfigData.MYSQL_HOST),
                    configData.getString(ConfigData.MYSQL_PORT),
                    configData.getString(ConfigData.MYSQL_USER),
                    configData.getString(ConfigData.MYSQL_DATABASE),
                    configData.getString(ConfigData.MYSQL_PASS)
            );
        }else {
            this.SQLite = new SQLite();
        }
        this.dbManager = new DbManager();
        dbManager.createTable();
        HtmlUtil.loadDefaultFile();

        Arrays.asList(
                new Connections(),
                new MenuListener(),
                new PlayerChatEvent(),
                new PlayerVerifyListener())
                .forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));

        if (!ConfigData.CHECK_UPDATES) return;

        getLogger().info("Checking updates, please wait...");
        this.updateChecker = new UpdateChecker();

        if (!updateChecker.isAvailable()) {
            getLogger().info("You are using latest version of PostMan!");
            return;
        }

        getLogger().info("There is new version available " + updateChecker.newVer() + " you are still on " + updateChecker.curVer());
        getLogger().info("Please download the new version as soon as possible!");
        getLogger().info("Download new update from here: https://www.spigotmc.org/resources/88316/");
    }

    @Override
    public void onDisable() {
        if (ConfigData.MYSQL_ENABLED) this.mySQL.disconnect();
        else this.SQLite.disconnect();
        playerEmailMap.clear();
        playerMenuUtilityMap.clear();
        playerVerificationMap.clear();
    }

    public static PlayerMenuUtility getPlayerMenuUtility(Player p) {
        PlayerMenuUtility playerMenuUtility;
        if (playerMenuUtilityMap.containsKey(p)) return playerMenuUtilityMap.get(p);
        playerMenuUtility = new PlayerMenuUtility(p);
        playerMenuUtilityMap.put(p, playerMenuUtility);
        return playerMenuUtility;
    }

    public static void addPending(Player player, String verificationCode) {
        if (!playerVerificationMap.containsKey(player.getUniqueId())) playerVerificationMap.put(player.getUniqueId(), verificationCode);
    }

    public static void removePending(Player player) {
        playerVerificationMap.remove(player.getUniqueId());
    }

    public static String getAuthCode(Player player) {
        if (isPending(player)) return playerVerificationMap.get(player.getUniqueId());
        return null;
    }

    public static boolean isPending(Player player) {
        return playerVerificationMap.containsKey(player.getUniqueId());
    }

    public static void setEmailPending(Player player, String string) {
        if (!playerEmailMap.containsKey(player.getUniqueId())) {
            playerEmailMap.put(player.getUniqueId(), string);
        }
    }

    public static String getEmailPending(Player player) {
        return playerEmailMap.getOrDefault(player.getUniqueId(), null);
    }

    public static void removeEmailPending(Player player) {
        playerEmailMap.remove(player.getUniqueId());
    }

    public boolean getFile(String arg) {
        String[] files = new File(getDataFolder() + File.separator + "Mails").list();
        assert files != null;
        return Arrays.asList(files).contains(arg);
    }

    public static PostMan getInstance() {
        return instance;
    }

    public static ConfigData getConfiguration() {
        return configData;
    }

    public static MessagesData getMessages() {
        return messagesData;
    }

    public UpdateChecker getUpdateChecker() {
        return updateChecker;
    }

    public ReflectUtils getReflectUtils() {
        return reflectUtils;
    }

    public MySQL getMySQL() {
        return mySQL;
    }

    public SQLite getSqLite() {
        return SQLite;
    }

    public DbManager getSQLData() {
        return dbManager;
    }

}
