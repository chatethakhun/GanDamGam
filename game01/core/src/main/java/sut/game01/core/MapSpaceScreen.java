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
    private final ImageLayer planeLayer;
    private final ImageLayer levev1;
    private final ImageLayer levev2;
    private final ImageLayer levev3;
    private int i;



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

        Image planeImage = assets().getImage("images/plane.png");
        this.planeLayer = graphics().createImageLayer(planeImage);
        planeLayer.setTranslation(100, 200);


        Image level1Image = assets().getImage("images/l1.png");
        this.levev1 = graphics().createImageLayer(level1Image);
        levev1.setTranslation(150, 350);

        Image level2Image = assets().getImage("images/l2.png");
        this.levev2 = graphics().createImageLayer(level2Image);
        levev2.setTranslation(200, 150);

        Image level3Image = assets().getImage("images/l3.png");
        this.levev3 = graphics().createImageLayer(level3Image);
        levev3.setTranslation(380, 80);

        this.mainLayer.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseDown(Mouse.ButtonEvent event) {
                for (i = 0;i <= ss.size(); i++){
                    ss.remove(ss.top());
                }
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
        this.layer.add(planeLayer);
        this.layer.add(levev1);
        this.layer.add(levev2);
        this.layer.add(levev3);
    }
}
