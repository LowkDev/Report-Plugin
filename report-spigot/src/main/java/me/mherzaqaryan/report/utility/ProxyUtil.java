package me.mherzaqaryan.report.utility;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import me.mherzaqaryan.report.ReportPlugin;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ProxyUtil implements PluginMessageListener {

    public static void runCommand(Player player, String cmd) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("RunCommand");
            out.writeUTF(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.sendPluginMessage(ReportPlugin.getInstance(), "BungeeCord", b.toByteArray());
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if (!channel.equalsIgnoreCase( "Report")) return;

        ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
        String subChannel = in.readUTF();

        if (subChannel.equalsIgnoreCase( "Send")) {
            String cmd = in.readUTF();
            Bukkit.dispatchCommand(player, cmd);
        }
        else if (subChannel.equalsIgnoreCase("PlaySound")) {
            String sound = in.readUTF();
            float volume = in.readFloat();
            float pitch = in.readFloat();
            player.playSound(player.getLocation(), Sound.valueOf(sound), volume, pitch);
        }
        else if (subChannel.equalsIgnoreCase("Teleport")) {
            String toPlayer = in.readUTF();
            Player p = Bukkit.getPlayer(toPlayer);
            if (p == null) return;
            Bukkit.getScheduler().runTaskLater(ReportPlugin.getInstance(), () ->{
                player.setAllowFlight(true);
                player.setFlying(true);
                player.setGameMode(GameMode.SPECTATOR);
                player.teleport(p.getLocation());
            }, 20L);
        }

    }

}
