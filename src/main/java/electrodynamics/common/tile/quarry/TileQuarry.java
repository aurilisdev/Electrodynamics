package electrodynamics.common.tile.quarry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.tile.IPlayerStorable;
import electrodynamics.client.render.event.levelstage.HandlerQuarryArm;
import electrodynamics.common.block.BlockFrame;
import electrodynamics.common.block.states.ElectrodynamicsBlockStates;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerQuarry;
import electrodynamics.common.item.ItemDrillHead;
import electrodynamics.common.item.ItemUpgrade;
import electrodynamics.common.item.subtype.SubtypeDrillHead;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.tile.TileCoolantResavoir;
import electrodynamics.common.tile.TileMotorComplex;
import electrodynamics.common.tile.TileSeismicRelay;
import electrodynamics.prefab.block.GenericEntityBlock;
import electrodynamics.prefab.block.GenericMachineBlock;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.InventoryUtils;
import electrodynamics.prefab.utilities.object.Location;
import electrodynamics.prefab.utilities.object.QuarryArmDataHolder;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import electrodynamics.registers.ElectrodynamicsBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

public class TileQuarry extends GenericTile implements IPlayerStorable {

	private static final int CAPACITY = 10000;
	private static final BlockState AIR = Blocks.AIR.defaultBlockState();
	private static final int CLEAR_SKIP = Math.max(Math.min(Constants.CLEARING_AIR_SKIP, 128), 0);
	private static final int MINE_SKIP = Math.max(Math.min(Constants.CLEARING_AIR_SKIP, 128), 0);
	
	public static final BlockPos OUT_OF_REACH = new BlockPos(0, -1000, 0);

	@Nullable
	private UUID placedBy = null;

	/* FRAME PARAMETERS */
	
	public final Property<Boolean> hasComponents;
	public final Property<Boolean> hasRing;

	private TileMotorComplex complex = null;
	private TileCoolantResavoir resavoir = null;
	private TileSeismicRelay relay = null;

	private boolean hasBottomStrip = false;
	private boolean hasTopStrip = false;
	private boolean hasLeftStrip = false;
	private boolean hasRightStrip = false;

	private BlockPos currPos = null;
	private BlockPos prevPos = null;

	private boolean prevIsCorner = false;

	private boolean lastIsCorner = false;
	
	private HashMap<BlockPos, BlockState> brokenFrames = new HashMap<>();
	private HashSet<BlockPos> repairedFrames = new HashSet<>();

	public final Property<List<BlockPos>> corners;
	public final Property<Boolean> cornerOnRight;

	private boolean hasHandledDecay = false;

	private boolean isAreaCleared = false;

	private int heightShiftCA = 0;
	private int widthShiftCA = 0;
	private int tickDelayCA = 0;

	private int widthShiftCR = 0;

	/* MINING PARAMETERS */

	// want these seperate to prevent potential mixups
	private int lengthShiftMiner = 0;
	private int heightShiftMiner = 1;
	private int widthShiftMiner = 0;
	private int tickDelayMiner = 0;

	public final Property<BlockPos> miningPos;
	public final Property<BlockPos> prevMiningPos;

	public final Property<Boolean> isFinished;

	private boolean widthReverse = false;
	private boolean lengthReverse = false;

	public final Property<Double> quarryPowerUsage;
	public final Property<Double> setupPowerUsage;
	public final Property<Boolean> isPowered;
	public final Property<Boolean> hasHead;
	public final Property<CompoundTag> currHead;

	public final Property<Boolean> hasItemVoid;
	public final Property<Integer> fortuneLevel;
	public final Property<Integer> silkTouchLevel;
	public final Property<Integer> unbreakingLevel;
	
	public final Property<Double> speed;

	private int widthShiftMaintainMining = 0;

	private boolean cont = false;

	/* CLIENT PARAMETERS */

	private List<Location> storedArmFrames = new ArrayList<>();

