package electrodynamics.common.tile;

import java.util.HashSet;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import electrodynamics.api.electricity.generator.IElectricGenerator;
import electrodynamics.api.sound.SoundAPI;
import electrodynamics.common.block.BlockMachine;
import electrodynamics.common.block.VoxelShapes;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerWindmill;
import electrodynamics.common.item.ItemUpgrade;
import electrodynamics.common.multiblock.IMultiblockTileNode;
import electrodynamics.common.multiblock.Subnode;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TileWindmill extends GenericTile implements IMultiblockTileNode, IElectricGenerator {
	protected CachedTileOutput output;
	public boolean isGenerating = false;
	public boolean directionFlag = false;
	public double savedTickRotation;
	public double generating;
	public double rotationSpeed;
	public double multiplier;

	public TileWindmill(BlockPos worldPosition, BlockState blockState) {
		super(DeferredRegisters.TILE_WINDMILL.get(), worldPosition, blockState);
		addComponent(new ComponentDirection());
		addComponent(new ComponentTickable().tickServer(this::tickServer).tickCommon(this::tickCommon).tickClient(this::tickClient));
		addComponent(new ComponentPacketHandler().guiPacketReader(this::readNBT).guiPacketWriter(this::writeNBT));
		addComponent(new ComponentElectrodynamic(this).output(Direction.DOWN));
		addComponent(new ComponentInventory(this).size(1).upgrades(1).slotFaces(0, Direction.values()).shouldSendInfo().validUpgrades(ContainerWindmill.VALID_UPGRADES).valid(machineValidator()));
		addComponent(new ComponentContainerProvider("container.windmill").createMenu((id, player) -> new ContainerWindmill(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));

	}

	@Override
	public AABB getRenderBoundingBox() {
		return super.getRenderBoundingBox().expandTowards(0, 1.5, 0);
	}

	protected void tickServer(ComponentTickable tickable) {
		if (output == null) {
			output = new CachedTileOutput(level, worldPosition.relative(Direction.DOWN));
		}

		if (tickable.getTicks() % 40 == 0) {
			output.update();
			this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
		}
		if (isGenerating && output.valid()) {
			ElectricityUtils.receivePower(output.getSafe(), Direction.UP, getProduced(), false);
		}
	}

	protected void tickCommon(ComponentTickable tickable) {
		savedTickRotation += (directionFlag ? 1 : -1) * rotationSpeed;
		rotationSpeed = Mth.clamp(rotationSpeed + 0.05 * (isGenerating ? 1 : -1), 0.0, 1.0);
		setMultiplier(1);
		for (ItemStack stack : this.<ComponentInventory>getComponent(ComponentType.Inventory).getItems()) {
			if (!stack.isEmpty() && stack.getItem() instanceof ItemUpgrade upgrade) {
				for (int i = 0; i < stack.getCount(); i++) {
					upgrade.subtype.applyUpgrade.accept(this, null, null);
				}
			}
		}
		ComponentDirection direction = getComponent(ComponentType.Direction);
		Direction facing = direction.getDirection();
		if (tickable.getTicks() % 40 == 0) {
			isGenerating = level.isEmptyBlock(worldPosition.relative(facing).relative(Direction.UP));
			float height = level.getHeight();
			double f = Math.log10((getBlockPos().getY() + height / 10) * 10 / height);
			generating = Constants.WINDMILL_MAX_AMPERAGE * Mth.clamp(f, 0, 1);
		}
	}

	protected void tickClient(ComponentTickable tickable) {
		if (isGenerating && tickable.getTicks() % 180 == 0) {
			SoundAPI.playSound(SoundRegister.SOUND_WINDMILL.get(), SoundSource.BLOCKS, 1, 1, worldPosition);
		}
	}

	protected void writeNBT(CompoundTag nbt) {
		nbt.putBoolean("isGenerating", isGenerating);
		nbt.putBoolean("directionFlag", directionFlag);
		nbt.putDouble("generating", generating);
	}

	protected void readNBT(CompoundTag nbt) {
		isGenerating = nbt.getBoolean("isGenerating");
		directionFlag = nbt.getBoolean("directionFlag");
		generating = nbt.getDouble("generating");
	}

	@Override
	public HashSet<Subnode> getSubNodes() {
		return BlockMachine.windmillsubnodes;
	}

	@Override
	public void setMultiplier(double val) {
		multiplier = val;
	}

	@Override
	public double getMultiplier() {
		return multiplier;
	}

	@Override
	public TransferPack getProduced() {
		return TransferPack.ampsVoltage(generating * multiplier, this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic).getVoltage());
	}

	static {
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(0.125, 0.0625, 0.125, 0.875, 0.125, 0.875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0.125, 0.1875, 0.8125, 0.1875, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.3125, 0, 0.3125, 0.6875, 0.0625, 0.6875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0, 0.8125, 0.875, 0.0625, 0.875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0, 0.125, 0.875, 0.0625, 0.1875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.8125, 0, 0.1875, 0.875, 0.0625, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0, 0.1875, 0.1875, 0.0625, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.375, 0.125, 0.328125, 0.625, 1.125, 0.671875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.3125, 0.125, 0.375, 0.6875, 1.125, 0.625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.3125, 0.125, 0.375, 0.375, 0.1875, 0.4375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 1.125, 0.3125, 0.8125, 1.5, 0.6875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.25, 1.1875, 0.25, 0.75, 1.4375, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.8125, 1.1875, 0.375, 0.875, 1.4375, 0.625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.875, 1.25, 0.4375, 0.9375, 1.375, 0.5625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.93325, 1.25375, 0.445, 1.04325, 1.36375, 0.555), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.984428125, 1.296301875, -0.16, 1.000928125, 1.406301875, 1.16), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.98825, 1.24483125, -0.139156875, 1.00475, 1.35483125, 1.125843125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.9864325, 0.682816875, 0.38924625, 1.0029325, 1.892816875, 0.49924625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.975345625, 1.244470625, -0.147260625, 0.991845625, 1.354470625, 1.117739375), BooleanOp.OR);
		VoxelShapes.registerShape(SubtypeMachine.windmill, shape, Direction.EAST);
	}
}
