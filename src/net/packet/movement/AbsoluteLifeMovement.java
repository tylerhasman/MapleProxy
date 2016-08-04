/*
	This file is part of the OdinMS Maple Story Server
    Copyright (C) 2008 Patrick Huy <patrick.huy@frz.cc>
		       Matthias Butz <matze@odinms.de>
		       Jan Christian Meyer <vimes@odinms.de>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation version 3 as published by
    the Free Software Foundation. You may not use, modify or distribute
    this program under any other version of the GNU Affero General Public
    License.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package net.packet.movement;

import io.netty.buffer.ByteBuf;

import java.awt.Point;

public class AbsoluteLifeMovement extends AbstractLifeMovement {
    private Point pixelsPerSecond = new Point(5, 5);
    private int unk;

    public AbsoluteLifeMovement(byte type, Point position, int duration, byte newstate) {
        super(type, position, duration, newstate);
    }

    public Point getPixelsPerSecond() {
        return pixelsPerSecond;
    }

    public void setPixelsPerSecond(Point wobble) {
        this.pixelsPerSecond = wobble;
    }

    public int getUnk() {
        return unk;
    }

    public void setUnk(int unk) {
        this.unk = unk;
    }

    @Override
    public void serialize(ByteBuf buf) {
        buf.writeByte(getType());
        buf.writeShort(getPosition().x);
        buf.writeShort(getPosition().y);
        buf.writeShort(pixelsPerSecond.x);
        buf.writeShort(pixelsPerSecond.y);
        buf.writeShort(unk);
        buf.writeByte(getNewstate());
        buf.writeShort(getDuration());
    }

}
