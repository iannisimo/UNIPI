module test_adder();

   //logic
   reg [3:0] my_a;
   reg [3:0] my_b;
   // logic
   reg [3:0] my_z;
   wire my_of;


   adder4 add(my_z, my_of, my_a, my_b);

   initial
     begin
	$dumpfile("provaadd.vcd");
	$dumpvars;

	my_a = 4'b0000;
	my_b = 4'b0000;

	#10
	  my_a = 4'b1000;
    my_b = 4'b1000;


	#10
	  $finish;

     end



endmodule // test_add
