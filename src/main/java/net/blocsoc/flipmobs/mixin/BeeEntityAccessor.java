package net.blocsoc.flipmobs.mixin;

import net.minecraft.entity.passive.BeeEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.UUID;

@Mixin(BeeEntity.class)
public interface BeeEntityAccessor {
    @Invoker("setAngerTime")
    void setAngerTime(int angerTime);

    @Invoker("setAngryAt")
    void setAngryAt(@Nullable UUID angryAt);
}