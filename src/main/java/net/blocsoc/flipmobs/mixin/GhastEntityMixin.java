package net.blocsoc.flipmobs.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.EnumSet;

@Mixin(GhastEntity.class)
public abstract class GhastEntityMixin extends FlyingEntity {


    public GhastEntityMixin(EntityType<? extends FlyingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initGoals", at = @At("HEAD"), cancellable = true)
    protected void initGoals(CallbackInfo ci) {

        this.goalSelector.add(5, new GhastEntityMixin.FlyRandomlyGoal((FlyingEntity) this));
        this.goalSelector.add(7, new GhastEntityMixin.LookAtTargetGoal((FlyingEntity) this));
        ci.cancel();

    }


    @Inject(method = "isDisallowedInPeaceful", at = @At("HEAD"), cancellable = true)
    protected boolean isDisallowedInPeaceful(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
        cir.cancel();
        return cir.getReturnValue();
    }

    static class FlyRandomlyGoal extends Goal {
        private final FlyingEntity ghast;

        public FlyRandomlyGoal(FlyingEntity ghast) {
            this.ghast = ghast;
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        @Override
        public boolean canStart() {
            MoveControl moveControl = this.ghast.getMoveControl();
            if (!moveControl.isMoving()) {
                return true;
            } else {
                double d = moveControl.getTargetX() - this.ghast.getX();
                double e = moveControl.getTargetY() - this.ghast.getY();
                double f = moveControl.getTargetZ() - this.ghast.getZ();
                double g = d * d + e * e + f * f;
                return g < 1.0 || g > 3600.0;
            }
        }

        @Override
        public boolean shouldContinue() {
            return false;
        }

        @Override
        public void start() {
            Random random = this.ghast.getRandom();
            double d = this.ghast.getX() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double e = this.ghast.getY() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double f = this.ghast.getZ() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.ghast.getMoveControl().moveTo(d, e, f, 1.0);
        }
    }

    static class LookAtTargetGoal extends Goal {
        private final FlyingEntity ghast;

        public LookAtTargetGoal(FlyingEntity ghast) {
            this.ghast = ghast;
            this.setControls(EnumSet.of(Goal.Control.LOOK));
        }

        @Override
        public boolean canStart() {
            return true;
        }

        @Override
        public boolean shouldRunEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            if (this.ghast.getTarget() == null) {
                Vec3d vec3d = this.ghast.getVelocity();
                this.ghast.setYaw(-((float) MathHelper.atan2(vec3d.x, vec3d.z)) * (180.0F / (float)Math.PI));
                this.ghast.bodyYaw = this.ghast.getYaw();
            } else {
                LivingEntity livingEntity = this.ghast.getTarget();
                double d = 64.0;
                if (livingEntity.squaredDistanceTo(this.ghast) < 4096.0) {
                    double e = livingEntity.getX() - this.ghast.getX();
                    double f = livingEntity.getZ() - this.ghast.getZ();
                    this.ghast.setYaw(-((float)MathHelper.atan2(e, f)) * (180.0F / (float)Math.PI));
                    this.ghast.bodyYaw = this.ghast.getYaw();
                }
            }
        }
    }


}