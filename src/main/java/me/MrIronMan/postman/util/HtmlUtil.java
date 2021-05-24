package me.MrIronMan.postman.util;

import me.MrIronMan.postman.PostMan;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class HtmlUtil {

    public static String fileName = "Mails";

    public static void loadDefaultFile() {
        File folder = new File(PostMan.getInstance().getDataFolder() + File.separator + fileName);
        if (!folder.exists()) {
            folder.mkdir();
        }
        File verifyHtml = new File(folder + File.separator, "Verify.html");
        File updateHtml = new File(folder + File.separator, "Update.html");
        File verifyFile = new File(PostMan.getInstance().getDataFolder(), "Verify.html");
        File updateFile = new File(PostMan.getInstance().getDataFolder(), "Update.html");
        if (!verifyHtml.exists()) {
            PostMan.getInstance().saveResource("Verify.html", false);
            try {
                FileUtils.copyFileToDirectory(verifyFile, folder);
                verifyFile.delete();
                PostMan.getInstance().getSQLData().setSubject("Verify.html", "Verification Code");
                PostMan.getInstance().getLogger().info("Verify.html file successfully loaded.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!updateHtml.exists()) {
            PostMan.getInstance().saveResource("Update.html", false);
            try {
                FileUtils.copyFileToDirectory(updateFile, folder);
                updateFile.delete();
                PostMan.getInstance().getSQLData().setSubject("Update.html", "Server Update");
                PostMan.getInstance().getLogger().info("Update.html file successfully loaded.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static File getHtmlFile(String string) {
        return new File(PostMan.getInstance().getDataFolder() + File.separator + fileName , string);
    }

}
