package io.github.shotoh.customsoffice.core;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.github.shotoh.customsoffice.CustomsOffice;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.EntityType;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

public class CustomsOfficeData {
    private final CustomsOffice plugin;
    private ArrayList<NonNativeAnimal> nonNativeAnimals;
    private ArrayList<PurchaseOrder> purchaseOrders;
    private NPC npc;

    public CustomsOfficeData(CustomsOffice plugin) {
        this.plugin = plugin;
        this.nonNativeAnimals = new ArrayList<>();
        this.purchaseOrders = new ArrayList<>();
        this.npc = null;
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

    public NPC getNpc() {
        return npc;
    }

    public void setNpc(NPC npc) {
        this.npc = npc;
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
            if (animals != null) {


                animals.add(new NonNativeAnimal(EntityType.DONKEY, 15, 200));
                //animals.add(new NonNativeAnimal(EntityType.PIG, 5, 400));


                HashSet<EntityType> typesSet = new HashSet<>();
                for (NonNativeAnimal nonNativeAnimal : animals) {
                    if (!typesSet.contains(nonNativeAnimal.getType())) {
                        typesSet.add(nonNativeAnimal.getType());
                        for (PurchaseOrder order : purchaseOrders) {
                            if (order.getType() == nonNativeAnimal.getType()) {
                                nonNativeAnimal.setRemainingQuantity(nonNativeAnimal.getRemainingQuantity() - 1);
                            }
                        }
                        nonNativeAnimals.add(nonNativeAnimal);
                    }
                }
            }
            plugin.getLogger().info(nonNativeAnimals.size() + " non native animals loaded!");
            // npc
            String npcUUIDString = plugin.getConfig().getString("npc-uuid");
            try {
                if (npcUUIDString != null) {
                    UUID npcUUID = UUID.fromString(npcUUIDString);
                    npc = CitizensAPI.getNPCRegistry().getByUniqueId(npcUUID);
                } else {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException e) {
                plugin.getLogger().severe("An invalid NPC uuid has been found in the config");
                plugin.getLogger().severe("All NPC interactions pertaining to this plugin will not work");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // TODO close all guis when event happened
        // TODO load worldguard
        // TODO food person
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