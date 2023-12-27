package electrodynamics.common.block.subtype;

import electrodynamics.api.ISubtype;
import electrodynamics.common.block.BlockMachine;
import electrodynamics.common.tile.electricitygrid.TileCircuitBreaker;
import electrodynamics.common.tile.electricitygrid.TileMultimeterBlock;
import electrodynamics.common.tile.electricitygrid.batteries.TileBatteryBox;
import electrodynamics.common.tile.electricitygrid.batteries.TileLithiumBatteryBox;
import electrodynamics.common.tile.electricitygrid.generators.TileAdvancedSolarPanel;
import electrodynamics.common.tile.electricitygrid.generators.TileCoalGenerator;
import electrodynamics.common.tile.electricitygrid.generators.TileCombustionChamber;
import electrodynamics.common.tile.electricitygrid.generators.TileCreativePowerSource;
import electrodynamics.common.tile.electricitygrid.generators.TileHydroelectricGenerator;
import electrodynamics.common.tile.electricitygrid.generators.TileSolarPanel;
import electrodynamics.common.tile.electricitygrid.generators.TileThermoelectricGenerator;
import electrodynamics.common.tile.electricitygrid.generators.TileWindmill;
import electrodynamics.common.tile.electricitygrid.transformer.TileGenericTransformer.TileTransformer;
import electrodynamics.common.tile.machines.TileChemicalCrystallizer;
import electrodynamics.common.tile.machines.TileChemicalMixer;
import electrodynamics.common.tile.machines.TileEnergizedAlloyer;
import electrodynamics.common.tile.machines.TileFermentationPlant;
import electrodynamics.common.tile.machines.TileLathe;
import electrodynamics.common.tile.machines.TileMineralWasher;
import electrodynamics.common.tile.machines.TileOxidationFurnace;
import electrodynamics.common.tile.machines.TileReinforcedAlloyer;
import electrodynamics.common.tile.machines.charger.TileChargerHV;
import electrodynamics.common.tile.machines.charger.TileChargerLV;
import electrodynamics.common.tile.machines.charger.TileChargerMV;
import electrodynamics.common.tile.machines.furnace.TileElectricFurnace;
import electrodynamics.common.tile.machines.furnace.TileElectricFurnaceDouble;
import electrodynamics.common.tile.machines.furnace.TileElectricFurnaceTriple;
import electrodynamics.common.tile.machines.mineralcrusher.TileMineralCrusher;
import electrodynamics.common.tile.machines.mineralcrusher.TileMineralCrusherDouble;
import electrodynamics.common.tile.machines.mineralcrusher.TileMineralCrusherTriple;
import electrodynamics.common.tile.machines.mineralgrinder.TileMineralGrinder;
import electrodynamics.common.tile.machines.mineralgrinder.TileMineralGrinderDouble;
import electrodynamics.common.tile.machines.mineralgrinder.TileMineralGrinderTriple;
import electrodynamics.common.tile.machines.wiremill.TileWireMill;
import electrodynamics.common.tile.machines.wiremill.TileWireMillDouble;
import electrodynamics.common.tile.machines.wiremill.TileWireMillTriple;
import electrodynamics.common.tile.pipelines.TileCreativeFluidSource;
import electrodynamics.common.tile.pipelines.TileElectricPump;
import electrodynamics.common.tile.pipelines.tank.fluid.TileFluidTankHSLA;
import electrodynamics.common.tile.pipelines.tank.fluid.TileFluidTankReinforced;
import electrodynamics.common.tile.pipelines.tank.fluid.TileFluidTankSteel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;

public enum SubtypeMachine implements ISubtype {
	
	electricfurnace(true, TileElectricFurnace.class, BlockRenderType.MODEL, 8, false),
	electricfurnacedouble(true, TileElectricFurnaceDouble.class, BlockRenderType.MODEL, 8, false),
	electricfurnacetriple(true, TileElectricFurnaceTriple.class, BlockRenderType.MODEL, 8, false),
	
	electricfurnacerunning(false, TileElectricFurnace.class, BlockRenderType.MODEL, 8, false),
	electricfurnacedoublerunning(false, TileElectricFurnaceDouble.class, BlockRenderType.MODEL, 8, false),
	electricfurnacetriplerunning(false, TileElectricFurnaceTriple.class, BlockRenderType.MODEL, 8, false),
	
	coalgenerator(true, TileCoalGenerator.class, BlockRenderType.MODEL, 12, false),
	coalgeneratorrunning(false, TileCoalGenerator.class, BlockRenderType.MODEL, 12, false),
    
