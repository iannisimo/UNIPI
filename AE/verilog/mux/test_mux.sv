module test_mux();

   //logic 
   reg  my_x, my_y, my_ic;
   // logic 
   wire my_z;

   mux miomux(my_z, my_x, my_y, my_ic);

   initial
     begin
	$dumpfile("provamux.vcd");
	$dumpvars;
	
	my_x = 0;
	my_y = 0;
	my_ic = 1;

	#10
	  my_x = 1;

	#10
	  my_ic = 0;
	
	#10
	  $finish;
	
     end
   
   

endmodule // test_mux
