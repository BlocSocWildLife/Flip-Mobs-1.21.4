package net.blocsoc.flipmobs.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.StriderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StriderEntity.class)
public abstract class StriderEntityMixin extends AnimalEntity {


    protected StriderEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initGoals", at = @At("HEAD"), cancellable = true)
    protected void initGoals(CallbackInfo ci) {





        this.goalSelector.add(8, new LookAtEntityGoal(((StriderEntity) (Object) this), PlayerEntity.class, 8.0F));
        this.goalSelector.add(8, new LookAroundGoal(((StriderEntity) (Object) this)));
        this.goalSelector.add(5, new MeleeAttackGoal(((StriderEntity) (Object) this), 1.0, true));
        this.goalSelector.add(7, new WanderAroundFarGoal(((StriderEntity) (Object) this), 1.0));
        this.targetSelector.add(2, new ActiveTargetGoal(((StriderEntity) (Object) this), PlayerEntity.class, true));

        ci.cancel();
    }

    @Inject(method = "createStriderAttributes", at = @At("HEAD"), cancellable = true)
    private static DefaultAttributeContainer.Builder createStriderAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> ci) {
        ci.cancel();

        ci.setReturnValue(AnimalEntity.createAnimalAttributes()
                .add(EntityAttributes.FOLLOW_RANGE, 35.0)
                .add(EntityAttributes.MAX_HEALTH, 10.0)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.175F)
                .add(EntityAttributes.ATTACK_DAMAGE, 3.0));
        return null;
    }
}