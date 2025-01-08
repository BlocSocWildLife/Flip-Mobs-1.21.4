package net.blocsoc.flipmobs.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.BreezeEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BreezeEntity.class)
public abstract class BreezeEntityMixin extends HostileEntity {


    protected BreezeEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "canTarget", at = @At("HEAD"), cancellable = true)
    public boolean canTarget(EntityType<?> type, CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue(false);
        cir.cancel();
        return cir.getReturnValue();
    }


}