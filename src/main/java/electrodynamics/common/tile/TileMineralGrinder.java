package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import electrodynamics.api.capability.electrodynamic.CapabilityElectrodynamic;
import electrodynamics.api.particle.ParticleAPI;
import electrodynamics.api.sound.SoundAPI;
import electrodynamics.common.inventory.container.ContainerO2OProcessor;
import electrodynamics.common.inventory.container.ContainerO2OProcessorDouble;
import electrodynamics.common.inventory.container.ContainerO2OProcessorTriple;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class TileMineralGrinder extends GenericTile {
    public long clientRunningTicks = 0;

    public TileMineralGrinder(BlockPos pos, BlockState state) {
	this(0, pos, state);
    }

    public TileMineralGrinder(int extra, BlockPos pos, BlockState state) {
	super(extra == 1 ? DeferredRegisters.TILE_MINERALGRINDERDOUBLE.get()
		: extra == 2 ? DeferredRegisters.TILE_MINERALGRINDERTRIPLE.get() : DeferredRegisters.TILE_MINERALGRINDER.get(), pos, state);

	int processorInputs = 1;
	int processorCount = extra + 1;
	int inputCount = processorInputs * (extra + 1);
	int outputCount = 1 * (extra + 1);
	int invSize = 3 + inputCount + outputCount;

	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentTickable().tickClient(this::tickClient));
	addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.NORTH)
		.voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * Math.pow(2, extra))
		.maxJoules(Constants.MINERALGRINDER_USAGE_PER_TICK * 20 * (extra + 1)));

	int[] ints = new int[extra + 1];
	for (int i = 0; i <= extra; i++) {
	    ints[i] = i * 2;
	}

	addComponent(new ComponentInventory(this).size(invSize).inputs(inputCount).outputs(outputCount).upgrades(3).processors(processorCount)
		.processorInputs(processorInputs).valid(machineValidator(ints)).setMachineSlots(extra).shouldSendInfo());
	addComponent(new ComponentContainerProvider("container.mineralgrinder" + extra).createMenu((id, player) -> (extra == 0
		? new ContainerO2OProcessor(id, player, getComponent(ComponentType.Inventory), getCoordsArray())
		: extra == 1 ? new ContainerO2OProcessorDouble(id, player, getComponent(ComponentType.Inventory), getCoordsArray())
			: extra == 2 ? new ContainerO2OProcessorTriple(id, player, getComponent(ComponentType.Inventory), getCoordsArray()) : null)));

	for (int i = 0; i <= extra; i++) {
	    addProcessor(new ComponentProcessor(this).setProcessorNumber(i)
		    .canProcess(component -> component.canProcessItem2ItemRecipe(component, ElectrodynamicsRecipeInit.MINERAL_GRINDER_TYPE))
		    .process(component -> component.processItem2ItemRecipe(component)).requiredTicks(Constants.MINERALGRINDER_REQUIRED_TICKS)
		    .usage(Constants.MINERALGRINDER_USAGE_PER_TICK));
	}
    }

    protected void tickClient(ComponentTickable tickable) {
	boolean has = getType() == DeferredRegisters.TILE_MINERALGRINDERDOUBLE.get()
		? getProcessor(0).operatingTicks + getProcessor(1).operatingTicks > 0
		: getType() == DeferredRegisters.TILE_MINERALGRINDERTRIPLE.get()
			? getProcessor(0).operatingTicks + getProcessor(1).operatingTicks + getProcessor(2).operatingTicks > 0
			: getProcessor(0).operatingTicks > 0;
	if (has) {
	    if (level.random.nextDouble() < 0.15) {
		level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + level.random.nextDouble(),
			worldPosition.getY() + level.random.nextDouble() * 0.2 + 0.8, worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D,
			0.0D);
	    }
	    if (tickable.getTicks() % 200 == 0) {
		SoundAPI.playSound(SoundRegister.SOUND_MINERALGRINDER.get(), SoundSource.BLOCKS, 0.5f, 1, worldPosition);
	    }
	    int amount = getType() == DeferredRegisters.TILE_MINERALGRINDERDOUBLE.get() ? 2
		    : getType() == DeferredRegisters.TILE_MINERALGRINDERTRIPLE.get() ? 3 : 1;
	    for (int i = 0; i < amount; i++) {
		ComponentInventory inv = getComponent(ComponentType.Inventory);
		ItemStack stack = inv.getInputContents().get(getProcessor(i).getProcessorNumber()).get(0);
		if (stack.getItem()instanceof BlockItem it) {
		    Block block = it.getBlock();
		    double d4 = level.random.nextDouble() * 12.0 / 16.0 + 0.5 - 6.0 / 16.0;
		    double d6 = level.random.nextDouble() * 12.0 / 16.0 + 0.5 - 6.0 / 16.0;
		    ParticleAPI.addGrindedParticle(level, worldPosition.getX() + d4, worldPosition.getY() + 0.8, worldPosition.getZ() + d6, 0.0D, 5D,
			    0.0D, block.defaultBlockState(), worldPosition);
		}
	    }
	    clientRunningTicks++;
	}
    }
}
