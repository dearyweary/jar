/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ballistickemu.Lobby.handlers;
import ballistickemu.Tools.DatabaseTools;
import ballistickemu.Types.StickClient;
import ballistickemu.Types.StickItem;
import ballistickemu.Types.StickColour;
import ballistickemu.Tools.ItemType;
import ballistickemu.Main;
import java.sql.PreparedStatement;
/**
 *
 * @author Simon
 */
public class BuyItemRequestHandler {
        public static void HandlePacket(StickClient client, String packet)
        { //0b999123123123456456456.
            if(packet.length() < 24)
            {
                System.out.println("Invalid buy item packet length received. Packet length: " + packet.length() + ", Packet data: " + packet);
                return;
            }
            int ItemID = Integer.valueOf(packet.substring(2, 5));
            int red1 = Integer.valueOf(packet.substring(5, 8));
            int green1 = Integer.valueOf(packet.substring(8, 11));
            int blue1 = Integer.valueOf(packet.substring(11, 14));
            int red2 = Integer.valueOf(packet.substring(14, 17));
            int green2 = Integer.valueOf(packet.substring(17, 20));
            int blue2 = Integer.valueOf(packet.substring(20, 23));
            int itemDBID = -1;
            int iType = ItemType.getItemType(ItemID);
            StickColour newColour = new StickColour(red1, green1, blue1, red2, green2, blue2);
            int price = Main.getLobbyServer().getShop().getPriceByItemID(ItemID);

            //some price checking stuff now
            if (price == -1) //ie, non-existant item
            {
                System.out.println("WARNING: " + client.getName() + " attempted to buy non-existant item with ID " + ItemID + ".");
                return;
            }
            
            if (price > client.getCash()) //trying to purchase something you can't afford, i see!
            {
                System.out.println("WARNING: " + client.getName() + " attempted to buy item costing more than held cash.");
                client.getIoSession().close(true);
                return;
            }


            try
            {
                PreparedStatement ps = DatabaseTools.getDbConnection().prepareStatement("INSERT INTO `inventory` (`userid`, `itemid`, `itemtype`, `red1`, `green1`, `blue1`, `red2`, `green2`, `blue2`) VALUES" +
                    " (?, ?, ?, ?, ?, ?, ?, ?, ?)");
                ps.setInt(1, client.getDbID());
                ps.setInt(2, ItemID);
                ps.setInt(3, iType);
                ps.setInt(4, red1);
                ps.setInt(5, green1);
                ps.setInt(6, blue1);
                ps.setInt(7, red2);
                ps.setInt(8, green2);
                ps.setInt(9, blue2);

                itemDBID = DatabaseTools.executeQuery(ps);

                PreparedStatement ps2 = DatabaseTools.getDbConnection().prepareStatement("UPDATE `users` SET `cash` = `cash` - ? WHERE `username` = ?");
                ps2.setInt(1, price);
                ps2.setString(2, client.getName());
                ps2.executeUpdate();

            } catch (Exception e)
            {
                System.out.println("There was an error updating the database with a new item.");
                e.printStackTrace();
            }
            //int _ItemID, int _dbID, int _userDBID, int itemType, Boolean _selected, StickColour _colour
            if(itemDBID != -1)
            {
                client.addItemToInventory(itemDBID, new StickItem(ItemID, itemDBID, client.getDbID(), iType, false, newColour));
                client.setCash(client.getCash() - price);
            }
            else
            {
                System.out.println("There was an error in updating the database with a new item - item DBID was returned as -1.");
            }
        }
}
