package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.api.particle.ParticleAPI;
import electrodynamics.api.sound.SoundAPI;
import electrodynamics.common.inventory.container.ContainerO2OProcessor;
import electrodynamics.common.inventory.container.ContainerO2OProcessorDouble;
import electrodynamics.common.inventory.container.ContainerO2OProcessorTriple;
import electrodynamics.common.item.ItemProcessorUpgrade;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.recipe.categories.o2o.specificmachines.MineralGrinderRecipe;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.tile.GenericTileTicking;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.type.ComponentProcessorType;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class TileMineralGrinder extends GenericTileTicking {
    public long clientRunningTicks = 0;

    public TileMineralGrinder(BlockPos pos, BlockState state) {
	this(0, pos, state);
    }

    public TileMineralGrinder(int extra, BlockPos pos, BlockState state) {
	super(extra == 1 ? DeferredRegisters.TILE_MINERALGRINDERDOUBLE.get()
		: extra == 2 ? DeferredRegisters.TILE_MINERALGRINDERTRIPLE.get() : DeferredRegisters.TILE_MINERALGRINDER.get(), pos, state);
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentTickable().tickClient(this::tickClient));
	addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.NORTH)
		.voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * Math.pow(2, extra))
		.maxJoules(Constants.MINERALGRINDER_USAGE_PER_TICK * 20 * (extra + 1)));
	addComponent(new ComponentInventory(this).size(5 + extra * 2)
		.valid((slot, stack) -> slot == 0 || slot == extra * 2 || extra == 2 && slot == 2
			|| slot != extra && slot != extra * 3 && slot != extra * 5 && stack.getItem() instanceof ItemProcessorUpgrade)
		.setMachineSlots(extra).shouldSendInfo());
	addComponent(new ComponentContainerProvider("container.mineralgrinder" + extra).createMenu((id, player) -> (extra == 0
		? new ContainerO2OProcessor(id, player, getComponent(ComponentType.Inventory), getCoordsArray())
		: extra == 1 ? new ContainerO2OProcessorDouble(id, player, getComponent(ComponentType.Inventory), getCoordsArray())
			: extra == 2 ? new ContainerO2OProcessorTriple(id, player, getComponent(ComponentType.Inventory), getCoordsArray()) : null)));
	if (extra == 0) {
	    ComponentProcessor pr = new ComponentProcessor(this).upgradeSlots(2, 3, 4)
		    .canProcess(component -> component.canProcessO2ORecipe(component, MineralGrinderRecipe.class,
			    ElectrodynamicsRecipeInit.MINERAL_GRINDER_TYPE))
		    .process(component -> component.processO2ORecipe(component, MineralGrinderRecipe.class))
		    .requiredTicks(Constants.MINERALGRINDER_REQUIRED_TICKS).usage(Constants.MINERALGRINDER_USAGE_PER_TICK)
		    .type(ComponentProcessorType.ObjectToObject);
	    addProcessor(pr);
	} else {
	    for (int i = 0; i <= extra; i++) {
		ComponentProcessor pr = new ComponentProcessor(this).upgradeSlots(extra * 2 + 2, extra * 2 + 3, extra * 2 + 4)
			.canProcess(component -> component.canProcessO2ORecipe(component, MineralGrinderRecipe.class,
				ElectrodynamicsRecipeInit.MINERAL_GRINDER_TYPE))
			.process(component -> component.processO2ORecipe(component, MineralGrinderRecipe.class))
			.requiredTicks(Constants.MINERALGRINDER_REQUIRED_TICKS).usage(Constants.MINERALGRINDER_USAGE_PER_TICK)
			.type(ComponentProcessorType.ObjectToObject);
		addProcessor(pr);
		pr.inputSlot(i * 2);
		pr.outputSlot(i * 2 + 1);
	    }
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
		ItemStack stack = getProcessor(i).getInput();
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
