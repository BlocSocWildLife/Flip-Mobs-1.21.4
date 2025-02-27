package net.blocsoc.flipmobs.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(BeeEntity.class)
public abstract class BeeEntityMixin extends AnimalEntity {




    protected BeeEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "getAngryAt", at = @At("HEAD"), cancellable = true)
    public UUID getAngryAt(CallbackInfoReturnable<UUID> cir) {

        World world = this.getWorld();

        PlayerEntity nearestPlayer = world.getClosestPlayer(this, 32);

        if(nearestPlayer != null){
            cir.setReturnValue(nearestPlayer.getUuid());
            return nearestPlayer.getUuid();
        }

        cir.setReturnValue(null);

        cir.cancel();
        return null;
    }

//    @Inject(method = "mobTick", at = @At("HEAD"))
//    private void mobTick(CallbackInfo info) {
//        if (!this.getWorld().isClient) {
//            PlayerEntity nearestPlayer = this.getWorld().getClosestPlayer(this, 10);
//            if (nearestPlayer != null && !nearestPlayer.isCreative() && !nearestPlayer.isSpectator()) {
//                BeeEntityAccessor accessor = (BeeEntityAccessor) this;
//                accessor.setAngryAt(nearestPlayer.getUuid());
//                accessor.setAngerTime(400);
//                this.setTarget(nearestPlayer);
//            }
//        }
//    }




}