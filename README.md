
MIRED
=======

WIP Readme.

# Dependencies
- NeoForge for Minecraft 1.21.1
- Create (6.0.10)
- Create Aeronautics (1.2.2) (maybe not needed, but needed for the future)

# Additions
## Brass Encased Redstone
Like redstone but as a block, i.e. vertical.

## Analog Computator
Select computation mode (add, subtract, multiply, divide) and `output = side-input <> back-input` where `<>` is the 
chosen computation mode.

## Analog SR Latch
Vaguely similar to create's Redstone Latch; when powered from either side it will store the analog signal provided from
behind, it comes in two modes:
- (default) continuous: as long as a signal is provided to either side, the stored value updates with its input
- on rising edge: only updates the input provided from the back once -- to store a new value, discontinue power to the 
  side and power it again.

## Analog Inverter (Model WIP)
Inverts the analog signal provied from behind, that is: `output = 15 - input`.

# about
Made by Mirandnyan.

No AI used to make this mod.
