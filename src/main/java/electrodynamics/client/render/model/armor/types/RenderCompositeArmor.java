package electrodynamics.client.render.model.armor.types;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class RenderCompositeArmor<T extends Entity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in
    // the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "composite_armor"), "main");
    private final ModelPart head;
    private final ModelPart chest;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart rightshoe;
    private final ModelPart leftshoe;

    public RenderCompositeArmor(ModelPart root) {
	this.head = root.getChild("head");
	this.chest = root.getChild("chest");
	this.rightArm = root.getChild("rightArm");
	this.leftArm = root.getChild("leftArm");
	this.rightLeg = root.getChild("rightLeg");
	this.leftLeg = root.getChild("leftLeg");
	this.rightshoe = root.getChild("rightshoe");
	this.leftshoe = root.getChild("leftshoe");
    }

    public static LayerDefinition createBodyLayer() {
	MeshDefinition meshdefinition = new MeshDefinition();
	PartDefinition partdefinition = meshdefinition.getRoot();

	partdefinition.addOrReplaceChild("head",
		CubeListBuilder.create().texOffs(19, 27).addBox(-6.0F, -6.0F, -4.0F, 1.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(30, 27)
			.addBox(-5.0F, -6.0F, 4.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(30, 10)
			.addBox(4.0F, -6.0F, 4.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 27)
			.addBox(5.0F, -6.0F, -4.0F, 1.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(0, 27)
			.addBox(-5.0F, -6.0F, -5.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 10)
			.addBox(3.0F, -6.0F, -5.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(55, 34)
			.addBox(-4.0F, -6.0F, 5.0F, 8.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(25, 0)
			.addBox(-3.0F, -8.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
			.addBox(-4.0F, -9.0F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(19, 10)
			.addBox(4.0F, -8.0F, -4.0F, 1.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(17, 41)
			.addBox(-4.0F, -8.0F, 4.0F, 8.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 10)
			.addBox(-5.0F, -8.0F, -4.0F, 1.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(44, 3)
			.addBox(-4.0F, -1.0F, -5.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(44, 0)
			.addBox(-5.0F, -2.0F, -5.0F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(71, 69)
			.addBox(-4.0F, -8.0F, -5.0F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
		PartPose.offset(0.0F, 0.0F, 0.0F));

	partdefinition.addOrReplaceChild("chest",
		CubeListBuilder.create().texOffs(38, 21).addBox(-4.0F, -23.5F, -3.0F, 8.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(38, 8)
			.addBox(-4.0F, -23.5F, 2.0F, 8.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(73, 6)
			.addBox(-4.0F, -23.5F, -4.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(11, 10)
			.addBox(-3.0F, -22.5F, -4.0F, 6.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
			.addBox(-1.0F, -22.5F, -5.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(80, 59)
			.addBox(-1.0F, -16.5F, -4.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(57, 23)
			.addBox(-4.0F, -23.5F, 3.0F, 8.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(79, 9)
			.addBox(-2.0F, -20.0F, 3.0F, 4.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 41)
			.addBox(-4.0F, -24.0F, -2.0F, 8.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(51, 55)
			.addBox(-4.0F, -24.0F, -2.0F, 0.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(42, 55)
			.addBox(4.0F, -24.0F, -2.0F, 0.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(38, 34)
			.addBox(-4.0F, -24.0F, 2.0F, 8.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)),
		PartPose.offset(0.0F, 24.0F, 0.0F));

	partdefinition.addOrReplaceChild("rightArm",
		CubeListBuilder.create().texOffs(0, 54).addBox(-4.0F, -2.0F, -2.0F, 1.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(33, 54)
			.addBox(1.0F, -2.0F, -2.0F, 0.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 71)
			.addBox(-3.0F, -2.0F, -3.0F, 4.0F, 12.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(60, 69)
			.addBox(-3.0F, -2.0F, 2.0F, 4.0F, 12.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(66, 0)
			.addBox(-3.0F, -3.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
		PartPose.offset(-5.0F, 2.0F, 0.0F));

	partdefinition.addOrReplaceChild("leftArm",
		CubeListBuilder.create().texOffs(24, 51).addBox(-1.0F, -2.0F, -2.0F, 0.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(13, 51)
			.addBox(3.0F, -2.0F, -2.0F, 1.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(22, 68)
			.addBox(-1.0F, -2.0F, -3.0F, 4.0F, 12.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(11, 68)
			.addBox(-1.0F, -2.0F, 2.0F, 4.0F, 12.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(57, 28)
			.addBox(-1.0F, -3.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
		PartPose.offset(5.0F, 2.0F, 0.0F));

	partdefinition.addOrReplaceChild("rightLeg",
		CubeListBuilder.create().texOffs(77, 43).addBox(-2.1F, 0.0F, -3.0F, 4.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(77, 17)
			.addBox(-2.1F, 0.0F, 2.0F, 4.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(66, 41)
			.addBox(-3.1F, 0.0F, -2.0F, 1.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(71, 55)
			.addBox(2.0F, 0.0F, -2.0F, 0.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(51, 79)
			.addBox(-1.6F, 3.0F, -4.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
		PartPose.offset(-1.9F, 12.0F, 0.0F));

	partdefinition.addOrReplaceChild("leftLeg",
		CubeListBuilder.create().texOffs(74, 32).addBox(-1.9F, 0.0F, -3.0F, 4.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(71, 73)
			.addBox(-1.9F, 0.0F, 2.0F, 4.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(33, 71)
			.addBox(-1.8F, 0.0F, -2.0F, 0.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(60, 55)
			.addBox(2.1F, 0.0F, -2.0F, 1.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(42, 79)
			.addBox(-1.4F, 3.0F, -4.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
		PartPose.offset(1.9F, 12.0F, 0.0F));

	partdefinition.addOrReplaceChild("rightshoe",
		CubeListBuilder.create().texOffs(57, 3).addBox(-1.8F, 4.0F, -4.0F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(66, 7)
			.addBox(-5.8F, 4.0F, -4.0F, 0.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(11, 27)
			.addBox(-5.8F, 4.0F, -4.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(55, 41)
			.addBox(-5.8F, 4.0F, 2.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(30, 47)
			.addBox(-5.8F, 7.1F, -4.0F, 4.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)),
		PartPose.offset(5.9F, 17.0F, 0.0F));

	partdefinition.addOrReplaceChild("leftshoe",
		CubeListBuilder.create().texOffs(70, 22).addBox(2.0F, 4.0F, -4.0F, 0.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(57, 13)
			.addBox(-3.0F, 4.0F, -4.0F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(76, 54)
			.addBox(-2.0F, 4.0F, 2.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(42, 72)
			.addBox(-2.0F, 4.0F, -4.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(45, 48)
			.addBox(-2.0F, 7.1F, -4.0F, 4.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)),
		PartPose.offset(-1.9F, 17.0F, 0.0F));

	return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue,
	    float alpha) {
	head.render(poseStack, buffer, packedLight, packedOverlay);
	chest.render(poseStack, buffer, packedLight, packedOverlay);
	rightArm.render(poseStack, buffer, packedLight, packedOverlay);
	leftArm.render(poseStack, buffer, packedLight, packedOverlay);
	rightLeg.render(poseStack, buffer, packedLight, packedOverlay);
	leftLeg.render(poseStack, buffer, packedLight, packedOverlay);
	rightshoe.render(poseStack, buffer, packedLight, packedOverlay);
	leftshoe.render(poseStack, buffer, packedLight, packedOverlay);
    }
}