package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.api.particle.ParticleAPI;
import electrodynamics.api.sound.SoundAPI;
import electrodynamics.common.inventory.container.ContainerO2OProcessor;
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

public class TileMineralCrusher extends GenericTileTicking {
    public long clientRunningTicks = 0;

    public TileMineralCrusher() {
	super(DeferredRegisters.TILE_MINERALCRUSHER.get());
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentTickable().tickClient(this::tickClient));
	addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.NORTH).voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * 2));
	addComponent(new ComponentInventory(this).size(5).relativeSlotFaces(0, Direction.EAST, Direction.UP)
		.relativeSlotFaces(1, Direction.WEST, Direction.DOWN)
		.valid((slot, stack) -> slot == 0 || slot != 1 && stack.getItem() instanceof ItemProcessorUpgrade).shouldSendInfo());
	addComponent(new ComponentContainerProvider("container.mineralcrusher")
		.createMenu((id, player) -> new ContainerO2OProcessor(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	addComponent(new ComponentProcessor(this).upgradeSlots(2, 3, 4).canProcess(component -> MachineRecipes.canProcess(this))
		.process(component -> MachineRecipes.process(this)).requiredTicks(Constants.MINERALCRUSHER_REQUIRED_TICKS)
		.usage(Constants.MINERALCRUSHER_USAGE_PER_TICK).type(ComponentProcessorType.ObjectToObject));
    }

    protected void tickClient(ComponentTickable tickable) {
	ComponentProcessor processor = getComponent(ComponentType.Processor);
	if (processor.operatingTicks > 0) {
	    Direction direction = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
	    if (world.rand.nextDouble() < 0.15) {
		double d4 = world.rand.nextDouble();
		double d5 = direction.getAxis() == Direction.Axis.X ? direction.getXOffset() * (direction.getXOffset() == -1 ? 0 : 1) : d4;
		double d6 = world.rand.nextDouble();
		double d7 = direction.getAxis() == Direction.Axis.Z ? direction.getZOffset() * (direction.getZOffset() == -1 ? 0 : 1) : d4;
		world.addParticle(ParticleTypes.SMOKE, pos.getX() + d5, pos.getY() + d6, pos.getZ() + d7, 0.0D, 0.0D, 0.0D);
	    }
	    double progress = Math.sin(0.05 * Math.PI * (clientRunningTicks % 20));
	    if (progress == 1) {
		SoundAPI.playSound(SoundRegister.SOUND_MINERALCRUSHER.get(), SoundCategory.BLOCKS, 5, .75f, pos);
	    } else if (progress < 0.3) {
		for (int i = 0; i < 5; i++) {
		    double d4 = world.rand.nextDouble() * 4.0 / 16.0 + 0.5 - 2.0 / 16.0;
		    double d6 = world.rand.nextDouble() * 4.0 / 16.0 + 0.5 - 2.0 / 16.0;
		    world.addParticle(ParticleTypes.SMOKE, pos.getX() + d4 + direction.getXOffset() * 0.2, pos.getY() + 0.4,
			    pos.getZ() + d6 + direction.getZOffset() * 0.2, 0.0D, 0.0D, 0.0D);
		}
		ItemStack stack = processor.getInput();
		if (stack.getItem() instanceof BlockItem) {
		    BlockItem it = (BlockItem) stack.getItem();
		    Block block = it.getBlock();
		    for (int i = 0; i < 5; i++) {
			double d4 = world.rand.nextDouble() * 4.0 / 16.0 + 0.5 - 2.0 / 16.0;
			double d6 = world.rand.nextDouble() * 4.0 / 16.0 + 0.5 - 2.0 / 16.0;
			ParticleAPI.addGrindedParticle(world, pos.getX() + d4 + direction.getXOffset() * 0.2, pos.getY() + 0.4,
				pos.getZ() + d6 + direction.getZOffset() * 0.2, 0.0D, 0.0D, 0.0D, block.getDefaultState(), pos);
		    }
		}
	    }
	    clientRunningTicks++;
	}
    }
}
