package net.blocsoc.flipmobs.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlazeEntity.class)
public abstract class BlazeEntityMixin extends HostileEntity {


    protected BlazeEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initGoals", at = @At("HEAD"), cancellable = true)
    protected void initGoals(CallbackInfo ci) {






        this.goalSelector.add(0, new SwimGoal(((BlazeEntity) (Object) this)));
        this.goalSelector.add(1, new EscapeDangerGoal(((BlazeEntity) (Object) this), 1.0));
        this.goalSelector.add(5, new WanderAroundFarGoal(((BlazeEntity) (Object) this), 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(((BlazeEntity) (Object) this), PlayerEntity.class, 6.0F));
        this.goalSelector.add(7, new LookAroundGoal(((BlazeEntity) (Object) this)));

        // Cancel the original method to prevent default goals from being added
        ci.cancel();
    }
}