package net.blocsoc.flipmobs.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombieEntity.class)
public abstract class ZombieEntityMixin extends HostileEntity {


    public ZombieEntityMixin(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);
    }


    @Inject(method = "initGoals", at = @At("HEAD"), cancellable = true)
    protected void initGoals(CallbackInfo ci) {






        this.goalSelector.add(1, new EscapeDangerGoal(((ZombieEntity) (Object) this), 1.0));
        this.goalSelector.add(5, new WanderAroundFarGoal(((ZombieEntity) (Object) this), 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(((ZombieEntity) (Object) this), PlayerEntity.class, 6.0F));
        this.goalSelector.add(7, new LookAroundGoal(((ZombieEntity) (Object) this)));

        // Cancel the original method to prevent default goals from being added
        ci.cancel();
    }
}