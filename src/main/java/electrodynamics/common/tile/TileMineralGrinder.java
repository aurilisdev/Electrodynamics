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
import electrodynamics.common.recipe.MachineRecipes;
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
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;

public class TileMineralGrinder extends GenericTileTicking {
    public long clientRunningTicks = 0;

    public TileMineralGrinder() {
	this(0);
    }

    public TileMineralGrinder(int extra) {
	super(extra == 1 ? DeferredRegisters.TILE_MINERALGRINDERDOUBLE.get()
		: extra == 2 ? DeferredRegisters.TILE_MINERALGRINDERTRIPLE.get() : DeferredRegisters.TILE_MINERALGRINDER.get());
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentTickable().tickClient(this::tickClient));
	addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.NORTH)
		.voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * Math.pow(2, extra)));
	addComponent(new ComponentInventory(this).size(5 + extra * 2)
		.valid((slot, stack) -> slot == 0 || slot == extra * 2 || extra == 2 && slot == 2
			|| slot != extra && slot != extra * 3 && slot != extra * 5 && stack.getItem() instanceof ItemProcessorUpgrade)
		.relativeFaceSlots(Direction.EAST, 0, extra * 2, extra * 4).relativeFaceSlots(Direction.UP, 0, extra * 2, extra * 4)
		.relativeFaceSlots(Direction.WEST, extra, extra * 2 - 1, extra * 3).relativeFaceSlots(Direction.DOWN, extra, extra * 2 - 1, extra * 3)
		.shouldSendInfo());
	addComponent(new ComponentContainerProvider("container.mineralgrinder" + extra).createMenu((id, player) -> (extra == 0
		? new ContainerO2OProcessor(id, player, getComponent(ComponentType.Inventory), getCoordsArray())
		: extra == 1 ? new ContainerO2OProcessorDouble(id, player, getComponent(ComponentType.Inventory), getCoordsArray())
			: extra == 2 ? new ContainerO2OProcessorTriple(id, player, getComponent(ComponentType.Inventory), getCoordsArray()) : null)));
	if (extra == 0) {
	    ComponentProcessor pr = new ComponentProcessor(this).upgradeSlots(2, 3, 4)
		    .canProcess(component -> MachineRecipes.canProcess(this, component, DeferredRegisters.TILE_MINERALGRINDER.get()))
		    .process(component -> MachineRecipes.process(this, component, DeferredRegisters.TILE_MINERALGRINDER.get()))
		    .requiredTicks(Constants.MINERALGRINDER_REQUIRED_TICKS).usage(Constants.MINERALGRINDER_USAGE_PER_TICK)
		    .type(ComponentProcessorType.ObjectToObject);
	    addProcessor(pr);
	} else {
	    for (int i = 0; i <= extra; i++) {
		ComponentProcessor pr = new ComponentProcessor(this).upgradeSlots(extra * 2 + 2, extra * 2 + 3, extra * 2 + 4)
			.canProcess(component -> MachineRecipes.canProcess(this, component, DeferredRegisters.TILE_MINERALGRINDER.get()))
			.process(component -> MachineRecipes.process(this, component, DeferredRegisters.TILE_MINERALGRINDER.get()))
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
	    if (world.rand.nextDouble() < 0.15) {
		world.addParticle(ParticleTypes.SMOKE, pos.getX() + world.rand.nextDouble(), pos.getY() + world.rand.nextDouble() * 0.2 + 0.8,
			pos.getZ() + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
	    }
	    if (tickable.getTicks() % 200 == 0) {
		SoundAPI.playSound(SoundRegister.SOUND_MINERALGRINDER.get(), SoundCategory.BLOCKS, 0.5f, 1, pos);
	    }
	    int amount = getType() == DeferredRegisters.TILE_MINERALGRINDERDOUBLE.get() ? 2
		    : getType() == DeferredRegisters.TILE_MINERALGRINDERTRIPLE.get() ? 3 : 1;
	    for (int i = 0; i < amount; i++) {
		ItemStack stack = getProcessor(i).getInput();
		if (stack.getItem() instanceof BlockItem) {
		    BlockItem it = (BlockItem) stack.getItem();
		    Block block = it.getBlock();
		    double d4 = world.rand.nextDouble() * 12.0 / 16.0 + 0.5 - 6.0 / 16.0;
		    double d6 = world.rand.nextDouble() * 12.0 / 16.0 + 0.5 - 6.0 / 16.0;
		    ParticleAPI.addGrindedParticle(world, pos.getX() + d4, pos.getY() + 0.8, pos.getZ() + d6, 0.0D, 5D, 0.0D, block.getDefaultState(),
			    pos);
		}
	    }
	    clientRunningTicks++;
	}
    }
}
