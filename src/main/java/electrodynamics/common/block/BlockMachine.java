package electrodynamics.common.block;

import java.util.Arrays;
import java.util.List;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.multiblock.Subnode;
import electrodynamics.api.multiblock.parent.IMultiblockParentBlock;
import electrodynamics.api.multiblock.parent.IMultiblockParentTile;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.prefab.block.GenericMachineBlock;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

public class BlockMachine extends GenericMachineBlock implements IMultiblockParentBlock {

	public static final BooleanProperty ON = BlockStateProperties.LIT;
	public static final IntegerProperty BATTERY_CHARGE = BlockStateProperties.AGE_7;

	public static final VoxelShape[] STANDARD_CUBE = new VoxelShape[] { VoxelShapes.block(), VoxelShapes.block(), VoxelShapes.block(), VoxelShapes.block(), VoxelShapes.block(), VoxelShapes.block() };

	public static final Subnode[] advancedsolarpanelsubnodes = new Subnode[9];
	public static final Subnode[] windmillsubnodes = { new Subnode(new BlockPos(0, 1, 0), new VoxelShape[] { VoxelShapes.block(), VoxelShapes.block(), VoxelShapes.or(Block.box(5, 0, 5, 11, 16, 11), Block.box(5, 10, 3, 11, 16, 13)), VoxelShapes.or(Block.box(5, 0, 5, 11, 16, 11), Block.box(5, 10, 3, 11, 16, 13)), VoxelShapes.or(Block.box(5, 0, 5, 11, 16, 11), Block.box(3, 10, 5, 13, 16, 11)),
			VoxelShapes.or(Block.box(5, 0, 5, 11, 16, 11), Block.box(3, 10, 5, 13, 16, 11)) }) };
	static {

		int counter = 0;

		int radius = 1;

		for (int i = -radius; i <= radius; i++) {

			for (int j = -radius; j <= radius; j++) {
				if (i == 0 && j == 0) {
					advancedsolarpanelsubnodes[counter] = new Subnode(new BlockPos(i, 1, j), VoxelShapes.or(Block.box(6, 0, 6, 10, 16, 10), Block.box(5, 13, 5, 11, 16, 11), Block.box(0, 14, 0, 16, 16, 16)));
				} else {
					advancedsolarpanelsubnodes[counter] = new Subnode(new BlockPos(i, 1, j), Block.box(0, 14, 0, 16, 16, 16));
				}

				counter++;
			}
		}

	}

	public final SubtypeMachine machine;

	public BlockMachine(SubtypeMachine machine) {
		super(reader -> machine.createTileEntity());
		this.machine = machine;
		if (machine.litBrightness > 0) {
			registerDefaultState(stateDefinition.any().setValue(ON, false));
		}
	}

	@Override
	public void fillItemCategory(ItemGroup pTab, NonNullList<ItemStack> pItems) {
		if (!machine.showInItemGroup) {
			return;
		}
		super.fillItemCategory(pTab, pItems);
	}

	@Override
	public boolean propagatesSkylightDown(BlockState pState, IBlockReader pLevel, BlockPos pPos) {
		if (machine.propogateLightDown) {
			return true;
		}
		return super.propagatesSkylightDown(pState, pLevel, pPos);
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {

		if (machine == SubtypeMachine.advancedsolarpanel) {
			return isValidMultiblockPlacement(state, worldIn, pos, advancedsolarpanelsubnodes);
		}
		if (machine == SubtypeMachine.windmill) {
			return isValidMultiblockPlacement(state, worldIn, pos, windmillsubnodes);
		}
		return super.canSurvive(state, worldIn, pos);

	}

	@Override
	public BlockRenderType getRenderShape(BlockState state) {
		return machine.getRenderType();
	}

	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {

		if (machine.litBrightness > 0 && state.hasProperty(ON) && state.getValue(ON)) {
			return machine.litBrightness;
		}

		return super.getLightValue(state, world, pos);
	}

	@Override
	public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		super.setPlacedBy(worldIn, pos, state, placer, stack);
		TileEntity tile = worldIn.getBlockEntity(pos);
		if (hasMultiBlock() && tile instanceof IMultiblockParentTile) {
			IMultiblockParentTile multi = (IMultiblockParentTile) tile;
			multi.onNodePlaced(worldIn, pos, state, placer, stack);
		}
	}

