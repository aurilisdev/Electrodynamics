package physica.nuclear.common.tile;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import physica.api.core.abstraction.Face;
import physica.api.nuclear.IElectromagnet;
import physica.library.location.GridLocation;
import physica.library.tile.TileBase;
import physica.nuclear.common.NuclearBlockRegister;
import physica.nuclear.common.configuration.ConfigNuclearPhysics;
import physica.nuclear.common.effect.damage.DamageSourcePlasma;

public class TilePlasma extends TileBase {

	public static boolean canPlace(Block block, World world, int x, int y, int z) {
		if (block == NuclearBlockRegister.blockFusionReactor) {
			return false;
		}
		return block.getBlockHardness(world, x, y, z) != -1 && !(block instanceof IElectromagnet);
	}

	public int strength = ConfigNuclearPhysics.PLASMA_STRENGTH;

	public static final int TARGET_TEMPERATURE = 4407;

	@Override
	public void updateServer(int ticks) {
		super.updateServer(ticks);
		GridLocation loc = getLocation();
		if (ticks == 20) {
			if (isServer()) {
				if (ConfigNuclearPhysics.PROTECTED_WORLDS.contains(World().getWorldInfo().getWorldName().toLowerCase())) {
					World().setBlock(loc.xCoord, loc.yCoord, loc.zCoord, Blocks.air);
					return;
				}

				int power = Math.max(strength - 1, 0);

				if (power <= 0) {
					World().setBlock(loc.xCoord, loc.yCoord, loc.zCoord, Blocks.fire);
					return;
				}
				float directions = 1;
				for (Face direction : Face.VALID) {
					if (World().rand.nextFloat() < directions) {
						Block block = World().getBlock(loc.xCoord + direction.offsetX, loc.yCoord + direction.offsetY, loc.zCoord + direction.offsetZ);
						if (canPlace(block, World(), loc.xCoord, loc.yCoord, loc.zCoord)) {
							directions -= 1 / 6f;
							World().setBlock(loc.xCoord + direction.offsetX, loc.yCoord + direction.offsetY, loc.zCoord + direction.offsetZ, NuclearBlockRegister.blockPlasma, 0, 3);

							TileEntity tile = World().getTileEntity(loc.xCoord + direction.offsetX, loc.yCoord + direction.offsetY, loc.zCoord + direction.offsetZ);
							if (tile instanceof TilePlasma) {
								int newPower = power + World().rand.nextInt(2) - 1;
								((TilePlasma) tile).strength = (int) (newPower / Math.max(1, block.getBlockHardness(World(), loc.xCoord + direction.offsetX, loc.yCoord + direction.offsetY, loc.zCoord + direction.offsetZ)));
							}
						}
					}
				}
			}
		} else if (ticks > 20) {
			if (ticks > strength * 5 && World().rand.nextFloat() < 0.333f) {
				World().setBlock(loc.xCoord, loc.yCoord, loc.zCoord, World().rand.nextFloat() < 0.025f ? Blocks.fire : Blocks.air);
			}
		}

		AxisAlignedBB bounds = AxisAlignedBB.getBoundingBox(loc.xCoord - 1, loc.yCoord - 1, loc.zCoord - 1, loc.xCoord + 2, loc.yCoord + 2, loc.zCoord + 2);
		List<Entity> entitiesNearby = World().getEntitiesWithinAABB(Entity.class, bounds);
		for (Entity entity : entitiesNearby) {
			entity.attackEntityFrom(DamageSourcePlasma.INSTANCE, (float) (10 / entity.getDistance(loc.xCoord + 0.5, loc.yCoord + 0.5, loc.zCoord + 0.5)));
		}
		for (Face direction : Face.VALID) {
			if (direction.ordinal() > 0) {
				if (World().getBlock(loc.xCoord + direction.offsetX, loc.yCoord + direction.offsetY, loc.zCoord + direction.offsetZ) == NuclearBlockRegister.blockElectromagnet) {
					Block blockAboveMagnet = World().getBlock(loc.xCoord + direction.offsetX, loc.yCoord + direction.offsetY + 1, loc.zCoord + direction.offsetZ);
					if (blockAboveMagnet == Blocks.water) {
						TileEntity tileAboveWater = World().getTileEntity(loc.xCoord + direction.offsetX, loc.yCoord + direction.offsetY + 2, loc.zCoord + direction.offsetZ);
						if (tileAboveWater instanceof TileTurbine) {
							TileTurbine turbine = (TileTurbine) tileAboveWater;
							float temperature = (float) (TARGET_TEMPERATURE * 1.25f / (TileFusionReactor.PLASMA_SPAWN_STRENGTH * 0.15226));
							int steam = (int) temperature * (World().getBlockMetadata(loc.xCoord + direction.offsetX, loc.yCoord + direction.offsetY, loc.zCoord + direction.offsetZ) > 1 ? 100 : 10);
							if (steam > 0) {
								turbine.addSteam(steam);
							}
						}
					}
				}
			}
		}
	}
}
