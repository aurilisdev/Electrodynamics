package electrodynamics.common.tile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.TargetValue;
import electrodynamics.api.tile.ITickableTileBase;
import electrodynamics.api.tile.electric.CapabilityElectrodynamic;
import electrodynamics.api.tile.electric.IElectrodynamic;
import electrodynamics.api.utilities.CachedTileOutput;
import electrodynamics.api.utilities.TileUtilities;
import electrodynamics.api.utilities.TransferPack;
import electrodynamics.common.block.BlockGenericMachine;
import electrodynamics.common.block.BlockMachine;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.ContainerCoalGenerator;
import electrodynamics.common.network.ElectricityUtilities;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.tile.generic.GenericTileInventory;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public class TileCoalGenerator extends GenericTileInventory implements ITickableTileBase, IElectrodynamic {
    public TransferPack currentOutput = TransferPack.EMPTY;
    public static final int[] SLOTS_INPUT = new int[] { 0 };
    public static final int COAL_BURN_TIME = 1000;

    protected CachedTileOutput output;
    protected TargetValue heat = new TargetValue(27);
    protected int burnTime;

    public TileCoalGenerator() {
	super(DeferredRegisters.TILE_COALGENERATOR.get());
    }

    private boolean isBurning() {
	return burnTime > 0;
    }

    @Override
    public void tickServer() {
	if (output == null) {
	    output = new CachedTileOutput(world, new BlockPos(pos).offset(getFacing().getOpposite()));
	}
	if (!isBurning() && !items.get(0).isEmpty()) {
	    burnTime = COAL_BURN_TIME;
	    decrStackSize(0, 1);
	}
	BlockMachine machine = (BlockMachine) getBlockState().getBlock();
	if (machine != null) {
	    boolean update = false;
	    if (machine.machine == SubtypeMachine.coalgenerator) {
		if (isBurning()) {
		    update = true;
		}
	    } else {
		if (!isBurning()) {
		    update = true;
		}
	    }
	    if (update) {
		world.setBlockState(pos,
			DeferredRegisters.SUBTYPEBLOCK_MAPPINGS
				.get(isBurning() ? SubtypeMachine.coalgeneratorrunning : SubtypeMachine.coalgenerator)
				.getDefaultState()
				.with(BlockGenericMachine.FACING, getBlockState().get(BlockGenericMachine.FACING)),
			3);
	    }
	}
	if (heat.get() > 27) {
	    ElectricityUtilities.receivePower(output.get(), getFacing(), currentOutput, false);
	}
	heat.rangeParameterize(27, 3000, isBurning() ? 3000 : 27, heat.get(), 600).flush();
	currentOutput = TransferPack.ampsVoltage(
		Constants.COALGENERATOR_MAX_OUTPUT.getAmps() * ((heat.get() - 27.0) / (3000.0 - 27.0)),
		Constants.COALGENERATOR_MAX_OUTPUT.getVoltage());
    }

    @Override
    public void tickCommon() {
	if (isBurning()) {
	    --burnTime;
	}
    }

    @Override
    public void tickClient() {
	if (((BlockMachine) getBlockState().getBlock()).machine == SubtypeMachine.coalgeneratorrunning) {
	    Direction dir = getFacing();
	    if (world.rand.nextInt(10) == 0) {
		world.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
			SoundEvents.BLOCK_CAMPFIRE_CRACKLE, SoundCategory.BLOCKS, 0.5F + world.rand.nextFloat(),
			world.rand.nextFloat() * 0.7F + 0.6F, false);
	    }

	    if (world.rand.nextInt(10) == 0) {
		for (int i = 0; i < world.rand.nextInt(1) + 1; ++i) {
		    world.addParticle(ParticleTypes.LAVA, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
			    dir.getXOffset(), 0.0, dir.getZOffset());
		}
	    }
	}
    }

    @Override
    public int getSizeInventory() {
	return 1;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
	return side == Direction.UP || side == TileUtilities.getRelativeSide(getFacing(), Direction.EAST) ? SLOTS_INPUT
		: SLOTS_EMPTY;
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
	return new ContainerCoalGenerator(id, player, this, inventorydata);
    }

    protected final IIntArray inventorydata = new IIntArray() {
	@Override
	public int get(int index) {
	    switch (index) {
	    case 0:
		return burnTime;
	    case 1:
		return (int) heat.get();
	    default:
		return 0;
	    }
	}

	@Override
	public void set(int index, int value) {
	    switch (index) {
	    case 0:
		burnTime = value;
		break;
	    case 1:
		heat.set(value);
		break;
	    }

	}

	@Override
	public int size() {
	    return 2;
	}
    };

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
	return stack.getItem() == Items.COAL || stack.getItem() == Items.CHARCOAL;
    }

    @Override
    public ITextComponent getDisplayName() {
	return new TranslationTextComponent("container.coalgenerator");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
	compound.putDouble("heat", heat.get());
	compound.putInt("burnTime", burnTime);
	return super.write(compound);
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
	super.read(state, compound);
	heat.set(compound.getDouble("heat"));
	burnTime = compound.getInt("burnTime");
    }

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
	if (capability == CapabilityElectrodynamic.ELECTRODYNAMIC && getFacing().getOpposite() == facing) {
	    return (LazyOptional<T>) LazyOptional.of(() -> this);
	}
	return super.getCapability(capability, facing);
    }

    @Override
    public void setJoulesStored(double joules) {
    }

    @Override
    public double getJoulesStored() {
	return 0;
    }

    @Override
    public double getMaxJoulesStored() {
	return 0;
    }

}
