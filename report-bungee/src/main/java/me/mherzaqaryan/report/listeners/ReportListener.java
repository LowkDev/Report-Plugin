package me.mherzaqaryan.report.listeners;

import me.mherzaqaryan.report.events.PlayerReportEvent;
import me.mherzaqaryan.report.utility.TextUtil;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import me.mherzaqaryan.report.utility.SpigotUtil;

import java.util.HashMap;
import java.util.UUID;

public class ReportListener implements Listener {

    public HashMap<UUID, Long> countDownMap = new HashMap<>();

    @EventHandler
    public void onReport(PlayerReportEvent e) {
        ProxiedPlayer reporter = e.getReporter();
        UUID uuid = reporter.getUniqueId();

        if (countDownMap.containsKey(uuid)) {
            if (countDownMap.get(uuid) > System.currentTimeMillis()) {
                e.setCancelled(true);
                int countdown = (int) (countDownMap.get(uuid) - System.currentTimeMillis()) / 1000;
                TextUtil.sendMessage(reporter, "&cPlease wait &e" + TextUtil.getFormatedTime(countdown) + "&c before reporting again.");
                return;
            }
        }
        countDownMap.put(uuid, System.currentTimeMillis() + (300 * 1000));

        TextUtil.sendMessage(reporter, "&d&lLowk&3&lCore &8→ &aYour report has been successfully sent. Thank you for improving our system and community");
        sendNotification(e.getReportId());

    }

    public void sendNotification(Integer reportId) {

        TextComponent line = new TextComponent(TextUtil.colorize("&7&l&m------------------------------------"));
        TextComponent text = new TextComponent(TextUtil.colorize(" &c✎ &c&lNew Report "));
        TextComponent textComponent = new TextComponent(TextUtil.colorize("&d(Click here to see the report)"));
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(TextUtil.colorize("&eClick to see the report")).create()));
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/reports watch " + reportId));
        text.addExtra(textComponent);

        for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
            if (player.hasPermission("Lowk.Report.Notification")) {
                SpigotUtil.playSound(player, "SUCCESSFUL_HIT", 1, 1);
                player.sendMessage(line);
                player.sendMessage(text);
                player.sendMessage(line);
            }
        }
    }

}
