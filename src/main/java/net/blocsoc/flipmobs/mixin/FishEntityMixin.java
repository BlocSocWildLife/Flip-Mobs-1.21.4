package net.blocsoc.flipmobs.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishEntity.class)
public abstract class FishEntityMixin extends WaterCreatureEntity {


    protected FishEntityMixin(EntityType<? extends WaterCreatureEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initGoals", at = @At("HEAD"), cancellable = true)
    protected void initGoals(CallbackInfo ci) {
        super.initGoals();



        this.goalSelector.add(4, new FishEntityMixin.SwimToRandomPlaceGoal(this));



        this.goalSelector.add(3, new MeleeAttackGoal(((FishEntity) (Object) this), 1.0, true));
        this.targetSelector.add(2, new ActiveTargetGoal(((FishEntity) (Object) this), PlayerEntity.class, true));

        ci.cancel();
    }

    @Inject(method = "createFishAttributes", at = @At("HEAD"), cancellable = true)
    private static DefaultAttributeContainer.Builder createFishAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> ci) {
        ci.cancel();

        ci.setReturnValue(AnimalEntity.createAnimalAttributes()
                .add(EntityAttributes.FOLLOW_RANGE, 35.0)
                .add(EntityAttributes.MAX_HEALTH, 3.0)
                .add(EntityAttributes.MOVEMENT_SPEED, 1.2)
                .add(EntityAttributes.ATTACK_DAMAGE, 3.0));
        return null;
    }

    public boolean hasSelfControl() {
        return true;
    }

    static class SwimToRandomPlaceGoal extends SwimAroundGoal {
        private final FishEntityMixin fish;

        public SwimToRandomPlaceGoal(FishEntityMixin fish) {
            super(fish, 1.0, 40);
            this.fish = fish;
        }

        @Override
        public boolean canStart() {
            return true;
        }
    }
}