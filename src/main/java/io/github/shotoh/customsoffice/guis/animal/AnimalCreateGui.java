package io.github.shotoh.customsoffice.guis.animal;

import com.rpkit.characters.bukkit.character.RPKCharacter;
import com.rpkit.characters.bukkit.character.RPKCharacterService;
import com.rpkit.core.service.Services;
import com.rpkit.economy.bukkit.currency.RPKCurrency;
import com.rpkit.economy.bukkit.currency.RPKCurrencyName;
import com.rpkit.economy.bukkit.currency.RPKCurrencyService;
import com.rpkit.economy.bukkit.economy.RPKEconomyService;
import com.rpkit.players.bukkit.profile.minecraft.RPKMinecraftProfileService;
import io.github.shotoh.customsoffice.CustomsOffice;
import io.github.shotoh.customsoffice.core.NonNativeAnimal;
import io.github.shotoh.customsoffice.core.PurchaseOrder;
import io.github.shotoh.customsoffice.guis.CustomsOfficeGui;
import io.github.shotoh.customsoffice.utils.GuiUtils;
import io.github.shotoh.customsoffice.utils.Utils;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AnimalCreateGui extends CustomsOfficeGui {
    private ArrayList<NonNativeAnimal> list;
    private int maxPages;
    private int page;

    public AnimalCreateGui(CustomsOffice plugin, Player player) {
        super(plugin, player);
        this.list = new ArrayList<>(plugin.getCustomsOfficeData().getNonNativeAnimals());
        this.maxPages = list.size() / 28 + 1;
        this.page = 0;
    }

    public AnimalCreateGui(CustomsOffice plugin, Player player, ArrayList<NonNativeAnimal> list, int page) {
        super(plugin, player);
        this.list = list;
        this.maxPages = list.size() / 28 + 1;
        if (page >= 0 && page < maxPages) {
            this.page = page;
        } else {
            this.page = 0;
        }
    }

    @Override
    public @NotNull Inventory getInventory() {
        MiniMessage mm = plugin.getMiniMessage();
        Inventory inv = Bukkit.createInventory(this, 54, mm.deserialize("Create Purchase Order"));
        for (int i = 0; i < 54; i++) {
            if (GuiUtils.notBorder(i)) {
                int index = GuiUtils.convertSlotToIndex(i);
                if (index + (page * 28) < list.size()) {
                    inv.setItem(i, list.get(index).getSpawnEgg(plugin));
                }
            } else if (i == 48) {
                if (maxPages > 1 && page > 0) {
                    inv.setItem(i, GuiUtils.getPreviousArrow(plugin));
                } else {
                    inv.setItem(i, GuiUtils.getGuiGlass(plugin));
                }
            } else if (i == 49) {
                inv.setItem(i, GuiUtils.getGuiClose(plugin));
            } else if (i == 50) {
                if (maxPages > 1 && page < maxPages - 1) {
                    inv.setItem(i, GuiUtils.getNextArrow(plugin));
                } else {
                    inv.setItem(i, GuiUtils.getGuiGlass(plugin));
                }
            } else {
                inv.setItem(i, GuiUtils.getGuiGlass(plugin));
            }
        }
        return inv;
    }

    @Override
    public void onInventoryClickEvent(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof AnimalCreateGui) {
            event.setCancelled(true);
            Inventory inv = event.getClickedInventory();
            Player player = (Player) event.getWhoClicked();
            int slot = event.getSlot();
            if (inv != player.getInventory()) {
                if (GuiUtils.notBorder(slot)) {
                    int index = GuiUtils.convertSlotToIndex(slot);
                    if (index >= 0 && index < 28) {
                        index += page * 28;
                        if (index < list.size()) {
                            NonNativeAnimal nonNativeAnimal = list.get(index);
                            if (nonNativeAnimal.getRemainingQuantity() > 0) {
                                RPKMinecraftProfileService minecraftProfileService = Services.INSTANCE.get(RPKMinecraftProfileService.class);
                                RPKCharacterService characterService = Services.INSTANCE.get(RPKCharacterService.class);
                                RPKCurrencyService currencyService = Services.INSTANCE.get(RPKCurrencyService.class);
                                RPKEconomyService economyService = Services.INSTANCE.get(RPKEconomyService.class);
                                RPKCurrency currency = currencyService.getCurrency(new RPKCurrencyName(nonNativeAnimal.getCurrencyName()));
                                if (currency != null) {
                                    RPKCharacter character = characterService.getPreloadedActiveCharacter(minecraftProfileService.getPreloadedMinecraftProfile(player));
                                    int amount = economyService.getPreloadedBalance(character, currency);
                                    if (amount >= nonNativeAnimal.getCost()) {
                                        new BukkitRunnable() {
                                            @Override
                                            public void run() {
                                                player.openInventory(new AnimalConfirmGui(plugin, player, nonNativeAnimal).getInventory());
                                                Utils.playSound(player, "block.lever.click", 1f, 1f);
                                            }
                                        }.runTaskLater(plugin, 1);
                                    } else {
                                        Utils.playSound(player, "entity.villager.no", 1f, 1f);
                                    }
                                } else {
                                    player.sendMessage(plugin.getMiniMessage().deserialize("<red>An error has occurred and the transaction could not be made!"));
                                }
                            } else {
                                Utils.playSound(player, "entity.villager.no", 1f, 1f);
                            }
                        }
                    }
                } else if (slot == 48) {
                    if (maxPages > 1 && page > 0) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.openInventory(new AnimalCreateGui(plugin, player, list, page - 1).getInventory());
                                Utils.playSound(player, "block.lever.click", 1f, 1f);
                            }
                        }.runTaskLater(plugin, 1);
                    }
                } else if (slot == 49) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.closeInventory();
                        }
                    }.runTaskLater(plugin, 1);
                } else if (slot == 50) {
                    if (maxPages > 1 && page < maxPages - 1) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.openInventory(new AnimalCreateGui(plugin, player, list, page + 1).getInventory());
                                Utils.playSound(player, "block.lever.click", 1f, 1f);
                            }
                        }.runTaskLater(plugin, 1);
                    }
                }
            }
        }
    }
}
