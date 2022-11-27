package electrodynamics.common.block.subtype;

import java.lang.reflect.InvocationTargetException;

import electrodynamics.api.ISubtype;
import electrodynamics.common.tile.TileAdvancedSolarPanel;
import electrodynamics.common.tile.TileBatteryBox;
import electrodynamics.common.tile.TileCarbyneBatteryBox;
import electrodynamics.common.tile.TileChargerHV;
import electrodynamics.common.tile.TileChargerLV;
import electrodynamics.common.tile.TileChargerMV;
import electrodynamics.common.tile.TileChemicalCrystallizer;
import electrodynamics.common.tile.TileChemicalMixer;
import electrodynamics.common.tile.TileCircuitBreaker;
import electrodynamics.common.tile.TileCoalGenerator;
import electrodynamics.common.tile.TileCombustionChamber;
import electrodynamics.common.tile.TileCoolantResavoir;
import electrodynamics.common.tile.TileCreativeFluidSource;
import electrodynamics.common.tile.TileCreativePowerSource;
import electrodynamics.common.tile.TileElectricArcFurnace;
import electrodynamics.common.tile.TileElectricArcFurnaceDouble;
import electrodynamics.common.tile.TileElectricArcFurnaceTriple;
import electrodynamics.common.tile.TileElectricFurnace;
import electrodynamics.common.tile.TileElectricFurnaceDouble;
import electrodynamics.common.tile.TileElectricFurnaceTriple;
import electrodynamics.common.tile.TileElectricPump;
import electrodynamics.common.tile.TileElectrolyticSeparator;
import electrodynamics.common.tile.TileEnergizedAlloyer;
import electrodynamics.common.tile.TileFermentationPlant;
import electrodynamics.common.tile.TileFluidVoid;
import electrodynamics.common.tile.TileHydroelectricGenerator;
import electrodynamics.common.tile.TileLathe;
import electrodynamics.common.tile.TileLithiumBatteryBox;
import electrodynamics.common.tile.TileMineralCrusher;
import electrodynamics.common.tile.TileMineralCrusherDouble;
import electrodynamics.common.tile.TileMineralCrusherTriple;
import electrodynamics.common.tile.TileMineralGrinder;
import electrodynamics.common.tile.TileMineralGrinderDouble;
import electrodynamics.common.tile.TileMineralGrinderTriple;
import electrodynamics.common.tile.TileMineralWasher;
import electrodynamics.common.tile.TileMotorComplex;
import electrodynamics.common.tile.TileMultimeterBlock;
import electrodynamics.common.tile.TileOxidationFurnace;
import electrodynamics.common.tile.TileQuarry;
import electrodynamics.common.tile.TileReinforcedAlloyer;
import electrodynamics.common.tile.TileSeismicRelay;
import electrodynamics.common.tile.TileSolarPanel;
import electrodynamics.common.tile.TileTankHSLA;
import electrodynamics.common.tile.TileTankReinforced;
import electrodynamics.common.tile.TileTankSteel;
import electrodynamics.common.tile.TileThermoelectricGenerator;
import electrodynamics.common.tile.TileTransformer;
import electrodynamics.common.tile.TileWindmill;
import electrodynamics.common.tile.TileWireMill;
import electrodynamics.common.tile.TileWireMillDouble;
import electrodynamics.common.tile.TileWireMillTriple;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public enum SubtypeMachine implements ISubtype {

	electricfurnace(true, TileElectricFurnace.class, 8),
	electricfurnacedouble(true, TileElectricFurnaceDouble.class, 8),
	electricfurnacetriple(true, TileElectricFurnaceTriple.class, 8),
	electricarcfurnace(true, TileElectricArcFurnace.class, 9),
	electricarcfurnacedouble(true, TileElectricArcFurnaceDouble.class, 9),
	electricarcfurnacetriple(true, TileElectricArcFurnaceTriple.class, 9),
	coalgenerator(true, TileCoalGenerator.class, 12),
	wiremill(true, TileWireMill.class),
	wiremilldouble(true, TileWireMillDouble.class),
	wiremilltriple(true, TileWireMillTriple.class),
	mineralcrusher(true, TileMineralCrusher.class),
	mineralcrusherdouble(true, TileMineralCrusherDouble.class),
	mineralcrushertriple(true, TileMineralCrusherTriple.class),
	mineralgrinder(true, TileMineralGrinder.class),
	mineralgrinderdouble(true, TileMineralGrinderDouble.class),
	mineralgrindertriple(true, TileMineralGrinderTriple.class),
	batterybox(true, TileBatteryBox.class, true),
	lithiumbatterybox(true, TileLithiumBatteryBox.class, true),
	carbynebatterybox(true, TileCarbyneBatteryBox.class, true),
	oxidationfurnace(true, TileOxidationFurnace.class, 6),
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
	chemicalmixer(true, TileChemicalMixer.class, true),
	chemicalcrystallizer(true, TileChemicalCrystallizer.class),
	circuitbreaker(true, TileCircuitBreaker.class),
	multimeterblock(true, TileMultimeterBlock.class),
	energizedalloyer(true, TileEnergizedAlloyer.class, 10),
	lathe(true, TileLathe.class),
	reinforcedalloyer(true, TileReinforcedAlloyer.class),
	chargerlv(true, TileChargerLV.class),
	chargermv(true, TileChargerMV.class),
	chargerhv(true, TileChargerHV.class),
	tanksteel(true, TileTankSteel.class),
	tankreinforced(true, TileTankReinforced.class, 15),
	tankhsla(true, TileTankHSLA.class),
	creativepowersource(true, TileCreativePowerSource.class),
	creativefluidsource(true, TileCreativeFluidSource.class),
	fluidvoid(true, TileFluidVoid.class),
	electrolyticseparator(true, TileElectrolyticSeparator.class),
	seismicrelay(true, TileSeismicRelay.class),
	quarry(true, TileQuarry.class),
	coolantresavoir(true, TileCoolantResavoir.class),
	motorcomplex(true, TileMotorComplex.class);

	public final Class<? extends BlockEntity> tileclass;
	public final boolean showInItemGroup;
	private RenderShape type = RenderShape.MODEL;
	public final int litBrightness;

	SubtypeMachine(boolean showInItemGroup, Class<? extends BlockEntity> tileclass) {
		this(showInItemGroup, tileclass, 0);
	}

	SubtypeMachine(boolean showInItemGroup, Class<? extends BlockEntity> tileclass, boolean customModel) {
		this(showInItemGroup, tileclass, customModel, 0);
	}
	
	SubtypeMachine(boolean showInItemGroup, Class<? extends BlockEntity> tileclass, int litBrightness) {
		this.showInItemGroup = showInItemGroup;
		this.tileclass = tileclass;
		this.litBrightness = litBrightness;
	}

	SubtypeMachine(boolean showInItemGroup, Class<? extends BlockEntity> tileclass, boolean customModel, int litBrightness) {
		this(showInItemGroup, tileclass, litBrightness);
		if (customModel) {
			type = RenderShape.ENTITYBLOCK_ANIMATED;
		}
	}

	public RenderShape getRenderType() {
		return type;
	}

	public BlockEntity createTileEntity(BlockPos pos, BlockState state) {
		if (tileclass != null) {
			try {
				return tileclass.getConstructor(BlockPos.class, BlockState.class).newInstance(pos, state);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
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

	public boolean isPlayerStorable() {
		return this == quarry;
	}
}
