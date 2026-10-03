package me.ez.jej.common;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class JuiceBoosterItem extends Item {

    public JuiceBoosterItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        tooltip.add(new TranslatableComponent("item.jej.juice_booster.desc").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return true;
    }
}
