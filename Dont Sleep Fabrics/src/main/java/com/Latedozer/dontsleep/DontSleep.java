package com.Latedozer.dontsleep;


import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public class DontSleep implements ModInitializer {

    public static final String MOD_ID = "dontsleep";
    public int tries = 0;
    float dayTime;
    public PlayerEntity playerCont;
    public ServerWorldEvents.Load events;
    public ServerWorld world;
    public ServerPlayerEntity player2;
    public MinecraftServer server;
    public PlayerManager pm;

    @Override
    public void onInitialize() {


        ServerWorldEvents.LOAD.register((MinecraftServer, ServerWorld)->{
            world = ServerWorld;
            server = MinecraftServer;
            pm = server.getPlayerManager();
        });



     BedUseCallback.EVENT.register((player, bed) ->{
         if (player.world.getRegistryKey() == World.OVERWORLD) {
             if (tries == 2) {
                 player.sendMessage(Text.of("Your tiredness compels you lay on the bed, you know you won't wake up"), true);
                 playerCont = player;
                 dayTime = world.getTimeOfDay();
                 return ActionResult.SUCCESS;
             }
             if (tries < 2) {
                 player.sendMessage(Text.of("You know you shouldn't lay down, they always came for you" +
                         " (SpawnPoint set)"), true);
                 ++tries;
             }
             player2 = pm.getPlayer(String.valueOf(player.getDisplayName()));
             if (player2 != null) {
                 player2.setSpawnPoint(World.OVERWORLD, player.getBlockPos(), 0, true, true);
             }
             return ActionResult.FAIL;
         }
         return ActionResult.SUCCESS;
     });
     WakeUpCallback.EVENT.register((updateSleepingPlayers) -> {
         playerCont.setHealth(0);
         playerCont.sendMessage(Text.of(playerCont.getEntityName() + " has succumbed to their night terrors"), false);
         world.setTimeOfDay((long)dayTime);
         return ActionResult.SUCCESS;
     });

    }


}

