package net.blocsoc.flipmobs.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.EnumSet;

//try and replace with drowned?

@Mixin(SquidEntity.class)
public abstract class SquidEntityMixin extends WaterAnimalEntity {
    Vec3d swimVec = Vec3d.ZERO;


    protected SquidEntityMixin(EntityType<? extends WaterAnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initGoals", at = @At("HEAD"), cancellable = true)
    protected void initGoals(CallbackInfo ci) {

//

        this.goalSelector.add(2, new SquidEntityMixin.DrownedAttackGoal(this, 1.0, false));
//        this.goalSelector.add(6, new SquidEntityMixin.TargetAboveWaterGoal(this, 1.0, this.getWorld().getSeaLevel()));
        this.targetSelector.add(1, new ActiveTargetGoal(((SquidEntity) (Object) this), PlayerEntity.class, true));

        this.goalSelector.add(7, new SquidEntityMixin.SwimGoal(this));

        ci.cancel();
    }

    @Inject(method = "createSquidAttributes", at = @At("HEAD"), cancellable = true)
    private static DefaultAttributeContainer.Builder createSquidAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> ci) {
        ci.cancel();

        ci.setReturnValue(MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, 10.0)
                .add(EntityAttributes.MOVEMENT_SPEED, 1.2F)
                .add(EntityAttributes.ATTACK_DAMAGE, 3.0));
        return null;
    }

    public boolean hasSwimmingVector() {
        return this.swimVec.lengthSquared() > 1.0E-5F;
    }

    static class SwimGoal extends Goal {
        private final SquidEntityMixin squid;

        public SwimGoal(SquidEntityMixin squid) {
            this.squid = squid;
        }

        @Override
        public boolean canStart() {
            return true;
        }

        @Override
        public void tick() {
            int i = this.squid.getDespawnCounter();
            if (i > 100) {
                this.squid.swimVec = Vec3d.ZERO;
            } else if (this.squid.getRandom().nextInt(toGoalTicks(50)) == 0 || !this.squid.touchingWater || !this.squid.hasSwimmingVector()) {
                float f = this.squid.getRandom().nextFloat() * (float) (Math.PI * 2);
                this.squid.swimVec = new Vec3d(
                        (double)(MathHelper.cos(f) * 0.2F), (double)(-0.1F + this.squid.getRandom().nextFloat() * 0.2F), (double)(MathHelper.sin(f) * 0.2F)
                );
            }
        }
    }

    static class DrownedAttackGoal extends MeleeAttackGoal {
        private final SquidEntityMixin drowned;

        public DrownedAttackGoal(SquidEntityMixin drowned, double speed, boolean pauseWhenMobIdle) {
            super(drowned, speed, pauseWhenMobIdle);
            this.drowned = drowned;
        }

        @Override
        public boolean canStart() {
            return super.canStart() && this.drowned.canDrownedAttackTarget(this.drowned.getTarget());
        }

        @Override
        public boolean shouldContinue() {
            return super.shouldContinue() && this.drowned.canDrownedAttackTarget(this.drowned.getTarget());
        }
    }

//    static class TargetAboveWaterGoal extends Goal {
//        private final SquidEntityMixin drowned;
//        private final double speed;
//        private final int minY;
//        private boolean foundTarget;
//
//        public TargetAboveWaterGoal(SquidEntityMixin drowned, double speed, int minY) {
//            this.drowned = drowned;
//            this.speed = speed;
//            this.minY = minY;
//        }
//
//        @Override
//        public boolean canStart() {
//            return !this.drowned.getWorld().isDay() && this.drowned.isTouchingWater() && this.drowned.getY() < (double)(this.minY - 2);
//        }
//
//        @Override
//        public boolean shouldContinue() {
//            return this.canStart() && !this.foundTarget;
//        }
//
//        @Override
//        public void tick() {
//            if (this.drowned.getY() < (double)(this.minY - 1) && (this.drowned.getNavigation().isIdle() || this.drowned.hasFinishedCurrentPath())) {
//                Vec3d vec3d = NoPenaltyTargeting.findTo(
//                        this.drowned, 4, 8, new Vec3d(this.drowned.getX(), (double)(this.minY - 1), this.drowned.getZ()), (float) (Math.PI / 2)
//                );
//                if (vec3d == null) {
//                    this.foundTarget = true;
//                    return;
//                }
//
//                this.drowned.getNavigation().startMovingTo(vec3d.x, vec3d.y, vec3d.z, this.speed);
//            }
//        }
//
//        @Override
//        public void start() {
//            this.drowned.setTargetingUnderwater(true);
//            this.foundTarget = false;
//        }
//
//        @Override
//        public void stop() {
//            this.drowned.setTargetingUnderwater(false);
//        }
//    }

    protected boolean hasFinishedCurrentPath() {
        Path path = this.getNavigation().getCurrentPath();
        if (path != null) {
            BlockPos blockPos = path.getTarget();
            if (blockPos != null) {
                double d = this.squaredDistanceTo((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ());
                if (d < 4.0) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean canDrownedAttackTarget(@Nullable LivingEntity target) {
        return target != null ? !this.getWorld().isDay() || target.isTouchingWater() : false;
    }


}