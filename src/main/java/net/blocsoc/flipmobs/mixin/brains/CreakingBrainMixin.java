package net.blocsoc.flipmobs.mixin.brains;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.ForgetAttackTargetTask;
import net.minecraft.entity.ai.brain.task.MeleeAttackTask;
import net.minecraft.entity.ai.brain.task.RangedApproachTask;
import net.minecraft.entity.mob.CreakingBrain;
import net.minecraft.entity.mob.CreakingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreakingBrain.class)
public class CreakingBrainMixin {

    @Inject(method = "addFightTasks", at = @At("HEAD"), cancellable = true)
    private static void addFightTasks(Brain<CreakingEntity> brain, CallbackInfo ci) {
        ci.cancel();
    }

}
