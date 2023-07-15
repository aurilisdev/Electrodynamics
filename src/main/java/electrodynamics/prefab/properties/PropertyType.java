package electrodynamics.prefab.properties;

import electrodynamics.api.gas.GasStack;
import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

public enum PropertyType {
	Byte((prop, buf) -> buf.writeByte((Byte) prop.get()), buf -> buf.readByte(), (prop, tag) -> tag.putByte(prop.getName(), (Byte) prop.get()), (prop, tag) -> tag.getByte(prop.getName()), val -> ((Number) val).byteValue()),
	//
	Boolean((prop, buf) -> buf.writeBoolean((Boolean) prop.get()), buf -> buf.readBoolean(), (prop, tag) -> tag.putBoolean(prop.getName(), (Boolean) prop.get()), (prop, tag) -> tag.getBoolean(prop.getName())),
	//
	Integer((prop, buf) -> buf.writeInt((Integer) prop.get()), buf -> buf.readInt(), (prop, tag) -> tag.putInt(prop.getName(), (Integer) prop.get()), (prop, tag) -> tag.getInt(prop.getName()), val -> ((Number) val).intValue()),
	//
	Float((prop, buf) -> buf.writeFloat((Float) prop.get()), buf -> buf.readFloat(), (prop, tag) -> tag.putFloat(prop.getName(), (Float) prop.get()), (prop, tag) -> tag.getFloat(prop.getName()), val -> ((Number) val).floatValue()),
	//
	Double((prop, buf) -> buf.writeDouble((Double) prop.get()), buf -> buf.readDouble(), (prop, tag) -> tag.putDouble(prop.getName(), (Double) prop.get()), (prop, tag) -> tag.getDouble(prop.getName()), val -> ((Number) val).doubleValue()),
	//
	UUID((prop, buf) -> buf.writeUUID((UUID) prop.get()), buf -> buf.readUUID(), (prop, tag) -> tag.putUUID(prop.getName(), (UUID) prop.get()), (prop, tag) -> tag.getUUID(prop.getName())),
	//
	CompoundTag((prop, buf) -> buf.writeNbt((CompoundTag) prop.get()), buf -> buf.readNbt(), (prop, tag) -> tag.put(prop.getName(), (CompoundTag) prop.get()), (prop, tag) -> tag.getCompound(prop.getName())),
	//
	BlockPos((prop, buf) -> buf.writeBlockPos((BlockPos) prop.get()), buf -> buf.readBlockPos(), (prop, tag) -> tag.put(prop.getName(), NbtUtils.writeBlockPos((BlockPos) prop.get())), (prop, tag) -> NbtUtils.readBlockPos(tag.getCompound(prop.getName()))),
	//
	InventoryItems((thisList, otherList) -> {
		NonNullList<ItemStack> thisCasted = (NonNullList<ItemStack>) thisList;
		NonNullList<ItemStack> otherCasted = (NonNullList<ItemStack>) otherList;
		if (thisCasted.size() != otherCasted.size()) {
			return false;
		}
		ItemStack a, b;
		for (int i = 0; i < thisCasted.size(); i++) {
			a = thisCasted.get(i);
			b = otherCasted.get(i);
			if (!ItemStack.isSameItemSameTags(a, b)) {
				return false;
			}
		}
		return true;
	}, (prop, buf) -> {
		//
		NonNullList<ItemStack> list = (NonNullList<ItemStack>) prop.get();
		int size = list.size();
		buf.writeInt(size);
		CompoundTag tag = new CompoundTag();
		ContainerHelper.saveAllItems(tag, list);
		buf.writeNbt(tag);
	}, buf -> {
		//
		int size = buf.readInt();
		NonNullList<ItemStack> toBeFilled = NonNullList.<ItemStack>withSize(size, ItemStack.EMPTY);
		ContainerHelper.loadAllItems(buf.readNbt(), toBeFilled);
		return toBeFilled;
	}, (prop, tag) -> {
		//
		NonNullList<ItemStack> list = (NonNullList<ItemStack>) prop.get();
		tag.putInt(prop.getName() + "_size", list.size());
		ContainerHelper.saveAllItems(tag, list);
	}, (prop, tag) -> {
		//
		int size = tag.getInt(prop.getName() + "_size");
		if (size == 0 || ((NonNullList<ItemStack>) prop.get()).size() != size) {
			return null; // null is handled in function method caller and signals a bad value to be ignored
		}
		NonNullList<ItemStack> toBeFilled = NonNullList.<ItemStack>withSize(size, ItemStack.EMPTY);
		ContainerHelper.loadAllItems(tag, toBeFilled);
		return toBeFilled;
	}),
	//
	Fluidstack((thisStack, otherStack) -> {
		FluidStack thisCasted = (FluidStack) thisStack;
		FluidStack otherCasted = (FluidStack) otherStack;
		if (thisCasted.getAmount() != otherCasted.getAmount()) {
			return false;
		}
		return thisCasted.getFluid().isSame(otherCasted.getFluid());
	}, (prop, buf) -> buf.writeFluidStack((FluidStack) prop.get()), buf -> buf.readFluidStack(), (prop, tag) -> {
		CompoundTag fluidTag = new CompoundTag();
		((FluidStack) prop.get()).writeToNBT(fluidTag);
		tag.put(prop.getName(), fluidTag);
	}, (prop, tag) -> FluidStack.loadFluidStackFromNBT(tag.getCompound(prop.getName()))),
	//
	BlockPosList((thisList, otherList) -> {
		List<BlockPos> thisCasted = (List<BlockPos>) thisList;
		List<BlockPos> otherCasted = (List<BlockPos>) otherList;
		if (thisCasted.size() != otherCasted.size()) {
			return false;
		}
		BlockPos a, b;
		for (int i = 0; i < thisCasted.size(); i++) {
			a = thisCasted.get(i);
			b = otherCasted.get(i);
			if (!a.equals(b)) {
				return false;
			}
		}
		return true;
	}, (prop, buf) -> {
		List<BlockPos> posList = (List<BlockPos>) prop.get();
		buf.writeInt(posList.size());
		posList.forEach(pos -> buf.writeBlockPos(pos));
	}, buf -> {
		List<BlockPos> list = new ArrayList<>();
		int size = buf.readInt();
		for (int i = 0; i < size; i++) {
			list.add(buf.readBlockPos());
		}
		return list;
	}, (prop, tag) -> {
		List<BlockPos> posList = (List<BlockPos>) prop.get();
		CompoundTag data = new CompoundTag();
		data.putInt("size", posList.size());
		for (int i = 0; i < posList.size(); i++) {
			data.put("pos" + i, NbtUtils.writeBlockPos(posList.get(i)));
		}
		tag.put(prop.getName(), data);
	}, (prop, tag) -> {
		List<BlockPos> list = new ArrayList<>();
		CompoundTag data = tag.getCompound(prop.getName());
		int size = data.getInt("size");
		for (int i = 0; i < size; i++) {
			list.add(NbtUtils.readBlockPos(data.getCompound("pos" + i)));
		}
		return list;
	}),
	//
	Location((prop, buf) -> ((Location) prop.get()).toBuffer(buf), buf -> electrodynamics.prefab.utilities.object.Location.fromBuffer(buf), (prop, tag) -> ((Location) prop.get()).writeToNBT(tag, prop.getName()), (prop, tag) -> electrodynamics.prefab.utilities.object.Location.readFromNBT(tag, prop.getName())),
	//
	Gasstack((prop, buf) -> ((GasStack) prop.get()).writeToBuffer(buf), buf -> GasStack.readFromBuffer(buf), (prop, tag) -> tag.put(prop.getName(), ((GasStack) prop.get()).writeToNbt()), (prop, tag) -> GasStack.readFromNbt(tag.getCompound(prop.getName()))),
	//
	Itemstack((thisStack, otherStack) -> {
		
		return ((ItemStack)thisStack).equals((ItemStack) otherStack, false);
		
	}, (prop, buf) -> buf.writeItem((ItemStack) prop.get()), buf -> buf.readItem(), (prop, tag) -> tag.put(prop.getName(), ((ItemStack) prop.get()).save(new CompoundTag())), (prop, tag) -> ItemStack.of(tag.getCompound(prop.getName()))),
	//
	Block((prop, buf) -> {
		buf.writeItem(new ItemStack(((net.minecraft.world.level.block.Block) prop.get()).asItem()));
	}, buf -> {
		ItemStack stack = buf.readItem();
		if (stack.isEmpty()) {
			return Blocks.AIR;
		}
		return ((BlockItem) stack.getItem()).getBlock();
	}, (prop, tag) -> {
		tag.put(prop.getName(), new ItemStack(((net.minecraft.world.level.block.Block) prop.get()).asItem()).save(new CompoundTag()));
	}, (prop, tag) -> {
		ItemStack stack = ItemStack.of(tag.getCompound(prop.getName()));
		if (stack.isEmpty()) {
			return Blocks.AIR;
		}
		return ((BlockItem) stack.getItem()).getBlock();
	}), 
	//
	Blockstate((prop, buf) -> buf.writeNbt(NbtUtils.writeBlockState((BlockState) prop.get())), buf -> NbtUtils.readBlockState(buf.readNbt()), (prop, tag) -> tag.put(prop.getName(), NbtUtils.writeBlockState((BlockState) prop.get())), (prop, tag) -> NbtUtils.readBlockState(tag.getCompound(prop.getName())));

