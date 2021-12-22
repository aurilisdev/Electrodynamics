package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.capability.electrodynamic.CapabilityElectrodynamic;
import electrodynamics.common.inventory.container.ContainerCobblestoneGenerator;
import electrodynamics.common.item.ItemUpgrade;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;

public class TileCobblestoneGenerator extends GenericTile {

	public boolean isPoweredClient;
	public double progressClient;

	private double processTime;
	private double progress;
	private boolean isPowered;

	public TileCobblestoneGenerator(BlockPos worldPos, BlockState blockState) {
		super(DeferredRegisters.TILE_COBBLESTONEGENERATOR.get(), worldPos, blockState);
		addComponent(new ComponentDirection());
		addComponent(new ComponentPacketHandler().customPacketWriter(this::createPacket).guiPacketWriter(this::createPacket)
				.customPacketReader(this::readPacket).guiPacketReader(this::readPacket));
		addComponent(new ComponentTickable().tickServer(this::tickServer));
		addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.DOWN).voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE)
				.maxJoules(Constants.COBBLE_GEN_USAGE_PER_TICK * 10));
		addComponent(new ComponentInventory(this).size(4).outputs(1).upgrades(3).valid(machineValidator()));
		addComponent(new ComponentContainerProvider("container.cobblestonegenerator")
				.createMenu((id, player) -> new ContainerCobblestoneGenerator(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}

	private void tickServer(ComponentTickable tick) {
		ComponentInventory inv = getComponent(ComponentType.Inventory);
		ItemStack output = inv.getOutputContents().get(0);
		double speed = 1;
		for(ItemStack upgrade : inv.getUpgradeContents()) {
			if(!upgrade.isEmpty() && upgrade.getItem() instanceof ItemUpgrade upg) {
				for (int i = 0; i < upgrade.getCount(); i++) {
					upg.subtype.applyUpgrade.accept(this, null, upgrade);
					if(upg.subtype == SubtypeItemUpgrade.advancedspeed ) {
						speed = Math.min(speed * 2.25, Math.pow(2.25, 3));
					} else if (upg.subtype == SubtypeItemUpgrade.basicspeed) {
						speed = Math.min(speed * 1.5, Math.pow(2.25, 3));;
					}
				}
			
			}
		}
		if(output.isEmpty() || output.getMaxStackSize() - output.getCount() > 0) {
			processTime += speed;
			ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
			electro.maxJoules(Constants.COBBLE_GEN_USAGE_PER_TICK * speed * 10);
			double usage = Constants.COBBLE_GEN_USAGE_PER_TICK * speed;
			if (electro.getJoulesStored() >= usage) {
				isPowered = true;
			} else {
				isPowered = false;
			}
			electro.joules(electro.getJoulesStored() - usage);
			if (isPowered) {
				progress = processTime / (Constants.COBBLE_GEN_REQUIRED_TICKS / speed);
				if (progress >= 1) {
					processTime = 0;
					if (output.isEmpty()) {
						inv.setItem(0, new ItemStack(Items.COBBLESTONE, 1).copy());
					} else {
						output.grow(1);
					}
				}
			}

		}
	}

	private void createPacket(CompoundTag nbt) {
		nbt.putDouble("progress", progress);
		nbt.putBoolean("isPowered", isPowered);
	}

	private void readPacket(CompoundTag nbt) {
		progressClient = nbt.getDouble("progress");
		isPoweredClient = nbt.getBoolean("isPowered");
	}

}
