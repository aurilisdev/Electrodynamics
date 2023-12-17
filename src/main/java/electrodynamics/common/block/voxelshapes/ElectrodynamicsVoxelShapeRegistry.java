package electrodynamics.common.block.voxelshapes;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.registers.ElectrodynamicsBlocks;
import net.minecraft.block.Block;
import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

public class ElectrodynamicsVoxelShapeRegistry {

	/**
	 * By convention all blocks in this method will be kept in alphabetical order
	 * 
	 */
	public static void init() {

		/* ADVANCED SOLAR PANEL */

		VoxelShape advancedsolarpanel = Block.box(0, 0, 0, 16, 2, 16);
		advancedsolarpanel = VoxelShapes.join(advancedsolarpanel, Block.box(2, 2, 2, 14, 3, 14), IBooleanFunction.OR);
		advancedsolarpanel = VoxelShapes.join(advancedsolarpanel, Block.box(3, 3, 3, 13, 7, 13), IBooleanFunction.OR);
		advancedsolarpanel = VoxelShapes.join(advancedsolarpanel, Block.box(6, 7, 6, 10, 16, 10), IBooleanFunction.OR);

		VoxelShapeRegistry.registerShape(SubtypeMachine.advancedsolarpanel, advancedsolarpanel, Direction.NORTH);

		/* CHEMICAL CRYSTALLIZER */

		VoxelShape chemicalcrystallizer = Block.box(0, 0, 0, 16, 5, 16);

		chemicalcrystallizer = VoxelShapes.or(chemicalcrystallizer, Block.box(0, 5, 0, 2, 6, 16));
		chemicalcrystallizer = VoxelShapes.or(chemicalcrystallizer, Block.box(2, 5, 0, 4, 6, 2));
		chemicalcrystallizer = VoxelShapes.or(chemicalcrystallizer, Block.box(2, 5, 14, 4, 6, 16));

		chemicalcrystallizer = VoxelShapes.or(chemicalcrystallizer, Block.box(4, 5, 0, 12, 12, 1));
		chemicalcrystallizer = VoxelShapes.or(chemicalcrystallizer, Block.box(4, 5, 15, 12, 12, 16));

		chemicalcrystallizer = VoxelShapes.or(chemicalcrystallizer, Block.box(12, 5, 0, 13, 6, 2));
		chemicalcrystallizer = VoxelShapes.or(chemicalcrystallizer, Block.box(12, 5, 14, 13, 6, 16));

		chemicalcrystallizer = VoxelShapes.or(chemicalcrystallizer, Block.box(13, 5, 0, 16, 16, 2));
		chemicalcrystallizer = VoxelShapes.or(chemicalcrystallizer, Block.box(13, 14, 2, 16, 16, 14));
		chemicalcrystallizer = VoxelShapes.or(chemicalcrystallizer, Block.box(13, 5, 14, 16, 16, 16));

		chemicalcrystallizer = VoxelShapes.or(chemicalcrystallizer, Block.box(15, 5, 4, 16, 12, 12));
		chemicalcrystallizer = VoxelShapes.or(chemicalcrystallizer, Block.box(14, 5, 2, 15, 14, 14));

		VoxelShapeRegistry.registerShape(SubtypeMachine.chemicalcrystallizer, chemicalcrystallizer, Direction.WEST);

		/* CIRCUIT BREAKER */

		VoxelShape circuitbreaker = Block.box(0, 0, 0, 16, 2, 16);
		circuitbreaker = VoxelShapes.or(circuitbreaker, Block.box(0, 2, 1, 16, 5, 15));
		circuitbreaker = VoxelShapes.or(circuitbreaker, Block.box(0, 2, 1, 16, 5, 15));
		circuitbreaker = VoxelShapes.or(circuitbreaker, Block.box(1, 5, 2, 15, 15, 14));
		circuitbreaker = VoxelShapes.or(circuitbreaker, Block.box(0, 5, 4, 1, 12, 12));
		circuitbreaker = VoxelShapes.or(circuitbreaker, Block.box(15, 5, 4, 16, 12, 12));

		VoxelShapeRegistry.registerShape(SubtypeMachine.circuitbreaker, circuitbreaker, Direction.WEST);

		/* COMBUSTION CHAMBER */

		VoxelShape combustionchamber = Block.box(1, 0, 0, 15, 1, 16);

		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(2, 1, 0, 3, 12, 1));
		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(13, 1, 0, 14, 12, 1));
		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(3, 12, 0, 13, 13, 1));
		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(4, 3, 0, 12, 11, 1));

		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(2, 12, 1, 3, 13, 15));
		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(3, 1, 1, 13, 12, 15));
		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(13, 12, 1, 14, 13, 15));

		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(2, 1, 15, 3, 12, 16));
		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(13, 1, 15, 14, 12, 16));
		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(3, 12, 15, 13, 13, 16));
		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(4, 3, 15, 12, 11, 16));

		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(2, 3, 1, 3, 4, 2));
		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(1, 4, 1, 2, 10, 2));
		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(2, 10, 1, 3, 11, 2));
		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(2, 3, 7, 3, 4, 9));
		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(1, 4, 7, 2, 10, 9));
		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(2, 10, 7, 3, 11, 9));
		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(2, 3, 14, 3, 4, 15));
		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(1, 4, 14, 2, 10, 15));
		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(2, 10, 14, 3, 11, 15));

		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(13, 3, 1, 14, 4, 2));
		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(14, 4, 1, 15, 10, 2));
		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(13, 10, 1, 14, 11, 2));
		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(13, 3, 7, 14, 4, 9));
		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(14, 4, 7, 15, 10, 9));
		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(13, 10, 7, 14, 11, 9));
		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(13, 3, 14, 14, 4, 15));
		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(14, 4, 14, 15, 10, 15));
		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(13, 10, 14, 14, 11, 15));

		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(4, 12, 4, 5, 13, 5));
		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(4, 12, 11, 5, 13, 12));

		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(7, 12, 2, 9, 14, 3));
		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(7, 14, 3, 9, 15, 4));
		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(5, 12, 4, 11, 15, 12));
		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(7, 12, 13, 9, 14, 14));
		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(7, 14, 12, 9, 15, 13));

		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(11, 12, 4, 12, 13, 5));
		combustionchamber = VoxelShapes.or(combustionchamber, Block.box(11, 12, 11, 12, 13, 12));

		VoxelShapeRegistry.registerShape(SubtypeMachine.combustionchamber, combustionchamber, Direction.WEST);

		/* DOWNGRADE TRANFORMER */

		VoxelShape downgradetransformer = VoxelShapes.empty();
		downgradetransformer = VoxelShapes.join(downgradetransformer, VoxelShapes.box(0, 0, 0, 1, 0.125, 1), IBooleanFunction.OR);
		downgradetransformer = VoxelShapes.join(downgradetransformer, VoxelShapes.box(0, 0.3125, 0.25, 0.0625, 0.75, 0.75), IBooleanFunction.OR);
		downgradetransformer = VoxelShapes.join(downgradetransformer, VoxelShapes.box(0, 0.125, 0.0625, 1, 0.3125, 0.9375), IBooleanFunction.OR);
		downgradetransformer = VoxelShapes.join(downgradetransformer, VoxelShapes.box(0.15625, 0.875, 0.625, 0.84375, 0.9375, 0.8125), IBooleanFunction.OR);
		downgradetransformer = VoxelShapes.join(downgradetransformer, VoxelShapes.box(0.15625, 0.875, 0.1875, 0.84375, 0.9375, 0.375), IBooleanFunction.OR);
		downgradetransformer = VoxelShapes.join(downgradetransformer, VoxelShapes.box(0.625, 0.6875, 0.15625, 0.875, 0.78125, 0.40625), IBooleanFunction.OR);
		downgradetransformer = VoxelShapes.join(downgradetransformer, VoxelShapes.box(0.625, 0.6875, 0.59375, 0.875, 0.78125, 0.84375), IBooleanFunction.OR);
		downgradetransformer = VoxelShapes.join(downgradetransformer, VoxelShapes.box(0.125, 0.6875, 0.59375, 0.375, 0.84375, 0.84375), IBooleanFunction.OR);
		downgradetransformer = VoxelShapes.join(downgradetransformer, VoxelShapes.box(0.125, 0.6875, 0.15625, 0.375, 0.84375, 0.40625), IBooleanFunction.OR);
		downgradetransformer = VoxelShapes.join(downgradetransformer, VoxelShapes.box(0.125, 0.375, 0.59375, 0.375, 0.53125, 0.84375), IBooleanFunction.OR);
		downgradetransformer = VoxelShapes.join(downgradetransformer, VoxelShapes.box(0.125, 0.375, 0.15625, 0.375, 0.53125, 0.40625), IBooleanFunction.OR);
		downgradetransformer = VoxelShapes.join(downgradetransformer, VoxelShapes.box(0.625, 0.4375, 0.15625, 0.875, 0.53125, 0.40625), IBooleanFunction.OR);
		downgradetransformer = VoxelShapes.join(downgradetransformer, VoxelShapes.box(0.625, 0.4375, 0.59375, 0.875, 0.53125, 0.84375), IBooleanFunction.OR);
		downgradetransformer = VoxelShapes.join(downgradetransformer, VoxelShapes.box(0.640625, 0.53125, 0.171875, 0.859375, 0.6875, 0.390625), IBooleanFunction.OR);
		downgradetransformer = VoxelShapes.join(downgradetransformer, VoxelShapes.box(0.640625, 0.53125, 0.609375, 0.859375, 0.6875, 0.828125), IBooleanFunction.OR);
		downgradetransformer = VoxelShapes.join(downgradetransformer, VoxelShapes.box(0.140625, 0.53125, 0.609375, 0.359375, 0.6875, 0.828125), IBooleanFunction.OR);
		downgradetransformer = VoxelShapes.join(downgradetransformer, VoxelShapes.box(0.140625, 0.53125, 0.171875, 0.359375, 0.6875, 0.390625), IBooleanFunction.OR);
		downgradetransformer = VoxelShapes.join(downgradetransformer, VoxelShapes.box(0.15625, 0.3125, 0.625, 0.34375, 0.875, 0.8125), IBooleanFunction.OR);
		downgradetransformer = VoxelShapes.join(downgradetransformer, VoxelShapes.box(0.15625, 0.3125, 0.1875, 0.34375, 0.875, 0.375), IBooleanFunction.OR);
		downgradetransformer = VoxelShapes.join(downgradetransformer, VoxelShapes.box(0.65625, 0.3125, 0.625, 0.84375, 0.875, 0.8125), IBooleanFunction.OR);
		downgradetransformer = VoxelShapes.join(downgradetransformer, VoxelShapes.box(0.65625, 0.3125, 0.1875, 0.84375, 0.875, 0.375), IBooleanFunction.OR);
		downgradetransformer = VoxelShapes.join(downgradetransformer, VoxelShapes.box(0.9375, 0.3125, 0.25, 1, 0.75, 0.75), IBooleanFunction.OR);
		VoxelShapeRegistry.registerShape(SubtypeMachine.downgradetransformer, downgradetransformer, Direction.EAST);

		/* ELECTRIC PUMP */

		VoxelShape electricpump = VoxelShapes.empty();
		electricpump = VoxelShapes.join(electricpump, VoxelShapes.box(0.125, 0.5625, 0.375, 0.9375, 0.75, 0.6875), IBooleanFunction.OR);
		electricpump = VoxelShapes.join(electricpump, VoxelShapes.box(0.28125, 0.125, 0.375, 0.875, 0.5625, 0.6875), IBooleanFunction.OR);
		electricpump = VoxelShapes.join(electricpump, VoxelShapes.box(0.375, 0.0625, 0.3125, 0.8125, 0.8125, 0.8125), IBooleanFunction.OR);
		electricpump = VoxelShapes.join(electricpump, VoxelShapes.box(0.5, 0.125, 0.125, 0.6875, 0.1875, 0.3125), IBooleanFunction.OR);
		electricpump = VoxelShapes.join(electricpump, VoxelShapes.box(0.4375, 0.375, 0.25, 0.75, 0.6875, 0.3125), IBooleanFunction.OR);
		electricpump = VoxelShapes.join(electricpump, VoxelShapes.box(0.3125, 0.3125, 0.6875, 0.6875, 0.6875, 1), IBooleanFunction.OR);
		electricpump = VoxelShapes.join(electricpump, VoxelShapes.box(0.53125, 0.1875, 0.125, 0.65625, 0.625, 0.1875), IBooleanFunction.OR);
		electricpump = VoxelShapes.join(electricpump, VoxelShapes.box(0.0625, 0.625, 0.3125, 0.3125, 0.8125, 0.75), IBooleanFunction.OR);
		electricpump = VoxelShapes.join(electricpump, VoxelShapes.box(0.1328125, 0.8125, 0.25, 0.2734375, 0.859375, 0.3125), IBooleanFunction.OR);
		electricpump = VoxelShapes.join(electricpump, VoxelShapes.box(0.5, 0.6875, 0.1875, 0.6875, 0.75, 0.25), IBooleanFunction.OR);
		electricpump = VoxelShapes.join(electricpump, VoxelShapes.box(0.14453125, 0.66015625, 0.25, 0.26171875, 0.77734375, 0.3125), IBooleanFunction.OR);
		electricpump = VoxelShapes.join(electricpump, VoxelShapes.box(0.515625, 0.484375, 0.1875, 0.671875, 0.640625, 0.25), IBooleanFunction.OR);
		electricpump = VoxelShapes.join(electricpump, VoxelShapes.box(0.1328125, 0.8125, 0.25, 0.2734375, 0.859375, 0.3125), IBooleanFunction.OR);
		electricpump = VoxelShapes.join(electricpump, VoxelShapes.box(0.5, 0.6875, 0.1875, 0.6875, 0.75, 0.25), IBooleanFunction.OR);
		electricpump = VoxelShapes.join(electricpump, VoxelShapes.box(0.0625, 0.6484375, 0.25, 0.109375, 0.7890625, 0.3125), IBooleanFunction.OR);
		electricpump = VoxelShapes.join(electricpump, VoxelShapes.box(0.40625, 0.46875, 0.1875, 0.46875, 0.65625, 0.25), IBooleanFunction.OR);
		electricpump = VoxelShapes.join(electricpump, VoxelShapes.box(0.0625, 0.6484375, 0.25, 0.109375, 0.7890625, 0.3125), IBooleanFunction.OR);
		electricpump = VoxelShapes.join(electricpump, VoxelShapes.box(0.40625, 0.46875, 0.1875, 0.46875, 0.65625, 0.25), IBooleanFunction.OR);
		electricpump = VoxelShapes.join(electricpump, VoxelShapes.box(0.1328125, 0.578125, 0.25, 0.2734375, 0.625, 0.3125), IBooleanFunction.OR);
		electricpump = VoxelShapes.join(electricpump, VoxelShapes.box(0.5, 0.375, 0.1875, 0.6875, 0.4375, 0.25), IBooleanFunction.OR);
		electricpump = VoxelShapes.join(electricpump, VoxelShapes.box(0.1328125, 0.578125, 0.25, 0.2734375, 0.625, 0.3125), IBooleanFunction.OR);
		electricpump = VoxelShapes.join(electricpump, VoxelShapes.box(0.5, 0.375, 0.1875, 0.6875, 0.4375, 0.25), IBooleanFunction.OR);
		electricpump = VoxelShapes.join(electricpump, VoxelShapes.box(0.296875, 0.6484375, 0.25, 0.34375, 0.7890625, 0.3125), IBooleanFunction.OR);
		electricpump = VoxelShapes.join(electricpump, VoxelShapes.box(0.71875, 0.46875, 0.1875, 0.78125, 0.65625, 0.25), IBooleanFunction.OR);
		electricpump = VoxelShapes.join(electricpump, VoxelShapes.box(0.296875, 0.6484375, 0.25, 0.34375, 0.7890625, 0.3125), IBooleanFunction.OR);
		electricpump = VoxelShapes.join(electricpump, VoxelShapes.box(0.71875, 0.46875, 0.1875, 0.78125, 0.65625, 0.25), IBooleanFunction.OR);
		electricpump = VoxelShapes.join(electricpump, VoxelShapes.box(0.375, 0.8125, 0.375, 0.6875, 0.875, 0.6875), IBooleanFunction.OR);
		electricpump = VoxelShapes.join(electricpump, VoxelShapes.box(0.4375, 0, 0.375, 0.75, 0.0625, 0.6875), IBooleanFunction.OR);
		electricpump = VoxelShapes.join(electricpump, VoxelShapes.box(0.3125, 0.875, 0.3125, 0.6875, 1.0, 0.6875), IBooleanFunction.OR);
		VoxelShapeRegistry.registerShape(SubtypeMachine.electricpump, electricpump, Direction.EAST);

		/* FERMENTATION PLANT */

		VoxelShape fermentationplant = Block.box(1, 0, 0, 15, 5, 16);

		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(5, 5, 0, 11, 11, 1));
		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(5, 5, 15, 11, 11, 16));

		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(5, 5, 1, 11, 8, 8));

		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(3, 5, 2, 4, 8, 3));
		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(3, 7, 3, 4, 8, 6));
		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(3, 5, 6, 4, 8, 7));
		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(12, 5, 2, 13, 8, 3));
		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(12, 7, 3, 13, 8, 6));
		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(12, 5, 6, 13, 8, 7));

		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(4, 8, 2, 5, 9, 3));
		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(4, 8, 4, 5, 9, 5));
		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(4, 8, 6, 5, 9, 7));
		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(11, 8, 2, 12, 9, 3));
		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(11, 8, 4, 12, 9, 5));
		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(11, 8, 6, 12, 9, 7));

		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(1, 5, 4, 2, 8, 5));
		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(2, 7, 4, 3, 8, 5));
		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(14, 5, 4, 15, 8, 5));
		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(13, 7, 4, 14, 8, 5));

		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(2, 5, 11, 3, 8, 12));
		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(3, 7, 11, 4, 8, 12));
		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(13, 5, 11, 14, 8, 12));
		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(12, 7, 11, 13, 8, 12));

		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(4, 5, 9, 5, 8, 10));
		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(4, 7, 10, 5, 8, 13));
		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(4, 5, 13, 5, 8, 14));
		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(11, 5, 9, 12, 8, 10));
		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(11, 7, 10, 12, 8, 13));
		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(11, 5, 13, 12, 8, 14));

		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(4, 8, 10, 5, 14, 11));
		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(5, 13, 10, 6, 14, 11));
		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(4, 8, 12, 5, 14, 13));
		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(5, 13, 12, 6, 14, 13));
		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(11, 8, 10, 12, 14, 11));
		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(10, 13, 10, 11, 14, 11));
		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(11, 8, 12, 12, 14, 13));
		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(10, 13, 12, 11, 14, 13));

		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(6, 8, 10, 10, 15, 13));
		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(6, 5, 10, 9, 6, 13));
		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(7, 6, 11, 8, 7, 12));

		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(7.5, 9, 9, 8.5, 10, 10));
		fermentationplant = VoxelShapes.or(fermentationplant, Block.box(7.5, 7, 8, 8.5, 9, 9));

		VoxelShapeRegistry.registerShape(ElectrodynamicsBlocks.getBlock(SubtypeMachine.fermentationplant), fermentationplant, Direction.WEST);

		/* HV CHARGER */

		VoxelShape hvcharger = VoxelShapes.empty();
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0, 0, 0, 1, 0.1875, 1), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0, 0.1875, 0.25, 0.125, 0.8125, 0.75), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.1875, 0.45, 0.75, 0.3125, 0.5125, 0.875), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.21875, 0.5, 0.84375, 0.28125, 0.5625, 0.90625), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.21875, 0.5, 0.71875, 0.28125, 0.5625, 0.78125), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.5, 0.45, 0.75, 0.625, 0.5125, 0.875), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.53125, 0.5, 0.84375, 0.59375, 0.5625, 0.90625), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.53125, 0.5, 0.71875, 0.59375, 0.5625, 0.78125), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.15625, 0.1875, 0.09375, 0.34375, 0.5, 0.28125), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.46875, 0.1875, 0.09375, 0.65625, 0.5, 0.28125), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.46875, 0.1875, 0.71875, 0.65625, 0.5, 0.90625), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.15625, 0.1875, 0.71875, 0.34375, 0.5, 0.90625), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.1875, 0.45, 0.125, 0.3125, 0.5125, 0.25), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.21875, 0.5, 0.21875, 0.28125, 0.5625, 0.28125), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.21875, 0.5, 0.09375, 0.28125, 0.5625, 0.15625), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.5, 0.45, 0.125, 0.625, 0.5125, 0.25), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.53125, 0.5, 0.21875, 0.59375, 0.5625, 0.28125), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.53125, 0.5, 0.09375, 0.59375, 0.5625, 0.15625), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.375, 0.1875, 0.375, 0.625, 0.5625, 0.625), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.4375, 0.5625, 0.40625, 0.5625, 0.9375, 0.46875), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.4375, 0.5625, 0.53125, 0.5625, 0.9375, 0.59375), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.375, 0.9375, 0.25, 0.6875, 1, 0.75), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.46875, 0.875, 0.3125, 0.53125, 0.9375, 0.4375), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.46875, 0.875, 0.5625, 0.53125, 0.9375, 0.6875), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.375, 1, 0.4375, 0.4375, 1.125, 0.5625), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.125, 0.4375, 0.375, 0.3125, 0.6875, 0.625), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.1875, 0.5625, 0.21875, 0.25, 0.6265625, 0.40625), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.21875, 0.5625, 0.21718749999999998, 0.59375, 0.625, 0.2796875), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.21875, 0.5625, 0.7203125, 0.59375, 0.625, 0.7828125), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.1875, 0.5625, 0.59375, 0.25, 0.6265625, 0.78125), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.21875, 0.5625, 0.84375, 0.78125, 0.625, 0.90625), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.71875, 0.1875, 0.84375, 0.78125, 0.5625, 0.90625), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.78125, 0.1875, 0.71875, 0.84375, 0.25, 0.90625), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.78125, 0.25, 0.65625, 0.84375, 0.3125, 0.78125), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.21875, 0.5625, 0.09375, 0.78125, 0.625, 0.15625), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.71875, 0.1875, 0.09375, 0.78125, 0.5625, 0.15625), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.78125, 0.1875, 0.09375, 0.84375, 0.25, 0.28125), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.78125, 0.25, 0.21875, 0.84375, 0.3125, 0.34375), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.71875, 0.1875, 0.34375, 0.78125, 0.25, 0.65625), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.71875, 0.3125, 0.34375, 0.78125, 0.375, 0.65625), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.84375, 0.1875, 0.34375, 0.90625, 0.25, 0.65625), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.84375, 0.3125, 0.34375, 0.90625, 0.375, 0.65625), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.78125, 0.1875, 0.34375, 0.84375, 0.25, 0.40625), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.78125, 0.3125, 0.34375, 0.84375, 0.375, 0.46875), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.78125, 0.1875, 0.59375, 0.84375, 0.25, 0.65625), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.78125, 0.3125, 0.53125, 0.84375, 0.375, 0.65625), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.71875, 0.25, 0.59375, 0.78125, 0.3125, 0.65625), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.84375, 0.25, 0.53125, 0.90625, 0.3125, 0.65625), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.71875, 0.25, 0.34375, 0.78125, 0.3125, 0.40625), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.84375, 0.25, 0.34375, 0.90625, 0.3125, 0.46875), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.78125, 0.375, 0.46875, 0.84375, 0.5625, 0.53125), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.75, 0.53125, 0.46875, 0.8125, 0.59375, 0.53125), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.71875, 0.5625, 0.46875, 0.78125, 0.625, 0.53125), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.65625, 0.625, 0.46875, 0.71875, 0.6875, 0.53125), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.6875, 0.59375, 0.46875, 0.75, 0.65625, 0.53125), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.4375, 0.625, 0.46875, 0.6875, 0.6875, 0.53125), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.40625, 0.65625, 0.46875, 0.46875, 0.71875, 0.53125), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.34375, 0.71875, 0.46875, 0.40625, 0.78125, 0.53125), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.375, 0.6875, 0.46875, 0.4375, 0.75, 0.53125), IBooleanFunction.OR);
		hvcharger = VoxelShapes.join(hvcharger, VoxelShapes.box(0.3125, 0.75, 0.46875, 0.375, 1.0625, 0.53125), IBooleanFunction.OR);
		VoxelShapeRegistry.registerShape(SubtypeMachine.chargerhv, hvcharger, Direction.EAST);

		/* HYDROELECTRIC GENERATOR */

		VoxelShape hydroelectricgenerator = VoxelShapes.empty();
		hydroelectricgenerator = VoxelShapes.join(hydroelectricgenerator, VoxelShapes.box(0.06, 0.25, 0.250625, 0.9975, 0.375, 0.750625), IBooleanFunction.OR);
		hydroelectricgenerator = VoxelShapes.join(hydroelectricgenerator, VoxelShapes.box(0.06, 0, 0.09125, 0.9975, 0.25, 0.90375), IBooleanFunction.OR);
		hydroelectricgenerator = VoxelShapes.join(hydroelectricgenerator, VoxelShapes.box(0.56, 0.375, 0.438125, 0.9975, 0.5, 0.563125), IBooleanFunction.OR);
		hydroelectricgenerator = VoxelShapes.join(hydroelectricgenerator, VoxelShapes.box(0.685, 0.375, 0.375625, 0.935, 0.5625, 0.625625), IBooleanFunction.OR);
		hydroelectricgenerator = VoxelShapes.join(hydroelectricgenerator, VoxelShapes.box(0.8725, 0.317394375, 0.33602125, 0.935, 0.379894375, 0.39852125), IBooleanFunction.OR);
		hydroelectricgenerator = VoxelShapes.join(hydroelectricgenerator, VoxelShapes.box(0.685, 0.317394375, 0.33602125, 0.7475, 0.379894375, 0.39852125), IBooleanFunction.OR);
		hydroelectricgenerator = VoxelShapes.join(hydroelectricgenerator, VoxelShapes.box(0.685, 0.317394375, 0.60272875, 0.7475, 0.379894375, 0.66522875), IBooleanFunction.OR);
		hydroelectricgenerator = VoxelShapes.join(hydroelectricgenerator, VoxelShapes.box(0.8725, 0.317394375, 0.60272875, 0.935, 0.379894375, 0.66522875), IBooleanFunction.OR);
		hydroelectricgenerator = VoxelShapes.join(hydroelectricgenerator, VoxelShapes.box(0.06, 0.375, 0.250625, 0.4975, 0.75, 0.750625), IBooleanFunction.OR);
		hydroelectricgenerator = VoxelShapes.join(hydroelectricgenerator, VoxelShapes.box(-0.0025, 0.25, 0.250625, 0.06, 0.75, 0.750625), IBooleanFunction.OR);
		hydroelectricgenerator = VoxelShapes.join(hydroelectricgenerator, VoxelShapes.box(0.4975, 0.375, 0.375625, 0.56, 0.5625, 0.625625), IBooleanFunction.OR);
		hydroelectricgenerator = VoxelShapes.join(hydroelectricgenerator, VoxelShapes.box(0.4975, 0.375, 0.313125, 0.56, 0.4375, 0.375625), IBooleanFunction.OR);
		hydroelectricgenerator = VoxelShapes.join(hydroelectricgenerator, VoxelShapes.box(0.4975, 0.375, 0.625625, 0.56, 0.4375, 0.688125), IBooleanFunction.OR);
		VoxelShapeRegistry.registerShape(SubtypeMachine.hydroelectricgenerator, hydroelectricgenerator, Direction.EAST);

		/* LATHE */

		VoxelShape lathe = VoxelShapes.empty();
		lathe = VoxelShapes.join(lathe, VoxelShapes.box(0, 0, 0, 1, 0.1875, 1), IBooleanFunction.OR);
		lathe = VoxelShapes.join(lathe, VoxelShapes.box(0, 0.1875, 0.25, 0.125, 0.75, 0.75), IBooleanFunction.OR);
		lathe = VoxelShapes.join(lathe, VoxelShapes.box(0.25, 0.1875, 0.1875, 0.3125, 0.9375, 0.25), IBooleanFunction.OR);
		lathe = VoxelShapes.join(lathe, VoxelShapes.box(0.1875, 0.1875, 0.25, 0.25, 0.9375, 0.3125), IBooleanFunction.OR);
		lathe = VoxelShapes.join(lathe, VoxelShapes.box(0.6875, 0.1875, 0.1875, 0.75, 0.9375, 0.25), IBooleanFunction.OR);
		lathe = VoxelShapes.join(lathe, VoxelShapes.box(0.75, 0.1875, 0.25, 0.8125, 0.9375, 0.3125), IBooleanFunction.OR);
		lathe = VoxelShapes.join(lathe, VoxelShapes.box(0.6875, 0.1875, 0.75, 0.75, 0.9375, 0.8125), IBooleanFunction.OR);
		lathe = VoxelShapes.join(lathe, VoxelShapes.box(0.75, 0.1875, 0.6875, 0.8125, 0.9375, 0.75), IBooleanFunction.OR);
		lathe = VoxelShapes.join(lathe, VoxelShapes.box(0.25, 0.1875, 0.75, 0.3125, 0.9375, 0.8125), IBooleanFunction.OR);
		lathe = VoxelShapes.join(lathe, VoxelShapes.box(0.1875, 0.1875, 0.6875, 0.25, 0.9375, 0.75), IBooleanFunction.OR);
		lathe = VoxelShapes.join(lathe, VoxelShapes.box(0.25, 0.9375, 0.25, 0.75, 1, 0.75), IBooleanFunction.OR);
		lathe = VoxelShapes.join(lathe, VoxelShapes.box(0.40625, 0.875, 0.34375, 0.59375, 0.9375, 0.65625), IBooleanFunction.OR);
		lathe = VoxelShapes.join(lathe, VoxelShapes.box(0.34375, 0.875, 0.40625, 0.40625, 0.9375, 0.59375), IBooleanFunction.OR);
		lathe = VoxelShapes.join(lathe, VoxelShapes.box(0.59375, 0.875, 0.40625, 0.65625, 0.9375, 0.59375), IBooleanFunction.OR);
		lathe = VoxelShapes.join(lathe, VoxelShapes.box(0.34375, 0.1875, 0.21875, 0.65625, 0.5625, 0.78125), IBooleanFunction.OR);
		lathe = VoxelShapes.join(lathe, VoxelShapes.box(0.21875, 0.1875, 0.34375, 0.34375, 0.5625, 0.65625), IBooleanFunction.OR);
		lathe = VoxelShapes.join(lathe, VoxelShapes.box(0.65625, 0.1875, 0.34375, 0.78125, 0.5625, 0.65625), IBooleanFunction.OR);
		lathe = VoxelShapes.join(lathe, VoxelShapes.box(0.28125, 0.1875, 0.28125, 0.34375, 0.5625, 0.34375), IBooleanFunction.OR);
		lathe = VoxelShapes.join(lathe, VoxelShapes.box(0.28125, 0.1875, 0.65625, 0.34375, 0.5625, 0.71875), IBooleanFunction.OR);
		lathe = VoxelShapes.join(lathe, VoxelShapes.box(0.65625, 0.1875, 0.28125, 0.71875, 0.5625, 0.34375), IBooleanFunction.OR);
		lathe = VoxelShapes.join(lathe, VoxelShapes.box(0.65625, 0.1875, 0.65625, 0.71875, 0.5625, 0.71875), IBooleanFunction.OR);
		lathe = VoxelShapes.join(lathe, VoxelShapes.box(0.40625, 0.5625, 0.34375, 0.59375, 0.625, 0.65625), IBooleanFunction.OR);
		lathe = VoxelShapes.join(lathe, VoxelShapes.box(0.34375, 0.5625, 0.40625, 0.40625, 0.625, 0.59375), IBooleanFunction.OR);
		lathe = VoxelShapes.join(lathe, VoxelShapes.box(0.59375, 0.5625, 0.40625, 0.65625, 0.625, 0.59375), IBooleanFunction.OR);
		lathe = VoxelShapes.join(lathe, VoxelShapes.box(0.46875, 0.84375, 0.40625, 0.53125, 0.875, 0.59375), IBooleanFunction.OR);
		lathe = VoxelShapes.join(lathe, VoxelShapes.box(0.40625, 0.8421875, 0.46875, 0.59375, 0.875, 0.53125), IBooleanFunction.OR);
		lathe = VoxelShapes.join(lathe, VoxelShapes.box(0.46875, 0.8328125, 0.46875, 0.53125, 0.8421875, 0.53125), IBooleanFunction.OR);
		lathe = VoxelShapes.join(lathe, VoxelShapes.box(0.4921874999999999, 0.8015625, 0.4921875000000001, 0.5078124999999999, 0.8578125, 0.5078125000000001), IBooleanFunction.OR);
		lathe = VoxelShapes.join(lathe, VoxelShapes.box(0.125, 0.1875, 0.125, 0.875, 0.25, 0.875), IBooleanFunction.OR);
		VoxelShapeRegistry.registerShape(SubtypeMachine.lathe, lathe, Direction.EAST);

		/* LV CHARGER */

		VoxelShape lvcharger = VoxelShapes.empty();
		lvcharger = VoxelShapes.join(lvcharger, VoxelShapes.box(0, 0, 0, 1, 0.1875, 1), IBooleanFunction.OR);
		lvcharger = VoxelShapes.join(lvcharger, VoxelShapes.box(0, 0.1875, 0.25, 0.125, 0.8125, 0.75), IBooleanFunction.OR);
		lvcharger = VoxelShapes.join(lvcharger, VoxelShapes.box(0.375, 0.1875, 0.375, 0.625, 0.5625, 0.625), IBooleanFunction.OR);
		lvcharger = VoxelShapes.join(lvcharger, VoxelShapes.box(0.4375, 0.5625, 0.4375, 0.5625, 0.9375, 0.5625), IBooleanFunction.OR);
		lvcharger = VoxelShapes.join(lvcharger, VoxelShapes.box(0.375, 0.9375, 0.25, 0.6875, 1, 0.75), IBooleanFunction.OR);
		lvcharger = VoxelShapes.join(lvcharger, VoxelShapes.box(0.46875, 0.875, 0.3125, 0.53125, 0.9375, 0.4375), IBooleanFunction.OR);
		lvcharger = VoxelShapes.join(lvcharger, VoxelShapes.box(0.46875, 0.875, 0.5625, 0.53125, 0.9375, 0.6875), IBooleanFunction.OR);
		lvcharger = VoxelShapes.join(lvcharger, VoxelShapes.box(0.125, 0.4375, 0.375, 0.1875, 0.6875, 0.625), IBooleanFunction.OR);
		lvcharger = VoxelShapes.join(lvcharger, VoxelShapes.box(0.1875, 0.5625, 0.46875, 0.25, 0.625, 0.53125), IBooleanFunction.OR);
		lvcharger = VoxelShapes.join(lvcharger, VoxelShapes.box(0.375, 1, 0.4375, 0.4375, 1.125, 0.5625), IBooleanFunction.OR);
		lvcharger = VoxelShapes.join(lvcharger, VoxelShapes.box(0.3125, 0.875, 0.46875, 0.375, 1.0625, 0.53125), IBooleanFunction.OR);
		lvcharger = VoxelShapes.join(lvcharger, VoxelShapes.box(0.25, 0.59375, 0.46875, 0.3125, 0.90625, 0.53125), IBooleanFunction.OR);
		VoxelShapeRegistry.registerShape(SubtypeMachine.chargerlv, lvcharger, Direction.EAST);

		/* MV CHARGER */

		VoxelShape mvcharger = VoxelShapes.empty();
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0, 0, 0, 1, 0.1875, 1), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0, 0.1875, 0.25, 0.125, 0.8125, 0.75), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.15625, 0.1875, 0.09375, 0.34375, 0.5, 0.28125), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.15625, 0.1875, 0.71875, 0.34375, 0.5, 0.90625), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.1875, 0.45, 0.75, 0.3125, 0.5125, 0.875), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.21875, 0.5, 0.84375, 0.28125, 0.5625, 0.90625), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.21875, 0.5, 0.71875, 0.28125, 0.5625, 0.78125), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.1875, 0.45, 0.125, 0.3125, 0.5125, 0.25), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.21875, 0.5, 0.21875, 0.28125, 0.5625, 0.28125), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.21875, 0.5, 0.09375, 0.28125, 0.5625, 0.15625), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.375, 0.1875, 0.375, 0.625, 0.5625, 0.625), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.4375, 0.5625, 0.4375, 0.5625, 0.9375, 0.5625), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.375, 0.9375, 0.25, 0.6875, 1, 0.75), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.46875, 0.875, 0.3125, 0.53125, 0.9375, 0.4375), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.46875, 0.875, 0.5625, 0.53125, 0.9375, 0.6875), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.375, 1, 0.4375, 0.4375, 1.125, 0.5625), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.125, 0.4375, 0.375, 0.1875, 0.6875, 0.625), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.1875, 0.5625, 0.53125, 0.25, 0.625, 0.59375), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.1875, 0.5625, 0.40625, 0.25, 0.625, 0.46875), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.21875, 0.5625, 0.21875, 0.28125, 0.6265625, 0.46875), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.21875, 0.5625, 0.53125, 0.28125, 0.6265625, 0.78125), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.40625, 0.1875, 0.84375, 0.46875, 0.5625, 0.90625), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.21875, 0.5625, 0.84375, 0.46875, 0.625, 0.90625), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.21875, 0.5625, 0.09375, 0.46875, 0.625, 0.15625), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.40625, 0.1875, 0.09375, 0.46875, 0.5625, 0.15625), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.78125, 0.1875, 0.09375, 0.84375, 0.25, 0.34375), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.78125, 0.1875, 0.65625, 0.84375, 0.25, 0.90625), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.78125, 0.25, 0.59375, 0.84375, 0.3125, 0.71875), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.78125, 0.25, 0.28125, 0.84375, 0.3125, 0.40625), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.46875, 0.1875, 0.09375, 0.78125, 0.25, 0.15625), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.46875, 0.1875, 0.84375, 0.78125, 0.25, 0.90625), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.71875, 0.1875, 0.40625, 0.78125, 0.25, 0.59375), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.71875, 0.3125, 0.40625, 0.78125, 0.375, 0.59375), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.84375, 0.1875, 0.40625, 0.90625, 0.25, 0.59375), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.84375, 0.3125, 0.40625, 0.90625, 0.375, 0.59375), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.78125, 0.1875, 0.40625, 0.84375, 0.25, 0.46875), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.78125, 0.3125, 0.40625, 0.84375, 0.375, 0.46875), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.78125, 0.1875, 0.53125, 0.84375, 0.25, 0.59375), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.78125, 0.3125, 0.53125, 0.84375, 0.375, 0.59375), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.71875, 0.25, 0.53125, 0.78125, 0.3125, 0.59375), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.84375, 0.25, 0.53125, 0.90625, 0.3125, 0.59375), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.71875, 0.25, 0.40625, 0.78125, 0.3125, 0.46875), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.84375, 0.25, 0.40625, 0.90625, 0.3125, 0.46875), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.78125, 0.375, 0.46875, 0.84375, 0.5, 0.53125), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.75, 0.46875, 0.5, 0.8125, 0.53125, 0.5625), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.71875, 0.5, 0.53125, 0.78125, 0.5625, 0.59375), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.6875, 0.53125, 0.5625, 0.75, 0.59375, 0.625), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.40625, 0.5625, 0.59375, 0.71875, 0.625, 0.65625), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.375, 0.59375, 0.5625, 0.4375, 0.65625, 0.625), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.3125, 0.65625, 0.5, 0.375, 0.71875, 0.5625), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.34375, 0.625, 0.53125, 0.40625, 0.6875, 0.59375), IBooleanFunction.OR);
		mvcharger = VoxelShapes.join(mvcharger, VoxelShapes.box(0.3125, 0.6875, 0.46875, 0.375, 1.0625, 0.53125), IBooleanFunction.OR);
		VoxelShapeRegistry.registerShape(SubtypeMachine.chargermv, mvcharger, Direction.EAST);

		/* SOLAR PANEL */

		VoxelShape solarpanel = VoxelShapes.empty();
		solarpanel = VoxelShapes.join(solarpanel, VoxelShapes.box(0, 0, 0, 1, 0.0625, 0.0625), IBooleanFunction.OR);
		solarpanel = VoxelShapes.join(solarpanel, VoxelShapes.box(0, 0, 0.9375, 1, 0.0625, 1), IBooleanFunction.OR);
		solarpanel = VoxelShapes.join(solarpanel, VoxelShapes.box(0.9375, 0, 0.0625, 1, 0.0625, 0.9375), IBooleanFunction.OR);
		solarpanel = VoxelShapes.join(solarpanel, VoxelShapes.box(0.25, 0, 0.25, 0.75, 0.0625, 0.75), IBooleanFunction.OR);
		solarpanel = VoxelShapes.join(solarpanel, VoxelShapes.box(0, 0, 0.0625, 0.0625, 0.0625, 0.9375), IBooleanFunction.OR);
		solarpanel = VoxelShapes.join(solarpanel, VoxelShapes.box(0, 0.0625, 0, 1, 0.125, 1), IBooleanFunction.OR);
		solarpanel = VoxelShapes.join(solarpanel, VoxelShapes.box(0.125, 0.125, 0.125, 0.875, 0.1875, 0.875), IBooleanFunction.OR);
		solarpanel = VoxelShapes.join(solarpanel, VoxelShapes.box(0.125, 0.4375, 0.125, 0.875, 0.5, 0.875), IBooleanFunction.OR);
		solarpanel = VoxelShapes.join(solarpanel, VoxelShapes.box(0.1875, 0.1875, 0.1875, 0.8125, 0.4375, 0.8125), IBooleanFunction.OR);
		solarpanel = VoxelShapes.join(solarpanel, VoxelShapes.box(0, 0.5, 0, 1, 0.5625, 1), IBooleanFunction.OR);
		VoxelShapeRegistry.registerShape(SubtypeMachine.solarpanel, solarpanel, Direction.EAST);

		/* THERMOELECTRIC GENERATOR */

		VoxelShape thermoelectricgenerator = VoxelShapes.empty();
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.0625, 0, 0.125, 0.4375, 0.875, 0.9375), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.25, 0.875, 0.25, 0.75, 1, 0.75), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.4375, 0, 0.125, 0.875, 0.03125, 0.9375), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0, 0, 0, 0.0625, 0.9375, 1), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.1875, 0.5625, 0.0625, 0.375, 0.75, 0.125), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.1875, 0.25, 0.0625, 0.375, 0.4375, 0.125), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.5625, 0.5625, 0.125, 0.75, 0.75, 0.1875), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.5625, 0.25, 0.125, 0.75, 0.4375, 0.1875), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.625, 0.625, 0.0625, 0.6875, 0.6875, 0.125), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.625, 0.3125, 0.0625, 0.6875, 0.375, 0.125), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.25, 0.625, 0, 0.6875, 0.6875, 0.0625), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.25, 0.3125, 0, 0.6875, 0.375, 0.0625), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.5, 0.125, 0.15625, 0.84375, 0.875, 0.65625), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.4375, 0.46875, 0.140625, 0.859375, 0.53125, 0.671875), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.75, 0.6875, 0.796875, 0.8125, 0.71875, 0.953125), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.875, 0.540625, 0.4125, 0.8875, 0.571875, 0.50625), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.5, 0.734375, 0.796875, 0.5625, 0.765625, 0.953125), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.75, 0.59375, 0.796875, 0.8125, 0.625, 0.953125), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.5, 0.640625, 0.796875, 0.5625, 0.671875, 0.953125), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.75, 0.515625, 0.796875, 0.8125, 0.546875, 0.953125), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.5, 0.5625, 0.796875, 0.5625, 0.59375, 0.953125), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.75, 0.421875, 0.796875, 0.8125, 0.453125, 0.953125), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.5, 0.46875, 0.796875, 0.5625, 0.5, 0.953125), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.75, 0.34375, 0.796875, 0.8125, 0.375, 0.953125), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.5, 0.390625, 0.796875, 0.5625, 0.421875, 0.953125), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.75, 0.25, 0.796875, 0.8125, 0.28125, 0.953125), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.5, 0.296875, 0.796875, 0.5625, 0.328125, 0.953125), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.734375, 0.09375, 0.90625, 0.78125, 0.21875, 0.9375), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.734375, 0.0625, 0.375, 0.78125, 0.09375, 0.9375), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.625, 0.0625, 0.375, 0.734375, 0.09375, 0.4375), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.5625, 0.09375, 0.3125, 0.75, 0.15625, 0.5), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.625, 0.796875, 0.703125, 0.6875, 0.828125, 0.8125), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.5625, 0.65625, 0.625, 0.75, 0.84375, 0.6875), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.8125, 0.46875, 0.3125, 0.875, 0.71875, 0.5625), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.63125, 0.715625, 0.65, 0.68125, 0.840625, 0.7125), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.4375, 0.15625, 0.140625, 0.859375, 0.21875, 0.671875), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.375, 0.15625, 0.8125, 0.75, 0.1875, 0.921875), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.375, 0.809375, 0.8125, 0.75, 0.840625, 0.921875), IBooleanFunction.OR);
		thermoelectricgenerator = VoxelShapes.join(thermoelectricgenerator, VoxelShapes.box(0.515625, 0.1875, 0.765625, 0.796875, 0.8125, 0.984375), IBooleanFunction.OR);
		VoxelShapeRegistry.registerShape(SubtypeMachine.thermoelectricgenerator, thermoelectricgenerator, Direction.EAST);

		/* UPGRADE TRANFORMER */

		VoxelShape upgradetransformer = VoxelShapes.empty();
		upgradetransformer = VoxelShapes.join(upgradetransformer, VoxelShapes.box(0, 0, 0, 1, 0.125, 1), IBooleanFunction.OR);
		upgradetransformer = VoxelShapes.join(upgradetransformer, VoxelShapes.box(0, 0.3125, 0.25, 0.0625, 0.75, 0.75), IBooleanFunction.OR);
		upgradetransformer = VoxelShapes.join(upgradetransformer, VoxelShapes.box(0, 0.125, 0.0625, 1, 0.3125, 0.9375), IBooleanFunction.OR);
		upgradetransformer = VoxelShapes.join(upgradetransformer, VoxelShapes.box(0.15625, 0.875, 0.625, 0.84375, 0.9375, 0.8125), IBooleanFunction.OR);
		upgradetransformer = VoxelShapes.join(upgradetransformer, VoxelShapes.box(0.15625, 0.875, 0.1875, 0.84375, 0.9375, 0.375), IBooleanFunction.OR);
		upgradetransformer = VoxelShapes.join(upgradetransformer, VoxelShapes.box(0.125, 0.6875, 0.59375, 0.375, 0.78125, 0.84375), IBooleanFunction.OR);
		upgradetransformer = VoxelShapes.join(upgradetransformer, VoxelShapes.box(0.625, 0.375, 0.59375, 0.875, 0.53125, 0.84375), IBooleanFunction.OR);
		upgradetransformer = VoxelShapes.join(upgradetransformer, VoxelShapes.box(0.625, 0.375, 0.15625, 0.875, 0.53125, 0.40625), IBooleanFunction.OR);
		upgradetransformer = VoxelShapes.join(upgradetransformer, VoxelShapes.box(0.625, 0.6875, 0.59375, 0.875, 0.84375, 0.84375), IBooleanFunction.OR);
		upgradetransformer = VoxelShapes.join(upgradetransformer, VoxelShapes.box(0.625, 0.6875, 0.15625, 0.875, 0.84375, 0.40625), IBooleanFunction.OR);
		upgradetransformer = VoxelShapes.join(upgradetransformer, VoxelShapes.box(0.125, 0.6875, 0.15625, 0.375, 0.78125, 0.40625), IBooleanFunction.OR);
		upgradetransformer = VoxelShapes.join(upgradetransformer, VoxelShapes.box(0.125, 0.4375, 0.59375, 0.375, 0.53125, 0.84375), IBooleanFunction.OR);
		upgradetransformer = VoxelShapes.join(upgradetransformer, VoxelShapes.box(0.125, 0.4375, 0.15625, 0.375, 0.53125, 0.40625), IBooleanFunction.OR);
		upgradetransformer = VoxelShapes.join(upgradetransformer, VoxelShapes.box(0.140625, 0.53125, 0.609375, 0.359375, 0.6875, 0.828125), IBooleanFunction.OR);
		upgradetransformer = VoxelShapes.join(upgradetransformer, VoxelShapes.box(0.640625, 0.53125, 0.609375, 0.859375, 0.6875, 0.828125), IBooleanFunction.OR);
		upgradetransformer = VoxelShapes.join(upgradetransformer, VoxelShapes.box(0.640625, 0.53125, 0.171875, 0.859375, 0.6875, 0.390625), IBooleanFunction.OR);
		upgradetransformer = VoxelShapes.join(upgradetransformer, VoxelShapes.box(0.140625, 0.53125, 0.171875, 0.359375, 0.6875, 0.390625), IBooleanFunction.OR);
		upgradetransformer = VoxelShapes.join(upgradetransformer, VoxelShapes.box(0.15625, 0.3125, 0.625, 0.34375, 0.875, 0.8125), IBooleanFunction.OR);
		upgradetransformer = VoxelShapes.join(upgradetransformer, VoxelShapes.box(0.15625, 0.3125, 0.1875, 0.34375, 0.875, 0.375), IBooleanFunction.OR);
		upgradetransformer = VoxelShapes.join(upgradetransformer, VoxelShapes.box(0.65625, 0.3125, 0.625, 0.84375, 0.875, 0.8125), IBooleanFunction.OR);
		upgradetransformer = VoxelShapes.join(upgradetransformer, VoxelShapes.box(0.65625, 0.3125, 0.1875, 0.84375, 0.875, 0.375), IBooleanFunction.OR);
		upgradetransformer = VoxelShapes.join(upgradetransformer, VoxelShapes.box(0.9375, 0.3125, 0.25, 1, 0.75, 0.75), IBooleanFunction.OR);
		VoxelShapeRegistry.registerShape(SubtypeMachine.upgradetransformer, upgradetransformer, Direction.EAST);

		/* WINDMILL */

		VoxelShape windmill = Block.box(2, 0, 2, 14, 2, 14);
		windmill = VoxelShapes.join(windmill, Block.box(3, 2, 3, 13, 3, 13), IBooleanFunction.OR);
		windmill = VoxelShapes.join(windmill, Block.box(5, 3, 5, 11, 16, 11), IBooleanFunction.OR);
		VoxelShapeRegistry.registerShape(SubtypeMachine.windmill, windmill, Direction.NORTH);

		/* WIREMILL */

		VoxelShape wiremill = VoxelShapes.empty();
		wiremill = VoxelShapes.join(wiremill, VoxelShapes.box(0, 0, 0, 1, 0.3125, 1), IBooleanFunction.OR);
		wiremill = VoxelShapes.join(wiremill, VoxelShapes.box(0.0625, 0.3125, 0, 0.3125, 1, 1), IBooleanFunction.OR);
		wiremill = VoxelShapes.join(wiremill, VoxelShapes.box(0.3125, 0.3125, 0.125, 0.6875, 0.375, 0.5625), IBooleanFunction.OR);
		wiremill = VoxelShapes.join(wiremill, VoxelShapes.box(0.375, 0.625, 0.9375, 0.4375, 0.75, 1), IBooleanFunction.OR);
		wiremill = VoxelShapes.join(wiremill, VoxelShapes.box(0.5625, 0.625, 0.9375, 0.625, 0.75, 1), IBooleanFunction.OR);
		wiremill = VoxelShapes.join(wiremill, VoxelShapes.box(0.4375, 0.75, 0.9375, 0.5625, 0.8125, 1), IBooleanFunction.OR);
		wiremill = VoxelShapes.join(wiremill, VoxelShapes.box(0.421875, 0.640625, 0.125, 0.578125, 0.796875, 0.5625), IBooleanFunction.OR);
		wiremill = VoxelShapes.join(wiremill, VoxelShapes.box(0.421875, 0.421875, 0.125, 0.578125, 0.578125, 0.5625), IBooleanFunction.OR);
		wiremill = VoxelShapes.join(wiremill, VoxelShapes.box(0.4375, 0.5625, 0.9375, 0.5625, 0.625, 1), IBooleanFunction.OR);
		wiremill = VoxelShapes.join(wiremill, VoxelShapes.box(0, 0.3125, 0.25, 0.0625, 0.75, 0.75), IBooleanFunction.OR);
		wiremill = VoxelShapes.join(wiremill, VoxelShapes.box(0, 0.3125, 0, 0.0625, 1, 0.125), IBooleanFunction.OR);
		wiremill = VoxelShapes.join(wiremill, VoxelShapes.box(0, 0.875, 0.125, 0.0625, 1, 0.875), IBooleanFunction.OR);
		wiremill = VoxelShapes.join(wiremill, VoxelShapes.box(0, 0.3125, 0.875, 0.0625, 1, 1), IBooleanFunction.OR);
		wiremill = VoxelShapes.join(wiremill, VoxelShapes.box(0.3125, 0.3125, 0.6875, 0.6875, 0.9375, 0.9375), IBooleanFunction.OR);
		wiremill = VoxelShapes.join(wiremill, VoxelShapes.box(0.3125, 0.3125, 0, 0.6875, 0.9375, 0.125), IBooleanFunction.OR);
		wiremill = VoxelShapes.join(wiremill, VoxelShapes.box(0.3125, 0.3125, 0.5625, 0.6875, 0.9375, 0.6875), IBooleanFunction.OR);
		wiremill = VoxelShapes.join(wiremill, VoxelShapes.box(0.6875, 0.3125, 0.6875, 1, 0.625, 0.9375), IBooleanFunction.OR);
		VoxelShapeRegistry.registerShape(SubtypeMachine.wiremill, wiremill, Direction.EAST);

	}

}
