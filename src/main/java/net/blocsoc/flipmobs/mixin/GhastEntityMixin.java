package net.blocsoc.flipmobs.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GhastEntity.class)
public abstract class GhastEntityMixin extends FlyingEntity {


    protected GhastEntityMixin(EntityType<? extends FlyingEntity> entityType, World world) {
        super(entityType, world);
    }



    @Inject(method = "initGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/monster/GhastEntity$FlyRandomlyGoal;<init>(Lnet/minecraft/entity/monster/GhastEntity;)V", shift = At.Shift.AFTER), cancellable = true)
    protected void initGoals(CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "isDisallowedInPeaceful", at = @At("HEAD"), cancellable = true)
    protected boolean isDisallowedInPeaceful(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
        cir.cancel();
        return cir.getReturnValue();
    }


}