package com.Latedozer.dontsleep;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.BedBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface WakeUpCallback {

    Event<WakeUpCallback> EVENT = EventFactory.createArrayBacked(WakeUpCallback.class,
            (listeners) -> (updateSleepingPlayers) -> {
                for (WakeUpCallback listener : listeners) {
                    ActionResult result = listener.interact(updateSleepingPlayers);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });

    ActionResult interact(Boolean updateSleepingPlayers);
}