package me.ez.jej.common;

import com.google.common.collect.Lists;
import me.ez.jej.Config;
import me.ez.jej.Init;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class JuiceClass extends PotionItem {

    private static final Component NO_EFFECT = (Component.translatable("effect.none")).withStyle(ChatFormatting.GRAY);


    public JuiceClass(Properties properties) {
        super(properties.stacksTo(1));
    }

    private List<MobEffectInstance> getJuiceEffects(ItemStack stack) {

        List<MobEffectInstance> list = Lists.newArrayList();

        if (Init.APPLE_JUICE.get() == stack.getItem() || Init.APPLE_JUICE_BOOSTED.get() == stack.getItem()) {
            if (stack.getItem() == Init.APPLE_JUICE_BOOSTED.get()) {
                list.add(new MobEffectInstance(Init.APPLE_BOOSTED_EFFECT, 3600, 0));
            } else {
                list.add(new MobEffectInstance(Init.APPLE_EFFECT, 3600, 0));
            }

        } else if (Init.SWEETBERRY_JUICE.get() == stack.getItem() || Init.SWEETBERRY_JUICE_BOOSTED.get() == stack.getItem()) {
            if (stack.getItem() == Init.SWEETBERRY_JUICE_BOOSTED.get()) {
                list.add(new MobEffectInstance(Init.SWEETBERRY_BOOSTED_EFFECT, 3600, 0));
            } else {
                list.add(new MobEffectInstance(Init.SWEETBERRY_EFFECT, 1200, 0));
            }

        } else if (Init.MELON_JUICE.get() == stack.getItem() || Init.MELON_JUICE_BOOSTED.get() == stack.getItem()) {
            if (stack.getItem() == Init.MELON_JUICE_BOOSTED.get()) {
                list.add(new MobEffectInstance(Init.MELON_BOOSTED_EFFECT, 3600, 0));
            } else {
                list.add(new MobEffectInstance(Init.MELON_EFFECT, 1200, 0));
            }

        } else if (Init.BAKEDPOTATO_JUICE.get() == stack.getItem() || Init.BAKEDPOTATO_JUICE_BOOSTED.get() == stack.getItem()) {
            if (stack.getItem() == Init.BAKEDPOTATO_JUICE_BOOSTED.get()) {
                list.add(new MobEffectInstance(Init.BAKEDPOTATO_BOOSTED_EFFECT, 2400, 0));
            } else {
                list.add(new MobEffectInstance(Init.BAKEDPOTATO_EFFECT, 3600, 0));
            }

        } else if (Init.PUMPKIN_JUICE.get() == stack.getItem() || Init.PUMPKIN_JUICE_BOOSTED.get() == stack.getItem()) {
            if (stack.getItem() == Init.PUMPKIN_JUICE_BOOSTED.get()) {
                list.add(new MobEffectInstance(Init.PUMPKIN_BOOSTED_EFFECT, 2400, 0));
            } else {
                list.add(new MobEffectInstance(Init.PUMPKIN_EFFECT, 1200, 0));
            }

        } else if (Init.WILDBERRY_JUICE.get() == stack.getItem() || Init.WILDBERRY_JUICE_BOOSTED.get() == stack.getItem()) {
            if (stack.getItem() == Init.WILDBERRY_JUICE_BOOSTED.get()) {
                list.add(new MobEffectInstance(Init.WILDBERRY_BOOSTED_EFFECT, 1200, 0));
            } else {
                list.add(new MobEffectInstance(Init.WILDBERRY_EFFECT, 1100, 0));
            }

        } else if (Init.ICEBERRY_JUICE.get() == stack.getItem() || Init.ICEBERRY_JUICE_BOOSTED.get() == stack.getItem()) {
            if (stack.getItem() == Init.ICEBERRY_JUICE_BOOSTED.get()) {
                list.add(new MobEffectInstance(Init.ICEBERRY_BOOSTED_EFFECT, 1200, 0));
            } else {
                list.add(new MobEffectInstance(Init.ICEBERRY_EFFECT, 1200, 0));
            }

        } else if (Init.DRIEDKELP_JUICE.get() == stack.getItem() || Init.DRIEDKELP_JUICE_BOOSTED.get() == stack.getItem()) {
            if (stack.getItem() == Init.DRIEDKELP_JUICE_BOOSTED.get()) {
                list.add(new MobEffectInstance(Init.DRIEDKELP_BOOSTED_EFFECT, 2400, 0));
            } else {
                list.add(new MobEffectInstance(Init.DRIEDKELP_EFFECT, 1200, 0));
            }

        } else if (Init.CARROT_JUICE.get() == stack.getItem() || Init.CARROT_JUICE_BOOSTED.get() == stack.getItem()) {
            if (stack.getItem() == Init.CARROT_JUICE_BOOSTED.get()) {
                list.add(new MobEffectInstance(Init.CARROT_BOOSTED_EFFECT, 3600, 0));
            } else {
                list.add(new MobEffectInstance(Init.CARROT_EFFECT, 1200, 0));
            }

        } else if (Init.GLISTERING_MELON_JUICE.get() == stack.getItem() || Init.GLISTERING_MELON_JUICE_BOOSTED.get() == stack.getItem()) {
            if (stack.getItem() == Init.GLISTERING_MELON_JUICE_BOOSTED.get()) {
                list.add(new MobEffectInstance(Init.GLISTERING_MELON_BOOSTED_EFFECT, 2400, 0));
            } else {
                list.add(new MobEffectInstance(Init.GLISTERING_MELON_EFFECT, 2400, 0));
            }

        } else if (Init.GOLDENAPPLE_JUICE.get() == stack.getItem() || Init.GOLDENAPPLE_JUICE_BOOSTED.get() == stack.getItem()) {
            if (stack.getItem() == Init.GOLDENAPPLE_JUICE_BOOSTED.get()) {
                list.add(new MobEffectInstance(Init.GOLDENAPPLE_BOOSTED_EFFECT, 3600, 0));
            } else {
                list.add(new MobEffectInstance(Init.GOLDENAPPLE_EFFECT, 3600, 0));
            }

        } else if (Init.GOLDENCARROT_JUICE.get() == stack.getItem() || Init.GOLDENCARROT_JUICE_BOOSTED.get() == stack.getItem()) {
            if (stack.getItem() == Init.GOLDENCARROT_JUICE_BOOSTED.get()) {
                list.add(new MobEffectInstance(Init.GOLDENCARROT_BOOSTED_EFFECT, 3600, 0));
            } else {
                list.add(new MobEffectInstance(Init.GOLDENCARROT_EFFECT, 3600, 0));
            }

        } else if (Init.CHORUS_JUICE.get() == stack.getItem() || Init.CHORUS_JUICE_BOOSTED.get() == stack.getItem()) {
            if (stack.getItem() == Init.CHORUS_JUICE_BOOSTED.get()) {
                list.add(new MobEffectInstance(Init.CHORUS_BOOSTED_EFFECT, 3600, 0));
            } else {
                list.add(new MobEffectInstance(Init.CHORUS_EFFECT, 1200, 0));
            }

        } else if (Init.GLOWBERRY_JUICE.get() == stack.getItem() || Init.GLOWBERRY_JUICE_BOOSTED.get() == stack.getItem()) {
            if (stack.getItem() == Init.GLOWBERRY_JUICE_BOOSTED.get()) {
                list.add(new MobEffectInstance(Init.GLOWBERRY_BOOSTED_EFFECT, 2400, 0));
            } else {
                list.add(new MobEffectInstance(Init.GLOWBERRY_EFFECT, 3600, 0));
            }

        } else if (Init.SPICY_JUICE.get() == stack.getItem() || Init.SPICY_JUICE_BOOSTED.get() == stack.getItem()) {
            if (stack.getItem() == Init.SPICY_JUICE_BOOSTED.get()) {
                list.add(new MobEffectInstance(Init.SPICY_BOOSTED_EFFECT, 2400, 0));
            } else {
                list.add(new MobEffectInstance(Init.SPICY_EFFECT, 1200, 0));
            }

        } else if (Init.GOLEM_JUICE.get() == stack.getItem() || Init.GOLEM_JUICE_BOOSTED.get() == stack.getItem()) {
            if (stack.getItem() == Init.GOLEM_JUICE_BOOSTED.get()) {
                list.add(new MobEffectInstance(Init.GOLEM_BOOSTED_EFFECT, 4800, 0));
            } else {
                list.add(new MobEffectInstance(Init.GOLEM_EFFECT, 3600, 0));
            }

        } else if (Init.SUNBERRY_JUICE.get() == stack.getItem() || Init.SUNBERRY_JUICE_BOOSTED.get() == stack.getItem()) {
            if (stack.getItem() == Init.SUNBERRY_JUICE_BOOSTED.get()) {
                list.add(new MobEffectInstance(Init.SUNBERRY_BOOSTED_EFFECT, 4800, 0));
            } else {
                list.add(new MobEffectInstance(Init.SUNBERRY_EFFECT, 2400, 0));
            }

        } else if (Init.BEETROOT_JUICE.get() == stack.getItem() || Init.BEETROOT_JUICE_BOOSTED.get() == stack.getItem()) {
            if (stack.getItem() == Init.BEETROOT_JUICE_BOOSTED.get()) {
                list.add(new MobEffectInstance(Init.BEETROOT_BOOSTED_EFFECT, 400, 0));
            } else {
                list.add(new MobEffectInstance(Init.BEETROOT_EFFECT, 200, 0));
            }

        } else if (Init.NETHERWART_JUICE.get() == stack.getItem() || Init.NETHERWART_JUICE_BOOSTED.get() == stack.getItem()) {
            if (stack.getItem() == Init.NETHERWART_JUICE_BOOSTED.get()) {
                list.add(new MobEffectInstance(Init.NETHERWART_BOOSTED_EFFECT, 3600, 0));
            } else {
                list.add(new MobEffectInstance(Init.NETHERWART_EFFECT, 1800, 0));
            }

        } else if (Init.COCOA_JUICE.get() == stack.getItem() || Init.COCOA_JUICE_BOOSTED.get() == stack.getItem()) {
            if (stack.getItem() == Init.COCOA_JUICE_BOOSTED.get()) {
                list.add(new MobEffectInstance(Init.COCOA_BOOSTED_EFFECT, 2400, 0));
            } else {
                list.add(new MobEffectInstance(Init.COCOA_EFFECT, 1200, 0));
            }

        }

        int fruitAmplifier = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath().endsWith("_boosted") ? 1 : 0;
        Holder<MobEffect> fruitPower = null;
        if (stack.is(Init.APPLE_JUICE.get()) || stack.is(Init.APPLE_JUICE_BOOSTED.get())) fruitPower = Init.ORCHARD_GUARD;
        else if (stack.is(Init.ICEBERRY_JUICE.get()) || stack.is(Init.ICEBERRY_JUICE_BOOSTED.get())) fruitPower = Init.FROSTBITE;
        else if (stack.is(Init.SUNBERRY_JUICE.get()) || stack.is(Init.SUNBERRY_JUICE_BOOSTED.get())) fruitPower = Init.SOLAR_CHARGE;
        else if (stack.is(Init.WILDBERRY_JUICE.get()) || stack.is(Init.WILDBERRY_JUICE_BOOSTED.get())) fruitPower = Init.FORAGERS_LUCK;
        if (fruitPower != null) list.add(new MobEffectInstance(fruitPower, fruitAmplifier == 0 ? 1200 : 2400, fruitAmplifier));
        double multiplier = Config.EFFECT_DURATION_MULTIPLIER.get();
        if (multiplier != 1.0D) {
            List<MobEffectInstance> scaled = Lists.newArrayList();
            for (MobEffectInstance instance : list) {
                int duration = (int) Math.max(1, Math.round(instance.getDuration() * multiplier));
                scaled.add(new MobEffectInstance(instance.getEffect(), duration, instance.getAmplifier(),
                        instance.isAmbient(), instance.isVisible(), instance.showIcon()));
            }
            list = scaled;
        }
        return list;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        Player player = livingEntity instanceof Player ? (Player)livingEntity : null;

        //Not Sure
        if (player instanceof ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)player, stack);
        }

        //Wanted
        if (!level.isClientSide()) {
            for(MobEffectInstance mobeffectinstance : getJuiceEffects(stack)) {
                if (mobeffectinstance.getEffect().value().isInstantenous()) {
                    //? if >=1.21.2 {
                    /*mobeffectinstance.getEffect().value().applyInstantenousEffect((net.minecraft.server.level.ServerLevel) livingEntity.level(), player, player, livingEntity, mobeffectinstance.getAmplifier(), 1.0D);
                    *///?} else {
                    mobeffectinstance.getEffect().value().applyInstantenousEffect(player, player, livingEntity, mobeffectinstance.getAmplifier(), 1.0D);
                    //?}
                } else {
                    livingEntity.addEffect(new MobEffectInstance(mobeffectinstance));
                }
            }

            //Overdrink debuff: too many juices in a short window causes nausea and hunger
            if (player != null && Config.OVERDRINK_ENABLED.get()) {
                int window = Config.OVERDRINK_WINDOW_TICKS.get();
                var data = player.getPersistentData();
                var bedrock = data.getCompound(player.getUUID().toString());
                long lastDrinkTime = bedrock.getLong("LastDrinkTime");
                int recentDrinks = bedrock.getInt("RecentDrinks");

                if (level.getGameTime() - lastDrinkTime < window) {
                    recentDrinks++;
                } else {
                    recentDrinks = 1;
                }

                bedrock.putLong("LastDrinkTime", level.getGameTime());
                bedrock.putInt("RecentDrinks", recentDrinks);
                data.put(player.getUUID().toString(), bedrock);

                if (recentDrinks >= Config.OVERDRINK_MAX_DRINKS.get()) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, Config.OVERDRINK_NAUSEA_TICKS.get(), 1));
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.HUNGER, Config.OVERDRINK_HUNGER_TICKS.get(), 0));
                    bedrock.putInt("RecentDrinks", 0);
                    data.put(player.getUUID().toString(), bedrock);
                }
            }
        }
        //Wanted
        if (player != null) {
            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }

        //Wanted
        if (Config.RETURN_GLASS_BOTTLE.get() && (player == null || !player.getAbilities().instabuild)) {
            if (stack.isEmpty()) {
                return new ItemStack(Init.GLASS_BOTTLE.get());
            }
            if (player != null) {
                player.getInventory().add(new ItemStack(Init.GLASS_BOTTLE.get()));
            }
        }

        level.gameEvent(livingEntity, GameEvent.DRINK, livingEntity.getEyePosition());
        return stack;
    }

    public @NotNull String getDescriptionId(ItemStack stack) {
        String registry_name = Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(stack.getItem())).toString().replace(":", ".");
        return "item." + registry_name;
    }

    @Override
    public boolean isFoil(ItemStack itemStack) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
        List<MobEffectInstance> list = getJuiceEffects(stack);
        if (list.isEmpty()) {
            components.add(NO_EFFECT);
        } else {
            for(MobEffectInstance mobeffectinstance : list) {
                MutableComponent mutablecomponent = Component.translatable(mobeffectinstance.getDescriptionId()).withStyle(ChatFormatting.GOLD);
                MobEffect mobeffect = mobeffectinstance.getEffect().value();

                if (mobeffectinstance.getAmplifier() > 0) {
                    mutablecomponent = Component.translatable("potion.withAmplifier", mutablecomponent, Component.translatable("potion.potency." + mobeffectinstance.getAmplifier())).withStyle(ChatFormatting.RED);
                }

                if (mobeffectinstance.getDuration() > 20) {
                    mutablecomponent = Component.translatable("potion.withDuration", mutablecomponent, MobEffectUtil.formatDuration(mobeffectinstance, 1.0F, 1.0F));
                }

                components.add(mutablecomponent.withStyle(mobeffect.getCategory().getTooltipFormatting()));
                if (mobeffect instanceof me.ez.jej.common.Effects.JuiceEffect juice) {
                    juice.appendTooltip(components);
                } else {
                    components.add(Component.translatable(mobeffectinstance.getDescriptionId() + ".desc").withStyle(ChatFormatting.GRAY));
                }
            }
        }
    }
}