	wiremill(true, TileWireMill.class),
	wiremilldouble(true, TileWireMillDouble.class),
	wiremilltriple(true, TileWireMillTriple.class),
	mineralcrusher(true, TileMineralCrusher.class),
	mineralcrusherdouble(true, TileMineralCrusherDouble.class),
	mineralcrushertriple(true, TileMineralCrusherTriple.class),
	mineralgrinder(true, TileMineralGrinder.class),
	mineralgrinderdouble(true, TileMineralGrinderDouble.class),
	mineralgrindertriple(true, TileMineralGrinderTriple.class),
	batterybox(true, TileBatteryBox.class, BlockRenderType.ENTITYBLOCK_ANIMATED, 0, false),
	lithiumbatterybox(true, TileLithiumBatteryBox.class, BlockRenderType.ENTITYBLOCK_ANIMATED, 0, false),
	
	oxidationfurnace(true, TileOxidationFurnace.class, BlockRenderType.MODEL, 6, false),
	oxidationfurnacerunning(false, TileOxidationFurnace.class, BlockRenderType.MODEL, 6, false),
	
	downgradetransformer(true, TileTransformer.class),
	upgradetransformer(true, TileTransformer.class),
	solarpanel(true, TileSolarPanel.class),
	advancedsolarpanel(true, TileAdvancedSolarPanel.class),
	electricpump(true, TileElectricPump.class),
	thermoelectricgenerator(true, TileThermoelectricGenerator.class),
	fermentationplant(true, TileFermentationPlant.class),
	combustionchamber(true, TileCombustionChamber.class),
	hydroelectricgenerator(true, TileHydroelectricGenerator.class),
	windmill(true, TileWindmill.class),
	mineralwasher(true, TileMineralWasher.class),
	chemicalmixer(true, TileChemicalMixer.class, BlockRenderType.ENTITYBLOCK_ANIMATED, 0, false),
	chemicalcrystallizer(true, TileChemicalCrystallizer.class),
	circuitbreaker(true, TileCircuitBreaker.class),
	multimeterblock(true, TileMultimeterBlock.class),
	
	energizedalloyer(true, TileEnergizedAlloyer.class, BlockRenderType.MODEL, 10, false),
	energizedalloyerrunning(false, TileEnergizedAlloyer.class, BlockRenderType.MODEL, 10, false),
	
	lathe(true, TileLathe.class),
	
	reinforcedalloyer(true, TileReinforcedAlloyer.class),
	reinforcedalloyerrunning(false, TileReinforcedAlloyer.class),
	
	chargerlv(true, TileChargerLV.class),
	chargermv(true, TileChargerMV.class),
	chargerhv(true, TileChargerHV.class),
	tanksteel(true, TileFluidTankSteel.class),
	tankreinforced(true, TileFluidTankReinforced.class, BlockRenderType.MODEL, 15, false),
	tankhsla(true, TileFluidTankHSLA.class),
	creativepowersource(true, TileCreativePowerSource.class),
	creativefluidsource(true, TileCreativeFluidSource.class);

	public final Class<? extends TileEntity> tileclass;
	public final boolean showInItemGroup;
	private BlockRenderType type = BlockRenderType.MODEL;
	public final int litBrightness;
	public final boolean propogateLightDown;

	SubtypeMachine(boolean showInItemGroup, Class<? extends TileEntity> tileclass) {
		this(showInItemGroup, tileclass, BlockRenderType.MODEL, 0, false);
	}

	SubtypeMachine(boolean showInItemGroup, Class<? extends TileEntity> tileclass, BlockRenderType renderShape, int litBrightness, boolean propogateLightDown) {
		this.showInItemGroup = showInItemGroup;
		this.tileclass = tileclass;
		this.litBrightness = litBrightness;
		this.type = renderShape;
		this.propogateLightDown = propogateLightDown;
	}

	public BlockRenderType getRenderType() {
		return type;
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
	
	public static boolean shouldBreakOnReplaced(BlockState before, BlockState after) {
		Block bb = before.getBlock();
		Block ba = after.getBlock();
		if (bb instanceof BlockMachine && ba instanceof BlockMachine) {
			BlockMachine tb = (BlockMachine) bb;
			BlockMachine ta = (BlockMachine) ba;
			SubtypeMachine mb = tb.machine;
			SubtypeMachine ma = ta.machine;
			return (mb != electricfurnace || ma != electricfurnacerunning) && (mb != electricfurnacerunning || ma != electricfurnace) && (mb != electricfurnacedouble || ma != electricfurnacedoublerunning) && (mb != electricfurnacedoublerunning || ma != electricfurnacedouble) && (mb != electricfurnacetriple || ma != electricfurnacetriplerunning) && (mb != electricfurnacetriplerunning || ma != electricfurnacetriple) && (mb != coalgenerator || ma != coalgeneratorrunning) && (mb != coalgeneratorrunning || ma != coalgenerator) && (mb != oxidationfurnace || ma != oxidationfurnacerunning) && (mb != oxidationfurnacerunning || ma != oxidationfurnace) && (mb != energizedalloyer || ma != energizedalloyerrunning) && (ma != energizedalloyer || mb != energizedalloyerrunning) && (ma != reinforcedalloyer || mb != reinforcedalloyerrunning) && (mb != reinforcedalloyer || ma != reinforcedalloyerrunning);
		}
		return true;
	}
}
