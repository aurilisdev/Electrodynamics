package electrodynamics.common.block.subtype;

import electrodynamics.api.ISubtype;
import electrodynamics.common.block.BlockMachine;
import electrodynamics.common.tile.TileAdvancedSolarPanel;
import electrodynamics.common.tile.TileBatteryBox;
import electrodynamics.common.tile.TileCoalGenerator;
import electrodynamics.common.tile.TileElectricFurnace;
import electrodynamics.common.tile.TileElectricPump;
import electrodynamics.common.tile.TileHydroelectricGenerator;
import electrodynamics.common.tile.TileMineralCrusher;
import electrodynamics.common.tile.TileMineralGrinder;
import electrodynamics.common.tile.TileOxidationFurnace;
import electrodynamics.common.tile.TileSolarPanel;
import electrodynamics.common.tile.TileThermoelectricGenerator;
import electrodynamics.common.tile.TileTransformer;
import electrodynamics.common.tile.TileWindmill;
import electrodynamics.common.tile.TileWireMill;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;

public enum SubtypeMachine implements ISubtype {
    electricfurnace(true, TileElectricFurnace.class), electricfurnacerunning(false, TileElectricFurnace.class),
    coalgenerator(true, TileCoalGenerator.class), coalgeneratorrunning(false, TileCoalGenerator.class), wiremill(true, TileWireMill.class),
    mineralcrusher(true, TileMineralCrusher.class, true), mineralgrinder(true, TileMineralGrinder.class, true), batterybox(true, TileBatteryBox.class, true),
    oxidationfurnace(true, TileOxidationFurnace.class), oxidationfurnacerunning(false, TileOxidationFurnace.class),
    downgradetransformer(true, TileTransformer.class), upgradetransformer(true, TileTransformer.class), solarpanel(true, TileSolarPanel.class),
    advancedsolarpanel(true, TileAdvancedSolarPanel.class), electricpump(true, TileElectricPump.class),
    thermoelectricgenerator(true, TileThermoelectricGenerator.class), hydroelectricgenerator(true, TileHydroelectricGenerator.class),
    windmill(true, TileWindmill.class);

    public final Class<? extends TileEntity> tileclass;
    public final boolean showInItemGroup;
    private BlockRenderType type = BlockRenderType.MODEL;

    private SubtypeMachine(boolean showInItemGroup, Class<? extends TileEntity> tileclass) {
	this.showInItemGroup = showInItemGroup;
	this.tileclass = tileclass;
    }

    private SubtypeMachine(boolean showInItemGroup, Class<? extends TileEntity> tileclass, boolean customModel) {
	this.showInItemGroup = showInItemGroup;
	this.tileclass = tileclass;
	if (customModel) {
	    type = BlockRenderType.ENTITYBLOCK_ANIMATED;
	}
    }

    public BlockRenderType getRenderType() {
	return type;
    }

    public static boolean shouldBreakOnReplaced(BlockState before, BlockState after) {
	Block bb = before.getBlock();
	Block ba = after.getBlock();
	if (bb instanceof BlockMachine && ba instanceof BlockMachine) {
	    SubtypeMachine mb = ((BlockMachine) bb).machine;
	    SubtypeMachine ma = ((BlockMachine) ba).machine;
	    if (mb == electricfurnace && ma == electricfurnacerunning || mb == electricfurnacerunning && ma == electricfurnace
		    || mb == coalgenerator && ma == coalgeneratorrunning || mb == coalgeneratorrunning && ma == coalgenerator
		    || mb == oxidationfurnace && ma == oxidationfurnacerunning || mb == oxidationfurnacerunning && ma == oxidationfurnace) {
		return false;
	    }
	}
	return true;
    }

    public TileEntity createTileEntity() {
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
