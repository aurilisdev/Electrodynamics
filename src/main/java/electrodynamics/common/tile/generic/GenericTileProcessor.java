package electrodynamics.common.tile.generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import electrodynamics.api.tile.ITickableTileBase;
import electrodynamics.api.tile.electric.IElectricTile;
import electrodynamics.api.tile.electric.IPowerReceiver;
import electrodynamics.api.tile.processing.IElectricProcessor;
import electrodynamics.api.utilities.TransferPack;
import electrodynamics.common.block.BlockMachine;
import electrodynamics.common.item.ItemProcessorUpgrade;
import electrodynamics.common.recipe.MachineRecipes;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.world.Explosion.Mode;

public abstract class GenericTileProcessor extends GenericTileInventory implements ITickableTileBase, IElectricTile, IPowerReceiver, IElectricProcessor {
	public static final double DEFAULT_BASIC_MACHINE_VOLTAGE = 120.0;
	protected HashSet<Integer> upgradeSlots = new HashSet<>();
	protected double currentOperatingTick = 0;
	protected double joules = 0;
	protected long ticks = 0;

	public GenericTileProcessor(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	protected void addUpgradeSlots(Integer... i) {
		upgradeSlots.addAll(new ArrayList<>(Arrays.asList(i)));
	}

	@Override
	public void tick() {
		ticks++;
		ITickableTileBase.super.tick();
	}

	@Override
	public void tickServer() {
		boolean failed = false;
		if (canProcess()) {
			joules -= getJoulesPerTick();
			increaseOperatingTick();
			if (currentOperatingTick >= getRequiredTicks()) {
				process();
				currentOperatingTick = 0;
			}
		} else {
			failed = true;
		}
		if (failed) {
			if (currentOperatingTick > 0) {
				failedOperation();
			}
		}
	}

	protected void increaseOperatingTick() {
		double currentSpeedMultiplier = 1;
		for (Integer in : upgradeSlots) {
			ItemStack stack = items.get(in);
			if (!stack.isEmpty()) {
				if (stack.getItem() instanceof ItemProcessorUpgrade) {
					ItemProcessorUpgrade upgrade = (ItemProcessorUpgrade) stack.getItem();
					currentSpeedMultiplier *= upgrade.subtype.speedMultiplier;
				}
			}
		}
		currentOperatingTick += currentSpeedMultiplier;
	}

	protected void failedOperation() {
		currentOperatingTick = 0;
	}

	public boolean isProcessing() {
		return currentOperatingTick > 0;
	}

	protected final IIntArray inventorydata = new IIntArray() {
		@Override
		public int get(int index) {
			switch (index) {
			case 0:
				return (int) currentOperatingTick;
			default:
				return 0;
			}
		}

		@Override
		public void set(int index, int value) {
			switch (index) {
			case 0:
				currentOperatingTick = value;
				break;
			}

		}

		@Override
		public int size() {
			return 1;
		}
	};

	@Override
	public void func_230337_a_(BlockState state, CompoundNBT compound) {
		super.func_230337_a_(state, compound);
		compound.putDouble(JOULES_STORED_NBT, joules);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		joules = compound.getDouble(JOULES_STORED_NBT);
		return super.write(compound);
	}

	public double getVoltage() {
		return DEFAULT_BASIC_MACHINE_VOLTAGE;
	}

	public abstract int getRequiredTicks();

	public boolean canProcess() {
		return MachineRecipes.canProcess(this);
	}

	public void process() {
		MachineRecipes.process(this);
	}

	@Override
	public void setJoulesStored(double joules) {
		this.joules = Math.min(getMaxJoulesStored(), joules);
	}

	@Override
	public double getJoulesStored() {
		return joules;
	}

	@Override
	public double getMaxJoulesStored() {
		return getJoulesPerTick() * 10;
	}

	@Override
	public TransferPack receivePower(TransferPack transfer, Direction dir, boolean debug) {
		if (!canConnectElectrically(dir)) {
			return TransferPack.EMPTY;
		}
		double received = Math.min(transfer.getJoules(), getMaxJoulesStored() - joules);
		if (!debug) {
			if (transfer.getVoltage() == getVoltage()) {
				joules += received;
			}
			if (transfer.getVoltage() > getVoltage()) {
				world.setBlockState(pos, Blocks.AIR.getDefaultState());
				world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 1, Mode.DESTROY);
				return TransferPack.EMPTY;
			}
		}
		return TransferPack.joulesVoltage(received, transfer.getVoltage());
	}

	@Override
	public boolean canConnectElectrically(Direction direction) {
		return getBlockState().get(BlockMachine.FACING).getOpposite() == direction;
	}
}
