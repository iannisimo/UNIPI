module register(output [1:N]z, input [1:N]x, input clock, input enable);
  parameter N = 2;

  reg [1:N]state;

  initial begin
    state = 0;
  end

  always @ (posedge clock) begin
    if(enable)
      state <= x;
  end

  assign z = state;

endmodule
