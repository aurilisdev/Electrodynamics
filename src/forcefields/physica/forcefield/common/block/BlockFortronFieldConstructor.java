package physica.forcefield.common.block;

import java.awt.Color;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import physica.forcefield.ForcefieldReferences;
import physica.forcefield.PhysicaForcefields;
import physica.forcefield.common.ForcefieldItemRegister;
import physica.forcefield.common.ForcefieldTabRegister;
import physica.forcefield.common.tile.TileFortronFieldConstructor;
import physica.library.block.BlockBaseContainerModelled;
import physica.nuclear.common.NuclearBlockRegister;
import physica.nuclear.common.block.BlockElectromagnet;

public class BlockFortronFieldConstructor extends BlockBaseContainerModelled {

	public BlockFortronFieldConstructor() {
		super(Material.iron);
		setHardness(10);
		setResistance(500);
		setHarvestLevel("pickaxe", 2);
		setCreativeTab(ForcefieldTabRegister.forcefieldTab);
		setBlockName(ForcefieldReferences.PREFIX + "fortronFieldConstructor");
		setBlockBounds(0, 0, 0, 1, 0.6f, 1);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileFortronFieldConstructor();
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileFortronFieldConstructor)
		{
			return ((TileFortronFieldConstructor) tile).getProjectorMode() == -1 ? 0 : 6;
		}
		return super.getLightValue(world, x, y, z);
	}

	@Override
	public void registerRecipes()
	{
		addRecipe(this, "PEP", "EDE", "PEP", 'E', "circuitElite", 'P', new ItemStack(NuclearBlockRegister.blockElectromagnet, 1, BlockElectromagnet.EnumElectromagnet.CONTAINMENT_NORMAL.ordinal()), 'D', Blocks.diamond_block);
	}

	@Override
	public boolean canWrench(World worldObj, int x, int y, int z)
	{
		TileEntity tile = worldObj.getTileEntity(x, y, z);
		if (tile instanceof TileFortronFieldConstructor)
		{
			TileFortronFieldConstructor constructor = (TileFortronFieldConstructor) tile;
			if (constructor.isCalculating)
			{
				return false;
			}
		}
		return super.canWrench(worldObj, x, y, z);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xHit, float yHit, float zHit)
	{
		if (!world.isRemote)
		{
			ItemStack itemStack = player.getCurrentEquippedItem();
			if (itemStack == null)
			{
				return super.onBlockActivated(world, x, y, z, player, side, xHit, yHit, zHit);
			}

			String defaultDisplayName = itemStack.getItem().getItemStackDisplayName(itemStack).toLowerCase();
			if (itemStack.getItem() == Items.dye || defaultDisplayName.contains("dye"))
			{
				TileEntity tile = world.getTileEntity(x, y, z);
				if (tile instanceof TileFortronFieldConstructor)
				{
					TileFortronFieldConstructor constructor = (TileFortronFieldConstructor) tile;
					if (constructor.getModuleCount(ForcefieldItemRegister.moduleMap.get("moduleUpgradeColorChange"), TileFortronFieldConstructor.SLOT_UPGRADES[0],
							TileFortronFieldConstructor.SLOT_UPGRADES[TileFortronFieldConstructor.SLOT_UPGRADES.length - 1]) > 0)
					{
						int color = constructor.fieldColorMultiplier();
						int saveColor = color;
						String colorName = null;

						boolean vanilla = itemStack.getItem() == Items.dye;
						int damage = itemStack.getItemDamage();
						if (vanilla && damage == 0 || defaultDisplayName.contains("black"))
						{
							color = Color.BLACK.brighter().getRGB();
							colorName = "black";
						} else if (vanilla && damage == 1 || defaultDisplayName.contains("red"))
						{
							color = Color.RED.getRGB();
							colorName = "red";
						} else if (vanilla && damage == 2 || defaultDisplayName.contains("green"))
						{
							color = Color.GREEN.darker().darker().getRGB();
							colorName = "green";
						} else if (vanilla && damage == 3 || defaultDisplayName.contains("brown"))
						{
							color = new Color(255, 255, 0).getRGB();
							colorName = "brown";
						} else if (vanilla && damage == 4 || defaultDisplayName.contains("blue") && !defaultDisplayName.contains("light blue"))
						{
							color = Color.BLUE.brighter().brighter().brighter().brighter().getRGB();
							colorName = "blue";
						} else if (vanilla && damage == 5 || defaultDisplayName.contains("purple"))
						{
							color = Color.MAGENTA.darker().darker().getRGB();
							colorName = "purple";
						} else if (vanilla && damage == 6 || defaultDisplayName.contains("cyan"))
						{
							color = Color.CYAN.darker().getRGB();
							colorName = "cyan";
						} else if (vanilla && damage == 7 || defaultDisplayName.contains("light gray"))
						{
							color = Color.LIGHT_GRAY.getRGB();
							colorName = "light gray";
						} else if (vanilla && damage == 8 || defaultDisplayName.contains("gray"))
						{
							color = Color.GRAY.getRGB();
							colorName = "gray";
						} else if (vanilla && damage == 9 || defaultDisplayName.contains("pink"))
						{
							color = Color.PINK.getRGB();
							colorName = "pink";
						} else if (vanilla && damage == 10 || defaultDisplayName.contains("lime"))
						{
							color = Color.GREEN.brighter().getRGB();
							colorName = "green";
						} else if (vanilla && damage == 11 || defaultDisplayName.contains("yellow"))
						{
							color = Color.YELLOW.getRGB();
							colorName = "yellow";
						} else if (vanilla && damage == 12 || defaultDisplayName.contains("default"))
						{
							color = PhysicaForcefields.DEFAULT_COLOR;
							colorName = "default";
						} else if (vanilla && damage == 13 || defaultDisplayName.contains("magenta"))
						{
							color = Color.MAGENTA.getRGB();
							colorName = "magenta";
						} else if (vanilla && damage == 14 || defaultDisplayName.contains("orange"))
						{
							color = Color.ORANGE.getRGB();
							colorName = "orange";
						} else if (vanilla && damage == 15 || defaultDisplayName.contains("white"))
						{
							color = Color.WHITE.getRGB();
							colorName = "white";
						}
						constructor.setFieldColorMultiplier(color);
						if (color != saveColor)
						{
							constructor.destroyField(false);
							player.addChatMessage(new ChatComponentText("Changed force field color to: " + colorName));
						} else if (colorName != null)
						{
							player.addChatMessage(new ChatComponentText("\u00A77The force field is already this color"));
						}
						return false;
					}
				}
			}
		}
		return super.onBlockActivated(world, x, y, z, player, side, xHit, yHit, zHit);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
	{
		if (!world.isRemote)
		{
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile instanceof TileFortronFieldConstructor)
			{
				((TileFortronFieldConstructor) tile).destroyField(true);
			}
		}
		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public String getSide()
	{
		return "Forcefields";
	}
}
