package electrodynamics.common.block.subtype;

import electrodynamics.api.subtype.Subtype;
import electrodynamics.common.block.BlockMachine;
import electrodynamics.common.tile.TileAdvancedSolarPanel;
import electrodynamics.common.tile.TileBatteryBox;
import electrodynamics.common.tile.TileCoalGenerator;
import electrodynamics.common.tile.TileElectricPump;
import electrodynamics.common.tile.TileSolarPanel;
import electrodynamics.common.tile.TileThermoelectricGenerator;
import electrodynamics.common.tile.TileTransformer;
import electrodynamics.common.tile.processor.TileElectricFurnace;
import electrodynamics.common.tile.processor.do2o.TileOxidationFurnace;
import electrodynamics.common.tile.processor.o2o.TileMineralCrusher;
import electrodynamics.common.tile.processor.o2o.TileMineralGrinder;
import electrodynamics.common.tile.processor.o2o.TileWireMill;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public enum SubtypeMachine implements Subtype {
    electricfurnace(true, TileElectricFurnace.class), electricfurnacerunning(false, TileElectricFurnace.class),
    coalgenerator(true, TileCoalGenerator.class), coalgeneratorrunning(false, TileCoalGenerator.class),
    wiremill(true, TileWireMill.class), mineralcrusher(true, TileMineralCrusher.class),
    mineralgrinder(true, TileMineralGrinder.class),
    /* TODO: Recipe with lead and acid? */ batterybox(true, TileBatteryBox.class),
    oxidationfurnace(true, TileOxidationFurnace.class), oxidationfurnacerunning(false, TileOxidationFurnace.class),
    downgradetransformer(true, TileTransformer.class), upgradetransformer(true, TileTransformer.class),
    solarpanel(true, TileSolarPanel.class), advancedsolarpanel(true, TileAdvancedSolarPanel.class),
    electricpump(true, TileElectricPump.class), thermoelectricgenerator(true, TileThermoelectricGenerator.class);

    public final Class<? extends TileEntity> tileclass;
    public final boolean showInItemGroup;

    private SubtypeMachine(boolean showInItemGroup, Class<? extends TileEntity> tileclass) {
	this.showInItemGroup = showInItemGroup;
	this.tileclass = tileclass;
    }

    public static boolean shouldBreakOnReplaced(BlockState before, BlockState after) {
	Block bb = before.getBlock();
	Block ba = after.getBlock();
	if (bb instanceof BlockMachine && ba instanceof BlockMachine) {
	    SubtypeMachine mb = ((BlockMachine) bb).machine;
	    SubtypeMachine ma = ((BlockMachine) ba).machine;
	    if (mb == electricfurnace && ma == electricfurnacerunning
		    || mb == electricfurnacerunning && ma == electricfurnace) {
		return false;
	    } else if (mb == coalgenerator && ma == coalgeneratorrunning
		    || mb == coalgeneratorrunning && ma == coalgenerator) {
		return false;
	    } else if (mb == oxidationfurnace && ma == oxidationfurnacerunning
		    || mb == oxidationfurnacerunning && ma == oxidationfurnace) {
		return false;
	    }
	}
	return true;
    }

    public TileEntity createTileEntity(IBlockReader worldIn) {
	if (tileclass != null) {
	    try {
		return tileclass.newInstance();
	    } catch (InstantiationException | IllegalAccessException e) {
		e.printStackTrace();
	    }
	}
	return null;
    }

    @Override
    public String tag() {
	return name();
    }

    @Override
    public String forgeTag() {
	return tag();
    }

    @Override
    public boolean isItem() {
	return false;
    }
}
