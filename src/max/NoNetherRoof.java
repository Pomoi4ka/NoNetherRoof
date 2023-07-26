package max;

import java.util.ArrayList;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

class PreviousLocation {
    private Player player;
    private Location location;

    PreviousLocation(Player player, Location location) {
        this.player = player;
        this.location = location;
    }
    
    public void teleportBack() {
        player.teleport(location);
    }

    public String getPlayerName() {
        return player.getName();
    }
}

class Preventer implements Listener {
    private static ArrayList positions = new ArrayList<PreviousLocation>();

    private PreviousLocation getPlayerPrevLoc(Player p) {
        String name = p.getName();

        int index = 0;
        for (PreviousLocation pl: (ArrayList<PreviousLocation>) positions) {
            if (name.equals(pl.getPlayerName())) {
                return pl;
            }
        }

        return null;
    }
    
    @EventHandler
    public void handler(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        Location toPos = event.getTo();
        if (toPos.getWorld().getEnvironment() == World.Environment.NETHER) {
            PreviousLocation prevloc = getPlayerPrevLoc(player);
            if (toPos.getBlockY() >= 123 && prevloc == null) {
                positions.add(new PreviousLocation(player, event.getFrom()));
            } else if (prevloc != null && toPos.getBlockY() < 123) {
                positions.remove(prevloc);
            }

            if (toPos.getBlockY() >= 127 && prevloc != null) {
                prevloc.teleportBack();
            }
        }
    }
}

public class NoNetherRoof extends JavaPlugin {
    
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Preventer(), this);
    }
}
