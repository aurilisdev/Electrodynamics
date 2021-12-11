package electrodynamics.common.item;

import java.util.List;

import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class ItemUpgrade extends Item {
    public final SubtypeItemUpgrade subtype;
    
    public ItemUpgrade(Properties properties, SubtypeItemUpgrade subtype) {
	super(properties.stacksTo(1));
	this.subtype = subtype;
    }

    //add direction storage capability
    
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
    	// TODO Auto-generated method stub
    	return super.initCapabilities(stack, nbt);
    }
    
    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
    	super.appendHoverText(stack, worldIn, tooltip, flagIn);
		if (subtype == SubtypeItemUpgrade.advancedcapacity || subtype == SubtypeItemUpgrade.basiccapacity) {
		    double capacityMultiplier = subtype == SubtypeItemUpgrade.advancedcapacity ? 2.25 : 1.5;
		    tooltip.add(new TranslatableComponent("tooltip.info.capacityupgrade", capacityMultiplier).withStyle(ChatFormatting.GRAY));
		    tooltip.add(new TranslatableComponent("tooltip.info.capacityupgradevoltage", (capacityMultiplier == 2.25 ? 4 : 2) + "x")
			    .withStyle(ChatFormatting.RED));
		}
		if (subtype == SubtypeItemUpgrade.advancedspeed || subtype == SubtypeItemUpgrade.basicspeed) {
		    double speedMultiplier = subtype == SubtypeItemUpgrade.advancedspeed ? 2.25 : 1.5;
		    tooltip.add(new TranslatableComponent("tooltip.info.speedupgrade", speedMultiplier).withStyle(ChatFormatting.GRAY));
		}
		if(subtype == SubtypeItemUpgrade.itemoutput) {
			tooltip.add(new TranslatableComponent("tooltip.info.itemoutputupgrade").withStyle(ChatFormatting.GRAY));
		}
    }
    
    //set direction and/or change existing one
    
    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
    	// TODO Auto-generated method stub
    	return super.use(p_41432_, p_41433_, p_41434_);
    }
    
}
