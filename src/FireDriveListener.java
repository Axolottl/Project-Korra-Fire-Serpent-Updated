package src;

import org.bukkit.event.EventPriority;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.EventHandler;
import com.projectkorra.projectkorra.ability.CoreAbility;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.Listener;

public class FireDriveListener implements Listener
{
    @SuppressWarnings("unchecked")
	@EventHandler
    public void onSneak(final PlayerToggleSneakEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (CoreAbility.hasAbility(event.getPlayer(), (Class)FireDrive.class)) {
            return;
        }
        new FireDrive(event.getPlayer());
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onChatBlue(final AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        final String msg = event.getMessage();
        if (msg.toLowerCase().contains("azula's fire burns blue")) {
            FireDrive.toggleBlueFire(player, true);
            event.setCancelled(true);
        }
        if (msg.toLowerCase().contains("zuko's fire burns orange")) {
            FireDrive.toggleBlueFire(player, false);
            event.setCancelled(true);
        }
    }
}
