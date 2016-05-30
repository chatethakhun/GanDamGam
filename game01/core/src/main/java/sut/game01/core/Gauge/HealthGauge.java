package sut.game01.core.Gauge;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.util.Callback;
import playn.core.util.Clock;
import sut.game01.core.GamePlay;
import sut.game01.core.sprite.Sprite;
import sut.game01.core.sprite.SpriteLoader;

/**
 * Created by Chatethakhun on 5/30/2016.
 */
public class HealthGauge {
    private static Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;


    public Layer layer() {

        return sprite.layer();
    }



    public enum State {
        FULL,DEC1,DEC2,DEC3,EMPTY
    };

    public static HealthGauge.State state = State.FULL;

    private int e = 0;
    private int offset = 0;


    public HealthGauge(final float x, final float y) {


        sprite = SpriteLoader.getSprite("images/HP/HP.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite sprite) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width() / 2f,
                        sprite.height() / 2f);
                sprite.layer().setTranslation(x, y + 13f);
                hasLoaded = true;

            }

            @Override
            public void onFailure(Throwable throwable) {
                PlayN.log().error("Error Loading Images!!!", throwable);
            }
        });
    }


    public void update(int delta) {

        if(hasLoaded == false) return;

        e += delta;

        if(e > 200) {
            switch (state) {
                case FULL: offset = 0;
                    break;
                case DEC1: offset = 1;
                    break;
                case DEC2: offset = 2;
                    break;
                case DEC3: offset = 3;
                    break;
                case EMPTY: offset = 4;
            }

            spriteIndex = offset + ((spriteIndex) % 1);
            sprite.setSprite(spriteIndex);
            e = 0;
        }




    }

    public void paint(Clock clock) {

    }





}
