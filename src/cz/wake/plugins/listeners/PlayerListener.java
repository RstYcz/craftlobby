package cz.wake.plugins.listeners;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.help.HelpTopic;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import cz.wake.plugins.Main;
import cz.wake.plugins.GUI.GadgetsMenu;
import cz.wake.plugins.GUI.Menu;
import cz.wake.plugins.GUI.StatisticsMG;
import cz.wake.plugins.GUI.Servers;
import cz.wake.plugins.GUI.VIPMenu;
import cz.wake.plugins.gadgets.TNTFountain;
import cz.wake.plugins.particles.FrozenWalk;

public class PlayerListener implements Listener{

	private Main plugin;
	
	public PlayerListener(Main plugin){
		this.plugin = plugin;
	}
	
	private HashMap<Player, Double> _time = new HashMap();
	HashMap<Player, BukkitRunnable> _cdRunnable = new HashMap();
	
	Menu hlavniMenu = new Menu();
	GadgetsMenu gadgetsMenu = new GadgetsMenu();
	Servers servers = new Servers();
	VIPMenu vmenu = new VIPMenu();
	InvClick ic = new InvClick();
	FrozenWalk fw = new FrozenWalk();
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onPlayerJoin(PlayerJoinEvent e){
		
		Player p = e.getPlayer();
		PlayerInventory inv = p.getInventory();
		
		ItemStack compass = new ItemStack(Material.COMPASS, 1);
		ItemMeta compassMeta = compass.getItemMeta();
		
		ItemStack playerHead = new ItemStack(Material.SKULL_ITEM, 1);
		playerHead.setDurability((short)3);
		SkullMeta playerHeadMeta = (SkullMeta)playerHead.getItemMeta();
		
		ItemStack gadgets = new ItemStack(Material.ENDER_CHEST);
		ItemMeta gadgetsMeta = gadgets.getItemMeta();
		
		ItemStack hider = new ItemStack(Material.INK_SACK,1,(byte)10);
		ItemMeta hiderMeta = hider.getItemMeta();
		
		ItemStack shopVip = new ItemStack(Material.EMERALD);
		ItemMeta svMeta = shopVip.getItemMeta();
		
		compassMeta.setDisplayName("§bVyber serveru §7(Klikni pravym)");
		compass.setItemMeta(compassMeta);
		
		playerHeadMeta.setDisplayName("§aProfil §7(Klikni pravym)");
		playerHeadMeta.setOwner(p.getName());
		playerHead.setItemMeta(playerHeadMeta);
		
		svMeta.setDisplayName("§aVIP Shop §7(Klikni pravym)");
		shopVip.setItemMeta(svMeta);
		
		gadgetsMeta.setDisplayName("§5Gadgets §7(Klikni pravym)");
		gadgets.setItemMeta(gadgetsMeta);
		
		hiderMeta.setDisplayName("§7Hraci: §a§lVIDITELNY");
		hider.setItemMeta(hiderMeta);
		
		
		p.getInventory().setItem(0, compass);
		p.getInventory().setItem(1, playerHead);
		p.getInventory().setItem(2, shopVip);
		p.getInventory().setItem(4, gadgets);
		p.getInventory().setItem(7, hider);
		
		//Ochrana před použitím starých itemů
		p.getInventory().setItem(3, null);
		p.getInventory().setItem(5, null);
		p.getInventory().setItem(6, null);
		p.getInventory().setItem(8, null);
		
		inv.setHelmet(null);
        inv.setChestplate(null);
        inv.setLeggings(null);
        inv.setBoots(null);
		
		p.setWalkSpeed(0.3F);
		p.setHealth(20F);
		p.setSaturation(20F);
		p.setFoodLevel(20);
		p.setGameMode(GameMode.ADVENTURE);
	}
	
