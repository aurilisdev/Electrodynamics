package electrodynamics.prefab.tile;

import java.util.Set;

import javax.annotation.Nullable;

import electrodynamics.api.References;
import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.categories.do2o.DO2ORecipe;
import electrodynamics.common.recipe.categories.fluid2item.Fluid2ItemRecipe;
import electrodynamics.common.recipe.categories.fluiditem2fluid.FluidItem2FluidRecipe;
import electrodynamics.common.recipe.categories.fluiditem2item.FluidItem2ItemRecipe;
import electrodynamics.common.recipe.categories.o2o.O2ORecipe;
import electrodynamics.prefab.tile.components.Component;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandler;
import electrodynamics.prefab.tile.components.type.ComponentName;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.utilities.Scheduler;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.INameable;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;

public class GenericTile extends TileEntity implements INameable {
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

    @Deprecated
    public GenericTile forceComponent(Component component) {
	component.holder(this);
	components[component.getType().ordinal()] = component;
	return this;
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
	super.read(state, compound);
	for (Component component : components) {
	    if (component != null) {
		component.holder(this);
		component.loadFromNBT(state, compound);
	    }
	}
	for (ComponentProcessor pr : processors) {
	    if (pr != null) {
		pr.holder(this);
		pr.loadFromNBT(state, compound);
	    }
	}
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
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
	return super.write(compound);
    }

    protected GenericTile(TileEntityType<?> tileEntityTypeIn) {
	super(tileEntityTypeIn);
    }

    @Override
    public void onLoad() {
	super.onLoad();
	if (hasComponent(ComponentType.PacketHandler)) {
	    Scheduler.schedule(1, () -> {
		this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendCustomPacket();
		this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
	    });

	}
    }

