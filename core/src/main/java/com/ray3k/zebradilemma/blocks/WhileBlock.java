package com.ray3k.zebradilemma.blocks;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.ray3k.zebradilemma.Block;
import com.ray3k.zebradilemma.Condition;

public class WhileBlock implements Block {
    public Condition condition;

    @Override
    public Array<Block> getChildren() {
        return null;
    }

    @Override
    public int getMinimumChildren() {
        return 0;
    }

    @Override
    public boolean needsCondition() {
        return false;
    }

    @Override
    public Condition getCondition() {
        return null;
    }

    @Override
    public String getDisplayString() {
        return "While";
    }

    @Override
    public void write(Json json) {

    }

    @Override
    public void read(Json json, JsonValue jsonData) {

    }
}
