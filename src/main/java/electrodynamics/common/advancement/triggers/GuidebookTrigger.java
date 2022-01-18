package electrodynamics.common.advancement.triggers;

import com.google.gson.JsonObject;

import electrodynamics.common.settings.Constants;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class GuidebookTrigger extends SimpleCriterionTrigger<GuidebookTrigger.TriggerInstance> {
	
	public static final ResourceLocation ID = new ResourceLocation("guidebook");

	   public ResourceLocation getId() {
	      return ID;
	   }

	   public GuidebookTrigger.TriggerInstance createInstance(JsonObject p_70644_, EntityPredicate.Composite p_70645_, DeserializationContext p_70646_) {
	      return new GuidebookTrigger.TriggerInstance(p_70645_);
	   }

	   public void trigger(ServerPlayer player) {
	      this.trigger(player, (instance) -> {
	         return Constants.DISPENSE_GUIDEBOOK;
	      });
	   }

	   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
	      public TriggerInstance(EntityPredicate.Composite composite) {
	         super(GuidebookTrigger.ID, composite);
	      }
	   }
	
}
