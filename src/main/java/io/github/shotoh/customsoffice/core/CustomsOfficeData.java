package io.github.shotoh.customsoffice.core;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import io.github.shotoh.customsoffice.CustomsOffice;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class CustomsOfficeData {
    private final CustomsOffice plugin;
    private ArrayList<NonNativeAnimal> nonNativeAnimals;
    private ArrayList<PurchaseOrder> purchaseOrders;
    private NPC animalNPC;
    private HashMap<ProtectedRegion, EntityType> regionHashMap;

    public CustomsOfficeData(CustomsOffice plugin) {
        this.plugin = plugin;
        this.nonNativeAnimals = new ArrayList<>();
        this.purchaseOrders = new ArrayList<>();
        this.animalNPC = null;
        this.regionHashMap = new HashMap<>();
    }

    public ArrayList<NonNativeAnimal> getNonNativeAnimals() {
        return nonNativeAnimals;
    }

    public void setNonNativeAnimals(ArrayList<NonNativeAnimal> nonNativeAnimals) {
        this.nonNativeAnimals = nonNativeAnimals;
    }

    public ArrayList<PurchaseOrder> getPurchaseOrders() {
        return purchaseOrders;
    }

    public void setPurchaseOrders(ArrayList<PurchaseOrder> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }

    public NPC getAnimalNPC() {
        return animalNPC;
    }

    public void setAnimalNPC(NPC animalNPC) {
        this.animalNPC = animalNPC;
    }

    public HashMap<ProtectedRegion, EntityType> getRegionHashMap() {
        return regionHashMap;
    }

    public void setRegionHashMap(HashMap<ProtectedRegion, EntityType> regionHashMap) {
        this.regionHashMap = regionHashMap;
    }

    public void load() {
        Gson gson = plugin.getGson();
        try {
            // orders
            FileReader orderReader = new FileReader(getFile("orders.json"));
            ArrayList<PurchaseOrder> orders = gson.fromJson(orderReader, new TypeToken<ArrayList<PurchaseOrder>>(){}.getType());
            orderReader.close();
            if (orders != null) {
                purchaseOrders.addAll(orders);
            }
            plugin.getLogger().info(purchaseOrders.size() + " purchase orders loaded!");
            // animals
            FileReader animalReader = new FileReader(getFile("animals.json"));
            ArrayList<NonNativeAnimal> animals = gson.fromJson(animalReader, new TypeToken<ArrayList<NonNativeAnimal>>(){}.getType());
            animalReader.close();
            if (animals == null) {
                animals = new ArrayList<>();
            }


            animals.add(new NonNativeAnimal(EntityType.DONKEY, 15, 15, 200));
            //animals.add(new NonNativeAnimal(EntityType.PIG, 5, 400));


            HashSet<EntityType> typesSet = new HashSet<>();
            for (NonNativeAnimal nonNativeAnimal : animals) {
                if (!typesSet.contains(nonNativeAnimal.getType())) {
                    typesSet.add(nonNativeAnimal.getType());
                    nonNativeAnimals.add(nonNativeAnimal);
                }
            }
            plugin.getLogger().info(nonNativeAnimals.size() + " non native animals loaded!");
            //
            WorldGuard.getInstance().getPlatform().getRegionContainer().get((World) Bukkit.getWorlds().get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // TODO close all guis when event happened
        // TODO food person
        // TODO amounts?
        // TODO hook into vault
        // commands
    }

    public void save() {
        Gson gson = plugin.getGson();
        try {
            // animals
            FileWriter animalWriter = new FileWriter(getFile("animals.json"));
            gson.toJson(nonNativeAnimals, new TypeToken<ArrayList<NonNativeAnimal>>(){}.getType(), animalWriter);
            animalWriter.close();
            plugin.getLogger().info(nonNativeAnimals.size() + " non native animals saved!");
            // orders
            FileWriter orderWriter = new FileWriter(getFile("orders.json"));
            gson.toJson(purchaseOrders, new TypeToken<ArrayList<PurchaseOrder>>(){}.getType(), orderWriter);
            orderWriter.close();
            plugin.getLogger().info(purchaseOrders.size() + " purchase orders saved!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getFile(String path) {
        File file = new File(plugin.getDataFolder(), path);
        if (!file.exists()) {
            plugin.saveResource(path, false);
        }
        return file;
    }
}
