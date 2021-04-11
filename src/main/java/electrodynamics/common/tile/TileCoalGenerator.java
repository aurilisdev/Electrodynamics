package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.block.BlockGenericMachine;
import electrodynamics.common.block.BlockMachine;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.ContainerCoalGenerator;
import electrodynamics.common.network.ElectricityUtilities;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.tile.GenericTileTicking;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TargetValue;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.block.BlockState;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;

public class TileCoalGenerator extends GenericTileTicking {
    public static final int COAL_BURN_TIME = 1000;
    protected static final int[] SLOTS_INPUT = new int[] { 0 };

    protected TransferPack currentOutput = TransferPack.EMPTY;
    protected CachedTileOutput output;
    protected TargetValue heat = new TargetValue(27);
    protected int burnTime;
    public double clientHeat;
    public double clientBurnTime;

    public TileCoalGenerator() {
	super(DeferredRegisters.TILE_COALGENERATOR.get());
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler().customPacketWriter(this::createPacket).guiPacketWriter(this::createPacket)
		.customPacketReader(this::readPacket).guiPacketReader(this::readPacket));
	addComponent(new ComponentTickable().tickClient(this::tickClient).tickCommon(this::tickCommon).tickServer(this::tickServer));
	addComponent(new ComponentElectrodynamic(this).relativeOutput(Direction.NORTH));
	addComponent(new ComponentInventory(this).size(1).slotFaces(0, Direction.UP, Direction.EAST, Direction.WEST, Direction.SOUTH, Direction.NORTH)
		.valid((index, stack) -> stack.getItem() == Items.COAL || stack.getItem() == Items.CHARCOAL || stack.getItem() == Items.COAL_BLOCK));
	addComponent(new ComponentContainerProvider("container.coalgenerator")
		.createMenu((id, player) -> new ContainerCoalGenerator(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
    }

    protected void tickServer(ComponentTickable tickable) {
	ComponentDirection direction = getComponent(ComponentType.Direction);
	if (output == null) {
	    output = new CachedTileOutput(world, new BlockPos(pos).offset(direction.getDirection().getOpposite()));
	}
	if (tickable.getTicks() % 20 == 0) {
	    output.update();
	}
	ComponentInventory inv = getComponent(ComponentType.Inventory);
	if (burnTime <= 0 && !inv.getStackInSlot(0).isEmpty()) {
	    burnTime = inv.getStackInSlot(0).getItem() == Items.COAL_BLOCK ? COAL_BURN_TIME * 9 : COAL_BURN_TIME;
	    inv.decrStackSize(0, 1);
	}
	BlockMachine machine = (BlockMachine) getBlockState().getBlock();
	if (machine != null) {
	    boolean update = false;
	    if (machine.machine == SubtypeMachine.coalgenerator) {
		if (burnTime > 0) {
		    update = true;
		}
	    } else {
		if (burnTime <= 0) {
		    update = true;
		}
	    }
	    if (update) {
		world.setBlockState(pos,
			DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(burnTime > 0 ? SubtypeMachine.coalgeneratorrunning : SubtypeMachine.coalgenerator)
				.getDefaultState().with(BlockGenericMachine.FACING, getBlockState().get(BlockGenericMachine.FACING)),
			3);
	    }
	}
	if (heat.get() > 27 && output.valid()) {
	    ElectricityUtilities.receivePower(output.getSafe(), direction.getDirection(), currentOutput, false);
	}
	heat.rangeParameterize(27, 3000, burnTime > 0 ? 3000 : 27, heat.get(), 600).flush();
	currentOutput = TransferPack.ampsVoltage(Constants.COALGENERATOR_MAX_OUTPUT.getAmps() * ((heat.get() - 27.0) / (3000.0 - 27.0)),
		Constants.COALGENERATOR_MAX_OUTPUT.getVoltage());
    }

    protected void tickCommon(ComponentTickable tickable) {
	if (burnTime > 0) {
	    --burnTime;
	}
    }

    protected void tickClient(ComponentTickable tickable) {
	if (((BlockMachine) getBlockState().getBlock()).machine == SubtypeMachine.coalgeneratorrunning) {
	    Direction dir = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
	    if (world.rand.nextInt(10) == 0) {
		world.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_CAMPFIRE_CRACKLE, SoundCategory.BLOCKS,
			0.5F + world.rand.nextFloat(), world.rand.nextFloat() * 0.7F + 0.6F, false);
	    }

	    if (world.rand.nextInt(10) == 0) {
		for (int i = 0; i < world.rand.nextInt(1) + 1; ++i) {
		    world.addParticle(ParticleTypes.LAVA, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, dir.getXOffset(), 0.0,
			    dir.getZOffset());
		}
	    }
	}
    }

    protected void createPacket(CompoundNBT nbt) {
	nbt.putDouble("clientHeat", heat.get());
	nbt.putDouble("clientBurnTime", burnTime);
    }

    protected void readPacket(CompoundNBT nbt) {
	clientHeat = nbt.getDouble("clientHeat");
	clientBurnTime = nbt.getDouble("clientBurnTime");
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
}
