package electrodynamics.common.block.subtype;

import java.lang.reflect.InvocationTargetException;

import electrodynamics.api.ISubtype;
import electrodynamics.common.tile.TileChemicalCrystallizer;
import electrodynamics.common.tile.TileChemicalMixer;
import electrodynamics.common.tile.TileCreativeFluidSource;
import electrodynamics.common.tile.TileElectricPump;
import electrodynamics.common.tile.TileElectrolyticSeparator;
import electrodynamics.common.tile.TileEnergizedAlloyer;
import electrodynamics.common.tile.TileFermentationPlant;
import electrodynamics.common.tile.TileLathe;
import electrodynamics.common.tile.TileMineralWasher;
import electrodynamics.common.tile.TileMultimeterBlock;
import electrodynamics.common.tile.TileOxidationFurnace;
import electrodynamics.common.tile.TileReinforcedAlloyer;
import electrodynamics.common.tile.TileTransformer;
import electrodynamics.common.tile.arcfurnace.TileElectricArcFurnace;
import electrodynamics.common.tile.arcfurnace.TileElectricArcFurnaceDouble;
import electrodynamics.common.tile.arcfurnace.TileElectricArcFurnaceTriple;
import electrodynamics.common.tile.battery.TileBatteryBox;
import electrodynamics.common.tile.battery.TileCarbyneBatteryBox;
import electrodynamics.common.tile.battery.TileLithiumBatteryBox;
import electrodynamics.common.tile.charger.TileChargerHV;
import electrodynamics.common.tile.charger.TileChargerLV;
import electrodynamics.common.tile.charger.TileChargerMV;
import electrodynamics.common.tile.furnace.TileElectricFurnace;
import electrodynamics.common.tile.furnace.TileElectricFurnaceDouble;
import electrodynamics.common.tile.furnace.TileElectricFurnaceTriple;
import electrodynamics.common.tile.generators.TileAdvancedSolarPanel;
import electrodynamics.common.tile.generators.TileCoalGenerator;
import electrodynamics.common.tile.generators.TileCombustionChamber;
import electrodynamics.common.tile.generators.TileCreativePowerSource;
import electrodynamics.common.tile.generators.TileHydroelectricGenerator;
import electrodynamics.common.tile.generators.TileSolarPanel;
import electrodynamics.common.tile.generators.TileThermoelectricGenerator;
import electrodynamics.common.tile.generators.TileWindmill;
import electrodynamics.common.tile.mineralcrusher.TileMineralCrusher;
import electrodynamics.common.tile.mineralcrusher.TileMineralCrusherDouble;
import electrodynamics.common.tile.mineralcrusher.TileMineralCrusherTriple;
import electrodynamics.common.tile.mineralgrinder.TileMineralGrinder;
import electrodynamics.common.tile.mineralgrinder.TileMineralGrinderDouble;
import electrodynamics.common.tile.mineralgrinder.TileMineralGrinderTriple;
import electrodynamics.common.tile.network.electric.TileCircuitBreaker;
import electrodynamics.common.tile.network.electric.TilePotentiometer;
import electrodynamics.common.tile.network.electric.TileRelay;
import electrodynamics.common.tile.network.fluid.TileFluidVoid;
import electrodynamics.common.tile.network.gas.TileGasVent;
import electrodynamics.common.tile.quarry.TileCoolantResavoir;
import electrodynamics.common.tile.quarry.TileMotorComplex;
import electrodynamics.common.tile.quarry.TileQuarry;
import electrodynamics.common.tile.quarry.TileSeismicRelay;
import electrodynamics.common.tile.tanks.fluid.TileFluidTankHSLA;
import electrodynamics.common.tile.tanks.fluid.TileFluidTankReinforced;
import electrodynamics.common.tile.tanks.fluid.TileFluidTankSteel;
import electrodynamics.common.tile.tanks.gas.TileGasTankReinforced;
import electrodynamics.common.tile.tanks.gas.TileGasTankHSLA;
import electrodynamics.common.tile.tanks.gas.TileGasTankSteel;
import electrodynamics.common.tile.wiremill.TileWireMill;
import electrodynamics.common.tile.wiremill.TileWireMillDouble;
import electrodynamics.common.tile.wiremill.TileWireMillTriple;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public enum SubtypeMachine implements ISubtype {

	electricfurnace(true, TileElectricFurnace.class, RenderShape.MODEL, 8, false),
	electricfurnacedouble(true, TileElectricFurnaceDouble.class, RenderShape.MODEL, 8, false),
	electricfurnacetriple(true, TileElectricFurnaceTriple.class, RenderShape.MODEL, 8, false),
	electricarcfurnace(true, TileElectricArcFurnace.class, RenderShape.MODEL, 9, false),
	electricarcfurnacedouble(true, TileElectricArcFurnaceDouble.class, RenderShape.MODEL, 9, false),
	electricarcfurnacetriple(true, TileElectricArcFurnaceTriple.class, RenderShape.MODEL, 9, false),
	coalgenerator(true, TileCoalGenerator.class, RenderShape.MODEL, 12, false),
	wiremill(true, TileWireMill.class),
	wiremilldouble(true, TileWireMillDouble.class),
	wiremilltriple(true, TileWireMillTriple.class),
	mineralcrusher(true, TileMineralCrusher.class),
	mineralcrusherdouble(true, TileMineralCrusherDouble.class),
	mineralcrushertriple(true, TileMineralCrusherTriple.class),
	mineralgrinder(true, TileMineralGrinder.class),
	mineralgrinderdouble(true, TileMineralGrinderDouble.class),
	mineralgrindertriple(true, TileMineralGrinderTriple.class),
	batterybox(true, TileBatteryBox.class, RenderShape.ENTITYBLOCK_ANIMATED, 0, false),
	lithiumbatterybox(true, TileLithiumBatteryBox.class, RenderShape.ENTITYBLOCK_ANIMATED, 0, false),
	carbynebatterybox(true, TileCarbyneBatteryBox.class, RenderShape.ENTITYBLOCK_ANIMATED, 0, false),
	oxidationfurnace(true, TileOxidationFurnace.class, RenderShape.MODEL, 6, false),
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
	chemicalmixer(true, TileChemicalMixer.class, RenderShape.ENTITYBLOCK_ANIMATED, 0, false),
	chemicalcrystallizer(true, TileChemicalCrystallizer.class),
	circuitbreaker(true, TileCircuitBreaker.class),
	multimeterblock(true, TileMultimeterBlock.class),
	energizedalloyer(true, TileEnergizedAlloyer.class, RenderShape.MODEL, 10, false),
	lathe(true, TileLathe.class),
	reinforcedalloyer(true, TileReinforcedAlloyer.class),
	chargerlv(true, TileChargerLV.class),
	chargermv(true, TileChargerMV.class),
	chargerhv(true, TileChargerHV.class),
	tanksteel(true, TileFluidTankSteel.class),
	tankreinforced(true, TileFluidTankReinforced.class, RenderShape.MODEL, 15, false),
	tankhsla(true, TileFluidTankHSLA.class),
	creativepowersource(true, TileCreativePowerSource.class),
	creativefluidsource(true, TileCreativeFluidSource.class),
	fluidvoid(true, TileFluidVoid.class),
	electrolyticseparator(true, TileElectrolyticSeparator.class),
	seismicrelay(true, TileSeismicRelay.class),
	quarry(true, TileQuarry.class),
	coolantresavoir(true, TileCoolantResavoir.class),
	motorcomplex(true, TileMotorComplex.class),
	gastanksteel(true, TileGasTankSteel.class),
	gastankreinforced(true, TileGasTankReinforced.class),
	gastankhsla(true, TileGasTankHSLA.class),
	gasvent(true, TileGasVent.class),
	relay(true, TileRelay.class),
	potentiometer(true, TilePotentiometer.class);

	public final Class<? extends BlockEntity> tileclass;
	public final boolean showInItemGroup;
	private RenderShape type = RenderShape.MODEL;
	public final int litBrightness;
	public final boolean propogateLightDown;
	
	SubtypeMachine(boolean showInItemGroup, Class<? extends BlockEntity> tileclass) {
		this(showInItemGroup, tileclass, RenderShape.MODEL, 0, false);
	}
	
	SubtypeMachine(boolean showInItemGroup, Class<? extends BlockEntity> tileclass, RenderShape renderShape, int litBrightness, boolean propogateLightDown) {
		this.showInItemGroup = showInItemGroup;
		this.tileclass = tileclass;
		this.litBrightness = litBrightness;
		this.type = renderShape;
		this.propogateLightDown = propogateLightDown;
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
