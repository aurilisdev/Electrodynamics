package electrodynamics.prefab.utilities.object;

import java.util.List;

import com.mojang.datafixers.util.Pair;

import electrodynamics.common.item.subtype.SubtypeDrillHead;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import voltaic.prefab.utilities.math.PrecisionVector;

public class QuarryArmDataHolder {

	private final List<Pair<PrecisionVector, AxisAlignedBB>> lightParts; 
	private final List<Pair<PrecisionVector, AxisAlignedBB>> darkParts; 
	private final List<Pair<PrecisionVector, AxisAlignedBB>> titaniumParts; 
	private final Pair<PrecisionVector, AxisAlignedBB> drillHead;
	private final SubtypeDrillHead headType;
	private final QuarryWheelDataHolder leftWheel;
	private final QuarryWheelDataHolder rightWheel;
	private final QuarryWheelDataHolder topWheel;
	private final QuarryWheelDataHolder bottomWheel;
	private final boolean running;
	private final int progress;
	private final int speed;
	private final List<BlockPos> corners;
	private final int[] signs;
	
	public QuarryArmDataHolder(List<Pair<PrecisionVector, AxisAlignedBB>> lightParts, List<Pair<PrecisionVector, AxisAlignedBB>> darkParts, List<Pair<PrecisionVector, AxisAlignedBB>> titaniumParts, Pair<PrecisionVector, AxisAlignedBB> drillHead, SubtypeDrillHead headType, QuarryWheelDataHolder leftWheel, QuarryWheelDataHolder rightWheel, QuarryWheelDataHolder topWheel, QuarryWheelDataHolder bottomWheel, boolean running, int progress, int speed, List<BlockPos> corners, int[] signs) {
		
		this.lightParts = lightParts;
		this.darkParts = darkParts;
		this.titaniumParts = titaniumParts;
		this.drillHead = drillHead;
		this.headType = headType;
		this.leftWheel = leftWheel;
		this.rightWheel = rightWheel;
		this.topWheel = topWheel;
		this.bottomWheel = bottomWheel;
		this.running = running;
		this.progress = progress;
		this.speed = speed;
		this.corners = corners;
		this.signs = signs;
		
	}
	
	public List<Pair<PrecisionVector, AxisAlignedBB>> lightParts() {
		return lightParts;
	}
	
	public List<Pair<PrecisionVector, AxisAlignedBB>> darkParts() {
		return darkParts;
	}
	
	public List<Pair<PrecisionVector, AxisAlignedBB>> titaniumParts() {
		return titaniumParts;
	}
	
	public Pair<PrecisionVector, AxisAlignedBB> drillHead() {
		return drillHead;
	}
	
	public SubtypeDrillHead headType() {
		return headType;
	}
	
	public QuarryWheelDataHolder leftWheel() {
		return leftWheel;
	}
	
	public QuarryWheelDataHolder rightWheel() {
		return rightWheel;
	}
	
	public QuarryWheelDataHolder topWheel() {
		return topWheel;
	}
	
	public QuarryWheelDataHolder bottomWheel() {
		return bottomWheel;
	}
	
	public boolean running() {
		return running;
	}
	
	public int progress() {
		return progress;
	}
	
	public int speed() {
		return speed;
	}
	
	public List<BlockPos> corners() {
		return corners;
	}
	
	public int[] signs() {
		return signs;
	}
	
	
}
