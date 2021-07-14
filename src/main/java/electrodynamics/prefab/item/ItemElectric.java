package electrodynamics.prefab.item;

import java.util.List;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.ElectricUnit;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ItemElectric extends Item {

    private final ElectricItemProperties properties;

    public ItemElectric(ElectricItemProperties properties) {
    	super(properties);
    	this.properties = properties;
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
	if (isInGroup(group)) {
	    ItemStack charged = new ItemStack(this);
	    setEnergyStored(charged, properties.capacity);
	    setTemperature(charged,0);
	    items.add(charged);
	    ItemStack empty = new ItemStack(this);
	    setEnergyStored(empty, 0);
	    setTemperature(empty,0);
	    items.add(empty);
	}
    }

    private static void setEnergyStored(ItemStack stack, double amount) {
		if (!stack.hasTag()) {
		    stack.setTag(new CompoundNBT());
		}
		stack.getTag().putDouble("joules", amount);
	    }
    
    private static void setTemperature(ItemStack stack, double amount) {
    	if(!stack.hasTag()) {
    		stack.setTag(new CompoundNBT());
    	}
    	stack.getTag().putDouble("temperature", amount);
    }

    
    
    
    public TransferPack extractPower(ItemStack stack, double amount, boolean debug) {
		if (!stack.hasTag()) {
		    return TransferPack.EMPTY;
		}
		double current = stack.getTag().getDouble("joules");
		double extracted = Math.min(current, Math.min(properties.extract.getJoules(), amount));
		if (!debug) {
		    setEnergyStored(stack, current - extracted);
		}
		return TransferPack.joulesVoltage(extracted, properties.extract.getVoltage());
    }

    public TransferPack decreaseTemperature(ItemStack stack, double amount, boolean debug, double minTemp) {
    	if(!stack.hasTag()) {
    		return TransferPack.EMPTY;
    	}
    	double currTemp = stack.getTag().getDouble("temperature");
    	if(!debug && currTemp > minTemp) {
    		setTemperature(stack, currTemp - amount);
    	}
    	return TransferPack.temperature(amount);
    }
    
    
    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
    	return 1.0 - getJoulesStored(stack) / properties.capacity;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
    	return true;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(new TranslationTextComponent("tooltip.item.electric.info").mergeStyle(TextFormatting.GRAY)
			.append(new StringTextComponent(ChatFormatter.getElectricDisplayShort(getJoulesStored(stack), ElectricUnit.JOULES))));
		tooltip.add(new TranslationTextComponent("tooltip.item.electric.voltage",
			ChatFormatter.getElectricDisplayShort(properties.receive.getVoltage(), ElectricUnit.VOLTAGE) + " / "
				+ ChatFormatter.getElectricDisplayShort(properties.extract.getVoltage(), ElectricUnit.VOLTAGE))
					.mergeStyle(TextFormatting.RED));
    }

    public TransferPack receivePower(ItemStack stack, TransferPack amount, boolean debug) {
		if (!stack.hasTag()) {
		    stack.setTag(new CompoundNBT());
		}

		double current = stack.getTag().getDouble("joules");
		double received = Math.min(amount.getJoules(), properties.capacity - current);
		if (!debug) {
		    if (amount.getVoltage() == properties.receive.getVoltage() || amount.getVoltage() == -1) {
			setEnergyStored(stack, current + received);
		    }
		    if (amount.getVoltage() > properties.receive.getVoltage()) {
			overVoltage(amount);
			return TransferPack.EMPTY;
		    }
		}
		return TransferPack.joulesVoltage(received, amount.getVoltage());
    }
    
    public TransferPack recieveHeat(ItemStack stack, TransferPack amount, boolean debug) {
    	if(!stack.hasTag()) {
    		stack.setTag(new CompoundNBT());
    	}
    	double currTemp = stack.getTag().getDouble("temperature");
    	if(!debug) {
    		setTemperature(stack, currTemp + amount.getTemperature());
    	}
    	return TransferPack.temperature(properties.temperature);
    }
    
    
    public ElectricItemProperties getProperties() {
    	return properties;
    }

    public double getJoulesStored(ItemStack stack) {
    	return stack.hasTag() ? stack.getTag().getDouble("joules") : 0;
    }
    
    public double getTemperatureStored(ItemStack stack) {
    	return stack.hasTag() ? stack.getTag().getDouble("temperature") : 0;
    }

    public void overVoltage(TransferPack attempt) {
    	// TODO: how would this be implemented
    }

    public static class ElectricItemProperties extends Properties {
		private double temperature;
	    private double capacity;
		private TransferPack receive = TransferPack.EMPTY;
		private TransferPack extract = TransferPack.EMPTY;
	
		public ElectricItemProperties capacity(double capacity) {
		    this.capacity = (int) capacity;
		    return this;
		}
	
		public ElectricItemProperties receive(TransferPack receive) {
		    this.receive = receive;
		    return this;
		}
	
		public ElectricItemProperties extract(TransferPack extract) {
		    this.extract = extract;
		    return this;
		}
		
		public ElectricItemProperties temperature(double temp) {
			this.temperature = (int) temp;
			return this;
		}

    }
}
