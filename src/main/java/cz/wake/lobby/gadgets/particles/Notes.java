package cz.wake.lobby.gadgets.particles;

import cz.wake.craftcore.utils.effects.ParticleEffect;
import cz.wake.lobby.Main;
import cz.wake.lobby.settings.SettingsMenu;
import cz.wake.lobby.utils.UtilMath;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Random;

public class Notes {

    public static final HashMap<String, Integer> no = new HashMap();
    public static int task10;

    @SuppressWarnings("deprecation")
    public void activateNotes(Player p) {
        if (!no.containsKey(p.getName())) {
            task10 = Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), new BukkitRunnable() {
                @Override
                public void run() {
                    if (no.containsKey(p.getName()) && p.isOnline()) {
                        for (int i = 0; i < 12; i++) {
                            Random random = new Random();
                            int j = random.nextInt(25);
                            ParticleEffect.ParticleColor pc = new ParticleEffect.NoteColor(j);
                            ParticleEffect.NOTE.display(pc, p.getLocation().add(UtilMath.randomDouble(-1.5, 1.5), UtilMath.randomDouble(0, 2.5), UtilMath.randomDouble(-1.5, 1.5)), SettingsMenu.particles);
                        }
                    }

                }

            }, 0L, 4L).getTaskId();
            no.put(p.getName(), Integer.valueOf(task10));
        }
    }
}
