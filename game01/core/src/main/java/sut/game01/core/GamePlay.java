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
import sut.game01.core.Characters.SandRock;
import sut.game01.core.Characters.Shenlong;
import sut.game01.core.Characters.StarBeam;
import sut.game01.core.Gauge.HealthGauge;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

import java.util.ArrayList;

import static playn.core.PlayN.*;


/**
 * Created by Chatethakhun on 24/3/2559.
 */
public class GamePlay extends Screen {


    private final ScreenStack ss;
    private final ImageLayer bgLayer;
    private final ImageLayer tableLayer;
    public static World world;
    private DebugDrawBox2D debugDraw;
    MapSpaceScreen mapSpaceScreen;
    private GroupLayer groupLayer = graphics().createGroupLayer();


    private boolean showDebugDraw = true,checkMatrix = false;
    public static float M_PER_PIXEL = 1 / 26.666667f;
    private static int width = 24;
    private static int height = 18;
    int check = 0,shenlongHP = 10,sandRockHP = 10, countPlayer = 0;
    int[][] matrix = new int[4][4];
    private boolean checkMulti2 = false;



    SandRock sandrock;
    HealthGauge healthGauge;
    Shenlong shenlong;



    static ArrayList<StarBeam> starBeamList;
    ArrayList<Body> impactStarBeam = new ArrayList<Body>();






    public GamePlay(final ScreenStack ss) {

        this.ss = ss;
        this.mapSpaceScreen = new MapSpaceScreen(ss);

        Image bgImage = assets().getImage("images/bg.png");
        this.bgLayer = graphics().createImageLayer(bgImage);


        Image tableImage = assets().getImage("images/Table/Table.png");
        this.tableLayer = graphics().createImageLayer(tableImage);
        tableLayer.setTranslation(175, 100);


        Vec2 gravity = new Vec2(0.0f, 10.0f);
        world = new World(gravity);
        world.setWarmStarting(true);
        world.setAutoClearForces(true);





        starBeamList = new ArrayList<StarBeam>();


    }

