package cz.wake.plugins.pets;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.server.v1_9_R1.*;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import cz.wake.plugins.Main;

public class PetManager implements Listener{
	
	public static Map<Player, CraftEntity> pet = new HashMap();
	
	private Main plugin;
	
	public PetManager(Main plugin){
		this.plugin = plugin;
	}
	
	public static void PetFollow(final Player player , final org.bukkit.entity.Entity pet , final double speed, final double distance){
		new BukkitRunnable(){
			@Override
			public void run(){
				if ((!pet.isValid() || (!player.isOnline()))){
					this.cancel();}
				net.minecraft.server.v1_9_R1.Entity pett = ((CraftEntity) pet).getHandle();
				((EntityInsentient) pett).getNavigation().a(2);
				Object petf = ((CraftEntity) pet).getHandle();
				Location targetLocation = player.getLocation();
				PathEntity path;
				path = ((EntityInsentient) petf).getNavigation().a(targetLocation.getX() + distance, targetLocation.getY(), targetLocation.getZ() + distance);
				if (path != null) {
					((EntityInsentient) petf).getNavigation().a(path, 1.0D);
					((EntityInsentient) petf).getNavigation().a(2.0D);}
				if(player.getWorld().getName().equalsIgnoreCase(pet.getWorld().getName()) ){
                    if(!player.isOnline()){
                        pet.remove();
                    } else {
                        int distance = (int) Bukkit.getPlayer(player.getName()).getLocation().distance(pet.getLocation());
                        if (distance > 10 && !pet.isDead() && player.isOnGround()) {
                            pet.teleport(player.getLocation());
                        }
                    }
                } else {
                    if(!player.isOnline()){
                        pet.remove();
                    }else{
                        pet.teleport(player);
                    }
                }
				if(player.isOnline()){
					int distance = (int) Bukkit.getPlayer(player.getName()).getLocation().distance(pet.getLocation());
					if (distance > 10 && !pet.isDead() && player.isOnGround()) {
						pet.teleport(player.getLocation());}
				}
				AttributeInstance attributes = ((EntityInsentient)((CraftEntity)pet).getHandle()).getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
				attributes.setValue(speed);
			}
		}.runTaskTimer(Main.getPlugin(), 0L, 20L);}

	@EventHandler
	public void onEntityTeleport(EntityTeleportEvent e){
		Entity localEntity = e.getEntity();
	    if (!(localEntity instanceof Enderman)) {
	      return;
	    }
	    if (localEntity.hasMetadata("Pet")) {
	      e.setCancelled(true);
	    }
	}
	
	@EventHandler
	  public void PetRemoveOnLeave(PlayerQuitEvent paramPlayerQuitEvent){
	    Player localPlayer = paramPlayerQuitEvent.getPlayer();
	    for (Entity localEntity : localPlayer.getWorld().getEntities()) {
	      if (localEntity == pet.get(localPlayer)){
	        pet.remove(localEntity);
	        localEntity.remove();
	      }
	    }
	  }
	  
	  @EventHandler
	  public void PetRemoveOnLeave(PlayerKickEvent paramPlayerKickEvent) {
	    Player localPlayer = paramPlayerKickEvent.getPlayer();
	    for (Entity localEntity : localPlayer.getWorld().getEntities()) {
	      if (localEntity == pet.get(localPlayer)){
	        pet.remove(localEntity);
	        localEntity.remove();
	      }
	    }
	  }
	  
	  @EventHandler
	  public void MobOnFire(EntityCombustEvent paramEntityCombustEvent){
	    Entity localEntity = paramEntityCombustEvent.getEntity();
	    if ((!(localEntity instanceof Skeleton)) && (!(localEntity instanceof Zombie))) {
	      return;
	    }
	    if (localEntity.hasMetadata("Pet")) {
	      paramEntityCombustEvent.setCancelled(true);
	    }
	  }
	  
