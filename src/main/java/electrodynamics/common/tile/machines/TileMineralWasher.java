package electrodynamics.common.tile.machines;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerMineralWasher;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.tile.components.type.ComponentInventory.InventoryBuilder;
import electrodynamics.prefab.tile.types.GenericFluidTile;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3f;

public class TileMineralWasher extends GenericFluidTile {
	public static final int MAX_TANK_CAPACITY = 5000;

	public TileMineralWasher() {
		super(ElectrodynamicsBlockTypes.TILE_MINERALWASHER.get());
		addComponent(new ComponentTickable(this).tickClient(this::tickClient));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentElectrodynamic(this, false, true).setInputDirections(Direction.NORTH).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 4));
		addComponent(new ComponentFluidHandlerMulti(this).setTanks(1, 1, new int[] { MAX_TANK_CAPACITY }, new int[] { MAX_TANK_CAPACITY }).setInputDirections(Direction.EAST).setOutputDirections(Direction.WEST).setRecipeType(ElectrodynamicsRecipeInit.MINERAL_WASHER_TYPE));
		addComponent(new ComponentInventory(this, InventoryBuilder.newInv().processors(1, 1, 0, 0).bucketInputs(1).bucketOutputs(1).upgrades(3)).setDirectionsBySlot(0, Direction.DOWN, Direction.UP, Direction.NORTH).validUpgrades(ContainerMineralWasher.VALID_UPGRADES).valid(machineValidator()));
		addComponent(new ComponentProcessor(this).canProcess(component -> component.outputToFluidPipe().consumeBucket().dispenseBucket().canProcessFluidItem2FluidRecipe(component, ElectrodynamicsRecipeInit.MINERAL_WASHER_TYPE)).process(component -> component.processFluidItem2FluidRecipe(component)));
		addComponent(new ComponentContainerProvider(SubtypeMachine.mineralwasher, this).createMenu((id, player) -> new ContainerMineralWasher(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return super.getRenderBoundingBox().inflate(1);
	}

	protected void tickClient(ComponentTickable tickable) {
		if (this.<ComponentProcessor>getComponent(IComponentType.Processor).isActive()) {
			if (level.random.nextDouble() < 0.15) {
				level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + level.random.nextDouble(), worldPosition.getY() + level.random.nextDouble() * 0.4 + 0.5, worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D, 0.0D);
			}
			ComponentInventory inv = getComponent(IComponentType.Inventory);
			ItemStack input = inv.getInputContents().get(0);
			Vector3f color;
			if (input.getItem() instanceof BlockItem) {
				BlockItem block = (BlockItem) input.getItem();
				if (block.getBlock().defaultBlockState().is(Blocks.MAGMA_BLOCK)) {
					color = new Vector3f(1f, 0.64706f, 0);
				} else {
					color = new Vector3f(1f, 1f, 0);
				}

			} else {
				color = new Vector3f(1f, 1f, 0);
			}
			for (int i = 0; i < 2; i++) {
				double x = 0.5 + level.random.nextDouble() * 0.4 - 0.2;
				double y = 0.5 + level.random.nextDouble() * 0.3 - 0.15;
				double z = 0.5 + level.random.nextDouble() * 0.4 - 0.2;
				level.addParticle(new RedstoneParticleData(color.x(), color.y(), color.z(), 1), worldPosition.getX() + x, worldPosition.getY() + y, worldPosition.getZ() + z, level.random.nextDouble() * 0.2 - 0.1, level.random.nextDouble() * 0.2 - 0.1, level.random.nextDouble() * 0.2 - 0.1);
			}
		}
	}

	@Override
	public int getComparatorSignal() {
		return this.<ComponentProcessor>getComponent(IComponentType.Processor).isActive() ? 15 : 0;
	}

}