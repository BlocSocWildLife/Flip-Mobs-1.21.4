package net.blocsoc.flipmobs.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RabbitEntity.class)
public abstract class RabbitEntityMixin extends AnimalEntity {


    protected RabbitEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initGoals", at = @At("HEAD"), cancellable = true)
    protected void initGoals(CallbackInfo ci) {






        this.goalSelector.add(0, new SwimGoal(((RabbitEntity) (Object) this)));
        this.goalSelector.add(1, new PowderSnowJumpGoal(this, this.getWorld()));
        this.goalSelector.add(8, new LookAtEntityGoal(((RabbitEntity) (Object) this), PlayerEntity.class, 8.0F));
        this.goalSelector.add(8, new LookAroundGoal(((RabbitEntity) (Object) this)));
        this.goalSelector.add(5, new MeleeAttackGoal(((RabbitEntity) (Object) this), 1.0, true));
        this.goalSelector.add(7, new WanderAroundFarGoal(((RabbitEntity) (Object) this), 1.0));
        this.targetSelector.add(2, new ActiveTargetGoal(((RabbitEntity) (Object) this), PlayerEntity.class, true));

        ci.cancel();
    }

    @Inject(method = "createRabbitAttributes", at = @At("HEAD"), cancellable = true)
    private static DefaultAttributeContainer.Builder createRabbitAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> ci) {
        ci.cancel();

        ci.setReturnValue(AnimalEntity.createAnimalAttributes()
                .add(EntityAttributes.FOLLOW_RANGE, 35.0)
                .add(EntityAttributes.MAX_HEALTH, 3.0)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.3F)
                .add(EntityAttributes.ATTACK_DAMAGE, 3.0));
        return null;
    }
}