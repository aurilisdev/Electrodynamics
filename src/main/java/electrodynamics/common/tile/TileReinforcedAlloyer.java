package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.api.sound.SoundAPI;
import electrodynamics.common.block.BlockGenericMachine;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.ContainerDO2OProcessor;
import electrodynamics.common.item.ItemProcessorUpgrade;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.recipe.categories.do2o.specificmachines.ReinforcedAlloyerRecipe;
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

public class TileReinforcedAlloyer extends GenericTileTicking{
	
	public TileReinforcedAlloyer() {
		super(DeferredRegisters.TILE_REINFORCEDALLOYER.get());
		addComponent(new ComponentDirection());
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentTickable().tickClient(this::tickClient));
		addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.NORTH).voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * 4));
		addComponent(new ComponentInventory(this).size(6).faceSlots(Direction.UP, 0, 1).relativeFaceSlots(Direction.EAST, 1)
			.relativeSlotFaces(2, Direction.DOWN, Direction.WEST)
			.valid((slot, stack) -> slot < 2 || slot > 2 && stack.getItem() instanceof ItemProcessorUpgrade));
		addComponent(new ComponentContainerProvider("container.reinforcedalloyer")
			.createMenu((id, player) -> new ContainerDO2OProcessor(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));

		addComponent(new ComponentProcessor(this).upgradeSlots(3, 4, 5).canProcess(component -> canProcessReinfAlloy(component))
			.process(component -> component.processDO2ORecipe(component, ReinforcedAlloyerRecipe.class))
			.requiredTicks(Constants.REINFORCEDALLOYER_REQUIRED_TICKS).usage(Constants.REINFORCEDALLOYER_USAGE_PER_TICK)
			.type(ComponentProcessorType.DoubleObjectToObject));
    }

    protected boolean canProcessReinfAlloy(ComponentProcessor component) {
		if (component.canProcessDO2ORecipe(component, ReinforcedAlloyerRecipe.class, ElectrodynamicsRecipeInit.REINFORCED_ALLOYER_TYPE)) {
		    if (getBlockState().getBlock() == DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.reinforcedalloyer)) {
			world.setBlockState(pos, DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.reinforcedalloyerrunning).getDefaultState()
				.with(BlockGenericMachine.FACING, getBlockState().get(BlockGenericMachine.FACING)), 2 | 16 | 32);
		    }
		    return true;
		} else if (getBlockState().getBlock() == DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.reinforcedalloyerrunning)) {
		    world.setBlockState(pos, DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.reinforcedalloyer).getDefaultState()
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
		    SoundAPI.playSound(SoundRegister.SOUND_HUM.get(), SoundCategory.BLOCKS, 1, 1, pos);
		}
    }


}
