package electrodynamics.common.player.armor.types.composite;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * PlayerModel - Either Mojang or a mod author (Taken From Memory)
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class CompositeArmorModel extends BipedModel<LivingEntity> {
    
	//main render
	public ModelRenderer HELMET;
	public ModelRenderer TORSO;
	public ModelRenderer RIGHT_ARM;
    public ModelRenderer LEFT_ARM;
    public ModelRenderer RIGHT_LEG;
    public ModelRenderer LEFT_LEG;
    public ModelRenderer RIGHT_BOOT;
    public ModelRenderer LEFT_BOOT;
    
    //temp component
    public ModelRenderer TorsoPiece_1;

    public CompositeArmorModel(float modelSize) {
    	super(modelSize);
    	
        this.textureWidth = 256;
        this.textureHeight = 256;
        
        /*HELMET*/
        
        HELMET = new ModelRenderer(this, 0, 8);
        HELMET.setRotationPoint(0.0F, 0.0F, 0.0F);
        HELMET.setRotationPoint(0.0F, 0.0F, 0.0F);
        HELMET.setTextureOffset(20, 1).addBox(-4.0F, -9.0F, -4.0F, 8.0F, 1.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        HELMET.setTextureOffset(178, 0).addBox(-5.0F, -8.0F, -5.0F, 1.0F, 8.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        HELMET.setTextureOffset(200, 0).addBox(4.0F, -8.0F, -5.0F, 1.0F, 8.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        HELMET.setTextureOffset(0, 5).addBox(-4.0F, -8.0F, 4.0F, 8.0F, 7.9F, 1.0F, 0.0F, 0.0F, 0.0F);
        HELMET.setTextureOffset(44, 1).addBox(-4.0F, -8.0F, -5.0F, 8.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        HELMET.setTextureOffset(44, 5).addBox(-4.0F, -1.0F, -5.0F, 8.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        HELMET.setTextureOffset(102, 5).addBox(-5.0F, -6.0F, -6.0F, 10.0F, 6.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        HELMET.setTextureOffset(56, 3).addBox(-6.0F, -6.0F, -5.0F, 1.0F, 5.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        HELMET.setTextureOffset(134, 3).addBox(5.0F, -6.0F, -5.0F, 1.0F, 5.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        
        /*TORSO*/
        
        //front
        TORSO = new ModelRenderer(this, 0, 0);
        TORSO.setRotationPoint(0.0F, 0.0F, 0.0F);
        TORSO.addBox(-1.0F, 9.0F, -4.0F, 2.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        
        //back
        TorsoPiece_1 = new ModelRenderer(this, 6, 0);
        TorsoPiece_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        TorsoPiece_1.addBox(-5.0F, 0.0F, -3.0F, 10.0F, 12.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        TorsoPiece_1.setTextureOffset(22, 0).addBox(-5.0F, 1.5F, -4.0F, 10.0F, 7.5F, 1.0F, 0.0F, 0.0F, 0.0F);
        TorsoPiece_1.setTextureOffset(44, 0).addBox(-5.0F, 0.5F, -6.0F, 10.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        TorsoPiece_1.setTextureOffset(70, 0).addBox(-3.0F, 1.0F, -5.0F, 6.0F, 6.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        TorsoPiece_1.setTextureOffset(84, 0).addBox(-1.0F, 1.5F, -6.0F, 2.0F, 5.5F, 1.0F, 0.0F, 0.0F, 0.0F);
        TorsoPiece_1.setTextureOffset(90, 0).addBox(-1.0F, 7.0F, -5.0F, 2.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        TorsoPiece_1.setTextureOffset(96, 0).addBox(-1.0F, 9.0F, -4.0F, 2.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        TorsoPiece_1.setTextureOffset(102, 0).addBox(-5.0F, 0.0F, 2.0F, 10.0F, 12.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        TorsoPiece_1.setTextureOffset(124, 0).addBox(-5.0F, 0.5F, 2.0F, 10.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        TorsoPiece_1.setTextureOffset(150, 0).addBox(-5.0F, -3.0F, 5.0F, 10.0F, 4.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        TorsoPiece_1.setTextureOffset(172, 0).addBox(-5.0F, 1.5F, 3.0F, 10.0F, 1.5F, 1.0F, 0.0F, 0.0F, 0.0F);
        TorsoPiece_1.setTextureOffset(194, 0).addBox(-2.0F, 4.0F, 3.0F, 4.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        
        TORSO.addChild(TorsoPiece_1);
        
        /*RIGHT ARM*/
        
        RIGHT_ARM = new ModelRenderer(this, 50, 0);
        RIGHT_ARM.setRotationPoint(5.0F, 2.0F, 0.0F);
        RIGHT_ARM.setTextureOffset(192, 0).addBox(0.0F, -2.0F, -3.0F, 3.0F, 12.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        RIGHT_ARM.setTextureOffset(140, 3).addBox(0.0F, -2.0F, 2.0F, 3.0F, 12.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        RIGHT_ARM.setTextureOffset(42, 3).addBox(3.0F, -2.0F, -2.0F, 1.0F, 12.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        RIGHT_ARM.setTextureOffset(0, 4).addBox(-1.0F, -3.0F, -2.0F, 4.0F, 1.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        
        /*LEFT ARM*/
        
        LEFT_ARM = new ModelRenderer(this, 64, 0);
        LEFT_ARM.setRotationPoint(-5.0F, 2.0F, 0.0F);
        LEFT_ARM.setTextureOffset(148, 0).addBox(-3.0F, -2.0F, -3.0F, 3.0F, 12.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        LEFT_ARM.setTextureOffset(158, 0).addBox(-3.0F, -2.0F, 2.0F, 3.0F, 12.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        LEFT_ARM.setTextureOffset(168, 0).addBox(-4.0F, -2.0F, -2.0F, 1.0F, 12.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        LEFT_ARM.setTextureOffset(110, 3).addBox(-3.0F, -3.0F, -2.0F, 4.0F, 1.0F, 4.0F, 0.0F, 0.0F, 0.0F);
                       
        /*RIGHT LEG*/
        
        RIGHT_LEG = new ModelRenderer(this, 64, 4);
        RIGHT_LEG.setRotationPoint(2.0F, 12.0F, 0.0F);
        RIGHT_LEG.setTextureOffset(2, 0).addBox(-2.0F, 0.0F, -3.0F, 4.0F, 12.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        RIGHT_LEG.setTextureOffset(66, 0).addBox(-2.0F, 0.0F, 2.0F, 4.0F, 12.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        RIGHT_LEG.setTextureOffset(76, 0).addBox(-1.5F, 2.0F, -4.0F, 3.0F, 4.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        RIGHT_LEG.setTextureOffset(84, 0).addBox(2.0F, 0.0F, -2.0F, 1.0F, 9.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        
        /*LEFT LEG*/
        
        LEFT_LEG = new ModelRenderer(this, 64, 5);
        LEFT_LEG.setRotationPoint(-2.0F, 12.0F, 0.0F);
        LEFT_LEG.setTextureOffset(94, 0).addBox(-2.0F, 0.0F, -3.0F, 4.0F, 12.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        LEFT_LEG.setTextureOffset(12, 2).addBox(-2.0F, 0.0F, 2.0F, 4.0F, 12.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        LEFT_LEG.setTextureOffset(104, 0).addBox(-3.0F, 0.0F, -2.0F, 1.0F, 9.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        LEFT_LEG.setTextureOffset(114, 3).addBox(-1.5F, 2.0F, -4.0F, 3.0F, 4.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        
        /*RIGHT BOOT*/
        
        RIGHT_BOOT = new ModelRenderer(this, 64, 13);
        RIGHT_BOOT.setRotationPoint(2.0F, 21.0F, 0.0F);
        RIGHT_BOOT.setTextureOffset(178, 0).addBox(-2.0F, 0.0F, -5.0F, 4.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        RIGHT_BOOT.setTextureOffset(53, 0).addBox(2.0F, 0.0F, -4.0F, 1.0F, 3.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        
        
        /*LEFT BOOT*/
        
        LEFT_BOOT = new ModelRenderer(this, 64, 13);
        LEFT_BOOT.setRotationPoint(-2.0F, 21.0F, 0.0F);
        LEFT_BOOT.setTextureOffset(148, 0).addBox(-2.0F, 0.0F, -5.0F, 4.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        LEFT_BOOT.setTextureOffset(15, 0).addBox(-3.0F, 0.0F, -4.0F, 1.0F, 3.0F, 7.0F, 0.0F, 0.0F, 0.0F);
      
        
        
        
        //Connect Pieces to bodyparts

        bipedHead.addChild(HELMET);
        bipedRightArm.addChild(RIGHT_ARM);
        bipedLeftArm.addChild(LEFT_ARM);
        bipedRightLeg.addChild(RIGHT_LEG);
        bipedLeftLeg.addChild(LEFT_LEG);
        bipedRightLeg.addChild(RIGHT_BOOT);
        bipedLeftLeg.addChild(LEFT_BOOT);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.HELMET, this.TORSO, this.RIGHT_ARM, this.LEFT_ARM, this.RIGHT_LEG, this.LEFT_LEG, this.RIGHT_BOOT, this.LEFT_BOOT).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
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
