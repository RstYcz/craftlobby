package cz.wake.plugins.utils;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_8_R3.ItemSkull;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

public class ItemFactory {

    public static ItemStack create(Material material, byte data, String displayName, String... lore) {
        ItemStack itemStack = new MaterialData(material, data).toItemStack(1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        if (lore != null) {
            List<String> finalLore = new ArrayList<>();
            for (String s : lore)
                finalLore.add(s);
            itemMeta.setLore(finalLore);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack create(Material material, byte data, String displayName) {
        return create(material, data, displayName, null);
    }
    
    public static org.bukkit.inventory.ItemStack createHead(String name, String uuid, String textureData)
    {
      net.minecraft.server.v1_8_R3.ItemStack sHead = CraftItemStack.asNMSCopy(new org.bukkit.inventory.ItemStack(Material.SKULL_ITEM, 1, (short)3));
      
      NBTTagCompound tag = new NBTTagCompound();
      NBTTagCompound skullOwnerTag = new NBTTagCompound();
      NBTTagCompound displayTag = new NBTTagCompound();
      NBTTagCompound propertiesTag = new NBTTagCompound();
      
      NBTTagList tagList = new NBTTagList();
      
      NBTTagCompound valueTag = new NBTTagCompound();
      valueTag.setString("Value", textureData);
      
      tagList.add(valueTag);
      
      propertiesTag.set("textures", tagList);
      
      skullOwnerTag.setString("Id", uuid);
      skullOwnerTag.setString("Name", name);
      
      skullOwnerTag.set("Properties", propertiesTag);
      
      displayTag.setString("Name", name);
      
      tag.set("SkullOwner", skullOwnerTag);
      
      tag.set("display", displayTag);
      
      sHead.setTag(tag);
      return CraftItemStack.asBukkitCopy(sHead);
    }
    
    public static ItemStack createColouredLeather(Material armourPart, int red, int green, int blue) {
        ItemStack itemStack = new ItemStack(armourPart);
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta)itemStack.getItemMeta();
        leatherArmorMeta.setColor(Color.fromRGB(red, green, blue));
        itemStack.setItemMeta(leatherArmorMeta);
        return itemStack;
    }
}