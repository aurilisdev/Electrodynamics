package physica.library.util;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class ChatUtilities {
	public static void addSpamlessMessages(int startLineNumber, String... messages) {
		IChatComponent[] msgs = new IChatComponent[messages.length];
		for (int index = 0; index < messages.length; index++) {
			msgs[index] = new ChatComponentText(messages[index]);
		}
		addSpamlessMessages(startLineNumber, msgs);
	}

	public static void addSpamlessMessages(int startLineNumber, IChatComponent... messages) {
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			GuiNewChat chat = Minecraft.getMinecraft().ingameGUI.getChatGUI();
			for (int i = startLineNumber; i < messages.length; i++) {
				chat.deleteChatLine(i);
			}
			for (int i = 0; i < messages.length; i++) {
				chat.printChatMessageWithOptionalDeletion(messages[i], startLineNumber + i);
			}
		}
	}
}
