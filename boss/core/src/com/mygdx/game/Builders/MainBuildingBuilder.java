package com.mygdx.game.Builders;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Components.CellComponent;
import com.mygdx.game.Components.CellsComponent;
import com.mygdx.game.Components.PlayerComponent;
import com.mygdx.game.Components.WorldObjects.ActionComponent;
import com.mygdx.game.Components.WorldObjects.Buildings.TasksComponent;
import com.mygdx.game.Components.WorldObjects.HealthComponent;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;
import com.mygdx.game.Entities.BuildingEntity;
import com.mygdx.game.Mappers.AssetsMapper;
import com.mygdx.game.Mappers.ResourceMapper;
import com.mygdx.game.OOP.Actions.*;
import com.mygdx.game.PathfindingUtils.MapGraph;
import com.mygdx.game.Play;

import java.util.Iterator;


public class MainBuildingBuilder {
    public static int COST = 600;
    private Play play;
    private TiledMapTileLayer background;
    private TiledMapTileLayer foreground;
    private TiledMapTile wallTile;
    private TiledMapTile wallTile2;
    private TiledMapTile wallTile3;
    private TiledMapTile wallTile4;
    private TiledMapTile wallTile5;
    private TiledMapTile wallTile6;
    private TiledMapTile wallTile7;
    private TiledMapTile wallTile8;
    private TiledMapTile wallTile9;
    private TiledMapTile wallTile10;
    private TiledMapTile wallTile11;
    private TiledMapTile wallTile12;
    private TiledMapTile wallTile13;
    private TiledMapTile wallTile14;
    private TiledMapTile wallTile15;
    private TiledMapTile wallTile16;

    public MainBuildingBuilder(OrthogonalTiledMapRenderer renderer, final Play play){
        this.background = (TiledMapTileLayer)renderer.getMap().getLayers().get("background");
        this.foreground = (TiledMapTileLayer)renderer.getMap().getLayers().get("foreground");
        TiledMapTileSet tileset = renderer.getMap().getTileSets().getTileSet("piso");
        wallTile = tileset.getTile(256+5);
        wallTile2 = tileset.getTile(256+6);
        wallTile3 = tileset.getTile(256+7);
        wallTile4 = tileset.getTile(256+8);
        wallTile5 = tileset.getTile(256+13);
        wallTile6 = tileset.getTile(256+14);
        wallTile7 = tileset.getTile(256+15);
        wallTile8 = tileset.getTile(256+16);
        wallTile9 = tileset.getTile(256+21);
        wallTile10 = tileset.getTile(256+22);
        wallTile11 = tileset.getTile(256+23);
        wallTile12 = tileset.getTile(256+24);
        wallTile13 = tileset.getTile(256+29);
        wallTile14 = tileset.getTile(256+30);
        wallTile15 = tileset.getTile(256+31);
        wallTile16 = tileset.getTile(256+32);
        this.play = play;
    }

