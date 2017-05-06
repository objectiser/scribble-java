package tutorial.adder;

import static tutorial.adder.Adder.Adder.Adder.Add;
import static tutorial.adder.Adder.Adder.Adder.Bye;
import static tutorial.adder.Adder.Adder.Adder.C;
import static tutorial.adder.Adder.Adder.Adder.Res;
import static tutorial.adder.Adder.Adder.Adder.S;

import org.scribble.net.Buf;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.session.MPSTEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import tutorial.adder.Adder.Adder.Adder;
import tutorial.adder.Adder.Adder.channels.C.Adder_C_1;
import tutorial.adder.Adder.Adder.channels.C.Adder_C_2;
import tutorial.adder.Adder.Adder.roles.C;

public class AdderC {

	public static void main(String[] args) throws Exception {
		Adder adder = new Adder();
		try (MPSTEndpoint<Adder, C> client =
					new MPSTEndpoint<>(adder, C, new ObjectStreamFormatter())) {	
			client.connect(S, SocketChannelEndpoint::new, "localhost", 8888);
			int n = 10;
			System.out.println(n + "th fib number: "
					+ run(new Adder_C_1(client), n));
		}
	}
	
	private static int run(Adder_C_1 c1, int n) throws Exception {
		Buf<Integer> x = new Buf<>(0);
		Buf<Integer> y = new Buf<>(1);
		for (int i = 1; i < n; i++) {
			Adder_C_2 c2 = c1.send(S, Add, x.val, y.val);
			x.val = y.val;
			c1 = c2.receive(S, Res, y);
		}
		c1.send(S, Bye);
		return x.val;
	}
}
