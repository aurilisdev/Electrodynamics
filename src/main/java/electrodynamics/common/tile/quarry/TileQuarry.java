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

import com.mojang.datafixers.util.Pair;

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
import electrodynamics.prefab.utilities.math.PrecisionVector;
import electrodynamics.prefab.utilities.object.Location;
import electrodynamics.prefab.utilities.object.QuarryArmDataHolder;
import electrodynamics.prefab.utilities.object.QuarryArmFrameWrapper;
import electrodynamics.prefab.utilities.object.QuarryWheelDataHolder;
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
	
	//these values are used to deal with client tick desync and provide more complex information 
	//on how the quarry should be rendered
	public final Property<Integer> speed;
	public final Property<Integer> progressCounter;
	public final Property<Boolean> running;

	private int widthShiftMaintainMining = 0;
	private boolean cont = false;
	
	//Client Parameters
	private QuarryArmFrameWrapper currentFrame = null;

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
		speed = property(new Property<Integer>(PropertyType.Integer, "speed", 0));
		progressCounter = property(new Property<Integer>(PropertyType.Integer, "progresscounter", 0));
		running = property(new Property<Boolean>(PropertyType.Boolean, "isrunning", false));
		
		addComponent(new ComponentDirection());
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentTickable().tickServer(this::tickServer).tickClient(this::tickClient));
		addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.DOWN).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 2).maxJoules(Constants.QUARRY_USAGE_PER_TICK * CAPACITY));
		addComponent(new ComponentInventory(this).size(19).inputs(7).outputs(9).upgrades(3).validUpgrades(ContainerQuarry.VALID_UPGRADES).valid(machineValidator()));
		addComponent(new ComponentContainerProvider(SubtypeMachine.quarry).createMenu((id, player) -> new ContainerQuarry(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}

	private void tickServer(ComponentTickable tick) {
		if(isFinished.get()) {
			running.set(false);
			if(!hasHandledDecay) {
				handleFramesDecay();
				hasHandledDecay = true;
			}
			return;
		}
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
			running.set(false);
			return;
		}
		
		//return if the quarry still does not have components
		if(!hasComponents.get()) {
			running.set(false);
			return;
		}
		
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
			running.set(false);
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
		
		//if the quarry components are invalid, return
		if(areComponentsNull()) {
			running.set(false);
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
			running.set(false);
			return;
		}
		
		//if the motor complex is in an invalid state return
		if(!complex.isPowered.get() || complex.speed.get() <= 0) {
			running.set(false);
			return;
		}
		
		
		int fluidUse = (int) (complex.powerMultiplier.get() * Constants.QUARRY_WATERUSAGE_PER_BLOCK);
		ComponentInventory inv = getComponent(ComponentType.Inventory);
		if(inv.getItem(0).getItem() instanceof ItemDrillHead head) {
			hasHead.set(true);
			writeHeadType(head.head);
		} else {
			hasHead.set(false);
			writeHeadType(null);
			running.set(false);
		}
		
		running.set(true);
		progressCounter.set(progressCounter.get() + 1);
		
		//if there is no room for mined blocks, the fluid resavoir doesn't have enough fluid, or there isn't a drill head, return
		if(!inv.areOutputsEmpty() || !resavoir.hasEnoughFluid(fluidUse) || !hasHead.get() || progressCounter.get() < speed.get()) {
			return;
		}
		
		progressCounter.set(0);
		
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
		boolean shouldSkip = true;
		
		//if the mined block shouldn't be skipped then don't skip it
		if(!cont) {
			return;
		}
		//loop until either the mining skip limit is reached or a non-skipable block is found
		while (shouldSkip) {
			if (miningPos.get().getY() <= world.getMinBuildHeight()) {
				heightShiftMiner = 1;
				isFinished.set(true);
				progressCounter.set(0);
				running.set(false);
				break;
			}
			if (lengthReverse ? lengthShiftMiner == 0 : lengthShiftMiner == length) {
				lengthReverse = !lengthReverse;
				if (widthReverse ? widthShiftMiner == 0 : widthShiftMiner == width) {
					widthReverse = !widthReverse;
					heightShiftMiner++;
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
					if (miningPos.get().getY() <= world.getMinBuildHeight()) {
						heightShiftMiner = 1;
						isFinished.set(true);
						progressCounter.set(0);
						running.set(false);
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
				int newWidthShift = -1 * (pos.getX() - (cornerStart.getX() - deltaW));
				int newLengthShift = -1 * (pos.getZ() - (cornerStart.getZ() - deltaL));
				widthShiftMiner = newWidthShift;
				lengthShiftMiner = newLengthShift;
				widthShiftMaintainMining = 0;
				heightShiftMiner = startPos.getY() - pos.getY() - 1;
				prevMiningPos.set(new BlockPos(miningPos.get()));
				miningPos.set(pos);
				widthReverse = false;
				lengthReverse = false;
				progressCounter.set(0);
				return;
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
			if (!head.isUnbreakable) {
				int durabilityUsed = (int) (Math.ceil(strength) / (unbreakingLevel.get() + 1.0F));
				if (drillHead.getDamageValue() + durabilityUsed >= drillHead.getMaxDamage()) {
					world.playSound(null, getBlockPos(), SoundEvents.ITEM_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
					drillHead.shrink(1);
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
		isChanged = true;
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
		isChanged = true;
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

	private boolean skipBlock(BlockState state) {
		return state.isAir() || !state.getFluidState().is(Fluids.EMPTY) || state.is(Blocks.BEDROCK) || (miningPos.get().getY() == level.getMinBuildHeight());
	}
	
	private static final double X_ARM_SEGMENT_LENGTH = 1;
	private static final double Y_ARM_SEGMENT_LENGTH = 1;
	private static final double Z_ARM_SEGMENT_LENGTH = 1;

	private void tickClient(ComponentTickable tick) {
		
		BlockPos pos = getBlockPos();
		HandlerQuarryArm.removeRenderData(getBlockPos());
		if(!hasCorners() || miningPos.get().equals(OUT_OF_REACH)) {
			return;
		}
		currentFrame = getCurrentFrame(tick.getTicks());
		if(currentFrame == null) {
			return;
		}
		
		//loc.add(-0.5,0,-0.5);
		BlockPos start = corners.get().get(3);
		BlockPos end = corners.get().get(0);

		double widthLeft, widthRight, widthTop, widthBottom;
		AABB headAabb = null;
		PrecisionVector headPos = null;
		
		QuarryWheelDataHolder leftWheel = null;
		QuarryWheelDataHolder rightWheel = null;
		QuarryWheelDataHolder topWheel = null;
		QuarryWheelDataHolder bottomWheel = null;

		List<Pair<PrecisionVector, AABB>> lightSegments = new ArrayList<>();
		List<Pair<PrecisionVector, AABB>> darkSegments = new ArrayList<>();
		List<Pair<PrecisionVector, AABB>> titanium = new ArrayList<>();

		double x = currentFrame.frame().x();
		double z = currentFrame.frame().z();
		double y = start.getY() + 0.5;
		
		/* Vertical Arm Segment */

		double deltaY = start.getY() - currentFrame.frame().y() - 1.2;
		
		vertical(x, y, z, deltaY, darkSegments, lightSegments, titanium);
		
		SubtypeDrillHead headType = readHeadType();
		
		if(headType != null) {
			headPos = new PrecisionVector(x, y - deltaY, z);
			headAabb = new AABB(0.3125, -0.2, 0.3125, 0.6875, -0.5 - 0.2, 0.6875);
		}

		/* Horizontal Arm Segment */
		
		Direction facing = ((ComponentDirection) getComponent(ComponentType.Direction)).getDirection().getOpposite();
		
		int wholeWidthLeft, wholeWidthRight, wholeWidthTop, wholeWidthBottom;
		double remainderWidthLeft, remainderWidthRight, remainderWidthTop, remainderWidthBottom;
		
		int[] markerLineSigns = new int[4];
		
		switch (facing) {
		
		case NORTH, SOUTH:

			widthLeft = x - start.getX();
			widthRight = x - end.getX();
			widthTop = z - start.getZ();
			widthBottom = z - end.getZ();

			wholeWidthLeft = (int) (widthLeft / X_ARM_SEGMENT_LENGTH);
			remainderWidthLeft = widthLeft - wholeWidthLeft;
			
			wholeWidthRight = (int) (widthRight / X_ARM_SEGMENT_LENGTH);
			remainderWidthRight = widthRight - wholeWidthRight;
			
			wholeWidthBottom = (int) (widthBottom / Z_ARM_SEGMENT_LENGTH);
			remainderWidthBottom = widthBottom - wholeWidthBottom;
			
			wholeWidthTop = (int) (widthTop / Z_ARM_SEGMENT_LENGTH);
			remainderWidthTop = widthTop - wholeWidthTop;
			
			
			if (facing == Direction.SOUTH) {
				south(x, y, z, darkSegments, lightSegments, widthLeft, widthRight, widthTop, widthBottom, wholeWidthLeft, wholeWidthRight, wholeWidthTop, wholeWidthBottom, remainderWidthLeft, remainderWidthRight, remainderWidthTop, remainderWidthBottom);
			} else {
				
				if(cornerOnRight.get()) {
					leftWheel = new QuarryWheelDataHolder(new PrecisionVector(x - widthLeft + 0.5, y, z + 0.5), 180, currentFrame.degrees() * -currentFrame.deltaZ(), 0);
					
					rightWheel = new QuarryWheelDataHolder(new PrecisionVector(x - widthRight + 0.5, y, z + 0.5), 0, currentFrame.degrees() * currentFrame.deltaZ(), 0);
					
				} else {
					leftWheel = new QuarryWheelDataHolder(new PrecisionVector(x - widthLeft + 0.5, y, z + 0.5), 0, currentFrame.degrees() * currentFrame.deltaZ(), 0);
					
					rightWheel = new QuarryWheelDataHolder(new PrecisionVector(x - widthRight + 0.5, y, z + 0.5), 180, currentFrame.degrees() * -currentFrame.deltaZ(), 0);
					
				}
				
				bottomWheel = new QuarryWheelDataHolder(new PrecisionVector(x + 0.5, y, z - widthBottom + 0.5), 90, currentFrame.degrees() * currentFrame.deltaX(), 0);
				
				topWheel = new QuarryWheelDataHolder(new PrecisionVector(x + 0.5, y, z - widthTop + 0.5), 270, currentFrame.degrees() * -currentFrame.deltaX(), 0);
				
				north(x, y, z, darkSegments, lightSegments, widthLeft, widthRight, widthTop, widthBottom, wholeWidthLeft, wholeWidthRight, wholeWidthTop, wholeWidthBottom, remainderWidthLeft, remainderWidthRight, remainderWidthTop, remainderWidthBottom);	
			
				markerLineSigns = new int[] {1, 1, -1, -1};
			
			}
			
			break;
		case EAST, WEST:

			widthLeft = z - start.getZ();
			widthRight = z - end.getZ();
			widthTop = x - start.getX();
			widthBottom = x - end.getX();
			
			wholeWidthLeft = (int) (widthLeft / Z_ARM_SEGMENT_LENGTH);
			remainderWidthLeft = widthLeft - wholeWidthLeft;
			
			wholeWidthRight = (int) (widthRight / Z_ARM_SEGMENT_LENGTH);
			remainderWidthRight = widthRight - wholeWidthRight;
			
			wholeWidthBottom = (int) (widthBottom / X_ARM_SEGMENT_LENGTH);
			remainderWidthBottom = widthBottom - wholeWidthBottom;
			
			wholeWidthTop = (int) (widthTop / X_ARM_SEGMENT_LENGTH);
			remainderWidthTop = widthTop - wholeWidthTop;
			
			if (facing == Direction.WEST) {
				//west(x, y, z, darkSegments, lightSegments, widthLeft, widthRight, widthTop, widthBottom, wholeWidthLeft, wholeWidthRight, wholeWidthTop, wholeWidthBottom, remainderWidthLeft, remainderWidthRight, remainderWidthTop, remainderWidthBottom);
			} else {
				//east(x, y, z, darkSegments, lightSegments, widthLeft, widthRight, widthTop, widthBottom, wholeWidthLeft, wholeWidthRight, wholeWidthTop, wholeWidthBottom, remainderWidthLeft, remainderWidthRight, remainderWidthTop, remainderWidthBottom);
			}
			
			break;
		default:
			break;
		}
		HandlerQuarryArm.addRenderData(pos, new QuarryArmDataHolder(lightSegments, darkSegments, titanium, Pair.of(headPos, headAabb), headType, leftWheel, rightWheel, topWheel, bottomWheel, running.get(), progressCounter.get() , speed.get(), corners.get(), markerLineSigns));
		
	}
	
	private void vertical(double x, double y, double z, double deltaY, List<Pair<PrecisionVector, AABB>> darkSegments, List<Pair<PrecisionVector, AABB>> lightSegments, List<Pair<PrecisionVector, AABB>> titanium) {
		int i = 0;
		/* Vertical Arm Segment */

		PrecisionVector pos = null;
		int wholeSegmentCount = (int) (deltaY / Y_ARM_SEGMENT_LENGTH);
		double remainder = deltaY / Y_ARM_SEGMENT_LENGTH - wholeSegmentCount;
		for (i = 0; i < wholeSegmentCount; i++) {
			pos = new PrecisionVector(x, y - i * Y_ARM_SEGMENT_LENGTH - 0.3125, z);
			//vertical lines
			lightSegments.add(Pair.of(pos, new AABB(0.25, 0, 0.25, 0.3125, -Y_ARM_SEGMENT_LENGTH, 0.3125)));
			lightSegments.add(Pair.of(pos, new AABB(0.25, 0, 0.6875, 0.3125, -Y_ARM_SEGMENT_LENGTH, 0.75)));
			lightSegments.add(Pair.of(pos, new AABB(0.6875, 0, 0.25, 0.75, -Y_ARM_SEGMENT_LENGTH, 0.3125)));
			lightSegments.add(Pair.of(pos, new AABB(0.6875, 0, 0.6875, 0.75, -Y_ARM_SEGMENT_LENGTH, 0.75)));
			
			//horizontal lines
			lightSegments.add(Pair.of(pos, new AABB(0.25, -0.5 - 0.03125, 0.3125, 0.3125, -0.5 + 0.03125, 0.6875)));
			lightSegments.add(Pair.of(pos, new AABB(0.3125, -0.5 - 0.03125, 0.6875, 0.6875, -0.5 + 0.03125, 0.75)));
			lightSegments.add(Pair.of(pos, new AABB(0.6875, -0.5 - 0.03125, 0.3125, 0.75, -0.5  + 0.03125, 0.6875)));
			lightSegments.add(Pair.of(pos, new AABB(0.3125, -0.5 - 0.03125, 0.25, 0.6875, -0.5  + 0.03125, 0.3125)));
			
			//cylinder
			darkSegments.add(Pair.of(pos, new AABB(0.375, 0, 0.375, 0.625, -Y_ARM_SEGMENT_LENGTH, 0.625)));
		}
		int wholeOffset = (int) (wholeSegmentCount * Y_ARM_SEGMENT_LENGTH);
		pos = new PrecisionVector(x, y - wholeOffset, z);
		//vertical lines
		lightSegments.add(Pair.of(pos, new AABB(0.25, -0.3125, 0.25, 0.3125, -Y_ARM_SEGMENT_LENGTH * remainder, 0.3125)));
		lightSegments.add(Pair.of(pos, new AABB(0.25, -0.3125, 0.6875, 0.3125, -Y_ARM_SEGMENT_LENGTH * remainder, 0.75)));
		lightSegments.add(Pair.of(pos, new AABB(0.6875, -0.3125, 0.25, 0.75, -Y_ARM_SEGMENT_LENGTH * remainder, 0.3125)));
		lightSegments.add(Pair.of(pos, new AABB(0.6875, -0.3125, 0.6875, 0.75, -Y_ARM_SEGMENT_LENGTH * remainder, 0.75)));
		
		//horizontal lines
		if(remainder > 0.5) {
			lightSegments.add(Pair.of(pos, new AABB(0.25, -0.5 - 0.03125, 0.3125, 0.3125, -0.5  + 0.03125, 0.6875)));
			lightSegments.add(Pair.of(pos, new AABB(0.3125, -0.5 - 0.03125, 0.6875, 0.6875, -0.5 + 0.03125, 0.75)));
			lightSegments.add(Pair.of(pos, new AABB(0.6875, -0.5 - 0.03125, 0.3125, 0.75, -0.5  + 0.03125, 0.6875)));
			lightSegments.add(Pair.of(pos, new AABB(0.3125, -0.5 - 0.03125, 0.25, 0.6875, -0.5  + 0.03125, 0.3125)));
		}
		
		//cylinder
		darkSegments.add(Pair.of(pos, new AABB(0.375, -0.3125, 0.375, 0.625, -Y_ARM_SEGMENT_LENGTH * remainder, 0.625)));

		//titanium.add(Pair.of(new PrecisionVector(x, y, z), new AABB(0.1875, 0.325, 0.1875, 0.8125, -0.325, 0.8125)));
		titanium.add(Pair.of(new PrecisionVector(x, y - deltaY, z), new AABB(0.20, 0, 0.20, 0.8, -0.2, 0.8)));
	}
	
	private void north(double x, double y, double z, List<Pair<PrecisionVector, AABB>> darkSegments, List<Pair<PrecisionVector, AABB>> lightSegments, double widthLeft, double widthRight, double widthTop, double widthBottom, int wholeWidthLeft, int wholeWidthRight, int wholeWidthTop, int wholeWidthBottom, double remainderWidthLeft, double remainderWidthRight, double remainderWidthTop, double remainderWidthBottom) {
		
		if(cornerOnRight.get()) {
			northOnRight(x, y, z, darkSegments, lightSegments, widthLeft, widthRight, wholeWidthLeft, wholeWidthRight, remainderWidthLeft, remainderWidthRight);
		} else {
			northOnLeft(x, y, z, darkSegments, lightSegments, widthLeft, widthRight, wholeWidthLeft, wholeWidthRight, remainderWidthLeft, remainderWidthRight);
		}
		
		/* BOTTOM */
		
		int i = 0;
		PrecisionVector pos = null;
		int removal = remainderWidthBottom > -0.625 ? 1 : 0;
		while(i > wholeWidthBottom + removal) {
			pos = new PrecisionVector(x, y, z - widthBottom + Z_ARM_SEGMENT_LENGTH * i + 0.1875);
			//horizontal lines
			lightSegments.add(Pair.of(pos, new AABB(0.25, -0.25, 0, 0.3125, -0.1875, -Z_ARM_SEGMENT_LENGTH)));
			lightSegments.add(Pair.of(pos, new AABB(0.25, 0.1875, 0, 0.3125, 0.25, -Z_ARM_SEGMENT_LENGTH)));
			lightSegments.add(Pair.of(pos, new AABB(0.6875, -0.25, 0, 0.75, -0.1875, -Z_ARM_SEGMENT_LENGTH)));
			lightSegments.add(Pair.of(pos, new AABB(0.6875, 0.1875, 0, 0.75, 0.25, -Z_ARM_SEGMENT_LENGTH)));
			//center
			darkSegments.add(Pair.of(pos, new AABB(0.375, -0.125, 0, 0.625, 0.125, -Z_ARM_SEGMENT_LENGTH)));
			//vertical lines
			lightSegments.add(Pair.of(pos, new AABB(0.25, -0.1875, -Z_ARM_SEGMENT_LENGTH + 0.0625, 0.3125, 0.1875, -Z_ARM_SEGMENT_LENGTH + 0)));
			lightSegments.add(Pair.of(pos, new AABB(0.6875, -0.1875, -Z_ARM_SEGMENT_LENGTH + 0.0625, 0.75, 0.1875, -Z_ARM_SEGMENT_LENGTH + 0)));
			lightSegments.add(Pair.of(pos, new AABB(0.3125, -0.25, -Z_ARM_SEGMENT_LENGTH + 0.0625, 0.6875, -0.1875, -Z_ARM_SEGMENT_LENGTH + 0)));
			lightSegments.add(Pair.of(pos, new AABB(0.3125, 0.1875, -Z_ARM_SEGMENT_LENGTH + 0.0625, 0.6875, 0.25, -Z_ARM_SEGMENT_LENGTH + 0)));
			
			i--;
		}
		pos = new PrecisionVector(x, y, z);
		
		if(removal == 1) {
			//horizontal lines
			lightSegments.add(Pair.of(pos, new AABB(0.25, -0.25, -remainderWidthBottom + Z_ARM_SEGMENT_LENGTH + 0.1875, 0.3125, -0.1875, 0.8125)));
			lightSegments.add(Pair.of(pos, new AABB(0.25, 0.1875, -remainderWidthBottom + Z_ARM_SEGMENT_LENGTH + 0.1875, 0.3125, 0.25, 0.8125)));
			lightSegments.add(Pair.of(pos, new AABB(0.6875, -0.25, -remainderWidthBottom + Z_ARM_SEGMENT_LENGTH + 0.1875, 0.75, -0.1875, 0.8125)));
			lightSegments.add(Pair.of(pos, new AABB(0.6875, 0.1875, -remainderWidthBottom + Z_ARM_SEGMENT_LENGTH + 0.1875, 0.75, 0.25, 0.8125)));
			//center
			darkSegments.add(Pair.of(pos, new AABB(0.375, -0.125, -remainderWidthBottom + Z_ARM_SEGMENT_LENGTH + 0.1875, 0.625, 0.125, 0.8125)));
			//vertical lines
			if(remainderWidthBottom < -0.375) {
				pos = new PrecisionVector(x, y, z - remainderWidthBottom - Z_ARM_SEGMENT_LENGTH + 0.1875);
				lightSegments.add(Pair.of(pos, new AABB(0.25, -0.1875, 1, 0.3125, 0.1875, 1.0625)));
				lightSegments.add(Pair.of(pos, new AABB(0.6875, -0.1875, 1, 0.75, 0.1875, 1.0625)));
				lightSegments.add(Pair.of(pos, new AABB(0.3125, -0.25, 1, 0.6875, -0.1875, 1.0625)));
				lightSegments.add(Pair.of(pos, new AABB(0.3125, 0.1875, 1, 0.6875, 0.25, 1.0625)));
					
			}
		   
		} else {
			//horizontal lines
			lightSegments.add(Pair.of(pos, new AABB(0.25, -0.25, -remainderWidthBottom + 0.1875, 0.3125, -0.1875, 0.8125)));
			lightSegments.add(Pair.of(pos, new AABB(0.25, 0.1875, -remainderWidthBottom + 0.1875, 0.3125, 0.25, 0.8125)));
			lightSegments.add(Pair.of(pos, new AABB(0.6875, -0.25, -remainderWidthBottom + 0.1875, 0.75, -0.1875, 0.8125)));
			lightSegments.add(Pair.of(pos, new AABB(0.6875, 0.1875, -remainderWidthBottom + 0.1875, 0.75, 0.25, 0.8125)));
			//center
			darkSegments.add(Pair.of(pos, new AABB(0.375, -0.125, -remainderWidthBottom + 0.1875, 0.625, 0.125, 0.8125)));
			//vertical lines
		    /*
			lightSegments.add(Pair.of(pos, new AABB(0.25, -0.1875, -remainderWidthBottom - Z_ARM_SEGMENT_LENGTH + 0.46875, 0.3125, 0.1875, -remainderWidthBottom - Z_ARM_SEGMENT_LENGTH + 0.53125)));
			lightSegments.add(Pair.of(pos, new AABB(0.6875, -0.1875, -remainderWidthBottom - Z_ARM_SEGMENT_LENGTH + 0.46875, 0.75, 0.1875, -remainderWidthBottom - Z_ARM_SEGMENT_LENGTH + + 0.53125)));
			lightSegments.add(Pair.of(pos, new AABB(0.3125, -0.25, -remainderWidthBottom - Z_ARM_SEGMENT_LENGTH + 0.46875, 0.6875, -0.1875, -remainderWidthBottom - Z_ARM_SEGMENT_LENGTH + 0.53125)));
			lightSegments.add(Pair.of(pos, new AABB(0.3125, 0.1875, -remainderWidthBottom - Z_ARM_SEGMENT_LENGTH + 0.46875, 0.6875, 0.25, -remainderWidthBottom - Z_ARM_SEGMENT_LENGTH + 0.53125)));
			*/
		}
		
		/* TOP */
		
		removal = remainderWidthTop < (0.625) ? 1 : 0;
		i = 0;
		
		while(i < wholeWidthTop - removal) {
			pos = new PrecisionVector(x, y, z - widthTop + Z_ARM_SEGMENT_LENGTH * i + 0.8125);
			//horizontal lines
			lightSegments.add(Pair.of(pos, new AABB(0.25, -0.25, Z_ARM_SEGMENT_LENGTH, 0.3125, -0.1875, 0)));
			lightSegments.add(Pair.of(pos, new AABB(0.25, 0.1875, Z_ARM_SEGMENT_LENGTH, 0.3125, 0.25, 0)));
			lightSegments.add(Pair.of(pos, new AABB(0.6875, -0.25, Z_ARM_SEGMENT_LENGTH, 0.75, -0.1875, 0)));
			lightSegments.add(Pair.of(pos, new AABB(0.6875, 0.1875, Z_ARM_SEGMENT_LENGTH, 0.75, 0.25, 0)));
			//center
			darkSegments.add(Pair.of(pos, new AABB(0.3875, -0.125, Z_ARM_SEGMENT_LENGTH, 0.625, 0.125, 0)));
			//vertical lines
			lightSegments.add(Pair.of(pos, new AABB(0.25, -0.1875, 0.9375, 0.3125, 0.1875, 1)));
			lightSegments.add(Pair.of(pos, new AABB(0.6875, -0.1875, 0.9375, 0.75, 0.1875, 1)));
			lightSegments.add(Pair.of(pos, new AABB(0.3125, -0.25, 0.9375, 0.6875, -0.1875, 1)));
			lightSegments.add(Pair.of(pos, new AABB(0.3125, 0.1875, 0.9375, 0.6875, 0.25, 1)));
			
			i++;
		}
		pos = new PrecisionVector(x, y, z);
		if(removal == 1) {
			
			//horizontal lines
			lightSegments.add(Pair.of(pos, new AABB(0.25, -0.25, 0.8125 - Z_ARM_SEGMENT_LENGTH - remainderWidthTop, 0.3125, -0.1875, 0.1875)));
			lightSegments.add(Pair.of(pos, new AABB(0.6875, -0.25, 0.8125 - Z_ARM_SEGMENT_LENGTH - remainderWidthTop, 0.75, -0.1875, 0.1875)));
			lightSegments.add(Pair.of(pos, new AABB(0.25, 0.1875, 0.8125 - Z_ARM_SEGMENT_LENGTH - remainderWidthTop, 0.3125, 0.25, 0.1875)));
			lightSegments.add(Pair.of(pos, new AABB(0.6875, 0.1875, 0.8125 - Z_ARM_SEGMENT_LENGTH - remainderWidthTop, 0.75, 0.25, 0.1875)));
			//center
			darkSegments.add(Pair.of(pos, new AABB(0.3875, -0.125, 0.8125 - Z_ARM_SEGMENT_LENGTH - remainderWidthTop, 0.625, 0.125, 0.1875)));
			//horizontal lines
			if(remainderWidthTop > 0.5) {
				pos = new PrecisionVector(x, y, z - remainderWidthTop - Z_ARM_SEGMENT_LENGTH + 0.8125);
				lightSegments.add(Pair.of(pos, new AABB(0.25, -0.1875, 0.9375, 0.3125, 0.1875, 1)));
				lightSegments.add(Pair.of(pos, new AABB(0.6875, -0.1875, 0.9375, 0.75, 0.1875, 1)));
				lightSegments.add(Pair.of(pos, new AABB(0.3125, -0.25, 0.9375, 0.6875, -0.1875, 1)));
				lightSegments.add(Pair.of(pos, new AABB(0.3125, 0.1875, 0.9375, 0.6875, 0.25, 1)));
				
			}
			
		} else {
			
			//horizontal lines
			lightSegments.add(Pair.of(pos, new AABB(0.25, -0.25, 0.1875, 0.3125, -0.1875, 0.8125 - remainderWidthTop)));
			lightSegments.add(Pair.of(pos, new AABB(0.6875, -0.25, 0.1875, 0.75, -0.1875, 0.8125 - remainderWidthTop)));
			lightSegments.add(Pair.of(pos, new AABB(0.25, 0.1875, 0.1875, 0.3125, 0.25, 0.8125 - remainderWidthTop)));
			lightSegments.add(Pair.of(pos, new AABB(0.6875, 0.1875, 0.1875, 0.75, 0.25, 0.8125 - remainderWidthTop)));
			//center
			darkSegments.add(Pair.of(pos, new AABB(0.3875, -0.125, 0.1875, 0.625, 0.125, 0.8125 - remainderWidthTop)));
			//vertical lines
			/*
			darkSegments.add(Pair.of(pos, new AABB(0.25, -0.1875, -remainderWidthTop + (0.8125 + 1), 0.3125, 0.1875, -remainderWidthTop + (0.8125 + 1.0625))));
			lightSegments.add(Pair.of(pos, new AABB(0.6875, -0.1875, -remainderWidthTop + (0.8125 + 0.468765), 0.75, 0.1875, -remainderWidthTop + (0.8125 + 0.53215))));
			lightSegments.add(Pair.of(pos, new AABB(0.3125, -0.25, -remainderWidthTop + (0.8125 + 0.468765), 0.6875, -0.1875, -remainderWidthTop + (0.8125 + 0.53215))));
			lightSegments.add(Pair.of(pos, new AABB(0.3125, 0.1875, -remainderWidthTop + (0.8125 + 0.468765), 0.6875, 0.25, -remainderWidthTop + (0.8125 + 0.53215))));
			*/
		}
		
		
	}
	
	private void northOnRight(double x, double y, double z, List<Pair<PrecisionVector, AABB>> darkSegments, List<Pair<PrecisionVector, AABB>> lightSegments, double widthLeft, double widthRight, int wholeWidthLeft, int wholeWidthRight, double remainderWidthLeft, double remainderWidthRight) {
		/* LEFT */
		
		int i =  1;
		PrecisionVector pos = null;
		while(i > wholeWidthLeft + 2) {
			pos = new PrecisionVector(x - widthLeft + X_ARM_SEGMENT_LENGTH * i - 0.8125, y, z);
			//horizontal lines
			lightSegments.add(Pair.of(pos, new AABB(-X_ARM_SEGMENT_LENGTH, 0.25, 0.25, 0, 0.1875, 0.3125)));
			lightSegments.add(Pair.of(pos, new AABB(-X_ARM_SEGMENT_LENGTH, 0.25, 0.6875, 0, 0.1875, 0.75)));
			lightSegments.add(Pair.of(pos, new AABB(-X_ARM_SEGMENT_LENGTH, -0.25, 0.25, 0, -0.1875, 0.3125)));
			lightSegments.add(Pair.of(pos, new AABB(-X_ARM_SEGMENT_LENGTH, -0.25, 0.6875, 0, -0.1875, 0.75)));
			//center
			darkSegments.add(Pair.of(pos, new AABB(-X_ARM_SEGMENT_LENGTH, 0.125, 0.375, 0, -0.125, 0.625)));
			//vertical lines
			lightSegments.add(Pair.of(pos, new AABB(-X_ARM_SEGMENT_LENGTH + 0.0625, -0.1875, 0.25, -X_ARM_SEGMENT_LENGTH + 0, 0.1875, 0.3125)));
			lightSegments.add(Pair.of(pos, new AABB(-X_ARM_SEGMENT_LENGTH + 0.0625, -0.1875, 0.6875, -X_ARM_SEGMENT_LENGTH + 0, 0.1875, 0.75)));
			lightSegments.add(Pair.of(pos, new AABB(-X_ARM_SEGMENT_LENGTH + 0.0625, -0.25, 0.3125, -X_ARM_SEGMENT_LENGTH + 0, -0.1875, 0.6875)));
			lightSegments.add(Pair.of(pos, new AABB(-X_ARM_SEGMENT_LENGTH + 0.0625, 0.1875, 0.3125, -X_ARM_SEGMENT_LENGTH + 0, 0.25, 0.6875)));
			
			i--;
		}
		pos = new PrecisionVector(x, y, z);
		//horizontal lines
		lightSegments.add(Pair.of(pos, new AABB(-remainderWidthLeft + X_ARM_SEGMENT_LENGTH + 0.1875, 0.25, 0.25, 0.8125, 0.1875, 0.3125)));
		lightSegments.add(Pair.of(pos, new AABB(-remainderWidthLeft + X_ARM_SEGMENT_LENGTH + 0.1875, 0.25, 0.6875, 0.8125, 0.1875, 0.75)));
		lightSegments.add(Pair.of(pos, new AABB(-remainderWidthLeft + X_ARM_SEGMENT_LENGTH + 0.1875, -0.25, 0.25, 0.8125, -0.1875, 0.3125)));
		lightSegments.add(Pair.of(pos, new AABB(-remainderWidthLeft + X_ARM_SEGMENT_LENGTH + 0.1875, -0.25, 0.6875, 0.8125, -0.1875, 0.75)));
		//center
		darkSegments.add(Pair.of(pos, new AABB(-remainderWidthLeft + X_ARM_SEGMENT_LENGTH + 0.1875, 0.125, 0.375, 0.8125, -0.125, 0.625)));
		if(remainderWidthLeft < -0.5) {
			pos = new PrecisionVector(x - remainderWidthLeft + X_ARM_SEGMENT_LENGTH + 0.1875, y, z);
			//vertical lines
			lightSegments.add(Pair.of(pos, new AABB(-0.9375, -0.1875, 0.25, -1, 0.1875, 0.3125)));
			lightSegments.add(Pair.of(pos, new AABB(-0.9375, -0.1875, 0.6875, -1, 0.1875, 0.75)));
			lightSegments.add(Pair.of(pos, new AABB(-0.9375, -0.25, 0.3125, -1, -0.1875, 0.6875)));
			lightSegments.add(Pair.of(pos, new AABB(-0.9375, 0.1875, 0.3125, -1, 0.25, 0.6875)));
		}
		
		/* RIGHT */
		
		i = 0;
		while(i < wholeWidthRight - 1) {
			pos = new PrecisionVector(x - widthRight + X_ARM_SEGMENT_LENGTH * i + 0.8125, y, z);
			//horizontal lines
			lightSegments.add(Pair.of(pos, new AABB(0, 0.25, 0.25, X_ARM_SEGMENT_LENGTH, 0.1875, 0.3125)));
			lightSegments.add(Pair.of(pos, new AABB(0, 0.25, 0.6875, X_ARM_SEGMENT_LENGTH, 0.1875, 0.75)));
			lightSegments.add(Pair.of(pos, new AABB(0, -0.25, 0.25, X_ARM_SEGMENT_LENGTH, -0.1875, 0.3125)));
			lightSegments.add(Pair.of(pos, new AABB(0, -0.25, 0.6875, X_ARM_SEGMENT_LENGTH, -0.1875, 0.75)));
			//center
			darkSegments.add(Pair.of(pos, new AABB(0, 0.125, 0.375, X_ARM_SEGMENT_LENGTH, -0.125, 0.625)));
			//vertical lines
			lightSegments.add(Pair.of(pos, new AABB(0.9375, -0.1875, 0.25, 1, 0.1875, 0.3125)));
			lightSegments.add(Pair.of(pos, new AABB(0.9375, -0.1875, 0.6875, 1, 0.1875, 0.75)));
			lightSegments.add(Pair.of(pos, new AABB(0.9375, -0.25, 0.3125, 1, -0.1875, 0.6875)));
			lightSegments.add(Pair.of(pos, new AABB(0.9375, 0.1875, 0.3125, 1, 0.25, 0.6875)));
			
			i++;
		}
		pos = new PrecisionVector(x, y, z);
		//horizontal lines
		lightSegments.add(Pair.of(pos, new AABB(-remainderWidthRight - X_ARM_SEGMENT_LENGTH + 0.8125, 0.25, 0.25, 0.1875, 0.1875, 0.3125)));
		lightSegments.add(Pair.of(pos, new AABB(-remainderWidthRight - X_ARM_SEGMENT_LENGTH + 0.8125, 0.25, 0.6875, 0.1875, 0.1875, 0.75)));
		lightSegments.add(Pair.of(pos, new AABB(-remainderWidthRight - X_ARM_SEGMENT_LENGTH + 0.8125, -0.25, 0.25, 0.1875, -0.1875, 0.3125)));
		lightSegments.add(Pair.of(pos, new AABB(-remainderWidthRight - X_ARM_SEGMENT_LENGTH + 0.8125, -0.25, 0.6875, 0.1875, -0.1875, 0.75)));
		//center
		darkSegments.add(Pair.of(pos, new AABB(-remainderWidthRight - X_ARM_SEGMENT_LENGTH + 0.8125,  0.125, 0.375, 0.1875, -0.125, 0.625)));
		//vertical lines
		if(remainderWidthRight > 0.5) {
			pos = new PrecisionVector(x - remainderWidthRight - X_ARM_SEGMENT_LENGTH + 0.8125, y, z);
			lightSegments.add(Pair.of(pos, new AABB(0.9375, -0.1875, 0.25, 1, 0.1875, 0.3125)));
			lightSegments.add(Pair.of(pos, new AABB(0.9375, -0.1875, 0.6875, 1, 0.1875, 0.75)));
			lightSegments.add(Pair.of(pos, new AABB(0.9375, -0.25, 0.3125, 1, -0.1875, 0.6875)));
			lightSegments.add(Pair.of(pos, new AABB(0.9375, 0.1875, 0.3125, 1, 0.25, 0.6875)));

		}
		
	}
	
	private void northOnLeft(double x, double y, double z, List<Pair<PrecisionVector, AABB>> darkSegments, List<Pair<PrecisionVector, AABB>> lightSegments, double widthLeft, double widthRight, int wholeWidthLeft, int wholeWidthRight, double remainderWidthLeft, double remainderWidthRight) {
		/* LEFT */
		
		int i = wholeWidthLeft - 1;
		PrecisionVector pos = null;
		while(i > 0) {
			pos = new PrecisionVector(x - widthLeft + X_ARM_SEGMENT_LENGTH * i - 0.1875, y, z);
			//horizontal lines
			lightSegments.add(Pair.of(pos, new AABB(0, 0.25, 0.25, X_ARM_SEGMENT_LENGTH, 0.1875, 0.3125)));
			lightSegments.add(Pair.of(pos, new AABB(0, 0.25, 0.6875, X_ARM_SEGMENT_LENGTH, 0.1875, 0.75)));
			lightSegments.add(Pair.of(pos, new AABB(0, -0.25, 0.25, X_ARM_SEGMENT_LENGTH, -0.1875, 0.3125)));
			lightSegments.add(Pair.of(pos, new AABB(0, -0.25, 0.6875, X_ARM_SEGMENT_LENGTH, -0.1875, 0.75)));
			//center
			darkSegments.add(Pair.of(pos, new AABB(0, 0.125, 0.375, X_ARM_SEGMENT_LENGTH, -0.125, 0.625)));
			//vertical lines
			lightSegments.add(Pair.of(pos, new AABB(X_ARM_SEGMENT_LENGTH - 0.0625, -0.1875, 0.25, X_ARM_SEGMENT_LENGTH - 0, 0.1875, 0.3125)));
			lightSegments.add(Pair.of(pos, new AABB(X_ARM_SEGMENT_LENGTH - 0.0625, -0.1875, 0.6875, X_ARM_SEGMENT_LENGTH - 0, 0.1875, 0.75)));
			lightSegments.add(Pair.of(pos, new AABB(X_ARM_SEGMENT_LENGTH - 0.0625, -0.25, 0.3125, X_ARM_SEGMENT_LENGTH - 0, -0.1875, 0.6875)));
			lightSegments.add(Pair.of(pos, new AABB(X_ARM_SEGMENT_LENGTH - 0.0625, 0.1875, 0.3125, X_ARM_SEGMENT_LENGTH - 0, 0.25, 0.6875)));
			
			i--;
		}
		pos = new PrecisionVector(x, y, z);
		//horizontal lines
		lightSegments.add(Pair.of(pos, new AABB(-remainderWidthLeft - 0.1875, 0.25, 0.25, 0.1875, 0.1875, 0.3125)));
		lightSegments.add(Pair.of(pos, new AABB(-remainderWidthLeft - 0.1875, 0.25, 0.6875, 0.1875, 0.1875, 0.75)));
		lightSegments.add(Pair.of(pos, new AABB(-remainderWidthLeft - 0.1875, -0.25, 0.25, 0.1875, -0.1875, 0.3125)));
		lightSegments.add(Pair.of(pos, new AABB(-remainderWidthLeft - 0.1875, -0.25, 0.6875, 0.1875, -0.1875, 0.75)));
		//center
		darkSegments.add(Pair.of(pos, new AABB(-remainderWidthLeft - 0.1875, 0.125, 0.375, 0.1875, -0.125, 0.625)));
		if(remainderWidthLeft > 0.5) {
			pos = new PrecisionVector(x - remainderWidthLeft - 0.1875, y, z);
			//vertical lines
			lightSegments.add(Pair.of(pos, new AABB(0.9375, -0.1875, 0.25, 1, 0.1875, 0.3125)));
			lightSegments.add(Pair.of(pos, new AABB(0.9375, -0.1875, 0.6875, 1, 0.1875, 0.75)));
			lightSegments.add(Pair.of(pos, new AABB(0.9375, -0.25, 0.3125, 1, -0.1875, 0.6875)));
			lightSegments.add(Pair.of(pos, new AABB(0.9375, 0.1875, 0.3125, 1, 0.25, 0.6875)));
		}
		
		
		/* RIGHT */
		
		i = 0;
		while(i > wholeWidthRight + 1) {
			pos = new PrecisionVector(x - widthRight + X_ARM_SEGMENT_LENGTH * i + 0.1875, y, z);
			//horizontal lines
			lightSegments.add(Pair.of(pos, new AABB(0, 0.25, 0.25, -X_ARM_SEGMENT_LENGTH, 0.1875, 0.3125)));
			lightSegments.add(Pair.of(pos, new AABB(0, 0.25, 0.6875, -X_ARM_SEGMENT_LENGTH, 0.1875, 0.75)));
			lightSegments.add(Pair.of(pos, new AABB(0, -0.25, 0.25, -X_ARM_SEGMENT_LENGTH, -0.1875, 0.3125)));
			lightSegments.add(Pair.of(pos, new AABB(0, -0.25, 0.6875, -X_ARM_SEGMENT_LENGTH, -0.1875, 0.75)));
			//center
			darkSegments.add(Pair.of(pos, new AABB(0, 0.125, 0.375, -X_ARM_SEGMENT_LENGTH, -0.125, 0.625)));
			//vertical lines
			lightSegments.add(Pair.of(pos, new AABB(-X_ARM_SEGMENT_LENGTH + 0, -0.1875, 0.25, -X_ARM_SEGMENT_LENGTH + 0.0625, 0.1875, 0.3125)));
			lightSegments.add(Pair.of(pos, new AABB(-X_ARM_SEGMENT_LENGTH + 0, -0.1875, 0.6875, -X_ARM_SEGMENT_LENGTH + 0.0625, 0.1875, 0.75)));
			lightSegments.add(Pair.of(pos, new AABB(-X_ARM_SEGMENT_LENGTH + 0, -0.25, 0.3125, -X_ARM_SEGMENT_LENGTH + 0.0625, -0.1875, 0.6875)));
			lightSegments.add(Pair.of(pos, new AABB(-X_ARM_SEGMENT_LENGTH + 0, 0.1875, 0.3125, -X_ARM_SEGMENT_LENGTH + 0.0625, 0.25, 0.6875)));
			
			i--;
		}
		pos = new PrecisionVector(x, y, z);
		//horizontal lines
		lightSegments.add(Pair.of(pos, new AABB(-remainderWidthRight + X_ARM_SEGMENT_LENGTH + 0.1875, 0.25, 0.25, 0.8125, 0.1875, 0.3125)));
		lightSegments.add(Pair.of(pos, new AABB(-remainderWidthRight + X_ARM_SEGMENT_LENGTH + 0.1875, 0.25, 0.6875, 0.8125, 0.1875, 0.75)));
		lightSegments.add(Pair.of(pos, new AABB(-remainderWidthRight + X_ARM_SEGMENT_LENGTH + 0.1875, -0.25, 0.25, 0.8125, -0.1875, 0.3125)));
		lightSegments.add(Pair.of(pos, new AABB(-remainderWidthRight + X_ARM_SEGMENT_LENGTH + 0.1875, -0.25, 0.6875, 0.8125, -0.1875, 0.75)));
		//center
		darkSegments.add(Pair.of(pos, new AABB(-remainderWidthRight + X_ARM_SEGMENT_LENGTH + 0.1875, 0.125, 0.375, 0.8125, -0.125, 0.625)));
		if(remainderWidthRight < -0.5) {
			pos = new PrecisionVector(x - remainderWidthRight + 0.1875, y, z);
			//vertical lines
			lightSegments.add(Pair.of(pos, new AABB(0, -0.1875, 0.25, 0.0625, 0.1875, 0.3125)));
			lightSegments.add(Pair.of(pos, new AABB(0, -0.1875, 0.6875, 0.0625, 0.1875, 0.75)));
			lightSegments.add(Pair.of(pos, new AABB(0, -0.25, 0.3125, 0.0625, -0.1875, 0.6875)));
			lightSegments.add(Pair.of(pos, new AABB(0, 0.1875, 0.3125, 0.0625, 0.25, 0.6875)));
		}
		
		
	}
	
	private void south(double x, double y, double z, List<Pair<PrecisionVector, AABB>> darkSegments, List<Pair<PrecisionVector, AABB>> lightSegments, double widthLeft, double widthRight, double widthTop, double widthBottom, int wholeWidthLeft, int wholeWidthRight, int wholeWidthTop, int wholeWidthBottom, double remainderWidthLeft, double remainderWidthRight, double remainderWidthTop, double remainderWidthBottom) {

		if (cornerOnRight.get()) {
			//southOnRight(x, y, z, darkSegments, lightSegments, widthLeft, widthRight, wholeWidthLeft, wholeWidthRight, remainderWidthLeft, remainderWidthRight);
		} else {
			//southOnLeft(x, y, z, darkSegments, lightSegments, widthLeft, widthRight, wholeWidthLeft, wholeWidthRight, remainderWidthLeft, remainderWidthRight);
		}
		
		/* BOTTOM */
		
		int i = 0;
		int removal = remainderWidthBottom < 0.625 ? 1 : 0;
		PrecisionVector pos;
		
		while(i < wholeWidthBottom - removal) {
			pos = new PrecisionVector(x, y, z - widthBottom + Z_ARM_SEGMENT_LENGTH * i + 0.8125);
			//horizontal lines
			lightSegments.add(Pair.of(pos, new AABB(0.25, -0.25, 0, 0.3125, -0.1875, Z_ARM_SEGMENT_LENGTH)));
			lightSegments.add(Pair.of(pos, new AABB(0.25, 0.1875, 0, 0.3125, 0.25, Z_ARM_SEGMENT_LENGTH)));
			lightSegments.add(Pair.of(pos, new AABB(0.6875, -0.25, 0, 0.75, -0.1875, Z_ARM_SEGMENT_LENGTH)));
			lightSegments.add(Pair.of(pos, new AABB(0.6875, 0.1875, 0, 0.75, 0.25, Z_ARM_SEGMENT_LENGTH)));
			//center
			darkSegments.add(Pair.of(pos, new AABB(0.375, -0.125, 0, 0.625, 0.125, Z_ARM_SEGMENT_LENGTH)));
			//vertical lines
			lightSegments.add(Pair.of(pos, new AABB(0.25, -0.1875, 0.9375, 0.3125, 0.1875, 1)));
			lightSegments.add(Pair.of(pos, new AABB(0.6875, -0.1875, 0.9375, 0.75, 0.1875, 1)));
			lightSegments.add(Pair.of(pos, new AABB(0.3125, -0.25, 0.9375, 0.6875, -0.1875, 1)));
			lightSegments.add(Pair.of(pos, new AABB(0.3125, 0.1875, 0.9375, 0.6875, 0.25, 1)));
			
			i++;
		}
		
		pos = new PrecisionVector(x, y, z);
		if(removal == 0) {
			//horizontal lines
			lightSegments.add(Pair.of(pos, new AABB(0.25, -0.25, -remainderWidthBottom + 0.8125, 0.3125, -0.1875, 0.1875)));
			lightSegments.add(Pair.of(pos, new AABB(0.25, 0.1875, -remainderWidthBottom + 0.8125, 0.3125, 0.25, 0.1875)));
			lightSegments.add(Pair.of(pos, new AABB(0.6875, -0.25, -remainderWidthBottom + 0.8125, 0.75, -0.1875, 0.1875)));
			lightSegments.add(Pair.of(pos, new AABB(0.6875, 0.1875, -remainderWidthBottom + 0.8125, 0.75, 0.25, 0.1875)));
			//center
			darkSegments.add(Pair.of(pos, new AABB(0.375, -0.125, -remainderWidthBottom + 0.8125, 0.625, 0.125, 0.1875)));
			//vertical lines
			/*
			pos = new PrecisionVector(x, y, z - remainderWidthBottom + 0.8125);
			lightSegments.add(Pair.of(pos, new AABB(0.25, -0.1875, 0.9375, 0.3125, 0.1875, 1)));
			lightSegments.add(Pair.of(pos, new AABB(0.6875, -0.1875, 0.9375, 0.75, 0.1875, 1)));
			lightSegments.add(Pair.of(pos, new AABB(0.3125, -0.25, 0.9375, 0.6875, -0.1875, 1)));
			lightSegments.add(Pair.of(pos, new AABB(0.3125, 0.1875, 0.9375, 0.6875, 0.25,  1)));
			*/
		} else {
			//horizontal lines
			lightSegments.add(Pair.of(pos, new AABB(0.25, -0.25, -remainderWidthBottom - Z_ARM_SEGMENT_LENGTH + 0.8125, 0.3125, -0.1875, 0.1875)));
			lightSegments.add(Pair.of(pos, new AABB(0.25, 0.1875, -remainderWidthBottom - Z_ARM_SEGMENT_LENGTH + 0.8125, 0.3125, 0.25, 0.1875)));
			lightSegments.add(Pair.of(pos, new AABB(0.6875, -0.25, -remainderWidthBottom - Z_ARM_SEGMENT_LENGTH + 0.8125, 0.75, -0.1875, 0.1875)));
			lightSegments.add(Pair.of(pos, new AABB(0.6875, 0.1875, -remainderWidthBottom - Z_ARM_SEGMENT_LENGTH + 0.8125, 0.75, 0.25, 0.1875)));
			//center
			darkSegments.add(Pair.of(pos, new AABB(0.375, -0.125, -remainderWidthBottom - Z_ARM_SEGMENT_LENGTH + 0.8125, 0.625, 0.125, 0.1875)));
			//vertical lines
			if(remainderWidthBottom > 0.5) {
				pos = new PrecisionVector(x, y, z - remainderWidthBottom - Z_ARM_SEGMENT_LENGTH + 0.8125);
				lightSegments.add(Pair.of(pos, new AABB(0.25, -0.1875, 0.9375, 0.3125, 0.1875, 1)));
				lightSegments.add(Pair.of(pos, new AABB(0.6875, -0.1875, 0.9375, 0.75, 0.1875, 1)));
				lightSegments.add(Pair.of(pos, new AABB(0.3125, -0.25, 0.9375, 0.6875, -0.1875, 1)));
				lightSegments.add(Pair.of(pos, new AABB(0.3125, 0.1875, 0.9375, 0.6875, 0.25, 1)));
			}
			
		}
		
		/* TOP */
		
		removal = remainderWidthTop > (-0.625) ? 1 : 0;
		i = 0;
		while(i > wholeWidthTop + removal) {
			pos = new PrecisionVector(x, y, z - widthTop + Z_ARM_SEGMENT_LENGTH * i + 0.1875);
			//horizontal lines
			lightSegments.add(Pair.of(pos, new AABB(0.25, -0.25, 0, 0.3125, -0.1875, -Z_ARM_SEGMENT_LENGTH)));
			lightSegments.add(Pair.of(pos, new AABB(0.25, 0.1875, 0, 0.3125, 0.25, -Z_ARM_SEGMENT_LENGTH)));
			lightSegments.add(Pair.of(pos, new AABB(0.6875, -0.25, 0, 0.75, -0.1875, -Z_ARM_SEGMENT_LENGTH)));
			lightSegments.add(Pair.of(pos, new AABB(0.6875, 0.1875, 0, 0.75, 0.25, -Z_ARM_SEGMENT_LENGTH)));
			//center
			darkSegments.add(Pair.of(pos, new AABB(0.3875, -0.125, 0, 0.625, 0.125, -Z_ARM_SEGMENT_LENGTH)));
			//vertical lines
			lightSegments.add(Pair.of(pos, new AABB(0.25, -0.1875, -Z_ARM_SEGMENT_LENGTH + 0, 0.3125, 0.1875, -Z_ARM_SEGMENT_LENGTH + 0.0625)));
			lightSegments.add(Pair.of(pos, new AABB(0.6875, -0.1875, -Z_ARM_SEGMENT_LENGTH + 0, 0.75, 0.1875, -Z_ARM_SEGMENT_LENGTH + 0.0625)));
			lightSegments.add(Pair.of(pos, new AABB(0.3125, -0.25, -Z_ARM_SEGMENT_LENGTH + 0, 0.6875, -0.1875, -Z_ARM_SEGMENT_LENGTH + 0.0625)));
			lightSegments.add(Pair.of(pos, new AABB(0.3125, 0.1875, -Z_ARM_SEGMENT_LENGTH + 0, 0.6875, 0.25, -Z_ARM_SEGMENT_LENGTH + 0.0625)));
			
			i--;
		}
		
		pos = new PrecisionVector(x, y, z);
		if(removal == 1) {
			//horizontal lines
			lightSegments.add(Pair.of(pos, new AABB(0.25, -0.25, -remainderWidthTop + Z_ARM_SEGMENT_LENGTH + 0.1875, 0.3125, -0.1875, 0.8125)));
			lightSegments.add(Pair.of(pos, new AABB(0.6875, -0.25, -remainderWidthTop + Z_ARM_SEGMENT_LENGTH + 0.1875, 0.75, -0.1875, 0.8125)));
			lightSegments.add(Pair.of(pos, new AABB(0.25, 0.1875, -remainderWidthTop + Z_ARM_SEGMENT_LENGTH + 0.1875, 0.3125, 0.25, 0.8125)));
			lightSegments.add(Pair.of(pos, new AABB(0.6875, 0.1875, -remainderWidthTop + Z_ARM_SEGMENT_LENGTH + 0.1875, 0.75, 0.25, 0.8125)));
			//center
			darkSegments.add(Pair.of(pos, new AABB(0.3875, -0.125, -remainderWidthTop + Z_ARM_SEGMENT_LENGTH + 0.1875, 0.625, 0.125, 0.8125)));
			//horizontal lines
			if(remainderWidthTop < -0.5) {
				pos = new PrecisionVector(x, y, z - remainderWidthTop + 0.1875);
				lightSegments.add(Pair.of(pos, new AABB(0.25, -0.1875, 0, 0.3125, 0.1875, 0.0625)));
				lightSegments.add(Pair.of(pos, new AABB(0.6875, -0.1875, 0, 0.75, 0.1875, 0.0625)));
				lightSegments.add(Pair.of(pos, new AABB(0.3125, -0.25, 0, 0.6875, -0.1875, 0.0625)));
				lightSegments.add(Pair.of(pos, new AABB(0.3125, 0.1875, 0, 0.6875, 0.25,  0.0625)));
			}
			
		} else {
			//horizontal lines
			lightSegments.add(Pair.of(pos, new AABB(0.25, -0.25, -remainderWidthTop + 0.1875, 0.3125, -0.1875, 0.8125)));
			lightSegments.add(Pair.of(pos, new AABB(0.6875, -0.25, -remainderWidthTop + 0.1875, 0.75, -0.1875, 0.8125)));
			lightSegments.add(Pair.of(pos, new AABB(0.25, 0.1875, -remainderWidthTop + 0.1875, 0.3125, 0.25, 0.8125)));
			lightSegments.add(Pair.of(pos, new AABB(0.6875, 0.1875, -remainderWidthTop + 0.1875, 0.75, 0.25, 0.8125)));
			//center
			darkSegments.add(Pair.of(pos, new AABB(0.3875, -0.125, -remainderWidthTop + 0.1875, 0.625, 0.125, 0.8125)));
			//vertical lines
			/*
			pos = new PrecisionVector(x, y, z - remainderWidthTop - Z_ARM_SEGMENT_LENGTH + 0.1875);
			lightSegments.add(Pair.of(pos, new AABB(0.25, -0.1875, 0, 0.3125, 0.1875, 0.0625)));
			lightSegments.add(Pair.of(pos, new AABB(0.6875, -0.1875, 0, 0.75, 0.1875, 0.0625)));
			lightSegments.add(Pair.of(pos, new AABB(0.3125, -0.25, 0, 0.6875, -0.1875, 0.0625)));
			lightSegments.add(Pair.of(pos, new AABB(0.3125, 0.1875, 0, 0.6875, 0.25, 0.0625)));
			*/
		}
		
	}
	
//	private void southOnRight(double x, double y, double z, List<Pair<PrecisionVector, AABB>> darkSegments, List<Pair<PrecisionVector, AABB>> lightSegments, double widthLeft, double widthRight, int wholeWidthLeft, int wholeWidthRight, double remainderWidthLeft, double remainderWidthRight) {
//		int i = 0;
//		/* LEFT */
//		
//		i = wholeWidthLeft - 1;
//
//		while(i > 0) {
//			//horizontal lines
//			lightSegments.add(new AABB(x - widthLeft + X_ARM_SEGMENT_LENGTH * i + 0.25, y + 0.25, z + 0.25, x - widthLeft + X_ARM_SEGMENT_LENGTH * (i - 1) + 0.25, y + 0.1875, z + 0.3125));
//			lightSegments.add(new AABB(x - widthLeft + X_ARM_SEGMENT_LENGTH * i + 0.25, y + 0.25, z + 0.6875, x - widthLeft + X_ARM_SEGMENT_LENGTH * (i - 1) + 0.25, y + 0.1875, z + 0.75));
//			lightSegments.add(new AABB(x - widthLeft + X_ARM_SEGMENT_LENGTH * i + 0.25, y - 0.25, z + 0.25, x - widthLeft + X_ARM_SEGMENT_LENGTH * (i - 1) + 0.25, y - 0.1875, z + 0.3125));
//			lightSegments.add(new AABB(x - widthLeft + X_ARM_SEGMENT_LENGTH * i + 0.25, y - 0.25, z + 0.6875, x - widthLeft + X_ARM_SEGMENT_LENGTH * (i - 1) + 0.25, y - 0.1875, z + 0.75));
//			//center
//			darkSegments.add(new AABB(x - widthLeft + X_ARM_SEGMENT_LENGTH * i + 0.25, y + 0.125, z + 0.375, x - widthLeft + X_ARM_SEGMENT_LENGTH * (i - 1) + 0.25, y - 0.125, z + 0.625));
//			//vertical lines
//			lightSegments.add(new AABB(x - widthLeft + X_ARM_SEGMENT_LENGTH * (i - 1) + (0.25 + 0.46875), y - 0.1875, z + 0.25, x - widthLeft + X_ARM_SEGMENT_LENGTH * (i - 1) + (0.25 + 0.53125), y + 0.1875, z + 0.3125));
//			lightSegments.add(new AABB(x - widthLeft + X_ARM_SEGMENT_LENGTH * (i - 1) + (0.25 + 0.46875), y - 0.1875, z + 0.6875, x - widthLeft + X_ARM_SEGMENT_LENGTH * (i - 1) + (0.25 + 0.53125), y + 0.1875, z + 0.75));
//			lightSegments.add(new AABB(x - widthLeft + X_ARM_SEGMENT_LENGTH * (i - 1) + (0.25 + 0.46875), y - 0.25, z + 0.3125, x - widthLeft + X_ARM_SEGMENT_LENGTH * (i - 1) + (0.25 + 0.53125), y - 0.1875, z + 0.6875));
//			lightSegments.add(new AABB(x - widthLeft + X_ARM_SEGMENT_LENGTH * (i - 1) + (0.25 + 0.46875), y + 0.1875, z + 0.3125, x - widthLeft + X_ARM_SEGMENT_LENGTH * (i - 1) + (0.25 + 0.53125), y + 0.25, z + 0.6875));
//			
//			i--;
//		}
//		//horizontal lines
//		lightSegments.add(new AABB(x - remainderWidthLeft - X_ARM_SEGMENT_LENGTH + 0.25, y + 0.25, z + 0.25, x + 0.25, y + 0.1875, z + 0.3125));
//		lightSegments.add(new AABB(x - remainderWidthLeft - X_ARM_SEGMENT_LENGTH + 0.25, y + 0.25, z + 0.6875, x + 0.25, y + 0.1875, z + 0.75));
//		lightSegments.add(new AABB(x - remainderWidthLeft - X_ARM_SEGMENT_LENGTH + 0.25, y - 0.25, z + 0.25, x + 0.25, y - 0.1875, z + 0.3125));
//		lightSegments.add(new AABB(x - remainderWidthLeft - X_ARM_SEGMENT_LENGTH + 0.25, y - 0.25, z + 0.6875, x + 0.25, y - 0.1875, z + 0.75));
//		//center
//		darkSegments.add(new AABB(x - remainderWidthLeft - X_ARM_SEGMENT_LENGTH + 0.25, y + 0.125, z + 0.375, x + 0.25, y - 0.125, z + 0.625));
//		//vertical lines
//		lightSegments.add(new AABB(x - remainderWidthLeft - X_ARM_SEGMENT_LENGTH + 0.75, y - 0.1875, z + 0.25, x - remainderWidthLeft - X_ARM_SEGMENT_LENGTH + 0.6875, y + 0.1875, z + 0.3125));
//		lightSegments.add(new AABB(x - remainderWidthLeft - X_ARM_SEGMENT_LENGTH + 0.75, y - 0.1875, z + 0.6875, x - remainderWidthLeft - X_ARM_SEGMENT_LENGTH + 0.6875, y + 0.1875, z + 0.75));
//		lightSegments.add(new AABB(x - remainderWidthLeft - X_ARM_SEGMENT_LENGTH + 0.75, y - 0.25, z + 0.3125, x - remainderWidthLeft - X_ARM_SEGMENT_LENGTH + 0.6875, y - 0.1875, z + 0.6875));
//		lightSegments.add(new AABB(x - remainderWidthLeft - X_ARM_SEGMENT_LENGTH + 0.75, y + 0.1875, z + 0.3125, x -remainderWidthLeft - X_ARM_SEGMENT_LENGTH + 0.6875, y + 0.25, z + 0.6875));
//		
//		/* RIGHT */
//		
//		i = wholeWidthRight + 1;
//		while(i < 0) {
//			//horizontal lines
//			lightSegments.add(new AABB(x - widthRight + X_ARM_SEGMENT_LENGTH * i + 0.6875, y + 0.25, z + 0.25, x - widthRight + X_ARM_SEGMENT_LENGTH * (i + 1) + 0.6875, y + 0.1875, z + 0.3125));
//			lightSegments.add(new AABB(x - widthRight + X_ARM_SEGMENT_LENGTH * i + 0.6875, y + 0.25, z + 0.6875, x - widthRight + X_ARM_SEGMENT_LENGTH * (i + 1) + 0.6875, y + 0.1875, z + 0.75));
//			lightSegments.add(new AABB(x - widthRight + X_ARM_SEGMENT_LENGTH * i + 0.6875, y - 0.25, z + 0.25, x - widthRight + X_ARM_SEGMENT_LENGTH * (i + 1) + 0.6875, y - 0.1875, z + 0.3125));
//			lightSegments.add(new AABB(x - widthRight + X_ARM_SEGMENT_LENGTH * i + 0.6875, y - 0.25, z + 0.6875, x - widthRight + X_ARM_SEGMENT_LENGTH * (i + 1) + 0.6875, y - 0.1875, z + 0.75));
//			//center
//			darkSegments.add(new AABB(x - widthRight + X_ARM_SEGMENT_LENGTH * i + 0.6875, y + 0.125, z + 0.375, x - widthRight + X_ARM_SEGMENT_LENGTH * (i + 1) + 0.6875, y - 0.125, z + 0.625));
//			//vertical lines
//			lightSegments.add(new AABB(x - widthRight + X_ARM_SEGMENT_LENGTH * i + (0.6875 + 0.46875), y - 0.1875, z + 0.25, x - widthRight + X_ARM_SEGMENT_LENGTH * i + (0.6875 + 0.53125), y + 0.1875, z + 0.3125));
//			lightSegments.add(new AABB(x - widthRight + X_ARM_SEGMENT_LENGTH * i + (0.6875 + 0.46875), y - 0.1875, z + 0.6875, x - widthRight + X_ARM_SEGMENT_LENGTH * i + (0.6875 + 0.53125), y + 0.1875, z + 0.75));
//			lightSegments.add(new AABB(x - widthRight + X_ARM_SEGMENT_LENGTH * i + (0.6875 + 0.46875), y - 0.25, z + 0.3125, x - widthRight + X_ARM_SEGMENT_LENGTH * i + (0.6875 + 0.53125), y - 0.1875, z + 0.6875));
//			lightSegments.add(new AABB(x - widthRight + X_ARM_SEGMENT_LENGTH * i + (0.6875 + 0.46875), y + 0.1875, z + 0.3125, x - widthRight + X_ARM_SEGMENT_LENGTH * i + (0.6875 + 0.53125), y + 0.25, z + 0.6875));
//			
//			i++;
//		}
//		
//		//horizontal lines
//		lightSegments.add(new AABB(x - remainderWidthRight + X_ARM_SEGMENT_LENGTH + 0.6875, y + 0.25, z + 0.25, x + 0.75, y + 0.1875, z + 0.3125));
//		lightSegments.add(new AABB(x - remainderWidthRight + X_ARM_SEGMENT_LENGTH + 0.6875, y + 0.25, z + 0.6875, x + 0.75, y + 0.1875, z + 0.75));
//		lightSegments.add(new AABB(x - remainderWidthRight + X_ARM_SEGMENT_LENGTH + 0.6875, y - 0.25, z + 0.25, x + 0.75, y - 0.1875, z + 0.3125));
//		lightSegments.add(new AABB(x - remainderWidthRight + X_ARM_SEGMENT_LENGTH + 0.6875, y - 0.25, z + 0.6875, x + 0.75, y - 0.1875, z + 0.75));
//		//center
//		darkSegments.add(new AABB(x - remainderWidthRight + X_ARM_SEGMENT_LENGTH + 0.6875, y + 0.125, z + 0.375, x + 0.75, y - 0.125, z + 0.625));
//		//vertical lines
//		lightSegments.add(new AABB(x - remainderWidthRight + (0.6875 + 0.46875), y - 0.1875, z + 0.25, x - remainderWidthRight + (0.6875 + 0.53125), y + 0.1875, z + 0.3125));
//		lightSegments.add(new AABB(x - remainderWidthRight + (0.6875 + 0.46875), y - 0.1875, z + 0.6875, x - remainderWidthRight + (0.6875 + 0.53125), y + 0.1875, z + 0.75));
//		lightSegments.add(new AABB(x - remainderWidthRight + (0.6875 + 0.46875), y - 0.25, z + 0.3125, x - remainderWidthRight + (0.6875 + 0.53125), y - 0.1875, z + 0.6875));
//		lightSegments.add(new AABB(x - remainderWidthRight + (0.6875 + 0.46875), y + 0.1875, z + 0.3125, x - remainderWidthRight + (0.6875 + 0.53125), y + 0.25, z + 0.6875));
//	
//	}
//	
//	private void southOnLeft(double x, double y, double z, List<Pair<PrecisionVector, AABB>> darkSegments, List<Pair<PrecisionVector, AABB>> lightSegments, double widthLeft, double widthRight, int wholeWidthLeft, int wholeWidthRight, double remainderWidthLeft, double remainderWidthRight) {
//		int i = 0;
//		/* LEFT */
//		
//		i = wholeWidthLeft + 1;
//		
//		while(i < 0) {
//			//horizontal lines
//			lightSegments.add(new AABB(x - widthLeft + X_ARM_SEGMENT_LENGTH * (i + 2) - 0.25, y + 0.25, z + 0.25, x - widthLeft + X_ARM_SEGMENT_LENGTH * (i + 1) - 0.25, y + 0.1875, z + 0.3125));
//			lightSegments.add(new AABB(x - widthLeft + X_ARM_SEGMENT_LENGTH * (i + 2) - 0.25, y + 0.25, z + 0.6875, x - widthLeft + X_ARM_SEGMENT_LENGTH * (i + 1) - 0.25, y + 0.1875, z + 0.75));
//			lightSegments.add(new AABB(x - widthLeft + X_ARM_SEGMENT_LENGTH * (i + 2) - 0.25, y - 0.25, z + 0.25, x - widthLeft + X_ARM_SEGMENT_LENGTH * (i + 1) - 0.25, y - 0.1875, z + 0.3125));
//			lightSegments.add(new AABB(x - widthLeft + X_ARM_SEGMENT_LENGTH * (i + 2) - 0.25, y - 0.25, z + 0.6875, x - widthLeft + X_ARM_SEGMENT_LENGTH * (i + 1) - 0.25, y - 0.1875, z + 0.75));
//			//center
//			darkSegments.add(new AABB(x - widthLeft + X_ARM_SEGMENT_LENGTH * (i + 2) - 0.25, y + 0.125, z + 0.375, x - widthLeft + X_ARM_SEGMENT_LENGTH * (i + 1) - 0.25, y - 0.125, z + 0.625));
//			//vertical lines
//			lightSegments.add(new AABB(x - widthLeft + X_ARM_SEGMENT_LENGTH * (i + 2) - (0.25 + 0.46875), y - 0.1875, z + 0.25, x - widthLeft + X_ARM_SEGMENT_LENGTH * (i + 2) - (0.25 + 0.53125), y + 0.1875, z + 0.3125));
//			lightSegments.add(new AABB(x - widthLeft + X_ARM_SEGMENT_LENGTH * (i + 2) - (0.25 + 0.46875), y - 0.1875, z + 0.6875, x - widthLeft + X_ARM_SEGMENT_LENGTH * (i + 2) - (0.25 + 0.53125), y + 0.1875, z + 0.75));
//			lightSegments.add(new AABB(x - widthLeft + X_ARM_SEGMENT_LENGTH * (i + 2) - (0.25 + 0.46875), y - 0.25, z + 0.3125, x - widthLeft + X_ARM_SEGMENT_LENGTH * (i + 2) - (0.25 + 0.53125), y - 0.1875, z + 0.6875));
//			lightSegments.add(new AABB(x - widthLeft + X_ARM_SEGMENT_LENGTH * (i + 2) - (0.25 + 0.46875), y + 0.1875, z + 0.3125, x - widthLeft + X_ARM_SEGMENT_LENGTH * (i + 2) - (0.25 + 0.53125), y + 0.25, z + 0.6875));
//			
//			i++;
//		}
//		//horizontal lines
//		lightSegments.add(new AABB(x - remainderWidthLeft + X_ARM_SEGMENT_LENGTH + 0.75, y + 0.25, z + 0.25, x + 0.8125, y + 0.1875, z + 0.3125));
//		lightSegments.add(new AABB(x - remainderWidthLeft + X_ARM_SEGMENT_LENGTH + 0.75, y + 0.25, z + 0.6875, x + 0.8125, y + 0.1875, z + 0.75));
//		lightSegments.add(new AABB(x - remainderWidthLeft + X_ARM_SEGMENT_LENGTH + 0.75, y - 0.25, z + 0.25, x + 0.8125, y - 0.1875, z + 0.3125));
//		lightSegments.add(new AABB(x - remainderWidthLeft + X_ARM_SEGMENT_LENGTH + 0.75, y - 0.25, z + 0.6875, x + 0.8125, y - 0.1875, z + 0.75));
//		//center
//		darkSegments.add(new AABB(x - remainderWidthLeft + X_ARM_SEGMENT_LENGTH + 0.75, y + 0.125, z + 0.375, x + 0.8125, y - 0.125, z + 0.625));
//		//vertical lines
//		lightSegments.add(new AABB(x - remainderWidthLeft + X_ARM_SEGMENT_LENGTH + 0.25, y - 0.1875, z + 0.25, x - remainderWidthLeft + X_ARM_SEGMENT_LENGTH + 0.1875, y + 0.1875, z + 0.3125));
//		lightSegments.add(new AABB(x - remainderWidthLeft + X_ARM_SEGMENT_LENGTH + 0.25, y - 0.1875, z + 0.6875, x - remainderWidthLeft + X_ARM_SEGMENT_LENGTH + 0.1875, y + 0.1875, z + 0.75));
//		lightSegments.add(new AABB(x - remainderWidthLeft + X_ARM_SEGMENT_LENGTH + 0.25, y - 0.25, z + 0.3125, x - remainderWidthLeft + X_ARM_SEGMENT_LENGTH + 0.1875, y - 0.1875, z + 0.6875));
//		lightSegments.add(new AABB(x - remainderWidthLeft + X_ARM_SEGMENT_LENGTH + 0.25, y + 0.1875, z + 0.3125, x -remainderWidthLeft + X_ARM_SEGMENT_LENGTH + 0.1875, y + 0.25, z + 0.6875));
//		
//		/* RIGHT */
//		
//		i = wholeWidthRight - 1;
//		while(i > 0) {
//			//horizontal lines
//			lightSegments.add(new AABB(x - widthRight + X_ARM_SEGMENT_LENGTH * (i - 1) + 0.3125, y + 0.25, z + 0.25, x - widthRight + X_ARM_SEGMENT_LENGTH * i + 0.3125, y + 0.1875, z + 0.3125));
//			lightSegments.add(new AABB(x - widthRight + X_ARM_SEGMENT_LENGTH * (i - 1) + 0.3125, y + 0.25, z + 0.6875, x - widthRight + X_ARM_SEGMENT_LENGTH * i + 0.3125, y + 0.1875, z + 0.75));
//			lightSegments.add(new AABB(x - widthRight + X_ARM_SEGMENT_LENGTH * (i - 1) + 0.3125, y - 0.25, z + 0.25, x - widthRight + X_ARM_SEGMENT_LENGTH * i + 0.3125, y - 0.1875, z + 0.3125));
//			lightSegments.add(new AABB(x - widthRight + X_ARM_SEGMENT_LENGTH * (i - 1) + 0.3125, y - 0.25, z + 0.6875, x - widthRight + X_ARM_SEGMENT_LENGTH * i + 0.3125, y - 0.1875, z + 0.75));
//			//center
//			darkSegments.add(new AABB(x - widthRight + X_ARM_SEGMENT_LENGTH * (i - 1) + 0.3125, y + 0.125, z + 0.375, x - widthRight + X_ARM_SEGMENT_LENGTH * i + 0.3125, y - 0.125, z + 0.625));
//			//vertical lines
//			lightSegments.add(new AABB(x - widthRight + X_ARM_SEGMENT_LENGTH * (i - 1) + (0.3125 + 0.46875), y - 0.1875, z + 0.25, x - widthRight + X_ARM_SEGMENT_LENGTH * (i - 1) + (0.3125 + 0.53125), y + 0.1875, z + 0.3125));
//			lightSegments.add(new AABB(x - widthRight + X_ARM_SEGMENT_LENGTH * (i - 1) + (0.3125 + 0.46875), y - 0.1875, z + 0.6875, x - widthRight + X_ARM_SEGMENT_LENGTH * (i - 1) + (0.3125 + 0.53125), y + 0.1875, z + 0.75));
//			lightSegments.add(new AABB(x - widthRight + X_ARM_SEGMENT_LENGTH * (i - 1) + (0.3125 + 0.46875), y - 0.25, z + 0.3125, x - widthRight + X_ARM_SEGMENT_LENGTH * (i - 1) + (0.3125 + 0.53125), y - 0.1875, z + 0.6875));
//			lightSegments.add(new AABB(x - widthRight + X_ARM_SEGMENT_LENGTH * (i - 1) + (0.3125 + 0.46875), y + 0.1875, z + 0.3125, x - widthRight + X_ARM_SEGMENT_LENGTH * (i - 1) + (0.3125 + 0.53125), y + 0.25, z + 0.6875));
//			
//			i--;
//		}
//		//horizontal lines
//		lightSegments.add(new AABB(x - remainderWidthRight - X_ARM_SEGMENT_LENGTH + 0.3125, y + 0.25, z + 0.25, x + 0.1875, y + 0.1875, z + 0.3125));
//		lightSegments.add(new AABB(x - remainderWidthRight - X_ARM_SEGMENT_LENGTH + 0.3125, y + 0.25, z + 0.6875, x + 0.1875, y + 0.1875, z + 0.75));
//		lightSegments.add(new AABB(x - remainderWidthRight - X_ARM_SEGMENT_LENGTH + 0.3125, y - 0.25, z + 0.25, x + 0.1875, y - 0.1875, z + 0.3125));
//		lightSegments.add(new AABB(x - remainderWidthRight - X_ARM_SEGMENT_LENGTH + 0.3125, y - 0.25, z + 0.6875, x + 0.1875, y - 0.1875, z + 0.75));
//		//center
//		darkSegments.add(new AABB(x - remainderWidthRight - X_ARM_SEGMENT_LENGTH + 0.3125, y + 0.125, z + 0.375, x + 0.1875, y - 0.125, z + 0.625));
//		//vertical lines
//		lightSegments.add(new AABB(x - remainderWidthRight - X_ARM_SEGMENT_LENGTH + (0.3125 + 0.46875), y - 0.1875, z + 0.25, x - remainderWidthRight - X_ARM_SEGMENT_LENGTH + (0.3125 + 0.53125), y + 0.1875, z + 0.3125));
//		lightSegments.add(new AABB(x - remainderWidthRight - X_ARM_SEGMENT_LENGTH + (0.3125 + 0.46875), y - 0.1875, z + 0.6875, x - remainderWidthRight - X_ARM_SEGMENT_LENGTH + (0.3125 + 0.53125), y + 0.1875, z + 0.75));
//		lightSegments.add(new AABB(x - remainderWidthRight - X_ARM_SEGMENT_LENGTH + (0.3125 + 0.46875), y - 0.25, z + 0.3125, x - remainderWidthRight - X_ARM_SEGMENT_LENGTH + (0.3125 + 0.53125), y - 0.1875, z + 0.6875));
//		lightSegments.add(new AABB(x - remainderWidthRight - X_ARM_SEGMENT_LENGTH + (0.3125 + 0.46875), y + 0.1875, z + 0.3125, x - remainderWidthRight - X_ARM_SEGMENT_LENGTH + (0.3125 + 0.53125), y + 0.25, z + 0.6875));
//		
//	}
	
//	private void east(double x, double y, double z, List<Pair<PrecisionVector, AABB>> darkSegments, List<Pair<PrecisionVector, AABB>> lightSegments, double widthLeft, double widthRight, double widthTop, double widthBottom, int wholeWidthLeft, int wholeWidthRight, int wholeWidthTop, int wholeWidthBottom, double remainderWidthLeft, double remainderWidthRight, double remainderWidthTop, double remainderWidthBottom) {
//		int i = 0;
//		if (cornerOnRight.get()) {
//			eastOnRight(x, y, z, darkSegments, lightSegments, widthLeft, widthRight, wholeWidthLeft, wholeWidthRight, remainderWidthLeft, remainderWidthRight);
//		} else {
//			eastOnLeft(x, y, z, darkSegments, lightSegments, widthLeft, widthRight, wholeWidthLeft, wholeWidthRight, remainderWidthLeft, remainderWidthRight);
//		}
//		
//		/* BOTTOM */
//		
//		i = 0;
//		while(i < wholeWidthBottom - 1) {
//			//vertical lines
//			lightSegments.add(new AABB(x - widthBottom + i * X_ARM_SEGMENT_LENGTH + 0.3125, y - 0.25, z + 0.25, x - widthBottom + (i + 1) * X_ARM_SEGMENT_LENGTH + 0.3125, y - 0.1875, z + 0.3125));
//			lightSegments.add(new AABB(x - widthBottom + i * X_ARM_SEGMENT_LENGTH + 0.3125, y - 0.25, z + 0.6875, x - widthBottom + (i + 1) * X_ARM_SEGMENT_LENGTH + 0.3125, y - 0.1875, z + 0.75));
//			lightSegments.add(new AABB(x - widthBottom + i * X_ARM_SEGMENT_LENGTH + 0.3125, y + 0.1875, z + 0.25, x - widthBottom + (i + 1) * X_ARM_SEGMENT_LENGTH + 0.3125, y + 0.25, z + 0.3125));
//			lightSegments.add(new AABB(x - widthBottom + i * X_ARM_SEGMENT_LENGTH + 0.3125, y + 0.1875, z + 0.6875, x - widthBottom + (i + 1) * X_ARM_SEGMENT_LENGTH + 0.3125, y + 0.25, z + 0.75));
//			//center
//			darkSegments.add(new AABB(x - widthBottom + i * X_ARM_SEGMENT_LENGTH + 0.3125, y - 0.125, z + 0.3875, x - widthBottom + (i + 1) * X_ARM_SEGMENT_LENGTH + 0.3125, y + 0.125, z + 0.625));
//			//vertical lines
//			lightSegments.add(new AABB(x - widthBottom + i * X_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), y - 0.25, z + 0.3125, x - widthBottom + i * X_ARM_SEGMENT_LENGTH + (0.3125 + 0.53215), y - 0.1875, z + 0.6875));
//			lightSegments.add(new AABB(x - widthBottom + i * X_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), y + 0.1875, z + 0.3125, x - widthBottom + i * X_ARM_SEGMENT_LENGTH + (0.3125 + 0.53215), y + 0.25, z + 0.6875));
//			lightSegments.add(new AABB(x - widthBottom + i * X_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), y - 0.1875, z + 0.25, x - widthBottom + i * X_ARM_SEGMENT_LENGTH + (0.3125 + 0.53215), y + 0.1875, z + 0.3125));
//			lightSegments.add(new AABB(x - widthBottom + i * X_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), y - 0.1875, z + 0.6875, x - widthBottom + i * X_ARM_SEGMENT_LENGTH + (0.3125 + 0.53215), y + 0.1875, z + 0.75));
//			
//			i++;
//		}
//		
//		//vertical lines
//		lightSegments.add(new AABB(x - remainderWidthBottom + 0.3125 - X_ARM_SEGMENT_LENGTH, y - 0.25, z + 0.25, x + 0.1875, y - 0.1875, z + 0.3125));
//		lightSegments.add(new AABB(x - remainderWidthBottom + 0.3125 - X_ARM_SEGMENT_LENGTH, y - 0.25, z + 0.6875, x + 0.1875, y - 0.1875, z + 0.75));
//		lightSegments.add(new AABB(x - remainderWidthBottom + 0.3125 - X_ARM_SEGMENT_LENGTH, y + 0.1875, z + 0.25, x + 0.1875, y + 0.25, z + 0.3125));
//		lightSegments.add(new AABB(x - remainderWidthBottom + 0.3125 - X_ARM_SEGMENT_LENGTH, y + 0.1875, z + 0.6875, x + 0.1875, y + 0.25, z + 0.75));
//		//center
//		darkSegments.add(new AABB(x - remainderWidthBottom + 0.3125 - X_ARM_SEGMENT_LENGTH, y - 0.125, z + 0.3875, x + 0.1875, y + 0.125, z + 0.625));
//		//vertical lines
//		lightSegments.add(new AABB(x - remainderWidthBottom - X_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), y - 0.25, z + 0.3125, x - remainderWidthBottom - X_ARM_SEGMENT_LENGTH + (0.3125 + 0.53215), y - 0.1875, z + 0.6875));
//		lightSegments.add(new AABB(x - remainderWidthBottom - X_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), y + 0.1875, z + 0.3125, x - remainderWidthBottom - X_ARM_SEGMENT_LENGTH + (0.3125 + 0.53215), y + 0.25, z + 0.6875));
//		lightSegments.add(new AABB(x - remainderWidthBottom - X_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), y - 0.1875, z + 0.25, x - remainderWidthBottom - X_ARM_SEGMENT_LENGTH + (0.3125 + 0.53215), y + 0.1875, z + 0.3125));
//		lightSegments.add(new AABB(x - remainderWidthBottom - X_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), y - 0.1875, z + 0.6875, x - remainderWidthBottom - X_ARM_SEGMENT_LENGTH + (0.3125 + 0.53215), y + 0.1875, z + 0.75));
//		
//		/* TOP */
//		
//		i = 0;
//		while(i > wholeWidthTop + 1) {
//			//vertical lines
//			lightSegments.add(new AABB(x - widthTop + i * X_ARM_SEGMENT_LENGTH + 0.6875, y - 0.25, z + 0.25, x - widthTop + (i - 1) * X_ARM_SEGMENT_LENGTH + 0.6875, y - 0.1875, z + 0.3125));
//			lightSegments.add(new AABB(x - widthTop + i * X_ARM_SEGMENT_LENGTH + 0.6875, y - 0.25, z + 0.6875, x - widthTop + (i - 1) * X_ARM_SEGMENT_LENGTH + 0.6875, y - 0.1875, z + 0.75));
//			lightSegments.add(new AABB(x - widthTop + i * X_ARM_SEGMENT_LENGTH + 0.6875, y + 0.1875, z + 0.25, x - widthTop + (i - 1) * X_ARM_SEGMENT_LENGTH + 0.6875, y + 0.25, z + 0.3125));
//			lightSegments.add(new AABB(x - widthTop + i * X_ARM_SEGMENT_LENGTH + 0.6875, y + 0.1875, z + 0.6875, x - widthTop + (i - 1) * X_ARM_SEGMENT_LENGTH + 0.6875, y + 0.25, z + 0.75));
//			//center
//			darkSegments.add(new AABB(x - widthTop + i * X_ARM_SEGMENT_LENGTH + 0.6875, y - 0.125, z + 0.3875, x - widthTop + (i - 1) * X_ARM_SEGMENT_LENGTH + 0.6875, y + 0.125, z + 0.625));
//			//vertical segments
//			lightSegments.add(new AABB(x - widthTop + (i - 1) * X_ARM_SEGMENT_LENGTH + (0.6875 + 0.468765), y - 0.1875, z + 0.25, x - widthTop + (i - 1) * X_ARM_SEGMENT_LENGTH + (0.6875 + 0.53215), y + 0.1875, z + 0.3125));
//			lightSegments.add(new AABB(x - widthTop + (i - 1) * X_ARM_SEGMENT_LENGTH + (0.6875 + 0.468765), y - 0.1875, z + 0.6875, x - widthTop + (i - 1) * X_ARM_SEGMENT_LENGTH + (0.6875 + 0.53215), y + 0.1875, z + 0.75));
//			lightSegments.add(new AABB(x - widthTop + (i - 1) * X_ARM_SEGMENT_LENGTH + (0.6875 + 0.468765), y - 0.25, z + 0.3125, x - widthTop + (i - 1) * X_ARM_SEGMENT_LENGTH + (0.6875 + 0.53215), y - 0.1875, z + 0.6875));
//			lightSegments.add(new AABB(x - widthTop + (i - 1) * X_ARM_SEGMENT_LENGTH + (0.6875 + 0.468765), y + 0.1875, z + 0.3125, x - widthTop + (i - 1) * X_ARM_SEGMENT_LENGTH + (0.6875 + 0.53215), y + 0.25, z + 0.6875));
//			
//			
//			i--;
//		}
//		
//		//vertical lines
//		lightSegments.add(new AABB(x - remainderWidthTop + X_ARM_SEGMENT_LENGTH + 0.6875, y - 0.25, z + 0.25, x + 0.8125, y - 0.1875, z + 0.3125));
//		lightSegments.add(new AABB(x - remainderWidthTop + X_ARM_SEGMENT_LENGTH + 0.6875, y - 0.25, z + 0.6875, x + 0.8125, y - 0.1875, z + 0.75));
//		lightSegments.add(new AABB(x - remainderWidthTop + X_ARM_SEGMENT_LENGTH + 0.6875, y + 0.1875, z + 0.25, x + 0.8125, y + 0.25, z + 0.3125));
//		lightSegments.add(new AABB(x - remainderWidthTop + X_ARM_SEGMENT_LENGTH + 0.6875, y + 0.1875, z + 0.6875, x + 0.8125, y + 0.25, z + 0.75));
//		//center
//		darkSegments.add(new AABB(x - remainderWidthTop + X_ARM_SEGMENT_LENGTH + 0.6875, y - 0.125, z + 0.3875, x + 0.8125, y + 0.125, z + 0.625));
//		//vertical segments
//		lightSegments.add(new AABB(x - remainderWidthTop + (0.6875 + 0.468765), y - 0.1875, z + 0.25, x - remainderWidthTop + (0.6875 + 0.53215), y + 0.1875, z + 0.3125));
//		lightSegments.add(new AABB(x - remainderWidthTop + (0.6875 + 0.468765), y - 0.1875, z + 0.6875, x - remainderWidthTop + (0.6875 + 0.53215), y + 0.1875, z + 0.75));
//		lightSegments.add(new AABB(x - remainderWidthTop + (0.6875 + 0.468765), y - 0.25, z + 0.3125, x - remainderWidthTop + (0.6875 + 0.53215), y - 0.1875, z + 0.6875));
//		lightSegments.add(new AABB(x - remainderWidthTop + (0.6875 + 0.468765), y + 0.1875, z + 0.3125, x - remainderWidthTop + (0.6875 + 0.53215), y + 0.25, z + 0.6875));
//		
//	}
//	
//	private void eastOnRight(double x, double y, double z, List<Pair<PrecisionVector, AABB>> darkSegments, List<Pair<PrecisionVector, AABB>> lightSegments, double widthLeft, double widthRight, int wholeWidthLeft, int wholeWidthRight, double remainderWidthLeft, double remainderWidthRight) {
//		int i = 0;
//		int removal = 0;
//		/* LEFT */
//		
//		i = -1;
//		removal = remainderWidthLeft < -0.125 ? 1 : 0;
//		while(i > wholeWidthLeft - removal) {
//			//horizontal lines
//			lightSegments.add(new AABB(x + 0.25, y - 0.25, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + 0.6875, x + 0.3125, y - 0.1875, z - widthLeft + (i + 1) * Z_ARM_SEGMENT_LENGTH + 0.6875));
//			lightSegments.add(new AABB(x + 0.25, y + 0.1875, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + 0.6875, x + 0.3125, y + 0.25, z - widthLeft + (i + 1) * Z_ARM_SEGMENT_LENGTH + 0.6875));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.25, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + 0.6875, x + 0.75, y - 0.1875, z - widthLeft + (i + 1) * Z_ARM_SEGMENT_LENGTH + 0.6875));
//			lightSegments.add(new AABB(x + 0.6875, y + 0.1875, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + 0.6875, x + 0.75, y + 0.25, z - widthLeft + (i + 1) * Z_ARM_SEGMENT_LENGTH + 0.6875));
//			//center
//			darkSegments.add(new AABB(x + 0.3875, y - 0.125, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + 0.6875, x + 0.625, y + 0.125, z - widthLeft + (i + 1) * Z_ARM_SEGMENT_LENGTH + 0.6875));
//			//vertical lines
//			lightSegments.add(new AABB(x + 0.3125, y - 0.25, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.468765), x + 0.6875, y - 0.1875, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + (0.68755 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.3125, y + 0.1875, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.468765), x + 0.6875, y + 0.25, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.25, y - 0.1875, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.468765), x + 0.3125, y + 0.1875, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.1875, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.468765), x + 0.75, y + 0.1875, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.53215)));
//			
//			i--;
//		}
//		
//		if(removal == 1) {
//			//horizontal lines
//			lightSegments.add(new AABB(x + 0.25, y - 0.25, z - remainderWidthLeft + 0.6875, x + 0.3125, y - 0.1875, z + 0.8125));
//			lightSegments.add(new AABB(x + 0.25, y + 0.1875, z - remainderWidthLeft + 0.6875, x + 0.3125, y + 0.25, z + 0.8125));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.25, z - remainderWidthLeft + 0.6875, x + 0.75, y - 0.1875, z + 0.8125));
//			lightSegments.add(new AABB(x + 0.6875, y + 0.1875, z - remainderWidthLeft + 0.6875, x + 0.75, y + 0.25, z + 0.8125));
//			//center
//			darkSegments.add(new AABB(x + 0.3875, y - 0.125, z - remainderWidthLeft + 0.6875, x + 0.625, y + 0.125, z + 0.8125));
//			//vertical lines
//			lightSegments.add(new AABB(x + 0.3125, y - 0.25, z - remainderWidthLeft - Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.468765), x + 0.6875, y - 0.1875, z - Z_ARM_SEGMENT_LENGTH - remainderWidthLeft + (0.6875 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.3125, y + 0.1875, z - remainderWidthLeft - Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.468765), x + 0.6875, y + 0.25, z - Z_ARM_SEGMENT_LENGTH - remainderWidthLeft + (0.6875 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.25, y - 0.1875, z - remainderWidthLeft - Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.468765), x + 0.3125, y + 0.1875, z - Z_ARM_SEGMENT_LENGTH - remainderWidthLeft + (0.6875 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.1875, z - remainderWidthLeft - Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.468765), x + 0.75, y + 0.1875, z - Z_ARM_SEGMENT_LENGTH - remainderWidthLeft + (0.6875 + 0.53215)));
//		} else {
//			//horizontal lines
//			lightSegments.add(new AABB(x + 0.25, y - 0.25, z - remainderWidthLeft + Z_ARM_SEGMENT_LENGTH + 0.6875, x + 0.3125, y - 0.1875, z + 0.8125));
//			lightSegments.add(new AABB(x + 0.25, y + 0.1875, z - remainderWidthLeft + Z_ARM_SEGMENT_LENGTH + 0.6875, x + 0.3125, y + 0.25, z + 0.8125));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.25, z - remainderWidthLeft + Z_ARM_SEGMENT_LENGTH + 0.6875, x + 0.75, y - 0.1875, z + 0.8125));
//			lightSegments.add(new AABB(x + 0.6875, y + 0.1875, z - remainderWidthLeft + Z_ARM_SEGMENT_LENGTH + 0.6875, x + 0.75, y + 0.25, z + 0.8125));
//			//center
//			darkSegments.add(new AABB(x + 0.3875, y - 0.125, z - remainderWidthLeft + Z_ARM_SEGMENT_LENGTH + 0.6875, x + 0.625, y + 0.125, z + 0.8125));
//			//vertical lines
//			lightSegments.add(new AABB(x + 0.3125, y - 0.25, z - remainderWidthLeft + (0.6875 + 0.468765), x + 0.6875, y - 0.1875, z - remainderWidthLeft + (0.6875 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.3125, y + 0.1875, z - remainderWidthLeft + (0.6875 + 0.468765), x + 0.6875, y + 0.25, z - remainderWidthLeft + (0.6875 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.25, y - 0.1875, z - remainderWidthLeft + (0.6875 + 0.468765), x + 0.3125, y + 0.1875, z - remainderWidthLeft + (0.6875 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.1875, z - remainderWidthLeft + (0.6875 + 0.468765), x + 0.75, y + 0.1875, z - remainderWidthLeft + (0.6875 + 0.53215)));
//		}
//		
//		/* RIGHT */
//		
//		i = 0;
//		removal = remainderWidthRight < 0.125 ? 1 : 0;
//		while(i < wholeWidthRight - removal) {
//			//horizontal lines
//			lightSegments.add(new AABB(x + 0.25, y - 0.25, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + 0.3125, x + 0.3125, y - 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * (i + 1) + 0.3125));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.25, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + 0.3125, x + 0.75, y - 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * (i + 1) + 0.3125));
//			lightSegments.add(new AABB(x + 0.25, y + 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + 0.3125, x + 0.3125, y + 0.25, z - widthRight + Z_ARM_SEGMENT_LENGTH * (i + 1) + 0.3125));
//			lightSegments.add(new AABB(x + 0.6875, y + 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + 0.3125, x + 0.75, y + 0.25, z - widthRight + Z_ARM_SEGMENT_LENGTH * (i + 1) + 0.3125));
//			//center
//			darkSegments.add(new AABB(x + 0.3875, y - 0.125, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + 0.3125, x + 0.625, y + 0.125, z - widthRight + Z_ARM_SEGMENT_LENGTH * (i + 1) + 0.3125));
//			//vertical lines
//			lightSegments.add(new AABB(x + 0.3125, y - 0.25, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + (0.3125 + 0.468765), x + 0.6875, y - 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + (0.3125 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.3125, y + 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + (0.3125 + 0.468765), x + 0.6875, y + 0.25, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + (0.3125 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.25, y - 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + (0.3125 + 0.468765), x + 0.3125, y + 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + (0.3125 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + (0.3125 + 0.468765), x + 0.75, y + 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + (0.3125 + 0.53215)));
//			
//			i++;
//		}
//		
//		if(removal == 1) {
//			lightSegments.add(new AABB(x + 0.25, y - 0.25, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + 0.3125, x + 0.3125, y - 0.1875, z + 0.1875));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.25, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + 0.3125, x + 0.75, y - 0.1875, z + 0.1875));
//			lightSegments.add(new AABB(x + 0.25, y + 0.1875, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + 0.3125, x + 0.3125, y + 0.25, z + 0.1875));
//			lightSegments.add(new AABB(x + 0.6875, y + 0.1875, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + 0.3125, x + 0.75, y + 0.25, z + 0.1875));
//			//center
//			darkSegments.add(new AABB(x + 0.3875, y - 0.125, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + 0.3125, x + 0.625, y + 0.125, z + 0.1875));
//			//vertical lines
//			lightSegments.add(new AABB(x + 0.3125, y - 0.25, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), x + 0.6875, y - 0.1875, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.3125, y + 0.1875, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), x + 0.6875, y + 0.25, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.25, y - 0.1875, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), x + 0.3125, y + 0.1875, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.1875, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), x + 0.75, y + 0.1875, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.53215)));
//		} else {
//			lightSegments.add(new AABB(x + 0.25, y - 0.25, z - remainderWidthRight + 0.3125, x + 0.3125, y - 0.1875, z + 0.1875));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.25, z - remainderWidthRight + 0.3125, x + 0.75, y - 0.1875, z + 0.1875));
//			lightSegments.add(new AABB(x + 0.25, y + 0.1875, z - remainderWidthRight + 0.3125, x + 0.3125, y + 0.25, z + 0.1875));
//			lightSegments.add(new AABB(x + 0.6875, y + 0.1875, z - remainderWidthRight + 0.3125, x + 0.75, y + 0.25, z + 0.1875));
//			//center
//			darkSegments.add(new AABB(x + 0.3875, y - 0.125, z - remainderWidthRight + 0.3125, x + 0.625, y + 0.125, z + 0.1875));
//			//vertical lines
//			lightSegments.add(new AABB(x + 0.3125, y - 0.25, z - remainderWidthRight + (0.3125 + 0.468765), x + 0.6875, y - 0.1875, z - remainderWidthRight + (0.3125 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.3125, y + 0.1875, z - remainderWidthRight + (0.3125 + 0.468765), x + 0.6875, y + 0.25, z - remainderWidthRight + (0.3125 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.25, y - 0.1875, z - remainderWidthRight + (0.3125 + 0.468765), x + 0.3125, y + 0.1875, z - remainderWidthRight + (0.3125 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.1875, z - remainderWidthRight + (0.3125 + 0.468765), x + 0.75, y + 0.1875, z - remainderWidthRight + (0.3125 + 0.53215)));
//		}
//	}
//	
//	private void eastOnLeft(double x, double y, double z, List<Pair<PrecisionVector, AABB>> darkSegments, List<Pair<PrecisionVector, AABB>> lightSegments, double widthLeft, double widthRight, int wholeWidthLeft, int wholeWidthRight, double remainderWidthLeft, double remainderWidthRight) {
//		int i = 0;
//		int removal = 0;
//		/* LEFT */
//		
//		i = 0;
//		removal = remainderWidthLeft < 0.125 ? 1 : 0;
//		while(i < wholeWidthLeft - removal) {
//			//horizontal lines
//			lightSegments.add(new AABB(x + 0.25, y - 0.25, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + 0.3125, x + 0.3125, y - 0.1875, z - widthLeft + (i + 1) * Z_ARM_SEGMENT_LENGTH + 0.3125));
//			lightSegments.add(new AABB(x + 0.25, y + 0.1875, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + 0.3125, x + 0.3125, y + 0.25, z - widthLeft + (i + 1) * Z_ARM_SEGMENT_LENGTH + 0.3125));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.25, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + 0.3125, x + 0.75, y - 0.1875, z - widthLeft + (i + 1) * Z_ARM_SEGMENT_LENGTH + 0.3125));
//			lightSegments.add(new AABB(x + 0.6875, y + 0.1875, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + 0.3125, x + 0.75, y + 0.25, z - widthLeft + (i + 1) * Z_ARM_SEGMENT_LENGTH + 0.3125));
//			//center
//			darkSegments.add(new AABB(x + 0.3875, y - 0.125, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + 0.3125, x + 0.625, y + 0.125, z - widthLeft + (i + 1) * Z_ARM_SEGMENT_LENGTH + 0.3125));
//			//vertical lines
//			lightSegments.add(new AABB(x + 0.3125, y - 0.25, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), x + 0.6875, y - 0.1875, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.3125, y + 0.1875, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), x + 0.6875, y + 0.25, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.25, y - 0.1875, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), x + 0.3125, y + 0.1875, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.1875, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), x + 0.75, y + 0.1875, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.53215)));
//			
//			i++;
//		}
//		
//		if(removal == 1) {
//			//horizontal lines
//			lightSegments.add(new AABB(x + 0.25, y - 0.25, z - remainderWidthLeft - Z_ARM_SEGMENT_LENGTH + 0.3125, x + 0.3125, y - 0.1875, z + 0.1875));
//			lightSegments.add(new AABB(x + 0.25, y + 0.1875, z - remainderWidthLeft - Z_ARM_SEGMENT_LENGTH + 0.3125, x + 0.3125, y + 0.25, z + 0.1875));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.25, z - remainderWidthLeft - Z_ARM_SEGMENT_LENGTH + 0.3125, x + 0.75, y - 0.1875, z + 0.1875));
//			lightSegments.add(new AABB(x + 0.6875, y + 0.1875, z - remainderWidthLeft - Z_ARM_SEGMENT_LENGTH + 0.3125, x + 0.75, y + 0.25, z + 0.1875));
//			//center
//			darkSegments.add(new AABB(x + 0.3875, y - 0.125, z - remainderWidthLeft - Z_ARM_SEGMENT_LENGTH + 0.3125, x + 0.625, y + 0.125, z + 0.1875));
//			//vertical lines
//			lightSegments.add(new AABB(x + 0.3125, y - 0.25, z - remainderWidthLeft - Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), x + 0.6875, y - 0.1875, z - Z_ARM_SEGMENT_LENGTH - remainderWidthLeft + (0.3125 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.3125, y + 0.1875, z - remainderWidthLeft - Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), x + 0.6875, y + 0.25, z - Z_ARM_SEGMENT_LENGTH - remainderWidthLeft + (0.3125 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.25, y - 0.1875, z - remainderWidthLeft - Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), x + 0.3125, y + 0.1875, z - Z_ARM_SEGMENT_LENGTH - remainderWidthLeft + (0.3125 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.1875, z - remainderWidthLeft - Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), x + 0.75, y + 0.1875, z - Z_ARM_SEGMENT_LENGTH - remainderWidthLeft + (0.3125 + 0.53215)));
//		} else {
//			//horizontal lines
//			lightSegments.add(new AABB(x + 0.25, y - 0.25, z - remainderWidthLeft + 0.3125, x + 0.3125, y - 0.1875, z + 0.1875));
//			lightSegments.add(new AABB(x + 0.25, y + 0.1875, z - remainderWidthLeft + 0.3125, x + 0.3125, y + 0.25, z + 0.1875));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.25, z - remainderWidthLeft + 0.3125, x + 0.75, y - 0.1875, z + 0.1875));
//			lightSegments.add(new AABB(x + 0.6875, y + 0.1875, z - remainderWidthLeft + 0.3125, x + 0.75, y + 0.25, z + 0.1875));
//			//center
//			darkSegments.add(new AABB(x + 0.3875, y - 0.125, z - remainderWidthLeft + 0.3125, x + 0.625, y + 0.125, z + 0.1875));
//			//vertical lines
//			lightSegments.add(new AABB(x + 0.3125, y - 0.25, z - remainderWidthLeft + (0.3125 + 0.468765), x + 0.6875, y - 0.1875, z - remainderWidthLeft + (0.3125 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.3125, y + 0.1875, z - remainderWidthLeft + (0.3125 + 0.468765), x + 0.6875, y + 0.25, z - remainderWidthLeft + (0.3125 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.25, y - 0.1875, z - remainderWidthLeft + (0.3125 + 0.468765), x + 0.3125, y + 0.1875, z - remainderWidthLeft + (0.3125 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.1875, z - remainderWidthLeft + (0.3125 + 0.468765), x + 0.75, y + 0.1875, z - remainderWidthLeft + (0.3125 + 0.53215)));
//		}
//		
//		/* RIGHT */
//		
//		i = -1;
//		removal = remainderWidthRight < -0.125 ? 1 : 0;
//		while(i > wholeWidthRight - removal) {
//			//horizontal lines
//			lightSegments.add(new AABB(x + 0.25, y - 0.25, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + 0.6875, x + 0.3125, y - 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * (i + 1) + 0.6875));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.25, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + 0.6875, x + 0.75, y - 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * (i + 1) + 0.6875));
//			lightSegments.add(new AABB(x + 0.25, y + 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + 0.6875, x + 0.3125, y + 0.25, z - widthRight + Z_ARM_SEGMENT_LENGTH * (i + 1) + 0.6875));
//			lightSegments.add(new AABB(x + 0.6875, y + 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + 0.6875, x + 0.75, y + 0.25, z - widthRight + Z_ARM_SEGMENT_LENGTH * (i + 1) + 0.6875));
//			//center
//			darkSegments.add(new AABB(x + 0.3875, y - 0.125, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + 0.6875, x + 0.625, y + 0.125, z - widthRight + Z_ARM_SEGMENT_LENGTH * (i + 1) + 0.6875));
//			//vertical lines
//			lightSegments.add(new AABB(x + 0.3125, y - 0.25, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + (0.6875 + 0.468765), x + 0.6875, y - 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + (0.6875 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.3125, y + 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + (0.6875 + 0.468765), x + 0.6875, y + 0.25, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + (0.6875 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.25, y - 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + (0.6875 + 0.468765), x + 0.3125, y + 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + (0.6875 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + (0.6875 + 0.468765), x + 0.75, y + 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + (0.6875 + 0.53215)));
//			
//			i--;
//		}
//		
//		if(removal == 1) {
//			lightSegments.add(new AABB(x + 0.25, y - 0.25, z - remainderWidthRight + 0.6875, x + 0.3125, y - 0.1875, z + 0.8125));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.25, z - remainderWidthRight + 0.6875, x + 0.75, y - 0.1875, z + 0.8125));
//			lightSegments.add(new AABB(x + 0.25, y + 0.1875, z - remainderWidthRight + 0.6875, x + 0.3125, y + 0.25, z + 0.8125));
//			lightSegments.add(new AABB(x + 0.6875, y + 0.1875, z - remainderWidthRight + 0.6875, x + 0.75, y + 0.25, z + 0.8125));
//			//center
//			darkSegments.add(new AABB(x + 0.3875, y - 0.125, z - remainderWidthRight + 0.6875, x + 0.625, y + 0.125, z + 0.8125));
//			//vertical lines
//			lightSegments.add(new AABB(x + 0.3125, y - 0.25, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.468765), x + 0.6875, y - 0.1875, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.3125, y + 0.1875, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.468765), x + 0.6875, y + 0.25, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.25, y - 0.1875, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.468765), x + 0.3125, y + 0.1875, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.1875, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.468765), x + 0.75, y + 0.1875, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.53215)));
//		} else {
//			lightSegments.add(new AABB(x + 0.25, y - 0.25, z + Z_ARM_SEGMENT_LENGTH - remainderWidthRight + 0.6875, x + 0.3125, y - 0.1875, z + 0.8125));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.25, z + Z_ARM_SEGMENT_LENGTH - remainderWidthRight + 0.6875, x + 0.75, y - 0.1875, z + 0.8125));
//			lightSegments.add(new AABB(x + 0.25, y + 0.1875, z + Z_ARM_SEGMENT_LENGTH - remainderWidthRight + 0.6875, x + 0.3125, y + 0.25, z + 0.8125));
//			lightSegments.add(new AABB(x + 0.6875, y + 0.1875, z + Z_ARM_SEGMENT_LENGTH - remainderWidthRight + 0.6875, x + 0.75, y + 0.25, z + 0.8125));
//			//center
//			darkSegments.add(new AABB(x + 0.3875, y - 0.125, z + Z_ARM_SEGMENT_LENGTH - remainderWidthRight + 0.6875, x + 0.625, y + 0.125, z + 0.8125));
//			//vertical lines
//			lightSegments.add(new AABB(x + 0.3125, y - 0.25, z - remainderWidthRight + (0.6875 + 0.468765), x + 0.6875, y - 0.1875, z - remainderWidthRight + (0.6875 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.3125, y + 0.1875, z - remainderWidthRight + (0.6875 + 0.468765), x + 0.6875, y + 0.25, z - remainderWidthRight + (0.6875 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.25, y - 0.1875, z - remainderWidthRight + (0.6875 + 0.468765), x + 0.3125, y + 0.1875, z - remainderWidthRight + (0.6875 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.1875, z - remainderWidthRight + (0.6875 + 0.468765), x + 0.75, y + 0.1875, z - remainderWidthRight + (0.6875 + 0.53215)));
//		}
//		
//	}
//	
//	private void west(double x, double y, double z, List<Pair<PrecisionVector, AABB>> darkSegments, List<Pair<PrecisionVector, AABB>> lightSegments, double widthLeft, double widthRight, double widthTop, double widthBottom, int wholeWidthLeft, int wholeWidthRight, int wholeWidthTop, int wholeWidthBottom, double remainderWidthLeft, double remainderWidthRight, double remainderWidthTop, double remainderWidthBottom) {
//		int i = 0;
//		if (cornerOnRight.get()) {
//			westOnRight(x, y, z, darkSegments, lightSegments, widthLeft, widthRight, wholeWidthLeft, wholeWidthRight, remainderWidthLeft, remainderWidthRight);
//		} else {
//			westOnLeft(x, y, z, darkSegments, lightSegments, widthLeft, widthRight, wholeWidthLeft, wholeWidthRight, remainderWidthLeft, remainderWidthRight);
//		}
//		
//		/* BOTTOM */
//		
//		i = -1;
//		while(i > wholeWidthBottom) {
//			//vertical lines
//			lightSegments.add(new AABB(x - widthBottom + i * X_ARM_SEGMENT_LENGTH + 0.6875, y - 0.25, z + 0.25, x - widthBottom + (i + 1) * X_ARM_SEGMENT_LENGTH + 0.6875, y - 0.1875, z + 0.3125));
//			lightSegments.add(new AABB(x - widthBottom + i * X_ARM_SEGMENT_LENGTH + 0.6875, y - 0.25, z + 0.6875, x - widthBottom + (i + 1) * X_ARM_SEGMENT_LENGTH + 0.6875, y - 0.1875, z + 0.75));
//			lightSegments.add(new AABB(x - widthBottom + i * X_ARM_SEGMENT_LENGTH + 0.6875, y + 0.1875, z + 0.25, x - widthBottom + (i + 1) * X_ARM_SEGMENT_LENGTH + 0.6875, y + 0.25, z + 0.3125));
//			lightSegments.add(new AABB(x - widthBottom + i * X_ARM_SEGMENT_LENGTH + 0.6875, y + 0.1875, z + 0.6875, x - widthBottom + (i + 1) * X_ARM_SEGMENT_LENGTH + 0.6875, y + 0.25, z + 0.75));
//			//center
//			darkSegments.add(new AABB(x - widthBottom + i * X_ARM_SEGMENT_LENGTH + 0.6875, y - 0.125, z + 0.3875, x - widthBottom + (i + 1) * X_ARM_SEGMENT_LENGTH + 0.6875, y + 0.125, z + 0.625));
//			//vertical lines
//			lightSegments.add(new AABB(x - widthBottom + i * X_ARM_SEGMENT_LENGTH + (0.6875 + 0.468765), y - 0.25, z + 0.3125, x - widthBottom + i * X_ARM_SEGMENT_LENGTH + (0.6875 + 0.53215), y - 0.1875, z + 0.6875));
//			lightSegments.add(new AABB(x - widthBottom + i * X_ARM_SEGMENT_LENGTH + (0.6875 + 0.468765), y + 0.1875, z + 0.3125, x - widthBottom + i * X_ARM_SEGMENT_LENGTH + (0.6875 + 0.53215), y + 0.25, z + 0.6875));
//			lightSegments.add(new AABB(x - widthBottom + i * X_ARM_SEGMENT_LENGTH + (0.6875 + 0.468765), y - 0.1875, z + 0.25, x - widthBottom + i * X_ARM_SEGMENT_LENGTH + (0.6875 + 0.53215), y + 0.1875, z + 0.3125));
//			lightSegments.add(new AABB(x - widthBottom + i * X_ARM_SEGMENT_LENGTH + (0.6875 + 0.468765), y - 0.1875, z + 0.6875, x - widthBottom + i * X_ARM_SEGMENT_LENGTH + (0.6875 + 0.53215), y + 0.1875, z + 0.75));
//			
//			i--;
//		}
//		
//		//vertical lines
//		lightSegments.add(new AABB(x - remainderWidthBottom + 0.6875 + X_ARM_SEGMENT_LENGTH, y - 0.25, z + 0.25, x + 0.8125, y - 0.1875, z + 0.3125));
//		lightSegments.add(new AABB(x - remainderWidthBottom + 0.6875 + X_ARM_SEGMENT_LENGTH, y - 0.25, z + 0.6875, x + 0.8125, y - 0.1875, z + 0.75));
//		lightSegments.add(new AABB(x - remainderWidthBottom + 0.6875 + X_ARM_SEGMENT_LENGTH, y + 0.1875, z + 0.25, x + 0.8125, y + 0.25, z + 0.3125));
//		lightSegments.add(new AABB(x - remainderWidthBottom + 0.6875 + X_ARM_SEGMENT_LENGTH, y + 0.1875, z + 0.6875, x + 0.8125, y + 0.25, z + 0.75));
//		//center
//		darkSegments.add(new AABB(x - remainderWidthBottom + 0.6875 + X_ARM_SEGMENT_LENGTH, y - 0.125, z + 0.3875, x + 0.8125, y + 0.125, z + 0.625));
//		//vertical lines
//		lightSegments.add(new AABB(x - remainderWidthBottom + (0.6875 + 0.468765), y - 0.25, z + 0.3125, x - remainderWidthBottom + (0.6875 + 0.53215), y - 0.1875, z + 0.6875));
//		lightSegments.add(new AABB(x - remainderWidthBottom + (0.6875 + 0.468765), y + 0.1875, z + 0.3125, x - remainderWidthBottom + (0.6875 + 0.53215), y + 0.25, z + 0.6875));
//		lightSegments.add(new AABB(x - remainderWidthBottom + (0.6875 + 0.468765), y - 0.1875, z + 0.25, x - remainderWidthBottom + (0.6875 + 0.53215), y + 0.1875, z + 0.3125));
//		lightSegments.add(new AABB(x - remainderWidthBottom + (0.6875 + 0.468765), y - 0.1875, z + 0.6875, x - remainderWidthBottom + (0.6875 + 0.53215), y + 0.1875, z + 0.75));
//		
//		/* TOP */
//		
//		i = 1;
//		while(i < wholeWidthTop) {
//			//vertical lines
//			lightSegments.add(new AABB(x - widthTop + i * X_ARM_SEGMENT_LENGTH + 0.3125, y - 0.25, z + 0.25, x - widthTop + (i - 1) * X_ARM_SEGMENT_LENGTH + 0.3125, y - 0.1875, z + 0.3125));
//			lightSegments.add(new AABB(x - widthTop + i * X_ARM_SEGMENT_LENGTH + 0.3125, y - 0.25, z + 0.6875, x - widthTop + (i - 1) * X_ARM_SEGMENT_LENGTH + 0.3125, y - 0.1875, z + 0.75));
//			lightSegments.add(new AABB(x - widthTop + i * X_ARM_SEGMENT_LENGTH + 0.3125, y + 0.1875, z + 0.25, x - widthTop + (i - 1) * X_ARM_SEGMENT_LENGTH + 0.3125, y + 0.25, z + 0.3125));
//			lightSegments.add(new AABB(x - widthTop + i * X_ARM_SEGMENT_LENGTH + 0.3125, y + 0.1875, z + 0.6875, x - widthTop + (i - 1) * X_ARM_SEGMENT_LENGTH + 0.3125, y + 0.25, z + 0.75));
//			//center
//			darkSegments.add(new AABB(x - widthTop + i * X_ARM_SEGMENT_LENGTH + 0.3125, y - 0.125, z + 0.3875, x - widthTop + (i - 1) * X_ARM_SEGMENT_LENGTH + 0.3125, y + 0.125, z + 0.625));
//			//vertical segments
//			lightSegments.add(new AABB(x - widthTop + (i - 1) * X_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), y - 0.1875, z + 0.25, x - widthTop + (i - 1) * X_ARM_SEGMENT_LENGTH + (0.3125 + 0.53215), y + 0.1875, z + 0.3125));
//			lightSegments.add(new AABB(x - widthTop + (i - 1) * X_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), y - 0.1875, z + 0.6875, x - widthTop + (i - 1) * X_ARM_SEGMENT_LENGTH + (0.3125 + 0.53215), y + 0.1875, z + 0.75));
//			lightSegments.add(new AABB(x - widthTop + (i - 1) * X_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), y - 0.25, z + 0.3125, x - widthTop + (i - 1) * X_ARM_SEGMENT_LENGTH + (0.3125 + 0.53215), y - 0.1875, z + 0.6875));
//			lightSegments.add(new AABB(x - widthTop + (i - 1) * X_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), y + 0.1875, z + 0.3125, x - widthTop + (i - 1) * X_ARM_SEGMENT_LENGTH + (0.3125 + 0.53215), y + 0.25, z + 0.6875));
//			
//			
//			i++;
//		}
//		
//		//vertical lines
//		lightSegments.add(new AABB(x - remainderWidthTop - X_ARM_SEGMENT_LENGTH + 0.3125, y - 0.25, z + 0.25, x + 0.1875, y - 0.1875, z + 0.3125));
//		lightSegments.add(new AABB(x - remainderWidthTop - X_ARM_SEGMENT_LENGTH + 0.3125, y - 0.25, z + 0.6875, x + 0.1875, y - 0.1875, z + 0.75));
//		lightSegments.add(new AABB(x - remainderWidthTop - X_ARM_SEGMENT_LENGTH + 0.3125, y + 0.1875, z + 0.25, x + 0.1875, y + 0.25, z + 0.3125));
//		lightSegments.add(new AABB(x - remainderWidthTop - X_ARM_SEGMENT_LENGTH + 0.3125, y + 0.1875, z + 0.6875, x + 0.1875, y + 0.25, z + 0.75));
//		//center
//		darkSegments.add(new AABB(x - remainderWidthTop - X_ARM_SEGMENT_LENGTH + 0.3125, y - 0.125, z + 0.3875, x + 0.1875, y + 0.125, z + 0.625));
//		//vertical segments
//		lightSegments.add(new AABB(x - remainderWidthTop - X_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), y - 0.1875, z + 0.25, x - X_ARM_SEGMENT_LENGTH - remainderWidthTop + (0.3125 + 0.53215), y + 0.1875, z + 0.3125));
//		lightSegments.add(new AABB(x - remainderWidthTop - X_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), y - 0.1875, z + 0.6875, x - X_ARM_SEGMENT_LENGTH - remainderWidthTop + (0.3125 + 0.53215), y + 0.1875, z + 0.75));
//		lightSegments.add(new AABB(x - remainderWidthTop - X_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), y - 0.25, z + 0.3125, x - X_ARM_SEGMENT_LENGTH - remainderWidthTop + (0.3125 + 0.53215), y - 0.1875, z + 0.6875));
//		lightSegments.add(new AABB(x - remainderWidthTop - X_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), y + 0.1875, z + 0.3125, x - X_ARM_SEGMENT_LENGTH - remainderWidthTop + (0.3125 + 0.53215), y + 0.25, z + 0.6875));
//		
//	}
//	
//	private void westOnRight(double x, double y, double z, List<Pair<PrecisionVector, AABB>> darkSegments, List<Pair<PrecisionVector, AABB>> lightSegments, double widthLeft, double widthRight, int wholeWidthLeft, int wholeWidthRight, double remainderWidthLeft, double remainderWidthRight) {
//		int i = 0;
//		int removal = 0;
//		/* LEFT */
//		
//		i = 0;
//		removal = remainderWidthLeft < 0.125 ? 1 : 0;
//		while(i < wholeWidthLeft - removal) {
//			//horizontal lines
//			lightSegments.add(new AABB(x + 0.25, y - 0.25, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + 0.3125, x + 0.3125, y - 0.1875, z - widthLeft + (i + 1) * Z_ARM_SEGMENT_LENGTH + 0.3125));
//			lightSegments.add(new AABB(x + 0.25, y + 0.1875, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + 0.3125, x + 0.3125, y + 0.25, z - widthLeft + (i + 1) * Z_ARM_SEGMENT_LENGTH + 0.3125));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.25, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + 0.3125, x + 0.75, y - 0.1875, z - widthLeft + (i + 1) * Z_ARM_SEGMENT_LENGTH + 0.3125));
//			lightSegments.add(new AABB(x + 0.6875, y + 0.1875, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + 0.3125, x + 0.75, y + 0.25, z - widthLeft + (i + 1) * Z_ARM_SEGMENT_LENGTH + 0.3125));
//			//center
//			darkSegments.add(new AABB(x + 0.3875, y - 0.125, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + 0.3125, x + 0.625, y + 0.125, z - widthLeft + (i + 1) * Z_ARM_SEGMENT_LENGTH + 0.3125));
//			//vertical lines
//			lightSegments.add(new AABB(x + 0.3125, y - 0.25, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), x + 0.6875, y - 0.1875, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.3125, y + 0.1875, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), x + 0.6875, y + 0.25, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.25, y - 0.1875, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), x + 0.3125, y + 0.1875, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.1875, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), x + 0.75, y + 0.1875, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.53215)));
//			
//			i++;
//		}
//		
//		if(removal == 1) {
//			//horizontal lines
//			lightSegments.add(new AABB(x + 0.25, y - 0.25, z - remainderWidthLeft - Z_ARM_SEGMENT_LENGTH + 0.3125, x + 0.3125, y - 0.1875, z + 0.1875));
//			lightSegments.add(new AABB(x + 0.25, y + 0.1875, z - remainderWidthLeft - Z_ARM_SEGMENT_LENGTH + 0.3125, x + 0.3125, y + 0.25, z + 0.1875));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.25, z - remainderWidthLeft - Z_ARM_SEGMENT_LENGTH + 0.3125, x + 0.75, y - 0.1875, z + 0.1875));
//			lightSegments.add(new AABB(x + 0.6875, y + 0.1875, z - remainderWidthLeft - Z_ARM_SEGMENT_LENGTH + 0.3125, x + 0.75, y + 0.25, z + 0.1875));
//			//center
//			darkSegments.add(new AABB(x + 0.3875, y - 0.125, z - remainderWidthLeft - Z_ARM_SEGMENT_LENGTH + 0.3125, x + 0.625, y + 0.125, z + 0.1875));
//			//vertical lines
//			lightSegments.add(new AABB(x + 0.3125, y - 0.25, z - remainderWidthLeft - Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), x + 0.6875, y - 0.1875, z - Z_ARM_SEGMENT_LENGTH - remainderWidthLeft + (0.3125 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.3125, y + 0.1875, z - remainderWidthLeft - Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), x + 0.6875, y + 0.25, z - Z_ARM_SEGMENT_LENGTH - remainderWidthLeft + (0.3125 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.25, y - 0.1875, z - remainderWidthLeft - Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), x + 0.3125, y + 0.1875, z - Z_ARM_SEGMENT_LENGTH - remainderWidthLeft + (0.3125 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.1875, z - remainderWidthLeft - Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), x + 0.75, y + 0.1875, z - Z_ARM_SEGMENT_LENGTH - remainderWidthLeft + (0.3125 + 0.53215)));
//		} else {
//			//horizontal lines
//			lightSegments.add(new AABB(x + 0.25, y - 0.25, z - remainderWidthLeft + 0.3125, x + 0.3125, y - 0.1875, z + 0.1875));
//			lightSegments.add(new AABB(x + 0.25, y + 0.1875, z - remainderWidthLeft + 0.3125, x + 0.3125, y + 0.25, z + 0.1875));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.25, z - remainderWidthLeft + 0.3125, x + 0.75, y - 0.1875, z + 0.1875));
//			lightSegments.add(new AABB(x + 0.6875, y + 0.1875, z - remainderWidthLeft + 0.3125, x + 0.75, y + 0.25, z + 0.1875));
//			//center
//			darkSegments.add(new AABB(x + 0.3875, y - 0.125, z - remainderWidthLeft + 0.3125, x + 0.625, y + 0.125, z + 0.1875));
//			//vertical lines
//			lightSegments.add(new AABB(x + 0.3125, y - 0.25, z - remainderWidthLeft + (0.3125 + 0.468765), x + 0.6875, y - 0.1875, z - remainderWidthLeft + (0.3125 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.3125, y + 0.1875, z - remainderWidthLeft + (0.3125 + 0.468765), x + 0.6875, y + 0.25, z - remainderWidthLeft + (0.3125 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.25, y - 0.1875, z - remainderWidthLeft + (0.3125 + 0.468765), x + 0.3125, y + 0.1875, z - remainderWidthLeft + (0.3125 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.1875, z - remainderWidthLeft + (0.3125 + 0.468765), x + 0.75, y + 0.1875, z - remainderWidthLeft + (0.3125 + 0.53215)));
//		}
//		
//		/* RIGHT */
//		
//		i = -1;
//		removal = remainderWidthRight < -0.125 ? 1 : 0;
//		while(i > wholeWidthRight - removal) {
//			//horizontal lines
//			lightSegments.add(new AABB(x + 0.25, y - 0.25, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + 0.6875, x + 0.3125, y - 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * (i + 1) + 0.6875));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.25, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + 0.6875, x + 0.75, y - 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * (i + 1) + 0.6875));
//			lightSegments.add(new AABB(x + 0.25, y + 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + 0.6875, x + 0.3125, y + 0.25, z - widthRight + Z_ARM_SEGMENT_LENGTH * (i + 1) + 0.6875));
//			lightSegments.add(new AABB(x + 0.6875, y + 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + 0.6875, x + 0.75, y + 0.25, z - widthRight + Z_ARM_SEGMENT_LENGTH * (i + 1) + 0.6875));
//			//center
//			darkSegments.add(new AABB(x + 0.3875, y - 0.125, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + 0.6875, x + 0.625, y + 0.125, z - widthRight + Z_ARM_SEGMENT_LENGTH * (i + 1) + 0.6875));
//			//vertical lines
//			lightSegments.add(new AABB(x + 0.3125, y - 0.25, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + (0.6875 + 0.468765), x + 0.6875, y - 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + (0.6875 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.3125, y + 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + (0.6875 + 0.468765), x + 0.6875, y + 0.25, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + (0.6875 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.25, y - 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + (0.6875 + 0.468765), x + 0.3125, y + 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + (0.6875 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + (0.6875 + 0.468765), x + 0.75, y + 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + (0.6875 + 0.53215)));
//			
//			i--;
//		}
//		
//		if(removal == 1) {
//			lightSegments.add(new AABB(x + 0.25, y - 0.25, z - remainderWidthRight + 0.6875, x + 0.3125, y - 0.1875, z + 0.8125));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.25, z - remainderWidthRight + 0.6875, x + 0.75, y - 0.1875, z + 0.8125));
//			lightSegments.add(new AABB(x + 0.25, y + 0.1875, z - remainderWidthRight + 0.6875, x + 0.3125, y + 0.25, z + 0.8125));
//			lightSegments.add(new AABB(x + 0.6875, y + 0.1875, z - remainderWidthRight + 0.6875, x + 0.75, y + 0.25, z + 0.8125));
//			//center
//			darkSegments.add(new AABB(x + 0.3875, y - 0.125, z - remainderWidthRight + 0.6875, x + 0.625, y + 0.125, z + 0.8125));
//			//vertical lines
//			lightSegments.add(new AABB(x + 0.3125, y - 0.25, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.468765), x + 0.6875, y - 0.1875, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.3125, y + 0.1875, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.468765), x + 0.6875, y + 0.25, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.25, y - 0.1875, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.468765), x + 0.3125, y + 0.1875, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.1875, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.468765), x + 0.75, y + 0.1875, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.53215)));
//		} else {
//			lightSegments.add(new AABB(x + 0.25, y - 0.25, z + Z_ARM_SEGMENT_LENGTH - remainderWidthRight + 0.6875, x + 0.3125, y - 0.1875, z + 0.8125));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.25, z + Z_ARM_SEGMENT_LENGTH - remainderWidthRight + 0.6875, x + 0.75, y - 0.1875, z + 0.8125));
//			lightSegments.add(new AABB(x + 0.25, y + 0.1875, z + Z_ARM_SEGMENT_LENGTH - remainderWidthRight + 0.6875, x + 0.3125, y + 0.25, z + 0.8125));
//			lightSegments.add(new AABB(x + 0.6875, y + 0.1875, z + Z_ARM_SEGMENT_LENGTH - remainderWidthRight + 0.6875, x + 0.75, y + 0.25, z + 0.8125));
//			//center
//			darkSegments.add(new AABB(x + 0.3875, y - 0.125, z + Z_ARM_SEGMENT_LENGTH - remainderWidthRight + 0.6875, x + 0.625, y + 0.125, z + 0.8125));
//			//vertical lines
//			lightSegments.add(new AABB(x + 0.3125, y - 0.25, z - remainderWidthRight + (0.6875 + 0.468765), x + 0.6875, y - 0.1875, z - remainderWidthRight + (0.6875 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.3125, y + 0.1875, z - remainderWidthRight + (0.6875 + 0.468765), x + 0.6875, y + 0.25, z - remainderWidthRight + (0.6875 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.25, y - 0.1875, z - remainderWidthRight + (0.6875 + 0.468765), x + 0.3125, y + 0.1875, z - remainderWidthRight + (0.6875 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.1875, z - remainderWidthRight + (0.6875 + 0.468765), x + 0.75, y + 0.1875, z - remainderWidthRight + (0.6875 + 0.53215)));
//		}
//		
//	}
//	
//	private void westOnLeft(double x, double y, double z, List<Pair<PrecisionVector, AABB>> darkSegments, List<Pair<PrecisionVector, AABB>> lightSegments, double widthLeft, double widthRight, int wholeWidthLeft, int wholeWidthRight, double remainderWidthLeft, double remainderWidthRight) {
//		int i = 0;
//		int removal = 0;
//		
//		/* LEFT */
//		
//		i = -1;
//		removal = remainderWidthLeft < -0.125 ? 1 : 0;
//		while(i > wholeWidthLeft - removal) {
//			//horizontal lines
//			lightSegments.add(new AABB(x + 0.25, y - 0.25, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + 0.6875, x + 0.3125, y - 0.1875, z - widthLeft + (i + 1) * Z_ARM_SEGMENT_LENGTH + 0.6875));
//			lightSegments.add(new AABB(x + 0.25, y + 0.1875, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + 0.6875, x + 0.3125, y + 0.25, z - widthLeft + (i + 1) * Z_ARM_SEGMENT_LENGTH + 0.6875));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.25, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + 0.6875, x + 0.75, y - 0.1875, z - widthLeft + (i + 1) * Z_ARM_SEGMENT_LENGTH + 0.6875));
//			lightSegments.add(new AABB(x + 0.6875, y + 0.1875, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + 0.6875, x + 0.75, y + 0.25, z - widthLeft + (i + 1) * Z_ARM_SEGMENT_LENGTH + 0.6875));
//			//center
//			darkSegments.add(new AABB(x + 0.3875, y - 0.125, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + 0.6875, x + 0.625, y + 0.125, z - widthLeft + (i + 1) * Z_ARM_SEGMENT_LENGTH + 0.6875));
//			//vertical lines
//			lightSegments.add(new AABB(x + 0.3125, y - 0.25, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.468765), x + 0.6875, y - 0.1875, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + (0.68755 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.3125, y + 0.1875, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.468765), x + 0.6875, y + 0.25, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.25, y - 0.1875, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.468765), x + 0.3125, y + 0.1875, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.1875, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.468765), x + 0.75, y + 0.1875, z - widthLeft + i * Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.53215)));
//			
//			i--;
//		}
//		
//		if(removal == 1) {
//			//horizontal lines
//			lightSegments.add(new AABB(x + 0.25, y - 0.25, z - remainderWidthLeft + 0.6875, x + 0.3125, y - 0.1875, z + 0.8125));
//			lightSegments.add(new AABB(x + 0.25, y + 0.1875, z - remainderWidthLeft + 0.6875, x + 0.3125, y + 0.25, z + 0.8125));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.25, z - remainderWidthLeft + 0.6875, x + 0.75, y - 0.1875, z + 0.8125));
//			lightSegments.add(new AABB(x + 0.6875, y + 0.1875, z - remainderWidthLeft + 0.6875, x + 0.75, y + 0.25, z + 0.8125));
//			//center
//			darkSegments.add(new AABB(x + 0.3875, y - 0.125, z - remainderWidthLeft + 0.6875, x + 0.625, y + 0.125, z + 0.8125));
//			//vertical lines
//			lightSegments.add(new AABB(x + 0.3125, y - 0.25, z - remainderWidthLeft - Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.468765), x + 0.6875, y - 0.1875, z - Z_ARM_SEGMENT_LENGTH - remainderWidthLeft + (0.6875 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.3125, y + 0.1875, z - remainderWidthLeft - Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.468765), x + 0.6875, y + 0.25, z - Z_ARM_SEGMENT_LENGTH - remainderWidthLeft + (0.6875 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.25, y - 0.1875, z - remainderWidthLeft - Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.468765), x + 0.3125, y + 0.1875, z - Z_ARM_SEGMENT_LENGTH - remainderWidthLeft + (0.6875 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.1875, z - remainderWidthLeft - Z_ARM_SEGMENT_LENGTH + (0.6875 + 0.468765), x + 0.75, y + 0.1875, z - Z_ARM_SEGMENT_LENGTH - remainderWidthLeft + (0.6875 + 0.53215)));
//		} else {
//			//horizontal lines
//			lightSegments.add(new AABB(x + 0.25, y - 0.25, z - remainderWidthLeft + Z_ARM_SEGMENT_LENGTH + 0.6875, x + 0.3125, y - 0.1875, z + 0.8125));
//			lightSegments.add(new AABB(x + 0.25, y + 0.1875, z - remainderWidthLeft + Z_ARM_SEGMENT_LENGTH + 0.6875, x + 0.3125, y + 0.25, z + 0.8125));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.25, z - remainderWidthLeft + Z_ARM_SEGMENT_LENGTH + 0.6875, x + 0.75, y - 0.1875, z + 0.8125));
//			lightSegments.add(new AABB(x + 0.6875, y + 0.1875, z - remainderWidthLeft + Z_ARM_SEGMENT_LENGTH + 0.6875, x + 0.75, y + 0.25, z + 0.8125));
//			//center
//			darkSegments.add(new AABB(x + 0.3875, y - 0.125, z - remainderWidthLeft + Z_ARM_SEGMENT_LENGTH + 0.6875, x + 0.625, y + 0.125, z + 0.8125));
//			//vertical lines
//			lightSegments.add(new AABB(x + 0.3125, y - 0.25, z - remainderWidthLeft + (0.6875 + 0.468765), x + 0.6875, y - 0.1875, z - remainderWidthLeft + (0.6875 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.3125, y + 0.1875, z - remainderWidthLeft + (0.6875 + 0.468765), x + 0.6875, y + 0.25, z - remainderWidthLeft + (0.6875 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.25, y - 0.1875, z - remainderWidthLeft + (0.6875 + 0.468765), x + 0.3125, y + 0.1875, z - remainderWidthLeft + (0.6875 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.1875, z - remainderWidthLeft + (0.6875 + 0.468765), x + 0.75, y + 0.1875, z - remainderWidthLeft + (0.6875 + 0.53215)));
//		}
//		
//		/* RIGHT */
//		
//		i = 0;
//		removal = remainderWidthRight < 0.125 ? 1 : 0;
//		while(i < wholeWidthRight - removal) {
//			//horizontal lines
//			lightSegments.add(new AABB(x + 0.25, y - 0.25, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + 0.3125, x + 0.3125, y - 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * (i + 1) + 0.3125));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.25, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + 0.3125, x + 0.75, y - 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * (i + 1) + 0.3125));
//			lightSegments.add(new AABB(x + 0.25, y + 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + 0.3125, x + 0.3125, y + 0.25, z - widthRight + Z_ARM_SEGMENT_LENGTH * (i + 1) + 0.3125));
//			lightSegments.add(new AABB(x + 0.6875, y + 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + 0.3125, x + 0.75, y + 0.25, z - widthRight + Z_ARM_SEGMENT_LENGTH * (i + 1) + 0.3125));
//			//center
//			darkSegments.add(new AABB(x + 0.3875, y - 0.125, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + 0.3125, x + 0.625, y + 0.125, z - widthRight + Z_ARM_SEGMENT_LENGTH * (i + 1) + 0.3125));
//			//vertical lines
//			lightSegments.add(new AABB(x + 0.3125, y - 0.25, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + (0.3125 + 0.468765), x + 0.6875, y - 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + (0.3125 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.3125, y + 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + (0.3125 + 0.468765), x + 0.6875, y + 0.25, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + (0.3125 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.25, y - 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + (0.3125 + 0.468765), x + 0.3125, y + 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + (0.3125 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + (0.3125 + 0.468765), x + 0.75, y + 0.1875, z - widthRight + Z_ARM_SEGMENT_LENGTH * i + (0.3125 + 0.53215)));
//			
//			i++;
//		}
//		
//		if(removal == 1) {
//			lightSegments.add(new AABB(x + 0.25, y - 0.25, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + 0.3125, x + 0.3125, y - 0.1875, z + 0.1875));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.25, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + 0.3125, x + 0.75, y - 0.1875, z + 0.1875));
//			lightSegments.add(new AABB(x + 0.25, y + 0.1875, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + 0.3125, x + 0.3125, y + 0.25, z + 0.1875));
//			lightSegments.add(new AABB(x + 0.6875, y + 0.1875, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + 0.3125, x + 0.75, y + 0.25, z + 0.1875));
//			//center
//			darkSegments.add(new AABB(x + 0.3875, y - 0.125, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + 0.3125, x + 0.625, y + 0.125, z + 0.1875));
//			//vertical lines
//			lightSegments.add(new AABB(x + 0.3125, y - 0.25, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), x + 0.6875, y - 0.1875, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.3125, y + 0.1875, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), x + 0.6875, y + 0.25, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.25, y - 0.1875, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), x + 0.3125, y + 0.1875, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.1875, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.468765), x + 0.75, y + 0.1875, z - remainderWidthRight - Z_ARM_SEGMENT_LENGTH + (0.3125 + 0.53215)));
//		} else {
//			lightSegments.add(new AABB(x + 0.25, y - 0.25, z - remainderWidthRight + 0.3125, x + 0.3125, y - 0.1875, z + 0.1875));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.25, z - remainderWidthRight + 0.3125, x + 0.75, y - 0.1875, z + 0.1875));
//			lightSegments.add(new AABB(x + 0.25, y + 0.1875, z - remainderWidthRight + 0.3125, x + 0.3125, y + 0.25, z + 0.1875));
//			lightSegments.add(new AABB(x + 0.6875, y + 0.1875, z - remainderWidthRight + 0.3125, x + 0.75, y + 0.25, z + 0.1875));
//			//center
//			darkSegments.add(new AABB(x + 0.3875, y - 0.125, z - remainderWidthRight + 0.3125, x + 0.625, y + 0.125, z + 0.1875));
//			//vertical lines
//			lightSegments.add(new AABB(x + 0.3125, y - 0.25, z - remainderWidthRight + (0.3125 + 0.468765), x + 0.6875, y - 0.1875, z - remainderWidthRight + (0.3125 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.3125, y + 0.1875, z - remainderWidthRight + (0.3125 + 0.468765), x + 0.6875, y + 0.25, z - remainderWidthRight + (0.3125 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.25, y - 0.1875, z - remainderWidthRight + (0.3125 + 0.468765), x + 0.3125, y + 0.1875, z - remainderWidthRight + (0.3125 + 0.53215)));
//			lightSegments.add(new AABB(x + 0.6875, y - 0.1875, z - remainderWidthRight + (0.3125 + 0.468765), x + 0.75, y + 0.1875, z - remainderWidthRight + (0.3125 + 0.53215)));
//		}
//
//	}

	@Nullable
	private QuarryArmFrameWrapper getCurrentFrame(long ticks) {
		if (miningPos.get().equals(OUT_OF_REACH)) {
			return new QuarryArmFrameWrapper(null, 0, 0, 0);
		}	
		
		if (!isMotorComplexPowered() || prevMiningPos.get().equals(OUT_OF_REACH) || prevMiningPos.get().equals(miningPos.get())) {
			return new QuarryArmFrameWrapper(new Location(miningPos.get().offset(0, -1, 0)), 0, 0, 0);
		}
		
		if(!hasHead.get()) {
			return currentFrame;
		}
		
		int numberOfFrames = speed.get().intValue();
		if (numberOfFrames == 0) {
			numberOfFrames = 1;
		}
		
		double deltaX = (miningPos.get().getX() - prevMiningPos.get().getX()) / (double) numberOfFrames;
		double deltaY = (miningPos.get().getY() - prevMiningPos.get().getY()) / (double) numberOfFrames;
		double deltaZ = (miningPos.get().getZ() - prevMiningPos.get().getZ()) / (double) numberOfFrames;
		
		if (Math.abs(deltaX) + Math.abs(deltaY) + Math.abs(deltaZ) == 0) {
			return new QuarryArmFrameWrapper(new Location(miningPos.get().offset(0, -1, 0)), 0, 0, 0);
		}
		float degress = 360.0F * ((float) progressCounter.get() / (float) numberOfFrames);
		int currFrame = progressCounter.get() % numberOfFrames; 
		
		int signX = (int) Math.signum(deltaX);
		int signZ = (int) Math.signum(deltaZ);
		
		return new QuarryArmFrameWrapper(new Location(prevMiningPos.get().getX() + deltaX * currFrame, prevMiningPos.get().getY() + deltaY * currFrame, prevMiningPos.get().getZ() + deltaZ * currFrame), signX, signZ, degress);
		
	}

	@Override
	public void saveAdditional(CompoundTag compound) {
		super.saveAdditional(compound);
		
		CompoundTag data = new CompoundTag();

		data.putBoolean("bottomStrip", hasBottomStrip);
		data.putBoolean("topStrip", hasTopStrip);
		data.putBoolean("leftStrip", hasLeftStrip);
		data.putBoolean("rightStrip", hasRightStrip);

		if (currPos != null) {
			data.putInt("currX", currPos.getX());
			data.putInt("currY", currPos.getY());
			data.putInt("currZ", currPos.getZ());
		}
		if (prevPos != null) {
			data.putInt("prevX", prevPos.getX());
			data.putInt("prevY", prevPos.getY());
			data.putInt("prevZ", prevPos.getZ());
		}

		data.putBoolean("prevIsCorner", prevIsCorner);
		data.putBoolean("lastIsCorner", lastIsCorner);

		data.putBoolean("hasDecayed", hasHandledDecay);

		data.putBoolean("areaClear", isAreaCleared);

		data.putInt("heightShiftCA", heightShiftCA);
		data.putInt("widthShiftCA", widthShiftCA);
		data.putInt("tickDelayCA", tickDelayCA);

		data.putInt("lengthShiftMiner", lengthShiftMiner);
		data.putInt("heightShiftMiner", heightShiftMiner);
		data.putInt("widthShiftMiner", widthShiftMiner);
		data.putInt("tickDelayMiner", tickDelayMiner);

		data.putBoolean("lengthReverse", lengthReverse);
		data.putBoolean("widthReverse", widthReverse);

		data.putInt("widthShiftCR", widthShiftCR);

		data.putInt("widthShiftMaintainMining", widthShiftMaintainMining);

		if (placedBy != null) {
			data.putUUID("placedBy", placedBy);
		}

		data.putBoolean("continue", cont);
		int i = 0;
		for(Entry<BlockPos, BlockState> entry : brokenFrames.entrySet()) {
			data.put("brokenframe" + i, NbtUtils.writeBlockPos(entry.getKey()));
			BlockFrame.writeToNbt(data, "brokenstate" + i, entry.getValue());
			i++;
		}
		data.putInt("brokenframecount", i);
		
		i = 0;
		for(BlockPos pos : repairedFrames) {
			data.put("repairedframe" + i, NbtUtils.writeBlockPos(pos));
			i++;
		}
		data.putInt("repairedframecount", i);
		
		compound.put("quarrydata", data);
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		
		CompoundTag data = compound.getCompound("quarrydata");

		hasBottomStrip = data.getBoolean("bottomStrip");
		hasTopStrip = data.getBoolean("topStrip");
		hasLeftStrip = data.getBoolean("leftStrip");
		hasRightStrip = data.getBoolean("rightStrip");

		if (data.contains("currX")) {
			currPos = new BlockPos(data.getInt("currX"), data.getInt("currY"), data.getInt("currZ"));
		} else {
			currPos = null;
		}

		if (data.contains("prevY")) {
			prevPos = new BlockPos(data.getInt("prevX"), data.getInt("prevY"), data.getInt("prevZ"));
		} else {
			prevPos = null;
		}

		prevIsCorner = data.getBoolean("prevIsCorner");
		lastIsCorner = data.getBoolean("lastIsCorner");

		hasHandledDecay = data.getBoolean("hasDecayed");

		isAreaCleared = data.getBoolean("areaClear");

		heightShiftCA = data.getInt("heightShiftCA");
		widthShiftCA = data.getInt("widthShiftCA");
		tickDelayCA = data.getInt("tickDelayCA");

		lengthShiftMiner = data.getInt("lengthShiftMiner");
		heightShiftMiner = data.getInt("heightShiftMiner");
		widthShiftMiner = data.getInt("widthShiftMiner");
		tickDelayMiner = data.getInt("tickDelayMiner");

		lengthReverse = data.getBoolean("lengthReverse");
		widthReverse = data.getBoolean("widthReverse");

		widthShiftCR = data.getInt("widthShiftCR");

		widthShiftMaintainMining = data.getInt("widthShiftMaintainMining");

		if (data.contains("placedBy")) {
			placedBy = data.getUUID("placedBy");
		}

		cont = data.getBoolean("continue");
		
		int brokenSize = data.getInt("brokenframecount");
		for(int i = 0; i < brokenSize; i++) {
			BlockPos pos = NbtUtils.readBlockPos(data.getCompound("brokenframe" + i));
			BlockState state = BlockFrame.readFromNbt(data.getCompound("brokenstate" + i));
			brokenFrames.put(pos, state);
		}
		
		int repairSize = data.getInt("repairedframecount");
		for(int i = 0; i < repairSize; i++) {
			repairedFrames.add(NbtUtils.readBlockPos(data.getCompound("repairedframe" + i)));
		}
		
		compound.remove("quarrydata");
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
		isFinished.set(true);
		isChanged = true;
		progressCounter.set(0);
		running.set(false);
		handleFramesDecayNoVarUpdate();
	}
	
	public void handleFramesDecayNoVarUpdate() {
		if(!hasCorners()) {
			return;
		}
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
		if(!level.isClientSide) {
			return complex != null && complex.isPowered.get();
		}
		ComponentDirection quarryDir = getComponent(ComponentType.Direction);
		Direction facing = quarryDir.getDirection().getOpposite();
		BlockEntity entity = level.getBlockEntity(getBlockPos().relative(facing.getClockWise()));
		if(entity != null && entity instanceof TileMotorComplex complex) {
			return complex.isPowered.get();
		}
		entity = level.getBlockEntity(getBlockPos().relative(facing.getCounterClockWise()));
		if(entity != null && entity instanceof TileMotorComplex complex) {
			return complex.isPowered.get();
		}
		
		return false;
	}

}
