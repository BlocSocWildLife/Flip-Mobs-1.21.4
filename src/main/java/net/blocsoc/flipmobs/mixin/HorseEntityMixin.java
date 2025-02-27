package net.blocsoc.flipmobs.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractHorseEntity.class)
public abstract class HorseEntityMixin extends AnimalEntity {


    protected HorseEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initGoals", at = @At("HEAD"), cancellable = true)
    protected void initGoals(CallbackInfo ci) {






        this.goalSelector.add(0, new SwimGoal(((AbstractHorseEntity) (Object) this)));
        this.goalSelector.add(8, new LookAtEntityGoal(((AbstractHorseEntity) (Object) this), PlayerEntity.class, 8.0F));
        this.goalSelector.add(8, new LookAroundGoal(((AbstractHorseEntity) (Object) this)));
        this.goalSelector.add(5, new MeleeAttackGoal(((AbstractHorseEntity) (Object) this), 1.0, true));
        this.goalSelector.add(7, new WanderAroundFarGoal(((AbstractHorseEntity) (Object) this), 1.0));
        this.targetSelector.add(2, new ActiveTargetGoal(((AbstractHorseEntity) (Object) this), PlayerEntity.class, true));

        ci.cancel();
    }

    @Inject(method = "createBaseHorseAttributes", at = @At("HEAD"), cancellable = true)
    private static DefaultAttributeContainer.Builder createBaseHorseAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> ci) {
        ci.cancel();

        ci.setReturnValue(AnimalEntity.createAnimalAttributes()
                .add(EntityAttributes.JUMP_STRENGTH, 0.7)
                .add(EntityAttributes.MAX_HEALTH, 53.0)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.225F)
                .add(EntityAttributes.STEP_HEIGHT, 1.0)
                .add(EntityAttributes.SAFE_FALL_DISTANCE, 6.0)
                .add(EntityAttributes.FALL_DAMAGE_MULTIPLIER, 0.5)
                .add(EntityAttributes.ATTACK_DAMAGE, 3.0));
        return null;
    }
}