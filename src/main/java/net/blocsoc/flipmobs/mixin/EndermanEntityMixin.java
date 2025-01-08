package net.blocsoc.flipmobs.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;
import java.util.UUID;

@Mixin(EndermanEntity.class)
public abstract class EndermanEntityMixin extends HostileEntity {


    protected EndermanEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initGoals", at = @At("HEAD"), cancellable = true)
    protected void initGoals(CallbackInfo ci) {






        this.goalSelector.add(0, new SwimGoal(((EndermanEntity) (Object) this)));
        this.goalSelector.add(1, new EscapeDangerGoal(((EndermanEntity) (Object) this), 1.0));
        this.goalSelector.add(5, new WanderAroundFarGoal(((EndermanEntity) (Object) this), 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(((EndermanEntity) (Object) this), PlayerEntity.class, 6.0F));
        this.goalSelector.add(7, new LookAroundGoal(((EndermanEntity) (Object) this)));

        // Cancel the original method to prevent default goals from being added
        ci.cancel();
    }


}