package electrodynamics.api.gui;

import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IGuiWrapper {
    void drawTexturedRect(MatrixStack stack, int x, int y, int u, int v, int w, int h);

    void drawTexturedRectFromIcon(MatrixStack stack, int x, int y, TextureAtlasSprite icon, int w, int h);

    void displayTooltip(MatrixStack stack, ITextComponent text, int xAxis, int yAxis);

    void displayTooltips(MatrixStack stack, List<? extends ITextProperties> tooltips, int xAxis, int yAxis);

    FontRenderer getFontRenderer();

}