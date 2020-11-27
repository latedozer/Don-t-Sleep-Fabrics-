package com.Latedozer.dontsleepmixins;

import com.Latedozer.dontsleep.WakeUpCallback;
import net.minecraft.block.BedBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static java.sql.Types.NULL;

@Mixin(PlayerEntity.class)
public class SleepWakeMixin {
    @Inject(at = @At(value = "INVOKE", ordinal = NULL), method = "wakeUp", cancellable = true)
    public void sleepWake(final boolean bl, final boolean updateSleepingPlayers, final CallbackInfo info) {
        ActionResult result = WakeUpCallback.EVENT.invoker().interact(updateSleepingPlayers);

        if(result == ActionResult.FAIL){
            info.cancel();
        }
    }
}
