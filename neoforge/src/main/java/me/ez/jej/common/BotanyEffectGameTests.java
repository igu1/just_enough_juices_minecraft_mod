package me.ez.jej.common;

import me.ez.jej.Init;
import me.ez.jej.Main;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(Main.MOD_ID)
@PrefixGameTestTemplate(false)
public class BotanyEffectGameTests {
    private static void check(GameTestHelper helper, boolean condition, String message) { if (!condition) helper.fail(message); }

    @GameTest(template = "empty", timeoutTicks = 20)
    public static void bushesGrowHarvestAndRegrow(GameTestHelper helper) {
        var level = helper.getLevel();
        var player = FakePlayerFactory.getMinecraft(level);
        BlockPos pos = helper.absolutePos(new BlockPos(1, 1, 1));
        level.setBlock(pos.below(), Blocks.DIRT.defaultBlockState(), 3);
        ModBushBlock[] bushes = {Init.WILD_BERRY_BUSH.get(), Init.ICE_BERRY_BUSH.get(), Init.SUN_BERRY_BUSH.get()};
        for (ModBushBlock bush : bushes) {
            var state = bush.defaultBlockState();
            level.setBlock(pos, state, 3);
            check(helper, state.canSurvive(level, pos), "Bush cannot be planted on dirt");
            for (int i = 1; i <= 3; i++) {
                bush.performBonemeal(level, level.random, pos, level.getBlockState(pos));
                check(helper, level.getBlockState(pos).getValue(ModBushBlock.AGE) == i, "Incorrect growth stage");
                check(helper, !bush.getShape(level.getBlockState(pos), level, pos, net.minecraft.world.phys.shapes.CollisionContext.empty()).isEmpty(), "Missing growth collision shape");
            }
            var hit = new BlockHitResult(Vec3.atCenterOf(pos), Direction.UP, pos, false);
            player.removeEffect(Init.FORAGERS_LUCK);
            level.random.setSeed(8723);
            bush.useWithoutItem(level.getBlockState(pos), level, pos, player, hit);
            var area = new AABB(pos).inflate(1);
            var drops = level.getEntitiesOfClass(ItemEntity.class, area);
            int normal = drops.stream().filter(e -> e.getItem().is(bush.DropItem(state).getItem())).mapToInt(e -> e.getItem().getCount()).sum();
            check(helper, normal >= 2 && normal <= 3, "Wrong harvest item/count");
            check(helper, level.getBlockState(pos).getValue(ModBushBlock.AGE) == 2, "Harvest did not select imported harvested stage");
            drops.forEach(net.minecraft.world.entity.Entity::discard);
            bush.performBonemeal(level, level.random, pos, level.getBlockState(pos));
            player.addEffect(new MobEffectInstance(Init.FORAGERS_LUCK, 100, 1));
            level.random.setSeed(8723);
            bush.useWithoutItem(level.getBlockState(pos), level, pos, player, hit);
            drops = level.getEntitiesOfClass(ItemEntity.class, area);
            int boosted = drops.stream().filter(e -> e.getItem().is(bush.DropItem(state).getItem())).mapToInt(e -> e.getItem().getCount()).sum();
            check(helper, boosted == normal + 2, "Forager's Luck did not add the expected berries");
            drops.forEach(net.minecraft.world.entity.Entity::discard);
            player.removeEffect(Init.FORAGERS_LUCK);
        }
        helper.succeed();
    }

    @GameTest(template = "empty", timeoutTicks = 20)
    public static void juiceGrantsNewPowers(GameTestHelper helper) {
        var target = EntityType.COW.create(helper.getLevel());
        var juices = new net.minecraft.world.item.Item[]{Init.APPLE_JUICE.get(), Init.ICEBERRY_JUICE.get(), Init.SUNBERRY_JUICE.get(), Init.WILDBERRY_JUICE.get(),
                Init.APPLE_JUICE_BOOSTED.get(), Init.ICEBERRY_JUICE_BOOSTED.get(), Init.SUNBERRY_JUICE_BOOSTED.get(), Init.WILDBERRY_JUICE_BOOSTED.get()};
        Holder<MobEffect>[] powers = new Holder[]{Init.ORCHARD_GUARD, Init.FROSTBITE, Init.SOLAR_CHARGE, Init.FORAGERS_LUCK};
        for (int i = 0; i < juices.length; i++) {
            target.removeAllEffects();
            juices[i].finishUsingItem(new ItemStack(juices[i]), helper.getLevel(), target);
            var power = target.getEffect(powers[i % 4]);
            check(helper, power != null && power.getAmplifier() == (i < 4 ? 0 : 1), "Juice did not grant the correct fruit power");
        }
        helper.succeed();
    }

