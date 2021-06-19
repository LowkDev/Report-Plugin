package me.mherzaqaryan.report.utility;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class SpigotUtil {

    public static void sendCommand(ProxiedPlayer player, String cmd) {
        if (player == null) return;

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Send");
        out.writeUTF(cmd);

        player.getServer().getInfo().sendData("Report", out.toByteArray());
    }

    public static void playSound(ProxiedPlayer player, String sound, float volume, float pitch) {
        if (player == null) return;

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("PlaySound");
        out.writeUTF(sound);
        out.writeFloat(volume);
        out.writeFloat(pitch);

        player.getServer().getInfo().sendData("Report", out.toByteArray());
    }

    public static void teleport(ProxiedPlayer player, String toPlayer) {
        if (player == null) return;

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Teleport");
        out.writeUTF(toPlayer);

        player.getServer().getInfo().sendData("Report", out.toByteArray());
    }

}
