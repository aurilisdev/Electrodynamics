package physica.core.common.block;

import java.util.Random;

import javax.annotation.Nonnull;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import physica.core.Physica;
import physica.core.common.block.state.BlockStateMachine;
import physica.core.common.block.state.BlockStateMachine.EnumMachine;
import universalelectricity.prefab.tile.electric.TileElectricMachine;

public class BlockMachine extends BlockInventory {
	public BlockMachine() {
		super("machine", Material.IRON);
		setHardness(3.5F);
		setResistance(16);
		setDefaultState(blockState.getBaseState().withProperty(BlockStateMachine.TYPE, EnumMachine.ELECTRIC_FURNACE));
	}

	@Override
	public void registerItemModel(Item item) {
		for (EnumMachine type : EnumMachine.values()) {
			Physica.proxy.registerBlockItemRenderer(item, type.ordinal(), name, "facing=north,type=" + type.getName());
		}
	}

	@Override
	@Nonnull
	@SideOnly(Side.CLIENT)
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return state.getValue(BlockStateMachine.TYPE).getRenderType();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
		for (EnumMachine type : EnumMachine.values()) {
			list.add(new ItemStack(this, 1, type.ordinal()));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random random) {
		EnumMachine type = state.getValue(BlockStateMachine.TYPE);
		TileEntity tile = world.getTileEntity(pos);

		if (tile instanceof TileElectricMachine) {
			TileElectricMachine TileElectricMachine = (TileElectricMachine) tile;

			if (type.hasParticle() && TileElectricMachine.getOperatingTicks() > 0) {
				float xRandom = pos.getX() + 0.5F;
				float yRandom = pos.getY() + 0.2F + random.nextFloat() * 6.0F / 16;
				float zRandom = pos.getZ() + 0.5F;
				float iRandom = 0.52F;
				float jRandom = random.nextFloat() * 0.6F - 0.3F;
				double xSpeed = 0;
				double ySpeed = type.getParticleSpeed();
				double zSpeed = 0;
				switch (TileElectricMachine.getFacing()) {
				case NORTH:
					world.spawnParticle(type.getParticleType(), xRandom + jRandom, yRandom, zRandom - iRandom, xSpeed,
							ySpeed, zSpeed);
					break;
				case SOUTH:
					world.spawnParticle(type.getParticleType(), xRandom + jRandom, yRandom, zRandom + iRandom, xSpeed,
							ySpeed, zSpeed);
					break;
				case WEST:
					world.spawnParticle(type.getParticleType(), xRandom - iRandom, yRandom, zRandom + jRandom, xSpeed,
							ySpeed, zSpeed);
					break;
				case EAST:
					world.spawnParticle(type.getParticleType(), xRandom + iRandom, yRandom, zRandom + jRandom, xSpeed,
							ySpeed, zSpeed);
					break;
				case DOWN:
					break;
				case UP:
					break;
				default:
					break;
				}
			}
		}
	}

	@Override
	@Nonnull
	public BlockStateContainer createBlockState() {
		return new BlockStateMachine(this);
	}

	@Override
	@Nonnull
	public IBlockState getStateFromMeta(int metadata) {
		return getDefaultState().withProperty(BlockStateMachine.TYPE, EnumMachine.values()[metadata]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(BlockStateMachine.TYPE).ordinal();
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity,
			ItemStack itemStack) {
		world.setBlockState(pos,
				state.withProperty(BlockStateMachine.TYPE, EnumMachine.values()[itemStack.getItemDamage()]));
		super.onBlockPlacedBy(world, pos, state, entity, itemStack);
	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!player.isSneaking()) {
			player.openGui(Physica.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
			return true;
		}
		return false;
	}

	@Override
	public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
		EnumMachine type = state.getValue(BlockStateMachine.TYPE);

		return type.getTileAsInstance();
	}
}