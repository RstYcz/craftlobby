package cz.wake.lobby.gadgets.particles;

import cz.wake.lobby.Main;
import cz.wake.lobby.utils.UtilMath;
import cz.wake.lobby.utils.UtilParticles;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class Enchanted {

    public static final HashMap<String, Integer> e = new HashMap();
    public static int task8;

    @SuppressWarnings("deprecation")
    public void activateEnchanted(Player p) {
        if (!e.containsKey(p.getName())) {
            task8 = Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), new BukkitRunnable() {
                @Override
                public void run() {
                    if (e.containsKey(p.getName()) && p.isOnline()) {
                        for (int i = 0; i < 60; i++) {
                            UtilParticles.play(p.getLocation().add(0, UtilMath.randomDouble(0.1, 2), 0), Effect.FLYING_GLYPH, 0, 0, 0, 0, 0, 8f, 1);
                        }
                    }
                }
            }, 0L, 4L).getTaskId();
            e.put(p.getName(), Integer.valueOf(task8));
        }
    }
}
