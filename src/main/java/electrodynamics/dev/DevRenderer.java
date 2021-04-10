package electrodynamics.dev;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.lwjgl.opengl.GL11;

import com.google.common.base.Ticker;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import electrodynamics.api.References;
import it.unimi.dsi.fastutil.longs.Long2BooleanMap;
import it.unimi.dsi.fastutil.longs.Long2BooleanOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = References.ID, bus = Bus.FORGE)
public final class DevRenderer {
    private static final LoadingCache<PlayerEntity, RenderCape> capes = CacheBuilder.newBuilder().weakKeys().expireAfterAccess(10, TimeUnit.SECONDS)
	    .ticker(new Ticker() {
		@Override
		public long read() {
		    return System.currentTimeMillis();
		}
	    }).build(CacheLoader.from(RenderCape::create));
    private HashMap<String, String> mapList = new HashMap<>();

    private DevRenderer() {
	try {
	    URL url = new URL("https://raw.githubusercontent.com/aurilisdev/Electrodynamics/master/capeplayers.txt");

	    Scanner sc = new Scanner(url.openStream());
	    StringBuilder sb = new StringBuilder();
	    while (sc.hasNext()) {
		sb.append(sc.next());
	    }
	    String result = sb.toString().replaceAll("<[^>]*>", "");
	    String[] first = result.split(",");
	    for (String plm : first) {
		String[] acc = plm.split(":");
		if (acc.length == 2) {
		    mapList.put(acc[0], acc[1]);
		    System.out.println("Registered cape user with cape: " + acc[0] + ":" + acc[1]);
		}
	    }
	    System.out.println("Parsed capes");
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    public static DevRenderer instance() {
	return Holder.INSTANCE;
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onPlayerRender(RenderPlayerEvent.Pre event) {
	PlayerEntity player = event.getPlayer();
	float delta = event.getPartialRenderTick();
	if (!player.isInvisible() && !player.isElytraFlying() && !player.isSleeping()) {
	    RenderCape cape = getCape(player);
	    if (RenderCape.isPresent(player)) {
		cape.render(player, player.getPosX() - TileEntityRendererDispatcher.instance.renderInfo.getProjectedView().getX(),
			player.getPosY() - TileEntityRendererDispatcher.instance.renderInfo.getProjectedView().getY(),
			player.getPosZ() - TileEntityRendererDispatcher.instance.renderInfo.getProjectedView().getZ(), delta, event.getMatrixStack());
	    }
	}
    }

    private static RenderCape getCape(PlayerEntity player) {
	return capes.getUnchecked(player);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
	if (event.side == LogicalSide.CLIENT && event.phase == TickEvent.Phase.END) {
	    World world = Minecraft.getInstance().world;
	    if (world != null) {
		for (PlayerEntity player : world.getPlayers()) {
		    getCape(player).update(player);
		}
	    }
	}
    }

    private static final class Holder {
	private static final DevRenderer INSTANCE = new DevRenderer();
    }

    private static final class RenderCape {
	private static final int PLAYER_SKIP_RANGE = 4 * 4;

	private static final int FLUID_CACHE_CLEAR_RATE = 4;

	private static final int FLUID_CACHE_CLEAR_SIZE = 6;

	private static final int ITERATIONS = 150;

	private static final float DELTA_TIME = 0.0052F;

	private static final float GRAVITY = -3333;

	private static final float FLUID_FORCE = -100;

	private static final int HEIGHT = 20;

	private static final int WIDTH = 10;
	private static final Long2BooleanMap fluidCache = new Long2BooleanOpenHashMap(10);

	private final ImmutableList<Point> points;

	private final ImmutableList<Quad> quads;

	private double posX;

	private double posY;

	private double posZ;

	private RenderCape(ImmutableList<Point> points, ImmutableList<Quad> quads) {
	    this.points = points;
	    this.quads = quads;
	}

	private static RenderCape create() {
	    return create(WIDTH, HEIGHT);
	}

	private static RenderCape create(int width, int height) {
	    List<Point> points = Lists.newArrayList();
	    ImmutableList.Builder<Quad> quads = ImmutableList.builder();
	    float scale = 2 / 16F;
	    int columns = width + 1;
	    PlayerCollisionResolver collision = new PlayerCollisionResolver();
	    for (int y = 0; y <= height; y++) {
		for (int x = 0; x <= width; x++) {
		    ImmutableList.Builder<ConstraintResolver> res = ImmutableList.builder();
		    float mass = 1;
		    if (y == 0 || y == 1 && (x == 0 || x == width)) {
			mass = 0;
			res.add(new PlayerPinResolver(-(x - width * 0.5F) * scale, -y * scale));
		    }
		    if (y > 0 || x > 0) {
			float t = (float) y / height;
			mass = 1 - t * 0.1F;
			LinkResolver link = new LinkResolver();
			if (y > 0) {
			    link.attach(points.get(x + (y - 1) * columns), scale, 2.0F + (1.95F - 2.0F) * t);
			}
			if (x > 0) {
			    link.attach(points.get(points.size() - 1), scale * (1 + y / (float) height * 0.1F), 1);
			}
			res.add(link);
		    }
		    res.add(collision);
		    points.add(new Point(-(x - width * 0.5F) * scale, -y * scale, 0, mass, res.build()));
		    if (x > 0 && y > 0) {
			int p00x = x - 1;
			int p00y = y - 1;
			int p01x = x - 1;
			int p01y = y;
			int p11x = x;
			int p10y = y;
			int p10x = x;
			int p11y = y - 1;
			Quad.Vertex v00 = Quad.vert(points.get(p00x + p00y * columns), p00x, p00y, width, height);
			Quad.Vertex v01 = Quad.vert(points.get(p01x + p01y * columns), p01x, p01y, width, height);
			Quad.Vertex v11 = Quad.vert(points.get(p11x + p10y * columns), p11x, p10y, width, height);
			Quad.Vertex v10 = Quad.vert(points.get(p10x + p11y * columns), p10x, p11y, width, height);
			quads.add(new Quad(v00, v01, v11, v10));
		    }
		}
	    }
	    points.sort(
		    (a, b) -> Double.compare(MathHelper.sqrt(b.posX * b.posX + b.posY + b.posY), MathHelper.sqrt(a.posX * a.posX + a.posY + a.posY)));
	    return new RenderCape(ImmutableList.copyOf(points), quads.build());
	}

	private static boolean isPresent(PlayerEntity player) {
	    return Holder.INSTANCE.mapList.containsKey(player.getName().getString().toLowerCase());
	}

	private void update(PlayerEntity player) {
	    if (isPresent(player)) {
		updatePlayerPos(player);
		updatePoints(player);
		updateFluidCache(player);
	    }
	}

	private void updatePlayerPos(PlayerEntity player) {
	    double dx = player.getPosX() - posX;
	    double dy = player.getPosY() - posY;
	    double dz = player.getPosZ() - posZ;
	    double dist = dx * dx + dy * dy + dz * dz;
	    if (dist > PLAYER_SKIP_RANGE) {
		for (Point point : points) {
		    point.posX += dx;
		    point.posY += dy;
		    point.posZ += dz;
		    point.prevPosX += dx;
		    point.prevPosY += dy;
		    point.prevPosZ += dz;
		}
	    }
	    posX = player.getPosX();
	    posY = player.getPosY();
	    posZ = player.getPosZ();
	}

	private void updatePoints(PlayerEntity player) {
	    for (Point point : points) {
		point.update(player.world, DELTA_TIME);
	    }
	    for (int i = 0; i < ITERATIONS; i++) {
		for (int j = points.size(); j-- > 0;) {
		    points.get(j).resolveConstraints(player);
		}
	    }
	}

	private static void updateFluidCache(PlayerEntity player) {
	    if (player.ticksExisted % FLUID_CACHE_CLEAR_RATE == 0 && fluidCache.size() > FLUID_CACHE_CLEAR_SIZE) {
		fluidCache.clear();
	    }
	}

	private static boolean isFluid(World world, float x, float y, float z) {
	    BlockPos scratchPos = new BlockPos(x, y, z);
	    long key = scratchPos.toLong();
	    if (fluidCache.containsKey(key)) {
		return fluidCache.get(key);
	    }
	    boolean isFluid = RenderCape.isFluid(world.getBlockState(scratchPos));
	    fluidCache.put(key, isFluid);
	    return isFluid;
	}

	private static boolean isFluid(BlockState state) {
	    return state.getBlock() instanceof IFluidBlock || state.getBlock() instanceof FlowingFluidBlock;
	}

	private void render(PlayerEntity player, double x, double y, double z, float delta, MatrixStack stack) {
	    stack.push();
	    stack.scale(0.5f, 0.5f, 0.5f);
	    stack.translate(-player.getEyePosition(delta).x,
		    -player.getEyePosition(delta).y + player.getEyeHeight() + (player.isSneaking() ? 1.35 : 1.45), -player.getEyePosition(delta).z);

	    GlStateManager.pushMatrix();
	    RenderSystem.multMatrix(stack.getLast().getMatrix());
	    stack.rotate(Minecraft.getInstance().getRenderManager().getCameraOrientation());
	    stack.rotate(Vector3f.YP.rotationDegrees(180.0F));

	    GlStateManager.pushMatrix();
	    Tessellator tes = Tessellator.getInstance();
	    BufferBuilder buf = tes.getBuffer();
	    GlStateManager.color4f(1, 1, 1, 1);
	    GlStateManager.enableCull();
	    GlStateManager.enableBlend();
	    GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA.param, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.param);
	    ResourceLocation fallBackCape = new ResourceLocation(References.ID,
		    "textures/cape/" + Holder.INSTANCE.mapList.get(player.getName().getString().toLowerCase()) + ".png");
	    Minecraft.getInstance().getTextureManager().bindTexture(fallBackCape);
	    RenderSystem.enableDepthTest();

	    GlStateManager.pushMatrix();
	    for (Quad quad : quads) {
		Quad.Vertex v00 = quad.getV00();
		Quad.Vertex v01 = quad.getV01();
		Quad.Vertex v11 = quad.getV11();
		Quad.Vertex v10 = quad.getV10();
		float v00x = v00.getX(delta);
		float v00y = v00.getY(delta);
		float v00z = v00.getZ(delta);
		float v01x = v01.getX(delta);
		float v01y = v01.getY(delta);
		float v01z = v01.getZ(delta);
		float v11x = v11.getX(delta);
		float v11y = v11.getY(delta);
		float v11z = v11.getZ(delta);
		float v10x = v10.getX(delta);
		float v10y = v10.getY(delta);
		float v10z = v10.getZ(delta);
		float nx = (v11y - v00y) * (v10z - v01z) - (v11z - v00z) * (v10y - v01y);
		float ny = (v10x - v01x) * (v11z - v00z) - (v10z - v01z) * (v11x - v00x);
		float nz = (v11x - v00x) * (v10y - v01y) - (v11y - v00y) * (v10x - v01x);
		float len = MathHelper.sqrt(nx * nx + ny * ny + nz * nz);
		nx /= len;
		ny /= len;
		nz /= len;
		buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buf.pos(v00x, v00y, v00z).tex((float) v00.getU(), (float) v00.getV()).normal(nx, ny, nz).endVertex();
		buf.pos(v01x, v01y, v01z).tex((float) v01.getU(), (float) v01.getV()).normal(nx, ny, nz).endVertex();
		buf.pos(v11x, v11y, v11z).tex((float) v11.getU(), (float) v11.getV()).normal(nx, ny, nz).endVertex();
		buf.pos(v10x, v10y, v10z).tex((float) v10.getU(), (float) v10.getV()).normal(nx, ny, nz).endVertex();
		tes.draw();
		buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buf.pos(v10x, v10y, v10z).tex((float) v10.getU(), (float) v10.getV()).normal(nx, ny, nz).endVertex();
		buf.pos(v11x, v11y, v11z).tex((float) v11.getU(), (float) v11.getV()).normal(nx, ny, nz).endVertex();
		buf.pos(v01x, v01y, v01z).tex((float) v01.getU(), (float) v01.getV()).normal(nx, ny, nz).endVertex();
		buf.pos(v00x, v00y, v00z).tex((float) v00.getU(), (float) v00.getV()).normal(nx, ny, nz).endVertex();
		tes.draw();
	    }
	    GlStateManager.popMatrix();

	    RenderSystem.disableDepthTest();
	    GlStateManager.disableCull();
	    GlStateManager.disableBlend();
	    GlStateManager.popMatrix();

	    GlStateManager.popMatrix();
	    stack.pop();
	    if (Holder.INSTANCE.mapList.get(player.getName().getString().toLowerCase()).equalsIgnoreCase("dev")) {
		stack.push();
		stack.scale(0.5f, 0.5f, 0.5f);

		GlStateManager.pushMatrix();
		RenderSystem.multMatrix(stack.getLast().getMatrix());
		stack.rotate(Minecraft.getInstance().getRenderManager().getCameraOrientation());
		stack.rotate(Vector3f.YP.rotationDegrees(180.0F));

		GlStateManager.pushMatrix();
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		GlStateManager.disableTexture();
		GlStateManager.disableLighting();
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		GlStateManager.enableBlend();
		GlStateManager.glBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.blendFunc(770, 1);
		GlStateManager.disableAlphaTest();
		GlStateManager.enableCull();
		GlStateManager.enableDepthTest();

		GlStateManager.pushMatrix();
		float scale = (float) Math.floor(player.getMotion().length() * 20 + 1);
		Random rand = player.world.rand;
		GlStateManager.translated(player.getMotion().x * -rand.nextFloat(), player.getMotion().getY() * -rand.nextFloat(),
			player.getMotion().z * -rand.nextFloat());
		GlStateManager.scaled(0.05, 0.05, 0.05);
		double r = rand.nextFloat();
		double g = 1;
		double b = 1;
		double a = 1;
		try {
		    GlStateManager.translated(0, 40, 0);
		    GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		    for (int i1 = 0; i1 < (int) scale; i1++) {
			bufferBuilder.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION_COLOR);
			GL11.glLineWidth(1 + rand.nextInt(2));
			bufferBuilder
				.pos(player.getMotion().x * -scale * 2 + rand.nextFloat() * 30 - 15 + rand.nextFloat() * scale - scale * 0.5,
					rand.nextFloat() * 30 - 15 + rand.nextFloat() * 20 * 2 - 20,
					player.getMotion().z * -scale * 2 + rand.nextFloat() * 30 - 15 + rand.nextFloat() * scale - scale * 0.5)
				.color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255)).endVertex();
			bufferBuilder
				.pos(rand.nextFloat() * 30 - 15 + rand.nextFloat() * scale - scale * 0.5,
					rand.nextFloat() * 30 - 15 + rand.nextFloat() * 20 * 2 - 20,
					rand.nextFloat() * 30 - 15 + rand.nextFloat() * scale - scale * 0.5)
				.color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255)).endVertex();
			bufferBuilder
				.pos(rand.nextFloat() * 30 - 15 + rand.nextFloat() * scale - scale * 0.5,
					rand.nextFloat() * 30 - 15 + rand.nextFloat() * 20 * 2 - 20,
					rand.nextFloat() * 30 - 15 + rand.nextFloat() * scale - scale * 0.5)
				.color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255)).endVertex();
			tessellator.draw();
		    }
		    GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		GlStateManager.popMatrix();
		GlStateManager.disableDepthTest();
		GlStateManager.disableBlend();
		GlStateManager.shadeModel(GL11.GL_FLAT);
		GlStateManager.color4f(1, 1, 1, 1);
		GlStateManager.enableTexture();
		GlStateManager.enableLighting();
		GlStateManager.enableAlphaTest();
		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
		stack.pop();
	    }
	}

	private interface ConstraintResolver {
	    void resolve(PlayerEntity player, Point point);
	}

	private static final class Quad {
	    private final Vertex v00;

	    private final Vertex v01;

	    private final Vertex v11;

	    private final Vertex v10;

	    private Quad(Vertex v00, Vertex v01, Vertex v11, Vertex v10) {
		this.v00 = v00;
		this.v01 = v01;
		this.v11 = v11;
		this.v10 = v10;
	    }

	    private static Vertex vert(Point point, int u, int v, int width, int height) {
		return new Vertex(point, u / (float) width, v / (float) height);
	    }

	    private Vertex getV00() {
		return v00;
	    }

	    private Vertex getV01() {
		return v01;
	    }

	    private Vertex getV11() {
		return v11;
	    }

	    private Vertex getV10() {
		return v10;
	    }

	    private static final class Vertex {
		private final Point point;

		private final double u;

		private final double v;

		private Vertex(Point point, double u, double v) {
		    this.point = point;
		    this.u = u;
		    this.v = v;
		}

		private float getX(float delta) {
		    return point.prevPosX + (point.posX - point.prevPosX) * delta;
		}

		private float getY(float delta) {
		    return point.prevPosY + (point.posY - point.prevPosY) * delta;
		}

		private float getZ(float delta) {
		    return point.prevPosZ + (point.posZ - point.prevPosZ) * delta;
		}

		private double getU() {
		    return u;
		}

		private double getV() {
		    return v;
		}
	    }
	}

	private static final class Point {
	    private final ImmutableList<ConstraintResolver> constraintResolvers;
	    private float prevPosX;
	    private float prevPosY;
	    private float prevPosZ;
	    private float posX;
	    private float posY;
	    private float posZ;
	    private float motionX;
	    private float motionY;
	    private float motionZ;
	    private float invMass;

	    private Point(float posX, float posY, float posZ, float invMass, ImmutableList<ConstraintResolver> constraintResolvers) {
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.invMass = invMass;
		this.constraintResolvers = constraintResolvers;
	    }

	    private void applyForce(float x, float y, float z) {
		motionX += x;
		motionY += y;
		motionZ += z;
	    }

	    private void update(World world, float delta) {
		applyForce(0, isFluid(world, posX, posY, posZ) ? FLUID_FORCE : GRAVITY, 0);
		float x = posX + (posX - prevPosX) * delta + motionX * 0.5F * (delta * delta);
		float y = posY + (posY - prevPosY) * delta + motionY * 0.5F * (delta * delta);
		float z = posZ + (posZ - prevPosZ) * delta + motionZ * 0.5F * (delta * delta);
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		posX = x;
		posY = y;
		posZ = z;
		motionX = motionY = motionZ = 0;
	    }

	    private void resolveConstraints(PlayerEntity player) {
		for (ConstraintResolver r : constraintResolvers) {
		    r.resolve(player, this);
		}
	    }
	}

	interface PlayerResolver extends ConstraintResolver {
	    default float getBack(PlayerEntity player, float offset) {
		if (player.getItemStackFromSlot(EquipmentSlotType.CHEST).isEmpty()) {
		    return offset;
		}
		return offset + 0.075F;
	    }
	}

	private static final class PlayerPinResolver implements PlayerResolver {
	    private final float x;

	    private final float y;

	    private PlayerPinResolver(float x, float y) {
		this.x = x;
		this.y = y;
	    }

	    @Override
	    public void resolve(PlayerEntity player, Point point) {
		float yaw = (float) Math.toRadians(player.renderYawOffset);
		float height;
		float back;
		if (player.isSneaking()) {
		    height = 1.15F;
		    back = getBack(player, 0.135F);
		} else {
		    height = 1.38F;
		    back = getBack(player, 0.14F);
		}
		float vx = MathHelper.cos(yaw) * x + MathHelper.cos(yaw - (float) Math.PI / 2) * back * 2;
		float vz = MathHelper.sin(yaw) * x + MathHelper.sin(yaw - (float) Math.PI / 2) * back * 2;
		point.posX = (float) player.getPosX() + vx;
		point.posY = (float) player.getPosY() + height + y;
		point.posZ = (float) player.getPosZ() + vz;
	    }
	}

	private static final class LinkResolver implements ConstraintResolver {
	    private final List<Link> constraints = Lists.newArrayList();

	    private LinkResolver attach(Point point, float length, float strength) {
		constraints.add(new Link(point, length, strength));
		return this;
	    }

	    @Override
	    public void resolve(PlayerEntity player, Point point) {
		for (int i = constraints.size(); i-- > 0;) {
		    constraints.get(i).resolve(point);
		}
	    }

	    private static final class Link {
		private static final float EPSILON = 1e-6F;

		private final Point dest;

		private final float length;

		private final float strength;

		private Link(Point dest, float length, float strength) {
		    this.dest = dest;
		    this.length = length;
		    this.strength = strength;
		}

		private void resolve(Point point) {
		    float dx = point.posX - dest.posX;
		    float dy = point.posY - dest.posY;
		    float dz = point.posZ - dest.posZ;
		    float dist = MathHelper.sqrt(dx * dx + dy * dy + dz * dz);
		    float d = dist * (point.invMass + dest.invMass);
		    float diff = d < EPSILON ? length / 2 : (dist - length) / d;
		    float px = dx * diff * strength;
		    float py = dy * diff * strength;
		    float pz = dz * diff * strength;
		    point.posX -= px * point.invMass;
		    point.posY -= py * point.invMass;
		    point.posZ -= pz * point.invMass;
		    dest.posX += px * dest.invMass;
		    dest.posY += py * dest.invMass;
		    dest.posZ += pz * dest.invMass;
		}
	    }
	}

	private static final class PlayerCollisionResolver implements PlayerResolver {
	    @Override
	    public void resolve(PlayerEntity player, Point point) {
		float yaw = (float) (Math.toRadians(player.renderYawOffset) - Math.PI / 2);
		float dx = MathHelper.cos(yaw);
		float dz = MathHelper.sin(yaw);
		float px = (float) player.getPosX();
		float py = (float) player.getPosY() + 0.56F;
		float pz = (float) player.getPosZ();
		if (player.isSneaking()) {
		    float dist = getBack(player, 0.55F);
		    float backX = px + dx * dist;
		    float backZ = pz + dz * dist;
		    float dy = 1.4F;
		    float len = MathHelper.sqrt(1 + dy * dy);
		    dx /= len;
		    dy /= len;
		    dz /= len;
		    float rz = (point.posX - (float) player.getPosX()) * dx + (point.posZ - (float) player.getPosZ()) * dz;
		    float a = (MathHelper.clamp(-rz, -0.5F, -0.4F) + 0.5F) / 0.1F;
		    collideWithPlane(point, backX, py, backZ, dx, dy, dz, a);
		} else {
		    float rx = (point.posX - (float) player.getPosX()) * MathHelper.cos(yaw + (float) Math.PI / 2)
			    + (point.posZ - (float) player.getPosZ()) * MathHelper.sin(yaw + (float) Math.PI / 2);
		    float a = 1 - (MathHelper.clamp(Math.abs(rx), 0.24F, 0.36F) - 0.24F) / (0.36F - 0.24F);
		    float dist = getBack(player, 0.14F);
		    collideWithPlane(point, px + dx * dist, py, pz + dz * dist, dx, 0, dz, a);
		}
	    }

	    private static float getDistToPlane(Point point, float px, float py, float pz, float nx, float ny, float nz) {
		return nx * (point.posX - px) + ny * (point.posY - py) + nz * (point.posZ - pz);
	    }

	    private static void collideWithPlane(Point point, float px, float py, float pz, float nx, float ny, float nz, float amount) {
		float d = getDistToPlane(point, px, py, pz, nx, ny, nz);
		if (d < 0) {
		    point.posX -= nx * d * amount;
		    point.posY -= ny * d * amount;
		    point.posZ -= nz * d * amount;
		}
	    }
	}
    }
}
