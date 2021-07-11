package electrodynamics.common.player.armor.types.composite;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * PlayerModel - Either Mojang or a mod author (Taken From Memory) Created using
 * Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class CompositeArmorModel extends BipedModel<LivingEntity> {

    public CompositeArmorModel(float modelSize) {

	super(modelSize, 0, 128, 128);

	ModelRenderer HEAD = new ModelRenderer(this);
	HEAD.setRotationPoint(0.0F, 0.0F, 0.0F);
	bipedHead.addChild(HEAD);
	HEAD.setTextureOffset(42, 16).addBox(-5.0F, -6.0F, -4.0F, 1.0F, 5.0F, 8.0F, 0.0F, false);
	HEAD.setTextureOffset(24, 16).addBox(4.0F, -6.0F, -4.0F, 1.0F, 5.0F, 8.0F, 0.0F, false);
	HEAD.setTextureOffset(16, 99).addBox(-4.0F, -2.0F, -5.0F, 8.0F, 1.0F, 1.0F, 0.0F, false);
	HEAD.setTextureOffset(32, 112).addBox(-3.0F, -6.0F, -5.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
	HEAD.setTextureOffset(42, 120).addBox(-4.0F, -6.0F, -5.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);
	HEAD.setTextureOffset(38, 120).addBox(3.0F, -6.0F, -5.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);
	HEAD.setTextureOffset(0, 57).addBox(-4.0F, -6.0F, 4.0F, 8.0F, 5.0F, 1.0F, 0.0F, false);
	HEAD.setTextureOffset(0, 16).addBox(-3.0F, -9.0F, -3.0F, 6.0F, 1.0F, 6.0F, 0.0F, false);
	HEAD.setTextureOffset(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 0.0F, 8.0F, 0.0F, false);
	HEAD.setTextureOffset(72, 0).addBox(4.0F, -8.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, false);
	HEAD.setTextureOffset(32, 41).addBox(-4.0F, -8.0F, 4.0F, 8.0F, 8.0F, 0.0F, 0.0F, false);
	HEAD.setTextureOffset(56, 0).addBox(-4.0F, -8.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, false);
	HEAD.setTextureOffset(32, 111).addBox(-4.0F, -1.0F, -4.0F, 8.0F, 1.0F, 0.0F, 0.0F, false);
	HEAD.setTextureOffset(34, 99).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 2.0F, 0.0F, 0.0F, false);

	ModelRenderer CHEST = new ModelRenderer(this);
	CHEST.setRotationPoint(0.0F, 24.0F, 0.0F);
	bipedBody.addChild(CHEST);
	CHEST.setTextureOffset(32, 0).addBox(-4.0F, -24.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);
	CHEST.setTextureOffset(0, 29).addBox(-4.0F, -23.5F, -3.0F, 8.0F, 11.0F, 1.0F, 0.0F, false);
	CHEST.setTextureOffset(60, 16).addBox(-4.0F, -23.5F, 2.0F, 8.0F, 11.0F, 1.0F, 0.0F, false);
	CHEST.setTextureOffset(16, 101).addBox(-4.0F, -23.5F, -4.0F, 8.0F, 1.0F, 1.0F, 0.0F, false);
	CHEST.setTextureOffset(8, 86).addBox(-3.0F, -22.5F, -4.0F, 6.0F, 6.0F, 1.0F, 0.0F, false);
	CHEST.setTextureOffset(48, 111).addBox(-1.0F, -22.5F, -5.0F, 2.0F, 6.0F, 1.0F, 0.0F, false);
	CHEST.setTextureOffset(32, 120).addBox(-1.0F, -16.5F, -4.0F, 2.0F, 3.0F, 1.0F, 0.0F, false);
	CHEST.setTextureOffset(34, 57).addBox(-4.0F, -23.5F, 3.0F, 8.0F, 3.0F, 1.0F, 0.0F, false);
	CHEST.setTextureOffset(16, 103).addBox(-2.0F, -20.0F, 3.0F, 4.0F, 5.0F, 2.0F, 0.0F, false);
	CHEST.setTextureOffset(34, 29).addBox(-4.0F, -24.0F, -2.0F, 8.0F, 12.0F, 0.0F, 0.0F, false);
	CHEST.setTextureOffset(26, 57).addBox(-4.0F, -24.0F, -2.0F, 0.0F, 12.0F, 4.0F, 0.0F, false);
	CHEST.setTextureOffset(18, 57).addBox(4.0F, -24.0F, -2.0F, 0.0F, 12.0F, 4.0F, 0.0F, false);
	CHEST.setTextureOffset(18, 29).addBox(-4.0F, -24.0F, 2.0F, 8.0F, 12.0F, 0.0F, 0.0F, false);

	ModelRenderer RIGHT_ARM = new ModelRenderer(this);
	RIGHT_ARM.setRotationPoint(-5.0F, 2.0F, 0.0F);
	bipedRightArm.addChild(RIGHT_ARM);
	RIGHT_ARM.setTextureOffset(24, 41).addBox(-3.0F, -2.0F, -2.0F, 0.0F, 12.0F, 4.0F, 0.0F, false);
	RIGHT_ARM.setTextureOffset(16, 41).addBox(1.0F, -2.0F, -2.0F, 0.0F, 12.0F, 4.0F, 0.0F, false);
	RIGHT_ARM.setTextureOffset(8, 99).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 0.0F, 0.0F, false);
	RIGHT_ARM.setTextureOffset(0, 99).addBox(-3.0F, -2.0F, 2.0F, 4.0F, 12.0F, 0.0F, 0.0F, false);
	RIGHT_ARM.setTextureOffset(40, 86).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 0.0F, 4.0F, 0.0F, false);

	ModelRenderer LEFTARM = new ModelRenderer(this);
	LEFTARM.setRotationPoint(5.0F, 2.0F, 0.0F);
	bipedLeftArm.addChild(LEFTARM);
	LEFTARM.setTextureOffset(8, 41).addBox(-1.0F, -2.0F, -2.0F, 0.0F, 12.0F, 4.0F, 0.0F, false);
	LEFTARM.setTextureOffset(0, 41).addBox(3.0F, -2.0F, -2.0F, 0.0F, 12.0F, 4.0F, 0.0F, false);
	LEFTARM.setTextureOffset(32, 86).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 0.0F, 0.0F, false);
	LEFTARM.setTextureOffset(24, 86).addBox(-1.0F, -2.0F, 2.0F, 4.0F, 12.0F, 0.0F, 0.0F, false);
	LEFTARM.setTextureOffset(8, 93).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 0.0F, 4.0F, 0.0F, false);

	ModelRenderer RIGHTLEG = new ModelRenderer(this);
	RIGHTLEG.setRotationPoint(-1.9F, 12.0F, 0.0F);
	bipedRightLeg.addChild(RIGHTLEG);
	RIGHTLEG.setTextureOffset(24, 111).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 0.0F, 0.0F, false);
	RIGHTLEG.setTextureOffset(16, 111).addBox(-2.0F, 0.0F, 2.0F, 4.0F, 9.0F, 0.0F, 0.0F, false);
	RIGHTLEG.setTextureOffset(0, 86).addBox(-2.0F, 0.0F, -2.0F, 0.0F, 9.0F, 4.0F, 0.0F, false);
	RIGHTLEG.setTextureOffset(52, 73).addBox(2.0F, 0.0F, -2.0F, 0.0F, 9.0F, 4.0F, 0.0F, false);
	RIGHTLEG.setTextureOffset(8, 120).addBox(-1.6F, 3.0F, -3.0F, 3.0F, 3.0F, 1.0F, 0.0F, false);

	ModelRenderer LEFTLEG = new ModelRenderer(this);
	LEFTLEG.setRotationPoint(1.9F, 12.0F, 0.0F);
	bipedLeftLeg.addChild(LEFTLEG);
	LEFTLEG.setTextureOffset(8, 111).addBox(-1.8F, 0.0F, -2.0F, 4.0F, 9.0F, 0.0F, 0.0F, false);
	LEFTLEG.setTextureOffset(0, 111).addBox(-1.8F, 0.0F, 2.0F, 4.0F, 9.0F, 0.0F, 0.0F, false);
	LEFTLEG.setTextureOffset(44, 73).addBox(-1.8F, 0.0F, -2.0F, 0.0F, 9.0F, 4.0F, 0.0F, false);
	LEFTLEG.setTextureOffset(36, 73).addBox(2.2F, 0.0F, -2.0F, 0.0F, 9.0F, 4.0F, 0.0F, false);
	LEFTLEG.setTextureOffset(0, 120).addBox(-1.4F, 3.0F, -3.0F, 3.0F, 3.0F, 1.0F, 0.0F, false);

	ModelRenderer RIGHTSHOE = new ModelRenderer(this);
	RIGHTSHOE.setRotationPoint(5.9F, 17.0F, 0.0F);
	bipedRightLeg.addChild(RIGHTSHOE);
	RIGHTSHOE.setTextureOffset(0, 73).addBox(-1.8F, 4.0F, -4.0F, 0.0F, 3.0F, 6.0F, 0.0F, false);
	RIGHTSHOE.setTextureOffset(34, 61).addBox(-5.8F, 4.0F, -4.0F, 0.0F, 3.0F, 6.0F, 0.0F, false);
	RIGHTSHOE.setTextureOffset(46, 120).addBox(-5.8F, 4.0F, -2.0F, 4.0F, 3.0F, -2.0F, 0.0F, false);
	RIGHTSHOE.setTextureOffset(16, 120).addBox(-5.8F, 4.0F, 2.0F, 4.0F, 3.0F, 0.0F, 0.0F, false);
	RIGHTSHOE.setTextureOffset(50, 29).addBox(-5.8F, 7.0F, -4.0F, 4.0F, 0.0F, 6.0F, 0.0F, false);

	ModelRenderer LEFTSHOE = new ModelRenderer(this);
	LEFTSHOE.setRotationPoint(-1.9F, 17.0F, 0.0F);
	bipedLeftLeg.addChild(LEFTSHOE);
	LEFTSHOE.setTextureOffset(24, 73).addBox(2.0F, 4.0F, -4.0F, 0.0F, 3.0F, 6.0F, 0.0F, false);
	LEFTSHOE.setTextureOffset(12, 73).addBox(-2.0F, 4.0F, -4.0F, 0.0F, 3.0F, 6.0F, 0.0F, false);
	LEFTSHOE.setTextureOffset(24, 120).addBox(-2.0F, 4.0F, 2.0F, 4.0F, 3.0F, 0.0F, 0.0F, false);
	LEFTSHOE.setTextureOffset(46, 121).addBox(-2.0F, 4.0F, -2.0F, 4.0F, 3.0F, -2.0F, 0.0F, false);
	LEFTSHOE.setTextureOffset(50, 35).addBox(-2.0F, 7.0F, -4.0F, 4.0F, 0.0F, 6.0F, 0.0F, false);

    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
	modelRenderer.rotateAngleX = x;
	modelRenderer.rotateAngleY = y;
	modelRenderer.rotateAngleZ = z;
    }

}
