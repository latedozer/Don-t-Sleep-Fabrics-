package com.Latedozer.dontsleep;



import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;


public class DontSleep implements ModInitializer {

    public static final String MOD_ID = "dontsleep";
    public int tries;
    float dayTime;
    public PlayerEntity playerCont;
    public ServerWorldEvents.Load events;
    public ServerWorld world;
    public ServerPlayerEntity player2;
    public MinecraftServer server;
    public PlayerManager pm;

    @Override
    public void onInitialize() {


        ServerWorldEvents.LOAD.register((MinecraftServer, ServerWorld) -> {
            world = ServerWorld;
            server = MinecraftServer;
            pm = server.getPlayerManager();
        });


        BedUseCallback.EVENT.register((player, bed) -> {

            if (player.world.getRegistryKey() == World.OVERWORLD) {
                player2 = pm.getPlayer(player.getUuid());
                tries = dontsleepcomponents.getPlayerIntComponent(player2);
                if (tries == 2) {
                    player.sendMessage(new LiteralText("Your tiredness compels you lay on the bed, you know you won't wake up"), true);
                    playerCont = player;
                    dayTime = world.getTimeOfDay();
                    return ActionResult.SUCCESS;
                }
                if (tries < 2) {
                    player.sendMessage(new LiteralText("You know you shouldn't lay down, they always came for you" + " (SpawnPoint set)"), true);
                    ++tries;
                    dontsleepcomponents.setPlayerIntComponent(player2, tries);
                }
                if (player2 != null) {
                    player2.setSpawnPoint(World.OVERWORLD, player.getBlockPos(), 0, true, true);
                }
                return ActionResult.FAIL;
            }
            return ActionResult.SUCCESS;
        });
        WakeUpCallback.EVENT.register((updateSleepingPlayers) -> {
            if(!world.isClient)
            world.setTimeOfDay((long) dayTime);
            playerCont.setHealth(0);
            playerCont.sendMessage(new LiteralText(playerCont.getEntityName() + " has succumbed to their night terrors"), false);
            server.sendSystemMessage(new LiteralText(playerCont.getEntityName() + " has succumbed to their night terrors"), null);
            return ActionResult.SUCCESS;
        });

    }
}



