package cz.wake.plugins.pets;

import java.util.Iterator;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Spider;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;

import cz.wake.plugins.Main;

public class SpiderNormal implements Listener{
	
	public static void activateSpider(Player p){
		for (Iterator localIterator = p.getWorld().getEntities().iterator(); localIterator.hasNext();)
	    {
	      Object localObject = (CraftEntity)localIterator.next();
	      if (localObject == PetManager.pet.get(p))
	      {
	        PetManager.pet.remove(localObject);
	        ((CraftEntity)localObject).remove();
	      }
	    }
		final Spider spider = (Spider) p.getWorld().spawnEntity(p.getLocation(), EntityType.SPIDER);
		PetManager.PetFollow(p, (CraftEntity)spider, 0.16D, 1.5D);
	    setMetadata((Spider)spider, "Pet", "Pet", Main.getInstance());
	    ((Spider)spider).setCustomNameVisible(true);
	    ((Spider)spider).setCustomName(ChatColor.LIGHT_PURPLE + p.getName());
	    PetManager.pet.put(p, (CraftEntity) spider);
	}
	
	public static void setMetadata(Spider paramPig, String paramString, Object paramObject, Main paramMain)
	  {
	    paramPig.setMetadata(paramString, new FixedMetadataValue(paramMain, paramObject));
	  }

}
