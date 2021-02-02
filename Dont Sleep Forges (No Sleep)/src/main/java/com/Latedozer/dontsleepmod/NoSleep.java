package com.Latedozer.dontsleepmod;


import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod("nosleep")
public class NoSleep {
    public int tries = 0;
    public NoSleep() {
        MinecraftForge.EVENT_BUS.register(this);

    }
    @SubscribeEvent
    public void event(PlayerSleepInBedEvent event) {
        ServerPlayerEntity player2 = (ServerPlayerEntity) event.getPlayer();
        PlayerEntity player = event.getPlayer();
        if (player2.world.getDimensionKey() == World.OVERWORLD) {
            player.sendStatusMessage(new StringTextComponent("You're too terrified to sleep"), true);
            player2.func_242111_a(World.OVERWORLD, player.getPosition(), (float) 0.0, true, false);
            event.setResult(PlayerEntity.SleepResult.NOT_POSSIBLE_HERE);
        }
    }
}


