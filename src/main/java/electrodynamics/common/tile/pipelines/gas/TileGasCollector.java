package electrodynamics.common.tile.pipelines.gas;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerGasCollector;
import electrodynamics.common.reloadlistener.GasCollectorChromoCardsRegister;
import electrodynamics.common.settings.ElectrodynamicsConfig;
import electrodynamics.registers.ElectrodynamicsSounds;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
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
import voltaic.prefab.tile.components.type.ComponentPacketHandler;
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
	addComponent(new ComponentPacketHandler(this));
	addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickClient(this::tickClient));
	addComponent(new ComponentElectrodynamic(this, false, true)
		.setInputDirections(BlockEntityUtils.MachineDirection.BOTTOM)
		.voltage(VoltaicCapabilities.DEFAULT_VOLTAGE * 2.0)
		.maxJoules(ElectrodynamicsConfig.INSTANCE.GAS_COLLECTOR_USAGE_PER_TICK.get() * 20));
	addComponent(new ComponentInventory(this,
		ComponentInventory.InventoryBuilder.newInv().inputs(1).gasOutputs(1).upgrades(3))
		.validUpgrades(ContainerGasCollector.VALID_UPGRADES).valid(machineValidator()));
	addComponent(new ComponentProcessor(this).canProcess(this::canProcess).process(this::process));
	addComponent(new ComponentContainerProvider(SubtypeMachine.gascollector.tag(), this)
		.createMenu((id, player) -> new ContainerGasCollector(id, player,
			getComponent(IComponentType.Inventory), getCoordsArray())));
	addComponent(new ComponentGasHandlerSimple(this, "", 5000, 1000, 10)
		.setOutputDirections(BlockEntityUtils.MachineDirection.BACK).setOnGasCondensed(getCondensedHandler()));
    }

    private void tickClient(ComponentTickable componentTickable) {
	if (!isSoundPlaying) {
	    isSoundPlaying = true;
	    SoundBarrierMethods.playTileSound(ElectrodynamicsSounds.SOUND_WINDMILL.get(), this, true);
	}
    }

    private void tickServer(ComponentTickable componentTickable) {

	ComponentGasHandlerSimple handler = getComponent(IComponentType.GasHandler);
	GasUtilities.fillItem(this, handler.asArray());
	GasUtilities.outputToPipe(this, handler.asArray(), handler.outputDirections);

    }

    private void process(ComponentProcessor componentProcessor, int procNumber) {
	ComponentInventory inv = getComponent(IComponentType.Inventory);
	ItemStack card = inv.getItem(CARD_SLOT);
	GasCollectorChromoCardsRegister.AtmosphericResult result = GasCollectorChromoCardsRegister.INSTANCE
		.getResult(card.getItem());
	ComponentGasHandlerSimple tank = getComponent(IComponentType.GasHandler);
	tank.fill(new GasStack(result.stack().getGas(),
		(int) (result.stack().getAmount() * componentProcessor.operatingSpeed.getValue()),
		result.stack().getTemperature(), result.stack().getPressure()), GasAction.EXECUTE);
    }

    private boolean canProcess(ComponentProcessor componentProcessor, int procNumber) {
	boolean valid = checkRecipe(componentProcessor);
	if (BlockEntityUtils.isLit(this) ^ valid) {
	    BlockEntityUtils.updateLit(this, valid);
	}
	return valid;
    }

    private boolean checkRecipe(ComponentProcessor componentProcessor) {
	ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);
	if (electro.getJoulesStored() < componentProcessor.getUsage(0)) {
	    return false;
	}
	ComponentInventory inv = getComponent(IComponentType.Inventory);
	ItemStack card = inv.getItem(CARD_SLOT);
	if (card.isEmpty() || !GasCollectorChromoCardsRegister.INSTANCE.hasResult(card.getItem())) {
	    return false;
	}
	GasCollectorChromoCardsRegister.AtmosphericResult result = GasCollectorChromoCardsRegister.INSTANCE
		.getResult(card.getItem());

	ComponentGasHandlerSimple tank = getComponent(IComponentType.GasHandler);
	if (!tank.isEmpty() && !tank.getGas().getGas().equals(result.stack().getGas())) {
	    return false;
	}

	if (result.biome() != null || result.biomeTag() != null) {

	    Holder<Biome> biomeHolder = getLevel().getBiome(getBlockPos());

	    if (result.biome() != null) {
	        return biomeHolder.unwrapKey()
	                .map(result.biome()::equals)
	                .orElse(false);
	    }

	    return biomeHolder.is(result.biomeTag());
	}

	return true;
    }

    @Override
    public void setNotPlaying() {
	isSoundPlaying = false;
    }

    @Override
    public boolean shouldPlaySound() {
	return this.<ComponentProcessor>getComponent(IComponentType.Processor).isActive(0);
    }

    @Override
    public int getComparatorSignal() {
	return this.<ComponentProcessor>getComponent(IComponentType.Processor).isActive(0) ? 15 : 0;
    }

}
