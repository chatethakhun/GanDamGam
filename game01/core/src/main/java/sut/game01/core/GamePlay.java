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

    private final ScreenStack ss;
    private final ImageLayer bgLayer;
    private final ImageLayer tableLayer;
    private int p;
    private ArrayList<SandRock> sandRock = new ArrayList<SandRock>();
    private boolean showDebugDraw = true, checkMatrix = false;
    public static World world;
    private DebugDrawBox2D debugDraw;
    public static float M_PER_PIXEL = 1 / 26.666667f;
    private static int width = 24;
    private static int height = 18;
    private int i = 0, j = 0, g = 0, check = 0;
    public static int[][] matrix = new int[4][4];
    Shenlong shenlong;
    StarBeam starBeam;
    //ArrayList<StarBeam> starBeams = new ArrayList<StarBeam>();
    int shenlongHP = 10;
    int sandRockHP = 10;
    MapSpaceScreen mapSpaceScreen;

    private GroupLayer groupLayer = graphics().createGroupLayer();
    static ArrayList<StarBeam> starBeamList = new ArrayList<StarBeam>();



    Boolean checkContact = false;


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

        sandRock.add(0, new SandRock(world, 50, 400f));






    }

    public void wasShown() {
        super.wasShown();
        this.layer.add(bgLayer);
        this.layer.add(tableLayer);

        shenlong = new Shenlong(world, 550f, 400f);
        this.layer.add(shenlong.layer());

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

        this.layer.add(sandRock.get(0).layer());
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
            world.setDebugDraw(debugDraw);
        }


        //this.layer.add(starBeams.get(0).layer());







    }

    @Override
    public void update(final int delta) {
        super.update(delta);
        world.step(0.033f, 10, 10);


        for (int k = 0; k < j; k++) {
            sandRock.get(k).update(delta);
        }

        shenlong.update(delta);



        for(StarBeam starBeam : starBeamList) {
            groupLayer.add(starBeam.layer());
        }



        world.setContactListener(new ContactListener() {
                                     @Override
                                     public void beginContact(final Contact contact) {


                                         mouse().setListener(new Mouse.Adapter() {

                                                                 @Override
                                                                 public void onMouseDown(Mouse.ButtonEvent event) {


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


                                                                     if (matrix[0][0] == 1 && matrix[0][1] == 1 && matrix[0][2] == 1) {

                                                                         StarBeam.state = StarBeam.State.ATTK;


                                                                         checkContact = true;

                                                                         if (contact.getFixtureB().getBody() == Shenlong.body/*Body Shenlong*/) {
                                                                             shenlongHP -= 3;
                                                                         }


                                                                         // }
                                                                         //world.destroyBody(StarBeam.body);
                                                                         System.out.println(checkContact);
                                                                         SandRock.state = SandRock.State.ATTK;
                                                                         clearMatrix();

                                                                         System.out.println("HP = " + shenlongHP);
                                                                         if (shenlongHP <= 0) {
                                                                             ss.push(mapSpaceScreen);


                                                                         }
                                                                     } else if (matrix[1][0] == 1 && matrix[2][0] == 1 && matrix[3][0] == 1) {
                                                                         clearMatrix();
                                                                         shenlongHP = shenlongHP + 3;
                                                                     } else if (matrix[1][2] == 1 && matrix[2][2] == 1 && matrix[3][2] == 1) {
                                                                         StarBeam.body.applyLinearImpulse(new Vec2(200f, 0), StarBeam.body.getPosition());


                                                                         clearMatrix();
                                                                         shenlongHP = shenlongHP - 6;


                                                                         System.out.println("HP = " + shenlongHP);
                                                                         if (shenlongHP <= 0) {
                                                                             ss.push(mapSpaceScreen);
                                                                             shenlongHP = 10;

                                                                         }
                                                                     }


                                                                     System.out.println(check / 16);
                                                                     System.out.println("=====================================");


                                                                 }
                                                             }
                                         );


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


    }




    @Override
    public void paint(Clock clock) {
        super.paint(clock);

        if (showDebugDraw) {
            debugDraw.getCanvas().clear();
            world.drawDebugData();
            debugDraw.getCanvas().setFillColor(Color.rgb(0, 255, 0));
            debugDraw.getCanvas().drawText("Shenlong HP = " + shenlongHP, 500, 100);
            debugDraw.getCanvas().drawText("Sandrock HP = " + sandRockHP, 50, 100);

        }


        for (int k = 0; k < j; k++) {
            sandRock.get(k).paint(clock);

        }


        shenlong.paint(clock);


        for(StarBeam starBeam : starBeamList){
            starBeam.paint(clock);
            this.layer.add(starBeam.layer());
        }



    }


    public void clearMatrix() {
        matrix = new int[4][4];
    }

    public static void addStarBeam(StarBeam starBeam) {
        starBeamList.add(starBeam);
    }


    //public void setStarBeamVisible() {


       // }




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