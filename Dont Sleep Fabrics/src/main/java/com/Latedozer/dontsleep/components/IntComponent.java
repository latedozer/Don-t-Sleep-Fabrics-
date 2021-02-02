package com.Latedozer.dontsleep.components;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import net.minecraft.nbt.CompoundTag;

interface SimpleIntComponent extends ComponentV3 {
    int getValue();
    void setValue(int valueToSet);
}

public class IntComponent implements SimpleIntComponent {

    // Variable to hold a player int value
    private int playerIntComponent;

    // Constructor
    public IntComponent(int valueToStore) {
        // Set the player int component value on this class
        this.playerIntComponent = valueToStore;
    }

    @Override
    public int getValue() {
        return this.playerIntComponent;
    }

    @Override
    public void setValue(int valueToSet) {
        // Set the int value for the player
        this.playerIntComponent = valueToSet;
    }

    @Override
    public void readFromNbt(CompoundTag compoundTag) {
        // The compoundTag contains the player Nbt we will read from it
        int savedPlayerIntComponent = compoundTag.getInt("PlayerIntComponent");
        // Set the player in component value on this class
        this.playerIntComponent = savedPlayerIntComponent;
    }

    @Override
    public void writeToNbt(CompoundTag compoundTag) {
        // Store the player int component value on the player's Nbt. This is saved to disk
        compoundTag.putInt("PlayerIntComponent", this.playerIntComponent);
    }
}

