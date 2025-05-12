package electrodynamics.common.tile.machines.quarry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import electrodynamics.client.event.levelstage.HandlerQuarryArm;
import electrodynamics.common.block.BlockFrame;
import electrodynamics.common.block.states.ElectrodynamicsBlockStates;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerQuarry;
import electrodynamics.common.item.ItemDrillHead;
import electrodynamics.common.item.subtype.SubtypeDrillHead;
import electrodynamics.common.settings.ElectroConstants;
import electrodynamics.registers.ElectrodynamicsBlocks;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
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
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import voltaic.api.tile.IPlayerStorable;
import voltaic.common.block.states.VoltaicBlockStates;
import voltaic.common.item.ItemUpgrade;
import voltaic.prefab.block.GenericMachineBlock;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.ListProperty;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentPacketHandler;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.ItemUtils;
import voltaic.registers.VoltaicCapabilities;

/**
 * Loosely based on Quarry concept from Buildcraft with aspects of Extra Utilities's Ender Quarry
 * 
 * @author skip999
 *
 */
public class TileQuarry extends GenericTile implements IPlayerStorable {

	private static final int CAPACITY = 10000;
	private static final BlockState AIR = Blocks.AIR.defaultBlockState();
	private static final int CLEAR_SKIP = Math.max(Math.min(ElectroConstants.CLEARING_AIR_SKIP, 128), 0);

	public static final int DRILL_HEAD_INDEX = 0;

	@Nullable
	private UUID placedBy = null;

	/* FRAME PARAMETERS */

	public final SingleProperty<Boolean> hasCoolantResavoir = property(new SingleProperty<>(PropertyTypes.BOOLEAN, "hascoolantresavoir", false));
	public final SingleProperty<Boolean> hasMotorComplex = property(new SingleProperty<>(PropertyTypes.BOOLEAN, "hasmotorcomplex", false));
	public final SingleProperty<Boolean> hasSeismicRelay = property(new SingleProperty<>(PropertyTypes.BOOLEAN, "hasseismicrelay", false));
	public final SingleProperty<Boolean> hasRing = property(new SingleProperty<>(PropertyTypes.BOOLEAN, "hasring", false));

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

	private final HashMap<BlockPos, BlockState> brokenFrames = new HashMap<>();
	private final HashSet<BlockPos> repairedFrames = new HashSet<>();

	public final ListProperty<BlockPos> corners = property(new ListProperty<>(PropertyTypes.BLOCK_POS_LIST, "corners", List.of(BlockEntityUtils.OUT_OF_REACH, BlockEntityUtils.OUT_OF_REACH, BlockEntityUtils.OUT_OF_REACH, BlockEntityUtils.OUT_OF_REACH)));
	public final SingleProperty<Boolean> cornerOnRight = property(new SingleProperty<>(PropertyTypes.BOOLEAN, "corneronright", false));

	private boolean hasHandledDecay = false;

	public final SingleProperty<Boolean> isAreaCleared = property(new SingleProperty<>(PropertyTypes.BOOLEAN, "areaClear", false));

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

	public final SingleProperty<BlockPos> miningPos = property(new SingleProperty<>(PropertyTypes.BLOCK_POS, "miningpos", BlockEntityUtils.OUT_OF_REACH));
	public final SingleProperty<BlockPos> prevMiningPos = property(new SingleProperty<>(PropertyTypes.BLOCK_POS, "prevminingpos", BlockEntityUtils.OUT_OF_REACH));

	public final SingleProperty<Boolean> isFinished = property(new SingleProperty<>(PropertyTypes.BOOLEAN, "isfinished", false));

	private boolean widthReverse = false;
	private boolean lengthReverse = false;

	public final SingleProperty<Double> quarryPowerUsage = property(new SingleProperty<>(PropertyTypes.DOUBLE, "quarrypowerusage", 0.0));
	public final SingleProperty<Double> setupPowerUsage = property(new SingleProperty<>(PropertyTypes.DOUBLE, "setuppowerusage", 0.0));
	public final SingleProperty<Boolean> isPowered = property(new SingleProperty<>(PropertyTypes.BOOLEAN, "ispowered", false));
	public final SingleProperty<Boolean> hasHead = property(new SingleProperty<>(PropertyTypes.BOOLEAN, "hashead", false));
	public final SingleProperty<Integer> currHead = property(new SingleProperty<>(PropertyTypes.INTEGER, "headtype", -1));

