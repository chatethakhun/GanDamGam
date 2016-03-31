package sut.game01.core.Characters;

import playn.core.Key;
import playn.core.Keyboard;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.util.Callback;
import sut.game01.core.sprite.Sprite;
import sut.game01.core.sprite.SpriteLoader;

/**
 * Created by Chatethakhun on 31/3/2559.
 */
public class SandRock {

    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;

    public void update(int delta) {

        if(hasLoaded == false) return;

         e += delta;


        if(e > 200) {
            switch (state) {
                case IDLE: offset = 7;break;
                case ATTK: offset = 0;
                    if(spriteIndex == 6) {
                        state = State.HURT;
                    }
                        break;


                case HURT: offset = 14;
                    if(spriteIndex == 20){
                        state = State.IDLE;
                    }
                    break;
            }
            spriteIndex = offset + ((spriteIndex + 1) % 7);
            sprite.setSprite(spriteIndex);
            e = 0;
            System.out.println(spriteIndex);
        }
    }

    public Layer layer() {
        return sprite.layer();
    }

    public enum State {
        ATTK,IDLE,HURT
    };

    private State state = State.IDLE;

    private int e = 0;
    private int offset = 14;


    public SandRock(final float x, final  float y) {

        PlayN.keyboard().setListener(new Keyboard.Listener() {
            @Override
            public void onKeyDown(Keyboard.Event event) {

            }

            @Override
            public void onKeyTyped(Keyboard.TypedEvent typedEvent) {

            }

            @Override
            public void onKeyUp(Keyboard.Event event) {

                if(event.key() == Key.SPACE) {
                    switch (state) {
                        case IDLE: state = State.ATTK;break;
                        case ATTK: state = State.HURT;break;

                    }
                }

            }
        });

        sprite = SpriteLoader.getSprite("images/Characters/SandRock/SandRock.json");
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
