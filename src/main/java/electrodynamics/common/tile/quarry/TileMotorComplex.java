package electrodynamics.common.tile.quarry;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.common.block.VoxelShapes;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerMotorComplex;
import electrodynamics.common.item.ItemUpgrade;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.sound.SoundBarrierMethods;
import electrodynamics.prefab.sound.utils.ITickableSound;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.tile.components.type.ComponentInventory.InventoryBuilder;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TileMotorComplex extends GenericTile implements ITickableSound {

	// 10 ticks per block
	public static final int DEFAULT_SPEED = Math.min(Constants.MIN_QUARRYBLOCKS_PER_TICK, 100);
	// 1 tick per block
	public static final int MAX_SPEED = Math.max(Constants.MAX_QUARRYBLOCKS_PER_TICK, 1);

	private boolean isSoundPlaying = false;

	public final Property<Integer> speed = property(new Property<>(PropertyType.Integer, "speed", DEFAULT_SPEED));
	public final Property<Double> powerMultiplier = property(new Property<>(PropertyType.Double, "powerMultiplier", 1.0));
	public final Property<Boolean> isPowered = property(new Property<>(PropertyType.Boolean, "isPowered", false));

	public TileMotorComplex(BlockPos pos, BlockState state) {
		super(ElectrodynamicsBlockTypes.TILE_MOTORCOMPLEX.get(), pos, state);
		addComponent(new ComponentDirection(this));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickClient(this::tickClient));
		addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.SOUTH).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 2).maxJoules(Constants.MOTORCOMPLEX_USAGE_PER_TICK * 10000));
		addComponent(new ComponentInventory(this, InventoryBuilder.newInv().upgrades(3)).validUpgrades(ContainerMotorComplex.VALID_UPGRADES).valid(machineValidator()));
		addComponent(new ComponentContainerProvider(SubtypeMachine.motorcomplex, this).createMenu((id, player) -> new ContainerMotorComplex(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}

	private void tickServer(ComponentTickable tick) {
		ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);

		if (electro.getJoulesStored() >= Constants.MOTORCOMPLEX_USAGE_PER_TICK * powerMultiplier.get()) {
			electro.joules(electro.getJoulesStored() - Constants.MOTORCOMPLEX_USAGE_PER_TICK * powerMultiplier.get());
			isPowered.set(true);
		} else {
			isPowered.set(false);
		}
	}

	private void tickClient(ComponentTickable tick) {
		if (shouldPlaySound() && !isSoundPlaying) {
			isSoundPlaying = true;
			SoundBarrierMethods.playTileSound(ElectrodynamicsSounds.SOUND_MOTORRUNNING.get(), this, true);
		}
	}

	@Override
	public void onInventoryChange(ComponentInventory inv, int slot) {
		super.onInventoryChange(inv, slot);
		if (inv.getUpgradeContents().size() > 0 && (slot >= inv.getUpgradeSlotStartIndex() || slot == -1)) {
			speed.set(DEFAULT_SPEED);
			powerMultiplier.set(1.0);
			for (ItemStack stack : inv.getUpgradeContents()) {
				if (!stack.isEmpty()) {
					for (int i = 0; i < stack.getCount(); i++) {
						switch (((ItemUpgrade) stack.getItem()).subtype) {
						case basicspeed:
							speed.set((int) Math.max((double) speed.get() * 0.8, MAX_SPEED));
							powerMultiplier.set(powerMultiplier.get() * 3);
							break;
						case advancedspeed:
							speed.set((int) Math.max((double) speed.get() * 0.5, MAX_SPEED));
							powerMultiplier.set(powerMultiplier.get() * 2);
							break;
						default:
							break;
						}
					}
				}
			}
		}
	}

	@Override
	public void setNotPlaying() {
		isSoundPlaying = false;
	}

	@Override
	public boolean shouldPlaySound() {
		return isPowered.get();
	}
	
	@Override
	public int getComparatorSignal() {
		return isPowered.get() ? 15 : 0;
	}

	static {
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.0625, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0.0625, 0, 0.1875, 1, 0.125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0.0625, 0.875, 0.1875, 1, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0.875, 0.125, 0.1875, 1, 0.875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.0625, 0.125, 0.125, 0.125, 0.875, 0.875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0.25, 0.25, 0.0625, 0.75, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0.25, 0.25, 0.1875, 0.75, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0.0625, 0.125, 0.1875, 0.125, 0.875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.5625, 0.0625, 0.03125, 0.8125, 0.625, 0.125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.5625, 0.0625, 0.875, 0.8125, 0.625, 0.96875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0.4375, 0, 0.5625, 0.5625, 0.125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0.4375, 0.875, 0.5625, 0.5625, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0.875, 0.4375, 0.4375, 1, 0.5625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.5, 0.0625, 0.75, 0.875, 0.125, 0.875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.5, 0.0625, 0.125, 0.875, 0.125, 0.25), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.5, 0.125, 0.71875, 0.875, 0.1875, 0.78125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.5, 0.125, 0.21875, 0.875, 0.1875, 0.28125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.4375, 0.125, 0.3125, 0.9375, 0.875, 0.6875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.4375, 0.3125, 0.125, 0.9375, 0.6875, 0.875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.4375, 0.6875, 0.6875, 0.9375, 0.75, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.4375, 0.75, 0.6875, 0.9375, 0.8125, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.4375, 0.1875, 0.6875, 0.9375, 0.25, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.4375, 0.25, 0.6875, 0.9375, 0.3125, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.4375, 0.75, 0.25, 0.9375, 0.8125, 0.3125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.4375, 0.6875, 0.1875, 0.9375, 0.75, 0.3125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.4375, 0.25, 0.1875, 0.9375, 0.3125, 0.3125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.4375, 0.1875, 0.25, 0.9375, 0.25, 0.3125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.9375, 0.25, 0.25, 1, 0.75, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.375, 0.3125, 0.25, 0.4375, 0.6875, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.375, 0.25, 0.3125, 0.4375, 0.3125, 0.6875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.375, 0.6875, 0.3125, 0.4375, 0.75, 0.6875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.75, 0.703125, 0.125, 0.875, 0.765625, 0.375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.5, 0.703125, 0.125, 0.625, 0.765625, 0.375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.5, 0.703125, 0.609375, 0.625, 0.765625, 0.859375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.75, 0.703125, 0.609375, 0.875, 0.765625, 0.859375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.4375, 0.875, 0.375, 0.8125, 0.953125, 0.625), BooleanOp.OR);
		VoxelShapes.registerShape(SubtypeMachine.motorcomplex, shape, Direction.EAST);

	}
}
