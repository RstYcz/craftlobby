package cz.wake.lobby.gadgets.pets;

import cz.wake.lobby.Main;
import cz.wake.lobby.utils.mobs.RideableCat;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftEntity;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Ocelot.Type;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.Iterator;

public class CatNormal {

    public static ArrayList<String> cb = new ArrayList();

    public static void activate(final Player p, boolean baby, Type type) {
        for (Iterator localIterator = p.getWorld().getEntities().iterator(); localIterator.hasNext(); ) {
            Object localObject = (CraftEntity) localIterator.next();
            if (localObject == PetManager.pet.get(p)) {
                PetManager.forceRemovePet(p);
                ((CraftEntity) localObject).remove();
            }
        }
        final Ocelot ocelot = RideableCat.spawn(p.getLocation());
        PetManager.petFollow(p, (CraftEntity) ocelot, 0.18D, 1.0D);
        setMetadata((Ocelot) ocelot, "Pet", "Pet", Main.getInstance());
        if (baby) {
            ((Ocelot) ocelot).setBaby();
        } else {
            ((Ocelot) ocelot).setAdult();
        }
        ((Ocelot) ocelot).setCatType(type);
        ((Ocelot) ocelot).setCustomNameVisible(true);
        ((Ocelot) ocelot).setCustomName(p.getName());
        PetManager.pet.put(p, (CraftEntity) ocelot);
        cb.add(p.getName());
        p.closeInventory();
    }

    public static void setMetadata(Ocelot paramPig, String paramString, Object paramObject, Main paramMain) {
        paramPig.setMetadata(paramString, new FixedMetadataValue(paramMain, paramObject));
    }

}