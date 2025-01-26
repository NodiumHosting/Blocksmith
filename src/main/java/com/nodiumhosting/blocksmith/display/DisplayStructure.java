package com.nodiumhosting.blocksmith.display;

import lombok.Getter;
import net.minestom.server.coordinate.Point;
import net.minestom.server.entity.Entity;

import java.util.List;

public class DisplayStructure {
    @Getter
    private final List<DisplayStructurePiece> pieces;

    public DisplayStructure(List<DisplayStructurePiece> pieces) {
        this.pieces = pieces;
    }

    public DisplayStructure toRealPosition(Point location) {
        List<DisplayStructurePiece> newPieces = this.pieces.stream().map(piece -> new DisplayStructurePiece(piece.getOffset().add(location), piece.getEntity())).toList();
        return new DisplayStructure(newPieces);
    }

    public static class DisplayStructurePiece {
        @Getter
        private final Point offset;
        @Getter
        private final Entity entity;

        public DisplayStructurePiece(Point offset, Entity entity) {
            this.offset = offset;
            this.entity = entity;
        }
    }
}
