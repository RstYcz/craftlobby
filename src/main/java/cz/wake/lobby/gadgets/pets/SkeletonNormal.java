package cz.wake.lobby.gadgets.pets;

import cz.wake.lobby.Main;
import cz.wake.lobby.utils.mobs.RideableSkeleton;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.Iterator;

public class SkeletonNormal {

    public static ArrayList<String> cn = new ArrayList();

    public static void activateWitch(Player p, Skeleton.SkeletonType type) {
        for (Iterator localIterator = p.getWorld().getEntities().iterator(); localIterator.hasNext(); ) {
            Object localObject = (CraftEntity) localIterator.next();
            if (localObject == PetManager.pet.get(p)) {
                PetManager.forceRemovePet(p);
                ((CraftEntity) localObject).remove();
            }
        }
        final Skeleton e = RideableSkeleton.spawn(p.getLocation());
        PetManager.PetFollow(p, (CraftEntity) e, 0.16D, 2D);
        setMetadata((Skeleton)e, "Pet", "Pet", Main.getInstance());
        ((Skeleton) e).setSkeletonType(type);
        ((Skeleton) e).setCustomNameVisible(true);
        ((Skeleton) e).setCustomName(p.getName());
        PetManager.pet.put(p, (CraftEntity) e);
        cn.add(p.getName());
        p.closeInventory();

    }

    public static void setMetadata(Skeleton paramPig, String paramString, Object paramObject, Main paramMain) {
        paramPig.setMetadata(paramString, new FixedMetadataValue(paramMain, paramObject));
    }
}