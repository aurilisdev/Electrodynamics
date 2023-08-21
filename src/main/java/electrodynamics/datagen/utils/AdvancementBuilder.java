package electrodynamics.datagen.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.extensions.IForgeAdvancementBuilder;

public class AdvancementBuilder implements IForgeAdvancementBuilder {

	public final ResourceLocation id;

	@Nullable
	private ResourceLocation parentId;
	@Nullable
	private Advancement parent;
	@Nullable
	private DisplayInfo display;
	private AdvancementRewards rewards = AdvancementRewards.EMPTY;
	private Map<String, Criterion> criteria = Maps.newLinkedHashMap();
	@Nullable
	private String[][] requirements;
	private RequirementsStrategy requirementsStrategy = RequirementsStrategy.AND;
	@Nullable
	private String comment;
	@Nullable
	private String author;

	@Nullable
	private List<ICondition> conditions;

	private AdvancementBuilder(ResourceLocation id) {
		this.id = id;
	}

	public static AdvancementBuilder create(ResourceLocation id) {
		return new AdvancementBuilder(id);
	}

	public AdvancementBuilder parent(Advancement parent) {
		this.parent = parent;
		return this;
	}

	public AdvancementBuilder parent(ResourceLocation parentId) {
		this.parentId = parentId;
		return this;
	}

	public AdvancementBuilder display(Item item, Component title, Component description, AdvancementBackgrounds background, FrameType frame, boolean showToast, boolean announceToChat, boolean hidden) {
		return this.display(new DisplayInfo(new ItemStack(item), title, description, background.loc, frame, showToast, announceToChat, hidden));
	}

	public AdvancementBuilder display(ItemStack stack, Component title, Component description, AdvancementBackgrounds background, FrameType frame, boolean showToast, boolean announceToChat, boolean hidden) {
		return this.display(new DisplayInfo(stack, title, description, background.loc, frame, showToast, announceToChat, hidden));
	}

	public AdvancementBuilder display(ItemStack stack, Component title, Component description, @Nullable ResourceLocation background, FrameType frame, boolean showToast, boolean announceToChat, boolean hidden) {
		return this.display(new DisplayInfo(stack, title, description, background, frame, showToast, announceToChat, hidden));
	}

	public AdvancementBuilder display(ItemLike item, Component title, Component description, @Nullable ResourceLocation background, FrameType frame, boolean showToast, boolean announceToChat, boolean hidden) {
		return this.display(new DisplayInfo(new ItemStack(item.asItem()), title, description, background, frame, showToast, announceToChat, hidden));
	}

	public AdvancementBuilder display(DisplayInfo display) {
		this.display = display;
		return this;
	}

	public AdvancementBuilder rewards(AdvancementRewards.Builder rewardsBuilder) {
		return this.rewards(rewardsBuilder.build());
	}

	public AdvancementBuilder rewards(AdvancementRewards rewards) {
		this.rewards = rewards;
		return this;
	}

	public AdvancementBuilder addCriterion(String key, CriterionTriggerInstance criterion) {
		return this.addCriterion(key, new Criterion(criterion));
	}

	public AdvancementBuilder addCriterion(String key, Criterion criterion) {
		if (this.criteria.containsKey(key)) {
			throw new IllegalArgumentException("Duplicate criterion " + key);
		}
		this.criteria.put(key, criterion);
		return this;
	}

	public AdvancementBuilder requirements(RequirementsStrategy strategy) {
		this.requirementsStrategy = strategy;
		return this;
	}

	public AdvancementBuilder requirements(String[][] requirements) {
		this.requirements = requirements;
		return this;
	}

	public AdvancementBuilder condition(ICondition condition) {
		if (conditions == null) {
			conditions = new ArrayList<>();
		}
		conditions.add(condition);
		return this;
	}

	public AdvancementBuilder comment(String comment) {
		this.comment = comment;
		return this;
	}

	public AdvancementBuilder author(String author) {
		this.author = author;
		return this;
	}

	/**
	 * Tries to resolve the parent of this advancement, if possible. Returns {@code true} on success.
	 */
	public boolean canBuild(Function<ResourceLocation, Advancement> parentLookup) {
		if (this.parentId == null) {
			return true;
		}
		if (this.parent == null) {
			this.parent = parentLookup.apply(this.parentId);
		}

		return this.parent != null;
	}

	public Advancement build() {
		if (!this.canBuild(resourceLocation -> null)) {
			throw new IllegalStateException("Tried to build incomplete advancement!");
		}
		if (this.requirements == null) {
			this.requirements = this.requirementsStrategy.createRequirements(this.criteria.keySet());
		}

		return new Advancement(id, this.parent, this.display, this.rewards, this.criteria, this.requirements, false);
	}

	public Advancement save(Consumer<AdvancementBuilder> consumer) {
		consumer.accept(this);
		return this.build();
	}

	public JsonObject serializeToJson() {
		if (this.requirements == null) {
			this.requirements = this.requirementsStrategy.createRequirements(this.criteria.keySet());
		}

		JsonObject jsonobject = new JsonObject();

		if (author != null) {
			jsonobject.addProperty("__author", author);
		}

		if (comment != null) {
			jsonobject.addProperty("__comment", comment);
		}

		if (this.parent != null) {
			jsonobject.addProperty("parent", this.parent.getId().toString());
		} else if (this.parentId != null) {
			jsonobject.addProperty("parent", this.parentId.toString());
		}

		if (this.display != null) {
			jsonobject.add("display", this.display.serializeToJson());
		}

		jsonobject.add("rewards", this.rewards.serializeToJson());
		JsonObject jsonobject1 = new JsonObject();

		for (Map.Entry<String, Criterion> entry : this.criteria.entrySet()) {
			jsonobject1.add(entry.getKey(), entry.getValue().serializeToJson());
		}

		jsonobject.add("criteria", jsonobject1);
		JsonArray jsonarray1 = new JsonArray();

		for (String[] astring : this.requirements) {
			JsonArray jsonarray = new JsonArray();

			for (String s : astring) {
				jsonarray.add(s);
			}

			jsonarray1.add(jsonarray);
		}

		jsonobject.add("requirements", jsonarray1);

		if (conditions != null) {
			JsonArray conds = new JsonArray();
			for (ICondition c : conditions) {
				conds.add(CraftingHelper.serialize(c));
			}
			jsonobject.add("conditions", conds);
		}

		return jsonobject;
	}

	public static enum AdvancementBackgrounds {

		NONE(null),
		// Vanilla
		ADVENTURE(new ResourceLocation("textures/gui/advancements/backgrounds/adventure.png")), //
		END(new ResourceLocation("textures/gui/advancements/backgrounds/end.png")), //
		HUSBANDRY(new ResourceLocation("textures/gui/advancements/backgrounds/husbandry.png")), //
		NETHER(new ResourceLocation("textures/gui/advancements/backgrounds/nether.png")), //
		STONE(new ResourceLocation("textures/gui/advancements/backgrounds/stone.png")); //

		public final ResourceLocation loc;

		private AdvancementBackgrounds(ResourceLocation loc) {
			this.loc = loc;
		}

	}

}
