package net.blocsoc.flipmobs.mixin;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinEntity.class)
public abstract class PiglinEntityMixin extends AbstractPiglinEntity {


    public PiglinEntityMixin(EntityType<? extends AbstractPiglinEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "isDisallowedInPeaceful", at = @At("HEAD"), cancellable = true)
    protected boolean isDisallowedInPeaceful(CallbackInfoReturnable<Boolean> ci) {
        ci.cancel();
        return false;
    }

    @Inject(method = "makeInitialWeapon", at = @At("HEAD"), cancellable = true)
    private ItemStack makeInitialWeapon(CallbackInfoReturnable<ItemStack> ci) {
        return new ItemStack(Items.CROSSBOW);
    }

    @Inject(method = "shootAt", at = @At("HEAD"), cancellable = true)
    public void shootAt(LivingEntity target, float pullProgress, CallbackInfo ci) {
        ci.cancel();
    }



}