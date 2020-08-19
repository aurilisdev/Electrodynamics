package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.formatting.MeasurementUnit;
import electrodynamics.api.tile.ITickableTileBase;
import electrodynamics.api.tile.electric.IElectricTile;
import electrodynamics.api.tile.electric.IPowerProvider;
import electrodynamics.api.tile.electric.IPowerReceiver;
import electrodynamics.api.utilities.CachedTileOutput;
import electrodynamics.api.utilities.TransferPack;
import electrodynamics.common.block.BlockMachine;
import electrodynamics.common.inventory.container.ContainerBatteryBox;
import electrodynamics.common.item.ItemProcessorUpgrade;
import electrodynamics.common.tile.generic.GenericTileInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion.Mode;

public class TileBatteryBox extends GenericTileInventory implements ITickableTileBase, IPowerProvider, IPowerReceiver, IElectricTile {
	public static final double DEFAULT_VOLTAGE = 120.0;
	public static final double DEFAULT_OUTPUT_JOULES_PER_TICK = 359.0 * DEFAULT_VOLTAGE / 20.0;
	public static final double DEFAULT_MAX_JOULES = MeasurementUnit.MEGA.value * 5;
	protected double currentCapacityMultiplier = 1;
	protected double receiveLimitLeft = 0;
	protected double joules = 0;
	private CachedTileOutput output;

	public TileBatteryBox() {
		super(DeferredRegisters.TILE_BATTERYBOX.get());
	}

	@Override
	public void tickServer() {
		if (output == null) {
			output = new CachedTileOutput(world, new BlockPos(pos).offset(getFacing().getOpposite()));
		}
		receiveLimitLeft = DEFAULT_OUTPUT_JOULES_PER_TICK * currentCapacityMultiplier;
		if (joules > 0) {
			if (output.get() instanceof IPowerReceiver) {
				TransferPack taken = output.<IPowerReceiver>get().receivePower(TransferPack.joulesVoltage(Math.min(joules, DEFAULT_OUTPUT_JOULES_PER_TICK * currentCapacityMultiplier), DEFAULT_VOLTAGE), getFacing(),
						false);
				joules -= taken.getJoules();
			}
		}
		currentCapacityMultiplier = 1;
		for (ItemStack stack : items) {
			if (!stack.isEmpty()) {
				if (stack.getItem() instanceof ItemProcessorUpgrade) {
					ItemProcessorUpgrade upgrade = (ItemProcessorUpgrade) stack.getItem();
					currentCapacityMultiplier *= upgrade.subtype.capacityMultiplier;
				}
			}
		}
		if (joules > DEFAULT_MAX_JOULES * currentCapacityMultiplier) {
			joules = DEFAULT_MAX_JOULES * currentCapacityMultiplier;
		}
	}

	@Override
	public void read(BlockState state, CompoundNBT compound) {
		super.read(state, compound);
		joules = compound.getDouble(JOULES_STORED_NBT);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);

		compound.putDouble(JOULES_STORED_NBT, joules);
		return compound;
	}

	@Override
	public int getSizeInventory() {
		return 3;
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		return SLOTS_EMPTY;
	}

	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return new ContainerBatteryBox(id, player, this, inventorydata);
	}

	protected final IIntArray inventorydata = new IIntArray() {
		@Override
		public int get(int index) {
			switch (index) {
			case 0:
				return (int) (joules / (DEFAULT_MAX_JOULES * currentCapacityMultiplier) * 10000);
			case 1:
				return (int) (currentCapacityMultiplier * 10000);
			default:
				return 0;
			}
		}

		@Override
		public void set(int index, int value) {
			switch (index) {
			case 0:
				joules = value;
				break;
			case 1:
				currentCapacityMultiplier = value;
				break;
			}

		}

		@Override
		public int size() {
			return 2;
		}
	};

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container.batterybox");
	}

	@Override
	public double getVoltage(Direction from) {
		return DEFAULT_VOLTAGE;
	}

	@Override
	@Deprecated // This method might be bugged
	public TransferPack extractPower(TransferPack transfer, Direction dir, boolean debug) {
		if (dir != getBlockState().get(BlockMachine.FACING)) {
			return TransferPack.EMPTY;
		} else {
			if (!canConnectElectrically(dir)) {
				return TransferPack.EMPTY;
			}
			double removed = Math.min(transfer.getJoules(), joules);
			if (!debug) {
				if (transfer.getVoltage() == DEFAULT_VOLTAGE) {
					joules -= removed;
				}
			}
			return TransferPack.joulesVoltage(removed, transfer.getVoltage());
		}
	}

	@Override
	public TransferPack receivePower(TransferPack transfer, Direction dir, boolean debug) {
		if (dir != getBlockState().get(BlockMachine.FACING)) {
			return TransferPack.EMPTY;
		} else {
			if (!canConnectElectrically(dir)) {
				return TransferPack.EMPTY;
			}
			double received = Math.min(Math.min(receiveLimitLeft, transfer.getJoules()), DEFAULT_MAX_JOULES * currentCapacityMultiplier - joules);
			if (!debug) {
				if (transfer.getVoltage() == DEFAULT_VOLTAGE) {
					joules += received;
					receiveLimitLeft -= received;
				}
				if (transfer.getVoltage() > DEFAULT_VOLTAGE) {
					world.setBlockState(pos, Blocks.AIR.getDefaultState());
					world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 1, Mode.DESTROY);
					return TransferPack.EMPTY;
				}
			}
			return TransferPack.joulesVoltage(received, transfer.getVoltage());
		}
	}

	@Override
	public boolean canConnectElectrically(Direction direction) {
		return getBlockState().get(BlockMachine.FACING).getOpposite() == direction || getBlockState().get(BlockMachine.FACING) == direction;
	}

}
