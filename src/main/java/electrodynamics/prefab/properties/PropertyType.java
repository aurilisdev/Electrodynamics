package electrodynamics.prefab.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

import javax.annotation.Nonnull;

import electrodynamics.api.References;
import electrodynamics.api.gas.GasStack;
import electrodynamics.prefab.utilities.object.Location;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;

public enum PropertyType implements IPropertyType {

	Byte((value, buf) -> buf.writeByte((Byte) value), FriendlyByteBuf::readByte, (prop, tag) -> tag.putByte(prop.getName(), (Byte) prop.get()), (prop, tag) -> tag.getByte(prop.getName()), val -> ((Number) val).byteValue()),
	//
	Boolean((value, buf) -> buf.writeBoolean((Boolean) value), FriendlyByteBuf::readBoolean, (prop, tag) -> tag.putBoolean(prop.getName(), (Boolean) prop.get()), (prop, tag) -> tag.getBoolean(prop.getName())),
	//
	Integer((value, buf) -> buf.writeInt((Integer) value), FriendlyByteBuf::readInt, (prop, tag) -> tag.putInt(prop.getName(), (Integer) prop.get()), (prop, tag) -> tag.getInt(prop.getName()), val -> ((Number) val).intValue()),
	//
	Long((value, buf) -> buf.writeLong((Long) value), FriendlyByteBuf::readLong, (prop, tag) -> tag.putLong(prop.getName(), (Long) prop.get()), (prop, tag) -> tag.getLong(prop.getName()), val -> ((Number) val).longValue()),
	//
	Float((value, buf) -> buf.writeFloat((Float) value), FriendlyByteBuf::readFloat, (prop, tag) -> tag.putFloat(prop.getName(), (Float) prop.get()), (prop, tag) -> tag.getFloat(prop.getName()), val -> ((Number) val).floatValue()),
	//
	Double((value, buf) -> buf.writeDouble((Double) value), FriendlyByteBuf::readDouble, (prop, tag) -> tag.putDouble(prop.getName(), (Double) prop.get()), (prop, tag) -> tag.getDouble(prop.getName()), val -> ((Number) val).doubleValue()),
	//
	UUID((value, buf) -> buf.writeUUID((UUID) value), FriendlyByteBuf::readUUID, (prop, tag) -> tag.putUUID(prop.getName(), (UUID) prop.get()), (prop, tag) -> tag.getUUID(prop.getName())),
	//
	CompoundTag((value, buf) -> buf.writeNbt((CompoundTag) value), FriendlyByteBuf::readNbt, (prop, tag) -> tag.put(prop.getName(), (CompoundTag) prop.get()), (prop, tag) -> tag.getCompound(prop.getName())),
	//
	BlockPos((value, buf) -> buf.writeBlockPos((BlockPos) value), FriendlyByteBuf::readBlockPos, (prop, tag) -> tag.put(prop.getName(), NbtUtils.writeBlockPos((BlockPos) prop.get())), (prop, tag) -> NbtUtils.readBlockPos(tag.getCompound(prop.getName()))),
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
	}, (value, buf) -> {
		//
		NonNullList<ItemStack> list = (NonNullList<ItemStack>) value;
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
	}, (value, buf) -> buf.writeFluidStack((FluidStack) value), FriendlyByteBuf::readFluidStack, (prop, tag) -> {
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
	}, (value, buf) -> {
		List<BlockPos> posList = (List<BlockPos>) value;
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
	Location((value, buf) -> ((Location) value).toBuffer(buf), electrodynamics.prefab.utilities.object.Location::fromBuffer, (prop, tag) -> ((Location) prop.get()).writeToNBT(tag, prop.getName()), (prop, tag) -> electrodynamics.prefab.utilities.object.Location.readFromNBT(tag, prop.getName())),
	//
	Gasstack((value, buf) -> ((GasStack) value).writeToBuffer(buf), GasStack::readFromBuffer, (prop, tag) -> tag.put(prop.getName(), ((GasStack) prop.get()).writeToNbt()), (prop, tag) -> GasStack.readFromNbt(tag.getCompound(prop.getName()))),
	//
	Itemstack((thisStack, otherStack) -> ((ItemStack) thisStack).equals((ItemStack) otherStack, false), (value, buf) -> buf.writeItem((ItemStack) value), FriendlyByteBuf::readItem, (prop, tag) -> tag.put(prop.getName(), ((ItemStack) prop.get()).save(new CompoundTag())), (prop, tag) -> ItemStack.of(tag.getCompound(prop.getName()))),
	//
	Block((value, buf) -> {
		buf.writeItem(new ItemStack(((net.minecraft.world.level.block.Block) value).asItem()));
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
	Blockstate((value, buf) -> buf.writeNbt(NbtUtils.writeBlockState((BlockState) value)), buf -> NbtUtils.readBlockState(buf.readNbt()), (prop, tag) -> tag.put(prop.getName(), NbtUtils.writeBlockState((BlockState) prop.get())), (prop, tag) -> NbtUtils.readBlockState(tag.getCompound(prop.getName()))),
	//
	Transferpack((value, buf) -> ((TransferPack) value).writeToBuffer(buf), buf -> TransferPack.readFromBuffer(buf), (prop, tag) -> tag.put(prop.getName(), ((TransferPack) prop.get()).writeToTag()), (prop, tag) -> TransferPack.readFromTag(tag.getCompound(prop.getName()))),
	//
	;

	private final ResourceLocation id;

	@Nonnull
	private final BiPredicate<Object, Object> predicate;

	@Nonnull
	private final BiConsumer<Object, FriendlyByteBuf> writeToBuffer;
	@Nonnull
	private final Function<FriendlyByteBuf, Object> readFromBuffer;

	@Nonnull
	private final BiConsumer<Property<?>, CompoundTag> writeToNbt;
	@Nonnull
	private final BiFunction<Property<?>, CompoundTag, Object> readFromNbt;

	@Nonnull
	private final Function<Object, Object> attemptCast;

	private PropertyType(@Nonnull BiPredicate<Object, Object> predicate, @Nonnull BiConsumer<Object, FriendlyByteBuf> writeToBuffer, @Nonnull Function<FriendlyByteBuf, Object> readFromBuffer, @Nonnull BiConsumer<Property<?>, CompoundTag> writeToNbt, @Nonnull BiFunction<Property<?>, CompoundTag, Object> readFromNbt) {
		this(predicate, writeToBuffer, readFromBuffer, writeToNbt, readFromNbt, val -> val);
	}

	private PropertyType(@Nonnull BiConsumer<Object, FriendlyByteBuf> writeToBuffer, @Nonnull Function<FriendlyByteBuf, Object> readFromBuffer, @Nonnull BiConsumer<Property<?>, CompoundTag> writeToNbt, @Nonnull BiFunction<Property<?>, CompoundTag, Object> readFromNbt) {
		this(writeToBuffer, readFromBuffer, writeToNbt, readFromNbt, val -> val);
	}

	private PropertyType(@Nonnull BiConsumer<Object, FriendlyByteBuf> writeToBuffer, @Nonnull Function<FriendlyByteBuf, Object> readFromBuffer, @Nonnull BiConsumer<Property<?>, CompoundTag> writeToNbt, @Nonnull BiFunction<Property<?>, CompoundTag, Object> readFromNbt, @Nonnull Function<Object, Object> attemptCast) {
		this(Object::equals, writeToBuffer, readFromBuffer, writeToNbt, readFromNbt, attemptCast);
	}

	private PropertyType(@Nonnull BiPredicate<Object, Object> predicate, @Nonnull BiConsumer<Object, FriendlyByteBuf> writeToBuffer, @Nonnull Function<FriendlyByteBuf, Object> readFromBuffer, @Nonnull BiConsumer<Property<?>, CompoundTag> writeToNbt, @Nonnull BiFunction<Property<?>, CompoundTag, Object> readFromNbt, @Nonnull Function<Object, Object> attemptCast) {
		id = new ResourceLocation(References.ID, name().toLowerCase(Locale.ROOT));
		this.predicate = predicate;
		this.writeToBuffer = writeToBuffer;
		this.readFromBuffer = readFromBuffer;
		this.writeToNbt = writeToNbt;
		this.readFromNbt = readFromNbt;
		this.attemptCast = attemptCast;
	}

	@Override
	public boolean hasChanged(Object currentValue, Object newValue) {
		return predicate.test(currentValue, newValue);
	}

	@Override
	public void writeToBuffer(Object value, FriendlyByteBuf buf) {
		writeToBuffer.accept(value, buf);
	}

	@Override
	public Object readFromBuffer(FriendlyByteBuf buf) {
		return readFromBuffer.apply(buf);
	}

	@Override
	public void writeToTag(Property<?> prop, net.minecraft.nbt.CompoundTag tag) {
		writeToNbt.accept(prop, tag);
	}

	@Override
	public Object readFromTag(Property<?> prop, net.minecraft.nbt.CompoundTag tag) {
		return readFromNbt.apply(prop, tag);
	}

	@Override
	public Object attemptCast(Object value) {
		return attemptCast.apply(value);
	}

	@Override
	public ResourceLocation getId() {
		return id;
	}

}
