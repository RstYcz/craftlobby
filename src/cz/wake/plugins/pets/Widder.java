package cz.wake.plugins.pets;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftWither;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import cz.wake.plugins.Main;

public class Widder {
	
	public static void activateCow(Player p){
		for (Iterator localIterator = p.getWorld().getEntities().iterator(); localIterator.hasNext();)
	    {
	      Object localObject = (CraftEntity)localIterator.next();
	      if (localObject == PetManager.pet.get(p))
	      {
	        PetManager.pet.remove(localObject);
	        ((CraftEntity)localObject).remove();
	      }
	    }
		final CraftWither wither = (CraftWither) p.getWorld().spawnEntity(p.getLocation(), EntityType.WITHER);
	    PetManager.PetFollow(p, (CraftEntity)wither, 0.16D, 1.5D);
	    setMetadata((CraftWither)wither, "Pet", "Pet", Main.getInstance());
	    ((CraftWither)wither).setCustomName("�f" + p.getName() + " Wither");
	    PetManager.pet.put(p, (CraftEntity) wither);
	    Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), new Runnable(){
			@Override
			public void run() {
				((CraftWither)wither).getHandle().r(600);
			}
	    	
	    }, 0L, 1L);
	}

	
	public static void setMetadata(CraftWither paramPig, String paramString, Object paramObject, Main paramMain)
	  {
	    paramPig.setMetadata(paramString, new FixedMetadataValue(paramMain, paramObject));
	  }
}