    @Override
    public ITextComponent getName() {
	return hasComponent(ComponentType.Name) ? this.<ComponentName>getComponent(ComponentType.Name).getName()
		: new StringTextComponent(References.ID + ".default.tile.name");
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
	if (cap == CapabilityElectrodynamic.ELECTRODYNAMIC) {
	    if (components[ComponentType.Electrodynamic.ordinal()] != null) {
		return components[ComponentType.Electrodynamic.ordinal()].getCapability(cap, side);
	    }
	}
	if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
	    if (components[ComponentType.FluidHandler.ordinal()] != null) {
		return components[ComponentType.FluidHandler.ordinal()].getCapability(cap, side);
	    }
	}
	if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
	    if (components[ComponentType.Inventory.ordinal()] != null) {
		return components[ComponentType.Inventory.ordinal()].getCapability(cap, side);
	    }
	}
	return super.getCapability(cap, side);
    }

    @Override
    public void remove() {
	super.remove();
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

    public IntArray getCoordsArray() {
	IntArray array = new IntArray(3);
	array.set(0, pos.getX());
	array.set(1, pos.getY());
	array.set(2, pos.getZ());
	return array;
    }

    @Override
    public BlockPos getPos() {
	return pos;
    }

    @Override
    public double getMaxRenderDistanceSquared() {
	return 256;
    }

    /**
     * Returns an O2O type Recipe based upon the input inventory of the TileEntity
     * in question
     * 
     * @param <T>         an O2O RecipeType subclass
     * @param pr          the component processor of the machine
     * @param recipeClass the Class of the RecipeType
     * @param typeIn      the RecipeType of the Recipe
     * @return a recipe. CAN BE NULL!
     */
    @Nullable
    public <T extends O2ORecipe> T getO2ORecipe(ComponentProcessor pr, Class<T> recipeClass, IRecipeType<?> typeIn) {
	ItemStack stack = pr.getInput();
	if (stack == null || stack.equals(new ItemStack(Items.AIR), true)) {
	    return null;
	}
	Set<IRecipe<?>> recipes = ElectrodynamicsRecipe.findRecipesbyType(typeIn, pr.getHolder().world);
	for (IRecipe<?> iRecipe : recipes) {
	    T recipe = recipeClass.cast(iRecipe);
	    if (recipe.matchesRecipe(pr)) {
		return recipe;
	    }
	}
	return null;
    }

    public <T extends DO2ORecipe> T getDO2ORecipe(ComponentProcessor pr, Class<T> recipeClass, IRecipeType<?> typeIn) {
	ItemStack[] stack = new ItemStack[] { pr.getInput(), pr.getSecondInput() };
	if (stack[0] == null || stack[0].equals(new ItemStack(Items.AIR), true)) {
	    return null;
	}
	if (stack[1] == null || stack[1].equals(new ItemStack(Items.AIR), true)) {
	    return null;
	}
	Set<IRecipe<?>> recipes = ElectrodynamicsRecipe.findRecipesbyType(typeIn, pr.getHolder().world);
	for (IRecipe<?> iRecipe : recipes) {
	    T recipe = recipeClass.cast(iRecipe);
	    if (recipe.matchesRecipe(pr)) {
		return recipe;
	    }
	}
	return null;
    }

    public <T extends FluidItem2FluidRecipe> T getFluidItem2FluidRecipe(ComponentProcessor pr, Class<T> recipeClass, IRecipeType<?> typeIn) {
	ItemStack stack = pr.getInput();
	if (stack == null || stack.equals(new ItemStack(Items.AIR), true)) {
	    return null;
	}

	ComponentFluidHandler fluidHandler = pr.getHolder().getComponent(ComponentType.FluidHandler);
	for (FluidTank fluidTank : fluidHandler.getFluidTanks()) {
	    if (fluidTank.getCapacity() > 0) {
		break;
	    }
	    return null;
	}

	Set<IRecipe<?>> recipes = ElectrodynamicsRecipe.findRecipesbyType(typeIn, pr.getHolder().world);
	for (IRecipe<?> iRecipe : recipes) {
	    T recipe = recipeClass.cast(iRecipe);
	    if (recipe.matchesRecipe(pr)) {
		return recipe;
	    }
	}
	return null;
    }

    public <T extends FluidItem2ItemRecipe> T getFluidItem2ItemRecipe(ComponentProcessor pr, Class<T> recipeClass, IRecipeType<?> typeIn) {
	ItemStack stack = pr.getInput();
	if (stack == null || stack.equals(new ItemStack(Items.AIR), true)) {
	    return null;
	}

	ComponentFluidHandler fluidHandler = pr.getHolder().getComponent(ComponentType.FluidHandler);
	for (FluidTank fluidTank : fluidHandler.getFluidTanks()) {
	    if (fluidTank.getCapacity() > 0) {
		break;
	    }
	    return null;
	}

	Set<IRecipe<?>> recipes = ElectrodynamicsRecipe.findRecipesbyType(typeIn, pr.getHolder().world);
	for (IRecipe<?> iRecipe : recipes) {
	    T recipe = recipeClass.cast(iRecipe);
	    if (recipe.matchesRecipe(pr)) {
		return recipe;
	    }
	}
	return null;
    }

    public <T extends Fluid2ItemRecipe> T getFluid2ItemRecipe(ComponentProcessor pr, Class<T> recipeClass, IRecipeType<?> typeIn) {
	ComponentFluidHandler fluidHandler = pr.getHolder().getComponent(ComponentType.FluidHandler);
	for (FluidTank fluidTank : fluidHandler.getFluidTanks()) {
	    if (fluidTank.getCapacity() > 0) {
		break;
	    }
	    return null;
	}
	Set<IRecipe<?>> recipes = ElectrodynamicsRecipe.findRecipesbyType(typeIn, pr.getHolder().world);
	for (IRecipe<?> iRecipe : recipes) {
	    T recipe = recipeClass.cast(iRecipe);
	    if (recipe.matchesRecipe(pr)) {
		return recipe;
	    }
	}
	return null;
    }

}
