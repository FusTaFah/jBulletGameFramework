/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jb3d;

import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import java.util.ArrayList;
import javax.vecmath.Vector3f;

/**
 *
 * @author mfade_000
 */
public class World extends DiscreteDynamicsWorld{
    
    private ArrayList<Cube> bodyList;
    
    public World(Vector3f gravity){
        super(new CollisionDispatcher(new DefaultCollisionConfiguration()), new DbvtBroadphase(), new SequentialImpulseConstraintSolver(), new DefaultCollisionConfiguration());
        bodyList = new ArrayList();
        this.setGravity(gravity);
    }
    
    public void addBody(Cube r){
        bodyList.add(r);
    }
    
    public ArrayList<Cube> getBodies(){
        return bodyList;
    }
}
