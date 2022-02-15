package electrodynamics.common.tile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.Triple;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.tile.IPlayerStorable;
import electrodynamics.client.ClientEvents;
import electrodynamics.common.block.states.ElectrodynamicsBlockStates;
import electrodynamics.common.inventory.container.tile.ContainerQuarry;
import electrodynamics.common.item.ItemDrillHead;
import electrodynamics.common.item.ItemUpgrade;
import electrodynamics.common.item.subtype.SubtypeDrillHead;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.block.GenericEntityBlock;
import electrodynamics.prefab.block.GenericMachineBlock;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.InventoryUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
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

	@Nullable
	private UUID placedBy = null;

	/* FRAME PARAMETERS */

	private boolean hasComponents = false;
	public boolean hasRing = false;

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

	private List<Triple<BlockPos, Direction, Boolean>> brokenFrames = new ArrayList<>();
	private BlockPos lastFixed = null;
	private boolean lastIsCorner = false;

	private static final int CLEAR_SKIP = Math.max(Math.min(Constants.CLEARING_AIR_SKIP, 128), 0);

	// we store the last known corners for frame removal purposes
	private List<BlockPos> corners = new ArrayList<>();
	private boolean cornerOnRight = false;

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

	private BlockPos miningPos = null;
	private BlockPos posForClient = null;

	private boolean isFinished = false;

	private boolean widthReverse = false;
	private boolean lengthReverse = false;

	public double quarryPowerUsage = 0;
	private boolean isPowered = false;
	private boolean hasHead = false;

	private boolean hasItemVoid = false;
	private int fortuneLevel = 0;
	private int silkTouchLevel = 0;
	private int unbreakingLevel = 0;

	private static final int MINE_SKIP = Math.max(Math.min(Constants.CLEARING_AIR_SKIP, 128), 0);

	private int widthShiftMaintainMining = 0;

	/* CLIENT PARAMETERS */

	private boolean clientOnRight = false;
	private List<BlockPos> clientCorners = new ArrayList<>();
	public BlockPos clientMiningPos = null;

	public boolean clientItemVoid = false;
	public int clientFortuneLevel = 0;
	public int clientSilkTouchLevel = 0;
	public int clientUnbreakingLevel = 0;

	public double clientPowerUsage = 0;
	public boolean clientIsPowered = false;

	public boolean clientFinished = false;
	public boolean clientHead = false;

	public TileQuarry(BlockPos pos, BlockState state) {
		super(DeferredRegisters.TILE_QUARRY.get(), pos, state);
		addComponent(new ComponentDirection());
		addComponent(new ComponentPacketHandler().customPacketWriter(this::createPacket).guiPacketWriter(this::createPacket).customPacketReader(this::readPacket).guiPacketReader(this::readPacket));
		addComponent(new ComponentTickable().tickServer(this::tickServer).tickClient(this::tickClient));
		addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.DOWN).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 2).maxJoules(Constants.QUARRY_USAGE_PER_TICK * CAPACITY));
		addComponent(new ComponentInventory(this).size(19).inputs(7).outputs(9).upgrades(3).valid(machineValidator()));
		addComponent(new ComponentContainerProvider("container.quarry").createMenu((id, player) -> new ContainerQuarry(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}

	private void tickServer(ComponentTickable tick) {
		BlockPos pos = getBlockPos();
		if (GenericMachineBlock.IPLAYERSTORABLE_MAP.containsKey(pos)) {
			setPlayer(GenericMachineBlock.IPLAYERSTORABLE_MAP.get(pos));
			GenericMachineBlock.IPLAYERSTORABLE_MAP.remove(pos);
		}
		this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
		if (hasComponents) {
			hasHandledDecay = false;
			if (hasRing) {
				ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
				Level world = getLevel();
				if (tick.getTicks() % 4 == 0) {
					cleanRing();
				}
				if (lastFixed != null) {
					TileFrame frame = (TileFrame) world.getBlockEntity(lastFixed);
					frame.setQuarryPos(getBlockPos());
					frame.setCorner(lastIsCorner);
					lastFixed = null;
				}
				if (brokenFrames.size() > 0) {
					fixBrokenFrames();
					// it only will mine if the frame is 100% intact and clear
				} else if (!isFinished && !areComponentsNull()) {
					if (tick.getTicks() % 4 == 0 && Constants.MAINTAIN_MINING_AREA) {
						maintainMiningArea();
					}
					if (complex.isPowered && tick.getTicks() % ((int) complex.speed + tickDelayMiner) == 0) {
						int fluidUse = (int) (complex.powerMultiplier * Constants.QUARRY_WATERUSAGE_PER_BLOCK);
						ComponentInventory inv = getComponent(ComponentType.Inventory);
						hasHead = inv.getItem(0).getItem() instanceof ItemDrillHead;
						if (inv.areOutputsEmpty() && resavoir.hasEnoughFluid(fluidUse) && hasHead) {
							double quarryPowerMultiplier = 0;
							silkTouchLevel = 0;
							fortuneLevel = 0;
							unbreakingLevel = 0;
							hasItemVoid = false;
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
							quarryPowerUsage = Constants.QUARRY_USAGE_PER_TICK * quarryPowerMultiplier;
							isPowered = electro.getJoulesStored() >= quarryPowerUsage;
							if (isPowered) {
								resavoir.drainFluid(fluidUse);
								BlockPos cornerStart = corners.get(3);
								BlockPos cornerEnd = corners.get(0);
								int deltaW = (int) Math.signum(cornerStart.getX() - cornerEnd.getX());
								int deltaL = (int) Math.signum(cornerStart.getZ() - cornerEnd.getZ());
								int width = cornerStart.getX() - cornerEnd.getX() - 2 * deltaW;
								int length = cornerStart.getZ() - cornerEnd.getZ() - 2 * deltaL;

								miningPos = new BlockPos(cornerStart.getX() - widthShiftMiner - deltaW, cornerStart.getY() - heightShiftMiner, cornerStart.getZ() - lengthShiftMiner - deltaL);

								BlockState state = world.getBlockState(miningPos);
								int blockSkip = 0;
								while (skipBlock(state) && blockSkip < MINE_SKIP) {
									if ((lengthReverse ? lengthShiftMiner == 0 : lengthShiftMiner == length)) {
										lengthReverse = !lengthReverse;
										if ((widthReverse ? widthShiftMiner == 0 : widthShiftMiner == width)) {
											widthReverse = !widthReverse;
											heightShiftMiner++;
											if (miningPos.getY() - 1 == world.getMinBuildHeight()) {
												heightShiftMiner = 1;
												isFinished = true;
											}
										} else {
											if (widthReverse) {
												widthShiftMiner -= deltaW;
											} else {
												widthShiftMiner += deltaW;
											}
										}
									} else {
										if (lengthReverse) {
											lengthShiftMiner -= deltaL;
										} else {
											lengthShiftMiner += deltaL;
										}
									}
									miningPos = new BlockPos(cornerStart.getX() - widthShiftMiner - deltaW, cornerStart.getY() - heightShiftMiner, cornerStart.getZ() - lengthShiftMiner - deltaL);
									state = world.getBlockState(miningPos);
									blockSkip++;
								}
								float strength = state.getDestroySpeed(world, miningPos);
								tickDelayMiner = (int) strength;
								if (!skipBlock(state) && strength >= 0) {
									posForClient = new BlockPos(miningPos.getX(), miningPos.getY(), miningPos.getZ());
									mineBlock(miningPos, state, strength, world, inv.getItem(0), inv, getPlayer((ServerLevel) world));
									electro.joules(electro.getJoulesStored() - Constants.QUARRY_USAGE_PER_TICK * quarryPowerMultiplier);
								}
								if ((lengthReverse ? lengthShiftMiner == 0 : lengthShiftMiner == length)) {
									if ((widthReverse ? widthShiftMiner == 0 : widthShiftMiner == width)) {
										widthReverse = !widthReverse;
										heightShiftMiner++;
										if (miningPos.getY() - 1 == world.getMinBuildHeight()) {
											heightShiftMiner = 1;
											isFinished = true;
										}
									} else {
										if (widthReverse) {
											widthShiftMiner -= deltaW;
										} else {
											widthShiftMiner += deltaW;
										}
									}
								} else {
									if (lengthReverse) {
										lengthShiftMiner -= deltaL;
									} else {
										lengthShiftMiner += deltaL;
									}
								}
							}
						}
					}
				} else if (isFinished && !hasHandledDecay) {
					handleFramesDecay();
					hasHandledDecay = true;
				}
			} else if (tick.getTicks() % (3 + tickDelayCA) == 0 && !isFinished) {
				if (isAreaCleared) {
					checkRing();
				} else {
					clearArea();
				}
			}
		} else if (hasCorners() && !hasHandledDecay && isAreaCleared) {
			handleFramesDecay();
		}
		if (tick.getTicks() % 5 == 0) {
			checkComponents();
		}

	}

	// TODO make this a little more fluid
	private void tickClient(ComponentTickable tick) {
		BlockPos pos = getBlockPos();
		ClientEvents.quarryArm.remove(pos);
		if (hasClientCorners() && clientMiningPos != null) {
			BlockPos miningCentered = clientMiningPos.offset(0.5, 0.5, 0.5);
			BlockPos startCentered = clientCorners.get(3).offset(0.5, 0.5, 0.5);
			BlockPos endCentered = clientCorners.get(0).offset(0.5, 0.5, 0.5);

			double widthLeft, widthRight, widthTop, widthBottom;
			AABB left, right, bottom, top, downArm, downHead;

			List<AABB> boxes = new ArrayList<>();

			double x = miningCentered.getX();
			double z = miningCentered.getZ();
			double y = startCentered.getY() + 0.5;

			double deltaY = startCentered.getY() - clientMiningPos.getY() - 1;

			downArm = new AABB(x + 0.25, y, z + 0.25, x + 0.75, y - deltaY, z + 0.75);
			downHead = new AABB(x + 0.3125, y - deltaY, z + 0.3125, x + 0.6875, y - deltaY - 0.5, z + 0.6875);

			Direction facing = ((ComponentDirection) getComponent(ComponentType.Direction)).getDirection().getOpposite();
			switch (facing) {
			case NORTH, SOUTH:

				widthLeft = x - startCentered.getX();
				widthRight = x - endCentered.getX();
				widthTop = z - startCentered.getZ();
				widthBottom = z - endCentered.getZ();

				if (facing == Direction.SOUTH) {
					if (clientOnRight) {
						left = new AABB(x, y - 0.25, z + 0.25, x - widthLeft + 0.25, y + 0.25, z + 0.75);
						right = new AABB(x, y - 0.25, z + 0.25, x - widthRight + 0.75, y + 0.25, z + 0.75);
					} else {
						left = new AABB(x, y - 0.25, z + 0.25, x - widthLeft + 0.75, y + 0.25, z + 0.75);
						right = new AABB(x, y - 0.25, z + 0.25, x - widthRight + 0.25, y + 0.25, z + 0.75);
					}
					bottom = new AABB(x + 0.25, y - 0.25, z, x + 0.75, y + 0.25, z - widthBottom + 0.25);
					top = new AABB(x + 0.25, y - 0.25, z, x + 0.75, y + 0.25, z - widthTop + 0.75);
				} else {
					if (clientOnRight) {
						left = new AABB(x, y - 0.25, z + 0.25, x - widthLeft + 0.75, y + 0.25, z + 0.75);
						right = new AABB(x, y - 0.25, z + 0.25, x - widthRight + 0.25, y + 0.25, z + 0.75);
					} else {
						left = new AABB(x, y - 0.25, z + 0.25, x - widthLeft + 0.25, y + 0.25, z + 0.75);
						right = new AABB(x, y - 0.25, z + 0.25, x - widthRight + 0.75, y + 0.25, z + 0.75);
					}
					bottom = new AABB(x + 0.25, y - 0.25, z, x + 0.75, y + 0.25, z - widthBottom + 0.75);
					top = new AABB(x + 0.25, y - 0.25, z, x + 0.75, y + 0.25, z - widthTop + 0.25);
				}

				boxes.add(left);
				boxes.add(right);
				boxes.add(bottom);
				boxes.add(top);
				boxes.add(downArm);
				boxes.add(downHead);

				break;
			case EAST, WEST:

				widthLeft = miningCentered.getZ() - startCentered.getZ();
				widthRight = miningCentered.getZ() - endCentered.getZ();
				widthTop = miningCentered.getX() - startCentered.getX();
				widthBottom = miningCentered.getX() - endCentered.getX();

				if (facing == Direction.WEST) {
					if (clientOnRight) {
						left = new AABB(x + 0.25, y - 0.25, z, x + 0.75, y + 0.25, z - widthLeft + 0.25);
						right = new AABB(x + 0.25, y - 0.25, z, x + 0.75, y + 0.25, z - widthRight + 0.75);
					} else {
						left = new AABB(x + 0.25, y - 0.25, z, x + 0.75, y + 0.25, z - widthLeft + 0.75);
						right = new AABB(x + 0.25, y - 0.25, z, x + 0.75, y + 0.25, z - widthRight + 0.25);
					}
					bottom = new AABB(x, y - 0.25, z + 0.25, x - widthBottom + 0.75, y + 0.25, z + 0.75);
					top = new AABB(x, y - 0.25, z + 0.25, x - widthTop + 0.25, y + 0.25, z + 0.75);
				} else {
					if (clientOnRight) {
						left = new AABB(x + 0.25, y - 0.25, z, x + 0.75, y + 0.25, z - widthLeft + 0.75);
						right = new AABB(x + 0.25, y - 0.25, z, x + 0.75, y + 0.25, z - widthRight + 0.25);
					} else {
						left = new AABB(x + 0.25, y - 0.25, z, x + 0.75, y + 0.25, z - widthLeft + 0.25);
						right = new AABB(x + 0.25, y - 0.25, z, x + 0.75, y + 0.25, z - widthRight + 0.75);
					}
					bottom = new AABB(x, y - 0.25, z + 0.25, x - widthBottom + 0.25, y + 0.25, z + 0.75);
					top = new AABB(x, y - 0.25, z + 0.25, x - widthTop + 0.75, y + 0.25, z + 0.75);
				}

				boxes.add(left);
				boxes.add(right);
				boxes.add(bottom);
				boxes.add(top);
				boxes.add(downArm);
				boxes.add(downHead);

				break;
			default:
				break;
			}

			ClientEvents.quarryArm.put(pos, boxes);

		}
	}

	// There is sadly not really a better way to do this
	private void maintainMiningArea() {
		Level world = getLevel();
		BlockPos cornerStart = corners.get(3);
		BlockPos cornerEnd = corners.get(0);
		int deltaW = (int) Math.signum(cornerStart.getX() - cornerEnd.getX());
		int deltaL = (int) Math.signum(cornerStart.getZ() - cornerEnd.getZ());
		int width = cornerStart.getX() - cornerEnd.getX() - 2 * deltaW;
		int length = cornerStart.getZ() - cornerEnd.getZ();
		BlockPos startPos = new BlockPos(cornerStart.getX() - widthShiftMaintainMining - deltaW, cornerStart.getY() - 1, cornerStart.getZ() - deltaL);
		BlockPos endPos = new BlockPos(cornerStart.getX() - widthShiftMaintainMining - deltaW, miningPos.getY() + 1, cornerStart.getZ() - length + deltaL);
		Stream<BlockPos> positions = BlockPos.betweenClosedStream(startPos, endPos);
		positions.forEach(pos -> {
			BlockState state = world.getBlockState(pos);
			if (!skipBlock(state)) {
				world.setBlockAndUpdate(pos, AIR);
				world.playSound(null, pos, state.getSoundType().getBreakSound(), SoundSource.BLOCKS, 0.5F, 1.0F);
			}
		});
		if (widthShiftMaintainMining == width) {
			widthShiftMaintainMining = 0;
		} else {
			widthShiftMaintainMining += deltaW;
		}

	}

	private void cleanRing() {
		Level world = getLevel();
		BlockPos cornerStart = corners.get(3);
		BlockPos cornerEnd = corners.get(0);
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
				world.setBlockAndUpdate(pos, AIR);
				world.playSound(null, pos, state.getSoundType().getBreakSound(), SoundSource.BLOCKS, 0.5F, 1.0F);
			}
		});
		if (widthShiftCR == width) {
			widthShiftCR = 0;
		} else {
			widthShiftCR += deltaW;
		}
	}

	private void mineBlock(BlockPos pos, BlockState state, float strength, Level world, ItemStack drillHead, ComponentInventory inv, Player player) {
		if (player != null || Constants.BYPASS_CLAIMS) {
			boolean sucess = world.destroyBlock(pos, false, player);
			if (sucess) {
				SubtypeDrillHead head = ((ItemDrillHead) drillHead.getItem()).head;
				if (!head.isUnbreakable) {
					int durabilityUsed = (int) (Math.ceil(strength) / (unbreakingLevel + 1.0F));
					if (drillHead.getDamageValue() + durabilityUsed >= drillHead.getMaxDamage()) {
						world.playSound(null, getBlockPos(), SoundEvents.ITEM_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
					}
					drillHead.setDamageValue(drillHead.getDamageValue() + durabilityUsed);
				}
				// TODO make this work with custom mining tiers
				ItemStack pickaxe = new ItemStack(Items.NETHERITE_PICKAXE);
				if (silkTouchLevel > 0) {
					pickaxe.enchant(Enchantments.SILK_TOUCH, silkTouchLevel);
				} else if (fortuneLevel > 0) {
					pickaxe.enchant(Enchantments.BLOCK_FORTUNE, fortuneLevel);
				}
				List<ItemStack> lootItems = Block.getDrops(state, (ServerLevel) world, pos, null, null, pickaxe);
				List<ItemStack> voidItemStacks = inv.getInputContents().get(0);
				voidItemStacks.remove(0);
				List<Item> voidItems = new ArrayList<>();
				voidItemStacks.forEach(h -> {
					voidItems.add(h.getItem());
				});
				List<ItemStack> items = new ArrayList<>();

				if (hasItemVoid) {
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
		}
	}

	// ironic how simple it is when you need to check the whole area
	private void clearArea() {
		ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
		quarryPowerUsage = Constants.QUARRY_USAGE_PER_TICK;
		isPowered = electro.getJoulesStored() >= quarryPowerUsage;
		if (hasCorners() && isPowered) {
			Level world = getLevel();
			BlockPos start = corners.get(3);
			BlockPos end = corners.get(0);
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
					} else {
						widthShiftCA += deltaW;
					}
				} else {
					heightShiftCA += deltaH;
				}
				checkPos = new BlockPos(start.getX() - widthShiftCA, start.getY(), start.getZ() - heightShiftCA);
				state = world.getBlockState(checkPos);
				blockSkip++;
			}
			if (strength >= 0 && electro.getJoulesStored() >= quarryPowerUsage * strength) {
				boolean sucess = false;
				if (!skipBlock(state)) {
					tickDelayCA = (int) Math.ceil(strength / 5.0F);
					electro.joules(electro.getJoulesStored() - quarryPowerUsage * strength);
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

	private void fixBrokenFrames() {
		Level world = getLevel();
		Triple<BlockPos, Direction, Boolean> blockInfo = brokenFrames.get(0);
		if (blockInfo.getRight()) {
			world.setBlockAndUpdate(blockInfo.getLeft(), DeferredRegisters.blockFrameCorner.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, Boolean.FALSE));
		} else {
			world.setBlockAndUpdate(blockInfo.getLeft(), DeferredRegisters.blockFrame.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, Boolean.FALSE).setValue(GenericEntityBlock.FACING, blockInfo.getMiddle()));
		}
		world.playSound(null, blockInfo.getLeft(), SoundEvents.ANVIL_PLACE, SoundSource.BLOCKS, 0.5F, 1.0F);
		lastFixed = blockInfo.getLeft();
		lastIsCorner = blockInfo.getRight();
		brokenFrames.remove(0);
	}

	private void checkRing() {
		ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
		if (electro.getJoulesStored() >= Constants.QUARRY_USAGE_PER_TICK && hasCorners()) {
			electro.joules(electro.getJoulesStored() - Constants.QUARRY_USAGE_PER_TICK);
			brokenFrames.clear();
			BlockState cornerState = DeferredRegisters.blockFrameCorner.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, Boolean.FALSE);
			BlockPos quarryPos = getBlockPos();
			Level world = getLevel();
			BlockPos frontOfQuarry = corners.get(0);
			BlockPos foqFar = corners.get(1);
			BlockPos foqCorner = corners.get(2);
			BlockPos farCorner = corners.get(3);
			Direction facing = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection().getOpposite();
			if (prevPos != null) {
				TileFrame frame = (TileFrame) world.getBlockEntity(prevPos);
				frame.setQuarryPos(quarryPos);
				frame.setCorner(prevIsCorner);
				if (hasAllStrips()) {
					hasRing = true;
					prevPos = null;
					isFinished = false;
					heightShiftMiner = 1;
					widthShiftMiner = 0;
					lengthShiftMiner = 0;
					quarryPowerUsage = 0;
					BlockPos cornerStart = corners.get(3);
					BlockPos cornerEnd = corners.get(0);
					int deltaW = (int) Math.signum(cornerStart.getX() - cornerEnd.getX());
					int deltaL = (int) Math.signum(cornerStart.getZ() - cornerEnd.getZ());
					miningPos = new BlockPos(cornerStart.getX() - deltaW, cornerStart.getY() - heightShiftMiner, cornerStart.getZ() - deltaL);
					posForClient = new BlockPos(miningPos.getX(), miningPos.getY(), miningPos.getZ());
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
					return;
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
					return;
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
					return;
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
					return;
				}
				break;
			default:
				break;
			}
		}
	}

	private void stripWithCorners(Level world, BlockPos startPos, BlockPos endPos, int startCV, int endCV, Direction relative, Direction frameFace, BlockState cornerState, boolean currPosX, boolean top) {
		if (currPos == null) {
			currPos = startPos;
		}
		if ((currPosX ? currPos.getX() : currPos.getZ()) == startCV) {
			world.setBlockAndUpdate(startPos, cornerState);
			prevIsCorner = true;
		} else if ((currPosX ? currPos.getX() : currPos.getZ()) == endCV) {
			world.setBlockAndUpdate(endPos, cornerState);
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
			world.setBlockAndUpdate(currPos, DeferredRegisters.blockFrame.defaultBlockState().setValue(GenericEntityBlock.FACING, frameFace).setValue(BlockStateProperties.WATERLOGGED, Boolean.FALSE));
			prevIsCorner = false;
		}
		world.playSound(null, currPos, SoundEvents.ANVIL_PLACE, SoundSource.BLOCKS, 0.5F, 1.0F);
		prevPos = new BlockPos(currPos.getX(), currPos.getY(), currPos.getZ());
		currPos = currPos.relative(cornerOnRight ? relative.getOpposite() : relative);
	}

	private void strip(Level world, BlockPos startPos, int endCV, Direction relative, Direction frameFace, boolean currPosX, boolean left) {
		if (currPos == null) {
			currPos = startPos.relative(relative);
		}
		world.setBlockAndUpdate(currPos, DeferredRegisters.blockFrame.defaultBlockState().setValue(GenericEntityBlock.FACING, cornerOnRight ? frameFace.getOpposite() : frameFace).setValue(BlockStateProperties.WATERLOGGED, Boolean.FALSE));
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
		if (leftEntity != null && leftEntity instanceof TileMotorComplex complex && ((ComponentDirection) complex.getComponent(ComponentType.Direction)).getDirection() == left) {
			rightEntity = world.getBlockEntity(machinePos.relative(right));
			if (rightEntity != null && rightEntity instanceof TileSeismicRelay relay && ((ComponentDirection) relay.getComponent(ComponentType.Direction)).getDirection() == quarryDir.getDirection()) {
				corners = relay.markerLocs;
				cornerOnRight = relay.cornerOnRight;
				relay = relay;
				aboveEntity = world.getBlockEntity(machinePos.above());
				if (aboveEntity != null && aboveEntity instanceof TileCoolantResavoir resavoir) {
					hasComponents = true;
					complex = complex;
					resavoir = resavoir;
				} else {
					hasComponents = false;
				}
			} else {
				hasComponents = false;
			}
		} else if (leftEntity != null && leftEntity instanceof TileSeismicRelay relay && ((ComponentDirection) relay.getComponent(ComponentType.Direction)).getDirection() == quarryDir.getDirection()) {
			corners = relay.markerLocs;
			cornerOnRight = relay.cornerOnRight;
			relay = relay;
			rightEntity = world.getBlockEntity(machinePos.relative(right));
			if (rightEntity != null && rightEntity instanceof TileMotorComplex complex && ((ComponentDirection) complex.getComponent(ComponentType.Direction)).getDirection() == right) {
				aboveEntity = world.getBlockEntity(machinePos.above());
				if (aboveEntity != null && aboveEntity instanceof TileCoolantResavoir resavoir) {
					hasComponents = true;
					complex = complex;
					resavoir = resavoir;
				} else {
					hasComponents = false;
				}
			} else {
				hasComponents = false;
			}
		} else {
			hasComponents = false;
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

	public void addBroken(Triple<BlockPos, Direction, Boolean> frameInfo) {
		brokenFrames.add(frameInfo);
	}

	private boolean hasCorners() {
		return corners.size() > 3;
	}

	public boolean hasClientCorners() {
		return clientCorners.size() > 3;
	}

	private boolean skipBlock(BlockState state) {
		return state.isAir() || !state.getFluidState().is(Fluids.EMPTY);
	}

	private void createPacket(CompoundTag nbt) {
		if (posForClient != null) {
			nbt.putInt("clientMiningX", posForClient.getX());
			nbt.putInt("clientMiningY", posForClient.getY());
			nbt.putInt("clientMiningZ", posForClient.getZ());
		}
		if (hasCorners()) {
			nbt.putBoolean("clientOnRight", cornerOnRight);
			nbt.putInt("clientCornerSize", corners.size());
			for (int i = 0; i < corners.size(); i++) {
				BlockPos pos = corners.get(i);
				nbt.putInt("clientCornerX" + i, pos.getX());
				nbt.putInt("clientCornerY" + i, pos.getY());
				nbt.putInt("clientCornerZ" + i, pos.getZ());
			}
		}

		nbt.putBoolean("clientVoid", hasItemVoid);
		nbt.putInt("clientFortune", fortuneLevel);
		nbt.putInt("clientSilk", silkTouchLevel);
		nbt.putInt("clientUnbreaking", unbreakingLevel);

		nbt.putDouble("clientUsage", quarryPowerUsage);
		nbt.putBoolean("clientPowered", isPowered);

		nbt.putBoolean("isFinished", isFinished);
		nbt.putBoolean("hasHead", hasHead);
	}

	private void readPacket(CompoundTag nbt) {
		clientCorners.clear();
		if (nbt.contains("clientMiningX")) {
			clientMiningPos = new BlockPos(nbt.getInt("clientMiningX"), nbt.getInt("clientMiningY"), nbt.getInt("clientMiningZ"));
		} else {
			clientMiningPos = null;
		}
		if (nbt.contains("clientCornerSize")) {
			clientOnRight = nbt.getBoolean("clientOnRight");
			int cornerSize = nbt.getInt("clientCornerSize");
			for (int i = 0; i < cornerSize; i++) {
				clientCorners.add(new BlockPos(nbt.getInt("clientCornerX" + i), nbt.getInt("clientCornerY" + i), nbt.getInt("clientCornerZ" + i)));
			}
		} else {
			clientCorners.clear();
		}

		clientItemVoid = nbt.getBoolean("clientVoid");
		clientFortuneLevel = nbt.getInt("clientFortune");
		clientSilkTouchLevel = nbt.getInt("clientSilk");
		clientUnbreakingLevel = nbt.getInt("clientUnbreaking");

		clientPowerUsage = nbt.getDouble("clientUsage");
		clientIsPowered = nbt.getBoolean("clientPowered");

		clientFinished = nbt.getBoolean("isFinished");
		clientHead = nbt.getBoolean("hasHead");
	}

	@Override
	public void saveAdditional(CompoundTag compound) {
		compound.putBoolean("hasComponents", hasComponents);
		compound.putBoolean("hasRing", hasRing);

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

		compound.putInt("brokenSize", brokenFrames.size());
		for (int i = 0; i < brokenFrames.size(); i++) {
			Triple<BlockPos, Direction, Boolean> blockInfo = brokenFrames.get(i);
			compound.putInt("frameX" + i, blockInfo.getLeft().getX());
			compound.putInt("frameY" + i, blockInfo.getLeft().getY());
			compound.putInt("frameZ" + i, blockInfo.getLeft().getZ());
			compound.putString("direction" + i, blockInfo.getMiddle().getName().toLowerCase());
			compound.putBoolean("isCorner" + i, blockInfo.getRight());
		}

		if (lastFixed != null) {
			compound.putInt("fixedX", lastFixed.getX());
			compound.putInt("fixedY", lastFixed.getY());
			compound.putInt("fixedZ", lastFixed.getZ());
		}

		compound.putBoolean("lastIsCorner", lastIsCorner);

		compound.putInt("cornerSize", corners.size());
		for (int i = 0; i < corners.size(); i++) {
			BlockPos pos = corners.get(i);
			compound.putInt("cornerX" + i, pos.getX());
			compound.putInt("cornerY" + i, pos.getY());
			compound.putInt("cornerZ" + i, pos.getZ());
		}

		compound.putBoolean("hasDecayed", hasHandledDecay);

		compound.putBoolean("onRight", cornerOnRight);
		compound.putBoolean("areaClear", isAreaCleared);

		compound.putInt("heightShiftCA", heightShiftCA);
		compound.putInt("widthShiftCA", widthShiftCA);
		compound.putInt("tickDelayCA", tickDelayCA);

		compound.putInt("lengthShiftMiner", lengthShiftMiner);
		compound.putInt("heightShiftMiner", heightShiftMiner);
		compound.putInt("widthShiftMiner", widthShiftMiner);
		compound.putInt("tickDelayMiner", tickDelayMiner);

		if (miningPos != null) {
			compound.putInt("miningX", miningPos.getX());
			compound.putInt("miningY", miningPos.getY());
			compound.putInt("miningZ", miningPos.getZ());
		}

		if (posForClient != null) {
			compound.putInt("cMiningX", posForClient.getX());
			compound.putInt("cMiningY", posForClient.getY());
			compound.putInt("cMiningZ", posForClient.getZ());
		}

		compound.putBoolean("isFinished", isFinished);
		compound.putBoolean("lengthReverse", lengthReverse);
		compound.putBoolean("widthReverse", widthReverse);

		compound.putInt("widthShiftCR", widthShiftCR);

		compound.putInt("widthShiftMaintainMining", widthShiftMaintainMining);

		if (placedBy != null) {
			compound.putUUID("placedBy", placedBy);
		}

		super.saveAdditional(compound);
	}

	@Override
	public void load(CompoundTag compound) {
		hasComponents = compound.getBoolean("hasComponents");
		hasRing = compound.getBoolean("hasRing");

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

		brokenFrames = new ArrayList<>();
		int size = compound.getInt("brokenSize");
		for (int i = 0; i < size; i++) {
			BlockPos pos = new BlockPos(compound.getInt("frameX" + i), compound.getInt("frameY" + i), compound.getInt("frameZ" + i));
			Direction direction = Direction.byName(compound.getString("direction" + i));
			Boolean bool = compound.getBoolean("isCorner" + i);
			brokenFrames.add(Triple.of(pos, direction, bool));
		}

		if (compound.contains("fixedX")) {
			lastFixed = new BlockPos(compound.getInt("fixedX"), compound.getInt("fixedY"), compound.getInt("fixedZ"));
		} else {
			lastFixed = null;
		}
		lastIsCorner = compound.getBoolean("lastIsCorner");
		corners = new ArrayList<>();
		int cornerSize = compound.getInt("cornerSize");
		for (int i = 0; i < cornerSize; i++) {
			corners.add(new BlockPos(compound.getInt("cornerX" + i), compound.getInt("cornerY" + i), compound.getInt("cornerZ" + i)));
		}

		hasHandledDecay = compound.getBoolean("hasDecayed");

		cornerOnRight = compound.getBoolean("onRight");
		isAreaCleared = compound.getBoolean("areaClear");

		heightShiftCA = compound.getInt("heightShiftCA");
		widthShiftCA = compound.getInt("widthShiftCA");
		tickDelayCA = compound.getInt("tickDelayCA");

		lengthShiftMiner = compound.getInt("lengthShiftMiner");
		heightShiftMiner = compound.getInt("heightShiftMiner");
		widthShiftMiner = compound.getInt("widthShiftMiner");
		tickDelayMiner = compound.getInt("tickDelayMiner");

		if (compound.contains("miningX")) {
			miningPos = new BlockPos(compound.getInt("miningX"), compound.getInt("miningY"), compound.getInt("miningZ"));
		}

		if (compound.contains("cMiningX")) {
			posForClient = new BlockPos(compound.getInt("cMiningX"), compound.getInt("cMiningY"), compound.getInt("cMiningZ"));
		}

		isFinished = compound.getBoolean("isFinished");

		lengthReverse = compound.getBoolean("lengthReverse");
		widthReverse = compound.getBoolean("widthReverse");

		widthShiftCR = compound.getInt("widthShiftCR");

		widthShiftMaintainMining = compound.getInt("widthShiftMaintainMining");

		if (compound.contains("placedBy")) {
			placedBy = compound.getUUID("placedBy");
		}

		super.load(compound);
	}

	@Override
	public void setRemoved() {
		if (hasCorners() && isAreaCleared) {
			handleFramesDecay();
		}
		if (getLevel().isClientSide) {
			ClientEvents.quarryArm.remove(getBlockPos());
		}
		super.setRemoved();
	}

	// it is assumed that the block has marker corners when you call this method
	private void handleFramesDecay() {
		miningPos = null;
		posForClient = null;
		hasHandledDecay = true;
		isAreaCleared = false;
		hasRing = false;
		hasBottomStrip = false;
		hasTopStrip = false;
		hasLeftStrip = false;
		hasRightStrip = false;
		lengthReverse = false;
		widthReverse = false;
		Level world = getLevel();
		BlockPos frontOfQuarry = corners.get(0);
		BlockPos foqFar = corners.get(1);
		BlockPos foqCorner = corners.get(2);
		BlockPos farCorner = corners.get(3);
		for (BlockPos pos : corners) {
			world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
		}
		BlockPos.betweenClosedStream(foqCorner, frontOfQuarry).forEach(pos -> updateState(world, pos));
		BlockPos.betweenClosedStream(farCorner, foqFar).forEach(pos -> updateState(world, pos));
		BlockPos.betweenClosedStream(foqCorner, farCorner).forEach(pos -> updateState(world, pos));
		BlockPos.betweenClosedStream(frontOfQuarry, foqFar).forEach(pos -> updateState(world, pos));
	}

	private void updateState(Level world, BlockPos pos) {
		BlockEntity entity = world.getBlockEntity(pos);
		if (entity != null && entity instanceof TileFrame frame) {
			frame.setNoNotify();
			frame.setQuarryPos(null);
		}
		BlockState state = world.getBlockState(pos);
		if (state.is(DeferredRegisters.blockFrame) || state.is(DeferredRegisters.blockFrameCorner)) {
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

}
