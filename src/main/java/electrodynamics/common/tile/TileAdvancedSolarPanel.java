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
import electrodynamics.common.tile.generic.GenericTileBase;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;

public class TileAdvancedSolarPanel extends GenericTileBase implements ITickableTileBase, IPowerProvider, IElectricTile {

	public static final TransferPack DEFAULT_OUTPUT = TransferPack.ampsVoltage(36, 120);
	protected CachedTileOutput output;

	public TileAdvancedSolarPanel() {
		super(DeferredRegisters.TILE_ADVANCEDSOLARPANEL.get());
	}

	@Override
	public void tickServer() {
		if (world.getWorldInfo().getDayTime() < 12000) {
			if (output == null) {
				output = new CachedTileOutput(world, new BlockPos(pos).offset(Direction.DOWN));
			}
			if (output.get() instanceof IPowerReceiver) {
				output.<IPowerReceiver>get().receivePower(TransferPack.joulesVoltage(DEFAULT_OUTPUT.getJoules() * ((6000 - Math.abs(6000.0 - world.getWorldInfo().getDayTime())) / 6000.0), DEFAULT_OUTPUT.getVoltage()),
						getFacing(), false);
			}
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
	public double getVoltage(Direction from) {
		return TileAdvancedSolarPanel.DEFAULT_OUTPUT.getVoltage();
	}

	@Override
	public TransferPack extractPower(TransferPack transfer, Direction from, boolean debug) {
		return TransferPack.EMPTY;
	}

	@Override
	public boolean canConnectElectrically(Direction direction) {
		return direction == Direction.DOWN;
	}

}
