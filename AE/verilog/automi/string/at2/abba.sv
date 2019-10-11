module m1(output z, input [1:2]x, input clock);

  parameter S0 = 2'b00;
  parameter S1 = 2'b01;
  parameter S2 = 2'b11;
  parameter S3 = 2'b10;
  parameter A  = 2'b00;
  parameter B  = 2'b01;
  parameter C  = 2'b11;

  reg [1:2] state;
  reg [1:2] n_state;

  initial begin
    state = S0;
  end

  always @ (posedge clock) begin
    state <= n_state;
  end

  always @ ( * ) begin
    case (state)
      S0: n_state = (x == A ? S1 : S0);
      S1: n_state = (x == B ? S2 : (x == A ? S1 : S0));
      S2: n_state = (x == B ? S3 : (x == A ? S1 : S0));
      // S3: n_state = S0;
    endcase
  end

  assign z = ((state == S3 && x == A) ? 1 : 0);

endmodule
