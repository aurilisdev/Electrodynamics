package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.tile.GenericTileTicking;
import electrodynamics.api.tile.components.ComponentType;
import electrodynamics.api.tile.components.type.ComponentDirection;
import electrodynamics.api.tile.components.type.ComponentElectrodynamic;
import electrodynamics.api.tile.components.type.ComponentPacketHandler;
import electrodynamics.api.tile.components.type.ComponentTickable;
import electrodynamics.api.utilities.object.CachedTileOutput;
import electrodynamics.api.utilities.object.TransferPack;
import electrodynamics.common.network.ElectricityUtilities;
import electrodynamics.common.settings.Constants;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class TileHydroelectricGenerator extends GenericTileTicking {
    protected CachedTileOutput output;
    public boolean isGenerating = false;
    public boolean directionFlag = false;
    public double savedTickRotation;

    public TileHydroelectricGenerator() {
	super(DeferredRegisters.TILE_HYDROELECTRICGENERATOR.get());
	addComponent(new ComponentDirection());
	addComponent(new ComponentTickable().addTickServer(this::tickServer).addTickCommon(this::tickCommon).addTickClient(this::tickClient));
	addComponent(new ComponentPacketHandler().addGuiPacketReader(this::readNBT).addGuiPacketWriter(this::writeNBT));
	addComponent(new ComponentElectrodynamic(this).addRelativeOutputDirection(Direction.NORTH));
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
	ComponentDirection direction = getComponent(ComponentType.Direction);
	Direction facing = direction.getDirection();
	return super.getRenderBoundingBox().expand(facing.getXOffset(), facing.getYOffset(), facing.getZOffset());
    }

    protected void tickServer(ComponentTickable tickable) {
	ComponentDirection direction = getComponent(ComponentType.Direction);
	Direction facing = direction.getDirection();
	if (output == null) {
	    output = new CachedTileOutput(world, pos.offset(facing.getOpposite()));
	}
	ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
	if (tickable.getTicks() % 20 == 0) {
	    BlockPos shift = pos.offset(facing);
	    BlockState onShift = world.getBlockState(shift);
	    isGenerating = onShift.getFluidState().getFluid() == Fluids.FLOWING_WATER;
	    if (isGenerating && onShift.getBlock() instanceof FlowingFluidBlock) {
		int amount = world.getBlockState(shift).get(FlowingFluidBlock.LEVEL);
		shift = pos.offset(facing).offset(facing.rotateY());
		onShift = world.getBlockState(shift);
		if (onShift.getBlock() instanceof FlowingFluidBlock) {
		    if (amount > onShift.get(FlowingFluidBlock.LEVEL)) {
			directionFlag = true;
		    } else {
			shift = pos.offset(facing).offset(facing.rotateY().getOpposite());
			onShift = world.getBlockState(shift);
			if (onShift.getBlock() instanceof FlowingFluidBlock) {
			    if (amount >= onShift.get(FlowingFluidBlock.LEVEL)) {
				directionFlag = false;
			    } else {
				isGenerating = false;
			    }
			} else {
			    isGenerating = false;
			}
		    }
		} else {
		    shift = pos.offset(facing).offset(facing.rotateY().getOpposite());
		    onShift = world.getBlockState(shift);
		    if (onShift.getBlock() instanceof FlowingFluidBlock) {
			if (amount >= onShift.get(FlowingFluidBlock.LEVEL)) {
			    directionFlag = false;
			} else {
			    isGenerating = false;
			}
		    } else {
			isGenerating = false;
		    }
		}
	    }
	    this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
	}
	if (isGenerating) {
	    ElectricityUtilities.receivePower(output.get(), facing,
		    TransferPack.ampsVoltage(Constants.HYDROELECTRICGENERATOR_AMPERAGE, electro.getVoltage()), false);
	}
    }

    protected void tickCommon(ComponentTickable tickable) {
	if (isGenerating) {
	    savedTickRotation += directionFlag ? 1 : -1;
	}
    }

    protected void tickClient(ComponentTickable tickable) {
	if (isGenerating && world.rand.nextDouble() < 0.3) {
	    Direction direction = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
	    double d4 = world.rand.nextDouble();
	    double d5 = direction.getAxis() == Direction.Axis.X ? direction.getXOffset() * (direction.getXOffset() == -1 ? 0.2D : 1.2D) : d4;
	    double d6 = world.rand.nextDouble();
	    double d7 = direction.getAxis() == Direction.Axis.Z ? direction.getZOffset() * (direction.getZOffset() == -1 ? 0.2D : 1.2D) : d4;
	    world.addParticle(ParticleTypes.BUBBLE_COLUMN_UP, pos.getX() + d5, pos.getY() + d6, pos.getZ() + d7, 0.0D, 0.0D, 0.0D);
	}
    }

    protected void writeNBT(CompoundNBT nbt) {
	nbt.putBoolean("hasWater", isGenerating);
	nbt.putBoolean("directionFlag", directionFlag);
    }

    protected void readNBT(CompoundNBT nbt) {
	isGenerating = nbt.getBoolean("hasWater");
	directionFlag = nbt.getBoolean("directionFlag");
    }
}
