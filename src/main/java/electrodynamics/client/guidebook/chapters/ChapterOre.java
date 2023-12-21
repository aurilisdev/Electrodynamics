package electrodynamics.client.guidebook.chapters;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.client.guidebook.ScreenGuidebook;
import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.OnKeyPress;
import electrodynamics.client.guidebook.utils.pagedata.OnTooltip;
import electrodynamics.client.guidebook.utils.pagedata.graphics.AbstractGraphicWrapper.GraphicTextDescriptor;
import electrodynamics.client.guidebook.utils.pagedata.graphics.ItemWrapperObject;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeOreDeepslate;
import electrodynamics.compatibility.jei.JeiBuffer;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

public class ChapterOre extends Chapter {

	private static final ItemWrapperObject LOGO = new ItemWrapperObject(7, 10, 32, 32, 32, 2.0F, ElectrodynamicsItems.getItem(SubtypeOre.tin));

	public ChapterOre(Module module) {
		super(module);
	}

	@Override
	public ItemWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public MutableComponent getTitle() {
		return ElectroTextUtils.guidebook("chapter.ores");
	}

	@Override
	public void addData() {

		// Regular Ores

		pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.getItem(SubtypeOre.aluminum), new GraphicTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.aluminum).getDescription().copy().withStyle(ChatFormatting.ITALIC)), new GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_aluminum"))), new GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.aluminum.minY, SubtypeOre.aluminum.maxY)), new GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.aluminum.veinsPerChunk)), new GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.aluminum.veinSize)), new GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.aluminum.harvestLevel))).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					screen.renderTooltip(stack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(ChatFormatting.GRAY), xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.getItem(SubtypeOre.aluminum));
			}

		}));

		pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.getItem(SubtypeOre.chromite), new GraphicTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.chromite).getDescription().copy().withStyle(ChatFormatting.ITALIC)), new GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_chromium"))), new GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.chromite.minY, SubtypeOre.chromite.maxY)), new GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.chromite.veinsPerChunk)), new GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.chromite.veinSize)), new GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.chromite.harvestLevel))).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					screen.renderTooltip(stack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(ChatFormatting.GRAY), xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.getItem(SubtypeOre.chromite));
			}

		}));

		pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.getItem(SubtypeOre.fluorite), new GraphicTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.fluorite).getDescription().copy().withStyle(ChatFormatting.ITALIC)), new GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_fluorite"))), new GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.fluorite.minY, SubtypeOre.fluorite.maxY)), new GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.fluorite.veinsPerChunk)), new GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.fluorite.veinSize)), new GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.fluorite.harvestLevel))).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					screen.renderTooltip(stack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(ChatFormatting.GRAY), xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.getItem(SubtypeOre.fluorite));
			}

		}));

		pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.getItem(SubtypeOre.lead), new GraphicTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.lead).getDescription().copy().withStyle(ChatFormatting.ITALIC)), new GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_lead"))), new GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.lead.minY, SubtypeOre.lead.maxY)), new GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.lead.veinsPerChunk)), new GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.lead.veinSize)), new GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.lead.harvestLevel))).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					screen.renderTooltip(stack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(ChatFormatting.GRAY), xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.getItem(SubtypeOre.lead));
			}

		}));

		pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.getItem(SubtypeOre.lepidolite), new GraphicTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.lepidolite).getDescription().copy().withStyle(ChatFormatting.ITALIC)), new GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_lithium"))), new GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.lepidolite.minY, SubtypeOre.lepidolite.maxY)), new GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.lepidolite.veinsPerChunk)), new GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.lepidolite.veinSize)), new GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.lepidolite.harvestLevel))).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					screen.renderTooltip(stack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(ChatFormatting.GRAY), xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.getItem(SubtypeOre.lepidolite));
			}

		}));

		pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.getItem(SubtypeOre.molybdenum), new GraphicTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.molybdenum).getDescription().copy().withStyle(ChatFormatting.ITALIC)), new GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_molybdenum"))), new GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.molybdenum.minY, SubtypeOre.molybdenum.maxY)), new GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.molybdenum.veinsPerChunk)), new GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.molybdenum.veinSize)), new GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.molybdenum.harvestLevel))).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					screen.renderTooltip(stack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(ChatFormatting.GRAY), xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.getItem(SubtypeOre.molybdenum));
			}

		}));

		pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.getItem(SubtypeOre.monazite), new GraphicTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.monazite).getDescription().copy().withStyle(ChatFormatting.ITALIC)), new GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_monazite"))), new GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.monazite.minY, SubtypeOre.monazite.maxY)), new GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.monazite.veinsPerChunk)), new GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.monazite.veinSize)), new GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.monazite.harvestLevel))).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					screen.renderTooltip(stack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(ChatFormatting.GRAY), xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.getItem(SubtypeOre.monazite));
			}

		}));

		pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.getItem(SubtypeOre.niter), new GraphicTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.niter).getDescription().copy().withStyle(ChatFormatting.ITALIC)), new GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_niter"))), new GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.niter.minY, SubtypeOre.niter.maxY)), new GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.niter.veinsPerChunk)), new GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.niter.veinSize)), new GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.niter.harvestLevel))).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					screen.renderTooltip(stack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(ChatFormatting.GRAY), xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.getItem(SubtypeOre.niter));
			}

		}));

		pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.getItem(SubtypeOre.halite), new GraphicTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.halite).getDescription().copy().withStyle(ChatFormatting.ITALIC)), new GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_salt"))), new GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.halite.minY, SubtypeOre.halite.maxY)), new GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.halite.veinsPerChunk)), new GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.halite.veinSize)), new GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.halite.harvestLevel))).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					screen.renderTooltip(stack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(ChatFormatting.GRAY), xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.getItem(SubtypeOre.halite));
			}

		}));

		pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.getItem(SubtypeOre.silver), new GraphicTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.silver).getDescription().copy().withStyle(ChatFormatting.ITALIC)), new GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_silver"))), new GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.silver.minY, SubtypeOre.silver.maxY)), new GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.silver.veinsPerChunk)), new GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.silver.veinSize)), new GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.silver.harvestLevel))).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					screen.renderTooltip(stack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(ChatFormatting.GRAY), xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.getItem(SubtypeOre.silver));
			}

		}));

		pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.getItem(SubtypeOre.sulfur), new GraphicTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.sulfur).getDescription().copy().withStyle(ChatFormatting.ITALIC)), new GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_sulfur"))), new GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.sulfur.minY, SubtypeOre.sulfur.maxY)), new GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.sulfur.veinsPerChunk)), new GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.sulfur.veinSize)), new GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.sulfur.harvestLevel))).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					screen.renderTooltip(stack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(ChatFormatting.GRAY), xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.getItem(SubtypeOre.sulfur));
			}

		}));

		pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.getItem(SubtypeOre.sylvite), new GraphicTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.sylvite).getDescription().copy().withStyle(ChatFormatting.ITALIC)), new GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_sylvite"))), new GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.sylvite.minY, SubtypeOre.sylvite.maxY)), new GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.sylvite.veinsPerChunk)), new GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.sylvite.veinSize)), new GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.sylvite.harvestLevel))).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					screen.renderTooltip(stack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(ChatFormatting.GRAY), xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.getItem(SubtypeOre.sylvite));
			}

		}));

		pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.getItem(SubtypeOre.tin), new GraphicTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.tin).getDescription().copy().withStyle(ChatFormatting.ITALIC)), new GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_tin"))), new GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.tin.minY, SubtypeOre.tin.maxY)), new GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.tin.veinsPerChunk)), new GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.tin.veinSize)), new GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.tin.harvestLevel))).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					screen.renderTooltip(stack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(ChatFormatting.GRAY), xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.getItem(SubtypeOre.tin));
			}

		}));

		pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.getItem(SubtypeOre.rutile), new GraphicTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.rutile).getDescription().copy().withStyle(ChatFormatting.ITALIC)), new GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_titanium"))), new GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.rutile.minY, SubtypeOre.rutile.maxY)), new GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.rutile.veinsPerChunk)), new GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.rutile.veinSize)), new GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.rutile.harvestLevel))).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					screen.renderTooltip(stack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(ChatFormatting.GRAY), xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.getItem(SubtypeOre.rutile));
			}

		}));

		pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.getItem(SubtypeOre.thorianite), new GraphicTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.thorianite).getDescription().copy().withStyle(ChatFormatting.ITALIC)), new GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_thorium"))), new GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.thorianite.minY, SubtypeOre.thorianite.maxY)), new GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.thorianite.veinsPerChunk)), new GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.thorianite.veinSize)), new GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.thorianite.harvestLevel))).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					screen.renderTooltip(stack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(ChatFormatting.GRAY), xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.getItem(SubtypeOre.thorianite));
			}

		}));

		pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.getItem(SubtypeOre.uraninite), new GraphicTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.uraninite).getDescription().copy().withStyle(ChatFormatting.ITALIC)), new GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_uranium"))), new GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.uraninite.minY, SubtypeOre.uraninite.maxY)), new GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.uraninite.veinsPerChunk)), new GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.uraninite.veinSize)), new GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.uraninite.harvestLevel))).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					screen.renderTooltip(stack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(ChatFormatting.GRAY), xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.getItem(SubtypeOre.uraninite));
			}

		}));

		pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.getItem(SubtypeOre.vanadinite), new GraphicTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.vanadinite).getDescription().copy().withStyle(ChatFormatting.ITALIC)), new GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_vanadium"))), new GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.vanadinite.minY, SubtypeOre.vanadinite.maxY)), new GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.vanadinite.veinsPerChunk)), new GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.vanadinite.veinSize)), new GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.vanadinite.harvestLevel))).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					screen.renderTooltip(stack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(ChatFormatting.GRAY), xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.getItem(SubtypeOre.vanadinite));
			}

		}));

		// Deep Ores

		pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.aluminum), new GraphicTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.aluminum).getDescription().copy().withStyle(ChatFormatting.ITALIC)), new GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_aluminum"))), new GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.aluminum.minY, SubtypeOreDeepslate.aluminum.maxY)), new GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOreDeepslate.aluminum.veinsPerChunk)), new GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOreDeepslate.aluminum.veinSize)), new GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOreDeepslate.aluminum.harvestLevel))).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					screen.renderTooltip(stack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(ChatFormatting.GRAY), xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.getItem(SubtypeOreDeepslate.aluminum));
			}

		}));

		pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.chromite), new GraphicTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.chromite).getDescription().copy().withStyle(ChatFormatting.ITALIC)), new GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_chromium"))), new GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.chromite.minY, SubtypeOreDeepslate.chromite.maxY)), new GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOreDeepslate.chromite.veinsPerChunk)), new GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOreDeepslate.chromite.veinSize)), new GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOreDeepslate.chromite.harvestLevel))).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					screen.renderTooltip(stack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(ChatFormatting.GRAY), xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.getItem(SubtypeOreDeepslate.chromite));
			}

		}));

		pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.fluorite), new GraphicTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.fluorite).getDescription().copy().withStyle(ChatFormatting.ITALIC)), new GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_fluorite"))), new GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.fluorite.minY, SubtypeOreDeepslate.fluorite.maxY)), new GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOreDeepslate.fluorite.veinsPerChunk)), new GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOreDeepslate.fluorite.veinSize)), new GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOreDeepslate.fluorite.harvestLevel))).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					screen.renderTooltip(stack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(ChatFormatting.GRAY), xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.getItem(SubtypeOreDeepslate.fluorite));
			}

		}));

		pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.lead), new GraphicTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.lead).getDescription().copy().withStyle(ChatFormatting.ITALIC)), new GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_lead"))), new GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.lead.minY, SubtypeOreDeepslate.lead.maxY)), new GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOreDeepslate.lead.veinsPerChunk)), new GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOreDeepslate.lead.veinSize)), new GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOreDeepslate.lead.harvestLevel))).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					screen.renderTooltip(stack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(ChatFormatting.GRAY), xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.getItem(SubtypeOreDeepslate.lead));
			}

		}));

		pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.lepidolite), new GraphicTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.lepidolite).getDescription().copy().withStyle(ChatFormatting.ITALIC)), new GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_lithium"))), new GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.lepidolite.minY, SubtypeOreDeepslate.lepidolite.maxY)), new GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOreDeepslate.lepidolite.veinsPerChunk)), new GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOreDeepslate.lepidolite.veinSize)), new GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOreDeepslate.lepidolite.harvestLevel))).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					screen.renderTooltip(stack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(ChatFormatting.GRAY), xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.getItem(SubtypeOreDeepslate.lepidolite));
			}

		}));

		pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.molybdenum), new GraphicTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.molybdenum).getDescription().copy().withStyle(ChatFormatting.ITALIC)), new GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_molybdenum"))), new GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.molybdenum.minY, SubtypeOreDeepslate.molybdenum.maxY)), new GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOreDeepslate.molybdenum.veinsPerChunk)), new GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOreDeepslate.molybdenum.veinSize)), new GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOreDeepslate.molybdenum.harvestLevel))).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					screen.renderTooltip(stack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(ChatFormatting.GRAY), xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.getItem(SubtypeOreDeepslate.molybdenum));
			}

		}));

		pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.monazite), new GraphicTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.monazite).getDescription().copy().withStyle(ChatFormatting.ITALIC)), new GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_monazite"))), new GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.monazite.minY, SubtypeOreDeepslate.monazite.maxY)), new GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOreDeepslate.monazite.veinsPerChunk)), new GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOreDeepslate.monazite.veinSize)), new GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOreDeepslate.monazite.harvestLevel))).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					screen.renderTooltip(stack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(ChatFormatting.GRAY), xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.getItem(SubtypeOreDeepslate.monazite));
			}

		}));

		pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.niter), new GraphicTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.niter).getDescription().copy().withStyle(ChatFormatting.ITALIC)), new GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_niter"))), new GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.niter.minY, SubtypeOreDeepslate.niter.maxY)), new GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOreDeepslate.niter.veinsPerChunk)), new GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOreDeepslate.niter.veinSize)), new GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOreDeepslate.niter.harvestLevel))).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					screen.renderTooltip(stack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(ChatFormatting.GRAY), xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.getItem(SubtypeOreDeepslate.niter));
			}

		}));

		pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.halite), new GraphicTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.halite).getDescription().copy().withStyle(ChatFormatting.ITALIC)), new GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_salt"))), new GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.halite.minY, SubtypeOreDeepslate.halite.maxY)), new GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOreDeepslate.halite.veinsPerChunk)), new GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOreDeepslate.halite.veinSize)), new GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOreDeepslate.halite.harvestLevel))).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					screen.renderTooltip(stack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(ChatFormatting.GRAY), xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.getItem(SubtypeOreDeepslate.halite));
			}

		}));

		pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.silver), new GraphicTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.silver).getDescription().copy().withStyle(ChatFormatting.ITALIC)), new GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_silver"))), new GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.silver.minY, SubtypeOreDeepslate.silver.maxY)), new GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOreDeepslate.silver.veinsPerChunk)), new GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOreDeepslate.silver.veinSize)), new GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOreDeepslate.silver.harvestLevel))).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					screen.renderTooltip(stack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(ChatFormatting.GRAY), xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.getItem(SubtypeOreDeepslate.silver));
			}

		}));

		pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.sulfur), new GraphicTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.sulfur).getDescription().copy().withStyle(ChatFormatting.ITALIC)), new GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_sulfur"))), new GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.sulfur.minY, SubtypeOreDeepslate.sulfur.maxY)), new GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOreDeepslate.sulfur.veinsPerChunk)), new GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOreDeepslate.sulfur.veinSize)), new GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOreDeepslate.sulfur.harvestLevel))).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					screen.renderTooltip(stack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(ChatFormatting.GRAY), xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.getItem(SubtypeOreDeepslate.sulfur));
			}

		}));

		pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.sylvite), new GraphicTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.sylvite).getDescription().copy().withStyle(ChatFormatting.ITALIC)), new GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_sylvite"))), new GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.sylvite.minY, SubtypeOreDeepslate.sylvite.maxY)), new GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOreDeepslate.sylvite.veinsPerChunk)), new GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOreDeepslate.sylvite.veinSize)), new GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOreDeepslate.sylvite.harvestLevel))).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					screen.renderTooltip(stack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(ChatFormatting.GRAY), xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.getItem(SubtypeOreDeepslate.sylvite));
			}

		}));

		pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.tin), new GraphicTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.tin).getDescription().copy().withStyle(ChatFormatting.ITALIC)), new GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_tin"))), new GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.tin.minY, SubtypeOreDeepslate.tin.maxY)), new GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOreDeepslate.tin.veinsPerChunk)), new GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOreDeepslate.tin.veinSize)), new GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOreDeepslate.tin.harvestLevel))).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					screen.renderTooltip(stack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(ChatFormatting.GRAY), xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.getItem(SubtypeOreDeepslate.tin));
			}

		}));

		pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.rutile), new GraphicTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.rutile).getDescription().copy().withStyle(ChatFormatting.ITALIC)), new GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_titanium"))), new GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.rutile.minY, SubtypeOreDeepslate.rutile.maxY)), new GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOreDeepslate.rutile.veinsPerChunk)), new GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOreDeepslate.rutile.veinSize)), new GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOreDeepslate.rutile.harvestLevel))).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					screen.renderTooltip(stack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(ChatFormatting.GRAY), xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.getItem(SubtypeOreDeepslate.rutile));
			}

		}));

		pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.thorianite), new GraphicTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.thorianite).getDescription().copy().withStyle(ChatFormatting.ITALIC)), new GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_thorium"))), new GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.thorianite.minY, SubtypeOreDeepslate.thorianite.maxY)), new GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOreDeepslate.thorianite.veinsPerChunk)), new GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOreDeepslate.thorianite.veinSize)), new GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOreDeepslate.thorianite.harvestLevel))).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					screen.renderTooltip(stack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(ChatFormatting.GRAY), xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.getItem(SubtypeOreDeepslate.thorianite));
			}

		}));

		pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.uraninite), new GraphicTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.uraninite).getDescription().copy().withStyle(ChatFormatting.ITALIC)), new GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_uranium"))), new GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.uraninite.minY, SubtypeOreDeepslate.uraninite.maxY)), new GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOreDeepslate.uraninite.veinsPerChunk)), new GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOreDeepslate.uraninite.veinSize)), new GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOreDeepslate.uraninite.harvestLevel))).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					screen.renderTooltip(stack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(ChatFormatting.GRAY), xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.getItem(SubtypeOreDeepslate.uraninite));
			}

		}));

		pageData.add(new ItemWrapperObject(7, 10, 32, 32, 50, 2.0F, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.vanadinite), new GraphicTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.vanadinite).getDescription().copy().withStyle(ChatFormatting.ITALIC)), new GraphicTextDescriptor(40, 10, ElectroTextUtils.guidebook("chapter.ores.material", ElectroTextUtils.guidebook("chapter.ores.material_vanadium"))), new GraphicTextDescriptor(40, 20, ElectroTextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.vanadinite.minY, SubtypeOreDeepslate.vanadinite.maxY)), new GraphicTextDescriptor(40, 30, ElectroTextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOreDeepslate.vanadinite.veinsPerChunk)), new GraphicTextDescriptor(40, 40, ElectroTextUtils.guidebook("chapter.ores.veinsize", SubtypeOreDeepslate.vanadinite.veinSize)), new GraphicTextDescriptor(40, 50, ElectroTextUtils.guidebook("chapter.ores.miningteir", SubtypeOreDeepslate.vanadinite.harvestLevel))).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					screen.renderTooltip(stack, ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(ChatFormatting.GRAY), xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.getItem(SubtypeOreDeepslate.vanadinite));
			}

		}));

	}

}