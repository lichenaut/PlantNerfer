# PlantNerfer

PlantNerfer allows for very configurable plant behavior.

[![Github All Releases](https://img.shields.io/github/downloads/lichenaut/PlantNerfer/total.svg)]()

## Command | Permission | Description

> /pn | plantnerfer.command | Use PlantNerfer commands. <br>
> /pn help | plantnerfer.command.help | Links to PlantNerfer documentation. <br>
> /pn reload | plantnerfer.command.reload | Reloads the plugin.

## Plant Configuration

### Example

#### Biome Group Section

> biome-group-list: <br>
> &nbsp;&nbsp;TestGroup: <br>
> &nbsp;&nbsp;&nbsp;&nbsp;- "PLAINS" <br>
> &nbsp;&nbsp;&nbsp;&nbsp;- "SAVANNA" <br>
> &nbsp;&nbsp;second-group: <br>
> &nbsp;&nbsp;&nbsp;&nbsp;- "FOREST" <br>

Two biome groups are created, "TestGroup" and "second-group". "TestGroup" contains the biomes "PLAINS" and "SAVANNA", and "second-group" contains the biome "FOREST". [Click here for the list of biomes.](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/block/Biome.html)

#### Plant Section

> oak-sapling: <br>
> &nbsp;&nbsp;can-place: true <br>
> &nbsp;&nbsp;growth-rate: 25 <br>
> &nbsp;&nbsp;death-rate: 10 <br>
> &nbsp;&nbsp;growth-rate-dark: 15 <br>
> &nbsp;&nbsp;death-rate-dark: 20 <br>
> &nbsp;&nbsp;bone-meal-success-rate: 25 <br>
> &nbsp;&nbsp;bone-meal-success-rate-dark: 10 <br>
> &nbsp;&nbsp;needs-hoe-for-drops: true <br>
> &nbsp;&nbsp;min-light: 9 <br>
> &nbsp;&nbsp;max-light: 15 <br>
> &nbsp;&nbsp;needs-sky: false <br>
> &nbsp;&nbsp;transparent-blocks-count-as-sky: false <br>
> &nbsp;&nbsp;no-sky-growth-rate: 10 <br>
> &nbsp;&nbsp;no-sky-death-rate: 25 <br>
> &nbsp;&nbsp;min-y: 0 <br>
> &nbsp;&nbsp;max-y: 200 <br>
> &nbsp;&nbsp;restrict-to-worlds: <br>
> &nbsp;&nbsp;&nbsp;&nbsp;- "world" <br>
> &nbsp;&nbsp;&nbsp;&nbsp;- "world_nether" <br>
> &nbsp;&nbsp;biome-groups: <br>
> &nbsp;&nbsp;&nbsp;&nbsp;second-group: <br>
> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;growth-rate: 100 <br>
> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;bone-meal-success-rate: 100 <br>
> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;bone-meal-success-rate-dark: 100 <br>
> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;needs-hoe-for-drops: false <br>
> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;restrict-to-worlds: <br>
> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- "world" <br>
> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;max-y: 255 <br>
> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;needs-sky: false <br>
> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;transparent-blocks-count-as-sky: true <br>
> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;no-sky-growth-rate: 100 <br>
> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;no-sky-death-rate: 0 <br>
> &nbsp;&nbsp;&nbsp;&nbsp;TestGroup: <br>
> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;can-place: false <br>
> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;restrict-to-worlds: <br>
> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- "world_nether" <br>

Oak saplings have poor mechanics normally, but in the biome group "second-group" they have an overall growth rate of 100. In the biome group "TestGroup", they can't be placed, ruling out its biomes.

### Can Place

> can-place: true/false

Can the plant be placed?

### Growth Rate

> growth-rate: #

A value of 100 is the vanilla growth rate. A value of 50 would make each progression to the next maturation stage a coin flip. This plugin cannot make plants grow faster.

### Growth Rate Dark

> growth-rate-dark: #

Similar to growth-rate, but applies to plants in light levels under 8.

### Death Rate

> death-rate: #

A value of 100 would make the plant disappear whenever it tries to progress to the next maturation stage. A value of 5 would make the plant disappear 1 in 20 growth attempts.

### Death Rate Dark

> death-rate-dark: #

Similar to death-rate, but applies to plants in light levels under 8.

### Bone Meal Success Rate

> bone-meal-success-rate: #

A value of 100 would make the plant try to grow whenever bone meal is used on it. A value of 50 would make the plant try to grow half the time.

### Bone Meal Success Rate Dark

> bone-meal-success-rate-dark: #

Similar to bone-meal-success-rate, but applies to plants in light levels under 8.

### Hoe-Reliant Drops

> needs-hoe-for-drops: true/false

If true, the plant will only drop its items when broken with a hoe.

### Hoe-Reliant Farmland Retaining

> needs-hoe-for-farmland-retain: true/false

If true, the Farmland the Farmland-related plant is broken on will turn into Dirt if not broken with a hoe.

### Minimum Light

> min-light: #

The minimum light level allowed for plant placement, plant growth, and plant bone meal-ing.

### Maximum Light

> max-light: #

The maximum light level allowed for plant placement, plant growth, and plant bone meal-ing.

### Needs Sky

> needs-sky: true/false

If true, the plant can only be placed, grown, and bone meal-ed if there are no blocks above it.

### Transparent Blocks Count as Sky

> transparent-blocks-count-as-sky: true/false

If true, blocks that let light pass through them will not be checked when a needs-sky plant checks for blocks above it.

### No Sky Growth Rate

> no-sky-growth-rate: #

Similar to growth-rate, but applies to plants with no sky above them. Does not replace any other growth rate, instead adds another chance for the plant to not grow.

### No Sky Death Rate

> no-sky-death-rate: #

Similar to death-rate, but applies to plants with no sky above them. Does not replace any other death rate, instead adds another chance for the plant to die.

### Minimum Y

> min-y: #

The minimum y-level allowed for plant placement.

### Maximum Y

> max-y: #

The maximum y-level allowed for plant placement.

### Restrict to Worlds

> restrict-to-worlds: <br>
> &nbsp;&nbsp;- "example_world_name" <br>
> &nbsp;&nbsp;- "example_world_name_2"

Restrict plant placement to certain worlds.

### Biome Groups

> See example under the "Plant Section" header

Any settings for a plant can also be defined in this section for per-group configuration, except for this setting.

## Notes

Note 0: PlantNerfer exists on top of vanilla mechanics; it does not replace them.

Note 1: 'dark' light levels are between and including 0 and 7.

Note 2: different plants have different numbers of growth stages, which means the same rate can affect some plants more than others.

Note 3: for a successful bone meal fertilization growth, the plant must go through bone meal rate, growth rate, and vanilla bone meal chance checks.

Note 4: These are the defaults, for any values not defined by the user:

> can-place: true <br>
> growth-rate: 100 <br>
> growth-rate-dark: 100 <br>
> death-rate: 0 <br>
> death-rate-dark: 0 <br>
> bone-meal-success-rate: 100 <br>
> bone-meal-success-rate-dark: 100 <br>
> needs-hoe-for-drops: false <br>
> needs-hoe-for-farmland-retain: false <br>
> min-light: 0 <br>
> max-light: 15 <br>
> needs-sky: false <br>
> transparent-blocks-count-as-sky: true <br>
> no-sky-growth-rate: 100 <br>
> no-sky-death-rate: 0 <br>
> min-y: -64 <br>
> max-y: 256 <br>
> restrict-to-worlds: (empty, so any) <br>
> biome-groups: (empty) <br>

Plants with completely empty config information will use vanilla mechanics.

Biome group settings will inherit default values from the plant settings, unless they are defined in the biome group settings.

If a biome is in multiple biome groups applied to a plant, only the last group's biome will be used for the plant.

The "*" character in plugin messages means "all", or "any".
