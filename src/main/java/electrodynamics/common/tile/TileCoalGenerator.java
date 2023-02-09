package electrodynamics.common.tile;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.common.block.BlockMachine;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerCoalGenerator;
import electrodynamics.common.reloadlistener.CoalGeneratorFuelRegister;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.tile.generic.GenericGeneratorTile;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TargetValue;
import electrodynamics.prefab.utilities.object.TransferPack;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;

public class TileCoalGenerator extends GenericGeneratorTile {
	protected CachedTileOutput output;
	protected TransferPack currentOutput = TransferPack.EMPTY;
	public TargetValue heat = new TargetValue(property(new Property<Double>(PropertyType.Double, "heat", 27.0)));
	public Property<Integer> burnTime = property(new Property<Integer>(PropertyType.Integer, "burnTime", 0));
	public Property<Integer> maxBurnTime = property(new Property<Integer>(PropertyType.Integer, "maxBurnTime", 1));
	//for future planned upgrades
	private Property<Double> multiplier = property(new Property<Double>(PropertyType.Double, "multiplier", 1.0));

	public TileCoalGenerator(BlockPos worldPosition, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_COALGENERATOR.get(), worldPosition, blockState, 1.0);
		addComponent(new ComponentDirection());
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentTickable().tickClient(this::tickClient).tickServer(this::tickServer));
		addComponent(new ComponentElectrodynamic(this).relativeOutput(Direction.NORTH));
		addComponent(new ComponentInventory(this).size(1).slotFaces(0, Direction.UP, Direction.EAST, Direction.WEST, Direction.SOUTH, Direction.NORTH).valid((index, stack, i) -> getValidItems().contains(stack.getItem())));
		addComponent(new ComponentContainerProvider(SubtypeMachine.coalgenerator).createMenu((id, player) -> new ContainerCoalGenerator(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}

	protected void tickServer(ComponentTickable tickable) {
		if (burnTime.get() > 0) {
			burnTime.set(burnTime.get() - 1);
		}
		ComponentDirection direction = getComponent(ComponentType.Direction);
		if (output == null) {
			output = new CachedTileOutput(level, worldPosition.relative(direction.getDirection().getOpposite()));
		}
		if (tickable.getTicks() % 20 == 0) {
			output.update(worldPosition.relative(direction.getDirection().getOpposite()));
		}
		ComponentInventory inv = getComponent(ComponentType.Inventory);
		ItemStack fuel = inv.getItem(0);
		if (burnTime.get() <= 0 && !fuel.isEmpty()) {
			burnTime.set(ForgeHooks.getBurnTime(fuel, null));
			fuel.shrink(1);
			maxBurnTime.set(Math.max(burnTime.get(), 1));
		}
		BlockMachine machine = (BlockMachine) getBlockState().getBlock();
		if (machine != null) {
			boolean greaterBurnTime = burnTime.get() > 0;
			if(BlockEntityUtils.isLit(this) ^ greaterBurnTime) {
				BlockEntityUtils.updateLit(this, greaterBurnTime);
			}
		}
		if (heat.getValue().get() > 27 && output.valid()) {
			ElectricityUtils.receivePower(output.getSafe(), direction.getDirection(), currentOutput, false);
		}
		heat.rangeParameterize(27, 3000, burnTime.get() > 0 ? 3000 : 27, heat.getValue().get(), 600).flush();
		currentOutput = getProduced();
	}

	protected void tickClient(ComponentTickable tickable) {
		if (getBlockState().getValue(BlockMachine.ON)) {
			Direction dir = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
			if (level.random.nextInt(10) == 0) {
				level.playLocalSound(worldPosition.getX() + 0.5D, worldPosition.getY() + 0.5D, worldPosition.getZ() + 0.5D, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 0.5F + level.random.nextFloat(), level.random.nextFloat() * 0.7F + 0.6F, false);
			}

			if (level.random.nextInt(10) == 0) {
				for (int i = 0; i < level.random.nextInt(1) + 1; ++i) {
					level.addParticle(ParticleTypes.LAVA, worldPosition.getX() + 0.5D, worldPosition.getY() + 0.5D, worldPosition.getZ() + 0.5D, dir.getStepX(), 0.0, dir.getStepZ());
				}
			}
		}
	}

	@Override
	public double getMultiplier() {
		return multiplier.get();
	}

	@Override
	public void setMultiplier(double val) {
		multiplier.set(val);
	}

	@Override
	public TransferPack getProduced() {
		return TransferPack.ampsVoltage(multiplier.get() * Constants.COALGENERATOR_MAX_OUTPUT.getAmps() * ((heat.getValue().get() - 27.0) / (3000.0 - 27.0)), Constants.COALGENERATOR_MAX_OUTPUT.getVoltage());
	}

	public static List<Item> getValidItems() {
		return new ArrayList<>(CoalGeneratorFuelRegister.INSTANCE.getFuels());
	}
}