	public final SingleProperty<Boolean> hasItemVoid = property(new SingleProperty<>(PropertyTypes.BOOLEAN, "hasitemvoid", false));
	public final SingleProperty<Integer> fortuneLevel = property(new SingleProperty<>(PropertyTypes.INTEGER, "fortunelevel", 0));
	public final SingleProperty<Integer> silkTouchLevel = property(new SingleProperty<>(PropertyTypes.INTEGER, "silktouchlevel", 0));
	public final SingleProperty<Integer> unbreakingLevel = property(new SingleProperty<>(PropertyTypes.INTEGER, "unbreakinglevel", 0));

	// these values are used to deal with client tick desync and provide more
	// complex information
	// on how the quarry should be rendered
	public final SingleProperty<Integer> speed = property(new SingleProperty<>(PropertyTypes.INTEGER, "speed", 0));
	public final SingleProperty<Integer> progressCounter = property(new SingleProperty<>(PropertyTypes.INTEGER, "progresscounter", 0));
	public final SingleProperty<Boolean> running = property(new SingleProperty<>(PropertyTypes.BOOLEAN, "isrunning", false));
	public final SingleProperty<Boolean> isTryingToMineFrame = property(new SingleProperty<>(PropertyTypes.BOOLEAN, "istryingtomineframe", false));

	private int widthShiftMaintainMining = 0;
	private boolean cont = false;

	// Client Parameters
	private QuarryRenderManger renderHandler = null;

