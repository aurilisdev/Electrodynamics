package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
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
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;

public class TileWireMill extends GenericTileTicking {
    public TileWireMill() {
	this(0);
    }

    public TileWireMill(int extra) {
	super(extra == 1 ? DeferredRegisters.TILE_WIREMILLDOUBLE.get()
		: extra == 2 ? DeferredRegisters.TILE_WIREMILLTRIPLE.get() : DeferredRegisters.TILE_WIREMILL.get());
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentTickable().tickClient(this::tickClient));
	addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.NORTH));
	addComponent(new ComponentInventory(this).size(5 + extra * 2)
		.valid((slot, stack) -> (slot == 0 || slot == extra * 2 || extra == 2 && slot == 2)
			|| (slot != extra && slot != extra * 3 && slot != extra * 5) && stack.getItem() instanceof ItemProcessorUpgrade)
		.relativeFaceSlots(Direction.EAST, 0, extra * 2, extra * 4).relativeFaceSlots(Direction.UP, 0, extra * 2, extra * 4)
		.relativeFaceSlots(Direction.WEST, extra, extra * 2 - 1, extra * 3)
		.relativeFaceSlots(Direction.DOWN, extra, extra * 2 - 1, extra * 3));
	addComponent(new ComponentContainerProvider("container.wiremill" + extra).createMenu((id, player) -> (extra == 0
		? new ContainerO2OProcessor(id, player, getComponent(ComponentType.Inventory), getCoordsArray())
		: extra == 1 ? new ContainerO2OProcessorDouble(id, player, getComponent(ComponentType.Inventory), getCoordsArray())
			: extra == 2 ? new ContainerO2OProcessorTriple(id, player, getComponent(ComponentType.Inventory), getCoordsArray()) : null)));
	if (extra == 0) {
	    ComponentProcessor pr = new ComponentProcessor(this).upgradeSlots(2, 3, 4)
		    .canProcess(component -> MachineRecipes.canProcess(this, component, DeferredRegisters.TILE_WIREMILL.get()))
		    .process(component -> MachineRecipes.process(this, component, DeferredRegisters.TILE_WIREMILL.get()))
		    .requiredTicks(Constants.WIREMILL_REQUIRED_TICKS).usage(Constants.WIREMILL_USAGE_PER_TICK)
		    .type(ComponentProcessorType.ObjectToObject);
	    addProcessor(pr);
	} else {
	    for (int i = 0; i <= extra; i++) {
		ComponentProcessor pr = new ComponentProcessor(this).upgradeSlots(extra * 2 + 2, extra * 2 + 3, extra * 2 + 4)
			.canProcess(component -> MachineRecipes.canProcess(this, component, DeferredRegisters.TILE_WIREMILL.get()))
			.process(component -> MachineRecipes.process(this, component, DeferredRegisters.TILE_WIREMILL.get()))
			.requiredTicks(Constants.WIREMILL_REQUIRED_TICKS).usage(Constants.WIREMILL_USAGE_PER_TICK)
			.type(ComponentProcessorType.ObjectToObject);
		addProcessor(pr);
		pr.inputSlot(i * 2);
		pr.outputSlot(i * 2 + 1);
	    }
	}
    }

    protected void tickClient(ComponentTickable tickable) {
	boolean has = getType() == DeferredRegisters.TILE_ELECTRICFURNACEDOUBLE.get()
		? getProcessor(0).operatingTicks + getProcessor(1).operatingTicks > 0
		: getType() == DeferredRegisters.TILE_ELECTRICFURNACETRIPLE.get()
			? getProcessor(0).operatingTicks + getProcessor(1).operatingTicks + getProcessor(2).operatingTicks > 0
			: getProcessor(0).operatingTicks > 0;
	if (has) {
	    if (world.rand.nextDouble() < 0.15) {
		world.addParticle(ParticleTypes.SMOKE, pos.getX() + world.rand.nextDouble(), pos.getY() + world.rand.nextDouble() * 0.5 + 0.5,
			pos.getZ() + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
	    }
	    if (tickable.getTicks() % 200 == 0) {
		SoundAPI.playSound(SoundRegister.SOUND_HUM.get(), SoundCategory.BLOCKS, 1, 1, pos);
	    }
	}
    }

}
