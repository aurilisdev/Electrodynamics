package electrodynamics.common.tile;

import java.util.HashSet;

import electrodynamics.common.block.BlockMachine;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerWindmill;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.common.multiblock.IMultiblockTileNode;
import electrodynamics.common.multiblock.Subnode;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.tile.generic.GenericGeneratorTile;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.sound.SoundBarrierMethods;
import electrodynamics.prefab.sound.utils.ITickableSound;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.tile.components.type.ComponentInventory.InventoryBuilder;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TransferPack;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class TileWindmill extends GenericGeneratorTile implements IMultiblockTileNode, ITickableSound {

	protected CachedTileOutput output;
	private Property<Boolean> isGenerating = property(new Property<>(PropertyType.Boolean, "isGenerating", false));
	public Property<Boolean> directionFlag = property(new Property<>(PropertyType.Boolean, "directionFlag", false));
	public Property<Double> generating = property(new Property<>(PropertyType.Double, "generating", 0.0));
	private Property<Double> multiplier = property(new Property<>(PropertyType.Double, "multiplier", 1.0));
	public double savedTickRotation;
	public double rotationSpeed;

	private boolean isSoundPlaying = false;

	public TileWindmill(BlockPos worldPosition, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_WINDMILL.get(), worldPosition, blockState, 2.25, SubtypeItemUpgrade.stator);
		addComponent(new ComponentDirection());
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentTickable().tickServer(this::tickServer).tickCommon(this::tickCommon).tickClient(this::tickClient));
		addComponent(new ComponentElectrodynamic(this).output(Direction.DOWN));
		addComponent(new ComponentInventory(this, InventoryBuilder.newInv().upgrades(1)).validUpgrades(ContainerWindmill.VALID_UPGRADES).valid(machineValidator()));
		addComponent(new ComponentContainerProvider(SubtypeMachine.windmill).createMenu((id, player) -> new ContainerWindmill(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));

	}

	@Override
	public AABB getRenderBoundingBox() {
		return super.getRenderBoundingBox().expandTowards(0, 1.5, 0);
	}

	protected void tickServer(ComponentTickable tickable) {
		ComponentDirection direction = getComponent(ComponentType.Direction);
		Direction facing = direction.getDirection();
		if (tickable.getTicks() % 40 == 0) {
			isGenerating.set(level.isEmptyBlock(worldPosition.relative(facing).relative(Direction.UP)));
			float height = Math.max(0, level.getHeight());
			double f = Math.log10((Math.max(0, getBlockPos().getY()) + height / 10.0) * 10.0 / height);
			generating.set(Constants.WINDMILL_MAX_AMPERAGE * Mth.clamp(f, 0, 1));
		}
		if (output == null) {
			output = new CachedTileOutput(level, worldPosition.relative(Direction.DOWN));
		}
		output.update(worldPosition.relative(Direction.DOWN));
		if (isGenerating.get() && output.valid()) {
			ElectricityUtils.receivePower(output.getSafe(), Direction.UP, getProduced(), false);
		}
	}

	protected void tickCommon(ComponentTickable tickable) {
		savedTickRotation += (directionFlag.get() ? 1 : -1) * rotationSpeed;
		rotationSpeed = Mth.clamp(rotationSpeed + 0.05 * (isGenerating.get() ? 1 : -1), 0.0, 1.0);
	}

	protected void tickClient(ComponentTickable tickable) {
		if (shouldPlaySound() && !isSoundPlaying) {
			isSoundPlaying = true;
			SoundBarrierMethods.playTileSound(ElectrodynamicsSounds.SOUND_HUM.get(), this, true);
		}
	}

	@Override
	public void setNotPlaying() {
		isSoundPlaying = false;
	}

	@Override
	public boolean shouldPlaySound() {
		return isGenerating.get();
	}

	@Override
	public HashSet<Subnode> getSubNodes() {
		return BlockMachine.windmillsubnodes;
	}

	@Override
	public void setMultiplier(double val) {
		multiplier.set(val);
	}

	@Override
	public double getMultiplier() {
		return multiplier.get();
	}

	@Override
	public TransferPack getProduced() {
		return TransferPack.ampsVoltage(generating.get() * multiplier.get(), this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic).getVoltage());
	}
}
