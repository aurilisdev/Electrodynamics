package electrodynamics.common.block.voxelshapes;

import java.util.HashMap;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.registers.UnifiedElectrodynamicsRegister;
import net.minecraft.block.Block;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

public class VoxelShapeRegistry {
	public static HashMap<Block, VoxelShape[]> shapesHashMap = new HashMap<>();

	public static VoxelShape getShape(SubtypeMachine machine, Direction currentDirection) {
		return getShape(UnifiedElectrodynamicsRegister.getSafeBlock(machine), currentDirection);
	}

	public static VoxelShape getShape(Block block, Direction currentDirection) {
		VoxelShape shape = VoxelShapes.block();
		VoxelShape[] shapes = shapesHashMap.getOrDefault(block, new VoxelShape[6]);
		shape = shapes[currentDirection.ordinal()] == null ? shape : shapes[currentDirection.ordinal()];
		shapesHashMap.put(block, shapes);
		return shape;
	}

	private static VoxelShape rotateShape(Direction from, Direction to, VoxelShape shape) {
		VoxelShape[] buffer = new VoxelShape[] { shape, VoxelShapes.empty() };

		int times = (to.get2DDataValue() - from.get2DDataValue() + 4) % 4;
		for (int i = 0; i < times; i++) {
			buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = VoxelShapes.or(buffer[1], VoxelShapes.create(new AxisAlignedBB(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX))));
			buffer[0] = buffer[1];
			buffer[1] = VoxelShapes.empty();
		}

		return buffer[0];
	}

	public static void registerShape(SubtypeMachine machine, VoxelShape shape, Direction baseDirection) {
		registerShape(UnifiedElectrodynamicsRegister.getSafeBlock(machine), shape, baseDirection);
	}

	public static void registerShape(Block block, VoxelShape shape, Direction baseDirection) {
		VoxelShape[] shapes = new VoxelShape[6];
		for (Direction dir : Direction.values()) {
			if (dir.ordinal() > 1) {
				shapes[dir.ordinal()] = rotateShape(baseDirection, dir, shape);
			}
		}
		shapesHashMap.put(block, shapes);
	}

}
