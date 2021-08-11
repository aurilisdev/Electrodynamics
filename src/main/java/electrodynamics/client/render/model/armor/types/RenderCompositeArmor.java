package electrodynamics.client.render.model.armor.types;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderCompositeArmor extends BipedModel<LivingEntity> {

    public final ModelRenderer HEAD;

    public final ModelRenderer CHEST;
    public final ModelRenderer RIGHT_ARM;
    public final ModelRenderer LEFT_ARM;

    public final ModelRenderer RIGHT_LEG;
    public final ModelRenderer LEFT_LEG;

    public final ModelRenderer RIGHT_SHOE;
    public final ModelRenderer LEFT_SHOE;

    public RenderCompositeArmor(float size) {
	super(size, 0, 128, 128);

	HEAD = new ModelRenderer(this);
	HEAD.setRotationPoint(0.0F, 0.0F, 0.0F);
	HEAD.setTextureOffset(28, 28).addBox(-6.0F, -6.0F, -4.0F, 1.0F, 5.0F, 8.0F, 0.0F, false);
	HEAD.setTextureOffset(36, 0).addBox(-5.0F, -6.0F, 4.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
	HEAD.setTextureOffset(11, 27).addBox(4.0F, -6.0F, 4.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
	HEAD.setTextureOffset(0, 28).addBox(5.0F, -6.0F, -4.0F, 1.0F, 5.0F, 8.0F, 0.0F, false);
	HEAD.setTextureOffset(0, 27).addBox(-5.0F, -6.0F, -5.0F, 2.0F, 4.0F, 1.0F, 0.0F, false);
	HEAD.setTextureOffset(25, 0).addBox(3.0F, -6.0F, -5.0F, 2.0F, 4.0F, 1.0F, 0.0F, false);
	HEAD.setTextureOffset(54, 26).addBox(-4.0F, -6.0F, 5.0F, 8.0F, 5.0F, 1.0F, 0.0F, false);
	HEAD.setTextureOffset(28, 19).addBox(-3.0F, -8.0F, -3.0F, 6.0F, 1.0F, 6.0F, 0.0F, false);
	HEAD.setTextureOffset(0, 0).addBox(-4.0F, -9.0F, -4.0F, 8.0F, 1.0F, 8.0F, 0.0F, false);
	HEAD.setTextureOffset(25, 2).addBox(4.0F, -8.0F, -4.0F, 1.0F, 8.0F, 8.0F, 0.0F, false);
	HEAD.setTextureOffset(47, 13).addBox(-4.0F, -8.0F, 4.0F, 8.0F, 8.0F, 1.0F, 0.0F, false);
	HEAD.setTextureOffset(17, 19).addBox(-5.0F, -8.0F, -4.0F, 1.0F, 8.0F, 8.0F, 0.0F, false);
	HEAD.setTextureOffset(75, 21).addBox(-4.0F, -1.0F, -5.0F, 8.0F, 1.0F, 1.0F, 0.0F, false);
	HEAD.setTextureOffset(53, 23).addBox(-5.0F, -2.0F, -5.0F, 10.0F, 1.0F, 1.0F, 0.0F, false);
	HEAD.setTextureOffset(73, 26).addBox(-4.0F, -8.0F, -5.0F, 8.0F, 2.0F, 1.0F, 0.0F, false);

	CHEST = new ModelRenderer(this);
	CHEST.setRotationPoint(0.0F, 24.0F, 0.0F);
	CHEST.setTextureOffset(19, 42).addBox(-4.0F, 0.5F, -3.0F, 8.0F, 11.0F, 1.0F, 0.0F, false);
	CHEST.setTextureOffset(0, 42).addBox(-4.0F, 0.5F, 2.0F, 8.0F, 11.0F, 1.0F, 0.0F, false);
	CHEST.setTextureOffset(76, 6).addBox(-4.0F, 0.5F, -4.0F, 8.0F, 1.0F, 1.0F, 0.0F, false);
	CHEST.setTextureOffset(79, 30).addBox(-3.0F, 1.5F, -4.0F, 6.0F, 6.0F, 1.0F, 0.0F, false);
	CHEST.setTextureOffset(0, 0).addBox(-1.0F, 1.5F, -5.0F, 2.0F, 6.0F, 1.0F, 0.0F, false);
	CHEST.setTextureOffset(69, 79).addBox(-1.0F, 7.5F, -4.0F, 2.0F, 3.0F, 1.0F, 0.0F, false);
	CHEST.setTextureOffset(66, 10).addBox(-4.0F, 0.5F, 3.0F, 8.0F, 3.0F, 1.0F, 0.0F, false);
	CHEST.setTextureOffset(82, 51).addBox(-2.0F, 4.0F, 3.0F, 4.0F, 5.0F, 2.0F, 0.0F, false);
	CHEST.setTextureOffset(44, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 0.0F, 0.0F, false);
	CHEST.setTextureOffset(38, 55).addBox(-4.0F, 0.0F, -2.0F, 0.0F, 12.0F, 4.0F, 0.0F, false);
	CHEST.setTextureOffset(29, 55).addBox(4.0F, 0.0F, -2.0F, 0.0F, 12.0F, 4.0F, 0.0F, false);
	CHEST.setTextureOffset(38, 42).addBox(-4.0F, 0.0F, 2.0F, 8.0F, 12.0F, 0.0F, 0.0F, false);

	RIGHT_ARM = new ModelRenderer(this);
	RIGHT_ARM.setRotationPoint(-5.0F, 2.0F, 0.0F);
	RIGHT_ARM.setTextureOffset(0, 55).addBox(-4.0F, -2.0F, -2.0F, 1.0F, 12.0F, 4.0F, 0.0F, false);
	RIGHT_ARM.setTextureOffset(20, 55).addBox(1.0F, -2.0F, -2.0F, 0.0F, 12.0F, 4.0F, 0.0F, false);
	RIGHT_ARM.setTextureOffset(0, 72).addBox(-3.0F, -2.0F, -3.0F, 4.0F, 12.0F, 1.0F, 0.0F, false);
	RIGHT_ARM.setTextureOffset(69, 65).addBox(-3.0F, -2.0F, 2.0F, 4.0F, 12.0F, 1.0F, 0.0F, false);
	RIGHT_ARM.setTextureOffset(70, 0).addBox(-3.0F, -3.0F, -2.0F, 4.0F, 1.0F, 4.0F, 0.0F, false);

	LEFT_ARM = new ModelRenderer(this);
	LEFT_ARM.setRotationPoint(5.0F, 2.0F, 0.0F);
	LEFT_ARM.setTextureOffset(11, 55).addBox(-1.0F, -2.0F, -2.0F, 0.0F, 12.0F, 4.0F, 0.0F, false);
	LEFT_ARM.setTextureOffset(51, 51).addBox(3.0F, -2.0F, -2.0F, 1.0F, 12.0F, 4.0F, 0.0F, false);
	LEFT_ARM.setTextureOffset(47, 68).addBox(-1.0F, -2.0F, -3.0F, 4.0F, 12.0F, 1.0F, 0.0F, false);
	LEFT_ARM.setTextureOffset(68, 33).addBox(-1.0F, -2.0F, 2.0F, 4.0F, 12.0F, 1.0F, 0.0F, false);
	LEFT_ARM.setTextureOffset(66, 15).addBox(-1.0F, -3.0F, -2.0F, 4.0F, 1.0F, 4.0F, 0.0F, false);

	RIGHT_LEG = new ModelRenderer(this);
	RIGHT_LEG.setRotationPoint(-1.9F, 12.0F, 0.0F);
	RIGHT_LEG.setTextureOffset(22, 82).addBox(-2.1F, 0.0F, -3.0F, 4.0F, 9.0F, 1.0F, 0.0F, false);
	RIGHT_LEG.setTextureOffset(11, 82).addBox(-2.1F, 0.0F, 2.0F, 4.0F, 9.0F, 1.0F, 0.0F, false);
	RIGHT_LEG.setTextureOffset(58, 65).addBox(-3.1F, 0.0F, -2.0F, 1.0F, 9.0F, 4.0F, 0.0F, false);
	RIGHT_LEG.setTextureOffset(73, 47).addBox(2.0F, 0.0F, -2.0F, 0.0F, 9.0F, 4.0F, 0.0F, false);
	RIGHT_LEG.setTextureOffset(46, 82).addBox(-1.6F, 3.0F, -4.0F, 3.0F, 3.0F, 1.0F, 0.0F, false);

	LEFT_LEG = new ModelRenderer(this);
	LEFT_LEG.setRotationPoint(1.9F, 12.0F, 0.0F);
	LEFT_LEG.setTextureOffset(80, 61).addBox(-1.9F, 0.0F, -3.0F, 4.0F, 9.0F, 1.0F, 0.0F, false);
	LEFT_LEG.setTextureOffset(58, 79).addBox(-1.9F, 0.0F, 2.0F, 4.0F, 9.0F, 1.0F, 0.0F, false);
	LEFT_LEG.setTextureOffset(37, 72).addBox(-1.8F, 0.0F, -2.0F, 0.0F, 9.0F, 4.0F, 0.0F, false);
	LEFT_LEG.setTextureOffset(62, 51).addBox(2.1F, 0.0F, -2.0F, 1.0F, 9.0F, 4.0F, 0.0F, false);
	LEFT_LEG.setTextureOffset(19, 36).addBox(-1.4F, 3.0F, -4.0F, 3.0F, 3.0F, 1.0F, 0.0F, false);

	RIGHT_SHOE = new ModelRenderer(this);
	RIGHT_SHOE.setRotationPoint(-1.9F, 17.0F, 0.0F);
	RIGHT_SHOE.setTextureOffset(55, 41).addBox(2.0F, 9.0F, -4.0F, 1.0F, 3.0F, 6.0F, 0.0F, false);
	RIGHT_SHOE.setTextureOffset(11, 72).addBox(-2.0F, 9.0F, -4.0F, 0.0F, 3.0F, 6.0F, 0.0F, false);
	RIGHT_SHOE.setTextureOffset(77, 76).addBox(-2.0F, 9.0F, -4.0F, 4.0F, 3.0F, 3.0F, 0.0F, false);
	RIGHT_SHOE.setTextureOffset(18, 72).addBox(-2.0F, 9.0F, 2.0F, 4.0F, 3.0F, 1.0F, 0.0F, false);
	RIGHT_SHOE.setTextureOffset(39, 27).addBox(-2.0F, 12.1F, -4.0F, 4.0F, 0.0F, 6.0F, 0.0F, false);

	LEFT_SHOE = new ModelRenderer(this);
	LEFT_SHOE.setRotationPoint(1.9F, 17.0F, 0.0F);
	LEFT_SHOE.setTextureOffset(24, 72).addBox(2.0F, 9.0F, -4.0F, 0.0F, 3.0F, 6.0F, 0.0F, false);
	LEFT_SHOE.setTextureOffset(61, 0).addBox(-3.0F, 9.0F, -4.0F, 1.0F, 3.0F, 6.0F, 0.0F, false);
	LEFT_SHOE.setTextureOffset(79, 38).addBox(-2.0F, 9.0F, 2.0F, 4.0F, 3.0F, 1.0F, 0.0F, false);
	LEFT_SHOE.setTextureOffset(78, 44).addBox(-2.0F, 9.0F, -4.0F, 4.0F, 3.0F, 3.0F, 0.0F, false);
	LEFT_SHOE.setTextureOffset(47, 34).addBox(-2.0F, 12.1F, -4.0F, 4.0F, 0.0F, 6.0F, 0.0F, false);

	bipedHead.addChild(HEAD);
	bipedBody.addChild(CHEST);
	bipedRightArm.addChild(RIGHT_ARM);
	bipedLeftArm.addChild(LEFT_ARM);
	bipedLeftLeg.addChild(RIGHT_LEG);
	bipedLeftLeg.addChild(LEFT_LEG);
	bipedRightLeg.addChild(RIGHT_SHOE);
	bipedLeftLeg.addChild(LEFT_SHOE);
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue,
	    float alpha) {
	if (HEAD.showModel) {
	    HEAD.copyModelAngles(bipedHead.getModelAngleCopy());
	    HEAD.render(matrixStack, buffer, packedLight, packedOverlay);
	}
	if (CHEST.showModel) {
	    CHEST.copyModelAngles(bipedBody.getModelAngleCopy());
	    CHEST.render(matrixStack, buffer, packedLight, packedOverlay);
	    RIGHT_ARM.copyModelAngles(bipedRightArm.getModelAngleCopy());
	    RIGHT_ARM.render(matrixStack, buffer, packedLight, packedOverlay);
	    LEFT_ARM.copyModelAngles(bipedLeftArm.getModelAngleCopy());
	    LEFT_ARM.render(matrixStack, buffer, packedLight, packedOverlay);
	}
	if (RIGHT_LEG.showModel) {
	    RIGHT_LEG.copyModelAngles(bipedRightLeg.getModelAngleCopy());
	    RIGHT_LEG.render(matrixStack, buffer, packedLight, packedOverlay);
	    LEFT_LEG.copyModelAngles(bipedLeftLeg.getModelAngleCopy());
	    LEFT_LEG.render(matrixStack, buffer, packedLight, packedOverlay);
	}
	if (RIGHT_SHOE.showModel) {
	    RIGHT_SHOE.copyModelAngles(bipedLeftLeg.getModelAngleCopy());
	    RIGHT_SHOE.render(matrixStack, buffer, packedLight, packedOverlay);
	    LEFT_SHOE.copyModelAngles(bipedRightLeg.getModelAngleCopy());
	    LEFT_SHOE.render(matrixStack, buffer, packedLight, packedOverlay);
	}
    }
}
