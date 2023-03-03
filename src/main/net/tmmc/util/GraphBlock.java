package net.tmmc.util;

import mindustry.gen.Building;
import mindustry.world.Block;

public class GraphBlock extends Block {
    public boolean hasRange = false;
    public float range = 0;

    public GraphBlock(String name) {
        super(name);

        this.destructible = true;
        this.solid = true;
    }

    public class GraphBlockBuild extends Building implements Ranged {
        public Building nearby() {
            return this.nearby(this.rotation);
        }

        public Building antiNearby() {
            return switch(this.rotation) {
                case 0 -> this.nearby(2);
                case 1 -> this.nearby(3);
                case 2 -> this.nearby(0);
                case 3 -> this.nearby(1);
                //unreached
                default -> null;
            };
        }

        public boolean isNearby(Block block) {
            return isTheSameBlock(this.nearby(), block);
        }

        public boolean isAntiNearby(Block block) {
            return isTheSameBlock(this.antiNearby(), block);
        }

        public boolean isTheSameBlock(Building building, Block block) {
            return XBlocks.isTheSameBlock(building, block);
        }

        @Override
        public void drawSelect() {
            super.drawSelect();

            if(this.enabledRange()) {
                this.drawRange();
            }
        }

        @Override
        public boolean enabledRange() {
            return hasRange;
        }

        @Override
        public float range() {
            return range;
        }
    }
}