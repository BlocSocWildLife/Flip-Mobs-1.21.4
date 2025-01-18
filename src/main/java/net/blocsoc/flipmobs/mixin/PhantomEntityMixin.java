package net.blocsoc.flipmobs.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.EnumSet;

@Mixin(PhantomEntity.class)
public abstract class PhantomEntityMixin extends FlyingEntity {




    protected PhantomEntityMixin(EntityType<? extends FlyingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "canTarget", at = @At("HEAD"), cancellable = true)
    public boolean canTarget(EntityType<?> type, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
        cir.cancel();
        return cir.getReturnValue();
    }


    @Inject(method = "isDisallowedInPeaceful", at = @At("HEAD"), cancellable = true)
    protected boolean isDisallowedInPeaceful(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
        cir.cancel();
        return cir.getReturnValue();
    }



}