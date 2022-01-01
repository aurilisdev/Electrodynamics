package electrodynamics.client.render.model.armor.types;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import electrodynamics.api.References;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class ModelNightVisionGoggles<T extends LivingEntity> extends HumanoidModel<T> {
	
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(References.ID, "night_vision_goggles"), "main");
	
	private final ModelPart parentHat;
	private final ModelPart parentHead;
	private final ModelPart parentChest;
	private final ModelPart parentRightArm;
	private final ModelPart parentLeftArm;
	private final ModelPart parentRightLeg;
	private final ModelPart parentLeftLeg;
	
	private static final String HAT = "hat";
	private static final String HEAD = "head";
	private static final String CHEST = "body";
	private static final String RIGHT_ARM = "right_arm";
	private static final String LEFT_ARM = "left_arm";
	private static final String RIGHT_LEG = "right_leg";
	private static final String LEFT_LEG = "left_leg";
	
	
	public ModelNightVisionGoggles(ModelPart root) {
		super(root);
		
		this.parentHat = root.getChild(HAT);
		this.parentHead = root.getChild(HEAD);
		this.parentChest = root.getChild(CHEST);
		this.parentRightArm = root.getChild(RIGHT_ARM);
		this.parentLeftArm = root.getChild(LEFT_ARM);
		this.parentRightLeg = root.getChild(RIGHT_LEG);
		this.parentLeftLeg = root.getChild(LEFT_LEG);
		
		this.parentHat.visible = false;
		this.parentHead.visible = true;
		this.parentChest.visible = false;
		this.parentRightArm.visible = false;
		this.parentLeftArm.visible = false;
		this.parentRightLeg.visible = false;
		this.parentLeftLeg.visible = false;
	}

	public static LayerDefinition createBodyLayer() {
		
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition part = mesh.getRoot();

		part.addOrReplaceChild(HAT, CubeListBuilder.create(), PartPose.offset(0, 0, 0));
		
		part.addOrReplaceChild(HEAD, CubeListBuilder.create().texOffs(11, 28).addBox(-3.0F, -5.0F, -8.5F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 28).addBox(2.0F, -5.0F, -8.5F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(22, 0).addBox(-2.0F, -3.0F, -8.5F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 22).addBox(-2.0F, -6.0F, -8.5F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(17, 22).addBox(-2.0F, -5.0F, -8.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-1.0F, -7.0F, -6.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(11, 12).addBox(2.0F, -9.0F, -4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-3.0F, -9.0F, -4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(11, 3).addBox(-3.0F, -8.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(11, 0).addBox(2.0F, -8.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 10).addBox(2.0F, -8.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 3).addBox(-3.0F, -8.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(11, 2).addBox(4.0F, -7.0F, -4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(22, 12).addBox(-4.0F, -7.0F, 4.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 10).addBox(-5.0F, -7.0F, -4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(22, 6).addBox(-4.0F, -7.0F, -5.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		part.addOrReplaceChild(CHEST, CubeListBuilder.create(), PartPose.offset(0, 0, 0));
		part.addOrReplaceChild(RIGHT_ARM, CubeListBuilder.create(), PartPose.offset(0, 0, 0));
		part.addOrReplaceChild(LEFT_ARM, CubeListBuilder.create(), PartPose.offset(0, 0, 0));
		part.addOrReplaceChild(RIGHT_LEG, CubeListBuilder.create(), PartPose.offset(0, 0, 0));
		part.addOrReplaceChild(LEFT_LEG, CubeListBuilder.create(), PartPose.offset(0, 0, 0));
		
		return LayerDefinition.create(mesh, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		parentHead.render(poseStack, buffer, packedLight, packedOverlay);
	}
}