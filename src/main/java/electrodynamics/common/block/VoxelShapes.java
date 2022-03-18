package electrodynamics.common.block;

import java.util.HashMap;

import electrodynamics.common.block.subtype.SubtypeMachine;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VoxelShapes {
	public static HashMap<SubtypeMachine, VoxelShape[]> shapesHashMap = new HashMap<>();

	public static VoxelShape getShape(SubtypeMachine machine, Direction currentDirection) {
		VoxelShape shape = Shapes.block();
		VoxelShape[] shapes = shapesHashMap.getOrDefault(machine, new VoxelShape[4]);
		shape = shapes[currentDirection.ordinal()] == null ? shape : shapes[currentDirection.ordinal()];
		shapesHashMap.put(machine, shapes);
		return shape;
	}

	private static VoxelShape rotateShape(Direction from, Direction to, VoxelShape shape) {
		VoxelShape[] buffer = new VoxelShape[] { shape, Shapes.empty() };

		int times = (to.get2DDataValue() - from.get2DDataValue() + 4) % 4;
		for (int i = 0; i < times; i++) {
			buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = Shapes.or(buffer[1], Shapes.create(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX)));
			buffer[0] = buffer[1];
			buffer[1] = Shapes.empty();
		}

		return buffer[0];
	}

	public static void registerShape(SubtypeMachine machine, VoxelShape shape, Direction baseDirection) {
		VoxelShape[] shapes = new VoxelShape[6];
		for (Direction dir : Direction.values()) {
			if (dir.ordinal() > 1) {
				shapes[dir.ordinal()] = rotateShape(baseDirection, dir, shape);
			}
		}
		shapesHashMap.put(machine, shapes);
	}

}