	@EventHandler
	public void onPlaceBlock(BlockPlaceEvent e){
		Player p = e.getPlayer();
		if(Main.getInstance().isDebug() && p.hasPermission("craftlobby.admin")){
			e.setCancelled(false);
		} else {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBreakBlock(BlockBreakEvent e){
		Player p = e.getPlayer();
		if(Main.getInstance().isDebug() && p.hasPermission("craftlobby.admin")){
			e.setCancelled(false);
		} else {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onItemMove(InventoryClickEvent e){
		Player p = (Player) e.getInventory().getHolder();
		if(Main.getInstance().isDebug() && p.hasPermission("craftlobby.admin")){
			e.setCancelled(false);
		} else {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent event){
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e){
		final Player p = e.getPlayer();
		if (((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK)) && 
				(e.getPlayer().getItemInHand().getType() == Material.SKULL_ITEM) && (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§aProfil §7(Klikni pravym)"))){
			this.hlavniMenu.openMenu(p);
		}
		if (((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK)) && 
				(e.getPlayer().getItemInHand().getType() == Material.EMERALD) && (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§aVIP Shop §7(Klikni pravym)"))){
			this.vmenu.openVIPMenu(p);
		}
		if (((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK)) && 
				(e.getPlayer().getItemInHand().getType() == Material.ENDER_CHEST) && (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§5Gadgets §7(Klikni pravym)"))){
			this.gadgetsMenu.openGadgetsMenu(p);
		}
		if (((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK)) && 
				(e.getPlayer().getItemInHand().getType() == Material.COMPASS) && (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§bVyber serveru §7(Klikni pravym)"))){
			this.servers.openServersMenu(p);
		}
		if (((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK))){
			if(e.getPlayer().getItemInHand().getType() == Material.INK_SACK && (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§7Hraci: §a§lVIDITELNY"))){
				if (!this._time.containsKey(e.getPlayer())){
					this._time.put(e.getPlayer(), Double.valueOf(8D + 0.1D));
					e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.WOOD_CLICK, 2.0F, 2.0F);
					for (Player players : Bukkit.getOnlinePlayers()){
						e.getPlayer().hidePlayer(players);
						ItemStack disable = new ItemStack(Material.INK_SACK,1,(byte)1);
				        ItemMeta im = disable.getItemMeta();
				        im.setDisplayName("§7Hraci: §c§lNEVIDITELNY");
				        disable.setItemMeta(im);
				        e.getPlayer().getInventory().setItem(7, disable);
				        e.getPlayer().updateInventory();
					}
					e.getPlayer().sendMessage(ChatColor.RED + "Vsechny aktualni hrace jsi zneviditelnil.");
					this._cdRunnable.put(e.getPlayer(), new BukkitRunnable(){
			      		@Override
			      		public void run()
			      		{
			      			PlayerListener.this._time.put(e.getPlayer(), Double.valueOf(((Double)PlayerListener.this._time.get(e.getPlayer())).doubleValue() - 0.1D));
			      			if (((Double)PlayerListener.this._time.get(e.getPlayer())).doubleValue() < 0.01D){
			      				PlayerListener.this._time.remove(e.getPlayer());
			      				PlayerListener.this._cdRunnable.remove(e.getPlayer());
			      				cancel();
			      				}
			      		}
			  	    });
			  	    ((BukkitRunnable)this._cdRunnable.get(e.getPlayer())).runTaskTimer(plugin, 2L, 2L);
				} else {
					MessagesListener.messageCooldown(e.getPlayer(), String.valueOf(arrondi(((Double)this._time.get(e.getPlayer())).doubleValue(), 1)));
			  		return;
				}
			}
			else {
				if((e.getPlayer().getItemInHand().getType() == Material.INK_SACK && (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§7Hraci: §c§lNEVIDITELNY")))){
					if (!this._time.containsKey(e.getPlayer())){
						this._time.put(e.getPlayer(), Double.valueOf(8D + 0.1D));
						e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.WOOD_CLICK, 2.0F, 2.0F);
						for (Player pl : Bukkit.getOnlinePlayers()){
							e.getPlayer().showPlayer(pl);
							ItemStack enable = new ItemStack(Material.INK_SACK,1,(byte)10);
					        ItemMeta im = enable.getItemMeta();
					        im.setDisplayName("§7Hraci: §a§lVIDITELNY");
					        enable.setItemMeta(im);
					        e.getPlayer().getInventory().setItem(7, enable);
					        e.getPlayer().updateInventory();
						}
						e.getPlayer().sendMessage(ChatColor.GREEN + "Vsechny hrace jsi zviditelnil.");
						this._cdRunnable.put(e.getPlayer(), new BukkitRunnable(){
				      		@Override
				      		public void run()
				      		{
				      			PlayerListener.this._time.put(e.getPlayer(), Double.valueOf(((Double)PlayerListener.this._time.get(e.getPlayer())).doubleValue() - 0.1D));
				      			if (((Double)PlayerListener.this._time.get(e.getPlayer())).doubleValue() < 0.01D){
				      				PlayerListener.this._time.remove(e.getPlayer());
				      				PlayerListener.this._cdRunnable.remove(e.getPlayer());
				      				cancel();
				      				}
				      		}
				  	    });
				  	    ((BukkitRunnable)this._cdRunnable.get(e.getPlayer())).runTaskTimer(plugin, 2L, 2L);
					} else {
						MessagesListener.messageCooldown(e.getPlayer(), String.valueOf(arrondi(((Double)this._time.get(e.getPlayer())).doubleValue(), 1)));
				  		return;
					}
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onLeave(final PlayerQuitEvent e){
		Player p = e.getPlayer();
		
		//Deaktivace particles
		ic.deactivateParticles(p);
		
		//Deaktivace cloaks
		ic.deactivateCloaks(p);
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onKick(PlayerKickEvent e){
		Player p = e.getPlayer();
		
		//Deaktivace particles
		ic.deactivateParticles(p);
		
		//Deaktivace cloaks
		ic.deactivateCloaks(p);
	}
	
	@EventHandler
	public void onFoodChange(FoodLevelChangeEvent e){
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onItemDrop(PlayerDropItemEvent e){
		e.setCancelled(true);
	}
	
	public static double arrondi(double A, int B){
		return (int)(A * Math.pow(10.0D, B) + 0.5D) / Math.pow(10.0D, B);
	}
	
}