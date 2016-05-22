package sut.game01.core;


import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.*;
import playn.core.DebugDrawBox2D;
import playn.core.util.Clock;
import sut.game01.core.Characters.Gun;
import sut.game01.core.Characters.SandRock;
import sut.game01.core.Characters.Shenlong;
import sut.game01.core.Characters.StarBeam;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

import java.util.ArrayList;
import java.util.HashMap;

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
    private boolean showDebugDraw = true,checkMatrix = false;
    private World world;
    private DebugDrawBox2D debugDraw;
    public static float M_PER_PIXEL = 1 / 26.666667f;
    private static int width = 24;
    private static int height = 18;
    private int i=0,j=0,g=0,check = 0;
    int [][]matrix = new int[4][4];





    ArrayList<Gun> test = new ArrayList<Gun>();
    Shenlong shenlong;
    ArrayList<StarBeam> starBeams = new ArrayList<StarBeam>();


    String debugString = String.valueOf("");
    int shenlongHP = 10;


    public GamePlay(final ScreenStack ss) {
        this.ss = ss;

        Image bgImage = assets().getImage("images/bg.png");
        this.bgLayer = graphics().createImageLayer(bgImage);




        Image tableImage = assets().getImage("images/Table/Table.png");
        this.tableLayer = graphics().createImageLayer(tableImage);
        tableLayer.setTranslation(175,100);



        Vec2 gravity = new Vec2(0.0f, 10.0f);
        world = new World(gravity);
        world.setWarmStarting(true);
        world.setAutoClearForces(true);

        sandRock.add(0,new SandRock(world, 100, 100f));

        starBeams.add(0,new StarBeam(world,150,100f));


    }

    public void wasShown() {
        super.wasShown();
        this.layer.add(bgLayer);
        this.layer.add(tableLayer);






        shenlong = new Shenlong(world,600,100f);
        this.layer.add(shenlong.layer());




        Body ground = world.createBody(new BodyDef());
        EdgeShape groundShape = new EdgeShape();
        groundShape.set(new Vec2(0, 15), new Vec2(width, 15));//set(new Vec2(???????), new Vec2(?????))
        ground.createFixture(groundShape, 0.0f);
        this.layer.add(sandRock.get(i).layer());




        j++;
        i++;






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



    this.layer.add(starBeams.get(0).layer());



    }
    @Override
    public void update(int delta) {
        super.update(delta);
        world.step(0.033f, 10, 10);


        for(int k=0;k<j;k++){
            sandRock.get(k).update(delta);
        }

        shenlong.update(delta);

        mouse().setListener(new Mouse.Adapter(){

            @Override
            public void onMouseDown(Mouse.ButtonEvent event) {


                for (int i = 0; i < matrix.length; i++) {
                    for (int j = 0; j < matrix[ i ].length; j++)
                    {
                        if(event.x() > 175 && event.x() < 250){
                            matrix[0][0] = 1;
                        } else if(event.x() > 250 && event.x() < 325) {
                            matrix[0][1] = 1;


                        } else if(event.x() > 325 && event.x() < 400){
                            matrix[0][2] = 1;
                            check = check + 1;
                            checkMatrix = true;
                            SandRock.state = SandRock.State.ATTK;
                            StarBeam.body.applyLinearImpulse(new Vec2(200f,0f), StarBeam.body.getPosition());
                        } else if(event.x() >400 && event.x() < 475){
                            matrix[0][3] = 1;
                        }
                            System.out.print(matrix[ i ][ j ] + " ");
                        }
                    if(checkMatrix = true){

                        matrix = new int[4][4];
                        checkMatrix = false;
                    }
                    System.out.println();

                }


                System.out.println(check/16);
                System.out.println("=====================================");
            }
        });


    }



    @Override
    public void paint(Clock clock) {
        super.paint(clock);



        for(int k=0;k<j;k++){
            sandRock.get(k).paint(clock);

        }


        shenlong.paint(clock);

        for(int k=0;k<j;k++){
            starBeams.get(k).paint(clock);

        }


        if(showDebugDraw) {
           debugDraw.getCanvas().clear();
           world.drawDebugData();
            debugDraw.getCanvas().setFillColor(Color.rgb(255,255,255));
            debugDraw.getCanvas().drawText(debugString,100,100);
        }
    }


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