    @GameTest(template = "empty", timeoutTicks = 20)
    public static void protectionChillingAndSolarHealing(GameTestHelper helper) {
        var level = helper.getLevel();
        var target = EntityType.COW.create(level);
        var attacker = EntityType.ZOMBIE.create(level);
        target.addEffect(new MobEffectInstance(Init.ORCHARD_GUARD, 100));
        target.addEffect(new MobEffectInstance(Init.FROSTBITE, 100));
        target.setHealth(20F);
        target.hurt(level.damageSources().mobAttack(attacker), 10F);
        check(helper, Math.abs(target.getHealth() - 11.5F) < .001, "Orchard Guard did not reduce damage");
        check(helper, attacker.hasEffect(Init.CHILLED) && attacker.getTicksFrozen() >= 100, "Frostbite did not chill attacker");
        target.invulnerableTime = 0;
        target.setHealth(20F);
        target.hurt(level.damageSources().fellOutOfWorld(), 10F);
        check(helper, target.getHealth() <= 10.001F, "Guard incorrectly protects against void damage");
        BlockPos column = helper.absolutePos(new BlockPos(1, 2, 1));
        BlockPos pos = new BlockPos(column.getX(), level.getHeight(net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING,
                column.getX(), column.getZ()) + 1, column.getZ());
        target.setPos(pos.getX() + .5, pos.getY(), pos.getZ() + .5);
        level.setDayTime(1000);
        level.setWeatherParameters(1000, 0, false, false);
        target.setHealth(4);
        Init.SOLAR_CHARGE.get().applyEffectTick(target, 1);
        check(helper, target.getHealth() == 5, "Solar Charge daylight fixture: health=" + target.getHealth()
                + ", max=" + target.getMaxHealth() + ", day=" + level.isDay() + ", sky=" + level.canSeeSky(target.blockPosition())
                + ", rain=" + level.isRainingAt(target.blockPosition()) + ", pos=" + target.blockPosition());
        for (int x = -1; x <= 1; x++) for (int z = -1; z <= 1; z++) level.setBlock(pos.offset(x, 1, z), Blocks.STONE.defaultBlockState(), 3);
        helper.runAfterDelay(5, () -> {
            check(helper, !level.canSeeSky(pos), "Shade fixture still has direct sky light");
            Init.SOLAR_CHARGE.get().applyEffectTick(target, 1);
            check(helper, target.getHealth() == 5, "Solar Charge healed without sky access");
            for (int x = -1; x <= 1; x++) for (int z = -1; z <= 1; z++) level.setBlock(pos.offset(x, 1, z), Blocks.AIR.defaultBlockState(), 3);
            helper.succeed();
        });
    }

    @GameTest(template = "empty", timeoutTicks = 20)
    public static void animationPacketsKeepInventory(GameTestHelper helper) {
        BlockPos pos = helper.absolutePos(new BlockPos(1, 1, 1));
        var state = Init.JUICE_TABLE.get().defaultBlockState();
        helper.getLevel().setBlock(pos, state, 3);
        var table = (JuiceTableBlockEntity) helper.getLevel().getBlockEntity(pos);
        table.setItem(0, new ItemStack(Items.APPLE, 3));
        table.data.set(0, 35);
        var tag = table.getUpdateTag(helper.getLevel().registryAccess());
        check(helper, tag.getInt("Progress") == 35 && !tag.contains("Items"), "Animation packet has wrong payload");
        table.handleUpdateTag(tag, helper.getLevel().registryAccess());
        check(helper, table.getItem(0).getCount() == 3, "Animation update erased inventory");
        check(helper, table.getAnimationTime(0) == 1.75F, "Wrong synchronized animation time");
        helper.succeed();
    }
}
