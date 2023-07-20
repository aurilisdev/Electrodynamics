package electrodynamics.prefab.screen.component.button.type;

import electrodynamics.api.screen.ITexture;
import electrodynamics.client.guidebook.ScreenGuidebook;
import electrodynamics.prefab.screen.component.button.ScreenComponentButton;

public class ButtonSpecificPage extends ScreenComponentButton<ButtonSpecificPage> {

	public final int page;

	public ButtonSpecificPage(int x, int y, int width, int height, int page) {
		super(x, y, width, height);
		this.page = page;
	}

	public ButtonSpecificPage(ITexture texture, int x, int y, int page) {
		super(texture, x, y);
		this.page = page;
	}

	@Override
	public boolean isVisible() {
		return page == ScreenGuidebook.currPageNumber || page == ScreenGuidebook.currPageNumber + 1;
	}

}
