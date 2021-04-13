package electrodynamics.common.tile;

import java.util.List;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.common.item.ItemFrequencyCard;
import electrodynamics.prefab.tile.GenericTileTicking;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.server.ServerWorld;

public class TileTeleporter extends GenericTileTicking {
    public int xCoord;
    public int yCoord;
    public int zCoord;
    public String world;

    public TileTeleporter() {
	super(DeferredRegisters.TILE_TELEPORTER.get());
	addComponent(new ComponentDirection());
	addComponent(new ComponentTickable().tickServer(this::tickServer));
	addComponent(new ComponentPacketHandler());
	addComponent(
		new ComponentElectrodynamic(this).maxJoules(5000000).voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * 4).input(Direction.DOWN));

    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
	return super.getRenderBoundingBox().grow(3);
    }

    protected void tickServer(ComponentTickable tickable) {
	if (tickable.getTicks() % 40 == 0) {
	    this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
	}
	ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
//	if (electro.getJoulesStored() == electro.getMaxJoulesStored()) {
	AxisAlignedBB BB = new AxisAlignedBB(pos).expand(1, 2, 1);
	List<PlayerEntity> player = getWorld().getEntitiesWithinAABB(EntityType.PLAYER, BB, en -> true);
	if (!player.isEmpty()) {
	    ServerWorld serverWorld = ItemFrequencyCard.getFromNBT((ServerWorld) getWorld(), this.world);
	    if (serverWorld == player.get(0).getEntityWorld()) {
		player.get(0).teleportKeepLoaded(xCoord, yCoord + 1, zCoord);
		electro.joules(0);
	    }
	}
//	}
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
	if (world != null) {
	    compound.putInt("xCoord", xCoord);
	    compound.putInt("yCoord", yCoord);
	    compound.putInt("zCoord", zCoord);
	    compound.putString("world", this.world);
	}
	return super.write(compound);
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
	super.read(state, compound);
	if (compound.contains("world")) {
	    xCoord = compound.getInt("xCoord");
	    yCoord = compound.getInt("yCoord");
	    zCoord = compound.getInt("zCoord");
	    world = compound.getString("world");
	}
    }
}
