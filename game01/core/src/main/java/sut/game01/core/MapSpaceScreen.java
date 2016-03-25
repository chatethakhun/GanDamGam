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
public class MapSpaceScreen extends Screen {

    private final ScreenStack ss;
    private final MapColonyScreen mapColonyScreen;
    private final ImageLayer bgLayer;
    private final ImageLayer mainLayer;
    private final ImageLayer arrowLayer;


    public MapSpaceScreen(final ScreenStack ss) {
        this.ss = ss;
        this.mapColonyScreen = new MapColonyScreen(ss);


        Image bgImage = assets().getImage("images/space.png");
        bgLayer = graphics().createImageLayer(bgImage);

        Image mainImage = assets().getImage("images/main.png");
        this.mainLayer = graphics().createImageLayer(mainImage);
        mainLayer.setTranslation(15, 15);

        Image arrowImage = assets().getImage("images/arrow.png");
        this.arrowLayer = graphics().createImageLayer(arrowImage);
        arrowLayer.setTranslation(570, 225);

        this.mainLayer.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseDown(Mouse.ButtonEvent event) {
                ss.remove(ss.top());
                ss.remove(ss.top());
            }
        });

        arrowLayer.addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                ss.push(mapColonyScreen);
            }
        });
    }

    @Override
    public void wasShown() {
        super.wasShown();

        this.layer.add(bgLayer);
        this.layer.add(mainLayer);
        this.layer.add(arrowLayer);
    }
}
