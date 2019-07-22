package physica.api.core.misc;

import physica.api.core.abstraction.FaceDirection;

public interface IRotatable {

	FaceDirection getFacing();

	void setFacing(FaceDirection facing);
}
