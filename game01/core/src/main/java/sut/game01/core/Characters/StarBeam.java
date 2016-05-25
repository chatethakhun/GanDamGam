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
 * Created by Chatethakhun on 19/5/2559.
 */
public class StarBeam {
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    public static Body body;

    public void update(int delta) {

        if(hasLoaded == false) return;
        sprite.layer().setTranslation(
                (body.getPosition().x / GamePlay.M_PER_PIXEL) - 10,
                body.getPosition().y / GamePlay.M_PER_PIXEL);

        e += delta;
            spriteIndex = offset + ((spriteIndex + 1) % 7);
            sprite.setSprite(spriteIndex);
            e = 0;

    }

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

    public static StarBeam.State state = StarBeam.State.IDLE;

    private int e = 0;
    private int offset = 7;


    public StarBeam(final World world, final float x, final float y) {

        sprite = SpriteLoader.getSprite("images/Characters/SandRock/StarBeam.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite sprite) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width() / 2f,
                        sprite.height() / 2f);
                sprite.layer().setTranslation(x, y + 13f);
                body = initPhysicsBody(world,
                        GamePlay.M_PER_PIXEL * x,
                        GamePlay.M_PER_PIXEL * y);

                hasLoaded = true;
            }

            @Override
            public void onFailure(Throwable throwable) {
                PlayN.log().error("Error Loading Images!!!",throwable);
            }
        });
    }

    private Body initPhysicsBody(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position = new Vec2(0,0);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sprite.layer().width() * GamePlay.M_PER_PIXEL/2 ,
                sprite.layer().height()* GamePlay.M_PER_PIXEL /2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.4f;
        //fixtureDef.friction = 0.1f;
        //fixtureDef.restitution = 1f;
        body.createFixture(fixtureDef);

        body.setLinearDamping(0.2f);
        body.setTransform(new Vec2(x,y), 0f);
        return body;
    }
}