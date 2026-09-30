package electrodynamics.common.tile.pipelines.gas;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerGasCollector;
import electrodynamics.common.reloadlistener.GasCollectorChromoCardsRegister;
import electrodynamics.common.settings.ElectrodynamicsConfig;
import electrodynamics.registers.ElectrodynamicsSounds;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.api.gas.GasAction;
import voltaic.api.gas.GasStack;
import voltaic.common.network.utils.GasUtilities;
import voltaic.prefab.sound.ITickableSound;
import voltaic.prefab.sound.SoundBarrierMethods;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentGasHandlerSimple;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentProcessor;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.tile.types.GenericGasTile;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.registers.VoltaicCapabilities;

public class TileGasCollector extends GenericGasTile implements ITickableSound {

    public static final int CARD_SLOT = 0;

    private boolean isSoundPlaying = false;

    public TileGasCollector(BlockPos worldPos, BlockState blockState) {
	super(ElectrodynamicsTiles.TILE_GASCOLLECTOR.get(), worldPos, blockState);

	addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickClient(this::tickClient));
	addComponent(new ComponentElectrodynamic(this, false, true)
		.setInputDirections(BlockEntityUtils.MachineDirection.BOTTOM)
		.voltage(VoltaicCapabilities.DEFAULT_VOLTAGE * 2.0)
		.maxJoules(ElectrodynamicsConfig.INSTANCE.GAS_COLLECTOR_USAGE_PER_TICK.get() * 20));
	addComponent(new ComponentInventory(this,
		ComponentInventory.InventoryBuilder.newInv().inputs(1).gasOutputs(1).upgrades(3))
		.validUpgrades(ContainerGasCollector.VALID_UPGRADES).valid(machineValidator()));
	addComponent(new ComponentProcessor(this).canProcess(this::canProcess).process(this::process)
		.usage(ElectrodynamicsConfig.INSTANCE.GAS_COLLECTOR_USAGE_PER_TICK.get(), 0));
	addComponent(new ComponentContainerProvider(SubtypeMachine.gascollector.tag(), this)
		.createMenu((id, player) -> new ContainerGasCollector(id, player,
			requireComponent(IComponentType.Inventory), getCoordsArray())));
	addComponent(new ComponentGasHandlerSimple(this, "", 5000, 1000, 10)
		.setOutputDirections(BlockEntityUtils.MachineDirection.BACK).setOnGasCondensed(getCondensedHandler()));
    }

    private void tickClient(Level level, ComponentTickable componentTickable) {
	if (!isSoundPlaying) {
	    isSoundPlaying = true;
	    SoundBarrierMethods.playTileSound(ElectrodynamicsSounds.SOUND_WINDMILL.get(), this, true);
	}
    }

    private void tickServer(Level level, ComponentTickable componentTickable) {
	ComponentGasHandlerSimple handler = requireComponent(IComponentType.GasHandler);
	GasUtilities.fillItem(this, handler.asArray());
	GasUtilities.outputToPipe(this, handler.asArray(), handler.outputDirections);

    }

    private void process(ComponentProcessor componentProcessor, Level level, int procNumber) {
	ComponentInventory inv = requireComponent(IComponentType.Inventory);
	ItemStack card = inv.getItem(CARD_SLOT);
	GasCollectorChromoCardsRegister.AtmosphericResult result = GasCollectorChromoCardsRegister.INSTANCE
		.getResult(card.getItem());
	ComponentGasHandlerSimple tank = requireComponent(IComponentType.GasHandler);
	tank.fill(new GasStack(result.stack().getGas(),
		(int) (result.stack().getAmount() * componentProcessor.operatingSpeed.getValue()),
		result.stack().getTemperature(), result.stack().getPressure()), GasAction.EXECUTE);
    }

    private boolean canProcess(ComponentProcessor componentProcessor, Level level, int procNumber) {
	boolean valid = checkRecipe(componentProcessor);
	if (BlockEntityUtils.isLit(this) ^ valid) {
	    BlockEntityUtils.updateLit(this, valid);
	}
	return valid;
    }

    private boolean checkRecipe(ComponentProcessor componentProcessor) {
	Level level = this.level;
	if (level == null)
	    return false;

	ComponentElectrodynamic electro = requireComponent(IComponentType.Electrodynamic);
	if (electro.getJoulesStored() < componentProcessor.getUsage(0))
	    return false;
	ComponentInventory inv = requireComponent(IComponentType.Inventory);
	ItemStack card = inv.getItem(CARD_SLOT);
	if (card.isEmpty() || !GasCollectorChromoCardsRegister.INSTANCE.hasResult(card.getItem()))
	    return false;
	GasCollectorChromoCardsRegister.AtmosphericResult result = GasCollectorChromoCardsRegister.INSTANCE
		.getResult(card.getItem());

	ComponentGasHandlerSimple tank = requireComponent(IComponentType.GasHandler);
	if (!tank.isEmpty() && !tank.getGas().getGas().equals(result.stack().getGas()))
	    return false;

	ResourceKey<Biome> biome = result.biome();
	TagKey<Biome> biomeTag = result.biomeTag();

	if (biome != null) {
	    Holder<Biome> biomeHolder = level.getBiome(getBlockPos());
	    return biomeHolder.unwrapKey().map(biome::equals).orElse(false);
	}

	if (biomeTag != null) {
	    Holder<Biome> biomeHolder = level.getBiome(getBlockPos());
	    return biomeHolder.is(biomeTag);
	}

	return true;
    }

    @Override
    public void setNotPlaying() {
	isSoundPlaying = false;
    }

    @Override
    public boolean shouldPlaySound() {
	return this.<ComponentProcessor>requireComponent(IComponentType.Processor).isActive(0);
    }

    @Override
    public int getComparatorSignal(Level level) {
	return this.<ComponentProcessor>requireComponent(IComponentType.Processor).isActive(0) ? 15 : 0;
    }

}
