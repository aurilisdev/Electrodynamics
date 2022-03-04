package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.sound.SoundAPI;
import electrodynamics.common.inventory.container.tile.ContainerMotorComplex;
import electrodynamics.common.item.ItemUpgrade;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class TileMotorComplex extends GenericTile {

	// 10 ticks per block
	public static final double DEFAULT_SPEED = Math.min(Constants.MIN_QUARRYBLOCKS_PER_TICK, 100);
	// 1 tick per block
	public static final double MAX_SPEED = Math.max(Constants.MAX_QUARRYBLOCKS_PER_TICK, 1);

	public double speed = 1;
	public double clientSpeed = 1;
	public double powerMultiplier = 1;
	public double clientMultiplier = 1;

	public boolean isPowered = false;
	public boolean clientPowered = false;

	public TileMotorComplex(BlockPos pos, BlockState state) {
		super(DeferredRegisters.TILE_MOTORCOMPLEX.get(), pos, state);
		addComponent(new ComponentDirection());
		addComponent(new ComponentPacketHandler().customPacketWriter(this::createPacket).guiPacketWriter(this::createPacket).customPacketReader(this::readPacket).guiPacketReader(this::readPacket));
		addComponent(new ComponentTickable().tickServer(this::tickServer).tickClient(this::tickClient));
		addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.SOUTH).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 2).maxJoules(Constants.MOTORCOMPLEX_USAGE_PER_TICK * 1000));
		addComponent(new ComponentInventory(this).size(3).upgrades(3).validUpgrades(ContainerMotorComplex.VALID_UPGRADES).valid(machineValidator()));
		addComponent(new ComponentContainerProvider("container.motorcomplex").createMenu((id, player) -> new ContainerMotorComplex(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}

	private void tickServer(ComponentTickable tick) {
		if (tick.getTicks() % 5 == 0) {
			this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
		}
		speed = DEFAULT_SPEED;
		powerMultiplier = 1;
		ComponentInventory inv = getComponent(ComponentType.Inventory);
		// comes out to roughly 128 kW; max speed still needs to be obtainable in survival...
		for (ItemStack stack : inv.getUpgradeContents()) {
			if (!stack.isEmpty()) {
				for (int i = 0; i < stack.getCount(); i++) {
					switch (((ItemUpgrade) stack.getItem()).subtype) {
					case basicspeed:
						speed = Math.max(speed *= 0.8, MAX_SPEED);
						powerMultiplier *= 3;
						break;
					case advancedspeed:
						speed = Math.max(speed *= 0.5, MAX_SPEED);
						powerMultiplier *= 2;
						break;
					default:
						break;
					}
				}
			}
		}
		ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);

		if (electro.getJoulesStored() >= Constants.MOTORCOMPLEX_USAGE_PER_TICK * powerMultiplier) {
			electro.joules(electro.getJoulesStored() - Constants.MOTORCOMPLEX_USAGE_PER_TICK * powerMultiplier);
			isPowered = true;
		} else {
			isPowered = false;
		}
	}

	private void tickClient(ComponentTickable tick) {
		if (tick.getTicks() % 20 == 0 && clientPowered) {
			SoundAPI.playSound(SoundRegister.SOUND_MOTORRUNNING.get(), SoundSource.BLOCKS, 1.0F, 1.0F, worldPosition);
		}
	}

	private void createPacket(CompoundTag nbt) {
		nbt.putDouble("speed", speed);
		nbt.putDouble("multiplier", powerMultiplier);
		nbt.putBoolean("isPowered", isPowered);
	}

	private void readPacket(CompoundTag nbt) {
		clientSpeed = nbt.getDouble("speed");
		clientMultiplier = nbt.getDouble("multiplier");
		clientPowered = nbt.getBoolean("isPowered");
	}

}
