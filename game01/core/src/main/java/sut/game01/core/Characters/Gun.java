package sut.game01.core.Characters;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.util.Callback;
import playn.core.util.Clock;
import sut.game01.core.GamePlay;
import sut.game01.core.sprite.Sprite;
import sut.game01.core.sprite.SpriteLoader;

/**
 * Created by Administrator on 18/5/2559.
 */
public class Gun {
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    public static Body body;


    public Layer layer() {
        return sprite.layer();
    }

    public void paint(Clock clock) {
        if(hasLoaded == false)return;
        sprite.layer().setTranslation(
                (body.getPosition().x / GamePlay.M_PER_PIXEL) - 10,
                body.getPosition().y / GamePlay.M_PER_PIXEL
        );
    }

    public enum State {
        ATTK,IDLE,HURT
    };

    public static State state = State.IDLE;

    private int e = 0;
    private int offset = 7;

    public static Body getBody() {
        return body;
    }
    public Gun(final World world, final float x, final float y) {

        sprite = SpriteLoader.getSprite("images/Gun/gun.json");
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
                PlayN.log().error("Error Loading Images!!!",throwable);
            }
        });
    }


}
