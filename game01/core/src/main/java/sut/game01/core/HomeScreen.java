package sut.game01.core;

import playn.core.Font;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Mouse;
import react.UnitSlot;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;
import tripleplay.ui.*;
import tripleplay.ui.layout.AxisLayout;


import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

/**
 * Created by Administrator on 23/3/2559.
 */
public class HomeScreen extends Screen {



    private final ScreenStack ss;
    private final TestScreen testScreen;
    private final ImageLayer bgLayer;
    private final ImageLayer logoLayer;
    private final ImageLayer newButton;
    private final ImageLayer loadLayer;
    private final ImageLayer settingLayer;

   // private final ImageLayer g1Layer;

    private Root root;

    public HomeScreen(final ScreenStack ss){
        this.ss = ss;
        this.testScreen = new TestScreen(ss);



        Image bgImage = assets().getImage("images/bg.png");
        this.bgLayer = graphics().createImageLayer(bgImage);

        //Image g1Image = assets().getImage("images/g1.png");
        //this.g1Layer = graphics().createImageLayer(g1Image);
        //g1Layer.setTranslation(400 , 0);

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
        settingLayer.setTranslation(10, 400);

        newButton.addListener(new Mouse.LayerAdapter(

        ) {

            public void onMouseUp(Mouse.ButtonEvent event) {
                ss.push(testScreen);
            }
        });



    }


    
    public void wasShown() {
        super.wasShown();

        this.layer.add(bgLayer);
        //this.layer.add(g1Layer);
        this.layer.add(logoLayer);
        this.layer.add(newButton);
        this.layer.add(loadLayer);
        this.layer.add(settingLayer);



    }
}
