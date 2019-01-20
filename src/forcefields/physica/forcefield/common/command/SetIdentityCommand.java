package physica.forcefield.common.command;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import physica.forcefield.common.ForcefieldItemRegister;

public class SetIdentityCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "setid";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/setid <username/uuid>";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length == 0) {
			sender.addChatMessage(new ChatComponentText("\u00A7cUsage: " + getCommandUsage(sender)));
			return;
		}

		if (sender instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) sender;
			ItemStack itemStack = player.getHeldItem();
			if (itemStack == null) {
				player.addChatMessage(new ChatComponentText("\u00A7cYou must be holding an id card to use this command"));
				return;
			}

			if (itemStack.getItem() == ForcefieldItemRegister.itemIdentifcationCard) {
				if (args.length == 1) {
					GameProfile gameProfile;
					try {
						UUID uuid = UUID.fromString(args[0]);
						gameProfile = MinecraftServer.getServer().func_152358_ax().func_152652_a(uuid);
					} catch (IllegalArgumentException e) {
						String name = args[0];
						gameProfile = MinecraftServer.getServer().func_152358_ax().func_152655_a(name);
					}

					if (gameProfile == null) {
						sender.addChatMessage(new ChatComponentText("\u00A7cUnable to find user: '" + args[0] + "' Please use /setid <name> <uuid> to use this player"));
						return;
					}
					ForcefieldItemRegister.itemIdentifcationCard.setUsername(itemStack, gameProfile.getName());
					ForcefieldItemRegister.itemIdentifcationCard.setUniqueId(itemStack, gameProfile.getId());
					ForcefieldItemRegister.itemIdentifcationCard.notifyIdentificationChange(player, gameProfile.getName());
				} else if (args.length == 2) {
					try {
						String name = args[0];
						UUID uuid = UUID.fromString(args[1]);
						ForcefieldItemRegister.itemIdentifcationCard.setUsername(itemStack, name);
						ForcefieldItemRegister.itemIdentifcationCard.setUniqueId(itemStack, uuid);
						ForcefieldItemRegister.itemIdentifcationCard.notifyIdentificationChange(player, name);
					} catch (IllegalArgumentException e) {
						player.addChatMessage(new ChatComponentText("\u00A7cInvalid player UUID!"));
					}
				} else {
					player.addChatMessage(new ChatComponentText("\u00A7cUsage: " + getCommandUsage(player)));
				}
			} else {
				sender.addChatMessage(new ChatComponentText("\u00A7cYou must be holding an identification card to perform this command"));
			}
		}
	}
}
