package physicsEngine;

import java.util.ArrayList;
import java.util.List;
import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.StaticPlaneShape;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;

import entities.Entity;

public class Bullet {

	DbvtBroadphase broadphase;
    DefaultCollisionConfiguration collisionConfiguration;
    CollisionDispatcher dispatcher;
    SequentialImpulseConstraintSolver solver;

    DynamicsWorld dynamicsWorld = new DiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration);
	
    List<RigidBody> rigidBodies = new ArrayList<RigidBody>();
    
    RigidBody fallRigidBody;
    RigidBody fallRigidBody2;
    RigidBody playerRigidBody;
    RigidBody groundRigidBody;
    
    private float mass = 1;
    
	public void initPhysics() {
		
		broadphase = new DbvtBroadphase();
	    collisionConfiguration = new DefaultCollisionConfiguration();
	    dispatcher = new CollisionDispatcher(collisionConfiguration);
	    solver = new SequentialImpulseConstraintSolver();
	    dynamicsWorld = new DiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration);
	    dynamicsWorld.setGravity(new Vector3f(0, -20f, 0));
	    
	    CollisionShape fallShape = new BoxShape(new Vector3f(2, 2, 2));
		DefaultMotionState fallMotionState = new DefaultMotionState(new Transform(new Matrix4f(new Quat4f(0, 0, 0, 1), new Vector3f(-100, 100, -100), 1.0f)));
		Vector3f fallShapeInertia = new Vector3f(0f, 0f, 0f);
		fallShape.calculateLocalInertia(mass, fallShapeInertia); // mass ve inertia
		RigidBodyConstructionInfo fallRigidBodyCI = new RigidBodyConstructionInfo(mass, fallMotionState, fallShape, fallShapeInertia); 
		fallRigidBodyCI.restitution = .60f;
		fallRigidBodyCI.friction = .60f; // If you put this to 1 it will never slide again.
		//fallRigidBodyCI.linearDamping = .20f;
		fallRigidBodyCI.angularDamping = .35f;
		fallRigidBody = new RigidBody(fallRigidBodyCI); 
		dynamicsWorld.addRigidBody(fallRigidBody);
		rigidBodies.add(fallRigidBody);
		
		CollisionShape fallShape2 = new BoxShape(new Vector3f(2, 2, 2));
		DefaultMotionState fallMotionState2 = new DefaultMotionState(new Transform(new Matrix4f(new Quat4f(0, 0, 0, 1), new Vector3f(-100, 80, -100), 1.0f)));
		Vector3f fallShapeInertia2 = new Vector3f(0f, 0f, 0f);
		fallShape2.calculateLocalInertia(mass, fallShapeInertia2); // mass ve inertia
		RigidBodyConstructionInfo fallRigidBodyCI2 = new RigidBodyConstructionInfo(mass, fallMotionState2, fallShape2, fallShapeInertia2); 
		fallRigidBodyCI2.restitution = .60f;
		fallRigidBodyCI2.friction = .60f;
		//fallRigidBodyCI2.linearDamping = .20f;
		fallRigidBodyCI2.angularDamping = .35f;
		fallRigidBody2 = new RigidBody(fallRigidBodyCI2); 
		dynamicsWorld.addRigidBody(fallRigidBody2);
		rigidBodies.add(fallRigidBody2);
		
		System.out.println("Physics objects: " + dynamicsWorld.getNumCollisionObjects());
	}
	    
	public void addGround() {
		CollisionShape groundShape = new StaticPlaneShape(new Vector3f(0, 1, 0), 0.25f);
	    DefaultMotionState groundMotionState = new DefaultMotionState(new Transform(new Matrix4f(new Quat4f(0, 0, 0, 1), new Vector3f(0, -8, 0), 1.0f))); 
	    Vector3f groundInertia = new Vector3f(0f, 0f, 0f);
	    groundShape.calculateLocalInertia(0, groundInertia); 
	    RigidBodyConstructionInfo groundRigidBodyCI = new RigidBodyConstructionInfo(0, groundMotionState, groundShape, groundInertia); 
	    groundRigidBodyCI.restitution = 0.3f;
	    groundRigidBody = new RigidBody(groundRigidBodyCI); 
	    dynamicsWorld.addRigidBody(groundRigidBody);
	    rigidBodies.add(groundRigidBody);
	}
	
	public void addBox1(Entity entity) {
		Quat4f outRot = new Quat4f();
		Vector3f entityPosition = fallRigidBody.getWorldTransform(new Transform()).origin;
		Quat4f entityRotation   = fallRigidBody.getWorldTransform(new Transform()).getRotation(outRot);
		entity.setPositionn(entityPosition.x, entityPosition.y, entityPosition.z);
		entity.setRotation (entityRotation.x, entityRotation.y, entityRotation.z);
	}
	
	public void addBox2(Entity entity) {
		Quat4f outRot = new Quat4f();
		Vector3f entityPosition = fallRigidBody2.getWorldTransform(new Transform()).origin;
		Quat4f entityRotation   = fallRigidBody2.getWorldTransform(new Transform()).getRotation(outRot);
		entity.setPositionn(entityPosition.x, entityPosition.y, entityPosition.z);
		entity.setRotation (entityRotation.x, entityRotation.y, entityRotation.z);
	}
	
	public void updatePhysics() 
	{
		dynamicsWorld.stepSimulation(1/60.f, 10); 
	}
	
	public void cleanUpPhysics() 
	{
		rigidBodies.clear();
	}
	
	// BULLET PHYSICS NOTES \\
	
	// btHeightfieldTerrainShape     For Heightmap
	// btConvexHullShape 			 For Dynamic objects
	// BvhTriangleMeshShape			 For Static  objects
	
}
