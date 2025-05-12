package electrodynamics.client.guidebook.chapters;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TextFormatting;
import voltaic.client.guidebook.ScreenGuidebook;
import voltaic.client.guidebook.utils.components.Chapter;
import voltaic.client.guidebook.utils.components.Module;
import voltaic.client.guidebook.utils.pagedata.OnKeyPress;
import voltaic.client.guidebook.utils.pagedata.OnTooltip;
import voltaic.client.guidebook.utils.pagedata.graphics.AbstractGraphicWrapper;
import voltaic.client.guidebook.utils.pagedata.graphics.ItemWrapperObject;
import voltaic.compatibility.jei.JeiBuffer;

public class ChapterOre extends Chapter {

    private static final ItemWrapperObject LOGO = new ItemWrapperObject(7, 10, 32, 32, 32, 2.0F, ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.tin));

    public ChapterOre(Module module) {
        super(module);
    }

    @Override
    public ItemWrapperObject getLogo() {
        return LOGO;
    }

    @Override
    public IFormattableTextComponent getTitle() {
        return ElectroTextUtils.guidebook("chapter.ores");
    }

    @Override
    public void addData() {

        // Regular Ores

        pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.aluminum), 
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 0, ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.aluminum).getDescription().copy().withStyle(TextFormatting.ITALIC)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_aluminum"))),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.aluminum.minY, SubtypeOre.aluminum.maxY)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.aluminum.veinsPerChunk)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.aluminum.veinSize)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.aluminum.harvestLevel))).onTooltip(new OnTooltip() {

            @Override
            public void onTooltip(MatrixStack poseStack, int xAxis, int yAxis, ScreenGuidebook screen) {
                if (JeiBuffer.isJeiInstalled()) {
                    screen.renderTooltip(poseStack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(TextFormatting.GRAY), xAxis, yAxis);
                }

            }
        }).onKeyPress(new OnKeyPress() {

            @Override
            public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

            }

            @Override
            public Object getJeiLookup() {
                return new ItemStack(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.aluminum));
            }

        }
        //
        ));

        pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.chromium),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 0, ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.chromium).getDescription().copy().withStyle(TextFormatting.ITALIC)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_chromium"))),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.chromium.minY, SubtypeOre.chromium.maxY)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.chromium.veinsPerChunk)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.chromium.veinSize)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.chromium.harvestLevel))).onTooltip(new OnTooltip() {

            @Override
            public void onTooltip(MatrixStack poseStack, int xAxis, int yAxis, ScreenGuidebook screen) {
                if (JeiBuffer.isJeiInstalled()) {
                    screen.renderTooltip(poseStack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(TextFormatting.GRAY), xAxis, yAxis);
                }

            }
        }).onKeyPress(new OnKeyPress() {

            @Override
            public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

            }

            @Override
            public Object getJeiLookup() {
                return new ItemStack(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.chromium));
            }

        }));

        pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.fluorite),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 0, ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.fluorite).getDescription().copy().withStyle(TextFormatting.ITALIC)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_fluorite"))),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.fluorite.minY, SubtypeOre.fluorite.maxY)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.fluorite.veinsPerChunk)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.fluorite.veinSize)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.fluorite.harvestLevel))).onTooltip(new OnTooltip() {

            @Override
            public void onTooltip(MatrixStack poseStack, int xAxis, int yAxis, ScreenGuidebook screen) {
                if (JeiBuffer.isJeiInstalled()) {
                    screen.renderTooltip(poseStack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(TextFormatting.GRAY), xAxis, yAxis);
                }

            }
        }).onKeyPress(new OnKeyPress() {

            @Override
            public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

            }

            @Override
            public Object getJeiLookup() {
                return new ItemStack(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.fluorite));
            }

        }));

        pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.lead),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 0, ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.lead).getDescription().copy().withStyle(TextFormatting.ITALIC)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_lead"))),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.lead.minY, SubtypeOre.lead.maxY)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.lead.veinsPerChunk)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.lead.veinSize)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.lead.harvestLevel))).onTooltip(new OnTooltip() {

            @Override
            public void onTooltip(MatrixStack poseStack, int xAxis, int yAxis, ScreenGuidebook screen) {
                if (JeiBuffer.isJeiInstalled()) {
                    screen.renderTooltip(poseStack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(TextFormatting.GRAY), xAxis, yAxis);
                }

            }
        }).onKeyPress(new OnKeyPress() {

            @Override
            public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

            }

            @Override
            public Object getJeiLookup() {
                return new ItemStack(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.lead));
            }

        }));

        pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.lithium),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 0, ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.lithium).getDescription().copy().withStyle(TextFormatting.ITALIC)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_lithium"))),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.lithium.minY, SubtypeOre.lithium.maxY)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.lithium.veinsPerChunk)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.lithium.veinSize)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.lithium.harvestLevel))).onTooltip(new OnTooltip() {

            @Override
            public void onTooltip(MatrixStack poseStack, int xAxis, int yAxis, ScreenGuidebook screen) {
                if (JeiBuffer.isJeiInstalled()) {
                    screen.renderTooltip(poseStack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(TextFormatting.GRAY), xAxis, yAxis);
                }

            }
        }).onKeyPress(new OnKeyPress() {

            @Override
            public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

            }

            @Override
            public Object getJeiLookup() {
                return new ItemStack(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.lithium));
            }

        }));

        pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.molybdenum),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 0, ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.molybdenum).getDescription().copy().withStyle(TextFormatting.ITALIC)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_molybdenum"))),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.molybdenum.minY, SubtypeOre.molybdenum.maxY)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.molybdenum.veinsPerChunk)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.molybdenum.veinSize)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.molybdenum.harvestLevel))).onTooltip(new OnTooltip() {

            @Override
            public void onTooltip(MatrixStack poseStack, int xAxis, int yAxis, ScreenGuidebook screen) {
                if (JeiBuffer.isJeiInstalled()) {
                    screen.renderTooltip(poseStack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(TextFormatting.GRAY), xAxis, yAxis);
                }

            }
        }).onKeyPress(new OnKeyPress() {

            @Override
            public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

            }

            @Override
            public Object getJeiLookup() {
                return new ItemStack(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.molybdenum));
            }

        }));

        pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.monazite),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 0, ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.monazite).getDescription().copy().withStyle(TextFormatting.ITALIC)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_monazite"))),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.monazite.minY, SubtypeOre.monazite.maxY)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.monazite.veinsPerChunk)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.monazite.veinSize)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.monazite.harvestLevel))).onTooltip(new OnTooltip() {

            @Override
            public void onTooltip(MatrixStack poseStack, int xAxis, int yAxis, ScreenGuidebook screen) {
                if (JeiBuffer.isJeiInstalled()) {
                    screen.renderTooltip(poseStack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(TextFormatting.GRAY), xAxis, yAxis);
                }

            }
        }).onKeyPress(new OnKeyPress() {

            @Override
            public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

            }

            @Override
            public Object getJeiLookup() {
                return new ItemStack(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.monazite));
            }

        }));

        pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.niter),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 0, ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.niter).getDescription().copy().withStyle(TextFormatting.ITALIC)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_niter"))),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.niter.minY, SubtypeOre.niter.maxY)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.niter.veinsPerChunk)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.niter.veinSize)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.niter.harvestLevel))).onTooltip(new OnTooltip() {

            @Override
            public void onTooltip(MatrixStack poseStack, int xAxis, int yAxis, ScreenGuidebook screen) {
                if (JeiBuffer.isJeiInstalled()) {
                    screen.renderTooltip(poseStack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(TextFormatting.GRAY), xAxis, yAxis);
                }

            }
        }).onKeyPress(new OnKeyPress() {

            @Override
            public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

            }

            @Override
            public Object getJeiLookup() {
                return new ItemStack(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.niter));
            }

        }));

        pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.salt),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 0, ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.salt).getDescription().copy().withStyle(TextFormatting.ITALIC)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_salt"))),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.salt.minY, SubtypeOre.salt.maxY)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.salt.veinsPerChunk)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.salt.veinSize)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.salt.harvestLevel))).onTooltip(new OnTooltip() {

            @Override
            public void onTooltip(MatrixStack poseStack, int xAxis, int yAxis, ScreenGuidebook screen) {
                if (JeiBuffer.isJeiInstalled()) {
                    screen.renderTooltip(poseStack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(TextFormatting.GRAY), xAxis, yAxis);
                }

            }
        }).onKeyPress(new OnKeyPress() {

            @Override
            public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

            }

            @Override
            public Object getJeiLookup() {
                return new ItemStack(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.salt));
            }

        }));

        pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.silver),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 0, ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.silver).getDescription().copy().withStyle(TextFormatting.ITALIC)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_silver"))),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.silver.minY, SubtypeOre.silver.maxY)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.silver.veinsPerChunk)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.silver.veinSize)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.silver.harvestLevel))).onTooltip(new OnTooltip() {

            @Override
            public void onTooltip(MatrixStack poseStack, int xAxis, int yAxis, ScreenGuidebook screen) {
                if (JeiBuffer.isJeiInstalled()) {
                    screen.renderTooltip(poseStack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(TextFormatting.GRAY), xAxis, yAxis);
                }

            }
        }).onKeyPress(new OnKeyPress() {

            @Override
            public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

            }

            @Override
            public Object getJeiLookup() {
                return new ItemStack(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.silver));
            }

        }));

        pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.sulfur),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 0, ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.sulfur).getDescription().copy().withStyle(TextFormatting.ITALIC)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_sulfur"))),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.sulfur.minY, SubtypeOre.sulfur.maxY)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.sulfur.veinsPerChunk)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.sulfur.veinSize)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.sulfur.harvestLevel))).onTooltip(new OnTooltip() {

            @Override
            public void onTooltip(MatrixStack poseStack, int xAxis, int yAxis, ScreenGuidebook screen) {
                if (JeiBuffer.isJeiInstalled()) {
                    screen.renderTooltip(poseStack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(TextFormatting.GRAY), xAxis, yAxis);
                }

            }
        }).onKeyPress(new OnKeyPress() {

            @Override
            public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

            }

            @Override
            public Object getJeiLookup() {
                return new ItemStack(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.sulfur));
            }

        }));

        pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.sylvite),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 0, ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.sylvite).getDescription().copy().withStyle(TextFormatting.ITALIC)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_sylvite"))),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.sylvite.minY, SubtypeOre.sylvite.maxY)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.sylvite.veinsPerChunk)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.sylvite.veinSize)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.sylvite.harvestLevel))).onTooltip(new OnTooltip() {

            @Override
            public void onTooltip(MatrixStack poseStack, int xAxis, int yAxis, ScreenGuidebook screen) {
                if (JeiBuffer.isJeiInstalled()) {
                    screen.renderTooltip(poseStack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(TextFormatting.GRAY), xAxis, yAxis);
                }

            }
        }).onKeyPress(new OnKeyPress() {

            @Override
            public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

            }

            @Override
            public Object getJeiLookup() {
                return new ItemStack(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.sylvite));
            }

        }));

        pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.tin),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 0, ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.tin).getDescription().copy().withStyle(TextFormatting.ITALIC)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_tin"))),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.tin.minY, SubtypeOre.tin.maxY)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.tin.veinsPerChunk)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.tin.veinSize)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.tin.harvestLevel))).onTooltip(new OnTooltip() {

            @Override
            public void onTooltip(MatrixStack poseStack, int xAxis, int yAxis, ScreenGuidebook screen) {
                if (JeiBuffer.isJeiInstalled()) {
                    screen.renderTooltip(poseStack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(TextFormatting.GRAY), xAxis, yAxis);
                }

            }
        }).onKeyPress(new OnKeyPress() {

            @Override
            public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

            }

            @Override
            public Object getJeiLookup() {
                return new ItemStack(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.tin));
            }

        }));

        pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.titanium),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 0, ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.titanium).getDescription().copy().withStyle(TextFormatting.ITALIC)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_titanium"))),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.titanium.minY, SubtypeOre.titanium.maxY)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.titanium.veinsPerChunk)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.titanium.veinSize)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.titanium.harvestLevel))).onTooltip(new OnTooltip() {

            @Override
            public void onTooltip(MatrixStack poseStack, int xAxis, int yAxis, ScreenGuidebook screen) {
                if (JeiBuffer.isJeiInstalled()) {
                    screen.renderTooltip(poseStack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(TextFormatting.GRAY), xAxis, yAxis);
                }

            }
        }).onKeyPress(new OnKeyPress() {

            @Override
            public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

            }

            @Override
            public Object getJeiLookup() {
                return new ItemStack(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.titanium));
            }

        }));

        pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.thorium),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 0, ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.thorium).getDescription().copy().withStyle(TextFormatting.ITALIC)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_thorium"))),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.thorium.minY, SubtypeOre.thorium.maxY)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.thorium.veinsPerChunk)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.thorium.veinSize)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.thorium.harvestLevel))).onTooltip(new OnTooltip() {

            @Override
            public void onTooltip(MatrixStack poseStack, int xAxis, int yAxis, ScreenGuidebook screen) {
                if (JeiBuffer.isJeiInstalled()) {
                    screen.renderTooltip(poseStack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(TextFormatting.GRAY), xAxis, yAxis);
                }

            }
        }).onKeyPress(new OnKeyPress() {

            @Override
            public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

            }

            @Override
            public Object getJeiLookup() {
                return new ItemStack(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.thorium));
            }

        }));

        pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.uranium),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 0, ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.uranium).getDescription().copy().withStyle(TextFormatting.ITALIC)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_uranium"))),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.uranium.minY, SubtypeOre.uranium.maxY)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.uranium.veinsPerChunk)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.uranium.veinSize)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.uranium.harvestLevel))).onTooltip(new OnTooltip() {

            @Override
            public void onTooltip(MatrixStack poseStack, int xAxis, int yAxis, ScreenGuidebook screen) {
                if (JeiBuffer.isJeiInstalled()) {
                    screen.renderTooltip(poseStack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(TextFormatting.GRAY), xAxis, yAxis);
                }

            }
        }).onKeyPress(new OnKeyPress() {

            @Override
            public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

            }

            @Override
            public Object getJeiLookup() {
                return new ItemStack(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.uranium));
            }

        }));

        pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.vanadium),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 0, ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.vanadium).getDescription().copy().withStyle(TextFormatting.ITALIC)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_vanadium"))),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.vanadium.minY, SubtypeOre.vanadium.maxY)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.vanadium.veinsPerChunk)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.vanadium.veinSize)),
                //
                new AbstractGraphicWrapper.GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.vanadium.harvestLevel))).onTooltip(new OnTooltip() {

            @Override
            public void onTooltip(MatrixStack poseStack, int xAxis, int yAxis, ScreenGuidebook screen) {
                if (JeiBuffer.isJeiInstalled()) {
                    screen.renderTooltip(poseStack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(TextFormatting.GRAY), xAxis, yAxis);
                }

            }
        }).onKeyPress(new OnKeyPress() {

            @Override
            public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

            }

            @Override
            public Object getJeiLookup() {
                return new ItemStack(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.vanadium));
            }

        }));

    }

}
