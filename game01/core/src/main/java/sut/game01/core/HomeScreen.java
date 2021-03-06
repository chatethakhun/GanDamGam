package sut.game01.core;

import playn.core.*;
import playn.core.util.Clock;
import react.UnitSlot;
import sut.game01.core.Characters.SandRock;
import sut.game01.core.Characters.Shenlong;
import sut.game01.core.Characters.StarBeam;
import sut.game01.core.Gauge.HealthGauge;
import sut.game01.core.Gauge.HealthGaugeSandRock;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;
import tripleplay.ui.*;
import tripleplay.ui.layout.AxisLayout;


import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.keyboard;

/**
 * Created by Administrator on 23/3/2559.
 */
public class HomeScreen extends Screen {



    private final ScreenStack ss;
    private final GamePlay gamePlay;
    private final Setting setting;
    private final GameOverScreen loadGame;
    private final ImageLayer bgLayer;
    private final ImageLayer logoLayer;
    private final ImageLayer newButton;
    private final ImageLayer loadLayer;
    private final ImageLayer settingLayer;





    private Root root;

    public HomeScreen(final ScreenStack ss){
        this.ss = ss;
        this.gamePlay = new GamePlay(ss);
        this.setting = new Setting(ss);
        this.loadGame = new GameOverScreen(ss);




        Image bgImage = assets().getImage("images/bg.png");
        this.bgLayer = graphics().createImageLayer(bgImage);


        Image logoImage = assets().getImage("images/logo.png");
        this.logoLayer = graphics().createImageLayer(logoImage);
        logoLayer.setTranslation(180 , 10);



        Image newImage = assets().getImage("images/button.png");
        this.newButton = graphics().createImageLayer(newImage);
        newButton.setTranslation(450, 330);

        Image loadImage = assets().getImage("images/load.png");
        this.loadLayer = graphics().createImageLayer(loadImage);
        loadLayer.setTranslation(450, 400);

        Image settingImage = assets().getImage("images/setting.png");
        this.settingLayer = graphics().createImageLayer(settingImage);
        settingLayer.setTranslation(20, 400);


        newButton.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseDown(Mouse.ButtonEvent event) {
                ss.push(gamePlay);
                gamePlay.clearScreen();

            }
        });

        settingLayer.addListener(new Mouse.LayerAdapter() {

            public void onMouseDown(Mouse.ButtonEvent event) {
                ss.push(setting);
            }
        });

        loadLayer.addListener(new Mouse.LayerAdapter() {

            public void onMouseDown(Mouse.ButtonEvent event) {
                ss.push(loadGame);
            }
        });



    }

    @Override
    public void update(int delta) {
        super.update(delta);

    }

    public void wasShown() {
        super.wasShown();

        this.layer.add(bgLayer);
        this.layer.add(logoLayer);
        this.layer.add(newButton);
        this.layer.add(loadLayer);
        this.layer.add(settingLayer);






    }

    @Override
    public void paint(Clock clock) {
        super.paint(clock);

    }
}
