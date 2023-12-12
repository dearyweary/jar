/*
 *     THIS FILE AND PROJECT IS SUPPLIED FOR EDUCATIONAL PURPOSES ONLY.
 *
 *     This program is free software; you can redistribute it
 *     and/or modify it under the terms of the GNU General
 *     Public License as published by the Free Software
 *     Foundation; either version 2 of the License, or (at your
 *     option) any later version.
 *
 *     This program is distributed in the hope that it will be
 *     useful, but WITHOUT ANY WARRANTY; without even the
 *     implied warranty of MERCHANTABILITY or FITNESS FOR A
 *     PARTICULAR PURPOSE. See the GNU General Public License
 *     for more details.
 *
 *     You should have received a copy of the GNU General
 *     Public License along with this program; if not, write to
 *     the Free Software Foundation, Inc., 59 Temple Place,
 */
package ballistickemu.Types;
import org.apache.mina.core.session.IoSession;
import ballistickemu.Main;
import ballistickemu.Tools.StickPacketMaker;
import java.util.LinkedHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import ballistickemu.Tools.DatabaseTools;
import ballistickemu.Tools.QuickplayTool;
import java.sql.PreparedStatement;
/**
 *
 * @author Simon
 */
public class StickClient {
    public static final String CLIENT_KEY = "CLIENT";

    private IoSession session;
    private String UID;
    private int kills;
    private int deaths;
    private int wins;
    private int losses;
    private int rounds;
    private int passExpiry;
    private int ticket;
    private int cash;
    private int game_wins;
    private int game_kills;
    private int game_deaths;
    private int user_level;
    private int dbID;
    private String name;
    private Boolean IsAtLobby;
    private Boolean IsReal;
    private Boolean HasPass;
    private StickColour colour;
    private StickRoom Room;
    private Boolean IsMod;
    private Boolean IsMuted;
    private Boolean IsBlended;
    private Boolean IsQuickplayChar;
    private Boolean IsRequiresUpdate;
    private LinkedHashMap<Integer, StickItem> Inventory;
    public ReentrantReadWriteLock InventoryLock = new ReentrantReadWriteLock(true);


    /**
     *
     * @param _session
     * @param new_UID
     */
    public StickClient(IoSession _session, String new_UID)
        {
            this.session = _session;
            this.UID = new_UID;
            this.IsAtLobby = true;
            this.IsReal = false;
            this.IsMod = false;
            this.IsBlended = false;
            this.IsMuted = false;
            this.IsQuickplayChar = false;
            this.IsRequiresUpdate = true;
            this.game_wins = 0;
            this.game_deaths = 0;
            this.game_kills = 0;
            this.colour = new StickColour(0, 0, 0, 0, 0, 0);
            this.Inventory = new LinkedHashMap<Integer, StickItem>();
            this.dbID = -1;
        }

    public StickClient()
    {
    }




        //SET
    /**
     *
     * @param _UID
     */
    public void setUID(String _UID)
        {
            this.UID = _UID;
        }

    public void setDbID(int newID)
        {
            this.dbID = newID;
        }

        public void setUserLevel(int _user_level)
        {
            this.user_level = _user_level;
        }
        public void setRequiresUpdate(Boolean FT)
        {
            this.IsRequiresUpdate = FT;
        }

    /**
     *
     * @param _name
     */
    public void setName(String _name)
        {
            this.name = _name;
        }

    public void setDeaths(int _deaths)
        {
            this.deaths = _deaths;
        }

    public void setWins(int _wins)
        {
            this.wins = _wins;
        }

    public void setLosses(int _losses)
        {
            this.losses = _losses;
        }

    public void setPassExpiry(int newPE)
        {
            this.passExpiry = newPE;
        }

    public void setRounds(int _rounds)
        {
            this.rounds = _rounds;
        }

    public void setTicket(int _ticket)
        {
            this.ticket = _ticket;
        }

    public void setCash(int _cash)
        {
            this.cash = _cash;
        }

    public void setPass(Boolean pass)
        {
            this.HasPass = pass;
        }



    public void setModStatus(Boolean Mod)
        {
            this.IsMod = Mod;
        }
    public void setBlendedStatus(Boolean Blended)
        {
            this.IsBlended = Blended;
        }
        /**
         *
         * @param _colour
         */
        public void setColour1(String _colour1)
        {
            this.colour.setColour1FromString(_colour1);
        }

        public void setColour2(String _colour2)
        {
            this.colour.setColour2FromString(_colour2);
        }

