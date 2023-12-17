package electrodynamics.client.guidebook.utils.pagedata;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.client.guidebook.ScreenGuidebook;

public interface OnTooltip {

	public void onTooltip(MatrixStack stack, int xAxis, int yAxis, ScreenGuidebook screen);

}