	  @EventHandler
	  public void onEntityClick(PlayerInteractEntityEvent e) {
			Player player = e.getPlayer();
			LivingEntity mob = (LivingEntity) e.getRightClicked();
			
			if(mob.getType() == EntityType.ENDERMITE){
				player.sendMessage("§cNa tohoto moba nelze nasednou!");
				return;
			}
			if(mob.getType() == EntityType.PLAYER){
				return;
			}
			if(mob.getType() == EntityType.PRIMED_TNT){
				return;
			}
			if(mob.getType() == EntityType.WITHER_SKULL){
				return;
			}
			if((mob.getName().contains(player.getName()))){
				mob.setPassenger(player);

				PacketPlayOutMount packet = new PacketPlayOutMount(((CraftPlayer)player).getHandle());
				for(Player p : player.getWorld().getPlayers()){
					((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
				}
			} else {
				player.sendMessage("§cTento mob neni tvuj!");
				return;
			}
	  }
	  
	  public static void forceRemovePet(Player p){
		  for (Entity localEntity : p.getWorld().getEntities()) {
		      if (localEntity == pet.get(p)){
		        pet.remove(localEntity);
		        localEntity.remove();
		      }
		  }
		  if(CowNormal.cp.contains(p.getName())){
			  CowNormal.cp.remove(p.getName());
		  }
		  if(ChickenNormal.cn.contains(p.getName())){
			  ChickenNormal.cn.remove(p.getName());
		  }
		  if(PigNormal.pn.contains(p.getName())){
			  PigNormal.pn.remove(p.getName());
		  }
		  if(WolfNormal.wn.contains(p.getName())){
			  WolfNormal.wn.remove(p.getName());
		  }
		  if(SilverfishNormal.sn.contains(p.getName())){
			  SilverfishNormal.sn.remove(p.getName());
		  }
		  if(ZombieNormal.zn.contains(p.getName())){
			  ZombieNormal.zn.remove(p.getName());
		  }
		  if(EndermiteNormal.en.contains(p.getName())){
			  EndermiteNormal.en.remove(p.getName());
		  }
		  if(RabbitBrown.rb.contains(p.getName())){
			  RabbitBrown.rb.remove(p.getName());
		  }
		  if(RabbitBlack.rb.contains(p.getName())){
			  RabbitBlack.rb.remove(p.getName());
		  }
		  if(RabbitGold.rg.contains(p.getName())){
			  RabbitGold.rg.remove(p.getName());
		  }
		  if(Cat.cb.contains(p.getName())){
			  Cat.cb.remove(p.getName());
		  }
		  if(SheepWhite.sw.contains(p.getName())){
			  SheepWhite.sw.remove(p.getName());
		  }
		  if(SheepGray.sg.contains(p.getName())){
			  SheepGray.sg.remove(p.getName());
		  }
		  if(SheepBrown.sb.contains(p.getName())){
			  SheepBrown.sb.remove(p.getName());
		  }
		  if(SheepSilver.ss.contains(p.getName())){
			  SheepSilver.ss.remove(p.getName());
		  }
		  if(HorseBrown.hb.contains(p.getName())){
			  HorseBrown.hb.remove(p.getName());
		  }
		  if(ZombieBaby.zb.contains(p.getName())){
			  ZombieBaby.zb.remove(p.getName());
		  }
		  if(PigBaby.pb.contains(p.getName())){
			  PigBaby.pb.remove(p.getName());
		  }
		  if(CowBaby.cb.contains(p.getName())){
			  CowBaby.cb.remove(p.getName());
		  }
		  if(WolfBaby.wb.contains(p.getName())){
			  WolfBaby.wb.remove(p.getName());
		  }
		  if(ChickenBaby.cb.contains(p.getName())){
			  ChickenBaby.cb.remove(p.getName());
		  }
		  if(RabbitBrownBaby.rb.contains(p.getName())){
			  RabbitBrownBaby.rb.remove(p.getName());
		  }
		  if(RabbitBlackBaby.rb.contains(p.getName())){
			  RabbitBlackBaby.rb.remove(p.getName());
		  }
		  if(RabbitGoldBaby.rg.contains(p.getName())){
			  RabbitGoldBaby.rg.remove(p.getName());
		  }
		  if(HorseBrownBaby.hb.contains(p.getName())){
			  HorseBrownBaby.hb.remove(p.getName());
		  }
		  if(SheepWhiteBaby.sw.contains(p.getName())){
			  SheepWhiteBaby.sw.remove(p.getName());
		  }
		  if(SheepGrayBaby.sg.contains(p.getName())){
			  SheepGrayBaby.sg.remove(p.getName());
		  }
		  if(SheepSilverBaby.ss.contains(p.getName())){
			  SheepSilverBaby.ss.remove(p.getName());
		  }
		  if(SheepBrownBaby.sb.contains(p.getName())){
			  SheepBrownBaby.sb.remove(p.getName());
		  }
				  
	  }
}
