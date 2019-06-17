package physica.core.common.command;

import java.util.Arrays;
import java.util.List;

import cpw.mods.fml.common.Loader;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import physica.CoreReferences;
import physica.Physica;
import physica.forcefield.PhysicaForcefields;
import physica.nuclear.PhysicaNuclearPhysics;

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
		} else {
			Physica.config.preInit();
			if (Loader.isModLoaded(CoreReferences.DOMAIN + "nuclearphysics")) {
				PhysicaNuclearPhysics.config.preInit();
			}
			if (Loader.isModLoaded(CoreReferences.DOMAIN + "forcefields")) {
				PhysicaForcefields.config.preInit();
			}
			sender.addChatMessage(new ChatComponentText("Physica configs reloaded successfully"));
		}
	}

}