	@Nonnull
	public final BiPredicate<Object, Object> predicate;

	@Nonnull
	public final BiConsumer<Property<?>, FriendlyByteBuf> writeToBuffer;
	@Nonnull
	public final Function<FriendlyByteBuf, Object> readFromBuffer;

	@Nonnull
	public final BiConsumer<Property<?>, CompoundTag> writeToNbt;
	@Nonnull
	public final BiFunction<Property<?>, CompoundTag, Object> readFromNbt;

	@Nonnull
	public final Function<Object, Object> attemptCast;

	private PropertyType(@Nonnull BiPredicate<Object, Object> predicate, @Nonnull BiConsumer<Property<?>, FriendlyByteBuf> writeToBuffer, @Nonnull Function<FriendlyByteBuf, Object> readFromBuffer, @Nonnull BiConsumer<Property<?>, CompoundTag> writeToNbt, @Nonnull BiFunction<Property<?>, CompoundTag, Object> readFromNbt) {
		this(predicate, writeToBuffer, readFromBuffer, writeToNbt, readFromNbt, val -> val);
	}

	private PropertyType(@Nonnull BiConsumer<Property<?>, FriendlyByteBuf> writeToBuffer, @Nonnull Function<FriendlyByteBuf, Object> readFromBuffer, @Nonnull BiConsumer<Property<?>, CompoundTag> writeToNbt, @Nonnull BiFunction<Property<?>, CompoundTag, Object> readFromNbt) {
		this(writeToBuffer, readFromBuffer, writeToNbt, readFromNbt, val -> val);
	}

