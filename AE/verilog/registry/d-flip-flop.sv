primitive sr-latch(output q, input s, input r);

  table
    0 0 : q;
    0 1 : 0;
    1 0 : 1;

  endtable

endprimitive
