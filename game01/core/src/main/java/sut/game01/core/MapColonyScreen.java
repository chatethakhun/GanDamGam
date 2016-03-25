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
public class MapColonyScreen extends Screen {

    private final ScreenStack ss;
    private final ImageLayer bgLayer;
    private final ImageLayer backarrow;

    public MapColonyScreen(final ScreenStack ss) {
        this.ss = ss;

        Image bgImage = assets().getImage("images/colony.png");
        this.bgLayer = graphics().createImageLayer(bgImage);

        Image bacArrowImage = assets().getImage("images/backarrow.png");
        this.backarrow = graphics().createImageLayer(bacArrowImage);
        backarrow.setTranslation(15, 225);

        backarrow.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                ss.remove(ss.top());
            }
        });



    }

    @Override
    public void wasShown() {
        super.wasShown();

        this.layer.add(bgLayer);
        this.layer.add(backarrow);


    }
}