	@Override
	public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		TileEntity tile = worldIn.getBlockEntity(pos);
		if (!(state.getBlock() == newState.getBlock() && state.getValue(FACING) != newState.getValue(FACING))) {

			if (tile instanceof IMultiblockParentTile) {
				IMultiblockParentTile multi = (IMultiblockParentTile) tile;
				multi.onNodeReplaced(worldIn, pos, true);
			}
		}
		if (newState.isAir(worldIn, pos)) {
			if (tile instanceof IMultiblockParentTile) {
				IMultiblockParentTile multi = (IMultiblockParentTile) tile;
				multi.onNodeReplaced(worldIn, pos, false);
			}
		}

		boolean update = SubtypeMachine.shouldBreakOnReplaced(state, newState);

		if (update) {
			super.onRemove(state, worldIn, pos, newState, isMoving);
		} else {
			worldIn.setBlocksDirty(pos, state, newState);
		}

	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		TileEntity tile = builder.getOptionalParameter(LootParameters.BLOCK_ENTITY);
		if (tile instanceof GenericTile) {
			GenericTile machine = (GenericTile) tile;

			Item item = this.asItem();

			switch (this.machine) {

			case coalgeneratorrunning:
				item = getMachine(SubtypeMachine.coalgenerator);
				break;
			case electricfurnacerunning:
				item = getMachine(SubtypeMachine.electricfurnace);
				break;
			case electricfurnacedoublerunning:
				item = getMachine(SubtypeMachine.electricfurnacedouble);
				break;
			case electricfurnacetriplerunning:
				item = getMachine(SubtypeMachine.electricfurnacetriple);
				break;
			case oxidationfurnacerunning:
				item = getMachine(SubtypeMachine.oxidationfurnace);
				break;
			case energizedalloyerrunning:
				item = getMachine(SubtypeMachine.energizedalloyer);
				break;
			case reinforcedalloyerrunning:
				item = getMachine(SubtypeMachine.reinforcedalloyer);
				break;
			default:
				break;

			}

			ItemStack stack = new ItemStack(item);
			ComponentInventory inv = machine.getComponent(IComponentType.Inventory);
			if (inv != null) {
				InventoryHelper.dropContents(machine.getLevel(), machine.getBlockPos(), inv.getItems());
				tile.getCapability(ElectrodynamicsCapabilities.ELECTRODYNAMIC).ifPresent(el -> {
					double joules = el.getJoulesStored();
					if (joules > 0) {
						stack.getOrCreateTag().putDouble("joules", joules);
					}
				});
			}
			return Arrays.asList(stack);

		}
		return super.getDrops(state, builder);
	}

	private static Item getMachine(SubtypeMachine inputMachine) {
		return ElectrodynamicsItems.getItem(inputMachine);
	}

	@Override
	public boolean hasMultiBlock() {
		return machine == SubtypeMachine.advancedsolarpanel || machine == SubtypeMachine.windmill;
	}

	@Override
	public ItemStack getCloneItemStack(IBlockReader level, BlockPos pPos, BlockState pState) {
		Item item = this.asItem();

		switch (this.machine) {

		case coalgeneratorrunning:
			item = getMachine(SubtypeMachine.coalgenerator);
			break;
		case electricfurnacerunning:
			item = getMachine(SubtypeMachine.electricfurnace);
			break;
		case electricfurnacedoublerunning:
			item = getMachine(SubtypeMachine.electricfurnacedouble);
			break;
		case electricfurnacetriplerunning:
			item = getMachine(SubtypeMachine.electricfurnacetriple);
			break;
		case oxidationfurnacerunning:
			item = getMachine(SubtypeMachine.oxidationfurnace);
			break;
		case energizedalloyerrunning:
			item = getMachine(SubtypeMachine.energizedalloyer);
			break;
		case reinforcedalloyerrunning:
			item = getMachine(SubtypeMachine.reinforcedalloyer);
			break;
		default:
			break;

		}

		ItemStack stack = new ItemStack(item);
		TileEntity tile = level.getBlockEntity(pPos);
		if (tile != null) {
			BlockEntityUtils.saveToItem(tile, stack);
		}
		return stack;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return super.getStateForPlacement(context).setValue(ON, false);
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(ON);
	}
}