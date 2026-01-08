package elementos;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Palo {
	    private Body body;

	    public Palo(World world, Vector2 position) {
	        BodyDef bodyDef = new BodyDef();
	        bodyDef.type = BodyDef.BodyType.StaticBody;
	        bodyDef.position.set(position);
	        body = world.createBody(bodyDef);

	        CircleShape circleShape = new CircleShape();
	        circleShape.setRadius(6); // Radio del círculo que representa el palo

	        FixtureDef fixtureDef = new FixtureDef();
	        fixtureDef.shape = circleShape;

	        body.createFixture(fixtureDef);

	        circleShape.dispose();
	    }
}
