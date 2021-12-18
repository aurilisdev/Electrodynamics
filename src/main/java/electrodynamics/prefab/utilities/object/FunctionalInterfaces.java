package electrodynamics.prefab.utilities.object;

public class FunctionalInterfaces {
	@FunctionalInterface
	public interface SingleFunction<R, P1> {
		R apply(P1 p1);
	}

	@FunctionalInterface
	public interface DoubleFunction<R, P1, P2> {
		R apply(P1 p1, P2 p2);
	}

	@FunctionalInterface
	public interface TripleFunction<R, P1, P2, P3> {
		R apply(P1 p1, P2 p2, P3 p3);
	}

	@FunctionalInterface
	public interface QuadFunction<R, P1, P2, P3, P4> {
		R apply(P1 p1, P2 p2, P3 p3, P4 p4);
	}

	@FunctionalInterface
	public interface PentaFunction<R, P1, P2, P3, P4, P5> {
		R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5);
	}

	@FunctionalInterface
	public interface HexaFunction<R, P1, P2, P3, P4, P5, P6> {
		R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6);
	}

	@FunctionalInterface
	public interface HeptaFunction<R, P1, P2, P3, P4, P5, P6, P7> {
		R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7);
	}

	@FunctionalInterface
	public interface OctoFunction<R, P1, P2, P3, P4, P5, P6, P7, P8> {
		R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8);
	}

	@FunctionalInterface
	public interface NoneFunction<R, P1, P2, P3, P4, P5, P6, P7, P8, P9> {
		R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9);
	}
}
