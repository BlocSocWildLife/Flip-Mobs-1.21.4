package net.blocsoc.flipmobs.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MagmaCubeEntity.class)
public abstract class MagmaCubeEntityMixin extends SlimeEntity {
    public MagmaCubeEntityMixin(EntityType<? extends SlimeEntity> entityType, World world) {
        super(entityType, world);
    }


//    @Inject(method = "initGoals", at = @At("HEAD"), cancellable = true)
//    protected void initGoals(CallbackInfo ci) {
//
//
//
//
//
//
//        this.goalSelector.add(1, new SlimeEntity.SwimmingGoal(this));
//        this.goalSelector.add(2, new SlimeEntity.FaceTowardTargetGoal(this));
//        this.goalSelector.add(3, new SlimeEntity.RandomLookGoal(this));
//        this.goalSelector.add(5, new SlimeEntity.MoveGoal(this));
//
//        // Cancel the original method to prevent default goals from being added
//        ci.cancel();
//    }

    @Inject(method = "canAttack", at = @At("HEAD"), cancellable = true)
    protected boolean canAttack(CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue(false);
        cir.cancel();
        return cir.getReturnValue();
    }


}