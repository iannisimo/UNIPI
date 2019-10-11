module abba (output z, input [1:2]x, input clock);

  wire [1:2]out_reg;
  wire [1:2]in_reg;

  register r(out_reg, clock, 1'b1, in_reg);
  sigma sm(in_reg, out_reg, x);
  omega om(z, out_reg, x);

endmodule // abba

module omega (output z, input [1:2]s, input [1:2]x);

  assign
    #1 z = s[1] & ~s[2] & ~x[1] & ~x[2];

endmodule // omega

module sigma (output [1:2]ns, input [1:2]s, input [1:2]x);

  assign
    #1 ns[1] = s[2] & ~x[1] & x[2];
  assign
    #2 ns[2] = ~s[1] &  s[2] & ~x[1]
          | ~s[1] & ~x[2]
          |  s[2] & ~x[2];

endmodule // sigma

module register (output reg [1:N]state, input clock, input enable, input [1:N]val);

  parameter N = 2;
  reg [1:N]tmp_state;

  // Viene eseguito quando il modulo viene istanziato
  initial begin
    tmp_state = 0;
  end

  // Viene eseguito ad ogni posedge del clock
  always @ (posedge clock) begin
    if (enable)
      tmp_state <= val; // '<=': Assegnamento asincrono (Piu' assegnamenti asincroni consecutivi vengono eseguiti contemporaneamente)
  end

  assign
    #1 state = tmp_state; // FIXME: reg state; cannot be driven by primitives or continuous assignment.



endmodule // register
