# This example is a simple nested loop. It has been devised to show
# benefits of the strategy of narrowing with restart.

nested = function() {
   i = 0
   while (i < 10) {
     j = 0
     while (j < 10)
       j = j+1
     i = i+1
   }
}
