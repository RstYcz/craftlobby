package cz.wake.lobby.gadgets.morphs;

import cz.wake.lobby.utils.ParticleEffect;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.LivingWatcher;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class VillagerMorph implements Listener {

    public static void activate(Player p) {
        MobDisguise localDisguise = new MobDisguise(DisguiseType.VILLAGER);
        DisguiseAPI.undisguiseToAll(p);
        LivingWatcher localLivingWatcher = localDisguise.getWatcher();
        localLivingWatcher.setCustomName("§d" + p.getName());
        localLivingWatcher.setCustomNameVisible(true);
        p.getOpenInventory().close();
        DisguiseAPI.disguiseToAll(p, localDisguise);
    }

    @EventHandler
    private void onPlayerLeftInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if ((e.getAction() == Action.LEFT_CLICK_AIR) || (e.getAction() == Action.LEFT_CLICK_BLOCK)) {
            if (!DisguiseAPI.isDisguised(p)) {
                return;
            }
            if (!DisguiseAPI.getDisguise(p).getType().equals(DisguiseType.VILLAGER)) {
                return;
            }
            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 10.0F, 1F);
            ParticleEffect.VILLAGER_HAPPY.display(2.0F, 2.0F, 2.0F, 0.0F, 45, p.getLocation(), 15.0D);
        }
    }

    @EventHandler
    private void onPlayerInteractRight(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if ((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            if (!DisguiseAPI.isDisguised(p)) {
                return;
            }
            if (!DisguiseAPI.getDisguise(p).getType().equals(DisguiseType.VILLAGER)) {
                return;
            }
            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_VILLAGER_HURT, 10.0F, 1F);
            ParticleEffect.VILLAGER_ANGRY.display(2.0F, 2.0F, 2.0F, 0.0F, 45, p.getLocation(), 15.0D);
        }
    }
}
