package electrodynamics.prefab.screen.component.editbox.type;

import electrodynamics.client.guidebook.ScreenGuidebook;
import electrodynamics.prefab.screen.component.editbox.ScreenComponentEditBox;
import net.minecraft.client.gui.Font;

public class EditBoxSpecificPage extends ScreenComponentEditBox {

	private final int page;

	public EditBoxSpecificPage(int x, int y, int width, int height, int page, Font font) {
		super(x, y, width, height, font);
		this.page = page;
	}

	@Override
	public boolean isVisible() {
		return page == ScreenGuidebook.currPageNumber || page == ScreenGuidebook.currPageNumber + 1;
	}

}
