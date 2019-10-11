module test_abba();

  reg [1:2] x;
  reg       clock;

  wire z;

  abba test(z, x, clock);
  initial clock = 0;

  always begin
    #3 clock = 1;
    #1 clock = 0;
  end

  initial begin
    $dumpfile("abba.vcd");
    $dumpvars;

    x = 2'b00;

    #4
    x = 2'b01;

    #8
    x = 2'b01;

    #1
    x = 2'b00;

    #10
    $finish;

  end
endmodule // test_abba
