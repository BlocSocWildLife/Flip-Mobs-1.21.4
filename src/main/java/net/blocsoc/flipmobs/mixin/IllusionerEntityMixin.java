package net.blocsoc.flipmobs.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.IllusionerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IllusionerEntity.class)
public abstract class IllusionerEntityMixin extends HostileEntity {


    protected IllusionerEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initGoals", at = @At("HEAD"), cancellable = true)
    protected void initGoals(CallbackInfo ci) {






        this.goalSelector.add(0, new SwimGoal(((IllusionerEntity) (Object) this)));
        this.goalSelector.add(1, new EscapeDangerGoal(((IllusionerEntity) (Object) this), 1.0));
        this.goalSelector.add(5, new WanderAroundFarGoal(((IllusionerEntity) (Object) this), 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(((IllusionerEntity) (Object) this), PlayerEntity.class, 6.0F));
        this.goalSelector.add(7, new LookAroundGoal(((IllusionerEntity) (Object) this)));

        // Cancel the original method to prevent default goals from being added
        ci.cancel();
    }


}