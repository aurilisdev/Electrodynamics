package physica.library.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import sun.reflect.FieldAccessor;
import sun.reflect.ReflectionFactory;

public class ReflectionHelper {
	private static final String				MODIFIERS_FIELD	= "modifiers";

	private static final ReflectionFactory	reflection		= ReflectionFactory.getReflectionFactory();

	public static void setStaticFinalField(Field field, Object value) throws NoSuchFieldException, IllegalAccessException
	{
		field.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField(MODIFIERS_FIELD);
		modifiersField.setAccessible(true);
		int modifiers = modifiersField.getInt(field);
		modifiers &= ~Modifier.FINAL;
		modifiersField.setInt(field, modifiers);
		FieldAccessor fa = reflection.newFieldAccessor(field, false);
		fa.set(null, value);
	}
}