module adder4(output logic [3:0] z, output oflow, input logic [3:0] a, input logic [3:0] b);

  wire of0;
  wire of1;
  wire of2;

  adder a0(z[0], of0, a[0], b[0], 1'b0);
  adder a1(z[1], of1, a[1], b[1], of0);
  adder a2(z[2], of2, a[2], b[2], of1);
  adder a3(z[3], oflow, a[3], b[3], of2);

endmodule

module adder(output z, output o, input a, input b, input r);

  assign z = (a == 0 ? (b == 0 ? r : !r) : (b == 0 ? !r : r));
  assign o = (a == 0 ? (b == 0 ? 0 : r) : (b == 0 ? r : !r));

endmodule