	private PropertyType(@Nonnull BiConsumer<Property<?>, FriendlyByteBuf> writeToBuffer, @Nonnull Function<FriendlyByteBuf, Object> readFromBuffer, @Nonnull BiConsumer<Property<?>, CompoundTag> writeToNbt, @Nonnull BiFunction<Property<?>, CompoundTag, Object> readFromNbt, @Nonnull Function<Object, Object> attemptCast) {
		this(Object::equals, writeToBuffer, readFromBuffer, writeToNbt, readFromNbt, attemptCast);
	}

	private PropertyType(@Nonnull BiPredicate<Object, Object> predicate, @Nonnull BiConsumer<Property<?>, FriendlyByteBuf> writeToBuffer, @Nonnull Function<FriendlyByteBuf, Object> readFromBuffer, @Nonnull BiConsumer<Property<?>, CompoundTag> writeToNbt, @Nonnull BiFunction<Property<?>, CompoundTag, Object> readFromNbt, @Nonnull Function<Object, Object> attemptCast) {
		this.predicate = predicate;
		this.writeToBuffer = writeToBuffer;
		this.readFromBuffer = readFromBuffer;
		this.writeToNbt = writeToNbt;
		this.readFromNbt = readFromNbt;
		this.attemptCast = attemptCast;
	}

