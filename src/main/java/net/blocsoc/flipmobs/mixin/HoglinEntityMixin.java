package net.blocsoc.flipmobs.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HoglinEntity.class)
public abstract class HoglinEntityMixin extends AnimalEntity implements Monster, Hoglin {


    protected HoglinEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "getTarget", at = @At("HEAD"), cancellable = true)
    public LivingEntity getTarget(CallbackInfoReturnable<LivingEntity> info) {
        return null;
    }

    @Inject(method = "tryAttack", at = @At("HEAD"), cancellable = true)
    public boolean tryAttack(ServerWorld world, Entity target, CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue(false);
        cir.cancel();
        return cir.getReturnValue();
    }
}