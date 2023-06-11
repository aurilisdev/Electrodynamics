package electrodynamics.client.guidebook.utils.pagedata;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.client.guidebook.ScreenGuidebook;

public interface OnTooltip {
	
	public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen);

}