	public TileQuarry(BlockPos pos, BlockState state) {
		super(ElectrodynamicsTiles.TILE_QUARRY.get(), pos, state);

		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickClient(this::tickClient));
		addComponent(new ComponentElectrodynamic(this, false, true).setInputDirections(BlockEntityUtils.MachineDirection.BOTTOM).voltage(VoltaicCapabilities.DEFAULT_VOLTAGE * 2).maxJoules(ElectroConstants.QUARRY_USAGE_PER_TICK * CAPACITY));
		addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().inputs(7).outputs(9).upgrades(3)).validUpgrades(ContainerQuarry.VALID_UPGRADES).valid(machineValidator()).setSlotsByDirection(BlockEntityUtils.MachineDirection.FRONT, 0, 7, 8, 9, 10, 11, 12, 13, 14, 15));
		addComponent(new ComponentContainerProvider(SubtypeMachine.quarry.tag(), this).createMenu((id, player) -> new ContainerQuarry(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

	private void tickServer(ComponentTickable tick) {
		if (isFinished.getValue()) {
			running.setValue(false);
			if (!hasHandledDecay) {
				handleFramesDecay();
				hasHandledDecay = true;
			}
			return;
		}
		speed.setValue(complex == null ? 0 : complex.speed.getValue() + tickDelayMiner);
		BlockPos pos = getBlockPos();
		if (GenericMachineBlock.IPLAYERSTORABLE_MAP.containsKey(pos)) {
			setPlayer(GenericMachineBlock.IPLAYERSTORABLE_MAP.get(pos));
			GenericMachineBlock.IPLAYERSTORABLE_MAP.remove(pos);
		}

		// check surrounding components every 5 ticks
		if ((tick.getTicks() + 1) % 5 == 0) {
			checkComponents();
		}

		// return if the quarry still does not have components
		if (!hasSeismicRelay.getValue()) {
			running.setValue(false);
			if (hasCorners() && !hasHandledDecay && isAreaCleared.getValue()) {
				handleFramesDecay();
			}
			return;
		}

		// return if the quarry still does not have components
		if (!hasSeismicRelay.getValue()) {
			running.setValue(false);
			return;
		}

		// if the quarry can still mine and doesn't have a ring, remedy that
		if (!hasRing.getValue() && tick.getTicks() % (3 + tickDelayCA) == 0 && !isFinished.getValue()) {
			if (isAreaCleared.getValue()) {
				checkRing();
			} else {
				clearArea();
			}
		}

		// if the quarry still doesn't have a ring return
		if (!hasRing.getValue()) {
			running.setValue(false);
			return;
		}

		Level world = getLevel();
		// clean the ring for obstructions
		if (tick.getTicks() % 4 == 0) {
			cleanRing();
		}
		// if frames were broken purposefully repair them
		if (!brokenFrames.isEmpty()) {
			handleBrokenFrames();
		}
		// set the tile data for the repaired frames
		if (!repairedFrames.isEmpty()) {
			handleRepairedFrames();
		}

		// if the quarry components are invalid, return
		if (areComponentsNull()) {
			running.setValue(false);
			return;
		}

		// remove blocks from the mined area
		if (tick.getTicks() % 4 == 0 && ElectroConstants.MAINTAIN_MINING_AREA) {
			maintainMiningArea();
		}

		boolean shouldFail = false;
		ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);

		isPowered.setValue(electro.getJoulesStored() >= quarryPowerUsage.getValue());

		// if there isn't enough power don't do anything
		if (!isPowered.getValue()) {
			running.setValue(false);
			shouldFail = true;
		}

		// if the motor complex is in an invalid state return
		if (!complex.isPowered.getValue() || complex.speed.getValue() <= 0) {
			running.setValue(false);
			shouldFail = true;
		}

		int fluidUse = (int) (complex.powerMultiplier.getValue() * ElectroConstants.QUARRY_WATERUSAGE_PER_BLOCK);
		ComponentInventory inv = getComponent(IComponentType.Inventory);
		if (inv.getItem(DRILL_HEAD_INDEX).getItem() instanceof ItemDrillHead head) {
			hasHead.setValue(true);
			writeHeadType(head.head);
		} else {
			hasHead.setValue(false);
			writeHeadType(null);
			running.setValue(false);
		}

		if (shouldFail) {
			return;
		}

		if (!inv.areOutputsEmpty() || !resavoir.hasEnoughFluid(fluidUse) || !hasHead.getValue()) {
			running.setValue(false);
			return;
		}

		running.setValue(true);
		progressCounter.setValue(progressCounter.getValue() + 1);

		if (progressCounter.getValue() < speed.getValue()) {
			return;
		}

		// if there is no room for mined blocks, the fluid resavoir doesn't have enough
		// fluid, or there isn't a drill head, return

		progressCounter.setValue(0);

		if (canMineIfFrame(world.getBlockState(miningPos.getValue()), miningPos.getValue())) {
			isTryingToMineFrame.setValue(true);
			return;
		}
		isTryingToMineFrame.setValue(false);

		resavoir.drainFluid(fluidUse);
		BlockPos cornerStart = corners.getValue().get(3);
		BlockPos cornerEnd = corners.getValue().get(0);
		int deltaW = (int) Math.signum(cornerStart.getX() - cornerEnd.getX());
		int deltaL = (int) Math.signum(cornerStart.getZ() - cornerEnd.getZ());
		int width = cornerStart.getX() - cornerEnd.getX() - 2 * deltaW;
		int length = cornerStart.getZ() - cornerEnd.getZ() - 2 * deltaL;
		// if we have the quarry mine the current block on the next tick, it
		// deals with the issue of the client pos always being one tick behind
		// the server's
		cont = true;
		if (!miningPos.getValue().equals(BlockEntityUtils.OUT_OF_REACH)) {
			BlockState miningState = world.getBlockState(miningPos.getValue());
			float strength = miningState.getDestroySpeed(world, miningPos.getValue());
			if (!skipBlock(miningState) && strength >= 0) {
				cont = mineBlock(miningPos.getValue(), miningState, strength, world, inv.getItem(0), inv, getPlayer((ServerLevel) world));
			}
		}
		prevMiningPos.setValue(new BlockPos(miningPos.getValue()));

		miningPos.setValue(new BlockPos(cornerStart.getX() - widthShiftMiner - deltaW, cornerStart.getY() - heightShiftMiner, cornerStart.getZ() - lengthShiftMiner - deltaL));

		BlockState state = world.getBlockState(miningPos.getValue());
		boolean shouldSkip = true;

		// if the mined block shouldn't be skipped then don't skip it
		if (!cont) {
			return;
		}
		// loop until either the mining skip limit is reached or a non-skipable block is
		// found
		while (shouldSkip) {
			if (miningPos.getValue().getY() <= world.getMinBuildHeight()) {
				heightShiftMiner = 1;
				isFinished.setValue(true);
				progressCounter.setValue(0);
				running.setValue(false);
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
			miningPos.setValue(new BlockPos(cornerStart.getX() - widthShiftMiner - deltaW, cornerStart.getY() - heightShiftMiner, cornerStart.getZ() - lengthShiftMiner - deltaL));
			state = world.getBlockState(miningPos.getValue());
			shouldSkip = skipBlock(state);
		}
		float strength = state.getDestroySpeed(world, miningPos.getValue());
		tickDelayMiner = (int) strength;
		if (!shouldSkip && strength >= 0) {
			electro.joules(electro.getJoulesStored() - quarryPowerUsage.getValue());
		}
		if (shouldSkip) {
			if (lengthReverse ? lengthShiftMiner == 0 : lengthShiftMiner == length) {
				lengthReverse = !lengthReverse;
				if (widthReverse ? widthShiftMiner == 0 : widthShiftMiner == width) {
					widthReverse = !widthReverse;
					heightShiftMiner++;
					if (miningPos.getValue().getY() <= world.getMinBuildHeight()) {
						heightShiftMiner = 1;
						isFinished.setValue(true);
						progressCounter.setValue(0);
						running.setValue(false);
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
	 * Looks for obstructions that have been placed in the previously mined area If one is found, the mining position is set to that position
	 */
	private void maintainMiningArea() {
		Level world = getLevel();
		BlockPos cornerStart = corners.getValue().get(3);
		BlockPos cornerEnd = corners.getValue().get(0);
		int deltaW = (int) Math.signum(cornerStart.getX() - cornerEnd.getX());
		int deltaL = (int) Math.signum(cornerStart.getZ() - cornerEnd.getZ());
		int width = cornerStart.getX() - cornerEnd.getX() - 2 * deltaW;
		int length = cornerStart.getZ() - cornerEnd.getZ();
		BlockPos startPos = new BlockPos(cornerStart.getX() - widthShiftMaintainMining - deltaW, cornerStart.getY(), cornerStart.getZ() - deltaL);
		BlockPos endPos = new BlockPos(cornerStart.getX() - widthShiftMaintainMining - deltaW, miningPos.getValue().getY() + 1, cornerStart.getZ() - length + deltaL);
		Iterator<BlockPos> positions = BlockPos.betweenClosedStream(startPos, endPos).iterator();
		BlockPos pos;
		while (positions.hasNext()) {
			pos = positions.next();
			BlockState state = world.getBlockState(pos);
			if (!skipBlock(state)) {
				int newWidthShift = -1 * (pos.getX() - (cornerStart.getX() - deltaW));
				int newLengthShift = -1 * (pos.getZ() - (cornerStart.getZ() - deltaL));
				widthShiftMiner = newWidthShift;
				lengthShiftMiner = newLengthShift;
				widthShiftMaintainMining = 0;
				heightShiftMiner = startPos.getY() - pos.getY() - 1;
				prevMiningPos.setValue(new BlockPos(miningPos.getValue()));
				miningPos.setValue(pos);
				widthReverse = false;
				lengthReverse = false;
				progressCounter.setValue(0);
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
		BlockPos cornerStart = corners.getValue().get(3);
		BlockPos cornerEnd = corners.getValue().get(0);
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
				boolean canMine = world.destroyBlock(pos, false, getPlayer((ServerLevel) world)) || ElectroConstants.BYPASS_CLAIMS;
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
				int durabilityUsed = (int) (Math.ceil(strength) / (unbreakingLevel.getValue() + 1.0F));
				if (drillHead.getDamageValue() + durabilityUsed >= drillHead.getMaxDamage()) {
					world.playSound(null, getBlockPos(), SoundEvents.ITEM_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
					drillHead.shrink(1);
				} else {
					drillHead.setDamageValue(drillHead.getDamageValue() + durabilityUsed);
				}
			}
			// TODO make this work with custom mining tiers
			ItemStack pickaxe = new ItemStack(Items.NETHERITE_PICKAXE);
			if (silkTouchLevel.getValue() > 0) {
				pickaxe.enchant(level.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(Enchantments.SILK_TOUCH), silkTouchLevel.getValue());
			} else if (fortuneLevel.getValue() > 0) {
				pickaxe.enchant(level.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(Enchantments.FORTUNE), fortuneLevel.getValue());
			}
			List<ItemStack> lootItems = Block.getDrops(state, (ServerLevel) world, pos, null, null, pickaxe);
			List<ItemStack> voidItemStacks = inv.getInputContents().subList(1, inv.getInputContents().size());
			List<Item> voidItems = new ArrayList<>();
			voidItemStacks.forEach(h -> voidItems.add(h.getItem()));
			List<ItemStack> items = new ArrayList<>();

			if (hasItemVoid.getValue()) {
				lootItems.forEach(lootItem -> {
					if (!voidItems.contains(lootItem.getItem())) {
						items.add(lootItem);
					}
				});
			} else {
				items.addAll(lootItems);
			}

			int max = inv.getOutputStartIndex() + inv.getOutputContents().size();

			for(ItemStack item : items) {

				for (int i = inv.getOutputStartIndex(); i < max; i++) {

					ItemStack contained = inv.getItem(i);

					int room = contained.getMaxStackSize() - contained.getCount();

					int amtAccepted = Math.min(room, item.getCount());

					if(amtAccepted == 0) {
						continue;
					}

					if (contained.isEmpty()) {

						inv.setItem(i, new ItemStack(item.getItem(), amtAccepted));

						item.shrink(amtAccepted);

					} else if (ItemUtils.testItems(item.getItem(), contained.getItem())) {

						contained.grow(amtAccepted);

						item.shrink(amtAccepted);

						inv.setChanged();

					}
					if(item.isEmpty()) {
						break;
					}
				}

			}

			world.playSound(null, pos, state.getSoundType().getBreakSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
		}
		return sucess;
	}

	// responsible for clearing initial obstructions from the mining area
	private void clearArea() {
		ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);
		setupPowerUsage.setValue(ElectroConstants.QUARRY_USAGE_PER_TICK);
		isPowered.setValue(electro.getJoulesStored() >= setupPowerUsage.getValue());
		if (hasCorners() && isPowered.getValue()) {
			Level world = getLevel();
			BlockPos start = corners.getValue().get(3);
			BlockPos end = corners.getValue().get(0);
			int width = start.getX() - end.getX();
			int height = start.getZ() - end.getZ();
			int deltaW = (int) Math.signum(width);
			int deltaH = (int) Math.signum(height);
			BlockPos checkPos = new BlockPos(start.getX() - widthShiftCA, start.getY(), start.getZ() - heightShiftCA);
			BlockState state = world.getBlockState(checkPos);
			if (canMineIfFrame(state, checkPos)) {
				isTryingToMineFrame.setValue(true);
				return;
			}
			isTryingToMineFrame.setValue(true);
			float strength = state.getDestroySpeed(world, checkPos);
			int blockSkip = 0;
			while (skipBlock(state) && blockSkip < CLEAR_SKIP) {
				if (heightShiftCA == height) {
					heightShiftCA = 0;
					if (widthShiftCA == width) {
						isAreaCleared.setValue(true);
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
			if (strength >= 0 && electro.getJoulesStored() >= setupPowerUsage.getValue() * strength) {
				boolean sucess = false;
				if (!skipBlock(state)) {
					tickDelayCA = (int) Math.ceil(strength / 5.0F);
					electro.joules(electro.getJoulesStored() - setupPowerUsage.getValue() * strength);
					sucess = world.destroyBlock(checkPos, false, getPlayer((ServerLevel) world));
					if (sucess) {
						world.playSound(null, checkPos, state.getSoundType().getBreakSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
					}
				}
				if (sucess) {
					if (heightShiftCA == height) {
						heightShiftCA = 0;
						if (widthShiftCA == width) {
							isAreaCleared.setValue(true);
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
		while (it.hasNext()) {
			pos = it.next();
			entity = world.getBlockEntity(pos);
			if (entity != null && entity instanceof TileFrame frame) {
				frame.setQuarryPos(getBlockPos());
				it.remove();
			}
		}
		isChanged = true;
	}

	private void checkRing() {
		ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);
		if (electro.getJoulesStored() < ElectroConstants.QUARRY_USAGE_PER_TICK && hasCorners()) {
			return;
		}
		electro.joules(electro.getJoulesStored() - ElectroConstants.QUARRY_USAGE_PER_TICK);
		BlockState cornerState = ElectrodynamicsBlocks.BLOCK_FRAME_CORNER.get().defaultBlockState().setValue(VoltaicBlockStates.WATERLOGGED, false);
		Level world = getLevel();
		BlockPos frontOfQuarry = corners.getValue().get(0);
		BlockPos foqFar = corners.getValue().get(1);
		BlockPos foqCorner = corners.getValue().get(2);
		BlockPos farCorner = corners.getValue().get(3);
		Direction facing = getFacing().getOpposite();
		if (prevPos != null) {
			if (hasAllStrips()) {
				hasRing.setValue(true);
				prevPos = null;
				isFinished.setValue(false);
				heightShiftMiner = 1;
				widthShiftMiner = 0;
				lengthShiftMiner = 0;
				quarryPowerUsage.setValue(0.0);
				setupPowerUsage.setValue(0.0);
				BlockPos cornerStart = corners.getValue().get(3);
				BlockPos cornerEnd = corners.getValue().get(0);
				int deltaW = (int) Math.signum(cornerStart.getX() - cornerEnd.getX());
				int deltaL = (int) Math.signum(cornerStart.getZ() - cornerEnd.getZ());
				miningPos.setValue(new BlockPos(cornerStart.getX() - deltaW, cornerStart.getY() - heightShiftMiner, cornerStart.getZ() - deltaL));
				prevMiningPos.setValue(new BlockPos(miningPos.getValue()));
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
		if (canMineIfFrame(world.getBlockState(currPos), currPos)) {
			isTryingToMineFrame.setValue(true);
			return;
		}
		isTryingToMineFrame.setValue(false);

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
			world.setBlockAndUpdate(currPos, ElectrodynamicsBlocks.BLOCK_FRAME.get().defaultBlockState().setValue(VoltaicBlockStates.FACING, frameFace).setValue(VoltaicBlockStates.WATERLOGGED, false));
			repairedFrames.add(currPos);
			prevIsCorner = false;
		}
		world.playSound(null, currPos, SoundEvents.ANVIL_PLACE, SoundSource.BLOCKS, 0.5F, 1.0F);
		prevPos = new BlockPos(currPos.getX(), currPos.getY(), currPos.getZ());
		currPos = currPos.relative(cornerOnRight.getValue() ? relative.getOpposite() : relative);
	}

	private void strip(Level world, BlockPos startPos, int endCV, Direction relative, Direction frameFace, boolean currPosX, boolean left) {
		if (currPos == null) {
			currPos = startPos.relative(relative);
		}
		if (canMineIfFrame(world.getBlockState(currPos), currPos)) {
			isTryingToMineFrame.setValue(true);
			return;
		}
		isTryingToMineFrame.setValue(false);
		world.setBlockAndUpdate(currPos, ElectrodynamicsBlocks.BLOCK_FRAME.get().defaultBlockState().setValue(VoltaicBlockStates.FACING, cornerOnRight.getValue() ? frameFace.getOpposite() : frameFace).setValue(VoltaicBlockStates.WATERLOGGED, false));
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
		Direction facing = getFacing().getOpposite();
		Level world = getLevel();
		BlockPos machinePos = getBlockPos();
		Direction left = facing.getCounterClockWise();
		Direction right = facing.getClockWise();
		BlockEntity leftEntity = world.getBlockEntity(machinePos.relative(left));
		BlockEntity rightEntity = world.getBlockEntity(machinePos.relative(right));
		BlockEntity aboveEntity = world.getBlockEntity(machinePos.above());

		// reformatted to allow for individual components to be missing

		if (leftEntity != null && leftEntity instanceof TileMotorComplex complexin && complexin.getFacing() == left) {
			complex = complexin;
			hasMotorComplex.setValue(true);
		} else if (rightEntity != null && rightEntity instanceof TileMotorComplex complexin && complexin.getFacing() == right) {
			complex = complexin;
			hasMotorComplex.setValue(true);
		} else {
			complex = null;
			hasMotorComplex.setValue(false);
		}

		if (leftEntity != null && leftEntity instanceof TileSeismicRelay relayin && relayin.getFacing() == facing.getOpposite()) {
			corners.wipeList();
			corners.addValues(relayin.markerLocs.getValue());
			cornerOnRight.setValue(relayin.cornerOnRight);
			relay = relayin;
			hasSeismicRelay.setValue(true);
		} else if (rightEntity != null && rightEntity instanceof TileSeismicRelay relayin && relayin.getFacing() == facing.getOpposite()) {
			corners.wipeList();
			corners.addValues(relayin.markerLocs.getValue());
			cornerOnRight.setValue(relayin.cornerOnRight);
			relay = relayin;
			hasSeismicRelay.setValue(true);
		} else {
			relay = null;
			hasSeismicRelay.setValue(false);
		}

		if (aboveEntity != null && aboveEntity instanceof TileCoolantResavoir resavoirin) {
			resavoir = resavoirin;
			hasCoolantResavoir.setValue(true);
		} else {
			resavoir = null;
			hasCoolantResavoir.setValue(false);
		}

	}

	private boolean areComponentsNull() {
		return complex == null || resavoir == null || relay == null;
	}

	private boolean hasAllStrips() {
		return hasBottomStrip && hasTopStrip && hasLeftStrip && hasRightStrip;
	}

	public boolean hasCorners() {
		return corners.getValue().size() > 3;
	}

	private boolean skipBlock(BlockState state) {
		return state.isAir() || state.is(Blocks.BEDROCK) || miningPos.getValue().getY() == level.getMinBuildHeight();
	}

	private void tickClient(ComponentTickable tick) {
		if (renderHandler == null) {
			renderHandler = new QuarryRenderManger();
		}
		renderHandler.render(this);
	}

	@Override
	protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
		super.saveAdditional(compound, registries);
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
		for (Entry<BlockPos, BlockState> entry : brokenFrames.entrySet()) {
			data.put("brokenframe" + i, NbtUtils.writeBlockPos(entry.getKey()));
			BlockFrame.writeToNbt(data, "brokenstate" + i, entry.getValue());
			i++;
		}
		data.putInt("brokenframecount", i);

		i = 0;
		for (BlockPos pos : repairedFrames) {
			data.put("repairedframe" + i, NbtUtils.writeBlockPos(pos));
			i++;
		}
		data.putInt("repairedframecount", i);

		compound.put("quarrydata", data);
	}

	@Override
	protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
		super.loadAdditional(compound, registries);
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
		for (int i = 0; i < brokenSize; i++) {
			BlockPos pos = NbtUtils.readBlockPos(data, "brokenframe" + i).get();
			BlockState state = BlockFrame.readFromNbt(data.getCompound("brokenstate" + i));
			brokenFrames.put(pos, state);
		}

		int repairSize = data.getInt("repairedframecount");
		for (int i = 0; i < repairSize; i++) {
			repairedFrames.add(NbtUtils.readBlockPos(data, "repairedframe" + i).get());
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
		if (level.isClientSide) {
			return;
		}
		handleFramesDecayNoVarUpdate();
	}

	public void handleFramesDecay() {
		miningPos.setValue(BlockEntityUtils.OUT_OF_REACH);
		prevMiningPos.setValue(BlockEntityUtils.OUT_OF_REACH);
		hasHandledDecay = true;
		isAreaCleared.setValue(false);
		hasRing.setValue(false);
		hasBottomStrip = false;
		hasTopStrip = false;
		hasLeftStrip = false;
		hasRightStrip = false;
		lengthReverse = false;
		widthReverse = false;
		isFinished.setValue(true);
		isChanged = true;
		progressCounter.setValue(0);
		running.setValue(false);
		brokenFrames.clear();
		repairedFrames.clear();
		corners.wipeList();
		handleFramesDecayNoVarUpdate();
	}

	public void handleFramesDecayNoVarUpdate() {
		if (!hasCorners()) {
			return;
		}
		Level world = getLevel();
		BlockPos frontOfQuarry = corners.getValue().get(0);
		BlockPos foqFar = corners.getValue().get(1);
		BlockPos foqCorner = corners.getValue().get(2);
		BlockPos farCorner = corners.getValue().get(3);
		for (BlockPos pos : corners.getValue()) {
			world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
		}
		BlockPos.betweenClosedStream(foqCorner, frontOfQuarry).forEach(pos -> updateState(world, pos));
		BlockPos.betweenClosedStream(farCorner, foqFar).forEach(pos -> updateState(world, pos));
		BlockPos.betweenClosedStream(foqCorner, farCorner).forEach(pos -> updateState(world, pos));
		BlockPos.betweenClosedStream(frontOfQuarry, foqFar).forEach(pos -> updateState(world, pos));
	}

	private static void updateState(Level world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		if (state.is(ElectrodynamicsBlocks.BLOCK_FRAME) || state.is(ElectrodynamicsBlocks.BLOCK_FRAME_CORNER)) {
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
		if (!inv.getUpgradeContents().isEmpty() && (slot >= inv.getUpgradeSlotStartIndex() || slot == -1)) {
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
			this.hasItemVoid.setValue(hasItemVoid);
			this.fortuneLevel.setValue(fortuneLevel);
			this.silkTouchLevel.setValue(silkTouchLevel);
			this.unbreakingLevel.setValue(unbreakingLevel);
			quarryPowerUsage.setValue(ElectroConstants.QUARRY_USAGE_PER_TICK * quarryPowerMultiplier);
		}
	}

	private void writeHeadType(SubtypeDrillHead head) {
		if (head == null) {
			currHead.setValue(-1);
		} else {
			currHead.setValue(head.ordinal());
		}
	}

	@Nullable
	public SubtypeDrillHead readHeadType() {
		return currHead.getValue() == -1 ? null : SubtypeDrillHead.values()[currHead.getValue()];
	}

	@Nullable
	public TileMotorComplex getMotorComplex() {
		Direction facing = getFacing().getOpposite();
		BlockEntity entity = level.getBlockEntity(getBlockPos().relative(facing.getClockWise()));
		if (entity != null && entity instanceof TileMotorComplex complex) {
			return complex;
		}
		entity = level.getBlockEntity(getBlockPos().relative(facing.getCounterClockWise()));
		if (entity != null && entity instanceof TileMotorComplex complex) {
			return complex;
		}
		return null;
	}

	@Nullable
	public TileCoolantResavoir getFluidResavoir() {
		BlockEntity entity = level.getBlockEntity(getBlockPos().offset(0, 1, 0));
		if (entity != null && entity instanceof TileCoolantResavoir resavoir) {
			return resavoir;
		}

		return null;
	}

	@Nullable
	public TileSeismicRelay getSeismicRelay() {
		Direction facing = getFacing().getOpposite();
		BlockEntity entity = level.getBlockEntity(getBlockPos().relative(facing.getClockWise()));
		if (entity != null && entity instanceof TileSeismicRelay relay) {
			return relay;
		}
		entity = level.getBlockEntity(getBlockPos().relative(facing.getCounterClockWise()));
		if (entity != null && entity instanceof TileSeismicRelay relay) {
			return relay;
		}
		return null;
	}

	public boolean isMotorComplexPowered() {
		if (!level.isClientSide) {
			return complex != null && complex.isPowered.getValue();
		}
		TileMotorComplex complex = getMotorComplex();
		if (complex == null) {
			return false;
		}
		return complex.isPowered.getValue();
	}

	public boolean canMineIfFrame(BlockState state, BlockPos pos) {
		if (state.is(ElectrodynamicsBlocks.BLOCK_FRAME) || state.is(ElectrodynamicsBlocks.BLOCK_FRAME_CORNER)) {
			BlockEntity entity = level.getBlockEntity(pos);
			if (entity != null && entity instanceof TileFrame frame) {
				return frame.ownerQuarryPos != null;
			}
		}
		return false;
	}

	@Override
	public int getComparatorSignal() {
		return isFinished.getValue() ? 15 : 0;
	}

}
