package sut.game01.core;

import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Mouse;
import playn.core.util.Clock;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

import static playn.core.PlayN.*;

/**
 * Created by Chatethakhun on 25/3/2559.
 */
public class MapColonyScreen extends Screen {

    private final ScreenStack ss;
    private final Image bgImage;
    private final ImageLayer bgLayer;
    private final ImageLayer backarrow;
    private final Image cloudImage;
    private final ImageLayer mainLayer;
    private final ImageLayer cloudLayer;
    private final ImageLayer cloudLayer2;
    private final ImageLayer level4;
    private final ImageLayer level5;
    private final ImageLayer level6;
    private float x = 10;
    private float y = 100;
    private float a = 10;
    private float b = 250;
    private int i;




    public MapColonyScreen(final ScreenStack ss) {
        this.ss = ss;

        this.bgImage = assets().getImage("images/colony.png");
        this.bgLayer = graphics().createImageLayer(bgImage);

        Image bacArrowImage = assets().getImage("images/backarrow.png");
        this.backarrow = graphics().createImageLayer(bacArrowImage);
        backarrow.setTranslation(15, 225);

        cloudImage = assets().getImage("images/cloud.png");
        cloudLayer = graphics().createImageLayer(cloudImage);
        cloudLayer2 = graphics().createImageLayer(cloudImage);

        Image mainImage = assets().getImage("images/main.png");
        this.mainLayer = graphics().createImageLayer(mainImage);
        mainLayer.setTranslation(15, 15);

        Image level4Image = assets().getImage("images/l4.png");
        this.level4 = graphics().createImageLayer(level4Image);
        level4.setTranslation(150, 350);

        Image level5Image = assets().getImage("images/l5.png");
        this.level5 = graphics().createImageLayer(level5Image);
        level5.setTranslation(400, 300);

        Image level6Image = assets().getImage("images/l6.png");
        this.level6 = graphics().createImageLayer(level6Image);
        level6.setTranslation(380, 80);


        backarrow.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                ss.remove(ss.top());
            }
        });

        mainLayer.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {

                for (i = 0; i <= ss.size(); i++){
                    ss.remove(ss.top());
                }
            }
        });



    }

    @Override
    public void update(int delta) {

        x += 0.1f * delta;
        if ( x > this.bgImage.width() + this.cloudImage.width()){
            x = -cloudImage.width();
        }
        cloudLayer.setTranslation(x, y);

        a += 0.3f * delta/2;
        if ( a > this.bgImage.width() - this.cloudImage.width()){
            a = -cloudImage.width();
        }
        cloudLayer2.setTranslation(a, b);
    }



    @Override
    public void wasShown() {
        super.wasShown();

        this.layer.add(bgLayer);
        this.layer.add(mainLayer);
        this.layer.add(backarrow);
        this.layer.add(cloudLayer);
        this.layer.add(cloudLayer2);
        this.layer.add(level4);
        this.layer.add(level5);
        this.layer.add(level6);


    }
}
