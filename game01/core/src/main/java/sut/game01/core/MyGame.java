package sut.game01.core;

import static playn.core.PlayN.*;

import playn.core.Game;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.util.Clock;
import tripleplay.game.ScreenStack;

import java.util.*;

public class MyGame extends Game.Default {

    public static final  int  UPDATE_RATE = 25;
    private ScreenStack ss = new ScreenStack();
    protected final Clock.Source clock = new Clock.Source(UPDATE_RATE);
    //private List<ImageLayer> ball_arr;
    //private Map<String, ImageLayer> ball;

  public MyGame() {
    super(UPDATE_RATE); // call update every 33ms (30 times per second)
  }


  public void init() {

        ss.push(new HomeScreen(ss));

      /*Image ballImage = assets().getImage("images/ball.ong");
      //this.ball = new HashMap<String, ImageLayer>();
      //this.ball_arr = new ArrayList<ImageLayer>();

        for(int i = 0; i < 20; i++) {
            this.ball.add(graphics().createImageLayer(ballImage));
            ball.put("ball_" + i, ball);

            //this.ball.add(graphics().createImageLayer(ballImage));
        }

        ball.get("ball_80");
        ball.put("mushroom_1", ...);


      for(ImageLayer b: this.ball.values()) {
          b.setTranslation(100 + new Random().nextInt(100),
          100 + new Random().nextInt(100));
          graphics().rootLayer().add(b);
      }

        ball.get("bal_80").setTranslation();
      */
  }


  public void update(int delta) {
      ss.update(delta);

      /*for(ImageLayer b: this.ball.values) {
          b.setTranslation(
                  b.tx() + (-5 + new Random().nextInt(10)),
                  b.ty() + (-5 + new Random().nextInt(10))
          );

      }*/

  }


  public void paint(float alpha) {
    // the background automatically paints itself, so no need to do anything here!
    clock.paint(alpha);
    ss.paint(clock);

  }
}
