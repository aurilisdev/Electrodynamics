package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.api.sound.SoundAPI;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.ContainerElectricFurnace;
import electrodynamics.common.inventory.container.ContainerElectricFurnaceDouble;
import electrodynamics.common.inventory.container.ContainerElectricFurnaceTriple;
import electrodynamics.common.item.ItemProcessorUpgrade;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.block.GenericEntityBlock;
import electrodynamics.prefab.tile.GenericTile;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class TileElectricFurnace extends GenericTile {

    protected Recipe<?> cachedRecipe = null;
    protected long timeSinceChange = 0;

    public TileElectricFurnace(BlockPos worldPosition, BlockState blockState) {
	this(0, worldPosition, blockState);
    }

    public TileElectricFurnace(int extra, BlockPos worldPosition, BlockState blockState) {
	super(extra == 1 ? DeferredRegisters.TILE_ELECTRICFURNACEDOUBLE.get()
		: extra == 2 ? DeferredRegisters.TILE_ELECTRICFURNACETRIPLE.get() : DeferredRegisters.TILE_ELECTRICFURNACE.get(), worldPosition,
		blockState);
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentTickable().tickClient(this::tickClient));
	addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.NORTH)
		.voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * Math.pow(2, extra))
		.maxJoules(Constants.ELECTRICFURNACE_USAGE_PER_TICK * 20 * (extra + 1)));
	addComponent(new ComponentInventory(this).size(5 + extra * 2)
		.valid((slot, stack) -> slot == 0 || slot == extra * 2 || extra == 2 && slot == 2
			|| slot != extra && slot != extra * 3 && slot != extra * 5 && stack.getItem() instanceof ItemProcessorUpgrade)
		.setMachineSlots(extra).shouldSendInfo());
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
	ItemStack result = cachedRecipe.getResultItem();
	if (!output.isEmpty()) {
	    output.setCount(output.getCount() + result.getCount());
	} else {
	    component.output(result.copy());
	}
	ItemStack input = component.getInput();
	input.shrink(1);
	if (input.getCount() == 0) {
	    inv.setItem(component.getInputOne(), ItemStack.EMPTY);
	}
    }

    protected boolean canProcess(ComponentProcessor component) {
	timeSinceChange++;
	if (this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic).getJoulesStored() >= component.getUsage()
		* component.operatingSpeed) {
	    if (timeSinceChange > 40) {
		Block bl = getBlockState().getBlock();
		if (bl == DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnace)) {
		    level.setBlock(worldPosition, DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnacerunning)
			    .defaultBlockState().setValue(GenericEntityBlock.FACING, getBlockState().getValue(GenericEntityBlock.FACING)),
			    2 | 16 | 32);
		} else if (bl == DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnacedouble)) {
		    level.setBlock(
			    worldPosition, DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnacedoublerunning)
				    .defaultBlockState().setValue(GenericEntityBlock.FACING, getBlockState().getValue(GenericEntityBlock.FACING)),
			    2 | 16 | 32);
		} else if (bl == DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnacetriple)) {
		    level.setBlock(
			    worldPosition, DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnacetriplerunning)
				    .defaultBlockState().setValue(GenericEntityBlock.FACING, getBlockState().getValue(GenericEntityBlock.FACING)),
			    2 | 16 | 32);
		}
		// TODO: This system should probably change to blockstate properties
		timeSinceChange = 0;
	    }
	    if (!component.getInput().isEmpty()) {
		if (cachedRecipe != null && !cachedRecipe.getIngredients().get(0).test(component.getInput())) {
		    cachedRecipe = null;
		}
		boolean hasRecipe = cachedRecipe != null;
		if (!hasRecipe) {
		    for (Recipe<?> recipe : level.getRecipeManager().getRecipes()) {
			if (recipe.getType() == RecipeType.SMELTING && recipe.getIngredients().get(0).test(component.getInput())) {
			    hasRecipe = true;
			    cachedRecipe = recipe;
			}
		    }
		}
		if (hasRecipe && cachedRecipe.getIngredients().get(0).test(component.getInput())) {
		    ItemStack output = component.getOutput();
		    ItemStack result = cachedRecipe.getResultItem();
		    return (output.isEmpty() || ItemStack.isSame(output, result))
			    && output.getCount() + result.getCount() <= output.getMaxStackSize();
		}
	    }
	} else if (timeSinceChange > 40) {
	    timeSinceChange = 0;
	    Block bl = getBlockState().getBlock();
	    if (bl == DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnacerunning)) {
		level.setBlock(worldPosition, DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnace).defaultBlockState()
			.setValue(GenericEntityBlock.FACING, getBlockState().getValue(GenericEntityBlock.FACING)), 2 | 16 | 32);
	    } else if (bl == DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnacedoublerunning)) {
		level.setBlock(worldPosition, DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnacedouble).defaultBlockState()
			.setValue(GenericEntityBlock.FACING, getBlockState().getValue(GenericEntityBlock.FACING)), 2 | 16 | 32);
	    } else if (bl == DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnacetriplerunning)) {
		level.setBlock(worldPosition, DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnacetriple).defaultBlockState()
			.setValue(GenericEntityBlock.FACING, getBlockState().getValue(GenericEntityBlock.FACING)), 2 | 16 | 32);
	    }
	    // TODO: This system should probably change to blockstate properties
	}

	return false;
    }

    protected void tickClient(ComponentTickable tickable) {
	boolean has = getType() == DeferredRegisters.TILE_ELECTRICFURNACEDOUBLE.get()
		? getProcessor(0).operatingTicks + getProcessor(1).operatingTicks > 0
		: getType() == DeferredRegisters.TILE_ELECTRICFURNACETRIPLE.get()
			? getProcessor(0).operatingTicks + getProcessor(1).operatingTicks + getProcessor(2).operatingTicks > 0
			: getProcessor(0).operatingTicks > 0;
	if (has && level.random.nextDouble() < 0.15) {
	    Direction direction = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
	    double d4 = level.random.nextDouble();
	    double d5 = direction.getAxis() == Direction.Axis.X ? direction.getStepX() * (direction.getStepX() == -1 ? 0 : 1) : d4;
	    double d6 = level.random.nextDouble();
	    double d7 = direction.getAxis() == Direction.Axis.Z ? direction.getStepZ() * (direction.getStepZ() == -1 ? 0 : 1) : d4;
	    level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + d5, worldPosition.getY() + d6, worldPosition.getZ() + d7, 0.0D, 0.0D, 0.0D);
	}
	if (has && tickable.getTicks() % 200 == 0) {
	    SoundAPI.playSound(SoundRegister.SOUND_HUM.get(), SoundSource.BLOCKS, 1, 1, worldPosition);
	}
    }
}
