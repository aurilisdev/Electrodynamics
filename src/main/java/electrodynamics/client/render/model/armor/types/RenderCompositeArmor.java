package electrodynamics.client.render.model.armor.types;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderCompositeArmor extends HumanoidModel<LivingEntity> {

    public final ModelPart HEAD;

    public final ModelPart CHEST;
    public final ModelPart RIGHT_ARM;
    public final ModelPart LEFT_ARM;

    public final ModelPart RIGHT_LEG;
    public final ModelPart LEFT_LEG;

    public final ModelPart RIGHT_SHOE;
    public final ModelPart LEFT_SHOE;

    public RenderCompositeArmor(float size) {
	super(size, 0, 128, 128);

	HEAD = new ModelPart(this);
	HEAD.setPos(0.0F, 0.0F, 0.0F);
	HEAD.texOffs(28, 28).addBox(-6.0F, -6.0F, -4.0F, 1.0F, 5.0F, 8.0F, 0.0F, false);
	HEAD.texOffs(36, 0).addBox(-5.0F, -6.0F, 4.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
	HEAD.texOffs(11, 27).addBox(4.0F, -6.0F, 4.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
	HEAD.texOffs(0, 28).addBox(5.0F, -6.0F, -4.0F, 1.0F, 5.0F, 8.0F, 0.0F, false);
	HEAD.texOffs(0, 27).addBox(-5.0F, -6.0F, -5.0F, 2.0F, 4.0F, 1.0F, 0.0F, false);
	HEAD.texOffs(25, 0).addBox(3.0F, -6.0F, -5.0F, 2.0F, 4.0F, 1.0F, 0.0F, false);
	HEAD.texOffs(54, 26).addBox(-4.0F, -6.0F, 5.0F, 8.0F, 5.0F, 1.0F, 0.0F, false);
	HEAD.texOffs(28, 19).addBox(-3.0F, -8.0F, -3.0F, 6.0F, 1.0F, 6.0F, 0.0F, false);
	HEAD.texOffs(0, 0).addBox(-4.0F, -9.0F, -4.0F, 8.0F, 1.0F, 8.0F, 0.0F, false);
	HEAD.texOffs(25, 2).addBox(4.0F, -8.0F, -4.0F, 1.0F, 8.0F, 8.0F, 0.0F, false);
	HEAD.texOffs(47, 13).addBox(-4.0F, -8.0F, 4.0F, 8.0F, 8.0F, 1.0F, 0.0F, false);
	HEAD.texOffs(17, 19).addBox(-5.0F, -8.0F, -4.0F, 1.0F, 8.0F, 8.0F, 0.0F, false);
	HEAD.texOffs(75, 21).addBox(-4.0F, -1.0F, -5.0F, 8.0F, 1.0F, 1.0F, 0.0F, false);
	HEAD.texOffs(53, 23).addBox(-5.0F, -2.0F, -5.0F, 10.0F, 1.0F, 1.0F, 0.0F, false);
	HEAD.texOffs(73, 26).addBox(-4.0F, -8.0F, -5.0F, 8.0F, 2.0F, 1.0F, 0.0F, false);

	CHEST = new ModelPart(this);
	CHEST.setPos(0.0F, 24.0F, 0.0F);
	CHEST.texOffs(19, 42).addBox(-4.0F, 0.5F, -3.0F, 8.0F, 11.0F, 1.0F, 0.0F, false);
	CHEST.texOffs(0, 42).addBox(-4.0F, 0.5F, 2.0F, 8.0F, 11.0F, 1.0F, 0.0F, false);
	CHEST.texOffs(76, 6).addBox(-4.0F, 0.5F, -4.0F, 8.0F, 1.0F, 1.0F, 0.0F, false);
	CHEST.texOffs(79, 30).addBox(-3.0F, 1.5F, -4.0F, 6.0F, 6.0F, 1.0F, 0.0F, false);
	CHEST.texOffs(0, 0).addBox(-1.0F, 1.5F, -5.0F, 2.0F, 6.0F, 1.0F, 0.0F, false);
	CHEST.texOffs(69, 79).addBox(-1.0F, 7.5F, -4.0F, 2.0F, 3.0F, 1.0F, 0.0F, false);
	CHEST.texOffs(66, 10).addBox(-4.0F, 0.5F, 3.0F, 8.0F, 3.0F, 1.0F, 0.0F, false);
	CHEST.texOffs(82, 51).addBox(-2.0F, 4.0F, 3.0F, 4.0F, 5.0F, 2.0F, 0.0F, false);
	CHEST.texOffs(44, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 0.0F, 0.0F, false);
	CHEST.texOffs(38, 55).addBox(-4.0F, 0.0F, -2.0F, 0.0F, 12.0F, 4.0F, 0.0F, false);
	CHEST.texOffs(29, 55).addBox(4.0F, 0.0F, -2.0F, 0.0F, 12.0F, 4.0F, 0.0F, false);
	CHEST.texOffs(38, 42).addBox(-4.0F, 0.0F, 2.0F, 8.0F, 12.0F, 0.0F, 0.0F, false);

	RIGHT_ARM = new ModelPart(this);
	RIGHT_ARM.setPos(-5.0F, 2.0F, 0.0F);
	RIGHT_ARM.texOffs(0, 55).addBox(-4.0F, -2.0F, -2.0F, 1.0F, 12.0F, 4.0F, 0.0F, false);
	RIGHT_ARM.texOffs(20, 55).addBox(1.0F, -2.0F, -2.0F, 0.0F, 12.0F, 4.0F, 0.0F, false);
	RIGHT_ARM.texOffs(0, 72).addBox(-3.0F, -2.0F, -3.0F, 4.0F, 12.0F, 1.0F, 0.0F, false);
	RIGHT_ARM.texOffs(69, 65).addBox(-3.0F, -2.0F, 2.0F, 4.0F, 12.0F, 1.0F, 0.0F, false);
	RIGHT_ARM.texOffs(70, 0).addBox(-3.0F, -3.0F, -2.0F, 4.0F, 1.0F, 4.0F, 0.0F, false);

	LEFT_ARM = new ModelPart(this);
	LEFT_ARM.setPos(5.0F, 2.0F, 0.0F);
	LEFT_ARM.texOffs(11, 55).addBox(-1.0F, -2.0F, -2.0F, 0.0F, 12.0F, 4.0F, 0.0F, false);
	LEFT_ARM.texOffs(51, 51).addBox(3.0F, -2.0F, -2.0F, 1.0F, 12.0F, 4.0F, 0.0F, false);
	LEFT_ARM.texOffs(47, 68).addBox(-1.0F, -2.0F, -3.0F, 4.0F, 12.0F, 1.0F, 0.0F, false);
	LEFT_ARM.texOffs(68, 33).addBox(-1.0F, -2.0F, 2.0F, 4.0F, 12.0F, 1.0F, 0.0F, false);
	LEFT_ARM.texOffs(66, 15).addBox(-1.0F, -3.0F, -2.0F, 4.0F, 1.0F, 4.0F, 0.0F, false);

	RIGHT_LEG = new ModelPart(this);
	RIGHT_LEG.setPos(-1.9F, 12.0F, 0.0F);
	RIGHT_LEG.texOffs(22, 82).addBox(-2.1F, 0.0F, -3.0F, 4.0F, 9.0F, 1.0F, 0.0F, false);
	RIGHT_LEG.texOffs(11, 82).addBox(-2.1F, 0.0F, 2.0F, 4.0F, 9.0F, 1.0F, 0.0F, false);
	RIGHT_LEG.texOffs(58, 65).addBox(-3.1F, 0.0F, -2.0F, 1.0F, 9.0F, 4.0F, 0.0F, false);
	RIGHT_LEG.texOffs(73, 47).addBox(2.0F, 0.0F, -2.0F, 0.0F, 9.0F, 4.0F, 0.0F, false);
	RIGHT_LEG.texOffs(46, 82).addBox(-1.6F, 3.0F, -4.0F, 3.0F, 3.0F, 1.0F, 0.0F, false);

	LEFT_LEG = new ModelPart(this);
	LEFT_LEG.setPos(1.9F, 12.0F, 0.0F);
	LEFT_LEG.texOffs(80, 61).addBox(-1.9F, 0.0F, -3.0F, 4.0F, 9.0F, 1.0F, 0.0F, false);
	LEFT_LEG.texOffs(58, 79).addBox(-1.9F, 0.0F, 2.0F, 4.0F, 9.0F, 1.0F, 0.0F, false);
	LEFT_LEG.texOffs(37, 72).addBox(-1.8F, 0.0F, -2.0F, 0.0F, 9.0F, 4.0F, 0.0F, false);
	LEFT_LEG.texOffs(62, 51).addBox(2.1F, 0.0F, -2.0F, 1.0F, 9.0F, 4.0F, 0.0F, false);
	LEFT_LEG.texOffs(19, 36).addBox(-1.4F, 3.0F, -4.0F, 3.0F, 3.0F, 1.0F, 0.0F, false);

	RIGHT_SHOE = new ModelPart(this);
	RIGHT_SHOE.setPos(-1.9F, 17.0F, 0.0F);
	RIGHT_SHOE.texOffs(55, 41).addBox(2.0F, 9.0F, -4.0F, 1.0F, 3.0F, 6.0F, 0.0F, false);
	RIGHT_SHOE.texOffs(11, 72).addBox(-2.0F, 9.0F, -4.0F, 0.0F, 3.0F, 6.0F, 0.0F, false);
	RIGHT_SHOE.texOffs(77, 76).addBox(-2.0F, 9.0F, -4.0F, 4.0F, 3.0F, 3.0F, 0.0F, false);
	RIGHT_SHOE.texOffs(18, 72).addBox(-2.0F, 9.0F, 2.0F, 4.0F, 3.0F, 1.0F, 0.0F, false);
	RIGHT_SHOE.texOffs(39, 27).addBox(-2.0F, 12.1F, -4.0F, 4.0F, 0.0F, 6.0F, 0.0F, false);

	LEFT_SHOE = new ModelPart(this);
	LEFT_SHOE.setPos(1.9F, 17.0F, 0.0F);
	LEFT_SHOE.texOffs(24, 72).addBox(2.0F, 9.0F, -4.0F, 0.0F, 3.0F, 6.0F, 0.0F, false);
	LEFT_SHOE.texOffs(61, 0).addBox(-3.0F, 9.0F, -4.0F, 1.0F, 3.0F, 6.0F, 0.0F, false);
	LEFT_SHOE.texOffs(79, 38).addBox(-2.0F, 9.0F, 2.0F, 4.0F, 3.0F, 1.0F, 0.0F, false);
	LEFT_SHOE.texOffs(78, 44).addBox(-2.0F, 9.0F, -4.0F, 4.0F, 3.0F, 3.0F, 0.0F, false);
	LEFT_SHOE.texOffs(47, 34).addBox(-2.0F, 12.1F, -4.0F, 4.0F, 0.0F, 6.0F, 0.0F, false);

	head.addChild(HEAD);
	body.addChild(CHEST);
	rightArm.addChild(RIGHT_ARM);
	leftArm.addChild(LEFT_ARM);
	leftLeg.addChild(RIGHT_LEG);
	leftLeg.addChild(LEFT_LEG);
	rightLeg.addChild(RIGHT_SHOE);
	leftLeg.addChild(LEFT_SHOE);
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue,
	    float alpha) {
	if (HEAD.visible) {
	    HEAD.copyFrom(head.createShallowCopy());
	    HEAD.render(matrixStack, buffer, packedLight, packedOverlay);
	}
	if (CHEST.visible) {
	    CHEST.copyFrom(body.createShallowCopy());
	    CHEST.render(matrixStack, buffer, packedLight, packedOverlay);
	    RIGHT_ARM.copyFrom(rightArm.createShallowCopy());
	    RIGHT_ARM.render(matrixStack, buffer, packedLight, packedOverlay);
	    LEFT_ARM.copyFrom(leftArm.createShallowCopy());
	    LEFT_ARM.render(matrixStack, buffer, packedLight, packedOverlay);
	}
	if (RIGHT_LEG.visible) {
	    RIGHT_LEG.copyFrom(rightLeg.createShallowCopy());
	    RIGHT_LEG.render(matrixStack, buffer, packedLight, packedOverlay);
	    LEFT_LEG.copyFrom(leftLeg.createShallowCopy());
	    LEFT_LEG.render(matrixStack, buffer, packedLight, packedOverlay);
	}
	if (RIGHT_SHOE.visible) {
	    RIGHT_SHOE.copyFrom(leftLeg.createShallowCopy());
	    RIGHT_SHOE.render(matrixStack, buffer, packedLight, packedOverlay);
	    LEFT_SHOE.copyFrom(rightLeg.createShallowCopy());
	    LEFT_SHOE.render(matrixStack, buffer, packedLight, packedOverlay);
	}
    }
}
