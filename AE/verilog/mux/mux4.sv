module mux4(output logic z, input logic [3:0] x, input logic [0:1] c);

	assign z = (
		c[0] == 0 ? (
			c[1] == 0 ?
				x[0] :
				x[1]
			) : (
			c[1] == 0 ?
				x[2] :
				x[3]
			)
		);

endmodule