        /**
         *
         * @param _kills
         */
        public void setKills(int _kills)
        {
            this.kills = _kills;
        }

        /**
         *
         * @param _gamekills
         */
        public void setGameKills(int _gamekills)
        {
            this.game_kills = _gamekills;
        }

        /**
         *
         * @param _gamedeaths
         */
        public void setGameDeaths(int _gamedeaths)
        {
            this.game_deaths = _gamedeaths;
        }

        /**
         *
         * @param AtLobby
         */
        public void setLobbyStatus(Boolean AtLobby)
        {
            this.IsAtLobby = AtLobby;
        }


        public void setGameWins(int GameWins)
        {
            this.game_wins = GameWins % 9;
        }
        
        public void incrementGameWins()
        {
            this.game_wins = (game_wins + 1) % 9;
            if(this.game_wins > 9)
                this.game_wins = this.game_wins - 10;
        }


        public void setIsReal(Boolean Real)
        {
            this.IsReal = Real;
        }

        public void setMuteStatus(Boolean Mute)
        {
            this.IsMuted = Mute;
        }


        public void setRoom(StickRoom room)
        {
            this.Room = room;
        }

        public void setQuickplayStatus(Boolean IsQP)
        {
            this.IsQuickplayChar = IsQP;
        }
        //GET
        /**
         *
         * @return
         */
        public String getUID()
        {
            return this.UID;
        }

        /**
         *
         * @return
         */
        public String getName()
        {
            return this.name;
        }

        /**
         *
         * @return
         */
        public String getColour()
        {
            return this.colour.getColour1AsString();
        }

        public String getColour2()
        {
            return this.colour.getColour2AsString();
        }
        /**
         *
         * @return
         */
        public int getKills()
        {
            return this.kills;
        }

        /**
         *
         * @return
         */
        public int getGameKills()
        {
            return this.game_kills;
        }

        /**
         *
         * @return
         */
        public int getGameDeaths()
        {
            return this.game_deaths;
        }

        /**
         *
         * @return
         */
        public Boolean getLobbyStatus()
        {
            return this.IsAtLobby;
        }

        public Boolean getRequiresUpdate()
        {
            return this.IsRequiresUpdate;
        }

        /**
         *
         * @return
         */
        public IoSession getIoSession()
        {
            return this.session;
        }

        public Boolean getReal()
        {
            return this.IsReal;
        }

       public Boolean getModStatus()
        {
            return this.IsMod;
        }
       public Boolean isBlended()
        {
            return this.IsMod;
        }

      public Boolean getPass()
        {
            return this.HasPass;
        }

        public Boolean getMuteStatus()
        {
            return this.IsMuted;
        }

        public Boolean getQuickplayStatus()
        {
            return this.IsQuickplayChar;
        }


        public StickRoom getRoom()
        {
            return this.Room;
        }

    public int getDeaths()
        {
            return this.deaths;
        }

    public int getWins()
        {
            return this.wins;
        }

    public int getLosses()
        {
            return this.losses;
        }

    public int getPassExpiry()
        {
            return this.passExpiry;
        }

    public int getRounds()
        {
            return this.rounds;
        }
    public int getGameWins()
        {
            return this.game_wins;
        }

    public int getTicket()
        {
            return this.ticket;
        }

    public int getCash()
        {
            return this.cash;
        }

    public int getUserLevel()
        {
            if(this.IsMod && this.IsBlended)
                return 0;
            return this.user_level;
        }

    public int getDbID()
        {
            return this.dbID;
        }

    public LinkedHashMap<Integer, StickItem> getInventory()
        {
            return this.Inventory;
        }



        
        public void write(StickPacket Packet)
        {
            if (Packet.getString().length() <1)
                return;
            try
            {
                 this.session.write(Packet.getString().substring(0, Packet.getString().length() -1));
            }
            catch(Exception e)
            {
                e.printStackTrace();
                if(this.getLobbyStatus())
                {
                    Main.getLobbyServer().getClientRegistry().deregisterClient(this);
                }
                else if(this.Room != null)
                {
                    this.Room.GetCR().deregisterClient(this);
                }
            }
            
        }

        public void writePolicyFile()
        {
            if(this.session.isConnected())
                this.session.write("<cross-domain-policy><allow-access-from domain=\"" + Main.IP + "\" to-ports=\"3724,47624,1138,1139,443,110,80\" /></cross-domain-policy>");
        }

        public void writeMessage(String Message)
        {
            this.write(StickPacketMaker.getMessagePacket(Message, this.UID));
        }
        
