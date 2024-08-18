package com.poor.kit.addon.modules;

import com.poor.kit.addon.PoorKit;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class AutoRepair extends Module {

    public AutoRepair() {
        super(
            PoorKit.CATEGORY, "Auto Repair Elytra",
            "Automatically replace non broken elytra to broken"
        );
    }

    @Override
    public void onActivate() {
        swapIfNecessary();
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        swapIfNecessary();
    }

    private void swapIfNecessary() {
        if (mc.player != null) {
            ItemStack checkSlotItem = mc.player.getEquippedStack(EquipmentSlot.CHEST);
            Item item = checkSlotItem.getItem();

            if (item == Items.ELYTRA) {
                boolean isDamaged = checkSlotItem.isDamaged();

                if (!isDamaged) {
                    tryEquipElytra();
                }

            }
        }
    }

    private void tryEquipElytra() {
        for (int i = 0; i < mc.player.getInventory().main.size(); i++) {
            ItemStack item = mc.player.getInventory().main.get(i);

            if (item.getItem() == Items.ELYTRA && item.isDamaged()) {
                Log.info(LogCategory.GENERAL, "equiping elytra");
                equip(i);
                break;
            }
        }
    }

    private void equip(int slot) {
        InvUtils.move().from(slot).toArmor(2);
    }
}
