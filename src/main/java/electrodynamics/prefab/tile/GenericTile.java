package electrodynamics.prefab.tile;

import java.util.Set;

import javax.annotation.Nullable;

import electrodynamics.api.References;
import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.categories.do2o.DO2ORecipe;
import electrodynamics.common.recipe.categories.fluid2item.Fluid2ItemRecipe;
import electrodynamics.common.recipe.categories.fluid3items2item.Fluid3Items2ItemRecipe;
import electrodynamics.common.recipe.categories.fluiditem2fluid.FluidItem2FluidRecipe;
import electrodynamics.common.recipe.categories.fluiditem2item.FluidItem2ItemRecipe;
import electrodynamics.common.recipe.categories.o2o.O2ORecipe;
import electrodynamics.prefab.tile.components.Component;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentName;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.utils.AbstractFluidHandler;
import electrodynamics.prefab.utilities.Scheduler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.Nameable;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;

public class GenericTile extends BlockEntity implements Nameable {
    private Component[] components = new Component[ComponentType.values().length];
    private ComponentProcessor[] processors = new ComponentProcessor[5];

    public boolean hasComponent(ComponentType type) {
	return components[type.ordinal()] != null;
    }

    public <T extends Component> T getComponent(ComponentType type) {
	return !hasComponent(type) ? null : (T) components[type.ordinal()];
    }

    public ComponentProcessor getProcessor(int id) {
	return processors[id];
    }

    public GenericTile addProcessor(ComponentProcessor processor) {
	for (int i = 0; i < processors.length; i++) {
	    if (processors[i] == null) {
		processors[i] = processor;
		processor.holder(this);
		break;
	    }
	}
	return this;
    }

    public GenericTile addComponent(Component component) {
	component.holder(this);
	if (hasComponent(component.getType())) {
	    throw new ExceptionInInitializerError("Component of type: " + component.getType().name() + " already registered!");
	}
	components[component.getType().ordinal()] = component;
	return this;
    }

    @Deprecated(forRemoval = false, since = "Try not using this method.")
    public GenericTile forceComponent(Component component) {
	component.holder(this);
	components[component.getType().ordinal()] = component;
	return this;
    }

    @Override
    public void load(CompoundTag compound) {
	for (Component component : components) {
	    if (component != null) {
		component.holder(this);
		component.loadFromNBT(compound);
	    }
	}
	for (ComponentProcessor pr : processors) {
	    if (pr != null) {
		pr.holder(this);
		pr.loadFromNBT(compound);
	    }
	}
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
	for (Component component : components) {
	    if (component != null) {
		component.holder(this);
		component.saveToNBT(compound);
	    }
	}
	for (ComponentProcessor pr : processors) {
	    if (pr != null) {
		pr.holder(this);
		pr.saveToNBT(compound);
	    }
	}
	return super.save(compound);
    }

    protected GenericTile(BlockEntityType<?> tileEntityTypeIn, BlockPos worldPos, BlockState blockState) {
	super(tileEntityTypeIn, worldPos, blockState);
    }

    @Override
    public void onLoad() {
	super.onLoad();
	// JSON recipe fluids have to be added at load time
	if (hasComponent(ComponentType.FluidHandler)) {
	    AbstractFluidHandler<?> tank = this.getComponent(ComponentType.FluidHandler);
	    tank.addFluids();
	}
	if (hasComponent(ComponentType.PacketHandler)) {
	    Scheduler.schedule(1, () -> {
		this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendCustomPacket();
		this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
	    });
	}
    }

