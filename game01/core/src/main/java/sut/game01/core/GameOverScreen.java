package sut.game01.core;

import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Mouse;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

import static playn.core.PlayN.*;

/**
 * Created by Chatethakhun on 25/3/2559.
 */
public class GameOverScreen extends Screen {

    private final ScreenStack ss;
    private final MapSpaceScreen mapSpaceScreen;
    private final ImageLayer bgLayer;
    private final ImageLayer rebornLayer;
    private final ImageLayer retryLayer;
    private final ImageLayer mainLayer;
    private final ImageLayer mapLayer;
    private int i;

    public GameOverScreen(final ScreenStack ss) {
        this.ss = ss;
        this.mapSpaceScreen = new MapSpaceScreen(ss);

        Image bgImage = assets().getImage("images/over.png");
        this.bgLayer = graphics().createImageLayer(bgImage);

        Image rebornImage = assets().getImage("images/reborn.png");
        this.rebornLayer = graphics().createImageLayer(rebornImage);
        rebornLayer.setTranslation(15,420);

        Image retryImage = assets().getImage("images/retry.png");
        this.retryLayer = graphics().createImageLayer(retryImage);
        retryLayer.setTranslation(15, 355);

        Image mainImage = assets().getImage("images/main.png");
        this.mainLayer = graphics().createImageLayer(mainImage);
        mainLayer.setTranslation(15,15);

        Image mapImage = assets().getImage("images/map.png");
        this.mapLayer = graphics().createImageLayer(mapImage);
        mapLayer.setTranslation(530,15);


        mainLayer.addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseDown(Mouse.ButtonEvent event) {
                for (i = 0; i < ss.size(); i++){
                    ss.remove(ss.top());
                }
            }
        });

        mapLayer.addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                ss.push(mapSpaceScreen);
            }
        });







    }


    public void wasShown() {
        super.wasShown();

        this.layer.add(bgLayer);
        this.layer.add(rebornLayer);
        this.layer.add(retryLayer);
        this.layer.add(mainLayer);
        this.layer.add(mapLayer);
    }
}
