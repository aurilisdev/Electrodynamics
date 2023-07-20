package electrodynamics.client.guidebook.utils.pagedata;

import electrodynamics.client.guidebook.ScreenGuidebook;

public interface OnKeyPress {

	// Attach this to an image
	public static final OnKeyPress JEI_LOOKUP = new OnKeyPress() {

		@Override
		public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

		}

		@Override
		public Object getJeiLookup() {
			return null;
		}
	};

	public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen);

	public Object getJeiLookup();

}