        public void writeCallbackMessage(String Message)
        {
            this.write(StickPacketMaker.getCallbackPacket(Message));
        }


        @Override
        public void finalize() throws Throwable {
          super.finalize();
    }

    public void addItemToInventory(int ItemDBID, StickItem item)
    {
        InventoryLock.writeLock().lock();
        try {
            this.Inventory.put(ItemDBID, item);
        } finally {
            InventoryLock.writeLock().unlock();
        }
    }

    public StickItem getItemByID(int ItemDBID)
    {
        InventoryLock.readLock().lock();
        try {
            if(!this.Inventory.containsKey(ItemDBID))
                return null;
            else
                return this.Inventory.get(ItemDBID);
        } finally {
            InventoryLock.readLock().unlock();
        }
    }

    public void setSelectedItem(int iType, int ItemID)
    {
        this.InventoryLock.readLock().lock();
        try
        {
            for (StickItem I : this.Inventory.values())
            {
                if(I.getitemType() == iType)
                    I.setSelected(false);
                    
                if(I.getItemDBID() == ItemID)
                    I.setSelected(true);
                    
            }
        } finally {
        this.InventoryLock.readLock().unlock();
        }
    }

    public StickItem getFirstSpinner()
    {
        this.InventoryLock.readLock().lock();
        try
        {
            for (StickItem I : this.Inventory.values())
            {
                if(I.getitemType() == 1)
                            return I;
            }
        } finally {
        this.InventoryLock.readLock().unlock();
        }
        return null;
    }

    public StickItem getFirstPet()
    {
        this.InventoryLock.readLock().lock();
        try
        {
            for (StickItem I : this.Inventory.values())
            {
                if(I.getitemType() == 2)
                            return I;
            }
        } finally {
        this.InventoryLock.readLock().unlock();
        }
        return null;
    }

    public StickItem getSelectedSpinner()
    {
        this.InventoryLock.readLock().lock();
        try
        {
            for (StickItem I : this.Inventory.values())
            {
                if((I.getitemType() == 1) && (I.isSelected()))
                            return I;                    
            }
        } finally {
        this.InventoryLock.readLock().unlock();
        }
        StickItem first = this.getFirstSpinner();
        first.setSelected(true);
        setSelectedInDB(first);
        return first;
    }

    public StickItem getSelectedPet()
    {
        this.InventoryLock.readLock().lock();
        try
        {
            for (StickItem I : this.Inventory.values())
            {
                if((I.getitemType() == 2) && (I.isSelected()))
                            return I;
            }
        } finally {
        this.InventoryLock.readLock().unlock();
        }
        StickItem first = this.getFirstPet();
        first.setSelected(true);
        setSelectedInDB(first);
        return first;
    }

    public String getFormattedInventoryData()
    {
        StringBuilder SB = new StringBuilder();
        this.InventoryLock.readLock().lock();
        int i = 0;
        try
        {
            for (StickItem I : this.Inventory.values())
            {
                if(I.isSelected())
                    i = 1;
                SB.append(I.getItemID());
                SB.append(i);
                SB.append(I.getColour().getColour1AsString());
                SB.append(I.getColour().getColour2AsString());
                SB.append(I.getItemDBID());
                SB.append(";");
                i = 0;
              //  SB.append("\0");
            }
            
        } finally {
            this.InventoryLock.readLock().unlock();
        }
        return SB.toString();
    }

    public StickColour getStickColour()
    {
        return this.colour;
    }

    private void setSelectedInDB(StickItem toChange)
    {
        try
        {
                if(toChange != null)
                {
                    PreparedStatement ps = DatabaseTools.getDbConnection().prepareStatement("UPDATE `inventory` SET `selected` = 0 WHERE `itemtype` = ? AND `userid` = ?");
                    ps.setInt(1, toChange.getitemType());
                    ps.setInt(2, this.dbID);
                    ps.executeUpdate();
                    ps = DatabaseTools.getDbConnection().prepareStatement("UPDATE `inventory` SET `selected` = 1 WHERE `id` = ? AND `userid` = ?");
                    ps.setInt(1, toChange.getItemDBID());
                    ps.setInt(2, this.dbID);
                    ps.executeUpdate();
                }
        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setUpAsQuickplay()
    {
        this.IsQuickplayChar = true;
        this.HasPass = false;
        this.name = QuickplayTool.getRandomName();
        this.Inventory = QuickplayTool.getRandomInventory();
    }

}
