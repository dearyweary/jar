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
import ballistickemu.Lobby.LobbyServer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import ballistickemu.Main;
import ballistickemu.Tools.DatabaseTools;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.sql.PreparedStatement;


/**
 *
 * @author Simon
 */
public class StickRoom {
        private StickClientRegistry CR;
        private String Name;
        private String MapID;
        private int CycleMode;
        private Boolean isPrivate;
       // private Timer RoomTimer;
        private int RoundTime;
        private int StorageKey;
        private String MapCycleList;
        private Boolean requiresPass;
        private LinkedHashMap<String, StickClient> VIPs;
        public ReentrantReadWriteLock VIPLock;
        private String CreatorName;

        public StickRoom()
        {
            this.CR = new StickClientRegistry(false);
        }

        public StickRoom(String _Name, String _MapID, int CM, Boolean Priv, LinkedHashMap<String, StickClient> VIPs, Boolean needsPass, String _CreatorName)
        {
            this.Name = _Name;
            this.MapID = _MapID;
            this.CycleMode = CM;
            this.isPrivate = Priv;
            this.RoundTime = 300;
            //this.RoomTimer = new Timer();
            this.CR = new StickClientRegistry(false);
            //this.RoomTimer.scheduleAtFixedRate(new OnTimedEvent(), 1000, 1000);
            this.VIPs = VIPs;
            this.requiresPass = needsPass;
            this.VIPLock = new ReentrantReadWriteLock();
            this.CreatorName = _CreatorName;
            Main.getLobbyServer().getRoomRegistry().scheduleRoomTimer(new OnTimedEvent());

        }



        public void BroadcastToRoom(StickPacket packet)
        {
            ArrayList<StickClient> ToDC = new ArrayList<StickClient>();
            this.CR.ClientsLock.readLock().lock();
            try
            {
                for (StickClient c : this.CR.getAllClients())
                {
                    try
                    {
                        if(!c.getLobbyStatus())
                        c.write(packet);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                        ToDC.add(c);
                    }
                }
            } finally {
                this.CR.ClientsLock.readLock().unlock();
            }
            for (StickClient c : ToDC)
            {
                this.CR.deregisterClient(c);
            }
            ToDC.removeAll(ToDC);
        }

        public StickClientRegistry GetCR()
        {
            return CR;
        }

        public void setName(String NewName)
        {
            this.Name = NewName;
        }

        public void setCreatorName(String _CN)
        {
            this.Name = _CN;
        }

        public void setMapID(String NewMapID)
        {
            this.MapID = NewMapID;
        }

        public void setCycleMode(int newCM)
        {
            this.CycleMode = newCM;
        }

        public void setPrivacy(Boolean priv)
        {
            this.isPrivate = priv;
        }

        public void setRoundTime(int time)
        {
            this.RoundTime = time;
        }
        public void setMapCycleList(String MCL)
        {
            this.MapCycleList = MCL;
        }

        public String getName()
        {
            return this.Name;
        }

        public String getCreatorName()
        {
            return this.CreatorName;
        }

        public String getMapID()
        {
            return this.MapID;
        }

        public int getCycleMode()
        {
            return this.CycleMode;
        }

        public Boolean getPrivacy()
        {
            return this.isPrivate;
        }

        public Boolean getNeedsPass()
        {
            return this.requiresPass;
        }

        public int getCurrentRoundTime()
        {
            return this.RoundTime;
        }

        public int getStorageKey()
        {
            return this.StorageKey;
        }

        public String getMapCycleList()
        {
            if (this.MapCycleList != null)
                return this.MapCycleList;
            else return null;
        }
        public void setStorageKey(int key)
        {
            this.StorageKey = key;
        }

        public void setNeedsPass(Boolean NeedsPass)
        {
            this.requiresPass = NeedsPass;
        }

        public void killRoom()
        {
            this.CR.ClientsLock.writeLock().lock();
            try
            {
                for (StickClient SC : this.CR.getAllClients())
                {
                    SC.getIoSession().close(false);
                }
            Main.getLobbyServer().getRoomRegistry().deRegisterRoom(Main.getLobbyServer().getRoomRegistry().GetRoomFromName(Name));
            }
            finally {
                this.CR.ClientsLock.writeLock().unlock();
            }
        }

        public LinkedHashMap<String, StickClient> getVIPs()
        {
            return this.VIPs;
        }

        /*
        public RoomTimer getStickRoomTimer()
        {
            return this.Timer;
        }
        */

         class OnTimedEvent implements Runnable
         {
                public void run ()
                {
                    if(CR.getAllClients().size() == 0)
                    {
                        Main.getLobbyServer().getRoomRegistry().deRegisterRoom(Main.getLobbyServer().getRoomRegistry().GetRoomFromName(Name));

                        try
                        {
                            StickRoom tempRoom = Main.getLobbyServer().getRoomRegistry().GetRoomFromName(Name);
                            if(tempRoom != null)
                                Main.getLobbyServer().getRoomRegistry().GetRoomFromName(Name).finalize();
                            this.finalize();
                        } catch (Throwable t){
                        System.out.println("There was an error deleting room " + Name);
                        t.printStackTrace();
                        }
                        return;
                    }

                    if(RoundTime > 0)
                  RoundTime = (RoundTime - 1);
                    else
                        RoundTime = 300;
                    if(RoundTime == 31)
                    {
                        updateStats(getWinner());
                    }
                    
                }

                private StickClient getWinner()
                {

                    int mostKills = -1;
                    StickClient tempWinner = null;
                    CR.ClientsLock.readLock().lock();

                    try
                    {
                        for (StickClient c : CR.getAllClients())
                        {
                                if(mostKills == -1)
                                {
                                    tempWinner = c;
                                    mostKills = c.getGameKills();
                                    continue;
                                }
                                if ((c != null) && (c.getGameKills() > mostKills))
                                {
                                    tempWinner = c;
                                    mostKills = c.getGameKills();
                                }
                        }
                    } finally {
                        CR.ClientsLock.readLock().unlock();
                    }
                    if(tempWinner == null)
                    {
                        System.out.println("Winner was null - setting winner as blank client");
                        return new StickClient();
                    }
                    tempWinner.incrementGameWins();
                    return tempWinner;
                }

                private void updateStats(StickClient winner)
                {
                    CR.ClientsLock.readLock().lock();
                    try
                    {
                        for (StickClient c : CR.getAllClients())
                        {
                            try
                            {
                                if(!c.getQuickplayStatus() && (c.getDbID() != -1))
                                {
                                    int win = 0;
                                    if (c.equals(winner))
                                        win = 1;
                                    PreparedStatement ps = DatabaseTools.getDbConnection().prepareStatement("UPDATE `users` SET `rounds` = `rounds` + 1, `kills` = `kills` + ?, `deaths` = `deaths` + ?, " +
                                            "`wins` = `wins` + ? WHERE `UID` = ?");
                                    ps.setInt(1, c.getGameKills());
                                    ps.setInt(2, c.getGameDeaths());
                                    ps.setInt(3, win);
                                    ps.setInt(4, c.getDbID());
                                    ps.executeUpdate();

                                    c.setGameKills(0);
                                    c.setGameDeaths(0);
                                    System.out.println("Updated stats for user: " + c.getName());
                                }

                            } catch (Exception e)
                            {
                                System.out.println("There was an error updating round stats for user " + c.getName());
                                e.printStackTrace();
                            }
                        }
                }
                finally
                {
                    CR.ClientsLock.readLock().unlock();
                }
         }
    }

         @Override
        public void finalize() throws Throwable {
          super.finalize();
    }
}

