package com.Latedozer.dontsleep;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.BedBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface BedUseCallback {

    Event<BedUseCallback> EVENT = EventFactory.createArrayBacked(BedUseCallback.class,
            (listeners) -> (player, bed) -> {
                for (BedUseCallback listener : listeners) {
                    ActionResult result = listener.interact(player, bed);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });

    ActionResult interact(PlayerEntity player, BedBlock bed);
}
