package electrodynamics.common.tile.machines;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerMineralWasher;
import electrodynamics.registers.ElectrodynamicsRecipies;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.*;
import voltaic.prefab.tile.types.GenericMaterialTile;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.registers.VoltaicCapabilities;

public class TileMineralWasher extends GenericMaterialTile {
    public static final int MAX_TANK_CAPACITY = 5000;

    public TileMineralWasher() {
        super(ElectrodynamicsTiles.TILE_MINERALWASHER.get());
        addComponent(new ComponentTickable(this).tickClient(this::tickClient));
        addComponent(new ComponentPacketHandler(this));
        addComponent(new ComponentElectrodynamic(this, false, true).setInputDirections(BlockEntityUtils.MachineDirection.BACK).voltage(VoltaicCapabilities.DEFAULT_VOLTAGE * 4));
        addComponent(new ComponentFluidHandlerMulti(this).setTanks(1, 1, new int[]{MAX_TANK_CAPACITY}, new int[]{MAX_TANK_CAPACITY}).setInputDirections(BlockEntityUtils.MachineDirection.RIGHT).setOutputDirections(BlockEntityUtils.MachineDirection.LEFT).setRecipeType(ElectrodynamicsRecipies.MINERAL_WASHER_TYPE));
        addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().processors(1, 1, 0, 0).bucketInputs(1).bucketOutputs(1).upgrades(3))
                //
                .setDirectionsBySlot(0, BlockEntityUtils.MachineDirection.BOTTOM, BlockEntityUtils.MachineDirection.TOP, BlockEntityUtils.MachineDirection.FRONT).validUpgrades(ContainerMineralWasher.VALID_UPGRADES).valid(machineValidator()));
        addComponent(new ComponentProcessor(this).canProcess((component, procNumber) -> component.outputToFluidPipe().consumeBucket().dispenseBucket().canProcessFluidItem2FluidRecipe(procNumber, ElectrodynamicsRecipies.MINERAL_WASHER_TYPE)).process(ComponentProcessor::processFluidItem2FluidRecipe));
        addComponent(new ComponentContainerProvider(SubtypeMachine.mineralwasher.tag(), this).createMenu((id, player) -> new ContainerMineralWasher(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
    }

    protected void tickClient(ComponentTickable tickable) {
        if (this.<ComponentProcessor>getComponent(IComponentType.Processor).isActive(0) && level.getRandom().nextDouble() < 0.15) {
            level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + level.random.nextDouble(), worldPosition.getY() + level.random.nextDouble() * 0.4 + 0.5, worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public int getComparatorSignal() {
        return this.<ComponentProcessor>getComponent(IComponentType.Processor).isActive(0) ? 15 : 0;
    }
    
    @Override
	public AxisAlignedBB getRenderBoundingBox() {
		return super.getRenderBoundingBox().inflate(1);
	}

}