	// Leave this all for now until we are ABSOLUTELY certain there are no crashing issues

	public void writeOld(Property<?> prop, FriendlyByteBuf buf) {
		
		//new ItemStack(Items.air).qua
		
		Object val = prop.get();
		switch (this) {
		case Boolean:
			buf.writeBoolean((Boolean) val);
			break;
		case Byte:
			buf.writeByte((Byte) val);
			break;
		case CompoundTag:
			buf.writeNbt((CompoundTag) val);
			break;
		case Double:
			buf.writeDouble((Double) val);
			break;
		case Float:
			buf.writeFloat((Float) val);
			break;
		case Integer:
			buf.writeInt((Integer) val);
			break;
		case UUID:
			buf.writeUUID((UUID) val);
			break;
		case BlockPos:
			buf.writeBlockPos((BlockPos) val);
			break;
		case InventoryItems:
			NonNullList<ItemStack> list = (NonNullList<ItemStack>) prop.get();
			int size = list.size();
			buf.writeInt(size);
			CompoundTag tag = new CompoundTag();
			ContainerHelper.saveAllItems(tag, list);
			buf.writeNbt(tag);
			break;
		case Fluidstack:
			buf.writeFluidStack((FluidStack) prop.get());
			break;
		case BlockPosList:
			List<BlockPos> posList = (List<BlockPos>) prop.get();
			buf.writeInt(posList.size());
			posList.forEach(pos -> buf.writeBlockPos(pos));
			break;
		case Location:
			Location loc = (Location) val;
			loc.toBuffer(buf);
			break;
		case Gasstack:
			GasStack stack = (GasStack) prop.get();
			stack.writeToBuffer(buf);
			break;
		default:
			break;
		}

	}

	public Object readOld(FriendlyByteBuf buf) {
		switch (this) {
		case Boolean:
			return buf.readBoolean();
		case Byte:
			return buf.readByte();
		case CompoundTag:
			return buf.readNbt();
		case Double:
			return buf.readDouble();
		case Float:
			return buf.readFloat();
		case Integer:
			return buf.readInt();
		case UUID:
			return buf.readUUID();
		case BlockPos:
			return buf.readBlockPos();
		case InventoryItems:
			int size = buf.readInt();
			NonNullList<ItemStack> toBeFilled = NonNullList.<ItemStack>withSize(size, ItemStack.EMPTY);
			ContainerHelper.loadAllItems(buf.readNbt(), toBeFilled);
			return toBeFilled;
		case Fluidstack:
			return buf.readFluidStack();
		case BlockPosList:
			List<BlockPos> list = new ArrayList<>();
			size = buf.readInt();
			for (int i = 0; i < size; i++) {
				list.add(buf.readBlockPos());
			}
			return list;
		case Location:
			return electrodynamics.prefab.utilities.object.Location.fromBuffer(buf);
		case Gasstack:
			return GasStack.readFromBuffer(buf);
		default:
			break;
		}
		return null;
	}

