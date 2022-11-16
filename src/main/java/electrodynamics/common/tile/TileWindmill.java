package electrodynamics.common.tile;

import java.util.HashSet;

import electrodynamics.api.electricity.generator.IElectricGenerator;
import electrodynamics.api.sound.SoundAPI;
import electrodynamics.common.block.BlockMachine;
import electrodynamics.common.inventory.container.tile.ContainerWindmill;
import electrodynamics.common.item.ItemUpgrade;
import electrodynamics.common.multiblock.IMultiblockTileNode;
import electrodynamics.common.multiblock.Subnode;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
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
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class TileWindmill extends GenericTile implements IMultiblockTileNode, IElectricGenerator {
	protected CachedTileOutput output;
	private Property<Boolean> isGenerating = property(new Property<Boolean>(PropertyType.Boolean, "isGenerating")).set(false);
	public Property<Boolean> directionFlag = property(new Property<Boolean>(PropertyType.Boolean, "directionFlag")).set(false);
	public Property<Double> generating = property(new Property<Double>(PropertyType.Double, "generating")).set(0.0);
	private Property<Double> multiplier = property(new Property<Double>(PropertyType.Double, "multiplier")).set(1.0);
	public double savedTickRotation;
	public double rotationSpeed;

	public TileWindmill(BlockPos worldPosition, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_WINDMILL.get(), worldPosition, blockState);
		addComponent(new ComponentDirection());
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentTickable().tickServer(this::tickServer).tickCommon(this::tickCommon).tickClient(this::tickClient));
		addComponent(new ComponentElectrodynamic(this).output(Direction.DOWN));
		addComponent(new ComponentInventory(this).size(1).upgrades(1).slotFaces(0, Direction.values()).shouldSendInfo().validUpgrades(ContainerWindmill.VALID_UPGRADES).valid(machineValidator()));
		addComponent(new ComponentContainerProvider("container.windmill").createMenu((id, player) -> new ContainerWindmill(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));

	}

	@Override
	public AABB getRenderBoundingBox() {
		return super.getRenderBoundingBox().expandTowards(0, 1.5, 0);
	}

	protected void tickServer(ComponentTickable tickable) {
		multiplier.set(1.0, true);
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
			isGenerating.set(level.isEmptyBlock(worldPosition.relative(facing).relative(Direction.UP)));
			float height = Math.max(0, level.getHeight());
			double f = Math.log10((getBlockPos().getY() + height / 10.0) * 10.0 / height);
			generating.set(Constants.WINDMILL_MAX_AMPERAGE * Mth.clamp(f, 0, 1));
		}
		if (output == null) {
			output = new CachedTileOutput(level, worldPosition.relative(Direction.DOWN));
		}

		if (tickable.getTicks() % 40 == 0) {
			output.update(worldPosition.relative(Direction.DOWN));
			this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
		}
		if (isGenerating.get() && output.valid()) {
			ElectricityUtils.receivePower(output.getSafe(), Direction.UP, getProduced(), false);
		}
	}

	protected void tickCommon(ComponentTickable tickable) {
		savedTickRotation += (directionFlag.get() ? 1 : -1) * rotationSpeed;
		rotationSpeed = Mth.clamp(rotationSpeed + 0.05 * (isGenerating.get() ? 1 : -1), 0.0, 1.0);
	}

	protected void tickClient(ComponentTickable tickable) {
		if (isGenerating.get() && tickable.getTicks() % 180 == 0) {
			SoundAPI.playSound(ElectrodynamicsSounds.SOUND_WINDMILL.get(), SoundSource.BLOCKS, 1, 1, worldPosition);
		}
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
