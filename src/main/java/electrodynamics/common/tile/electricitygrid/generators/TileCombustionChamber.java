package electrodynamics.common.tile.electricitygrid.generators;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerCombustionChamber;
import electrodynamics.common.reloadlistener.CombustionFuelRegister;
import electrodynamics.common.settings.ElectrodynamicsConfig;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.prefab.utilities.object.CombustionFuelSource;
import electrodynamics.registers.ElectrodynamicsSounds;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import voltaic.api.electricity.generator.IElectricGenerator;
import voltaic.common.network.utils.FluidUtilities;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.sound.ITickableSound;
import voltaic.prefab.sound.SoundBarrierMethods;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerMulti;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.tile.types.GenericMaterialTile;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.object.TransferPack;

public class TileCombustionChamber extends GenericMaterialTile implements IElectricGenerator, ITickableSound {

    public static final int TICKS_PER_MILLIBUCKET = 200;
    public static final int TANK_CAPACITY = 1000;

    public SingleProperty<Boolean> running = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.BOOLEAN, "running", false));
    public SingleProperty<Integer> burnTime = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.INTEGER, "burnTime", 0));

    private double fuelMultiplier = 1;
    private final SingleProperty<Double> multiplier = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.DOUBLE, "multiplier", 1.0));
    private final SingleProperty<Boolean> hasRedstoneSignal = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.BOOLEAN, "redstonesignal", false));
    private boolean isSoundPlaying;

    public TileCombustionChamber(BlockPos worldPosition, BlockState blockState) {
	super(ElectrodynamicsTiles.TILE_COMBUSTIONCHAMBER.get(), worldPosition, blockState);
	addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickClient(this::tickClient));
	addComponent(new ComponentElectrodynamic(this, true, false)
		.setOutputDirections(BlockEntityUtils.MachineDirection.RIGHT));
	addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().bucketInputs(1))
		.valid((slot, stack, inventory) -> stack.getCapability(Capabilities.FluidHandler.ITEM) != null));
	addComponent(new ComponentFluidHandlerMulti(this).setInputTanks(1, TANK_CAPACITY)
		.setInputDirections(BlockEntityUtils.MachineDirection.LEFT)
		.setInputFluidTags(CombustionFuelRegister.INSTANCE.getFluidTags()));
	addComponent(new ComponentContainerProvider(SubtypeMachine.combustionchamber.tag(), this)
		.createMenu((id, player) -> new ContainerCombustionChamber(id, player,
			requireComponent(IComponentType.Inventory), getCoordsArray())));
    }

    protected void tickServer(Level level, ComponentTickable tickable) {
	if (hasRedstoneSignal.getValue()) {
	    running.setValue(false);
	    return;
	}
	ComponentFluidHandlerMulti handler = requireComponent(IComponentType.FluidHandler);
	FluidUtilities.drainItem(this, handler.getInputTanks());
	FluidTank tank = handler.getInputTanks()[0];
	if (burnTime.getValue() <= 0) {
	    running.setValue(false);
	    if (tank.getFluidAmount() > 0) {
		CombustionFuelSource source = CombustionFuelRegister.INSTANCE.getFuelFromFluid(tank.getFluid());
		if (!source.isEmpty()) {
		    tank.drain(new FluidStack(tank.getFluid().getFluid(), source.getFuelUsage()), FluidAction.EXECUTE);
		    fuelMultiplier = source.getPowerMultiplier();
		    running.setValue(true);
		    burnTime.setValue(TICKS_PER_MILLIBUCKET);
		}
	    }
	} else {
	    running.setValue(true);
	}
	if (burnTime.getValue() > 0)
	    burnTime.setValue(burnTime.getValue() - 1);
	if (running.getValue() && burnTime.getValue() > 0) {
	    Direction outputDirection = getFacing().getClockWise();
	    BlockEntity target = level.getBlockEntity(worldPosition.relative(outputDirection));
	    if (target != null && !target.isRemoved())
		ElectricityUtils.receivePower(target, outputDirection.getOpposite(), getProduced(), false);
	}
    }

    protected void tickClient(Level level, ComponentTickable tickable) {
	if (!running.getValue())
	    return;
	if (level.random.nextDouble() < 0.15)
	    level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + level.random.nextDouble(),
		    worldPosition.getY() + level.random.nextDouble(), worldPosition.getZ() + level.random.nextDouble(),
		    0.0D, 0.0D, 0.0D);
	if (!isSoundPlaying) {
	    isSoundPlaying = true;
	    SoundBarrierMethods.playTileSound(ElectrodynamicsSounds.SOUND_COMBUSTIONCHAMBER.get(), this, true);
	}
    }

    @Override
    public void setNotPlaying() {
	isSoundPlaying = false;
    }

    @Override
    public boolean shouldPlaySound() {
	return running.getValue();
    }

    @Override
    public void setMultiplier(double val) {
	multiplier.setValue(val);
    }

    @Override
    public double getMultiplier() {
	return multiplier.getValue();
    }

    @Override
    public TransferPack getProduced() {
	ComponentElectrodynamic electro = requireComponent(IComponentType.Electrodynamic);
	return TransferPack.joulesVoltage(ElectrodynamicsConfig.INSTANCE.COMBUSTIONCHAMBER_JOULES_PER_TICK.get()
		* fuelMultiplier * multiplier.getValue(), electro.getVoltage());
    }

    @Override
    public int getComparatorSignal(Level level) {
	return running.getValue() ? 15 : 0;
    }

    @Override
    public void onNeighbourChanged(LevelReader reader, BlockPos neighbor, boolean blockStateTrigger) {
	if (reader instanceof Level level && !level.isClientSide)
	    hasRedstoneSignal.setValue(level.hasNeighborSignal(getBlockPos()));
    }
}