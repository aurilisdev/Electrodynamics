package physica.core.common.command;

import cpw.mods.fml.common.Loader;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import physica.CoreReferences;
import physica.Physica;
import physica.forcefield.PhysicaForcefields;
import physica.nuclear.PhysicaNuclearPhysics;

import java.util.Arrays;
import java.util.List;

public class CommandPhysica extends CommandBase {

	@Override
	public String getCommandName() {
		return CoreReferences.DOMAIN;
	}

	@Override
	public List<String> getCommandAliases() {
		return Arrays.asList("phys");
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/" + getCommandName();
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length == 0) {
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "[" + EnumChatFormatting.GOLD + CoreReferences.NAME + EnumChatFormatting.DARK_GRAY + "]"));
			sender.addChatMessage(new ChatComponentText(" A Mod focused around science and technology that introduces many new machines/blocks and items into the game"));
			sender.addChatMessage(new ChatComponentText(" Developed by aurilisdev"));
			sender.addChatMessage(new ChatComponentText(" Website: github.com/Aurilisdev/" + CoreReferences.NAME));
			sender.addChatMessage(new ChatComponentText(" Version: " + CoreReferences.VERSION));
		} else if (args[0].equalsIgnoreCase("reload")) {
			Physica.config.preInit();
			if (Loader.isModLoaded(CoreReferences.DOMAIN + "nuclearphysics")) {
				PhysicaNuclearPhysics.config.preInit();
			}
			if (Loader.isModLoaded(CoreReferences.DOMAIN + "forcefields")) {
				PhysicaForcefields.config.preInit();
			}
			sender.addChatMessage(new ChatComponentText("Physica configs reloaded successfully"));
		} else if (args[0].equalsIgnoreCase("item")) {
			if (sender instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) sender;
				ItemStack itemStack = player.getItemInUse();
				if (itemStack != null) {
					sender.addChatMessage(new ChatComponentText(itemStack.getItem().getUnlocalizedName()));
				} else {
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "You are not holding anything in your hand!"));
				}
			} else {
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "This command must be executed in game"));
			}
		} else {
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Unknown Physica sub-command"));
		}
	}

}
