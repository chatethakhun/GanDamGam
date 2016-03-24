package sut.game01.core;

import playn.core.ImageLayer;
import playn.core.Mouse;
import playn.core.PlayN.*;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;
import playn.core.Image;

import static playn.core.PlayN.*;


/**
 * Created by Administrator on 23/3/2559.
 */
public class TestScreen extends Screen {
    private final ScreenStack ss;
    private final ImageLayer bgLayer;
    private final ImageLayer backButton;

    public TestScreen(final ScreenStack ss) {
        this.ss = ss;

        Image bgImage = assets().getImage("images/bg.png");
        this.bgLayer = graphics().createImageLayer(bgImage);


        Image backImage = assets().getImage("images/back.png");
        this.backButton = graphics().createImageLayer(backImage);
        backButton.setTranslation(10 , 10);

        backButton.addListener(new Mouse.LayerAdapter(){

            public void onMouseUp(Mouse.ButtonEvent event) {
                ss.remove(ss.top());
            }
        });

    }


    public void wasShown() {
        super.wasShown();
        this.layer.add(bgLayer);
        this.layer.add(backButton);
    }
}
