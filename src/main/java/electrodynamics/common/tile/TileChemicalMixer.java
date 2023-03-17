package electrodynamics.common.tile;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerChemicalMixer;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentInventory.InventoryBuilder;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.tile.types.GenericFluidTile;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class TileChemicalMixer extends GenericFluidTile {

	public static final int MAX_TANK_CAPACITY = 5000;

	public TileChemicalMixer(BlockPos worldPosition, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_CHEMICALMIXER.get(), worldPosition, blockState);
		addComponent(new ComponentTickable().tickClient(this::tickClient));
		addComponent(new ComponentDirection());
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.NORTH).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 2));
		addComponent(new ComponentFluidHandlerMulti(this).setTanks(1, 1, new int[] { MAX_TANK_CAPACITY }, new int[] { MAX_TANK_CAPACITY }).setInputDirections(Direction.EAST).setOutputDirections(Direction.WEST).setRecipeType(ElectrodynamicsRecipeInit.CHEMICAL_MIXER_TYPE.get()));
		addComponent(new ComponentInventory(this, InventoryBuilder.newInv().processors(1, 1, 0, 0).bucketInputs(1).bucketOutputs(1).upgrades(3)).relativeSlotFaces(0, Direction.EAST, Direction.UP).relativeSlotFaces(1, Direction.DOWN).validUpgrades(ContainerChemicalMixer.VALID_UPGRADES).valid(machineValidator()));
		addComponent(new ComponentProcessor(this).canProcess(component -> component.outputToPipe().consumeBucket().dispenseBucket().canProcessFluidItem2FluidRecipe(component, ElectrodynamicsRecipeInit.CHEMICAL_MIXER_TYPE.get())).process(component -> component.processFluidItem2FluidRecipe(component)));
		addComponent(new ComponentContainerProvider(SubtypeMachine.chemicalmixer).createMenu((id, player) -> new ContainerChemicalMixer(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));

	}

	@Override
	public AABB getRenderBoundingBox() {
		return super.getRenderBoundingBox().inflate(1);
	}

	protected void tickClient(ComponentTickable tickable) {
		boolean running = this.<ComponentProcessor>getComponent(ComponentType.Processor).operatingTicks.get() > 0;
		if (running) {
			if (level.random.nextDouble() < 0.15) {
				level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + level.random.nextDouble(), worldPosition.getY() + level.random.nextDouble() * 0.4 + 0.5, worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D, 0.0D);
			}
		}
	}

}