    public BuildingEntity getWall(final PlayerComponent playerComponent, int x, int y){
        Array<ActionComponent> actions = new Array<ActionComponent>();
        ActionComponent craftHarland = new ActionComponent();
        craftHarland.button = AssetsMapper.craftHarlandWorkerButton;;
        craftHarland.listener = new ClickListener(){
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                play.hudComponent.hintTitle.setText("Train Harland Worker");
                play.hudComponent.hintCost.setText("Cost: " + HarlandWorkerBuilder.COST);
                if(playerComponent.resources < HarlandWorkerBuilder.COST){
                    play.hudComponent.hintCost.setColor(play.hudComponent.redColor);
                }else {
                    play.hudComponent.hintCost.setColor(play.hudComponent.greenColor);
                }
                play.hudComponent.hintContent.setText("This unit is useful to gather resources and to build structures");
            }
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor){
                play.hudComponent.hintTitle.setText("");
                play.hudComponent.hintCost.setText("");
                play.hudComponent.hintContent.setText("");
            }
            public void clicked (InputEvent event, float x, float y) {
                playerComponent.selectedObject.act(new AddToBuildQueueAction(new CreateHarlandWorkerAction(play.mapGraph, playerComponent, play), HarlandWorkerBuilder.COST));
            }
        };

        ActionComponent craftHarlandSoldier = new ActionComponent();
        craftHarlandSoldier.button = AssetsMapper.craftHarlandSoldierButton;;
        craftHarlandSoldier.listener = new ClickListener(){
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                play.hudComponent.hintTitle.setText("Train Harlanding Soldier");
                play.hudComponent.hintCost.setText("Cost: " + HarlandSoldierBuilder.COST);
                if(playerComponent.resources < HarlandSoldierBuilder.COST){
                    play.hudComponent.hintCost.setColor(play.hudComponent.redColor);
                }else {
                    play.hudComponent.hintCost.setColor(play.hudComponent.greenColor);
                }
                play.hudComponent.hintContent.setText("This Unit is the main defense for the harlandings");
            }
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor){
                play.hudComponent.hintTitle.setText("");
                play.hudComponent.hintCost.setText("");
                play.hudComponent.hintContent.setText("");
            }
            public void clicked (InputEvent event, float x, float y) {
                playerComponent.selectedObject.act(new AddToBuildQueueAction(new CreateHarlandSoldierAction(play.mapGraph, playerComponent, play), HarlandSoldierBuilder.COST));
            }
        };

        ActionComponent craftHarlandAmablesFlatterer = new ActionComponent();
        craftHarlandAmablesFlatterer.button = AssetsMapper.craftHarlandAmablesFlattererButton;;
        craftHarlandAmablesFlatterer.listener = new ClickListener(){
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                play.hudComponent.hintTitle.setText("Train Harlanding Amable's Flatterer");
                play.hudComponent.hintCost.setText("Cost: " + HarlandAmablesFlattererBuilder.COST);
                if(playerComponent.resources < HarlandAmablesFlattererBuilder.COST){
                    play.hudComponent.hintCost.setColor(play.hudComponent.redColor);
                }else {
                    play.hudComponent.hintCost.setColor(play.hudComponent.greenColor);
                }
                play.hudComponent.hintContent.setText("This Unit is the main attack for the harlandings");
            }
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor){
                play.hudComponent.hintTitle.setText("");
                play.hudComponent.hintCost.setText("");
                play.hudComponent.hintContent.setText("");
            }
            public void clicked (InputEvent event, float x, float y) {
                playerComponent.selectedObject.act(new AddToBuildQueueAction(new CreateHarlandAmablesFlattererAction(play.mapGraph, playerComponent, play), HarlandAmablesFlattererBuilder.COST));
            }
        };


        craftHarland.key = "craftHarland";
        craftHarlandSoldier.key = "craftHarlandSoldier";
        craftHarlandAmablesFlatterer.key = "craftHarlandAmablesFlatterer";
        actions.add(craftHarland);
        actions.add(craftHarlandSoldier);
        actions.add(craftHarlandAmablesFlatterer);
        Array<CellComponent> cells = new Array<CellComponent>();
        Vector2 v = new Vector2(x, y);
        CellComponent cellComponent = create_cell(wallTile9, background, v, 0 ,1);
        CellComponent cellComponent2 = create_cell(wallTile10, background, v, 1, 1);
        CellComponent cellComponent3 = create_cell(wallTile11, background, v, 2 ,1);
        CellComponent cellComponent4 = create_cell(wallTile12, background, v, 3, 1);
        CellComponent cellComponent5 = create_cell(wallTile13, background, v, 0 ,0);
        CellComponent cellComponent6 = create_cell(wallTile14, background, v, 1, 0);
        CellComponent cellComponent7 = create_cell(wallTile15, background, v, 2 ,0);
        CellComponent cellComponent8 = create_cell(wallTile16, background, v, 3, 0);
        cellComponent.blocked = true;
        cellComponent2.blocked = true;
        cellComponent3.blocked = true;
        cellComponent4.blocked = true;
        cellComponent5.blocked = true;
        cellComponent6.blocked = true;
        cellComponent7.blocked = true;
        cellComponent8.blocked = true;
        CellComponent cellComponent9 = create_cell(wallTile, foreground, v, 0 ,3);
        CellComponent cellComponent10 = create_cell(wallTile2, foreground, v, 1, 3);
        CellComponent cellComponent11 = create_cell(wallTile3, foreground, v, 2 ,3);
        CellComponent cellComponent12 = create_cell(wallTile4, foreground, v, 3, 3);
        CellComponent cellComponent13 = create_cell(wallTile5, foreground, v, 0 ,2);
        CellComponent cellComponent14 = create_cell(wallTile6, foreground, v, 1, 2);
        CellComponent cellComponent15 = create_cell(wallTile7, foreground, v, 2 ,2);
        CellComponent cellComponent16 = create_cell(wallTile8, foreground, v, 3, 2);
        cells.add(cellComponent);cells.add(cellComponent2);
        cells.add(cellComponent3);cells.add(cellComponent4);
        cells.add(cellComponent5);cells.add(cellComponent6);
        cells.add(cellComponent7);cells.add(cellComponent8);
        cells.add(cellComponent9);cells.add(cellComponent10);
        cells.add(cellComponent11);cells.add(cellComponent12);
        cells.add(cellComponent13);cells.add(cellComponent14);
        cells.add(cellComponent15);cells.add(cellComponent16);
        CellsComponent cellsComponent = new CellsComponent(cells, 4, 2);
        WorldObjectComponent wo = new WorldObjectComponent();
        wo.actions = actions;
        wo.cost = COST;
        wo.objectName = "Harlanding's Center";
        wo.sellValue = 900;
        wo.bounds = new RectangleMapObject(
                x * ResourceMapper.tileWidth,
                y * ResourceMapper.tileHeight,
                ResourceMapper.tileWidth *  cellsComponent.width,
                ResourceMapper.tileHeight * cellsComponent.height);
        wo.visibility = 1024f;
        return new BuildingEntity(playerComponent, new Vector2(x, y),
                cellsComponent, new HealthComponent(600),
                wo, play, 1024f);
    }

    private CellComponent create_cell(TiledMapTile t, TiledMapTileLayer layer,
                                       Vector2 position, int xOffset, int yOffset) {
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(t);
        CellComponent cellComponent = new CellComponent();
        cellComponent.layer = layer;
        cellComponent.position = position.cpy().add(xOffset, yOffset);
        cellComponent.xOffset = xOffset;
        cellComponent.yOffset = yOffset;
        cellComponent.cell = cell;
        return cellComponent;
    }
}