	public TileQuarry(BlockPos pos, BlockState state) {
		super(ElectrodynamicsBlockTypes.TILE_QUARRY.get(), pos, state);
		
		hasComponents = property(new Property<Boolean>(PropertyType.Boolean, "hascomponents", false));
		hasRing = property(new Property<Boolean>(PropertyType.Boolean, "hasring", false));
		cornerOnRight = property(new Property<Boolean>(PropertyType.Boolean, "corneronright", false));
		
		corners = property(new Property<List<BlockPos>>(PropertyType.BlockPosList, "corners", new ArrayList<>()));
		miningPos = property(new Property<BlockPos>(PropertyType.BlockPos, "miningpos", OUT_OF_REACH));
		prevMiningPos = property(new Property<BlockPos>(PropertyType.BlockPos, "prevminingpos", OUT_OF_REACH));
	
		quarryPowerUsage = property(new Property<Double>(PropertyType.Double, "quarrypowerusage", 0.0));
		setupPowerUsage = property(new Property<Double>(PropertyType.Double, "setuppowerusage", 0.0));
		isPowered = property(new Property<Boolean>(PropertyType.Boolean, "ispowered", false));
		hasHead = property(new Property<Boolean>(PropertyType.Boolean, "hashead", false));
		currHead = property(new Property<CompoundTag>(PropertyType.CompoundTag, "headtype", new CompoundTag()));
		
		hasItemVoid = property(new Property<Boolean>(PropertyType.Boolean, "hasitemvoid", false));
		fortuneLevel = property(new Property<Integer>(PropertyType.Integer, "fortunelevel", 0));
		silkTouchLevel = property(new Property<Integer>(PropertyType.Integer, "silktouchlevel", 0));
		unbreakingLevel = property(new Property<Integer>(PropertyType.Integer, "unbreakinglevel", 0));
		
		isFinished = property(new Property<Boolean>(PropertyType.Boolean, "isfinished", false));
		speed = property(new Property<Double>(PropertyType.Double, "speed", 0.0));
		
		addComponent(new ComponentDirection());
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentTickable().tickServer(this::tickServer).tickClient(this::tickClient));
		addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.DOWN).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 2).maxJoules(Constants.QUARRY_USAGE_PER_TICK * CAPACITY));
		addComponent(new ComponentInventory(this).size(19).inputs(7).outputs(9).upgrades(3).validUpgrades(ContainerQuarry.VALID_UPGRADES).valid(machineValidator()));
		addComponent(new ComponentContainerProvider(SubtypeMachine.quarry).createMenu((id, player) -> new ContainerQuarry(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}

	private void tickServer(ComponentTickable tick) {
		speed.set(complex == null ? 0 : complex.speed.get() + tickDelayMiner);
		BlockPos pos = getBlockPos();
		if (GenericMachineBlock.IPLAYERSTORABLE_MAP.containsKey(pos)) {
			setPlayer(GenericMachineBlock.IPLAYERSTORABLE_MAP.get(pos));
			GenericMachineBlock.IPLAYERSTORABLE_MAP.remove(pos);
		}
		
		//check surrounding components every 5 ticks
		if ((tick.getTicks() + 1) % 5 == 0) {
			checkComponents();
		}
		// if the components are invalid and the quarry has a ring, decay the ring
		if(!hasComponents.get() && hasCorners() && !hasHandledDecay && isAreaCleared) {
			handleFramesDecay();
			return;
		}
		
		//return if the quarry still does not have components
		if(!hasComponents.get()) {
			return;
		}
		
		hasHandledDecay = false;
		
		//if the quarry can still mine and doesn't have a ring, remedy that
		if(!hasRing.get() && tick.getTicks() % (3 + tickDelayCA) == 0 && !isFinished.get()) {
			if (isAreaCleared) {
				checkRing();
			} else {
				clearArea();
			}
		}
		
		//if the quarry still doesn't have a ring return
		if(!hasRing.get()) {
			return;
		}
		
		Level world = getLevel();
		//clean the ring for obstructions
		if (tick.getTicks() % 4 == 0) {
			cleanRing();
		}
		//if frames were broken purposefully repair them
		if (!brokenFrames.isEmpty()) {
			handleBrokenFrames();
		}
		//set the tile data for the repaired frames
		if(!repairedFrames.isEmpty()) {
			handleRepairedFrames();
		}
		
		//if the quarry is done and has a ring, decay the ring and stop mining
		if(isFinished.get() && !hasHandledDecay) {
			handleFramesDecay();
			hasHandledDecay = true;
			return;
		}
		
		//if the quarry is done or the components are invalid, return
		if(isFinished.get() || areComponentsNull()) {
			return;
		}
		
		//remove blocks from the mined area
		if (tick.getTicks() % 4 == 0 && Constants.MAINTAIN_MINING_AREA) {
			maintainMiningArea();
		}
		
		
		ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
		
		isPowered.set(electro.getJoulesStored() >= quarryPowerUsage.get());
		
		//if there isn't enough power don't do anything
		if(!isPowered.get()) {
			return;
		}
		
		//if the motor complex is in an invalid state return
		if(!complex.isPowered.get() || complex.speed.get() <= 0) {
			return;
		}
		
		
		int fluidUse = (int) (complex.powerMultiplier.get() * Constants.QUARRY_WATERUSAGE_PER_BLOCK);
		ComponentInventory inv = getComponent(ComponentType.Inventory);
		hasHead.set(inv.getItem(0).getItem() instanceof ItemDrillHead);
		
		//if there is no room for mined blocks, the fluid resavoir doesn't have enough fluid, or there isn't a drill head, return
		if(!inv.areOutputsEmpty() || !resavoir.hasEnoughFluid(fluidUse) || !hasHead.get() || (tick.getTicks() % complex.speed.get() != 0)) {
			return;
		}
		
		resavoir.drainFluid(fluidUse);
		BlockPos cornerStart = corners.get().get(3);
		BlockPos cornerEnd = corners.get().get(0);
		int deltaW = (int) Math.signum(cornerStart.getX() - cornerEnd.getX());
		int deltaL = (int) Math.signum(cornerStart.getZ() - cornerEnd.getZ());
		int width = cornerStart.getX() - cornerEnd.getX() - 2 * deltaW;
		int length = cornerStart.getZ() - cornerEnd.getZ() - 2 * deltaL;
		// if we have the quarry mine the current block on the next tick, it
		// deals with the issue of the client pos always being one tick behind
		// the server's
		cont = true;
		if (!miningPos.get().equals(OUT_OF_REACH)) {
			BlockState miningState = world.getBlockState(miningPos.get());
			float strength = miningState.getDestroySpeed(world, miningPos.get());
			if (!skipBlock(miningState) && strength >= 0) {
				cont = mineBlock(miningPos.get(), miningState, strength, world, inv.getItem(0), inv, getPlayer((ServerLevel) world));
			}
		}
		prevMiningPos.set(new BlockPos(miningPos.get()));
		
		miningPos.set(new BlockPos(cornerStart.getX() - widthShiftMiner - deltaW, cornerStart.getY() - heightShiftMiner, cornerStart.getZ() - lengthShiftMiner - deltaL));

		BlockState state = world.getBlockState(miningPos.get());
		int blockSkip = 0;
		boolean shouldSkip = true;
		
		//if the mined block shouldn't be skipped then don't skip it
		if(!cont) {
			return;
		}
		//loop until either the mining skip limit is reached or a non-skipable block is found
		while (shouldSkip && blockSkip < MINE_SKIP) {
			if (lengthReverse ? lengthShiftMiner == 0 : lengthShiftMiner == length) {
				lengthReverse = !lengthReverse;
				if (widthReverse ? widthShiftMiner == 0 : widthShiftMiner == width) {
					widthReverse = !widthReverse;
					heightShiftMiner++;
					if (miningPos.get().getY() - 1 == world.getMinBuildHeight()) {
						heightShiftMiner = 1;
						isFinished.set(true);
					}
				} else if (widthReverse) {
					widthShiftMiner -= deltaW;
				} else {
					widthShiftMiner += deltaW;
				}
			} else if (lengthReverse) {
				lengthShiftMiner -= deltaL;
			} else {
				lengthShiftMiner += deltaL;
			}
			miningPos.set(new BlockPos(cornerStart.getX() - widthShiftMiner - deltaW, cornerStart.getY() - heightShiftMiner, cornerStart.getZ() - lengthShiftMiner - deltaL));
			state = world.getBlockState(miningPos.get());
			blockSkip++;
			shouldSkip = skipBlock(state);
		}
		float strength = state.getDestroySpeed(world, miningPos.get());
		tickDelayMiner = (int) strength;
		if (!shouldSkip && strength >= 0) {
			electro.joules(electro.getJoulesStored() - quarryPowerUsage.get());
		}
		if (shouldSkip) {
			if (lengthReverse ? lengthShiftMiner == 0 : lengthShiftMiner == length) {
				lengthReverse = !lengthReverse;
				if (widthReverse ? widthShiftMiner == 0 : widthShiftMiner == width) {
					widthReverse = !widthReverse;
					heightShiftMiner++;
					if (miningPos.get().getY() - 1 == world.getMinBuildHeight()) {
						heightShiftMiner = 1;
						isFinished.set(true);
					}
				} else if (widthReverse) {
					widthShiftMiner -= deltaW;
				} else {
					widthShiftMiner += deltaW;
				}
			} else if (lengthReverse) {
				lengthShiftMiner -= deltaL;
			} else {
				lengthShiftMiner += deltaL;
			}
		}
	
		
	}

	private static final int Y_ARM_SEGMENT_LENGTH = 20;

	private void tickClient(ComponentTickable tick) {
		
		BlockPos pos = getBlockPos();
		HandlerQuarryArm.removeRenderData(getBlockPos());
		if (hasCorners() && !miningPos.get().equals(OUT_OF_REACH)) {
			if (storedArmFrames.isEmpty()) {
				storedArmFrames = getArmFrames();
			}
			Location loc = storedArmFrames.get(0).add(-0.5, 0, -0.5);
			BlockPos startCentered = corners.get().get(3);
			BlockPos endCentered = corners.get().get(0);

			double widthLeft;
			double widthRight;
			double widthTop;
			double widthBottom;
			AABB left;
			AABB right;
			AABB bottom;
			AABB top;
			AABB center;
			List<AABB> downArms = new ArrayList<>();
			AABB headHolder;
			AABB downHead = null;

			List<AABB> arms = new ArrayList<>();
			List<AABB> titanium = new ArrayList<>();

			double x = loc.x();
			double z = loc.z();
			double y = startCentered.getY() + 0.5;

			double deltaY = startCentered.getY() - loc.y() - 0.7;

			int wholeSegmentCount = (int) (deltaY / Y_ARM_SEGMENT_LENGTH);
			double remainder = deltaY / Y_ARM_SEGMENT_LENGTH - wholeSegmentCount;
			for (int i = 0; i < wholeSegmentCount; i++) {
				downArms.add(new AABB(x + 0.25, y - i * Y_ARM_SEGMENT_LENGTH, z + 0.25, x + 0.75, y - (i + 1) * Y_ARM_SEGMENT_LENGTH, z + 0.75));
			}
			int wholeOffset = wholeSegmentCount * Y_ARM_SEGMENT_LENGTH;
			downArms.add(new AABB(x + 0.25, y - wholeOffset, z + 0.25, x + 0.75, y - wholeOffset - Y_ARM_SEGMENT_LENGTH * remainder, z + 0.75));

			headHolder = new AABB(x + 0.20, y - deltaY, z + 0.20, x + 0.8, y - deltaY - 0.2, z + 0.8);

			downHead = new AABB(x + 0.3125, y - deltaY - 0.2, z + 0.3125, x + 0.6875, y - deltaY - 0.5 - 0.2, z + 0.6875);

			center = new AABB(x + 0.1875, y + 0.325, z + 0.1875, x + 0.8125, y - 0.325, z + 0.8125);

			Direction facing = ((ComponentDirection) getComponent(ComponentType.Direction)).getDirection().getOpposite();
			switch (facing) {
			case NORTH, SOUTH:

				widthLeft = x - startCentered.getX();
				widthRight = x - endCentered.getX();
				widthTop = z - startCentered.getZ();
				widthBottom = z - endCentered.getZ();

				if (facing == Direction.SOUTH) {
					if (cornerOnRight.get()) {
						left = new AABB(x + 0.1875, y - 0.25, z + 0.25, x - widthLeft + 0.25, y + 0.25, z + 0.75);
						right = new AABB(x + 0.8125, y - 0.25, z + 0.25, x - widthRight + 0.75, y + 0.25, z + 0.75);
					} else {
						left = new AABB(x + 0.8125, y - 0.25, z + 0.25, x - widthLeft + 0.75, y + 0.25, z + 0.75);
						right = new AABB(x + 0.1875, y - 0.25, z + 0.25, x - widthRight + 0.25, y + 0.25, z + 0.75);
					}
					bottom = new AABB(x + 0.25, y - 0.25, z + 0.1875, x + 0.75, y + 0.25, z - widthBottom + 0.25);
					top = new AABB(x + 0.25, y - 0.25, z + 0.8125, x + 0.75, y + 0.25, z - widthTop + 0.75);
				} else {
					if (cornerOnRight.get()) {
						left = new AABB(x + 0.8125, y - 0.25, z + 0.25, x - widthLeft + 0.75, y + 0.25, z + 0.75);
						right = new AABB(x + 0.1875, y - 0.25, z + 0.25, x - widthRight + 0.25, y + 0.25, z + 0.75);
					} else {
						left = new AABB(x + 0.1875, y - 0.25, z + 0.25, x - widthLeft + 0.25, y + 0.25, z + 0.75);
						right = new AABB(x + 0.8125, y - 0.25, z + 0.25, x - widthRight + 0.75, y + 0.25, z + 0.75);
					}
					bottom = new AABB(x + 0.25, y - 0.25, z + 0.8125, x + 0.75, y + 0.25, z - widthBottom + 0.75);
					top = new AABB(x + 0.25, y - 0.25, z + 0.1875, x + 0.75, y + 0.25, z - widthTop + 0.25);
				}

				arms.add(left);
				arms.add(right);
				arms.add(bottom);
				arms.add(top);
				arms.addAll(downArms);

				titanium.add(center);
				titanium.add(headHolder);

				break;
			case EAST, WEST:

				widthLeft = loc.z() - startCentered.getZ();
				widthRight = loc.z() - endCentered.getZ();
				widthTop = loc.x() - startCentered.getX();
				widthBottom = loc.x() - endCentered.getX();

				if (facing == Direction.WEST) {
					if (cornerOnRight.get()) {
						left = new AABB(x + 0.25, y - 0.25, z + 0.1875, x + 0.75, y + 0.25, z - widthLeft + 0.25);
						right = new AABB(x + 0.25, y - 0.25, z + 0.8125, x + 0.75, y + 0.25, z - widthRight + 0.75);
					} else {
						left = new AABB(x + 0.25, y - 0.25, z + 0.8125, x + 0.75, y + 0.25, z - widthLeft + 0.75);
						right = new AABB(x + 0.25, y - 0.25, z + 0.1875, x + 0.75, y + 0.25, z - widthRight + 0.25);
					}
					bottom = new AABB(x + 0.8125, y - 0.25, z + 0.25, x - widthBottom + 0.75, y + 0.25, z + 0.75);
					top = new AABB(x + 0.1875, y - 0.25, z + 0.25, x - widthTop + 0.25, y + 0.25, z + 0.75);
				} else {
					if (cornerOnRight.get()) {
						left = new AABB(x + 0.25, y - 0.25, z + 0.8125, x + 0.75, y + 0.25, z - widthLeft + 0.75);
						right = new AABB(x + 0.25, y - 0.25, z + 0.1875, x + 0.75, y + 0.25, z - widthRight + 0.25);
					} else {
						left = new AABB(x + 0.25, y - 0.25, z + 0.1875, x + 0.75, y + 0.25, z - widthLeft + 0.25);
						right = new AABB(x + 0.25, y - 0.25, z + 0.8125, x + 0.75, y + 0.25, z - widthRight + 0.75);
					}
					bottom = new AABB(x + 0.1875, y - 0.25, z + 0.25, x - widthBottom + 0.25, y + 0.25, z + 0.75);
					top = new AABB(x + 0.8125, y - 0.25, z + 0.25, x - widthTop + 0.75, y + 0.25, z + 0.75);
				}

				arms.add(left);
				arms.add(right);
				arms.add(bottom);
				arms.add(top);
				arms.addAll(downArms);

				titanium.add(center);

				titanium.add(headHolder);

				break;
			default:
				break;
			}
			storedArmFrames.remove(0);
			HandlerQuarryArm.addRenderData(pos, new QuarryArmDataHolder(arms, titanium, downHead, readHeadType()));
		}
	}

	private List<Location> getArmFrames() {
		List<Location> armFrames = new ArrayList<>();
		if (!miningPos.get().equals(OUT_OF_REACH)) {
			if (isMotorComplexPowered() && !prevMiningPos.get().equals(OUT_OF_REACH)) {
				int numberOfFrames = speed.get().intValue();
				if (numberOfFrames == 0) {
					numberOfFrames = 1;
				}
				double deltaX = (miningPos.get().getX() - prevMiningPos.get().getX()) / numberOfFrames;
				double deltaY = (miningPos.get().getY() - prevMiningPos.get().getY()) / numberOfFrames;
				double deltaZ = (miningPos.get().getZ() - prevMiningPos.get().getZ()) / numberOfFrames;
				if (Math.abs(deltaX) + Math.abs(deltaY) + Math.abs(deltaZ) == 0) {
					armFrames.add(new Location(miningPos.get()));
				} else {
					for (int i = 1; i <= numberOfFrames; i++) {
						armFrames.add(new Location(prevMiningPos.get().getX() + deltaX * i, prevMiningPos.get().getY() + deltaY * i, prevMiningPos.get().getZ() + deltaZ * i));
					}
				}
			} else {
				armFrames.add(new Location(miningPos.get()));
			}

		}
		return armFrames;
	}
	
	public void addBrokenFrame(BlockPos frame, BlockState frameState) {
		brokenFrames.put(frame, frameState);
	}

	/**
	 * Looks for obstructions that have been placed in the previously mined area
	 * If one is found, the mining position is set to that position 
	 */
	private void maintainMiningArea() {
		Level world = getLevel();
		BlockPos cornerStart = corners.get().get(3);
		BlockPos cornerEnd = corners.get().get(0);
		int deltaW = (int) Math.signum(cornerStart.getX() - cornerEnd.getX());
		int deltaL = (int) Math.signum(cornerStart.getZ() - cornerEnd.getZ());
		int width = cornerStart.getX() - cornerEnd.getX() - 2 * deltaW;
		int length = cornerStart.getZ() - cornerEnd.getZ();
		BlockPos startPos = new BlockPos(cornerStart.getX() - widthShiftMaintainMining - deltaW, cornerStart.getY(), cornerStart.getZ() - deltaL);
		BlockPos endPos = new BlockPos(cornerStart.getX() - widthShiftMaintainMining - deltaW, miningPos.get().getY() + 1, cornerStart.getZ() - length + deltaL);
		Iterator<BlockPos> positions = BlockPos.betweenClosedStream(startPos, endPos).iterator();
		BlockPos pos;
		while(positions.hasNext()) {
			pos = positions.next();
			BlockState state = world.getBlockState(pos);
			if (!skipBlock(state)) {
				boolean canMine = world.destroyBlock(pos, false, getPlayer((ServerLevel) world)) || Constants.BYPASS_CLAIMS;
				if (canMine) {
					int newWidthShift = -1 * (pos.getX() - (cornerStart.getX() - deltaW));
					int newLengthShift = -1 * (pos.getZ() - (cornerStart.getZ() - deltaL));
					widthShiftMiner = newWidthShift;
					lengthShiftMiner = newLengthShift;
					widthShiftMaintainMining = 0;
					
					miningPos.set(pos);
					return;
				}
			}
		}
		
		if (widthShiftMaintainMining == width) {
			widthShiftMaintainMining = 0;
		} else {
			widthShiftMaintainMining += deltaW;
		}

	}

	/**
	 * clears obstructions from the inside of the frame ring
	 */
	private void cleanRing() {
		Level world = getLevel();
		BlockPos cornerStart = corners.get().get(3);
		BlockPos cornerEnd = corners.get().get(0);
		int deltaW = (int) Math.signum(cornerStart.getX() - cornerEnd.getX());
		int deltaL = (int) Math.signum(cornerStart.getZ() - cornerEnd.getZ());
		int width = cornerStart.getX() - cornerEnd.getX() - 2 * deltaW;
		int length = cornerStart.getZ() - cornerEnd.getZ();
		BlockPos startPos = new BlockPos(cornerStart.getX() - widthShiftCR - deltaW, cornerStart.getY(), cornerStart.getZ() - deltaL);
		BlockPos endPos = new BlockPos(cornerStart.getX() - widthShiftCR - deltaW, cornerStart.getY(), cornerStart.getZ() - length + deltaL);
		Stream<BlockPos> positions = BlockPos.betweenClosedStream(startPos, endPos);
		positions.forEach(pos -> {
			BlockState state = world.getBlockState(pos);
			if (!skipBlock(state)) {
				boolean canMine = world.destroyBlock(pos, false, getPlayer((ServerLevel) world)) || Constants.BYPASS_CLAIMS;
				if (canMine) {
					world.setBlockAndUpdate(pos, AIR);
					world.playSound(null, pos, state.getSoundType().getBreakSound(), SoundSource.BLOCKS, 0.5F, 1.0F);
				}
			}
		});
		if (widthShiftCR == width) {
			widthShiftCR = 0;
		} else {
			widthShiftCR += deltaW;
		}
	}

	private boolean mineBlock(BlockPos pos, BlockState state, float strength, Level world, ItemStack drillHead, ComponentInventory inv, Player player) {
		boolean sucess = world.destroyBlock(pos, false, player);
		if (sucess) {
			SubtypeDrillHead head = ((ItemDrillHead) drillHead.getItem()).head;
			writeHeadType(head);;
			if (!head.isUnbreakable) {
				int durabilityUsed = (int) (Math.ceil(strength) / (unbreakingLevel.get() + 1.0F));
				if (drillHead.getDamageValue() + durabilityUsed >= drillHead.getMaxDamage()) {
					world.playSound(null, getBlockPos(), SoundEvents.ITEM_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
					drillHead.shrink(1);
					writeHeadType(null);
				} else {
					drillHead.setDamageValue(drillHead.getDamageValue() + durabilityUsed);
				}
			}
			// TODO make this work with custom mining tiers
			ItemStack pickaxe = new ItemStack(Items.NETHERITE_PICKAXE);
			if (silkTouchLevel.get() > 0) {
				pickaxe.enchant(Enchantments.SILK_TOUCH, silkTouchLevel.get());
			} else if (fortuneLevel.get() > 0) {
				pickaxe.enchant(Enchantments.BLOCK_FORTUNE, fortuneLevel.get());
			}
			List<ItemStack> lootItems = Block.getDrops(state, (ServerLevel) world, pos, null, null, pickaxe);
			List<ItemStack> voidItemStacks = inv.getInputContents().get(0);
			voidItemStacks.remove(0);
			List<Item> voidItems = new ArrayList<>();
			voidItemStacks.forEach(h -> voidItems.add(h.getItem()));
			List<ItemStack> items = new ArrayList<>();

			if (hasItemVoid.get()) {
				lootItems.forEach(lootItem -> {
					if (!voidItems.contains(lootItem.getItem())) {
						items.add(lootItem);
					}
				});
			} else {
				items.addAll(lootItems);
			}
			InventoryUtils.addItemsToInventory(inv, items, inv.getOutputStartIndex(), inv.getOutputContents().size());
			world.playSound(null, pos, state.getSoundType().getBreakSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
		}
		return sucess;
	}

	//responsible for clearing initial obstructions from the mining area
	private void clearArea() {
		ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
		setupPowerUsage.set(Constants.QUARRY_USAGE_PER_TICK);
		isPowered.set(electro.getJoulesStored() >= setupPowerUsage.get());
		if (hasCorners() && isPowered.get()) {
			Level world = getLevel();
			BlockPos start = corners.get().get(3);
			BlockPos end = corners.get().get(0);
			int width = start.getX() - end.getX();
			int height = start.getZ() - end.getZ();
			int deltaW = (int) Math.signum(width);
			int deltaH = (int) Math.signum(height);
			BlockPos checkPos = new BlockPos(start.getX() - widthShiftCA, start.getY(), start.getZ() - heightShiftCA);
			BlockState state = world.getBlockState(checkPos);
			float strength = state.getDestroySpeed(world, checkPos);
			int blockSkip = 0;
			while (skipBlock(state) && blockSkip < CLEAR_SKIP) {
				if (heightShiftCA == height) {
					heightShiftCA = 0;
					if (widthShiftCA == width) {
						isAreaCleared = true;
						widthShiftCA = 0;
						tickDelayCA = 0;
						return;
					}
					widthShiftCA += deltaW;
				} else {
					heightShiftCA += deltaH;
				}
				checkPos = new BlockPos(start.getX() - widthShiftCA, start.getY(), start.getZ() - heightShiftCA);
				state = world.getBlockState(checkPos);
				blockSkip++;
			}
			if (strength >= 0 && electro.getJoulesStored() >= setupPowerUsage.get() * strength) {
				boolean sucess = false;
				if (!skipBlock(state)) {
					tickDelayCA = (int) Math.ceil(strength / 5.0F);
					electro.joules(electro.getJoulesStored() - setupPowerUsage.get() * strength);
					sucess = world.destroyBlock(checkPos, false, getPlayer((ServerLevel) world));
					if (sucess) {
						world.playSound(null, checkPos, state.getSoundType().getBreakSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
					}
				}
				if (sucess) {
					if (heightShiftCA == height) {
						heightShiftCA = 0;
						if (widthShiftCA == width) {
							isAreaCleared = true;
							widthShiftCA = 0;
							tickDelayCA = 0;
						} else {
							widthShiftCA += deltaW;
						}
					} else {
						heightShiftCA += deltaH;
					}
				}
			}
		}
	}

	/**
	 * Replaces all broken frames
	 */
	private void handleBrokenFrames() {
		Level world = getLevel();
		brokenFrames.forEach((pos, state) -> {
			world.setBlockAndUpdate(pos, state);
			world.playSound(null, pos, SoundEvents.ANVIL_PLACE, SoundSource.BLOCKS, 0.5F, 1.0F);
			repairedFrames.add(new BlockPos(pos));
		});
		brokenFrames.clear();
	}
	
	/**
	 * Sets the quarry owner pos in the repaired frames
	 */
	private void handleRepairedFrames() {
		Level world = getLevel();
		Iterator<BlockPos> it = repairedFrames.iterator();
		BlockPos pos;
		BlockEntity entity;
		while(it.hasNext()) {
			pos = it.next();
			entity = world.getBlockEntity(pos);
			if(entity != null && entity instanceof TileFrame frame) {
				frame.setQuarryPos(getBlockPos());
				it.remove();
			}
		}
	}

	private void checkRing() {
		ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
		if (electro.getJoulesStored() < Constants.QUARRY_USAGE_PER_TICK && hasCorners()) {
			return;
		}
		electro.joules(electro.getJoulesStored() - Constants.QUARRY_USAGE_PER_TICK);
		BlockState cornerState = ElectrodynamicsBlocks.blockFrameCorner.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, Boolean.FALSE);
		Level world = getLevel();
		BlockPos frontOfQuarry = corners.get().get(0);
		BlockPos foqFar = corners.get().get(1);
		BlockPos foqCorner = corners.get().get(2);
		BlockPos farCorner = corners.get().get(3);
		Direction facing = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection().getOpposite();
		if (prevPos != null) {
			if (hasAllStrips()) {
				hasRing.set(true);
				prevPos = null;
				isFinished.set(false);
				heightShiftMiner = 1;
				widthShiftMiner = 0;
				lengthShiftMiner = 0;
				quarryPowerUsage.set(0.0);
				setupPowerUsage.set(0.0);
				BlockPos cornerStart = corners.get().get(3);
				BlockPos cornerEnd = corners.get().get(0);
				int deltaW = (int) Math.signum(cornerStart.getX() - cornerEnd.getX());
				int deltaL = (int) Math.signum(cornerStart.getZ() - cornerEnd.getZ());
				miningPos.set(new BlockPos(cornerStart.getX() - deltaW, cornerStart.getY() - heightShiftMiner, cornerStart.getZ() - deltaL));
				prevMiningPos.set(new BlockPos(miningPos.get()));
			}
		}
		switch (facing) {
		case EAST:
			if (!hasBottomStrip) {
				stripWithCorners(world, foqCorner, frontOfQuarry, foqCorner.getZ(), frontOfQuarry.getZ(), Direction.SOUTH, Direction.EAST, cornerState, false, false);
				return;
			}
			if (!hasTopStrip) {
				stripWithCorners(world, farCorner, foqFar, farCorner.getZ(), foqFar.getZ(), Direction.SOUTH, Direction.WEST, cornerState, false, true);
				return;
			}
			if (!hasLeftStrip) {
				strip(world, foqCorner, farCorner.getX(), Direction.EAST, Direction.SOUTH, true, true);
				return;
			}
			if (!hasRightStrip) {
				strip(world, frontOfQuarry, foqFar.getX(), Direction.EAST, Direction.NORTH, true, false);
			}
			break;
		case WEST:
			if (!hasBottomStrip) {
				stripWithCorners(world, foqCorner, frontOfQuarry, foqCorner.getZ(), frontOfQuarry.getZ(), Direction.NORTH, Direction.WEST, cornerState, false, false);
				return;
			}
			if (!hasTopStrip) {
				stripWithCorners(world, farCorner, foqFar, foqCorner.getZ(), frontOfQuarry.getZ(), Direction.NORTH, Direction.EAST, cornerState, false, true);
				return;
			}
			if (!hasLeftStrip) {
				strip(world, foqCorner, farCorner.getX(), Direction.WEST, Direction.NORTH, true, true);
				return;
			}
			if (!hasRightStrip) {
				strip(world, frontOfQuarry, foqFar.getX(), Direction.WEST, Direction.SOUTH, true, false);
			}
			break;
		case SOUTH:
			if (!hasBottomStrip) {
				stripWithCorners(world, foqCorner, frontOfQuarry, foqCorner.getX(), frontOfQuarry.getX(), Direction.WEST, Direction.SOUTH, cornerState, true, false);
				return;
			}
			if (!hasTopStrip) {
				stripWithCorners(world, farCorner, foqFar, farCorner.getX(), foqFar.getX(), Direction.WEST, Direction.NORTH, cornerState, true, true);
				return;
			}
			if (!hasLeftStrip) {
				strip(world, foqCorner, farCorner.getZ(), Direction.SOUTH, Direction.WEST, false, true);
				return;
			}
			if (!hasRightStrip) {
				strip(world, frontOfQuarry, foqFar.getZ(), Direction.SOUTH, Direction.EAST, false, false);
			}
			break;
		case NORTH:
			if (!hasBottomStrip) {
				stripWithCorners(world, foqCorner, frontOfQuarry, foqCorner.getX(), frontOfQuarry.getX(), Direction.EAST, Direction.NORTH, cornerState, true, false);
				return;
			}
			if (!hasTopStrip) {
				stripWithCorners(world, farCorner, foqFar, farCorner.getX(), foqFar.getX(), Direction.EAST, Direction.SOUTH, cornerState, true, true);
				return;
			}
			if (!hasLeftStrip) {
				strip(world, foqCorner, farCorner.getZ(), Direction.NORTH, Direction.EAST, false, true);
				return;
			}
			if (!hasRightStrip) {
				strip(world, frontOfQuarry, foqFar.getZ(), Direction.NORTH, Direction.WEST, false, false);
			}
			break;
		default:
			break;
		}
		
	}

	private void stripWithCorners(Level world, BlockPos startPos, BlockPos endPos, int startCV, int endCV, Direction relative, Direction frameFace, BlockState cornerState, boolean currPosX, boolean top) {
		if (currPos == null) {
			currPos = startPos;
		}
		if ((currPosX ? currPos.getX() : currPos.getZ()) == startCV) {
			world.setBlockAndUpdate(startPos, cornerState);
			repairedFrames.add(startPos);
			prevIsCorner = true;
		} else if ((currPosX ? currPos.getX() : currPos.getZ()) == endCV) {
			world.setBlockAndUpdate(endPos, cornerState);
			repairedFrames.add(endPos);
			if (top) {
				hasTopStrip = true;
			} else {
				hasBottomStrip = true;
			}
			prevPos = new BlockPos(currPos.getX(), currPos.getY(), currPos.getZ());
			prevIsCorner = true;
			currPos = null;
			return;
		} else {
			world.setBlockAndUpdate(currPos, ElectrodynamicsBlocks.blockFrame.defaultBlockState().setValue(GenericEntityBlock.FACING, frameFace).setValue(BlockStateProperties.WATERLOGGED, Boolean.FALSE));
			repairedFrames.add(currPos);
			prevIsCorner = false;
		}
		world.playSound(null, currPos, SoundEvents.ANVIL_PLACE, SoundSource.BLOCKS, 0.5F, 1.0F);
		prevPos = new BlockPos(currPos.getX(), currPos.getY(), currPos.getZ());
		currPos = currPos.relative(cornerOnRight.get() ? relative.getOpposite() : relative);
	}

	private void strip(Level world, BlockPos startPos, int endCV, Direction relative, Direction frameFace, boolean currPosX, boolean left) {
		if (currPos == null) {
			currPos = startPos.relative(relative);
		}
		world.setBlockAndUpdate(currPos, ElectrodynamicsBlocks.blockFrame.defaultBlockState().setValue(GenericEntityBlock.FACING, cornerOnRight.get() ? frameFace.getOpposite() : frameFace).setValue(BlockStateProperties.WATERLOGGED, Boolean.FALSE));
		repairedFrames.add(currPos);
		world.playSound(null, currPos, SoundEvents.ANVIL_PLACE, SoundSource.BLOCKS, 0.5F, 1.0F);
		prevPos = new BlockPos(currPos.getX(), currPos.getY(), currPos.getZ());
		prevIsCorner = false;
		currPos = currPos.relative(relative);
		if ((currPosX ? currPos.getX() : currPos.getZ()) == endCV) {
			currPos = null;
			if (left) {
				hasLeftStrip = true;
			} else {
				hasRightStrip = true;
			}
		}
	}

	private void checkComponents() {
		ComponentDirection quarryDir = getComponent(ComponentType.Direction);
		Direction facing = quarryDir.getDirection().getOpposite();
		Level world = getLevel();
		BlockPos machinePos = getBlockPos();
		Direction left = facing.getCounterClockWise();
		Direction right = facing.getClockWise();
		BlockEntity leftEntity = world.getBlockEntity(machinePos.relative(left));
		BlockEntity rightEntity;
		BlockEntity aboveEntity;
		if (leftEntity != null && leftEntity instanceof TileMotorComplex complexin && ((ComponentDirection) complexin.getComponent(ComponentType.Direction)).getDirection() == left) {
			rightEntity = world.getBlockEntity(machinePos.relative(right));
			if (rightEntity != null && rightEntity instanceof TileSeismicRelay relayin && ((ComponentDirection) relayin.getComponent(ComponentType.Direction)).getDirection() == quarryDir.getDirection()) {
				corners.set(relayin.markerLocs);
				corners.forceDirty();
				cornerOnRight.set(relayin.cornerOnRight);
				relay = relayin;
				aboveEntity = world.getBlockEntity(machinePos.above());
				if (aboveEntity != null && aboveEntity instanceof TileCoolantResavoir resavoirin) {
					hasComponents.set(true);
					complex = complexin;
					resavoir = resavoirin;
				} else {
					hasComponents.set(false);
				}
			} else {
				hasComponents.set(false);
			}
		} else if (leftEntity != null && leftEntity instanceof TileSeismicRelay relayin && ((ComponentDirection) relayin.getComponent(ComponentType.Direction)).getDirection() == quarryDir.getDirection()) {
			corners.set(relayin.markerLocs);
			corners.forceDirty();
			cornerOnRight.set(relayin.cornerOnRight);
			relay = relayin;
			rightEntity = world.getBlockEntity(machinePos.relative(right));
			if (rightEntity != null && rightEntity instanceof TileMotorComplex complexin && ((ComponentDirection) complexin.getComponent(ComponentType.Direction)).getDirection() == right) {
				aboveEntity = world.getBlockEntity(machinePos.above());
				if (aboveEntity != null && aboveEntity instanceof TileCoolantResavoir resavoirin) {
					hasComponents.set(true);
					complex = complexin;
					resavoir = resavoirin;
				} else {
					hasComponents.set(false);
				}
			} else {
				hasComponents.set(false);
			}
		} else {
			hasComponents.set(false);
			complex = null;
			resavoir = null;
			relay = null;
		}
	}

	private boolean areComponentsNull() {
		return complex == null || resavoir == null || relay == null;
	}

	private boolean hasAllStrips() {
		return hasBottomStrip && hasTopStrip && hasLeftStrip && hasRightStrip;
	}

	public boolean hasCorners() {
		return corners.get().size() > 3;
	}

	private static boolean skipBlock(BlockState state) {
		return state.isAir() || !state.getFluidState().is(Fluids.EMPTY) || state.is(Blocks.BEDROCK);
	}

	@Override
	public void saveAdditional(CompoundTag compound) {
		super.saveAdditional(compound);

		compound.putBoolean("bottomStrip", hasBottomStrip);
		compound.putBoolean("topStrip", hasTopStrip);
		compound.putBoolean("leftStrip", hasLeftStrip);
		compound.putBoolean("rightStrip", hasRightStrip);

		if (currPos != null) {
			compound.putInt("currX", currPos.getX());
			compound.putInt("currY", currPos.getY());
			compound.putInt("currZ", currPos.getZ());
		}
		if (prevPos != null) {
			compound.putInt("prevX", prevPos.getX());
			compound.putInt("prevY", prevPos.getY());
			compound.putInt("prevZ", prevPos.getZ());
		}

		compound.putBoolean("prevIsCorner", prevIsCorner);
		compound.putBoolean("lastIsCorner", lastIsCorner);

		compound.putBoolean("hasDecayed", hasHandledDecay);

		compound.putBoolean("areaClear", false);

		compound.putInt("heightShiftCA", heightShiftCA);
		compound.putInt("widthShiftCA", widthShiftCA);
		compound.putInt("tickDelayCA", tickDelayCA);

		compound.putInt("lengthShiftMiner", lengthShiftMiner);
		compound.putInt("heightShiftMiner", heightShiftMiner);
		compound.putInt("widthShiftMiner", widthShiftMiner);
		compound.putInt("tickDelayMiner", tickDelayMiner);

		compound.putBoolean("lengthReverse", lengthReverse);
		compound.putBoolean("widthReverse", widthReverse);

		compound.putInt("widthShiftCR", widthShiftCR);

		compound.putInt("widthShiftMaintainMining", widthShiftMaintainMining);

		if (placedBy != null) {
			compound.putUUID("placedBy", placedBy);
		}

		compound.putBoolean("continue", cont);
		int i = 0;
		for(Entry<BlockPos, BlockState> entry : brokenFrames.entrySet()) {
			compound.put("brokenframe" + i, NbtUtils.writeBlockPos(entry.getKey()));
			BlockFrame.writeToNbt(compound, "brokenstate" + i, entry.getValue());
			i++;
		}
		compound.putInt("brokenframecount", i);
		
		i = 0;
		for(BlockPos pos : repairedFrames) {
			compound.put("repairedframe" + i, NbtUtils.writeBlockPos(pos));
			i++;
		}
		compound.putInt("repairedframecount", i);
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);

		hasBottomStrip = compound.getBoolean("bottomStrip");
		hasTopStrip = compound.getBoolean("topStrip");
		hasLeftStrip = compound.getBoolean("leftStrip");
		hasRightStrip = compound.getBoolean("rightStrip");

		if (compound.contains("currX")) {
			currPos = new BlockPos(compound.getInt("currX"), compound.getInt("currY"), compound.getInt("currZ"));
		} else {
			currPos = null;
		}

		if (compound.contains("prevY")) {
			prevPos = new BlockPos(compound.getInt("prevX"), compound.getInt("prevY"), compound.getInt("prevZ"));
		} else {
			prevPos = null;
		}

		prevIsCorner = compound.getBoolean("prevIsCorner");
		lastIsCorner = compound.getBoolean("lastIsCorner");

		hasHandledDecay = compound.getBoolean("hasDecayed");

		isAreaCleared = compound.getBoolean("areaClear");

		heightShiftCA = compound.getInt("heightShiftCA");
		widthShiftCA = compound.getInt("widthShiftCA");
		tickDelayCA = compound.getInt("tickDelayCA");

		lengthShiftMiner = compound.getInt("lengthShiftMiner");
		heightShiftMiner = compound.getInt("heightShiftMiner");
		widthShiftMiner = compound.getInt("widthShiftMiner");
		tickDelayMiner = compound.getInt("tickDelayMiner");

		lengthReverse = compound.getBoolean("lengthReverse");
		widthReverse = compound.getBoolean("widthReverse");

		widthShiftCR = compound.getInt("widthShiftCR");

		widthShiftMaintainMining = compound.getInt("widthShiftMaintainMining");

		if (compound.contains("placedBy")) {
			placedBy = compound.getUUID("placedBy");
		}

		cont = compound.getBoolean("continue");
		
		int brokenSize = compound.getInt("brokenframecount");
		for(int i = 0; i < brokenSize; i++) {
			BlockPos pos = NbtUtils.readBlockPos(compound.getCompound("brokenframe" + i));
			BlockState state = BlockFrame.readFromNbt(compound.getCompound("brokenstate" + i));
			brokenFrames.put(pos, state);
		}
		
		int repairSize = compound.getInt("repairedframecount");
		for(int i = 0; i < repairSize; i++) {
			repairedFrames.add(NbtUtils.readBlockPos(compound.getCompound("repairedframe" + i)));
		}
		

	}

	@Override
	public void setRemoved() {
		if (getLevel().isClientSide) {
			HandlerQuarryArm.removeRenderData(getBlockPos());
		}
		super.setRemoved();
	}
	
	@Override
	public void onBlockDestroyed() {
		handleFramesDecayNoVarUpdate();
	}

	// it is assumed that the block has marker corners when you call this method
	public void handleFramesDecay() {
		miningPos.set(OUT_OF_REACH);
		prevMiningPos.set(OUT_OF_REACH);
		hasHandledDecay = true;
		isAreaCleared = false;
		hasRing.set(false);
		hasBottomStrip = false;
		hasTopStrip = false;
		hasLeftStrip = false;
		hasRightStrip = false;
		lengthReverse = false;
		widthReverse = false;
		handleFramesDecayNoVarUpdate();
	}
	
	public void handleFramesDecayNoVarUpdate() {
		Level world = getLevel();
		BlockPos frontOfQuarry = corners.get().get(0);
		BlockPos foqFar = corners.get().get(1);
		BlockPos foqCorner = corners.get().get(2);
		BlockPos farCorner = corners.get().get(3);
		for (BlockPos pos : corners.get()) {
			world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
		}
		BlockPos.betweenClosedStream(foqCorner, frontOfQuarry).forEach(pos -> updateState(world, pos));
		BlockPos.betweenClosedStream(farCorner, foqFar).forEach(pos -> updateState(world, pos));
		BlockPos.betweenClosedStream(foqCorner, farCorner).forEach(pos -> updateState(world, pos));
		BlockPos.betweenClosedStream(frontOfQuarry, foqFar).forEach(pos -> updateState(world, pos));
	}

	private static void updateState(Level world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		if (state.is(ElectrodynamicsBlocks.blockFrame) || state.is(ElectrodynamicsBlocks.blockFrameCorner)) {
			world.setBlockAndUpdate(pos, state.setValue(ElectrodynamicsBlockStates.QUARRY_FRAME_DECAY, Boolean.TRUE));
		}
	}

	@Override
	public void setPlayer(LivingEntity player) {
		placedBy = player == null ? null : player.getUUID();
	}

	@Override
	public UUID getPlayerID() {
		return placedBy;
	}

	@Nullable
	private FakePlayer getPlayer(ServerLevel world) {
		if (placedBy == null) {
			return null;
		}
		Player player = world.getPlayerByUUID(placedBy);
		if (player != null) {
			return FakePlayerFactory.get(world, player.getGameProfile());
		}
		return null;
	}
	
	@Override
	public void onInventoryChange(ComponentInventory inv, int slot) {
		super.onInventoryChange(inv, slot);
		if(inv.getUpgradeContents().size() > 0 && (slot >= inv.getUpgradeSlotStartIndex() || slot == -1)) {
			double quarryPowerMultiplier = 0;
			int silkTouchLevel = 0;
			int fortuneLevel = 0;
			int unbreakingLevel = 0;
			boolean hasItemVoid = false;
			for (ItemStack stack : inv.getUpgradeContents()) {
				if (!stack.isEmpty()) {
					ItemUpgrade upgrade = (ItemUpgrade) stack.getItem();
					for (int i = 0; i < stack.getCount(); i++) {
						switch (upgrade.subtype) {
						case itemvoid:
							hasItemVoid = true;
							if (quarryPowerMultiplier < 1) {
								quarryPowerMultiplier = 1;
							}
							break;
						case silktouch:
							if (fortuneLevel == 0 && silkTouchLevel < 1) {
								silkTouchLevel++;
							}
							if (quarryPowerMultiplier < 1) {
								quarryPowerMultiplier = 1;
							}
							quarryPowerMultiplier = Math.min(quarryPowerMultiplier *= 4, CAPACITY / 10);
							break;
						case fortune:
							if (silkTouchLevel == 0 && fortuneLevel < 3) {
								fortuneLevel++;
							}
							if (quarryPowerMultiplier < 1) {
								quarryPowerMultiplier = 1;
							}
							quarryPowerMultiplier = Math.min(quarryPowerMultiplier *= 2, CAPACITY / 10);
							break;
						case unbreaking:
							if (quarryPowerMultiplier < 1) {
								quarryPowerMultiplier = 1;
							}
							if (unbreakingLevel < 3) {
								unbreakingLevel++;
							}
							quarryPowerMultiplier = Math.min(quarryPowerMultiplier *= 1.5, CAPACITY / 10);
							break;
						default:
							break;
						}
					}
				}
			}
			this.hasItemVoid.set(hasItemVoid);
			this.fortuneLevel.set(fortuneLevel);
			this.silkTouchLevel.set(silkTouchLevel);
			this.unbreakingLevel.set(unbreakingLevel);
			quarryPowerUsage.set(Constants.QUARRY_USAGE_PER_TICK * quarryPowerMultiplier);
		}
	}
	
	private void writeHeadType(SubtypeDrillHead head) {
		CompoundTag tag = new CompoundTag();
		if(head != null) {
			tag.putString("head", head.name());
		}
		currHead.set(tag);
	}
	
	@Nullable
	public SubtypeDrillHead readHeadType() {
		return currHead.get().contains("head") ? SubtypeDrillHead.valueOf(currHead.get().getString("head")) : null;
	}
	
	public boolean isMotorComplexPowered() {
		ComponentDirection quarryDir = getComponent(ComponentType.Direction);
		Direction facing = quarryDir.getDirection().getOpposite();
		BlockEntity entity;
		if(cornerOnRight.get()) {
			entity = level.getBlockEntity(getBlockPos().relative(facing.getCounterClockWise()));
		} else {
			entity = level.getBlockEntity(getBlockPos().relative(facing.getClockWise()));
		}
		if(entity != null && entity instanceof TileMotorComplex complex) {
			return complex.isPowered.get();
		}
		
		return false;
	}

}
