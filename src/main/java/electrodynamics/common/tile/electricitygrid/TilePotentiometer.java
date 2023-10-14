package electrodynamics.common.tile.electricitygrid;

import java.util.stream.Stream;

import electrodynamics.api.capability.types.electrodynamic.ICapabilityElectrodynamic.LoadProfile;
import electrodynamics.common.block.VoxelShapes;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerPotentiometer;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.utilities.object.TransferPack;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TilePotentiometer extends GenericTile {

	public final Property<Double> powerConsumption = property(new Property<>(PropertyType.Double, "consumption", -1.0)).onChange((prop, oldval) -> {
		if (level.isClientSide) {
			return;
		}
		setChanged();
	});

	public TilePotentiometer(BlockPos pos, BlockState state) {
		super(ElectrodynamicsBlockTypes.TILE_POTENTIOMETER.get(), pos, state);
		addComponent(new ComponentContainerProvider(SubtypeMachine.potentiometer, this).createMenu((id, player) -> new ContainerPotentiometer(id, player, getCoordsArray())));
		addComponent(new ComponentElectrodynamic(this, false, true).receivePower(this::receivePower).getConnectedLoad(this::getConnectedLoad).setInputDirections(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.DOWN).voltage(-1.0D));
	}

	private TransferPack receivePower(TransferPack pack, boolean debug) {
		if (powerConsumption.get() < 0) {
			return pack;
		}
		double accepted = Math.min(pack.getJoules(), powerConsumption.get());
		return TransferPack.joulesVoltage(accepted, pack.getVoltage());
	}

	private TransferPack getConnectedLoad(LoadProfile loadProfile, Direction dir) {
		if (dir == Direction.UP || dir == Direction.DOWN) {
			return TransferPack.EMPTY;
		}
		return TransferPack.joulesVoltage(powerConsumption.get(), -1);
	}

	static {

		VoxelShape shape = Stream.of(
				//
				Stream.of(
						//
						Block.box(0, 0, 0, 16, 1, 16),
						//
						Block.box(0, 1, 0, 1, 11, 1),
						//
						Block.box(0, 1, 15, 1, 11, 16),
						//
						Block.box(15, 1, 15, 16, 11, 16),
						//
						Block.box(15, 1, 0, 16, 11, 1),
						//
						Block.box(1, 10, 0, 4, 11, 1),
						//
						Block.box(12, 10, 0, 15, 11, 1),
						//
						Block.box(1, 10, 15, 4, 11, 16),
						//
						Block.box(12, 10, 15, 15, 11, 16),
						//
						Block.box(0, 10, 1, 1, 11, 4),
						//
						Block.box(0, 10, 12, 1, 11, 15),
						//
						Block.box(15, 10, 1, 16, 11, 4),
						//
						Block.box(15, 10, 12, 16, 11, 15)
				//
				).reduce(Shapes::or).get(),
				//
				Stream.of(
						//
						Block.box(2, 1, 2, 14, 12, 14),
						//
						Block.box(4, 12, 4, 12, 13, 12),
						//
						Block.box(6.5, 13, 6.5, 9.5, 17, 9.5)
				//
				).reduce(Shapes::or).get(),
				//
				Stream.of(
						//
						Block.box(4, 4, 0, 12, 12, 1),
						//
						Block.box(5, 5, 1, 11, 11, 2),
						//
						Block.box(5, 5, 14, 11, 11, 15),
						//
						Block.box(4, 4, 15, 12, 12, 16),
						//
						Block.box(0, 4, 4, 1, 12, 12),
						//
						Block.box(15, 4, 4, 16, 12, 12),
						//
						Block.box(14, 5, 5, 15, 11, 11),
						//
						Block.box(1, 5, 5, 2, 11, 11)
				//
				).reduce(Shapes::or).get()
		//
		).reduce(Shapes::or).get();

		VoxelShapes.registerShape(SubtypeMachine.potentiometer, shape, Direction.NORTH);

	}

}
