/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jb3d;

import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.shape.Box;
import javafx.stage.Stage;
import javafx.util.Duration;
import com.bulletphysics.dynamics.*;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.bulletphysics.linearmath.Transform;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import static javafx.scene.input.KeyCode.W;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape3D;
import javax.vecmath.Vector3f;

/**
 *
 * @author mfade_000
 */
public class JB3D extends Application{
    
    private Box b;
    private Box grnd;
    private World dw;
    private final PerspectiveCamera camera = new PerspectiveCamera(true);
    private final Xform cameraXform = new Xform();
    private final Xform cameraXform2 = new Xform();
    private final Xform cameraXform3 = new Xform();
    private boolean wPressed        = false;
    private boolean dPressed        = false;
    private boolean sPressed        = false;
    private boolean aPressed        = false;
    private boolean iPressed       = false;
    private boolean lPressed     = false;
    private boolean kPressed     = false;
    private boolean jPressed    = false;
    private Point3D northOfCamera;
    private boolean spacePressed = false;
    private Group root;
    
    
    
    @Override
    public void start(Stage primaryStage) {
        
        //create the world
        dw = new World(new Vector3f(0, -10, 0));
        
        //create the bodies in the world
        int width = 5;
        Cube rb = new Cube(dw, width, width, width, false, 1, javafx.scene.paint.Color.GREEN);
        rb.translate(new Vector3f(0, 20, 70));
        
        
        //create the ground
        
        for(int i = 0; i < 20; i++){
            for(int j = 0; j < 20; j++){
                Cube ground = new Cube(dw, width, width, width, true, 0, javafx.scene.paint.Color.PURPLE);
                ground.translate(new Vector3f(20-j*width, -10, 70-i*width));
            }
        }
        
        root = new Group();
        buildCamera();
        camera.setFieldOfView(55);
        
        //add the graphical objects to the group
        for(Cube c : dw.getBodies()){
            root.getChildren().add(c.getBox());
        }
        javafx.scene.PointLight p = new javafx.scene.PointLight(Color.WHITE);
        p.setTranslateX(0);
        p.setTranslateY(20);
        p.setTranslateZ(70);
//        p.
        
        //add the group to the scene
        Scene scene = new Scene(root, 700, 500, true);
        
        //handle the inputs
        handleKeyboard(scene);
//        handleMouse(scene);
        primaryStage.setTitle("Hello World!");
        
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.setCamera(camera);
        
        
        final Duration d = Duration.millis(1000/60);
        final KeyFrame f = new KeyFrame(d, new EventHandler(){
            int x = 0;
            @Override
            public void handle(Event e) {
                dw.stepSimulation((float)d.toMillis());
                rb.activate();
                keyControls(rb, new Vector3f());
                for(Cube body : dw.getBodies()){
                    render(body.getBox(), body);
                }
                System.out.println(wPressed);
                System.out.println(dPressed);
                System.out.println(sPressed);
                System.out.println(aPressed);
                System.out.println(wPressed);
                System.out.println(spacePressed);
                System.out.println(iPressed);
                System.out.println(lPressed);
                System.out.println(kPressed);
                System.out.println(jPressed);
                System.out.println();
//                Transform t = new Transform();
//                Vector3f v3f1 = new Vector3f();
//                Vector3f v3f2 = new Vector3f();
//                rb.getCollisionShape().getAabb(t, v3f1, v3f2);
//                System.out.println("Position: x="+t.origin.x+" y="+t.origin.y+" z="+t.origin.z);
//                System.out.println("Min: x="+v3f1.x+" y="+v3f1.y+" z="+v3f1.z);
//                System.out.println("Max: x="+v3f2.x+" y="+v3f2.y+" z="+v3f2.z+'\n');
                
            }
        });
        
        TimelineBuilder.
        create().
        cycleCount(Animation.INDEFINITE).
        keyFrames(f).
        build().
        play();
    }
    
    private void render(Shape3D s, RigidBody r){
        s.setTranslateX(r.getCenterOfMassPosition(new Vector3f()).x);
        s.setTranslateY(r.getCenterOfMassPosition(new Vector3f()).y);
        s.setTranslateZ(r.getCenterOfMassPosition(new Vector3f()).z);
    }
    
    private void buildCamera() {
        root.getChildren().add(cameraXform);
        cameraXform.getChildren().add(cameraXform2);
        cameraXform2.getChildren().add(cameraXform3);
        cameraXform3.getChildren().add(camera);
        cameraXform3.setRotateZ(180.0);
        camera.setFarClip(300);
    }
    
    private void keyControls(RigidBody rb, Vector3f v){
        Vector3f rbSpeed = rb.getLinearVelocity(v);
        
        if(wPressed) rb.setLinearVelocity(new Vector3f(rbSpeed.x, rbSpeed.y, 10));
        else if(dPressed) rb.setLinearVelocity(new Vector3f(-10, rbSpeed.y, rbSpeed.z));
        else if(sPressed) rb.setLinearVelocity(new Vector3f(rbSpeed.x, rbSpeed.y, -10));
        else if(aPressed) rb.setLinearVelocity(new Vector3f(10, rbSpeed.y, rbSpeed.z));
        else if(spacePressed) rb.setLinearVelocity(new Vector3f(rbSpeed.x, 10, rbSpeed.z));
        else if(!wPressed && !dPressed && !sPressed && !aPressed && !spacePressed)
            rb.setLinearVelocity(new Vector3f(0, rbSpeed.y, 0));
        if(jPressed){
            camera.setRotationAxis(new Point3D(0, 1, 0));
            camera.setRotate(camera.getRotate()-1);
        }else if(lPressed){
            camera.setRotationAxis(new Point3D(0, 1, 0));
            camera.setRotate(camera.getRotate()+1);
        }
    }
    
    private void handleKeyboard(Scene scene){
        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent k){
                switch (k.getCode()){
                    case W: wPressed = true;
                        break;
                    case D: dPressed = true;
                        break;
                    case S: sPressed = true;
                        break;
                    case A: aPressed = true;
                        break;
                    case SPACE: spacePressed = true;
                        break;
                    case I: iPressed = true;
                        break;
                    case L: lPressed = true;
                        break;
                    case K: kPressed = true;
                        break;
                    case J: jPressed = true;
                        break;
                }
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent k){
                switch (k.getCode()){
                    case W: wPressed = false;
                        break;
                    case D: dPressed = false;
                        break;
                    case S: sPressed = false;
                        break;
                    case A: aPressed = false;
                        break;
                    case SPACE: spacePressed = false;
                        break;
                    case I: iPressed = false;
                        break;
                    case L: lPressed = false;
                        break;
                    case K: kPressed = false;
                        break;
                    case J: jPressed = false;
                        break;
                }
            }
        });
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
