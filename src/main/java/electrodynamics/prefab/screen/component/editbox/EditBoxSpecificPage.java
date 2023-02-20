package electrodynamics.prefab.screen.component.editbox;

import electrodynamics.client.guidebook.ScreenGuidebook;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;

public class EditBoxSpecificPage extends EditBox {

	private final int page;
	
	public EditBoxSpecificPage(Font font, int x, int y, int width, int height, int page, Component message) {
		super(font, x, y, width, height, message);
		this.page =  page;
	}
	
	@Override
	public boolean isVisible() {
		return page == ScreenGuidebook.currPageNumber || page == ScreenGuidebook.currPageNumber + 1;
	}

}
