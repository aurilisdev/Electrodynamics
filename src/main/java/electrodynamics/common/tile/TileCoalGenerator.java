package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.tile.ITickableTileBase;
import electrodynamics.api.tile.electric.IElectricTile;
import electrodynamics.api.tile.electric.IPowerProvider;
import electrodynamics.api.tile.electric.IPowerReceiver;
import electrodynamics.api.utilities.CachedTileOutput;
import electrodynamics.api.utilities.TransferPack;
import electrodynamics.common.block.BlockGenericMachine;
import electrodynamics.common.block.BlockMachine;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.ContainerCoalGenerator;
import electrodynamics.common.tile.generic.GenericTileInventory;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TileCoalGenerator extends GenericTileInventory implements ITickableTileBase, IPowerProvider, IElectricTile {

	public static final TransferPack DEFAULT_OUTPUT = TransferPack.ampsVoltage(34, 120);
	public static final int[] SLOTS_UP = new int[] { 0 };
	public static final int COAL_BURN_TIME = 1000;

	protected CachedTileOutput output;
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
				world.setBlockState(pos, DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(isBurning() ? SubtypeMachine.coalgeneratorrunning : SubtypeMachine.coalgenerator).getDefaultState().with(BlockGenericMachine.FACING,
						getBlockState().get(BlockGenericMachine.FACING)), 3);
			}
		}

		if (isBurning()) {
			if (output.get() instanceof IPowerReceiver) {
				output.<IPowerReceiver>get().receivePower(DEFAULT_OUTPUT, getFacing(), false);
			}
		}
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
			Direction dir = getBlockState().get(BlockGenericMachine.FACING);
			if (world.rand.nextInt(10) == 0) {
				world.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_CAMPFIRE_CRACKLE, SoundCategory.BLOCKS, 0.5F + world.rand.nextFloat(), world.rand.nextFloat() * 0.7F + 0.6F,
						false);
			}

			if (world.rand.nextInt(10) == 0) {
				for (int i = 0; i < world.rand.nextInt(1) + 1; ++i) {
					world.addParticle(ParticleTypes.LAVA, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, dir.getXOffset(), 0.0, dir.getZOffset());
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
		return side == Direction.UP ? SLOTS_UP : SLOTS_EMPTY;
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
			}

		}

		@Override
		public int size() {
			return 1;
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
	public double getVoltage(Direction from) {
		return TileCoalGenerator.DEFAULT_OUTPUT.getVoltage();
	}

	@Override
	public TransferPack extractPower(TransferPack transfer, Direction from, boolean debug) {
		return TransferPack.EMPTY;
	}

	@Override
	public boolean canConnectElectrically(Direction direction) {
		return getBlockState().get(BlockGenericMachine.FACING).getOpposite() == direction;
	}

}
