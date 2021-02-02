package com.Latedozer.dontsleep;

import com.Latedozer.dontsleep.components.IntComponent;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import nerdhub.cardinal.components.api.util.RespawnCopyStrategy;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class dontsleepcomponents implements EntityComponentInitializer {
    // Key containing the player IntComponent
    public static final ComponentKey<IntComponent> SLEEP_TRIES =
            ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier("dontsleep:sleeptries"), IntComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry entityComponentFactoryRegistry) {
        // Register the component for the player using the strategy INVENTORY
        entityComponentFactoryRegistry.registerForPlayers(SLEEP_TRIES, playerEntity -> {
            // Create an IntComponent with a default value. This will be saved on the player Nbt
            // The variable playerEntity would be the player that will get this new component created for them
            return new IntComponent(-2);
        }, RespawnCopyStrategy.ALWAYS_COPY);
    }

    // Method to set the player int value
    public static void setPlayerIntComponent(ServerPlayerEntity serverPlayerEntity, int valueToStore) {
        // Set the int value for the given player
        SLEEP_TRIES.get(serverPlayerEntity).setValue(valueToStore);
    }

    // Method to get the player int value
    public static int getPlayerIntComponent(ServerPlayerEntity serverPlayerEntity) {
        // Get the given player int value and return it
        return SLEEP_TRIES.get(serverPlayerEntity).getValue();
    }
}
