package me.MrIronMan.postman;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class UpdateChecker {

    private final PostMan plugin = PostMan.getInstance();
    private String newVer;
    private boolean isAvailable;

    public UpdateChecker() {
        try {
            String localVersion = plugin.getDescription().getVersion();
            String url = "https://api.spigotmc.org/legacy/update.php?resource=";
            HttpsURLConnection connection = (HttpsURLConnection) new URL(url + PostMan.spigotId).openConnection();
            connection.setRequestMethod("GET");
            String raw = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();

            String remoteVersion;

            if(raw.contains("-")) remoteVersion = raw.split("-")[0].trim();
            else remoteVersion = raw;

            if(!localVersion.equalsIgnoreCase(remoteVersion)) {
                this.newVer = raw;
                this.isAvailable = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String newVer() {
        return newVer;
    }

    public String curVer() {
        return plugin.getDescription().getVersion();
    }

    public boolean isAvailable() {
        return isAvailable;
    }

}
