package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import electrodynamics.api.sound.SoundAPI;
import electrodynamics.common.network.ElectricityUtilities;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.tile.GenericTileTicking;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandler;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fluids.FluidStack;

public class TileCombustionChamber extends GenericTileTicking {
    public static final int TICKS_PER_MILLIBUCKET = 200;
    public static final int TANK_CAPACITY = 100;
    public boolean running = false;
    private int burnTime;
    private CachedTileOutput output;

    public TileCombustionChamber() {
	super(DeferredRegisters.TILE_COMBUSTIONCHAMBER.get());
	addComponent(new ComponentDirection());
	addComponent(new ComponentTickable().tickServer(this::tickServer).tickClient(this::tickClient));
	addComponent(new ComponentPacketHandler().customPacketReader(this::readNBT).customPacketWriter(this::writeNBT).guiPacketReader(this::readNBT)
		.guiPacketWriter(this::writeNBT));
	addComponent(new ComponentElectrodynamic(this).relativeOutput(Direction.EAST));
	addComponent(new ComponentFluidHandler(this).addFluidTank(DeferredRegisters.fluidEthanol, TANK_CAPACITY, true)
		.relativeInput(Direction.WEST));
    }

    protected void tickServer(ComponentTickable tickable) {
	ComponentDirection direction = getComponent(ComponentType.Direction);
	Direction facing = direction.getDirection();
	if (output == null) {
	    output = new CachedTileOutput(world, pos.offset(facing.rotateY()));
	}
	if (tickable.getTicks() % 40 == 0) {
	    output.update();
	}
	ComponentFluidHandler tank = getComponent(ComponentType.FluidHandler);
	ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
	if (burnTime <= 0) {
	    boolean shouldSend = !running;
	    running = false;
	    FluidStack stack = tank.getStackFromFluid(DeferredRegisters.fluidEthanol, true);
	    if (stack.getAmount() > 0) {
		stack.setAmount(stack.getAmount() - 1);
		running = true;
		burnTime = TICKS_PER_MILLIBUCKET;
		shouldSend = true;
	    }
	    if (shouldSend) {
		this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
	    }
	} else {
	    running = true;
	    burnTime--;
	}
	if (running && burnTime > 0 && output.valid()) {
	    ElectricityUtilities.receivePower(output.getSafe(), facing.rotateY().getOpposite(),
		    TransferPack.joulesVoltage(Constants.COMBUSTIONCHAMBER_JOULES_PER_TICK, electro.getVoltage()), false);
	}
    }

    protected void tickClient(ComponentTickable tickable) {
	if (running && world.rand.nextDouble() < 0.15) {
	    world.addParticle(ParticleTypes.SMOKE, pos.getX() + world.rand.nextDouble(), pos.getY() + world.rand.nextDouble(),
		    pos.getZ() + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
	}
	if (running && tickable.getTicks() % 100 == 0) {
	    SoundAPI.playSound(SoundRegister.SOUND_COMBUSTIONCHAMBER.get(), SoundCategory.BLOCKS, 1, 1, pos);
	}
    }

    protected void writeNBT(CompoundNBT nbt) {
	nbt.putBoolean("running", running);
    }

    protected void readNBT(CompoundNBT nbt) {
	running = nbt.getBoolean("running");
    }
}
