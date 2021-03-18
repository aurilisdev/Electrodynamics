package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.tile.GenericTileTicking;
import electrodynamics.api.tile.components.ComponentType;
import electrodynamics.api.tile.components.type.ComponentContainerProvider;
import electrodynamics.api.tile.components.type.ComponentDirection;
import electrodynamics.api.tile.components.type.ComponentElectrodynamic;
import electrodynamics.api.tile.components.type.ComponentInventory;
import electrodynamics.api.tile.components.type.ComponentPacketHandler;
import electrodynamics.api.tile.components.type.ComponentProcessor;
import electrodynamics.api.tile.components.type.ComponentProcessorType;
import electrodynamics.api.tile.components.type.ComponentTickable;
import electrodynamics.common.block.BlockGenericMachine;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.ContainerElectricFurnace;
import electrodynamics.common.item.ItemProcessorUpgrade;
import electrodynamics.common.settings.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
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
	super(DeferredRegisters.TILE_ELECTRICFURNACE.get());
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentTickable().addTickClient(this::tickClient));
	addComponent(new ComponentElectrodynamic(this).addRelativeInputDirection(Direction.NORTH));
	addComponent(new ComponentInventory().setInventorySize(5).addSlotsOnFace(Direction.UP, 0).addSlotsOnFace(Direction.DOWN, 1)
		.setItemValidPredicate((slot, stack) -> slot == 0 || slot != 1 && stack.getItem() instanceof ItemProcessorUpgrade)
		.addRelativeSlotsOnFace(Direction.EAST, 0).addRelativeSlotsOnFace(Direction.WEST, 1));
	addComponent(new ComponentContainerProvider("container.electricfurnace").setCreateMenuFunction(
		(id, player) -> new ContainerElectricFurnace(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	addComponent(new ComponentProcessor(this).addUpgradeSlots(2, 3, 4).setCanProcess(this::canProcess).setFailed(component -> cachedRecipe = null)
		.setProcess(this::process).setRequiredTicks(Constants.ELECTRICFURNACE_REQUIRED_TICKS)
		.setJoulesPerTick(Constants.ELECTRICFURNACE_USAGE_PER_TICK).setType(ComponentProcessorType.ObjectToObject));
    }

    protected void process(ComponentProcessor component) {
	ComponentInventory inv = getComponent(ComponentType.Inventory);
	ItemStack output = component.getOutput();
	ItemStack result = cachedRecipe.getRecipeOutput();
	if (!output.isEmpty()) {
	    output.setCount(output.getCount() + result.getCount());
	} else {
	    component.setOutput(result.copy());
	}
	ItemStack input = component.getInput();
	input.shrink(1);
	if (input.getCount() == 0) {
	    inv.setInventorySlotContents(0, ItemStack.EMPTY);
	}
    }

    protected boolean canProcess(ComponentProcessor component) {
	timeSinceChange++;
	if (this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic).getJoulesStored() >= component.getJoulesPerTick()
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
	ComponentProcessor processor = getComponent(ComponentType.Processor);
	if (processor.operatingTicks > 0 && world.rand.nextDouble() < 0.15) {
	    Direction direction = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
	    double d4 = world.rand.nextDouble();
	    double d5 = direction.getAxis() == Direction.Axis.X ? direction.getXOffset() * (direction.getXOffset() == -1 ? 0 : 1) : d4;
	    double d6 = world.rand.nextDouble();
	    double d7 = direction.getAxis() == Direction.Axis.Z ? direction.getZOffset() * (direction.getZOffset() == -1 ? 0 : 1) : d4;
	    world.addParticle(ParticleTypes.SMOKE, pos.getX() + d5, pos.getY() + d6, pos.getZ() + d7, 0.0D, 0.0D, 0.0D);
	}
	if (processor.operatingTicks > 0 && tickable.getTicks() % 200 == 0) {
	    Minecraft.getInstance().getSoundHandler().play(new SimpleSound(DeferredRegisters.SOUND_HUM.get(), SoundCategory.BLOCKS, 1, 1, pos));
	}
    }

}