    public void wasShown() {
        super.wasShown();
        this.layer.add(bgLayer);
        this.layer.add(tableLayer);
        layer.add(groupLayer);

        sandrock = new SandRock(world, 50, 400f);
        this.layer.add(sandrock.layer());

        shenlong = new Shenlong(world, 550f, 400f);
        this.layer.add(shenlong.layer());

        healthGauge = new HealthGauge(520f,50f);
        this.layer.add(healthGauge.layer());

        Body ground = world.createBody(new BodyDef());
        EdgeShape groundShape = new EdgeShape();
        groundShape.set(new Vec2(0, 15), new Vec2(width, 15));//set(new Vec2(???????), new Vec2(?????))
        ground.createFixture(groundShape, 0.0f);

        Body groundL = world.createBody(new BodyDef());
        EdgeShape groundShapeL = new EdgeShape();
        groundShapeL.set(new Vec2(0, 0), new Vec2(0, height));
        groundL.createFixture(groundShapeL, 0.0f);

        Body groundR = world.createBody(new BodyDef());
        EdgeShape groundShapeR = new EdgeShape();
        groundShapeR.set(new Vec2(width, 0), new Vec2(width, height));
        groundR.createFixture(groundShapeR, 0.0f);

        keyboard().setListener(new Keyboard.Listener() {
            @Override
            public void onKeyDown(Keyboard.Event event) {
                switch (event.key()) {
                    case ESCAPE:
                        ss.remove(ss.top());

                }
            }

            @Override
            public void onKeyTyped(Keyboard.TypedEvent typedEvent) {

            }

            @Override
            public void onKeyUp(Keyboard.Event event) {

            }
        });




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
            world.setDebugDraw(debugDraw);
        }

    }

    @Override
    public void update(int delta) {
        world.step(0.033f, 10, 10);



       sandrock.update(delta);


        shenlong.update(delta);



        for(StarBeam starBeam : starBeamList) {
            starBeam.update(delta);
            groupLayer.add(starBeam.layer());
        }

        healthGauge.update(delta);





        world.setContactListener(new ContactListener() {
                                     @Override
                                     public void beginContact(final Contact contact) {






                                         mouse().setListener(new Mouse.Adapter() {

                                                                 @Override
                                                                 public void onMouseUp(Mouse.ButtonEvent event) {


                                                                     for (int i = 0; i < matrix.length; i++) {
                                                                         for (int j = 0; j < matrix[i].length; j++) {
                                                                             if (event.x() > 175 && event.x() < 250 && event.y() > 100 && event.y() < 175) {
                                                                                 matrix[0][0] = 1;
                                                                                 checkMatrix = true;
                                                                             } else if (event.x() > 250 && event.x() < 325 && event.y() > 100 && event.y() < 175) {
                                                                                 matrix[0][1] = 1;
                                                                                 checkMatrix = true;
                                                                             } else if (event.x() > 325 && event.x() < 400 && event.y() > 100 && event.y() < 175) {
                                                                                 matrix[0][2] = 1;
                                                                                 checkMatrix = true;
                                                                             } else if (event.x() > 400 && event.x() < 475 && event.y() > 100 && event.y() < 175) {
                                                                                 matrix[0][3] = 1;
                                                                                 checkMatrix = true;
                                                                             }

                                                                             //======================================================================================

                                                                             else if (event.x() > 175 && event.x() < 250 && event.y() > 175 && event.y() < 250) {
                                                                                 matrix[1][0] = 1;
                                                                                 checkMatrix = true;
                                                                             } else if (event.x() > 250 && event.x() < 325 && event.y() > 175 && event.y() < 250) {
                                                                                 matrix[1][1] = 1;
                                                                                 checkMatrix = true;
                                                                             } else if (event.x() > 325 && event.x() < 400 && event.y() > 175 && event.y() < 250) {
                                                                                 matrix[1][2] = 1;
                                                                                 checkMatrix = true;
                                                                             } else if (event.x() > 400 && event.x() < 475 && event.y() > 175 && event.y() < 250) {
                                                                                 matrix[1][3] = 1;
                                                                                 checkMatrix = true;
                                                                             }

                                                                             //=======================================================================================
                                                                             else if (event.x() > 175 && event.x() < 250 && event.y() > 250 && event.y() < 325) {
                                                                                 matrix[2][0] = 1;
                                                                                 checkMatrix = true;
                                                                             } else if (event.x() > 250 && event.x() < 325 && event.y() > 250 && event.y() < 325) {
                                                                                 matrix[2][1] = 1;
                                                                                 checkMatrix = true;
                                                                             } else if (event.x() > 325 && event.x() < 400 && event.y() > 250 && event.y() < 325) {
                                                                                 matrix[2][2] = 1;
                                                                                 checkMatrix = true;
                                                                             } else if (event.x() > 400 && event.x() < 475 && event.y() > 250 && event.y() < 325) {
                                                                                 matrix[2][3] = 1;
                                                                                 checkMatrix = true;
                                                                             }

                                                                             //======================================================================================
                                                                             else if (event.x() > 175 && event.x() < 250 && event.y() > 325 && event.y() < 400) {
                                                                                 matrix[3][0] = 1;
                                                                                 checkMatrix = true;
                                                                             } else if (event.x() > 250 && event.x() < 325 && event.y() > 325 && event.y() < 400) {
                                                                                 matrix[3][1] = 1;
                                                                                 checkMatrix = true;
                                                                             } else if (event.x() > 325 && event.x() < 400 && event.y() > 325 && event.y() < 400) {
                                                                                 matrix[3][2] = 1;
                                                                                 checkMatrix = true;
                                                                             } else if (event.x() > 400 && event.x() < 475 && event.y() > 325 && event.y() < 400) {
                                                                                 matrix[3][3] = 1;
                                                                                 checkMatrix = true;
                                                                             }
                                                                             System.out.print(matrix[i][j] + " ");
                                                                         }
                                                                         System.out.println();
                                                                     }
                                                                     if(matrix[0][0] == 1 && matrix[0][1] == 1 && matrix[0][2] == 1) {
                                                                         SandRock.state = SandRock.State.ATTK;
                                                                         clearMatrix();



                                                                     }else if(matrix[1][0] == 1 && matrix[2][0] == 1 && matrix[3][0] == 1){
                                                                         switch (HealthGauge.state) {
                                                                             case DEC1: HealthGauge.state = HealthGauge.State.FULL;
                                                                                 break;
                                                                             case DEC2: HealthGauge.state = HealthGauge.State.DEC1;
                                                                                 break;
                                                                             case DEC3: HealthGauge.state = HealthGauge.State.DEC2;
                                                                                 break;
                                                                         }
                                                                         while (shenlongHP < 10) {
                                                                             shenlongHP = shenlongHP + 3;
                                                                         }
                                                                         clearMatrix();
                                                                     }else if(matrix[1][2] == 1 && matrix[2][2] == 1 && matrix[3][2] == 1){
                                                                         SandRock.state = SandRock.State.ATTK;
                                                                         checkMulti2 = true;
                                                                         clearMatrix();

                                                                     }else if(matrix[0][3] == 1 && matrix[1][3] == 1 && matrix[2][3] == 1){
                                                                         switch (HealthGauge.state) {
                                                                             case DEC1: HealthGauge.state = HealthGauge.State.FULL;
                                                                                 break;
                                                                             case DEC2: HealthGauge.state = HealthGauge.State.DEC1;
                                                                                 break;
                                                                             case DEC3: HealthGauge.state = HealthGauge.State.DEC2;
                                                                                 break;
                                                                         }
                                                                         while (shenlongHP < 10) {
                                                                             shenlongHP = shenlongHP + 3;
                                                                         }
                                                                         clearMatrix();
                                                                     }



                                                                     System.out.println(check / 16);
                                                                     System.out.println("=====================================");
                                                                 }
                                                             }



                                         );


                                         if(contact.getFixtureA().getBody() == Shenlong.body && contact.getFixtureB().getBody() == StarBeam.body      ) {

                                             StarBeam.visibleBody();
                                             impactStarBeam.add(StarBeam.body);
                                             Shenlong.body.applyForce(new Vec2(-500f, 0f), Shenlong.body.getPosition());

                                             int sum = 0;

                                             System.out.println("Hit sum = " + sum);
                                             sum++;

                                             if(checkMulti2 == false) {
                                                 switch (HealthGauge.state){
                                                     case FULL: HealthGauge.state = HealthGauge.State.DEC1;
                                                         break;
                                                     case DEC1:HealthGauge.state = HealthGauge.State.DEC2;
                                                         break;
                                                     case DEC2:HealthGauge.state = HealthGauge.State.DEC3;
                                                         break;
                                                 }
                                                 checkMulti2 = false;
                                                 shenlongHP = (shenlongHP - 3);

                                             }else {
                                                 switch (HealthGauge.state) {
                                                     case FULL: HealthGauge.state = HealthGauge.State.DEC2;
                                                         break;
                                                     case DEC1: HealthGauge.state = HealthGauge.State.DEC3;
                                                         break;
                                                 }
                                                 shenlongHP = shenlongHP - 6;
                                                 checkMulti2 = false;
                                             }



                                         }





                                         if(shenlongHP <= 0 ) {
                                             ss.push(mapSpaceScreen);
                                         }

                                     }


                                     @Override
                                     public void endContact(Contact contact) {

                                     }

                                     @Override
                                     public void preSolve(Contact contact, Manifold manifold) {

                                     }

                                     @Override
                                     public void postSolve(Contact contact, ContactImpulse contactImpulse) {

                                     }
                                 }
        );


        for(Body body : impactStarBeam) {
            world.destroyBody(body);
        }






    }




    @Override
    public void paint(Clock clock) {
        super.paint(clock);

        if (showDebugDraw) {
            debugDraw.getCanvas().clear();
            world.drawDebugData();
            debugDraw.getCanvas().setFillColor(Color.rgb(0, 255, 0));
            debugDraw.getCanvas().drawText("Shenlong HP = " + shenlongHP  , 500, 100);
            debugDraw.getCanvas().drawText("Sandrock HP = " + sandRockHP , 50, 100);

        }
    sandrock.paint(clock);


        shenlong.paint(clock);


        for(StarBeam starBeam : starBeamList){
            starBeam.paint(clock);
            layer.add(starBeam.layer());
        }

    healthGauge.paint(clock);


    }


    public void clearMatrix() {
        matrix = new int[4][4];
    }

    public static void addStarBeam(StarBeam starBeam) {
        starBeamList.add(starBeam);
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