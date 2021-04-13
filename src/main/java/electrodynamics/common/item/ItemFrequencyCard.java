package electrodynamics.common.item;

import java.util.List;
import java.util.UUID;

import electrodynamics.common.tile.TileTeleporter;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ItemFrequencyCard extends Item {

    public ItemFrequencyCard(Properties properties) {
	super(properties.maxStackSize(1));
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
	TileEntity ent = context.getWorld().getTileEntity(context.getPos());
	if (ent instanceof TileTeleporter && !ent.getWorld().isRemote) {
	    CompoundNBT nbt = stack.getOrCreateTag();
	    if (nbt.contains("world")) {
		TileTeleporter tel = (TileTeleporter) ent;
		tel.xCoord = nbt.getInt("xCoord");
		tel.yCoord = nbt.getInt("yCoord");
		tel.zCoord = nbt.getInt("zCoord");
		tel.world = nbt.getString("world");
		context.getPlayer().sendMessage(new TranslationTextComponent("tooltip.frequencycard.linked",
			tel.world + ", " + tel.xCoord + ", " + tel.yCoord + ", " + tel.zCoord), UUID.randomUUID());
	    } else {
		nbt.putInt("xCoord", ent.getPos().getX());
		nbt.putInt("yCoord", ent.getPos().getY());
		nbt.putInt("zCoord", ent.getPos().getZ());
		nbt.putString("world", ent.getWorld().getDimensionKey().getLocation().getPath());
	    }
	}
	return super.onItemUseFirst(stack, context);
    }

    public static ServerWorld getFromNBT(ServerWorld base, String str) {
	for (ServerWorld world : base.getWorld().getServer().getWorlds()) {
	    if (world.getDimensionKey().getLocation().getPath().equalsIgnoreCase(str)) {
		return world;
	    }
	}
	return null;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
	super.addInformation(stack, worldIn, tooltip, flagIn);
	if (stack.hasTag()) {
	    CompoundNBT nbt = stack.getTag();
	    int x = nbt.getInt("xCoord");
	    int y = nbt.getInt("yCoord");
	    int z = nbt.getInt("zCoord");
	    String world = nbt.getString("world");
	    tooltip.add(new TranslationTextComponent("tooltip.frequencycard.linked", world + ", " + x + ", " + y + ", " + z));
	} else {
	    tooltip.add(new TranslationTextComponent("tooltip.frequencycard.notag"));
	}
    }
}
