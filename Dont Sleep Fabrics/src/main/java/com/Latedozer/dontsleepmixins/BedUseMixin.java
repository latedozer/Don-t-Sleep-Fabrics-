package com.Latedozer.dontsleepmixins;

import com.Latedozer.dontsleep.BedUseCallback;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static java.sql.Types.NULL;

@Mixin(BedBlock.class)
    public class BedUseMixin{
    @Inject(at = @At(value = "INVOKE", ordinal = NULL), method = "onUse", cancellable = true)
    public void onSleep(final BlockState state, final World world, BlockPos pos,final PlayerEntity player,final Hand hand,final BlockHitResult hit,final CallbackInfoReturnable<Boolean> info) {
        ActionResult result = BedUseCallback.EVENT.invoker().interact(player, (BedBlock) (Object) this);

        if(result == ActionResult.FAIL){
            info.cancel();
        }
    }
    }

