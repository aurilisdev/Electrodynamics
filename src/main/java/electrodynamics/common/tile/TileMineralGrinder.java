package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.sound.DistanceSound;
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
import electrodynamics.client.particle.GrindedParticle;
import electrodynamics.common.inventory.container.ContainerO2OProcessor;
import electrodynamics.common.item.ItemProcessorUpgrade;
import electrodynamics.common.recipe.MachineRecipes;
import electrodynamics.common.settings.Constants;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;

public class TileMineralGrinder extends GenericTileTicking {
    public long clientRunningTicks = 0;

    public TileMineralGrinder() {
	super(DeferredRegisters.TILE_MINERALGRINDER.get());
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentTickable().tickClient(this::tickClient));
	addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.NORTH));
	addComponent(new ComponentInventory(this).size(5).relativeSlotFaces(0, Direction.EAST, Direction.UP)
		.relativeSlotFaces(1, Direction.WEST, Direction.DOWN)
		.valid((slot, stack) -> slot == 0 || slot != 1 && stack.getItem() instanceof ItemProcessorUpgrade).shouldSendInfo());
	addComponent(new ComponentContainerProvider("container.mineralgrinder")
		.createMenu((id, player) -> new ContainerO2OProcessor(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	addComponent(new ComponentProcessor(this).upgradeSlots(2, 3, 4).canProcess(component -> MachineRecipes.canProcess(this))
		.process(component -> MachineRecipes.process(this)).requiredTicks(Constants.MINERALGRINDER_REQUIRED_TICKS)
		.usage(Constants.MINERALGRINDER_USAGE_PER_TICK).type(ComponentProcessorType.ObjectToObject));
    }

    protected void tickClient(ComponentTickable tickable) {
	ComponentProcessor processor = getComponent(ComponentType.Processor);
	if (processor.operatingTicks > 0) {
	    if (world.rand.nextDouble() < 0.15) {
		world.addParticle(ParticleTypes.SMOKE, pos.getX() + world.rand.nextDouble(), pos.getY() + world.rand.nextDouble() * 0.2 + 0.8,
			pos.getZ() + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
	    }
	    if (tickable.getTicks() % 200 == 0) {
		Minecraft.getInstance().getSoundHandler()
			.play(new DistanceSound(DeferredRegisters.SOUND_MINERALGRINDER.get(), SoundCategory.BLOCKS, 0.5f, 1, pos));
	    }
	    ItemStack stack = processor.getInput();
	    if (stack.getItem() instanceof BlockItem) {
		BlockItem it = (BlockItem) stack.getItem();
		Block block = it.getBlock();
		double d4 = world.rand.nextDouble() * 12.0 / 16.0 + 0.5 - 6.0 / 16.0;
		double d6 = world.rand.nextDouble() * 12.0 / 16.0 + 0.5 - 6.0 / 16.0;
		Minecraft.getInstance().particles.addEffect(new GrindedParticle((ClientWorld) world, pos.getX() + d4, pos.getY() + 0.8,
			pos.getZ() + d6, 0.0D, 5D, 0.0D, block.getDefaultState()).setBlockPos(pos));
	    }
	    clientRunningTicks++;
	}
    }
}
