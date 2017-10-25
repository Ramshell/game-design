package com.platformer.ar.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class GameTexturePacker {
    //private static final String INPUT_DIR = Gdx.files.internal("assets/data/animations").toString();
    private static final String INPUT_DIR = "./core/assets/robot/";
    private static final String OUTPUT_DIR = "./core/assets/animations/";
    private static final String PACK_FILE = "animations";

    private static final float[] HUNDRED_PERCENT = new float[] {1f};
    private static final float[] FIFTY_PERCENT = new float[] {0.5f};

    public static void main(String[] args){
        // create the packing's settings
        Settings settings = new Settings();

        // adjust the padding settings
        settings.scale = HUNDRED_PERCENT;//FIFTY_PERCENT;
        settings.paddingX = 2;
        settings.paddingY = 2;
        settings.edgePadding = false;
        settings.maxWidth = 2048;//4096;
        settings.maxHeight = 2048;//4096;

        // pack the images
        settings.combineSubdirectories = false;
        TexturePacker.process(settings, INPUT_DIR, OUTPUT_DIR, PACK_FILE);
    }
}
