package electrodynamics.common.tile.generic;

import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.api.item.IItemElectric;
import electrodynamics.common.inventory.container.ContainerChargerGeneric;
import electrodynamics.prefab.tile.GenericTileTicking;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.world.Explosion.Mode;

public abstract class TileGenericCharger extends GenericTileTicking {

    protected TileGenericCharger(TileEntityType<?> typeIn, int voltageMultiplier, String containerName) {
	super(typeIn);
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler().guiPacketReader(this::loadFromNBT).guiPacketWriter(this::saveToNBT));
	addComponent(new ComponentTickable().tickCommon(this::tickCommon));
	addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.NORTH)
		.voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * voltageMultiplier).maxJoules(1000.0 * voltageMultiplier));
	addComponent(new ComponentInventory(this).size(2).valid((slot, stack) -> slot < 1));
	addComponent(new ComponentContainerProvider("container.charger" + containerName)
		.createMenu((id, player) -> new ContainerChargerGeneric(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));

    }

    public void tickCommon(ComponentTickable tickable) {
	ComponentInventory inventory = getComponent(ComponentType.Inventory);
	ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
	ItemStack itemInput = inventory.getStackInSlot(0);
	if (!itemInput.isEmpty() && electro.getJoulesStored() == electro.getMaxJoulesStored() && itemInput.getItem() instanceof IItemElectric) {

	    IItemElectric electricItem = (IItemElectric) itemInput.getItem();
	    double recieveVoltage = electricItem.getElectricProperties().receive.getVoltage();
	    double machineVoltage = electro.getVoltage();

	    if (machineVoltage > recieveVoltage) {
		electricItem.overVoltage(TransferPack.joulesVoltage(electricItem.getElectricProperties().receive.getJoules(), machineVoltage));
		world.setBlockState(pos, Blocks.AIR.getDefaultState());
		world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 2f, Mode.DESTROY);
	    } else if (machineVoltage == recieveVoltage) {
		electricItem.receivePower(itemInput, TransferPack.joulesVoltage(electro.getJoulesStored(), machineVoltage), false);
		electro.extractPower(
			electricItem.receivePower(itemInput, TransferPack.joulesVoltage(electro.getJoulesStored(), machineVoltage), false), false);
	    } else {
		float underVoltRatio = (float) ((float) machineVoltage / recieveVoltage);
		float itemStoredRatio = (float) ((float) electricItem.getJoulesStored(itemInput) / electricItem.getElectricProperties().capacity);

		float x = Math.abs(itemStoredRatio / (itemStoredRatio - underVoltRatio + 0.00000001F/* ensures it's never zero */));
		float reductionCoef = getRationalFunctionValue(x);
		if (itemStoredRatio >= underVoltRatio) {
		    electricItem.extractPower(itemInput, electro.getJoulesStored() * reductionCoef, false);
		} else {
		    electricItem.receivePower(itemInput, TransferPack.joulesVoltage(electro.getJoulesStored() * reductionCoef, recieveVoltage),
			    false);
		    electro.extractPower(TransferPack.joulesVoltage(electro.getMaxJoulesStored() * reductionCoef, recieveVoltage), false);
		}
	    }
	    if (electricItem.getJoulesStored(itemInput) == electricItem.getElectricProperties().capacity && inventory.getStackInSlot(1).isEmpty()) {
		inventory.setInventorySlotContents(1, inventory.getStackInSlot(0).copy());
		inventory.getStackInSlot(0).shrink(1);
	    }
	    this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
	}

    }

    // to simulate undervolting a chargable object
    private static float getRationalFunctionValue(float x) {
	if (x >= 100.0F) {
	    return 0.0F;
	} else if (x <= 1.0F) {
	    return 1.0F;
	} else {
	    return 1 / x;
	}
    }

    protected void loadFromNBT(CompoundNBT nbt) {
	NonNullList<ItemStack> obj = this.<ComponentInventory>getComponent(ComponentType.Inventory).getItems();
	obj.clear();
	ItemStackHelper.loadAllItems(nbt, obj);
    }

    protected void saveToNBT(CompoundNBT nbt) {
	ItemStackHelper.saveAllItems(nbt, this.<ComponentInventory>getComponent(ComponentType.Inventory).getItems());
    }

}
