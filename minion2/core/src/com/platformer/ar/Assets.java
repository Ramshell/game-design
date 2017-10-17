package com.platformer.ar;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Assets{
    public static Texture robotIdle, robotIdle1, robotIdle2, robotIdle3, robotIdle4, robotIdle5, robotIdle6,
            robotIdle7, robotIdle8, robotIdle9;
    public static Texture robotShoot;
    public static Texture robotRun, robotRun1, robotRun2, robotRun3, robotRun4, robotRun5, robotRun6,
            robotRun7, robotRun8, robotRun9;
    public static Texture robotSlide;
    public static Texture robotRunShoot;
    public static Texture robotMelee;
    public static Texture robotRunMelee;
    public static Animation<TextureRegion> robotIdleAnim;
    public static Animation<TextureRegion> robotRunRightAnim;
    public static Animation<TextureRegion> robotRunLeftAnim;


    public static void load(){
        robotIdle = new Texture("robot/Idle/Idle (1).png");
        robotIdle1 = new Texture("robot/Idle/Idle (2).png");
        robotIdle2 = new Texture("robot/Idle/Idle (3).png");
        robotIdle3 = new Texture("robot/Idle/Idle (4).png");
        robotIdle4 = new Texture("robot/Idle/Idle (5).png");
        robotIdle5 = new Texture("robot/Idle/Idle (6).png");
        robotIdle6 = new Texture("robot/Idle/Idle (7).png");
        robotIdle7 = new Texture("robot/Idle/Idle (8).png");
        robotIdle8 = new Texture("robot/Idle/Idle (9).png");
        robotIdle9 = new Texture("robot/Idle/Idle (10).png");

        Array<TextureRegion> arr1 = new Array<TextureRegion>();
        arr1.add(new TextureRegion(robotIdle, 0, 0, 556, 567));
        arr1.add(new TextureRegion(robotIdle1, 0, 0, 556, 567));
        arr1.add(new TextureRegion(robotIdle2, 0, 0, 556, 567));
        arr1.add(new TextureRegion(robotIdle3, 0, 0, 556, 567));
        arr1.add(new TextureRegion(robotIdle4, 0, 0, 556, 567));
        arr1.add(new TextureRegion(robotIdle5, 0, 0, 556, 567));
        arr1.add(new TextureRegion(robotIdle6, 0, 0, 556, 567));
        arr1.add(new TextureRegion(robotIdle7, 0, 0, 556, 567));
        arr1.add(new TextureRegion(robotIdle8, 0, 0, 556, 567));
        arr1.add(new TextureRegion(robotIdle9, 0, 0, 556, 567));
        robotIdleAnim = new Animation<TextureRegion>(0.1f, arr1, Animation.PlayMode.LOOP);


        robotRun = new Texture("robot/Run/Run (1).png");
        robotRun1 = new Texture("robot/Run/Run (2).png");
        robotRun2 = new Texture("robot/Run/Run (3).png");
        robotRun3 = new Texture("robot/Run/Run (4).png");
        robotRun4 = new Texture("robot/Run/Run (5).png");
        robotRun5 = new Texture("robot/Run/Run (6).png");
        robotRun6 = new Texture("robot/Run/Run (7).png");
        robotRun7 = new Texture("robot/Run/Run (8).png");

        Array<TextureRegion> arr = new Array<TextureRegion>();
        arr.add(new TextureRegion(robotRun, 0, 0, 556, 567));
        arr.add(new TextureRegion(robotRun1, 0, 0, 556, 567));
        arr.add(new TextureRegion(robotRun2, 0, 0, 556, 567));
        arr.add(new TextureRegion(robotRun3, 0, 0, 556, 567));
        arr.add(new TextureRegion(robotRun4, 0, 0, 556, 567));
        arr.add(new TextureRegion(robotRun5, 0, 0, 556, 567));
        arr.add(new TextureRegion(robotRun6, 0, 0, 556, 567));
        arr.add(new TextureRegion(robotRun7, 0, 0, 556, 567));

        Array<TextureRegion> arr2 = new Array<TextureRegion>();
        TextureRegion robotRunRegion = new TextureRegion(robotRun, 0, 0, 556, 567);
        TextureRegion robotRunRegion1 = new TextureRegion(robotRun1, 0, 0, 556, 567);
        TextureRegion robotRunRegion2 = new TextureRegion(robotRun2, 0, 0, 556, 567);
        TextureRegion robotRunRegion3 = new TextureRegion(robotRun3, 0, 0, 556, 567);
        TextureRegion robotRunRegion4 = new TextureRegion(robotRun4, 0, 0, 556, 567);
        TextureRegion robotRunRegion5 = new TextureRegion(robotRun5, 0, 0, 556, 567);
        TextureRegion robotRunRegion6 = new TextureRegion(robotRun6, 0, 0, 556, 567);
        TextureRegion robotRunRegion7 = new TextureRegion(robotRun7, 0, 0, 556, 567);
        arr2.add(robotRunRegion);
        arr2.add(robotRunRegion1);
        arr2.add(robotRunRegion2);
        arr2.add(robotRunRegion3);
        arr2.add(robotRunRegion4);
        arr2.add(robotRunRegion5);
        arr2.add(robotRunRegion6);
        arr2.add(robotRunRegion7);
        robotRunRegion.flip(true, false);
        robotRunRegion1.flip(true, false);
        robotRunRegion2.flip(true, false);
        robotRunRegion3.flip(true, false);
        robotRunRegion4.flip(true, false);
        robotRunRegion5.flip(true, false);
        robotRunRegion6.flip(true, false);
        robotRunRegion7.flip(true, false);


        robotRunRightAnim = new Animation<TextureRegion>(0.2f, arr, Animation.PlayMode.LOOP);
        robotRunLeftAnim = new Animation<TextureRegion>(0.2f, arr2, Animation.PlayMode.LOOP);

    }
}
