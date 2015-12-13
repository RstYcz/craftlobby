package cz.wake.plugins.gadgets;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import cz.wake.plugins.Main;
import cz.wake.plugins.listeners.MessagesListener;
import cz.wake.plugins.utils.ItemFactory;
import cz.wake.plugins.utils.UtilMath;
import cz.wake.plugins.utils.UtilParticles;

public class BlackHole implements Listener{
	
	private HashMap<Player, Double> _time = new HashMap();
	HashMap<Player, BukkitRunnable> _cdRunnable = new HashMap();
	
	Item i;
	
	private Main plugin;
	
	public BlackHole(Main plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPee(PlayerInteractEvent e){
		final Player player = e.getPlayer();
	    ItemStack item = e.getItem();
	    Action action = e.getAction();
		
	    if(item == null){
	    	return;
	    }
	    if (!item.hasItemMeta()) {
	        return;
	    }
	    if (!item.getType().equals(Material.STAINED_CLAY)) {
	        return;
	    }
	    if (!item.getItemMeta().getDisplayName().contains("BlackHole")) {
	        return;
	    }
	    if (!player.hasPermission("craftlobby.gadget.blackhole")){
	    	return;
	    }
	    e.setCancelled(true);
	    player.updateInventory();
	    if ((action.equals(Action.RIGHT_CLICK_AIR)) || (action.equals(Action.RIGHT_CLICK_BLOCK))){
	    	if (this._time.containsKey(player)){
			  	MessagesListener.messageCooldown(player, String.valueOf(arrondi(((Double)this._time.get(player)).doubleValue(), 1)));
			  		return;
			  	}
	    	this._time.put(player, Double.valueOf(20D + 0.1D));
	    	if (i != null) {
	            i.remove();
	            i = null;
	        }
	        Item item2 = player.getWorld().dropItem(player.getEyeLocation(), ItemFactory.create(Material.STAINED_CLAY, (byte) 0xf, UUID.randomUUID().toString()));
	        item2.setPickupDelay(Integer.MAX_VALUE);
	        item2.setVelocity(player.getEyeLocation().getDirection().multiply(1.3d));
	        i = item2;

			@SuppressWarnings("deprecation")
			final int w = Bukkit.getServer().getScheduler().runTaskTimer(Main.getPlugin(), new BukkitRunnable(){
	        	@Override
	        	public void run(){
	        		if (i != null && i.isOnGround()) {
	                    int strands = 6;
	                    int particles = 25;
	                    float radius = 5;
	                    float curve = 10;
	                    double rotation = Math.PI / 4;

	                    Location location = i.getLocation();
	                    for (int i = 1; i <= strands; i++) {
	                        for (int j = 1; j <= particles; j++) {
	                            float ratio = (float) j / particles;
	                            double angle = curve * ratio * 2 * Math.PI / strands + (2 * Math.PI * i / strands) + rotation;
	                            double x = Math.cos(angle) * ratio * radius;
	                            double z = Math.sin(angle) * ratio * radius;
	                            location.add(x, 0, z);
	                            UtilParticles.play(location, Effect.LARGE_SMOKE);
	                            location.subtract(x, 0, z);
	                        }
	                    }
	                    for (Entity ent : i.getNearbyEntities(5, 3, 5)) {
	                        Vector vector = i.getLocation().toVector().subtract(ent.getLocation().toVector());
	                        UtilMath.applyVelocity(ent, vector);
	                        if (ent instanceof Player)
	                            ((Player) ent).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 40));
	                    }
	                }
	        	}
	        }, 5L, 5L).getTaskId();
	        Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable(){
	    		@Override
	    		public void run(){
	    			Bukkit.getScheduler().cancelTask(w);
	    			clear();
	    		}
	    	}, 240L);
	        
	        this._cdRunnable.put(player, new BukkitRunnable(){
	    		@Override
	    		public void run(){
	    			BlackHole.this._time.put(player, Double.valueOf(((Double)BlackHole.this._time.get(player)).doubleValue() - 0.1D));
	      			if (((Double)BlackHole.this._time.get(player)).doubleValue() < 0.01D){
	      				BlackHole.this._time.remove(player);
	      				BlackHole.this._cdRunnable.remove(player);
	      				cancel();
	    		}
	    		
	    	}
	    });((BukkitRunnable)this._cdRunnable.get(player)).runTaskTimer(plugin, 2L, 2L); 
	    }
	}
	
	 public void clear() {
	        if (i != null)
	            i.remove();
	    }
	 
	 private static double arrondi(double A, int B){
		    return (int)(A * Math.pow(10.0D, B) + 0.5D) / Math.pow(10.0D, B);
		}
	
	

}