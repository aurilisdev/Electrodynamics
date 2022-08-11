package physica.forcefield.common.calculations;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import physica.forcefield.common.ForcefieldItemRegister;
import physica.forcefield.common.configuration.ConfigForcefields;
import physica.forcefield.common.tile.TileFortronFieldConstructor;
import physica.library.location.GridLocation;
import physica.library.location.VectorLocation;

public class ConstructorCalculationThread extends Thread {

	public static final int				EXCLUDE_RADIUS	= 3;
	private TileFortronFieldConstructor	constructor;

	public ConstructorCalculationThread(TileFortronFieldConstructor constructor) {
		this.constructor = constructor;
		setName("ConstructorCalculationThread");
		setPriority(3);
	}

	@Override
	public void run()
	{
		super.run();
		if (!constructor.isInvalid())
		{
			constructor.setCalculating(true);
			constructor.calculatedFieldPoints.clear();
			int index = constructor.getProjectorMode();
			int x = constructor.xCoordShifted();
			int y = constructor.yCoordShifted();
			int z = constructor.zCoordShifted();
			boolean interiorModule = constructor.getModuleCount(ForcefieldItemRegister.moduleMap.get("moduleUpgradeInterior"), TileFortronFieldConstructor.SLOT_UPGRADES[0],
					TileFortronFieldConstructor.SLOT_UPGRADES[TileFortronFieldConstructor.SLOT_UPGRADES.length - 1]) > 0;
			if (index == ForcefieldItemRegister.moduleMap.get("moduleShapeCube").getItemDamage())
			{
				calculateCube(interiorModule, x, y, z);
			} else if (index == ForcefieldItemRegister.moduleMap.get("moduleShapeSphere").getItemDamage() || index == ForcefieldItemRegister.moduleMap.get("moduleShapeHemisphere").getItemDamage())
			{
				calculateSphere(interiorModule, x, y, z, index == ForcefieldItemRegister.moduleMap.get("moduleShapeHemisphere").getItemDamage());
			} else if (index == ForcefieldItemRegister.moduleMap.get("moduleShapePyramid").getItemDamage())
			{
				calculatePyramid(interiorModule, x, y, z);
			}

			if (isInterrupted())
			{
				constructor.calculatedFieldPoints.clear();
			} else
			{
				GridLocation constructorLocation = constructor.getLocation();
				if (constructor.shouldDisintegrate)
				{
					List<GridLocation> exclude = new ArrayList<>();
					for (int dx = -EXCLUDE_RADIUS; dx <= EXCLUDE_RADIUS; dx++)
					{
						for (int dz = -EXCLUDE_RADIUS; dz <= EXCLUDE_RADIUS; dz++)
						{
							for (int dy = -EXCLUDE_RADIUS; dy <= EXCLUDE_RADIUS; dy++)
							{
								exclude.add(new GridLocation(constructorLocation.xCoord + dx, constructorLocation.yCoord + dy, constructorLocation.zCoord + dz));
							}
						}
					}
					constructor.calculatedFieldPoints.removeAll(exclude);
				}
				constructor.maximumForceFieldCount = constructor.calculatedFieldPoints.size();
			}

			constructor.setCalculating(false);
		}
	}

	private void calculateCube(boolean interior, int x, int y, int z)
	{
		ItemStack scaleModule = ForcefieldItemRegister.moduleMap.get("moduleManipulationScale");
		int xRadiusPos = (int) (x
				+ Math.min(64, constructor.getModuleCountIn(scaleModule, TileFortronFieldConstructor.SLOT_EAST[0], TileFortronFieldConstructor.SLOT_EAST[1])) / (interior ? ConfigForcefields.FORCEFIELD_INTERIOR_MODULE_DOWNSIZE : 1));
		int yRadiusPos = (int) Math.min(255,
				Math.max(0, y + constructor.getModuleCountIn(scaleModule, TileFortronFieldConstructor.SLOT_UP[0], TileFortronFieldConstructor.SLOT_UP[1]) / (interior ? ConfigForcefields.FORCEFIELD_INTERIOR_MODULE_DOWNSIZE : 1)));
		int zRadiusPos = (int) (z
				+ Math.min(64, constructor.getModuleCountIn(scaleModule, TileFortronFieldConstructor.SLOT_SOUTH[0], TileFortronFieldConstructor.SLOT_SOUTH[1])) / (interior ? ConfigForcefields.FORCEFIELD_INTERIOR_MODULE_DOWNSIZE : 1));
		int xRadiusNeg = (int) (x
				- Math.min(64, constructor.getModuleCountIn(scaleModule, TileFortronFieldConstructor.SLOT_WEST[0], TileFortronFieldConstructor.SLOT_WEST[1])) / (interior ? ConfigForcefields.FORCEFIELD_INTERIOR_MODULE_DOWNSIZE : 1));
		int yRadiusNeg = (int) Math.max(0,
				y - constructor.getModuleCountIn(scaleModule, TileFortronFieldConstructor.SLOT_DOWN[0], TileFortronFieldConstructor.SLOT_DOWN[1]) / (interior ? ConfigForcefields.FORCEFIELD_INTERIOR_MODULE_DOWNSIZE : 1));
		int zRadiusNeg = (int) (z
				- Math.min(64, constructor.getModuleCountIn(scaleModule, TileFortronFieldConstructor.SLOT_NORTH[0], TileFortronFieldConstructor.SLOT_NORTH[1])) / (interior ? ConfigForcefields.FORCEFIELD_INTERIOR_MODULE_DOWNSIZE : 1));
		for (int i = xRadiusNeg; i <= xRadiusPos; i++)
		{
			for (int j = yRadiusNeg; j <= yRadiusPos; j++)
			{
				for (int k = zRadiusNeg; k <= zRadiusPos; k++)
				{
					if (isInterrupted())
					{
						return;
					}
					boolean isEdge = i == xRadiusNeg || i == xRadiusPos || j == yRadiusNeg || j == yRadiusPos || k == zRadiusNeg || k == zRadiusPos;
					if (interior != isEdge)
					{
						constructor.calculatedFieldPoints.add(new GridLocation(i, j, k));
					}
				}
			}
		}
	}

