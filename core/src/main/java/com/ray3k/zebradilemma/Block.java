package com.ray3k.zebradilemma;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import java.io.Serializable;

public interface Block extends Json.Serializable {
    Array<Block> getChildren();
    int getMinimumChildren();
    boolean needsCondition();
    Condition getCondition();
    String getDisplayString();
}
