Life Sim

Right now, I'm just working on the simulator side.

It's conway's game of life on a more massive scale, with a few twists.

Simulation Strategy #1:

1.  Game engine calls 'tick' on copy of gameboard to move the aging process one quantum for all objects on the gameboard
2.  Game engine determins if anything needs to be adjusted within the copy of the simulation and perhaps repeats #1 a few times
3.  Game engine calls 'tick' on actual gameboard 
----end of natural aging process----
4.  Game engine updates board with player actions / AI bots, microbial movement, etc
5.  Microbe behavior is determined by properties in its DNA


Aging Process

1. Microbes age and do other things, sharing or losing DNA, health, etc to other microbes
2. Proteins within the microbial DNA change and mutate, can die or split, are shared along bloodlines
3. Conway's game of life on a much more complex scale for microbial lifecycle, with deep DNA behaviors


V1, I plan to have all simulator events output as text as if the microbes/DNA were warriors on a battlefield.

V1.1 Graphics

V1.2 Sandbox - user can paint dna at dna level, paint pre-made microbes at microbe level  
