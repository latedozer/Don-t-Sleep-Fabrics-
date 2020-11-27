package com.Latedozer.dontsleep;


import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public class NoSleep implements ModInitializer {

    public static final String MOD_ID = "dontsleep";
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
         player2 = pm.getPlayer(player.getUuid());
         if(player2.world.getRegistryKey() == World.OVERWORLD) {
                 player.sendMessage(Text.of("You know you shouldn't lay down, they always came for you"), true);
             if (player2 != null) {
                 player2.setSpawnPoint(world.OVERWORLD, player.getBlockPos(), 0, true, true);
             }
             return ActionResult.FAIL;
         }
         return ActionResult.SUCCESS;
     });

    }


}

