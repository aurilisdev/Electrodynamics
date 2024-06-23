package electrodynamics.prefab.tile.types;

import org.jetbrains.annotations.NotNull;

import electrodynamics.client.modelbakers.modelproperties.ModelPropertyConnections;
import electrodynamics.common.block.connect.util.EnumConnectType;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;

public abstract class GenericConnectTile extends GenericTile {

	// DUNSWE

	public static final int DOWN_MASK = 0b11110000000000000000000000000000;
	public static final int UP_MASK = 0b00001111000000000000000000000000;
	public static final int NORTH_MASK = 0b00000000111100000000000000000000;
	public static final int SOUTH_MASK = 0b00000000000011110000000000000000;
	public static final int WEST_MASK = 0b00000000000000001111000000000000;
	public static final int EAST_MASK = 0b00000000000000000000111100000000;

	public final Property<Integer> connections = property(new Property<>(PropertyType.Integer, "connections", 0).onChange((property, block) -> {
		if(!level.isClientSide) {
			return;
		}
		requestModelDataUpdate();
	}));

	public final Property<BlockState> camoflaugedBlock = property(new Property<>(PropertyType.Blockstate, "camoflaugedblock", Blocks.AIR.defaultBlockState())).onChange((property, block) -> {
		level.getChunkSource().getLightEngine().checkBlock(worldPosition);
	});

	public final Property<BlockState> scaffoldBlock = property(new Property<>(PropertyType.Blockstate, "scaffoldblock", Blocks.AIR.defaultBlockState())).onChange((property, block) -> {
		level.getChunkSource().getLightEngine().checkBlock(worldPosition);
	});

	public GenericConnectTile(BlockEntityType<?> tile, BlockPos pos, BlockState state) {
		super(tile, pos, state);
		addComponent(new ComponentPacketHandler(this));
	}

	public void setCamoBlock(BlockState block) {
		camoflaugedBlock.set(block);
		setChanged();
	}

	public BlockState getCamoBlock() {
		return camoflaugedBlock.get();
	}

	public boolean isCamoAir() {
		return getCamoBlock().isAir();
	}

	public void setScaffoldBlock(BlockState scaffold) {
		scaffoldBlock.set(scaffold);
		setChanged();
	}

	public BlockState getScaffoldBlock() {
		return scaffoldBlock.get();
	}

	public boolean isScaffoldAir() {
		return getScaffoldBlock().isAir();
	}

	@Override
	public void onPlace(BlockState oldState, boolean isMoving) {
		super.onPlace(oldState, isMoving);
		if (!level.isClientSide) {
			this.<ComponentPacketHandler>getComponent(IComponentType.PacketHandler).sendProperties();
		}
	}

	public EnumConnectType readConnection(Direction dir) {

		int connectionData = connections.get();
		int extracted = 0;
		switch (dir) {
		case DOWN:
			extracted = connectionData & DOWN_MASK;
			break;
		case UP:
			extracted = connectionData & UP_MASK;
			break;
		case NORTH:
			extracted = connectionData & NORTH_MASK;
			break;
		case SOUTH:
			extracted = connectionData & SOUTH_MASK;
			break;
		case WEST:
			extracted = connectionData & WEST_MASK;
			break;
		case EAST:
			extracted = connectionData & EAST_MASK;
			break;
		default:
			break;
		}
		
		return EnumConnectType.values()[(extracted << dir.ordinal() * 4)];

	}
	
	public void writeConnection(Direction dir, EnumConnectType connection) {
		
		int connectionData = connections.get();

		switch (dir) {
		case DOWN:
			connectionData = connectionData & ~DOWN_MASK;
			break;
		case UP:
			connectionData = connectionData & ~UP_MASK;
			break;
		case NORTH:
			connectionData = connectionData & ~NORTH_MASK;
			break;
		case SOUTH:
			connectionData = connectionData & ~SOUTH_MASK;
			break;
		case WEST:
			connectionData = connectionData & ~WEST_MASK;
			break;
		case EAST:
			connectionData = connectionData & ~EAST_MASK;
			break;
		default:
			break;
		}
		connectionData = connectionData | (connection.ordinal() >> dir.ordinal() * 4);
		
		connections.set(connectionData);
	}
	
	public EnumConnectType[] readConnections() {
		EnumConnectType[] connections = new EnumConnectType[6];
		for(Direction dir : Direction.values()) {
			connections[dir.ordinal()] = readConnection(dir);
		}
		return connections;
	}
	
	@Override
	public @NotNull ModelData getModelData() {
		return ModelData.builder().with(ModelPropertyConnections.INSTANCE, readConnections()).build();
	}

}
