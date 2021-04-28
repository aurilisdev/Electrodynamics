package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.api.sound.SoundAPI;
import electrodynamics.common.block.BlockGenericMachine;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.ContainerElectricFurnace;
import electrodynamics.common.inventory.container.ContainerElectricFurnaceDouble;
import electrodynamics.common.inventory.container.ContainerElectricFurnaceTriple;
import electrodynamics.common.item.ItemProcessorUpgrade;
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
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;

public class TileElectricFurnace extends GenericTileTicking {

    protected IRecipe<?> cachedRecipe = null;
    protected long timeSinceChange = 0;

    public TileElectricFurnace() {
	this(0);
    }

    public TileElectricFurnace(int extra) {
	super(extra == 1 ? DeferredRegisters.TILE_ELECTRICFURNACEDOUBLE.get()
		: extra == 2 ? DeferredRegisters.TILE_ELECTRICFURNACETRIPLE.get() : DeferredRegisters.TILE_ELECTRICFURNACE.get());
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentTickable().tickClient(this::tickClient));
	addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.NORTH)
		.voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * Math.pow(2, extra)));
	addComponent(new ComponentInventory(this).size(5 + extra * 2)
		.valid((slot, stack) -> (slot == 0 || slot == extra * 2 || extra == 2 && slot == 2)
			|| (slot != extra && slot != extra * 3 && slot != extra * 5) && stack.getItem() instanceof ItemProcessorUpgrade)
		.relativeFaceSlots(Direction.EAST, 0, extra * 2, extra * 4).relativeFaceSlots(Direction.UP, 0, extra * 2, extra * 4)
		.relativeFaceSlots(Direction.WEST, extra, extra * 2 - 1, extra * 3)
		.relativeFaceSlots(Direction.DOWN, extra, extra * 2 - 1, extra * 3));
	addComponent(new ComponentContainerProvider("container.electricfurnace" + extra).createMenu((id,
		player) -> (extra == 0 ? new ContainerElectricFurnace(id, player, getComponent(ComponentType.Inventory), getCoordsArray())
			: extra == 1 ? new ContainerElectricFurnaceDouble(id, player, getComponent(ComponentType.Inventory), getCoordsArray())
				: extra == 2 ? new ContainerElectricFurnaceTriple(id, player, getComponent(ComponentType.Inventory), getCoordsArray())
					: null)));
	if (extra == 0) {
	    ComponentProcessor pr = new ComponentProcessor(this).upgradeSlots(2, 3, 4).canProcess(this::canProcess)
		    .failed(component -> cachedRecipe = null).process(this::process).requiredTicks(Constants.ELECTRICFURNACE_REQUIRED_TICKS)
		    .usage(Constants.ELECTRICFURNACE_USAGE_PER_TICK).type(ComponentProcessorType.ObjectToObject);
	    addProcessor(pr);
	} else {
	    for (int i = 0; i <= extra; i++) {
		ComponentProcessor pr = new ComponentProcessor(this).upgradeSlots(extra * 2 + 2, extra * 2 + 3, extra * 2 + 4)
			.canProcess(this::canProcess).failed(component -> cachedRecipe = null).process(this::process)
			.requiredTicks(Constants.ELECTRICFURNACE_REQUIRED_TICKS).usage(Constants.ELECTRICFURNACE_USAGE_PER_TICK)
			.type(ComponentProcessorType.ObjectToObject);
		addProcessor(pr);
		pr.inputSlot(i * 2);
		pr.outputSlot(i * 2 + 1);
	    }
	}
    }

    protected void process(ComponentProcessor component) {
	ComponentInventory inv = getComponent(ComponentType.Inventory);
	ItemStack output = component.getOutput();
	ItemStack result = cachedRecipe.getRecipeOutput();
	if (!output.isEmpty()) {
	    output.setCount(output.getCount() + result.getCount());
	} else {
	    component.output(result.copy());
	}
	ItemStack input = component.getInput();
	input.shrink(1);
	if (input.getCount() == 0) {
	    inv.setInventorySlotContents(0, ItemStack.EMPTY);
	}
    }

    protected boolean canProcess(ComponentProcessor component) {
	timeSinceChange++;
	if (this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic).getJoulesStored() >= component.getUsage()
		* component.operatingSpeed) {
	    if (timeSinceChange > 40 && getBlockState().getBlock() == DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnace)) {
		world.setBlockState(pos, DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnacerunning).getDefaultState()
			.with(BlockGenericMachine.FACING, getBlockState().get(BlockGenericMachine.FACING)), 2 | 16 | 32);
		timeSinceChange = 0;
	    }
	    if (!component.getInput().isEmpty()) {
		if (cachedRecipe != null && !cachedRecipe.getIngredients().get(0).test(component.getInput())) {
		    cachedRecipe = null;
		}
		boolean hasRecipe = cachedRecipe != null;
		if (!hasRecipe) {
		    for (IRecipe<?> recipe : world.getRecipeManager().getRecipes()) {
			if (recipe.getType() == IRecipeType.SMELTING && recipe.getIngredients().get(0).test(component.getInput())) {
			    hasRecipe = true;
			    cachedRecipe = recipe;
			}
		    }
		}
		if (hasRecipe && cachedRecipe.getIngredients().get(0).test(component.getInput())) {
		    ItemStack output = component.getOutput();
		    ItemStack result = cachedRecipe.getRecipeOutput();
		    return (output.isEmpty() || ItemStack.areItemsEqual(output, result))
			    && output.getCount() + result.getCount() <= output.getMaxStackSize();
		}
	    }
	} else if (timeSinceChange > 40
		&& getBlockState().getBlock() == DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnacerunning)) {
	    timeSinceChange = 0;
	    world.setBlockState(pos, DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnace).getDefaultState()
		    .with(BlockGenericMachine.FACING, getBlockState().get(BlockGenericMachine.FACING)), 2 | 16 | 32);
	}

	return false;
    }

    protected void tickClient(ComponentTickable tickable) {
	boolean has = getType() == DeferredRegisters.TILE_ELECTRICFURNACEDOUBLE.get()
		? getProcessor(0).operatingTicks + getProcessor(1).operatingTicks > 0
		: getType() == DeferredRegisters.TILE_ELECTRICFURNACETRIPLE.get()
			? getProcessor(0).operatingTicks + getProcessor(1).operatingTicks + getProcessor(2).operatingTicks > 0
			: getProcessor(0).operatingTicks > 0;
	if (has && world.rand.nextDouble() < 0.15) {
	    Direction direction = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
	    double d4 = world.rand.nextDouble();
	    double d5 = direction.getAxis() == Direction.Axis.X ? direction.getXOffset() * (direction.getXOffset() == -1 ? 0 : 1) : d4;
	    double d6 = world.rand.nextDouble();
	    double d7 = direction.getAxis() == Direction.Axis.Z ? direction.getZOffset() * (direction.getZOffset() == -1 ? 0 : 1) : d4;
	    world.addParticle(ParticleTypes.SMOKE, pos.getX() + d5, pos.getY() + d6, pos.getZ() + d7, 0.0D, 0.0D, 0.0D);
	}
	if (has && tickable.getTicks() % 200 == 0) {
	    SoundAPI.playSound(SoundRegister.SOUND_HUM.get(), SoundCategory.BLOCKS, 1, 1, pos);
	}
    }
}
