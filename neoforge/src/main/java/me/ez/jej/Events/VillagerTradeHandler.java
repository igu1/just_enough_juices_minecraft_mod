package me.ez.jej.Events;

import me.ez.jej.Config;
import me.ez.jej.Init;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class VillagerTradeHandler {

    @SubscribeEvent
    public static void onVillagerTrades(VillagerTradesEvent event) {
        if (!Config.ENABLE_VILLAGER_TRADES.get()) {
            return;
        }
        if (event.getType() == VillagerProfession.FARMER) {
            List<VillagerTrades.ItemListing> level1 = event.getTrades().get(1);
            List<VillagerTrades.ItemListing> level2 = event.getTrades().get(2);
            List<VillagerTrades.ItemListing> level3 = event.getTrades().get(3);
            List<VillagerTrades.ItemListing> level4 = event.getTrades().get(4);

            level1.add(juice(Init.APPLE_JUICE.get(), 2, 4, 2));
            level1.add(juice(Init.SWEETBERRY_JUICE.get(), 2, 4, 2));

            level2.add(juice(Init.CARROT_JUICE.get(), 3, 4, 5));
            level2.add(juice(Init.WILDBERRY_JUICE.get(), 3, 4, 5));

            level3.add(juice(Init.ICEBERRY_JUICE.get(), 4, 3, 10));
            level3.add(juice(Init.MELON_JUICE.get(), 4, 3, 10));

            level4.add(juice(Init.GOLDENAPPLE_JUICE.get(), 8, 2, 20));
        } else if (event.getType() == VillagerProfession.CLERIC) {
            event.getTrades().get(2).add(juice(Init.NETHERWART_JUICE.get(), 4, 4, 10));
            event.getTrades().get(3).add(juice(Init.SPICY_JUICE.get(), 5, 3, 15));
        }
    }

    @SubscribeEvent
    public static void onWandererTrades(WandererTradesEvent event) {
        if (!Config.ENABLE_VILLAGER_TRADES.get()) {
            return;
        }
        event.getRareTrades().add(juice(Init.GOLEM_JUICE.get(), 12, 2, 30));
        event.getRareTrades().add(juice(Init.GLISTERING_MELON_JUICE.get(), 8, 2, 25));
        event.getRareTrades().add(juice(Init.GOLDENCARROT_JUICE.get(), 8, 2, 25));
        event.getRareTrades().add(juice(Init.CHORUS_JUICE.get(), 6, 3, 20));
        event.getRareTrades().add(juice(Init.GLOWBERRY_JUICE.get(), 6, 3, 20));
    }

    private static VillagerTrades.ItemListing juice(Item juice, int emeraldCost, int count, int xp) {
        return (trader, random) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, emeraldCost),
                new ItemStack(juice, count),
                6,
                xp,
                0.05F);
    }
}
