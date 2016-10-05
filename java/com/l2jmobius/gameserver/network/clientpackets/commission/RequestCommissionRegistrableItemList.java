/*
 * This file is part of the L2J Mobius project.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2jmobius.gameserver.network.clientpackets.commission;

import java.util.ArrayList;

import com.l2jmobius.gameserver.instancemanager.CommissionManager;
import com.l2jmobius.gameserver.model.actor.instance.L2PcInstance;
import com.l2jmobius.gameserver.model.items.instance.L2ItemInstance;
import com.l2jmobius.gameserver.network.clientpackets.L2GameClientPacket;
import com.l2jmobius.gameserver.network.serverpackets.commission.ExCloseCommission;
import com.l2jmobius.gameserver.network.serverpackets.commission.ExResponseCommissionItemList;

/**
 * @author NosBit
 */
public class RequestCommissionRegistrableItemList extends L2GameClientPacket
{
	@Override
	protected void readImpl()
	{
	}
	
	@Override
	protected void runImpl()
	{
		final L2PcInstance player = getActiveChar();
		if (player == null)
		{
			return;
		}
		
		if (!CommissionManager.isPlayerAllowedToInteract(player))
		{
			player.sendPacket(ExCloseCommission.STATIC_PACKET);
			return;
		}
		
		final ArrayList<L2ItemInstance> auctionableItemList = new ArrayList<>();
		for (L2ItemInstance item : player.getInventory().getAvailableItems(false, false, false))
		{
			if (item.getItem().isAuctionable())
			{
				auctionableItemList.add(item);
			}
		}
		
		player.sendPacket(new ExResponseCommissionItemList(auctionableItemList));
	}
	
	@Override
	public String getType()
	{
		return getClass().getSimpleName();
	}
	
}