    @Override
    public net.minecraft.network.chat.Component getName() {
	return hasComponent(ComponentType.Name) ? this.<ComponentName>getComponent(ComponentType.Name).getName()
		: new TextComponent(References.ID + ".default.tile.name");
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
	if (cap == CapabilityElectrodynamic.ELECTRODYNAMIC && components[ComponentType.Electrodynamic.ordinal()] != null) {
	    return components[ComponentType.Electrodynamic.ordinal()].getCapability(cap, side);
	}
	if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && components[ComponentType.FluidHandler.ordinal()] != null) {
	    return components[ComponentType.FluidHandler.ordinal()].getCapability(cap, side);
	}
	if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && components[ComponentType.Inventory.ordinal()] != null) {
	    return components[ComponentType.Inventory.ordinal()].getCapability(cap, side);
	}
	return super.getCapability(cap, side);
    }

    @Override
    public void setRemoved() {
	super.setRemoved();
	for (Component component : components) {
	    if (component != null) {
		component.holder(this);
		component.remove();
	    }
	}
	for (ComponentProcessor pr : processors) {
	    if (pr != null) {
		pr.holder(this);
		pr.remove();
	    }
	}
    }

    public SimpleContainerData getCoordsArray() {
	SimpleContainerData array = new SimpleContainerData(3);
	array.set(0, worldPosition.getX());
	array.set(1, worldPosition.getY());
	array.set(2, worldPosition.getZ());
	return array;
    }

    @Override
    public BlockPos getBlockPos() {
	return worldPosition;
    }

    @Nullable
    public <T extends O2ORecipe> T getO2ORecipe(ComponentProcessor pr, Class<T> recipeClass, RecipeType<?> typeIn) {
	ItemStack stack = pr.getInput();
	if (stack == null || stack.equals(new ItemStack(Items.AIR), true)) {
	    return null;
	}
	Set<Recipe<?>> recipes = ElectrodynamicsRecipe.findRecipesbyType(typeIn, pr.getHolder().level);
	for (Recipe<?> iRecipe : recipes) {
	    T recipe = recipeClass.cast(iRecipe);
	    if (recipe.matchesRecipe(pr)) {
		return recipe;
	    }
	}
	return null;
    }

    public <T extends DO2ORecipe> T getDO2ORecipe(ComponentProcessor pr, Class<T> recipeClass, RecipeType<?> typeIn) {
	ItemStack[] stack = new ItemStack[] { pr.getInput(), pr.getSecondInput() };
	if (stack[0] == null || stack[0].equals(new ItemStack(Items.AIR), true) || stack[1] == null
		|| stack[1].equals(new ItemStack(Items.AIR), true)) {
	    return null;
	}
	Set<Recipe<?>> recipes = ElectrodynamicsRecipe.findRecipesbyType(typeIn, pr.getHolder().level);
	for (Recipe<?> iRecipe : recipes) {
	    T recipe = recipeClass.cast(iRecipe);
	    if (recipe.matchesRecipe(pr)) {
		return recipe;
	    }
	}
	return null;
    }

    public <T extends FluidItem2FluidRecipe> T getFluidItem2FluidRecipe(ComponentProcessor pr, Class<T> recipeClass, RecipeType<?> typeIn) {
	ItemStack stack = pr.getInput();
	if (stack == null || stack.equals(new ItemStack(Items.AIR), true)) {
	    return null;
	}

	ComponentFluidHandlerMulti fluidHandler = pr.getHolder().getComponent(ComponentType.FluidHandler);
	for (FluidTank fluidTank : fluidHandler.getInputFluidTanks()) {
	    if (fluidTank.getCapacity() > 0) {
		break;
	    }
	    return null;
	}

	Set<Recipe<?>> recipes = ElectrodynamicsRecipe.findRecipesbyType(typeIn, pr.getHolder().level);
	for (Recipe<?> iRecipe : recipes) {
	    T recipe = recipeClass.cast(iRecipe);
	    if (recipe.matchesRecipe(pr)) {
		return recipe;
	    }
	}
	return null;
    }

    public <T extends FluidItem2ItemRecipe> T getFluidItem2ItemRecipe(ComponentProcessor pr, Class<T> recipeClass, RecipeType<?> typeIn) {
	ItemStack stack = pr.getInput();
	if (stack == null || stack.equals(new ItemStack(Items.AIR), true)) {
	    return null;
	}

	ComponentFluidHandlerMulti fluidHandler = pr.getHolder().getComponent(ComponentType.FluidHandler);
	for (FluidTank fluidTank : fluidHandler.getInputFluidTanks()) {
	    if (fluidTank.getCapacity() > 0) {
		break;
	    }
	    return null;
	}

	Set<Recipe<?>> recipes = ElectrodynamicsRecipe.findRecipesbyType(typeIn, pr.getHolder().level);
	for (Recipe<?> iRecipe : recipes) {
	    T recipe = recipeClass.cast(iRecipe);
	    if (recipe.matchesRecipe(pr)) {
		return recipe;
	    }
	}
	return null;
    }

    public <T extends Fluid3Items2ItemRecipe> T getFluid3Items2ItemRecipe(ComponentProcessor pr, Class<T> recipeClass, RecipeType<?> typeIn) {
	ItemStack stack = pr.getInput();
	if (stack == null || stack.equals(new ItemStack(Items.AIR), true)) {
	    return null;
	}

	ComponentFluidHandlerMulti fluidHandler = pr.getHolder().getComponent(ComponentType.FluidHandler);
	for (FluidTank fluidTank : fluidHandler.getInputFluidTanks()) {
	    if (fluidTank.getCapacity() > 0) {
		break;
	    }
	    return null;
	}

	Set<Recipe<?>> recipes = ElectrodynamicsRecipe.findRecipesbyType(typeIn, pr.getHolder().level);
	for (Recipe<?> iRecipe : recipes) {
	    T recipe = recipeClass.cast(iRecipe);
	    if (recipe.matchesRecipe(pr)) {
		return recipe;
	    }
	}
	return null;
    }

    public <T extends Fluid2ItemRecipe> T getFluid2ItemRecipe(ComponentProcessor pr, Class<T> recipeClass, RecipeType<?> typeIn) {
	ComponentFluidHandlerMulti fluidHandler = pr.getHolder().getComponent(ComponentType.FluidHandler);
	for (FluidTank fluidTank : fluidHandler.getInputFluidTanks()) {
	    if (fluidTank.getCapacity() > 0) {
		break;
	    }
	    return null;
	}
	Set<Recipe<?>> recipes = ElectrodynamicsRecipe.findRecipesbyType(typeIn, pr.getHolder().level);
	for (Recipe<?> iRecipe : recipes) {
	    T recipe = recipeClass.cast(iRecipe);
	    if (recipe.matchesRecipe(pr)) {
		return recipe;
	    }
	}
	return null;
    }

}
