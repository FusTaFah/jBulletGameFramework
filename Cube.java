/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jb3d;

import com.bulletphysics.collision.dispatch.CollisionFlags;
import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javax.vecmath.Vector3f;

/**
 *
 * @author mfade_000
 */
public class Cube extends RigidBody{
    
    private final float halfWidth;
    private final float halfHeight;
    private final float halfDepth;
    private Box box;
    
    public Cube(World w, float halfWidth, float halfHeight, float halfDepth, boolean isStatic, float mass, Color c){
        super(mass, new DefaultMotionState(new Transform()), new BoxShape(new Vector3f(halfWidth, halfHeight, halfDepth)));
        Transform origin = new Transform();
        origin.origin.set(0,0,0);
        this.setCenterOfMassTransform(origin);
        
        box = new Box();
        box.setWidth(halfWidth);
        BoxShape bs = (BoxShape)this.collisionShape;
        
        box.setHeight(halfHeight);
        box.setDepth(halfDepth);
        PhongMaterial x = new PhongMaterial();
        x.setDiffuseColor(c);
        box.setMaterial(x);
        
        this.halfWidth = halfWidth;
        this.halfHeight = halfHeight;
        this.halfDepth = halfDepth;
        
        if(isStatic){
            this.collisionShape.calculateLocalInertia(0, new Vector3f(0,0,0));
            this.collisionFlags = CollisionFlags.STATIC_OBJECT;
        }
        w.addBody(this);
        w.addRigidBody(this);
    }
    
    public void setPosition(Vector3f pos){
        this.translate(pos);
    }
    
    public Box getBox(){
        return box;
    }

    public float getHalfWidth() {
        return halfWidth;
    }

    public float getHalfHeight() {
        return halfHeight;
    }

    public float getHalfDepth() {
        return halfDepth;
    }

}
