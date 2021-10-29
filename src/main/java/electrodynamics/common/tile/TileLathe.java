package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.api.particle.ParticleAPI;
import electrodynamics.api.sound.SoundAPI;
import electrodynamics.common.inventory.container.ContainerO2OProcessor;
import electrodynamics.common.item.ItemProcessorUpgrade;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.recipe.categories.o2o.specificmachines.LatheRecipe;
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
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class TileLathe extends GenericTileTicking {

    public long clientRunningTicks = 0;

    public TileLathe(BlockPos worldPosition, BlockState blockState) {
	super(DeferredRegisters.TILE_LATHE.get(), worldPosition, blockState);
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentTickable().tickClient(this::tickClient));
	addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.NORTH).voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * 2)
		.maxJoules(Constants.LATHE_USAGE_PER_TICK * 20));
	addComponent(new ComponentInventory(this).size(5)
		.valid((slot, stack) -> slot < 1 || slot > 1 && stack.getItem() instanceof ItemProcessorUpgrade).setMachineSlots(0).shouldSendInfo());
	addComponent(new ComponentContainerProvider("container.lathe")
		.createMenu((id, player) -> new ContainerO2OProcessor(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	addProcessor(new ComponentProcessor(this).upgradeSlots(2, 3, 4)
		.canProcess(component -> component.canProcessO2ORecipe(component, LatheRecipe.class, ElectrodynamicsRecipeInit.LATHE_TYPE))
		.process(component -> component.processO2ORecipe(component, LatheRecipe.class)).requiredTicks(Constants.LATHE_REQUIRED_TICKS)
		.usage(Constants.LATHE_USAGE_PER_TICK).type(ComponentProcessorType.ObjectToObject));
    }

    protected void tickClient(ComponentTickable tickable) {
	if (getProcessor(0).operatingTicks > 0) {
	    Direction direction = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
	    if (level.random.nextDouble() < 0.10) {
		for (int i = 0; i < 5; i++) {
		    double d4 = level.random.nextDouble() * 4.0 / 16.0 + 0.5 - 2.0 / 16.0;
		    double d6 = level.random.nextDouble() * 4.0 / 16.0 + 0.5 - 2.0 / 16.0;
		    ParticleAPI.addGrindedParticle(level, worldPosition.getX() + d4 + direction.getStepX() * 0.2, worldPosition.getY() + 0.7,
			    worldPosition.getZ() + d6 + direction.getStepZ() * 0.2, 0.0D, 0.0D, 0.0D, Blocks.IRON_BLOCK.defaultBlockState(),
			    worldPosition);
		}
	    }
	    double progress = Math.sin(0.05 * Math.PI * (clientRunningTicks % 20));
	    if (progress == 1) {
		SoundAPI.playSound(SoundRegister.SOUND_LATHEPLAYING.get(), SoundSource.BLOCKS, 5, .75f, worldPosition);
	    }
	    clientRunningTicks++;
	}
    }

}
