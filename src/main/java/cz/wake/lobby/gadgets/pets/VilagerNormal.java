package cz.wake.lobby.gadgets.pets;

/*import cz.wake.lobby.Main;
import cz.wake.lobby.utils.mobs.RideableVillager;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.Iterator;

public class VilagerNormal {

    public static ArrayList<String> cn = new ArrayList();

    public static void activateWitch(Player p, boolean baby, Villager.Profession pr) {
        for (Iterator localIterator = p.getWorld().getEntities().iterator(); localIterator.hasNext(); ) {
            Object localObject = (CraftEntity) localIterator.next();
            if (localObject == PetManager.pet.get(p)) {
                PetManager.forceRemovePet(p);
                ((CraftEntity) localObject).remove();
            }
        }
        final Villager e = RideableVillager.spawn(p.getLocation());
        PetManager.petFollow(p, (CraftEntity) e, 0.16D, 2D);
        setMetadata((Villager) e, "Pet", "Pet", Main.getInstance());
        if (baby) {
            ((Villager) e).setBaby();
        }
        ((Villager) e).setProfession(pr);
        ((Villager) e).setCustomNameVisible(true);
        ((Villager) e).setCustomName(p.getName());
        PetManager.pet.put(p, (CraftEntity) e);
        cn.add(p.getName());
        p.closeInventory();

    }

    public static void setMetadata(Villager paramPig, String paramString, Object paramObject, Main paramMain) {
        paramPig.setMetadata(paramString, new FixedMetadataValue(paramMain, paramObject));
    }
}*/
