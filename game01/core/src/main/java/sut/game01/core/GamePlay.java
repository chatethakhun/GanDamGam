package sut.game01.core;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import playn.core.*;
import playn.core.util.Clock;
import sut.game01.core.Characters.SandRock;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

import java.util.ArrayList;

import static playn.core.PlayN.*;


/**
 * Created by Chatethakhun on 24/3/2559.
 */
public class GamePlay extends Screen {
    // Level
    private final ScreenStack ss;
    private final ImageLayer bgLayer;
    //private final ImageLayer gunLayer;
    private final ImageLayer tableLayer;
    private int p;
    private ArrayList<SandRock> sandRock = new ArrayList<SandRock>();
    //private ArrayList<Image> gunImage = new ArrayList<Image>();
    private ArrayList<ImageLayer> gunLayer = new ArrayList<ImageLayer>();
    private boolean showDebugDraw = true;
    private World world;
    private DebugDrawBox2D debugDraw;
    public static float M_PER_PIXEL = 1 / 26.666667f;
    private static int width = 24;
    private static int height = 18;
    private int i=0,j=0,check = 0;

    public GamePlay(final ScreenStack ss) {
        this.ss = ss;

        Image bgImage = assets().getImage("images/bg.png");
        this.bgLayer = graphics().createImageLayer(bgImage);

        Image gunImage = assets().getImage("images/Gun/gun.png");
        gunLayer.add(graphics().createImageLayer(gunImage));
        //Image gunImage = assets().getImage("images/Gun/gun.png");
        //this.gunLayer = graphics().createImageLayer(gunImage);
        //gunLayer.setTranslation(185, 130);

        Image tableImage = assets().getImage("images/Table/Table.png");
        this.tableLayer = graphics().createImageLayer(tableImage);
        tableLayer.setTranslation(170,120);


        keyboard().setListener(new Keyboard.Listener() {
            @Override
            public void onKeyDown(Keyboard.Event event) {
                if(event.key() == Key.ESCAPE){
                for (p = 0; p < ss.size(); p++) {
                    ss.remove(ss.top());
                    System.out.println(check);
                    check = 1;
                    }
                }else if (event.key() == Key.SPACE) {
                        switch (SandRock.state) {
                            case IDLE: SandRock.state = SandRock.State.ATTK;
                        }
                    }


            }

            @Override
            public void onKeyTyped(Keyboard.TypedEvent typedEvent) {

            }

            @Override
            public void onKeyUp(Keyboard.Event event) {

            }
        });



        Vec2 gravity = new Vec2(0.0f, 10.0f);
        world = new World(gravity);
        world.setWarmStarting(true);
        world.setAutoClearForces(true);




    }

    public void wasShown() {
        super.wasShown();
        this.layer.add(bgLayer);
        this.layer.add(tableLayer);

            this.layer.add(gunLayer.get(i));


        sandRock.add(new SandRock(world, 100, 100f));

        Body ground = world.createBody(new BodyDef());
        EdgeShape groundShape = new EdgeShape();
        groundShape.set(new Vec2(0, 9), new Vec2(width, 9));//set(new Vec2(่ำแหน่ง), new Vec2(ตขนาด))
        ground.createFixture(groundShape, 0.0f);
        this.layer.add(sandRock.get(i).layer());
        System.out.println(check);
        if(check == 1) {

            layer.remove(sandRock.get(i).layer());
            System.out.print("FOUND!!!!");
            check = 0;
            //this.layer.remove();
            //graphics().d
            //this.layer.remove(sandRock.get(0).layer());
            //this.layer.remove(sandRock.get(0).layer());

        }

        if (showDebugDraw) {
            CanvasImage image = graphics().createImage((int) (width / GamePlay.M_PER_PIXEL),
                    (int) (height / GamePlay.M_PER_PIXEL));
            layer.add(graphics().createImageLayer(image));
            debugDraw = new DebugDrawBox2D();
            debugDraw.setCanvas(image);
            debugDraw.setFlipY(false);
            debugDraw.setStrokeAlpha(150);
            debugDraw.setFillAlpha(75);
            debugDraw.setStrokeWidth(2.0f);
            debugDraw.setFlags(DebugDraw.e_shapeBit |
                    DebugDraw.e_jointBit |
                    DebugDraw.e_aabbBit);
            debugDraw.setCamera(0, 0, 1f / GamePlay.M_PER_PIXEL);
            //world.setDebugDraw(debugDraw);
        }


        /*mouse().setListener(new Mouse.Adapter() {
            @Override
            public void onMouseDown(Mouse.ButtonEvent event) {
                sandRock.add(i,new SandRock(world,event.x(),event.y()));
                layer.add(sandRock.get(i).layer());
                i++;
                j++;
            }

        });
*/


    }
    @Override
    public void update(int delta) {
        super.update(delta);
        world.step(0.033f, 10, 10);
        for(int k=0;k<=j;k++){
            sandRock.get(k).update(delta);
        }
    }



    @Override
    public void paint(Clock clock) {
        super.paint(clock);
        for(int k=0;k<=j;k++){
            sandRock.get(k).paint(clock);
        }
        if(showDebugDraw) {
            debugDraw.getCanvas().clear();
            world.drawDebugData();
        }
    }
}