	private void calculateSphere(boolean interior, int x, int y, int z, boolean semi)
	{
		ItemStack scaleModule = ForcefieldItemRegister.moduleMap.get("moduleManipulationScale");
		int radius = Math.min(64, constructor.getModuleCount(scaleModule, TileFortronFieldConstructor.SLOT_MODULES[0], TileFortronFieldConstructor.SLOT_MODULES[TileFortronFieldConstructor.SLOT_MODULES.length - 1]) / 6);
		if (interior)
		{
			radius /= ConfigForcefields.FORCEFIELD_INTERIOR_MODULE_DOWNSIZE;
		}
		for (int i = x - radius; i <= x + radius; i++)
		{
			for (int j = !semi ? Math.max(0, constructor.yCoordShifted() - radius) : Math.max(0, y); j <= Math.min(255, y + radius); j++)
			{
				for (int k = z - radius; k <= z + radius; k++)
				{
					if (isInterrupted())
					{
						return;
					}
					VectorLocation vector = new VectorLocation(i + 0.5f, j + 0.5f, k + 0.5f);
					int distance = (int) vector.getDistance(constructor.xCoordShifted() + 0.5f, constructor.yCoordShifted() + 0.5f, constructor.zCoordShifted() + 0.5f);
					if (interior ? distance <= radius : distance == radius)
					{
						constructor.calculatedFieldPoints.add(vector.BlockLocation());
					}
				}
			}
		}
	}

	private void calculatePyramid(boolean interior, int x, int y, int z)
	{
		ItemStack scaleModule = ForcefieldItemRegister.moduleMap.get("moduleManipulationScale");
		double xPos = x + Math.min(64, constructor.getModuleCountIn(scaleModule, TileFortronFieldConstructor.SLOT_EAST[0], TileFortronFieldConstructor.SLOT_EAST[1])) / (interior ? ConfigForcefields.FORCEFIELD_INTERIOR_MODULE_DOWNSIZE : 1);
		double yPos = Math.min(255, y + constructor.getModuleCountIn(scaleModule, TileFortronFieldConstructor.SLOT_UP[0], TileFortronFieldConstructor.SLOT_UP[1]) / (interior ? ConfigForcefields.FORCEFIELD_INTERIOR_MODULE_DOWNSIZE : 1));
		double zPos = z
				+ Math.min(64, constructor.getModuleCountIn(scaleModule, TileFortronFieldConstructor.SLOT_SOUTH[0], TileFortronFieldConstructor.SLOT_SOUTH[1])) / (interior ? ConfigForcefields.FORCEFIELD_INTERIOR_MODULE_DOWNSIZE : 1);
		double xNeg = x - Math.min(64, constructor.getModuleCountIn(scaleModule, TileFortronFieldConstructor.SLOT_WEST[0], TileFortronFieldConstructor.SLOT_WEST[1])) / (interior ? ConfigForcefields.FORCEFIELD_INTERIOR_MODULE_DOWNSIZE : 1);
		double yNeg = Math.max(0, y - constructor.getModuleCountIn(scaleModule, TileFortronFieldConstructor.SLOT_DOWN[0], TileFortronFieldConstructor.SLOT_DOWN[1]) / (interior ? ConfigForcefields.FORCEFIELD_INTERIOR_MODULE_DOWNSIZE : 1));
		double zNeg = z
				- Math.min(64, constructor.getModuleCountIn(scaleModule, TileFortronFieldConstructor.SLOT_NORTH[0], TileFortronFieldConstructor.SLOT_NORTH[1])) / (interior ? ConfigForcefields.FORCEFIELD_INTERIOR_MODULE_DOWNSIZE : 1);
		yPos += constructor.yCoordShifted() - yNeg;
		yPos = Math.min(255, yPos);
		for (int j = (int) yNeg; j <= yPos; j++)
		{
			xNeg += 1;
			xPos -= 1;
			zNeg += 1;
			zPos -= 1;
			for (int i = (int) xNeg; i <= xPos; i++)
			{
				for (int k = (int) zNeg; k <= zPos; k++)
				{
					if (isInterrupted())
					{
						return;
					}
					boolean isEdge = j == yPos || j == yNeg || (i == xNeg || i == xPos) && j >= yNeg && j <= yPos || (k == zNeg || k == zPos) && j >= yNeg && j <= yPos;
					if (interior != isEdge)
					{
						constructor.calculatedFieldPoints.add(new GridLocation(i, j, k));
					}
				}
			}
		}
	}
}
