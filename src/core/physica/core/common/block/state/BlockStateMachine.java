package physica.core.common.block.state;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IStringSerializable;
import physica.core.Physica;
import physica.core.common.block.BlockMachine;

public class BlockStateMachine extends BlockStateFacing {
	public static PropertyEnum<EnumMachine> TYPE = PropertyEnum.create("type", EnumMachine.class);

	public BlockStateMachine(BlockMachine block) {
		super(block, TYPE);
	}

	public enum EnumMachine implements IStringSerializable {
		ELECTRIC_FURNACE(TileEntityFurnace.class, EnumBlockRenderType.ENTITYBLOCK_ANIMATED); // TODO: Fix this
		private Class<? extends TileEntity> tileClass;
		private EnumBlockRenderType renderType;

		private boolean particle;
		private EnumParticleTypes particleType;
		private double particleSpeed;

		EnumMachine(Class<? extends TileEntity> tileClass, EnumBlockRenderType renderType) {
			this.tileClass = tileClass;
			this.renderType = renderType;
		}

		EnumMachine(Class<? extends TileEntity> tileClass) {
			this(tileClass, EnumBlockRenderType.ENTITYBLOCK_ANIMATED);
		}

		EnumMachine(Class<? extends TileEntity> tileClass, EnumParticleTypes particleType, double particleSpeed) {
			this(tileClass);
			particle = true;
			this.particleType = particleType;
			this.particleSpeed = particleSpeed;
		}

		EnumMachine(Class<? extends TileEntity> tileClass, EnumParticleTypes particleType) {
			this(tileClass, particleType, 0);
		}

		EnumMachine(Class<? extends TileEntity> tileClass, double particleSpeed) {
			this(tileClass);
			particle = true;
			this.particleSpeed = particleSpeed;
		}

		@Override
		public String getName() {
			return name().toLowerCase();
		}

		public Class<? extends TileEntity> getTileClass() {
			return tileClass;
		}

		public TileEntity getTileAsInstance() {
			try {
				return tileClass.newInstance();
			} catch (Exception e) {
				Physica.logger.error("Unable to indirectly create tile entity.");
				e.printStackTrace();
			}

			return null;
		}

		public EnumBlockRenderType getRenderType() {
			return renderType;
		}

		public boolean hasParticle() {
			return particle;
		}

		public EnumParticleTypes getParticleType() {
			return particleType;
		}

		public double getParticleSpeed() {
			return particleSpeed;
		}
	}

}