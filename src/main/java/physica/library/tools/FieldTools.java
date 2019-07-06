package physica.library.tools;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.List;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class FieldTools {

	public enum NBTSaveType {
		Boolean, Byte, Double, Float, IntArray, Integer, Long, Short, String, NBTBase
	}
	@Retention(value = RUNTIME)
	@Target(value = ElementType.FIELD)
	public @interface NBTSaveField {

		public String name();

		public NBTSaveType type();
	}
	public enum SyncSendType {
		Array, Collection, Byte, Boolean, Enum, Integer, Short, Long, Float, Double, UTF8String, NBTTagCompound, ItemStack, FluidTank
	}
	@Retention(value = RUNTIME)
	@Target(value = ElementType.FIELD)
	public @interface SyncSendField {

		public SyncSendType type();
	}

	public static void attemptWriteNBTSave(NBTTagCompound compound, Field field, Object instance) {
		Annotation[] annotations = field.getAnnotations();
		boolean isAccessible = field.isAccessible();
		if (!isAccessible) {
			field.setAccessible(true);
		}
		try {
			for (Annotation anno : annotations) {
				if (anno instanceof NBTSaveField) {
					NBTSaveField save = (NBTSaveField) anno;
					String name = save.name();
					switch (save.type()) {
					case Boolean:
						compound.setBoolean(name, field.getBoolean(instance));
						break;
					case Byte:
						compound.setByte(name, field.getByte(instance));
						break;
					case Double:
						compound.setDouble(name, field.getDouble(instance));
						break;
					case Float:
						compound.setFloat(name, field.getFloat(instance));
						break;
					case IntArray:
						compound.setIntArray(name, (int[]) field.get(instance));
						break;
					case Integer:
						compound.setInteger(name, field.getInt(instance));
						break;
					case Long:
						compound.setLong(name, field.getLong(instance));
						break;
					case NBTBase:
						compound.setTag(name, (NBTBase) field.get(instance));
						break;
					case Short:
						compound.setShort(name, field.getShort(instance));
						break;
					case String:
						compound.setString(name, (String) field.get(instance));
						break;
					default:
						break;
					}
					break;
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		if (!isAccessible) {
			field.setAccessible(false);
		}
	}

	public static void attemptReadNBTSave(NBTTagCompound compound, Field field, Object instance) {
		Annotation[] annotations = field.getAnnotations();
		boolean isAccessible = field.isAccessible();
		if (!isAccessible) {
			field.setAccessible(true);
		}
		try {
			for (Annotation anno : annotations) {
				if (anno instanceof NBTSaveField) {
					NBTSaveField save = (NBTSaveField) anno;
					String name = save.name();
					switch (save.type()) {
					case Boolean:
						field.set(instance, compound.getBoolean(name));
						break;
					case Byte:
						field.set(instance, compound.getByte(name));
						break;
					case Double:
						field.set(instance, compound.getDouble(name));
						break;
					case Float:
						field.set(instance, compound.getFloat(name));
						break;
					case IntArray:
						field.set(instance, compound.getIntArray(name));
						break;
					case Integer:
						field.set(instance, compound.getInteger(name));
						break;
					case Long:
						field.set(instance, compound.getLong(name));
						break;
					case NBTBase:
						field.set(instance, compound.getTag(name));
						break;
					case Short:
						field.set(instance, compound.getShort(name));
						break;
					case String:
						field.set(instance, compound.getString(name));
						break;
					default:
						break;
					}
					break;
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		if (!isAccessible) {
			field.setAccessible(false);
		}
	}

	public static void attemptWriteSync(List<Object> data, Field field, Object instance) {
		Annotation[] annotations = field.getAnnotations();
		boolean isAccessible = field.isAccessible();
		if (!isAccessible) {
			field.setAccessible(true);
		}
		try {
			for (Annotation anno : annotations) {
				if (anno instanceof SyncSendField) {
					data.add(field.get(instance));
					break;
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		if (!isAccessible) {
			field.setAccessible(false);
		}
	}

	public static void attemptReadSync(List<Object> data, Field field, Object instance) {
		Annotation[] annotations = field.getAnnotations();
		boolean isAccessible = field.isAccessible();
		if (!isAccessible) {
			field.setAccessible(true);
		}
		try {
			for (Annotation anno : annotations) {
				if (anno instanceof SyncSendField) {
					data.add(field.get(instance));
					break;
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		if (!isAccessible) {
			field.setAccessible(false);
		}
	}

}
