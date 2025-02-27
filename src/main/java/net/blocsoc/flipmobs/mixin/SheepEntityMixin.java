package net.blocsoc.flipmobs.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SheepEntity.class)
public abstract class SheepEntityMixin extends AnimalEntity {


    protected SheepEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
    private int eatGrassTimer;
    private EatGrassGoal eatGrassGoal;

    @Inject(method = "initGoals", at = @At("HEAD"), cancellable = true)
    protected void initGoals(CallbackInfo ci) {
        this.eatGrassGoal = new EatGrassGoal(this);






        this.goalSelector.add(0, new SwimGoal(((SheepEntity) (Object) this)));
        this.goalSelector.add(8, new LookAtEntityGoal(((SheepEntity) (Object) this), PlayerEntity.class, 8.0F));
        this.goalSelector.add(8, new LookAroundGoal(((SheepEntity) (Object) this)));
        this.goalSelector.add(5, new MeleeAttackGoal(((SheepEntity) (Object) this), 1.0, true));
        this.goalSelector.add(7, new WanderAroundFarGoal(((SheepEntity) (Object) this), 1.0));
        this.targetSelector.add(2, new ActiveTargetGoal(((SheepEntity) (Object) this), PlayerEntity.class, true));
        this.goalSelector.add(5, this.eatGrassGoal);

        ci.cancel();
    }

    @Inject(method = "createSheepAttributes", at = @At("HEAD"), cancellable = true)
    private static DefaultAttributeContainer.Builder createSheepAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> ci) {
        ci.cancel();

        ci.setReturnValue(AnimalEntity.createAnimalAttributes()
                .add(EntityAttributes.FOLLOW_RANGE, 35.0)
                .add(EntityAttributes.MAX_HEALTH, 8.0)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.23)
                .add(EntityAttributes.ATTACK_DAMAGE, 3.0));
        return null;
    }
}