	public void saveOld(Property<?> prop, CompoundTag tag) {
		Object val = prop.get();
		switch (this) {
		case Boolean:
			tag.putBoolean(prop.getName(), (Boolean) val);
			break;
		case Byte:
			tag.putByte(prop.getName(), (Byte) val);
			break;
		case CompoundTag:
			tag.put(prop.getName(), (CompoundTag) val);
			break;
		case Double:
			tag.putDouble(prop.getName(), (Double) val);
			break;
		case Float:
			tag.putFloat(prop.getName(), (Float) val);
			break;
		case Integer:
			tag.putInt(prop.getName(), (Integer) val);
			break;
		case UUID:
			tag.putUUID(prop.getName(), (UUID) val);
			break;
		case BlockPos:
			if (val instanceof BlockPos pos) {
				tag.put(prop.getName(), NbtUtils.writeBlockPos(pos));
			}
			break;
		case InventoryItems:
			NonNullList<ItemStack> list = (NonNullList<ItemStack>) prop.get();
			tag.putInt(prop.getName() + "_size", list.size());
			ContainerHelper.saveAllItems(tag, list);
			break;
		case Fluidstack:
			CompoundTag fluidTag = new CompoundTag();
			((FluidStack) prop.get()).writeToNBT(fluidTag);
			tag.put(prop.getName(), fluidTag);
			break;
		case BlockPosList:
			List<BlockPos> posList = (List<BlockPos>) prop.get();
			CompoundTag data = new CompoundTag();
			data.putInt("size", posList.size());
			for (int i = 0; i < posList.size(); i++) {
				data.put("pos" + i, NbtUtils.writeBlockPos(posList.get(i)));
			}
			tag.put(prop.getName(), data);
			break;
		case Location:
			((Location) val).writeToNBT(tag, prop.getName());
			break;
		case Gasstack:
			tag.put(prop.getName(), ((GasStack) val).writeToNbt());
			break;
		default:
			break;
		}
	}

	public void loadOld(Property<?> prop, CompoundTag tag) {
		Object val = null;
		switch (this) {
		case Boolean:
			val = tag.getBoolean(prop.getName());
			break;
		case Byte:
			val = tag.getByte(prop.getName());
			break;
		case CompoundTag:
			val = tag.get(prop.getName());
			break;
		case Double:
			val = tag.getDouble(prop.getName());
			break;
		case Float:
			val = tag.getFloat(prop.getName());
			break;
		case Integer:
			val = tag.getInt(prop.getName());
			break;
		case UUID:
			val = tag.getUUID(prop.getName());
			break;
		case BlockPos:
			if (tag.contains(prop.getName())) {
				val = NbtUtils.readBlockPos(tag.getCompound(prop.getName()));
			}
			break;
		case InventoryItems:
			int size = tag.getInt(prop.getName() + "_size");
			if (size == 0) {
				NonNullList<ItemStack> propVal = (NonNullList<ItemStack>) prop.get();
				size = propVal != null ? propVal.size() : 0;
			}
			NonNullList<ItemStack> toBeFilled = NonNullList.<ItemStack>withSize(size, ItemStack.EMPTY);
			ContainerHelper.loadAllItems(tag, toBeFilled);
			val = toBeFilled;
			break;
		case Fluidstack:
			val = FluidStack.loadFluidStackFromNBT(tag.getCompound(prop.getName()));
			break;
		case BlockPosList:
			List<BlockPos> list = new ArrayList<>();
			CompoundTag data = tag.getCompound(prop.getName());
			size = data.getInt("size");
			for (int i = 0; i < size; i++) {
				list.add(NbtUtils.readBlockPos(data.getCompound("pos" + i)));
			}
			val = list;
			break;
		case Location:
			val = electrodynamics.prefab.utilities.object.Location.readFromNBT(tag, prop.getName());
			break;
		case Gasstack:
			val = GasStack.readFromNbt(tag.getCompound(prop.getName()));
			break;
		default:
			break;
		}
		tag.remove(prop.getName());
		prop.load(val);
	}

	Object attemptCastOld(Object updated) {
		// This is done so we remove some issues between different number types.
		switch (this) {
		case Byte:
			if (updated instanceof Number) {
				return (byte) ((Number) updated).byteValue();
			}
			break;
		case Double:
			if (updated instanceof Number) {
				return (double) ((Number) updated).doubleValue();
			}
			break;
		case Float:
			if (updated instanceof Number) {
				return (float) ((Number) updated).floatValue();
			}
			break;
		case Integer:
			if (updated instanceof Number) {
				return (int) ((Number) updated).intValue();
			}
			break;
		default:
			break;
		}
		return updated;

	}

}
