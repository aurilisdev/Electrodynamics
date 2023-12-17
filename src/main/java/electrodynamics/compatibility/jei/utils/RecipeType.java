package electrodynamics.compatibility.jei.utils;

import net.minecraft.util.ResourceLocation;

public final class RecipeType<T> {
	public static <T> RecipeType<T> create(String nameSpace, String path, Class<? extends T> recipeClass) {
		ResourceLocation uid = new ResourceLocation(nameSpace, path);
		return new RecipeType<>(uid, recipeClass);
	}

	private final ResourceLocation uid;
	private final Class<? extends T> recipeClass;

	public RecipeType(ResourceLocation uid, Class<? extends T> recipeClass) {
		this.uid = uid;
		this.recipeClass = recipeClass;
	}

	/**
	 * The unique id of this recipe type.
	 */
	public ResourceLocation getUid() {
		return uid;
	}

	/**
	 * The class of recipes represented by this recipe type.
	 */
	public Class<? extends T> getRecipeClass() {
		return recipeClass;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof RecipeType<?>)) {
			return false;
		}
		RecipeType<?> other = (RecipeType<?>) obj;
		return this.recipeClass == other.recipeClass && this.uid.equals(other.uid);
	}

	@Override
	public int hashCode() {
		return 31 * uid.hashCode() + recipeClass.hashCode();
	}

	@Override
	public String toString() {
		return "RecipeType[" + "uid=" + uid + ", " + "recipeClass=" + recipeClass + ']';
	}

}
